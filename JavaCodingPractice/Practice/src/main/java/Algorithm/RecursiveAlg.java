package Algorithm;

public class RecursiveAlg {
    /*---------------------------------1.Given a Tuple for eg. (a, b, c)..----------------------------------*/
    /*---------------------------------Output : (*, *, *), (*, *, c), (*, b, *), (*, b, c),-----------------*/
    /*---------------------------------(a, *, *), (a, *, c), (a, b, *), (a, b, c)---------------------------*/
    public void populateSequence(char[] src, int rec, StringBuilder out) {
    	if (rec == src.length) {
    		System.out.println(out);
    		return;
    	}
    	out.append('*');
    	populateSequence(src, rec + 1, out);
    	out.setLength(out.length() - 1);
    	out.append(src[rec]);
    	populateSequence(src, rec + 1, out);
    	out.setLength(out.length() - 1);
    }
}
