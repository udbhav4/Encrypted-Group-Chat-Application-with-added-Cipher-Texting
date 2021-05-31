import java.util.*;

class Baconian
{
	public static String cipher(String msg)
	{              
                  Dictionary<Character, String> baconian = new Hashtable<>();
                  baconian.put('A', "aaaaa");
                  baconian.put('B', "aaaab");
                  baconian.put('C', "aaaba");
                  baconian.put('D', "aaabb");
                  baconian.put('E', "aabaa");
                  baconian.put('F', "aabab");
                  baconian.put('G', "aabba");
                  baconian.put('H', "aabbb");
                  baconian.put('I', "abaaa");
                  baconian.put('J', "abaab");
                  baconian.put('K', "ababa");
                  baconian.put('L', "ababb");
                  baconian.put('M', "abbaa");
                  baconian.put('N', "abbab");
                  baconian.put('O', "abbba");
                  baconian.put('P', "abbbb");
                  baconian.put('Q', "baaaa");
                  baconian.put('R', "baaab");
                  baconian.put('S', "baaba");
                  baconian.put('T', "baabb");
                  baconian.put('U', "babaa");
                  baconian.put('V', "babab");
                  baconian.put('W', "babba");
                  baconian.put('X', "babbb");
                  baconian.put('Y', "bbaaa");
                  baconian.put('Z', "bbaab");

                  char[] encpt1 = msg.toUpperCase().toCharArray();
                  String encpt2 = new String(""); 

                  for(int i=0; i<msg.length(); i++)
                  {
                     
                     if(encpt1[i] == ' ') {
                    	 encpt2 += ' ';
                     }

                     else if(encpt1[i] == '.' || encpt1[i] == '?' || encpt1[i] == '!' || encpt1[i] == ':' || encpt1[i] == '\"' || encpt1[i] == '\'' || encpt1[i] == '(' || encpt1[i] == ')') {
                    	 encpt2 += encpt1[i];
                     }
                     
                     else
                    	 encpt2 = encpt2 + baconian.get(encpt1[i]);
                  }
                  
                  return encpt2;
	}

    public static String decipher(String encpt)
    {

                  Dictionary<String, String> baconian = new Hashtable<>();
                  baconian.put("aaaaa", "A");
                  baconian.put("aaaab", "B");
                  baconian.put("aaaba", "C");
                  baconian.put("aaabb", "D");
                  baconian.put("aabaa", "E");
                  baconian.put("aabab", "F");
                  baconian.put("aabba", "G");
                  baconian.put("aabbb", "H");
                  baconian.put("abaaa", "I");
                  baconian.put("abaab", "J");
                  baconian.put("ababa", "K");
                  baconian.put("ababb", "L");
                  baconian.put("abbaa", "M");
                  baconian.put("abbab", "N");
                  baconian.put("abbba", "O");
                  baconian.put("abbbb", "P");
                  baconian.put("baaaa", "Q");
                  baconian.put("baaab", "R");
                  baconian.put("baaba", "S");
                  baconian.put("baabb", "T");
                  baconian.put("babaa", "U");
                  baconian.put("babab", "V");
                  baconian.put("babba", "W");
                  baconian.put("babbb", "X");
                  baconian.put("bbaaa", "Y");
                  baconian.put("bbaab", "Z");

                  int i;
                  String decpt1 = new String("");
                  for(i=0; i<encpt.length(); i=i+5)
                  {
                	  if(encpt.charAt(i) == ' ') {
                     	 i-=4;
                		  decpt1 += ' ';
                      }

                      else if(encpt.charAt(i) == '.' || encpt.charAt(i) == '?' || encpt.charAt(i) == '!' || encpt.charAt(i) == ':' || encpt.charAt(i) == '\"' || encpt.charAt(i) == '\'' || encpt.charAt(i) == '(' || encpt.charAt(i) == ')') {
                    	  decpt1 += encpt.charAt(i);
                    	  i-=4;
                      }
                      
                      else
                    	  decpt1 += baconian.get(encpt.substring(i,i+5));
                  }
                  return decpt1;
           
          }
}