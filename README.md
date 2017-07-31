## Task from Qiwi

Отрисовать форму, поведение и вид которой определяется файлом в формате json (содержание файла находится в конце описания
задания и по урлу https://w.qiwi.com/mobile/form/form.json).

Форма должна реагировать на пользовательский ввод описанным в файле поведением.
Валидацию текстовых полей ввода можно проводить по кнопке "валидировать", но в идеале, использовать RxJava throttle метод, то есть пользователь вводит, что хочет, и если ввода нет определенное количество времени – валидировать содержимое поля.
Можно использовать любой архитектурный паттерн, но для этой задачи неплохо подходит MVVM.

Очень желательно разделять логику построения дерева и рендеринг формы.

### Libraries used
* Google Gson
* RxJava/RxAndroid
* RxBindings
* Retrolambda
