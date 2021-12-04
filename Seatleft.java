package bookdemo;

public class Seatleft {
	private int floor;
	private int port;
	private int seat;
	private int left;
	public Seatleft(int floor, int port, int seat, int left) {
		super();
		this.floor = floor;
		this.port = port;
		this.seat = seat;
		this.left = left;
	}
	
	public Seatleft() {
		
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	@Override
	public String toString() {
		return "Seatleft [floor=" + floor + ", port=" + port + ", seat=" + seat + ", left=" + left + "]";
	}
	
	
	
}
