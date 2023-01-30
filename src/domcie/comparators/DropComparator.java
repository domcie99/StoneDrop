package domcie.comparators;

import java.util.Comparator;

import domcie.objects.User;

@SuppressWarnings("hiding")
public class DropComparator<User> implements Comparator<User> {

	@Override
	public int compare(Object o1, Object o2) {
		@SuppressWarnings("unchecked")
		User s1 = (User)o1;
		@SuppressWarnings("unchecked")
		User s2 = (User)o2;
		if(((domcie.objects.User) s1).getXp()==((domcie.objects.User) s2).getXp()){
			return 0;
		} else if(((domcie.objects.User) s1).getXp()>=((domcie.objects.User) s2).getXp()){
			return 1;
		} else {
			return -1;
		}
	}

}
