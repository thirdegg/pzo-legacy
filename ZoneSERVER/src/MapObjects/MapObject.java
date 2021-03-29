package MapObjects;

import MapObjects.Units.Player;
import Utils.Rectang;

import java.util.concurrent.atomic.AtomicLong;
// супер класс для всего что находится на игровой карте
public class MapObject {
    //гемератор айдишников
    public static AtomicLong generatorId = new AtomicLong();
    // тип объекта и расстояние пикселей на котором находится хитбокс(квадрат для сталкновений) от x y
    public int tip, plasrecx, plasrecy;
    // позиция в мире
    public float x, y;
    // середина хитбокса или просто центральная точка объекта
    public float centx, centy;
    // ну тут понятно ))
    public long id;
    // remov выставляется true ели надо удальть объект с карты
    // unit указыват что объект является юнитом (игроки кабаны зомби фригеры)
    // clientvisible указывает виден ли объект для игрока(клиента) (есть некоторые невидимые аномалии)
    public boolean remov, unit, clientvisible = true;
    // хитбокс(прямоугольник для определения столкновений)
    public Rectang rectang;
    // может ли объект быть атакован (только юниты)
    public boolean canbeattacked;
    // id канала для передачи данных клиенту через сеть (использется только для игроков)
    public int idchanel, state;
    // некоторые объекты не имеют хитбокса и для взаимодействия используют круг (центральная точка centx centy)
    public int radius;
// расстояние при котором игрок обнаруживает данный объект
    public int raddclient=300;
    // подтип объекта (артефакт коробка аномалия юнит)// нуждается в доработке
    public int podtip;
// обновление логики переопределяется не всеми объектами (только теми у кого есть логика)
    //переделать
    public boolean ataknpc;
    public void run(float delta) {
    }
    // метод через который можно послать какой либо сигнал объекту
    //1 stop ataked 2 rem playuer 3 you kill me 4 start modifikarion 5 stop modofikation
    public void signal(int tip,Object object){}
    // дебафф который трати определенное кол-во жизни объекту определенное время и через определенный промежуток
    // нпдо будет перенести в метод signal
    public void ataked(long id, float kolvolife, float timeperiod, float fulltime, int tipuron, boolean notendtime,boolean send,boolean fasturon) {
    }
    // одноразовое уменьшение кол-ва жизней
    // надо будет перенести в signal
    public void ataked(long id, float kolvolife,int tipuron,boolean send) {
    }
    // устанавливает объек в определенную позицию на карте
    public void setPosition(float xx,float yy){
        x=xx;y=yy;
        if(rectang!=null){
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;}
        else{
        centx=x;centy=y;
        }
    }
    // этот метод добавляет данный объект в список объектов игрока и возвращает доп параметры // переопределяется не всеми объектами
    // вызывается в классе WMap в методе addObjectsToPlayer когда объект попадает в зону видимости для игрока (переменная raddclient)
    public String addedAndDop(Player pl, String str) {
        pl.mapobjects.put(id, this);
        return str;
    }
    // метод возвращающий состояние объекта используется только для передачи данных клиенту(игроку) // переопределяется не всеми объектами
    // вызывается в классе WMap в методе addObjectsToPlayer когда объект попадает в зону видимости для игрока (переменная raddclient)
    public String getStateForClient() {
        return null;
    }
}
