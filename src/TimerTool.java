public class TimerTool {

	private long lastMS;

	private long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}

	public boolean hasReached(double milliseconds, boolean reset) {
		if (this.getCurrentMS() - this.lastMS >= milliseconds) {
			if (reset) {
				this.reset();
			}
			return true;
		}
		return false;
	}
	
	public boolean hasReached(double milliseconds) {
		if (this.getCurrentMS() - this.lastMS >= milliseconds) {
			return true;
		}
		return false;
	}

	public void reset() {
		this.lastMS = this.getCurrentMS();
	}

	public long getTime() {
		return System.nanoTime() / 1000000L;
	}

}
