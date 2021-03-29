package Utils;

import MapObjects.MapObject;

/**
 * Created by 777 on 13.05.2017.
 */
public class AddMOparameters {
    public MapObject mo;
    public boolean clashChek;
    public boolean clashChekWithYoutType;
    public int napravlenie;
    public AddMOparameters( MapObject mo,boolean clashChek,boolean clashChekWithYoutType,int napravlenie){
        this.clashChek=clashChek;
        this.mo=mo;
        this.clashChekWithYoutType=clashChekWithYoutType;
        this.napravlenie=napravlenie;
    }
}
