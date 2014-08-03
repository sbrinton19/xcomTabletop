package XCOM;

import java.util.ArrayList;

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
	public boolean gene;
	public boolean psi;
	public boolean sex;
	public Human(){
		abilityList = new byte[16];
		for(int i = 0; i < abilityList.length; i++)
			abilityList[i] = -1;
		medalList = new byte[5];
		for(int i = 0; i < medalList.length; i++)
			medalList[i] = -1;
	}
	
	public static byte getAbilityID(String name){
		for(int i = 0; i < abilities.length;i++){
			if(name.equals(abilities[i]))
				return (byte) i;
		}
		for(int i = 0; i < mecAbilities.length;i++){
			if(name.equals(mecAbilities[i]))
				return (byte) (i + abilities.length);
		}
		for(int i = 0; i < psionicAbilities.length; i++){
			if(name.equals(psionicAbilities[i]))
				return (byte) (i + abilities.length+mecAbilities.length);
		}
		for(int i = 0; i < geneAbilities.length; i++){
			if(name.equals(geneAbilities[i]))
				return (byte) (i + abilities.length+mecAbilities.length+psionicAbilities.length);
		}
		return -1;
	}
	
	public static byte getMedalID(String medal){
		for(int i = 0; i < medals.length; i++){
			if(medal.equals(medals[i]))
				return (byte) i;
		}
		return -1;
	}
	
	public static String getAbilityByID(byte id){
		if(id < 0)
			return "";
		if(id < abilities.length)
			return abilities[id];
		id -= abilities.length;
		if (id < mecAbilities.length)
			return mecAbilities[id];
		id -= mecAbilities.length;
		if(id < psionicAbilities.length)
			return psionicAbilities[id];
		id -= psionicAbilities.length;
		if(id < geneAbilities.length)
			return geneAbilities[id];
		return "";
	}
	
	public String getRank(){
		if(xp < 90){
			return ranks[0];
		}
		else if(xp < 480){
			return ranks[1];
		}
		else if(xp < 700){
			return ranks[2];
		}
		else if(xp < 925){
			return ranks[3];
		}
		else if(xp < 1380){
			return ranks[4];
		}
		else if(xp < 1840){
			return ranks[5];
		}
		else if(xp < 2550){
			return ranks[6];
		}
		else
			return ranks[7];
	}
	
	public String getAbilitiesString(){
		StringBuilder abilities = new StringBuilder("");
		for(int i = 0; i < abilityList.length; i++){
			if(i != 0 && abilityList[i]!=-1)
				abilities.append(", ");
			abilities.append(getAbilityByID(abilityList[i]));
			
			if(i%4 == 0 && i != 0)
				abilities.append("<br>");
		}
		abilities.append("</html>");
		return abilities.toString();
	}
	
	public String getMedalsString(){
		StringBuilder medals = new StringBuilder("");
		for(int i = 0; i < medalList.length; i++){
			if(i != 0 && medalList[i] !=-1)
				medals.append(", ");
			try{
				medals.append(Human.medals[medalList[i]]);
			}catch(ArrayIndexOutOfBoundsException e){
				//Whatever
			}
			
		}
		return medals.toString();
	}
	
	public String[] getPossibleAbilities(){
		ArrayList<String> possible = new ArrayList<String>();
		
		for(int i = 0; i < Human.abilities.length; i++)
			possible.add(Human.abilities[i]);
		
		if(mec){
			for(int i = 0; i < Human.mecAbilities.length; i++)
				possible.add(Human.mecAbilities[i]);
		}
		else if(gene){
			for(int i = 0; i < Human.geneAbilities.length; i++)
				possible.add(Human.geneAbilities[i]);
		}
		if(psi){
			for(int i = 0; i < Human.psionicAbilities.length; i++)
				possible.add(Human.psionicAbilities[i]);
		}
		Object[] temp = possible.toArray();
		String[] result = new String[temp.length];
		for(int i = 0; i < result.length;i++)
			result[i] = (String) temp[i];
		return result;
	}
	public boolean hasAbility(String ability){
		byte abilityID = Human.getAbilityID(ability);
		if(abilityID == -1)
			return false;
		for(int i = 0; i< abilityList.length; i++){
			if(abilityList[i] == abilityID)
				return true;
		}
		return false;
	}
	
	public boolean hasMedal(String medal){
		byte medalID = Human.getMedalID(medal);
		for(int i = 0; i < medalList.length; i++){
			if(medalList[i] == medalID)
				return true;
		}
		return false;
	}

	public void addAbility(String ability){
		byte abilityID = Human.getAbilityID(ability);
		if(abilityID == -1)
			return;
		for(int i = 0; i< abilityList.length; i++){
			if(abilityList[i] == -1){
				abilityList[i] = abilityID;
				return;
			}
		}
	}
	
	public void addMedal(String medal){
		byte medalID = Human.getMedalID(medal);
		if(medalID == -1)
			return;
		for(int i =0; i< medalList.length;i++){
			if(medalList[i] == -1){
				medalList[i] = medalID;
				return;
			}
		}
	}
	
	public void removeMedal(String medal){
		byte medalID = Human.getMedalID(medal);
		if(medalID == -1)
			return;
		for(int i =0; i< medalList.length;i++){
			if(medalList[i] == medalID){
				medalList[i] = -1;
				return;
			}
		}
	}
	
	public void removeAbility(String ability){
		byte abilityID = Human.getAbilityID(ability);
		if(abilityID == -1)
			return;
		for(int i = 0; i< abilityList.length; i++){
			if(abilityList[i] == abilityID){
				abilityList[i] = -1;
				return;
			}
		}
	}
}
