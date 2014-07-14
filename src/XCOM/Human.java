package XCOM;

public class Human {
	public static final String[] classes = { "Heavy", "Assault", "Support", "Sniper" };
	public static final String[] nationalities = { "Egyptian", "Nigerian", "South African", "Australian",
		"Chinese", "Indian", "Israeli", "Japanese", "South Korean", "Belgian", "French", "German", "Greek",
		"Irish", "Italian", "Dutch", "Norwegian", "Polish", "Russian", "Scottish", "Spainish", "Swedish", 
		"English", "Ukrainian", "Canadian", "Mexican", "American", "Brazilian", "Argentinian", "Finnish"};
	public static final String[] abilities = {"Aggression", "Battle Scanner", "Bring 'Em On", "Bullet Swarm", 
		"Close & Personal", "Close Combat Specialist", "Combat Drugs", "Covering Fire", "Damn Good Ground", 
		"Danger Zone", "Deep Pockers", "Dense Smoke", "Disabling Shot", "Double Tap", "Executioner", 
		"Extra Conditioning", "Field Medic", "Fire Rocket", "Flush", "Grenadier", "Gunslinger", "Headshot", 
		"HEAT Ammo", "Holo-Targeting", "In The Zone", "Killer Instinct", "Lightning Reflexes", "Low Profile",
		"Mayhem", "Opportunist", "Rapid Fire", "Rapid Reaction", "Resilience", "Revive", "Suppression", 
		"Rocketeer", "Run & Gun", "Savior", "Sentinel", "Shredder Rocket", "Smoke & Mirrors", "Smoke Grenade", 
		"Snap Shot", "Sprinter", "Squadsight", "Tactical Sense", "Will to Survive"  };
	public static final String[] mecAbilities = {"Body Shield", "Distortion Field", "Platform Stability", 
		"Shock-Absorbent Armor", "Collateral Damage", "Advanced Fire Control", "Automated Threat Assessment",
		"Vital-Point Targeting", "Damage Control", "Jetboot Module", "One for All", "Repair Servos", 
		"Expanded Storage", "Overdrive", "Reactive Targeting Sensors", "Absorbtion Fields"};
	public static final String[] psionicAbilities = {"Mindfray", "Mind Control", "Psi Panic", "Psi Inspiration", 
		"Telekinetic Field", "Rift"};
	public static final String[] geneAbilities = {"Neural Feedback", "Neural Damping", "Hyper Reactive Pupils",
		"Depth Perception", "Adrenal Neurosympathy", "Secondary Heart", "Bioelectric Skin", "Mimetic Skin", 
		"Muscle Fiber Density", "Adaptive Bone Marrow"};
	public static final String[] medals = {"Urban Combat Badge", "Defender's Medal", "International Service Cross",
		"Council Medal of Honor", "Star of Terra"};
	public static final String[] ranks = {"Rookie", "Squaddie", "Corporal", "Sergeant", "Lieutenant", "Captain", 
		"Major", "Colonel"};
	
	
	public String name;
	public String owner;
	public int xp;
	public int aim;
	public int will;
	public byte health;
	public byte movement;
	public byte charClass;
	public byte nationality;
	//Max 16 (Genemodded, volunteer, colonel)
	public byte[] abilityList;
	
	//Max 5
	public byte[] medalList;
	public boolean mec;
	public boolean psi;
	public boolean sex;
	public Human(){
		abilityList = new byte[16];
		medalList = new byte[5];
	}
}
