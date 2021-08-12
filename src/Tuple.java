
public class Tuple {
	private Object[] datas;
	private int lastIndex;
	
	public Tuple(int length) {
		this.datas=new Object[length];
		lastIndex=0;
	}
	
	public void append(Object dataToAdd) {
		datas[lastIndex]=dataToAdd;
		lastIndex++;
	}
	public void deleteAllElements() {
		for (int i = 0; i < datas.length; i++) {
			datas[i]=null;
		}
		lastIndex=0;
	}
	
	public void delete(int indexToDelete) {
		if (datas[indexToDelete]!=null) {
			datas[indexToDelete]=null;
		}else {
			System.out.println("Given index is empty, failed to delete");
		}
	}
	
	public int[] emptyIndexes() {
		int emptyIndex=0;
		for (int i = 0; i < lastIndex; i++) {
			if (datas[i]==null) {
				emptyIndex++;
			}
		}
		int[] emptyIndexes=new int[emptyIndex];
		int count=0;
		System.out.println("Empty Indexes");
		for (int i = 0; i < lastIndex; i++) {
			if (datas[i]==null) {
				emptyIndexes[count]=i;
				System.out.println(i +" ");
				count++;
			}
		}
		return emptyIndexes;
	}
	public int length() {
		return datas.length;
	}
	public Object getElement(int index) {
		if (datas[index]!=null) {
			return datas[index]; 
		}else {
			System.out.println("Given index is empty, failed to get");
			return null;
		}
	}
	public void setElement(int index, Object dataToAdd) {
		if (datas[index]==null) {
			datas[index]=dataToAdd;
		}else {
			System.out.println("Given index is not empty, failed to add");
		}
	}
}
