
public class Hero {
	private int hp;
	
	public Hero()
	{
		this.hp = 100;
	}
	
	public void deal(int dmg)
	{
		hp -= dmg;
	}
	
	public int getHP(){
		return hp;
	}
		
}
