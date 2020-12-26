package babaIsYou.entity.entityEnum;

public enum EventBabaGame {
	Defeat(0),Win(1),Stop(1),Good(2),Destroy(3),DestroyAll(4);



	private final int idEvent;

	EventBabaGame(int idEvent) {
		this.idEvent = idEvent;
	}

}
