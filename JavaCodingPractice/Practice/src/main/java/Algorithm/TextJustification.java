package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class TextJustification {
	public List<String> format(String[] src, int max) {
        List<String> res = new ArrayList<String>();
        if (src == null || src.length == 0) return res;
        int counter = 0;
        int prev = 0;
        for (int i=0; i<src.length; ++i) {
            if (counter + src[i].length() + i - prev > max) {
                int spaceNum = 0;
                int extraNum = 0;
                if ((i - prev - 1) >0 ) {
                    spaceNum = (max - counter) / (i - prev - 1);
                    extraNum = (max - counter) % (i - prev - 1);
                }
                StringBuffer sb = new StringBuffer();
                for (int j = prev; j<i; ++j) {
                    sb.append(src[j]);
                    if (j < i - 1) {
                        for (int k=0; k<spaceNum; ++k) {
                            sb.append(' ');
                        }
                        if (extraNum > 0) {
                            sb.append(' ');
                            --extraNum;
                        }
                    }
                }
                for (int j=sb.length(); j < max; ++j) {
                    sb.append(' ');
                }
                res.add(sb.toString());
                counter = 0;
                prev = i;
            }
            counter += src[i].length();
        }
        StringBuffer sb = new StringBuffer();
        for(int i = prev;i < src.length; i++) {
            sb.append(src[i]);
            if (sb.length() < max)
                sb.append(" ");
        }
        
        for(int i = sb.length(); i < max;i++) {
            sb.append(" ");
        }
        res.add(sb.toString());
        return res;
    }
	
	//with line number
	public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<String>();
        int curLen = 0;
        List<String> curList = new ArrayList<String>();
        for (int i=0; i<words.length;) {
            int numberLen = String.valueOf(res.size()).length();
            if (curLen + curList.size() + 1 + words[i].length() + numberLen > maxWidth) {
                int interval = (maxWidth - curLen - numberLen) / curList.size();
                int mod = (maxWidth - curLen - numberLen) % curList.size();
                StringBuilder sbuilder =new StringBuilder();
                for (int j=0; j<curList.size(); ++j) {
                    sbuilder.append(curList.get(j));
                    for (int k=0; k<interval; ++k) {
                        sbuilder.append(' ');
                    }
                    if (mod > 0) {
                        sbuilder.append(' ');
                        --mod;
                    }
                }
                sbuilder.append(String.valueOf(res.size()));
                res.add(sbuilder.toString());
                curList = new ArrayList<String>();
                curLen = 0;
            } else {
                curList.add(words[i]);
                curLen += words[i].length();
                ++i;
            }
        }
        return res;
    }
}
