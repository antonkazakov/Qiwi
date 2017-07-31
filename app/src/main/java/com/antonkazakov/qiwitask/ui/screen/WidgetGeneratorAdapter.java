package com.antonkazakov.qiwitask.ui.screen;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.antonkazakov.qiwitask.R;
import com.antonkazakov.qiwitask.data.beans.Element;
import com.antonkazakov.qiwitask.data.beans.Validator;
import com.antonkazakov.qiwitask.data.beans.Widget;
import com.antonkazakov.qiwitask.ui.validation.SimpleRegexValidator;
import com.antonkazakov.qiwitask.ui.validation.Validatable;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class WidgetGeneratorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = WidgetGeneratorAdapter.class.getSimpleName();

    private static final String RADIO = "radio";
    private static final String TEXT = "text";

    /**
     * Item views types
     */
    private static final int WRITABLE_ITEM = 0;
    private static final int SPINNER_ITEM = 1;
    private static final int UNDEFINED_ITEM = 42;

    /**
     * Caching validators. Using regex pattern as a key.
     */
    private static Map<String, Validatable> validatorsCache = new HashMap<>();
    /**
     * This collection will be filled after first load
     */
    private List<Element> elements = new ArrayList<>();
    /**
     * This collection is actually item that will be displayed
     */
    private List<Element> elementsToBeShown = new ArrayList<>();

    /**
     * Unique keyset for element names. Used to know was it initial child creation or not,
     * because spinner using custom clicklistener we need to initialize default selected item.
     */
    private Set<String> elementNamesKeySet = new HashSet<>();

    /**
     * Container for subs. Must be cleared in activity callback
     */
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public int getItemViewType(int position) {
        if (elementsToBeShown.get(position).getView() != null) {
            if (elementsToBeShown.get(position).getView().getWidget().getType().equals(RADIO)) {
                return SPINNER_ITEM;
            } else {
                return WRITABLE_ITEM;
            }
        }
        return UNDEFINED_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SPINNER_ITEM) {
            return new SpinnerItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false));
        } else {
            return new WritableItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.writable_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            Element element = elementsToBeShown.get(holder.getAdapterPosition());
            if (element.getValidator() != null) {
                Validatable validatable = validatorsCache.get(element.getValidator().getPredicate().getPattern());
                if (validatable == null) {
                    validatable = new SimpleRegexValidator(element.getValidator());
                }
                if (holder.getItemViewType() == WRITABLE_ITEM) {
                    WritableItemViewHolder writableItemViewHolder = (WritableItemViewHolder) holder;
                    buildWritableItem(element, writableItemViewHolder, (SimpleRegexValidator) validatable);
                }
                if (holder.getItemViewType() == SPINNER_ITEM) {
                    SpinnerItemViewHolder spinnerItemViewHolder = (SpinnerItemViewHolder) holder;
                    buildSpinnerItem(element, spinnerItemViewHolder);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return elementsToBeShown.size();
    }


    /**
     * Building simple EditText item with hint, validator
     *
     * @param element
     * @param writableItemViewHolder
     */
    private void buildWritableItem(Element element,
                                   WritableItemViewHolder writableItemViewHolder,
                                   SimpleRegexValidator simpleRegexValidator) {
        if (element.getView().getWidget().getKeyboard() != null &&
                element.getView().getWidget().getKeyboard().equals("numeric")) {
            writableItemViewHolder.editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            writableItemViewHolder.editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        writableItemViewHolder.editText.setHint(element.getView().getPrompt());
        writableItemViewHolder.editText.setText(element.getView().getText());
        writableItemViewHolder.textInputLayout.setError(null);

        Subscription rxTextViewObservable = RxTextView.textChanges(writableItemViewHolder.editText)
                .filter(charSequence -> charSequence.length() >= 2)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!simpleRegexValidator.isValid(s)) {
                        element.getView().setText(s);
                        writableItemViewHolder.textInputLayout.setError(simpleRegexValidator.getMessage());
                    } else {
                        writableItemViewHolder.textInputLayout.setError(null);
                    }
                });

        compositeSubscription.add(rxTextViewObservable);
    }

    /**
     * Building spinner item with list of Choices
     *
     * @param element
     * @param spinnerItemViewHolder
     */
    private void buildSpinnerItem(Element element,
                                  SpinnerItemViewHolder spinnerItemViewHolder) {
        List<Widget.Choice> choices = element.getView().getWidget().getChoices();
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(
                spinnerItemViewHolder.itemView.getContext(),
                R.layout.spinner_child_item,
                choices);
        spinnerItemViewHolder.spinner.setAdapter(spinnerAdapter);

        /**
         * Using custom spinner click listener because OnItemSelectedListener will cause infinite loop
         * or will trigger twice depending on other actions.
         * The best solution I got -> create custom listener which will only trigger when dropdownitem was clicked
         */
        Subscription subscription1 = Observable.unsafeCreate((Observable.OnSubscribe<Integer>) subscriber -> {
            SpinnerAdapter.OnPleaseClickListener onPleaseClickListener =
                    subscriber::onNext;
            spinnerAdapter.setPleaseClickListener(onPleaseClickListener);
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    insertChilds(choices.get(integer).getValue(),
                            element.getName(),
                            elements);
                    new Handler().post(() -> spinnerItemViewHolder.spinner.setSelection(integer));
                }, throwable -> Log.e(TAG, "call: ", throwable));

        /**
         * To default select first item in spinner
         */
        Subscription subscription2 = RxAdapterView.itemSelections(spinnerItemViewHolder.spinner)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (!elementNamesKeySet.contains(element.getName())) {
                        insertChilds(choices.get(integer).getValue(),
                                element.getName(),
                                elements);
                        elementNamesKeySet.add(element.getName());
                    }
                }, throwable -> Log.e(TAG, "call: ", throwable));

        compositeSubscription.addAll(subscription1, subscription2);
    }

    /**
     * Inserting all field values w/o dependencies
     *
     * @param elements
     */
    public void insertStartItems(List<Element> elements) {
        for (Element element : elements) {
            if (element.getType().equals("field")) {
                this.elementsToBeShown.add(element);
            }
            this.elements.add(element);
        }
        notifyDataSetChanged();
    }

    /**
     * Insert
     *
     * @param toValidate
     * @param parent
     * @param elements
     */
    @SuppressWarnings("unchecked")
    private void insertChilds(String toValidate, String parent, List<Element> elements) {
        deleteChildOf(parent);
        for (Element element : elements) {
            if (element.getCondition() != null &&
                    element.getCondition().getField().equals(parent) &&
                    element.getCondition().getPredicate() != null) {
                Validatable<String> v = new SimpleRegexValidator(new Validator(element.getCondition().getPredicate()));
                if (v.isValid(toValidate)) {
                    for (int i = 0; i < element.getContent().getElements().size(); i++) {
                        element.getContent().getElements().get(i).setParent(parent);
                        if (!elementsToBeShown.contains(element.getContent().getElements().get(i))) {
                            element.getContent().getElements().get(i).getView().setText("");
                            elementsToBeShown.add(element.getContent().getElements().get(i));
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Using iterator to child nodes of parent node
     * if selected node have children nodes too then
     * recursively iterate over it and remove its child too up to the leafs.
     *
     * @param parentFiled
     */
    private void deleteChildOf(String parentFiled) {
        Iterator<Element> it = elementsToBeShown.iterator();
        while (it.hasNext()) {
            Element element = it.next();
            if (element.getParent() != null &&
                    element.getParent().equals(parentFiled)) {
                if (element.getContent() != null && element.getContent().getElements() != null) {
                    deleteChildOf(parentFiled);
                }
                it.remove();
            }
        }
    }

    /**
     * Clead RxBindings subscriptions after detaching adapter from activity
     */
    public void clearSubscriptions() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.clear();
        }
    }

}
