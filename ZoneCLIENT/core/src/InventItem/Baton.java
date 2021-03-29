package InventItem;

public class Baton extends Item {
    public Baton(int idd) {
        id = idd;
        tipitem = 5;
        name="Батон";
        opisanie="Слегка черствый батон\nЭффект: голод -10";
        cena=30;
        use=true;
    }

}
