package fr.univLille.cristal.shex.util;

import net.sf.saxon.expr.EarlyEvaluationContext;
import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.functions.Matches;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.value.AtomicValue;

/**
 * 
 * @author Iovka Boneva
 * 10 oct. 2017
 */
public class XPath {
	public static XPathContext context = new EarlyEvaluationContext((new Processor(false)).getUnderlyingConfiguration());
	public static Matches matcher = new Matches();
	
	
	/** Respecting syntaxe in 3.1
	 * 
	 */
	public static boolean matches(String input, String regex, String flags) {
		String tmpregex = regex.replaceAll("\00","NULLCHARACTER0000REPLACEMENT");
		String tmpinput = input.replaceAll("\00","NULLCHARACTER0000REPLACEMENT");

		AtomicValue inputAV = new XdmAtomicValue(tmpinput).getUnderlyingValue();
		AtomicValue regexAV = new XdmAtomicValue(tmpregex).getUnderlyingValue();
		if (flags == null) flags = "";
		try {
			return matcher.evalMatches(inputAV, regexAV, flags, context);
		} catch (XPathException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static String normalizeRegex(String st) {
	    StringBuilder sb = new StringBuilder(st.length());
	    for (int i = 0; i < st.length(); i++) {
	        char ch = st.charAt(i);
	        if (ch == '\\') {
	            char nextChar = (i == st.length() - 1) ? '\\' : st.charAt(i + 1);
	            // Octal escape?
	            if (nextChar >= '0' && nextChar <= '7') {
	                String code = "" + nextChar;
	                i++;
	                if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                        && st.charAt(i + 1) <= '7') {
	                    code += st.charAt(i + 1);
	                    i++;
	                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                            && st.charAt(i + 1) <= '7') {
	                        code += st.charAt(i + 1);
	                        i++;
	                    }
	                }
	                sb.append((char) Integer.parseInt(code, 8));
	                continue;
	            }
	            switch (nextChar) {
	            case '\\':
	            	sb.append('\\');
	                ch = '\\';
	                break;
	            case 'b':
	                ch = '\b';
	                break;
	            case 'f':
	                ch = '\f';
	                break;
	            case 'n':
	                ch = '\n';
	                break;
	            case 'r':
	                ch = '\r';
	                break;
	            case 't':
	                ch = '\t';
	                break;
	            case '\"':
	                ch = '\"';
	                break;
	            case '\'':
	                ch = '\'';
	                break;
	            case '/':
	            	ch ='/';
	            	break;
	            // Hex Unicode: u????
	            case 'u':
	                if (i >= st.length() - 5) {
	                    ch = 'u';
	                    break;
	                }
	                int code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                                + st.charAt(i + 4) + st.charAt(i + 5), 16);
	                sb.append(Character.toChars(code));
	                i += 5;
	                continue;
	            case 'U':
	                if (i >= st.length() - 9) {
	                    ch = 'U';
	                    break;
	                }
	                code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                           + st.charAt(i + 4) + st.charAt(i + 5)
	                           + st.charAt(i + 6) + st.charAt(i + 7)
	                           + st.charAt(i + 8) + st.charAt(i + 9)
	                                , 16);
	                sb.append(Character.toChars(code));
	                i += 9;
	                continue;
	            default:
	            	sb.append("\\");
	            	ch = nextChar;
	            }
	            i++;
	            
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}
	
//	/**
//	 * Unescapes a string that contains standard Java escape sequences.
//	 * <ul>
//	 * <li><strong>\b \f \n \r \t \" \'</strong> :
//	 * BS, FF, NL, CR, TAB, double and single quote.</li>
//	 * <li><strong>\X \XX \XXX</strong> : Octal character
//	 * specification (0 - 377, 0x00 - 0xFF).</li>
//	 * <li><strong>\\uXXXX</strong> : Hexadecimal based Unicode character.</li>
//	 * </ul>
//	 * 
//	 * @param st
//	 *            A string optionally containing standard java escape sequences.
//	 * @return The translated string.
//	 */
	public static String unescapeJavaString(String st) {
	    StringBuilder sb = new StringBuilder(st.length());
	    for (int i = 0; i < st.length(); i++) {
	        char ch = st.charAt(i);
	        if (ch == '\\') {
	            char nextChar = (i == st.length() - 1) ? '\\' : st.charAt(i + 1);
	            // Octal escape?
	            if (nextChar >= '0' && nextChar <= '7') {
	                String code = "" + nextChar;
	                i++;
	                if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                        && st.charAt(i + 1) <= '7') {
	                    code += st.charAt(i + 1);
	                    i++;
	                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                            && st.charAt(i + 1) <= '7') {
	                        code += st.charAt(i + 1);
	                        i++;
	                    }
	                }
	                sb.append((char) Integer.parseInt(code, 8));
	                continue;
	            }
	            switch (nextChar) {
	            case '\\':
	                ch = '\\';
	                break;
	            case 'b':
	                ch = '\b';
	                break;
	            case 'f':
	                ch = '\f';
	                break;
	            case 'n':
	                ch = '\n';
	                break;
	            case 'r':
	                ch = '\r';
	                break;
	            case 't':
	                ch = '\t';
	                break;
	            case '\"':
	                ch = '\"';
	                break;
	            case '\'':
	                ch = '\'';
	                break;
	            // Hex Unicode: u????
	            case 'u':
	                if (i >= st.length() - 5) {
	                    ch = 'u';
	                    break;
	                }
	                int code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                                + st.charAt(i + 4) + st.charAt(i + 5), 16);
	                sb.append(Character.toChars(code));
	                i += 5;
	                continue;
	            case 'U':
	                if (i >= st.length() - 9) {
	                    ch = 'U';
	                    break;
	                }
	                code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                           + st.charAt(i + 4) + st.charAt(i + 5)
	                           + st.charAt(i + 6) + st.charAt(i + 7)
	                           + st.charAt(i + 8) + st.charAt(i + 9)
	                                , 16);
	                sb.append(Character.toChars(code));
	                i += 9;
	                continue;
	            default:
	            	sb.append("\\");
	            	ch = nextChar;
	            }
	            i++;
	            
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
	}
	

//	public static void main(String[] args) {
//		String input="/	\\b\\n\\r\\f\\a𝒸";
//		String regex = "^\\/	\\n\\r-\\\\a\\U0001D4B8$";
//		System.out.println(matches(input,regex,null));
//	}
}
