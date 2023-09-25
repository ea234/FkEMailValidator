package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestClassAssertTrueFalse
{
  private static int          TEST_B_TEST_NR                    = 2;

  private static int          LAUFENDE_ZAHL                     = 0;

  private static int          COUNT_ASSERT_IS_TRUE              = 0;

  private static int          COUNT_ASSERT_IS_FALSE             = 0;

  private static int          TRUE_RESULT_COUNT_EMAIL_IS_TRUE   = 0;

  private static int          TRUE_RESULT_COUNT_EMAIL_IS_FALSE  = 0;

  private static int          FALSE_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          FALSE_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          FALSE_RESULT_COUNT_ERROR          = 0;

  private static int          TRUE_RESULT_COUNT_ERROR           = 0;

  private static int          BREITE_SPALTE_EMAIL_AUSGABE       = 76;

  private static boolean      KNZ_FILLUP_AKTIV                  = false;

  private static boolean      KNZ_LOG_AUSGABE                   = true;

  private static boolean      TEST_B_KNZ_AKTIV                  = false;

  private static StringBuffer m_str_buffer                      = null;

  /*
   * <pre>
   * 
   * Unit Tests eMail Validator
   * 
   * IPV6
   * 
   * https://www.regextester.com/104037
   * (([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))
   * 
   * http://sqa.fyicenter.com/1000334_IPv6_Address_Validator.html#Result
   * 
   * https://formvalidation.io/guide/validators/ip/
   * 
   * https://de.wikipedia.org/wiki/Internationalisierter_Domainname
   * 
   * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
   * 
   * https://stackoverflow.com/questions/297420/list-of-email-addresses-that-can-be-used-to-test-a-javascript-validation-script/297494#297494
   *  
   * </pre>
   */

  public static void main( String[] args )
  {
    /*
     * <pre>
     * Testcases for eMail Validation
     * 
     * ---- Correct ---------------------------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  "n@d.td"                                                                     =   0 =  OK 
     *     2 - assertIsTrue  "1@2.td"                                                                     =   0 =  OK 
     *     3 - assertIsTrue  "12.345@678.90.tld"                                                          =   0 =  OK 
     *     4 - assertIsTrue  "name1.name2@domain1.tld"                                                    =   0 =  OK 
     *     5 - assertIsTrue  "name1+name2@domain1.tld"                                                    =   0 =  OK 
     *     6 - assertIsTrue  "name1-name2@domain1.tld"                                                    =   0 =  OK 
     *     7 - assertIsTrue  "name1.name2@subdomain1.domain1.tld"                                         =   0 =  OK 
     *     8 - assertIsTrue  "name1.name2@subdomain1.tu-domain1.tld"                                      =   0 =  OK 
     *     9 - assertIsTrue  "name1.name2@subdomain1.tu_domain1.tld"                                      =   0 =  OK 
     *    10 - assertIsTrue  "escaped.at\@.sign@domain.tld"                                               =   0 =  OK 
     *    11 - assertIsTrue  "\"at.sign.@\".in.string@domain.tld"                                         =   1 =  OK 
     *    12 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                       =   2 =  OK 
     *    13 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *    14 - assertIsTrue  "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]"                                 =   4 =  OK 
     *    15 - assertIsTrue  "ip4.without.brackets@1.2.3.4"                                               =   2 =  OK 
     *    16 - assertIsTrue  "\"string1\".name1@domain1.tld"                                              =   1 =  OK 
     *    17 - assertIsTrue  "name1.\"string1\"@domain1.tld"                                              =   1 =  OK 
     *    18 - assertIsTrue  "name1.\"string1\".name2@domain1.tld"                                        =   1 =  OK 
     *    19 - assertIsTrue  "name1.\"string1\".name2@subdomain1.domain1.tld"                             =   1 =  OK 
     *    20 - assertIsTrue  "\"string1\".\"quote2\".name1@domain1.tld"                                   =   1 =  OK 
     *    21 - assertIsTrue  "\"string1\"@domain1.tld"                                                    =   1 =  OK 
     *    22 - assertIsTrue  "\"string1\\"embedded string\\"\"@domain1.tld"                               =   1 =  OK 
     *    23 - assertIsTrue  "\"string1(embedded comment)\"@domain1.tld"                                  =   1 =  OK 
     *    24 - assertIsTrue  "(comment1)name1@domain1.tld"                                                =   6 =  OK 
     *    25 - assertIsTrue  "(comment1)-name1@domain1.tld"                                               =   6 =  OK 
     *    26 - assertIsTrue  "name1(comment1)@domain1.tld"                                                =   6 =  OK 
     *    27 - assertIsTrue  "name1@(comment1)domain1.tld"                                                =   6 =  OK 
     *    28 - assertIsTrue  "name1@domain1.tld(comment1)"                                                =   6 =  OK 
     *    29 - assertIsTrue  "(spaces after comment)     name1.name2@domain1.tld"                         =   6 =  OK 
     *    30 - assertIsTrue  "name1.name2@domain1.tld   (spaces before comment)"                          =   6 =  OK 
     *    31 - assertIsTrue  "(comment1.\\"comment2)name1@domain1.tld"                                    =   6 =  OK 
     *    32 - assertIsTrue  "(comment1.\\"String\\")name1@domain1.tld"                                   =   6 =  OK 
     *    33 - assertIsTrue  "(comment1.\\"String\\".@domain.tld)name1@domain1.tld"                       =   6 =  OK 
     *    34 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                       =   2 =  OK 
     *    35 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                       =   2 =  OK 
     *    36 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                       =   2 =  OK 
     *    37 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                       =   2 =  OK 
     *    38 - assertIsTrue  "(comment1)\"string1\".name1@domain1.tld"                                    =   7 =  OK 
     *    39 - assertIsTrue  "(comment1)name1.\"string1\"@domain1.tld"                                    =   7 =  OK 
     *    40 - assertIsTrue  "name1.\"string1\"(comment1)@domain1.tld"                                    =   7 =  OK 
     *    41 - assertIsTrue  "\"string1\".name1(comment1)@domain1.tld"                                    =   7 =  OK 
     *    42 - assertIsTrue  "name1.\"string1\"@(comment1)domain1.tld"                                    =   7 =  OK 
     *    43 - assertIsTrue  "\"string1\".name1@domain1.tld(comment1)"                                    =   7 =  OK 
     *    44 - assertIsTrue  "<name1.name2@domain1.tld>"                                                  =   0 =  OK 
     *    45 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                            =   0 =  OK 
     *    46 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                            =   0 =  OK 
     *    47 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                                  =   0 =  OK 
     *    48 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                               =   2 =  OK 
     *    49 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                                  =   4 =  OK 
     *    50 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                               =   2 =  OK 
     *    51 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                                 =   4 =  OK 
     *    52 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"        =   6 =  OK 
     *    53 - assertIsTrue  "\"display name\" <local.part@(comment)domain-name.top_level_domain>"        =   6 =  OK 
     *    54 - assertIsTrue  "\"display name\" <(comment) local.part.\"string1\"@domain-name.top_level_domain>" =   7 =  OK 
     *    55 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part@domain-name.top_level_domain>" =   6 =  OK 
     *    56 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part.wiht.escaped.at\@.sign@domain-name.top_level_domain>" =   6 =  OK 
     * 
     * ---- No Input --------------------------------------------------------------------------------------------------------------------
     * 
     *    57 - assertIsFalse null                                                                         =  10 =  OK    Laenge: Eingabe ist null
     *    58 - assertIsFalse ""                                                                           =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    59 - assertIsFalse "        "                                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Sign ---------------------------------------------------------------------------------------------------------------------
     * 
     *    60 - assertIsFalse "1234567890"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    61 - assertIsFalse "OnlyTextNoDotNoAt"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    62 - assertIsFalse "email.with.no.at.sign"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    63 - assertIsFalse "email.with.no.domain@"                                                      =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    64 - assertIsFalse "@@domain.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    65 - assertIsFalse "email.with.no.domain\@domain.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    66 - assertIsFalse "email.with.no.domain\@.domain.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    67 - assertIsFalse "email.with.no.domain\@123domain.com"                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    68 - assertIsFalse "email.with.no.domain\@_domain.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    69 - assertIsFalse "email.with.no.domain\@-domain.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    70 - assertIsFalse "email.with.double\@no.domain\@domain.com"                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    71 - assertIsTrue  "\"wrong.at.sign.combination.in.string1@.\"@domain.com"                      =   1 =  OK 
     *    72 - assertIsTrue  "\"wrong.at.sign.combination.in.string2.@\"@domain.com"                      =   1 =  OK 
     *    73 - assertIsTrue  "email.with.escaped.at\@.sign.version1@domain.com"                           =   0 =  OK 
     *    74 - assertIsTrue  "email.with.escaped.\@.sign.version2@domain.com"                             =   0 =  OK 
     *    75 - assertIsTrue  "email.with.escaped.at\@123.sign.version3@domain.com"                        =   0 =  OK 
     *    76 - assertIsTrue  "email.with.escaped.\@123.sign.version4@domain.com"                          =   0 =  OK 
     *    77 - assertIsTrue  "email.with.escaped.at\@-.sign.version5@domain.com"                          =   0 =  OK 
     *    78 - assertIsTrue  "email.with.escaped.\@-.sign.version6@domain.com"                            =   0 =  OK 
     *    79 - assertIsTrue  "email.with.escaped.at.sign.\@@domain.com"                                   =   0 =  OK 
     *    80 - assertIsFalse "@@email.with.unescaped.at.sign.as.local.part"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    81 - assertIsTrue  "\@@email.with.escaped.at.sign.as.local.part"                                =   0 =  OK 
     *    82 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    83 - assertIsFalse "@no.local.part.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    84 - assertIsFalse "@@@@@@only.multiple.at.signs.in.local.part.com"                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    85 - assertIsFalse "local.part.with.two.@at.signs@domain.com"                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    86 - assertIsFalse "local.part.ends.with.at.sign@@domain.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    87 - assertIsFalse "local.part.with.at.sign.before@.point@domain.com"                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    88 - assertIsFalse "local.part.with.at.sign.after.@point@domain.com"                            =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    89 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    90 - assertIsTrue  "(comment @) local.part.with.at.sign.in.comment@domain.com"                  =   6 =  OK 
     *    91 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"                =   6 =  OK 
     *    92 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *    93 - assertIsTrue  "\"String@\".local.part.with.at.sign.in.string@domain.com"                   =   1 =  OK 
     *    94 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                               =   0 =  OK 
     *    95 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    96 - assertIsFalse "@.@.@.@.@.@@domain.com"                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    97 - assertIsFalse "@.@.@."                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    98 - assertIsFalse "\@.\@@\@.\@"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    99 - assertIsFalse "@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   100 - assertIsFalse "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   101 - assertIsFalse "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Seperator -------------------------------------------------------------------------------------------------------------------
     * 
     *   102 - assertIsFalse "EmailAdressWith@NoDots"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   103 - assertIsFalse "..local.part.starts.with.dot@domain.com"                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   104 - assertIsFalse "local.part.ends.with.dot.@domain.com"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   105 - assertIsTrue  "local.part.with.dot.character@domain.com"                                   =   0 =  OK 
     *   106 - assertIsFalse "local.part.with.dot.before..point@domain.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   107 - assertIsFalse "local.part.with.dot.after..point@domain.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   108 - assertIsFalse "local.part.with.double.dot..test@domain.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   109 - assertIsTrue  "(comment .) local.part.with.dot.in.comment@domain.com"                      =   6 =  OK 
     *   110 - assertIsTrue  "\"string.\".local.part.with.dot.in.String@domain.com"                       =   1 =  OK 
     *   111 - assertIsFalse "\"string\.\".local.part.with.escaped.dot.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   112 - assertIsFalse ".@local.part.only.dot.domain.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   113 - assertIsFalse "......@local.part.only.consecutive.dot.domain.com"                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   114 - assertIsFalse "...........@dot.domain.com"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   115 - assertIsFalse "name . <pointy.brackets1.with.dot.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   116 - assertIsFalse "<pointy.brackets2.with.dot.in.display.name@domain.com> name ."              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   117 - assertIsTrue  "domain.part@with.dot.com"                                                   =   0 =  OK 
     *   118 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   119 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   120 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   121 - assertIsFalse "domain.part@with.dot.before..point.com"                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   122 - assertIsFalse "domain.part@with.dot.after..point.com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   123 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   124 - assertIsTrue  "domain.part.with.dot.in.comment@(comment .)domain.com"                      =   6 =  OK 
     *   125 - assertIsFalse "domain.part.only.dot@..com"                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   126 - assertIsFalse "top.level.domain.only@dot.."                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   127 - assertIsFalse "...local.part.starts.with.double.dot@domain.com"                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   128 - assertIsFalse "local.part.ends.with.double.dot..@domain.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   129 - assertIsFalse "local.part.with.double.dot..character@domain.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   130 - assertIsFalse "local.part.with.double.dot.before...point@domain.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   131 - assertIsFalse "local.part.with.double.dot.after...point@domain.com"                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   132 - assertIsFalse "local.part.with.double.double.dot....test@domain.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   133 - assertIsTrue  "(comment ..) local.part.with.double.dot.in.comment@domain.com"              =   6 =  OK 
     *   134 - assertIsTrue  "\"string..\".local.part.with.double.dot.in.String@domain.com"               =   1 =  OK 
     *   135 - assertIsFalse "\"string\..\".local.part.with.escaped.double.dot.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   136 - assertIsFalse "..@local.part.only.double.dot.domain.com"                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   137 - assertIsFalse "............@local.part.only.consecutive.double.dot.domain.com"             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   138 - assertIsFalse ".................@double.dot.domain.com"                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   139 - assertIsFalse "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   140 - assertIsFalse "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .."      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   141 - assertIsFalse "domain.part@with..double.dot.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   142 - assertIsFalse "domain.part@..with.double.dot.at.domain.start.com"                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   143 - assertIsFalse "domain.part@with.double.dot.at.domain.end1...com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   144 - assertIsFalse "domain.part@with.double.dot.at.domain.end2.com.."                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   145 - assertIsFalse "domain.part@with.double.dot.before...point.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   146 - assertIsFalse "domain.part@with.double.dot.after...point.com"                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   147 - assertIsFalse "domain.part@with.consecutive.double.dot....test.com"                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   148 - assertIsTrue  "domain.part.with.comment.with.double.dot@(comment ..)domain.com"            =   6 =  OK 
     *   149 - assertIsFalse "domain.part.only.double.dot@...com"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   150 - assertIsFalse "top.level.domain.only@double.dot..."                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ------------------------------------------------------------------------------------------------------------------
     * 
     *   151 - assertIsTrue  "&local&&part&with&$@amp.com"                                                =   0 =  OK 
     *   152 - assertIsTrue  "*local**part*with*@asterisk.com"                                            =   0 =  OK 
     *   153 - assertIsTrue  "$local$$part$with$@dollar.com"                                              =   0 =  OK 
     *   154 - assertIsTrue  "=local==part=with=@equality.com"                                            =   0 =  OK 
     *   155 - assertIsTrue  "!local!!part!with!@exclamation.com"                                         =   0 =  OK 
     *   156 - assertIsTrue  "`local``part`with`@grave-accent.com"                                        =   0 =  OK 
     *   157 - assertIsTrue  "#local##part#with#@hash.com"                                                =   0 =  OK 
     *   158 - assertIsTrue  "-local--part-with-@hypen.com"                                               =   0 =  OK 
     *   159 - assertIsTrue  "{local{part{{with{@leftbracket.com"                                         =   0 =  OK 
     *   160 - assertIsTrue  "%local%%part%with%@percentage.com"                                          =   0 =  OK 
     *   161 - assertIsTrue  "|local||part|with|@pipe.com"                                                =   0 =  OK 
     *   162 - assertIsTrue  "+local++part+with+@plus.com"                                                =   0 =  OK 
     *   163 - assertIsTrue  "?local??part?with?@question.com"                                            =   0 =  OK 
     *   164 - assertIsTrue  "}local}part}}with}@rightbracket.com"                                        =   0 =  OK 
     *   165 - assertIsTrue  "~local~~part~with~@tilde.com"                                               =   0 =  OK 
     *   166 - assertIsTrue  "^local^^part^with^@xor.com"                                                 =   0 =  OK 
     *   167 - assertIsTrue  "_local__part_with_@underscore.com"                                          =   0 =  OK 
     *   168 - assertIsFalse ":local::part:with:@colon.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   169 - assertIsTrue  "&.local.part.starts.with.amp@domain.com"                                    =   0 =  OK 
     *   170 - assertIsTrue  "local.part.ends.with.amp&@domain.com"                                       =   0 =  OK 
     *   171 - assertIsTrue  "local.part.with.amp&character@domain.com"                                   =   0 =  OK 
     *   172 - assertIsTrue  "local.part.with.amp.before&.point@domain.com"                               =   0 =  OK 
     *   173 - assertIsTrue  "local.part.with.amp.after.&point@domain.com"                                =   0 =  OK 
     *   174 - assertIsTrue  "local.part.with.double.amp&&test@domain.com"                                =   0 =  OK 
     *   175 - assertIsTrue  "(comment &) local.part.with.amp.in.comment@domain.com"                      =   6 =  OK 
     *   176 - assertIsTrue  "\"string&\".local.part.with.amp.in.String@domain.com"                       =   1 =  OK 
     *   177 - assertIsFalse "\"string\&\".local.part.with.escaped.amp.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   178 - assertIsTrue  "&@local.part.only.amp.domain.com"                                           =   0 =  OK 
     *   179 - assertIsTrue  "&&&&&&@local.part.only.consecutive.amp.domain.com"                          =   0 =  OK 
     *   180 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                                 =   0 =  OK 
     *   181 - assertIsFalse "name & <pointy.brackets1.with.amp.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   182 - assertIsFalse "<pointy.brackets2.with.amp.in.display.name@domain.com> name &"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   183 - assertIsFalse "domain.part@with&amp.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   184 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   185 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   186 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   187 - assertIsFalse "domain.part@with.amp.before&.point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   188 - assertIsFalse "domain.part@with.amp.after.&point.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   189 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   190 - assertIsTrue  "domain.part.with.amp.in.comment@(comment &)domain.com"                      =   6 =  OK 
     *   191 - assertIsFalse "domain.part.only.amp@&.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   192 - assertIsFalse "top.level.domain.only@amp.&"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   193 - assertIsTrue  "*.local.part.starts.with.asterisk@domain.com"                               =   0 =  OK 
     *   194 - assertIsTrue  "local.part.ends.with.asterisk*@domain.com"                                  =   0 =  OK 
     *   195 - assertIsTrue  "local.part.with.asterisk*character@domain.com"                              =   0 =  OK 
     *   196 - assertIsTrue  "local.part.with.asterisk.before*.point@domain.com"                          =   0 =  OK 
     *   197 - assertIsTrue  "local.part.with.asterisk.after.*point@domain.com"                           =   0 =  OK 
     *   198 - assertIsTrue  "local.part.with.double.asterisk**test@domain.com"                           =   0 =  OK 
     *   199 - assertIsTrue  "(comment *) local.part.with.asterisk.in.comment@domain.com"                 =   6 =  OK 
     *   200 - assertIsTrue  "\"string*\".local.part.with.asterisk.in.String@domain.com"                  =   1 =  OK 
     *   201 - assertIsFalse "\"string\*\".local.part.with.escaped.asterisk.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   202 - assertIsTrue  "*@local.part.only.asterisk.domain.com"                                      =   0 =  OK 
     *   203 - assertIsTrue  "******@local.part.only.consecutive.asterisk.domain.com"                     =   0 =  OK 
     *   204 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                            =   0 =  OK 
     *   205 - assertIsFalse "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   206 - assertIsFalse "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   207 - assertIsFalse "domain.part@with*asterisk.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   208 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   209 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   210 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   211 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   212 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   213 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   214 - assertIsTrue  "domain.part.with.asterisk.in.comment@(comment *)domain.com"                 =   6 =  OK 
     *   215 - assertIsFalse "domain.part.only.asterisk@*.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   216 - assertIsFalse "top.level.domain.only@asterisk.*"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   217 - assertIsTrue  "_.local.part.starts.with.underscore@domain.com"                             =   0 =  OK 
     *   218 - assertIsTrue  "local.part.ends.with.underscore_@domain.com"                                =   0 =  OK 
     *   219 - assertIsTrue  "local.part.with.underscore_character@domain.com"                            =   0 =  OK 
     *   220 - assertIsTrue  "local.part.with.underscore.before_.point@domain.com"                        =   0 =  OK 
     *   221 - assertIsTrue  "local.part.with.underscore.after._point@domain.com"                         =   0 =  OK 
     *   222 - assertIsTrue  "local.part.with.double.underscore__test@domain.com"                         =   0 =  OK 
     *   223 - assertIsTrue  "(comment _) local.part.with.underscore.in.comment@domain.com"               =   6 =  OK 
     *   224 - assertIsTrue  "\"string_\".local.part.with.underscore.in.String@domain.com"                =   1 =  OK 
     *   225 - assertIsFalse "\"string\_\".local.part.with.escaped.underscore.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   226 - assertIsTrue  "_@local.part.only.underscore.domain.com"                                    =   0 =  OK 
     *   227 - assertIsTrue  "______@local.part.only.consecutive.underscore.domain.com"                   =   0 =  OK 
     *   228 - assertIsTrue  "_._._._._._@underscore.domain.com"                                          =   0 =  OK 
     *   229 - assertIsFalse "name _ <pointy.brackets1.with.underscore.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   230 - assertIsFalse "<pointy.brackets2.with.underscore.in.display.name@domain.com> name _"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   231 - assertIsTrue  "domain.part@with_underscore.com"                                            =   0 =  OK 
     *   232 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   233 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   234 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   235 - assertIsFalse "domain.part@with.underscore.before_.point.com"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   236 - assertIsFalse "domain.part@with.underscore.after._point.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   237 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                          =   0 =  OK 
     *   238 - assertIsTrue  "domain.part.with.underscore.in.comment@(comment _)domain.com"               =   6 =  OK 
     *   239 - assertIsFalse "domain.part.only.underscore@_.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   240 - assertIsFalse "top.level.domain.only@underscore._"                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   241 - assertIsTrue  "$.local.part.starts.with.dollar@domain.com"                                 =   0 =  OK 
     *   242 - assertIsTrue  "local.part.ends.with.dollar$@domain.com"                                    =   0 =  OK 
     *   243 - assertIsTrue  "local.part.with.dollar$character@domain.com"                                =   0 =  OK 
     *   244 - assertIsTrue  "local.part.with.dollar.before$.point@domain.com"                            =   0 =  OK 
     *   245 - assertIsTrue  "local.part.with.dollar.after.$point@domain.com"                             =   0 =  OK 
     *   246 - assertIsTrue  "local.part.with.double.dollar$$test@domain.com"                             =   0 =  OK 
     *   247 - assertIsTrue  "(comment $) local.part.with.dollar.in.comment@domain.com"                   =   6 =  OK 
     *   248 - assertIsTrue  "\"string$\".local.part.with.dollar.in.String@domain.com"                    =   1 =  OK 
     *   249 - assertIsFalse "\"string\$\".local.part.with.escaped.dollar.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   250 - assertIsTrue  "$@local.part.only.dollar.domain.com"                                        =   0 =  OK 
     *   251 - assertIsTrue  "$$$$$$@local.part.only.consecutive.dollar.domain.com"                       =   0 =  OK 
     *   252 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                              =   0 =  OK 
     *   253 - assertIsFalse "name $ <pointy.brackets1.with.dollar.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   254 - assertIsFalse "<pointy.brackets2.with.dollar.in.display.name@domain.com> name $"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   255 - assertIsFalse "domain.part@with$dollar.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   256 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   257 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   258 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   259 - assertIsFalse "domain.part@with.dollar.before$.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   260 - assertIsFalse "domain.part@with.dollar.after.$point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   261 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   262 - assertIsTrue  "domain.part.with.dollar.in.comment@(comment $)domain.com"                   =   6 =  OK 
     *   263 - assertIsFalse "domain.part.only.dollar@$.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   264 - assertIsFalse "top.level.domain.only@dollar.$"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   265 - assertIsTrue  "=.local.part.starts.with.equality@domain.com"                               =   0 =  OK 
     *   266 - assertIsTrue  "local.part.ends.with.equality=@domain.com"                                  =   0 =  OK 
     *   267 - assertIsTrue  "local.part.with.equality=character@domain.com"                              =   0 =  OK 
     *   268 - assertIsTrue  "local.part.with.equality.before=.point@domain.com"                          =   0 =  OK 
     *   269 - assertIsTrue  "local.part.with.equality.after.=point@domain.com"                           =   0 =  OK 
     *   270 - assertIsTrue  "local.part.with.double.equality==test@domain.com"                           =   0 =  OK 
     *   271 - assertIsTrue  "(comment =) local.part.with.equality.in.comment@domain.com"                 =   6 =  OK 
     *   272 - assertIsTrue  "\"string=\".local.part.with.equality.in.String@domain.com"                  =   1 =  OK 
     *   273 - assertIsFalse "\"string\=\".local.part.with.escaped.equality.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   274 - assertIsTrue  "=@local.part.only.equality.domain.com"                                      =   0 =  OK 
     *   275 - assertIsTrue  "======@local.part.only.consecutive.equality.domain.com"                     =   0 =  OK 
     *   276 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                            =   0 =  OK 
     *   277 - assertIsFalse "name = <pointy.brackets1.with.equality.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   278 - assertIsFalse "<pointy.brackets2.with.equality.in.display.name@domain.com> name ="         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   279 - assertIsFalse "domain.part@with=equality.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   280 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   281 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   282 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   283 - assertIsFalse "domain.part@with.equality.before=.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   284 - assertIsFalse "domain.part@with.equality.after.=point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   285 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   286 - assertIsTrue  "domain.part.with.equality.in.comment@(comment =)domain.com"                 =   6 =  OK 
     *   287 - assertIsFalse "domain.part.only.equality@=.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   288 - assertIsFalse "top.level.domain.only@equality.="                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   289 - assertIsTrue  "!.local.part.starts.with.exclamation@domain.com"                            =   0 =  OK 
     *   290 - assertIsTrue  "local.part.ends.with.exclamation!@domain.com"                               =   0 =  OK 
     *   291 - assertIsTrue  "local.part.with.exclamation!character@domain.com"                           =   0 =  OK 
     *   292 - assertIsTrue  "local.part.with.exclamation.before!.point@domain.com"                       =   0 =  OK 
     *   293 - assertIsTrue  "local.part.with.exclamation.after.!point@domain.com"                        =   0 =  OK 
     *   294 - assertIsTrue  "local.part.with.double.exclamation!!test@domain.com"                        =   0 =  OK 
     *   295 - assertIsTrue  "(comment !) local.part.with.exclamation.in.comment@domain.com"              =   6 =  OK 
     *   296 - assertIsTrue  "\"string!\".local.part.with.exclamation.in.String@domain.com"               =   1 =  OK 
     *   297 - assertIsFalse "\"string\!\".local.part.with.escaped.exclamation.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   298 - assertIsTrue  "!@local.part.only.exclamation.domain.com"                                   =   0 =  OK 
     *   299 - assertIsTrue  "!!!!!!@local.part.only.consecutive.exclamation.domain.com"                  =   0 =  OK 
     *   300 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                         =   0 =  OK 
     *   301 - assertIsFalse "name ! <pointy.brackets1.with.exclamation.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   302 - assertIsFalse "<pointy.brackets2.with.exclamation.in.display.name@domain.com> name !"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   303 - assertIsFalse "domain.part@with!exclamation.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   304 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   305 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   306 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   307 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   308 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   309 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   310 - assertIsTrue  "domain.part.with.exclamation.in.comment@(comment !)domain.com"              =   6 =  OK 
     *   311 - assertIsFalse "domain.part.only.exclamation@!.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   312 - assertIsFalse "top.level.domain.only@exclamation.!"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   313 - assertIsTrue  "?.local.part.starts.with.question@domain.com"                               =   0 =  OK 
     *   314 - assertIsTrue  "local.part.ends.with.question?@domain.com"                                  =   0 =  OK 
     *   315 - assertIsTrue  "local.part.with.question?character@domain.com"                              =   0 =  OK 
     *   316 - assertIsTrue  "local.part.with.question.before?.point@domain.com"                          =   0 =  OK 
     *   317 - assertIsTrue  "local.part.with.question.after.?point@domain.com"                           =   0 =  OK 
     *   318 - assertIsTrue  "local.part.with.double.question??test@domain.com"                           =   0 =  OK 
     *   319 - assertIsTrue  "(comment ?) local.part.with.question.in.comment@domain.com"                 =   6 =  OK 
     *   320 - assertIsTrue  "\"string?\".local.part.with.question.in.String@domain.com"                  =   1 =  OK 
     *   321 - assertIsFalse "\"string\?\".local.part.with.escaped.question.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   322 - assertIsTrue  "?@local.part.only.question.domain.com"                                      =   0 =  OK 
     *   323 - assertIsTrue  "??????@local.part.only.consecutive.question.domain.com"                     =   0 =  OK 
     *   324 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                            =   0 =  OK 
     *   325 - assertIsFalse "name ? <pointy.brackets1.with.question.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   326 - assertIsFalse "<pointy.brackets2.with.question.in.display.name@domain.com> name ?"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   327 - assertIsFalse "domain.part@with?question.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   328 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   329 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   330 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   331 - assertIsFalse "domain.part@with.question.before?.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   332 - assertIsFalse "domain.part@with.question.after.?point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   333 - assertIsFalse "domain.part@with.consecutive.question??test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   334 - assertIsTrue  "domain.part.with.question.in.comment@(comment ?)domain.com"                 =   6 =  OK 
     *   335 - assertIsFalse "domain.part.only.question@?.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   336 - assertIsFalse "top.level.domain.only@question.?"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   337 - assertIsTrue  "`.local.part.starts.with.grave-accent@domain.com"                           =   0 =  OK 
     *   338 - assertIsTrue  "local.part.ends.with.grave-accent`@domain.com"                              =   0 =  OK 
     *   339 - assertIsTrue  "local.part.with.grave-accent`character@domain.com"                          =   0 =  OK 
     *   340 - assertIsTrue  "local.part.with.grave-accent.before`.point@domain.com"                      =   0 =  OK 
     *   341 - assertIsTrue  "local.part.with.grave-accent.after.`point@domain.com"                       =   0 =  OK 
     *   342 - assertIsTrue  "local.part.with.double.grave-accent``test@domain.com"                       =   0 =  OK 
     *   343 - assertIsTrue  "(comment `) local.part.with.grave-accent.in.comment@domain.com"             =   6 =  OK 
     *   344 - assertIsTrue  "\"string`\".local.part.with.grave-accent.in.String@domain.com"              =   1 =  OK 
     *   345 - assertIsFalse "\"string\`\".local.part.with.escaped.grave-accent.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   346 - assertIsTrue  "`@local.part.only.grave-accent.domain.com"                                  =   0 =  OK 
     *   347 - assertIsTrue  "``````@local.part.only.consecutive.grave-accent.domain.com"                 =   0 =  OK 
     *   348 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                        =   0 =  OK 
     *   349 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   350 - assertIsFalse "<pointy.brackets2.with.grave-accent.in.display.name@domain.com> name `"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   351 - assertIsFalse "domain.part@with`grave-accent.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   352 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   353 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   354 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   355 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   356 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   357 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   358 - assertIsTrue  "domain.part.with.grave-accent.in.comment@(comment `)domain.com"             =   6 =  OK 
     *   359 - assertIsFalse "domain.part.only.grave-accent@`.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   360 - assertIsFalse "top.level.domain.only@grave-accent.`"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   361 - assertIsTrue  "#.local.part.starts.with.hash@domain.com"                                   =   0 =  OK 
     *   362 - assertIsTrue  "local.part.ends.with.hash#@domain.com"                                      =   0 =  OK 
     *   363 - assertIsTrue  "local.part.with.hash#character@domain.com"                                  =   0 =  OK 
     *   364 - assertIsTrue  "local.part.with.hash.before#.point@domain.com"                              =   0 =  OK 
     *   365 - assertIsTrue  "local.part.with.hash.after.#point@domain.com"                               =   0 =  OK 
     *   366 - assertIsTrue  "local.part.with.double.hash##test@domain.com"                               =   0 =  OK 
     *   367 - assertIsTrue  "(comment #) local.part.with.hash.in.comment@domain.com"                     =   6 =  OK 
     *   368 - assertIsTrue  "\"string#\".local.part.with.hash.in.String@domain.com"                      =   1 =  OK 
     *   369 - assertIsFalse "\"string\#\".local.part.with.escaped.hash.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   370 - assertIsTrue  "#@local.part.only.hash.domain.com"                                          =   0 =  OK 
     *   371 - assertIsTrue  "######@local.part.only.consecutive.hash.domain.com"                         =   0 =  OK 
     *   372 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                                =   0 =  OK 
     *   373 - assertIsFalse "name # <pointy.brackets1.with.hash.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   374 - assertIsFalse "<pointy.brackets2.with.hash.in.display.name@domain.com> name #"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   375 - assertIsFalse "domain.part@with#hash.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   376 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   377 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   378 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   379 - assertIsFalse "domain.part@with.hash.before#.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   380 - assertIsFalse "domain.part@with.hash.after.#point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   381 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   382 - assertIsTrue  "domain.part.with.hash.in.comment@(comment #)domain.com"                     =   6 =  OK 
     *   383 - assertIsFalse "domain.part.only.hash@#.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   384 - assertIsFalse "top.level.domain.only@hash.#"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   385 - assertIsTrue  "%.local.part.starts.with.percentage@domain.com"                             =   0 =  OK 
     *   386 - assertIsTrue  "local.part.ends.with.percentage%@domain.com"                                =   0 =  OK 
     *   387 - assertIsTrue  "local.part.with.percentage%character@domain.com"                            =   0 =  OK 
     *   388 - assertIsTrue  "local.part.with.percentage.before%.point@domain.com"                        =   0 =  OK 
     *   389 - assertIsTrue  "local.part.with.percentage.after.%point@domain.com"                         =   0 =  OK 
     *   390 - assertIsTrue  "local.part.with.double.percentage%%test@domain.com"                         =   0 =  OK 
     *   391 - assertIsTrue  "(comment %) local.part.with.percentage.in.comment@domain.com"               =   6 =  OK 
     *   392 - assertIsTrue  "\"string%\".local.part.with.percentage.in.String@domain.com"                =   1 =  OK 
     *   393 - assertIsFalse "\"string\%\".local.part.with.escaped.percentage.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   394 - assertIsTrue  "%@local.part.only.percentage.domain.com"                                    =   0 =  OK 
     *   395 - assertIsTrue  "%%%%%%@local.part.only.consecutive.percentage.domain.com"                   =   0 =  OK 
     *   396 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                          =   0 =  OK 
     *   397 - assertIsFalse "name % <pointy.brackets1.with.percentage.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   398 - assertIsFalse "<pointy.brackets2.with.percentage.in.display.name@domain.com> name %"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   399 - assertIsFalse "domain.part@with%percentage.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   400 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   401 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   402 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   403 - assertIsFalse "domain.part@with.percentage.before%.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   404 - assertIsFalse "domain.part@with.percentage.after.%point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   405 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   406 - assertIsTrue  "domain.part.with.percentage.in.comment@(comment %)domain.com"               =   6 =  OK 
     *   407 - assertIsFalse "domain.part.only.percentage@%.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   408 - assertIsFalse "top.level.domain.only@percentage.%"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   409 - assertIsTrue  "|.local.part.starts.with.pipe@domain.com"                                   =   0 =  OK 
     *   410 - assertIsTrue  "local.part.ends.with.pipe|@domain.com"                                      =   0 =  OK 
     *   411 - assertIsTrue  "local.part.with.pipe|character@domain.com"                                  =   0 =  OK 
     *   412 - assertIsTrue  "local.part.with.pipe.before|.point@domain.com"                              =   0 =  OK 
     *   413 - assertIsTrue  "local.part.with.pipe.after.|point@domain.com"                               =   0 =  OK 
     *   414 - assertIsTrue  "local.part.with.double.pipe||test@domain.com"                               =   0 =  OK 
     *   415 - assertIsTrue  "(comment |) local.part.with.pipe.in.comment@domain.com"                     =   6 =  OK 
     *   416 - assertIsTrue  "\"string|\".local.part.with.pipe.in.String@domain.com"                      =   1 =  OK 
     *   417 - assertIsFalse "\"string\|\".local.part.with.escaped.pipe.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   418 - assertIsTrue  "|@local.part.only.pipe.domain.com"                                          =   0 =  OK 
     *   419 - assertIsTrue  "||||||@local.part.only.consecutive.pipe.domain.com"                         =   0 =  OK 
     *   420 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                                =   0 =  OK 
     *   421 - assertIsFalse "name | <pointy.brackets1.with.pipe.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   422 - assertIsFalse "<pointy.brackets2.with.pipe.in.display.name@domain.com> name |"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   423 - assertIsFalse "domain.part@with|pipe.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   424 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   425 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   426 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   427 - assertIsFalse "domain.part@with.pipe.before|.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   428 - assertIsFalse "domain.part@with.pipe.after.|point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   429 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   430 - assertIsTrue  "domain.part.with.pipe.in.comment@(comment |)domain.com"                     =   6 =  OK 
     *   431 - assertIsFalse "domain.part.only.pipe@|.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   432 - assertIsFalse "top.level.domain.only@pipe.|"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   433 - assertIsTrue  "+.local.part.starts.with.plus@domain.com"                                   =   0 =  OK 
     *   434 - assertIsTrue  "local.part.ends.with.plus+@domain.com"                                      =   0 =  OK 
     *   435 - assertIsTrue  "local.part.with.plus+character@domain.com"                                  =   0 =  OK 
     *   436 - assertIsTrue  "local.part.with.plus.before+.point@domain.com"                              =   0 =  OK 
     *   437 - assertIsTrue  "local.part.with.plus.after.+point@domain.com"                               =   0 =  OK 
     *   438 - assertIsTrue  "local.part.with.double.plus++test@domain.com"                               =   0 =  OK 
     *   439 - assertIsTrue  "(comment +) local.part.with.plus.in.comment@domain.com"                     =   6 =  OK 
     *   440 - assertIsTrue  "\"string+\".local.part.with.plus.in.String@domain.com"                      =   1 =  OK 
     *   441 - assertIsFalse "\"string\+\".local.part.with.escaped.plus.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   442 - assertIsTrue  "+@local.part.only.plus.domain.com"                                          =   0 =  OK 
     *   443 - assertIsTrue  "++++++@local.part.only.consecutive.plus.domain.com"                         =   0 =  OK 
     *   444 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                                =   0 =  OK 
     *   445 - assertIsFalse "name + <pointy.brackets1.with.plus.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   446 - assertIsFalse "<pointy.brackets2.with.plus.in.display.name@domain.com> name +"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   447 - assertIsFalse "domain.part@with+plus.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   448 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   449 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   450 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   451 - assertIsFalse "domain.part@with.plus.before+.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   452 - assertIsFalse "domain.part@with.plus.after.+point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   453 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   454 - assertIsTrue  "domain.part.with.plus.in.comment@(comment +)domain.com"                     =   6 =  OK 
     *   455 - assertIsFalse "domain.part.only.plus@+.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   456 - assertIsFalse "top.level.domain.only@plus.+"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   457 - assertIsTrue  "{.local.part.starts.with.leftbracket@domain.com"                            =   0 =  OK 
     *   458 - assertIsTrue  "local.part.ends.with.leftbracket{@domain.com"                               =   0 =  OK 
     *   459 - assertIsTrue  "local.part.with.leftbracket{character@domain.com"                           =   0 =  OK 
     *   460 - assertIsTrue  "local.part.with.leftbracket.before{.point@domain.com"                       =   0 =  OK 
     *   461 - assertIsTrue  "local.part.with.leftbracket.after.{point@domain.com"                        =   0 =  OK 
     *   462 - assertIsTrue  "local.part.with.double.leftbracket{{test@domain.com"                        =   0 =  OK 
     *   463 - assertIsTrue  "(comment {) local.part.with.leftbracket.in.comment@domain.com"              =   6 =  OK 
     *   464 - assertIsTrue  "\"string{\".local.part.with.leftbracket.in.String@domain.com"               =   1 =  OK 
     *   465 - assertIsFalse "\"string\{\".local.part.with.escaped.leftbracket.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   466 - assertIsTrue  "{@local.part.only.leftbracket.domain.com"                                   =   0 =  OK 
     *   467 - assertIsTrue  "{{{{{{@local.part.only.consecutive.leftbracket.domain.com"                  =   0 =  OK 
     *   468 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                         =   0 =  OK 
     *   469 - assertIsFalse "name { <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   470 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name {"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   471 - assertIsFalse "domain.part@with{leftbracket.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   472 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   473 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   474 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   475 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   476 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   477 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   478 - assertIsTrue  "domain.part.with.leftbracket.in.comment@(comment {)domain.com"              =   6 =  OK 
     *   479 - assertIsFalse "domain.part.only.leftbracket@{.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   480 - assertIsFalse "top.level.domain.only@leftbracket.{"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   481 - assertIsTrue  "}.local.part.starts.with.rightbracket@domain.com"                           =   0 =  OK 
     *   482 - assertIsTrue  "local.part.ends.with.rightbracket}@domain.com"                              =   0 =  OK 
     *   483 - assertIsTrue  "local.part.with.rightbracket}character@domain.com"                          =   0 =  OK 
     *   484 - assertIsTrue  "local.part.with.rightbracket.before}.point@domain.com"                      =   0 =  OK 
     *   485 - assertIsTrue  "local.part.with.rightbracket.after.}point@domain.com"                       =   0 =  OK 
     *   486 - assertIsTrue  "local.part.with.double.rightbracket}}test@domain.com"                       =   0 =  OK 
     *   487 - assertIsTrue  "(comment }) local.part.with.rightbracket.in.comment@domain.com"             =   6 =  OK 
     *   488 - assertIsTrue  "\"string}\".local.part.with.rightbracket.in.String@domain.com"              =   1 =  OK 
     *   489 - assertIsFalse "\"string\}\".local.part.with.escaped.rightbracket.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   490 - assertIsTrue  "}@local.part.only.rightbracket.domain.com"                                  =   0 =  OK 
     *   491 - assertIsTrue  "}}}}}}@local.part.only.consecutive.rightbracket.domain.com"                 =   0 =  OK 
     *   492 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                        =   0 =  OK 
     *   493 - assertIsFalse "name } <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   494 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name }"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   495 - assertIsFalse "domain.part@with}rightbracket.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   496 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   497 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   498 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   499 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   500 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   501 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   502 - assertIsTrue  "domain.part.with.rightbracket.in.comment@(comment })domain.com"             =   6 =  OK 
     *   503 - assertIsFalse "domain.part.only.rightbracket@}.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   504 - assertIsFalse "top.level.domain.only@rightbracket.}"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   505 - assertIsFalse "(.local.part.starts.with.leftbracket@domain.com"                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   506 - assertIsFalse "local.part.ends.with.leftbracket(@domain.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   507 - assertIsFalse "local.part.with.leftbracket(character@domain.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   508 - assertIsFalse "local.part.with.leftbracket.before(.point@domain.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   509 - assertIsFalse "local.part.with.leftbracket.after.(point@domain.com"                        = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   510 - assertIsFalse "local.part.with.double.leftbracket((test@domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   511 - assertIsFalse "(comment () local.part.with.leftbracket.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   512 - assertIsTrue  "\"string(\".local.part.with.leftbracket.in.String@domain.com"               =   1 =  OK 
     *   513 - assertIsFalse "\"string\(\".local.part.with.escaped.leftbracket.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   514 - assertIsFalse "(@local.part.only.leftbracket.domain.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   515 - assertIsFalse "((((((@local.part.only.consecutive.leftbracket.domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   516 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   517 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"      =   0 =  OK 
     *   518 - assertIsTrue  "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ("      =   0 =  OK 
     *   519 - assertIsFalse "domain.part@with(leftbracket.com"                                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   520 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   521 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   522 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   523 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   524 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   525 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   526 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment ()domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   527 - assertIsFalse "domain.part.only.leftbracket@(.com"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   528 - assertIsFalse "top.level.domain.only@leftbracket.("                                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   529 - assertIsFalse ").local.part.starts.with.rightbracket@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   530 - assertIsFalse "local.part.ends.with.rightbracket)@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   531 - assertIsFalse "local.part.with.rightbracket)character@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   532 - assertIsFalse "local.part.with.rightbracket.before).point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   533 - assertIsFalse "local.part.with.rightbracket.after.)point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   534 - assertIsFalse "local.part.with.double.rightbracket))test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   535 - assertIsFalse "(comment )) local.part.with.rightbracket.in.comment@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   536 - assertIsTrue  "\"string)\".local.part.with.rightbracket.in.String@domain.com"              =   1 =  OK 
     *   537 - assertIsFalse "\"string\)\".local.part.with.escaped.rightbracket.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   538 - assertIsFalse ")@local.part.only.rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   539 - assertIsFalse "))))))@local.part.only.consecutive.rightbracket.domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   540 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   541 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"     =   0 =  OK 
     *   542 - assertIsTrue  "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name )"     =   0 =  OK 
     *   543 - assertIsFalse "domain.part@with)rightbracket.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   544 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   545 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   546 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   547 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   548 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   549 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   550 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ))domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   551 - assertIsFalse "domain.part.only.rightbracket@).com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   552 - assertIsFalse "top.level.domain.only@rightbracket.)"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   553 - assertIsFalse "[.local.part.starts.with.leftbracket@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   554 - assertIsFalse "local.part.ends.with.leftbracket[@domain.com"                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   555 - assertIsFalse "local.part.with.leftbracket[character@domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   556 - assertIsFalse "local.part.with.leftbracket.before[.point@domain.com"                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   557 - assertIsFalse "local.part.with.leftbracket.after.[point@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   558 - assertIsFalse "local.part.with.double.leftbracket[[test@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   559 - assertIsFalse "(comment [) local.part.with.leftbracket.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   560 - assertIsTrue  "\"string[\".local.part.with.leftbracket.in.String@domain.com"               =   1 =  OK 
     *   561 - assertIsFalse "\"string\[\".local.part.with.escaped.leftbracket.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   562 - assertIsFalse "[@local.part.only.leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   563 - assertIsFalse "[[[[[[@local.part.only.consecutive.leftbracket.domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   564 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   565 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   566 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ["      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   567 - assertIsFalse "domain.part@with[leftbracket.com"                                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   568 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   569 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   570 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   571 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   572 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   573 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   574 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment [)domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   575 - assertIsFalse "domain.part.only.leftbracket@[.com"                                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   576 - assertIsFalse "top.level.domain.only@leftbracket.["                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   577 - assertIsFalse "].local.part.starts.with.rightbracket@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   578 - assertIsFalse "local.part.ends.with.rightbracket]@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   579 - assertIsFalse "local.part.with.rightbracket]character@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   580 - assertIsFalse "local.part.with.rightbracket.before].point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   581 - assertIsFalse "local.part.with.rightbracket.after.]point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   582 - assertIsFalse "local.part.with.double.rightbracket]]test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   583 - assertIsFalse "(comment ]) local.part.with.rightbracket.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   584 - assertIsTrue  "\"string]\".local.part.with.rightbracket.in.String@domain.com"              =   1 =  OK 
     *   585 - assertIsFalse "\"string\]\".local.part.with.escaped.rightbracket.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   586 - assertIsFalse "]@local.part.only.rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   587 - assertIsFalse "]]]]]]@local.part.only.consecutive.rightbracket.domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   588 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   589 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   590 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name ]"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   591 - assertIsFalse "domain.part@with]rightbracket.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   592 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   593 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   594 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   595 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   596 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   597 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   598 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ])domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   599 - assertIsFalse "domain.part.only.rightbracket@].com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   600 - assertIsFalse "top.level.domain.only@rightbracket.]"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   601 - assertIsFalse "().local.part.starts.with.empty.bracket@domain.com"                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   602 - assertIsTrue  "local.part.ends.with.empty.bracket()@domain.com"                            =   6 =  OK 
     *   603 - assertIsFalse "local.part.with.empty.bracket()character@domain.com"                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   604 - assertIsFalse "local.part.with.empty.bracket.before().point@domain.com"                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   605 - assertIsFalse "local.part.with.empty.bracket.after.()point@domain.com"                     = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   606 - assertIsFalse "local.part.with.double.empty.bracket()()test@domain.com"                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   607 - assertIsFalse "(comment ()) local.part.with.empty.bracket.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   608 - assertIsTrue  "\"string()\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   609 - assertIsFalse "\"string\()\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   610 - assertIsFalse "()@local.part.only.empty.bracket.domain.com"                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   611 - assertIsFalse "()()()()()()@local.part.only.consecutive.empty.bracket.domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   612 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   613 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =   0 =  OK 
     *   614 - assertIsTrue  "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name ()"   =   0 =  OK 
     *   615 - assertIsFalse "domain.part@with()empty.bracket.com"                                        = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   616 - assertIsTrue  "domain.part@()with.empty.bracket.at.domain.start.com"                       =   6 =  OK 
     *   617 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1().com"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   618 - assertIsTrue  "domain.part@with.empty.bracket.at.domain.end2.com()"                        =   6 =  OK 
     *   619 - assertIsFalse "domain.part@with.empty.bracket.before().point.com"                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   620 - assertIsFalse "domain.part@with.empty.bracket.after.()point.com"                           = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   621 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"                     = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   622 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment ())domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   623 - assertIsFalse "domain.part.only.empty.bracket@().com"                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   624 - assertIsFalse "top.level.domain.only@empty.bracket.()"                                     = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   625 - assertIsTrue  "{}.local.part.starts.with.empty.bracket@domain.com"                         =   0 =  OK 
     *   626 - assertIsTrue  "local.part.ends.with.empty.bracket{}@domain.com"                            =   0 =  OK 
     *   627 - assertIsTrue  "local.part.with.empty.bracket{}character@domain.com"                        =   0 =  OK 
     *   628 - assertIsTrue  "local.part.with.empty.bracket.before{}.point@domain.com"                    =   0 =  OK 
     *   629 - assertIsTrue  "local.part.with.empty.bracket.after.{}point@domain.com"                     =   0 =  OK 
     *   630 - assertIsTrue  "local.part.with.double.empty.bracket{}{}test@domain.com"                    =   0 =  OK 
     *   631 - assertIsTrue  "(comment {}) local.part.with.empty.bracket.in.comment@domain.com"           =   6 =  OK 
     *   632 - assertIsTrue  "\"string{}\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   633 - assertIsFalse "\"string\{}\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   634 - assertIsTrue  "{}@local.part.only.empty.bracket.domain.com"                                =   0 =  OK 
     *   635 - assertIsTrue  "{}{}{}{}{}{}@local.part.only.consecutive.empty.bracket.domain.com"          =   0 =  OK 
     *   636 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                                 =   0 =  OK 
     *   637 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   638 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name {}"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   639 - assertIsFalse "domain.part@with{}empty.bracket.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   640 - assertIsFalse "domain.part@{}with.empty.bracket.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   641 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1{}.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   642 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com{}"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   643 - assertIsFalse "domain.part@with.empty.bracket.before{}.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   644 - assertIsFalse "domain.part@with.empty.bracket.after.{}point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   645 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   646 - assertIsTrue  "domain.part.with.empty.bracket.in.comment@(comment {})domain.com"           =   6 =  OK 
     *   647 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   648 - assertIsFalse "top.level.domain.only@empty.bracket.{}"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   649 - assertIsFalse "[].local.part.starts.with.empty.bracket@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   650 - assertIsFalse "local.part.ends.with.empty.bracket[]@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   651 - assertIsFalse "local.part.with.empty.bracket[]character@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   652 - assertIsFalse "local.part.with.empty.bracket.before[].point@domain.com"                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   653 - assertIsFalse "local.part.with.empty.bracket.after.[]point@domain.com"                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   654 - assertIsFalse "local.part.with.double.empty.bracket[][]test@domain.com"                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   655 - assertIsFalse "(comment []) local.part.with.empty.bracket.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   656 - assertIsTrue  "\"string[]\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   657 - assertIsFalse "\"string\[]\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   658 - assertIsFalse "[]@local.part.only.empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   659 - assertIsFalse "[][][][][][]@local.part.only.consecutive.empty.bracket.domain.com"          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   660 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   661 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   662 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name []"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   663 - assertIsFalse "domain.part@with[]empty.bracket.com"                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   664 - assertIsFalse "domain.part@[]with.empty.bracket.at.domain.start.com"                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   665 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1[].com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   666 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com[]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   667 - assertIsFalse "domain.part@with.empty.bracket.before[].point.com"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   668 - assertIsFalse "domain.part@with.empty.bracket.after.[]point.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   669 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   670 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment [])domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   671 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   672 - assertIsFalse "top.level.domain.only@empty.bracket.[]"                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   673 - assertIsFalse "<>.local.part.starts.with.empty.bracket@domain.com"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   674 - assertIsFalse "local.part.ends.with.empty.bracket<>@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsFalse "local.part.with.empty.bracket<>character@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   676 - assertIsFalse "local.part.with.empty.bracket.before<>.point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   677 - assertIsFalse "local.part.with.empty.bracket.after.<>point@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   678 - assertIsFalse "local.part.with.double.empty.bracket<><>test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   679 - assertIsFalse "(comment <>) local.part.with.empty.bracket.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   680 - assertIsTrue  "\"string<>\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   681 - assertIsFalse "\"string\<>\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   682 - assertIsFalse "<>@local.part.only.empty.bracket.domain.com"                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   683 - assertIsFalse "<><><><><><>@local.part.only.consecutive.empty.bracket.domain.com"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   684 - assertIsFalse "<>.<>.<>.<>.<>.<>@empty.bracket.domain.com"                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   685 - assertIsFalse "name <> <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   686 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name <>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   687 - assertIsFalse "domain.part@with<>empty.bracket.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   688 - assertIsFalse "domain.part@<>with.empty.bracket.at.domain.start.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   689 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1<>.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   690 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com<>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   691 - assertIsFalse "domain.part@with.empty.bracket.before<>.point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   692 - assertIsFalse "domain.part@with.empty.bracket.after.<>point.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   693 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   694 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment <>)domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   695 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   696 - assertIsFalse "top.level.domain.only@empty.bracket.<>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   697 - assertIsFalse ")(.local.part.starts.with.false.bracket1@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   698 - assertIsFalse "local.part.ends.with.false.bracket1)(@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   699 - assertIsFalse "local.part.with.false.bracket1)(character@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   700 - assertIsFalse "local.part.with.false.bracket1.before)(.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   701 - assertIsFalse "local.part.with.false.bracket1.after.)(point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   702 - assertIsFalse "local.part.with.double.false.bracket1)()(test@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   703 - assertIsFalse "(comment )() local.part.with.false.bracket1.in.comment@domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   704 - assertIsTrue  "\"string)(\".local.part.with.false.bracket1.in.String@domain.com"           =   1 =  OK 
     *   705 - assertIsFalse "\"string\)(\".local.part.with.escaped.false.bracket1.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   706 - assertIsFalse ")(@local.part.only.false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   707 - assertIsFalse ")()()()()()(@local.part.only.consecutive.false.bracket1.domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   708 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   709 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1.in.display.name@domain.com>"  =   0 =  OK 
     *   710 - assertIsTrue  "<pointy.brackets2.with.false.bracket1.in.display.name@domain.com> name )("  =   0 =  OK 
     *   711 - assertIsFalse "domain.part@with)(false.bracket1.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   712 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   713 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   714 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   715 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   716 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   717 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   718 - assertIsFalse "domain.part.with.false.bracket1.in.comment@(comment )()domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   719 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   720 - assertIsFalse "top.level.domain.only@false.bracket1.)("                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   721 - assertIsTrue  "}{.local.part.starts.with.false.bracket2@domain.com"                        =   0 =  OK 
     *   722 - assertIsTrue  "local.part.ends.with.false.bracket2}{@domain.com"                           =   0 =  OK 
     *   723 - assertIsTrue  "local.part.with.false.bracket2}{character@domain.com"                       =   0 =  OK 
     *   724 - assertIsTrue  "local.part.with.false.bracket2.before}{.point@domain.com"                   =   0 =  OK 
     *   725 - assertIsTrue  "local.part.with.false.bracket2.after.}{point@domain.com"                    =   0 =  OK 
     *   726 - assertIsTrue  "local.part.with.double.false.bracket2}{}{test@domain.com"                   =   0 =  OK 
     *   727 - assertIsTrue  "(comment }{) local.part.with.false.bracket2.in.comment@domain.com"          =   6 =  OK 
     *   728 - assertIsTrue  "\"string}{\".local.part.with.false.bracket2.in.String@domain.com"           =   1 =  OK 
     *   729 - assertIsFalse "\"string\}{\".local.part.with.escaped.false.bracket2.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   730 - assertIsTrue  "}{@local.part.only.false.bracket2.domain.com"                               =   0 =  OK 
     *   731 - assertIsTrue  "}{}{}{}{}{}{@local.part.only.consecutive.false.bracket2.domain.com"         =   0 =  OK 
     *   732 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                                =   0 =  OK 
     *   733 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2.in.display.name@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   734 - assertIsFalse "<pointy.brackets2.with.false.bracket2.in.display.name@domain.com> name }{"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   735 - assertIsFalse "domain.part@with}{false.bracket2.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   736 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   737 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   738 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   739 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   740 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   741 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   742 - assertIsTrue  "domain.part.with.false.bracket2.in.comment@(comment }{)domain.com"          =   6 =  OK 
     *   743 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   744 - assertIsFalse "top.level.domain.only@false.bracket2.}{"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   745 - assertIsFalse "][.local.part.starts.with.false.bracket3@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   746 - assertIsFalse "local.part.ends.with.false.bracket3][@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   747 - assertIsFalse "local.part.with.false.bracket3][character@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   748 - assertIsFalse "local.part.with.false.bracket3.before][.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   749 - assertIsFalse "local.part.with.false.bracket3.after.][point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   750 - assertIsFalse "local.part.with.double.false.bracket3][][test@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   751 - assertIsFalse "(comment ][) local.part.with.false.bracket3.in.comment@domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   752 - assertIsTrue  "\"string][\".local.part.with.false.bracket3.in.String@domain.com"           =   1 =  OK 
     *   753 - assertIsFalse "\"string\][\".local.part.with.escaped.false.bracket3.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   754 - assertIsFalse "][@local.part.only.false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   755 - assertIsFalse "][][][][][][@local.part.only.consecutive.false.bracket3.domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   756 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   757 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3.in.display.name@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   758 - assertIsFalse "<pointy.brackets2.with.false.bracket3.in.display.name@domain.com> name ]["  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   759 - assertIsFalse "domain.part@with][false.bracket3.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   760 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   761 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   762 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   763 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   764 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   765 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   766 - assertIsFalse "domain.part.with.false.bracket3.in.comment@(comment ][)domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   767 - assertIsFalse "domain.part.only.false.bracket3@][.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   768 - assertIsFalse "top.level.domain.only@false.bracket3.]["                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   769 - assertIsFalse "><.local.part.starts.with.false.bracket4@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   770 - assertIsFalse "local.part.ends.with.false.bracket4><@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   771 - assertIsFalse "local.part.with.false.bracket4><character@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse "local.part.with.false.bracket4.before><.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   773 - assertIsFalse "local.part.with.false.bracket4.after.><point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   774 - assertIsFalse "local.part.with.double.false.bracket4><><test@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   775 - assertIsFalse "(comment ><) local.part.with.false.bracket4.in.comment@domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   776 - assertIsTrue  "\"string><\".local.part.with.false.bracket4.in.String@domain.com"           =   1 =  OK 
     *   777 - assertIsFalse "\"string\><\".local.part.with.escaped.false.bracket4.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   778 - assertIsFalse "><@local.part.only.false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   779 - assertIsFalse "><><><><><><@local.part.only.consecutive.false.bracket4.domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   780 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   781 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4.in.display.name@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   782 - assertIsFalse "<pointy.brackets2.with.false.bracket4.in.display.name@domain.com> name ><"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   783 - assertIsFalse "domain.part@with><false.bracket4.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   784 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   785 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   786 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   787 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   788 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   789 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   790 - assertIsFalse "domain.part.with.false.bracket4.in.comment@(comment ><)domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   791 - assertIsFalse "domain.part.only.false.bracket4@><.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   792 - assertIsFalse "top.level.domain.only@false.bracket4.><"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   793 - assertIsFalse "<.local.part.starts.with.lower.than@domain.com"                             =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   794 - assertIsFalse "local.part.ends.with.lower.than<@domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   795 - assertIsFalse "local.part.with.lower.than<character@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   796 - assertIsFalse "local.part.with.lower.than.before<.point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   797 - assertIsFalse "local.part.with.lower.than.after.<point@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   798 - assertIsFalse "local.part.with.double.lower.than<<test@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   799 - assertIsFalse "(comment <) local.part.with.lower.than.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   800 - assertIsTrue  "\"string<\".local.part.with.lower.than.in.String@domain.com"                =   1 =  OK 
     *   801 - assertIsFalse "\"string\<\".local.part.with.escaped.lower.than.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   802 - assertIsFalse "<@local.part.only.lower.than.domain.com"                                    =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   803 - assertIsFalse "<<<<<<@local.part.only.consecutive.lower.than.domain.com"                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   804 - assertIsFalse "<.<.<.<.<.<@lower.than.domain.com"                                          =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   805 - assertIsFalse "name < <pointy.brackets1.with.lower.than.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   806 - assertIsFalse "<pointy.brackets2.with.lower.than.in.display.name@domain.com> name <"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   807 - assertIsFalse "domain.part@with<lower.than.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   808 - assertIsFalse "domain.part@<with.lower.than.at.domain.start.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   809 - assertIsFalse "domain.part@with.lower.than.at.domain.end1<.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   810 - assertIsFalse "domain.part@with.lower.than.at.domain.end2.com<"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   811 - assertIsFalse "domain.part@with.lower.than.before<.point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   812 - assertIsFalse "domain.part@with.lower.than.after.<point.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   813 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   814 - assertIsFalse "domain.part.with.lower.than.in.comment@(comment <)domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   815 - assertIsFalse "domain.part.only.lower.than@<.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   816 - assertIsFalse "top.level.domain.only@lower.than.<"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   817 - assertIsFalse ">.local.part.starts.with.greater.than@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   818 - assertIsFalse "local.part.ends.with.greater.than>@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   819 - assertIsFalse "local.part.with.greater.than>character@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   820 - assertIsFalse "local.part.with.greater.than.before>.point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   821 - assertIsFalse "local.part.with.greater.than.after.>point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   822 - assertIsFalse "local.part.with.double.greater.than>>test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   823 - assertIsFalse "(comment >) local.part.with.greater.than.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   824 - assertIsTrue  "\"string>\".local.part.with.greater.than.in.String@domain.com"              =   1 =  OK 
     *   825 - assertIsFalse "\"string\>\".local.part.with.escaped.greater.than.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   826 - assertIsFalse ">@local.part.only.greater.than.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   827 - assertIsFalse ">>>>>>@local.part.only.consecutive.greater.than.domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   828 - assertIsFalse ">.>.>.>.>.>@greater.than.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   829 - assertIsFalse "name > <pointy.brackets1.with.greater.than.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   830 - assertIsFalse "<pointy.brackets2.with.greater.than.in.display.name@domain.com> name >"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   831 - assertIsFalse "domain.part@with>greater.than.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   832 - assertIsFalse "domain.part@>with.greater.than.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   833 - assertIsFalse "domain.part@with.greater.than.at.domain.end1>.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   834 - assertIsFalse "domain.part@with.greater.than.at.domain.end2.com>"                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   835 - assertIsFalse "domain.part@with.greater.than.before>.point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   836 - assertIsFalse "domain.part@with.greater.than.after.>point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   837 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   838 - assertIsFalse "domain.part.with.greater.than.in.comment@(comment >)domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   839 - assertIsFalse "domain.part.only.greater.than@>.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   840 - assertIsFalse "top.level.domain.only@greater.than.>"                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   841 - assertIsTrue  "~.local.part.starts.with.tilde@domain.com"                                  =   0 =  OK 
     *   842 - assertIsTrue  "local.part.ends.with.tilde~@domain.com"                                     =   0 =  OK 
     *   843 - assertIsTrue  "local.part.with.tilde~character@domain.com"                                 =   0 =  OK 
     *   844 - assertIsTrue  "local.part.with.tilde.before~.point@domain.com"                             =   0 =  OK 
     *   845 - assertIsTrue  "local.part.with.tilde.after.~point@domain.com"                              =   0 =  OK 
     *   846 - assertIsTrue  "local.part.with.double.tilde~~test@domain.com"                              =   0 =  OK 
     *   847 - assertIsTrue  "(comment ~) local.part.with.tilde.in.comment@domain.com"                    =   6 =  OK 
     *   848 - assertIsTrue  "\"string~\".local.part.with.tilde.in.String@domain.com"                     =   1 =  OK 
     *   849 - assertIsFalse "\"string\~\".local.part.with.escaped.tilde.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   850 - assertIsTrue  "~@local.part.only.tilde.domain.com"                                         =   0 =  OK 
     *   851 - assertIsTrue  "~~~~~~@local.part.only.consecutive.tilde.domain.com"                        =   0 =  OK 
     *   852 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                               =   0 =  OK 
     *   853 - assertIsFalse "name ~ <pointy.brackets1.with.tilde.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   854 - assertIsFalse "<pointy.brackets2.with.tilde.in.display.name@domain.com> name ~"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   855 - assertIsFalse "domain.part@with~tilde.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   856 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   857 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   858 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   859 - assertIsFalse "domain.part@with.tilde.before~.point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   860 - assertIsFalse "domain.part@with.tilde.after.~point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   861 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   862 - assertIsTrue  "domain.part.with.tilde.in.comment@(comment ~)domain.com"                    =   6 =  OK 
     *   863 - assertIsFalse "domain.part.only.tilde@~.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   864 - assertIsFalse "top.level.domain.only@tilde.~"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   865 - assertIsTrue  "^.local.part.starts.with.xor@domain.com"                                    =   0 =  OK 
     *   866 - assertIsTrue  "local.part.ends.with.xor^@domain.com"                                       =   0 =  OK 
     *   867 - assertIsTrue  "local.part.with.xor^character@domain.com"                                   =   0 =  OK 
     *   868 - assertIsTrue  "local.part.with.xor.before^.point@domain.com"                               =   0 =  OK 
     *   869 - assertIsTrue  "local.part.with.xor.after.^point@domain.com"                                =   0 =  OK 
     *   870 - assertIsTrue  "local.part.with.double.xor^^test@domain.com"                                =   0 =  OK 
     *   871 - assertIsTrue  "(comment ^) local.part.with.xor.in.comment@domain.com"                      =   6 =  OK 
     *   872 - assertIsTrue  "\"string^\".local.part.with.xor.in.String@domain.com"                       =   1 =  OK 
     *   873 - assertIsFalse "\"string\^\".local.part.with.escaped.xor.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   874 - assertIsTrue  "^@local.part.only.xor.domain.com"                                           =   0 =  OK 
     *   875 - assertIsTrue  "^^^^^^@local.part.only.consecutive.xor.domain.com"                          =   0 =  OK 
     *   876 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                                 =   0 =  OK 
     *   877 - assertIsFalse "name ^ <pointy.brackets1.with.xor.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   878 - assertIsFalse "<pointy.brackets2.with.xor.in.display.name@domain.com> name ^"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   879 - assertIsFalse "domain.part@with^xor.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   880 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   881 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   882 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   883 - assertIsFalse "domain.part@with.xor.before^.point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   884 - assertIsFalse "domain.part@with.xor.after.^point.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   885 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   886 - assertIsTrue  "domain.part.with.xor.in.comment@(comment ^)domain.com"                      =   6 =  OK 
     *   887 - assertIsFalse "domain.part.only.xor@^.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   888 - assertIsFalse "top.level.domain.only@xor.^"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   889 - assertIsFalse ":.local.part.starts.with.colon@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   890 - assertIsFalse "local.part.ends.with.colon:@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   891 - assertIsFalse "local.part.with.colon:character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   892 - assertIsFalse "local.part.with.colon.before:.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   893 - assertIsFalse "local.part.with.colon.after.:point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   894 - assertIsFalse "local.part.with.double.colon::test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   895 - assertIsFalse "(comment :) local.part.with.colon.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   896 - assertIsTrue  "\"string:\".local.part.with.colon.in.String@domain.com"                     =   1 =  OK 
     *   897 - assertIsFalse "\"string\:\".local.part.with.escaped.colon.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   898 - assertIsFalse ":@local.part.only.colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   899 - assertIsFalse "::::::@local.part.only.consecutive.colon.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   900 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   901 - assertIsFalse "name : <pointy.brackets1.with.colon.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   902 - assertIsFalse "<pointy.brackets2.with.colon.in.display.name@domain.com> name :"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   903 - assertIsFalse "domain.part@with:colon.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   904 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   905 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   906 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   907 - assertIsFalse "domain.part@with.colon.before:.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   908 - assertIsFalse "domain.part@with.colon.after.:point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   909 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   910 - assertIsFalse "domain.part.with.colon.in.comment@(comment :)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   911 - assertIsFalse "domain.part.only.colon@:.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   912 - assertIsFalse "top.level.domain.only@colon.:"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   913 - assertIsFalse " .local.part.starts.with.space@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   914 - assertIsFalse "local.part.ends.with.space @domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   915 - assertIsFalse "local.part.with.space character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   916 - assertIsFalse "local.part.with.space.before .point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   917 - assertIsFalse "local.part.with.space.after. point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   918 - assertIsFalse "local.part.with.double.space  test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   919 - assertIsTrue  "(comment  ) local.part.with.space.in.comment@domain.com"                    =   6 =  OK 
     *   920 - assertIsTrue  "\"string \".local.part.with.space.in.String@domain.com"                     =   1 =  OK 
     *   921 - assertIsTrue  "\"string\ \".local.part.with.escaped.space.in.String@domain.com"            =   1 =  OK 
     *   922 - assertIsFalse " @local.part.only.space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   923 - assertIsFalse "      @local.part.only.consecutive.space.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   924 - assertIsFalse " . . . . . @space.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   925 - assertIsTrue  "name   <pointy.brackets1.with.space.in.display.name@domain.com>"            =   0 =  OK 
     *   926 - assertIsTrue  "<pointy.brackets2.with.space.in.display.name@domain.com> name  "            =   0 =  OK 
     *   927 - assertIsFalse "domain.part@with space.com"                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   928 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   929 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   930 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   931 - assertIsFalse "domain.part@with.space.before .point.com"                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   932 - assertIsFalse "domain.part@with.space.after. point.com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   933 - assertIsFalse "domain.part@with.consecutive.space  test.com"                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   934 - assertIsTrue  "domain.part.with.space.in.comment@(comment  )domain.com"                    =   6 =  OK 
     *   935 - assertIsFalse "domain.part.only.space@ .com"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   936 - assertIsFalse "top.level.domain.only@space. "                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   937 - assertIsFalse ",.local.part.starts.with.comma@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   938 - assertIsFalse "local.part.ends.with.comma,@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   939 - assertIsFalse "local.part.with.comma,character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   940 - assertIsFalse "local.part.with.comma.before,.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   941 - assertIsFalse "local.part.with.comma.after.,point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   942 - assertIsFalse "local.part.with.double.comma,,test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   943 - assertIsFalse "(comment ,) local.part.with.comma.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   944 - assertIsTrue  "\"string,\".local.part.with.comma.in.String@domain.com"                     =   1 =  OK 
     *   945 - assertIsFalse "\"string\,\".local.part.with.escaped.comma.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   946 - assertIsFalse ",@local.part.only.comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   947 - assertIsFalse ",,,,,,@local.part.only.consecutive.comma.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   948 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   949 - assertIsFalse "name , <pointy.brackets1.with.comma.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   950 - assertIsFalse "<pointy.brackets2.with.comma.in.display.name@domain.com> name ,"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   951 - assertIsFalse "domain.part@with,comma.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   952 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   953 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   954 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   955 - assertIsFalse "domain.part@with.comma.before,.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   956 - assertIsFalse "domain.part@with.comma.after.,point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   957 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   958 - assertIsFalse "domain.part.with.comma.in.comment@(comment ,)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   959 - assertIsFalse "domain.part.only.comma@,.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   960 - assertIsFalse "top.level.domain.only@comma.,"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   961 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   962 - assertIsFalse "local.part.ends.with.at@@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   963 - assertIsFalse "local.part.with.at@character@domain.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   964 - assertIsFalse "local.part.with.at.before@.point@domain.com"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   965 - assertIsFalse "local.part.with.at.after.@point@domain.com"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   966 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   967 - assertIsTrue  "(comment @) local.part.with.at.in.comment@domain.com"                       =   6 =  OK 
     *   968 - assertIsTrue  "\"string@\".local.part.with.at.in.String@domain.com"                        =   1 =  OK 
     *   969 - assertIsTrue  "\"string\@\".local.part.with.escaped.at.in.String@domain.com"               =   1 =  OK 
     *   970 - assertIsFalse "@@local.part.only.at.domain.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   971 - assertIsFalse "@@@@@@@local.part.only.consecutive.at.domain.com"                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   972 - assertIsFalse "@.@.@.@.@.@@at.domain.com"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   973 - assertIsFalse "name @ <pointy.brackets1.with.at.in.display.name@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   974 - assertIsFalse "<pointy.brackets2.with.at.in.display.name@domain.com> name @"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   975 - assertIsFalse "domain.part@with@at.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   976 - assertIsFalse "domain.part@@with.at.at.domain.start.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   977 - assertIsFalse "domain.part@with.at.at.domain.end1@.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   978 - assertIsFalse "domain.part@with.at.at.domain.end2.com@"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   979 - assertIsFalse "domain.part@with.at.before@.point.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   980 - assertIsFalse "domain.part@with.at.after.@point.com"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   981 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   982 - assertIsTrue  "domain.part.with.at.in.comment@(comment @)domain.com"                       =   6 =  OK 
     *   983 - assertIsFalse "domain.part.only.at@@.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   984 - assertIsFalse "top.level.domain.only@at.@"                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   985 - assertIsFalse "§.local.part.starts.with.paragraph@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   986 - assertIsFalse "local.part.ends.with.paragraph§@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   987 - assertIsFalse "local.part.with.paragraph§character@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   988 - assertIsFalse "local.part.with.paragraph.before§.point@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   989 - assertIsFalse "local.part.with.paragraph.after.§point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   990 - assertIsFalse "local.part.with.double.paragraph§§test@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   991 - assertIsFalse "(comment §) local.part.with.paragraph.in.comment@domain.com"                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   992 - assertIsFalse "\"string§\".local.part.with.paragraph.in.String@domain.com"                 =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   993 - assertIsFalse "\"string\§\".local.part.with.escaped.paragraph.in.String@domain.com"        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   994 - assertIsFalse "§@local.part.only.paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   995 - assertIsFalse "§§§§§§@local.part.only.consecutive.paragraph.domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   996 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   997 - assertIsFalse "name § <pointy.brackets1.with.paragraph.in.display.name@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   998 - assertIsFalse "<pointy.brackets2.with.paragraph.in.display.name@domain.com> name §"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   999 - assertIsFalse "domain.part@with§paragraph.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1000 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1001 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1002 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1003 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1004 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1005 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1006 - assertIsFalse "domain.part.with.paragraph.in.comment@(comment §)domain.com"                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1007 - assertIsFalse "domain.part.only.paragraph@§.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1008 - assertIsFalse "top.level.domain.only@paragraph.§"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1009 - assertIsTrue  "'.local.part.starts.with.double.quote@domain.com"                           =   0 =  OK 
     *  1010 - assertIsTrue  "local.part.ends.with.double.quote'@domain.com"                              =   0 =  OK 
     *  1011 - assertIsTrue  "local.part.with.double.quote'character@domain.com"                          =   0 =  OK 
     *  1012 - assertIsTrue  "local.part.with.double.quote.before'.point@domain.com"                      =   0 =  OK 
     *  1013 - assertIsTrue  "local.part.with.double.quote.after.'point@domain.com"                       =   0 =  OK 
     *  1014 - assertIsTrue  "local.part.with.double.double.quote''test@domain.com"                       =   0 =  OK 
     *  1015 - assertIsTrue  "(comment ') local.part.with.double.quote.in.comment@domain.com"             =   6 =  OK 
     *  1016 - assertIsTrue  "\"string'\".local.part.with.double.quote.in.String@domain.com"              =   1 =  OK 
     *  1017 - assertIsTrue  "\"string\'\".local.part.with.escaped.double.quote.in.String@domain.com"     =   1 =  OK 
     *  1018 - assertIsTrue  "'@local.part.only.double.quote.domain.com"                                  =   0 =  OK 
     *  1019 - assertIsTrue  "''''''@local.part.only.consecutive.double.quote.domain.com"                 =   0 =  OK 
     *  1020 - assertIsTrue  "'.'.'.'.'.'@double.quote.domain.com"                                        =   0 =  OK 
     *  1021 - assertIsFalse "name ' <pointy.brackets1.with.double.quote.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1022 - assertIsFalse "<pointy.brackets2.with.double.quote.in.display.name@domain.com> name '"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1023 - assertIsFalse "domain.part@with'double.quote.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1024 - assertIsFalse "domain.part@'with.double.quote.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1025 - assertIsFalse "domain.part@with.double.quote.at.domain.end1'.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1026 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com'"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1027 - assertIsFalse "domain.part@with.double.quote.before'.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1028 - assertIsFalse "domain.part@with.double.quote.after.'point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1029 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1030 - assertIsTrue  "domain.part.with.double.quote.in.comment@(comment ')domain.com"             =   6 =  OK 
     *  1031 - assertIsFalse "domain.part.only.double.quote@'.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1032 - assertIsFalse "top.level.domain.only@double.quote.'"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1033 - assertIsTrue  "/.local.part.starts.with.forward.slash@domain.com"                          =   0 =  OK 
     *  1034 - assertIsTrue  "local.part.ends.with.forward.slash/@domain.com"                             =   0 =  OK 
     *  1035 - assertIsTrue  "local.part.with.forward.slash/character@domain.com"                         =   0 =  OK 
     *  1036 - assertIsTrue  "local.part.with.forward.slash.before/.point@domain.com"                     =   0 =  OK 
     *  1037 - assertIsTrue  "local.part.with.forward.slash.after./point@domain.com"                      =   0 =  OK 
     *  1038 - assertIsTrue  "local.part.with.double.forward.slash//test@domain.com"                      =   0 =  OK 
     *  1039 - assertIsTrue  "(comment /) local.part.with.forward.slash.in.comment@domain.com"            =   6 =  OK 
     *  1040 - assertIsTrue  "\"string/\".local.part.with.forward.slash.in.String@domain.com"             =   1 =  OK 
     *  1041 - assertIsFalse "\"string\/\".local.part.with.escaped.forward.slash.in.String@domain.com"    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1042 - assertIsTrue  "/@local.part.only.forward.slash.domain.com"                                 =   0 =  OK 
     *  1043 - assertIsTrue  "//////@local.part.only.consecutive.forward.slash.domain.com"                =   0 =  OK 
     *  1044 - assertIsTrue  "/./././././@forward.slash.domain.com"                                       =   0 =  OK 
     *  1045 - assertIsFalse "name / <pointy.brackets1.with.forward.slash.in.display.name@domain.com>"    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1046 - assertIsFalse "<pointy.brackets2.with.forward.slash.in.display.name@domain.com> name /"    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1047 - assertIsFalse "domain.part@with/forward.slash.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1048 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1049 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1050 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1051 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1052 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1053 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1054 - assertIsTrue  "domain.part.with.forward.slash.in.comment@(comment /)domain.com"            =   6 =  OK 
     *  1055 - assertIsFalse "domain.part.only.forward.slash@/.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1056 - assertIsFalse "top.level.domain.only@forward.slash./"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1057 - assertIsTrue  "-.local.part.starts.with.hyphen@domain.com"                                 =   0 =  OK 
     *  1058 - assertIsTrue  "local.part.ends.with.hyphen-@domain.com"                                    =   0 =  OK 
     *  1059 - assertIsTrue  "local.part.with.hyphen-character@domain.com"                                =   0 =  OK 
     *  1060 - assertIsTrue  "local.part.with.hyphen.before-.point@domain.com"                            =   0 =  OK 
     *  1061 - assertIsTrue  "local.part.with.hyphen.after.-point@domain.com"                             =   0 =  OK 
     *  1062 - assertIsTrue  "local.part.with.double.hyphen--test@domain.com"                             =   0 =  OK 
     *  1063 - assertIsTrue  "(comment -) local.part.with.hyphen.in.comment@domain.com"                   =   6 =  OK 
     *  1064 - assertIsTrue  "\"string-\".local.part.with.hyphen.in.String@domain.com"                    =   1 =  OK 
     *  1065 - assertIsFalse "\"string\-\".local.part.with.escaped.hyphen.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1066 - assertIsTrue  "-@local.part.only.hyphen.domain.com"                                        =   0 =  OK 
     *  1067 - assertIsTrue  "------@local.part.only.consecutive.hyphen.domain.com"                       =   0 =  OK 
     *  1068 - assertIsTrue  "-.-.-.-.-.-@hyphen.domain.com"                                              =   0 =  OK 
     *  1069 - assertIsFalse "name - <pointy.brackets1.with.hyphen.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1070 - assertIsFalse "<pointy.brackets2.with.hyphen.in.display.name@domain.com> name -"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1071 - assertIsTrue  "domain.part@with-hyphen.com"                                                =   0 =  OK 
     *  1072 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1073 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1074 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1075 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1076 - assertIsFalse "domain.part@with.hyphen.after.-point.com"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1077 - assertIsTrue  "domain.part@with.consecutive.hyphen--test.com"                              =   0 =  OK 
     *  1078 - assertIsTrue  "domain.part.with.hyphen.in.comment@(comment -)domain.com"                   =   6 =  OK 
     *  1079 - assertIsFalse "domain.part.only.hyphen@-.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1080 - assertIsFalse "top.level.domain.only@hyphen.-"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1081 - assertIsFalse "\"\".local.part.starts.with.empty.string1@domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1082 - assertIsFalse "local.part.ends.with.empty.string1\"\"@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1083 - assertIsFalse "local.part.with.empty.string1\"\"character@domain.com"                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1084 - assertIsFalse "local.part.with.empty.string1.before\"\".point@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1085 - assertIsFalse "local.part.with.empty.string1.after.\"\"point@domain.com"                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1086 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"test@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1087 - assertIsFalse "(comment \"\") local.part.with.empty.string1.in.comment@domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1088 - assertIsFalse "\"string\"\"\".local.part.with.empty.string1.in.String@domain.com"          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1089 - assertIsFalse "\"string\\"\"\".local.part.with.escaped.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1090 - assertIsFalse "\"\"@local.part.only.empty.string1.domain.com"                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1091 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1092 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"                     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1093 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1.in.display.name@domain.com>" =   0 =  OK 
     *  1094 - assertIsTrue  "<pointy.brackets2.with.empty.string1.in.display.name@domain.com> name \"\"" =   0 =  OK 
     *  1095 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1096 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1097 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1098 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1099 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1100 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1101 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1102 - assertIsFalse "domain.part.with.empty.string1.in.comment@(comment \"\")domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1103 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1104 - assertIsFalse "top.level.domain.only@empty.string1.\"\""                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1105 - assertIsFalse "a\"\"b.local.part.starts.with.empty.string2@domain.com"                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1106 - assertIsFalse "local.part.ends.with.empty.string2a\"\"b@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1107 - assertIsFalse "local.part.with.empty.string2a\"\"bcharacter@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1108 - assertIsFalse "local.part.with.empty.string2.beforea\"\"b.point@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1109 - assertIsFalse "local.part.with.empty.string2.after.a\"\"bpoint@domain.com"                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1110 - assertIsFalse "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com"            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1111 - assertIsFalse "(comment a\"\"b) local.part.with.empty.string2.in.comment@domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1112 - assertIsFalse "\"stringa\"\"b\".local.part.with.empty.string2.in.String@domain.com"        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1113 - assertIsFalse "\"string\a\"\"b\".local.part.with.escaped.empty.string2.in.String@domain.com" =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1114 - assertIsFalse "a\"\"b@local.part.only.empty.string2.domain.com"                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1115 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@local.part.only.consecutive.empty.string2.domain.com" =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1116 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1117 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2.in.display.name@domain.com>" =   0 =  OK 
     *  1118 - assertIsTrue  "<pointy.brackets2.with.empty.string2.in.display.name@domain.com> name a\"\"b" =   0 =  OK 
     *  1119 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1120 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1121 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1122 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1123 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1124 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1125 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1126 - assertIsFalse "domain.part.with.empty.string2.in.comment@(comment a\"\"b)domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1127 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1128 - assertIsFalse "top.level.domain.only@empty.string2.a\"\"b"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1129 - assertIsFalse "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com"            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1130 - assertIsFalse "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1131 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"character@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1132 - assertIsFalse "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com"       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1133 - assertIsFalse "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com"        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1134 - assertIsFalse "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1135 - assertIsFalse "(comment \"\"\"\") local.part.with.double.empty.string1.in.comment@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1136 - assertIsFalse "\"string\"\"\"\"\".local.part.with.double.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1137 - assertIsFalse "\"string\\"\"\"\"\".local.part.with.escaped.double.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1138 - assertIsFalse "\"\"\"\"@local.part.only.double.empty.string1.domain.com"                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1139 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1140 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1141 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1.in.display.name@domain.com>" =   0 =  OK 
     *  1142 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1.in.display.name@domain.com> name \"\"\"\"" =   0 =  OK 
     *  1143 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1144 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1145 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1146 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1147 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1148 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1149 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com"  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1150 - assertIsFalse "domain.part.with.double.empty.string1.in.comment@(comment \"\"\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1151 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1152 - assertIsFalse "top.level.domain.only@double.empty.string1.\"\"\"\""                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1153 - assertIsFalse "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com"           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1154 - assertIsFalse "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1155 - assertIsFalse "local.part.with.double.empty.string2\"\".\"\"character@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1156 - assertIsFalse "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com"      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1157 - assertIsFalse "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com"       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1158 - assertIsFalse "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1159 - assertIsFalse "(comment \"\".\"\") local.part.with.double.empty.string2.in.comment@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1160 - assertIsFalse "\"string\"\".\"\"\".local.part.with.double.empty.string2.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1161 - assertIsFalse "\"string\\"\".\"\"\".local.part.with.escaped.double.empty.string2.in.String@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1162 - assertIsFalse "\"\".\"\"@local.part.only.double.empty.string2.domain.com"                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1163 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\"@local.part.only.consecutive.double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1164 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com"         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1165 - assertIsFalse "name \"\".\"\" <pointy.brackets1.with.double.empty.string2.in.display.name@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1166 - assertIsFalse "<pointy.brackets2.with.double.empty.string2.in.display.name@domain.com> name \"\".\"\"" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1167 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1168 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1169 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1170 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1171 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1172 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1173 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1174 - assertIsFalse "domain.part.with.double.empty.string2.in.comment@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1175 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1176 - assertIsFalse "top.level.domain.only@double.empty.string2.\"\".\"\""                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1177 - assertIsTrue  "0.local.part.starts.with.number0@domain.com"                                =   0 =  OK 
     *  1178 - assertIsTrue  "local.part.ends.with.number00@domain.com"                                   =   0 =  OK 
     *  1179 - assertIsTrue  "local.part.with.number00character@domain.com"                               =   0 =  OK 
     *  1180 - assertIsTrue  "local.part.with.number0.before0.point@domain.com"                           =   0 =  OK 
     *  1181 - assertIsTrue  "local.part.with.number0.after.0point@domain.com"                            =   0 =  OK 
     *  1182 - assertIsTrue  "local.part.with.double.number000test@domain.com"                            =   0 =  OK 
     *  1183 - assertIsTrue  "(comment 0) local.part.with.number0.in.comment@domain.com"                  =   6 =  OK 
     *  1184 - assertIsTrue  "\"string0\".local.part.with.number0.in.String@domain.com"                   =   1 =  OK 
     *  1185 - assertIsFalse "\"string\0\".local.part.with.escaped.number0.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1186 - assertIsTrue  "0@local.part.only.number0.domain.com"                                       =   0 =  OK 
     *  1187 - assertIsTrue  "000000@local.part.only.consecutive.number0.domain.com"                      =   0 =  OK 
     *  1188 - assertIsTrue  "0.0.0.0.0.0@number0.domain.com"                                             =   0 =  OK 
     *  1189 - assertIsTrue  "name 0 <pointy.brackets1.with.number0.in.display.name@domain.com>"          =   0 =  OK 
     *  1190 - assertIsTrue  "<pointy.brackets2.with.number0.in.display.name@domain.com> name 0"          =   0 =  OK 
     *  1191 - assertIsTrue  "domain.part@with0number0.com"                                               =   0 =  OK 
     *  1192 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                              =   0 =  OK 
     *  1193 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                               =   0 =  OK 
     *  1194 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                               =   0 =  OK 
     *  1195 - assertIsTrue  "domain.part@with.number0.before0.point.com"                                 =   0 =  OK 
     *  1196 - assertIsTrue  "domain.part@with.number0.after.0point.com"                                  =   0 =  OK 
     *  1197 - assertIsTrue  "domain.part@with.consecutive.number000test.com"                             =   0 =  OK 
     *  1198 - assertIsTrue  "domain.part.with.number0.in.comment@(comment 0)domain.com"                  =   6 =  OK 
     *  1199 - assertIsTrue  "domain.part.only.number0@0.com"                                             =   0 =  OK 
     *  1200 - assertIsFalse "top.level.domain.only@number0.0"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1201 - assertIsTrue  "9.local.part.starts.with.number9@domain.com"                                =   0 =  OK 
     *  1202 - assertIsTrue  "local.part.ends.with.number99@domain.com"                                   =   0 =  OK 
     *  1203 - assertIsTrue  "local.part.with.number99character@domain.com"                               =   0 =  OK 
     *  1204 - assertIsTrue  "local.part.with.number9.before9.point@domain.com"                           =   0 =  OK 
     *  1205 - assertIsTrue  "local.part.with.number9.after.9point@domain.com"                            =   0 =  OK 
     *  1206 - assertIsTrue  "local.part.with.double.number999test@domain.com"                            =   0 =  OK 
     *  1207 - assertIsTrue  "(comment 9) local.part.with.number9.in.comment@domain.com"                  =   6 =  OK 
     *  1208 - assertIsTrue  "\"string9\".local.part.with.number9.in.String@domain.com"                   =   1 =  OK 
     *  1209 - assertIsFalse "\"string\9\".local.part.with.escaped.number9.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1210 - assertIsTrue  "9@local.part.only.number9.domain.com"                                       =   0 =  OK 
     *  1211 - assertIsTrue  "999999@local.part.only.consecutive.number9.domain.com"                      =   0 =  OK 
     *  1212 - assertIsTrue  "9.9.9.9.9.9@number9.domain.com"                                             =   0 =  OK 
     *  1213 - assertIsTrue  "name 9 <pointy.brackets1.with.number9.in.display.name@domain.com>"          =   0 =  OK 
     *  1214 - assertIsTrue  "<pointy.brackets2.with.number9.in.display.name@domain.com> name 9"          =   0 =  OK 
     *  1215 - assertIsTrue  "domain.part@with9number9.com"                                               =   0 =  OK 
     *  1216 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                              =   0 =  OK 
     *  1217 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                               =   0 =  OK 
     *  1218 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                               =   0 =  OK 
     *  1219 - assertIsTrue  "domain.part@with.number9.before9.point.com"                                 =   0 =  OK 
     *  1220 - assertIsTrue  "domain.part@with.number9.after.9point.com"                                  =   0 =  OK 
     *  1221 - assertIsTrue  "domain.part@with.consecutive.number999test.com"                             =   0 =  OK 
     *  1222 - assertIsTrue  "domain.part.with.number9.in.comment@(comment 9)domain.com"                  =   6 =  OK 
     *  1223 - assertIsTrue  "domain.part.only.number9@9.com"                                             =   0 =  OK 
     *  1224 - assertIsFalse "top.level.domain.only@number9.9"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1225 - assertIsTrue  "0123456789.local.part.starts.with.numbers@domain.com"                       =   0 =  OK 
     *  1226 - assertIsTrue  "local.part.ends.with.numbers0123456789@domain.com"                          =   0 =  OK 
     *  1227 - assertIsTrue  "local.part.with.numbers0123456789character@domain.com"                      =   0 =  OK 
     *  1228 - assertIsTrue  "local.part.with.numbers.before0123456789.point@domain.com"                  =   0 =  OK 
     *  1229 - assertIsTrue  "local.part.with.numbers.after.0123456789point@domain.com"                   =   0 =  OK 
     *  1230 - assertIsTrue  "local.part.with.double.numbers01234567890123456789test@domain.com"          =   0 =  OK 
     *  1231 - assertIsTrue  "(comment 0123456789) local.part.with.numbers.in.comment@domain.com"         =   6 =  OK 
     *  1232 - assertIsTrue  "\"string0123456789\".local.part.with.numbers.in.String@domain.com"          =   1 =  OK 
     *  1233 - assertIsFalse "\"string\0123456789\".local.part.with.escaped.numbers.in.String@domain.com" =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1234 - assertIsTrue  "0123456789@local.part.only.numbers.domain.com"                              =   0 =  OK 
     *  1235 - assertIsTrue  "01234567890123@local.part.only.consecutive.numbers.domain.com"              =   0 =  OK 
     *  1236 - assertIsTrue  "0123456789.0123456789.0123456789@numbers.domain.com"                        =   0 =  OK 
     *  1237 - assertIsTrue  "name 0123456789 <pointy.brackets1.with.numbers.in.display.name@domain.com>" =   0 =  OK 
     *  1238 - assertIsTrue  "<pointy.brackets2.with.numbers.in.display.name@domain.com> name 0123456789" =   0 =  OK 
     *  1239 - assertIsTrue  "domain.part@with0123456789numbers.com"                                      =   0 =  OK 
     *  1240 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"                     =   0 =  OK 
     *  1241 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                      =   0 =  OK 
     *  1242 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                      =   0 =  OK 
     *  1243 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                        =   0 =  OK 
     *  1244 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                         =   0 =  OK 
     *  1245 - assertIsTrue  "domain.part@with.consecutive.numbers01234567890123456789test.com"           =   0 =  OK 
     *  1246 - assertIsTrue  "domain.part.with.numbers.in.comment@(comment 0123456789)domain.com"         =   6 =  OK 
     *  1247 - assertIsTrue  "domain.part.only.numbers@0123456789.com"                                    =   0 =  OK 
     *  1248 - assertIsFalse "top.level.domain.only@numbers.0123456789"                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1249 - assertIsFalse "\.local.part.starts.with.slash@domain.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1250 - assertIsFalse "local.part.ends.with.slash\@domain.com"                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1251 - assertIsFalse "local.part.with.slash\character@domain.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1252 - assertIsFalse "local.part.with.slash.before\.point@domain.com"                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1253 - assertIsFalse "local.part.with.slash.after.\point@domain.com"                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1254 - assertIsTrue  "local.part.with.double.slash\\test@domain.com"                              =   0 =  OK 
     *  1255 - assertIsFalse "(comment \) local.part.with.slash.in.comment@domain.com"                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1256 - assertIsFalse "\"string\\".local.part.with.slash.in.String@domain.com"                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1257 - assertIsTrue  "\"string\\\".local.part.with.escaped.slash.in.String@domain.com"            =   1 =  OK 
     *  1258 - assertIsFalse "\@local.part.only.slash.domain.com"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1259 - assertIsTrue  "\\\\\\@local.part.only.consecutive.slash.domain.com"                        =   0 =  OK 
     *  1260 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1261 - assertIsTrue  "escaped character is space \ <pointy.brackets1.with.slash.in.display.name@domain.com>" =   0 =  OK 
     *  1262 - assertIsFalse "no escaped character \<pointy.brackets1.with.slash.in.display.name@domain.com>" =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
     *  1263 - assertIsFalse "<pointy.brackets2.with.slash.in.display.name@domain.com> name \"            =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
     *  1264 - assertIsFalse "domain.part@with\slash.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1265 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1266 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1267 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1268 - assertIsFalse "domain.part@with.slash.before\.point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1269 - assertIsFalse "domain.part@with.slash.after.\point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1270 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1271 - assertIsFalse "domain.part.with.slash.in.comment@(comment \)domain.com"                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1272 - assertIsFalse "domain.part.only.slash@\.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1273 - assertIsFalse "top.level.domain.only@slash.\"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1274 - assertIsTrue  "\"str\".local.part.starts.with.string@domain.com"                           =   1 =  OK 
     *  1275 - assertIsFalse "local.part.ends.with.string\"str\"@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1276 - assertIsFalse "local.part.with.string\"str\"character@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1277 - assertIsFalse "local.part.with.string.before\"str\".point@domain.com"                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1278 - assertIsFalse "local.part.with.string.after.\"str\"point@domain.com"                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1279 - assertIsFalse "local.part.with.double.string\"str\"\"str\"test@domain.com"                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1280 - assertIsFalse "(comment \"str\") local.part.with.string.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1281 - assertIsFalse "\"string\"str\"\".local.part.with.string.in.String@domain.com"              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1282 - assertIsFalse "\"string\\"str\"\".local.part.with.escaped.string.in.String@domain.com"     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1283 - assertIsTrue  "\"str\"@local.part.only.string.domain.com"                                  =   1 =  OK 
     *  1284 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@local.part.only.consecutive.string.domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1285 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"          =   1 =  OK 
     *  1286 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string.in.display.name@domain.com>"     =   0 =  OK 
     *  1287 - assertIsTrue  "<pointy.brackets2.with.string.in.display.name@domain.com> name \"str\""     =   0 =  OK 
     *  1288 - assertIsFalse "domain.part@with\"str\"string.com"                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1289 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1290 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1291 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1292 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1293 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1294 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1295 - assertIsFalse "domain.part.with.string.in.comment@(comment \"str\")domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1296 - assertIsFalse "domain.part.only.string@\"str\".com"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1297 - assertIsFalse "top.level.domain.only@string.\"str\""                                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1298 - assertIsFalse "(comment).local.part.starts.with.comment@domain.com"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1299 - assertIsTrue  "local.part.ends.with.comment(comment)@domain.com"                           =   6 =  OK 
     *  1300 - assertIsFalse "local.part.with.comment(comment)character@domain.com"                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1301 - assertIsFalse "local.part.with.comment.before(comment).point@domain.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1302 - assertIsFalse "local.part.with.comment.after.(comment)point@domain.com"                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1303 - assertIsFalse "local.part.with.double.comment(comment)(comment)test@domain.com"            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1304 - assertIsFalse "(comment (comment)) local.part.with.comment.in.comment@domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1305 - assertIsTrue  "\"string(comment)\".local.part.with.comment.in.String@domain.com"           =   1 =  OK 
     *  1306 - assertIsFalse "\"string\(comment)\".local.part.with.escaped.comment.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1307 - assertIsFalse "(comment)@local.part.only.comment.domain.com"                               =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1308 - assertIsFalse "(comment)(comment)(comment)@local.part.only.consecutive.comment.domain.com" =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1309 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1310 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment.in.display.name@domain.com>"  =   0 =  OK 
     *  1311 - assertIsTrue  "<pointy.brackets2.with.comment.in.display.name@domain.com> name (comment)"  =   0 =  OK 
     *  1312 - assertIsFalse "domain.part@with(comment)comment.com"                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1313 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                      =   6 =  OK 
     *  1314 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1315 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                       =   6 =  OK 
     *  1316 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1317 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                          = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1318 - assertIsFalse "domain.part@with.consecutive.comment(comment)(comment)test.com"             = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1319 - assertIsFalse "domain.part.with.comment.in.comment@(comment (comment))domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1320 - assertIsFalse "domain.part.only.comment@(comment).com"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1321 - assertIsFalse "top.level.domain.only@comment.(comment)"                                    = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     * 
     * ---- IP V4 -----------------------------------------------------------------------------------------------------------------------
     * 
     *  1322 - assertIsFalse "\"\"@[]"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1323 - assertIsFalse "\"\"@[1"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1324 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})"   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1325 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1326 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1327 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1328 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1329 - assertIsFalse "1.2.3.4]@[5.6.7.8]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1330 - assertIsFalse "[1.2.3.4@[5.6.7.8]"                                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1331 - assertIsFalse "[1.2.3.4][5.6.7.8]@[9.10.11.12]"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1332 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12]"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1333 - assertIsFalse "[1.2.3.4]@[5.6.7.8]9.10.11.12]"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1334 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12["                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1335 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1336 - assertIsTrue  "ip4.in.local.part.as.string1.\"[1.2.3.4]\"@[5.6.7.8]"                       =   3 =  OK 
     *  1337 - assertIsTrue  "ip4.in.local.part.as.string2.\"@[1.2.3.4]\"@[5.6.7.8]"                      =   3 =  OK 
     *  1338 - assertIsFalse "ip4.ends.with.alpha.character1@[1.2.3.Z]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1339 - assertIsFalse "ip4.ends.with.alpha.character2@[1.2.3.]Z"                                   =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1340 - assertIsFalse "ip4.ends.with.top.level.domain@[1.2.3.].de"                                 =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1341 - assertIsFalse "ip4.with.double.ip4@[1.2.3.4][5.6.7.8]"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1342 - assertIsFalse "ip4.with.ip4.in.comment1@([1.2.3.4])"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1343 - assertIsFalse "ip4.with.ip4.in.comment2@([1.2.3.4])[5.6.7.8]"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1344 - assertIsFalse "ip4.with.ip4.in.comment3@[1.2.3.4]([5.6.7.8])"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1345 - assertIsTrue  "ip4.with.ip4.in.comment4@[1.2.3.4] (@)"                                     =   2 =  OK 
     *  1346 - assertIsTrue  "ip4.with.ip4.in.comment5@[1.2.3.4] (@.)"                                    =   2 =  OK 
     *  1347 - assertIsFalse "ip4.with.hex.numbers@[AB.CD.EF.EA]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1348 - assertIsFalse "ip4.with.hex.number.overflow@[AB.CD.EF.FF1]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1349 - assertIsFalse "ip4.with.double.brackets@[1.2.3.4][5.6.7.8]"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1350 - assertIsFalse "ip4.missing.at.sign[1.2.3.4]"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1351 - assertIsFalse "ip4.missing.the.start.bracket@]"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1352 - assertIsFalse "ip4.missing.the.end.bracket@["                                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1353 - assertIsFalse "ip4.missing.the.start.bracket@1.2.3.4]"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1354 - assertIsFalse "ip4.missing.the.end.bracket@[1.2.3.4"                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1355 - assertIsFalse "ip4.missing.numbers.and.the.start.bracket@...]"                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1356 - assertIsFalse "ip4.missing.numbers.and.the.end.bracket@[..."                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1357 - assertIsFalse "ip4.missplaced.start.bracket1[@1.2.3.4]"                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1358 - assertIsFalse "ip4.missing.the.first.number@[.2.3.4]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1359 - assertIsFalse "ip4.missing.the.last.number@[1.2.3.]"                                       =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1360 - assertIsFalse "ip4.last.number.is.space@[1.2.3. ]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1361 - assertIsFalse "ip4.with.only.one.numberABC.DEF@[1]"                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1362 - assertIsFalse "ip4.with.only.two.numbers@[1.2]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1363 - assertIsFalse "ip4.with.only.three.numbers@[1.2.3]"                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1364 - assertIsFalse "ip4.with.five.numbers@[1.2.3.4.5]"                                          =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1365 - assertIsFalse "ip4.with.six.numbers@[1.2.3.4.5.6]"                                         =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1366 - assertIsFalse "ip4.with.byte.overflow1@[1.2.3.256]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1367 - assertIsFalse "ip4.with.byte.overflow2@[1.2.3.1000]"                                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1368 - assertIsFalse "ip4.with.to.many.leading.zeros@[0001.000002.000003.00000004]"               =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1369 - assertIsTrue  "ip4.with.two.leading.zeros@[001.002.003.004]"                               =   2 =  OK 
     *  1370 - assertIsTrue  "ip4.zero@[0.0.0.0]"                                                         =   2 =  OK 
     *  1371 - assertIsTrue  "ip4.correct1@[1.2.3.4]"                                                     =   2 =  OK 
     *  1372 - assertIsTrue  "ip4.correct2@[255.255.255.255]"                                             =   2 =  OK 
     *  1373 - assertIsTrue  "\"ip4.local.part.as.string\"@[127.0.0.1]"                                   =   3 =  OK 
     *  1374 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                         =   3 =  OK 
     *  1375 - assertIsFalse "ip4.with.negative.number1@[-1.2.3.4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1376 - assertIsFalse "ip4.with.negative.number2@[1.-2.3.4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1377 - assertIsFalse "ip4.with.negative.number3@[1.2.-3.4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1378 - assertIsFalse "ip4.with.negative.number4@[1.2.3.-4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1379 - assertIsFalse "ip4.with.only.empty.brackets@[]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1380 - assertIsFalse "ip4.with.three.empty.brackets@[][][]"                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1381 - assertIsFalse "ip4.with.only.one.dot.in.brackets@[.]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1382 - assertIsFalse "ip4.with.only.double.dot.in.brackets@[..]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1383 - assertIsFalse "ip4.with.only.triple.dot.in.brackets@[...]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1384 - assertIsFalse "ip4.with.only.four.dots.in.brackets@[....]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1385 - assertIsFalse "ip4.with.dot.between.numbers@[123.14.5.178.90]"                             =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1386 - assertIsFalse "ip4.with.dot.before.point@[123.145..178.90]"                                =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1387 - assertIsFalse "ip4.with.dot.after.point@[123.145..178.90]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1388 - assertIsFalse "ip4.with.dot.before.start.bracket@.[123.145.178.90]"                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1389 - assertIsFalse "ip4.with.dot.after.start.bracket@[.123.145.178.90]"                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1390 - assertIsFalse "ip4.with.dot.before.end.bracket@[123.145.178.90.]"                          =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1391 - assertIsFalse "ip4.with.dot.after.end.bracket@[123.145.178.90]."                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1392 - assertIsFalse "ip4.with.double.dot.between.numbers@[123.14..5.178.90]"                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1393 - assertIsFalse "ip4.with.double.dot.before.point@[123.145...178.90]"                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1394 - assertIsFalse "ip4.with.double.dot.after.point@[123.145...178.90]"                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1395 - assertIsFalse "ip4.with.double.dot.before.start.bracket@..[123.145.178.90]"                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1396 - assertIsFalse "ip4.with.double.dot.after.start.bracket@[..123.145.178.90]"                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1397 - assertIsFalse "ip4.with.double.dot.before.end.bracket@[123.145.178.90..]"                  =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1398 - assertIsFalse "ip4.with.double.dot.after.end.bracket@[123.145.178.90].."                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1399 - assertIsFalse "ip4.with.amp.between.numbers@[123.14&5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1400 - assertIsFalse "ip4.with.amp.before.point@[123.145&.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1401 - assertIsFalse "ip4.with.amp.after.point@[123.145.&178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1402 - assertIsFalse "ip4.with.amp.before.start.bracket@&[123.145.178.90]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1403 - assertIsFalse "ip4.with.amp.after.start.bracket@[&123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1404 - assertIsFalse "ip4.with.amp.before.end.bracket@[123.145.178.90&]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1405 - assertIsFalse "ip4.with.amp.after.end.bracket@[123.145.178.90]&"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1406 - assertIsFalse "ip4.with.asterisk.between.numbers@[123.14*5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1407 - assertIsFalse "ip4.with.asterisk.before.point@[123.145*.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1408 - assertIsFalse "ip4.with.asterisk.after.point@[123.145.*178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1409 - assertIsFalse "ip4.with.asterisk.before.start.bracket@*[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1410 - assertIsFalse "ip4.with.asterisk.after.start.bracket@[*123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1411 - assertIsFalse "ip4.with.asterisk.before.end.bracket@[123.145.178.90*]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1412 - assertIsFalse "ip4.with.asterisk.after.end.bracket@[123.145.178.90]*"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1413 - assertIsFalse "ip4.with.underscore.between.numbers@[123.14_5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1414 - assertIsFalse "ip4.with.underscore.before.point@[123.145_.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1415 - assertIsFalse "ip4.with.underscore.after.point@[123.145._178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1416 - assertIsFalse "ip4.with.underscore.before.start.bracket@_[123.145.178.90]"                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1417 - assertIsFalse "ip4.with.underscore.after.start.bracket@[_123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1418 - assertIsFalse "ip4.with.underscore.before.end.bracket@[123.145.178.90_]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1419 - assertIsFalse "ip4.with.underscore.after.end.bracket@[123.145.178.90]_"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1420 - assertIsFalse "ip4.with.dollar.between.numbers@[123.14$5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1421 - assertIsFalse "ip4.with.dollar.before.point@[123.145$.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1422 - assertIsFalse "ip4.with.dollar.after.point@[123.145.$178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1423 - assertIsFalse "ip4.with.dollar.before.start.bracket@$[123.145.178.90]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1424 - assertIsFalse "ip4.with.dollar.after.start.bracket@[$123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1425 - assertIsFalse "ip4.with.dollar.before.end.bracket@[123.145.178.90$]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1426 - assertIsFalse "ip4.with.dollar.after.end.bracket@[123.145.178.90]$"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1427 - assertIsFalse "ip4.with.equality.between.numbers@[123.14=5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1428 - assertIsFalse "ip4.with.equality.before.point@[123.145=.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1429 - assertIsFalse "ip4.with.equality.after.point@[123.145.=178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1430 - assertIsFalse "ip4.with.equality.before.start.bracket@=[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1431 - assertIsFalse "ip4.with.equality.after.start.bracket@[=123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1432 - assertIsFalse "ip4.with.equality.before.end.bracket@[123.145.178.90=]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1433 - assertIsFalse "ip4.with.equality.after.end.bracket@[123.145.178.90]="                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1434 - assertIsFalse "ip4.with.exclamation.between.numbers@[123.14!5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1435 - assertIsFalse "ip4.with.exclamation.before.point@[123.145!.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1436 - assertIsFalse "ip4.with.exclamation.after.point@[123.145.!178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1437 - assertIsFalse "ip4.with.exclamation.before.start.bracket@![123.145.178.90]"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1438 - assertIsFalse "ip4.with.exclamation.after.start.bracket@[!123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1439 - assertIsFalse "ip4.with.exclamation.before.end.bracket@[123.145.178.90!]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1440 - assertIsFalse "ip4.with.exclamation.after.end.bracket@[123.145.178.90]!"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1441 - assertIsFalse "ip4.with.question.between.numbers@[123.14?5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1442 - assertIsFalse "ip4.with.question.before.point@[123.145?.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1443 - assertIsFalse "ip4.with.question.after.point@[123.145.?178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1444 - assertIsFalse "ip4.with.question.before.start.bracket@?[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1445 - assertIsFalse "ip4.with.question.after.start.bracket@[?123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1446 - assertIsFalse "ip4.with.question.before.end.bracket@[123.145.178.90?]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1447 - assertIsFalse "ip4.with.question.after.end.bracket@[123.145.178.90]?"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1448 - assertIsFalse "ip4.with.grave-accent.between.numbers@[123.14`5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1449 - assertIsFalse "ip4.with.grave-accent.before.point@[123.145`.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1450 - assertIsFalse "ip4.with.grave-accent.after.point@[123.145.`178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1451 - assertIsFalse "ip4.with.grave-accent.before.start.bracket@`[123.145.178.90]"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1452 - assertIsFalse "ip4.with.grave-accent.after.start.bracket@[`123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1453 - assertIsFalse "ip4.with.grave-accent.before.end.bracket@[123.145.178.90`]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1454 - assertIsFalse "ip4.with.grave-accent.after.end.bracket@[123.145.178.90]`"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1455 - assertIsFalse "ip4.with.hash.between.numbers@[123.14#5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1456 - assertIsFalse "ip4.with.hash.before.point@[123.145#.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1457 - assertIsFalse "ip4.with.hash.after.point@[123.145.#178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1458 - assertIsFalse "ip4.with.hash.before.start.bracket@#[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1459 - assertIsFalse "ip4.with.hash.after.start.bracket@[#123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1460 - assertIsFalse "ip4.with.hash.before.end.bracket@[123.145.178.90#]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1461 - assertIsFalse "ip4.with.hash.after.end.bracket@[123.145.178.90]#"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1462 - assertIsFalse "ip4.with.percentage.between.numbers@[123.14%5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1463 - assertIsFalse "ip4.with.percentage.before.point@[123.145%.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1464 - assertIsFalse "ip4.with.percentage.after.point@[123.145.%178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1465 - assertIsFalse "ip4.with.percentage.before.start.bracket@%[123.145.178.90]"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1466 - assertIsFalse "ip4.with.percentage.after.start.bracket@[%123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1467 - assertIsFalse "ip4.with.percentage.before.end.bracket@[123.145.178.90%]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1468 - assertIsFalse "ip4.with.percentage.after.end.bracket@[123.145.178.90]%"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1469 - assertIsFalse "ip4.with.pipe.between.numbers@[123.14|5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1470 - assertIsFalse "ip4.with.pipe.before.point@[123.145|.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ip4.with.pipe.after.point@[123.145.|178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1472 - assertIsFalse "ip4.with.pipe.before.start.bracket@|[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1473 - assertIsFalse "ip4.with.pipe.after.start.bracket@[|123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1474 - assertIsFalse "ip4.with.pipe.before.end.bracket@[123.145.178.90|]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1475 - assertIsFalse "ip4.with.pipe.after.end.bracket@[123.145.178.90]|"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1476 - assertIsFalse "ip4.with.plus.between.numbers@[123.14+5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1477 - assertIsFalse "ip4.with.plus.before.point@[123.145+.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1478 - assertIsFalse "ip4.with.plus.after.point@[123.145.+178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1479 - assertIsFalse "ip4.with.plus.before.start.bracket@+[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1480 - assertIsFalse "ip4.with.plus.after.start.bracket@[+123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1481 - assertIsFalse "ip4.with.plus.before.end.bracket@[123.145.178.90+]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1482 - assertIsFalse "ip4.with.plus.after.end.bracket@[123.145.178.90]+"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1483 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14{5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1484 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145{.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1485 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1486 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@{[123.145.178.90]"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1487 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[{123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1488 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90{]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1489 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]{"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1490 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14}5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1491 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145}.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1492 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.}178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1493 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@}[123.145.178.90]"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1494 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[}123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1495 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90}]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1496 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]}"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1497 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14(5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1498 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145(.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1499 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1500 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@([123.145.178.90]"                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1501 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[(123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1502 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90(]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1503 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]("                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1504 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14)5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1505 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145).178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.)178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1507 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@)[123.145.178.90]"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1508 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[)123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1509 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90)]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1510 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90])"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1511 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14[5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1512 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145[.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1513 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.[178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1514 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@[[123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1515 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[[123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1516 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90[]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1517 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]["                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1518 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14]5.178.90]"                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1519 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145].178.90]"                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1520 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.]178.90]"                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1521 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@][123.145.178.90]"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1522 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[]123.145.178.90]"                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1523 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90]]"                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1524 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]]"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1525 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14()5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1526 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145().178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1527 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.()178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1528 - assertIsTrue  "ip4.with.empty.bracket.before.start.bracket@()[123.145.178.90]"             =   2 =  OK 
     *  1529 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[()123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1530 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90()]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1531 - assertIsTrue  "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]()"                =   2 =  OK 
     *  1532 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14{}5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1533 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145{}.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1534 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.{}178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1535 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@{}[123.145.178.90]"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1536 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[{}123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1537 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90{}]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1538 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]{}"                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1539 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14[]5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1540 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145[].178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1541 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.[]178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1542 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@[][123.145.178.90]"             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1543 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[[]123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1544 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90[]]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1545 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90][]"                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1546 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14<>5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1547 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145<>.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1548 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.<>178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1549 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@<>[123.145.178.90]"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1550 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[<>123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1551 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90<>]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1552 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]<>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1553 - assertIsFalse "ip4.with.false.bracket1.between.numbers@[123.14)(5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1554 - assertIsFalse "ip4.with.false.bracket1.before.point@[123.145)(.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1555 - assertIsFalse "ip4.with.false.bracket1.after.point@[123.145.)(178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1556 - assertIsFalse "ip4.with.false.bracket1.before.start.bracket@)([123.145.178.90]"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1557 - assertIsFalse "ip4.with.false.bracket1.after.start.bracket@[)(123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1558 - assertIsFalse "ip4.with.false.bracket1.before.end.bracket@[123.145.178.90)(]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1559 - assertIsFalse "ip4.with.false.bracket1.after.end.bracket@[123.145.178.90])("               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1560 - assertIsFalse "ip4.with.false.bracket2.between.numbers@[123.14}{5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1561 - assertIsFalse "ip4.with.false.bracket2.before.point@[123.145}{.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1562 - assertIsFalse "ip4.with.false.bracket2.after.point@[123.145.}{178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1563 - assertIsFalse "ip4.with.false.bracket2.before.start.bracket@}{[123.145.178.90]"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1564 - assertIsFalse "ip4.with.false.bracket2.after.start.bracket@[}{123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1565 - assertIsFalse "ip4.with.false.bracket2.before.end.bracket@[123.145.178.90}{]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip4.with.false.bracket2.after.end.bracket@[123.145.178.90]}{"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1567 - assertIsFalse "ip4.with.false.bracket4.between.numbers@[123.14><5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1568 - assertIsFalse "ip4.with.false.bracket4.before.point@[123.145><.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip4.with.false.bracket4.after.point@[123.145.><178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1570 - assertIsFalse "ip4.with.false.bracket4.before.start.bracket@><[123.145.178.90]"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1571 - assertIsFalse "ip4.with.false.bracket4.after.start.bracket@[><123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip4.with.false.bracket4.before.end.bracket@[123.145.178.90><]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1573 - assertIsFalse "ip4.with.false.bracket4.after.end.bracket@[123.145.178.90]><"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1574 - assertIsFalse "ip4.with.lower.than.between.numbers@[123.14<5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1575 - assertIsFalse "ip4.with.lower.than.before.point@[123.145<.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1576 - assertIsFalse "ip4.with.lower.than.after.point@[123.145.<178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1577 - assertIsFalse "ip4.with.lower.than.before.start.bracket@<[123.145.178.90]"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1578 - assertIsFalse "ip4.with.lower.than.after.start.bracket@[<123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1579 - assertIsFalse "ip4.with.lower.than.before.end.bracket@[123.145.178.90<]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1580 - assertIsFalse "ip4.with.lower.than.after.end.bracket@[123.145.178.90]<"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1581 - assertIsFalse "ip4.with.greater.than.between.numbers@[123.14>5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1582 - assertIsFalse "ip4.with.greater.than.before.point@[123.145>.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1583 - assertIsFalse "ip4.with.greater.than.after.point@[123.145.>178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1584 - assertIsFalse "ip4.with.greater.than.before.start.bracket@>[123.145.178.90]"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1585 - assertIsFalse "ip4.with.greater.than.after.start.bracket@[>123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1586 - assertIsFalse "ip4.with.greater.than.before.end.bracket@[123.145.178.90>]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1587 - assertIsFalse "ip4.with.greater.than.after.end.bracket@[123.145.178.90]>"                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1588 - assertIsFalse "ip4.with.tilde.between.numbers@[123.14~5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1589 - assertIsFalse "ip4.with.tilde.before.point@[123.145~.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1590 - assertIsFalse "ip4.with.tilde.after.point@[123.145.~178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1591 - assertIsFalse "ip4.with.tilde.before.start.bracket@~[123.145.178.90]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1592 - assertIsFalse "ip4.with.tilde.after.start.bracket@[~123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsFalse "ip4.with.tilde.before.end.bracket@[123.145.178.90~]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1594 - assertIsFalse "ip4.with.tilde.after.end.bracket@[123.145.178.90]~"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1595 - assertIsFalse "ip4.with.xor.between.numbers@[123.14^5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1596 - assertIsFalse "ip4.with.xor.before.point@[123.145^.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1597 - assertIsFalse "ip4.with.xor.after.point@[123.145.^178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1598 - assertIsFalse "ip4.with.xor.before.start.bracket@^[123.145.178.90]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1599 - assertIsFalse "ip4.with.xor.after.start.bracket@[^123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1600 - assertIsFalse "ip4.with.xor.before.end.bracket@[123.145.178.90^]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1601 - assertIsFalse "ip4.with.xor.after.end.bracket@[123.145.178.90]^"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1602 - assertIsFalse "ip4.with.colon.between.numbers@[123.14:5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1603 - assertIsFalse "ip4.with.colon.before.point@[123.145:.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1604 - assertIsFalse "ip4.with.colon.after.point@[123.145.:178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1605 - assertIsFalse "ip4.with.colon.before.start.bracket@:[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1606 - assertIsFalse "ip4.with.colon.after.start.bracket@[:123.145.178.90]"                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1607 - assertIsFalse "ip4.with.colon.before.end.bracket@[123.145.178.90:]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1608 - assertIsFalse "ip4.with.colon.after.end.bracket@[123.145.178.90]:"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1609 - assertIsFalse "ip4.with.space.between.numbers@[123.14 5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1610 - assertIsFalse "ip4.with.space.before.point@[123.145 .178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1611 - assertIsFalse "ip4.with.space.after.point@[123.145. 178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1612 - assertIsFalse "ip4.with.space.before.start.bracket@ [123.145.178.90]"                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1613 - assertIsFalse "ip4.with.space.after.start.bracket@[ 123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1614 - assertIsFalse "ip4.with.space.before.end.bracket@[123.145.178.90 ]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1615 - assertIsFalse "ip4.with.space.after.end.bracket@[123.145.178.90] "                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1616 - assertIsFalse "ip4.with.comma.between.numbers@[123.14,5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1617 - assertIsFalse "ip4.with.comma.before.point@[123.145,.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1618 - assertIsFalse "ip4.with.comma.after.point@[123.145.,178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1619 - assertIsFalse "ip4.with.comma.before.start.bracket@,[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1620 - assertIsFalse "ip4.with.comma.after.start.bracket@[,123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1621 - assertIsFalse "ip4.with.comma.before.end.bracket@[123.145.178.90,]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1622 - assertIsFalse "ip4.with.comma.after.end.bracket@[123.145.178.90],"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1623 - assertIsFalse "ip4.with.at.between.numbers@[123.14@5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1624 - assertIsFalse "ip4.with.at.before.point@[123.145@.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1625 - assertIsFalse "ip4.with.at.after.point@[123.145.@178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1626 - assertIsFalse "ip4.with.at.before.start.bracket@@[123.145.178.90]"                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1627 - assertIsFalse "ip4.with.at.after.start.bracket@[@123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1628 - assertIsFalse "ip4.with.at.before.end.bracket@[123.145.178.90@]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1629 - assertIsFalse "ip4.with.at.after.end.bracket@[123.145.178.90]@"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1630 - assertIsFalse "ip4.with.paragraph.between.numbers@[123.14§5.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1631 - assertIsFalse "ip4.with.paragraph.before.point@[123.145§.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1632 - assertIsFalse "ip4.with.paragraph.after.point@[123.145.§178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1633 - assertIsFalse "ip4.with.paragraph.before.start.bracket@§[123.145.178.90]"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1634 - assertIsFalse "ip4.with.paragraph.after.start.bracket@[§123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1635 - assertIsFalse "ip4.with.paragraph.before.end.bracket@[123.145.178.90§]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1636 - assertIsFalse "ip4.with.paragraph.after.end.bracket@[123.145.178.90]§"                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1637 - assertIsFalse "ip4.with.double.quote.between.numbers@[123.14'5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1638 - assertIsFalse "ip4.with.double.quote.before.point@[123.145'.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1639 - assertIsFalse "ip4.with.double.quote.after.point@[123.145.'178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1640 - assertIsFalse "ip4.with.double.quote.before.start.bracket@'[123.145.178.90]"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1641 - assertIsFalse "ip4.with.double.quote.after.start.bracket@['123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1642 - assertIsFalse "ip4.with.double.quote.before.end.bracket@[123.145.178.90']"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1643 - assertIsFalse "ip4.with.double.quote.after.end.bracket@[123.145.178.90]'"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1644 - assertIsFalse "ip4.with.forward.slash.between.numbers@[123.14/5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1645 - assertIsFalse "ip4.with.forward.slash.before.point@[123.145/.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1646 - assertIsFalse "ip4.with.forward.slash.after.point@[123.145./178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1647 - assertIsFalse "ip4.with.forward.slash.before.start.bracket@/[123.145.178.90]"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1648 - assertIsFalse "ip4.with.forward.slash.after.start.bracket@[/123.145.178.90]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1649 - assertIsFalse "ip4.with.forward.slash.before.end.bracket@[123.145.178.90/]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1650 - assertIsFalse "ip4.with.forward.slash.after.end.bracket@[123.145.178.90]/"                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1651 - assertIsFalse "ip4.with.hyphen.between.numbers@[123.14-5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1652 - assertIsFalse "ip4.with.hyphen.before.point@[123.145-.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1653 - assertIsFalse "ip4.with.hyphen.after.point@[123.145.-178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1654 - assertIsFalse "ip4.with.hyphen.before.start.bracket@-[123.145.178.90]"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1655 - assertIsFalse "ip4.with.hyphen.after.start.bracket@[-123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1656 - assertIsFalse "ip4.with.hyphen.before.end.bracket@[123.145.178.90-]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1657 - assertIsFalse "ip4.with.hyphen.after.end.bracket@[123.145.178.90]-"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1658 - assertIsFalse "ip4.with.empty.string1.between.numbers@[123.14\"\"5.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1659 - assertIsFalse "ip4.with.empty.string1.before.point@[123.145\"\".178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1660 - assertIsFalse "ip4.with.empty.string1.after.point@[123.145.\"\"178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1661 - assertIsFalse "ip4.with.empty.string1.before.start.bracket@\"\"[123.145.178.90]"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1662 - assertIsFalse "ip4.with.empty.string1.after.start.bracket@[\"\"123.145.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1663 - assertIsFalse "ip4.with.empty.string1.before.end.bracket@[123.145.178.90\"\"]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1664 - assertIsFalse "ip4.with.empty.string1.after.end.bracket@[123.145.178.90]\"\""              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1665 - assertIsFalse "ip4.with.empty.string2.between.numbers@[123.14a\"\"b5.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1666 - assertIsFalse "ip4.with.empty.string2.before.point@[123.145a\"\"b.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1667 - assertIsFalse "ip4.with.empty.string2.after.point@[123.145.a\"\"b178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1668 - assertIsFalse "ip4.with.empty.string2.before.start.bracket@a\"\"b[123.145.178.90]"         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1669 - assertIsFalse "ip4.with.empty.string2.after.start.bracket@[a\"\"b123.145.178.90]"          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1670 - assertIsFalse "ip4.with.empty.string2.before.end.bracket@[123.145.178.90a\"\"b]"           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1671 - assertIsFalse "ip4.with.empty.string2.after.end.bracket@[123.145.178.90]a\"\"b"            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1672 - assertIsFalse "ip4.with.double.empty.string1.between.numbers@[123.14\"\"\"\"5.178.90]"     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1673 - assertIsFalse "ip4.with.double.empty.string1.before.point@[123.145\"\"\"\".178.90]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1674 - assertIsFalse "ip4.with.double.empty.string1.after.point@[123.145.\"\"\"\"178.90]"         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1675 - assertIsFalse "ip4.with.double.empty.string1.before.start.bracket@\"\"\"\"[123.145.178.90]" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1676 - assertIsFalse "ip4.with.double.empty.string1.after.start.bracket@[\"\"\"\"123.145.178.90]" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1677 - assertIsFalse "ip4.with.double.empty.string1.before.end.bracket@[123.145.178.90\"\"\"\"]"  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1678 - assertIsFalse "ip4.with.double.empty.string1.after.end.bracket@[123.145.178.90]\"\"\"\""   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1679 - assertIsFalse "ip4.with.double.empty.string2.between.numbers@[123.14\"\".\"\"5.178.90]"    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1680 - assertIsFalse "ip4.with.double.empty.string2.before.point@[123.145\"\".\"\".178.90]"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1681 - assertIsFalse "ip4.with.double.empty.string2.after.point@[123.145.\"\".\"\"178.90]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1682 - assertIsFalse "ip4.with.double.empty.string2.before.start.bracket@\"\".\"\"[123.145.178.90]" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1683 - assertIsFalse "ip4.with.double.empty.string2.after.start.bracket@[\"\".\"\"123.145.178.90]" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1684 - assertIsFalse "ip4.with.double.empty.string2.before.end.bracket@[123.145.178.90\"\".\"\"]" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1685 - assertIsFalse "ip4.with.double.empty.string2.after.end.bracket@[123.145.178.90]\"\".\"\""  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1686 - assertIsFalse "ip4.with.number0.between.numbers@[123.1405.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1687 - assertIsFalse "ip4.with.number0.before.point@[123.1450.178.90]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1688 - assertIsFalse "ip4.with.number0.after.point@[123.145.0178.90]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1689 - assertIsFalse "ip4.with.number0.before.start.bracket@0[123.145.178.90]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1690 - assertIsFalse "ip4.with.number0.after.start.bracket@[0123.145.178.90]"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1691 - assertIsFalse "ip4.with.number0.before.end.bracket@[123.145.178.900]"                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1692 - assertIsFalse "ip4.with.number0.after.end.bracket@[123.145.178.90]0"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1693 - assertIsFalse "ip4.with.number9.between.numbers@[123.1495.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1694 - assertIsFalse "ip4.with.number9.before.point@[123.1459.178.90]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1695 - assertIsFalse "ip4.with.number9.after.point@[123.145.9178.90]"                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1696 - assertIsFalse "ip4.with.number9.before.start.bracket@9[123.145.178.90]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1697 - assertIsFalse "ip4.with.number9.after.start.bracket@[9123.145.178.90]"                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1698 - assertIsFalse "ip4.with.number9.before.end.bracket@[123.145.178.909]"                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1699 - assertIsFalse "ip4.with.number9.after.end.bracket@[123.145.178.90]9"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1700 - assertIsFalse "ip4.with.numbers.between.numbers@[123.1401234567895.178.90]"                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1701 - assertIsFalse "ip4.with.numbers.before.point@[123.1450123456789.178.90]"                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1702 - assertIsFalse "ip4.with.numbers.after.point@[123.145.0123456789178.90]"                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1703 - assertIsFalse "ip4.with.numbers.before.start.bracket@0123456789[123.145.178.90]"           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1704 - assertIsFalse "ip4.with.numbers.after.start.bracket@[0123456789123.145.178.90]"            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1705 - assertIsFalse "ip4.with.numbers.before.end.bracket@[123.145.178.900123456789]"             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1706 - assertIsFalse "ip4.with.numbers.after.end.bracket@[123.145.178.90]0123456789"              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1707 - assertIsFalse "ip4.with.byte.overflow.between.numbers@[123.149995.178.90]"                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1708 - assertIsFalse "ip4.with.byte.overflow.before.point@[123.145999.178.90]"                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1709 - assertIsFalse "ip4.with.byte.overflow.after.point@[123.145.999178.90]"                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1710 - assertIsFalse "ip4.with.byte.overflow.before.start.bracket@999[123.145.178.90]"            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1711 - assertIsFalse "ip4.with.byte.overflow.after.start.bracket@[999123.145.178.90]"             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1712 - assertIsFalse "ip4.with.byte.overflow.before.end.bracket@[123.145.178.90999]"              =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1713 - assertIsFalse "ip4.with.byte.overflow.after.end.bracket@[123.145.178.90]999"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1714 - assertIsFalse "ip4.with.no.hex.number.between.numbers@[123.14xyz5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1715 - assertIsFalse "ip4.with.no.hex.number.before.point@[123.145xyz.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1716 - assertIsFalse "ip4.with.no.hex.number.after.point@[123.145.xyz178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1717 - assertIsFalse "ip4.with.no.hex.number.before.start.bracket@xyz[123.145.178.90]"            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1718 - assertIsFalse "ip4.with.no.hex.number.after.start.bracket@[xyz123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1719 - assertIsFalse "ip4.with.no.hex.number.before.end.bracket@[123.145.178.90xyz]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1720 - assertIsFalse "ip4.with.no.hex.number.after.end.bracket@[123.145.178.90]xyz"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1721 - assertIsFalse "ip4.with.slash.between.numbers@[123.14\5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1722 - assertIsFalse "ip4.with.slash.before.point@[123.145\.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1723 - assertIsFalse "ip4.with.slash.after.point@[123.145.\178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1724 - assertIsFalse "ip4.with.slash.before.start.bracket@\[123.145.178.90]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1725 - assertIsFalse "ip4.with.slash.after.start.bracket@[\123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1726 - assertIsFalse "ip4.with.slash.before.end.bracket@[123.145.178.90\]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1727 - assertIsFalse "ip4.with.slash.after.end.bracket@[123.145.178.90]\"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1728 - assertIsFalse "ip4.with.string.between.numbers@[123.14\"str\"5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1729 - assertIsFalse "ip4.with.string.before.point@[123.145\"str\".178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1730 - assertIsFalse "ip4.with.string.after.point@[123.145.\"str\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1731 - assertIsFalse "ip4.with.string.before.start.bracket@\"str\"[123.145.178.90]"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1732 - assertIsFalse "ip4.with.string.after.start.bracket@[\"str\"123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1733 - assertIsFalse "ip4.with.string.before.end.bracket@[123.145.178.90\"str\"]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1734 - assertIsFalse "ip4.with.string.after.end.bracket@[123.145.178.90]\"str\""                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1735 - assertIsFalse "ip4.with.comment.between.numbers@[123.14(comment)5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1736 - assertIsFalse "ip4.with.comment.before.point@[123.145(comment).178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1737 - assertIsFalse "ip4.with.comment.after.point@[123.145.(comment)178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1738 - assertIsTrue  "ip4.with.comment.before.start.bracket@(comment)[123.145.178.90]"            =   2 =  OK 
     *  1739 - assertIsFalse "ip4.with.comment.after.start.bracket@[(comment)123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1740 - assertIsFalse "ip4.with.comment.before.end.bracket@[123.145.178.90(comment)]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1741 - assertIsTrue  "ip4.with.comment.after.end.bracket@[123.145.178.90](comment)"               =   2 =  OK 
     *  1742 - assertIsTrue  "email@[123.123.123.123]"                                                    =   2 =  OK 
     *  1743 - assertIsFalse "email@111.222.333"                                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1744 - assertIsFalse "email@111.222.333.256"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1745 - assertIsFalse "email@[123.123.123.123"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1746 - assertIsFalse "email@[123.123.123].123"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1747 - assertIsFalse "email@123.123.123.123]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1748 - assertIsFalse "email@123.123.[123.123]"                                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1749 - assertIsFalse "ab@988.120.150.10"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1750 - assertIsFalse "ab@120.256.256.120"                                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1751 - assertIsFalse "ab@120.25.1111.120"                                                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1752 - assertIsFalse "ab@[188.120.150.10"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1753 - assertIsFalse "ab@188.120.150.10]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1754 - assertIsFalse "ab@[188.120.150.10].com"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1755 - assertIsTrue  "ab@188.120.150.10"                                                          =   2 =  OK 
     *  1756 - assertIsTrue  "ab@1.0.0.10"                                                                =   2 =  OK 
     *  1757 - assertIsTrue  "ab@120.25.254.120"                                                          =   2 =  OK 
     *  1758 - assertIsTrue  "ab@01.120.150.1"                                                            =   2 =  OK 
     *  1759 - assertIsTrue  "ab@88.120.150.021"                                                          =   2 =  OK 
     *  1760 - assertIsTrue  "ab@88.120.150.01"                                                           =   2 =  OK 
     *  1761 - assertIsTrue  "email@123.123.123.123"                                                      =   2 =  OK 
     * 
     * ---- IP V6 -----------------------------------------------------------------------------------------------------------------------
     * 
     *  1762 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                                 =   4 =  OK 
     *  1763 - assertIsFalse "ABC.DEF@[IP"                                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1764 - assertIsFalse "ABC.DEF@[IPv6]"                                                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1765 - assertIsFalse "ABC.DEF@[IPv6:]"                                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1766 - assertIsFalse "ABC.DEF@[IPv6:"                                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1767 - assertIsFalse "ABC.DEF@[IPv6::]"                                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1768 - assertIsFalse "ABC.DEF@[IPv6::"                                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1769 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1770 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1771 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1772 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1773 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1774 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                       =   4 =  OK 
     *  1775 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                                     =   4 =  OK 
     *  1776 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                  =   4 =  OK 
     *  1777 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                                 =   4 =  OK 
     *  1778 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                 =   4 =  OK 
     *  1779 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                               =   4 =  OK 
     *  1780 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1781 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                             =   4 =  OK 
     *  1782 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1783 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1784 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                                   =   4 =  OK 
     *  1785 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1786 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1787 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1788 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1789 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1790 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                              =   4 =  OK 
     *  1791 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1792 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1793 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1794 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1795 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1796 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1797 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1798 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1799 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1800 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1801 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1802 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1803 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1804 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1805 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1806 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1807 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1808 - assertIsFalse "ip.v6.with.dot@[.IPv6:1:22:3:4:5:6:7]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1809 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:2..2:3:4:5:6:7]"                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1810 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22..:3:4:5:6:7]"                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1811 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:..3:4:5:6:7]"                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1812 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7..]"                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1813 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7].."                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1814 - assertIsFalse "ip.v6.with.double.dot@..[IPv6:1:22:3:4:5:6:7]"                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1815 - assertIsFalse "ip.v6.with.double.dot@[..IPv6:1:22:3:4:5:6:7]"                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1816 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1817 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1818 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1819 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1820 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1821 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1822 - assertIsFalse "ip.v6.with.amp@[&IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1823 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1824 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1825 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1826 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1827 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1828 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1829 - assertIsFalse "ip.v6.with.asterisk@[*IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1830 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1831 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1832 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1833 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1834 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1835 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1836 - assertIsFalse "ip.v6.with.underscore@[_IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1837 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1838 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1839 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1840 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1841 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1842 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1843 - assertIsFalse "ip.v6.with.dollar@[$IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1844 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1845 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1846 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1847 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1848 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1849 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1850 - assertIsFalse "ip.v6.with.equality@[=IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1851 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1852 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1853 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1854 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1855 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1856 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1857 - assertIsFalse "ip.v6.with.exclamation@[!IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1858 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1859 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1860 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1861 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1862 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1863 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1864 - assertIsFalse "ip.v6.with.question@[?IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1865 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1866 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1867 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1868 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1869 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1870 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1871 - assertIsFalse "ip.v6.with.grave-accent@[`IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1872 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1873 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1874 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1875 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1876 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1877 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1878 - assertIsFalse "ip.v6.with.hash@[#IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1879 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1880 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1881 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1882 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1883 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1884 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1885 - assertIsFalse "ip.v6.with.percentage@[%IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1886 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1887 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1888 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1889 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1890 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1891 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1892 - assertIsFalse "ip.v6.with.pipe@[|IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1893 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1894 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1895 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1896 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1897 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1898 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1899 - assertIsFalse "ip.v6.with.plus@[+IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1900 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1901 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1902 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1903 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1904 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1905 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1906 - assertIsFalse "ip.v6.with.leftbracket@[{IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1907 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1908 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1909 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1910 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1911 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1912 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1913 - assertIsFalse "ip.v6.with.rightbracket@[}IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1914 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1915 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1916 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1917 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1918 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                              =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1919 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1920 - assertIsFalse "ip.v6.with.leftbracket@[(IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1921 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1922 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1923 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1924 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1925 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1926 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1927 - assertIsFalse "ip.v6.with.rightbracket@[)IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1928 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1929 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1930 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1931 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1932 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1933 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1934 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1935 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1936 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1937 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1938 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1939 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1940 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1941 - assertIsFalse "ip.v6.with.rightbracket@[]IPv6:1:22:3:4:5:6:7]"                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1942 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1943 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1944 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1945 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1946 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                           =   4 =  OK 
     *  1947 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                           =   4 =  OK 
     *  1948 - assertIsFalse "ip.v6.with.empty.bracket@[()IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1949 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1950 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1951 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1952 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1953 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1954 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1955 - assertIsFalse "ip.v6.with.empty.bracket@[{}IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1956 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1957 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1958 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1959 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1960 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1961 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1962 - assertIsFalse "ip.v6.with.empty.bracket@[[]IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1963 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1964 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1965 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1966 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1967 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1968 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1969 - assertIsFalse "ip.v6.with.empty.bracket@[<>IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1970 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1971 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1972 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1973 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1974 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1975 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1976 - assertIsFalse "ip.v6.with.false.bracket1@[)(IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1977 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1978 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1979 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1980 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1981 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1982 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1983 - assertIsFalse "ip.v6.with.false.bracket2@[}{IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1984 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1985 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1986 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1987 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1988 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1989 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1990 - assertIsFalse "ip.v6.with.false.bracket4@[><IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1991 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1992 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1993 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1994 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1995 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1996 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1997 - assertIsFalse "ip.v6.with.lower.than@[<IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1998 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1999 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2000 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2001 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2002 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                             =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2003 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2004 - assertIsFalse "ip.v6.with.greater.than@[>IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2005 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2006 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2007 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2008 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2009 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2010 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2011 - assertIsFalse "ip.v6.with.tilde@[~IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2012 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2013 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2014 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2015 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2016 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2017 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2018 - assertIsFalse "ip.v6.with.xor@[^IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2019 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                                    =   4 =  OK 
     *  2020 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                    =   4 =  OK 
     *  2021 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                    =   4 =  OK 
     *  2022 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                                    =   4 =  OK 
     *  2023 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2024 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2025 - assertIsFalse "ip.v6.with.colon@[:IPv6:1:22:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2026 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2027 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2028 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2029 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2030 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2031 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2032 - assertIsFalse "ip.v6.with.space@[ IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2033 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2034 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2035 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2036 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2037 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2038 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2039 - assertIsFalse "ip.v6.with.comma@[,IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2040 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2041 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2042 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2043 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2044 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2045 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2046 - assertIsFalse "ip.v6.with.at@[@IPv6:1:22:3:4:5:6:7]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2047 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2048 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2049 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2050 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2051 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2052 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2053 - assertIsFalse "ip.v6.with.paragraph@[§IPv6:1:22:3:4:5:6:7]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2054 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2055 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2056 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2057 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2058 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2059 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2060 - assertIsFalse "ip.v6.with.double.quote@['IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2061 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2062 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2063 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2064 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2065 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                            =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2066 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2067 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2068 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2069 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2070 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2071 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2072 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2073 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2074 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2075 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2076 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2077 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2078 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2079 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2080 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2081 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2082 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2083 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2084 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2085 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2086 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2087 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2088 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2089 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2090 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2091 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2092 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2093 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2094 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2095 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2096 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2097 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2098 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2099 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2100 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2101 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2102 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2103 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:202:3:4:5:6:7]"                                  =   4 =  OK 
     *  2104 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:220:3:4:5:6:7]"                                  =   4 =  OK 
     *  2105 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:03:4:5:6:7]"                                  =   4 =  OK 
     *  2106 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:70]"                                  =   4 =  OK 
     *  2107 - assertIsFalse "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:7]0"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2108 - assertIsFalse "ip.v6.with.number0@0[IPv6:1:22:3:4:5:6:7]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2109 - assertIsFalse "ip.v6.with.number0@[0IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2110 - assertIsFalse "ip.v6.with.number9@[IPv6:1:292:3:4:5:6:7]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2111 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:229:3:4:5:6:7]"                                  =   4 =  OK 
     *  2112 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:93:4:5:6:7]"                                  =   4 =  OK 
     *  2113 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:79]"                                  =   4 =  OK 
     *  2114 - assertIsFalse "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:7]9"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2115 - assertIsFalse "ip.v6.with.number9@9[IPv6:1:22:3:4:5:6:7]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2116 - assertIsFalse "ip.v6.with.number9@[9IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2117 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2118 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2119 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2120 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2121 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2122 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2123 - assertIsFalse "ip.v6.with.numbers@[0123456789IPv6:1:22:3:4:5:6:7]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2124 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                          =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2125 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                          =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2126 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]"                          =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2127 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7999]"                          =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2128 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]999"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2129 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2130 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2131 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2132 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2133 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2134 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2135 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2136 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2137 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2138 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2139 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2140 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2141 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2142 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2143 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2144 - assertIsFalse "ip.v6.with.slash@[\IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2145 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2146 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2147 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2148 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2149 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2150 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2151 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2152 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2153 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2154 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2155 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2156 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                          =   4 =  OK 
     *  2157 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                          =   4 =  OK 
     *  2158 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2159 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"                     =   4 =  OK 
     *  2160 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"                     =   4 =  OK 
     *  2161 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"                     =   4 =  OK 
     *  2162 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                                    =   4 =  OK 
     *  2163 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                                     =   4 =  OK 
     *  2164 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2165 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2166 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2167 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 -----------------------------------------------------------------------------------------------------
     * 
     *  2168 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                             =   4 =  OK 
     *  2169 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                             =   4 =  OK 
     *  2170 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                                 =   4 =  OK 
     *  2171 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                                 =   4 =  OK 
     *  2172 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2173 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2174 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2175 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2176 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2177 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2178 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2179 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                           =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2180 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.0001]"                                          =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2181 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- ip4 without brackets --------------------------------------------------------------------------------------------------------
     * 
     *  2182 - assertIsTrue  "ip4.without.brackets.ok1@127.0.0.1"                                         =   2 =  OK 
     *  2183 - assertIsTrue  "ip4.without.brackets.ok2@0.0.0.0"                                           =   2 =  OK 
     *  2184 - assertIsFalse "ip4.without.brackets.but.with.space.at.end@127.0.0.1 "                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2185 - assertIsFalse "ip4.without.brackets.byte.overflow@127.0.999.1"                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2186 - assertIsFalse "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1"                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2187 - assertIsFalse "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1"                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2188 - assertIsFalse "ip4.without.brackets.negative.number@127.0.-1.1"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2189 - assertIsFalse "ip4.without.brackets.point.error1@127.0..0.1"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2190 - assertIsFalse "ip4.without.brackets.point.error1@127...1"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2191 - assertIsFalse "ip4.without.brackets.error.bracket@127.0.1.1[]"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2192 - assertIsFalse "ip4.without.brackets.point.error2@127001"                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2193 - assertIsFalse "ip4.without.brackets.point.error3@127.0.0."                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2194 - assertIsFalse "ip4.without.brackets.character.error@127.0.A.1"                             =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2195 - assertIsFalse "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1"                  =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2196 - assertIsTrue  "ip4.without.brackets.normal.tld1@127.0.0.1.com"                             =   0 =  OK 
     *  2197 - assertIsTrue  "ip4.without.brackets.normal.tld2@127.0.99.1.com"                            =   0 =  OK 
     *  2198 - assertIsTrue  "ip4.without.brackets.normal.tld3@127.0.A.1.com"                             =   0 =  OK 
     * 
     * ---- Strings ---------------------------------------------------------------------------------------------------------------------
     * 
     *  2199 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                         =   1 =  OK 
     *  2200 - assertIsTrue  "\"ABC\".\"DEF\"@GHI.DE"                                                     =   1 =  OK 
     *  2201 - assertIsFalse "-\"ABC\".\"DEF\"@GHI.DE"                                                    = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
     *  2202 - assertIsFalse "\"ABC\"-.\"DEF\"@GHI.DE"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2203 - assertIsFalse "\"ABC\".-\"DEF\"@GHI.DE"                                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2204 - assertIsFalse ".\"ABC\".\"DEF\"@GHI.DE"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2205 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                         =   1 =  OK 
     *  2206 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                         =   1 =  OK 
     *  2207 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                       =   1 =  OK 
     *  2208 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2209 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2210 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2211 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2212 - assertIsFalse "\"\"@GHI.DE"                                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2213 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2214 - assertIsFalse "A@G\"HI.DE"                                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2215 - assertIsFalse "\"@GHI.DE"                                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2216 - assertIsFalse "ABC.DEF.\""                                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2217 - assertIsFalse "ABC.DEF@\"\""                                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2218 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2219 - assertIsFalse "ABC.DEF@GHI.DE\""                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2220 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2221 - assertIsFalse "\"Escape.Sequenz.Ende\""                                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2222 - assertIsFalse "ABC.DEF\"GHI.DE"                                                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2223 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2224 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2225 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2226 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                         =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2227 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2228 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                        =   1 =  OK 
     *  2229 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                                 =   1 =  OK 
     *  2230 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2231 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2232 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2233 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2234 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2235 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2236 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2237 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2238 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                                     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2239 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2240 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2241 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2242 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2243 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2244 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2245 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2246 - assertIsFalse "\"\".ABC.DEF@GHI.DE"                                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2247 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2248 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                          =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2249 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                       =   1 =  OK 
     *  2250 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                                     =   1 =  OK 
     *  2251 - assertIsTrue  "\"ABC.\\".\\".DEF\"@GHI.DE"                                                 =   1 =  OK 
     *  2252 - assertIsTrue  "\"ABC.\\"\\".DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  2253 - assertIsTrue  "\"ABC.\\" \@ \\".DEF\"@GHI.DE"                                              =   1 =  OK 
     *  2254 - assertIsFalse "\"Ende.am.Eingabeende\""                                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2255 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2256 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                       =   1 =  OK 
     *  2257 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2258 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2259 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2260 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                      =   1 =  OK 
     *  2261 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                              =   1 =  OK 
     *  2262 - assertIsFalse "\"Joe Smith\" email@domain.com"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2263 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2264 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments --------------------------------------------------------------------------------------------------------------------
     * 
     *  2265 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                           =   6 =  OK 
     *  2266 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                          =   6 =  OK 
     *  2267 - assertIsFalse "ABC.DEF(GHI)    @JKL.MNO"                                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2268 - assertIsFalse "(ABC) (DEF)  GHI@JKL.MNO"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2269 - assertIsFalse "(ABC) (DEF).GHI@JKL.MNO"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2270 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                           =   6 =  OK 
     *  2271 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                       =   6 =  OK 
     *  2272 - assertIsFalse "ABC.DEF@GHI.JKL(MNO)    "                                                   = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2273 - assertIsTrue  "ABC.DEF@(GHI)JKL.MNO"                                                       =   6 =  OK 
     *  2274 - assertIsTrue  "ABC.DEF@(GHI.@.)JKL.MNO"                                                    =   6 =  OK 
     *  2275 - assertIsFalse "ABC.DEF@(GHI\@)JKL.MNO"                                                     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2276 - assertIsFalse "ABC.DEF@(GHI)   JKL.MNO"                                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2277 - assertIsFalse "ABC.DEF@(GHI.)   JKL.MNO"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2278 - assertIsFalse "ABC.DEF@(GHI.) (ABC)JKL.MNO"                                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2279 - assertIsFalse "ABC.DEF@(GHI.)   JKL(MNO)"                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2280 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                                 =   6 =  OK 
     *  2281 - assertIsFalse "ABC.DEF@             (MNO)"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2282 - assertIsFalse "ABC.DEF@   .         (MNO)"                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2283 - assertIsFalse "ABC.DEF              (MNO)"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2284 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2285 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2286 - assertIsFalse "ABC.DEF@GHI.JKL          "                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2287 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2288 - assertIsFalse "("                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2289 - assertIsFalse ")"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2290 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                         =   6 =  OK 
     *  2291 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                          =   6 =  OK 
     *  2292 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                          =   6 =  OK 
     *  2293 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                          =   6 =  OK 
     *  2294 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                          =   6 =  OK 
     *  2295 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2296 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2297 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2298 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2299 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2300 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2301 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2302 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2303 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                          = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2304 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2305 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                      = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  2306 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2307 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2308 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2309 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                           =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  2310 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2311 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2312 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                          =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2313 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                         =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2314 - assertIsFalse "ABC(DEF@GHI.JKL"                                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2315 - assertIsFalse "ABC.DEF@GHI)JKL"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2316 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2317 - assertIsFalse "ABC.DEF)@GHI.JKL"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2318 - assertIsFalse "ABC(DEF@GHI).JKL"                                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2319 - assertIsFalse "ABC(DEF.GHI).JKL"                                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2320 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                          =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2321 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2322 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2323 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2324 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2325 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2326 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                                 =   2 =  OK 
     *  2327 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                                = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  2328 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                                 =   2 =  OK 
     *  2329 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                             =   2 =  OK 
     *  2330 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2331 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                     =   4 =  OK 
     *  2332 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                     =   4 =  OK 
     *  2333 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                 =   4 =  OK 
     *  2334 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2335 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                                  =   6 =  OK 
     *  2336 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                                  =   6 =  OK 
     *  2337 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                                  = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2338 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2339 - assertIsTrue  "(comment)john.smith@example.com"                                            =   6 =  OK 
     *  2340 - assertIsTrue  "john.smith(comment)@example.com"                                            =   6 =  OK 
     *  2341 - assertIsTrue  "john.smith@(comment)example.com"                                            =   6 =  OK 
     *  2342 - assertIsTrue  "john.smith@example.com(comment)"                                            =   6 =  OK 
     *  2343 - assertIsFalse "john.smith@exampl(comment)e.com"                                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2344 - assertIsFalse "john.s(comment)mith@example.com"                                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2345 - assertIsFalse "john.smith(comment)@(comment)example.com"                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2346 - assertIsFalse "john.smith(com@ment)example.com"                                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2347 - assertIsFalse "email( (nested) )@plus.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2348 - assertIsFalse "email)mirror(@plus.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2349 - assertIsFalse "email@plus.com (not closed comment"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2350 - assertIsFalse "email(with @ in comment)plus.com"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2351 - assertIsTrue  "email@domain.com (joe Smith)"                                               =   6 =  OK 
     *  2352 - assertIsFalse "a@abc(bananas)def.com"                                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2353 - assertIsTrue  "\"address(comment\"@example.com"                                            =   1 =  OK 
     *  2354 - assertIsFalse "address(co\"mm\"ent)@example.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2355 - assertIsFalse "address(co\"mment)@example.com"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     * 
     * ---- Pointy Brackets -------------------------------------------------------------------------------------------------------------
     * 
     *  2356 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                                  =   0 =  OK 
     *  2357 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                                  =   0 =  OK 
     *  2358 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                                   =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2359 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  2360 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                              =   0 =  OK 
     *  2361 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                        =   1 =  OK 
     *  2362 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                                =   1 =  OK 
     *  2363 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2364 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2365 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2366 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2367 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2368 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2369 - assertIsFalse "ABC DEF <A@A>"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2370 - assertIsFalse "<A@A> ABC DEF"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2371 - assertIsFalse "ABC DEF <>"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2372 - assertIsFalse "<> ABC DEF"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2373 - assertIsFalse "<"                                                                          =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  2374 - assertIsFalse ">"                                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2375 - assertIsFalse "<         >"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2376 - assertIsFalse "< <     > >"                                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2377 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                          =   0 =  OK 
     *  2378 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                         = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2379 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2380 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                         =   0 =  OK 
     *  2381 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2382 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                         =   0 =  OK 
     *  2383 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2384 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                                 =   6 =  OK 
     *  2385 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2386 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                                = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2387 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                                =   6 =  OK 
     *  2388 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                                = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2389 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                                =   6 =  OK 
     *  2390 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                                = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2391 - assertIsTrue  "Joe Smith <email@domain.com>"                                               =   0 =  OK 
     *  2392 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2393 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2394 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"            =   9 =  OK 
     *  2395 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"            =   9 =  OK 
     *  2396 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"            =   9 =  OK 
     *  2397 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "           =   9 =  OK 
     *  2398 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2399 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2400 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2401 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2402 - assertIsFalse "Test |<gaaf <email@domain.com>"                                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2403 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2404 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2405 - assertIsFalse "<null>@mail.com"                                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2406 - assertIsFalse "email.adress@domain.com <display name>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2407 - assertIsFalse "eimail.adress@domain.com <eimail.adress@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2408 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2409 - assertIsFalse "<eimail>.<adress>@domain.com"                                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2410 - assertIsFalse "<eimail>.<adress> email.adress@domain.com"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------------------------
     * 
     *  2411 - assertIsTrue  "A@B.CD"                                                                     =   0 =  OK 
     *  2412 - assertIsFalse "A@B.C"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2413 - assertIsFalse "A@COM"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2414 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                            =   0 =  OK 
     *  2415 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  2416 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  2417 - assertIsTrue  "A@B.CD"                                                                     =   0 =  OK 
     *  2418 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  2419 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2420 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2421 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2422 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld> True64 " =   0 =  OK 
     *  2423 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2424 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2425 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2426 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"      =   0 =  OK 
     *  2427 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  2428 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2429 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2430 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2431 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2432 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2433 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2434 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2435 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2436 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2437 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2438 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2439 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2440 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
     *  2441 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  2442 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  2443 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2444 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2445 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  2446 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2447 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2448 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2449 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
     *  2450 - assertIsTrue  "email@domain.topleveldomain"                                                =   0 =  OK 
     *  2451 - assertIsTrue  "email@email.email.mydomain"                                                 =   0 =  OK 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ --------------------------------------------------------------------------------
     * 
     *  2452 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                           =   6 =  OK 
     *  2453 - assertIsTrue  "\"MaxMustermann\"@example.com"                                              =   1 =  OK 
     *  2454 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                                 =   1 =  OK 
     *  2455 - assertIsTrue  "\".John.Doe\"@example.com"                                                  =   1 =  OK 
     *  2456 - assertIsTrue  "\"John.Doe.\"@example.com"                                                  =   1 =  OK 
     *  2457 - assertIsTrue  "\"John..Doe\"@example.com"                                                  =   1 =  OK 
     *  2458 - assertIsTrue  "john.smith(comment)@example.com"                                            =   6 =  OK 
     *  2459 - assertIsTrue  "(comment)john.smith@example.com"                                            =   6 =  OK 
     *  2460 - assertIsTrue  "john.smith@(comment)example.com"                                            =   6 =  OK 
     *  2461 - assertIsTrue  "john.smith@example.com(comment)"                                            =   6 =  OK 
     *  2462 - assertIsTrue  "john.smith@example.com"                                                     =   0 =  OK 
     *  2463 - assertIsTrue  "joeuser+tag@example.com"                                                    =   0 =  OK 
     *  2464 - assertIsTrue  "jsmith@[192.168.2.1]"                                                       =   2 =  OK 
     *  2465 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                                  =   4 =  OK 
     *  2466 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                          =   0 =  OK 
     *  2467 - assertIsTrue  "Marc Dupont <md118@example.com>"                                            =   0 =  OK 
     *  2468 - assertIsTrue  "simple@example.com"                                                         =   0 =  OK 
     *  2469 - assertIsTrue  "very.common@example.com"                                                    =   0 =  OK 
     *  2470 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                             =   0 =  OK 
     *  2471 - assertIsTrue  "other.email-with-hyphen@example.com"                                        =   0 =  OK 
     *  2472 - assertIsTrue  "fully-qualified-domain@example.com"                                         =   0 =  OK 
     *  2473 - assertIsTrue  "user.name+tag+sorting@example.com"                                          =   0 =  OK 
     *  2474 - assertIsTrue  "user+mailbox/department=shipping@example.com"                               =   0 =  OK 
     *  2475 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                           =   0 =  OK 
     *  2476 - assertIsTrue  "x@example.com"                                                              =   0 =  OK 
     *  2477 - assertIsTrue  "info@firma.org"                                                             =   0 =  OK 
     *  2478 - assertIsTrue  "example-indeed@strange-example.com"                                         =   0 =  OK 
     *  2479 - assertIsTrue  "admin@mailserver1"                                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2480 - assertIsTrue  "example@s.example"                                                          =   0 =  OK 
     *  2481 - assertIsTrue  "\" \"@example.org"                                                          =   1 =  OK 
     *  2482 - assertIsTrue  "mailhost!username@example.org"                                              =   0 =  OK 
     *  2483 - assertIsTrue  "user%example.com@example.org"                                               =   0 =  OK 
     *  2484 - assertIsTrue  "joe25317@NOSPAMexample.com"                                                 =   0 =  OK 
     *  2485 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                                 =   0 =  OK 
     *  2486 - assertIsTrue  "nama@contoh.com"                                                            =   0 =  OK 
     *  2487 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                                 =   0 =  OK 
     *  2488 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2489 - assertIsFalse "Abc.example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2490 - assertIsFalse "Abc..123@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2491 - assertIsFalse "A@b@c@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2492 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2493 - assertIsFalse "just\"not\"right@example.com"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2494 - assertIsFalse "this is\"not\allowed@example.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2495 - assertIsFalse "this\ still\\"not\\allowed@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2496 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2497 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"         =   0 =  OK 
     *  2498 - assertIsTrue  "(buero)office@example.com"                                                  =   6 =  OK 
     *  2499 - assertIsTrue  "office(etage-3)@example.com"                                                =   6 =  OK 
     *  2500 - assertIsFalse "off(kommentar)ice@example.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2501 - assertIsTrue  "\"(buero)office\"@example.com"                                              =   1 =  OK 
     *  2502 - assertIsTrue  "\"office(etage-3)\"@example.com"                                            =   1 =  OK 
     *  2503 - assertIsTrue  "\"off(kommentar)ice\"@example.com"                                          =   1 =  OK 
     *  2504 - assertIsTrue  "\"address(comment)\"@example.com"                                           =   1 =  OK 
     *  2505 - assertIsTrue  "Buero <office@example.com>"                                                 =   0 =  OK 
     *  2506 - assertIsTrue  "\"vorname(Kommentar).nachname\"@provider.de"                                =   1 =  OK 
     *  2507 - assertIsTrue  "\"Herr \\"Kaiser\\" Franz Beckenbauer\" <local-part@domain-part.com>"       =   0 =  OK 
     *  2508 - assertIsTrue  "Erwin Empfaenger <erwin@example.com>"                                       =   0 =  OK 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ---------------------------------------------------------------------------------
     * 
     *  2509 - assertIsFalse "nolocalpart.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2510 - assertIsFalse "test@example.com test"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2511 - assertIsFalse "user  name@example.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2512 - assertIsFalse "user   name@example.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2513 - assertIsFalse "example.@example.co.uk"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2514 - assertIsFalse "example@example@example.co.uk"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2515 - assertIsFalse "(test_exampel@example.fr}"                                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2516 - assertIsFalse "example(example)example@example.co.uk"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2517 - assertIsFalse ".example@localhost"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2518 - assertIsFalse "ex\ample@localhost"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2519 - assertIsFalse "example@local\host"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2520 - assertIsFalse "example@localhost."                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2521 - assertIsFalse "user name@example.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2522 - assertIsFalse "username@ example . com"                                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2523 - assertIsFalse "example@(fake}.com"                                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2524 - assertIsFalse "example@(fake.com"                                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2525 - assertIsTrue  "username@example.com"                                                       =   0 =  OK 
     *  2526 - assertIsTrue  "usern.ame@example.com"                                                      =   0 =  OK 
     *  2527 - assertIsFalse "user[na]me@example.com"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2528 - assertIsFalse "\"\"\"@iana.org"                                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2529 - assertIsFalse "\"\\"@iana.org"                                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2530 - assertIsFalse "\"test\"test@iana.org"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2531 - assertIsFalse "\"test\"\"test\"@iana.org"                                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2532 - assertIsTrue  "\"test\".\"test\"@iana.org"                                                 =   1 =  OK 
     *  2533 - assertIsTrue  "\"test\".test@iana.org"                                                     =   1 =  OK 
     *  2534 - assertIsFalse "\"test\\"@iana.org"                                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2535 - assertIsFalse "\r\ntest@iana.org"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2536 - assertIsFalse "\r\n test@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2537 - assertIsFalse "\r\n \r\ntest@iana.org"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2538 - assertIsFalse "\r\n \r\n test@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2539 - assertIsFalse "test@iana.org \r\n"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2540 - assertIsFalse "test@iana.org \r\n "                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2541 - assertIsFalse "test@iana.org \r\n \r\n"                                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2542 - assertIsFalse "test@iana.org \r\n\r\n"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2543 - assertIsFalse "test@iana.org  \r\n\r\n "                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2544 - assertIsFalse "test@iana/icann.org"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2545 - assertIsFalse "test@foo;bar.com"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2546 - assertIsFalse "a@test.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2547 - assertIsFalse "comment)example@example.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2548 - assertIsFalse "comment(example))@example.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2549 - assertIsFalse "example@example)comment.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2550 - assertIsFalse "example@example(comment)).com"                                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2551 - assertIsFalse "example@[1.2.3.4"                                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2552 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2553 - assertIsFalse "exam(ple@exam).ple"                                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2554 - assertIsFalse "example@(example))comment.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2555 - assertIsTrue  "example@example.com"                                                        =   0 =  OK 
     *  2556 - assertIsTrue  "example@example.co.uk"                                                      =   0 =  OK 
     *  2557 - assertIsTrue  "example_underscore@example.fr"                                              =   0 =  OK 
     *  2558 - assertIsTrue  "exam'ple@example.com"                                                       =   0 =  OK 
     *  2559 - assertIsTrue  "exam\ ple@example.com"                                                      =   0 =  OK 
     *  2560 - assertIsFalse "example((example))@fakedfake.co.uk"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2561 - assertIsFalse "example@faked(fake).co.uk"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2562 - assertIsTrue  "example+@example.com"                                                       =   0 =  OK 
     *  2563 - assertIsTrue  "example@with-hyphen.example.com"                                            =   0 =  OK 
     *  2564 - assertIsTrue  "with-hyphen@example.com"                                                    =   0 =  OK 
     *  2565 - assertIsTrue  "example@1leadingnum.example.com"                                            =   0 =  OK 
     *  2566 - assertIsTrue  "1leadingnum@example.com"                                                    =   0 =  OK 
     *  2567 - assertIsTrue  "инфо@письмо.рф"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2568 - assertIsTrue  "\"username\"@example.com"                                                   =   1 =  OK 
     *  2569 - assertIsTrue  "\"user.name\"@example.com"                                                  =   1 =  OK 
     *  2570 - assertIsTrue  "\"user name\"@example.com"                                                  =   1 =  OK 
     *  2571 - assertIsTrue  "\"user@name\"@example.com"                                                  =   1 =  OK 
     *  2572 - assertIsFalse "\"\a\"@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2573 - assertIsTrue  "\"test\ test\"@iana.org"                                                    =   1 =  OK 
     *  2574 - assertIsFalse "\"\"@iana.org"                                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2575 - assertIsFalse "\"\"@[]"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2576 - assertIsTrue  "\"\\"\"@iana.org"                                                           =   1 =  OK 
     *  2577 - assertIsTrue  "example@localhost"                                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- unsorted from the WEB -------------------------------------------------------------------------------------------------------
     * 
     *  2578 - assertIsFalse "<')))><@fish.left.com"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2579 - assertIsFalse "><(((*>@fish.right.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2580 - assertIsFalse " check@this.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2581 - assertIsFalse " email@example.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2582 - assertIsFalse ".....@a...."                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2583 - assertIsFalse "..@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2584 - assertIsFalse "..@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2585 - assertIsTrue  "\"test....\"@gmail.com"                                                     =   1 =  OK 
     *  2586 - assertIsFalse "test....@gmail.com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2587 - assertIsTrue  "name@xn--4ca9at.at"                                                         =   0 =  OK 
     *  2588 - assertIsTrue  "simon-@hotmail.com"                                                         =   0 =  OK 
     *  2589 - assertIsTrue  "!@mydomain.net"                                                             =   0 =  OK 
     *  2590 - assertIsTrue  "sean.o'leary@cobbcounty.org"                                                =   0 =  OK 
     *  2591 - assertIsTrue  "xjhgjg876896@domain.com"                                                    =   0 =  OK 
     *  2592 - assertIsTrue  "Tony Snow <tony@example.com>"                                               =   0 =  OK 
     *  2593 - assertIsTrue  "(tony snow) tony@example.com"                                               =   6 =  OK 
     *  2594 - assertIsTrue  "tony%example.com@example.org"                                               =   0 =  OK 
     *  2595 - assertIsFalse "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2596 - assertIsFalse "a-b'c_d.e@f-g.h"                                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2597 - assertIsFalse "a-b'c_d.@f-g.h"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2598 - assertIsFalse "a-b'c_d.e@f-.h"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2599 - assertIsTrue  "\"root\"@example.com"                                                       =   1 =  OK 
     *  2600 - assertIsTrue  "root@example.com"                                                           =   0 =  OK 
     *  2601 - assertIsFalse ".@s.dd"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2602 - assertIsFalse ".@s.dd"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2603 - assertIsFalse ".a@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2604 - assertIsFalse ".a@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2605 - assertIsFalse ".abc@somedomain.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2606 - assertIsFalse ".dot@example.com"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2607 - assertIsFalse ".email@domain.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2608 - assertIsFalse ".journaldev@journaldev.com"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2609 - assertIsFalse ".username@yahoo.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2610 - assertIsFalse ".username@yahoo.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2611 - assertIsFalse ".xxxx@mysite.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2612 - assertIsFalse "asdf@asdf"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2613 - assertIsFalse "123@$.xyz"                                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2614 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2615 - assertIsFalse "@%^%#$@#$@#.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2616 - assertIsFalse "@b.com"                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2617 - assertIsFalse "@domain.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2618 - assertIsFalse "@example.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2619 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2620 - assertIsFalse "@yahoo.com"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2621 - assertIsFalse "@you.me.net"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2622 - assertIsFalse "A@b@c@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2623 - assertIsFalse "Abc.example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2624 - assertIsFalse "Abc@example.com."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2625 - assertIsFalse "Display Name <email@plus.com> (after name with display)"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2626 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2627 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2628 - assertIsFalse "Foobar Some@thing.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2629 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2630 - assertIsFalse "MailTo:casesensitve@domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2631 - assertIsFalse "No -foo@bar.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2632 - assertIsFalse "No asd@-bar.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2633 - assertIsFalse "DomainHyphen@-atstart"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2634 - assertIsFalse "DomainHyphen@atend-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2635 - assertIsFalse "DomainHyphen@bb.-cc"                                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2636 - assertIsFalse "DomainHyphen@bb.-cc-"                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2637 - assertIsFalse "DomainHyphen@bb.cc-"                                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2638 - assertIsFalse "DomainHyphen@bb.c-c"                                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2639 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2640 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                          =   0 =  OK 
     *  2641 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2642 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2643 - assertIsFalse "DomainNotAllowedCharacter@example'"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2644 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2645 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                       =   0 =  OK 
     *  2646 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                         =   0 =  OK 
     *  2647 - assertIsFalse "tld.starts.with.digit@domain.2com"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2648 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                            =   0 =  OK 
     *  2649 - assertIsFalse "email@=qowaiv.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2650 - assertIsFalse "email@plus+.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2651 - assertIsFalse "email@domain.com>"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2652 - assertIsFalse "email@mailto:domain.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2653 - assertIsFalse "mailto:mailto:email@domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2654 - assertIsFalse "email@-domain.com"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2655 - assertIsFalse "email@domain-.com"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2656 - assertIsFalse "email@domain.com-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2657 - assertIsFalse "email@{leftbracket.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2658 - assertIsFalse "email@rightbracket}.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2659 - assertIsFalse "email@pp|e.com"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2660 - assertIsTrue  "email@domain.domain.domain.com.com"                                         =   0 =  OK 
     *  2661 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                                  =   0 =  OK 
     *  2662 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                           =   0 =  OK 
     *  2663 - assertIsFalse "unescaped white space@fake$com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2664 - assertIsFalse "\"Joe Smith email@domain.com"                                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2665 - assertIsFalse "\"Joe Smith' email@domain.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2666 - assertIsFalse "\"Joe Smith\"email@domain.com"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2667 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2668 - assertIsTrue  "{john'doe}@my.server"                                                       =   0 =  OK 
     *  2669 - assertIsTrue  "email@domain-one.com"                                                       =   0 =  OK 
     *  2670 - assertIsTrue  "_______@domain.com"                                                         =   0 =  OK 
     *  2671 - assertIsTrue  "?????@domain.com"                                                           =   0 =  OK 
     *  2672 - assertIsFalse "local@?????.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2673 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                              =   1 =  OK 
     *  2674 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                                     =   1 =  OK 
     *  2675 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                  =   0 =  OK 
     *  2676 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                           =   0 =  OK 
     *  2677 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2678 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"                    =   0 =  OK 
     *  2679 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2680 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                        =   1 =  OK 
     *  2681 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2682 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2683 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2684 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2685 - assertIsFalse "\"Joe\Blow\"@example.com"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2686 - assertIsFalse "\"\"Joe Smith email@domain.com"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2687 - assertIsFalse "\"\"Joe Smith' email@domain.com"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2688 - assertIsFalse "\"\"Joe Smith\"\"email@domain.com"                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2689 - assertIsFalse "\"qu@example.com"                                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2690 - assertIsFalse "\$A12345@example.com"                                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2691 - assertIsFalse "_@bde.cc,"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2692 - assertIsFalse "a..b@bde.cc"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2693 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2694 - assertIsFalse "a.b@example,co.de"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2695 - assertIsFalse "a.b@example,com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2696 - assertIsFalse "a>b@somedomain.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2697 - assertIsFalse "a@.com"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2698 - assertIsFalse "a@b."                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2699 - assertIsFalse "a@b.-de.cc"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2700 - assertIsFalse "a@b._de.cc"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2701 - assertIsFalse "a@bde-.cc"                                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2702 - assertIsFalse "a@bde.c-c"                                                                  =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2703 - assertIsFalse "a@bde.cc."                                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2704 - assertIsFalse "a@bde_.cc"                                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2705 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2706 - assertIsFalse "ab@120.25.1111.120"                                                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2707 - assertIsFalse "ab@120.256.256.120"                                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2708 - assertIsFalse "ab@188.120.150.10]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2709 - assertIsFalse "ab@988.120.150.10"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2710 - assertIsFalse "ab@[188.120.150.10"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2711 - assertIsFalse "ab@[188.120.150.10].com"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2712 - assertIsFalse "ab@b+de.cc"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2713 - assertIsFalse "ab@sd@dd"                                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2714 - assertIsFalse "abc.@somedomain.com"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2715 - assertIsFalse "abc@def@example.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2716 - assertIsFalse "abc@gmail..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2717 - assertIsFalse "abc@gmail.com.."                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2718 - assertIsFalse "abc\"defghi\"xyz@example.com"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2719 - assertIsFalse "abc\@example.com"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2720 - assertIsFalse "abc\\"def\\"ghi@example.com"                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2721 - assertIsFalse "abc\\@def@example.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2722 - assertIsFalse "as3d@dac.coas-"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2723 - assertIsFalse "asd@dasd@asd.cm"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2724 - assertIsFalse "check@this..com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2725 - assertIsFalse "check@thiscom"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2726 - assertIsFalse "da23@das..com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2727 - assertIsFalse "dad@sds"                                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2728 - assertIsFalse "dasddas-@.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2729 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2730 - assertIsFalse "dot.@example.com"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2731 - assertIsFalse "doug@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2732 - assertIsFalse "email( (nested) )@plus.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2733 - assertIsFalse "email(with @ in comment)plus.com"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2734 - assertIsFalse "email)mirror(@plus.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2735 - assertIsFalse "email..email@domain.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2736 - assertIsFalse "email..email@domain.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2737 - assertIsFalse "email.@domain.com"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2738 - assertIsFalse "email.domain.com"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2739 - assertIsFalse "email@#hash.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2740 - assertIsFalse "email@.domain.com"                                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2741 - assertIsFalse "email@111.222.333"                                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2742 - assertIsFalse "email@111.222.333.256"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2743 - assertIsFalse "email@123.123.123.123]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2744 - assertIsFalse "email@123.123.[123.123]"                                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2745 - assertIsFalse "email@=qowaiv.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2746 - assertIsFalse "email@[123.123.123.123"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2747 - assertIsFalse "email@[123.123.123].123"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2748 - assertIsFalse "email@caret^xor.com"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2749 - assertIsFalse "email@colon:colon.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2750 - assertIsFalse "email@dollar$.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2751 - assertIsFalse "email@domain"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2752 - assertIsFalse "email@domain-.com"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2753 - assertIsFalse "email@domain..com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2754 - assertIsFalse "email@domain.com-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2755 - assertIsFalse "email@domain.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2756 - assertIsFalse "email@domain.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2757 - assertIsFalse "email@domain.com>"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2758 - assertIsFalse "email@domain@domain.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2759 - assertIsFalse "email@example"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2760 - assertIsFalse "email@example..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2761 - assertIsFalse "email@example.co.uk."                                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2762 - assertIsFalse "email@example.com "                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2763 - assertIsFalse "email@exclamation!mark.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2764 - assertIsFalse "email@grave`accent.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2765 - assertIsFalse "email@mailto:domain.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2766 - assertIsFalse "email@obelix*asterisk.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2767 - assertIsFalse "email@plus+.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2768 - assertIsFalse "email@plus.com (not closed comment"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2769 - assertIsFalse "email@p|pe.com"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2770 - assertIsFalse "email@question?mark.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2771 - assertIsFalse "email@r&amp;d.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2772 - assertIsFalse "email@rightbracket}.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2773 - assertIsFalse "email@wave~tilde.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2774 - assertIsFalse "email@{leftbracket.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2775 - assertIsFalse "f...bar@gmail.com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2776 - assertIsFalse "fa ke@false.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2777 - assertIsFalse "fake@-false.com"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2778 - assertIsFalse "fake@fal se.com"                                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2779 - assertIsFalse "fdsa"                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2780 - assertIsFalse "fdsa@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2781 - assertIsFalse "fdsa@fdsa"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2782 - assertIsFalse "fdsa@fdsa."                                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2783 - assertIsFalse "foo.bar#gmail.co.u"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2784 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2785 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2786 - assertIsFalse "foo~&(&)(@bar.com"                                                          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2787 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2788 - assertIsFalse "get_at_m.e@gmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2789 - assertIsFalse "hallo2ww22@example....caaaao"                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2790 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2791 - assertIsFalse "hello world@example.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2792 - assertIsFalse "invalid.email.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2793 - assertIsFalse "invalid@email@domain.com"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2794 - assertIsFalse "isis@100%.nl"                                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2795 - assertIsFalse "j..s@proseware.com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2796 - assertIsFalse "j.@server1.proseware.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2797 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2798 - assertIsFalse "journaldev"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2799 - assertIsFalse "journaldev()*@gmail.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2800 - assertIsFalse "journaldev..2002@gmail.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2801 - assertIsFalse "journaldev.@gmail.com"                                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2802 - assertIsFalse "journaldev123@.com"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2803 - assertIsFalse "journaldev123@.com.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2804 - assertIsFalse "journaldev123@gmail.a"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2805 - assertIsFalse "journaldev@%*.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2806 - assertIsFalse "journaldev@.com.my"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2807 - assertIsFalse "journaldev@gmail.com.1a"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2808 - assertIsFalse "journaldev@journaldev@gmail.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2809 - assertIsFalse "js@proseware..com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2810 - assertIsFalse "mailto:email@domain.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2811 - assertIsFalse "mailto:mailto:email@domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2812 - assertIsFalse "me..2002@gmail.com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2813 - assertIsFalse "me.@gmail.com"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2814 - assertIsFalse "me123@.com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2815 - assertIsFalse "me123@.com.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2816 - assertIsFalse "me@%*.com"                                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2817 - assertIsFalse "me@.com.my"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2818 - assertIsFalse "me@gmail.com.1a"                                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2819 - assertIsFalse "me@me@gmail.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2820 - assertIsFalse "myemail@@sample.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2821 - assertIsFalse "myemail@sa@mple.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2822 - assertIsFalse "myemailsample.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2823 - assertIsFalse "ote\"@example.com"                                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2824 - assertIsFalse "pio_pio@#factory.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2825 - assertIsFalse "pio_pio@factory.c#om"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2826 - assertIsFalse "pio_pio@factory.c*om"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2827 - assertIsFalse "plain.address"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2828 - assertIsFalse "plainaddress"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2829 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2830 - assertIsFalse "this is not valid@email$com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2831 - assertIsFalse "this\ still\\"not\allowed@example.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2832 - assertIsFalse "two..dot@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2833 - assertIsFalse "user#domain.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2834 - assertIsFalse "username@yahoo..com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2835 - assertIsFalse "username@yahoo.c"                                                           =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2836 - assertIsTrue  "username@domain.com"                                                        =   0 =  OK 
     *  2837 - assertIsTrue  "_username@domain.com"                                                       =   0 =  OK 
     *  2838 - assertIsTrue  "username_@domain.com"                                                       =   0 =  OK 
     *  2839 - assertIsFalse "xxx@u*.com"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2840 - assertIsFalse "xxxx..1234@yahoo.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2841 - assertIsFalse "xxxx.ourearth.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2842 - assertIsFalse "xxxx123@gmail.b"                                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2843 - assertIsFalse "xxxx@.com.my"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2844 - assertIsFalse "xxxx@.org.org"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2845 - assertIsFalse "xxxxx()*@gmail.com"                                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2846 - assertIsFalse "{something}@{something}.{something}"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2847 - assertIsTrue  "mymail\@hello@hotmail.com"                                                  =   0 =  OK 
     *  2848 - assertIsTrue  "!def!xyz%abc@example.com"                                                   =   0 =  OK 
     *  2849 - assertIsTrue  "!sd@gh.com"                                                                 =   0 =  OK 
     *  2850 - assertIsTrue  "$A12345@example.com"                                                        =   0 =  OK 
     *  2851 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                      =   0 =  OK 
     *  2852 - assertIsTrue  "%2@gmail.com"                                                               =   0 =  OK 
     *  2853 - assertIsTrue  "--@ooo.ooo"                                                                 =   0 =  OK 
     *  2854 - assertIsTrue  "-@bde.cc"                                                                   =   0 =  OK 
     *  2855 - assertIsTrue  "-asd@das.com"                                                               =   0 =  OK 
     *  2856 - assertIsTrue  "1234567890@domain.com"                                                      =   0 =  OK 
     *  2857 - assertIsTrue  "1@domain.com"                                                               =   0 =  OK 
     *  2858 - assertIsTrue  "1@gmail.com"                                                                =   0 =  OK 
     *  2859 - assertIsTrue  "1_example@something.gmail.com"                                              =   0 =  OK 
     *  2860 - assertIsTrue  "2@bde.cc"                                                                   =   0 =  OK 
     *  2861 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                                     =   0 =  OK 
     *  2862 - assertIsTrue  "<boss@nil.test>"                                                            =   0 =  OK 
     *  2863 - assertIsTrue  "<john@doe.com>"                                                             =   0 =  OK 
     *  2864 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                                  =   0 =  OK 
     *  2865 - assertIsTrue  "Abc.123@example.com"                                                        =   0 =  OK 
     *  2866 - assertIsTrue  "Abc@10.42.0.1"                                                              =   2 =  OK 
     *  2867 - assertIsTrue  "Abc@example.com"                                                            =   0 =  OK 
     *  2868 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                       =   0 =  OK 
     *  2869 - assertIsTrue  "Fred\ Bloggs@example.com"                                                   =   0 =  OK 
     *  2870 - assertIsTrue  "Joe.\\Blow@example.com"                                                     =   0 =  OK 
     *  2871 - assertIsTrue  "John <john@doe.com>"                                                        =   0 =  OK 
     *  2872 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                                =   0 =  OK 
     *  2873 - assertIsTrue  "This is <john@127.0.0.1>"                                                   =   2 =  OK 
     *  2874 - assertIsTrue  "This is <john@[127.0.0.1]>"                                                 =   2 =  OK 
     *  2875 - assertIsTrue  "Who? <one@y.test>"                                                          =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2876 - assertIsTrue  "\" \"@example.org"                                                          =   1 =  OK 
     *  2877 - assertIsTrue  "\"%2\"@gmail.com"                                                           =   1 =  OK 
     *  2878 - assertIsTrue  "\"Abc@def\"@example.com"                                                    =   1 =  OK 
     *  2879 - assertIsTrue  "\"Abc\@def\"@example.com"                                                   =   1 =  OK 
     *  2880 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                          =   1 =  OK 
     *  2881 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                                =   1 =  OK 
     *  2882 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                               =   1 =  OK 
     *  2883 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                         =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2884 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                                  =   1 =  OK 
     *  2885 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                                  =   1 =  OK 
     *  2886 - assertIsTrue  "\"a..b\"@gmail.com"                                                         =   1 =  OK 
     *  2887 - assertIsTrue  "\"a@b\"@example.com"                                                        =   1 =  OK 
     *  2888 - assertIsTrue  "\"a_b\"@gmail.com"                                                          =   1 =  OK 
     *  2889 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                               =   1 =  OK 
     *  2890 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                        =   1 =  OK 
     *  2891 - assertIsTrue  "\"foo\@bar\"@Something.com"                                                 =   1 =  OK 
     *  2892 - assertIsTrue  "\"j\\"s\"@proseware.com"                                                    =   1 =  OK 
     *  2893 - assertIsTrue  "\"myemail@sa\"@mple.com"                                                    =   1 =  OK 
     *  2894 - assertIsTrue  "_-_@bde.cc"                                                                 =   0 =  OK 
     *  2895 - assertIsTrue  "_@gmail.com"                                                                =   0 =  OK 
     *  2896 - assertIsTrue  "_dasd@sd.com"                                                               =   0 =  OK 
     *  2897 - assertIsTrue  "_dasd_das_@9.com"                                                           =   0 =  OK 
     *  2898 - assertIsTrue  "_somename@example.com"                                                      =   0 =  OK 
     *  2899 - assertIsTrue  "a&d@somedomain.com"                                                         =   0 =  OK 
     *  2900 - assertIsTrue  "a*d@somedomain.com"                                                         =   0 =  OK 
     *  2901 - assertIsTrue  "a+b@bde.cc"                                                                 =   0 =  OK 
     *  2902 - assertIsTrue  "a+b@c.com"                                                                  =   0 =  OK 
     *  2903 - assertIsTrue  "a-b@bde.cc"                                                                 =   0 =  OK 
     *  2904 - assertIsTrue  "a.a@test.com"                                                               =   0 =  OK 
     *  2905 - assertIsTrue  "a.b@com"                                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2906 - assertIsTrue  "a/d@somedomain.com"                                                         =   0 =  OK 
     *  2907 - assertIsTrue  "a2@bde.cc"                                                                  =   0 =  OK 
     *  2908 - assertIsTrue  "a@123.45.67.89"                                                             =   2 =  OK 
     *  2909 - assertIsTrue  "a@b.c.com"                                                                  =   0 =  OK 
     *  2910 - assertIsTrue  "a@b.com"                                                                    =   0 =  OK 
     *  2911 - assertIsTrue  "a@bc.com"                                                                   =   0 =  OK 
     *  2912 - assertIsTrue  "a@bcom"                                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2913 - assertIsTrue  "a@domain.com"                                                               =   0 =  OK 
     *  2914 - assertIsTrue  "a__z@provider.com"                                                          =   0 =  OK 
     *  2915 - assertIsTrue  "a_z%@gmail.com"                                                             =   0 =  OK 
     *  2916 - assertIsTrue  "aaron@theinfo.org"                                                          =   0 =  OK 
     *  2917 - assertIsTrue  "ab@288.120.150.10.com"                                                      =   0 =  OK 
     *  2918 - assertIsTrue  "ab@[120.254.254.120]"                                                       =   2 =  OK 
     *  2919 - assertIsTrue  "ab@b-de.cc"                                                                 =   0 =  OK 
     *  2920 - assertIsTrue  "ab@c.com"                                                                   =   0 =  OK 
     *  2921 - assertIsTrue  "ab_c@bde.cc"                                                                =   0 =  OK 
     *  2922 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                             =   1 =  OK 
     *  2923 - assertIsTrue  "abc.efg@gmail.com"                                                          =   0 =  OK 
     *  2924 - assertIsTrue  "abc.xyz@gmail.com.in"                                                       =   0 =  OK 
     *  2925 - assertIsTrue  "abc123xyz@asdf.co.in"                                                       =   0 =  OK 
     *  2926 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                       =   0 =  OK 
     *  2927 - assertIsTrue  "abc@abc.abc"                                                                =   0 =  OK 
     *  2928 - assertIsTrue  "abc@abc.abcd"                                                               =   0 =  OK 
     *  2929 - assertIsTrue  "abc@abc.abcde"                                                              =   0 =  OK 
     *  2930 - assertIsTrue  "abc@abc.co.in"                                                              =   0 =  OK 
     *  2931 - assertIsTrue  "abc@abc.com.com.com.com"                                                    =   0 =  OK 
     *  2932 - assertIsTrue  "abc@gmail.com.my"                                                           =   0 =  OK 
     *  2933 - assertIsTrue  "abc\@def@example.com"                                                       =   0 =  OK 
     *  2934 - assertIsTrue  "abc\\@example.com"                                                          =   0 =  OK 
     *  2935 - assertIsTrue  "abcxyz123@qwert.com"                                                        =   0 =  OK 
     *  2936 - assertIsTrue  "alex@example.com"                                                           =   0 =  OK 
     *  2937 - assertIsTrue  "alireza@test.co.uk"                                                         =   0 =  OK 
     *  2938 - assertIsTrue  "asd-@asd.com"                                                               =   0 =  OK 
     *  2939 - assertIsTrue  "begeddov@jfinity.com"                                                       =   0 =  OK 
     *  2940 - assertIsTrue  "check@this.com"                                                             =   0 =  OK 
     *  2941 - assertIsTrue  "cog@wheel.com"                                                              =   0 =  OK 
     *  2942 - assertIsTrue  "customer/department=shipping@example.com"                                   =   0 =  OK 
     *  2943 - assertIsTrue  "d._.___d@gmail.com"                                                         =   0 =  OK 
     *  2944 - assertIsTrue  "d.j@server1.proseware.com"                                                  =   0 =  OK 
     *  2945 - assertIsTrue  "d.oy.smith@gmail.com"                                                       =   0 =  OK 
     *  2946 - assertIsTrue  "d23d@da9.co9"                                                               =   0 =  OK 
     *  2947 - assertIsTrue  "d_oy_smith@gmail.com"                                                       =   0 =  OK 
     *  2948 - assertIsTrue  "dasd-dasd@das.com.das"                                                      =   0 =  OK 
     *  2949 - assertIsTrue  "dasd.dadas@dasd.com"                                                        =   0 =  OK 
     *  2950 - assertIsTrue  "dasd_-@jdas.com"                                                            =   0 =  OK 
     *  2951 - assertIsTrue  "david.jones@proseware.com"                                                  =   0 =  OK 
     *  2952 - assertIsTrue  "dclo@us.ibm.com"                                                            =   0 =  OK 
     *  2953 - assertIsTrue  "dda_das@das-dasd.com"                                                       =   0 =  OK 
     *  2954 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                               =   0 =  OK 
     *  2955 - assertIsTrue  "digit-only-domain@123.com"                                                  =   0 =  OK 
     *  2956 - assertIsTrue  "doysmith@gmail.com"                                                         =   0 =  OK 
     *  2957 - assertIsTrue  "drp@drp.cz"                                                                 =   0 =  OK 
     *  2958 - assertIsTrue  "dsq!a?@das.com"                                                             =   0 =  OK 
     *  2959 - assertIsTrue  "email@domain.co.de"                                                         =   0 =  OK 
     *  2960 - assertIsTrue  "email@domain.com"                                                           =   0 =  OK 
     *  2961 - assertIsTrue  "email@example.co.uk"                                                        =   0 =  OK 
     *  2962 - assertIsTrue  "email@example.com"                                                          =   0 =  OK 
     *  2963 - assertIsTrue  "email@mail.gmail.com"                                                       =   0 =  OK 
     *  2964 - assertIsTrue  "email@subdomain.domain.com"                                                 =   0 =  OK 
     *  2965 - assertIsTrue  "example@example.co"                                                         =   0 =  OK 
     *  2966 - assertIsTrue  "f.f.f@bde.cc"                                                               =   0 =  OK 
     *  2967 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                      =   0 =  OK 
     *  2968 - assertIsTrue  "first-name-last-name@d-a-n.com"                                             =   0 =  OK 
     *  2969 - assertIsTrue  "firstname+lastname@domain.com"                                              =   0 =  OK 
     *  2970 - assertIsTrue  "firstname-lastname@domain.com"                                              =   0 =  OK 
     *  2971 - assertIsTrue  "firstname.lastname@domain.com"                                              =   0 =  OK 
     *  2972 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                                  =   0 =  OK 
     *  2973 - assertIsTrue  "futureTLD@somewhere.fooo"                                                   =   0 =  OK 
     *  2974 - assertIsTrue  "hello.me_1@email.com"                                                       =   0 =  OK 
     *  2975 - assertIsTrue  "hello7___@ca.com.pt"                                                        =   0 =  OK 
     *  2976 - assertIsTrue  "info@ermaelan.com"                                                          =   0 =  OK 
     *  2977 - assertIsTrue  "j.s@server1.proseware.com"                                                  =   0 =  OK 
     *  2978 - assertIsTrue  "j@proseware.com9"                                                           =   0 =  OK 
     *  2979 - assertIsTrue  "j_9@[129.126.118.1]"                                                        =   2 =  OK 
     *  2980 - assertIsTrue  "jinujawad6s@gmail.com"                                                      =   0 =  OK 
     *  2981 - assertIsTrue  "john.doe@example.com"                                                       =   0 =  OK 
     *  2982 - assertIsTrue  "john.o'doe@example.com"                                                     =   0 =  OK 
     *  2983 - assertIsTrue  "john.smith@example.com"                                                     =   0 =  OK 
     *  2984 - assertIsTrue  "jones@ms1.proseware.com"                                                    =   0 =  OK 
     *  2985 - assertIsTrue  "journaldev+100@gmail.com"                                                   =   0 =  OK 
     *  2986 - assertIsTrue  "journaldev-100@journaldev.net"                                              =   0 =  OK 
     *  2987 - assertIsTrue  "journaldev-100@yahoo-test.com"                                              =   0 =  OK 
     *  2988 - assertIsTrue  "journaldev-100@yahoo.com"                                                   =   0 =  OK 
     *  2989 - assertIsTrue  "journaldev.100@journaldev.com.au"                                           =   0 =  OK 
     *  2990 - assertIsTrue  "journaldev.100@yahoo.com"                                                   =   0 =  OK 
     *  2991 - assertIsTrue  "journaldev111@journaldev.com"                                               =   0 =  OK 
     *  2992 - assertIsTrue  "journaldev@1.com"                                                           =   0 =  OK 
     *  2993 - assertIsTrue  "journaldev@gmail.com.com"                                                   =   0 =  OK 
     *  2994 - assertIsTrue  "journaldev@yahoo.com"                                                       =   0 =  OK 
     *  2995 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                          =   0 =  OK 
     *  2996 - assertIsTrue  "js#internal@proseware.com"                                                  =   0 =  OK 
     *  2997 - assertIsTrue  "js*@proseware.com"                                                          =   0 =  OK 
     *  2998 - assertIsTrue  "js@proseware.com9"                                                          =   0 =  OK 
     *  2999 - assertIsTrue  "me+100@me.com"                                                              =   0 =  OK 
     *  3000 - assertIsTrue  "me+alpha@example.com"                                                       =   0 =  OK 
     *  3001 - assertIsTrue  "me-100@me.com"                                                              =   0 =  OK 
     *  3002 - assertIsTrue  "me-100@me.com.au"                                                           =   0 =  OK 
     *  3003 - assertIsTrue  "me-100@yahoo-test.com"                                                      =   0 =  OK 
     *  3004 - assertIsTrue  "me.100@me.com"                                                              =   0 =  OK 
     *  3005 - assertIsTrue  "me@aaronsw.com"                                                             =   0 =  OK 
     *  3006 - assertIsTrue  "me@company.co.uk"                                                           =   0 =  OK 
     *  3007 - assertIsTrue  "me@gmail.com"                                                               =   0 =  OK 
     *  3008 - assertIsTrue  "me@gmail.com"                                                               =   0 =  OK 
     *  3009 - assertIsTrue  "me@mail.s2.example.com"                                                     =   0 =  OK 
     *  3010 - assertIsTrue  "me@me.cu.uk"                                                                =   0 =  OK 
     *  3011 - assertIsTrue  "my.ownsite@ourearth.org"                                                    =   0 =  OK 
     *  3012 - assertIsFalse "myemail@sample"                                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3013 - assertIsTrue  "myemail@sample.com"                                                         =   0 =  OK 
     *  3014 - assertIsTrue  "mysite@you.me.net"                                                          =   0 =  OK 
     *  3015 - assertIsTrue  "o'hare@example.com"                                                         =   0 =  OK 
     *  3016 - assertIsTrue  "peter.example@domain.comau"                                                 =   0 =  OK 
     *  3017 - assertIsTrue  "peter.piper@example.com"                                                    =   0 =  OK 
     *  3018 - assertIsTrue  "peter_123@news.com"                                                         =   0 =  OK 
     *  3019 - assertIsTrue  "pio^_pio@factory.com"                                                       =   0 =  OK 
     *  3020 - assertIsTrue  "pio_#pio@factory.com"                                                       =   0 =  OK 
     *  3021 - assertIsTrue  "pio_pio@factory.com"                                                        =   0 =  OK 
     *  3022 - assertIsTrue  "pio_~pio@factory.com"                                                       =   0 =  OK 
     *  3023 - assertIsTrue  "piskvor@example.lighting"                                                   =   0 =  OK 
     *  3024 - assertIsTrue  "rss-dev@yahoogroups.com"                                                    =   0 =  OK 
     *  3025 - assertIsTrue  "someone+tag@somewhere.net"                                                  =   0 =  OK 
     *  3026 - assertIsTrue  "someone@somewhere.co.uk"                                                    =   0 =  OK 
     *  3027 - assertIsTrue  "someone@somewhere.com"                                                      =   0 =  OK 
     *  3028 - assertIsTrue  "something_valid@somewhere.tld"                                              =   0 =  OK 
     *  3029 - assertIsTrue  "tvf@tvf.cz"                                                                 =   0 =  OK 
     *  3030 - assertIsTrue  "user#@domain.co.in"                                                         =   0 =  OK 
     *  3031 - assertIsTrue  "user'name@domain.co.in"                                                     =   0 =  OK 
     *  3032 - assertIsTrue  "user+mailbox@example.com"                                                   =   0 =  OK 
     *  3033 - assertIsTrue  "user-name@domain.co.in"                                                     =   0 =  OK 
     *  3034 - assertIsTrue  "user.name@domain.com"                                                       =   0 =  OK 
     *  3035 - assertIsFalse ".user.name@domain.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3036 - assertIsFalse "user-name@domain.com."                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3037 - assertIsFalse "username@.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3038 - assertIsTrue  "user1@domain.com"                                                           =   0 =  OK 
     *  3039 - assertIsTrue  "user?name@domain.co.in"                                                     =   0 =  OK 
     *  3040 - assertIsTrue  "user@domain.co.in"                                                          =   0 =  OK 
     *  3041 - assertIsTrue  "user@domain.com"                                                            =   0 =  OK 
     *  3042 - assertIsFalse "user@domaincom"                                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3043 - assertIsTrue  "user_name@domain.co.in"                                                     =   0 =  OK 
     *  3044 - assertIsTrue  "user_name@domain.com"                                                       =   0 =  OK 
     *  3045 - assertIsTrue  "username@yahoo.corporate"                                                   =   0 =  OK 
     *  3046 - assertIsTrue  "username@yahoo.corporate.in"                                                =   0 =  OK 
     *  3047 - assertIsTrue  "username+something@domain.com"                                              =   0 =  OK 
     *  3048 - assertIsTrue  "vdv@dyomedea.com"                                                           =   0 =  OK 
     *  3049 - assertIsTrue  "xxxx@gmail.com"                                                             =   0 =  OK 
     *  3050 - assertIsTrue  "xxxxxx@yahoo.com"                                                           =   0 =  OK 
     *  3051 - assertIsTrue  "user+mailbox/department=shipping@example.com"                               =   0 =  OK 
     *  3052 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                                    =   4 =  OK 
     *  3053 - assertIsFalse "user@localserver"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3054 - assertIsTrue  "w.b.f@test.com"                                                             =   0 =  OK 
     *  3055 - assertIsTrue  "w.b.f@test.museum"                                                          =   0 =  OK 
     *  3056 - assertIsTrue  "yoursite@ourearth.com"                                                      =   0 =  OK 
     *  3057 - assertIsTrue  "~pio_pio@factory.com"                                                       =   0 =  OK 
     *  3058 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                   =   0 =  OK 
     *  3059 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3060 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3061 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  3062 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  3063 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3064 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"             =   0 =  OK 
     *  3065 - assertIsTrue  "valid@[1.1.1.1]"                                                            =   2 =  OK 
     *  3066 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3067 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                                     =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3068 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                            =   4 =  OK 
     *  3069 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"             =   4 =  OK 
     *  3070 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                                  =   4 =  OK 
     *  3071 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"                     =   4 =  OK 
     *  3072 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                       =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3073 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                                =   4 =  OK 
     *  3074 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                                =   4 =  OK 
     *  3075 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                          =   2 =  OK 
     *  3076 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                              =   2 =  OK 
     *  3077 - assertIsFalse "invalid@[10]"                                                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3078 - assertIsFalse "invalid@[10.1]"                                                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3079 - assertIsFalse "invalid@[10.1.52]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3080 - assertIsFalse "invalid@[256.256.256.256]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3081 - assertIsFalse "invalid@[IPv6:123456]"                                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3082 - assertIsFalse "invalid@[127.0.0.1.]"                                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  3083 - assertIsFalse "invalid@[127.0.0.1]."                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3084 - assertIsFalse "invalid@[127.0.0.1]x"                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3085 - assertIsFalse "invalid@domain1.com@domain2.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3086 - assertIsFalse "\"locál-part\"@example.com"                                                 =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3087 - assertIsFalse "invalid@[IPv6:1::2:]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3088 - assertIsFalse "invalid@[IPv6::1::1]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3089 - assertIsFalse "invalid@[]"                                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3090 - assertIsFalse "invalid@[111.111.111.111"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3091 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3092 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3093 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3094 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3095 - assertIsFalse "invalid@[IPv6:1111:1111]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3096 - assertIsFalse "\"invalid-qstring@example.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs --------------------------------------------------
     * 
     *  3097 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                                 =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3098 - assertIsTrue  "\"back\slash\"@sld.com"                                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3099 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                                =   1 =  OK 
     *  3100 - assertIsTrue  "\"quoted\"@sld.com"                                                         =   1 =  OK 
     *  3101 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                         =   1 =  OK 
     *  3102 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                         =   0 =  OK 
     *  3103 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                        =   0 =  OK 
     *  3104 - assertIsTrue  "01234567890@numbers-in-local.net"                                           =   0 =  OK 
     *  3105 - assertIsTrue  "a@single-character-in-local.org"                                            =   0 =  OK 
     *  3106 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org"  =   0 =  OK 
     *  3107 - assertIsTrue  "backticksarelegit@test.com"                                                 =   0 =  OK 
     *  3108 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                                 =   2 =  OK 
     *  3109 - assertIsTrue  "country-code-tld@sld.rw"                                                    =   0 =  OK 
     *  3110 - assertIsTrue  "country-code-tld@sld.uk"                                                    =   0 =  OK 
     *  3111 - assertIsTrue  "letters-in-sld@123.com"                                                     =   0 =  OK 
     *  3112 - assertIsTrue  "local@dash-in-sld.com"                                                      =   0 =  OK 
     *  3113 - assertIsTrue  "local@sld.newTLD"                                                           =   0 =  OK 
     *  3114 - assertIsTrue  "local@sub.domains.com"                                                      =   0 =  OK 
     *  3115 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                           =   0 =  OK 
     *  3116 - assertIsTrue  "one-character-third-level@a.example.com"                                    =   0 =  OK 
     *  3117 - assertIsTrue  "one-letter-sld@x.org"                                                       =   0 =  OK 
     *  3118 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                                   =   0 =  OK 
     *  3119 - assertIsTrue  "single-character-in-sld@x.org"                                              =   0 =  OK 
     *  3120 - assertIsTrue  "uncommon-tld@sld.mobi"                                                      =   0 =  OK 
     *  3121 - assertIsTrue  "uncommon-tld@sld.museum"                                                    =   0 =  OK 
     *  3122 - assertIsTrue  "uncommon-tld@sld.travel"                                                    =   0 =  OK 
     *  3123 - assertIsFalse "invalid"                                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3124 - assertIsFalse "invalid@"                                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3125 - assertIsFalse "invalid @"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3126 - assertIsFalse "invalid@[555.666.777.888]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3127 - assertIsFalse "invalid@[IPv6:123456]"                                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3128 - assertIsFalse "invalid@[127.0.0.1.]"                                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  3129 - assertIsFalse "invalid@[127.0.0.1]."                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3130 - assertIsFalse "invalid@[127.0.0.1]x"                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3131 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3132 - assertIsFalse "@missing-local.org"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3133 - assertIsFalse "IP-and-port@127.0.0.1:25"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3134 - assertIsFalse "another-invalid-ip@127.0.0.256"                                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3135 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3136 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3137 - assertIsFalse "invalid-ip@127.0.0.1.26"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3138 - assertIsFalse "local-ends-with-dot.@sld.com"                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3139 - assertIsFalse "missing-at-sign.net"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3140 - assertIsFalse "missing-sld@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3141 - assertIsFalse "missing-tld@sld."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3142 - assertIsFalse "sld-ends-with-dash@sld-.com"                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3143 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3144 - assertIsFalse "two..consecutive-dots@sld.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3145 - assertIsTrue  "unbracketed-IP@127.0.0.1"                                                   =   2 =  OK 
     *  3146 - assertIsFalse "underscore.error@example.com_"                                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php -------------------------------------------------
     * 
     *  3147 - assertIsTrue  "first.last@iana.org"                                                        =   0 =  OK 
     *  3148 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org"  =   0 =  OK 
     *  3149 - assertIsTrue  "\"first\\"last\"@iana.org"                                                  =   1 =  OK 
     *  3150 - assertIsTrue  "\"first@last\"@iana.org"                                                    =   1 =  OK 
     *  3151 - assertIsTrue  "\"first\\last\"@iana.org"                                                   =   1 =  OK 
     *  3152 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  3153 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  3154 - assertIsTrue  "first.last@[12.34.56.78]"                                                   =   2 =  OK 
     *  3155 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                          =   4 =  OK 
     *  3156 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                           =   4 =  OK 
     *  3157 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                          =   4 =  OK 
     *  3158 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                          =   4 =  OK 
     *  3159 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"                  =   4 =  OK 
     *  3160 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  3161 - assertIsTrue  "first.last@3com.com"                                                        =   0 =  OK 
     *  3162 - assertIsTrue  "first.last@123.iana.org"                                                    =   0 =  OK 
     *  3163 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3164 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                      =   4 =  OK 
     *  3165 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                      =   1 =  OK 
     *  3166 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                                  =   1 =  OK 
     *  3167 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                                    =   1 =  OK 
     *  3168 - assertIsTrue  "\"Abc@def\"@iana.org"                                                       =   1 =  OK 
     *  3169 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                                 =   1 =  OK 
     *  3170 - assertIsTrue  "user+mailbox@iana.org"                                                      =   0 =  OK 
     *  3171 - assertIsTrue  "$A12345@iana.org"                                                           =   0 =  OK 
     *  3172 - assertIsTrue  "!def!xyz%abc@iana.org"                                                      =   0 =  OK 
     *  3173 - assertIsTrue  "_somename@iana.org"                                                         =   0 =  OK 
     *  3174 - assertIsTrue  "dclo@us.ibm.com"                                                            =   0 =  OK 
     *  3175 - assertIsTrue  "peter.piper@iana.org"                                                       =   0 =  OK 
     *  3176 - assertIsTrue  "test@iana.org"                                                              =   0 =  OK 
     *  3177 - assertIsTrue  "TEST@iana.org"                                                              =   0 =  OK 
     *  3178 - assertIsTrue  "1234567890@iana.org"                                                        =   0 =  OK 
     *  3179 - assertIsTrue  "test+test@iana.org"                                                         =   0 =  OK 
     *  3180 - assertIsTrue  "test-test@iana.org"                                                         =   0 =  OK 
     *  3181 - assertIsTrue  "t*est@iana.org"                                                             =   0 =  OK 
     *  3182 - assertIsTrue  "+1~1+@iana.org"                                                             =   0 =  OK 
     *  3183 - assertIsTrue  "{_test_}@iana.org"                                                          =   0 =  OK 
     *  3184 - assertIsTrue  "test.test@iana.org"                                                         =   0 =  OK 
     *  3185 - assertIsTrue  "\"test.test\"@iana.org"                                                     =   1 =  OK 
     *  3186 - assertIsTrue  "test.\"test\"@iana.org"                                                     =   1 =  OK 
     *  3187 - assertIsTrue  "\"test@test\"@iana.org"                                                     =   1 =  OK 
     *  3188 - assertIsTrue  "test@123.123.123.x123"                                                      =   0 =  OK 
     *  3189 - assertIsTrue  "test@123.123.123.123"                                                       =   2 =  OK 
     *  3190 - assertIsTrue  "test@[123.123.123.123]"                                                     =   2 =  OK 
     *  3191 - assertIsTrue  "test@example.iana.org"                                                      =   0 =  OK 
     *  3192 - assertIsTrue  "test@example.example.iana.org"                                              =   0 =  OK 
     *  3193 - assertIsTrue  "customer/department@iana.org"                                               =   0 =  OK 
     *  3194 - assertIsTrue  "_Yosemite.Sam@iana.org"                                                     =   0 =  OK 
     *  3195 - assertIsTrue  "~@iana.org"                                                                 =   0 =  OK 
     *  3196 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                                 =   1 =  OK 
     *  3197 - assertIsTrue  "Ima.Fool@iana.org"                                                          =   0 =  OK 
     *  3198 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                      =   1 =  OK 
     *  3199 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                                    =   1 =  OK 
     *  3200 - assertIsTrue  "\"first\".\"last\"@iana.org"                                                =   1 =  OK 
     *  3201 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                         =   1 =  OK 
     *  3202 - assertIsTrue  "\"first\".last@iana.org"                                                    =   1 =  OK 
     *  3203 - assertIsTrue  "first.\"last\"@iana.org"                                                    =   1 =  OK 
     *  3204 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                                     =   1 =  OK 
     *  3205 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                         =   1 =  OK 
     *  3206 - assertIsTrue  "\"first.middle.last\"@iana.org"                                             =   1 =  OK 
     *  3207 - assertIsTrue  "\"first..last\"@iana.org"                                                   =   1 =  OK 
     *  3208 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                         =   1 =  OK 
     *  3209 - assertIsFalse "first.last @iana.orgin"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3210 - assertIsTrue  "\"test blah\"@iana.orgin"                                                   =   1 =  OK 
     *  3211 - assertIsTrue  "name.lastname@domain.com"                                                   =   0 =  OK 
     *  3212 - assertIsTrue  "a@bar.com"                                                                  =   0 =  OK 
     *  3213 - assertIsTrue  "aaa@[123.123.123.123]"                                                      =   2 =  OK 
     *  3214 - assertIsTrue  "a-b@bar.com"                                                                =   0 =  OK 
     *  3215 - assertIsFalse "+@b.c"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3216 - assertIsTrue  "+@b.com"                                                                    =   0 =  OK 
     *  3217 - assertIsTrue  "a@b.co-foo.uk"                                                              =   0 =  OK 
     *  3218 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                         =   1 =  OK 
     *  3219 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                         =   1 =  OK 
     *  3220 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                           =   0 =  OK 
     *  3221 - assertIsTrue  "foobar@192.168.0.1"                                                         =   2 =  OK 
     *  3222 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                          =   6 =  OK 
     *  3223 - assertIsTrue  "user%uucp!path@berkeley.edu"                                                =   0 =  OK 
     *  3224 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                                  =   0 =  OK 
     *  3225 - assertIsTrue  "test@test.com"                                                              =   0 =  OK 
     *  3226 - assertIsTrue  "test@xn--example.com"                                                       =   0 =  OK 
     *  3227 - assertIsTrue  "test@example.com"                                                           =   0 =  OK 
     *  3228 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                   =   0 =  OK 
     *  3229 - assertIsTrue  "first\@last@iana.org"                                                       =   0 =  OK 
     *  3230 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                                   =   0 =  OK 
     *  3231 - assertIsFalse "first.last@example.123"                                                     =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3232 - assertIsFalse "first.last@comin"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3233 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                                  =   1 =  OK 
     *  3234 - assertIsTrue  "Abc\@def@iana.org"                                                          =   0 =  OK 
     *  3235 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                      =   0 =  OK 
     *  3236 - assertIsFalse "Joe.\Blow@iana.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3237 - assertIsTrue  "first.last@sub.do.com"                                                      =   0 =  OK 
     *  3238 - assertIsFalse "first.last"                                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3239 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                            =   0 =  OK 
     *  3240 - assertIsTrue  "\"hello world\"@example.com"                                                =   1 =  OK 
     *  3241 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3242 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                               =   1 =  OK 
     *  3243 - assertIsTrue  "example+tag@gmail.com"                                                      =   0 =  OK 
     *  3244 - assertIsFalse ".ann..other.@example.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3245 - assertIsTrue  "ann.other@example.com"                                                      =   0 =  OK 
     *  3246 - assertIsTrue  "something@something.something"                                              =   0 =  OK 
     *  3247 - assertIsTrue  "c@(Chris's host.)public.examplein"                                          =   6 =  OK 
     *  3248 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3249 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3250 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3251 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3252 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3253 - assertIsFalse "first().last@iana.orgin"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3254 - assertIsFalse "pete(his account)@silly.test(his host)"                                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3255 - assertIsFalse "jdoe@machine(comment). examplein"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3256 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3257 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3258 - assertIsFalse "1234 @ local(blah) .machine .examplein"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3259 - assertIsFalse "a@bin"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3260 - assertIsFalse "a@barin"                                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3261 - assertIsFalse "@about.museum"                                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3262 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3263 - assertIsFalse ".first.last@iana.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3264 - assertIsFalse "first.last.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3265 - assertIsFalse "first..last@iana.org"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3266 - assertIsFalse "\"first\"last\"@iana.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3267 - assertIsFalse "first.last@"                                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3268 - assertIsFalse "first.last@-xample.com"                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3269 - assertIsFalse "first.last@exampl-.com"                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3270 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3271 - assertIsFalse "abc\@iana.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3272 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3273 - assertIsFalse "abc@def@iana.org"                                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3274 - assertIsFalse "@iana.org"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3275 - assertIsFalse "doug@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3276 - assertIsFalse "\"qu@iana.org"                                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  3277 - assertIsFalse "ote\"@iana.org"                                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3278 - assertIsFalse ".dot@iana.org"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3279 - assertIsFalse "dot.@iana.org"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3280 - assertIsFalse "two..dot@iana.org"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3281 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3282 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3283 - assertIsFalse "hello world@iana.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3284 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3285 - assertIsFalse "test.iana.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3286 - assertIsFalse "test.@iana.org"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3287 - assertIsFalse "test..test@iana.org"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3288 - assertIsFalse ".test@iana.org"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3289 - assertIsFalse "test@test@iana.org"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3290 - assertIsFalse "test@@iana.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3291 - assertIsFalse "-- test --@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3292 - assertIsFalse "[test]@iana.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3293 - assertIsFalse "\"test\"test\"@iana.org"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3294 - assertIsFalse "()[]\;:.><@iana.org"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3295 - assertIsFalse "test@."                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3296 - assertIsFalse "test@example."                                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3297 - assertIsFalse "test@.org"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3298 - assertIsFalse "test@[123.123.123.123"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3299 - assertIsFalse "test@123.123.123.123]"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3300 - assertIsFalse "NotAnEmail"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3301 - assertIsFalse "@NotAnEmail"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3302 - assertIsFalse "\"test\"blah\"@iana.org"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3303 - assertIsFalse ".wooly@iana.org"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3304 - assertIsFalse "wo..oly@iana.org"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3305 - assertIsFalse "pootietang.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3306 - assertIsFalse ".@iana.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3307 - assertIsFalse "Ima Fool@iana.org"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3308 - assertIsFalse "foo@[\1.2.3.4]"                                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3309 - assertIsFalse "first.\"\".last@iana.org"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3310 - assertIsFalse "first\last@iana.org"                                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3311 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3312 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3313 - assertIsFalse "cal(foo(bar)@iamcal.com"                                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3314 - assertIsFalse "cal(foo)bar)@iamcal.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3315 - assertIsFalse "cal(foo\)@iamcal.com"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3316 - assertIsFalse "first(middle)last@iana.org"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3317 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3318 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3319 - assertIsFalse ".@"                                                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3320 - assertIsFalse "@bar.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3321 - assertIsFalse "@@bar.com"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3322 - assertIsFalse "aaa.com"                                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3323 - assertIsFalse "aaa@.com"                                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3324 - assertIsFalse "aaa@.123"                                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3325 - assertIsFalse "aaa@[123.123.123.123]a"                                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3326 - assertIsFalse "aaa@[123.123.123.333]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3327 - assertIsFalse "a@bar.com."                                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3328 - assertIsFalse "a@-b.com"                                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3329 - assertIsFalse "a@b-.com"                                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3330 - assertIsFalse "-@..com"                                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3331 - assertIsFalse "-@a..com"                                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3332 - assertIsFalse "@about.museum-"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3333 - assertIsFalse "test@...........com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3334 - assertIsFalse "first.last@[IPv6::]"                                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3335 - assertIsFalse "first.last@[IPv6::::]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3336 - assertIsFalse "first.last@[IPv6::b4]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3337 - assertIsFalse "first.last@[IPv6::::b4]"                                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3338 - assertIsFalse "first.last@[IPv6::b3:b4]"                                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3339 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3340 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3341 - assertIsFalse "first.last@[IPv6:a1:]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3342 - assertIsFalse "first.last@[IPv6:a1:::]"                                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3343 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3344 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3345 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3346 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3347 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3348 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3349 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3350 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3351 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3352 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3353 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3354 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3355 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                        =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  3356 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3357 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3358 - assertIsFalse "first.last@[IPv6::a2::b4]"                                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3359 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3360 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3361 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3362 - assertIsFalse "first.last@[.12.34.56.78]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  3363 - assertIsFalse "first.last@[12.34.56.789]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3364 - assertIsFalse "first.last@[::12.34.56.78]"                                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3365 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                            =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3366 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                            =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  3367 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3368 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3369 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"             =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3370 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3371 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3372 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3373 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3374 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3375 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3376 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3377 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3378 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3379 - assertIsTrue  "first.last@[IPv6:::]"                                                       =   4 =  OK 
     *  3380 - assertIsTrue  "first.last@[IPv6:::b4]"                                                     =   4 =  OK 
     *  3381 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                                  =   4 =  OK 
     *  3382 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                                   =   4 =  OK 
     *  3383 - assertIsTrue  "first.last@[IPv6:a1::]"                                                     =   4 =  OK 
     *  3384 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                                  =   4 =  OK 
     *  3385 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                                    =   4 =  OK 
     *  3386 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                                    =   4 =  OK 
     *  3387 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3388 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3389 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3390 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3391 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                          =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3392 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3393 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3394 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3395 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3396 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                       =   4 =  OK 
     * 
     * ---- https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/ -------------------------------
     * 
     *  3397 - assertIsTrue  "hello@example.com"                                                          =   0 =  OK 
     *  3398 - assertIsTrue  "hello@example.co.uk"                                                        =   0 =  OK 
     *  3399 - assertIsTrue  "hello-2020@example.com"                                                     =   0 =  OK 
     *  3400 - assertIsTrue  "hello.2020@example.com"                                                     =   0 =  OK 
     *  3401 - assertIsTrue  "hello_2020@example.com"                                                     =   0 =  OK 
     *  3402 - assertIsTrue  "h@example.com"                                                              =   0 =  OK 
     *  3403 - assertIsTrue  "h@example-example.com"                                                      =   0 =  OK 
     *  3404 - assertIsTrue  "h@example-example-example.com"                                              =   0 =  OK 
     *  3405 - assertIsTrue  "h@example.example-example.com"                                              =   0 =  OK 
     *  3406 - assertIsTrue  "hello.world-2020@example.com"                                               =   0 =  OK 
     *  3407 - assertIsTrue  "hello@example_example.com"                                                  =   0 =  OK 
     *  3408 - assertIsTrue  "hello!+2020@example.com"                                                    =   0 =  OK 
     *  3409 - assertIsFalse "hello"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3410 - assertIsFalse "hello@2020@example.com"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3411 - assertIsFalse ".hello@example.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3412 - assertIsFalse "hello.@example.com"                                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3413 - assertIsFalse "hello..world@example.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3414 - assertIsFalse "hello@example.a"                                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  3415 - assertIsFalse "hello@example..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3416 - assertIsFalse "hello@.com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3417 - assertIsFalse "hello@.com."                                                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3418 - assertIsFalse "hello@-example.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3419 - assertIsFalse "hello@example.com-"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3420 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234xx@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3421 - assertIsTrue  "email@goes_here.com"                                                        =   0 =  OK 
     *  3422 - assertIsTrue  "double--dash@example.com"                                                   =   0 =  OK 
     * 
     * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------
     * 
     *  3423 - assertIsFalse ""                                                                           =  11 =  OK    Laenge: Eingabe ist Leerstring
     *  3424 - assertIsFalse " "                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3425 - assertIsFalse " jkt@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3426 - assertIsFalse "jkt@gmail.com "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3427 - assertIsFalse "jkt@ gmail.com"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3428 - assertIsFalse "jkt@g mail.com"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3429 - assertIsFalse "jkt @gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3430 - assertIsFalse "j kt@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- https://www.abstractapi.com/guides/java-email-validation --------------------------------------------------------------------
     * 
     *  3431 - assertIsTrue  "\"test123\"@gmail.com"                                                      =   1 =  OK 
     *  3432 - assertIsTrue  "test123@gmail.comcomco"                                                     =   0 =  OK 
     *  3433 - assertIsTrue  "test123@gmail.c"                                                            =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  3434 - assertIsTrue  "test1&23@gmail.com"                                                         =   0 =  OK 
     *  3435 - assertIsTrue  "test123@gmail.com"                                                          =   0 =  OK 
     *  3436 - assertIsFalse "test123@gmail..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3437 - assertIsFalse ".test123@gmail.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3438 - assertIsFalse "test123@gmail.com."                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3439 - assertIsFalse "test1Ὠ23@gmail.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- https://www.javatpoint.com/java-email-validation ----------------------------------------------------------------------------
     * 
     *  3440 - assertIsTrue  "javaTpoint@domain.co.in"                                                    =   0 =  OK 
     *  3441 - assertIsTrue  "javaTpoint@domain.com"                                                      =   0 =  OK 
     *  3442 - assertIsTrue  "javaTpoint.name@domain.com"                                                 =   0 =  OK 
     *  3443 - assertIsTrue  "javaTpoint#@domain.co.in"                                                   =   0 =  OK 
     *  3444 - assertIsTrue  "javaTpoint@domain.com"                                                      =   0 =  OK 
     *  3445 - assertIsFalse "javaTpoint@domaincom"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3446 - assertIsTrue  "javaTpoint@domain.co.in"                                                    =   0 =  OK 
     *  3447 - assertIsTrue  "12453@domain.com"                                                           =   0 =  OK 
     *  3448 - assertIsTrue  "javaTpoint.name@domain.com"                                                 =   0 =  OK 
     *  3449 - assertIsTrue  "1avaTpoint#@domain.co.in"                                                   =   0 =  OK 
     *  3450 - assertIsTrue  "javaTpoint@domain.co.in"                                                    =   0 =  OK 
     *  3451 - assertIsTrue  "javaTpoint@domain.com"                                                      =   0 =  OK 
     *  3452 - assertIsTrue  "javaTpoint.name@domain.com"                                                 =   0 =  OK 
     *  3453 - assertIsTrue  "javaTpoint#@domain.co.in"                                                   =   0 =  OK 
     *  3454 - assertIsTrue  "java'Tpoint@domain.com"                                                     =   0 =  OK 
     *  3455 - assertIsFalse ".javaTpoint@yahoo.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3456 - assertIsFalse "javaTpoint@domain.com."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3457 - assertIsFalse "javaTpoint#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3458 - assertIsFalse "javaTpoint@domain..com"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3459 - assertIsFalse "@yahoo.com"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3460 - assertIsFalse "javaTpoint#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3461 - assertIsFalse "12javaTpoint#domain.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     * 
     * ---- https://java2blog.com/validate-email-address-in-java/ -----------------------------------------------------------------------
     * 
     *  3462 - assertIsTrue  "admin@java2blog.com"                                                        =   0 =  OK 
     *  3463 - assertIsFalse "@java2blog.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3464 - assertIsTrue  "arpit.mandliya@java2blog.com"                                               =   0 =  OK 
     * 
     * ---- https://www.tutorialspoint.com/javaexamples/regular_email.htm ---------------------------------------------------------------
     * 
     *  3465 - assertIsTrue  "sairamkrishna@tutorialspoint.com"                                           =   0 =  OK 
     *  3466 - assertIsTrue  "kittuprasad700@gmail.com"                                                   =   0 =  OK 
     *  3467 - assertIsFalse "sairamkrishna_mammahe%google-india.com"                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3468 - assertIsTrue  "sairam.krishna@gmail-indai.com"                                             =   0 =  OK 
     *  3469 - assertIsTrue  "sai#@youtube.co.in"                                                         =   0 =  OK 
     *  3470 - assertIsFalse "kittu@domaincom"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3471 - assertIsFalse "kittu#gmail.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3472 - assertIsFalse "@pindom.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * ---- https://www.rohannagar.com/jmail/ -------------------------------------------------------------------------------------------
     * 
     *  3473 - assertIsFalse "\"qu@test.org"                                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  3474 - assertIsFalse "ote\"@test.org"                                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3475 - assertIsFalse "\"().:;<>[\]@example.com"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3476 - assertIsFalse "\"\"\"@iana.org"                                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3477 - assertIsFalse "Abc.example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3478 - assertIsFalse "A@b@c@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3479 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3480 - assertIsFalse "this is\"not\allowed@example.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3481 - assertIsFalse "this\ still\"not\allowed@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3482 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3483 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3484 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3485 - assertIsFalse "plainaddress"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3486 - assertIsFalse "@example.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3487 - assertIsFalse ".email@example.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3488 - assertIsFalse "email.@example.com"                                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3489 - assertIsFalse "email..email@example.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3490 - assertIsFalse "email@-example.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3491 - assertIsFalse "email@111.222.333.44444"                                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  3492 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3493 - assertIsFalse "email@[12.34.44.56"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3494 - assertIsFalse "email@14.44.56.34]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3495 - assertIsFalse "email@[1.1.23.5f]"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3496 - assertIsFalse "email@[3.256.255.23]"                                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3497 - assertIsTrue  "\"first\\"last\"@test.org"                                                  =   1 =  OK 
     *  3498 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3499 - assertIsTrue  "first\@last@iana.org"                                                       =   0 =  OK 
     *  3500 - assertIsFalse "test@example.com "                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3501 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3502 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3503 - assertIsFalse "invalid@about.museum-"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3504 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3505 - assertIsFalse "abc@def@test.org"                                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3506 - assertIsTrue  "abc\@def@test.org"                                                          =   0 =  OK 
     *  3507 - assertIsFalse "abc\@test.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3508 - assertIsFalse "@test.org"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3509 - assertIsFalse ".dot@test.org"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3510 - assertIsFalse "dot.@test.org"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3511 - assertIsFalse "two..dot@test.org"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3512 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3513 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3514 - assertIsFalse "hello world@test.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3515 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3516 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3517 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3518 - assertIsFalse "test.test.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3519 - assertIsFalse "test.@test.org"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3520 - assertIsFalse "test..test@test.org"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3521 - assertIsFalse ".test@test.org"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3522 - assertIsFalse "test@test@test.org"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3523 - assertIsFalse "test@@test.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3524 - assertIsFalse "-- test --@test.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3525 - assertIsFalse "[test]@test.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3526 - assertIsFalse "\"test\"test\"@test.org"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3527 - assertIsFalse "()[]\;:.><@test.org"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3528 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3529 - assertIsFalse ".@test.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3530 - assertIsFalse "Ima Fool@test.org"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3531 - assertIsTrue  "\"first\\"last\"@test.org"                                                  =   1 =  OK 
     *  3532 - assertIsFalse "foo@[.2.3.4]"                                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3533 - assertIsFalse "first\last@test.org"                                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3534 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3535 - assertIsFalse "first(middle)last@test.org"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3536 - assertIsFalse "\"test\"test@test.com"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3537 - assertIsFalse "()@test.com"                                                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  3538 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  3539 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3540 - assertIsFalse "invalid@[1]"                                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3541 - assertIsFalse "ä📧@-foo"                                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3542 - assertIsFalse "ä📧@foo-"                                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3543 - assertIsFalse "first(comment(inner@comment.com"                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3544 - assertIsFalse "Joe A Smith <email@example.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3545 - assertIsFalse "Joe A Smith email@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3546 - assertIsFalse "Joe A Smith <email@example.com->"                                           =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3547 - assertIsFalse "Joe A Smith <email@-example.com->"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3548 - assertIsFalse "Joe A Smith <email>"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3549 - assertIsTrue  "\"email\"@example.com"                                                      =   1 =  OK 
     *  3550 - assertIsTrue  "\"first@last\"@test.org"                                                    =   1 =  OK 
     *  3551 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                                 =   1 =  OK 
     *  3552 - assertIsFalse "\"first\"last\"@test.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3553 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                         =   1 =  OK 
     *  3554 - assertIsFalse "\"first\last\"@test.org"                                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3555 - assertIsTrue  "\"Abc\@def\"@test.org"                                                      =   1 =  OK 
     *  3556 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                                  =   1 =  OK 
     *  3557 - assertIsFalse "\"Joe.\Blow\"@test.org"                                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3558 - assertIsTrue  "\"Abc@def\"@test.org"                                                       =   1 =  OK 
     *  3559 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                                   =   1 =  OK 
     *  3560 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@test.org"                                             =   1 =  OK 
     *  3561 - assertIsTrue  "\"[[ test ]]\"@test.org"                                                    =   1 =  OK 
     *  3562 - assertIsTrue  "\"test.test\"@test.org"                                                     =   1 =  OK 
     *  3563 - assertIsTrue  "test.\"test\"@test.org"                                                     =   1 =  OK 
     *  3564 - assertIsTrue  "\"test@test\"@test.org"                                                     =   1 =  OK 
     *  3565 - assertIsFalse "\"test tabulator  est\"@test.org"                                           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3566 - assertIsTrue  "\"first\".\"last\"@test.org"                                                =   1 =  OK 
     *  3567 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                         =   1 =  OK 
     *  3568 - assertIsTrue  "\"first\".last@test.org"                                                    =   1 =  OK 
     *  3569 - assertIsTrue  "first.\"last\"@test.org"                                                    =   1 =  OK 
     *  3570 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                                     =   1 =  OK 
     *  3571 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                         =   1 =  OK 
     *  3572 - assertIsTrue  "\"first.middle.last\"@test.org"                                             =   1 =  OK 
     *  3573 - assertIsTrue  "\"first..last\"@test.org"                                                   =   1 =  OK 
     *  3574 - assertIsTrue  "\"Unicode NULL \"@char.com"                                                 =   1 =  OK 
     *  3575 - assertIsFalse "\"test\blah\"@test.org"                                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3576 - assertIsFalse "\"testlah\"@test.org"                                                      =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3577 - assertIsTrue  "\"test\\"blah\"@test.org"                                                   =   1 =  OK 
     *  3578 - assertIsTrue  "\"first\\"last\"@test.org"                                                  =   1 =  OK 
     *  3579 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                           =   1 =  OK 
     *  3580 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3581 - assertIsTrue  "\"test blah\"@test.org"                                                     =   1 =  OK 
     *  3582 - assertIsTrue  "first.last@test.org"                                                        =   0 =  OK 
     *  3583 - assertIsFalse "jdoe@machine(comment).example"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3584 - assertIsFalse "first.\"\".last@test.org"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3585 - assertIsFalse "\"\"@test.org"                                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3586 - assertIsTrue  "very.common@example.org"                                                    =   0 =  OK 
     *  3587 - assertIsTrue  "test/test@test.com"                                                         =   0 =  OK 
     *  3588 - assertIsTrue  "user-@example.org"                                                          =   0 =  OK 
     *  3589 - assertIsTrue  "firstname.lastname@example.com"                                             =   0 =  OK 
     *  3590 - assertIsTrue  "email@subdomain.example.com"                                                =   0 =  OK 
     *  3591 - assertIsTrue  "firstname+lastname@example.com"                                             =   0 =  OK 
     *  3592 - assertIsTrue  "1234567890@example.com"                                                     =   0 =  OK 
     *  3593 - assertIsTrue  "email@example-one.com"                                                      =   0 =  OK 
     *  3594 - assertIsTrue  "_______@example.com"                                                        =   0 =  OK 
     *  3595 - assertIsTrue  "email@example.name"                                                         =   0 =  OK 
     *  3596 - assertIsTrue  "email@example.museum"                                                       =   0 =  OK 
     *  3597 - assertIsTrue  "email@example.co.jp"                                                        =   0 =  OK 
     *  3598 - assertIsTrue  "firstname-lastname@example.com"                                             =   0 =  OK 
     *  3599 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  3600 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3601 - assertIsTrue  "first.last@123.test.org"                                                    =   0 =  OK 
     *  3602 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3603 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org"  =   0 =  OK 
     *  3604 - assertIsTrue  "user+mailbox@test.org"                                                      =   0 =  OK 
     *  3605 - assertIsTrue  "customer/department=shipping@test.org"                                      =   0 =  OK 
     *  3606 - assertIsTrue  "$A12345@test.org"                                                           =   0 =  OK 
     *  3607 - assertIsTrue  "!def!xyz%abc@test.org"                                                      =   0 =  OK 
     *  3608 - assertIsTrue  "_somename@test.org"                                                         =   0 =  OK 
     *  3609 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                            =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3610 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3611 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3612 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3613 - assertIsTrue  "+@b.c"                                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3614 - assertIsTrue  "TEST@test.org"                                                              =   0 =  OK 
     *  3615 - assertIsTrue  "1234567890@test.org"                                                        =   0 =  OK 
     *  3616 - assertIsTrue  "test-test@test.org"                                                         =   0 =  OK 
     *  3617 - assertIsTrue  "t*est@test.org"                                                             =   0 =  OK 
     *  3618 - assertIsTrue  "+1~1+@test.org"                                                             =   0 =  OK 
     *  3619 - assertIsTrue  "{_test_}@test.org"                                                          =   0 =  OK 
     *  3620 - assertIsTrue  "valid@about.museum"                                                         =   0 =  OK 
     *  3621 - assertIsTrue  "a@bar"                                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3622 - assertIsFalse "cal(foo\@bar)@iamcal.com"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3623 - assertIsTrue  "(comment)test@test.org"                                                     =   6 =  OK 
     *  3624 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3625 - assertIsFalse "cal(foo\)bar)@iamcal.com"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3626 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.com"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3627 - assertIsFalse "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3628 - assertIsFalse "pete(his account)@silly.test(his host)"                                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3629 - assertIsFalse "first(abc\(def)@test.org"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3630 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3631 - assertIsTrue  "c@(Chris's host.)public.example"                                            =   6 =  OK 
     *  3632 - assertIsTrue  "_Yosemite.Sam@test.org"                                                     =   0 =  OK 
     *  3633 - assertIsTrue  "~@test.org"                                                                 =   0 =  OK 
     *  3634 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                              =   6 =  OK 
     *  3635 - assertIsTrue  "test@Bücher.ch"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3636 - assertIsTrue  "あいうえお@example.com"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3637 - assertIsTrue  "Pelé@example.com"                                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3638 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3639 - assertIsTrue  "我買@屋企.香港"                                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3640 - assertIsTrue  "二ノ宮@黒川.日本"                                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3641 - assertIsTrue  "медведь@с-балалайкой.рф"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3642 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3643 - assertIsTrue  "email@example.com (Joe Smith)"                                              =   6 =  OK 
     *  3644 - assertIsFalse "cal@iamcal(woo).(yay)com"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3645 - assertIsFalse "first(abc.def).last@test.org"                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3646 - assertIsFalse "first(a\"bc.def).last@test.org"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3647 - assertIsFalse "first.(\")middle.last(\")@test.org"                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  3648 - assertIsFalse "first().last@test.org"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3649 - assertIsTrue  "mymail\@hello@hotmail.com"                                                  =   0 =  OK 
     *  3650 - assertIsTrue  "Abc\@def@test.org"                                                          =   0 =  OK 
     *  3651 - assertIsTrue  "Fred\ Bloggs@test.org"                                                      =   0 =  OK 
     *  3652 - assertIsTrue  "Joe.\\Blow@test.org"                                                        =   0 =  OK 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ---------------------------
     * 
     *  3653 - assertIsTrue  "me@example.com"                                                             =   0 =  OK 
     *  3654 - assertIsTrue  "a.nonymous@example.com"                                                     =   0 =  OK 
     *  3655 - assertIsTrue  "name+tag@example.com"                                                       =   0 =  OK 
     *  3656 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                             =   2 =  OK 
     *  3657 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"          =   4 =  OK 
     *  3658 - assertIsTrue  "me(this is a comment)@example.com"                                          =   6 =  OK 
     *  3659 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                                  =   1 =  OK 
     *  3660 - assertIsTrue  "me.example@com"                                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3661 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                            =   0 =  OK 
     *  3662 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"        =   0 =  OK 
     *  3663 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                           =   0 =  OK 
     *  3664 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                          =   0 =  OK 
     *  3665 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                         =   0 =  OK 
     *  3666 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                        =   0 =  OK 
     *  3667 - assertIsFalse "NotAnEmail"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3668 - assertIsFalse "me@"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3669 - assertIsFalse "@example.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3670 - assertIsFalse ".me@example.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3671 - assertIsFalse "me@example..com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3672 - assertIsFalse "me\@example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3673 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3674 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3675 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3676 - assertIsFalse "semico...@gmail.com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- my old tests ----------------------------------------------------------------------------------------------------------------
     * 
     *  3677 - assertIsTrue  "A@B.CD"                                                                     =   0 =  OK 
     *  3678 - assertIsTrue  "A.\"B\"@C.DE"                                                               =   1 =  OK 
     *  3679 - assertIsTrue  "A.B@[1.2.3.4]"                                                              =   2 =  OK 
     *  3680 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                          =   3 =  OK 
     *  3681 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                 =   4 =  OK 
     *  3682 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                             =   5 =  OK 
     *  3683 - assertIsTrue  "(A)B@C.DE"                                                                  =   6 =  OK 
     *  3684 - assertIsTrue  "A(B)@C.DE"                                                                  =   6 =  OK 
     *  3685 - assertIsTrue  "(A)\"B\"@C.DE"                                                              =   7 =  OK 
     *  3686 - assertIsTrue  "\"A\"(B)@C.DE"                                                              =   7 =  OK 
     *  3687 - assertIsTrue  "(A)B@[1.2.3.4]"                                                             =   2 =  OK 
     *  3688 - assertIsTrue  "A(B)@[1.2.3.4]"                                                             =   2 =  OK 
     *  3689 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                         =   8 =  OK 
     *  3690 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                         =   8 =  OK 
     *  3691 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                =   4 =  OK 
     *  3692 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                =   4 =  OK 
     *  3693 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                            =   9 =  OK 
     *  3694 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                            =   9 =  OK 
     *  3695 - assertIsTrue  "a.b.c.d@domain.com"                                                         =   0 =  OK 
     *  3696 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3697 - assertIsFalse "ABC.DEF.GHI.JKL"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3698 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3699 - assertIsFalse "ABC.DEF @GHI.JKL"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3700 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3701 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3702 - assertIsFalse "ABC.DEF@"                                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3703 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3704 - assertIsFalse "ABC@DEF@GHI.JKL"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3705 - assertIsFalse "@%^%#$@#$@#.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3706 - assertIsFalse "email.domain.com"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3707 - assertIsFalse "email@domain@domain.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3708 - assertIsFalse "first@last@test.org"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3709 - assertIsFalse "@test@a.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3710 - assertIsFalse "@\"someStringThatMightBe@email.com"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3711 - assertIsFalse "test@@test.com"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3712 - assertIsFalse "ABCDEF@GHIJKL"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3713 - assertIsFalse "ABC.DEF@GHIJKL"                                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3714 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3715 - assertIsFalse "ABC.DEF@GHI.JKL."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3716 - assertIsFalse "ABC..DEF@GHI.JKL"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3717 - assertIsFalse "ABC.DEF@GHI..JKL"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3718 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3719 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3720 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3721 - assertIsFalse "ABC.DEF@."                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3722 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                       =   1 =  OK 
     *  3723 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                        =   0 =  OK 
     *  3724 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                           =   0 =  OK 
     *  3725 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                           =   0 =  OK 
     *  3726 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                            =   0 =  OK 
     *  3727 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                            =   0 =  OK 
     *  3728 - assertIsFalse "ABC.DEF@GHI.2KL"                                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3729 - assertIsFalse "ABC.DEF@GHI.JK-"                                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3730 - assertIsFalse "ABC.DEF@GHI.JK_"                                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3731 - assertIsFalse "ABC.DEF@-HI.JKL"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3732 - assertIsFalse "ABC.DEF@_HI.JKL"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3733 - assertIsFalse "ABC DEF@GHI.DE"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3734 - assertIsFalse "ABC.DEF@GHI DE"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3735 - assertIsFalse "A . B & C . D"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3736 - assertIsFalse " A . B & C . D"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3737 - assertIsFalse "(?).[!]@{&}.<:>"                                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3738 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3739 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
     * 
     * ---- unsupported -----------------------------------------------------------------------------------------------------------------
     * 
     *  3740 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3741 - assertIsTrue  "Ärger.Öde@Übel.de"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3742 - assertIsTrue  "Smørrebrød@danmark.dk"                                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3743 - assertIsTrue  "ip6.without.brackets@1:2:3:4:5:6:7:8"                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3744 - assertIsTrue  "email.address.without@topleveldomain"                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3745 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3746 - assertIsFalse "@1st.relay,@2nd.relay:user@final.domain"                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------------------------
     * 
     * Fillup ist nicht aktiv
     * 
     * 
     * ---- Statistik -------------------------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1250   KORREKT 1205 =   96.400 % | FALSCH ERKANNT   45 =    3.600 % = Error 0
     *   ASSERT_IS_FALSE 2496   KORREKT 2482 =   99.439 % | FALSCH ERKANNT   14 =    0.561 % = Error 0
     * 
     *   GESAMT          3746   KORREKT 3687 =   98.425 % | FALSCH ERKANNT   59 =    1.575 % = Error 0
     * 
     * 
     *   Millisekunden    120 = 0.03203416978109984
     * 
     * </pre> 
     */

    /*
     * Variable fuer die Startzeit der Funktion deklarieren und die aktuellen
     * System-Millisekunden speichern
     */
    long time_stamp_start = System.currentTimeMillis();

    generateTestCases();

    try
    {
      wlHeadline( "Correct" );

      assertIsTrue( "n@d.td" );
      assertIsTrue( "1@2.td" );
      assertIsTrue( "12.345@678.90.tld" );

      assertIsTrue( "name1.name2@domain1.tld" );
      assertIsTrue( "name1+name2@domain1.tld" );
      assertIsTrue( "name1-name2@domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.tu-domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.tu_domain1.tld" );

      assertIsTrue( "escaped.at\\@.sign@domain.tld" );
      assertIsTrue( "\"at.sign.@\".in.string@domain.tld" );

      assertIsTrue( "ip4.adress@[1.2.3.4]" );
      assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]" );
      assertIsTrue( "ip4.without.brackets@1.2.3.4" );

      assertIsTrue( "\"string1\".name1@domain1.tld" );
      assertIsTrue( "name1.\"string1\"@domain1.tld" );
      assertIsTrue( "name1.\"string1\".name2@domain1.tld" );
      assertIsTrue( "name1.\"string1\".name2@subdomain1.domain1.tld" );
      assertIsTrue( "\"string1\".\"quote2\".name1@domain1.tld" );
      assertIsTrue( "\"string1\"@domain1.tld" );
      assertIsTrue( "\"string1\\\"embedded string\\\"\"@domain1.tld" );
      assertIsTrue( "\"string1(embedded comment)\"@domain1.tld" );

      assertIsTrue( "(comment1)name1@domain1.tld" );
      assertIsTrue( "(comment1)-name1@domain1.tld" );
      assertIsTrue( "name1(comment1)@domain1.tld" );
      assertIsTrue( "name1@(comment1)domain1.tld" );
      assertIsTrue( "name1@domain1.tld(comment1)" );
      assertIsTrue( "(spaces after comment)     name1.name2@domain1.tld" );
      assertIsTrue( "name1.name2@domain1.tld   (spaces before comment)" );

      assertIsTrue( "(comment1.\\\"comment2)name1@domain1.tld" );

      assertIsTrue( "(comment1.\\\"String\\\")name1@domain1.tld" );
      assertIsTrue( "(comment1.\\\"String\\\".@domain.tld)name1@domain1.tld" );

      assertIsTrue( "(comment1)name1.ip4.adress@[1.2.3.4]" );
      assertIsTrue( "name1.ip4.adress(comment1)@[1.2.3.4]" );
      assertIsTrue( "name1.ip4.adress@(comment1)[1.2.3.4]" );
      assertIsTrue( "name1.ip4.adress@[1.2.3.4](comment1)" );

      assertIsTrue( "(comment1)\"string1\".name1@domain1.tld" );
      assertIsTrue( "(comment1)name1.\"string1\"@domain1.tld" );
      assertIsTrue( "name1.\"string1\"(comment1)@domain1.tld" );
      assertIsTrue( "\"string1\".name1(comment1)@domain1.tld" );
      assertIsTrue( "name1.\"string1\"@(comment1)domain1.tld" );
      assertIsTrue( "\"string1\".name1@domain1.tld(comment1)" );

      assertIsTrue( "<name1.name2@domain1.tld>" );
      assertIsTrue( "name3 <name1.name2@domain1.tld>" );
      assertIsTrue( "<name1.name2@domain1.tld> name3" );
      assertIsTrue( "\"name3 name4\" <name1.name2@domain1.tld>" );

      assertIsTrue( "name1 <ip4.adress@[1.2.3.4]>" );
      assertIsTrue( "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>" );
      assertIsTrue( "<ip4.adress@[1.2.3.4]> name1" );
      assertIsTrue( "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1" );

      assertIsTrue( "\"display name\" <(comment)local.part@domain-name.top_level_domain>" );
      assertIsTrue( "\"display name\" <local.part@(comment)domain-name.top_level_domain>" );
      assertIsTrue( "\"display name\" <(comment) local.part.\"string1\"@domain-name.top_level_domain>" );

      assertIsTrue( "\"display name \\\"string\\\" \" <(comment)local.part@domain-name.top_level_domain>" );
      assertIsTrue( "\"display name \\\"string\\\" \" <(comment)local.part.wiht.escaped.at\\@.sign@domain-name.top_level_domain>" );

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      wlHeadline( "AT-Sign" );

      assertIsFalse( "1234567890" );
      assertIsFalse( "OnlyTextNoDotNoAt" );
      assertIsFalse( "email.with.no.at.sign" );
      assertIsFalse( "email.with.no.domain@" );
      assertIsFalse( "@@domain.com" );

      /*
       * Eat this, you folks, that you are saying, only test for the occurance 
       * of one AT-Sign. 
       */
      assertIsFalse( "email.with.no.domain\\@domain.com" );
      assertIsFalse( "email.with.no.domain\\@.domain.com" );
      assertIsFalse( "email.with.no.domain\\@123domain.com" );
      assertIsFalse( "email.with.no.domain\\@_domain.com" );
      assertIsFalse( "email.with.no.domain\\@-domain.com" );
      assertIsFalse( "email.with.double\\@no.domain\\@domain.com" );
      assertIsTrue( "\"wrong.at.sign.combination.in.string1@.\"@domain.com" );
      assertIsTrue( "\"wrong.at.sign.combination.in.string2.@\"@domain.com" );

      assertIsTrue( "email.with.escaped.at\\@.sign.version1@domain.com" );
      assertIsTrue( "email.with.escaped.\\@.sign.version2@domain.com" );
      assertIsTrue( "email.with.escaped.at\\@123.sign.version3@domain.com" );
      assertIsTrue( "email.with.escaped.\\@123.sign.version4@domain.com" );
      assertIsTrue( "email.with.escaped.at\\@-.sign.version5@domain.com" );
      assertIsTrue( "email.with.escaped.\\@-.sign.version6@domain.com" );
      assertIsTrue( "email.with.escaped.at.sign.\\@@domain.com" );

      assertIsFalse( "@@email.with.unescaped.at.sign.as.local.part" );
      assertIsTrue( "\\@@email.with.escaped.at.sign.as.local.part" );
      assertIsFalse( "@.local.part.starts.with.at@domain.com" );
      assertIsFalse( "@no.local.part.com" );
      assertIsFalse( "@@@@@@only.multiple.at.signs.in.local.part.com" );

      assertIsFalse( "local.part.with.two.@at.signs@domain.com" );
      assertIsFalse( "local.part.ends.with.at.sign@@domain.com" );
      assertIsFalse( "local.part.with.at.sign.before@.point@domain.com" );
      assertIsFalse( "local.part.with.at.sign.after.@point@domain.com" );
      assertIsFalse( "local.part.with.double.at@@test@domain.com" );
      assertIsTrue( "(comment @) local.part.with.at.sign.in.comment@domain.com" );
      assertIsTrue( "domain.part.with.comment.with.at@(comment with @)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.qouted.at@(comment with \\@)domain.com" );
      assertIsTrue( "\"String@\".local.part.with.at.sign.in.string@domain.com" );
      assertIsTrue( "\\@.\\@.\\@.\\@.\\@.\\@@domain.com" );
      assertIsFalse( "\\@.\\@.\\@.\\@.\\@.\\@@at.sub\\@domain.com" );
      assertIsFalse( "@.@.@.@.@.@@domain.com" );
      assertIsFalse( "@.@.@." );
      assertIsFalse( "\\@.\\@@\\@.\\@" );
      assertIsFalse( "@" );
      assertIsFalse( "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @" );

      wlHeadline( "Seperator" );

      assertIsFalse( "EmailAdressWith@NoDots" );

      assertIsFalse( "..local.part.starts.with.dot@domain.com" );
      assertIsFalse( "local.part.ends.with.dot.@domain.com" );
      assertIsTrue( "local.part.with.dot.character@domain.com" );
      assertIsFalse( "local.part.with.dot.before..point@domain.com" );
      assertIsFalse( "local.part.with.dot.after..point@domain.com" );
      assertIsFalse( "local.part.with.double.dot..test@domain.com" );
      assertIsTrue( "(comment .) local.part.with.dot.in.comment@domain.com" );
      assertIsTrue( "\"string.\".local.part.with.dot.in.String@domain.com" );
      assertIsFalse( "\"string\\.\".local.part.with.escaped.dot.in.String@domain.com" );
      assertIsFalse( ".@local.part.only.dot.domain.com" );
      assertIsFalse( "......@local.part.only.consecutive.dot.domain.com" );
      assertIsFalse( "...........@dot.domain.com" );
      assertIsFalse( "name . <pointy.brackets1.with.dot.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.dot.in.display.name@domain.com> name ." );

      assertIsTrue( "domain.part@with.dot.com" );
      assertIsFalse( "domain.part@.with.dot.at.domain.start.com" );
      assertIsFalse( "domain.part@with.dot.at.domain.end1..com" );
      assertIsFalse( "domain.part@with.dot.at.domain.end2.com." );
      assertIsFalse( "domain.part@with.dot.before..point.com" );
      assertIsFalse( "domain.part@with.dot.after..point.com" );
      assertIsFalse( "domain.part@with.consecutive.dot..test.com" );
      assertIsTrue( "domain.part.with.dot.in.comment@(comment .)domain.com" );
      assertIsFalse( "domain.part.only.dot@..com" );
      assertIsFalse( "top.level.domain.only@dot.." );

      assertIsFalse( "...local.part.starts.with.double.dot@domain.com" );
      assertIsFalse( "local.part.ends.with.double.dot..@domain.com" );
      assertIsFalse( "local.part.with.double.dot..character@domain.com" );
      assertIsFalse( "local.part.with.double.dot.before...point@domain.com" );
      assertIsFalse( "local.part.with.double.dot.after...point@domain.com" );
      assertIsFalse( "local.part.with.double.double.dot....test@domain.com" );
      assertIsTrue( "(comment ..) local.part.with.double.dot.in.comment@domain.com" );
      assertIsTrue( "\"string..\".local.part.with.double.dot.in.String@domain.com" );
      assertIsFalse( "\"string\\..\".local.part.with.escaped.double.dot.in.String@domain.com" );
      assertIsFalse( "..@local.part.only.double.dot.domain.com" );
      assertIsFalse( "............@local.part.only.consecutive.double.dot.domain.com" );
      assertIsFalse( ".................@double.dot.domain.com" );
      assertIsFalse( "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .." );

      assertIsFalse( "domain.part@with..double.dot.com" );
      assertIsFalse( "domain.part@..with.double.dot.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.dot.at.domain.end1...com" );
      assertIsFalse( "domain.part@with.double.dot.at.domain.end2.com.." );
      assertIsFalse( "domain.part@with.double.dot.before...point.com" );
      assertIsFalse( "domain.part@with.double.dot.after...point.com" );
      assertIsFalse( "domain.part@with.consecutive.double.dot....test.com" );
      assertIsTrue( "domain.part.with.comment.with.double.dot@(comment ..)domain.com" );
      assertIsFalse( "domain.part.only.double.dot@...com" );
      assertIsFalse( "top.level.domain.only@double.dot..." );

      wlHeadline( "Characters" );

      assertIsTrue( "&local&&part&with&$@amp.com" );
      assertIsTrue( "*local**part*with*@asterisk.com" );
      assertIsTrue( "$local$$part$with$@dollar.com" );
      assertIsTrue( "=local==part=with=@equality.com" );
      assertIsTrue( "!local!!part!with!@exclamation.com" );
      assertIsTrue( "`local``part`with`@grave-accent.com" );
      assertIsTrue( "#local##part#with#@hash.com" );
      assertIsTrue( "-local--part-with-@hypen.com" );
      assertIsTrue( "{local{part{{with{@leftbracket.com" );
      assertIsTrue( "%local%%part%with%@percentage.com" );
      assertIsTrue( "|local||part|with|@pipe.com" );
      assertIsTrue( "+local++part+with+@plus.com" );
      assertIsTrue( "?local??part?with?@question.com" );
      assertIsTrue( "}local}part}}with}@rightbracket.com" );
      assertIsTrue( "~local~~part~with~@tilde.com" );
      assertIsTrue( "^local^^part^with^@xor.com" );
      assertIsTrue( "_local__part_with_@underscore.com" );
      assertIsFalse( ":local::part:with:@colon.com" );

      assertIsTrue( "&.local.part.starts.with.amp@domain.com" );
      assertIsTrue( "local.part.ends.with.amp&@domain.com" );
      assertIsTrue( "local.part.with.amp&character@domain.com" );
      assertIsTrue( "local.part.with.amp.before&.point@domain.com" );
      assertIsTrue( "local.part.with.amp.after.&point@domain.com" );
      assertIsTrue( "local.part.with.double.amp&&test@domain.com" );
      assertIsTrue( "(comment &) local.part.with.amp.in.comment@domain.com" );
      assertIsTrue( "\"string&\".local.part.with.amp.in.String@domain.com" );
      assertIsFalse( "\"string\\&\".local.part.with.escaped.amp.in.String@domain.com" );
      assertIsTrue( "&@local.part.only.amp.domain.com" );
      assertIsTrue( "&&&&&&@local.part.only.consecutive.amp.domain.com" );
      assertIsTrue( "&.&.&.&.&.&@amp.domain.com" );
      assertIsFalse( "name & <pointy.brackets1.with.amp.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.amp.in.display.name@domain.com> name &" );

      assertIsFalse( "domain.part@with&amp.com" );
      assertIsFalse( "domain.part@&with.amp.at.domain.start.com" );
      assertIsFalse( "domain.part@with.amp.at.domain.end1&.com" );
      assertIsFalse( "domain.part@with.amp.at.domain.end2.com&" );
      assertIsFalse( "domain.part@with.amp.before&.point.com" );
      assertIsFalse( "domain.part@with.amp.after.&point.com" );
      assertIsFalse( "domain.part@with.consecutive.amp&&test.com" );
      assertIsTrue( "domain.part.with.amp.in.comment@(comment &)domain.com" );
      assertIsFalse( "domain.part.only.amp@&.com" );
      assertIsFalse( "top.level.domain.only@amp.&" );

      assertIsTrue( "*.local.part.starts.with.asterisk@domain.com" );
      assertIsTrue( "local.part.ends.with.asterisk*@domain.com" );
      assertIsTrue( "local.part.with.asterisk*character@domain.com" );
      assertIsTrue( "local.part.with.asterisk.before*.point@domain.com" );
      assertIsTrue( "local.part.with.asterisk.after.*point@domain.com" );
      assertIsTrue( "local.part.with.double.asterisk**test@domain.com" );
      assertIsTrue( "(comment *) local.part.with.asterisk.in.comment@domain.com" );
      assertIsTrue( "\"string*\".local.part.with.asterisk.in.String@domain.com" );
      assertIsFalse( "\"string\\*\".local.part.with.escaped.asterisk.in.String@domain.com" );
      assertIsTrue( "*@local.part.only.asterisk.domain.com" );
      assertIsTrue( "******@local.part.only.consecutive.asterisk.domain.com" );
      assertIsTrue( "*.*.*.*.*.*@asterisk.domain.com" );
      assertIsFalse( "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *" );

      assertIsFalse( "domain.part@with*asterisk.com" );
      assertIsFalse( "domain.part@*with.asterisk.at.domain.start.com" );
      assertIsFalse( "domain.part@with.asterisk.at.domain.end1*.com" );
      assertIsFalse( "domain.part@with.asterisk.at.domain.end2.com*" );
      assertIsFalse( "domain.part@with.asterisk.before*.point.com" );
      assertIsFalse( "domain.part@with.asterisk.after.*point.com" );
      assertIsFalse( "domain.part@with.consecutive.asterisk**test.com" );
      assertIsTrue( "domain.part.with.asterisk.in.comment@(comment *)domain.com" );
      assertIsFalse( "domain.part.only.asterisk@*.com" );
      assertIsFalse( "top.level.domain.only@asterisk.*" );

      assertIsTrue( "_.local.part.starts.with.underscore@domain.com" );
      assertIsTrue( "local.part.ends.with.underscore_@domain.com" );
      assertIsTrue( "local.part.with.underscore_character@domain.com" );
      assertIsTrue( "local.part.with.underscore.before_.point@domain.com" );
      assertIsTrue( "local.part.with.underscore.after._point@domain.com" );
      assertIsTrue( "local.part.with.double.underscore__test@domain.com" );
      assertIsTrue( "(comment _) local.part.with.underscore.in.comment@domain.com" );
      assertIsTrue( "\"string_\".local.part.with.underscore.in.String@domain.com" );
      assertIsFalse( "\"string\\_\".local.part.with.escaped.underscore.in.String@domain.com" );
      assertIsTrue( "_@local.part.only.underscore.domain.com" );
      assertIsTrue( "______@local.part.only.consecutive.underscore.domain.com" );
      assertIsTrue( "_._._._._._@underscore.domain.com" );
      assertIsFalse( "name _ <pointy.brackets1.with.underscore.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.underscore.in.display.name@domain.com> name _" );

      assertIsTrue( "domain.part@with_underscore.com" );
      assertIsFalse( "domain.part@_with.underscore.at.domain.start.com" );
      assertIsFalse( "domain.part@with.underscore.at.domain.end1_.com" );
      assertIsFalse( "domain.part@with.underscore.at.domain.end2.com_" );
      assertIsFalse( "domain.part@with.underscore.before_.point.com" );
      assertIsFalse( "domain.part@with.underscore.after._point.com" );
      assertIsTrue( "domain.part@with.consecutive.underscore__test.com" );
      assertIsTrue( "domain.part.with.underscore.in.comment@(comment _)domain.com" );
      assertIsFalse( "domain.part.only.underscore@_.com" );
      assertIsFalse( "top.level.domain.only@underscore._" );

      assertIsTrue( "$.local.part.starts.with.dollar@domain.com" );
      assertIsTrue( "local.part.ends.with.dollar$@domain.com" );
      assertIsTrue( "local.part.with.dollar$character@domain.com" );
      assertIsTrue( "local.part.with.dollar.before$.point@domain.com" );
      assertIsTrue( "local.part.with.dollar.after.$point@domain.com" );
      assertIsTrue( "local.part.with.double.dollar$$test@domain.com" );
      assertIsTrue( "(comment $) local.part.with.dollar.in.comment@domain.com" );
      assertIsTrue( "\"string$\".local.part.with.dollar.in.String@domain.com" );
      assertIsFalse( "\"string\\$\".local.part.with.escaped.dollar.in.String@domain.com" );
      assertIsTrue( "$@local.part.only.dollar.domain.com" );
      assertIsTrue( "$$$$$$@local.part.only.consecutive.dollar.domain.com" );
      assertIsTrue( "$.$.$.$.$.$@dollar.domain.com" );
      assertIsFalse( "name $ <pointy.brackets1.with.dollar.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.dollar.in.display.name@domain.com> name $" );

      assertIsFalse( "domain.part@with$dollar.com" );
      assertIsFalse( "domain.part@$with.dollar.at.domain.start.com" );
      assertIsFalse( "domain.part@with.dollar.at.domain.end1$.com" );
      assertIsFalse( "domain.part@with.dollar.at.domain.end2.com$" );
      assertIsFalse( "domain.part@with.dollar.before$.point.com" );
      assertIsFalse( "domain.part@with.dollar.after.$point.com" );
      assertIsFalse( "domain.part@with.consecutive.dollar$$test.com" );
      assertIsTrue( "domain.part.with.dollar.in.comment@(comment $)domain.com" );
      assertIsFalse( "domain.part.only.dollar@$.com" );
      assertIsFalse( "top.level.domain.only@dollar.$" );

      assertIsTrue( "=.local.part.starts.with.equality@domain.com" );
      assertIsTrue( "local.part.ends.with.equality=@domain.com" );
      assertIsTrue( "local.part.with.equality=character@domain.com" );
      assertIsTrue( "local.part.with.equality.before=.point@domain.com" );
      assertIsTrue( "local.part.with.equality.after.=point@domain.com" );
      assertIsTrue( "local.part.with.double.equality==test@domain.com" );
      assertIsTrue( "(comment =) local.part.with.equality.in.comment@domain.com" );
      assertIsTrue( "\"string=\".local.part.with.equality.in.String@domain.com" );
      assertIsFalse( "\"string\\=\".local.part.with.escaped.equality.in.String@domain.com" );
      assertIsTrue( "=@local.part.only.equality.domain.com" );
      assertIsTrue( "======@local.part.only.consecutive.equality.domain.com" );
      assertIsTrue( "=.=.=.=.=.=@equality.domain.com" );
      assertIsFalse( "name = <pointy.brackets1.with.equality.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.equality.in.display.name@domain.com> name =" );

      assertIsFalse( "domain.part@with=equality.com" );
      assertIsFalse( "domain.part@=with.equality.at.domain.start.com" );
      assertIsFalse( "domain.part@with.equality.at.domain.end1=.com" );
      assertIsFalse( "domain.part@with.equality.at.domain.end2.com=" );
      assertIsFalse( "domain.part@with.equality.before=.point.com" );
      assertIsFalse( "domain.part@with.equality.after.=point.com" );
      assertIsFalse( "domain.part@with.consecutive.equality==test.com" );
      assertIsTrue( "domain.part.with.equality.in.comment@(comment =)domain.com" );
      assertIsFalse( "domain.part.only.equality@=.com" );
      assertIsFalse( "top.level.domain.only@equality.=" );

      assertIsTrue( "!.local.part.starts.with.exclamation@domain.com" );
      assertIsTrue( "local.part.ends.with.exclamation!@domain.com" );
      assertIsTrue( "local.part.with.exclamation!character@domain.com" );
      assertIsTrue( "local.part.with.exclamation.before!.point@domain.com" );
      assertIsTrue( "local.part.with.exclamation.after.!point@domain.com" );
      assertIsTrue( "local.part.with.double.exclamation!!test@domain.com" );
      assertIsTrue( "(comment !) local.part.with.exclamation.in.comment@domain.com" );
      assertIsTrue( "\"string!\".local.part.with.exclamation.in.String@domain.com" );
      assertIsFalse( "\"string\\!\".local.part.with.escaped.exclamation.in.String@domain.com" );
      assertIsTrue( "!@local.part.only.exclamation.domain.com" );
      assertIsTrue( "!!!!!!@local.part.only.consecutive.exclamation.domain.com" );
      assertIsTrue( "!.!.!.!.!.!@exclamation.domain.com" );
      assertIsFalse( "name ! <pointy.brackets1.with.exclamation.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.exclamation.in.display.name@domain.com> name !" );

      assertIsFalse( "domain.part@with!exclamation.com" );
      assertIsFalse( "domain.part@!with.exclamation.at.domain.start.com" );
      assertIsFalse( "domain.part@with.exclamation.at.domain.end1!.com" );
      assertIsFalse( "domain.part@with.exclamation.at.domain.end2.com!" );
      assertIsFalse( "domain.part@with.exclamation.before!.point.com" );
      assertIsFalse( "domain.part@with.exclamation.after.!point.com" );
      assertIsFalse( "domain.part@with.consecutive.exclamation!!test.com" );
      assertIsTrue( "domain.part.with.exclamation.in.comment@(comment !)domain.com" );
      assertIsFalse( "domain.part.only.exclamation@!.com" );
      assertIsFalse( "top.level.domain.only@exclamation.!" );

      assertIsTrue( "?.local.part.starts.with.question@domain.com" );
      assertIsTrue( "local.part.ends.with.question?@domain.com" );
      assertIsTrue( "local.part.with.question?character@domain.com" );
      assertIsTrue( "local.part.with.question.before?.point@domain.com" );
      assertIsTrue( "local.part.with.question.after.?point@domain.com" );
      assertIsTrue( "local.part.with.double.question??test@domain.com" );
      assertIsTrue( "(comment ?) local.part.with.question.in.comment@domain.com" );
      assertIsTrue( "\"string?\".local.part.with.question.in.String@domain.com" );
      assertIsFalse( "\"string\\?\".local.part.with.escaped.question.in.String@domain.com" );
      assertIsTrue( "?@local.part.only.question.domain.com" );
      assertIsTrue( "??????@local.part.only.consecutive.question.domain.com" );
      assertIsTrue( "?.?.?.?.?.?@question.domain.com" );
      assertIsFalse( "name ? <pointy.brackets1.with.question.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.question.in.display.name@domain.com> name ?" );

      assertIsFalse( "domain.part@with?question.com" );
      assertIsFalse( "domain.part@?with.question.at.domain.start.com" );
      assertIsFalse( "domain.part@with.question.at.domain.end1?.com" );
      assertIsFalse( "domain.part@with.question.at.domain.end2.com?" );
      assertIsFalse( "domain.part@with.question.before?.point.com" );
      assertIsFalse( "domain.part@with.question.after.?point.com" );
      assertIsFalse( "domain.part@with.consecutive.question??test.com" );
      assertIsTrue( "domain.part.with.question.in.comment@(comment ?)domain.com" );
      assertIsFalse( "domain.part.only.question@?.com" );
      assertIsFalse( "top.level.domain.only@question.?" );

      assertIsTrue( "`.local.part.starts.with.grave-accent@domain.com" );
      assertIsTrue( "local.part.ends.with.grave-accent`@domain.com" );
      assertIsTrue( "local.part.with.grave-accent`character@domain.com" );
      assertIsTrue( "local.part.with.grave-accent.before`.point@domain.com" );
      assertIsTrue( "local.part.with.grave-accent.after.`point@domain.com" );
      assertIsTrue( "local.part.with.double.grave-accent``test@domain.com" );
      assertIsTrue( "(comment `) local.part.with.grave-accent.in.comment@domain.com" );
      assertIsTrue( "\"string`\".local.part.with.grave-accent.in.String@domain.com" );
      assertIsFalse( "\"string\\`\".local.part.with.escaped.grave-accent.in.String@domain.com" );
      assertIsTrue( "`@local.part.only.grave-accent.domain.com" );
      assertIsTrue( "``````@local.part.only.consecutive.grave-accent.domain.com" );
      assertIsTrue( "`.`.`.`.`.`@grave-accent.domain.com" );
      assertIsFalse( "name ` <pointy.brackets1.with.grave-accent.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.grave-accent.in.display.name@domain.com> name `" );

      assertIsFalse( "domain.part@with`grave-accent.com" );
      assertIsFalse( "domain.part@`with.grave-accent.at.domain.start.com" );
      assertIsFalse( "domain.part@with.grave-accent.at.domain.end1`.com" );
      assertIsFalse( "domain.part@with.grave-accent.at.domain.end2.com`" );
      assertIsFalse( "domain.part@with.grave-accent.before`.point.com" );
      assertIsFalse( "domain.part@with.grave-accent.after.`point.com" );
      assertIsFalse( "domain.part@with.consecutive.grave-accent``test.com" );
      assertIsTrue( "domain.part.with.grave-accent.in.comment@(comment `)domain.com" );
      assertIsFalse( "domain.part.only.grave-accent@`.com" );
      assertIsFalse( "top.level.domain.only@grave-accent.`" );

      assertIsTrue( "#.local.part.starts.with.hash@domain.com" );
      assertIsTrue( "local.part.ends.with.hash#@domain.com" );
      assertIsTrue( "local.part.with.hash#character@domain.com" );
      assertIsTrue( "local.part.with.hash.before#.point@domain.com" );
      assertIsTrue( "local.part.with.hash.after.#point@domain.com" );
      assertIsTrue( "local.part.with.double.hash##test@domain.com" );
      assertIsTrue( "(comment #) local.part.with.hash.in.comment@domain.com" );
      assertIsTrue( "\"string#\".local.part.with.hash.in.String@domain.com" );
      assertIsFalse( "\"string\\#\".local.part.with.escaped.hash.in.String@domain.com" );
      assertIsTrue( "#@local.part.only.hash.domain.com" );
      assertIsTrue( "######@local.part.only.consecutive.hash.domain.com" );
      assertIsTrue( "#.#.#.#.#.#@hash.domain.com" );
      assertIsFalse( "name # <pointy.brackets1.with.hash.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.hash.in.display.name@domain.com> name #" );

      assertIsFalse( "domain.part@with#hash.com" );
      assertIsFalse( "domain.part@#with.hash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.hash.at.domain.end1#.com" );
      assertIsFalse( "domain.part@with.hash.at.domain.end2.com#" );
      assertIsFalse( "domain.part@with.hash.before#.point.com" );
      assertIsFalse( "domain.part@with.hash.after.#point.com" );
      assertIsFalse( "domain.part@with.consecutive.hash##test.com" );
      assertIsTrue( "domain.part.with.hash.in.comment@(comment #)domain.com" );
      assertIsFalse( "domain.part.only.hash@#.com" );
      assertIsFalse( "top.level.domain.only@hash.#" );

      assertIsTrue( "%.local.part.starts.with.percentage@domain.com" );
      assertIsTrue( "local.part.ends.with.percentage%@domain.com" );
      assertIsTrue( "local.part.with.percentage%character@domain.com" );
      assertIsTrue( "local.part.with.percentage.before%.point@domain.com" );
      assertIsTrue( "local.part.with.percentage.after.%point@domain.com" );
      assertIsTrue( "local.part.with.double.percentage%%test@domain.com" );
      assertIsTrue( "(comment %) local.part.with.percentage.in.comment@domain.com" );
      assertIsTrue( "\"string%\".local.part.with.percentage.in.String@domain.com" );
      assertIsFalse( "\"string\\%\".local.part.with.escaped.percentage.in.String@domain.com" );
      assertIsTrue( "%@local.part.only.percentage.domain.com" );
      assertIsTrue( "%%%%%%@local.part.only.consecutive.percentage.domain.com" );
      assertIsTrue( "%.%.%.%.%.%@percentage.domain.com" );
      assertIsFalse( "name % <pointy.brackets1.with.percentage.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.percentage.in.display.name@domain.com> name %" );

      assertIsFalse( "domain.part@with%percentage.com" );
      assertIsFalse( "domain.part@%with.percentage.at.domain.start.com" );
      assertIsFalse( "domain.part@with.percentage.at.domain.end1%.com" );
      assertIsFalse( "domain.part@with.percentage.at.domain.end2.com%" );
      assertIsFalse( "domain.part@with.percentage.before%.point.com" );
      assertIsFalse( "domain.part@with.percentage.after.%point.com" );
      assertIsFalse( "domain.part@with.consecutive.percentage%%test.com" );
      assertIsTrue( "domain.part.with.percentage.in.comment@(comment %)domain.com" );
      assertIsFalse( "domain.part.only.percentage@%.com" );
      assertIsFalse( "top.level.domain.only@percentage.%" );

      assertIsTrue( "|.local.part.starts.with.pipe@domain.com" );
      assertIsTrue( "local.part.ends.with.pipe|@domain.com" );
      assertIsTrue( "local.part.with.pipe|character@domain.com" );
      assertIsTrue( "local.part.with.pipe.before|.point@domain.com" );
      assertIsTrue( "local.part.with.pipe.after.|point@domain.com" );
      assertIsTrue( "local.part.with.double.pipe||test@domain.com" );
      assertIsTrue( "(comment |) local.part.with.pipe.in.comment@domain.com" );
      assertIsTrue( "\"string|\".local.part.with.pipe.in.String@domain.com" );
      assertIsFalse( "\"string\\|\".local.part.with.escaped.pipe.in.String@domain.com" );
      assertIsTrue( "|@local.part.only.pipe.domain.com" );
      assertIsTrue( "||||||@local.part.only.consecutive.pipe.domain.com" );
      assertIsTrue( "|.|.|.|.|.|@pipe.domain.com" );
      assertIsFalse( "name | <pointy.brackets1.with.pipe.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.pipe.in.display.name@domain.com> name |" );

      assertIsFalse( "domain.part@with|pipe.com" );
      assertIsFalse( "domain.part@|with.pipe.at.domain.start.com" );
      assertIsFalse( "domain.part@with.pipe.at.domain.end1|.com" );
      assertIsFalse( "domain.part@with.pipe.at.domain.end2.com|" );
      assertIsFalse( "domain.part@with.pipe.before|.point.com" );
      assertIsFalse( "domain.part@with.pipe.after.|point.com" );
      assertIsFalse( "domain.part@with.consecutive.pipe||test.com" );
      assertIsTrue( "domain.part.with.pipe.in.comment@(comment |)domain.com" );
      assertIsFalse( "domain.part.only.pipe@|.com" );
      assertIsFalse( "top.level.domain.only@pipe.|" );

      assertIsTrue( "+.local.part.starts.with.plus@domain.com" );
      assertIsTrue( "local.part.ends.with.plus+@domain.com" );
      assertIsTrue( "local.part.with.plus+character@domain.com" );
      assertIsTrue( "local.part.with.plus.before+.point@domain.com" );
      assertIsTrue( "local.part.with.plus.after.+point@domain.com" );
      assertIsTrue( "local.part.with.double.plus++test@domain.com" );
      assertIsTrue( "(comment +) local.part.with.plus.in.comment@domain.com" );
      assertIsTrue( "\"string+\".local.part.with.plus.in.String@domain.com" );
      assertIsFalse( "\"string\\+\".local.part.with.escaped.plus.in.String@domain.com" );
      assertIsTrue( "+@local.part.only.plus.domain.com" );
      assertIsTrue( "++++++@local.part.only.consecutive.plus.domain.com" );
      assertIsTrue( "+.+.+.+.+.+@plus.domain.com" );
      assertIsFalse( "name + <pointy.brackets1.with.plus.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.plus.in.display.name@domain.com> name +" );

      assertIsFalse( "domain.part@with+plus.com" );
      assertIsFalse( "domain.part@+with.plus.at.domain.start.com" );
      assertIsFalse( "domain.part@with.plus.at.domain.end1+.com" );
      assertIsFalse( "domain.part@with.plus.at.domain.end2.com+" );
      assertIsFalse( "domain.part@with.plus.before+.point.com" );
      assertIsFalse( "domain.part@with.plus.after.+point.com" );
      assertIsFalse( "domain.part@with.consecutive.plus++test.com" );
      assertIsTrue( "domain.part.with.plus.in.comment@(comment +)domain.com" );
      assertIsFalse( "domain.part.only.plus@+.com" );
      assertIsFalse( "top.level.domain.only@plus.+" );

      assertIsTrue( "{.local.part.starts.with.leftbracket@domain.com" );
      assertIsTrue( "local.part.ends.with.leftbracket{@domain.com" );
      assertIsTrue( "local.part.with.leftbracket{character@domain.com" );
      assertIsTrue( "local.part.with.leftbracket.before{.point@domain.com" );
      assertIsTrue( "local.part.with.leftbracket.after.{point@domain.com" );
      assertIsTrue( "local.part.with.double.leftbracket{{test@domain.com" );
      assertIsTrue( "(comment {) local.part.with.leftbracket.in.comment@domain.com" );
      assertIsTrue( "\"string{\".local.part.with.leftbracket.in.String@domain.com" );
      assertIsFalse( "\"string\\{\".local.part.with.escaped.leftbracket.in.String@domain.com" );
      assertIsTrue( "{@local.part.only.leftbracket.domain.com" );
      assertIsTrue( "{{{{{{@local.part.only.consecutive.leftbracket.domain.com" );
      assertIsTrue( "{.{.{.{.{.{@leftbracket.domain.com" );
      assertIsFalse( "name { <pointy.brackets1.with.leftbracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name {" );

      assertIsFalse( "domain.part@with{leftbracket.com" );
      assertIsFalse( "domain.part@{with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1{.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com{" );
      assertIsFalse( "domain.part@with.leftbracket.before{.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.{point.com" );
      assertIsFalse( "domain.part@with.consecutive.leftbracket{{test.com" );
      assertIsTrue( "domain.part.with.leftbracket.in.comment@(comment {)domain.com" );
      assertIsFalse( "domain.part.only.leftbracket@{.com" );
      assertIsFalse( "top.level.domain.only@leftbracket.{" );

      assertIsTrue( "}.local.part.starts.with.rightbracket@domain.com" );
      assertIsTrue( "local.part.ends.with.rightbracket}@domain.com" );
      assertIsTrue( "local.part.with.rightbracket}character@domain.com" );
      assertIsTrue( "local.part.with.rightbracket.before}.point@domain.com" );
      assertIsTrue( "local.part.with.rightbracket.after.}point@domain.com" );
      assertIsTrue( "local.part.with.double.rightbracket}}test@domain.com" );
      assertIsTrue( "(comment }) local.part.with.rightbracket.in.comment@domain.com" );
      assertIsTrue( "\"string}\".local.part.with.rightbracket.in.String@domain.com" );
      assertIsFalse( "\"string\\}\".local.part.with.escaped.rightbracket.in.String@domain.com" );
      assertIsTrue( "}@local.part.only.rightbracket.domain.com" );
      assertIsTrue( "}}}}}}@local.part.only.consecutive.rightbracket.domain.com" );
      assertIsTrue( "}.}.}.}.}.}@rightbracket.domain.com" );
      assertIsFalse( "name } <pointy.brackets1.with.rightbracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name }" );

      assertIsFalse( "domain.part@with}rightbracket.com" );
      assertIsFalse( "domain.part@}with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1}.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com}" );
      assertIsFalse( "domain.part@with.rightbracket.before}.point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.}point.com" );
      assertIsFalse( "domain.part@with.consecutive.rightbracket}}test.com" );
      assertIsTrue( "domain.part.with.rightbracket.in.comment@(comment })domain.com" );
      assertIsFalse( "domain.part.only.rightbracket@}.com" );
      assertIsFalse( "top.level.domain.only@rightbracket.}" );

      assertIsFalse( "(.local.part.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.leftbracket(@domain.com" );
      assertIsFalse( "local.part.with.leftbracket(character@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.before(.point@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.after.(point@domain.com" );
      assertIsFalse( "local.part.with.double.leftbracket((test@domain.com" );
      assertIsFalse( "(comment () local.part.with.leftbracket.in.comment@domain.com" );
      assertIsTrue( "\"string(\".local.part.with.leftbracket.in.String@domain.com" );
      assertIsFalse( "\"string\\(\".local.part.with.escaped.leftbracket.in.String@domain.com" );
      assertIsFalse( "(@local.part.only.leftbracket.domain.com" );
      assertIsFalse( "((((((@local.part.only.consecutive.leftbracket.domain.com" );
      assertIsFalse( "(.(.(.(.(.(@leftbracket.domain.com" );
      assertIsTrue( "name ( <pointy.brackets1.with.leftbracket.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name (" );

      assertIsFalse( "domain.part@with(leftbracket.com" );
      assertIsFalse( "domain.part@(with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1(.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com(" );
      assertIsFalse( "domain.part@with.leftbracket.before(.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.(point.com" );
      assertIsFalse( "domain.part@with.consecutive.leftbracket((test.com" );
      assertIsFalse( "domain.part.with.leftbracket.in.comment@(comment ()domain.com" );
      assertIsFalse( "domain.part.only.leftbracket@(.com" );
      assertIsFalse( "top.level.domain.only@leftbracket.(" );

      assertIsFalse( ").local.part.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.rightbracket)@domain.com" );
      assertIsFalse( "local.part.with.rightbracket)character@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.before).point@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.after.)point@domain.com" );
      assertIsFalse( "local.part.with.double.rightbracket))test@domain.com" );
      assertIsFalse( "(comment )) local.part.with.rightbracket.in.comment@domain.com" );
      assertIsTrue( "\"string)\".local.part.with.rightbracket.in.String@domain.com" );
      assertIsFalse( "\"string\\)\".local.part.with.escaped.rightbracket.in.String@domain.com" );
      assertIsFalse( ")@local.part.only.rightbracket.domain.com" );
      assertIsFalse( "))))))@local.part.only.consecutive.rightbracket.domain.com" );
      assertIsFalse( ").).).).).)@rightbracket.domain.com" );
      assertIsTrue( "name ) <pointy.brackets1.with.rightbracket.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name )" );

      assertIsFalse( "domain.part@with)rightbracket.com" );
      assertIsFalse( "domain.part@)with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1).com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com)" );
      assertIsFalse( "domain.part@with.rightbracket.before).point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.)point.com" );
      assertIsFalse( "domain.part@with.consecutive.rightbracket))test.com" );
      assertIsFalse( "domain.part.with.rightbracket.in.comment@(comment ))domain.com" );
      assertIsFalse( "domain.part.only.rightbracket@).com" );
      assertIsFalse( "top.level.domain.only@rightbracket.)" );

      assertIsFalse( "[.local.part.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.leftbracket[@domain.com" );
      assertIsFalse( "local.part.with.leftbracket[character@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.before[.point@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.after.[point@domain.com" );
      assertIsFalse( "local.part.with.double.leftbracket[[test@domain.com" );
      assertIsFalse( "(comment [) local.part.with.leftbracket.in.comment@domain.com" );
      assertIsTrue( "\"string[\".local.part.with.leftbracket.in.String@domain.com" );
      assertIsFalse( "\"string\\[\".local.part.with.escaped.leftbracket.in.String@domain.com" );
      assertIsFalse( "[@local.part.only.leftbracket.domain.com" );
      assertIsFalse( "[[[[[[@local.part.only.consecutive.leftbracket.domain.com" );
      assertIsFalse( "[.[.[.[.[.[@leftbracket.domain.com" );
      assertIsFalse( "name [ <pointy.brackets1.with.leftbracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name [" );

      assertIsFalse( "domain.part@with[leftbracket.com" );
      assertIsFalse( "domain.part@[with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1[.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com[" );
      assertIsFalse( "domain.part@with.leftbracket.before[.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.[point.com" );
      assertIsFalse( "domain.part@with.consecutive.leftbracket[[test.com" );
      assertIsFalse( "domain.part.with.leftbracket.in.comment@(comment [)domain.com" );
      assertIsFalse( "domain.part.only.leftbracket@[.com" );
      assertIsFalse( "top.level.domain.only@leftbracket.[" );

      assertIsFalse( "].local.part.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.rightbracket]@domain.com" );
      assertIsFalse( "local.part.with.rightbracket]character@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.before].point@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.after.]point@domain.com" );
      assertIsFalse( "local.part.with.double.rightbracket]]test@domain.com" );
      assertIsFalse( "(comment ]) local.part.with.rightbracket.in.comment@domain.com" );
      assertIsTrue( "\"string]\".local.part.with.rightbracket.in.String@domain.com" );
      assertIsFalse( "\"string\\]\".local.part.with.escaped.rightbracket.in.String@domain.com" );
      assertIsFalse( "]@local.part.only.rightbracket.domain.com" );
      assertIsFalse( "]]]]]]@local.part.only.consecutive.rightbracket.domain.com" );
      assertIsFalse( "].].].].].]@rightbracket.domain.com" );
      assertIsFalse( "name ] <pointy.brackets1.with.rightbracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name ]" );

      assertIsFalse( "domain.part@with]rightbracket.com" );
      assertIsFalse( "domain.part@]with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1].com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com]" );
      assertIsFalse( "domain.part@with.rightbracket.before].point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.]point.com" );
      assertIsFalse( "domain.part@with.consecutive.rightbracket]]test.com" );
      assertIsFalse( "domain.part.with.rightbracket.in.comment@(comment ])domain.com" );
      assertIsFalse( "domain.part.only.rightbracket@].com" );
      assertIsFalse( "top.level.domain.only@rightbracket.]" );

      assertIsFalse( "().local.part.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.part.ends.with.empty.bracket()@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket()character@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.before().point@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.after.()point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.bracket()()test@domain.com" );
      assertIsFalse( "(comment ()) local.part.with.empty.bracket.in.comment@domain.com" );
      assertIsTrue( "\"string()\".local.part.with.empty.bracket.in.String@domain.com" );
      assertIsFalse( "\"string\\()\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
      assertIsFalse( "()@local.part.only.empty.bracket.domain.com" );
      assertIsFalse( "()()()()()()@local.part.only.consecutive.empty.bracket.domain.com" );
      assertIsFalse( "().().().().().()@empty.bracket.domain.com" );
      assertIsTrue( "name () <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name ()" );

      assertIsFalse( "domain.part@with()empty.bracket.com" );
      assertIsTrue( "domain.part@()with.empty.bracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1().com" );
      assertIsTrue( "domain.part@with.empty.bracket.at.domain.end2.com()" );
      assertIsFalse( "domain.part@with.empty.bracket.before().point.com" );
      assertIsFalse( "domain.part@with.empty.bracket.after.()point.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket()()test.com" );
      assertIsFalse( "domain.part.with.empty.bracket.in.comment@(comment ())domain.com" );
      assertIsFalse( "domain.part.only.empty.bracket@().com" );
      assertIsFalse( "top.level.domain.only@empty.bracket.()" );

      assertIsTrue( "{}.local.part.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.part.ends.with.empty.bracket{}@domain.com" );
      assertIsTrue( "local.part.with.empty.bracket{}character@domain.com" );
      assertIsTrue( "local.part.with.empty.bracket.before{}.point@domain.com" );
      assertIsTrue( "local.part.with.empty.bracket.after.{}point@domain.com" );
      assertIsTrue( "local.part.with.double.empty.bracket{}{}test@domain.com" );
      assertIsTrue( "(comment {}) local.part.with.empty.bracket.in.comment@domain.com" );
      assertIsTrue( "\"string{}\".local.part.with.empty.bracket.in.String@domain.com" );
      assertIsFalse( "\"string\\{}\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
      assertIsTrue( "{}@local.part.only.empty.bracket.domain.com" );
      assertIsTrue( "{}{}{}{}{}{}@local.part.only.consecutive.empty.bracket.domain.com" );
      assertIsTrue( "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com" );
      assertIsFalse( "name {} <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name {}" );

      assertIsFalse( "domain.part@with{}empty.bracket.com" );
      assertIsFalse( "domain.part@{}with.empty.bracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1{}.com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end2.com{}" );
      assertIsFalse( "domain.part@with.empty.bracket.before{}.point.com" );
      assertIsFalse( "domain.part@with.empty.bracket.after.{}point.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket{}{}test.com" );
      assertIsTrue( "domain.part.with.empty.bracket.in.comment@(comment {})domain.com" );
      assertIsFalse( "domain.part.only.empty.bracket@{}.com" );
      assertIsFalse( "top.level.domain.only@empty.bracket.{}" );

      assertIsFalse( "[].local.part.starts.with.empty.bracket@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.bracket[]@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket[]character@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.before[].point@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.after.[]point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.bracket[][]test@domain.com" );
      assertIsFalse( "(comment []) local.part.with.empty.bracket.in.comment@domain.com" );
      assertIsTrue( "\"string[]\".local.part.with.empty.bracket.in.String@domain.com" );
      assertIsFalse( "\"string\\[]\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
      assertIsFalse( "[]@local.part.only.empty.bracket.domain.com" );
      assertIsFalse( "[][][][][][]@local.part.only.consecutive.empty.bracket.domain.com" );
      assertIsFalse( "[].[].[].[].[].[]@empty.bracket.domain.com" );
      assertIsFalse( "name [] <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name []" );

      assertIsFalse( "domain.part@with[]empty.bracket.com" );
      assertIsFalse( "domain.part@[]with.empty.bracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1[].com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end2.com[]" );
      assertIsFalse( "domain.part@with.empty.bracket.before[].point.com" );
      assertIsFalse( "domain.part@with.empty.bracket.after.[]point.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket[][]test.com" );
      assertIsFalse( "domain.part.with.empty.bracket.in.comment@(comment [])domain.com" );
      assertIsFalse( "domain.part.only.empty.bracket@[].com" );
      assertIsFalse( "top.level.domain.only@empty.bracket.[]" );

      assertIsFalse( "<>.local.part.starts.with.empty.bracket@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.bracket<>@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket<>character@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.before<>.point@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.after.<>point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.bracket<><>test@domain.com" );
      assertIsFalse( "(comment <>) local.part.with.empty.bracket.in.comment@domain.com" );
      assertIsTrue( "\"string<>\".local.part.with.empty.bracket.in.String@domain.com" );
      assertIsFalse( "\"string\\<>\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
      assertIsFalse( "<>@local.part.only.empty.bracket.domain.com" );
      assertIsFalse( "<><><><><><>@local.part.only.consecutive.empty.bracket.domain.com" );
      assertIsFalse( "<>.<>.<>.<>.<>.<>@empty.bracket.domain.com" );
      assertIsFalse( "name <> <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name <>" );

      assertIsFalse( "domain.part@with<>empty.bracket.com" );
      assertIsFalse( "domain.part@<>with.empty.bracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1<>.com" );
      assertIsFalse( "domain.part@with.empty.bracket.at.domain.end2.com<>" );
      assertIsFalse( "domain.part@with.empty.bracket.before<>.point.com" );
      assertIsFalse( "domain.part@with.empty.bracket.after.<>point.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket<><>test.com" );
      assertIsFalse( "domain.part.with.empty.bracket.in.comment@(comment <>)domain.com" );
      assertIsFalse( "domain.part.only.empty.bracket@<>.com" );
      assertIsFalse( "top.level.domain.only@empty.bracket.<>" );

      assertIsFalse( ")(.local.part.starts.with.false.bracket1@domain.com" );
      assertIsFalse( "local.part.ends.with.false.bracket1)(@domain.com" );
      assertIsFalse( "local.part.with.false.bracket1)(character@domain.com" );
      assertIsFalse( "local.part.with.false.bracket1.before)(.point@domain.com" );
      assertIsFalse( "local.part.with.false.bracket1.after.)(point@domain.com" );
      assertIsFalse( "local.part.with.double.false.bracket1)()(test@domain.com" );
      assertIsFalse( "(comment )() local.part.with.false.bracket1.in.comment@domain.com" );
      assertIsTrue( "\"string)(\".local.part.with.false.bracket1.in.String@domain.com" );
      assertIsFalse( "\"string\\)(\".local.part.with.escaped.false.bracket1.in.String@domain.com" );
      assertIsFalse( ")(@local.part.only.false.bracket1.domain.com" );
      assertIsFalse( ")()()()()()(@local.part.only.consecutive.false.bracket1.domain.com" );
      assertIsFalse( ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com" );
      assertIsTrue( "name )( <pointy.brackets1.with.false.bracket1.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.false.bracket1.in.display.name@domain.com> name )(" );

      assertIsFalse( "domain.part@with)(false.bracket1.com" );
      assertIsFalse( "domain.part@)(with.false.bracket1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket1.at.domain.end1)(.com" );
      assertIsFalse( "domain.part@with.false.bracket1.at.domain.end2.com)(" );
      assertIsFalse( "domain.part@with.false.bracket1.before)(.point.com" );
      assertIsFalse( "domain.part@with.false.bracket1.after.)(point.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket1)()(test.com" );
      assertIsFalse( "domain.part.with.false.bracket1.in.comment@(comment )()domain.com" );
      assertIsFalse( "domain.part.only.false.bracket1@)(.com" );
      assertIsFalse( "top.level.domain.only@false.bracket1.)(" );

      assertIsTrue( "}{.local.part.starts.with.false.bracket2@domain.com" );
      assertIsTrue( "local.part.ends.with.false.bracket2}{@domain.com" );
      assertIsTrue( "local.part.with.false.bracket2}{character@domain.com" );
      assertIsTrue( "local.part.with.false.bracket2.before}{.point@domain.com" );
      assertIsTrue( "local.part.with.false.bracket2.after.}{point@domain.com" );
      assertIsTrue( "local.part.with.double.false.bracket2}{}{test@domain.com" );
      assertIsTrue( "(comment }{) local.part.with.false.bracket2.in.comment@domain.com" );
      assertIsTrue( "\"string}{\".local.part.with.false.bracket2.in.String@domain.com" );
      assertIsFalse( "\"string\\}{\".local.part.with.escaped.false.bracket2.in.String@domain.com" );
      assertIsTrue( "}{@local.part.only.false.bracket2.domain.com" );
      assertIsTrue( "}{}{}{}{}{}{@local.part.only.consecutive.false.bracket2.domain.com" );
      assertIsTrue( "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com" );
      assertIsFalse( "name }{ <pointy.brackets1.with.false.bracket2.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket2.in.display.name@domain.com> name }{" );

      assertIsFalse( "domain.part@with}{false.bracket2.com" );
      assertIsFalse( "domain.part@}{with.false.bracket2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket2.at.domain.end1}{.com" );
      assertIsFalse( "domain.part@with.false.bracket2.at.domain.end2.com}{" );
      assertIsFalse( "domain.part@with.false.bracket2.before}{.point.com" );
      assertIsFalse( "domain.part@with.false.bracket2.after.}{point.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket2}{}{test.com" );
      assertIsTrue( "domain.part.with.false.bracket2.in.comment@(comment }{)domain.com" );
      assertIsFalse( "domain.part.only.false.bracket2@}{.com" );
      assertIsFalse( "top.level.domain.only@false.bracket2.}{" );

      assertIsFalse( "][.local.part.starts.with.false.bracket3@domain.com" );
      assertIsFalse( "local.part.ends.with.false.bracket3][@domain.com" );
      assertIsFalse( "local.part.with.false.bracket3][character@domain.com" );
      assertIsFalse( "local.part.with.false.bracket3.before][.point@domain.com" );
      assertIsFalse( "local.part.with.false.bracket3.after.][point@domain.com" );
      assertIsFalse( "local.part.with.double.false.bracket3][][test@domain.com" );
      assertIsFalse( "(comment ][) local.part.with.false.bracket3.in.comment@domain.com" );
      assertIsTrue( "\"string][\".local.part.with.false.bracket3.in.String@domain.com" );
      assertIsFalse( "\"string\\][\".local.part.with.escaped.false.bracket3.in.String@domain.com" );
      assertIsFalse( "][@local.part.only.false.bracket3.domain.com" );
      assertIsFalse( "][][][][][][@local.part.only.consecutive.false.bracket3.domain.com" );
      assertIsFalse( "][.][.][.][.][.][@false.bracket3.domain.com" );
      assertIsFalse( "name ][ <pointy.brackets1.with.false.bracket3.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket3.in.display.name@domain.com> name ][" );

      assertIsFalse( "domain.part@with][false.bracket3.com" );
      assertIsFalse( "domain.part@][with.false.bracket3.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket3.at.domain.end1][.com" );
      assertIsFalse( "domain.part@with.false.bracket3.at.domain.end2.com][" );
      assertIsFalse( "domain.part@with.false.bracket3.before][.point.com" );
      assertIsFalse( "domain.part@with.false.bracket3.after.][point.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket3][][test.com" );
      assertIsFalse( "domain.part.with.false.bracket3.in.comment@(comment ][)domain.com" );
      assertIsFalse( "domain.part.only.false.bracket3@][.com" );
      assertIsFalse( "top.level.domain.only@false.bracket3.][" );

      assertIsFalse( "><.local.part.starts.with.false.bracket4@domain.com" );
      assertIsFalse( "local.part.ends.with.false.bracket4><@domain.com" );
      assertIsFalse( "local.part.with.false.bracket4><character@domain.com" );
      assertIsFalse( "local.part.with.false.bracket4.before><.point@domain.com" );
      assertIsFalse( "local.part.with.false.bracket4.after.><point@domain.com" );
      assertIsFalse( "local.part.with.double.false.bracket4><><test@domain.com" );
      assertIsFalse( "(comment ><) local.part.with.false.bracket4.in.comment@domain.com" );
      assertIsTrue( "\"string><\".local.part.with.false.bracket4.in.String@domain.com" );
      assertIsFalse( "\"string\\><\".local.part.with.escaped.false.bracket4.in.String@domain.com" );
      assertIsFalse( "><@local.part.only.false.bracket4.domain.com" );
      assertIsFalse( "><><><><><><@local.part.only.consecutive.false.bracket4.domain.com" );
      assertIsFalse( "><.><.><.><.><.><@false.bracket4.domain.com" );
      assertIsFalse( "name >< <pointy.brackets1.with.false.bracket4.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket4.in.display.name@domain.com> name ><" );

      assertIsFalse( "domain.part@with><false.bracket4.com" );
      assertIsFalse( "domain.part@><with.false.bracket4.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket4.at.domain.end1><.com" );
      assertIsFalse( "domain.part@with.false.bracket4.at.domain.end2.com><" );
      assertIsFalse( "domain.part@with.false.bracket4.before><.point.com" );
      assertIsFalse( "domain.part@with.false.bracket4.after.><point.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket4><><test.com" );
      assertIsFalse( "domain.part.with.false.bracket4.in.comment@(comment ><)domain.com" );
      assertIsFalse( "domain.part.only.false.bracket4@><.com" );
      assertIsFalse( "top.level.domain.only@false.bracket4.><" );

      assertIsFalse( "<.local.part.starts.with.lower.than@domain.com" );
      assertIsFalse( "local.part.ends.with.lower.than<@domain.com" );
      assertIsFalse( "local.part.with.lower.than<character@domain.com" );
      assertIsFalse( "local.part.with.lower.than.before<.point@domain.com" );
      assertIsFalse( "local.part.with.lower.than.after.<point@domain.com" );
      assertIsFalse( "local.part.with.double.lower.than<<test@domain.com" );
      assertIsFalse( "(comment <) local.part.with.lower.than.in.comment@domain.com" );
      assertIsTrue( "\"string<\".local.part.with.lower.than.in.String@domain.com" );
      assertIsFalse( "\"string\\<\".local.part.with.escaped.lower.than.in.String@domain.com" );
      assertIsFalse( "<@local.part.only.lower.than.domain.com" );
      assertIsFalse( "<<<<<<@local.part.only.consecutive.lower.than.domain.com" );
      assertIsFalse( "<.<.<.<.<.<@lower.than.domain.com" );
      assertIsFalse( "name < <pointy.brackets1.with.lower.than.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.lower.than.in.display.name@domain.com> name <" );

      assertIsFalse( "domain.part@with<lower.than.com" );
      assertIsFalse( "domain.part@<with.lower.than.at.domain.start.com" );
      assertIsFalse( "domain.part@with.lower.than.at.domain.end1<.com" );
      assertIsFalse( "domain.part@with.lower.than.at.domain.end2.com<" );
      assertIsFalse( "domain.part@with.lower.than.before<.point.com" );
      assertIsFalse( "domain.part@with.lower.than.after.<point.com" );
      assertIsFalse( "domain.part@with.consecutive.lower.than<<test.com" );
      assertIsFalse( "domain.part.with.lower.than.in.comment@(comment <)domain.com" );
      assertIsFalse( "domain.part.only.lower.than@<.com" );
      assertIsFalse( "top.level.domain.only@lower.than.<" );

      assertIsFalse( ">.local.part.starts.with.greater.than@domain.com" );
      assertIsFalse( "local.part.ends.with.greater.than>@domain.com" );
      assertIsFalse( "local.part.with.greater.than>character@domain.com" );
      assertIsFalse( "local.part.with.greater.than.before>.point@domain.com" );
      assertIsFalse( "local.part.with.greater.than.after.>point@domain.com" );
      assertIsFalse( "local.part.with.double.greater.than>>test@domain.com" );
      assertIsFalse( "(comment >) local.part.with.greater.than.in.comment@domain.com" );
      assertIsTrue( "\"string>\".local.part.with.greater.than.in.String@domain.com" );
      assertIsFalse( "\"string\\>\".local.part.with.escaped.greater.than.in.String@domain.com" );
      assertIsFalse( ">@local.part.only.greater.than.domain.com" );
      assertIsFalse( ">>>>>>@local.part.only.consecutive.greater.than.domain.com" );
      assertIsFalse( ">.>.>.>.>.>@greater.than.domain.com" );
      assertIsFalse( "name > <pointy.brackets1.with.greater.than.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.greater.than.in.display.name@domain.com> name >" );

      assertIsFalse( "domain.part@with>greater.than.com" );
      assertIsFalse( "domain.part@>with.greater.than.at.domain.start.com" );
      assertIsFalse( "domain.part@with.greater.than.at.domain.end1>.com" );
      assertIsFalse( "domain.part@with.greater.than.at.domain.end2.com>" );
      assertIsFalse( "domain.part@with.greater.than.before>.point.com" );
      assertIsFalse( "domain.part@with.greater.than.after.>point.com" );
      assertIsFalse( "domain.part@with.consecutive.greater.than>>test.com" );
      assertIsFalse( "domain.part.with.greater.than.in.comment@(comment >)domain.com" );
      assertIsFalse( "domain.part.only.greater.than@>.com" );
      assertIsFalse( "top.level.domain.only@greater.than.>" );

      assertIsTrue( "~.local.part.starts.with.tilde@domain.com" );
      assertIsTrue( "local.part.ends.with.tilde~@domain.com" );
      assertIsTrue( "local.part.with.tilde~character@domain.com" );
      assertIsTrue( "local.part.with.tilde.before~.point@domain.com" );
      assertIsTrue( "local.part.with.tilde.after.~point@domain.com" );
      assertIsTrue( "local.part.with.double.tilde~~test@domain.com" );
      assertIsTrue( "(comment ~) local.part.with.tilde.in.comment@domain.com" );
      assertIsTrue( "\"string~\".local.part.with.tilde.in.String@domain.com" );
      assertIsFalse( "\"string\\~\".local.part.with.escaped.tilde.in.String@domain.com" );
      assertIsTrue( "~@local.part.only.tilde.domain.com" );
      assertIsTrue( "~~~~~~@local.part.only.consecutive.tilde.domain.com" );
      assertIsTrue( "~.~.~.~.~.~@tilde.domain.com" );
      assertIsFalse( "name ~ <pointy.brackets1.with.tilde.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.tilde.in.display.name@domain.com> name ~" );

      assertIsFalse( "domain.part@with~tilde.com" );
      assertIsFalse( "domain.part@~with.tilde.at.domain.start.com" );
      assertIsFalse( "domain.part@with.tilde.at.domain.end1~.com" );
      assertIsFalse( "domain.part@with.tilde.at.domain.end2.com~" );
      assertIsFalse( "domain.part@with.tilde.before~.point.com" );
      assertIsFalse( "domain.part@with.tilde.after.~point.com" );
      assertIsFalse( "domain.part@with.consecutive.tilde~~test.com" );
      assertIsTrue( "domain.part.with.tilde.in.comment@(comment ~)domain.com" );
      assertIsFalse( "domain.part.only.tilde@~.com" );
      assertIsFalse( "top.level.domain.only@tilde.~" );

      assertIsTrue( "^.local.part.starts.with.xor@domain.com" );
      assertIsTrue( "local.part.ends.with.xor^@domain.com" );
      assertIsTrue( "local.part.with.xor^character@domain.com" );
      assertIsTrue( "local.part.with.xor.before^.point@domain.com" );
      assertIsTrue( "local.part.with.xor.after.^point@domain.com" );
      assertIsTrue( "local.part.with.double.xor^^test@domain.com" );
      assertIsTrue( "(comment ^) local.part.with.xor.in.comment@domain.com" );
      assertIsTrue( "\"string^\".local.part.with.xor.in.String@domain.com" );
      assertIsFalse( "\"string\\^\".local.part.with.escaped.xor.in.String@domain.com" );
      assertIsTrue( "^@local.part.only.xor.domain.com" );
      assertIsTrue( "^^^^^^@local.part.only.consecutive.xor.domain.com" );
      assertIsTrue( "^.^.^.^.^.^@xor.domain.com" );
      assertIsFalse( "name ^ <pointy.brackets1.with.xor.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.xor.in.display.name@domain.com> name ^" );

      assertIsFalse( "domain.part@with^xor.com" );
      assertIsFalse( "domain.part@^with.xor.at.domain.start.com" );
      assertIsFalse( "domain.part@with.xor.at.domain.end1^.com" );
      assertIsFalse( "domain.part@with.xor.at.domain.end2.com^" );
      assertIsFalse( "domain.part@with.xor.before^.point.com" );
      assertIsFalse( "domain.part@with.xor.after.^point.com" );
      assertIsFalse( "domain.part@with.consecutive.xor^^test.com" );
      assertIsTrue( "domain.part.with.xor.in.comment@(comment ^)domain.com" );
      assertIsFalse( "domain.part.only.xor@^.com" );
      assertIsFalse( "top.level.domain.only@xor.^" );

      assertIsFalse( ":.local.part.starts.with.colon@domain.com" );
      assertIsFalse( "local.part.ends.with.colon:@domain.com" );
      assertIsFalse( "local.part.with.colon:character@domain.com" );
      assertIsFalse( "local.part.with.colon.before:.point@domain.com" );
      assertIsFalse( "local.part.with.colon.after.:point@domain.com" );
      assertIsFalse( "local.part.with.double.colon::test@domain.com" );
      assertIsFalse( "(comment :) local.part.with.colon.in.comment@domain.com" );
      assertIsTrue( "\"string:\".local.part.with.colon.in.String@domain.com" );
      assertIsFalse( "\"string\\:\".local.part.with.escaped.colon.in.String@domain.com" );
      assertIsFalse( ":@local.part.only.colon.domain.com" );
      assertIsFalse( "::::::@local.part.only.consecutive.colon.domain.com" );
      assertIsFalse( ":.:.:.:.:.:@colon.domain.com" );
      assertIsFalse( "name : <pointy.brackets1.with.colon.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.colon.in.display.name@domain.com> name :" );

      assertIsFalse( "domain.part@with:colon.com" );
      assertIsFalse( "domain.part@:with.colon.at.domain.start.com" );
      assertIsFalse( "domain.part@with.colon.at.domain.end1:.com" );
      assertIsFalse( "domain.part@with.colon.at.domain.end2.com:" );
      assertIsFalse( "domain.part@with.colon.before:.point.com" );
      assertIsFalse( "domain.part@with.colon.after.:point.com" );
      assertIsFalse( "domain.part@with.consecutive.colon::test.com" );
      assertIsFalse( "domain.part.with.colon.in.comment@(comment :)domain.com" );
      assertIsFalse( "domain.part.only.colon@:.com" );
      assertIsFalse( "top.level.domain.only@colon.:" );

      assertIsFalse( " .local.part.starts.with.space@domain.com" );
      assertIsFalse( "local.part.ends.with.space @domain.com" );
      assertIsFalse( "local.part.with.space character@domain.com" );
      assertIsFalse( "local.part.with.space.before .point@domain.com" );
      assertIsFalse( "local.part.with.space.after. point@domain.com" );
      assertIsFalse( "local.part.with.double.space  test@domain.com" );
      assertIsTrue( "(comment  ) local.part.with.space.in.comment@domain.com" );
      assertIsTrue( "\"string \".local.part.with.space.in.String@domain.com" );
      assertIsTrue( "\"string\\ \".local.part.with.escaped.space.in.String@domain.com" );
      assertIsFalse( " @local.part.only.space.domain.com" );
      assertIsFalse( "      @local.part.only.consecutive.space.domain.com" );
      assertIsFalse( " . . . . . @space.domain.com" );
      assertIsTrue( "name   <pointy.brackets1.with.space.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.space.in.display.name@domain.com> name  " );

      assertIsFalse( "domain.part@with space.com" );
      assertIsFalse( "domain.part@ with.space.at.domain.start.com" );
      assertIsFalse( "domain.part@with.space.at.domain.end1 .com" );
      assertIsFalse( "domain.part@with.space.at.domain.end2.com " );
      assertIsFalse( "domain.part@with.space.before .point.com" );
      assertIsFalse( "domain.part@with.space.after. point.com" );
      assertIsFalse( "domain.part@with.consecutive.space  test.com" );
      assertIsTrue( "domain.part.with.space.in.comment@(comment  )domain.com" );
      assertIsFalse( "domain.part.only.space@ .com" );
      assertIsFalse( "top.level.domain.only@space. " );

      assertIsFalse( ",.local.part.starts.with.comma@domain.com" );
      assertIsFalse( "local.part.ends.with.comma,@domain.com" );
      assertIsFalse( "local.part.with.comma,character@domain.com" );
      assertIsFalse( "local.part.with.comma.before,.point@domain.com" );
      assertIsFalse( "local.part.with.comma.after.,point@domain.com" );
      assertIsFalse( "local.part.with.double.comma,,test@domain.com" );
      assertIsFalse( "(comment ,) local.part.with.comma.in.comment@domain.com" );
      assertIsTrue( "\"string,\".local.part.with.comma.in.String@domain.com" );
      assertIsFalse( "\"string\\,\".local.part.with.escaped.comma.in.String@domain.com" );
      assertIsFalse( ",@local.part.only.comma.domain.com" );
      assertIsFalse( ",,,,,,@local.part.only.consecutive.comma.domain.com" );
      assertIsFalse( ",.,.,.,.,.,@comma.domain.com" );
      assertIsFalse( "name , <pointy.brackets1.with.comma.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.comma.in.display.name@domain.com> name ," );

      assertIsFalse( "domain.part@with,comma.com" );
      assertIsFalse( "domain.part@,with.comma.at.domain.start.com" );
      assertIsFalse( "domain.part@with.comma.at.domain.end1,.com" );
      assertIsFalse( "domain.part@with.comma.at.domain.end2.com," );
      assertIsFalse( "domain.part@with.comma.before,.point.com" );
      assertIsFalse( "domain.part@with.comma.after.,point.com" );
      assertIsFalse( "domain.part@with.consecutive.comma,,test.com" );
      assertIsFalse( "domain.part.with.comma.in.comment@(comment ,)domain.com" );
      assertIsFalse( "domain.part.only.comma@,.com" );
      assertIsFalse( "top.level.domain.only@comma.," );

      assertIsFalse( "@.local.part.starts.with.at@domain.com" );
      assertIsFalse( "local.part.ends.with.at@@domain.com" );
      assertIsFalse( "local.part.with.at@character@domain.com" );
      assertIsFalse( "local.part.with.at.before@.point@domain.com" );
      assertIsFalse( "local.part.with.at.after.@point@domain.com" );
      assertIsFalse( "local.part.with.double.at@@test@domain.com" );
      assertIsTrue( "(comment @) local.part.with.at.in.comment@domain.com" );
      assertIsTrue( "\"string@\".local.part.with.at.in.String@domain.com" );
      assertIsTrue( "\"string\\@\".local.part.with.escaped.at.in.String@domain.com" );
      assertIsFalse( "@@local.part.only.at.domain.com" );
      assertIsFalse( "@@@@@@@local.part.only.consecutive.at.domain.com" );
      assertIsFalse( "@.@.@.@.@.@@at.domain.com" );
      assertIsFalse( "name @ <pointy.brackets1.with.at.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.at.in.display.name@domain.com> name @" );

      assertIsFalse( "domain.part@with@at.com" );
      assertIsFalse( "domain.part@@with.at.at.domain.start.com" );
      assertIsFalse( "domain.part@with.at.at.domain.end1@.com" );
      assertIsFalse( "domain.part@with.at.at.domain.end2.com@" );
      assertIsFalse( "domain.part@with.at.before@.point.com" );
      assertIsFalse( "domain.part@with.at.after.@point.com" );
      assertIsFalse( "domain.part@with.consecutive.at@@test.com" );
      assertIsTrue( "domain.part.with.at.in.comment@(comment @)domain.com" );
      assertIsFalse( "domain.part.only.at@@.com" );
      assertIsFalse( "top.level.domain.only@at.@" );

      assertIsFalse( "§.local.part.starts.with.paragraph@domain.com" );
      assertIsFalse( "local.part.ends.with.paragraph§@domain.com" );
      assertIsFalse( "local.part.with.paragraph§character@domain.com" );
      assertIsFalse( "local.part.with.paragraph.before§.point@domain.com" );
      assertIsFalse( "local.part.with.paragraph.after.§point@domain.com" );
      assertIsFalse( "local.part.with.double.paragraph§§test@domain.com" );
      assertIsFalse( "(comment §) local.part.with.paragraph.in.comment@domain.com" );
      assertIsFalse( "\"string§\".local.part.with.paragraph.in.String@domain.com" );
      assertIsFalse( "\"string\\§\".local.part.with.escaped.paragraph.in.String@domain.com" );
      assertIsFalse( "§@local.part.only.paragraph.domain.com" );
      assertIsFalse( "§§§§§§@local.part.only.consecutive.paragraph.domain.com" );
      assertIsFalse( "§.§.§.§.§.§@paragraph.domain.com" );
      assertIsFalse( "name § <pointy.brackets1.with.paragraph.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.paragraph.in.display.name@domain.com> name §" );

      assertIsFalse( "domain.part@with§paragraph.com" );
      assertIsFalse( "domain.part@§with.paragraph.at.domain.start.com" );
      assertIsFalse( "domain.part@with.paragraph.at.domain.end1§.com" );
      assertIsFalse( "domain.part@with.paragraph.at.domain.end2.com§" );
      assertIsFalse( "domain.part@with.paragraph.before§.point.com" );
      assertIsFalse( "domain.part@with.paragraph.after.§point.com" );
      assertIsFalse( "domain.part@with.consecutive.paragraph§§test.com" );
      assertIsFalse( "domain.part.with.paragraph.in.comment@(comment §)domain.com" );
      assertIsFalse( "domain.part.only.paragraph@§.com" );
      assertIsFalse( "top.level.domain.only@paragraph.§" );

      assertIsTrue( "'.local.part.starts.with.double.quote@domain.com" );
      assertIsTrue( "local.part.ends.with.double.quote'@domain.com" );
      assertIsTrue( "local.part.with.double.quote'character@domain.com" );
      assertIsTrue( "local.part.with.double.quote.before'.point@domain.com" );
      assertIsTrue( "local.part.with.double.quote.after.'point@domain.com" );
      assertIsTrue( "local.part.with.double.double.quote''test@domain.com" );
      assertIsTrue( "(comment ') local.part.with.double.quote.in.comment@domain.com" );
      assertIsTrue( "\"string'\".local.part.with.double.quote.in.String@domain.com" );
      assertIsTrue( "\"string\\'\".local.part.with.escaped.double.quote.in.String@domain.com" );
      assertIsTrue( "'@local.part.only.double.quote.domain.com" );
      assertIsTrue( "''''''@local.part.only.consecutive.double.quote.domain.com" );
      assertIsTrue( "'.'.'.'.'.'@double.quote.domain.com" );
      assertIsFalse( "name ' <pointy.brackets1.with.double.quote.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.double.quote.in.display.name@domain.com> name '" );

      assertIsFalse( "domain.part@with'double.quote.com" );
      assertIsFalse( "domain.part@'with.double.quote.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.quote.at.domain.end1'.com" );
      assertIsFalse( "domain.part@with.double.quote.at.domain.end2.com'" );
      assertIsFalse( "domain.part@with.double.quote.before'.point.com" );
      assertIsFalse( "domain.part@with.double.quote.after.'point.com" );
      assertIsFalse( "domain.part@with.consecutive.double.quote''test.com" );
      assertIsTrue( "domain.part.with.double.quote.in.comment@(comment ')domain.com" );
      assertIsFalse( "domain.part.only.double.quote@'.com" );
      assertIsFalse( "top.level.domain.only@double.quote.'" );

      assertIsTrue( "/.local.part.starts.with.forward.slash@domain.com" );
      assertIsTrue( "local.part.ends.with.forward.slash/@domain.com" );
      assertIsTrue( "local.part.with.forward.slash/character@domain.com" );
      assertIsTrue( "local.part.with.forward.slash.before/.point@domain.com" );
      assertIsTrue( "local.part.with.forward.slash.after./point@domain.com" );
      assertIsTrue( "local.part.with.double.forward.slash//test@domain.com" );
      assertIsTrue( "(comment /) local.part.with.forward.slash.in.comment@domain.com" );
      assertIsTrue( "\"string/\".local.part.with.forward.slash.in.String@domain.com" );
      assertIsFalse( "\"string\\/\".local.part.with.escaped.forward.slash.in.String@domain.com" );
      assertIsTrue( "/@local.part.only.forward.slash.domain.com" );
      assertIsTrue( "//////@local.part.only.consecutive.forward.slash.domain.com" );
      assertIsTrue( "/./././././@forward.slash.domain.com" );
      assertIsFalse( "name / <pointy.brackets1.with.forward.slash.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.forward.slash.in.display.name@domain.com> name /" );

      assertIsFalse( "domain.part@with/forward.slash.com" );
      assertIsFalse( "domain.part@/with.forward.slash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.forward.slash.at.domain.end1/.com" );
      assertIsFalse( "domain.part@with.forward.slash.at.domain.end2.com/" );
      assertIsFalse( "domain.part@with.forward.slash.before/.point.com" );
      assertIsFalse( "domain.part@with.forward.slash.after./point.com" );
      assertIsFalse( "domain.part@with.consecutive.forward.slash//test.com" );
      assertIsTrue( "domain.part.with.forward.slash.in.comment@(comment /)domain.com" );
      assertIsFalse( "domain.part.only.forward.slash@/.com" );
      assertIsFalse( "top.level.domain.only@forward.slash./" );

      assertIsTrue( "-.local.part.starts.with.hyphen@domain.com" );
      assertIsTrue( "local.part.ends.with.hyphen-@domain.com" );
      assertIsTrue( "local.part.with.hyphen-character@domain.com" );
      assertIsTrue( "local.part.with.hyphen.before-.point@domain.com" );
      assertIsTrue( "local.part.with.hyphen.after.-point@domain.com" );
      assertIsTrue( "local.part.with.double.hyphen--test@domain.com" );
      assertIsTrue( "(comment -) local.part.with.hyphen.in.comment@domain.com" );
      assertIsTrue( "\"string-\".local.part.with.hyphen.in.String@domain.com" );
      assertIsFalse( "\"string\\-\".local.part.with.escaped.hyphen.in.String@domain.com" );
      assertIsTrue( "-@local.part.only.hyphen.domain.com" );
      assertIsTrue( "------@local.part.only.consecutive.hyphen.domain.com" );
      assertIsTrue( "-.-.-.-.-.-@hyphen.domain.com" );
      assertIsFalse( "name - <pointy.brackets1.with.hyphen.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.hyphen.in.display.name@domain.com> name -" );

      assertIsTrue( "domain.part@with-hyphen.com" );
      assertIsFalse( "domain.part@-with.hyphen.at.domain.start.com" );
      assertIsFalse( "domain.part@with.hyphen.at.domain.end1-.com" );
      assertIsFalse( "domain.part@with.hyphen.at.domain.end2.com-" );
      assertIsFalse( "domain.part@with.hyphen.before-.point.com" );
      assertIsFalse( "domain.part@with.hyphen.after.-point.com" );
      assertIsTrue( "domain.part@with.consecutive.hyphen--test.com" );
      assertIsTrue( "domain.part.with.hyphen.in.comment@(comment -)domain.com" );
      assertIsFalse( "domain.part.only.hyphen@-.com" );
      assertIsFalse( "top.level.domain.only@hyphen.-" );

      assertIsFalse( "\"\".local.part.starts.with.empty.string1@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.string1\"\"@domain.com" );
      assertIsFalse( "local.part.with.empty.string1\"\"character@domain.com" );
      assertIsFalse( "local.part.with.empty.string1.before\"\".point@domain.com" );
      assertIsFalse( "local.part.with.empty.string1.after.\"\"point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1\"\"\"\"test@domain.com" );
      assertIsFalse( "(comment \"\") local.part.with.empty.string1.in.comment@domain.com" );
      assertIsFalse( "\"string\"\"\".local.part.with.empty.string1.in.String@domain.com" );
      assertIsFalse( "\"string\\\"\"\".local.part.with.escaped.empty.string1.in.String@domain.com" );
      assertIsFalse( "\"\"@local.part.only.empty.string1.domain.com" );
      assertIsFalse( "\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.empty.string1.domain.com" );
      assertIsFalse( "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com" );
      assertIsTrue( "name \"\" <pointy.brackets1.with.empty.string1.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.string1.in.display.name@domain.com> name \"\"" );

      assertIsFalse( "domain.part@with\"\"empty.string1.com" );
      assertIsFalse( "domain.part@\"\"with.empty.string1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.string1.at.domain.end1\"\".com" );
      assertIsFalse( "domain.part@with.empty.string1.at.domain.end2.com\"\"" );
      assertIsFalse( "domain.part@with.empty.string1.before\"\".point.com" );
      assertIsFalse( "domain.part@with.empty.string1.after.\"\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.string1\"\"\"\"test.com" );
      assertIsFalse( "domain.part.with.empty.string1.in.comment@(comment \"\")domain.com" );
      assertIsFalse( "domain.part.only.empty.string1@\"\".com" );
      assertIsFalse( "top.level.domain.only@empty.string1.\"\"" );

      assertIsFalse( "a\"\"b.local.part.starts.with.empty.string2@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.string2a\"\"b@domain.com" );
      assertIsFalse( "local.part.with.empty.string2a\"\"bcharacter@domain.com" );
      assertIsFalse( "local.part.with.empty.string2.beforea\"\"b.point@domain.com" );
      assertIsFalse( "local.part.with.empty.string2.after.a\"\"bpoint@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com" );
      assertIsFalse( "(comment a\"\"b) local.part.with.empty.string2.in.comment@domain.com" );
      assertIsFalse( "\"stringa\"\"b\".local.part.with.empty.string2.in.String@domain.com" );
      assertIsFalse( "\"string\\a\"\"b\".local.part.with.escaped.empty.string2.in.String@domain.com" );
      assertIsFalse( "a\"\"b@local.part.only.empty.string2.domain.com" );
      assertIsFalse( "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@local.part.only.consecutive.empty.string2.domain.com" );
      assertIsFalse( "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com" );
      assertIsTrue( "name a\"\"b <pointy.brackets1.with.empty.string2.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.string2.in.display.name@domain.com> name a\"\"b" );

      assertIsFalse( "domain.part@witha\"\"bempty.string2.com" );
      assertIsFalse( "domain.part@a\"\"bwith.empty.string2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.string2.at.domain.end1a\"\"b.com" );
      assertIsFalse( "domain.part@with.empty.string2.at.domain.end2.coma\"\"b" );
      assertIsFalse( "domain.part@with.empty.string2.beforea\"\"b.point.com" );
      assertIsFalse( "domain.part@with.empty.string2.after.a\"\"bpoint.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com" );
      assertIsFalse( "domain.part.with.empty.string2.in.comment@(comment a\"\"b)domain.com" );
      assertIsFalse( "domain.part.only.empty.string2@a\"\"b.com" );
      assertIsFalse( "top.level.domain.only@empty.string2.a\"\"b" );

      assertIsFalse( "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com" );
      assertIsFalse( "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1\"\"\"\"character@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com" );
      assertIsFalse( "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" );
      assertIsFalse( "(comment \"\"\"\") local.part.with.double.empty.string1.in.comment@domain.com" );
      assertIsFalse( "\"string\"\"\"\"\".local.part.with.double.empty.string1.in.String@domain.com" );
      assertIsFalse( "\"string\\\"\"\"\"\".local.part.with.escaped.double.empty.string1.in.String@domain.com" );
      assertIsFalse( "\"\"\"\"@local.part.only.double.empty.string1.domain.com" );
      assertIsFalse( "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.double.empty.string1.domain.com" );
      assertIsFalse( "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" );
      assertIsTrue( "name \"\"\"\" <pointy.brackets1.with.double.empty.string1.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.double.empty.string1.in.display.name@domain.com> name \"\"\"\"" );

      assertIsFalse( "domain.part@with\"\"\"\"double.empty.string1.com" );
      assertIsFalse( "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com" );
      assertIsFalse( "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\"" );
      assertIsFalse( "domain.part@with.double.empty.string1.before\"\"\"\".point.com" );
      assertIsFalse( "domain.part@with.double.empty.string1.after.\"\"\"\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" );
      assertIsFalse( "domain.part.with.double.empty.string1.in.comment@(comment \"\"\"\")domain.com" );
      assertIsFalse( "domain.part.only.double.empty.string1@\"\"\"\".com" );
      assertIsFalse( "top.level.domain.only@double.empty.string1.\"\"\"\"" );

      assertIsFalse( "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com" );
      assertIsFalse( "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2\"\".\"\"character@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com" );
      assertIsFalse( "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" );
      assertIsFalse( "(comment \"\".\"\") local.part.with.double.empty.string2.in.comment@domain.com" );
      assertIsFalse( "\"string\"\".\"\"\".local.part.with.double.empty.string2.in.String@domain.com" );
      assertIsFalse( "\"string\\\"\".\"\"\".local.part.with.escaped.double.empty.string2.in.String@domain.com" );
      assertIsFalse( "\"\".\"\"@local.part.only.double.empty.string2.domain.com" );
      assertIsFalse( "\"\".\"\"\"\".\"\"\"\".\"\"\"\"@local.part.only.consecutive.double.empty.string2.domain.com" );
      assertIsFalse( "\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" );
      assertIsFalse( "name \"\".\"\" <pointy.brackets1.with.double.empty.string2.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.double.empty.string2.in.display.name@domain.com> name \"\".\"\"" );

      assertIsFalse( "domain.part@with\"\".\"\"double.empty.string2.com" );
      assertIsFalse( "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com" );
      assertIsFalse( "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\"" );
      assertIsFalse( "domain.part@with.double.empty.string2.before\"\".\"\".point.com" );
      assertIsFalse( "domain.part@with.double.empty.string2.after.\"\".\"\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" );
      assertIsFalse( "domain.part.with.double.empty.string2.in.comment@(comment \"\".\"\")domain.com" );
      assertIsFalse( "domain.part.only.double.empty.string2@\"\".\"\".com" );
      assertIsFalse( "top.level.domain.only@double.empty.string2.\"\".\"\"" );

      assertIsTrue( "0.local.part.starts.with.number0@domain.com" );
      assertIsTrue( "local.part.ends.with.number00@domain.com" );
      assertIsTrue( "local.part.with.number00character@domain.com" );
      assertIsTrue( "local.part.with.number0.before0.point@domain.com" );
      assertIsTrue( "local.part.with.number0.after.0point@domain.com" );
      assertIsTrue( "local.part.with.double.number000test@domain.com" );
      assertIsTrue( "(comment 0) local.part.with.number0.in.comment@domain.com" );
      assertIsTrue( "\"string0\".local.part.with.number0.in.String@domain.com" );
      assertIsFalse( "\"string\\0\".local.part.with.escaped.number0.in.String@domain.com" );
      assertIsTrue( "0@local.part.only.number0.domain.com" );
      assertIsTrue( "000000@local.part.only.consecutive.number0.domain.com" );
      assertIsTrue( "0.0.0.0.0.0@number0.domain.com" );
      assertIsTrue( "name 0 <pointy.brackets1.with.number0.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.number0.in.display.name@domain.com> name 0" );

      assertIsTrue( "domain.part@with0number0.com" );
      assertIsTrue( "domain.part@0with.number0.at.domain.start.com" );
      assertIsTrue( "domain.part@with.number0.at.domain.end10.com" );
      assertIsTrue( "domain.part@with.number0.at.domain.end2.com0" );
      assertIsTrue( "domain.part@with.number0.before0.point.com" );
      assertIsTrue( "domain.part@with.number0.after.0point.com" );
      assertIsTrue( "domain.part@with.consecutive.number000test.com" );
      assertIsTrue( "domain.part.with.number0.in.comment@(comment 0)domain.com" );
      assertIsTrue( "domain.part.only.number0@0.com" );
      assertIsFalse( "top.level.domain.only@number0.0" );

      assertIsTrue( "9.local.part.starts.with.number9@domain.com" );
      assertIsTrue( "local.part.ends.with.number99@domain.com" );
      assertIsTrue( "local.part.with.number99character@domain.com" );
      assertIsTrue( "local.part.with.number9.before9.point@domain.com" );
      assertIsTrue( "local.part.with.number9.after.9point@domain.com" );
      assertIsTrue( "local.part.with.double.number999test@domain.com" );
      assertIsTrue( "(comment 9) local.part.with.number9.in.comment@domain.com" );
      assertIsTrue( "\"string9\".local.part.with.number9.in.String@domain.com" );
      assertIsFalse( "\"string\\9\".local.part.with.escaped.number9.in.String@domain.com" );
      assertIsTrue( "9@local.part.only.number9.domain.com" );
      assertIsTrue( "999999@local.part.only.consecutive.number9.domain.com" );
      assertIsTrue( "9.9.9.9.9.9@number9.domain.com" );
      assertIsTrue( "name 9 <pointy.brackets1.with.number9.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.number9.in.display.name@domain.com> name 9" );

      assertIsTrue( "domain.part@with9number9.com" );
      assertIsTrue( "domain.part@9with.number9.at.domain.start.com" );
      assertIsTrue( "domain.part@with.number9.at.domain.end19.com" );
      assertIsTrue( "domain.part@with.number9.at.domain.end2.com9" );
      assertIsTrue( "domain.part@with.number9.before9.point.com" );
      assertIsTrue( "domain.part@with.number9.after.9point.com" );
      assertIsTrue( "domain.part@with.consecutive.number999test.com" );
      assertIsTrue( "domain.part.with.number9.in.comment@(comment 9)domain.com" );
      assertIsTrue( "domain.part.only.number9@9.com" );
      assertIsFalse( "top.level.domain.only@number9.9" );

      assertIsTrue( "0123456789.local.part.starts.with.numbers@domain.com" );
      assertIsTrue( "local.part.ends.with.numbers0123456789@domain.com" );
      assertIsTrue( "local.part.with.numbers0123456789character@domain.com" );
      assertIsTrue( "local.part.with.numbers.before0123456789.point@domain.com" );
      assertIsTrue( "local.part.with.numbers.after.0123456789point@domain.com" );
      assertIsTrue( "local.part.with.double.numbers01234567890123456789test@domain.com" );
      assertIsTrue( "(comment 0123456789) local.part.with.numbers.in.comment@domain.com" );
      assertIsTrue( "\"string0123456789\".local.part.with.numbers.in.String@domain.com" );
      assertIsFalse( "\"string\\0123456789\".local.part.with.escaped.numbers.in.String@domain.com" );
      assertIsTrue( "0123456789@local.part.only.numbers.domain.com" );
      assertIsTrue( "01234567890123@local.part.only.consecutive.numbers.domain.com" );
      assertIsTrue( "0123456789.0123456789.0123456789@numbers.domain.com" );
      assertIsTrue( "name 0123456789 <pointy.brackets1.with.numbers.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.numbers.in.display.name@domain.com> name 0123456789" );

      assertIsTrue( "domain.part@with0123456789numbers.com" );
      assertIsTrue( "domain.part@0123456789with.numbers.at.domain.start.com" );
      assertIsTrue( "domain.part@with.numbers.at.domain.end10123456789.com" );
      assertIsTrue( "domain.part@with.numbers.at.domain.end2.com0123456789" );
      assertIsTrue( "domain.part@with.numbers.before0123456789.point.com" );
      assertIsTrue( "domain.part@with.numbers.after.0123456789point.com" );
      assertIsTrue( "domain.part@with.consecutive.numbers01234567890123456789test.com" );
      assertIsTrue( "domain.part.with.numbers.in.comment@(comment 0123456789)domain.com" );
      assertIsTrue( "domain.part.only.numbers@0123456789.com" );
      assertIsFalse( "top.level.domain.only@numbers.0123456789" );

      assertIsFalse( "\\.local.part.starts.with.slash@domain.com" );
      assertIsFalse( "local.part.ends.with.slash\\@domain.com" );
      assertIsFalse( "local.part.with.slash\\character@domain.com" );
      assertIsFalse( "local.part.with.slash.before\\.point@domain.com" );
      assertIsFalse( "local.part.with.slash.after.\\point@domain.com" );
      assertIsTrue( "local.part.with.double.slash\\\\test@domain.com" );
      assertIsFalse( "(comment \\) local.part.with.slash.in.comment@domain.com" );
      assertIsFalse( "\"string\\\".local.part.with.slash.in.String@domain.com" );
      assertIsTrue( "\"string\\\\\".local.part.with.escaped.slash.in.String@domain.com" );
      assertIsFalse( "\\@local.part.only.slash.domain.com" );
      assertIsTrue( "\\\\\\\\\\\\@local.part.only.consecutive.slash.domain.com" );
      assertIsFalse( "\\.\\.\\.\\.\\.\\@slash.domain.com" );
      assertIsTrue( "escaped character is space \\ <pointy.brackets1.with.slash.in.display.name@domain.com>" );
      assertIsFalse( "no escaped character \\<pointy.brackets1.with.slash.in.display.name@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.slash.in.display.name@domain.com> name \\" );

      assertIsFalse( "domain.part@with\\slash.com" );
      assertIsFalse( "domain.part@\\with.slash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.slash.at.domain.end1\\.com" );
      assertIsFalse( "domain.part@with.slash.at.domain.end2.com\\" );
      assertIsFalse( "domain.part@with.slash.before\\.point.com" );
      assertIsFalse( "domain.part@with.slash.after.\\point.com" );
      assertIsFalse( "domain.part@with.consecutive.slash\\\\test.com" );
      assertIsFalse( "domain.part.with.slash.in.comment@(comment \\)domain.com" );
      assertIsFalse( "domain.part.only.slash@\\.com" );
      assertIsFalse( "top.level.domain.only@slash.\\" );

      assertIsTrue( "\"str\".local.part.starts.with.string@domain.com" );
      assertIsFalse( "local.part.ends.with.string\"str\"@domain.com" );
      assertIsFalse( "local.part.with.string\"str\"character@domain.com" );
      assertIsFalse( "local.part.with.string.before\"str\".point@domain.com" );
      assertIsFalse( "local.part.with.string.after.\"str\"point@domain.com" );
      assertIsFalse( "local.part.with.double.string\"str\"\"str\"test@domain.com" );
      assertIsFalse( "(comment \"str\") local.part.with.string.in.comment@domain.com" );
      assertIsFalse( "\"string\"str\"\".local.part.with.string.in.String@domain.com" );
      assertIsFalse( "\"string\\\"str\"\".local.part.with.escaped.string.in.String@domain.com" );
      assertIsTrue( "\"str\"@local.part.only.string.domain.com" );
      assertIsFalse( "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@local.part.only.consecutive.string.domain.com" );
      assertIsTrue( "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com" );
      assertIsTrue( "name \"str\" <pointy.brackets1.with.string.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.string.in.display.name@domain.com> name \"str\"" );

      assertIsFalse( "domain.part@with\"str\"string.com" );
      assertIsFalse( "domain.part@\"str\"with.string.at.domain.start.com" );
      assertIsFalse( "domain.part@with.string.at.domain.end1\"str\".com" );
      assertIsFalse( "domain.part@with.string.at.domain.end2.com\"str\"" );
      assertIsFalse( "domain.part@with.string.before\"str\".point.com" );
      assertIsFalse( "domain.part@with.string.after.\"str\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.string\"str\"\"str\"test.com" );
      assertIsFalse( "domain.part.with.string.in.comment@(comment \"str\")domain.com" );
      assertIsFalse( "domain.part.only.string@\"str\".com" );
      assertIsFalse( "top.level.domain.only@string.\"str\"" );

      assertIsFalse( "(comment).local.part.starts.with.comment@domain.com" );
      assertIsTrue( "local.part.ends.with.comment(comment)@domain.com" );
      assertIsFalse( "local.part.with.comment(comment)character@domain.com" );
      assertIsFalse( "local.part.with.comment.before(comment).point@domain.com" );
      assertIsFalse( "local.part.with.comment.after.(comment)point@domain.com" );
      assertIsFalse( "local.part.with.double.comment(comment)(comment)test@domain.com" );
      assertIsFalse( "(comment (comment)) local.part.with.comment.in.comment@domain.com" );
      assertIsTrue( "\"string(comment)\".local.part.with.comment.in.String@domain.com" );
      assertIsFalse( "\"string\\(comment)\".local.part.with.escaped.comment.in.String@domain.com" );
      assertIsFalse( "(comment)@local.part.only.comment.domain.com" );
      assertIsFalse( "(comment)(comment)(comment)@local.part.only.consecutive.comment.domain.com" );
      assertIsFalse( "(comment).(comment).(comment).(comment)@comment.domain.com" );
      assertIsTrue( "name (comment) <pointy.brackets1.with.comment.in.display.name@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.comment.in.display.name@domain.com> name (comment)" );

      assertIsFalse( "domain.part@with(comment)comment.com" );
      assertIsTrue( "domain.part@(comment)with.comment.at.domain.start.com" );
      assertIsFalse( "domain.part@with.comment.at.domain.end1(comment).com" );
      assertIsTrue( "domain.part@with.comment.at.domain.end2.com(comment)" );
      assertIsFalse( "domain.part@with.comment.before(comment).point.com" );
      assertIsFalse( "domain.part@with.comment.after.(comment)point.com" );
      assertIsFalse( "domain.part@with.consecutive.comment(comment)(comment)test.com" );
      assertIsFalse( "domain.part.with.comment.in.comment@(comment (comment))domain.com" );
      assertIsFalse( "domain.part.only.comment@(comment).com" );
      assertIsFalse( "top.level.domain.only@comment.(comment)" );

      wlHeadline( "IP V4" );

      assertIsFalse( "\"\"@[]" );
      assertIsFalse( "\"\"@[1" );
      assertIsFalse( "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" );
      assertIsFalse( "ABC.DEF@{1.2.3.4}" );
      assertIsFalse( "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[{][})][}][}\\\"]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "[1.2.3.4@[5.6.7.8]" );
      assertIsFalse( "[1.2.3.4][5.6.7.8]@[9.10.11.12]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8][9.10.11.12]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]9.10.11.12]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8][9.10.11.12[" );

      assertIsFalse( "ABC.DEF@[1.2...3.4]" );

      assertIsTrue( "ip4.in.local.part.as.string1.\"[1.2.3.4]\"@[5.6.7.8]" );
      assertIsTrue( "ip4.in.local.part.as.string2.\"@[1.2.3.4]\"@[5.6.7.8]" );
      assertIsFalse( "ip4.ends.with.alpha.character1@[1.2.3.Z]" );
      assertIsFalse( "ip4.ends.with.alpha.character2@[1.2.3.]Z" );
      assertIsFalse( "ip4.ends.with.top.level.domain@[1.2.3.].de" );

      assertIsFalse( "ip4.with.double.ip4@[1.2.3.4][5.6.7.8]" );

      assertIsFalse( "ip4.with.ip4.in.comment1@([1.2.3.4])" );
      assertIsFalse( "ip4.with.ip4.in.comment2@([1.2.3.4])[5.6.7.8]" );
      assertIsFalse( "ip4.with.ip4.in.comment3@[1.2.3.4]([5.6.7.8])" );

      assertIsTrue( "ip4.with.ip4.in.comment4@[1.2.3.4] (@)" );
      assertIsTrue( "ip4.with.ip4.in.comment5@[1.2.3.4] (@.)" );

      assertIsFalse( "ip4.with.hex.numbers@[AB.CD.EF.EA]" );
      assertIsFalse( "ip4.with.hex.number.overflow@[AB.CD.EF.FF1]" );

      assertIsFalse( "ip4.with.double.brackets@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ip4.missing.at.sign[1.2.3.4]" );
      assertIsFalse( "ip4.missing.the.start.bracket@]" );
      assertIsFalse( "ip4.missing.the.end.bracket@[" );
      assertIsFalse( "ip4.missing.the.start.bracket@1.2.3.4]" );
      assertIsFalse( "ip4.missing.the.end.bracket@[1.2.3.4" );

      assertIsFalse( "ip4.missing.numbers.and.the.start.bracket@...]" );
      assertIsFalse( "ip4.missing.numbers.and.the.end.bracket@[..." );
      assertIsFalse( "ip4.missplaced.start.bracket1[@1.2.3.4]" );

      assertIsFalse( "ip4.missing.the.first.number@[.2.3.4]" );
      assertIsFalse( "ip4.missing.the.last.number@[1.2.3.]" );
      assertIsFalse( "ip4.last.number.is.space@[1.2.3. ]" );

      assertIsFalse( "ip4.with.only.one.numberABC.DEF@[1]" );
      assertIsFalse( "ip4.with.only.two.numbers@[1.2]" );
      assertIsFalse( "ip4.with.only.three.numbers@[1.2.3]" );
      assertIsFalse( "ip4.with.five.numbers@[1.2.3.4.5]" );
      assertIsFalse( "ip4.with.six.numbers@[1.2.3.4.5.6]" );
      assertIsFalse( "ip4.with.byte.overflow1@[1.2.3.256]" );
      assertIsFalse( "ip4.with.byte.overflow2@[1.2.3.1000]" );
      assertIsFalse( "ip4.with.to.many.leading.zeros@[0001.000002.000003.00000004]" );
      assertIsTrue( "ip4.with.two.leading.zeros@[001.002.003.004]" );
      assertIsTrue( "ip4.zero@[0.0.0.0]" );
      assertIsTrue( "ip4.correct1@[1.2.3.4]" );
      assertIsTrue( "ip4.correct2@[255.255.255.255]" );
      assertIsTrue( "\"ip4.local.part.as.string\"@[127.0.0.1]" );
      assertIsTrue( "\"    \"@[1.2.3.4]" );

      assertIsFalse( "ip4.with.negative.number1@[-1.2.3.4]" );
      assertIsFalse( "ip4.with.negative.number2@[1.-2.3.4]" );
      assertIsFalse( "ip4.with.negative.number3@[1.2.-3.4]" );
      assertIsFalse( "ip4.with.negative.number4@[1.2.3.-4]" );

      assertIsFalse( "ip4.with.only.empty.brackets@[]" );
      assertIsFalse( "ip4.with.three.empty.brackets@[][][]" );

      assertIsFalse( "ip4.with.only.one.dot.in.brackets@[.]" );
      assertIsFalse( "ip4.with.only.double.dot.in.brackets@[..]" );
      assertIsFalse( "ip4.with.only.triple.dot.in.brackets@[...]" );
      assertIsFalse( "ip4.with.only.four.dots.in.brackets@[....]" );

      assertIsFalse( "ip4.with.dot.between.numbers@[123.14.5.178.90]" );
      assertIsFalse( "ip4.with.dot.before.point@[123.145..178.90]" );
      assertIsFalse( "ip4.with.dot.after.point@[123.145..178.90]" );
      assertIsFalse( "ip4.with.dot.before.start.bracket@.[123.145.178.90]" );
      assertIsFalse( "ip4.with.dot.after.start.bracket@[.123.145.178.90]" );
      assertIsFalse( "ip4.with.dot.before.end.bracket@[123.145.178.90.]" );
      assertIsFalse( "ip4.with.dot.after.end.bracket@[123.145.178.90]." );

      assertIsFalse( "ip4.with.double.dot.between.numbers@[123.14..5.178.90]" );
      assertIsFalse( "ip4.with.double.dot.before.point@[123.145...178.90]" );
      assertIsFalse( "ip4.with.double.dot.after.point@[123.145...178.90]" );
      assertIsFalse( "ip4.with.double.dot.before.start.bracket@..[123.145.178.90]" );
      assertIsFalse( "ip4.with.double.dot.after.start.bracket@[..123.145.178.90]" );
      assertIsFalse( "ip4.with.double.dot.before.end.bracket@[123.145.178.90..]" );
      assertIsFalse( "ip4.with.double.dot.after.end.bracket@[123.145.178.90].." );

      assertIsFalse( "ip4.with.amp.between.numbers@[123.14&5.178.90]" );
      assertIsFalse( "ip4.with.amp.before.point@[123.145&.178.90]" );
      assertIsFalse( "ip4.with.amp.after.point@[123.145.&178.90]" );
      assertIsFalse( "ip4.with.amp.before.start.bracket@&[123.145.178.90]" );
      assertIsFalse( "ip4.with.amp.after.start.bracket@[&123.145.178.90]" );
      assertIsFalse( "ip4.with.amp.before.end.bracket@[123.145.178.90&]" );
      assertIsFalse( "ip4.with.amp.after.end.bracket@[123.145.178.90]&" );

      assertIsFalse( "ip4.with.asterisk.between.numbers@[123.14*5.178.90]" );
      assertIsFalse( "ip4.with.asterisk.before.point@[123.145*.178.90]" );
      assertIsFalse( "ip4.with.asterisk.after.point@[123.145.*178.90]" );
      assertIsFalse( "ip4.with.asterisk.before.start.bracket@*[123.145.178.90]" );
      assertIsFalse( "ip4.with.asterisk.after.start.bracket@[*123.145.178.90]" );
      assertIsFalse( "ip4.with.asterisk.before.end.bracket@[123.145.178.90*]" );
      assertIsFalse( "ip4.with.asterisk.after.end.bracket@[123.145.178.90]*" );

      assertIsFalse( "ip4.with.underscore.between.numbers@[123.14_5.178.90]" );
      assertIsFalse( "ip4.with.underscore.before.point@[123.145_.178.90]" );
      assertIsFalse( "ip4.with.underscore.after.point@[123.145._178.90]" );
      assertIsFalse( "ip4.with.underscore.before.start.bracket@_[123.145.178.90]" );
      assertIsFalse( "ip4.with.underscore.after.start.bracket@[_123.145.178.90]" );
      assertIsFalse( "ip4.with.underscore.before.end.bracket@[123.145.178.90_]" );
      assertIsFalse( "ip4.with.underscore.after.end.bracket@[123.145.178.90]_" );

      assertIsFalse( "ip4.with.dollar.between.numbers@[123.14$5.178.90]" );
      assertIsFalse( "ip4.with.dollar.before.point@[123.145$.178.90]" );
      assertIsFalse( "ip4.with.dollar.after.point@[123.145.$178.90]" );
      assertIsFalse( "ip4.with.dollar.before.start.bracket@$[123.145.178.90]" );
      assertIsFalse( "ip4.with.dollar.after.start.bracket@[$123.145.178.90]" );
      assertIsFalse( "ip4.with.dollar.before.end.bracket@[123.145.178.90$]" );
      assertIsFalse( "ip4.with.dollar.after.end.bracket@[123.145.178.90]$" );

      assertIsFalse( "ip4.with.equality.between.numbers@[123.14=5.178.90]" );
      assertIsFalse( "ip4.with.equality.before.point@[123.145=.178.90]" );
      assertIsFalse( "ip4.with.equality.after.point@[123.145.=178.90]" );
      assertIsFalse( "ip4.with.equality.before.start.bracket@=[123.145.178.90]" );
      assertIsFalse( "ip4.with.equality.after.start.bracket@[=123.145.178.90]" );
      assertIsFalse( "ip4.with.equality.before.end.bracket@[123.145.178.90=]" );
      assertIsFalse( "ip4.with.equality.after.end.bracket@[123.145.178.90]=" );

      assertIsFalse( "ip4.with.exclamation.between.numbers@[123.14!5.178.90]" );
      assertIsFalse( "ip4.with.exclamation.before.point@[123.145!.178.90]" );
      assertIsFalse( "ip4.with.exclamation.after.point@[123.145.!178.90]" );
      assertIsFalse( "ip4.with.exclamation.before.start.bracket@![123.145.178.90]" );
      assertIsFalse( "ip4.with.exclamation.after.start.bracket@[!123.145.178.90]" );
      assertIsFalse( "ip4.with.exclamation.before.end.bracket@[123.145.178.90!]" );
      assertIsFalse( "ip4.with.exclamation.after.end.bracket@[123.145.178.90]!" );

      assertIsFalse( "ip4.with.question.between.numbers@[123.14?5.178.90]" );
      assertIsFalse( "ip4.with.question.before.point@[123.145?.178.90]" );
      assertIsFalse( "ip4.with.question.after.point@[123.145.?178.90]" );
      assertIsFalse( "ip4.with.question.before.start.bracket@?[123.145.178.90]" );
      assertIsFalse( "ip4.with.question.after.start.bracket@[?123.145.178.90]" );
      assertIsFalse( "ip4.with.question.before.end.bracket@[123.145.178.90?]" );
      assertIsFalse( "ip4.with.question.after.end.bracket@[123.145.178.90]?" );

      assertIsFalse( "ip4.with.grave-accent.between.numbers@[123.14`5.178.90]" );
      assertIsFalse( "ip4.with.grave-accent.before.point@[123.145`.178.90]" );
      assertIsFalse( "ip4.with.grave-accent.after.point@[123.145.`178.90]" );
      assertIsFalse( "ip4.with.grave-accent.before.start.bracket@`[123.145.178.90]" );
      assertIsFalse( "ip4.with.grave-accent.after.start.bracket@[`123.145.178.90]" );
      assertIsFalse( "ip4.with.grave-accent.before.end.bracket@[123.145.178.90`]" );
      assertIsFalse( "ip4.with.grave-accent.after.end.bracket@[123.145.178.90]`" );

      assertIsFalse( "ip4.with.hash.between.numbers@[123.14#5.178.90]" );
      assertIsFalse( "ip4.with.hash.before.point@[123.145#.178.90]" );
      assertIsFalse( "ip4.with.hash.after.point@[123.145.#178.90]" );
      assertIsFalse( "ip4.with.hash.before.start.bracket@#[123.145.178.90]" );
      assertIsFalse( "ip4.with.hash.after.start.bracket@[#123.145.178.90]" );
      assertIsFalse( "ip4.with.hash.before.end.bracket@[123.145.178.90#]" );
      assertIsFalse( "ip4.with.hash.after.end.bracket@[123.145.178.90]#" );

      assertIsFalse( "ip4.with.percentage.between.numbers@[123.14%5.178.90]" );
      assertIsFalse( "ip4.with.percentage.before.point@[123.145%.178.90]" );
      assertIsFalse( "ip4.with.percentage.after.point@[123.145.%178.90]" );
      assertIsFalse( "ip4.with.percentage.before.start.bracket@%[123.145.178.90]" );
      assertIsFalse( "ip4.with.percentage.after.start.bracket@[%123.145.178.90]" );
      assertIsFalse( "ip4.with.percentage.before.end.bracket@[123.145.178.90%]" );
      assertIsFalse( "ip4.with.percentage.after.end.bracket@[123.145.178.90]%" );

      assertIsFalse( "ip4.with.pipe.between.numbers@[123.14|5.178.90]" );
      assertIsFalse( "ip4.with.pipe.before.point@[123.145|.178.90]" );
      assertIsFalse( "ip4.with.pipe.after.point@[123.145.|178.90]" );
      assertIsFalse( "ip4.with.pipe.before.start.bracket@|[123.145.178.90]" );
      assertIsFalse( "ip4.with.pipe.after.start.bracket@[|123.145.178.90]" );
      assertIsFalse( "ip4.with.pipe.before.end.bracket@[123.145.178.90|]" );
      assertIsFalse( "ip4.with.pipe.after.end.bracket@[123.145.178.90]|" );

      assertIsFalse( "ip4.with.plus.between.numbers@[123.14+5.178.90]" );
      assertIsFalse( "ip4.with.plus.before.point@[123.145+.178.90]" );
      assertIsFalse( "ip4.with.plus.after.point@[123.145.+178.90]" );
      assertIsFalse( "ip4.with.plus.before.start.bracket@+[123.145.178.90]" );
      assertIsFalse( "ip4.with.plus.after.start.bracket@[+123.145.178.90]" );
      assertIsFalse( "ip4.with.plus.before.end.bracket@[123.145.178.90+]" );
      assertIsFalse( "ip4.with.plus.after.end.bracket@[123.145.178.90]+" );

      assertIsFalse( "ip4.with.leftbracket.between.numbers@[123.14{5.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.point@[123.145{.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.after.point@[123.145.{178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.start.bracket@{[123.145.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.after.start.bracket@[{123.145.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.end.bracket@[123.145.178.90{]" );
      assertIsFalse( "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]{" );

      assertIsFalse( "ip4.with.rightbracket.between.numbers@[123.14}5.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.point@[123.145}.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.after.point@[123.145.}178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.start.bracket@}[123.145.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.after.start.bracket@[}123.145.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.end.bracket@[123.145.178.90}]" );
      assertIsFalse( "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]}" );

      assertIsFalse( "ip4.with.leftbracket.between.numbers@[123.14(5.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.point@[123.145(.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.after.point@[123.145.(178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.start.bracket@([123.145.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.after.start.bracket@[(123.145.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.end.bracket@[123.145.178.90(]" );
      assertIsFalse( "ip4.with.leftbracket.after.end.bracket@[123.145.178.90](" );

      assertIsFalse( "ip4.with.rightbracket.between.numbers@[123.14)5.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.point@[123.145).178.90]" );
      assertIsFalse( "ip4.with.rightbracket.after.point@[123.145.)178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.start.bracket@)[123.145.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.after.start.bracket@[)123.145.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.end.bracket@[123.145.178.90)]" );
      assertIsFalse( "ip4.with.rightbracket.after.end.bracket@[123.145.178.90])" );

      assertIsFalse( "ip4.with.leftbracket.between.numbers@[123.14[5.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.point@[123.145[.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.after.point@[123.145.[178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.start.bracket@[[123.145.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.after.start.bracket@[[123.145.178.90]" );
      assertIsFalse( "ip4.with.leftbracket.before.end.bracket@[123.145.178.90[]" );
      assertIsFalse( "ip4.with.leftbracket.after.end.bracket@[123.145.178.90][" );

      assertIsFalse( "ip4.with.rightbracket.between.numbers@[123.14]5.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.point@[123.145].178.90]" );
      assertIsFalse( "ip4.with.rightbracket.after.point@[123.145.]178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.start.bracket@][123.145.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.after.start.bracket@[]123.145.178.90]" );
      assertIsFalse( "ip4.with.rightbracket.before.end.bracket@[123.145.178.90]]" );
      assertIsFalse( "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]]" );

      assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14()5.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145().178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.()178.90]" );
      assertIsTrue( "ip4.with.empty.bracket.before.start.bracket@()[123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[()123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90()]" );
      assertIsTrue( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]()" );

      assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14{}5.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145{}.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.{}178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.start.bracket@{}[123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[{}123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90{}]" );
      assertIsFalse( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]{}" );

      assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14[]5.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145[].178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.[]178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.start.bracket@[][123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[[]123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90[]]" );
      assertIsFalse( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90][]" );

      assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14<>5.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145<>.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.<>178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.start.bracket@<>[123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[<>123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90<>]" );
      assertIsFalse( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]<>" );

      assertIsFalse( "ip4.with.false.bracket1.between.numbers@[123.14)(5.178.90]" );
      assertIsFalse( "ip4.with.false.bracket1.before.point@[123.145)(.178.90]" );
      assertIsFalse( "ip4.with.false.bracket1.after.point@[123.145.)(178.90]" );
      assertIsFalse( "ip4.with.false.bracket1.before.start.bracket@)([123.145.178.90]" );
      assertIsFalse( "ip4.with.false.bracket1.after.start.bracket@[)(123.145.178.90]" );
      assertIsFalse( "ip4.with.false.bracket1.before.end.bracket@[123.145.178.90)(]" );
      assertIsFalse( "ip4.with.false.bracket1.after.end.bracket@[123.145.178.90])(" );

      assertIsFalse( "ip4.with.false.bracket2.between.numbers@[123.14}{5.178.90]" );
      assertIsFalse( "ip4.with.false.bracket2.before.point@[123.145}{.178.90]" );
      assertIsFalse( "ip4.with.false.bracket2.after.point@[123.145.}{178.90]" );
      assertIsFalse( "ip4.with.false.bracket2.before.start.bracket@}{[123.145.178.90]" );
      assertIsFalse( "ip4.with.false.bracket2.after.start.bracket@[}{123.145.178.90]" );
      assertIsFalse( "ip4.with.false.bracket2.before.end.bracket@[123.145.178.90}{]" );
      assertIsFalse( "ip4.with.false.bracket2.after.end.bracket@[123.145.178.90]}{" );

      assertIsFalse( "ip4.with.false.bracket4.between.numbers@[123.14><5.178.90]" );
      assertIsFalse( "ip4.with.false.bracket4.before.point@[123.145><.178.90]" );
      assertIsFalse( "ip4.with.false.bracket4.after.point@[123.145.><178.90]" );
      assertIsFalse( "ip4.with.false.bracket4.before.start.bracket@><[123.145.178.90]" );
      assertIsFalse( "ip4.with.false.bracket4.after.start.bracket@[><123.145.178.90]" );
      assertIsFalse( "ip4.with.false.bracket4.before.end.bracket@[123.145.178.90><]" );
      assertIsFalse( "ip4.with.false.bracket4.after.end.bracket@[123.145.178.90]><" );

      assertIsFalse( "ip4.with.lower.than.between.numbers@[123.14<5.178.90]" );
      assertIsFalse( "ip4.with.lower.than.before.point@[123.145<.178.90]" );
      assertIsFalse( "ip4.with.lower.than.after.point@[123.145.<178.90]" );
      assertIsFalse( "ip4.with.lower.than.before.start.bracket@<[123.145.178.90]" );
      assertIsFalse( "ip4.with.lower.than.after.start.bracket@[<123.145.178.90]" );
      assertIsFalse( "ip4.with.lower.than.before.end.bracket@[123.145.178.90<]" );
      assertIsFalse( "ip4.with.lower.than.after.end.bracket@[123.145.178.90]<" );

      assertIsFalse( "ip4.with.greater.than.between.numbers@[123.14>5.178.90]" );
      assertIsFalse( "ip4.with.greater.than.before.point@[123.145>.178.90]" );
      assertIsFalse( "ip4.with.greater.than.after.point@[123.145.>178.90]" );
      assertIsFalse( "ip4.with.greater.than.before.start.bracket@>[123.145.178.90]" );
      assertIsFalse( "ip4.with.greater.than.after.start.bracket@[>123.145.178.90]" );
      assertIsFalse( "ip4.with.greater.than.before.end.bracket@[123.145.178.90>]" );
      assertIsFalse( "ip4.with.greater.than.after.end.bracket@[123.145.178.90]>" );

      assertIsFalse( "ip4.with.tilde.between.numbers@[123.14~5.178.90]" );
      assertIsFalse( "ip4.with.tilde.before.point@[123.145~.178.90]" );
      assertIsFalse( "ip4.with.tilde.after.point@[123.145.~178.90]" );
      assertIsFalse( "ip4.with.tilde.before.start.bracket@~[123.145.178.90]" );
      assertIsFalse( "ip4.with.tilde.after.start.bracket@[~123.145.178.90]" );
      assertIsFalse( "ip4.with.tilde.before.end.bracket@[123.145.178.90~]" );
      assertIsFalse( "ip4.with.tilde.after.end.bracket@[123.145.178.90]~" );

      assertIsFalse( "ip4.with.xor.between.numbers@[123.14^5.178.90]" );
      assertIsFalse( "ip4.with.xor.before.point@[123.145^.178.90]" );
      assertIsFalse( "ip4.with.xor.after.point@[123.145.^178.90]" );
      assertIsFalse( "ip4.with.xor.before.start.bracket@^[123.145.178.90]" );
      assertIsFalse( "ip4.with.xor.after.start.bracket@[^123.145.178.90]" );
      assertIsFalse( "ip4.with.xor.before.end.bracket@[123.145.178.90^]" );
      assertIsFalse( "ip4.with.xor.after.end.bracket@[123.145.178.90]^" );

      assertIsFalse( "ip4.with.colon.between.numbers@[123.14:5.178.90]" );
      assertIsFalse( "ip4.with.colon.before.point@[123.145:.178.90]" );
      assertIsFalse( "ip4.with.colon.after.point@[123.145.:178.90]" );
      assertIsFalse( "ip4.with.colon.before.start.bracket@:[123.145.178.90]" );
      assertIsFalse( "ip4.with.colon.after.start.bracket@[:123.145.178.90]" );
      assertIsFalse( "ip4.with.colon.before.end.bracket@[123.145.178.90:]" );
      assertIsFalse( "ip4.with.colon.after.end.bracket@[123.145.178.90]:" );

      assertIsFalse( "ip4.with.space.between.numbers@[123.14 5.178.90]" );
      assertIsFalse( "ip4.with.space.before.point@[123.145 .178.90]" );
      assertIsFalse( "ip4.with.space.after.point@[123.145. 178.90]" );
      assertIsFalse( "ip4.with.space.before.start.bracket@ [123.145.178.90]" );
      assertIsFalse( "ip4.with.space.after.start.bracket@[ 123.145.178.90]" );
      assertIsFalse( "ip4.with.space.before.end.bracket@[123.145.178.90 ]" );
      assertIsFalse( "ip4.with.space.after.end.bracket@[123.145.178.90] " );

      assertIsFalse( "ip4.with.comma.between.numbers@[123.14,5.178.90]" );
      assertIsFalse( "ip4.with.comma.before.point@[123.145,.178.90]" );
      assertIsFalse( "ip4.with.comma.after.point@[123.145.,178.90]" );
      assertIsFalse( "ip4.with.comma.before.start.bracket@,[123.145.178.90]" );
      assertIsFalse( "ip4.with.comma.after.start.bracket@[,123.145.178.90]" );
      assertIsFalse( "ip4.with.comma.before.end.bracket@[123.145.178.90,]" );
      assertIsFalse( "ip4.with.comma.after.end.bracket@[123.145.178.90]," );

      assertIsFalse( "ip4.with.at.between.numbers@[123.14@5.178.90]" );
      assertIsFalse( "ip4.with.at.before.point@[123.145@.178.90]" );
      assertIsFalse( "ip4.with.at.after.point@[123.145.@178.90]" );
      assertIsFalse( "ip4.with.at.before.start.bracket@@[123.145.178.90]" );
      assertIsFalse( "ip4.with.at.after.start.bracket@[@123.145.178.90]" );
      assertIsFalse( "ip4.with.at.before.end.bracket@[123.145.178.90@]" );
      assertIsFalse( "ip4.with.at.after.end.bracket@[123.145.178.90]@" );

      assertIsFalse( "ip4.with.paragraph.between.numbers@[123.14§5.178.90]" );
      assertIsFalse( "ip4.with.paragraph.before.point@[123.145§.178.90]" );
      assertIsFalse( "ip4.with.paragraph.after.point@[123.145.§178.90]" );
      assertIsFalse( "ip4.with.paragraph.before.start.bracket@§[123.145.178.90]" );
      assertIsFalse( "ip4.with.paragraph.after.start.bracket@[§123.145.178.90]" );
      assertIsFalse( "ip4.with.paragraph.before.end.bracket@[123.145.178.90§]" );
      assertIsFalse( "ip4.with.paragraph.after.end.bracket@[123.145.178.90]§" );

      assertIsFalse( "ip4.with.double.quote.between.numbers@[123.14'5.178.90]" );
      assertIsFalse( "ip4.with.double.quote.before.point@[123.145'.178.90]" );
      assertIsFalse( "ip4.with.double.quote.after.point@[123.145.'178.90]" );
      assertIsFalse( "ip4.with.double.quote.before.start.bracket@'[123.145.178.90]" );
      assertIsFalse( "ip4.with.double.quote.after.start.bracket@['123.145.178.90]" );
      assertIsFalse( "ip4.with.double.quote.before.end.bracket@[123.145.178.90']" );
      assertIsFalse( "ip4.with.double.quote.after.end.bracket@[123.145.178.90]'" );

      assertIsFalse( "ip4.with.forward.slash.between.numbers@[123.14/5.178.90]" );
      assertIsFalse( "ip4.with.forward.slash.before.point@[123.145/.178.90]" );
      assertIsFalse( "ip4.with.forward.slash.after.point@[123.145./178.90]" );
      assertIsFalse( "ip4.with.forward.slash.before.start.bracket@/[123.145.178.90]" );
      assertIsFalse( "ip4.with.forward.slash.after.start.bracket@[/123.145.178.90]" );
      assertIsFalse( "ip4.with.forward.slash.before.end.bracket@[123.145.178.90/]" );
      assertIsFalse( "ip4.with.forward.slash.after.end.bracket@[123.145.178.90]/" );

      assertIsFalse( "ip4.with.hyphen.between.numbers@[123.14-5.178.90]" );
      assertIsFalse( "ip4.with.hyphen.before.point@[123.145-.178.90]" );
      assertIsFalse( "ip4.with.hyphen.after.point@[123.145.-178.90]" );
      assertIsFalse( "ip4.with.hyphen.before.start.bracket@-[123.145.178.90]" );
      assertIsFalse( "ip4.with.hyphen.after.start.bracket@[-123.145.178.90]" );
      assertIsFalse( "ip4.with.hyphen.before.end.bracket@[123.145.178.90-]" );
      assertIsFalse( "ip4.with.hyphen.after.end.bracket@[123.145.178.90]-" );

      assertIsFalse( "ip4.with.empty.string1.between.numbers@[123.14\"\"5.178.90]" );
      assertIsFalse( "ip4.with.empty.string1.before.point@[123.145\"\".178.90]" );
      assertIsFalse( "ip4.with.empty.string1.after.point@[123.145.\"\"178.90]" );
      assertIsFalse( "ip4.with.empty.string1.before.start.bracket@\"\"[123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.string1.after.start.bracket@[\"\"123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.string1.before.end.bracket@[123.145.178.90\"\"]" );
      assertIsFalse( "ip4.with.empty.string1.after.end.bracket@[123.145.178.90]\"\"" );

      assertIsFalse( "ip4.with.empty.string2.between.numbers@[123.14a\"\"b5.178.90]" );
      assertIsFalse( "ip4.with.empty.string2.before.point@[123.145a\"\"b.178.90]" );
      assertIsFalse( "ip4.with.empty.string2.after.point@[123.145.a\"\"b178.90]" );
      assertIsFalse( "ip4.with.empty.string2.before.start.bracket@a\"\"b[123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.string2.after.start.bracket@[a\"\"b123.145.178.90]" );
      assertIsFalse( "ip4.with.empty.string2.before.end.bracket@[123.145.178.90a\"\"b]" );
      assertIsFalse( "ip4.with.empty.string2.after.end.bracket@[123.145.178.90]a\"\"b" );

      assertIsFalse( "ip4.with.double.empty.string1.between.numbers@[123.14\"\"\"\"5.178.90]" );
      assertIsFalse( "ip4.with.double.empty.string1.before.point@[123.145\"\"\"\".178.90]" );
      assertIsFalse( "ip4.with.double.empty.string1.after.point@[123.145.\"\"\"\"178.90]" );
      assertIsFalse( "ip4.with.double.empty.string1.before.start.bracket@\"\"\"\"[123.145.178.90]" );
      assertIsFalse( "ip4.with.double.empty.string1.after.start.bracket@[\"\"\"\"123.145.178.90]" );
      assertIsFalse( "ip4.with.double.empty.string1.before.end.bracket@[123.145.178.90\"\"\"\"]" );
      assertIsFalse( "ip4.with.double.empty.string1.after.end.bracket@[123.145.178.90]\"\"\"\"" );

      assertIsFalse( "ip4.with.double.empty.string2.between.numbers@[123.14\"\".\"\"5.178.90]" );
      assertIsFalse( "ip4.with.double.empty.string2.before.point@[123.145\"\".\"\".178.90]" );
      assertIsFalse( "ip4.with.double.empty.string2.after.point@[123.145.\"\".\"\"178.90]" );
      assertIsFalse( "ip4.with.double.empty.string2.before.start.bracket@\"\".\"\"[123.145.178.90]" );
      assertIsFalse( "ip4.with.double.empty.string2.after.start.bracket@[\"\".\"\"123.145.178.90]" );
      assertIsFalse( "ip4.with.double.empty.string2.before.end.bracket@[123.145.178.90\"\".\"\"]" );
      assertIsFalse( "ip4.with.double.empty.string2.after.end.bracket@[123.145.178.90]\"\".\"\"" );

      assertIsFalse( "ip4.with.number0.between.numbers@[123.1405.178.90]" );
      assertIsFalse( "ip4.with.number0.before.point@[123.1450.178.90]" );
      assertIsFalse( "ip4.with.number0.after.point@[123.145.0178.90]" );
      assertIsFalse( "ip4.with.number0.before.start.bracket@0[123.145.178.90]" );
      assertIsFalse( "ip4.with.number0.after.start.bracket@[0123.145.178.90]" );
      assertIsFalse( "ip4.with.number0.before.end.bracket@[123.145.178.900]" );
      assertIsFalse( "ip4.with.number0.after.end.bracket@[123.145.178.90]0" );

      assertIsFalse( "ip4.with.number9.between.numbers@[123.1495.178.90]" );
      assertIsFalse( "ip4.with.number9.before.point@[123.1459.178.90]" );
      assertIsFalse( "ip4.with.number9.after.point@[123.145.9178.90]" );
      assertIsFalse( "ip4.with.number9.before.start.bracket@9[123.145.178.90]" );
      assertIsFalse( "ip4.with.number9.after.start.bracket@[9123.145.178.90]" );
      assertIsFalse( "ip4.with.number9.before.end.bracket@[123.145.178.909]" );
      assertIsFalse( "ip4.with.number9.after.end.bracket@[123.145.178.90]9" );

      assertIsFalse( "ip4.with.numbers.between.numbers@[123.1401234567895.178.90]" );
      assertIsFalse( "ip4.with.numbers.before.point@[123.1450123456789.178.90]" );
      assertIsFalse( "ip4.with.numbers.after.point@[123.145.0123456789178.90]" );
      assertIsFalse( "ip4.with.numbers.before.start.bracket@0123456789[123.145.178.90]" );
      assertIsFalse( "ip4.with.numbers.after.start.bracket@[0123456789123.145.178.90]" );
      assertIsFalse( "ip4.with.numbers.before.end.bracket@[123.145.178.900123456789]" );
      assertIsFalse( "ip4.with.numbers.after.end.bracket@[123.145.178.90]0123456789" );

      assertIsFalse( "ip4.with.byte.overflow.between.numbers@[123.149995.178.90]" );
      assertIsFalse( "ip4.with.byte.overflow.before.point@[123.145999.178.90]" );
      assertIsFalse( "ip4.with.byte.overflow.after.point@[123.145.999178.90]" );
      assertIsFalse( "ip4.with.byte.overflow.before.start.bracket@999[123.145.178.90]" );
      assertIsFalse( "ip4.with.byte.overflow.after.start.bracket@[999123.145.178.90]" );
      assertIsFalse( "ip4.with.byte.overflow.before.end.bracket@[123.145.178.90999]" );
      assertIsFalse( "ip4.with.byte.overflow.after.end.bracket@[123.145.178.90]999" );

      assertIsFalse( "ip4.with.no.hex.number.between.numbers@[123.14xyz5.178.90]" );
      assertIsFalse( "ip4.with.no.hex.number.before.point@[123.145xyz.178.90]" );
      assertIsFalse( "ip4.with.no.hex.number.after.point@[123.145.xyz178.90]" );
      assertIsFalse( "ip4.with.no.hex.number.before.start.bracket@xyz[123.145.178.90]" );
      assertIsFalse( "ip4.with.no.hex.number.after.start.bracket@[xyz123.145.178.90]" );
      assertIsFalse( "ip4.with.no.hex.number.before.end.bracket@[123.145.178.90xyz]" );
      assertIsFalse( "ip4.with.no.hex.number.after.end.bracket@[123.145.178.90]xyz" );

      assertIsFalse( "ip4.with.slash.between.numbers@[123.14\\5.178.90]" );
      assertIsFalse( "ip4.with.slash.before.point@[123.145\\.178.90]" );
      assertIsFalse( "ip4.with.slash.after.point@[123.145.\\178.90]" );
      assertIsFalse( "ip4.with.slash.before.start.bracket@\\[123.145.178.90]" );
      assertIsFalse( "ip4.with.slash.after.start.bracket@[\\123.145.178.90]" );
      assertIsFalse( "ip4.with.slash.before.end.bracket@[123.145.178.90\\]" );
      assertIsFalse( "ip4.with.slash.after.end.bracket@[123.145.178.90]\\" );

      assertIsFalse( "ip4.with.string.between.numbers@[123.14\"str\"5.178.90]" );
      assertIsFalse( "ip4.with.string.before.point@[123.145\"str\".178.90]" );
      assertIsFalse( "ip4.with.string.after.point@[123.145.\"str\"178.90]" );
      assertIsFalse( "ip4.with.string.before.start.bracket@\"str\"[123.145.178.90]" );
      assertIsFalse( "ip4.with.string.after.start.bracket@[\"str\"123.145.178.90]" );
      assertIsFalse( "ip4.with.string.before.end.bracket@[123.145.178.90\"str\"]" );
      assertIsFalse( "ip4.with.string.after.end.bracket@[123.145.178.90]\"str\"" );

      assertIsFalse( "ip4.with.comment.between.numbers@[123.14(comment)5.178.90]" );
      assertIsFalse( "ip4.with.comment.before.point@[123.145(comment).178.90]" );
      assertIsFalse( "ip4.with.comment.after.point@[123.145.(comment)178.90]" );
      assertIsTrue( "ip4.with.comment.before.start.bracket@(comment)[123.145.178.90]" );
      assertIsFalse( "ip4.with.comment.after.start.bracket@[(comment)123.145.178.90]" );
      assertIsFalse( "ip4.with.comment.before.end.bracket@[123.145.178.90(comment)]" );
      assertIsTrue( "ip4.with.comment.after.end.bracket@[123.145.178.90](comment)" );

      assertIsTrue( "email@[123.123.123.123]" );
      assertIsFalse( "email@111.222.333" );
      assertIsFalse( "email@111.222.333.256" );
      assertIsFalse( "email@[123.123.123.123" );
      assertIsFalse( "email@[123.123.123].123" );
      assertIsFalse( "email@123.123.123.123]" );
      assertIsFalse( "email@123.123.[123.123]" );

      assertIsFalse( "ab@988.120.150.10" );
      assertIsFalse( "ab@120.256.256.120" );
      assertIsFalse( "ab@120.25.1111.120" );
      assertIsFalse( "ab@[188.120.150.10" );
      assertIsFalse( "ab@188.120.150.10]" );
      assertIsFalse( "ab@[188.120.150.10].com" );
      assertIsTrue( "ab@188.120.150.10" );
      assertIsTrue( "ab@1.0.0.10" );
      assertIsTrue( "ab@120.25.254.120" );
      assertIsTrue( "ab@01.120.150.1" );
      assertIsTrue( "ab@88.120.150.021" );
      assertIsTrue( "ab@88.120.150.01" );
      assertIsTrue( "email@123.123.123.123" );

      wlHeadline( "IP V6" );

      assertIsTrue( "ABC.DEF@[IPv6:2001:db8::1]" );
      assertIsFalse( "ABC.DEF@[IP" );
      assertIsFalse( "ABC.DEF@[IPv6]" );

      assertIsFalse( "ABC.DEF@[IPv6:]" );
      assertIsFalse( "ABC.DEF@[IPv6:" );
      assertIsFalse( "ABC.DEF@[IPv6::]" );
      assertIsFalse( "ABC.DEF@[IPv6::" );
      assertIsFalse( "ABC.DEF@[IPv6:::::...]" );
      assertIsFalse( "ABC.DEF@[IPv6:::::..." );
      assertIsFalse( "ABC.DEF@[IPv6::::::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5::]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6:7" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv4:1:2:3:4]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2::4:5::]" );
      assertIsFalse( "ABC.DEF@[I127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[D127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[iPv6:2001:db8::1]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3::5::7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:Z]" );
      assertIsFalse( "ABC.DEF@[IPv6:12:34]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC.DEF@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])" );
      assertIsFalse( "ABC.DEF@([IPv6:1:2:3:4:5:6])" );

      assertIsFalse( "ABC.DEF@[IPv6:1:-2:3:4:5:]" );

      assertIsFalse( "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]." );
      assertIsFalse( "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[.IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:2..2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22..:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22:..3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7..]" );
      assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7].." );
      assertIsFalse( "ip.v6.with.double.dot@..[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.dot@[..IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&" );
      assertIsFalse( "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[&IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*" );
      assertIsFalse( "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[*IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_" );
      assertIsFalse( "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[_IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$" );
      assertIsFalse( "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[$IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]=" );
      assertIsFalse( "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[=IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!" );
      assertIsFalse( "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[!IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?" );
      assertIsFalse( "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[?IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`" );
      assertIsFalse( "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[`IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#" );
      assertIsFalse( "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[#IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%" );
      assertIsFalse( "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[%IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|" );
      assertIsFalse( "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[|IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+" );
      assertIsFalse( "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[+IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{" );
      assertIsFalse( "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[{IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}" );
      assertIsFalse( "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[}IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7](" );
      assertIsFalse( "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[(IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])" );
      assertIsFalse( "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[)IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7][" );
      assertIsFalse( "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );
      assertIsFalse( "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[]IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]" );
      assertIsTrue( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()" );
      assertIsTrue( "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[()IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}" );
      assertIsFalse( "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[{}IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[[]IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>" );
      assertIsFalse( "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[<>IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])(" );
      assertIsFalse( "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[)(IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{" );
      assertIsFalse( "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[}{IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><" );
      assertIsFalse( "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[><IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<" );
      assertIsFalse( "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[<IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>" );
      assertIsFalse( "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[>IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~" );
      assertIsFalse( "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[~IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^" );
      assertIsFalse( "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[^IPv6:1:22:3:4:5:6:7]" );

      assertIsTrue( "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]" );
      assertIsFalse( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:" );
      assertIsFalse( "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.colon@[:IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] " );
      assertIsFalse( "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[ IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7]," );
      assertIsFalse( "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[,IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@" );
      assertIsFalse( "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[@IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§" );
      assertIsFalse( "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[§IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'" );
      assertIsFalse( "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@['IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/" );
      assertIsFalse( "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-" );
      assertIsFalse( "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"" );
      assertIsFalse( "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b" );
      assertIsFalse( "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\"" );
      assertIsFalse( "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\"" );
      assertIsFalse( "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]" );

      assertIsTrue( "ip.v6.with.number0@[IPv6:1:202:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.number0@[IPv6:1:220:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.number0@[IPv6:1:22:03:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:70]" );
      assertIsFalse( "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:7]0" );
      assertIsFalse( "ip.v6.with.number0@0[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.number0@[0IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.number9@[IPv6:1:292:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.number9@[IPv6:1:229:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.number9@[IPv6:1:22:93:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:79]" );
      assertIsFalse( "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:7]9" );
      assertIsFalse( "ip.v6.with.number9@9[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.number9@[9IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789" );
      assertIsFalse( "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[0123456789IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7999]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]999" );
      assertIsFalse( "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz" );
      assertIsFalse( "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.slash@[IPv6:1:2\\2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22\\:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:\\3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\\]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\\" );
      assertIsFalse( "ip.v6.with.slash@\\[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[\\IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\"" );
      assertIsFalse( "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]" );
      assertIsTrue( "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)" );
      assertIsTrue( "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]" );

      /*
       * https://formvalidation.io/guide/validators/ip/
       */

      assertIsTrue( "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]" );
      assertIsTrue( "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]" );
      assertIsTrue( "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]" );
      assertIsTrue( "ABC.DEF@[IPv6:fe00::1]" );
      assertIsFalse( "ABC.DEF@[IPv6:10.168.0001.100]" );
      assertIsFalse( "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]" );
      assertIsFalse( "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]" );
      assertIsFalse( "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]" );

      wlHeadline( "IP V4 embedded in IP V6" );

      assertIsTrue( "ABC.DEF@[IPv6::FFFF:127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[IPv6::ffff:127.0.0.1]" );

      assertIsTrue( "ABC.DEF@[::FFFF:127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[::ffff:127.0.0.1]" );

      assertIsFalse( "ABC.DEF@[IPv6::ffff:.127.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::fff:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::1234:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6:::127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::FFFF:-127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::FFFF:127.0.-0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.999]" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.0001]" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]" );

      wlHeadline( "ip4 without brackets" );

      assertIsTrue( "ip4.without.brackets.ok1@127.0.0.1" );
      assertIsTrue( "ip4.without.brackets.ok2@0.0.0.0" );
      assertIsFalse( "ip4.without.brackets.but.with.space.at.end@127.0.0.1 " );
      assertIsFalse( "ip4.without.brackets.byte.overflow@127.0.999.1" );
      assertIsFalse( "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1" );
      assertIsFalse( "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1" );
      assertIsFalse( "ip4.without.brackets.negative.number@127.0.-1.1" );
      assertIsFalse( "ip4.without.brackets.point.error1@127.0..0.1" );
      assertIsFalse( "ip4.without.brackets.point.error1@127...1" );
      assertIsFalse( "ip4.without.brackets.error.bracket@127.0.1.1[]" );

      assertIsFalse( "ip4.without.brackets.point.error2@127001" );
      assertIsFalse( "ip4.without.brackets.point.error3@127.0.0." );
      assertIsFalse( "ip4.without.brackets.character.error@127.0.A.1" );
      assertIsFalse( "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1" );
      assertIsTrue( "ip4.without.brackets.normal.tld1@127.0.0.1.com" );
      assertIsTrue( "ip4.without.brackets.normal.tld2@127.0.99.1.com" );
      assertIsTrue( "ip4.without.brackets.normal.tld3@127.0.A.1.com" );

      wlHeadline( "Strings" );

      assertIsTrue( "\"ABC.DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\".\"DEF\"@GHI.DE" );
      assertIsFalse( "-\"ABC\".\"DEF\"@GHI.DE" );
      assertIsFalse( "\"ABC\"-.\"DEF\"@GHI.DE" );
      assertIsFalse( "\"ABC\".-\"DEF\"@GHI.DE" );
      assertIsFalse( ".\"ABC\".\"DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC@DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\\"DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\@DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\'DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\\\DEF\"@GHI.DE" );
      assertIsFalse( "\"ABC DEF@G\"HI.DE" );
      assertIsFalse( "\"\"@GHI.DE" );
      assertIsFalse( "\"ABC.DEF@G\"HI.DE" );
      assertIsFalse( "A@G\"HI.DE" );
      assertIsFalse( "\"@GHI.DE" );
      assertIsFalse( "ABC.DEF.\"" );
      assertIsFalse( "ABC.DEF@\"\"" );
      assertIsFalse( "ABC.DEF@G\"HI.DE" );
      assertIsFalse( "ABC.DEF@GHI.DE\"" );
      assertIsFalse( "ABC.DEF@\"GHI.DE" );
      assertIsFalse( "\"Escape.Sequenz.Ende\"" );
      assertIsFalse( "ABC.DEF\"GHI.DE" );
      assertIsFalse( "ABC.DEF\"@GHI.DE" );
      assertIsFalse( "ABC.DE\"F@GHI.DE" );
      assertIsFalse( "\"ABC.DEF@GHI.DE" );
      assertIsFalse( "\"ABC.DEF@GHI.DE\"" );
      assertIsTrue( "\".ABC.DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC.DEF.\"@GHI.DE" );
      assertIsTrue( "\"ABC\".DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "A\"BC\".DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "\"ABC\".DEF.G\"HI\"@JKL.de" );
      assertIsFalse( "\"AB\"C.DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "\"ABC\".DEF.\"GHI\"J@KL.de" );
      assertIsFalse( "\"AB\"C.D\"EF\"@GHI.DE" );
      assertIsFalse( "A\"B\"C.D\"E\"F@GHI.DE" );
      assertIsFalse( "ABC.DEF@G\"H\"I.DE" );

      assertIsFalse( "\"\".\"\".ABC.DEF@GHI.DE" );
      assertIsFalse( "\"\"\"\"ABC.DEF@GHI.DE" );
      assertIsFalse( "AB\"\"\"\"C.DEF@GHI.DE" );
      assertIsFalse( "ABC.DEF@G\"\"\"\"HI.DE" );
      assertIsFalse( "ABC.DEF@GHI.D\"\"\"\"E" );
      assertIsFalse( "ABC.DEF@GHI.D\"\".\"\"E" );
      assertIsFalse( "ABC.DEF@GHI.\"\"\"\"DE" );
      assertIsFalse( "ABC.DEF@GHI.\"\".\"\"DE" );
      assertIsFalse( "ABC.DEF@GHI.D\"\"E" );
      assertIsFalse( "\"\".ABC.DEF@GHI.DE" );

      assertIsFalse( "ABC.DEF\"@GHI.DE" );
      assertIsFalse( "ABC.DEF.\"@GHI.DE" );

      assertIsTrue( "\"ABC..DEF\"@GHI.JKL" );
      assertIsTrue( "AB.\"(CD)\".EF@GHI.JKL" );
      assertIsTrue( "\"ABC.\\\".\\\".DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC.\\\"\\\".DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC.\\\" \\@ \\\".DEF\"@GHI.DE" );
      assertIsFalse( "\"Ende.am.Eingabeende\"" );
      assertIsFalse( "0\"00.000\"@GHI.JKL" );

      assertIsTrue( "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de" );
      assertIsFalse( "\"A[B]C\".D(E)F.\"GHI\"@JKL.de" );
      assertIsFalse( "\"A[B]C\".D[E]F.\"GHI\"@JKL.de" );
      assertIsFalse( "\"A[B]C\".D<E>F.\"GHI\"@JKL.de" );
      assertIsTrue( "\"()<>[]:.;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org" );

      assertIsTrue( "\"ABC \\\"\\\\\\\" !\".DEF@GHI.DE" );

      assertIsFalse( "\"Joe Smith\" email@domain.com" );
      assertIsFalse( "\"Joe\\tSmith\".email@domain.com" );
      assertIsFalse( "\"Joe\"Smith\" email@domain.com" );

      wlHeadline( "Comments" );

      assertIsTrue( "(ABC)DEF@GHI.JKL" );
      assertIsTrue( "(ABC) DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF(GHI)    @JKL.MNO" );
      assertIsFalse( "(ABC) (DEF)  GHI@JKL.MNO" );
      assertIsFalse( "(ABC) (DEF).GHI@JKL.MNO" );
      assertIsTrue( "ABC(DEF)@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.JKL(MNO)" );
      assertIsFalse( "ABC.DEF@GHI.JKL(MNO)    " );
      assertIsTrue( "ABC.DEF@(GHI)JKL.MNO" );
      assertIsTrue( "ABC.DEF@(GHI.@.)JKL.MNO" );
      assertIsFalse( "ABC.DEF@(GHI\\@)JKL.MNO" );
      assertIsFalse( "ABC.DEF@(GHI)   JKL.MNO" );
      assertIsFalse( "ABC.DEF@(GHI.)   JKL.MNO" );
      assertIsFalse( "ABC.DEF@(GHI.) (ABC)JKL.MNO" );
      assertIsFalse( "ABC.DEF@(GHI.)   JKL(MNO)" );
      assertIsTrue( "ABC.DEF@GHI.JKL      (MNO)" );
      assertIsFalse( "ABC.DEF@             (MNO)" );
      assertIsFalse( "ABC.DEF@   .         (MNO)" );
      assertIsFalse( "ABC.DEF              (MNO)" );
      assertIsFalse( "ABC.DEF@GHI.         (MNO)" );
      assertIsFalse( "ABC.DEF@GHI.JKL       MNO" );
      assertIsFalse( "ABC.DEF@GHI.JKL          " );
      assertIsFalse( "ABC.DEF@GHI.JKL       .  " );

      assertIsFalse( "(" );
      assertIsFalse( ")" );
      assertIsTrue( "ABC.DEF@GHI.JKL ()" );
      assertIsTrue( "ABC.DEF@GHI.JKL()" );
      assertIsTrue( "ABC.DEF@()GHI.JKL" );
      assertIsTrue( "ABC.DEF()@GHI.JKL" );
      assertIsTrue( "()ABC.DEF@GHI.JKL" );
      assertIsFalse( "()()()ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GHI.JKL)" );
      assertIsFalse( "ABC.DEF@GHI.JKL ()()" );
      assertIsFalse( "(ABC)(DEF)@GHI.JKL" );
      assertIsFalse( "(ABC()DEF)@GHI.JKL" );
      assertIsFalse( "(ABC(Z)DEF)@GHI.JKL" );
      assertIsFalse( "(ABC).(DEF)@GHI.JKL" );
      assertIsFalse( "(ABC).DEF@GHI.JKL" );
      assertIsFalse( "ABC.(DEF)@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GHI).JKL" );
      assertIsFalse( "ABC.DEF@GHI.(JKL).MNO" );
      assertIsFalse( "ABC.DEF@GHI.JK(L.M)NO" );
      assertIsFalse( "AB(CD)EF@GHI.JKL" );
      assertIsFalse( "AB.(CD).EF@GHI.JKL" );
      assertIsFalse( "(ABCDEF)@GHI.JKL" );
      assertIsFalse( "(ABCDEF).@GHI.JKL" );
      assertIsFalse( "(AB\"C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\@C)DEF@GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI)JKL" );
      assertIsFalse( ")ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF)@GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI).JKL" );
      assertIsFalse( "ABC(DEF.GHI).JKL" );
      assertIsFalse( "(ABC.DEF@GHI.JKL)" );
      assertIsFalse( "(A(B(C)DEF@GHI.JKL" );
      assertIsFalse( "(A)B)C)DEF@GHI.JKL" );
      assertIsFalse( "(A)BCDE(F)@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GH)I.JK(LM)" );
      assertIsFalse( "ABC.DEF@(GH(I.JK)L)M" );
      assertIsTrue( "ABC.DEF@(comment)[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@(comment) [1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4](comment)" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]    (comment)" );
      assertIsFalse( "ABC.DEF@[1.2.3(comment).4]" );
      assertIsTrue( "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)" );
      assertIsFalse( "(Comment).ABC.DEF@GHI.JKL" );
      assertIsTrue( "(Comment)-ABC.DEF@GHI.JKL" );
      assertIsTrue( "(Comment)_ABC.DEF@GHI.JKL" );
      assertIsFalse( "-(Comment)ABC.DEF@GHI.JKL" );
      assertIsFalse( ".(Comment)ABC.DEF@GHI.JKL" );

      assertIsTrue( "(comment)john.smith@example.com" );
      assertIsTrue( "john.smith(comment)@example.com" );
      assertIsTrue( "john.smith@(comment)example.com" );
      assertIsTrue( "john.smith@example.com(comment)" );
      assertIsFalse( "john.smith@exampl(comment)e.com" );
      assertIsFalse( "john.s(comment)mith@example.com" );
      assertIsFalse( "john.smith(comment)@(comment)example.com" );
      assertIsFalse( "john.smith(com@ment)example.com" );
      assertIsFalse( "email( (nested) )@plus.com" );
      assertIsFalse( "email)mirror(@plus.com" );
      assertIsFalse( "email@plus.com (not closed comment" );
      assertIsFalse( "email(with @ in comment)plus.com" );
      assertIsTrue( "email@domain.com (joe Smith)" );
      assertIsFalse( "a@abc(bananas)def.com" );

      assertIsTrue( "\"address(comment\"@example.com" );
      assertIsFalse( "address(co\"mm\"ent)@example.com" );
      assertIsFalse( "address(co\"mment)@example.com" );

      wlHeadline( "Pointy Brackets" );

      assertIsTrue( "ABC DEF <ABC.DEF@GHI.JKL>" );
      assertIsTrue( "<ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL ABC DEF" );
      assertIsTrue( "\"ABC DEF \"<ABC.DEF@GHI.JKL>" );
      assertIsTrue( "\"ABC<DEF>\"@JKL.DE" );
      assertIsTrue( "\"ABC<DEF@GHI.COM>\"@JKL.DE" );
      assertIsFalse( "ABC DEF <ABC.<DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>" );
      assertIsFalse( "ABC DEF <ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF >ABC.DEF@GHI.JKL<" );
      assertIsFalse( ">ABC.DEF@GHI.JKL< ABC DEF" );
      assertIsFalse( "ABC DEF <A@A>" );
      assertIsFalse( "<A@A> ABC DEF" );
      assertIsFalse( "ABC DEF <>" );
      assertIsFalse( "<> ABC DEF" );
      assertIsFalse( "<" );
      assertIsFalse( ">" );
      assertIsFalse( "<         >" );
      assertIsFalse( "< <     > >" );
      assertIsTrue( "<ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<.ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL.>" );

      assertIsTrue( "<-ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL->" );

      assertIsTrue( "<_ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL_>" );

      assertIsTrue( "<(Comment)ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<(Comment).ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<.(Comment)ABC.DEF@GHI.JKL>" );
      assertIsTrue( "<(Comment)-ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<-(Comment)ABC.DEF@GHI.JKL>" );
      assertIsTrue( "<(Comment)_ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<_(Comment)ABC.DEF@GHI.JKL>" );

      assertIsTrue( "Joe Smith <email@domain.com>" );
      assertIsFalse( "Joe Smith <mailto:email@domain.com>" );
      assertIsFalse( "Joe Smith <mailto:email(with comment)@domain.com>" );
      assertIsTrue( "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsTrue( "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " );
      assertIsFalse( "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsFalse( "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " );
      assertIsFalse( "Test |<gaaf <email@domain.com>" );
      assertIsFalse( "Display Name <email@plus.com> (Comment after name with display)" );
      assertIsFalse( "\"With extra < within quotes\" Display Name<email@domain.com>" );
      assertIsFalse( "<null>@mail.com" );

      assertIsFalse( "email.adress@domain.com <display name>" );
      assertIsFalse( "eimail.adress@domain.com <eimail.adress@domain.com>" );
      assertIsFalse( "display.name@false.com <email.adress@correct.com>" );
      assertIsFalse( "<eimail>.<adress>@domain.com" );
      assertIsFalse( "<eimail>.<adress> email.adress@domain.com" );

      wlHeadline( "Length" );

      String str_50 = "12345678901234567890123456789012345678901234567890";
      String str_63 = "A23456789012345678901234567890123456789012345678901234567890123";
      //               1234567890          1234567890          1234567890          1234567890          
      String str_64 = "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz";

      assertIsTrue( "A@B.CD" );
      assertIsFalse( "A@B.C" );
      assertIsFalse( "A@COM" );

      assertIsTrue( "ABC.DEF@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI." + str_63 );
      assertIsFalse( "ABC.DEF@GHI." + str_63 + "A" );

      assertIsTrue( "A@B.CD" );

      assertIsTrue( str_64 + "@ZZZZZZZZZX.ZL" );
      assertIsFalse( str_64 + "T@ZZZZZZZZZX.ZL" );

      assertIsTrue( "True64 <" + str_64 + "@domain1.tld>" );
      assertIsFalse( "False64 <" + str_64 + "T@domain1.tld>" );

      assertIsTrue( "<" + str_64 + "@domain1.tld> True64 " );
      assertIsFalse( "<" + str_64 + "T@domain1.tld> False64 " );

      assertIsTrue( "<" + str_64 + "@domain1.tld>" );
      assertIsFalse( "<" + str_64 + "T@domain1.tld>" );

      assertIsTrue( "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );

      assertIsTrue( "domain.label.with.63.characters@" + str_63 + ".com" );
      assertIsFalse( "domain.label.with.64.characters@" + str_63 + "A.com" );
      assertIsTrue( "two.domain.labels.with.63.characters@" + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "domain.label.with.63.and.64.characters@" + str_63 + "." + str_63 + "A.com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + ".com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsTrue( "" + str_50 + ".1234567@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "" + str_50 + ".12345678@" + str_63 + "." + str_63 + "." + str_63 + ".com" );

      assertIsTrue( "eMail Test XX1 <63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );
      assertIsTrue( "eMail Test XX2 <" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );
      assertIsFalse( "eMail Test XX3 AAA<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );

      assertIsTrue( "" + str_50 + " " + str_50 + " " + str_50 + " " + str_50 + " " + str_50.substring( 0, 38 ) + "<A@B.de.com>" );

      assertIsTrue( "<63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test OK3" );
      assertIsTrue( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test OK4" );
      assertIsFalse( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test FALSE3" );
      assertIsFalse( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + "A.com> eMail Test FALSE4" );

      assertIsTrue( "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com" );
      assertIsFalse( "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" );
      assertIsFalse( "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" );
      assertIsFalse( "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" );

      assertIsTrue( "email@domain.topleveldomain" );
      assertIsTrue( "email@email.email.mydomain" );

      wlHeadline( "https://en.wikipedia.org/wiki/Email_address/" );

      assertIsTrue( "MaxMuster(Kommentar)@example.com" );
      assertIsTrue( "\"MaxMustermann\"@example.com" );
      assertIsTrue( "Max.\"Musterjunge\".Mustermann@example.com" );

      assertIsTrue( "\".John.Doe\"@example.com" );
      assertIsTrue( "\"John.Doe.\"@example.com" );
      assertIsTrue( "\"John..Doe\"@example.com" );
      assertIsTrue( "john.smith(comment)@example.com" );
      assertIsTrue( "(comment)john.smith@example.com" );
      assertIsTrue( "john.smith@(comment)example.com" );
      assertIsTrue( "john.smith@example.com(comment)" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "joeuser+tag@example.com" );

      assertIsTrue( "jsmith@[192.168.2.1]" );
      assertIsTrue( "jsmith@[IPv6:2001:db8::1]" );
      assertIsTrue( "surelsaya@surabaya.vibriel.net.id" );
      assertIsTrue( "Marc Dupont <md118@example.com>" );
      assertIsTrue( "simple@example.com" );
      assertIsTrue( "very.common@example.com" );
      assertIsTrue( "disposable.style.email.with+symbol@example.com" );
      assertIsTrue( "other.email-with-hyphen@example.com" );
      assertIsTrue( "fully-qualified-domain@example.com" );
      assertIsTrue( "user.name+tag+sorting@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "!#$%&'*+-/=?^_`.{|}~@example.com" );
      assertIsTrue( "x@example.com" );
      assertIsTrue( "info@firma.org" );
      assertIsTrue( "example-indeed@strange-example.com" );
      assertIsTrue( "admin@mailserver1" );
      assertIsTrue( "example@s.example" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "mailhost!username@example.org" );
      assertIsTrue( "user%example.com@example.org" );
      assertIsTrue( "joe25317@NOSPAMexample.com" );
      assertIsTrue( "Peter.Zapfl@Telekom.DBP.De" );
      assertIsTrue( "nama@contoh.com" );
      assertIsTrue( "Peter.Zapfl@Telekom.DBP.De" );
      assertIsFalse( "\"John Smith\" (johnsmith@example.com)" ); // ?
      assertIsFalse( "Abc.example.com" );
      assertIsFalse( "Abc..123@example.com" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsFalse( "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "just\"not\"right@example.com" );
      assertIsFalse( "this is\"not\\allowed@example.com" );
      assertIsFalse( "this\\ still\\\"not\\\\allowed@example.com" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );
      assertIsTrue( "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" );
      assertIsTrue( "(buero)office@example.com" );
      assertIsTrue( "office(etage-3)@example.com" );
      assertIsFalse( "off(kommentar)ice@example.com" );
      assertIsTrue( "\"(buero)office\"@example.com" );
      assertIsTrue( "\"office(etage-3)\"@example.com" );
      assertIsTrue( "\"off(kommentar)ice\"@example.com" );
      assertIsTrue( "\"address(comment)\"@example.com" );
      assertIsTrue( "Buero <office@example.com>" );
      assertIsTrue( "\"vorname(Kommentar).nachname\"@provider.de" );
      assertIsTrue( "\"Herr \\\"Kaiser\\\" Franz Beckenbauer\" <local-part@domain-part.com>" );
      assertIsTrue( "Erwin Empfaenger <erwin@example.com>" );

      wlHeadline( "https://github.com/egulias/EmailValidator4J" );

      assertIsFalse( "nolocalpart.com" );
      assertIsFalse( "test@example.com test" );
      assertIsFalse( "user  name@example.com" );
      assertIsFalse( "user   name@example.com" );
      assertIsFalse( "example.@example.co.uk" );
      assertIsFalse( "example@example@example.co.uk" );
      assertIsFalse( "(test_exampel@example.fr}" );
      assertIsFalse( "example(example)example@example.co.uk" );
      assertIsFalse( ".example@localhost" );
      assertIsFalse( "ex\\ample@localhost" );
      assertIsFalse( "example@local\\host" );
      assertIsFalse( "example@localhost." );
      assertIsFalse( "user name@example.com" );
      assertIsFalse( "username@ example . com" );
      assertIsFalse( "example@(fake}.com" );
      assertIsFalse( "example@(fake.com" );
      assertIsTrue( "username@example.com" );
      assertIsTrue( "usern.ame@example.com" );
      assertIsFalse( "user[na]me@example.com" );
      assertIsFalse( "\"\"\"@iana.org" );
      assertIsFalse( "\"\\\"@iana.org" );
      assertIsFalse( "\"test\"test@iana.org" );
      assertIsFalse( "\"test\"\"test\"@iana.org" );
      assertIsTrue( "\"test\".\"test\"@iana.org" );
      assertIsTrue( "\"test\".test@iana.org" );
      assertIsFalse( String.format( "\"test\\%s@iana.org", "\"" ) );
      assertIsFalse( "\r\ntest@iana.org" );
      assertIsFalse( "\r\n test@iana.org" );
      assertIsFalse( "\r\n \r\ntest@iana.org" );
      assertIsFalse( "\r\n \r\n test@iana.org" );
      assertIsFalse( "test@iana.org \r\n" );
      assertIsFalse( "test@iana.org \r\n " );
      assertIsFalse( "test@iana.org \r\n \r\n" );
      assertIsFalse( "test@iana.org \r\n\r\n" );
      assertIsFalse( "test@iana.org  \r\n\r\n " );
      assertIsFalse( "test@iana/icann.org" );
      assertIsFalse( "test@foo;bar.com" );
      assertIsFalse( (char) 1 + "a@test.com" );
      assertIsFalse( "comment)example@example.com" );
      assertIsFalse( "comment(example))@example.com" );
      assertIsFalse( "example@example)comment.com" );
      assertIsFalse( "example@example(comment)).com" );
      assertIsFalse( "example@[1.2.3.4" );
      assertIsFalse( "example@[IPv6:1:2:3:4:5:6:7:8" );
      assertIsFalse( "exam(ple@exam).ple" );
      assertIsFalse( "example@(example))comment.com" );
      assertIsTrue( "example@example.com" );
      assertIsTrue( "example@example.co.uk" );
      assertIsTrue( "example_underscore@example.fr" );
      assertIsTrue( "exam'ple@example.com" );
      assertIsTrue( String.format( "exam\\%sple@example.com", " " ) );
      assertIsFalse( "example((example))@fakedfake.co.uk" );
      assertIsFalse( "example@faked(fake).co.uk" );
      assertIsTrue( "example+@example.com" );
      assertIsTrue( "example@with-hyphen.example.com" );
      assertIsTrue( "with-hyphen@example.com" );
      assertIsTrue( "example@1leadingnum.example.com" );
      assertIsTrue( "1leadingnum@example.com" );
      assertIsTrue( "инфо@письмо.рф" );
      assertIsTrue( "\"username\"@example.com" );
      assertIsTrue( "\"user.name\"@example.com" );
      assertIsTrue( "\"user name\"@example.com" );
      assertIsTrue( "\"user@name\"@example.com" );
      assertIsFalse( "\"\\a\"@iana.org" );
      assertIsTrue( "\"test\\ test\"@iana.org" );
      assertIsFalse( "\"\"@iana.org" );
      assertIsFalse( "\"\"@[]" );
      assertIsTrue( String.format( "\"\\%s\"@iana.org", "\"" ) );
      assertIsTrue( "example@localhost" );

      wlHeadline( "unsorted from the WEB" );

      /*
       * <pre>
       * 
       * Various examples from the Internet.
       * 
       * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
       * https://stackoverflow.com/questions/25471114/how-to-validate-an-e-mail-address-in-swift?noredirect=1&lq=1
       * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
       * https://stackoverflow.com/questions/6850894/regex-split-email-address?noredirect=1&lq=1
       * https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript?page=3&tab=votes#tab-top
       * https://stackoverflow.com/questions/22993545/ruby-email-validation-with-regex?noredirect=1&lq=1
       * https://docs.microsoft.com/en-us/dotnet/api/system.net.mail.mailaddress.address?redirectedfrom=MSDN&view=netframework-4.8#System_Net_Mail_MailAddress_Address
       * https://github.com/manishsaraan/email-validator/blob/master/test.js
       * https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/
       * https://www.journaldev.com/638/java-email-validation-regex
       * https://www.linuxjournal.com/article/9585
       * 
       * </pre>
       */

      assertIsFalse( "<')))><@fish.left.com" );
      assertIsFalse( "><(((*>@fish.right.com" );
      assertIsFalse( " check@this.com" );
      assertIsFalse( " email@example.com" );
      assertIsFalse( ".....@a...." );
      assertIsFalse( "..@test.com" );
      assertIsFalse( "..@test.com" );
      assertIsTrue( "\"test....\"@gmail.com" ); // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression?noredirect=1&lq=1
      assertIsFalse( "test....@gmail.com" );
      assertIsTrue( "name@xn--4ca9at.at" );
      assertIsTrue( "simon-@hotmail.com" );
      assertIsTrue( "!@mydomain.net" );
      assertIsTrue( "sean.o'leary@cobbcounty.org" );
      assertIsTrue( "xjhgjg876896@domain.com" );
      assertIsTrue( "Tony Snow <tony@example.com>" );
      assertIsTrue( "(tony snow) tony@example.com" );
      assertIsTrue( "tony%example.com@example.org" );

      assertIsFalse( "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]" );

      assertIsFalse( "a-b'c_d.e@f-g.h" );
      assertIsFalse( "a-b'c_d.@f-g.h" );
      assertIsFalse( "a-b'c_d.e@f-.h" );
      assertIsTrue( "\"root\"@example.com" );
      assertIsTrue( "root@example.com" );

      assertIsFalse( ".@s.dd" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( ".abc@somedomain.com" );
      assertIsFalse( ".dot@example.com" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( ".journaldev@journaldev.com" );
      assertIsFalse( ".username@yahoo.com" );
      assertIsFalse( ".username@yahoo.com" );
      assertIsFalse( ".xxxx@mysite.org" );
      assertIsFalse( "asdf@asdf" );
      assertIsFalse( "123@$.xyz" );
      assertIsFalse( "<1234   @   local(blah)  .machine .example>" );
      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "@b.com" );
      assertIsFalse( "@domain.com" );
      assertIsFalse( "@example.com" );
      assertIsFalse( "@mail.example.com:joe@sixpack.com" );
      assertIsFalse( "@yahoo.com" );
      assertIsFalse( "@you.me.net" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsFalse( "Abc.example.com" );
      assertIsFalse( "Abc@example.com." );
      assertIsFalse( "Display Name <email@plus.com> (after name with display)" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ L\\.@example.com" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@example.com" );
      assertIsFalse( "Foobar Some@thing.com" );
      assertIsFalse( "Joe Smith &lt;email@domain.com&gt;" );
      assertIsFalse( "MailTo:casesensitve@domain.com" );
      assertIsFalse( "No -foo@bar.com" );
      assertIsFalse( "No asd@-bar.com" );
      assertIsFalse( "DomainHyphen@-atstart" );
      assertIsFalse( "DomainHyphen@atend-.com" );
      assertIsFalse( "DomainHyphen@bb.-cc" );
      assertIsFalse( "DomainHyphen@bb.-cc-" );
      assertIsFalse( "DomainHyphen@bb.cc-" );
      assertIsFalse( "DomainHyphen@bb.c-c" ); // https://tools.ietf.org/id/draft-liman-tld-names-01.html
      assertIsFalse( "DomainNotAllowedCharacter@/atstart" );
      assertIsTrue( "DomainNotAllowedCharacter@a.start" );
      assertIsFalse( "DomainNotAllowedCharacter@atst\\art.com" );
      assertIsFalse( "DomainNotAllowedCharacter@exa\\mple" );
      assertIsFalse( "DomainNotAllowedCharacter@example'" );
      assertIsFalse( "DomainNotAllowedCharacter@100%.de'" );
      assertIsTrue( "domain.starts.with.digit@2domain.com" );
      assertIsTrue( "domain.ends.with.digit@domain2.com" );
      assertIsFalse( "tld.starts.with.digit@domain.2com" );
      assertIsTrue( "tld.ends.with.digit@domain.com2" );

      assertIsFalse( "email@=qowaiv.com" );
      assertIsFalse( "email@plus+.com" );
      assertIsFalse( "email@domain.com>" );
      assertIsFalse( "email@mailto:domain.com" );
      assertIsFalse( "mailto:mailto:email@domain.com" );
      assertIsFalse( "email@-domain.com" );
      assertIsFalse( "email@domain-.com" );
      assertIsFalse( "email@domain.com-" );
      assertIsFalse( "email@{leftbracket.com" );
      assertIsFalse( "email@rightbracket}.com" );
      assertIsFalse( "email@pp|e.com" );

      assertIsTrue( "email@domain.domain.domain.com.com" );
      assertIsTrue( "email@domain.domain.domain.domain.com.com" );
      assertIsTrue( "email@domain.domain.domain.domain.domain.com.com" );

      assertIsFalse( "unescaped white space@fake$com" );
      assertIsFalse( "\"Joe Smith email@domain.com" );
      assertIsFalse( "\"Joe Smith' email@domain.com" );
      assertIsFalse( "\"Joe Smith\"email@domain.com" );
      assertIsFalse( "Joe Smith &lt;email@domain.com&gt;" );
      assertIsTrue( "{john'doe}@my.server" );
      assertIsTrue( "email@domain-one.com" );
      assertIsTrue( "_______@domain.com" );
      assertIsTrue( "?????@domain.com" );
      assertIsFalse( "local@?????.com" );
      assertIsTrue( "\"B3V3RLY H1LL$\"@example.com" );
      assertIsTrue( "\"-- --- .. -.\"@sh.de" );
      assertIsTrue( "{{-^-}{-=-}{-^-}}@GHI.JKL" );
      assertIsTrue( "#!$%&'*+-/=?^_`{}|~@eksample.org" );
      assertIsFalse( "eksample@#!$%&'*+-/=?^_`{}|~.org" );
      assertIsTrue( "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE" );
      assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2.4}" );
      assertIsTrue( "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de" );
      assertIsFalse( "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com" );

      assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@example.com" );
      assertIsFalse( "\"Joe Q. Public\" <john.q.public@example.com>" );
      assertIsFalse( "\"Joe\\Blow\"@example.com" );
      assertIsFalse( "\"\"Joe Smith email@domain.com" );
      assertIsFalse( "\"\"Joe Smith' email@domain.com" );
      assertIsFalse( "\"\"Joe Smith\"\"email@domain.com" );
      assertIsFalse( "\"qu@example.com" );
      assertIsFalse( "\\$A12345@example.com" );
      assertIsFalse( "_@bde.cc," );
      assertIsFalse( "a..b@bde.cc" );
      assertIsFalse( "a.\"b@c\".x.\"@\".d.e@f.g@" );
      assertIsFalse( "a.b@example,co.de" );
      assertIsFalse( "a.b@example,com" );
      assertIsFalse( "a>b@somedomain.com" );
      assertIsFalse( "a@.com" );
      assertIsFalse( "a@b." );
      assertIsFalse( "a@b.-de.cc" );
      assertIsFalse( "a@b._de.cc" );
      assertIsFalse( "a@bde-.cc" );
      assertIsFalse( "a@bde.c-c" );
      assertIsFalse( "a@bde.cc." );
      assertIsFalse( "a@bde_.cc" );
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "ab@120.25.1111.120" );
      assertIsFalse( "ab@120.256.256.120" );
      assertIsFalse( "ab@188.120.150.10]" );
      assertIsFalse( "ab@988.120.150.10" );
      assertIsFalse( "ab@[188.120.150.10" );
      assertIsFalse( "ab@[188.120.150.10].com" );
      assertIsFalse( "ab@b+de.cc" );
      assertIsFalse( "ab@sd@dd" );
      assertIsFalse( "abc.@somedomain.com" );
      assertIsFalse( "abc@def@example.com" );
      assertIsFalse( "abc@gmail..com" );
      assertIsFalse( "abc@gmail.com.." );
      assertIsFalse( "abc\"defghi\"xyz@example.com" );
      assertIsFalse( "abc\\@example.com" );
      assertIsFalse( "abc\\\"def\\\"ghi@example.com" );
      assertIsFalse( "abc\\\\@def@example.com" );
      assertIsFalse( "as3d@dac.coas-" );
      assertIsFalse( "asd@dasd@asd.cm" );
      assertIsFalse( "check@this..com" );
      assertIsFalse( "check@thiscom" );
      assertIsFalse( "da23@das..com" );
      assertIsFalse( "dad@sds" );
      assertIsFalse( "dasddas-@.com" );
      assertIsFalse( "david.gilbertson@SOME+THING-ODD!!.com" );
      assertIsFalse( "dot.@example.com" );
      assertIsFalse( "doug@" );
      assertIsFalse( "email( (nested) )@plus.com" );
      assertIsFalse( "email(with @ in comment)plus.com" );
      assertIsFalse( "email)mirror(@plus.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@#hash.com" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@111.222.333" );
      assertIsFalse( "email@111.222.333.256" );
      assertIsFalse( "email@123.123.123.123]" );
      assertIsFalse( "email@123.123.[123.123]" );
      assertIsFalse( "email@=qowaiv.com" );
      assertIsFalse( "email@[123.123.123.123" );
      assertIsFalse( "email@[123.123.123].123" );
      assertIsFalse( "email@caret^xor.com" );
      assertIsFalse( "email@colon:colon.com" );
      assertIsFalse( "email@dollar$.com" );
      assertIsFalse( "email@domain" );
      assertIsFalse( "email@domain-.com" );
      assertIsFalse( "email@domain..com" );
      assertIsFalse( "email@domain.com-" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@domain.com>" );
      assertIsFalse( "email@domain@domain.com" );
      assertIsFalse( "email@example" );
      assertIsFalse( "email@example..com" );
      assertIsFalse( "email@example.co.uk." );
      assertIsFalse( "email@example.com " );
      assertIsFalse( "email@exclamation!mark.com" );
      assertIsFalse( "email@grave`accent.com" );
      assertIsFalse( "email@mailto:domain.com" );
      assertIsFalse( "email@obelix*asterisk.com" );
      assertIsFalse( "email@plus+.com" );
      assertIsFalse( "email@plus.com (not closed comment" );
      assertIsFalse( "email@p|pe.com" );
      assertIsFalse( "email@question?mark.com" );
      assertIsFalse( "email@r&amp;d.com" );
      assertIsFalse( "email@rightbracket}.com" );
      assertIsFalse( "email@wave~tilde.com" );
      assertIsFalse( "email@{leftbracket.com" );
      assertIsFalse( "f...bar@gmail.com" );
      assertIsFalse( "fa ke@false.com" );
      assertIsFalse( "fake@-false.com" );
      assertIsFalse( "fake@fal se.com" );
      assertIsFalse( "fdsa" );
      assertIsFalse( "fdsa@" );
      assertIsFalse( "fdsa@fdsa" );
      assertIsFalse( "fdsa@fdsa." );
      assertIsFalse( "foo.bar#gmail.co.u" );
      assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );
      assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
      assertIsFalse( "foo~&(&)(@bar.com" );
      assertIsFalse( "gatsby@f.sc.ot.t.f.i.tzg.era.l.d." );
      assertIsFalse( "get_at_m.e@gmail" );
      assertIsFalse( "hallo2ww22@example....caaaao" );
      assertIsFalse( "hallo@example.coassjj#sswzazaaaa" );
      assertIsFalse( "hello world@example.com" );
      assertIsFalse( "invalid.email.com" );
      assertIsFalse( "invalid@email@domain.com" );
      assertIsFalse( "isis@100%.nl" );
      assertIsFalse( "j..s@proseware.com" );
      assertIsFalse( "j.@server1.proseware.com" );
      assertIsFalse( "jane@jungle.com: | /usr/bin/vacation" );
      assertIsFalse( "journaldev" );
      assertIsFalse( "journaldev()*@gmail.com" );
      assertIsFalse( "journaldev..2002@gmail.com" );
      assertIsFalse( "journaldev.@gmail.com" );
      assertIsFalse( "journaldev123@.com" );
      assertIsFalse( "journaldev123@.com.com" );
      assertIsFalse( "journaldev123@gmail.a" );
      assertIsFalse( "journaldev@%*.com" );
      assertIsFalse( "journaldev@.com.my" );
      assertIsFalse( "journaldev@gmail.com.1a" );
      assertIsFalse( "journaldev@journaldev@gmail.com" );
      assertIsFalse( "js@proseware..com" );
      assertIsFalse( "mailto:email@domain.com" );
      assertIsFalse( "mailto:mailto:email@domain.com" );
      assertIsFalse( "me..2002@gmail.com" );
      assertIsFalse( "me.@gmail.com" );
      assertIsFalse( "me123@.com" );
      assertIsFalse( "me123@.com.com" );
      assertIsFalse( "me@%*.com" );
      assertIsFalse( "me@.com.my" );
      assertIsFalse( "me@gmail.com.1a" );
      assertIsFalse( "me@me@gmail.com" );
      assertIsFalse( "myemail@@sample.com" );
      assertIsFalse( "myemail@sa@mple.com" );
      assertIsFalse( "myemailsample.com" );
      assertIsFalse( "ote\"@example.com" );
      assertIsFalse( "pio_pio@#factory.com" );
      assertIsFalse( "pio_pio@factory.c#om" );
      assertIsFalse( "pio_pio@factory.c*om" );
      assertIsFalse( "plain.address" );
      assertIsFalse( "plainaddress" );
      assertIsFalse( "tarzan@jungle.org,jane@jungle.org" );
      assertIsFalse( "this is not valid@email$com" );
      assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
      assertIsFalse( "two..dot@example.com" );
      assertIsFalse( "user#domain.com" );
      assertIsFalse( "username@yahoo..com" );
      assertIsFalse( "username@yahoo.c" );
      assertIsTrue( "username@domain.com" );
      assertIsTrue( "_username@domain.com" );
      assertIsTrue( "username_@domain.com" );
      assertIsFalse( "xxx@u*.com" );
      assertIsFalse( "xxxx..1234@yahoo.com" );
      assertIsFalse( "xxxx.ourearth.com" );
      assertIsFalse( "xxxx123@gmail.b" );
      assertIsFalse( "xxxx@.com.my" );
      assertIsFalse( "xxxx@.org.org" );
      assertIsFalse( "xxxxx()*@gmail.com" );
      assertIsFalse( "{something}@{something}.{something}" );

      assertIsTrue( "mymail\\@hello@hotmail.com" );

      assertIsTrue( "!def!xyz%abc@example.com" );
      assertIsTrue( "!sd@gh.com" );
      assertIsTrue( "$A12345@example.com" );
      assertIsTrue( "%20f3v34g34@gvvre.com" );
      assertIsTrue( "%2@gmail.com" );
      assertIsTrue( "--@ooo.ooo" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "-asd@das.com" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "1@gmail.com" );
      assertIsTrue( "1_example@something.gmail.com" );
      assertIsTrue( "2@bde.cc" );
      assertIsTrue( "3c296rD3HNEE@d139c.a51" );
      assertIsTrue( "<boss@nil.test>" );
      assertIsTrue( "<john@doe.com>" );
      assertIsTrue( "A__z/J0hn.sm{it!}h_comment@example.com.co" );
      assertIsTrue( "Abc.123@example.com" );
      assertIsTrue( "Abc@10.42.0.1" );
      assertIsTrue( "Abc@example.com" );
      assertIsTrue( "D.Oy'Smith@gmail.com" );
      assertIsTrue( "Fred\\ Bloggs@example.com" );
      assertIsTrue( "Joe.\\\\Blow@example.com" );
      assertIsTrue( "John <john@doe.com>" );
      assertIsTrue( "PN=Joe/OU=X400/@gateway.com" );
      assertIsTrue( "This is <john@127.0.0.1>" );
      assertIsTrue( "This is <john@[127.0.0.1]>" );
      assertIsTrue( "Who? <one@y.test>" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "\"%2\"@gmail.com" );
      assertIsTrue( "\"Abc@def\"@example.com" );
      assertIsTrue( "\"Abc\\@def\"@example.com" );
      assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
      assertIsTrue( "\"Giant; \\\"Big\\\" Box\" <sysservices@example.net>" );
      assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
      assertIsTrue( "\"Look at all these spaces!\"@example.com" );
      assertIsTrue( "\"a..b\"@gmail.com" );
      assertIsTrue( "\"a@b\"@example.com" );
      assertIsTrue( "\"a_b\"@gmail.com" );
      assertIsTrue( "\"abcdefghixyz\"@example.com" );
      assertIsTrue( "\"cogwheel the orange\"@example.com" );
      assertIsTrue( "\"foo\\@bar\"@Something.com" );
      assertIsTrue( "\"j\\\"s\"@proseware.com" );
      assertIsTrue( "\"myemail@sa\"@mple.com" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsTrue( "_@gmail.com" );
      assertIsTrue( "_dasd@sd.com" );
      assertIsTrue( "_dasd_das_@9.com" );
      assertIsTrue( "_somename@example.com" );
      assertIsTrue( "a&d@somedomain.com" );
      assertIsTrue( "a*d@somedomain.com" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "a+b@c.com" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "a.b@com" );
      assertIsTrue( "a/d@somedomain.com" );
      assertIsTrue( "a2@bde.cc" );
      assertIsTrue( "a@123.45.67.89" );
      assertIsTrue( "a@b.c.com" );
      assertIsTrue( "a@b.com" );
      assertIsTrue( "a@bc.com" );
      assertIsTrue( "a@bcom" );
      assertIsTrue( "a@domain.com" );
      assertIsTrue( "a__z@provider.com" );
      assertIsTrue( "a_z%@gmail.com" );
      assertIsTrue( "aaron@theinfo.org" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "ab@c.com" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "abc.\"defghi\".xyz@example.com" );
      assertIsTrue( "abc.efg@gmail.com" );
      assertIsTrue( "abc.xyz@gmail.com.in" );
      assertIsTrue( "abc123xyz@asdf.co.in" );
      assertIsTrue( "abc1_xyz1@gmail1.com" );
      assertIsTrue( "abc@abc.abc" );
      assertIsTrue( "abc@abc.abcd" );
      assertIsTrue( "abc@abc.abcde" );
      assertIsTrue( "abc@abc.co.in" );
      assertIsTrue( "abc@abc.com.com.com.com" );
      assertIsTrue( "abc@gmail.com.my" );
      assertIsTrue( "abc\\@def@example.com" );
      assertIsTrue( "abc\\\\@example.com" );
      assertIsTrue( "abcxyz123@qwert.com" );
      assertIsTrue( "alex@example.com" );
      assertIsTrue( "alireza@test.co.uk" );
      assertIsTrue( "asd-@asd.com" );
      assertIsTrue( "begeddov@jfinity.com" );
      assertIsTrue( "check@this.com" );
      assertIsTrue( "cog@wheel.com" );
      assertIsTrue( "customer/department=shipping@example.com" );
      assertIsTrue( "d._.___d@gmail.com" );
      assertIsTrue( "d.j@server1.proseware.com" );
      assertIsTrue( "d.oy.smith@gmail.com" );
      assertIsTrue( "d23d@da9.co9" );
      assertIsTrue( "d_oy_smith@gmail.com" );
      assertIsTrue( "dasd-dasd@das.com.das" );
      assertIsTrue( "dasd.dadas@dasd.com" );
      assertIsTrue( "dasd_-@jdas.com" );
      assertIsTrue( "david.jones@proseware.com" );
      assertIsTrue( "dclo@us.ibm.com" );
      assertIsTrue( "dda_das@das-dasd.com" );
      assertIsTrue( "digit-only-domain-with-subdomain@sub.123.com" );
      assertIsTrue( "digit-only-domain@123.com" );
      assertIsTrue( "doysmith@gmail.com" );
      assertIsTrue( "drp@drp.cz" );
      assertIsTrue( "dsq!a?@das.com" );
      assertIsTrue( "email@domain.co.de" );
      assertIsTrue( "email@domain.com" );
      assertIsTrue( "email@example.co.uk" );
      assertIsTrue( "email@example.com" );
      assertIsTrue( "email@mail.gmail.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "example@example.co" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsTrue( "f.o.o.b.a.r@gmail.com" );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
      assertIsTrue( "futureTLD@somewhere.fooo" );
      assertIsTrue( "hello.me_1@email.com" );
      assertIsTrue( "hello7___@ca.com.pt" );
      assertIsTrue( "info@ermaelan.com" );
      assertIsTrue( "j.s@server1.proseware.com" );
      assertIsTrue( "j@proseware.com9" );
      assertIsTrue( "j_9@[129.126.118.1]" );
      assertIsTrue( "jinujawad6s@gmail.com" );
      assertIsTrue( "john.doe@example.com" );
      assertIsTrue( "john.o'doe@example.com" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "jones@ms1.proseware.com" );
      assertIsTrue( "journaldev+100@gmail.com" );
      assertIsTrue( "journaldev-100@journaldev.net" );
      assertIsTrue( "journaldev-100@yahoo-test.com" );
      assertIsTrue( "journaldev-100@yahoo.com" );
      assertIsTrue( "journaldev.100@journaldev.com.au" );
      assertIsTrue( "journaldev.100@yahoo.com" );
      assertIsTrue( "journaldev111@journaldev.com" );
      assertIsTrue( "journaldev@1.com" );
      assertIsTrue( "journaldev@gmail.com.com" );
      assertIsTrue( "journaldev@yahoo.com" );
      assertIsTrue( "journaldev_100@yahoo-test.ABC.CoM" );
      assertIsTrue( "js#internal@proseware.com" );
      assertIsTrue( "js*@proseware.com" );
      assertIsTrue( "js@proseware.com9" );
      assertIsTrue( "me+100@me.com" );
      assertIsTrue( "me+alpha@example.com" );
      assertIsTrue( "me-100@me.com" );
      assertIsTrue( "me-100@me.com.au" );
      assertIsTrue( "me-100@yahoo-test.com" );
      assertIsTrue( "me.100@me.com" );
      assertIsTrue( "me@aaronsw.com" );
      assertIsTrue( "me@company.co.uk" );
      assertIsTrue( "me@gmail.com" );
      assertIsTrue( "me@gmail.com" );
      assertIsTrue( "me@mail.s2.example.com" );
      assertIsTrue( "me@me.cu.uk" );
      assertIsTrue( "my.ownsite@ourearth.org" );
      assertIsFalse( "myemail@sample" );
      assertIsTrue( "myemail@sample.com" );
      assertIsTrue( "mysite@you.me.net" );
      assertIsTrue( "o'hare@example.com" );
      assertIsTrue( "peter.example@domain.comau" );
      assertIsTrue( "peter.piper@example.com" );
      assertIsTrue( "peter_123@news.com" );
      assertIsTrue( "pio^_pio@factory.com" );
      assertIsTrue( "pio_#pio@factory.com" );
      assertIsTrue( "pio_pio@factory.com" );
      assertIsTrue( "pio_~pio@factory.com" );
      assertIsTrue( "piskvor@example.lighting" );
      assertIsTrue( "rss-dev@yahoogroups.com" );
      assertIsTrue( "someone+tag@somewhere.net" );
      assertIsTrue( "someone@somewhere.co.uk" );
      assertIsTrue( "someone@somewhere.com" );
      assertIsTrue( "something_valid@somewhere.tld" );
      assertIsTrue( "tvf@tvf.cz" );
      assertIsTrue( "user#@domain.co.in" );
      assertIsTrue( "user'name@domain.co.in" );
      assertIsTrue( "user+mailbox@example.com" );
      assertIsTrue( "user-name@domain.co.in" );
      assertIsTrue( "user.name@domain.com" );
      assertIsFalse( ".user.name@domain.com" );
      assertIsFalse( "user-name@domain.com." );
      assertIsFalse( "username@.com" );
      assertIsTrue( "user1@domain.com" );
      assertIsTrue( "user?name@domain.co.in" );
      assertIsTrue( "user@domain.co.in" );
      assertIsTrue( "user@domain.com" );
      assertIsFalse( "user@domaincom" );
      assertIsTrue( "user_name@domain.co.in" );
      assertIsTrue( "user_name@domain.com" );
      assertIsTrue( "username@yahoo.corporate" );
      assertIsTrue( "username@yahoo.corporate.in" );
      assertIsTrue( "username+something@domain.com" );
      assertIsTrue( "vdv@dyomedea.com" );
      assertIsTrue( "xxxx@gmail.com" );
      assertIsTrue( "xxxxxx@yahoo.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "user@[IPv6:2001:DB8::1]" );
      assertIsFalse( "user@localserver" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsTrue( "yoursite@ourearth.com" );
      assertIsTrue( "~pio_pio@factory.com" );

      assertIsTrue( "{^c\\@**Dog^}@cartoon.com" );
      assertIsFalse( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" );
      assertIsFalse( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" );
      assertIsTrue( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" );
      assertIsTrue( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" );
      assertIsFalse( "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" );
      assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );

      assertIsTrue( "valid@[1.1.1.1]" );
      assertIsTrue( "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]" );
      assertIsTrue( "valid.ipv6v4.addr@[IPv6:::12.34.56.78]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:::]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:::12.34.56.78]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]" );
      assertIsTrue( "valid.ipv6.addr@[IPv6:0::1]" );
      assertIsTrue( "valid.ipv4.addr@[255.255.255.255]" );
      assertIsTrue( "valid.ipv4.addr@[123.1.72.10]" );

      assertIsFalse( "invalid@[10]" );
      assertIsFalse( "invalid@[10.1]" );
      assertIsFalse( "invalid@[10.1.52]" );
      assertIsFalse( "invalid@[256.256.256.256]" );
      assertIsFalse( "invalid@[IPv6:123456]" );
      assertIsFalse( "invalid@[127.0.0.1.]" );
      assertIsFalse( "invalid@[127.0.0.1]." );
      assertIsFalse( "invalid@[127.0.0.1]x" );
      assertIsFalse( "invalid@domain1.com@domain2.com" );
      assertIsFalse( "\"locál-part\"@example.com" ); // international local-part when allowInternational=false should fail);
      assertIsFalse( "invalid@[IPv6:1::2:]" ); // incomplete IPv6);
      assertIsFalse( "invalid@[IPv6::1::1]" );

      assertIsFalse( "invalid@[]" ); // empty IP literal);
      assertIsFalse( "invalid@[111.111.111.111" ); // unenclosed IPv4 literal);
      assertIsFalse( "invalid@[IPv6:2607:f0d0:1002:51::4" ); // unenclosed IPv6 literal);
      assertIsFalse( "invalid@[IPv6:1111::1111::1111]" ); // invalid IPv6-comp);
      assertIsFalse( "invalid@[IPv6:1111:::1111::1111]" ); // more than 2 consecutive :'s in IPv6);
      assertIsFalse( "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]" ); // invalid IPv4 address in IPv6v4);
      assertIsFalse( "invalid@[IPv6:1111:1111]" ); // incomplete IPv6);
      assertIsFalse( "\"invalid-qstring@example.com" ); // unterminated q-string in local-part of the addr-spec);

      wlHeadline( "https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs" );

      assertIsTrue( "\"\\e\\s\\c\\a\\p\\e\\d\"@sld.com" );
      assertIsTrue( "\"back\\slash\"@sld.com" );
      assertIsTrue( "\"escaped\\\"quote\"@sld.com" );
      assertIsTrue( "\"quoted\"@sld.com" );
      assertIsTrue( "\"quoted-at-sign@sld.org\"@sld.com" );
      assertIsTrue( "&'*+-./=?^_{}~@other-valid-characters-in-local.net" );
      assertIsTrue( "_.-+~^*'`{GEO}`'*^~+-._@example.com" );
      assertIsTrue( "01234567890@numbers-in-local.net" );
      assertIsTrue( "a@single-character-in-local.org" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" );
      assertIsTrue( "backticksarelegit@test.com" );
      assertIsTrue( "bracketed-IP-instead-of-domain@[127.0.0.1]" );
      assertIsTrue( "country-code-tld@sld.rw" );
      assertIsTrue( "country-code-tld@sld.uk" );
      assertIsTrue( "letters-in-sld@123.com" );
      assertIsTrue( "local@dash-in-sld.com" );
      assertIsTrue( "local@sld.newTLD" );
      assertIsTrue( "local@sub.domains.com" );
      assertIsTrue( "mixed-1234-in-{+^}-local@sld.net" );
      assertIsTrue( "one-character-third-level@a.example.com" );
      assertIsTrue( "one-letter-sld@x.org" );
      assertIsTrue( "punycode-numbers-in-tld@sld.xn--3e0b707e" );
      assertIsTrue( "single-character-in-sld@x.org" );
      assertIsTrue( "uncommon-tld@sld.mobi" );
      assertIsTrue( "uncommon-tld@sld.museum" );
      assertIsTrue( "uncommon-tld@sld.travel" );
      assertIsFalse( "invalid" );
      assertIsFalse( "invalid@" );
      assertIsFalse( "invalid @" );
      assertIsFalse( "invalid@[555.666.777.888]" );
      assertIsFalse( "invalid@[IPv6:123456]" );
      assertIsFalse( "invalid@[127.0.0.1.]" );
      assertIsFalse( "invalid@[127.0.0.1]." );
      assertIsFalse( "invalid@[127.0.0.1]x" );
      assertIsFalse( "<>@[]`|@even-more-invalid-characters-in-local.org" );
      assertIsFalse( "@missing-local.org" );
      assertIsFalse( "IP-and-port@127.0.0.1:25" );
      assertIsFalse( "another-invalid-ip@127.0.0.256" );
      assertIsFalse( "ip.range.overflow@[127.0.0.256]" );
      assertIsFalse( "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org" );
      assertIsFalse( "invalid-ip@127.0.0.1.26" );
      assertIsFalse( "local-ends-with-dot.@sld.com" );
      assertIsFalse( "missing-at-sign.net" );
      assertIsFalse( "missing-sld@.com" );
      assertIsFalse( "missing-tld@sld." );
      assertIsFalse( "sld-ends-with-dash@sld-.com" );
      assertIsFalse( "sld-starts-with-dashsh@-sld.com" );
      assertIsFalse( "two..consecutive-dots@sld.com" );
      assertIsTrue( "unbracketed-IP@127.0.0.1" );
      assertIsFalse( "underscore.error@example.com_" );

      wlHeadline( "https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php" );

      assertIsTrue( "first.last@iana.org" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678901234@iana.org" );
      assertIsTrue( "\"first\\\"last\"@iana.org" );
      assertIsTrue( "\"first@last\"@iana.org" );
      assertIsTrue( "\"first\\\\last\"@iana.org" );
      assertIsTrue( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" );
      assertIsTrue( "first.last@[12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]" );
      assertIsTrue( "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" );
      assertIsTrue( "first.last@3com.com" );
      assertIsTrue( "first.last@123.iana.org" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]" );
      assertIsTrue( "\"Abc\\@def\"@iana.org" );
      assertIsTrue( "\"Fred\\ Bloggs\"@iana.org" );
      assertIsTrue( "\"Joe.\\\\Blow\"@iana.org" );
      assertIsTrue( "\"Abc@def\"@iana.org" );
      assertIsTrue( "\"Fred Bloggs\"@iana.orgin" );
      assertIsTrue( "user+mailbox@iana.org" );
      assertIsTrue( "$A12345@iana.org" );
      assertIsTrue( "!def!xyz%abc@iana.org" );
      assertIsTrue( "_somename@iana.org" );
      assertIsTrue( "dclo@us.ibm.com" );
      assertIsTrue( "peter.piper@iana.org" );
      assertIsTrue( "test@iana.org" );
      assertIsTrue( "TEST@iana.org" );
      assertIsTrue( "1234567890@iana.org" );
      assertIsTrue( "test+test@iana.org" );
      assertIsTrue( "test-test@iana.org" );
      assertIsTrue( "t*est@iana.org" );
      assertIsTrue( "+1~1+@iana.org" );
      assertIsTrue( "{_test_}@iana.org" );
      assertIsTrue( "test.test@iana.org" );
      assertIsTrue( "\"test.test\"@iana.org" );
      assertIsTrue( "test.\"test\"@iana.org" );
      assertIsTrue( "\"test@test\"@iana.org" );
      assertIsTrue( "test@123.123.123.x123" );
      assertIsTrue( "test@123.123.123.123" );
      assertIsTrue( "test@[123.123.123.123]" );
      assertIsTrue( "test@example.iana.org" );
      assertIsTrue( "test@example.example.iana.org" );
      assertIsTrue( "customer/department@iana.org" );
      assertIsTrue( "_Yosemite.Sam@iana.org" );
      assertIsTrue( "~@iana.org" );
      assertIsTrue( "\"Austin@Powers\"@iana.org" );
      assertIsTrue( "Ima.Fool@iana.org" );
      assertIsTrue( "\"Ima.Fool\"@iana.org" );
      assertIsTrue( "\"Ima Fool\"@iana.orgin" );
      assertIsTrue( "\"first\".\"last\"@iana.org" );
      assertIsTrue( "\"first\".middle.\"last\"@iana.org" );
      assertIsTrue( "\"first\".last@iana.org" );
      assertIsTrue( "first.\"last\"@iana.org" );
      assertIsTrue( "\"first\".\"middle\".\"last\"@iana.org" );
      assertIsTrue( "\"first.middle\".\"last\"@iana.org" );
      assertIsTrue( "\"first.middle.last\"@iana.org" );
      assertIsTrue( "\"first..last\"@iana.org" );
      assertIsTrue( "first.\"middle\".\"last\"@iana.org" );
      assertIsFalse( "first.last @iana.orgin" );
      assertIsTrue( "\"test blah\"@iana.orgin" );
      assertIsTrue( "name.lastname@domain.com" );
      assertIsTrue( "a@bar.com" );
      assertIsTrue( "aaa@[123.123.123.123]" );
      assertIsTrue( "a-b@bar.com" );
      assertIsFalse( "+@b.c" );
      assertIsTrue( "+@b.com" );
      assertIsTrue( "a@b.co-foo.uk" );
      assertIsTrue( "\"hello my name is\"@stutter.comin" );
      assertIsTrue( "\"Test \\\"Fail\\\" Ing\"@iana.orgin" );
      assertIsTrue( "shaitan@my-domain.thisisminekthx" );
      assertIsTrue( "foobar@192.168.0.1" );
      assertIsTrue( "HM2Kinsists@(that comments are allowed)this.is.ok" );
      assertIsTrue( "user%uucp!path@berkeley.edu" );
      assertIsTrue( "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com" );
      assertIsTrue( "test@test.com" );
      assertIsTrue( "test@xn--example.com" );
      assertIsTrue( "test@example.com" );
      assertIsTrue( "{^c\\@**Dog^}@cartoon.com" );
      assertIsTrue( "first\\@last@iana.org" );
      assertIsTrue( "phil.h\\@\\@ck@haacked.com" );
      assertIsFalse( "first.last@example.123" );
      assertIsFalse( "first.last@comin" );
      assertIsTrue( "\"[[ test ]]\"@iana.orgin" );
      assertIsTrue( "Abc\\@def@iana.org" );
      assertIsTrue( "Fred\\ Bloggs@iana.org" );
      assertIsFalse( "Joe.\\Blow@iana.org" );
      assertIsTrue( "first.last@sub.do.com" );
      assertIsFalse( "first.last" );
      assertIsTrue( "wild.wezyr@best-server-ever.com" );
      assertIsTrue( "\"hello world\"@example.com" );
      assertIsFalse( "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com" );
      assertIsTrue( "John.\"The*$hizzle*Bizzle\".Doe@whatever.com" );
      assertIsTrue( "example+tag@gmail.com" );
      assertIsFalse( ".ann..other.@example.com" );
      assertIsTrue( "ann.other@example.com" );
      assertIsTrue( "something@something.something" );
      assertIsTrue( "c@(Chris's host.)public.examplein" );
      assertIsFalse( "(foo)cal(bar)@(baz)iamcal.com(quux)" );
      assertIsFalse( "cal@iamcal(woo).(yay)comin" );
      assertIsFalse( "cal(woo(yay)hoopla)@iamcal.comin" );
      assertIsFalse( "cal(foo\\@bar)@iamcal.comin" );
      assertIsFalse( "cal(foo\\)bar)@iamcal.comin" );
      assertIsFalse( "first().last@iana.orgin" );
      assertIsFalse( "pete(his account)@silly.test(his host)" );
      assertIsFalse( "jdoe@machine(comment). examplein" );
      assertIsFalse( "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" );
      assertIsFalse( "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin" );
      assertIsFalse( "1234 @ local(blah) .machine .examplein" );
      assertIsFalse( "a@bin" );
      assertIsFalse( "a@barin" );
      assertIsFalse( "@about.museum" );
      assertIsFalse( "12345678901234567890123456789012345678901234567890123456789012345@iana.org" );
      assertIsFalse( ".first.last@iana.org" );
      assertIsFalse( "first.last.@iana.org" );
      assertIsFalse( "first..last@iana.org" );
      assertIsFalse( "\"first\"last\"@iana.org" );
      assertIsFalse( "first.last@" );
      assertIsFalse( "first.last@-xample.com" );
      assertIsFalse( "first.last@exampl-.com" );
      assertIsFalse( "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" );
      assertIsFalse( "abc\\@iana.org" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@iana.org" );
      assertIsFalse( "abc@def@iana.org" );
      assertIsFalse( "@iana.org" );
      assertIsFalse( "doug@" );
      assertIsFalse( "\"qu@iana.org" );
      assertIsFalse( "ote\"@iana.org" );
      assertIsFalse( ".dot@iana.org" );
      assertIsFalse( "dot.@iana.org" );
      assertIsFalse( "two..dot@iana.org" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@iana.org" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ L\\.@iana.org" );
      assertIsFalse( "hello world@iana.org" );
      assertIsFalse( "gatsby@f.sc.ot.t.f.i.tzg.era.l.d." );
      assertIsFalse( "test.iana.org" );
      assertIsFalse( "test.@iana.org" );
      assertIsFalse( "test..test@iana.org" );
      assertIsFalse( ".test@iana.org" );
      assertIsFalse( "test@test@iana.org" );
      assertIsFalse( "test@@iana.org" );
      assertIsFalse( "-- test --@iana.org" );
      assertIsFalse( "[test]@iana.org" );
      assertIsFalse( "\"test\"test\"@iana.org" );
      assertIsFalse( "()[]\\;:.><@iana.org" );
      assertIsFalse( "test@." );
      assertIsFalse( "test@example." );
      assertIsFalse( "test@.org" );
      assertIsFalse( "test@[123.123.123.123" );
      assertIsFalse( "test@123.123.123.123]" );
      assertIsFalse( "NotAnEmail" );
      assertIsFalse( "@NotAnEmail" );
      assertIsFalse( "\"test\"blah\"@iana.org" );
      assertIsFalse( ".wooly@iana.org" );
      assertIsFalse( "wo..oly@iana.org" );
      assertIsFalse( "pootietang.@iana.org" );
      assertIsFalse( ".@iana.org" );
      assertIsFalse( "Ima Fool@iana.org" );
      assertIsFalse( "foo@[\\1.2.3.4]" );
      assertIsFalse( "first.\"\".last@iana.org" );
      assertIsFalse( "first\\last@iana.org" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]" );
      assertIsFalse( "\"foo\"(yay)@(hoopla)[1.2.3.4]" );
      assertIsFalse( "cal(foo(bar)@iamcal.com" );
      assertIsFalse( "cal(foo)bar)@iamcal.com" );
      assertIsFalse( "cal(foo\\)@iamcal.com" );
      assertIsFalse( "first(middle)last@iana.org" );
      assertIsFalse( "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" );
      assertIsFalse( "a(a(b(c)d(e(f))g)(h(i)j)@iana.org" );
      assertIsFalse( ".@" );
      assertIsFalse( "@bar.com" );
      assertIsFalse( "@@bar.com" );
      assertIsFalse( "aaa.com" );
      assertIsFalse( "aaa@.com" );
      assertIsFalse( "aaa@.123" );
      assertIsFalse( "aaa@[123.123.123.123]a" );
      assertIsFalse( "aaa@[123.123.123.333]" );
      assertIsFalse( "a@bar.com." );
      assertIsFalse( "a@-b.com" );
      assertIsFalse( "a@b-.com" );
      assertIsFalse( "-@..com" );
      assertIsFalse( "-@a..com" );
      assertIsFalse( "@about.museum-" );
      assertIsFalse( "test@...........com" );
      assertIsFalse( "first.last@[IPv6::]" );
      assertIsFalse( "first.last@[IPv6::::]" );
      assertIsFalse( "first.last@[IPv6::b4]" );
      assertIsFalse( "first.last@[IPv6::::b4]" );
      assertIsFalse( "first.last@[IPv6::b3:b4]" );
      assertIsFalse( "first.last@[IPv6::::b3:b4]" );
      assertIsFalse( "first.last@[IPv6:a1:::b4]" );
      assertIsFalse( "first.last@[IPv6:a1:]" );
      assertIsFalse( "first.last@[IPv6:a1:::]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:::]" );
      assertIsFalse( "first.last@[IPv6::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6::::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1:11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1:::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]" );
      assertIsFalse( "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::11.22.33]" );
      assertIsFalse( "first.last@[IPv6:a1::11.22.33.44.55]" );
      assertIsFalse( "first.last@[IPv6:a1::b211.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::b2::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::b3:]" );
      assertIsFalse( "first.last@[IPv6::a2::b4]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]" );
      assertIsFalse( "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]" );
      assertIsFalse( "first.last@[.12.34.56.78]" );
      assertIsFalse( "first.last@[12.34.56.789]" );
      assertIsFalse( "first.last@[::12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:::12.34.56.78]" );
      assertIsFalse( "first.last@[IPv5:::12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]" );
      assertIsFalse( "first.last@[IPv6:1111:2222::3333::4444:5555:6666]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:333x::4444:5555]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:33333::4444:5555]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:::]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::5555:6666::]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]" );
      assertIsTrue( "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]" );
      assertIsTrue( "first.last@[IPv6:::]" );
      assertIsTrue( "first.last@[IPv6:::b4]" );
      assertIsTrue( "first.last@[IPv6:::b3:b4]" );
      assertIsTrue( "first.last@[IPv6:a1::b4]" );
      assertIsTrue( "first.last@[IPv6:a1::]" );
      assertIsTrue( "first.last@[IPv6:a1:a2::]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:cdef::]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:CDEF::]" );
      assertIsTrue( "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1:a2::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1::b2:11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]" );

      wlHeadline( "https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/" );

      assertIsTrue( "hello@example.com" );
      assertIsTrue( "hello@example.co.uk" ); // .co.uk, 2 tld
      assertIsTrue( "hello-2020@example.com" ); // -
      assertIsTrue( "hello.2020@example.com" ); // .
      assertIsTrue( "hello_2020@example.com" ); // _
      assertIsTrue( "h@example.com" ); // local-part one letter
      assertIsTrue( "h@example-example.com" ); // domain contains a hyphen -
      assertIsTrue( "h@example-example-example.com" ); // domain contains two hyphens - -
      assertIsTrue( "h@example.example-example.com" ); // domain contains . -
      assertIsTrue( "hello.world-2020@example.com" ); // local-part contains . -

      assertIsTrue( "hello@example_example.com" ); // domain doesn't allow underscore
      assertIsTrue( "hello!+2020@example.com" ); // local-part don't allow special characters like !+

      assertIsFalse( "hello" ); // email need at least one @
      assertIsFalse( "hello@2020@example.com" ); // email doesn't allow more than one @
      assertIsFalse( ".hello@example.com" ); // local-part can't start with a dot .
      assertIsFalse( "hello.@example.com" ); // local-part can't end with a dot .
      assertIsFalse( "hello..world@example.com" ); // local part don't allow dot . appear consecutively
      assertIsFalse( "hello@example.a" ); // domain tld min 2 chars
      assertIsFalse( "hello@example..com" ); // domain doesn't allow dot . appear consecutively
      assertIsFalse( "hello@.com" ); // domain doesn't start with a dot .
      assertIsFalse( "hello@.com." ); // domain doesn't end with a dot .
      assertIsFalse( "hello@-example.com" ); // domain doesn't allow to start with a hyphen -
      assertIsFalse( "hello@example.com-" ); // domain doesn't allow to end with a hyphen -
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234xx@example.com" ); // local part is longer than 64 characters

      assertIsTrue( "email@goes_here.com" );
      assertIsTrue( "double--dash@example.com" );

      wlHeadline( "https://github.com/dotnet/docs/issues/6620" );

      assertIsFalse( "" ); //(empty string) results in ArgumentException
      assertIsFalse( " " ); //(string that contains only white spaces) results in FormatException
      assertIsFalse( " jkt@gmail.com" ); //(white spaces at the start or at the end) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "jkt@gmail.com " ); //(white spaces at the start or at the end) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "jkt@ gmail.com" ); //(white space is directly after @) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "jkt@g mail.com" ); //(any not trailing white space after @ except the above case) resuls in FormatException
      assertIsFalse( "jkt @gmail.com" ); //(white space is in front of @) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "j kt@gmail.com" ); //(white space is before @, inside the username) results in "kt@gmail.com" (the part after a white space is taken as the mail address)

      wlHeadline( "https://www.abstractapi.com/guides/java-email-validation" );

      assertIsTrue( "\"test123\"@gmail.com" );
      assertIsTrue( "test123@gmail.comcomco" );
      assertIsTrue( "test123@gmail.c" );
      assertIsTrue( "test1&23@gmail.com" );
      assertIsTrue( "test123@gmail.com" );
      assertIsFalse( "test123@gmail..com" );
      assertIsFalse( ".test123@gmail.com" );
      assertIsFalse( "test123@gmail.com." );
      assertIsFalse( "test1Ὠ23@gmail.com" );

      wlHeadline( "https://www.javatpoint.com/java-email-validation" );

      assertIsTrue( "javaTpoint@domain.co.in" );
      assertIsTrue( "javaTpoint@domain.com" );
      assertIsTrue( "javaTpoint.name@domain.com" );
      assertIsTrue( "javaTpoint#@domain.co.in" );
      assertIsTrue( "javaTpoint@domain.com" );
      assertIsFalse( "javaTpoint@domaincom" );
      assertIsTrue( "javaTpoint@domain.co.in" );
      assertIsTrue( "12453@domain.com" );
      assertIsTrue( "javaTpoint.name@domain.com" );
      assertIsTrue( "1avaTpoint#@domain.co.in" );

      assertIsTrue( "javaTpoint@domain.co.in" );
      assertIsTrue( "javaTpoint@domain.com" );
      assertIsTrue( "javaTpoint.name@domain.com" );
      assertIsTrue( "javaTpoint#@domain.co.in" );
      assertIsTrue( "java'Tpoint@domain.com" );

      assertIsFalse( ".javaTpoint@yahoo.com" );
      assertIsFalse( "javaTpoint@domain.com." );
      assertIsFalse( "javaTpoint#domain.com" );
      assertIsFalse( "javaTpoint@domain..com" );

      assertIsFalse( "@yahoo.com" );
      assertIsFalse( "javaTpoint#domain.com" );
      assertIsFalse( "12javaTpoint#domain.com" );

      wlHeadline( "https://java2blog.com/validate-email-address-in-java/" );

      assertIsTrue( "admin@java2blog.com" );
      assertIsFalse( "@java2blog.com" );
      assertIsTrue( "arpit.mandliya@java2blog.com" );

      wlHeadline( "https://www.tutorialspoint.com/javaexamples/regular_email.htm" );

      assertIsTrue( "sairamkrishna@tutorialspoint.com" );
      assertIsTrue( "kittuprasad700@gmail.com" );
      assertIsFalse( "sairamkrishna_mammahe%google-india.com" );
      assertIsTrue( "sairam.krishna@gmail-indai.com" );
      assertIsTrue( "sai#@youtube.co.in" );
      assertIsFalse( "kittu@domaincom" );
      assertIsFalse( "kittu#gmail.com" );
      assertIsFalse( "@pindom.com" );

      wlHeadline( "https://www.rohannagar.com/jmail/" );

      assertIsFalse( "\"qu@test.org" ); // Opening quote must have a closing quote
      assertIsFalse( "ote\"@test.org" ); // Closing quote must have an opening quote
      assertIsFalse( "\"().:;<>[\\]@example.com" ); // Opening quote must have a closing quote
      assertIsFalse( "\"\"\"@iana.org" ); // Each quote must be in a pair
      assertIsFalse( "Abc.example.com" ); // The @ character must be present
      assertIsFalse( "A@b@c@example.com" ); // There can only be a single @ character
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "this is\"not\\allowed@example.com" ); // Whitespace should be quoted or dot-separated
      assertIsFalse( "this\\ still\"not\\allowed@example.com" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );
      assertIsFalse( "QA[icon]CHOCOLATE[icon]@test.com" ); // Unquoted [ and ] characters are not allowed
      assertIsFalse( "QA\\[icon\\]CHOCOLATE\\[icon\\]@test.com" ); // Unquoted [ and ] characters are not allowed
      assertIsFalse( "plainaddress" ); // The @ character must be present
      assertIsFalse( "@example.com" );
      assertIsFalse( ".email@example.com" );
      assertIsFalse( "email.@example.com" );
      assertIsFalse( "email..email@example.com" );
      assertIsFalse( "email@-example.com" );
      assertIsFalse( "email@111.222.333.44444" );
      assertIsFalse( "this\\ is\"really\"not\\allowed@example.com" );
      assertIsFalse( "email@[12.34.44.56" );
      assertIsFalse( "email@14.44.56.34]" );
      assertIsFalse( "email@[1.1.23.5f]" );
      assertIsFalse( "email@[3.256.255.23]" );
      assertIsTrue( "\"first\\\"last\"@test.org" );
      assertIsFalse( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" );
      assertIsTrue( "first\\@last@iana.org" );
      assertIsFalse( "test@example.com " );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]" );
      assertIsFalse( "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]" );
      assertIsFalse( "invalid@about.museum-" );
      assertIsFalse( "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" );
      assertIsFalse( "abc@def@test.org" );
      assertIsTrue( "abc\\@def@test.org" );
      assertIsFalse( "abc\\@test.org" );
      assertIsFalse( "@test.org" );
      assertIsFalse( ".dot@test.org" );
      assertIsFalse( "dot.@test.org" );
      assertIsFalse( "two..dot@test.org" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@test.org" );
      assertIsFalse( "Doug\\ \"Ace\"\\ L\\.@test.org" );
      assertIsFalse( "hello world@test.org" );
      assertIsFalse( "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" );
      assertIsFalse( "a(a(b(c)d(e(f))g)(h(i)j)@test.org" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@test.org" );
      assertIsFalse( "test.test.org" );
      assertIsFalse( "test.@test.org" );
      assertIsFalse( "test..test@test.org" );
      assertIsFalse( ".test@test.org" );
      assertIsFalse( "test@test@test.org" );
      assertIsFalse( "test@@test.org" );
      assertIsFalse( "-- test --@test.org" );
      assertIsFalse( "[test]@test.org" );
      assertIsFalse( "\"test\"test\"@test.org" );
      assertIsFalse( "()[]\\;:.><@test.org" );
      assertIsFalse( "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" );
      assertIsFalse( ".@test.org" );
      assertIsFalse( "Ima Fool@test.org" );
      assertIsTrue( "\"first\\\"last\"@test.org" );
      assertIsFalse( "foo@[\1.2.3.4]" );
      assertIsFalse( "first\\last@test.org" );
      assertIsFalse( "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" );
      assertIsFalse( "first(middle)last@test.org" );
      assertIsFalse( "\"test\"test@test.com" );
      assertIsFalse( "()@test.com" );
      assertIsFalse( "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" );
      assertIsFalse( "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" );
      assertIsFalse( "invalid@[1]" );
      assertIsFalse( "ä📧@-foo" );
      assertIsFalse( "ä📧@foo-" );
      assertIsFalse( "first(comment(inner@comment.com" );
      assertIsFalse( "Joe A Smith <email@example.com" );
      assertIsFalse( "Joe A Smith email@example.com" );
      assertIsFalse( "Joe A Smith <email@example.com->" );
      assertIsFalse( "Joe A Smith <email@-example.com->" );
      assertIsFalse( "Joe A Smith <email>" );
      assertIsTrue( "\"email\"@example.com" );
      assertIsTrue( "\"first@last\"@test.org" );
      assertIsTrue( "very.unusual.\"@\".unusual.com@example.com" );
      assertIsFalse( "\"first\"last\"@test.org" );
      assertIsTrue( "much.\"more\\ unusual\"@example.com" );
      assertIsFalse( "\"first\\last\"@test.org" );
      assertIsTrue( "\"Abc\\@def\"@test.org" );
      assertIsTrue( "\"Fred\\ Bloggs\"@test.org" );
      assertIsFalse( "\"Joe.\\Blow\"@test.org" );
      assertIsTrue( "\"Abc@def\"@test.org" );
      assertIsTrue( "\"Fred Bloggs\"@test.org" );
      assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@test.org" );
      assertIsTrue( "\"[[ test ]]\"@test.org" );
      assertIsTrue( "\"test.test\"@test.org" );
      assertIsTrue( "test.\"test\"@test.org" );
      assertIsTrue( "\"test@test\"@test.org" );
      assertIsFalse( "\"test tabulator \test\"@test.org" );
      assertIsTrue( "\"first\".\"last\"@test.org" );
      assertIsTrue( "\"first\".middle.\"last\"@test.org" );
      assertIsTrue( "\"first\".last@test.org" );
      assertIsTrue( "first.\"last\"@test.org" );
      assertIsTrue( "\"first\".\"middle\".\"last\"@test.org" );
      assertIsTrue( "\"first.middle\".\"last\"@test.org" );
      assertIsTrue( "\"first.middle.last\"@test.org" );
      assertIsTrue( "\"first..last\"@test.org" );
      assertIsTrue( "\"Unicode NULL \"@char.com" );
      assertIsFalse( "\"test\\blah\"@test.org" );
      assertIsFalse( "\"test\blah\"@test.org" );
      assertIsTrue( "\"test\\\"blah\"@test.org" );
      assertIsTrue( "\"first\\\"last\"@test.org" );
      assertIsTrue( "\"Test \\\"Fail\\\" Ing\"@test.org" );
      assertIsFalse( "\"Test \"Fail\" Ing\"@test.org" );
      assertIsTrue( "\"test blah\"@test.org" );
      assertIsTrue( "first.last@test.org" );
      assertIsFalse( "jdoe@machine(comment).example" );
      assertIsFalse( "first.\"\".last@test.org" );
      assertIsFalse( "\"\"@test.org" );
      assertIsTrue( "very.common@example.org" );
      assertIsTrue( "test/test@test.com" );
      assertIsTrue( "user-@example.org" );
      assertIsTrue( "firstname.lastname@example.com" );
      assertIsTrue( "email@subdomain.example.com" );
      assertIsTrue( "firstname+lastname@example.com" );
      assertIsTrue( "1234567890@example.com" );
      assertIsTrue( "email@example-one.com" );
      assertIsTrue( "_______@example.com" );
      assertIsTrue( "email@example.name" );
      assertIsTrue( "email@example.museum" );
      assertIsTrue( "email@example.co.jp" );
      assertIsTrue( "firstname-lastname@example.com" );
      assertIsTrue( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" );
      assertIsTrue( "first.last@123.test.org" );
      assertIsTrue( "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678901234@test.org" );
      assertIsTrue( "user+mailbox@test.org" );
      assertIsTrue( "customer/department=shipping@test.org" );
      assertIsTrue( "$A12345@test.org" );
      assertIsTrue( "!def!xyz%abc@test.org" );
      assertIsTrue( "_somename@test.org" );
      assertIsTrue( "first.last@[IPv6:::12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]" );
      assertIsTrue( "+@b.c" );
      assertIsTrue( "TEST@test.org" );
      assertIsTrue( "1234567890@test.org" );
      assertIsTrue( "test-test@test.org" );
      assertIsTrue( "t*est@test.org" );
      assertIsTrue( "+1~1+@test.org" );
      assertIsTrue( "{_test_}@test.org" );
      assertIsTrue( "valid@about.museum" );
      assertIsTrue( "a@bar" );
      assertIsFalse( "cal(foo\\@bar)@iamcal.com" );
      assertIsTrue( "(comment)test@test.org" );
      assertIsFalse( "(foo)cal(bar)@(baz)iamcal.com(quux)" );
      assertIsFalse( "cal(foo\\)bar)@iamcal.com" );
      assertIsFalse( "cal(woo(yay)hoopla)@iamcal.com" );
      assertIsFalse( "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" );
      assertIsFalse( "pete(his account)@silly.test(his host)" );
      assertIsFalse( "first(abc\\(def)@test.org" );
      assertIsFalse( "a(a(b(c)d(e(f))g)h(i)j)@test.org" );
      assertIsTrue( "c@(Chris's host.)public.example" );
      assertIsTrue( "_Yosemite.Sam@test.org" );
      assertIsTrue( "~@test.org" );
      assertIsTrue( "Iinsist@(that comments are allowed)this.is.ok" );
      assertIsTrue( "test@Bücher.ch" );
      assertIsTrue( "あいうえお@example.com" );
      assertIsTrue( "Pelé@example.com" );
      assertIsTrue( "δοκιμή@παράδειγμα.δοκιμή" );
      assertIsTrue( "我買@屋企.香港" );
      assertIsTrue( "二ノ宮@黒川.日本" );
      assertIsTrue( "медведь@с-балалайкой.рф" );
      assertIsTrue( "संपर्क@डाटामेल.भारत" );
      assertIsTrue( "email@example.com (Joe Smith)" );
      assertIsFalse( "cal@iamcal(woo).(yay)com" );
      assertIsFalse( "first(abc.def).last@test.org" );
      assertIsFalse( "first(a\"bc.def).last@test.org" );
      assertIsFalse( "first.(\")middle.last(\")@test.org" );
      assertIsFalse( "first().last@test.org" );
      assertIsTrue( "mymail\\@hello@hotmail.com" );
      assertIsTrue( "Abc\\@def@test.org" );
      assertIsTrue( "Fred\\ Bloggs@test.org" );
      assertIsTrue( "Joe.\\\\Blow@test.org" );

      wlHeadline( "https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java" );

      assertIsTrue( "me@example.com" );
      assertIsTrue( "a.nonymous@example.com" );
      assertIsTrue( "name+tag@example.com" );
      assertIsTrue( "!#$%&'+-/=.?^`{|}~@[1.0.0.127]" );
      assertIsTrue( "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]" );
      assertIsTrue( "me(this is a comment)@example.com" );
      assertIsTrue( "\"bob(hi)smith\"@test.com" );
      assertIsTrue( "me.example@com" );
      assertIsTrue( "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com" );
      assertIsTrue( "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net" );

      /*
       * https://github.com/bbottema/simple-java-mail/issues/16
       */
      assertIsTrue( "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );
      assertIsTrue( "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );
      assertIsTrue( "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );
      assertIsTrue( "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );

      assertIsFalse( "NotAnEmail" );
      assertIsFalse( "me@" );
      assertIsFalse( "@example.com" );
      assertIsFalse( ".me@example.com" );
      assertIsFalse( "me@example..com" );
      assertIsFalse( "me\\@example.com" );
      assertIsFalse( "\"ßoµ\" <notifications@example.com>" );
      assertIsFalse( "[Kayaks] <kayaks@kayaks.org>" );
      assertIsFalse( "Kayaks.org <kayaks@kayaks.org>" );
      assertIsFalse( "semico...@gmail.com" );

      wlHeadline( "my old tests" );

      assertIsTrue( "A@B.CD" );
      assertIsTrue( "A.\"B\"@C.DE" );
      assertIsTrue( "A.B@[1.2.3.4]" );
      assertIsTrue( "A.\"B\"@[1.2.3.4]" );
      assertIsTrue( "A.B@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "(A)B@C.DE" );
      assertIsTrue( "A(B)@C.DE" );
      assertIsTrue( "(A)\"B\"@C.DE" );
      assertIsTrue( "\"A\"(B)@C.DE" );
      assertIsTrue( "(A)B@[1.2.3.4]" );
      assertIsTrue( "A(B)@[1.2.3.4]" );
      assertIsTrue( "(A)\"B\"@[1.2.3.4]" );
      assertIsTrue( "\"A\"(B)@[1.2.3.4]" );
      assertIsTrue( "(A)B@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "A(B)@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "a.b.c.d@domain.com" );

      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "ABC.DEF.GHI.JKL" );
      assertIsFalse( "ABC.DEF@ GHI.JKL" );
      assertIsFalse( "ABC.DEF @GHI.JKL" );
      assertIsFalse( "ABC.DEF @ GHI.JKL" );
      assertIsFalse( "ABC.DEF@.@.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );
      assertIsFalse( "ABC@DEF@GHI.JKL" );
      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );
      assertIsFalse( "first@last@test.org" );
      assertIsFalse( "@test@a.com" );
      assertIsFalse( "@\"someStringThatMightBe@email.com" );
      assertIsFalse( "test@@test.com" );

      assertIsFalse( "ABCDEF@GHIJKL" );
      assertIsFalse( "ABC.DEF@GHIJKL" );
      assertIsFalse( ".ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL." );
      assertIsFalse( "ABC..DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI..JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL.." );
      assertIsFalse( "ABC.DEF.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@.GHI.JKL" );
      assertIsFalse( "ABC.DEF@." );
      assertIsTrue( "\"ABC..DEF\"@GHI.JKL" );

      assertIsTrue( "ABC1.DEF2@GHI3.JKL4" );
      assertIsTrue( "ABC.DEF_@GHI.JKL" );
      assertIsTrue( "#ABC.DEF@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.JK2" );
      assertIsTrue( "ABC.DEF@2HI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.2KL" );
      assertIsFalse( "ABC.DEF@GHI.JK-" );
      assertIsFalse( "ABC.DEF@GHI.JK_" );
      assertIsFalse( "ABC.DEF@-HI.JKL" );
      assertIsFalse( "ABC.DEF@_HI.JKL" );
      assertIsFalse( "ABC DEF@GHI.DE" );
      assertIsFalse( "ABC.DEF@GHI DE" );
      assertIsFalse( "A . B & C . D" );
      assertIsFalse( " A . B & C . D" );
      assertIsFalse( "(?).[!]@{&}.<:>" );

      assertIsFalse( "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " );
      assertIsTrue( "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" );

      wlHeadline( "unsupported" );

      assertIsTrue( "Loïc.Accentué@voilà.fr" );
      assertIsTrue( "Ärger.Öde@Übel.de" );
      assertIsTrue( "Smørrebrød@danmark.dk" );

      assertIsTrue( "ip6.without.brackets@1:2:3:4:5:6:7:8" );

      assertIsTrue( "email.address.without@topleveldomain" );

      assertIsTrue( "EmailAddressWithout@PointSeperator" );

      /*
       * https://github.com/RohanNagar/jmail/issues/6
       */
      assertIsFalse( "@1st.relay,@2nd.relay:user@final.domain" );

      wlHeadline( "Fillup" );

      if ( KNZ_FILLUP_AKTIV )
      {
        while ( COUNT_ASSERT_IS_TRUE < COUNT_ASSERT_IS_FALSE )
        {
          assertIsTrue( "valid.email.from.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.tofalse.com" );

          KNZ_LOG_AUSGABE = false;

          if ( COUNT_ASSERT_IS_TRUE + 1 == COUNT_ASSERT_IS_FALSE )
          {
            wl( "          ..." );

            KNZ_LOG_AUSGABE = true;

            assertIsTrue( "valid.email.to.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.tofalse.com" );
          }
        }

        KNZ_LOG_AUSGABE = true;

        while ( COUNT_ASSERT_IS_FALSE < COUNT_ASSERT_IS_TRUE )
        {
          assertIsFalse( "false.email.from.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.totrue.[()]" );

          KNZ_LOG_AUSGABE = false;

          if ( COUNT_ASSERT_IS_FALSE + 1 == COUNT_ASSERT_IS_TRUE )
          {
            wl( "          ..." );

            KNZ_LOG_AUSGABE = true;

            assertIsFalse( "false.email.to.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.totrue.[()]" );
          }
        }
      }
      else
      {
        wl( "Fillup ist nicht aktiv" );
      }

      KNZ_LOG_AUSGABE = true;
    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    DecimalFormatSymbols m_other_symbols = null;

    DecimalFormat m_number_format = null;

    m_other_symbols = new DecimalFormatSymbols( Locale.getDefault() );

    m_other_symbols.setDecimalSeparator( '.' );
    m_other_symbols.setGroupingSeparator( '.' );

    m_number_format = new DecimalFormat( "###.##", m_other_symbols );

    m_number_format.setMaximumFractionDigits( 3 );
    m_number_format.setMinimumFractionDigits( 3 );

    double email_ok_proz_true_korrekt_erkannt = ( 100.0 * TRUE_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_TRUE;

    double email_ok_proz_false_korrekt_erkannt = ( 100.0 * FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_FALSE;

    double email_ok_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( TRUE_RESULT_COUNT_EMAIL_IS_TRUE + FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    double email_false_proz_true_korrekt_erkannt = ( 100.0 * TRUE_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_TRUE;

    double email_false_proz_false_korrekt_erkannt = ( 100.0 * FALSE_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_FALSE;

    double email_false_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( TRUE_RESULT_COUNT_EMAIL_IS_FALSE + FALSE_RESULT_COUNT_EMAIL_IS_TRUE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    wl( "" );

    wlHeadline( "Statistik" );

    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( TRUE_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_ok_proz_true_korrekt_erkannt ) + m_number_format.format( email_ok_proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( TRUE_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_true_korrekt_erkannt ) + m_number_format.format( email_false_proz_true_korrekt_erkannt ) + " % = Error " + TRUE_RESULT_COUNT_ERROR );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_ok_proz_false_korrekt_erkannt ) + m_number_format.format( email_ok_proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( FALSE_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_false_proz_false_korrekt_erkannt ) + m_number_format.format( email_false_proz_false_korrekt_erkannt ) + " % = Error " + FALSE_RESULT_COUNT_ERROR );
    wl( "" );
    wl( "  GESAMT          " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( TRUE_RESULT_COUNT_EMAIL_IS_TRUE + FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( email_ok_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_ok_proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( FALSE_RESULT_COUNT_EMAIL_IS_TRUE + TRUE_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_false_proz_korrekt_erkannt_insgesamt ) + " % = Error " + ( TRUE_RESULT_COUNT_ERROR + FALSE_RESULT_COUNT_ERROR ) );
    wl( "" );

    /*
     * Variable fuer die Zeitdifferenz deklarieren
     */
    long zeit_differenz_millisekunden = 0;

    /*
     * Variable fuer die Endzeit der Funktion deklarieren und die aktuellen
     * System-Millisekunden speichern
     */
    long time_stamp_ende = System.currentTimeMillis();

    /*
     * Funktionslaufzeit in Millisekunden berechnen
     */
    zeit_differenz_millisekunden = time_stamp_ende - time_stamp_start;

    /*
     * Informationen ueber die Laufzeit in das Log schreiben
     */
    wl( "" );
    wl( "  Millisekunden " + FkString.right( "          " + zeit_differenz_millisekunden, 6 ) + " = " + ( (double) zeit_differenz_millisekunden / (double) ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) ) );
    wl( "" );

    if ( m_str_buffer != null )
    {
      String home_dir = "/home/ea234";

      //home_dir = "c:/Daten/";

      schreibeDatei( home_dir + "/log_test_email_assert_true_false.txt", m_str_buffer.toString() );
    }

    m_str_buffer = null;

    System.exit( 0 );
  }

  private static String getStringZahl( int pZahl )
  {
    return ( pZahl < 10 ? "   " : ( pZahl < 100 ? "  " : ( pZahl < 1000 ? " " : "" ) ) ) + pZahl;
  }

  private static String getEinzug( double pZahl )
  {
    return ( pZahl < 10.0 ? "   " : ( pZahl < 100.0 ? "  " : ( pZahl < 1000.0 ? " " : "" ) ) );
  }

  private static void wlHeadline( String pString )
  {
    wl( "" );
    wl( left( "---- " + pString + " ------------------------------------------------------------------------------------------------------------------------------", 130 ) );
    wl( "" );
  }

  /**
   * <pre>
   * Schneidet Anzahl-Stellen von dem uebergebenen String ab und gibt diesen zurueck.
   * 
   * Ist der Parameter "pString" gleich \"null\", wird ein Leerstring zurueckgegeben.
   * 
   * Uebersteigt die Anazhl der abzuschneidenden Stellen die Stringlaenge, wird der
   * Quellstring insgesamt zurueckgegeben.
   * 
   * Ist die Anzahl der abzuschneidenden Stellen negativ oder 0, wird ein Leerstring zurueckgegeben.
   * 
   * FkString.left( "ABC.DEF.GHI.JKL",  3 ) = "ABC"
   * FkString.left( "ABC.DEF.GHI.JKL",  4 ) = "ABC."
   * FkString.left( "ABC.DEF.GHI.JKL", 20 ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.left( "ABC.DEF.GHI.JKL", -3 ) = "" = negative Anzahl von Stellen = Leerstring
   * FkString.left(                "", 10 ) = "" = pString ist Leerstring      = Leerstring
   * FkString.left(              null, 10 ) = "" = pString ist null            = Leerstring
   * </pre>
   * 
   * @param pString der Quellstring
   * @param pAnzahlStellen die Anzahl der von links abzuschneidenden Stellen
   * @return den sich ergebenden String, Leerstring wenn die Anzahl der Stellen negativ ist oder pString null ist
   */
  private static String left( String pString, int pAnzahlStellen )
  {
    /*
     * Pruefung: "pString" gleich "null" ?
     * 
     * Ist der Parameter "pString" gleich "null" gibt es keinen String. 
     * Der Aufrufder bekommt einen Leerstring zurueck
     */
    if ( pString == null )
    {
      return "";
    }

    /*
     * Pruefung: Anzahl der Stellen negativ?
     * Ist die Anzahl der abzuschneidenden Stellen negativ, bleibt 
     * kein Teil von pString uebrig. Dieser Fall wird analog einer 
     * Uebergabe von 0 Zeichen abschneiden behandelt.  
     * 
     * Der Aufrufer bekommt einen Leerstring zurueck.
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    /*
     * Pruefung: Teilstring zurueckgeben?
     * Ist die Anzahl der Stellen kleiner als die Laenge von "pString", 
     * wird ein Teilstring zurueckgegeben.
     *
     * Der Aufrufer bekommt den Teilstring ab der Position 0 bis zur
     * Anzahl der abzuschneidenden Stellen zuruek. 
     */
    if ( pAnzahlStellen < pString.length() )
    {
      return pString.substring( 0, pAnzahlStellen );
    }

    /*
     * Ueberschreitet die Anzahl der abzuschneidenden Stellen die 
     * Laenge des Eingabestrings, muss kein Zeichen vom Eingabestring
     * abgeschnitten werden. 
     * 
     * Der Aufrufer bekommt die Eingabe zuruek.
     */
    return pString;
  }

  /**
   * Ausgabe auf System.out
   * 
   * @param pString der auszugebende String
   */
  private static void wl( String pString )
  {
    if ( m_str_buffer == null )
    {
      m_str_buffer = new StringBuffer();
    }

    m_str_buffer.append( "\n" + "     * " + pString );

    System.out.println( pString );
  }

  private static String getID()
  {
    LAUFENDE_ZAHL++;

    if ( LAUFENDE_ZAHL == Integer.MAX_VALUE )
    {
      LAUFENDE_ZAHL = 0;
    }

    return ( LAUFENDE_ZAHL < 10 ? "    " : ( LAUFENDE_ZAHL < 100 ? "   " : ( LAUFENDE_ZAHL < 1000 ? "  " : ( LAUFENDE_ZAHL < 10000 ? " " : "" ) ) ) ) + LAUFENDE_ZAHL;
  }

  private static void assertIsTrue( String pString )
  {
    boolean knz_is_valid_email_adress = false;

    int return_code = 0;

    try
    {
      if ( TEST_B_KNZ_AKTIV )
      {
        knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

        //knz_is_valid_email_adress = EmailAddressValidator.isValid( pString, RFC_COMPLIANT );

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + FkString.getFeldLinksMin( "" + knz_is_valid_email_adress, 7 ) + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER #### " ) );
        }
      }
      else
      {
        return_code = FkEMail.checkEMailAdresse( pString );

        knz_is_valid_email_adress = return_code < 10;

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
        }
      }

      if ( knz_is_valid_email_adress )
      {
        TRUE_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        TRUE_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      TRUE_RESULT_COUNT_ERROR++;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsTrue " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = Fehler = " + err_inst.getMessage() );
      }
    }

    COUNT_ASSERT_IS_TRUE++;
  }

  private static void assertIsFalse( String pString )
  {
    boolean knz_is_valid_email_adress = false;

    boolean knz_soll_wert = false;

    int return_code = 0;

    try
    {
      if ( TEST_B_KNZ_AKTIV )
      {
        //knz_is_valid_email_adress = JMail.isValid( pString );

        //knz_is_valid_email_adress = EmailAddressValidator.isValid( pString, RFC_COMPLIANT );

        knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + FkString.getFeldLinksMin( "" + knz_is_valid_email_adress, 7 ) + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) );
        }
      }
      else
      {
        return_code = FkEMail.checkEMailAdresse( pString );

        knz_is_valid_email_adress = return_code < 10;

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
        }
      }

      if ( knz_is_valid_email_adress )
      {
        FALSE_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        FALSE_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      FALSE_RESULT_COUNT_ERROR++;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = Fehler = " + err_inst.getMessage() );
      }
    }

    COUNT_ASSERT_IS_FALSE++;
  }

  private static void generateTestCases()
  {
    //generateTest( ".", "dot" );
    //generateTest( "..", "double.dot" );
    //generateTest( "&", "amp" );
    //generateTest( "*", "asterisk" );
    //generateTest( "_", "underscore" );
    //generateTest( "$", "dollar" );
    //generateTest( "=", "equality" );
    //generateTest( "!", "exclamation" );
    //generateTest( "?", "question" );
    //generateTest( "`", "grave-accent" );
    //generateTest( "#", "hash" );
    //generateTest( "%", "percentage" );
    //generateTest( "|", "pipe" );
    //generateTest( "+", "plus" );
    //generateTest( "{", "leftbracket" );
    //generateTest( "}", "rightbracket" );
    //generateTest( "(", "leftbracket" );
    //generateTest( ")", "rightbracket" );
    //generateTest( "[", "leftbracket" );
    //generateTest( "]", "rightbracket" );
    //generateTest( "()", "empty.bracket" );
    //generateTest( "{}", "empty.bracket" );
    //generateTest( "[]", "empty.bracket" );
    //generateTest( "<>", "empty.bracket" );
    //generateTest( ")(", "false.bracket1" );
    //generateTest( "}{", "false.bracket2" );
    //generateTest( "][", "false.bracket3" );--------------------------
    //generateTest( "><", "false.bracket4" );
    //generateTest( "<", "lower.than" );
    //generateTest( ">", "greater.than" );
    //generateTest( "~", "tilde" );
    //generateTest( "^", "xor" );
    //generateTest( ":", "colon" );
    //generateTest( " ", "space" );
    //generateTest( ",", "comma" );
    //generateTest( "@", "at" );
    //generateTest( "§", "paragraph" );
    //generateTest( "'", "double.quote" );
    //xxgenerateTest( "\"", "double.quote" );
    //generateTest( "/", "forward.slash" );
    //generateTest( "-", "hyphen" );
    //generateTest( "\\\"\\\"", "empty.string1" );
    //generateTest( "a\\\"\\\"b", "empty.string2" );
    //generateTest( "\\\"\\\"\\\"\\\"", "double.empty.string1" );
    //generateTest( "\\\"\\\".\\\"\\\"", "double.empty.string2" );
    //generateTest( "0", "number0" );
    //generateTest( "9", "number9" );
    //generateTest( "0123456789", "numbers" );
    //generateTest( "999", "byte.overflow" );
    //generateTest( "xyz", "no.hex.number" );
    //generateTest( "\\\\", "slash" );
    //generateTest( "\\\"str\\\"", "string" );
    //generateTest( "(comment)", "comment" );
  }

  private static void generateTest( String pCharacter, String pName )
  {
//    wl( "" );
//    wl( "      assertIsTrue( \"" + pCharacter + ".local.part.starts.with." + pName + "@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.part.ends.with." + pName + pCharacter + "@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.part.with." + pName + pCharacter + "character@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.part.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.part.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.part.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
//    wl( "      assertIsFalse( \"(comment " + pCharacter + ") local.part.with." + pName + ".in.comment@domain.com\" );" );
//    wl( "      assertIsTrue( \"\\\"string" + pCharacter + "\\\".local.part.with." + pName + ".in.String@domain.com\" );" );
//    wl( "      assertIsFalse( \"\\\"string\\\\" + pCharacter + "\\\".local.part.with.escaped." + pName + ".in.String@domain.com\" );" );
//    wl( "      assertIsTrue( \"" + pCharacter + "@local.part.only." + pName + ".domain.com\" );" );
//    wl( "      assertIsTrue( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@local.part.only.consecutive." + pName + ".domain.com\" );" );
//    wl( "      assertIsTrue( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
//    wl( "      assertIsFalse( \"name " + pCharacter + " <pointy.brackets1.with." + pName + ".in.display.name@domain.com>\" );" );
//    wl( "      assertIsFalse( \"<pointy.brackets2.with." + pName + ".in.display.name@domain.com> name " + pCharacter + "\" );" );
//    wl( "" );
//    wl( "      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
//    wl( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with.consecutive." + pName + "" + pCharacter + "" + pCharacter + "test.com\" );" );
//    wl( "      assertIsTrue( \"domain.part.with." + pName + ".in.comment@(comment " + pCharacter + ")domain.com\" );" );
//
//    wl( "      assertIsFalse( \"domain.part.only." + pName + "@" + pCharacter + ".com\" );" );
//
//    wl( "      assertIsFalse( \"top.level.domain.only@" + pName + "." + pCharacter + "\" );" );
//
//    wl( "" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".between.numbers@[123.14" + pCharacter + "5.178.90]\" );" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".before.point@[123.145" + pCharacter + ".178.90]\" );" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".after.point@[123.145." + pCharacter + "178.90]\" );" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".before.start.bracket@" + pCharacter + "[123.145.178.90]\" );" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".after.start.bracket@[" + pCharacter + "123.145.178.90]\" );" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".before.end.bracket@[123.145.178.90" + pCharacter + "]\" );" );
//    wl( "      assertIsFalse( \"ip4.with." + pName + ".after.end.bracket@[123.145.178.90]" + pCharacter + "\" );" );
    wl( "" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:2" + pCharacter + "2:3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22" + pCharacter + ":3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:" + pCharacter + "3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7" + pCharacter + "]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7]" + pCharacter + "\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@" + pCharacter + "[IPv6:1:22:3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[" + pCharacter + "IPv6:1:22:3:4:5:6:7]\" );" );

  }

  /**
   * <pre>
   * Erstellt die Datei und schreibt dort den "pInhalt" rein.
   * 
   * Ist kein "pInhalt" null wird eine leere Datei erstellt.
   * </pre>
   * 
   * @param pDateiName der Dateiname
   * @param pInhalt    der zu schreibende Inhalt
   * @return TRUE wenn die Datei geschrieben werden konnte, sonst False
   */
  private static boolean schreibeDatei( String pDateiName, String pInhalt )
  {
    try
    {
      FileWriter output_stream = new FileWriter( pDateiName, false );

      if ( pInhalt != null )
      {
        output_stream.write( pInhalt );
      }

      output_stream.flush();

      output_stream.close();

      output_stream = null;

      return true;
    }
    catch ( Exception err_inst )
    {
      System.out.println( "Fehler: errSchreibeDatei " + err_inst.getMessage() );
    }

    return false;
  }
}
