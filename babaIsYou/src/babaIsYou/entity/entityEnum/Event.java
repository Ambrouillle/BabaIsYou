package babaIsYou.entity.entityEnum;

public enum Event {
	Defeat(0),Win(1),Stop(1),Good(2);
	 
	

	private  int idEvent;
	
	Event(int idEvent) {
		this.idEvent = idEvent;
	}

}
