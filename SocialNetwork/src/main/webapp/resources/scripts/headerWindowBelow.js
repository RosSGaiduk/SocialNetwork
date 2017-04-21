/**
 * Created by Rostyslav on 14.03.2017.
 */
function header(){
    var darkLayer = document.getElementById("shadow");
    if (darkLayer==null) {
        darkLayer = document.createElement('div'); // слой затемнения
        darkLayer.id = 'shadow'; // id чтобы подхватить стиль
        document.body.appendChild(darkLayer); // включаем затемнение
    }
    var modalWin = document.getElementById('popupWin'); // находим наше "окно"
    modalWin.style.display = 'block'; // "включаем" его
    modalWin.scrollTop = 0;

    darkLayer.onclick = function () {  // при клике на слой затемнения все исчезнет
        darkLayer.parentNode.removeChild(darkLayer); // удаляем затемнение
        modalWin.style.display = 'none'; // делаем окно невидимым
        while (modalWin.firstChild) modalWin.removeChild(modalWin.firstChild);
        return false;
    };
}