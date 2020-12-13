
package babaIsYou.entity.entityEnum;

public enum Events {
  Defeat(0),Win(1),Stop(1),Good(2);



  private  int idEvent;

  Events(int idEvent) {
    this.idEvent = idEvent;
  }

}