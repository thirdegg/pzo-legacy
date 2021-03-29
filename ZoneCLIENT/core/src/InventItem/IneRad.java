package InventItem;

public class IneRad extends Item {
    int rad = 5;

    public IneRad(int idd) {
        id = idd;
        tipitem = 10;
        name="Инъекция от радиации";
        opisanie="Шприц с инекцией от радиации\nЭффект: радиация -2.5";
        cena=30;
        use=true;
    }

}
