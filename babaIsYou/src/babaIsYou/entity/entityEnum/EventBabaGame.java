package babaIsYou.entity.entityEnum;

public enum EventBabaGame {
	Defeat(0),Win(1),Stop(1),Good(2),Destroy(3),DestroyAll(4),Explode(5);



	private final int idEvent;

	EventBabaGame(int idEvent) {
		this.idEvent = idEvent;
	}
	
	/**
	 * Getter for idEvent
	 * @return this.idEvent
	 */
	public int getIdEvent() {
		return idEvent;
	}
	
	

}
