// ANTLR4 Equivalent of accompanying bnf, developed in
// http://www.w3.org/2005/01/yacker/uploads/ShEx3
// Updated to Jul 27 AM ShEx3
// Updated to Aug 23 AM ShEx3 (last change was EGP 20150820)
// Sept 21 AM disallow single internal unary (e.g. {(:p .{2}){3}}
//            Change (non-standard) "codeLabel" to "productionName"
// Oct 26 - change annotation predicate to include rdftype (how did this slip in to the production rules?
// Dec 30 - update to match http://www.w3.org/2005/01/yacker/uploads/ShEx2/bnf with last change "EGP 20151120"
// May 23, 2016 - Update to match http://www.w3.org/2005/01/yacker/uploads/ShEx2/bnf with last change "EGP20160520" AND ';' separator and '//' for annotations
// May 24, 2016 - EGP20150424
// Aug 11, 2016 - EGP20160708
// Sep 14, 2016 - Revised to match Eric's latest reshuffle
// Sep 24, 2016 - Switched to TT grammar (vs inner and outer shapes)
// Sep 26, 2016 - Refactored to match https://raw.githubusercontent.com/shexSpec/shex.js/7eb770fe2b5bab9edfe9558dc07bb6f6dcdf5d23/doc/bnf
// Oct 27, 2016 - Added comments to '*', '*' and '?' to facilitate parsing
// Oct 27, 2016 - Added qualifier rule to be reused by shapeDefinition and inlineShapeDefinition
// Oct 27, 2016 - Added negation rule
// Mar 03, 2017 - removed ^^-style facet arguments per shex#41
// Mar 03, 2017 - switch to ~/regexp/
// Apr 09, 2017 - removed WS fragment (unused)
// Apr 09, 2017 - revise REGEXP definition
// Apr 09, 2017 - factor out REGEXP_FLAGS so we don't have to parse them out
// Apr 09, 2017 - literalRange / languageRange additions
// Apr 09, 2017 - factor out shapeRef to match spec
// Apr 09, 2017 - update repeatRange to allow differentiation of {INTEGER} and {INTEGER,}
// Apr 09, 2017 - add STEM_MARK and UNBOUNDED tokens to eliminate lex token parsing
// Jun 10, 2018 - add empty language stem


grammar ShapeMap;

shapeMap                    : shapeAssociation (',' shapeAssociation)* ;
shapeAssociation            : nodeSpec shapeSpec ;
shapeSpec                   : '@' (iri | 'START') ;
nodeSpec                    : objectTerm | triplePattern ;
triplePattern               : '{' 'FOCUS' predicate (objectTerm | '_') '}'
                            | '{' (subjectTerm | '_') predicate  'FOCUS' '}'
                            ;
objectTerm                  : subjectTerm
                            | literal
                            ;
subjectTerm                 : iri
                            | blankNode
                            ;
literal                     : numericLiteral
                            | rdfLiteral
                            | booleanLiteral
                            ;				
numericLiteral              : INTEGER
				            | DECIMAL
				            | DOUBLE
				            ;
rdfLiteral                  : langString | string ('^^' iri)? ;
booleanLiteral              : KW_TRUE
				            | KW_FALSE
				            ;
string                      : STRING_LITERAL_LONG1
                            | STRING_LITERAL_LONG2
                            | STRING_LITERAL1
				            | STRING_LITERAL2
				            ;
langString                  : ( STRING_LITERAL_LONG1 | STRING_LITERAL_LONG2 | STRING_LITERAL1 | STRING_LITERAL2 ) LANGTAG ;
predicate                   : iri
				            | rdfType
			            	;
rdfType			            : RDF_TYPE ;
iri                         : IRIREF
			            	| prefixedName
				            ;
prefixedName                : PNAME_LN
				            | PNAME_NS
				            ;
blankNode                   : BLANK_NODE_LABEL ;



// keywords


KW_TRUE         	        : 'true' ;
KW_FALSE        	        : 'false' ;

// terminals

PNAME_LN			        : PNAME_NS PN_LOCAL ;
PNAME_NS			        : PN_PREFIX? ':' ;
IRIREF                      : '<' (~[\u0000-\u0020=<>"{}|^`\\] | UCHAR)* '>' ; /* #x00=NULL #01-#x1F=control codes #x20=space */
BLANK_NODE_LABEL            : '_:' (PN_CHARS_U | [0-9]) ((PN_CHARS | '.')* PN_CHARS)? ;
RDF_TYPE                    : 'a' ;
LANGTAG                     : '@' [a-zA-Z]+ ('-' [a-zA-Z0-9]+)* ;
INTEGER                     : [+-]? [0-9]+ ;
DECIMAL                     : [+-]? [0-9]* '.' [0-9]+ ;
DOUBLE                      : [+-]? ([0-9]+ '.' [0-9]* EXPONENT | '.'? [0-9]+ EXPONENT) ;
EXPONENT                    : [eE] [+-]? [0-9]+ ;
STRING_LITERAL1             : '\'' (~[\u0027\u005C\u000A\u000D] | ECHAR | UCHAR)* '\'' ; /* #x27=' #x5C=\ #xA=new line #xD=carriage return */
STRING_LITERAL2             : '"' (~[\u0022\u005C\u000A\u000D] | ECHAR | UCHAR)* '"' ;   /* #x22=" #x5C=\ #xA=new line #xD=carriage return */
STRING_LITERAL_LONG1        : '\'\'\'' (('\'' | '\'\'')? (~['\\] | ECHAR | UCHAR))* '\'\'\'' ;
STRING_LITERAL_LONG2        : '"""' (('"' | '""')? (~["\\] | ECHAR | UCHAR))* '"""' ;
UCHAR                       : '\\u' HEX HEX HEX HEX | '\\U' HEX HEX HEX HEX HEX HEX HEX HEX ;
ECHAR                       : '\\' [tbnrf\\"'] ;
PN_CHARS_BASE 		        : [A-Z] | [a-z] | [\u00C0-\u00D6] | [\u00D8-\u00F6] | [\u00F8-\u02FF] | [\u0370-\u037D]
					   		| [\u037F-\u1FFF] | [\u200C-\u200D] | [\u2070-\u218F] | [\u2C00-\u2FEF] | [\u3001-\uD7FF]
					        | [\uF900-\uFDCF] | [\uFDF0-\uFFFD] | [\u{10000}-\u{EFFFD}]
					   		;
PN_CHARS_U                  : PN_CHARS_BASE | '_' ;
PN_CHARS                    : PN_CHARS_U | '-' | [0-9] | [\u00B7] | [\u0300-\u036F] | [\u203F-\u2040] ;
PN_PREFIX                   : PN_CHARS_BASE ((PN_CHARS | '.')* PN_CHARS)? ;
PN_LOCAL                    : (PN_CHARS_U | ':' | [0-9] | PLX) ((PN_CHARS | '.' | ':' | PLX)* (PN_CHARS | ':' | PLX))? ;
PLX                         : PERCENT | PN_LOCAL_ESC ;
PERCENT                     : '%' HEX HEX ;
HEX                         : [0-9] | [A-F] | [a-f] ;
PN_LOCAL_ESC                : '\\' ('_' | '~' | '.' | '-' | '!' | '$' | '&' | '\'' | '(' | ')' | '*' | '+' | ','
					  		| ';' | '=' | '/' | '?' | '#' | '@' | '%') ;
PASS				        : ( [ \t\r\n]+ | '#' [^\r\n]* ) -> skip;




