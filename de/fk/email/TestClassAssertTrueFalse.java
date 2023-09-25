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
     *    31 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                       =   2 =  OK 
     *    32 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                       =   2 =  OK 
     *    33 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                       =   2 =  OK 
     *    34 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                       =   2 =  OK 
     *    35 - assertIsTrue  "(comment1)\"string1\".name1@domain1.tld"                                    =   7 =  OK 
     *    36 - assertIsTrue  "(comment1)name1.\"string1\"@domain1.tld"                                    =   7 =  OK 
     *    37 - assertIsTrue  "name1.\"string1\"(comment1)@domain1.tld"                                    =   7 =  OK 
     *    38 - assertIsTrue  "\"string1\".name1(comment1)@domain1.tld"                                    =   7 =  OK 
     *    39 - assertIsTrue  "name1.\"string1\"@(comment1)domain1.tld"                                    =   7 =  OK 
     *    40 - assertIsTrue  "\"string1\".name1@domain1.tld(comment1)"                                    =   7 =  OK 
     *    41 - assertIsTrue  "<name1.name2@domain1.tld>"                                                  =   0 =  OK 
     *    42 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                            =   0 =  OK 
     *    43 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                            =   0 =  OK 
     *    44 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                                  =   0 =  OK 
     *    45 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                               =   2 =  OK 
     *    46 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                                  =   4 =  OK 
     *    47 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                               =   2 =  OK 
     *    48 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                                 =   4 =  OK 
     *    49 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"        =   6 =  OK 
     *    50 - assertIsTrue  "\"display name\" <local.part@(comment)domain-name.top_level_domain>"        =   6 =  OK 
     *    51 - assertIsTrue  "\"display name\" <(comment) local.part.\"string1\"@domain-name.top_level_domain>" =   7 =  OK 
     *    52 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part@domain-name.top_level_domain>" =   6 =  OK 
     *    53 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part.wiht.escaped.at\@.sign@domain-name.top_level_domain>" =   6 =  OK 
     * 
     * ---- No Input --------------------------------------------------------------------------------------------------------------------
     * 
     *    54 - assertIsFalse null                                                                         =  10 =  OK    Laenge: Eingabe ist null
     *    55 - assertIsFalse ""                                                                           =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    56 - assertIsFalse "        "                                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Sign ---------------------------------------------------------------------------------------------------------------------
     * 
     *    57 - assertIsFalse "1234567890"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    58 - assertIsFalse "OnlyTextNoDotNoAt"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    59 - assertIsFalse "email.with.no.at.sign"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    60 - assertIsFalse "email.with.no.domain@"                                                      =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    61 - assertIsFalse "@@domain.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    62 - assertIsFalse "email.with.no.domain\@domain.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    63 - assertIsFalse "email.with.no.domain\@.domain.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    64 - assertIsFalse "email.with.no.domain\@123domain.com"                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    65 - assertIsFalse "email.with.no.domain\@_domain.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    66 - assertIsFalse "email.with.no.domain\@-domain.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    67 - assertIsFalse "email.with.double\@no.domain\@domain.com"                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    68 - assertIsTrue  "\"wrong.at.sign.combination.in.string1@.\"@domain.com"                      =   1 =  OK 
     *    69 - assertIsTrue  "\"wrong.at.sign.combination.in.string2.@\"@domain.com"                      =   1 =  OK 
     *    70 - assertIsTrue  "email.with.escaped.at\@.sign.version1@domain.com"                           =   0 =  OK 
     *    71 - assertIsTrue  "email.with.escaped.\@.sign.version2@domain.com"                             =   0 =  OK 
     *    72 - assertIsTrue  "email.with.escaped.at\@123.sign.version3@domain.com"                        =   0 =  OK 
     *    73 - assertIsTrue  "email.with.escaped.\@123.sign.version4@domain.com"                          =   0 =  OK 
     *    74 - assertIsTrue  "email.with.escaped.at\@-.sign.version5@domain.com"                          =   0 =  OK 
     *    75 - assertIsTrue  "email.with.escaped.\@-.sign.version6@domain.com"                            =   0 =  OK 
     *    76 - assertIsTrue  "email.with.escaped.at.sign.\@@domain.com"                                   =   0 =  OK 
     *    77 - assertIsFalse "@@email.with.unescaped.at.sign.as.local.part"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    78 - assertIsTrue  "\@@email.with.escaped.at.sign.as.local.part"                                =   0 =  OK 
     *    79 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    80 - assertIsFalse "@no.local.part.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    81 - assertIsFalse "@@@@@@only.multiple.at.signs.in.local.part.com"                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    82 - assertIsFalse "local.part.with.two.@at.signs@domain.com"                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    83 - assertIsFalse "local.part.ends.with.at.sign@@domain.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    84 - assertIsFalse "local.part.with.at.sign.before@.point@domain.com"                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    85 - assertIsFalse "local.part.with.at.sign.after.@point@domain.com"                            =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    86 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    87 - assertIsTrue  "(comment @) local.part.with.at.sign.in.comment@domain.com"                  =   6 =  OK 
     *    88 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"                =   6 =  OK 
     *    89 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *    90 - assertIsTrue  "\"String@\".local.part.with.at.sign.in.string@domain.com"                   =   1 =  OK 
     *    91 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                               =   0 =  OK 
     *    92 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    93 - assertIsFalse "@.@.@.@.@.@@domain.com"                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    94 - assertIsFalse "@.@.@."                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    95 - assertIsFalse "\@.\@@\@.\@"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    96 - assertIsFalse "@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    97 - assertIsFalse "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    98 - assertIsFalse "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Seperator -------------------------------------------------------------------------------------------------------------------
     * 
     *    99 - assertIsFalse "EmailAdressWith@NoDots"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   100 - assertIsFalse "..local.part.starts.with.dot@domain.com"                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   101 - assertIsFalse "local.part.ends.with.dot.@domain.com"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   102 - assertIsTrue  "local.part.with.dot.character@domain.com"                                   =   0 =  OK 
     *   103 - assertIsFalse "local.part.with.dot.before..point@domain.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   104 - assertIsFalse "local.part.with.dot.after..point@domain.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   105 - assertIsFalse "local.part.with.double.dot..test@domain.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   106 - assertIsTrue  "(comment .) local.part.with.dot.in.comment@domain.com"                      =   6 =  OK 
     *   107 - assertIsTrue  "\"string.\".local.part.with.dot.in.String@domain.com"                       =   1 =  OK 
     *   108 - assertIsFalse "\"string\.\".local.part.with.escaped.dot.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   109 - assertIsFalse ".@local.part.only.dot.domain.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   110 - assertIsFalse "......@local.part.only.consecutive.dot.domain.com"                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   111 - assertIsFalse "...........@dot.domain.com"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   112 - assertIsFalse "name . <pointy.brackets1.with.dot.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   113 - assertIsFalse "<pointy.brackets2.with.dot.in.display.name@domain.com> name ."              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   114 - assertIsTrue  "domain.part@with.dot.com"                                                   =   0 =  OK 
     *   115 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   116 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   117 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   118 - assertIsFalse "domain.part@with.dot.before..point.com"                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   119 - assertIsFalse "domain.part@with.dot.after..point.com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   120 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   121 - assertIsTrue  "domain.part.with.dot.in.comment@(comment .)domain.com"                      =   6 =  OK 
     *   122 - assertIsFalse "domain.part.only.dot@..com"                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   123 - assertIsFalse "top.level.domain.only@dot.."                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   124 - assertIsFalse "...local.part.starts.with.double.dot@domain.com"                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   125 - assertIsFalse "local.part.ends.with.double.dot..@domain.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   126 - assertIsFalse "local.part.with.double.dot..character@domain.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   127 - assertIsFalse "local.part.with.double.dot.before...point@domain.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   128 - assertIsFalse "local.part.with.double.dot.after...point@domain.com"                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   129 - assertIsFalse "local.part.with.double.double.dot....test@domain.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   130 - assertIsTrue  "(comment ..) local.part.with.double.dot.in.comment@domain.com"              =   6 =  OK 
     *   131 - assertIsTrue  "\"string..\".local.part.with.double.dot.in.String@domain.com"               =   1 =  OK 
     *   132 - assertIsFalse "\"string\..\".local.part.with.escaped.double.dot.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   133 - assertIsFalse "..@local.part.only.double.dot.domain.com"                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   134 - assertIsFalse "............@local.part.only.consecutive.double.dot.domain.com"             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   135 - assertIsFalse ".................@double.dot.domain.com"                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   136 - assertIsFalse "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   137 - assertIsFalse "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .."      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   138 - assertIsFalse "domain.part@with..double.dot.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   139 - assertIsFalse "domain.part@..with.double.dot.at.domain.start.com"                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   140 - assertIsFalse "domain.part@with.double.dot.at.domain.end1...com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   141 - assertIsFalse "domain.part@with.double.dot.at.domain.end2.com.."                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   142 - assertIsFalse "domain.part@with.double.dot.before...point.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   143 - assertIsFalse "domain.part@with.double.dot.after...point.com"                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   144 - assertIsFalse "domain.part@with.consecutive.double.dot....test.com"                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   145 - assertIsTrue  "domain.part.with.comment.with.double.dot@(comment ..)domain.com"            =   6 =  OK 
     *   146 - assertIsFalse "domain.part.only.double.dot@...com"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   147 - assertIsFalse "top.level.domain.only@double.dot..."                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ------------------------------------------------------------------------------------------------------------------
     * 
     *   148 - assertIsTrue  "&local&&part&with&$@amp.com"                                                =   0 =  OK 
     *   149 - assertIsTrue  "*local**part*with*@asterisk.com"                                            =   0 =  OK 
     *   150 - assertIsTrue  "$local$$part$with$@dollar.com"                                              =   0 =  OK 
     *   151 - assertIsTrue  "=local==part=with=@equality.com"                                            =   0 =  OK 
     *   152 - assertIsTrue  "!local!!part!with!@exclamation.com"                                         =   0 =  OK 
     *   153 - assertIsTrue  "`local``part`with`@grave-accent.com"                                        =   0 =  OK 
     *   154 - assertIsTrue  "#local##part#with#@hash.com"                                                =   0 =  OK 
     *   155 - assertIsTrue  "-local--part-with-@hypen.com"                                               =   0 =  OK 
     *   156 - assertIsTrue  "{local{part{{with{@leftbracket.com"                                         =   0 =  OK 
     *   157 - assertIsTrue  "%local%%part%with%@percentage.com"                                          =   0 =  OK 
     *   158 - assertIsTrue  "|local||part|with|@pipe.com"                                                =   0 =  OK 
     *   159 - assertIsTrue  "+local++part+with+@plus.com"                                                =   0 =  OK 
     *   160 - assertIsTrue  "?local??part?with?@question.com"                                            =   0 =  OK 
     *   161 - assertIsTrue  "}local}part}}with}@rightbracket.com"                                        =   0 =  OK 
     *   162 - assertIsTrue  "~local~~part~with~@tilde.com"                                               =   0 =  OK 
     *   163 - assertIsTrue  "^local^^part^with^@xor.com"                                                 =   0 =  OK 
     *   164 - assertIsTrue  "_local__part_with_@underscore.com"                                          =   0 =  OK 
     *   165 - assertIsFalse ":local::part:with:@colon.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   166 - assertIsTrue  "&.local.part.starts.with.amp@domain.com"                                    =   0 =  OK 
     *   167 - assertIsTrue  "local.part.ends.with.amp&@domain.com"                                       =   0 =  OK 
     *   168 - assertIsTrue  "local.part.with.amp&character@domain.com"                                   =   0 =  OK 
     *   169 - assertIsTrue  "local.part.with.amp.before&.point@domain.com"                               =   0 =  OK 
     *   170 - assertIsTrue  "local.part.with.amp.after.&point@domain.com"                                =   0 =  OK 
     *   171 - assertIsTrue  "local.part.with.double.amp&&test@domain.com"                                =   0 =  OK 
     *   172 - assertIsTrue  "(comment &) local.part.with.amp.in.comment@domain.com"                      =   6 =  OK 
     *   173 - assertIsTrue  "\"string&\".local.part.with.amp.in.String@domain.com"                       =   1 =  OK 
     *   174 - assertIsFalse "\"string\&\".local.part.with.escaped.amp.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   175 - assertIsTrue  "&@local.part.only.amp.domain.com"                                           =   0 =  OK 
     *   176 - assertIsTrue  "&&&&&&@local.part.only.consecutive.amp.domain.com"                          =   0 =  OK 
     *   177 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                                 =   0 =  OK 
     *   178 - assertIsFalse "name & <pointy.brackets1.with.amp.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   179 - assertIsFalse "<pointy.brackets2.with.amp.in.display.name@domain.com> name &"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   180 - assertIsFalse "domain.part@with&amp.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   181 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   182 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   183 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   184 - assertIsFalse "domain.part@with.amp.before&.point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   185 - assertIsFalse "domain.part@with.amp.after.&point.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   186 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   187 - assertIsTrue  "domain.part.with.amp.in.comment@(comment &)domain.com"                      =   6 =  OK 
     *   188 - assertIsFalse "domain.part.only.amp@&.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   189 - assertIsFalse "top.level.domain.only@amp.&"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   190 - assertIsTrue  "*.local.part.starts.with.asterisk@domain.com"                               =   0 =  OK 
     *   191 - assertIsTrue  "local.part.ends.with.asterisk*@domain.com"                                  =   0 =  OK 
     *   192 - assertIsTrue  "local.part.with.asterisk*character@domain.com"                              =   0 =  OK 
     *   193 - assertIsTrue  "local.part.with.asterisk.before*.point@domain.com"                          =   0 =  OK 
     *   194 - assertIsTrue  "local.part.with.asterisk.after.*point@domain.com"                           =   0 =  OK 
     *   195 - assertIsTrue  "local.part.with.double.asterisk**test@domain.com"                           =   0 =  OK 
     *   196 - assertIsTrue  "(comment *) local.part.with.asterisk.in.comment@domain.com"                 =   6 =  OK 
     *   197 - assertIsTrue  "\"string*\".local.part.with.asterisk.in.String@domain.com"                  =   1 =  OK 
     *   198 - assertIsFalse "\"string\*\".local.part.with.escaped.asterisk.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   199 - assertIsTrue  "*@local.part.only.asterisk.domain.com"                                      =   0 =  OK 
     *   200 - assertIsTrue  "******@local.part.only.consecutive.asterisk.domain.com"                     =   0 =  OK 
     *   201 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                            =   0 =  OK 
     *   202 - assertIsFalse "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   203 - assertIsFalse "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   204 - assertIsFalse "domain.part@with*asterisk.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   205 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   206 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   207 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   208 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   209 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   210 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   211 - assertIsTrue  "domain.part.with.asterisk.in.comment@(comment *)domain.com"                 =   6 =  OK 
     *   212 - assertIsFalse "domain.part.only.asterisk@*.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   213 - assertIsFalse "top.level.domain.only@asterisk.*"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   214 - assertIsTrue  "_.local.part.starts.with.underscore@domain.com"                             =   0 =  OK 
     *   215 - assertIsTrue  "local.part.ends.with.underscore_@domain.com"                                =   0 =  OK 
     *   216 - assertIsTrue  "local.part.with.underscore_character@domain.com"                            =   0 =  OK 
     *   217 - assertIsTrue  "local.part.with.underscore.before_.point@domain.com"                        =   0 =  OK 
     *   218 - assertIsTrue  "local.part.with.underscore.after._point@domain.com"                         =   0 =  OK 
     *   219 - assertIsTrue  "local.part.with.double.underscore__test@domain.com"                         =   0 =  OK 
     *   220 - assertIsTrue  "(comment _) local.part.with.underscore.in.comment@domain.com"               =   6 =  OK 
     *   221 - assertIsTrue  "\"string_\".local.part.with.underscore.in.String@domain.com"                =   1 =  OK 
     *   222 - assertIsFalse "\"string\_\".local.part.with.escaped.underscore.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   223 - assertIsTrue  "_@local.part.only.underscore.domain.com"                                    =   0 =  OK 
     *   224 - assertIsTrue  "______@local.part.only.consecutive.underscore.domain.com"                   =   0 =  OK 
     *   225 - assertIsTrue  "_._._._._._@underscore.domain.com"                                          =   0 =  OK 
     *   226 - assertIsFalse "name _ <pointy.brackets1.with.underscore.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   227 - assertIsFalse "<pointy.brackets2.with.underscore.in.display.name@domain.com> name _"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   228 - assertIsTrue  "domain.part@with_underscore.com"                                            =   0 =  OK 
     *   229 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   230 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   231 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   232 - assertIsFalse "domain.part@with.underscore.before_.point.com"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   233 - assertIsFalse "domain.part@with.underscore.after._point.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   234 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                          =   0 =  OK 
     *   235 - assertIsTrue  "domain.part.with.underscore.in.comment@(comment _)domain.com"               =   6 =  OK 
     *   236 - assertIsFalse "domain.part.only.underscore@_.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   237 - assertIsFalse "top.level.domain.only@underscore._"                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   238 - assertIsTrue  "$.local.part.starts.with.dollar@domain.com"                                 =   0 =  OK 
     *   239 - assertIsTrue  "local.part.ends.with.dollar$@domain.com"                                    =   0 =  OK 
     *   240 - assertIsTrue  "local.part.with.dollar$character@domain.com"                                =   0 =  OK 
     *   241 - assertIsTrue  "local.part.with.dollar.before$.point@domain.com"                            =   0 =  OK 
     *   242 - assertIsTrue  "local.part.with.dollar.after.$point@domain.com"                             =   0 =  OK 
     *   243 - assertIsTrue  "local.part.with.double.dollar$$test@domain.com"                             =   0 =  OK 
     *   244 - assertIsTrue  "(comment $) local.part.with.dollar.in.comment@domain.com"                   =   6 =  OK 
     *   245 - assertIsTrue  "\"string$\".local.part.with.dollar.in.String@domain.com"                    =   1 =  OK 
     *   246 - assertIsFalse "\"string\$\".local.part.with.escaped.dollar.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   247 - assertIsTrue  "$@local.part.only.dollar.domain.com"                                        =   0 =  OK 
     *   248 - assertIsTrue  "$$$$$$@local.part.only.consecutive.dollar.domain.com"                       =   0 =  OK 
     *   249 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                              =   0 =  OK 
     *   250 - assertIsFalse "name $ <pointy.brackets1.with.dollar.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   251 - assertIsFalse "<pointy.brackets2.with.dollar.in.display.name@domain.com> name $"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   252 - assertIsFalse "domain.part@with$dollar.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   253 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   254 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   255 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   256 - assertIsFalse "domain.part@with.dollar.before$.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   257 - assertIsFalse "domain.part@with.dollar.after.$point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   258 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   259 - assertIsTrue  "domain.part.with.dollar.in.comment@(comment $)domain.com"                   =   6 =  OK 
     *   260 - assertIsFalse "domain.part.only.dollar@$.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   261 - assertIsFalse "top.level.domain.only@dollar.$"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   262 - assertIsTrue  "=.local.part.starts.with.equality@domain.com"                               =   0 =  OK 
     *   263 - assertIsTrue  "local.part.ends.with.equality=@domain.com"                                  =   0 =  OK 
     *   264 - assertIsTrue  "local.part.with.equality=character@domain.com"                              =   0 =  OK 
     *   265 - assertIsTrue  "local.part.with.equality.before=.point@domain.com"                          =   0 =  OK 
     *   266 - assertIsTrue  "local.part.with.equality.after.=point@domain.com"                           =   0 =  OK 
     *   267 - assertIsTrue  "local.part.with.double.equality==test@domain.com"                           =   0 =  OK 
     *   268 - assertIsTrue  "(comment =) local.part.with.equality.in.comment@domain.com"                 =   6 =  OK 
     *   269 - assertIsTrue  "\"string=\".local.part.with.equality.in.String@domain.com"                  =   1 =  OK 
     *   270 - assertIsFalse "\"string\=\".local.part.with.escaped.equality.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   271 - assertIsTrue  "=@local.part.only.equality.domain.com"                                      =   0 =  OK 
     *   272 - assertIsTrue  "======@local.part.only.consecutive.equality.domain.com"                     =   0 =  OK 
     *   273 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                            =   0 =  OK 
     *   274 - assertIsFalse "name = <pointy.brackets1.with.equality.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   275 - assertIsFalse "<pointy.brackets2.with.equality.in.display.name@domain.com> name ="         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   276 - assertIsFalse "domain.part@with=equality.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   277 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   278 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   279 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   280 - assertIsFalse "domain.part@with.equality.before=.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   281 - assertIsFalse "domain.part@with.equality.after.=point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   282 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   283 - assertIsTrue  "domain.part.with.equality.in.comment@(comment =)domain.com"                 =   6 =  OK 
     *   284 - assertIsFalse "domain.part.only.equality@=.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   285 - assertIsFalse "top.level.domain.only@equality.="                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   286 - assertIsTrue  "!.local.part.starts.with.exclamation@domain.com"                            =   0 =  OK 
     *   287 - assertIsTrue  "local.part.ends.with.exclamation!@domain.com"                               =   0 =  OK 
     *   288 - assertIsTrue  "local.part.with.exclamation!character@domain.com"                           =   0 =  OK 
     *   289 - assertIsTrue  "local.part.with.exclamation.before!.point@domain.com"                       =   0 =  OK 
     *   290 - assertIsTrue  "local.part.with.exclamation.after.!point@domain.com"                        =   0 =  OK 
     *   291 - assertIsTrue  "local.part.with.double.exclamation!!test@domain.com"                        =   0 =  OK 
     *   292 - assertIsTrue  "(comment !) local.part.with.exclamation.in.comment@domain.com"              =   6 =  OK 
     *   293 - assertIsTrue  "\"string!\".local.part.with.exclamation.in.String@domain.com"               =   1 =  OK 
     *   294 - assertIsFalse "\"string\!\".local.part.with.escaped.exclamation.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   295 - assertIsTrue  "!@local.part.only.exclamation.domain.com"                                   =   0 =  OK 
     *   296 - assertIsTrue  "!!!!!!@local.part.only.consecutive.exclamation.domain.com"                  =   0 =  OK 
     *   297 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                         =   0 =  OK 
     *   298 - assertIsFalse "name ! <pointy.brackets1.with.exclamation.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   299 - assertIsFalse "<pointy.brackets2.with.exclamation.in.display.name@domain.com> name !"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   300 - assertIsFalse "domain.part@with!exclamation.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   301 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   302 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   303 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   304 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   305 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   306 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   307 - assertIsTrue  "domain.part.with.exclamation.in.comment@(comment !)domain.com"              =   6 =  OK 
     *   308 - assertIsFalse "domain.part.only.exclamation@!.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   309 - assertIsFalse "top.level.domain.only@exclamation.!"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   310 - assertIsTrue  "?.local.part.starts.with.question@domain.com"                               =   0 =  OK 
     *   311 - assertIsTrue  "local.part.ends.with.question?@domain.com"                                  =   0 =  OK 
     *   312 - assertIsTrue  "local.part.with.question?character@domain.com"                              =   0 =  OK 
     *   313 - assertIsTrue  "local.part.with.question.before?.point@domain.com"                          =   0 =  OK 
     *   314 - assertIsTrue  "local.part.with.question.after.?point@domain.com"                           =   0 =  OK 
     *   315 - assertIsTrue  "local.part.with.double.question??test@domain.com"                           =   0 =  OK 
     *   316 - assertIsTrue  "(comment ?) local.part.with.question.in.comment@domain.com"                 =   6 =  OK 
     *   317 - assertIsTrue  "\"string?\".local.part.with.question.in.String@domain.com"                  =   1 =  OK 
     *   318 - assertIsFalse "\"string\?\".local.part.with.escaped.question.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   319 - assertIsTrue  "?@local.part.only.question.domain.com"                                      =   0 =  OK 
     *   320 - assertIsTrue  "??????@local.part.only.consecutive.question.domain.com"                     =   0 =  OK 
     *   321 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                            =   0 =  OK 
     *   322 - assertIsFalse "name ? <pointy.brackets1.with.question.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   323 - assertIsFalse "<pointy.brackets2.with.question.in.display.name@domain.com> name ?"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   324 - assertIsFalse "domain.part@with?question.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   325 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   326 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   327 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   328 - assertIsFalse "domain.part@with.question.before?.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   329 - assertIsFalse "domain.part@with.question.after.?point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   330 - assertIsFalse "domain.part@with.consecutive.question??test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   331 - assertIsTrue  "domain.part.with.question.in.comment@(comment ?)domain.com"                 =   6 =  OK 
     *   332 - assertIsFalse "domain.part.only.question@?.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   333 - assertIsFalse "top.level.domain.only@question.?"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   334 - assertIsTrue  "`.local.part.starts.with.grave-accent@domain.com"                           =   0 =  OK 
     *   335 - assertIsTrue  "local.part.ends.with.grave-accent`@domain.com"                              =   0 =  OK 
     *   336 - assertIsTrue  "local.part.with.grave-accent`character@domain.com"                          =   0 =  OK 
     *   337 - assertIsTrue  "local.part.with.grave-accent.before`.point@domain.com"                      =   0 =  OK 
     *   338 - assertIsTrue  "local.part.with.grave-accent.after.`point@domain.com"                       =   0 =  OK 
     *   339 - assertIsTrue  "local.part.with.double.grave-accent``test@domain.com"                       =   0 =  OK 
     *   340 - assertIsTrue  "(comment `) local.part.with.grave-accent.in.comment@domain.com"             =   6 =  OK 
     *   341 - assertIsTrue  "\"string`\".local.part.with.grave-accent.in.String@domain.com"              =   1 =  OK 
     *   342 - assertIsFalse "\"string\`\".local.part.with.escaped.grave-accent.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   343 - assertIsTrue  "`@local.part.only.grave-accent.domain.com"                                  =   0 =  OK 
     *   344 - assertIsTrue  "``````@local.part.only.consecutive.grave-accent.domain.com"                 =   0 =  OK 
     *   345 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                        =   0 =  OK 
     *   346 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   347 - assertIsFalse "<pointy.brackets2.with.grave-accent.in.display.name@domain.com> name `"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   348 - assertIsFalse "domain.part@with`grave-accent.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   349 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   350 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   351 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   352 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   353 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   354 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   355 - assertIsTrue  "domain.part.with.grave-accent.in.comment@(comment `)domain.com"             =   6 =  OK 
     *   356 - assertIsFalse "domain.part.only.grave-accent@`.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   357 - assertIsFalse "top.level.domain.only@grave-accent.`"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   358 - assertIsTrue  "#.local.part.starts.with.hash@domain.com"                                   =   0 =  OK 
     *   359 - assertIsTrue  "local.part.ends.with.hash#@domain.com"                                      =   0 =  OK 
     *   360 - assertIsTrue  "local.part.with.hash#character@domain.com"                                  =   0 =  OK 
     *   361 - assertIsTrue  "local.part.with.hash.before#.point@domain.com"                              =   0 =  OK 
     *   362 - assertIsTrue  "local.part.with.hash.after.#point@domain.com"                               =   0 =  OK 
     *   363 - assertIsTrue  "local.part.with.double.hash##test@domain.com"                               =   0 =  OK 
     *   364 - assertIsTrue  "(comment #) local.part.with.hash.in.comment@domain.com"                     =   6 =  OK 
     *   365 - assertIsTrue  "\"string#\".local.part.with.hash.in.String@domain.com"                      =   1 =  OK 
     *   366 - assertIsFalse "\"string\#\".local.part.with.escaped.hash.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   367 - assertIsTrue  "#@local.part.only.hash.domain.com"                                          =   0 =  OK 
     *   368 - assertIsTrue  "######@local.part.only.consecutive.hash.domain.com"                         =   0 =  OK 
     *   369 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                                =   0 =  OK 
     *   370 - assertIsFalse "name # <pointy.brackets1.with.hash.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   371 - assertIsFalse "<pointy.brackets2.with.hash.in.display.name@domain.com> name #"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   372 - assertIsFalse "domain.part@with#hash.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   373 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   374 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   375 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   376 - assertIsFalse "domain.part@with.hash.before#.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   377 - assertIsFalse "domain.part@with.hash.after.#point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   378 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   379 - assertIsTrue  "domain.part.with.hash.in.comment@(comment #)domain.com"                     =   6 =  OK 
     *   380 - assertIsFalse "domain.part.only.hash@#.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   381 - assertIsFalse "top.level.domain.only@hash.#"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   382 - assertIsTrue  "%.local.part.starts.with.percentage@domain.com"                             =   0 =  OK 
     *   383 - assertIsTrue  "local.part.ends.with.percentage%@domain.com"                                =   0 =  OK 
     *   384 - assertIsTrue  "local.part.with.percentage%character@domain.com"                            =   0 =  OK 
     *   385 - assertIsTrue  "local.part.with.percentage.before%.point@domain.com"                        =   0 =  OK 
     *   386 - assertIsTrue  "local.part.with.percentage.after.%point@domain.com"                         =   0 =  OK 
     *   387 - assertIsTrue  "local.part.with.double.percentage%%test@domain.com"                         =   0 =  OK 
     *   388 - assertIsTrue  "(comment %) local.part.with.percentage.in.comment@domain.com"               =   6 =  OK 
     *   389 - assertIsTrue  "\"string%\".local.part.with.percentage.in.String@domain.com"                =   1 =  OK 
     *   390 - assertIsFalse "\"string\%\".local.part.with.escaped.percentage.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   391 - assertIsTrue  "%@local.part.only.percentage.domain.com"                                    =   0 =  OK 
     *   392 - assertIsTrue  "%%%%%%@local.part.only.consecutive.percentage.domain.com"                   =   0 =  OK 
     *   393 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                          =   0 =  OK 
     *   394 - assertIsFalse "name % <pointy.brackets1.with.percentage.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   395 - assertIsFalse "<pointy.brackets2.with.percentage.in.display.name@domain.com> name %"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   396 - assertIsFalse "domain.part@with%percentage.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   397 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   398 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   399 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   400 - assertIsFalse "domain.part@with.percentage.before%.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   401 - assertIsFalse "domain.part@with.percentage.after.%point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   402 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   403 - assertIsTrue  "domain.part.with.percentage.in.comment@(comment %)domain.com"               =   6 =  OK 
     *   404 - assertIsFalse "domain.part.only.percentage@%.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   405 - assertIsFalse "top.level.domain.only@percentage.%"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   406 - assertIsTrue  "|.local.part.starts.with.pipe@domain.com"                                   =   0 =  OK 
     *   407 - assertIsTrue  "local.part.ends.with.pipe|@domain.com"                                      =   0 =  OK 
     *   408 - assertIsTrue  "local.part.with.pipe|character@domain.com"                                  =   0 =  OK 
     *   409 - assertIsTrue  "local.part.with.pipe.before|.point@domain.com"                              =   0 =  OK 
     *   410 - assertIsTrue  "local.part.with.pipe.after.|point@domain.com"                               =   0 =  OK 
     *   411 - assertIsTrue  "local.part.with.double.pipe||test@domain.com"                               =   0 =  OK 
     *   412 - assertIsTrue  "(comment |) local.part.with.pipe.in.comment@domain.com"                     =   6 =  OK 
     *   413 - assertIsTrue  "\"string|\".local.part.with.pipe.in.String@domain.com"                      =   1 =  OK 
     *   414 - assertIsFalse "\"string\|\".local.part.with.escaped.pipe.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   415 - assertIsTrue  "|@local.part.only.pipe.domain.com"                                          =   0 =  OK 
     *   416 - assertIsTrue  "||||||@local.part.only.consecutive.pipe.domain.com"                         =   0 =  OK 
     *   417 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                                =   0 =  OK 
     *   418 - assertIsFalse "name | <pointy.brackets1.with.pipe.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   419 - assertIsFalse "<pointy.brackets2.with.pipe.in.display.name@domain.com> name |"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   420 - assertIsFalse "domain.part@with|pipe.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   421 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   422 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   423 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   424 - assertIsFalse "domain.part@with.pipe.before|.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   425 - assertIsFalse "domain.part@with.pipe.after.|point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   426 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   427 - assertIsTrue  "domain.part.with.pipe.in.comment@(comment |)domain.com"                     =   6 =  OK 
     *   428 - assertIsFalse "domain.part.only.pipe@|.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   429 - assertIsFalse "top.level.domain.only@pipe.|"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   430 - assertIsTrue  "+.local.part.starts.with.plus@domain.com"                                   =   0 =  OK 
     *   431 - assertIsTrue  "local.part.ends.with.plus+@domain.com"                                      =   0 =  OK 
     *   432 - assertIsTrue  "local.part.with.plus+character@domain.com"                                  =   0 =  OK 
     *   433 - assertIsTrue  "local.part.with.plus.before+.point@domain.com"                              =   0 =  OK 
     *   434 - assertIsTrue  "local.part.with.plus.after.+point@domain.com"                               =   0 =  OK 
     *   435 - assertIsTrue  "local.part.with.double.plus++test@domain.com"                               =   0 =  OK 
     *   436 - assertIsTrue  "(comment +) local.part.with.plus.in.comment@domain.com"                     =   6 =  OK 
     *   437 - assertIsTrue  "\"string+\".local.part.with.plus.in.String@domain.com"                      =   1 =  OK 
     *   438 - assertIsFalse "\"string\+\".local.part.with.escaped.plus.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   439 - assertIsTrue  "+@local.part.only.plus.domain.com"                                          =   0 =  OK 
     *   440 - assertIsTrue  "++++++@local.part.only.consecutive.plus.domain.com"                         =   0 =  OK 
     *   441 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                                =   0 =  OK 
     *   442 - assertIsFalse "name + <pointy.brackets1.with.plus.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   443 - assertIsFalse "<pointy.brackets2.with.plus.in.display.name@domain.com> name +"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   444 - assertIsFalse "domain.part@with+plus.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   445 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   446 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   447 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   448 - assertIsFalse "domain.part@with.plus.before+.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   449 - assertIsFalse "domain.part@with.plus.after.+point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   450 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   451 - assertIsTrue  "domain.part.with.plus.in.comment@(comment +)domain.com"                     =   6 =  OK 
     *   452 - assertIsFalse "domain.part.only.plus@+.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   453 - assertIsFalse "top.level.domain.only@plus.+"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   454 - assertIsTrue  "{.local.part.starts.with.leftbracket@domain.com"                            =   0 =  OK 
     *   455 - assertIsTrue  "local.part.ends.with.leftbracket{@domain.com"                               =   0 =  OK 
     *   456 - assertIsTrue  "local.part.with.leftbracket{character@domain.com"                           =   0 =  OK 
     *   457 - assertIsTrue  "local.part.with.leftbracket.before{.point@domain.com"                       =   0 =  OK 
     *   458 - assertIsTrue  "local.part.with.leftbracket.after.{point@domain.com"                        =   0 =  OK 
     *   459 - assertIsTrue  "local.part.with.double.leftbracket{{test@domain.com"                        =   0 =  OK 
     *   460 - assertIsTrue  "(comment {) local.part.with.leftbracket.in.comment@domain.com"              =   6 =  OK 
     *   461 - assertIsTrue  "\"string{\".local.part.with.leftbracket.in.String@domain.com"               =   1 =  OK 
     *   462 - assertIsFalse "\"string\{\".local.part.with.escaped.leftbracket.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   463 - assertIsTrue  "{@local.part.only.leftbracket.domain.com"                                   =   0 =  OK 
     *   464 - assertIsTrue  "{{{{{{@local.part.only.consecutive.leftbracket.domain.com"                  =   0 =  OK 
     *   465 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                         =   0 =  OK 
     *   466 - assertIsFalse "name { <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   467 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name {"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   468 - assertIsFalse "domain.part@with{leftbracket.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   469 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   470 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   471 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   472 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   473 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   474 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   475 - assertIsTrue  "domain.part.with.leftbracket.in.comment@(comment {)domain.com"              =   6 =  OK 
     *   476 - assertIsFalse "domain.part.only.leftbracket@{.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   477 - assertIsFalse "top.level.domain.only@leftbracket.{"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   478 - assertIsTrue  "}.local.part.starts.with.rightbracket@domain.com"                           =   0 =  OK 
     *   479 - assertIsTrue  "local.part.ends.with.rightbracket}@domain.com"                              =   0 =  OK 
     *   480 - assertIsTrue  "local.part.with.rightbracket}character@domain.com"                          =   0 =  OK 
     *   481 - assertIsTrue  "local.part.with.rightbracket.before}.point@domain.com"                      =   0 =  OK 
     *   482 - assertIsTrue  "local.part.with.rightbracket.after.}point@domain.com"                       =   0 =  OK 
     *   483 - assertIsTrue  "local.part.with.double.rightbracket}}test@domain.com"                       =   0 =  OK 
     *   484 - assertIsTrue  "(comment }) local.part.with.rightbracket.in.comment@domain.com"             =   6 =  OK 
     *   485 - assertIsTrue  "\"string}\".local.part.with.rightbracket.in.String@domain.com"              =   1 =  OK 
     *   486 - assertIsFalse "\"string\}\".local.part.with.escaped.rightbracket.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   487 - assertIsTrue  "}@local.part.only.rightbracket.domain.com"                                  =   0 =  OK 
     *   488 - assertIsTrue  "}}}}}}@local.part.only.consecutive.rightbracket.domain.com"                 =   0 =  OK 
     *   489 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                        =   0 =  OK 
     *   490 - assertIsFalse "name } <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   491 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name }"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   492 - assertIsFalse "domain.part@with}rightbracket.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   493 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   494 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   495 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   496 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   497 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   498 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   499 - assertIsTrue  "domain.part.with.rightbracket.in.comment@(comment })domain.com"             =   6 =  OK 
     *   500 - assertIsFalse "domain.part.only.rightbracket@}.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   501 - assertIsFalse "top.level.domain.only@rightbracket.}"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   502 - assertIsFalse "(.local.part.starts.with.leftbracket@domain.com"                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   503 - assertIsFalse "local.part.ends.with.leftbracket(@domain.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   504 - assertIsFalse "local.part.with.leftbracket(character@domain.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   505 - assertIsFalse "local.part.with.leftbracket.before(.point@domain.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   506 - assertIsFalse "local.part.with.leftbracket.after.(point@domain.com"                        = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   507 - assertIsFalse "local.part.with.double.leftbracket((test@domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   508 - assertIsFalse "(comment () local.part.with.leftbracket.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   509 - assertIsTrue  "\"string(\".local.part.with.leftbracket.in.String@domain.com"               =   1 =  OK 
     *   510 - assertIsFalse "\"string\(\".local.part.with.escaped.leftbracket.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   511 - assertIsFalse "(@local.part.only.leftbracket.domain.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   512 - assertIsFalse "((((((@local.part.only.consecutive.leftbracket.domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   513 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   514 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"      =   0 =  OK 
     *   515 - assertIsTrue  "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ("      =   0 =  OK 
     *   516 - assertIsFalse "domain.part@with(leftbracket.com"                                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   517 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   518 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   519 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   520 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   521 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   522 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   523 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment ()domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   524 - assertIsFalse "domain.part.only.leftbracket@(.com"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   525 - assertIsFalse "top.level.domain.only@leftbracket.("                                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   526 - assertIsFalse ").local.part.starts.with.rightbracket@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   527 - assertIsFalse "local.part.ends.with.rightbracket)@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   528 - assertIsFalse "local.part.with.rightbracket)character@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   529 - assertIsFalse "local.part.with.rightbracket.before).point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   530 - assertIsFalse "local.part.with.rightbracket.after.)point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   531 - assertIsFalse "local.part.with.double.rightbracket))test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   532 - assertIsFalse "(comment )) local.part.with.rightbracket.in.comment@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   533 - assertIsTrue  "\"string)\".local.part.with.rightbracket.in.String@domain.com"              =   1 =  OK 
     *   534 - assertIsFalse "\"string\)\".local.part.with.escaped.rightbracket.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   535 - assertIsFalse ")@local.part.only.rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   536 - assertIsFalse "))))))@local.part.only.consecutive.rightbracket.domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   537 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   538 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"     =   0 =  OK 
     *   539 - assertIsTrue  "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name )"     =   0 =  OK 
     *   540 - assertIsFalse "domain.part@with)rightbracket.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   541 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   542 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   543 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   544 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   545 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   546 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   547 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ))domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   548 - assertIsFalse "domain.part.only.rightbracket@).com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   549 - assertIsFalse "top.level.domain.only@rightbracket.)"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   550 - assertIsFalse "[.local.part.starts.with.leftbracket@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   551 - assertIsFalse "local.part.ends.with.leftbracket[@domain.com"                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   552 - assertIsFalse "local.part.with.leftbracket[character@domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   553 - assertIsFalse "local.part.with.leftbracket.before[.point@domain.com"                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   554 - assertIsFalse "local.part.with.leftbracket.after.[point@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   555 - assertIsFalse "local.part.with.double.leftbracket[[test@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   556 - assertIsFalse "(comment [) local.part.with.leftbracket.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   557 - assertIsTrue  "\"string[\".local.part.with.leftbracket.in.String@domain.com"               =   1 =  OK 
     *   558 - assertIsFalse "\"string\[\".local.part.with.escaped.leftbracket.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   559 - assertIsFalse "[@local.part.only.leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   560 - assertIsFalse "[[[[[[@local.part.only.consecutive.leftbracket.domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   561 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   562 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   563 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ["      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   564 - assertIsFalse "domain.part@with[leftbracket.com"                                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   565 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   566 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   567 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   568 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   569 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   570 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   571 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment [)domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   572 - assertIsFalse "domain.part.only.leftbracket@[.com"                                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   573 - assertIsFalse "top.level.domain.only@leftbracket.["                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   574 - assertIsFalse "].local.part.starts.with.rightbracket@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   575 - assertIsFalse "local.part.ends.with.rightbracket]@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   576 - assertIsFalse "local.part.with.rightbracket]character@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   577 - assertIsFalse "local.part.with.rightbracket.before].point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   578 - assertIsFalse "local.part.with.rightbracket.after.]point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   579 - assertIsFalse "local.part.with.double.rightbracket]]test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   580 - assertIsFalse "(comment ]) local.part.with.rightbracket.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   581 - assertIsTrue  "\"string]\".local.part.with.rightbracket.in.String@domain.com"              =   1 =  OK 
     *   582 - assertIsFalse "\"string\]\".local.part.with.escaped.rightbracket.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   583 - assertIsFalse "]@local.part.only.rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   584 - assertIsFalse "]]]]]]@local.part.only.consecutive.rightbracket.domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   585 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   586 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   587 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name ]"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   588 - assertIsFalse "domain.part@with]rightbracket.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   589 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   590 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   591 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   592 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   593 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   594 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   595 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ])domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   596 - assertIsFalse "domain.part.only.rightbracket@].com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   597 - assertIsFalse "top.level.domain.only@rightbracket.]"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   598 - assertIsFalse "().local.part.starts.with.empty.bracket@domain.com"                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   599 - assertIsTrue  "local.part.ends.with.empty.bracket()@domain.com"                            =   6 =  OK 
     *   600 - assertIsFalse "local.part.with.empty.bracket()character@domain.com"                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   601 - assertIsFalse "local.part.with.empty.bracket.before().point@domain.com"                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   602 - assertIsFalse "local.part.with.empty.bracket.after.()point@domain.com"                     = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   603 - assertIsFalse "local.part.with.double.empty.bracket()()test@domain.com"                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   604 - assertIsFalse "(comment ()) local.part.with.empty.bracket.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   605 - assertIsTrue  "\"string()\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   606 - assertIsFalse "\"string\()\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   607 - assertIsFalse "()@local.part.only.empty.bracket.domain.com"                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   608 - assertIsFalse "()()()()()()@local.part.only.consecutive.empty.bracket.domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   609 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   610 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =   0 =  OK 
     *   611 - assertIsTrue  "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name ()"   =   0 =  OK 
     *   612 - assertIsFalse "domain.part@with()empty.bracket.com"                                        = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   613 - assertIsTrue  "domain.part@()with.empty.bracket.at.domain.start.com"                       =   6 =  OK 
     *   614 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1().com"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   615 - assertIsTrue  "domain.part@with.empty.bracket.at.domain.end2.com()"                        =   6 =  OK 
     *   616 - assertIsFalse "domain.part@with.empty.bracket.before().point.com"                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   617 - assertIsFalse "domain.part@with.empty.bracket.after.()point.com"                           = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   618 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"                     = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   619 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment ())domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   620 - assertIsFalse "domain.part.only.empty.bracket@().com"                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   621 - assertIsFalse "top.level.domain.only@empty.bracket.()"                                     = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   622 - assertIsTrue  "{}.local.part.starts.with.empty.bracket@domain.com"                         =   0 =  OK 
     *   623 - assertIsTrue  "local.part.ends.with.empty.bracket{}@domain.com"                            =   0 =  OK 
     *   624 - assertIsTrue  "local.part.with.empty.bracket{}character@domain.com"                        =   0 =  OK 
     *   625 - assertIsTrue  "local.part.with.empty.bracket.before{}.point@domain.com"                    =   0 =  OK 
     *   626 - assertIsTrue  "local.part.with.empty.bracket.after.{}point@domain.com"                     =   0 =  OK 
     *   627 - assertIsTrue  "local.part.with.double.empty.bracket{}{}test@domain.com"                    =   0 =  OK 
     *   628 - assertIsTrue  "(comment {}) local.part.with.empty.bracket.in.comment@domain.com"           =   6 =  OK 
     *   629 - assertIsTrue  "\"string{}\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   630 - assertIsFalse "\"string\{}\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   631 - assertIsTrue  "{}@local.part.only.empty.bracket.domain.com"                                =   0 =  OK 
     *   632 - assertIsTrue  "{}{}{}{}{}{}@local.part.only.consecutive.empty.bracket.domain.com"          =   0 =  OK 
     *   633 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                                 =   0 =  OK 
     *   634 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   635 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name {}"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   636 - assertIsFalse "domain.part@with{}empty.bracket.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   637 - assertIsFalse "domain.part@{}with.empty.bracket.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   638 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1{}.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   639 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com{}"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   640 - assertIsFalse "domain.part@with.empty.bracket.before{}.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   641 - assertIsFalse "domain.part@with.empty.bracket.after.{}point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   642 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   643 - assertIsTrue  "domain.part.with.empty.bracket.in.comment@(comment {})domain.com"           =   6 =  OK 
     *   644 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   645 - assertIsFalse "top.level.domain.only@empty.bracket.{}"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   646 - assertIsFalse "[].local.part.starts.with.empty.bracket@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   647 - assertIsFalse "local.part.ends.with.empty.bracket[]@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   648 - assertIsFalse "local.part.with.empty.bracket[]character@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   649 - assertIsFalse "local.part.with.empty.bracket.before[].point@domain.com"                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   650 - assertIsFalse "local.part.with.empty.bracket.after.[]point@domain.com"                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   651 - assertIsFalse "local.part.with.double.empty.bracket[][]test@domain.com"                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   652 - assertIsFalse "(comment []) local.part.with.empty.bracket.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   653 - assertIsTrue  "\"string[]\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   654 - assertIsFalse "\"string\[]\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   655 - assertIsFalse "[]@local.part.only.empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   656 - assertIsFalse "[][][][][][]@local.part.only.consecutive.empty.bracket.domain.com"          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   657 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   658 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   659 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name []"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   660 - assertIsFalse "domain.part@with[]empty.bracket.com"                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   661 - assertIsFalse "domain.part@[]with.empty.bracket.at.domain.start.com"                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   662 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1[].com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   663 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com[]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   664 - assertIsFalse "domain.part@with.empty.bracket.before[].point.com"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   665 - assertIsFalse "domain.part@with.empty.bracket.after.[]point.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   666 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   667 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment [])domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   668 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   669 - assertIsFalse "top.level.domain.only@empty.bracket.[]"                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   670 - assertIsFalse "<>.local.part.starts.with.empty.bracket@domain.com"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   671 - assertIsFalse "local.part.ends.with.empty.bracket<>@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   672 - assertIsFalse "local.part.with.empty.bracket<>character@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   673 - assertIsFalse "local.part.with.empty.bracket.before<>.point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   674 - assertIsFalse "local.part.with.empty.bracket.after.<>point@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsFalse "local.part.with.double.empty.bracket<><>test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   676 - assertIsFalse "(comment <>) local.part.with.empty.bracket.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   677 - assertIsTrue  "\"string<>\".local.part.with.empty.bracket.in.String@domain.com"            =   1 =  OK 
     *   678 - assertIsFalse "\"string\<>\".local.part.with.escaped.empty.bracket.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   679 - assertIsFalse "<>@local.part.only.empty.bracket.domain.com"                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   680 - assertIsFalse "<><><><><><>@local.part.only.consecutive.empty.bracket.domain.com"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   681 - assertIsFalse "<>.<>.<>.<>.<>.<>@empty.bracket.domain.com"                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   682 - assertIsFalse "name <> <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   683 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name <>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   684 - assertIsFalse "domain.part@with<>empty.bracket.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   685 - assertIsFalse "domain.part@<>with.empty.bracket.at.domain.start.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   686 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1<>.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   687 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com<>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   688 - assertIsFalse "domain.part@with.empty.bracket.before<>.point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   689 - assertIsFalse "domain.part@with.empty.bracket.after.<>point.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   690 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   691 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment <>)domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   692 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   693 - assertIsFalse "top.level.domain.only@empty.bracket.<>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   694 - assertIsFalse ")(.local.part.starts.with.false.bracket1@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   695 - assertIsFalse "local.part.ends.with.false.bracket1)(@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   696 - assertIsFalse "local.part.with.false.bracket1)(character@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   697 - assertIsFalse "local.part.with.false.bracket1.before)(.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   698 - assertIsFalse "local.part.with.false.bracket1.after.)(point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   699 - assertIsFalse "local.part.with.double.false.bracket1)()(test@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   700 - assertIsFalse "(comment )() local.part.with.false.bracket1.in.comment@domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   701 - assertIsTrue  "\"string)(\".local.part.with.false.bracket1.in.String@domain.com"           =   1 =  OK 
     *   702 - assertIsFalse "\"string\)(\".local.part.with.escaped.false.bracket1.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   703 - assertIsFalse ")(@local.part.only.false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   704 - assertIsFalse ")()()()()()(@local.part.only.consecutive.false.bracket1.domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   705 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   706 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1.in.display.name@domain.com>"  =   0 =  OK 
     *   707 - assertIsTrue  "<pointy.brackets2.with.false.bracket1.in.display.name@domain.com> name )("  =   0 =  OK 
     *   708 - assertIsFalse "domain.part@with)(false.bracket1.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   709 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   710 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   711 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   712 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   713 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   714 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   715 - assertIsFalse "domain.part.with.false.bracket1.in.comment@(comment )()domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   716 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   717 - assertIsFalse "top.level.domain.only@false.bracket1.)("                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   718 - assertIsTrue  "}{.local.part.starts.with.false.bracket2@domain.com"                        =   0 =  OK 
     *   719 - assertIsTrue  "local.part.ends.with.false.bracket2}{@domain.com"                           =   0 =  OK 
     *   720 - assertIsTrue  "local.part.with.false.bracket2}{character@domain.com"                       =   0 =  OK 
     *   721 - assertIsTrue  "local.part.with.false.bracket2.before}{.point@domain.com"                   =   0 =  OK 
     *   722 - assertIsTrue  "local.part.with.false.bracket2.after.}{point@domain.com"                    =   0 =  OK 
     *   723 - assertIsTrue  "local.part.with.double.false.bracket2}{}{test@domain.com"                   =   0 =  OK 
     *   724 - assertIsTrue  "(comment }{) local.part.with.false.bracket2.in.comment@domain.com"          =   6 =  OK 
     *   725 - assertIsTrue  "\"string}{\".local.part.with.false.bracket2.in.String@domain.com"           =   1 =  OK 
     *   726 - assertIsFalse "\"string\}{\".local.part.with.escaped.false.bracket2.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   727 - assertIsTrue  "}{@local.part.only.false.bracket2.domain.com"                               =   0 =  OK 
     *   728 - assertIsTrue  "}{}{}{}{}{}{@local.part.only.consecutive.false.bracket2.domain.com"         =   0 =  OK 
     *   729 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                                =   0 =  OK 
     *   730 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2.in.display.name@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   731 - assertIsFalse "<pointy.brackets2.with.false.bracket2.in.display.name@domain.com> name }{"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   732 - assertIsFalse "domain.part@with}{false.bracket2.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   733 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   734 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   735 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   736 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   737 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   738 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   739 - assertIsTrue  "domain.part.with.false.bracket2.in.comment@(comment }{)domain.com"          =   6 =  OK 
     *   740 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   741 - assertIsFalse "top.level.domain.only@false.bracket2.}{"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   742 - assertIsFalse "][.local.part.starts.with.false.bracket3@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   743 - assertIsFalse "local.part.ends.with.false.bracket3][@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   744 - assertIsFalse "local.part.with.false.bracket3][character@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   745 - assertIsFalse "local.part.with.false.bracket3.before][.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   746 - assertIsFalse "local.part.with.false.bracket3.after.][point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   747 - assertIsFalse "local.part.with.double.false.bracket3][][test@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   748 - assertIsFalse "(comment ][) local.part.with.false.bracket3.in.comment@domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   749 - assertIsTrue  "\"string][\".local.part.with.false.bracket3.in.String@domain.com"           =   1 =  OK 
     *   750 - assertIsFalse "\"string\][\".local.part.with.escaped.false.bracket3.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   751 - assertIsFalse "][@local.part.only.false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   752 - assertIsFalse "][][][][][][@local.part.only.consecutive.false.bracket3.domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   753 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   754 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3.in.display.name@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   755 - assertIsFalse "<pointy.brackets2.with.false.bracket3.in.display.name@domain.com> name ]["  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   756 - assertIsFalse "domain.part@with][false.bracket3.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   757 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   758 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   759 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   760 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   761 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   762 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   763 - assertIsFalse "domain.part.with.false.bracket3.in.comment@(comment ][)domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   764 - assertIsFalse "domain.part.only.false.bracket3@][.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   765 - assertIsFalse "top.level.domain.only@false.bracket3.]["                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   766 - assertIsFalse "><.local.part.starts.with.false.bracket4@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   767 - assertIsFalse "local.part.ends.with.false.bracket4><@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   768 - assertIsFalse "local.part.with.false.bracket4><character@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   769 - assertIsFalse "local.part.with.false.bracket4.before><.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   770 - assertIsFalse "local.part.with.false.bracket4.after.><point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   771 - assertIsFalse "local.part.with.double.false.bracket4><><test@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse "(comment ><) local.part.with.false.bracket4.in.comment@domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   773 - assertIsTrue  "\"string><\".local.part.with.false.bracket4.in.String@domain.com"           =   1 =  OK 
     *   774 - assertIsFalse "\"string\><\".local.part.with.escaped.false.bracket4.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   775 - assertIsFalse "><@local.part.only.false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   776 - assertIsFalse "><><><><><><@local.part.only.consecutive.false.bracket4.domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   777 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   778 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4.in.display.name@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   779 - assertIsFalse "<pointy.brackets2.with.false.bracket4.in.display.name@domain.com> name ><"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   780 - assertIsFalse "domain.part@with><false.bracket4.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   781 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   782 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   783 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   784 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   785 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   786 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   787 - assertIsFalse "domain.part.with.false.bracket4.in.comment@(comment ><)domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   788 - assertIsFalse "domain.part.only.false.bracket4@><.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   789 - assertIsFalse "top.level.domain.only@false.bracket4.><"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   790 - assertIsFalse "<.local.part.starts.with.lower.than@domain.com"                             =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   791 - assertIsFalse "local.part.ends.with.lower.than<@domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   792 - assertIsFalse "local.part.with.lower.than<character@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   793 - assertIsFalse "local.part.with.lower.than.before<.point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   794 - assertIsFalse "local.part.with.lower.than.after.<point@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   795 - assertIsFalse "local.part.with.double.lower.than<<test@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   796 - assertIsFalse "(comment <) local.part.with.lower.than.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   797 - assertIsTrue  "\"string<\".local.part.with.lower.than.in.String@domain.com"                =   1 =  OK 
     *   798 - assertIsFalse "\"string\<\".local.part.with.escaped.lower.than.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   799 - assertIsFalse "<@local.part.only.lower.than.domain.com"                                    =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   800 - assertIsFalse "<<<<<<@local.part.only.consecutive.lower.than.domain.com"                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   801 - assertIsFalse "<.<.<.<.<.<@lower.than.domain.com"                                          =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   802 - assertIsFalse "name < <pointy.brackets1.with.lower.than.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   803 - assertIsFalse "<pointy.brackets2.with.lower.than.in.display.name@domain.com> name <"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   804 - assertIsFalse "domain.part@with<lower.than.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   805 - assertIsFalse "domain.part@<with.lower.than.at.domain.start.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   806 - assertIsFalse "domain.part@with.lower.than.at.domain.end1<.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   807 - assertIsFalse "domain.part@with.lower.than.at.domain.end2.com<"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   808 - assertIsFalse "domain.part@with.lower.than.before<.point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   809 - assertIsFalse "domain.part@with.lower.than.after.<point.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   810 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   811 - assertIsFalse "domain.part.with.lower.than.in.comment@(comment <)domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   812 - assertIsFalse "domain.part.only.lower.than@<.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   813 - assertIsFalse "top.level.domain.only@lower.than.<"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   814 - assertIsFalse ">.local.part.starts.with.greater.than@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   815 - assertIsFalse "local.part.ends.with.greater.than>@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   816 - assertIsFalse "local.part.with.greater.than>character@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   817 - assertIsFalse "local.part.with.greater.than.before>.point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   818 - assertIsFalse "local.part.with.greater.than.after.>point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   819 - assertIsFalse "local.part.with.double.greater.than>>test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   820 - assertIsFalse "(comment >) local.part.with.greater.than.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   821 - assertIsTrue  "\"string>\".local.part.with.greater.than.in.String@domain.com"              =   1 =  OK 
     *   822 - assertIsFalse "\"string\>\".local.part.with.escaped.greater.than.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   823 - assertIsFalse ">@local.part.only.greater.than.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   824 - assertIsFalse ">>>>>>@local.part.only.consecutive.greater.than.domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   825 - assertIsFalse ">.>.>.>.>.>@greater.than.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   826 - assertIsFalse "name > <pointy.brackets1.with.greater.than.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   827 - assertIsFalse "<pointy.brackets2.with.greater.than.in.display.name@domain.com> name >"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   828 - assertIsFalse "domain.part@with>greater.than.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   829 - assertIsFalse "domain.part@>with.greater.than.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   830 - assertIsFalse "domain.part@with.greater.than.at.domain.end1>.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   831 - assertIsFalse "domain.part@with.greater.than.at.domain.end2.com>"                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   832 - assertIsFalse "domain.part@with.greater.than.before>.point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   833 - assertIsFalse "domain.part@with.greater.than.after.>point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   834 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   835 - assertIsFalse "domain.part.with.greater.than.in.comment@(comment >)domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   836 - assertIsFalse "domain.part.only.greater.than@>.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   837 - assertIsFalse "top.level.domain.only@greater.than.>"                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   838 - assertIsTrue  "~.local.part.starts.with.tilde@domain.com"                                  =   0 =  OK 
     *   839 - assertIsTrue  "local.part.ends.with.tilde~@domain.com"                                     =   0 =  OK 
     *   840 - assertIsTrue  "local.part.with.tilde~character@domain.com"                                 =   0 =  OK 
     *   841 - assertIsTrue  "local.part.with.tilde.before~.point@domain.com"                             =   0 =  OK 
     *   842 - assertIsTrue  "local.part.with.tilde.after.~point@domain.com"                              =   0 =  OK 
     *   843 - assertIsTrue  "local.part.with.double.tilde~~test@domain.com"                              =   0 =  OK 
     *   844 - assertIsTrue  "(comment ~) local.part.with.tilde.in.comment@domain.com"                    =   6 =  OK 
     *   845 - assertIsTrue  "\"string~\".local.part.with.tilde.in.String@domain.com"                     =   1 =  OK 
     *   846 - assertIsFalse "\"string\~\".local.part.with.escaped.tilde.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   847 - assertIsTrue  "~@local.part.only.tilde.domain.com"                                         =   0 =  OK 
     *   848 - assertIsTrue  "~~~~~~@local.part.only.consecutive.tilde.domain.com"                        =   0 =  OK 
     *   849 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                               =   0 =  OK 
     *   850 - assertIsFalse "name ~ <pointy.brackets1.with.tilde.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   851 - assertIsFalse "<pointy.brackets2.with.tilde.in.display.name@domain.com> name ~"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   852 - assertIsFalse "domain.part@with~tilde.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   853 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   854 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   855 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   856 - assertIsFalse "domain.part@with.tilde.before~.point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   857 - assertIsFalse "domain.part@with.tilde.after.~point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   858 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   859 - assertIsTrue  "domain.part.with.tilde.in.comment@(comment ~)domain.com"                    =   6 =  OK 
     *   860 - assertIsFalse "domain.part.only.tilde@~.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   861 - assertIsFalse "top.level.domain.only@tilde.~"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   862 - assertIsTrue  "^.local.part.starts.with.xor@domain.com"                                    =   0 =  OK 
     *   863 - assertIsTrue  "local.part.ends.with.xor^@domain.com"                                       =   0 =  OK 
     *   864 - assertIsTrue  "local.part.with.xor^character@domain.com"                                   =   0 =  OK 
     *   865 - assertIsTrue  "local.part.with.xor.before^.point@domain.com"                               =   0 =  OK 
     *   866 - assertIsTrue  "local.part.with.xor.after.^point@domain.com"                                =   0 =  OK 
     *   867 - assertIsTrue  "local.part.with.double.xor^^test@domain.com"                                =   0 =  OK 
     *   868 - assertIsTrue  "(comment ^) local.part.with.xor.in.comment@domain.com"                      =   6 =  OK 
     *   869 - assertIsTrue  "\"string^\".local.part.with.xor.in.String@domain.com"                       =   1 =  OK 
     *   870 - assertIsFalse "\"string\^\".local.part.with.escaped.xor.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   871 - assertIsTrue  "^@local.part.only.xor.domain.com"                                           =   0 =  OK 
     *   872 - assertIsTrue  "^^^^^^@local.part.only.consecutive.xor.domain.com"                          =   0 =  OK 
     *   873 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                                 =   0 =  OK 
     *   874 - assertIsFalse "name ^ <pointy.brackets1.with.xor.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   875 - assertIsFalse "<pointy.brackets2.with.xor.in.display.name@domain.com> name ^"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   876 - assertIsFalse "domain.part@with^xor.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   877 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   878 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   879 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   880 - assertIsFalse "domain.part@with.xor.before^.point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   881 - assertIsFalse "domain.part@with.xor.after.^point.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   882 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   883 - assertIsTrue  "domain.part.with.xor.in.comment@(comment ^)domain.com"                      =   6 =  OK 
     *   884 - assertIsFalse "domain.part.only.xor@^.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   885 - assertIsFalse "top.level.domain.only@xor.^"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   886 - assertIsFalse ":.local.part.starts.with.colon@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   887 - assertIsFalse "local.part.ends.with.colon:@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   888 - assertIsFalse "local.part.with.colon:character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   889 - assertIsFalse "local.part.with.colon.before:.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   890 - assertIsFalse "local.part.with.colon.after.:point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   891 - assertIsFalse "local.part.with.double.colon::test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   892 - assertIsFalse "(comment :) local.part.with.colon.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   893 - assertIsTrue  "\"string:\".local.part.with.colon.in.String@domain.com"                     =   1 =  OK 
     *   894 - assertIsFalse "\"string\:\".local.part.with.escaped.colon.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   895 - assertIsFalse ":@local.part.only.colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   896 - assertIsFalse "::::::@local.part.only.consecutive.colon.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   897 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   898 - assertIsFalse "name : <pointy.brackets1.with.colon.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   899 - assertIsFalse "<pointy.brackets2.with.colon.in.display.name@domain.com> name :"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   900 - assertIsFalse "domain.part@with:colon.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   901 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   902 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   903 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   904 - assertIsFalse "domain.part@with.colon.before:.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   905 - assertIsFalse "domain.part@with.colon.after.:point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   906 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   907 - assertIsFalse "domain.part.with.colon.in.comment@(comment :)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   908 - assertIsFalse "domain.part.only.colon@:.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   909 - assertIsFalse "top.level.domain.only@colon.:"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   910 - assertIsFalse " .local.part.starts.with.space@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   911 - assertIsFalse "local.part.ends.with.space @domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   912 - assertIsFalse "local.part.with.space character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   913 - assertIsFalse "local.part.with.space.before .point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   914 - assertIsFalse "local.part.with.space.after. point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   915 - assertIsFalse "local.part.with.double.space  test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   916 - assertIsTrue  "(comment  ) local.part.with.space.in.comment@domain.com"                    =   6 =  OK 
     *   917 - assertIsTrue  "\"string \".local.part.with.space.in.String@domain.com"                     =   1 =  OK 
     *   918 - assertIsTrue  "\"string\ \".local.part.with.escaped.space.in.String@domain.com"            =   1 =  OK 
     *   919 - assertIsFalse " @local.part.only.space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   920 - assertIsFalse "      @local.part.only.consecutive.space.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   921 - assertIsFalse " . . . . . @space.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   922 - assertIsTrue  "name   <pointy.brackets1.with.space.in.display.name@domain.com>"            =   0 =  OK 
     *   923 - assertIsTrue  "<pointy.brackets2.with.space.in.display.name@domain.com> name  "            =   0 =  OK 
     *   924 - assertIsFalse "domain.part@with space.com"                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   925 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   926 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   927 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   928 - assertIsFalse "domain.part@with.space.before .point.com"                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   929 - assertIsFalse "domain.part@with.space.after. point.com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   930 - assertIsFalse "domain.part@with.consecutive.space  test.com"                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   931 - assertIsTrue  "domain.part.with.space.in.comment@(comment  )domain.com"                    =   6 =  OK 
     *   932 - assertIsFalse "domain.part.only.space@ .com"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   933 - assertIsFalse "top.level.domain.only@space. "                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   934 - assertIsFalse ",.local.part.starts.with.comma@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   935 - assertIsFalse "local.part.ends.with.comma,@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   936 - assertIsFalse "local.part.with.comma,character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   937 - assertIsFalse "local.part.with.comma.before,.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   938 - assertIsFalse "local.part.with.comma.after.,point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   939 - assertIsFalse "local.part.with.double.comma,,test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   940 - assertIsFalse "(comment ,) local.part.with.comma.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   941 - assertIsTrue  "\"string,\".local.part.with.comma.in.String@domain.com"                     =   1 =  OK 
     *   942 - assertIsFalse "\"string\,\".local.part.with.escaped.comma.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   943 - assertIsFalse ",@local.part.only.comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   944 - assertIsFalse ",,,,,,@local.part.only.consecutive.comma.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   945 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   946 - assertIsFalse "name , <pointy.brackets1.with.comma.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   947 - assertIsFalse "<pointy.brackets2.with.comma.in.display.name@domain.com> name ,"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   948 - assertIsFalse "domain.part@with,comma.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   949 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   950 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   951 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   952 - assertIsFalse "domain.part@with.comma.before,.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   953 - assertIsFalse "domain.part@with.comma.after.,point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   954 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   955 - assertIsFalse "domain.part.with.comma.in.comment@(comment ,)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   956 - assertIsFalse "domain.part.only.comma@,.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   957 - assertIsFalse "top.level.domain.only@comma.,"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   958 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   959 - assertIsFalse "local.part.ends.with.at@@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   960 - assertIsFalse "local.part.with.at@character@domain.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   961 - assertIsFalse "local.part.with.at.before@.point@domain.com"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   962 - assertIsFalse "local.part.with.at.after.@point@domain.com"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   963 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   964 - assertIsTrue  "(comment @) local.part.with.at.in.comment@domain.com"                       =   6 =  OK 
     *   965 - assertIsTrue  "\"string@\".local.part.with.at.in.String@domain.com"                        =   1 =  OK 
     *   966 - assertIsTrue  "\"string\@\".local.part.with.escaped.at.in.String@domain.com"               =   1 =  OK 
     *   967 - assertIsFalse "@@local.part.only.at.domain.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   968 - assertIsFalse "@@@@@@@local.part.only.consecutive.at.domain.com"                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   969 - assertIsFalse "@.@.@.@.@.@@at.domain.com"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   970 - assertIsFalse "name @ <pointy.brackets1.with.at.in.display.name@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   971 - assertIsFalse "<pointy.brackets2.with.at.in.display.name@domain.com> name @"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   972 - assertIsFalse "domain.part@with@at.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   973 - assertIsFalse "domain.part@@with.at.at.domain.start.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   974 - assertIsFalse "domain.part@with.at.at.domain.end1@.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   975 - assertIsFalse "domain.part@with.at.at.domain.end2.com@"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   976 - assertIsFalse "domain.part@with.at.before@.point.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   977 - assertIsFalse "domain.part@with.at.after.@point.com"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   978 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   979 - assertIsTrue  "domain.part.with.at.in.comment@(comment @)domain.com"                       =   6 =  OK 
     *   980 - assertIsFalse "domain.part.only.at@@.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   981 - assertIsFalse "top.level.domain.only@at.@"                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   982 - assertIsFalse "§.local.part.starts.with.paragraph@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   983 - assertIsFalse "local.part.ends.with.paragraph§@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   984 - assertIsFalse "local.part.with.paragraph§character@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   985 - assertIsFalse "local.part.with.paragraph.before§.point@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   986 - assertIsFalse "local.part.with.paragraph.after.§point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   987 - assertIsFalse "local.part.with.double.paragraph§§test@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   988 - assertIsFalse "(comment §) local.part.with.paragraph.in.comment@domain.com"                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   989 - assertIsFalse "\"string§\".local.part.with.paragraph.in.String@domain.com"                 =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   990 - assertIsFalse "\"string\§\".local.part.with.escaped.paragraph.in.String@domain.com"        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   991 - assertIsFalse "§@local.part.only.paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   992 - assertIsFalse "§§§§§§@local.part.only.consecutive.paragraph.domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   993 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   994 - assertIsFalse "name § <pointy.brackets1.with.paragraph.in.display.name@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   995 - assertIsFalse "<pointy.brackets2.with.paragraph.in.display.name@domain.com> name §"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   996 - assertIsFalse "domain.part@with§paragraph.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   997 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   998 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   999 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1000 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1001 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1002 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1003 - assertIsFalse "domain.part.with.paragraph.in.comment@(comment §)domain.com"                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1004 - assertIsFalse "domain.part.only.paragraph@§.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1005 - assertIsFalse "top.level.domain.only@paragraph.§"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1006 - assertIsTrue  "'.local.part.starts.with.double.quote@domain.com"                           =   0 =  OK 
     *  1007 - assertIsTrue  "local.part.ends.with.double.quote'@domain.com"                              =   0 =  OK 
     *  1008 - assertIsTrue  "local.part.with.double.quote'character@domain.com"                          =   0 =  OK 
     *  1009 - assertIsTrue  "local.part.with.double.quote.before'.point@domain.com"                      =   0 =  OK 
     *  1010 - assertIsTrue  "local.part.with.double.quote.after.'point@domain.com"                       =   0 =  OK 
     *  1011 - assertIsTrue  "local.part.with.double.double.quote''test@domain.com"                       =   0 =  OK 
     *  1012 - assertIsTrue  "(comment ') local.part.with.double.quote.in.comment@domain.com"             =   6 =  OK 
     *  1013 - assertIsTrue  "\"string'\".local.part.with.double.quote.in.String@domain.com"              =   1 =  OK 
     *  1014 - assertIsTrue  "\"string\'\".local.part.with.escaped.double.quote.in.String@domain.com"     =   1 =  OK 
     *  1015 - assertIsTrue  "'@local.part.only.double.quote.domain.com"                                  =   0 =  OK 
     *  1016 - assertIsTrue  "''''''@local.part.only.consecutive.double.quote.domain.com"                 =   0 =  OK 
     *  1017 - assertIsTrue  "'.'.'.'.'.'@double.quote.domain.com"                                        =   0 =  OK 
     *  1018 - assertIsFalse "name ' <pointy.brackets1.with.double.quote.in.display.name@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1019 - assertIsFalse "<pointy.brackets2.with.double.quote.in.display.name@domain.com> name '"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1020 - assertIsFalse "domain.part@with'double.quote.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1021 - assertIsFalse "domain.part@'with.double.quote.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1022 - assertIsFalse "domain.part@with.double.quote.at.domain.end1'.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1023 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com'"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1024 - assertIsFalse "domain.part@with.double.quote.before'.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1025 - assertIsFalse "domain.part@with.double.quote.after.'point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1026 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1027 - assertIsTrue  "domain.part.with.double.quote.in.comment@(comment ')domain.com"             =   6 =  OK 
     *  1028 - assertIsFalse "domain.part.only.double.quote@'.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1029 - assertIsFalse "top.level.domain.only@double.quote.'"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1030 - assertIsTrue  "/.local.part.starts.with.forward.slash@domain.com"                          =   0 =  OK 
     *  1031 - assertIsTrue  "local.part.ends.with.forward.slash/@domain.com"                             =   0 =  OK 
     *  1032 - assertIsTrue  "local.part.with.forward.slash/character@domain.com"                         =   0 =  OK 
     *  1033 - assertIsTrue  "local.part.with.forward.slash.before/.point@domain.com"                     =   0 =  OK 
     *  1034 - assertIsTrue  "local.part.with.forward.slash.after./point@domain.com"                      =   0 =  OK 
     *  1035 - assertIsTrue  "local.part.with.double.forward.slash//test@domain.com"                      =   0 =  OK 
     *  1036 - assertIsTrue  "(comment /) local.part.with.forward.slash.in.comment@domain.com"            =   6 =  OK 
     *  1037 - assertIsTrue  "\"string/\".local.part.with.forward.slash.in.String@domain.com"             =   1 =  OK 
     *  1038 - assertIsFalse "\"string\/\".local.part.with.escaped.forward.slash.in.String@domain.com"    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1039 - assertIsTrue  "/@local.part.only.forward.slash.domain.com"                                 =   0 =  OK 
     *  1040 - assertIsTrue  "//////@local.part.only.consecutive.forward.slash.domain.com"                =   0 =  OK 
     *  1041 - assertIsTrue  "/./././././@forward.slash.domain.com"                                       =   0 =  OK 
     *  1042 - assertIsFalse "name / <pointy.brackets1.with.forward.slash.in.display.name@domain.com>"    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1043 - assertIsFalse "<pointy.brackets2.with.forward.slash.in.display.name@domain.com> name /"    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1044 - assertIsFalse "domain.part@with/forward.slash.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1045 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1046 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1047 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1048 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1049 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1050 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1051 - assertIsTrue  "domain.part.with.forward.slash.in.comment@(comment /)domain.com"            =   6 =  OK 
     *  1052 - assertIsFalse "domain.part.only.forward.slash@/.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1053 - assertIsFalse "top.level.domain.only@forward.slash./"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1054 - assertIsTrue  "-.local.part.starts.with.hyphen@domain.com"                                 =   0 =  OK 
     *  1055 - assertIsTrue  "local.part.ends.with.hyphen-@domain.com"                                    =   0 =  OK 
     *  1056 - assertIsTrue  "local.part.with.hyphen-character@domain.com"                                =   0 =  OK 
     *  1057 - assertIsTrue  "local.part.with.hyphen.before-.point@domain.com"                            =   0 =  OK 
     *  1058 - assertIsTrue  "local.part.with.hyphen.after.-point@domain.com"                             =   0 =  OK 
     *  1059 - assertIsTrue  "local.part.with.double.hyphen--test@domain.com"                             =   0 =  OK 
     *  1060 - assertIsTrue  "(comment -) local.part.with.hyphen.in.comment@domain.com"                   =   6 =  OK 
     *  1061 - assertIsTrue  "\"string-\".local.part.with.hyphen.in.String@domain.com"                    =   1 =  OK 
     *  1062 - assertIsFalse "\"string\-\".local.part.with.escaped.hyphen.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1063 - assertIsTrue  "-@local.part.only.hyphen.domain.com"                                        =   0 =  OK 
     *  1064 - assertIsTrue  "------@local.part.only.consecutive.hyphen.domain.com"                       =   0 =  OK 
     *  1065 - assertIsTrue  "-.-.-.-.-.-@hyphen.domain.com"                                              =   0 =  OK 
     *  1066 - assertIsFalse "name - <pointy.brackets1.with.hyphen.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1067 - assertIsFalse "<pointy.brackets2.with.hyphen.in.display.name@domain.com> name -"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1068 - assertIsTrue  "domain.part@with-hyphen.com"                                                =   0 =  OK 
     *  1069 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1070 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1071 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1072 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1073 - assertIsFalse "domain.part@with.hyphen.after.-point.com"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1074 - assertIsTrue  "domain.part@with.consecutive.hyphen--test.com"                              =   0 =  OK 
     *  1075 - assertIsTrue  "domain.part.with.hyphen.in.comment@(comment -)domain.com"                   =   6 =  OK 
     *  1076 - assertIsFalse "domain.part.only.hyphen@-.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1077 - assertIsFalse "top.level.domain.only@hyphen.-"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1078 - assertIsFalse "\"\".local.part.starts.with.empty.string1@domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1079 - assertIsFalse "local.part.ends.with.empty.string1\"\"@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1080 - assertIsFalse "local.part.with.empty.string1\"\"character@domain.com"                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1081 - assertIsFalse "local.part.with.empty.string1.before\"\".point@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1082 - assertIsFalse "local.part.with.empty.string1.after.\"\"point@domain.com"                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1083 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"test@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1084 - assertIsFalse "(comment \"\") local.part.with.empty.string1.in.comment@domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1085 - assertIsFalse "\"string\"\"\".local.part.with.empty.string1.in.String@domain.com"          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1086 - assertIsFalse "\"string\\"\"\".local.part.with.escaped.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1087 - assertIsFalse "\"\"@local.part.only.empty.string1.domain.com"                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1088 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1089 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"                     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1090 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1.in.display.name@domain.com>" =   0 =  OK 
     *  1091 - assertIsTrue  "<pointy.brackets2.with.empty.string1.in.display.name@domain.com> name \"\"" =   0 =  OK 
     *  1092 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1093 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1094 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1095 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1096 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1097 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1098 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1099 - assertIsFalse "domain.part.with.empty.string1.in.comment@(comment \"\")domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1100 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1101 - assertIsFalse "top.level.domain.only@empty.string1.\"\""                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1102 - assertIsFalse "a\"\"b.local.part.starts.with.empty.string2@domain.com"                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1103 - assertIsFalse "local.part.ends.with.empty.string2a\"\"b@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1104 - assertIsFalse "local.part.with.empty.string2a\"\"bcharacter@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1105 - assertIsFalse "local.part.with.empty.string2.beforea\"\"b.point@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1106 - assertIsFalse "local.part.with.empty.string2.after.a\"\"bpoint@domain.com"                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1107 - assertIsFalse "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com"            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1108 - assertIsFalse "(comment a\"\"b) local.part.with.empty.string2.in.comment@domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1109 - assertIsFalse "\"stringa\"\"b\".local.part.with.empty.string2.in.String@domain.com"        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1110 - assertIsFalse "\"string\a\"\"b\".local.part.with.escaped.empty.string2.in.String@domain.com" =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1111 - assertIsFalse "a\"\"b@local.part.only.empty.string2.domain.com"                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1112 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@local.part.only.consecutive.empty.string2.domain.com" =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1113 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1114 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2.in.display.name@domain.com>" =   0 =  OK 
     *  1115 - assertIsTrue  "<pointy.brackets2.with.empty.string2.in.display.name@domain.com> name a\"\"b" =   0 =  OK 
     *  1116 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1117 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1118 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1119 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1120 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1121 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1122 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1123 - assertIsFalse "domain.part.with.empty.string2.in.comment@(comment a\"\"b)domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1124 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1125 - assertIsFalse "top.level.domain.only@empty.string2.a\"\"b"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1126 - assertIsFalse "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com"            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1127 - assertIsFalse "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1128 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"character@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1129 - assertIsFalse "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com"       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1130 - assertIsFalse "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com"        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1131 - assertIsFalse "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1132 - assertIsFalse "(comment \"\"\"\") local.part.with.double.empty.string1.in.comment@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1133 - assertIsFalse "\"string\"\"\"\"\".local.part.with.double.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1134 - assertIsFalse "\"string\\"\"\"\"\".local.part.with.escaped.double.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1135 - assertIsFalse "\"\"\"\"@local.part.only.double.empty.string1.domain.com"                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1136 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1137 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1138 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1.in.display.name@domain.com>" =   0 =  OK 
     *  1139 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1.in.display.name@domain.com> name \"\"\"\"" =   0 =  OK 
     *  1140 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1141 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1142 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1143 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1144 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1145 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1146 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com"  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1147 - assertIsFalse "domain.part.with.double.empty.string1.in.comment@(comment \"\"\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1148 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1149 - assertIsFalse "top.level.domain.only@double.empty.string1.\"\"\"\""                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1150 - assertIsFalse "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com"           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1151 - assertIsFalse "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1152 - assertIsFalse "local.part.with.double.empty.string2\"\".\"\"character@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1153 - assertIsFalse "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com"      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1154 - assertIsFalse "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com"       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1155 - assertIsFalse "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1156 - assertIsFalse "(comment \"\".\"\") local.part.with.double.empty.string2.in.comment@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1157 - assertIsFalse "\"string\"\".\"\"\".local.part.with.double.empty.string2.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1158 - assertIsFalse "\"string\\"\".\"\"\".local.part.with.escaped.double.empty.string2.in.String@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1159 - assertIsFalse "\"\".\"\"@local.part.only.double.empty.string2.domain.com"                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1160 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\"@local.part.only.consecutive.double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1161 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com"         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1162 - assertIsFalse "name \"\".\"\" <pointy.brackets1.with.double.empty.string2.in.display.name@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1163 - assertIsFalse "<pointy.brackets2.with.double.empty.string2.in.display.name@domain.com> name \"\".\"\"" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1164 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1165 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1166 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1167 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1168 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1169 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1170 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1171 - assertIsFalse "domain.part.with.double.empty.string2.in.comment@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1172 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1173 - assertIsFalse "top.level.domain.only@double.empty.string2.\"\".\"\""                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1174 - assertIsTrue  "0.local.part.starts.with.number0@domain.com"                                =   0 =  OK 
     *  1175 - assertIsTrue  "local.part.ends.with.number00@domain.com"                                   =   0 =  OK 
     *  1176 - assertIsTrue  "local.part.with.number00character@domain.com"                               =   0 =  OK 
     *  1177 - assertIsTrue  "local.part.with.number0.before0.point@domain.com"                           =   0 =  OK 
     *  1178 - assertIsTrue  "local.part.with.number0.after.0point@domain.com"                            =   0 =  OK 
     *  1179 - assertIsTrue  "local.part.with.double.number000test@domain.com"                            =   0 =  OK 
     *  1180 - assertIsTrue  "(comment 0) local.part.with.number0.in.comment@domain.com"                  =   6 =  OK 
     *  1181 - assertIsTrue  "\"string0\".local.part.with.number0.in.String@domain.com"                   =   1 =  OK 
     *  1182 - assertIsFalse "\"string\0\".local.part.with.escaped.number0.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1183 - assertIsTrue  "0@local.part.only.number0.domain.com"                                       =   0 =  OK 
     *  1184 - assertIsTrue  "000000@local.part.only.consecutive.number0.domain.com"                      =   0 =  OK 
     *  1185 - assertIsTrue  "0.0.0.0.0.0@number0.domain.com"                                             =   0 =  OK 
     *  1186 - assertIsTrue  "name 0 <pointy.brackets1.with.number0.in.display.name@domain.com>"          =   0 =  OK 
     *  1187 - assertIsTrue  "<pointy.brackets2.with.number0.in.display.name@domain.com> name 0"          =   0 =  OK 
     *  1188 - assertIsTrue  "domain.part@with0number0.com"                                               =   0 =  OK 
     *  1189 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                              =   0 =  OK 
     *  1190 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                               =   0 =  OK 
     *  1191 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                               =   0 =  OK 
     *  1192 - assertIsTrue  "domain.part@with.number0.before0.point.com"                                 =   0 =  OK 
     *  1193 - assertIsTrue  "domain.part@with.number0.after.0point.com"                                  =   0 =  OK 
     *  1194 - assertIsTrue  "domain.part@with.consecutive.number000test.com"                             =   0 =  OK 
     *  1195 - assertIsTrue  "domain.part.with.number0.in.comment@(comment 0)domain.com"                  =   6 =  OK 
     *  1196 - assertIsTrue  "domain.part.only.number0@0.com"                                             =   0 =  OK 
     *  1197 - assertIsFalse "top.level.domain.only@number0.0"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1198 - assertIsTrue  "9.local.part.starts.with.number9@domain.com"                                =   0 =  OK 
     *  1199 - assertIsTrue  "local.part.ends.with.number99@domain.com"                                   =   0 =  OK 
     *  1200 - assertIsTrue  "local.part.with.number99character@domain.com"                               =   0 =  OK 
     *  1201 - assertIsTrue  "local.part.with.number9.before9.point@domain.com"                           =   0 =  OK 
     *  1202 - assertIsTrue  "local.part.with.number9.after.9point@domain.com"                            =   0 =  OK 
     *  1203 - assertIsTrue  "local.part.with.double.number999test@domain.com"                            =   0 =  OK 
     *  1204 - assertIsTrue  "(comment 9) local.part.with.number9.in.comment@domain.com"                  =   6 =  OK 
     *  1205 - assertIsTrue  "\"string9\".local.part.with.number9.in.String@domain.com"                   =   1 =  OK 
     *  1206 - assertIsFalse "\"string\9\".local.part.with.escaped.number9.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1207 - assertIsTrue  "9@local.part.only.number9.domain.com"                                       =   0 =  OK 
     *  1208 - assertIsTrue  "999999@local.part.only.consecutive.number9.domain.com"                      =   0 =  OK 
     *  1209 - assertIsTrue  "9.9.9.9.9.9@number9.domain.com"                                             =   0 =  OK 
     *  1210 - assertIsTrue  "name 9 <pointy.brackets1.with.number9.in.display.name@domain.com>"          =   0 =  OK 
     *  1211 - assertIsTrue  "<pointy.brackets2.with.number9.in.display.name@domain.com> name 9"          =   0 =  OK 
     *  1212 - assertIsTrue  "domain.part@with9number9.com"                                               =   0 =  OK 
     *  1213 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                              =   0 =  OK 
     *  1214 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                               =   0 =  OK 
     *  1215 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                               =   0 =  OK 
     *  1216 - assertIsTrue  "domain.part@with.number9.before9.point.com"                                 =   0 =  OK 
     *  1217 - assertIsTrue  "domain.part@with.number9.after.9point.com"                                  =   0 =  OK 
     *  1218 - assertIsTrue  "domain.part@with.consecutive.number999test.com"                             =   0 =  OK 
     *  1219 - assertIsTrue  "domain.part.with.number9.in.comment@(comment 9)domain.com"                  =   6 =  OK 
     *  1220 - assertIsTrue  "domain.part.only.number9@9.com"                                             =   0 =  OK 
     *  1221 - assertIsFalse "top.level.domain.only@number9.9"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1222 - assertIsTrue  "0123456789.local.part.starts.with.numbers@domain.com"                       =   0 =  OK 
     *  1223 - assertIsTrue  "local.part.ends.with.numbers0123456789@domain.com"                          =   0 =  OK 
     *  1224 - assertIsTrue  "local.part.with.numbers0123456789character@domain.com"                      =   0 =  OK 
     *  1225 - assertIsTrue  "local.part.with.numbers.before0123456789.point@domain.com"                  =   0 =  OK 
     *  1226 - assertIsTrue  "local.part.with.numbers.after.0123456789point@domain.com"                   =   0 =  OK 
     *  1227 - assertIsTrue  "local.part.with.double.numbers01234567890123456789test@domain.com"          =   0 =  OK 
     *  1228 - assertIsTrue  "(comment 0123456789) local.part.with.numbers.in.comment@domain.com"         =   6 =  OK 
     *  1229 - assertIsTrue  "\"string0123456789\".local.part.with.numbers.in.String@domain.com"          =   1 =  OK 
     *  1230 - assertIsFalse "\"string\0123456789\".local.part.with.escaped.numbers.in.String@domain.com" =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1231 - assertIsTrue  "0123456789@local.part.only.numbers.domain.com"                              =   0 =  OK 
     *  1232 - assertIsTrue  "01234567890123@local.part.only.consecutive.numbers.domain.com"              =   0 =  OK 
     *  1233 - assertIsTrue  "0123456789.0123456789.0123456789@numbers.domain.com"                        =   0 =  OK 
     *  1234 - assertIsTrue  "name 0123456789 <pointy.brackets1.with.numbers.in.display.name@domain.com>" =   0 =  OK 
     *  1235 - assertIsTrue  "<pointy.brackets2.with.numbers.in.display.name@domain.com> name 0123456789" =   0 =  OK 
     *  1236 - assertIsTrue  "domain.part@with0123456789numbers.com"                                      =   0 =  OK 
     *  1237 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"                     =   0 =  OK 
     *  1238 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                      =   0 =  OK 
     *  1239 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                      =   0 =  OK 
     *  1240 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                        =   0 =  OK 
     *  1241 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                         =   0 =  OK 
     *  1242 - assertIsTrue  "domain.part@with.consecutive.numbers01234567890123456789test.com"           =   0 =  OK 
     *  1243 - assertIsTrue  "domain.part.with.numbers.in.comment@(comment 0123456789)domain.com"         =   6 =  OK 
     *  1244 - assertIsTrue  "domain.part.only.numbers@0123456789.com"                                    =   0 =  OK 
     *  1245 - assertIsFalse "top.level.domain.only@numbers.0123456789"                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1246 - assertIsFalse "\.local.part.starts.with.slash@domain.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1247 - assertIsFalse "local.part.ends.with.slash\@domain.com"                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1248 - assertIsFalse "local.part.with.slash\character@domain.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1249 - assertIsFalse "local.part.with.slash.before\.point@domain.com"                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1250 - assertIsFalse "local.part.with.slash.after.\point@domain.com"                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1251 - assertIsTrue  "local.part.with.double.slash\\test@domain.com"                              =   0 =  OK 
     *  1252 - assertIsFalse "(comment \) local.part.with.slash.in.comment@domain.com"                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1253 - assertIsFalse "\"string\\".local.part.with.slash.in.String@domain.com"                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1254 - assertIsTrue  "\"string\\\".local.part.with.escaped.slash.in.String@domain.com"            =   1 =  OK 
     *  1255 - assertIsFalse "\@local.part.only.slash.domain.com"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1256 - assertIsTrue  "\\\\\\@local.part.only.consecutive.slash.domain.com"                        =   0 =  OK 
     *  1257 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1258 - assertIsTrue  "escaped character is space \ <pointy.brackets1.with.slash.in.display.name@domain.com>" =   0 =  OK 
     *  1259 - assertIsFalse "no escaped character \<pointy.brackets1.with.slash.in.display.name@domain.com>" =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
     *  1260 - assertIsFalse "<pointy.brackets2.with.slash.in.display.name@domain.com> name \"            =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
     *  1261 - assertIsFalse "domain.part@with\slash.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1262 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1263 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1264 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1265 - assertIsFalse "domain.part@with.slash.before\.point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1266 - assertIsFalse "domain.part@with.slash.after.\point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1267 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1268 - assertIsFalse "domain.part.with.slash.in.comment@(comment \)domain.com"                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1269 - assertIsFalse "domain.part.only.slash@\.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1270 - assertIsFalse "top.level.domain.only@slash.\"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1271 - assertIsTrue  "\"str\".local.part.starts.with.string@domain.com"                           =   1 =  OK 
     *  1272 - assertIsFalse "local.part.ends.with.string\"str\"@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1273 - assertIsFalse "local.part.with.string\"str\"character@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1274 - assertIsFalse "local.part.with.string.before\"str\".point@domain.com"                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1275 - assertIsFalse "local.part.with.string.after.\"str\"point@domain.com"                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1276 - assertIsFalse "local.part.with.double.string\"str\"\"str\"test@domain.com"                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1277 - assertIsFalse "(comment \"str\") local.part.with.string.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1278 - assertIsFalse "\"string\"str\"\".local.part.with.string.in.String@domain.com"              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1279 - assertIsFalse "\"string\\"str\"\".local.part.with.escaped.string.in.String@domain.com"     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1280 - assertIsTrue  "\"str\"@local.part.only.string.domain.com"                                  =   1 =  OK 
     *  1281 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@local.part.only.consecutive.string.domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1282 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"          =   1 =  OK 
     *  1283 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string.in.display.name@domain.com>"     =   0 =  OK 
     *  1284 - assertIsTrue  "<pointy.brackets2.with.string.in.display.name@domain.com> name \"str\""     =   0 =  OK 
     *  1285 - assertIsFalse "domain.part@with\"str\"string.com"                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1286 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1287 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1288 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1289 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1290 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1291 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1292 - assertIsFalse "domain.part.with.string.in.comment@(comment \"str\")domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1293 - assertIsFalse "domain.part.only.string@\"str\".com"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1294 - assertIsFalse "top.level.domain.only@string.\"str\""                                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1295 - assertIsFalse "(comment).local.part.starts.with.comment@domain.com"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1296 - assertIsTrue  "local.part.ends.with.comment(comment)@domain.com"                           =   6 =  OK 
     *  1297 - assertIsFalse "local.part.with.comment(comment)character@domain.com"                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1298 - assertIsFalse "local.part.with.comment.before(comment).point@domain.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1299 - assertIsFalse "local.part.with.comment.after.(comment)point@domain.com"                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1300 - assertIsFalse "local.part.with.double.comment(comment)(comment)test@domain.com"            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1301 - assertIsFalse "(comment (comment)) local.part.with.comment.in.comment@domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1302 - assertIsTrue  "\"string(comment)\".local.part.with.comment.in.String@domain.com"           =   1 =  OK 
     *  1303 - assertIsFalse "\"string\(comment)\".local.part.with.escaped.comment.in.String@domain.com"  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1304 - assertIsFalse "(comment)@local.part.only.comment.domain.com"                               =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1305 - assertIsFalse "(comment)(comment)(comment)@local.part.only.consecutive.comment.domain.com" =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1306 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1307 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment.in.display.name@domain.com>"  =   0 =  OK 
     *  1308 - assertIsTrue  "<pointy.brackets2.with.comment.in.display.name@domain.com> name (comment)"  =   0 =  OK 
     *  1309 - assertIsFalse "domain.part@with(comment)comment.com"                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1310 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                      =   6 =  OK 
     *  1311 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1312 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                       =   6 =  OK 
     *  1313 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1314 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                          = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1315 - assertIsFalse "domain.part@with.consecutive.comment(comment)(comment)test.com"             = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1316 - assertIsFalse "domain.part.with.comment.in.comment@(comment (comment))domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1317 - assertIsFalse "domain.part.only.comment@(comment).com"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1318 - assertIsFalse "top.level.domain.only@comment.(comment)"                                    = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     * 
     * ---- IP V4 -----------------------------------------------------------------------------------------------------------------------
     * 
     *  1319 - assertIsFalse "\"\"@[]"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1320 - assertIsFalse "\"\"@[1"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1321 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})"   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1322 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1323 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1324 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1325 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1326 - assertIsFalse "1.2.3.4]@[5.6.7.8]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1327 - assertIsFalse "[1.2.3.4@[5.6.7.8]"                                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1328 - assertIsFalse "[1.2.3.4][5.6.7.8]@[9.10.11.12]"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1329 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12]"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1330 - assertIsFalse "[1.2.3.4]@[5.6.7.8]9.10.11.12]"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1331 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12["                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1332 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1333 - assertIsTrue  "ip4.in.local.part.as.string1.\"[1.2.3.4]\"@[5.6.7.8]"                       =   3 =  OK 
     *  1334 - assertIsTrue  "ip4.in.local.part.as.string2.\"@[1.2.3.4]\"@[5.6.7.8]"                      =   3 =  OK 
     *  1335 - assertIsFalse "ip4.ends.with.alpha.character1@[1.2.3.Z]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1336 - assertIsFalse "ip4.ends.with.alpha.character2@[1.2.3.]Z"                                   =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1337 - assertIsFalse "ip4.ends.with.top.level.domain@[1.2.3.].de"                                 =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1338 - assertIsFalse "ip4.with.double.ip4@[1.2.3.4][5.6.7.8]"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1339 - assertIsFalse "ip4.with.ip4.in.comment1@([1.2.3.4])"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1340 - assertIsFalse "ip4.with.ip4.in.comment2@([1.2.3.4])[5.6.7.8]"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1341 - assertIsFalse "ip4.with.ip4.in.comment3@[1.2.3.4]([5.6.7.8])"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1342 - assertIsTrue  "ip4.with.ip4.in.comment4@[1.2.3.4] (@)"                                     =   2 =  OK 
     *  1343 - assertIsTrue  "ip4.with.ip4.in.comment5@[1.2.3.4] (@.)"                                    =   2 =  OK 
     *  1344 - assertIsFalse "ip4.with.hex.numbers@[AB.CD.EF.EA]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1345 - assertIsFalse "ip4.with.hex.number.overflow@[AB.CD.EF.FF1]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1346 - assertIsFalse "ip4.with.double.brackets@[1.2.3.4][5.6.7.8]"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1347 - assertIsFalse "ip4.missing.at.sign[1.2.3.4]"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1348 - assertIsFalse "ip4.missing.the.start.bracket@]"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1349 - assertIsFalse "ip4.missing.the.end.bracket@["                                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1350 - assertIsFalse "ip4.missing.the.start.bracket@1.2.3.4]"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1351 - assertIsFalse "ip4.missing.the.end.bracket@[1.2.3.4"                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1352 - assertIsFalse "ip4.missing.numbers.and.the.start.bracket@...]"                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1353 - assertIsFalse "ip4.missing.numbers.and.the.end.bracket@[..."                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1354 - assertIsFalse "ip4.missplaced.start.bracket1[@1.2.3.4]"                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1355 - assertIsFalse "ip4.missing.the.first.number@[.2.3.4]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1356 - assertIsFalse "ip4.missing.the.last.number@[1.2.3.]"                                       =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1357 - assertIsFalse "ip4.last.number.is.space@[1.2.3. ]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1358 - assertIsFalse "ip4.with.only.one.numberABC.DEF@[1]"                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1359 - assertIsFalse "ip4.with.only.two.numbers@[1.2]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1360 - assertIsFalse "ip4.with.only.three.numbers@[1.2.3]"                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1361 - assertIsFalse "ip4.with.five.numbers@[1.2.3.4.5]"                                          =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1362 - assertIsFalse "ip4.with.six.numbers@[1.2.3.4.5.6]"                                         =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1363 - assertIsFalse "ip4.with.byte.overflow1@[1.2.3.256]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1364 - assertIsFalse "ip4.with.byte.overflow2@[1.2.3.1000]"                                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1365 - assertIsFalse "ip4.with.to.many.leading.zeros@[0001.000002.000003.00000004]"               =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1366 - assertIsTrue  "ip4.with.two.leading.zeros@[001.002.003.004]"                               =   2 =  OK 
     *  1367 - assertIsTrue  "ip4.zero@[0.0.0.0]"                                                         =   2 =  OK 
     *  1368 - assertIsTrue  "ip4.correct1@[1.2.3.4]"                                                     =   2 =  OK 
     *  1369 - assertIsTrue  "ip4.correct2@[255.255.255.255]"                                             =   2 =  OK 
     *  1370 - assertIsTrue  "\"ip4.local.part.as.string\"@[127.0.0.1]"                                   =   3 =  OK 
     *  1371 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                         =   3 =  OK 
     *  1372 - assertIsFalse "ip4.with.negative.number1@[-1.2.3.4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1373 - assertIsFalse "ip4.with.negative.number2@[1.-2.3.4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1374 - assertIsFalse "ip4.with.negative.number3@[1.2.-3.4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1375 - assertIsFalse "ip4.with.negative.number4@[1.2.3.-4]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1376 - assertIsFalse "ip4.with.only.empty.brackets@[]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1377 - assertIsFalse "ip4.with.three.empty.brackets@[][][]"                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1378 - assertIsFalse "ip4.with.only.one.dot.in.brackets@[.]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1379 - assertIsFalse "ip4.with.only.double.dot.in.brackets@[..]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1380 - assertIsFalse "ip4.with.only.triple.dot.in.brackets@[...]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1381 - assertIsFalse "ip4.with.only.four.dots.in.brackets@[....]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1382 - assertIsFalse "ip4.with.dot.between.numbers@[123.14.5.178.90]"                             =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1383 - assertIsFalse "ip4.with.dot.before.point@[123.145..178.90]"                                =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1384 - assertIsFalse "ip4.with.dot.after.point@[123.145..178.90]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1385 - assertIsFalse "ip4.with.dot.before.start.bracket@.[123.145.178.90]"                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1386 - assertIsFalse "ip4.with.dot.after.start.bracket@[.123.145.178.90]"                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1387 - assertIsFalse "ip4.with.dot.before.end.bracket@[123.145.178.90.]"                          =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1388 - assertIsFalse "ip4.with.dot.after.end.bracket@[123.145.178.90]."                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1389 - assertIsFalse "ip4.with.double.dot.between.numbers@[123.14..5.178.90]"                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1390 - assertIsFalse "ip4.with.double.dot.before.point@[123.145...178.90]"                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1391 - assertIsFalse "ip4.with.double.dot.after.point@[123.145...178.90]"                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1392 - assertIsFalse "ip4.with.double.dot.before.start.bracket@..[123.145.178.90]"                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1393 - assertIsFalse "ip4.with.double.dot.after.start.bracket@[..123.145.178.90]"                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1394 - assertIsFalse "ip4.with.double.dot.before.end.bracket@[123.145.178.90..]"                  =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1395 - assertIsFalse "ip4.with.double.dot.after.end.bracket@[123.145.178.90].."                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1396 - assertIsFalse "ip4.with.amp.between.numbers@[123.14&5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1397 - assertIsFalse "ip4.with.amp.before.point@[123.145&.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1398 - assertIsFalse "ip4.with.amp.after.point@[123.145.&178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1399 - assertIsFalse "ip4.with.amp.before.start.bracket@&[123.145.178.90]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1400 - assertIsFalse "ip4.with.amp.after.start.bracket@[&123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1401 - assertIsFalse "ip4.with.amp.before.end.bracket@[123.145.178.90&]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1402 - assertIsFalse "ip4.with.amp.after.end.bracket@[123.145.178.90]&"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1403 - assertIsFalse "ip4.with.asterisk.between.numbers@[123.14*5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1404 - assertIsFalse "ip4.with.asterisk.before.point@[123.145*.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1405 - assertIsFalse "ip4.with.asterisk.after.point@[123.145.*178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1406 - assertIsFalse "ip4.with.asterisk.before.start.bracket@*[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1407 - assertIsFalse "ip4.with.asterisk.after.start.bracket@[*123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1408 - assertIsFalse "ip4.with.asterisk.before.end.bracket@[123.145.178.90*]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1409 - assertIsFalse "ip4.with.asterisk.after.end.bracket@[123.145.178.90]*"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1410 - assertIsFalse "ip4.with.underscore.between.numbers@[123.14_5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1411 - assertIsFalse "ip4.with.underscore.before.point@[123.145_.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1412 - assertIsFalse "ip4.with.underscore.after.point@[123.145._178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1413 - assertIsFalse "ip4.with.underscore.before.start.bracket@_[123.145.178.90]"                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1414 - assertIsFalse "ip4.with.underscore.after.start.bracket@[_123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1415 - assertIsFalse "ip4.with.underscore.before.end.bracket@[123.145.178.90_]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1416 - assertIsFalse "ip4.with.underscore.after.end.bracket@[123.145.178.90]_"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1417 - assertIsFalse "ip4.with.dollar.between.numbers@[123.14$5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1418 - assertIsFalse "ip4.with.dollar.before.point@[123.145$.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1419 - assertIsFalse "ip4.with.dollar.after.point@[123.145.$178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1420 - assertIsFalse "ip4.with.dollar.before.start.bracket@$[123.145.178.90]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1421 - assertIsFalse "ip4.with.dollar.after.start.bracket@[$123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1422 - assertIsFalse "ip4.with.dollar.before.end.bracket@[123.145.178.90$]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1423 - assertIsFalse "ip4.with.dollar.after.end.bracket@[123.145.178.90]$"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1424 - assertIsFalse "ip4.with.equality.between.numbers@[123.14=5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1425 - assertIsFalse "ip4.with.equality.before.point@[123.145=.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1426 - assertIsFalse "ip4.with.equality.after.point@[123.145.=178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1427 - assertIsFalse "ip4.with.equality.before.start.bracket@=[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1428 - assertIsFalse "ip4.with.equality.after.start.bracket@[=123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1429 - assertIsFalse "ip4.with.equality.before.end.bracket@[123.145.178.90=]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1430 - assertIsFalse "ip4.with.equality.after.end.bracket@[123.145.178.90]="                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1431 - assertIsFalse "ip4.with.exclamation.between.numbers@[123.14!5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1432 - assertIsFalse "ip4.with.exclamation.before.point@[123.145!.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1433 - assertIsFalse "ip4.with.exclamation.after.point@[123.145.!178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1434 - assertIsFalse "ip4.with.exclamation.before.start.bracket@![123.145.178.90]"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1435 - assertIsFalse "ip4.with.exclamation.after.start.bracket@[!123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1436 - assertIsFalse "ip4.with.exclamation.before.end.bracket@[123.145.178.90!]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1437 - assertIsFalse "ip4.with.exclamation.after.end.bracket@[123.145.178.90]!"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1438 - assertIsFalse "ip4.with.question.between.numbers@[123.14?5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1439 - assertIsFalse "ip4.with.question.before.point@[123.145?.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1440 - assertIsFalse "ip4.with.question.after.point@[123.145.?178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1441 - assertIsFalse "ip4.with.question.before.start.bracket@?[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1442 - assertIsFalse "ip4.with.question.after.start.bracket@[?123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1443 - assertIsFalse "ip4.with.question.before.end.bracket@[123.145.178.90?]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1444 - assertIsFalse "ip4.with.question.after.end.bracket@[123.145.178.90]?"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1445 - assertIsFalse "ip4.with.grave-accent.between.numbers@[123.14`5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1446 - assertIsFalse "ip4.with.grave-accent.before.point@[123.145`.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1447 - assertIsFalse "ip4.with.grave-accent.after.point@[123.145.`178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1448 - assertIsFalse "ip4.with.grave-accent.before.start.bracket@`[123.145.178.90]"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1449 - assertIsFalse "ip4.with.grave-accent.after.start.bracket@[`123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1450 - assertIsFalse "ip4.with.grave-accent.before.end.bracket@[123.145.178.90`]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1451 - assertIsFalse "ip4.with.grave-accent.after.end.bracket@[123.145.178.90]`"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1452 - assertIsFalse "ip4.with.hash.between.numbers@[123.14#5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1453 - assertIsFalse "ip4.with.hash.before.point@[123.145#.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1454 - assertIsFalse "ip4.with.hash.after.point@[123.145.#178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1455 - assertIsFalse "ip4.with.hash.before.start.bracket@#[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1456 - assertIsFalse "ip4.with.hash.after.start.bracket@[#123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1457 - assertIsFalse "ip4.with.hash.before.end.bracket@[123.145.178.90#]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1458 - assertIsFalse "ip4.with.hash.after.end.bracket@[123.145.178.90]#"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1459 - assertIsFalse "ip4.with.percentage.between.numbers@[123.14%5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1460 - assertIsFalse "ip4.with.percentage.before.point@[123.145%.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1461 - assertIsFalse "ip4.with.percentage.after.point@[123.145.%178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1462 - assertIsFalse "ip4.with.percentage.before.start.bracket@%[123.145.178.90]"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1463 - assertIsFalse "ip4.with.percentage.after.start.bracket@[%123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1464 - assertIsFalse "ip4.with.percentage.before.end.bracket@[123.145.178.90%]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1465 - assertIsFalse "ip4.with.percentage.after.end.bracket@[123.145.178.90]%"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1466 - assertIsFalse "ip4.with.pipe.between.numbers@[123.14|5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1467 - assertIsFalse "ip4.with.pipe.before.point@[123.145|.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1468 - assertIsFalse "ip4.with.pipe.after.point@[123.145.|178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1469 - assertIsFalse "ip4.with.pipe.before.start.bracket@|[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1470 - assertIsFalse "ip4.with.pipe.after.start.bracket@[|123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ip4.with.pipe.before.end.bracket@[123.145.178.90|]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1472 - assertIsFalse "ip4.with.pipe.after.end.bracket@[123.145.178.90]|"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1473 - assertIsFalse "ip4.with.plus.between.numbers@[123.14+5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1474 - assertIsFalse "ip4.with.plus.before.point@[123.145+.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1475 - assertIsFalse "ip4.with.plus.after.point@[123.145.+178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1476 - assertIsFalse "ip4.with.plus.before.start.bracket@+[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1477 - assertIsFalse "ip4.with.plus.after.start.bracket@[+123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1478 - assertIsFalse "ip4.with.plus.before.end.bracket@[123.145.178.90+]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1479 - assertIsFalse "ip4.with.plus.after.end.bracket@[123.145.178.90]+"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1480 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14{5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1481 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145{.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1482 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1483 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@{[123.145.178.90]"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1484 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[{123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1485 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90{]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1486 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]{"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1487 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14}5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1488 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145}.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1489 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.}178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1490 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@}[123.145.178.90]"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1491 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[}123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1492 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90}]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1493 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]}"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1494 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14(5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1495 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145(.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1496 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1497 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@([123.145.178.90]"                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1498 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[(123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1499 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90(]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1500 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]("                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1501 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14)5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1502 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145).178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1503 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.)178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1504 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@)[123.145.178.90]"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1505 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[)123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90)]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1507 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90])"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1508 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14[5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1509 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145[.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1510 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.[178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1511 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@[[123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1512 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[[123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1513 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90[]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1514 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]["                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1515 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14]5.178.90]"                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1516 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145].178.90]"                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1517 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.]178.90]"                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1518 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@][123.145.178.90]"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1519 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[]123.145.178.90]"                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1520 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90]]"                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1521 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]]"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1522 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14()5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1523 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145().178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1524 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.()178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1525 - assertIsTrue  "ip4.with.empty.bracket.before.start.bracket@()[123.145.178.90]"             =   2 =  OK 
     *  1526 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[()123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1527 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90()]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1528 - assertIsTrue  "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]()"                =   2 =  OK 
     *  1529 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14{}5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1530 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145{}.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1531 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.{}178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1532 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@{}[123.145.178.90]"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1533 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[{}123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1534 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90{}]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1535 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]{}"                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1536 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14[]5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1537 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145[].178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1538 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.[]178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1539 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@[][123.145.178.90]"             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1540 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[[]123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1541 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90[]]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1542 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90][]"                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1543 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14<>5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1544 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145<>.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1545 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.<>178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1546 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@<>[123.145.178.90]"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1547 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[<>123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1548 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90<>]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1549 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]<>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1550 - assertIsFalse "ip4.with.false.bracket1.between.numbers@[123.14)(5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1551 - assertIsFalse "ip4.with.false.bracket1.before.point@[123.145)(.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1552 - assertIsFalse "ip4.with.false.bracket1.after.point@[123.145.)(178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1553 - assertIsFalse "ip4.with.false.bracket1.before.start.bracket@)([123.145.178.90]"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1554 - assertIsFalse "ip4.with.false.bracket1.after.start.bracket@[)(123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1555 - assertIsFalse "ip4.with.false.bracket1.before.end.bracket@[123.145.178.90)(]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1556 - assertIsFalse "ip4.with.false.bracket1.after.end.bracket@[123.145.178.90])("               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1557 - assertIsFalse "ip4.with.false.bracket2.between.numbers@[123.14}{5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1558 - assertIsFalse "ip4.with.false.bracket2.before.point@[123.145}{.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1559 - assertIsFalse "ip4.with.false.bracket2.after.point@[123.145.}{178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1560 - assertIsFalse "ip4.with.false.bracket2.before.start.bracket@}{[123.145.178.90]"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1561 - assertIsFalse "ip4.with.false.bracket2.after.start.bracket@[}{123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1562 - assertIsFalse "ip4.with.false.bracket2.before.end.bracket@[123.145.178.90}{]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1563 - assertIsFalse "ip4.with.false.bracket2.after.end.bracket@[123.145.178.90]}{"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1564 - assertIsFalse "ip4.with.false.bracket4.between.numbers@[123.14><5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1565 - assertIsFalse "ip4.with.false.bracket4.before.point@[123.145><.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip4.with.false.bracket4.after.point@[123.145.><178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1567 - assertIsFalse "ip4.with.false.bracket4.before.start.bracket@><[123.145.178.90]"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1568 - assertIsFalse "ip4.with.false.bracket4.after.start.bracket@[><123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip4.with.false.bracket4.before.end.bracket@[123.145.178.90><]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1570 - assertIsFalse "ip4.with.false.bracket4.after.end.bracket@[123.145.178.90]><"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1571 - assertIsFalse "ip4.with.lower.than.between.numbers@[123.14<5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip4.with.lower.than.before.point@[123.145<.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1573 - assertIsFalse "ip4.with.lower.than.after.point@[123.145.<178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1574 - assertIsFalse "ip4.with.lower.than.before.start.bracket@<[123.145.178.90]"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1575 - assertIsFalse "ip4.with.lower.than.after.start.bracket@[<123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1576 - assertIsFalse "ip4.with.lower.than.before.end.bracket@[123.145.178.90<]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1577 - assertIsFalse "ip4.with.lower.than.after.end.bracket@[123.145.178.90]<"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1578 - assertIsFalse "ip4.with.greater.than.between.numbers@[123.14>5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1579 - assertIsFalse "ip4.with.greater.than.before.point@[123.145>.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1580 - assertIsFalse "ip4.with.greater.than.after.point@[123.145.>178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1581 - assertIsFalse "ip4.with.greater.than.before.start.bracket@>[123.145.178.90]"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1582 - assertIsFalse "ip4.with.greater.than.after.start.bracket@[>123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1583 - assertIsFalse "ip4.with.greater.than.before.end.bracket@[123.145.178.90>]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1584 - assertIsFalse "ip4.with.greater.than.after.end.bracket@[123.145.178.90]>"                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1585 - assertIsFalse "ip4.with.tilde.between.numbers@[123.14~5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1586 - assertIsFalse "ip4.with.tilde.before.point@[123.145~.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1587 - assertIsFalse "ip4.with.tilde.after.point@[123.145.~178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1588 - assertIsFalse "ip4.with.tilde.before.start.bracket@~[123.145.178.90]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1589 - assertIsFalse "ip4.with.tilde.after.start.bracket@[~123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1590 - assertIsFalse "ip4.with.tilde.before.end.bracket@[123.145.178.90~]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1591 - assertIsFalse "ip4.with.tilde.after.end.bracket@[123.145.178.90]~"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1592 - assertIsFalse "ip4.with.xor.between.numbers@[123.14^5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsFalse "ip4.with.xor.before.point@[123.145^.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1594 - assertIsFalse "ip4.with.xor.after.point@[123.145.^178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1595 - assertIsFalse "ip4.with.xor.before.start.bracket@^[123.145.178.90]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1596 - assertIsFalse "ip4.with.xor.after.start.bracket@[^123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1597 - assertIsFalse "ip4.with.xor.before.end.bracket@[123.145.178.90^]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1598 - assertIsFalse "ip4.with.xor.after.end.bracket@[123.145.178.90]^"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1599 - assertIsFalse "ip4.with.colon.between.numbers@[123.14:5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1600 - assertIsFalse "ip4.with.colon.before.point@[123.145:.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1601 - assertIsFalse "ip4.with.colon.after.point@[123.145.:178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1602 - assertIsFalse "ip4.with.colon.before.start.bracket@:[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1603 - assertIsFalse "ip4.with.colon.after.start.bracket@[:123.145.178.90]"                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1604 - assertIsFalse "ip4.with.colon.before.end.bracket@[123.145.178.90:]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1605 - assertIsFalse "ip4.with.colon.after.end.bracket@[123.145.178.90]:"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1606 - assertIsFalse "ip4.with.space.between.numbers@[123.14 5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1607 - assertIsFalse "ip4.with.space.before.point@[123.145 .178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1608 - assertIsFalse "ip4.with.space.after.point@[123.145. 178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1609 - assertIsFalse "ip4.with.space.before.start.bracket@ [123.145.178.90]"                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1610 - assertIsFalse "ip4.with.space.after.start.bracket@[ 123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1611 - assertIsFalse "ip4.with.space.before.end.bracket@[123.145.178.90 ]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1612 - assertIsFalse "ip4.with.space.after.end.bracket@[123.145.178.90] "                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1613 - assertIsFalse "ip4.with.comma.between.numbers@[123.14,5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1614 - assertIsFalse "ip4.with.comma.before.point@[123.145,.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1615 - assertIsFalse "ip4.with.comma.after.point@[123.145.,178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1616 - assertIsFalse "ip4.with.comma.before.start.bracket@,[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1617 - assertIsFalse "ip4.with.comma.after.start.bracket@[,123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1618 - assertIsFalse "ip4.with.comma.before.end.bracket@[123.145.178.90,]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1619 - assertIsFalse "ip4.with.comma.after.end.bracket@[123.145.178.90],"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1620 - assertIsFalse "ip4.with.at.between.numbers@[123.14@5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1621 - assertIsFalse "ip4.with.at.before.point@[123.145@.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1622 - assertIsFalse "ip4.with.at.after.point@[123.145.@178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1623 - assertIsFalse "ip4.with.at.before.start.bracket@@[123.145.178.90]"                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1624 - assertIsFalse "ip4.with.at.after.start.bracket@[@123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1625 - assertIsFalse "ip4.with.at.before.end.bracket@[123.145.178.90@]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1626 - assertIsFalse "ip4.with.at.after.end.bracket@[123.145.178.90]@"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1627 - assertIsFalse "ip4.with.paragraph.between.numbers@[123.14§5.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1628 - assertIsFalse "ip4.with.paragraph.before.point@[123.145§.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1629 - assertIsFalse "ip4.with.paragraph.after.point@[123.145.§178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1630 - assertIsFalse "ip4.with.paragraph.before.start.bracket@§[123.145.178.90]"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1631 - assertIsFalse "ip4.with.paragraph.after.start.bracket@[§123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1632 - assertIsFalse "ip4.with.paragraph.before.end.bracket@[123.145.178.90§]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1633 - assertIsFalse "ip4.with.paragraph.after.end.bracket@[123.145.178.90]§"                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1634 - assertIsFalse "ip4.with.double.quote.between.numbers@[123.14'5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1635 - assertIsFalse "ip4.with.double.quote.before.point@[123.145'.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1636 - assertIsFalse "ip4.with.double.quote.after.point@[123.145.'178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1637 - assertIsFalse "ip4.with.double.quote.before.start.bracket@'[123.145.178.90]"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1638 - assertIsFalse "ip4.with.double.quote.after.start.bracket@['123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1639 - assertIsFalse "ip4.with.double.quote.before.end.bracket@[123.145.178.90']"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1640 - assertIsFalse "ip4.with.double.quote.after.end.bracket@[123.145.178.90]'"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1641 - assertIsFalse "ip4.with.forward.slash.between.numbers@[123.14/5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1642 - assertIsFalse "ip4.with.forward.slash.before.point@[123.145/.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1643 - assertIsFalse "ip4.with.forward.slash.after.point@[123.145./178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1644 - assertIsFalse "ip4.with.forward.slash.before.start.bracket@/[123.145.178.90]"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1645 - assertIsFalse "ip4.with.forward.slash.after.start.bracket@[/123.145.178.90]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1646 - assertIsFalse "ip4.with.forward.slash.before.end.bracket@[123.145.178.90/]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1647 - assertIsFalse "ip4.with.forward.slash.after.end.bracket@[123.145.178.90]/"                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1648 - assertIsFalse "ip4.with.hyphen.between.numbers@[123.14-5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1649 - assertIsFalse "ip4.with.hyphen.before.point@[123.145-.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1650 - assertIsFalse "ip4.with.hyphen.after.point@[123.145.-178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1651 - assertIsFalse "ip4.with.hyphen.before.start.bracket@-[123.145.178.90]"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1652 - assertIsFalse "ip4.with.hyphen.after.start.bracket@[-123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1653 - assertIsFalse "ip4.with.hyphen.before.end.bracket@[123.145.178.90-]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1654 - assertIsFalse "ip4.with.hyphen.after.end.bracket@[123.145.178.90]-"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1655 - assertIsFalse "ip4.with.empty.string1.between.numbers@[123.14\"\"5.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1656 - assertIsFalse "ip4.with.empty.string1.before.point@[123.145\"\".178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1657 - assertIsFalse "ip4.with.empty.string1.after.point@[123.145.\"\"178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1658 - assertIsFalse "ip4.with.empty.string1.before.start.bracket@\"\"[123.145.178.90]"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1659 - assertIsFalse "ip4.with.empty.string1.after.start.bracket@[\"\"123.145.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1660 - assertIsFalse "ip4.with.empty.string1.before.end.bracket@[123.145.178.90\"\"]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1661 - assertIsFalse "ip4.with.empty.string1.after.end.bracket@[123.145.178.90]\"\""              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1662 - assertIsFalse "ip4.with.empty.string2.between.numbers@[123.14a\"\"b5.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1663 - assertIsFalse "ip4.with.empty.string2.before.point@[123.145a\"\"b.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1664 - assertIsFalse "ip4.with.empty.string2.after.point@[123.145.a\"\"b178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1665 - assertIsFalse "ip4.with.empty.string2.before.start.bracket@a\"\"b[123.145.178.90]"         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1666 - assertIsFalse "ip4.with.empty.string2.after.start.bracket@[a\"\"b123.145.178.90]"          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1667 - assertIsFalse "ip4.with.empty.string2.before.end.bracket@[123.145.178.90a\"\"b]"           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1668 - assertIsFalse "ip4.with.empty.string2.after.end.bracket@[123.145.178.90]a\"\"b"            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1669 - assertIsFalse "ip4.with.double.empty.string1.between.numbers@[123.14\"\"\"\"5.178.90]"     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1670 - assertIsFalse "ip4.with.double.empty.string1.before.point@[123.145\"\"\"\".178.90]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1671 - assertIsFalse "ip4.with.double.empty.string1.after.point@[123.145.\"\"\"\"178.90]"         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1672 - assertIsFalse "ip4.with.double.empty.string1.before.start.bracket@\"\"\"\"[123.145.178.90]" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1673 - assertIsFalse "ip4.with.double.empty.string1.after.start.bracket@[\"\"\"\"123.145.178.90]" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1674 - assertIsFalse "ip4.with.double.empty.string1.before.end.bracket@[123.145.178.90\"\"\"\"]"  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1675 - assertIsFalse "ip4.with.double.empty.string1.after.end.bracket@[123.145.178.90]\"\"\"\""   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1676 - assertIsFalse "ip4.with.double.empty.string2.between.numbers@[123.14\"\".\"\"5.178.90]"    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1677 - assertIsFalse "ip4.with.double.empty.string2.before.point@[123.145\"\".\"\".178.90]"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1678 - assertIsFalse "ip4.with.double.empty.string2.after.point@[123.145.\"\".\"\"178.90]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1679 - assertIsFalse "ip4.with.double.empty.string2.before.start.bracket@\"\".\"\"[123.145.178.90]" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1680 - assertIsFalse "ip4.with.double.empty.string2.after.start.bracket@[\"\".\"\"123.145.178.90]" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1681 - assertIsFalse "ip4.with.double.empty.string2.before.end.bracket@[123.145.178.90\"\".\"\"]" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1682 - assertIsFalse "ip4.with.double.empty.string2.after.end.bracket@[123.145.178.90]\"\".\"\""  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1683 - assertIsFalse "ip4.with.number0.between.numbers@[123.1405.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1684 - assertIsFalse "ip4.with.number0.before.point@[123.1450.178.90]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1685 - assertIsFalse "ip4.with.number0.after.point@[123.145.0178.90]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1686 - assertIsFalse "ip4.with.number0.before.start.bracket@0[123.145.178.90]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1687 - assertIsFalse "ip4.with.number0.after.start.bracket@[0123.145.178.90]"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1688 - assertIsFalse "ip4.with.number0.before.end.bracket@[123.145.178.900]"                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1689 - assertIsFalse "ip4.with.number0.after.end.bracket@[123.145.178.90]0"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1690 - assertIsFalse "ip4.with.number9.between.numbers@[123.1495.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1691 - assertIsFalse "ip4.with.number9.before.point@[123.1459.178.90]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1692 - assertIsFalse "ip4.with.number9.after.point@[123.145.9178.90]"                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1693 - assertIsFalse "ip4.with.number9.before.start.bracket@9[123.145.178.90]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1694 - assertIsFalse "ip4.with.number9.after.start.bracket@[9123.145.178.90]"                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1695 - assertIsFalse "ip4.with.number9.before.end.bracket@[123.145.178.909]"                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1696 - assertIsFalse "ip4.with.number9.after.end.bracket@[123.145.178.90]9"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1697 - assertIsFalse "ip4.with.numbers.between.numbers@[123.1401234567895.178.90]"                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1698 - assertIsFalse "ip4.with.numbers.before.point@[123.1450123456789.178.90]"                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1699 - assertIsFalse "ip4.with.numbers.after.point@[123.145.0123456789178.90]"                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1700 - assertIsFalse "ip4.with.numbers.before.start.bracket@0123456789[123.145.178.90]"           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1701 - assertIsFalse "ip4.with.numbers.after.start.bracket@[0123456789123.145.178.90]"            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1702 - assertIsFalse "ip4.with.numbers.before.end.bracket@[123.145.178.900123456789]"             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1703 - assertIsFalse "ip4.with.numbers.after.end.bracket@[123.145.178.90]0123456789"              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1704 - assertIsFalse "ip4.with.byte.overflow.between.numbers@[123.149995.178.90]"                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1705 - assertIsFalse "ip4.with.byte.overflow.before.point@[123.145999.178.90]"                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1706 - assertIsFalse "ip4.with.byte.overflow.after.point@[123.145.999178.90]"                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1707 - assertIsFalse "ip4.with.byte.overflow.before.start.bracket@999[123.145.178.90]"            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1708 - assertIsFalse "ip4.with.byte.overflow.after.start.bracket@[999123.145.178.90]"             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1709 - assertIsFalse "ip4.with.byte.overflow.before.end.bracket@[123.145.178.90999]"              =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1710 - assertIsFalse "ip4.with.byte.overflow.after.end.bracket@[123.145.178.90]999"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1711 - assertIsFalse "ip4.with.no.hex.number.between.numbers@[123.14xyz5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1712 - assertIsFalse "ip4.with.no.hex.number.before.point@[123.145xyz.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1713 - assertIsFalse "ip4.with.no.hex.number.after.point@[123.145.xyz178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1714 - assertIsFalse "ip4.with.no.hex.number.before.start.bracket@xyz[123.145.178.90]"            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1715 - assertIsFalse "ip4.with.no.hex.number.after.start.bracket@[xyz123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1716 - assertIsFalse "ip4.with.no.hex.number.before.end.bracket@[123.145.178.90xyz]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1717 - assertIsFalse "ip4.with.no.hex.number.after.end.bracket@[123.145.178.90]xyz"               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1718 - assertIsFalse "ip4.with.slash.between.numbers@[123.14\5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1719 - assertIsFalse "ip4.with.slash.before.point@[123.145\.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1720 - assertIsFalse "ip4.with.slash.after.point@[123.145.\178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1721 - assertIsFalse "ip4.with.slash.before.start.bracket@\[123.145.178.90]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1722 - assertIsFalse "ip4.with.slash.after.start.bracket@[\123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1723 - assertIsFalse "ip4.with.slash.before.end.bracket@[123.145.178.90\]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1724 - assertIsFalse "ip4.with.slash.after.end.bracket@[123.145.178.90]\"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1725 - assertIsFalse "ip4.with.string.between.numbers@[123.14\"str\"5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1726 - assertIsFalse "ip4.with.string.before.point@[123.145\"str\".178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1727 - assertIsFalse "ip4.with.string.after.point@[123.145.\"str\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1728 - assertIsFalse "ip4.with.string.before.start.bracket@\"str\"[123.145.178.90]"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1729 - assertIsFalse "ip4.with.string.after.start.bracket@[\"str\"123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1730 - assertIsFalse "ip4.with.string.before.end.bracket@[123.145.178.90\"str\"]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1731 - assertIsFalse "ip4.with.string.after.end.bracket@[123.145.178.90]\"str\""                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1732 - assertIsFalse "ip4.with.comment.between.numbers@[123.14(comment)5.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1733 - assertIsFalse "ip4.with.comment.before.point@[123.145(comment).178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1734 - assertIsFalse "ip4.with.comment.after.point@[123.145.(comment)178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1735 - assertIsTrue  "ip4.with.comment.before.start.bracket@(comment)[123.145.178.90]"            =   2 =  OK 
     *  1736 - assertIsFalse "ip4.with.comment.after.start.bracket@[(comment)123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1737 - assertIsFalse "ip4.with.comment.before.end.bracket@[123.145.178.90(comment)]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1738 - assertIsTrue  "ip4.with.comment.after.end.bracket@[123.145.178.90](comment)"               =   2 =  OK 
     *  1739 - assertIsTrue  "email@[123.123.123.123]"                                                    =   2 =  OK 
     *  1740 - assertIsFalse "email@111.222.333"                                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1741 - assertIsFalse "email@111.222.333.256"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1742 - assertIsFalse "email@[123.123.123.123"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1743 - assertIsFalse "email@[123.123.123].123"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1744 - assertIsFalse "email@123.123.123.123]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1745 - assertIsFalse "email@123.123.[123.123]"                                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1746 - assertIsFalse "ab@988.120.150.10"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1747 - assertIsFalse "ab@120.256.256.120"                                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1748 - assertIsFalse "ab@120.25.1111.120"                                                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1749 - assertIsFalse "ab@[188.120.150.10"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1750 - assertIsFalse "ab@188.120.150.10]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1751 - assertIsFalse "ab@[188.120.150.10].com"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1752 - assertIsTrue  "ab@188.120.150.10"                                                          =   2 =  OK 
     *  1753 - assertIsTrue  "ab@1.0.0.10"                                                                =   2 =  OK 
     *  1754 - assertIsTrue  "ab@120.25.254.120"                                                          =   2 =  OK 
     *  1755 - assertIsTrue  "ab@01.120.150.1"                                                            =   2 =  OK 
     *  1756 - assertIsTrue  "ab@88.120.150.021"                                                          =   2 =  OK 
     *  1757 - assertIsTrue  "ab@88.120.150.01"                                                           =   2 =  OK 
     *  1758 - assertIsTrue  "email@123.123.123.123"                                                      =   2 =  OK 
     * 
     * ---- IP V6 -----------------------------------------------------------------------------------------------------------------------
     * 
     *  1759 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                                 =   4 =  OK 
     *  1760 - assertIsFalse "ABC.DEF@[IP"                                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1761 - assertIsFalse "ABC.DEF@[IPv6]"                                                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1762 - assertIsFalse "ABC.DEF@[IPv6:]"                                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1763 - assertIsFalse "ABC.DEF@[IPv6:"                                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1764 - assertIsFalse "ABC.DEF@[IPv6::]"                                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1765 - assertIsFalse "ABC.DEF@[IPv6::"                                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1766 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1767 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1768 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1769 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1770 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1771 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                       =   4 =  OK 
     *  1772 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                                     =   4 =  OK 
     *  1773 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                  =   4 =  OK 
     *  1774 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                                 =   4 =  OK 
     *  1775 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                 =   4 =  OK 
     *  1776 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                               =   4 =  OK 
     *  1777 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1778 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                             =   4 =  OK 
     *  1779 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1780 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1781 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                                   =   4 =  OK 
     *  1782 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1783 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1784 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1785 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1786 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1787 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                              =   4 =  OK 
     *  1788 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1789 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1790 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1791 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1792 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1793 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1794 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1795 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1796 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1797 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1798 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1799 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1800 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1801 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1802 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1803 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1804 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1805 - assertIsFalse "ip.v6.with.dot@[.IPv6:1:22:3:4:5:6:7]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1806 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:2..2:3:4:5:6:7]"                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1807 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22..:3:4:5:6:7]"                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1808 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:..3:4:5:6:7]"                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1809 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7..]"                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1810 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7].."                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1811 - assertIsFalse "ip.v6.with.double.dot@..[IPv6:1:22:3:4:5:6:7]"                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1812 - assertIsFalse "ip.v6.with.double.dot@[..IPv6:1:22:3:4:5:6:7]"                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1813 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1814 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1815 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1816 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1817 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1818 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1819 - assertIsFalse "ip.v6.with.amp@[&IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1820 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1821 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1822 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1823 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1824 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1825 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1826 - assertIsFalse "ip.v6.with.asterisk@[*IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1827 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1828 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1829 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1830 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1831 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1832 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1833 - assertIsFalse "ip.v6.with.underscore@[_IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1834 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1835 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1836 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1837 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1838 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1839 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1840 - assertIsFalse "ip.v6.with.dollar@[$IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1841 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1842 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1843 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1844 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1845 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1846 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1847 - assertIsFalse "ip.v6.with.equality@[=IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1848 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1849 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1850 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1851 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1852 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1853 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1854 - assertIsFalse "ip.v6.with.exclamation@[!IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1855 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1856 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1857 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1858 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1859 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1860 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1861 - assertIsFalse "ip.v6.with.question@[?IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1862 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1863 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1864 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1865 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1866 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1867 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1868 - assertIsFalse "ip.v6.with.grave-accent@[`IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1869 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1870 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1871 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1872 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1873 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1874 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1875 - assertIsFalse "ip.v6.with.hash@[#IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1876 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1877 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1878 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1879 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1880 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1881 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1882 - assertIsFalse "ip.v6.with.percentage@[%IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1883 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1884 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1885 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1886 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1887 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1888 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1889 - assertIsFalse "ip.v6.with.pipe@[|IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1890 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1891 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1892 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1893 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1894 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1895 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1896 - assertIsFalse "ip.v6.with.plus@[+IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1897 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1898 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1899 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1900 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1901 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1902 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1903 - assertIsFalse "ip.v6.with.leftbracket@[{IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1904 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1905 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1906 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1907 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1908 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1909 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1910 - assertIsFalse "ip.v6.with.rightbracket@[}IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1911 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1912 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1913 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1914 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1915 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                              =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1916 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1917 - assertIsFalse "ip.v6.with.leftbracket@[(IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1918 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1919 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1920 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1921 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1922 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1923 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1924 - assertIsFalse "ip.v6.with.rightbracket@[)IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1925 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1926 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1927 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1928 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1929 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1930 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1931 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1932 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1933 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1934 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1935 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1936 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1937 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1938 - assertIsFalse "ip.v6.with.rightbracket@[]IPv6:1:22:3:4:5:6:7]"                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1939 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1940 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1941 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1942 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1943 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                           =   4 =  OK 
     *  1944 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                           =   4 =  OK 
     *  1945 - assertIsFalse "ip.v6.with.empty.bracket@[()IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1946 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1947 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1948 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1949 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1950 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1951 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1952 - assertIsFalse "ip.v6.with.empty.bracket@[{}IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1953 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1954 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1955 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1956 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1957 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1958 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1959 - assertIsFalse "ip.v6.with.empty.bracket@[[]IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1960 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1961 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1962 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1963 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1964 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1965 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1966 - assertIsFalse "ip.v6.with.empty.bracket@[<>IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1967 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1968 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1969 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1970 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1971 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1972 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1973 - assertIsFalse "ip.v6.with.false.bracket1@[)(IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1974 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1975 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1976 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1977 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1978 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1979 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1980 - assertIsFalse "ip.v6.with.false.bracket2@[}{IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1981 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1982 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1983 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1984 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1985 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1986 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1987 - assertIsFalse "ip.v6.with.false.bracket4@[><IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1988 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1989 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1990 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1991 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1992 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1993 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1994 - assertIsFalse "ip.v6.with.lower.than@[<IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1995 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1996 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1997 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1998 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1999 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                             =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2000 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2001 - assertIsFalse "ip.v6.with.greater.than@[>IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2002 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2003 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2004 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2005 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2006 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2007 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2008 - assertIsFalse "ip.v6.with.tilde@[~IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2009 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2010 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2011 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2012 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2013 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2014 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2015 - assertIsFalse "ip.v6.with.xor@[^IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2016 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                                    =   4 =  OK 
     *  2017 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                    =   4 =  OK 
     *  2018 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                    =   4 =  OK 
     *  2019 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                                    =   4 =  OK 
     *  2020 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2021 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2022 - assertIsFalse "ip.v6.with.colon@[:IPv6:1:22:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2023 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2024 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2025 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2026 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2027 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2028 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2029 - assertIsFalse "ip.v6.with.space@[ IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2030 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2031 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2032 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2033 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2034 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2035 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2036 - assertIsFalse "ip.v6.with.comma@[,IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2037 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2038 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2039 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2040 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2041 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2042 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2043 - assertIsFalse "ip.v6.with.at@[@IPv6:1:22:3:4:5:6:7]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2044 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2045 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2046 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2047 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2048 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2049 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2050 - assertIsFalse "ip.v6.with.paragraph@[§IPv6:1:22:3:4:5:6:7]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2051 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2052 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2053 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2054 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2055 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2056 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2057 - assertIsFalse "ip.v6.with.double.quote@['IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2058 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2059 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2060 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2061 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2062 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                            =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2063 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2064 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2065 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2066 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2067 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2068 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2069 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2070 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2071 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2072 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2073 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2074 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2075 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2076 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2077 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2078 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2079 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2080 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2081 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2082 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2083 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2084 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2085 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2086 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2087 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2088 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2089 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2090 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2091 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2092 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2093 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2094 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2095 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2096 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2097 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2098 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2099 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2100 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:202:3:4:5:6:7]"                                  =   4 =  OK 
     *  2101 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:220:3:4:5:6:7]"                                  =   4 =  OK 
     *  2102 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:03:4:5:6:7]"                                  =   4 =  OK 
     *  2103 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:70]"                                  =   4 =  OK 
     *  2104 - assertIsFalse "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:7]0"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2105 - assertIsFalse "ip.v6.with.number0@0[IPv6:1:22:3:4:5:6:7]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2106 - assertIsFalse "ip.v6.with.number0@[0IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2107 - assertIsFalse "ip.v6.with.number9@[IPv6:1:292:3:4:5:6:7]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2108 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:229:3:4:5:6:7]"                                  =   4 =  OK 
     *  2109 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:93:4:5:6:7]"                                  =   4 =  OK 
     *  2110 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:79]"                                  =   4 =  OK 
     *  2111 - assertIsFalse "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:7]9"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2112 - assertIsFalse "ip.v6.with.number9@9[IPv6:1:22:3:4:5:6:7]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2113 - assertIsFalse "ip.v6.with.number9@[9IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2114 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2115 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2116 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2117 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2118 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2119 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2120 - assertIsFalse "ip.v6.with.numbers@[0123456789IPv6:1:22:3:4:5:6:7]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2121 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                          =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2122 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                          =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2123 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]"                          =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2124 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7999]"                          =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2125 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]999"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2126 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2127 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2128 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2129 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2130 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2131 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2132 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2133 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2134 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2135 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2136 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2137 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2138 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2139 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2140 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2141 - assertIsFalse "ip.v6.with.slash@[\IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2142 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2143 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2144 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2145 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2146 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2147 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2148 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2149 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2150 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2151 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2152 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2153 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                          =   4 =  OK 
     *  2154 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                          =   4 =  OK 
     *  2155 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2156 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"                     =   4 =  OK 
     *  2157 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"                     =   4 =  OK 
     *  2158 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"                     =   4 =  OK 
     *  2159 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                                    =   4 =  OK 
     *  2160 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                                     =   4 =  OK 
     *  2161 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2162 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2163 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2164 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 -----------------------------------------------------------------------------------------------------
     * 
     *  2165 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                             =   4 =  OK 
     *  2166 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                             =   4 =  OK 
     *  2167 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                                 =   4 =  OK 
     *  2168 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                                 =   4 =  OK 
     *  2169 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2170 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2171 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2172 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2173 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2174 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2175 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2176 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                           =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2177 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.0001]"                                          =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2178 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- ip4 without brackets --------------------------------------------------------------------------------------------------------
     * 
     *  2179 - assertIsTrue  "ip4.without.brackets.ok1@127.0.0.1"                                         =   2 =  OK 
     *  2180 - assertIsTrue  "ip4.without.brackets.ok2@0.0.0.0"                                           =   2 =  OK 
     *  2181 - assertIsFalse "ip4.without.brackets.but.with.space.at.end@127.0.0.1 "                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2182 - assertIsFalse "ip4.without.brackets.byte.overflow@127.0.999.1"                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2183 - assertIsFalse "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1"                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2184 - assertIsFalse "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1"                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2185 - assertIsFalse "ip4.without.brackets.negative.number@127.0.-1.1"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2186 - assertIsFalse "ip4.without.brackets.point.error1@127.0..0.1"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2187 - assertIsFalse "ip4.without.brackets.point.error1@127...1"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2188 - assertIsFalse "ip4.without.brackets.error.bracket@127.0.1.1[]"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2189 - assertIsFalse "ip4.without.brackets.point.error2@127001"                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2190 - assertIsFalse "ip4.without.brackets.point.error3@127.0.0."                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2191 - assertIsFalse "ip4.without.brackets.character.error@127.0.A.1"                             =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2192 - assertIsFalse "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1"                  =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2193 - assertIsTrue  "ip4.without.brackets.normal.tld1@127.0.0.1.com"                             =   0 =  OK 
     *  2194 - assertIsTrue  "ip4.without.brackets.normal.tld2@127.0.99.1.com"                            =   0 =  OK 
     *  2195 - assertIsTrue  "ip4.without.brackets.normal.tld3@127.0.A.1.com"                             =   0 =  OK 
     * 
     * ---- Strings ---------------------------------------------------------------------------------------------------------------------
     * 
     *  2196 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                         =   1 =  OK 
     *  2197 - assertIsTrue  "\"ABC\".\"DEF\"@GHI.DE"                                                     =   1 =  OK 
     *  2198 - assertIsFalse "-\"ABC\".\"DEF\"@GHI.DE"                                                    = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
     *  2199 - assertIsFalse "\"ABC\"-.\"DEF\"@GHI.DE"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2200 - assertIsFalse "\"ABC\".-\"DEF\"@GHI.DE"                                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2201 - assertIsFalse ".\"ABC\".\"DEF\"@GHI.DE"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2202 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                         =   1 =  OK 
     *  2203 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                         =   1 =  OK 
     *  2204 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                       =   1 =  OK 
     *  2205 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2206 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2207 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2208 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2209 - assertIsFalse "\"\"@GHI.DE"                                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2210 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2211 - assertIsFalse "A@G\"HI.DE"                                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2212 - assertIsFalse "\"@GHI.DE"                                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2213 - assertIsFalse "ABC.DEF.\""                                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2214 - assertIsFalse "ABC.DEF@\"\""                                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2215 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2216 - assertIsFalse "ABC.DEF@GHI.DE\""                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2217 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2218 - assertIsFalse "\"Escape.Sequenz.Ende\""                                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2219 - assertIsFalse "ABC.DEF\"GHI.DE"                                                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2220 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2221 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2222 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2223 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                         =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2224 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                        =   1 =  OK 
     *  2225 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                        =   1 =  OK 
     *  2226 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                                 =   1 =  OK 
     *  2227 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2228 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2229 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2230 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2231 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2232 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2233 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2234 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2235 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                                     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2236 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2237 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2238 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2239 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2240 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2241 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2242 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  2243 - assertIsFalse "\"\".ABC.DEF@GHI.DE"                                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2244 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  2245 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                          =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2246 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                       =   1 =  OK 
     *  2247 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                                     =   1 =  OK 
     *  2248 - assertIsTrue  "\"ABC.\\".\\".DEF\"@GHI.DE"                                                 =   1 =  OK 
     *  2249 - assertIsTrue  "\"ABC.\\"\\".DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  2250 - assertIsTrue  "\"ABC.\\" \@ \\".DEF\"@GHI.DE"                                              =   1 =  OK 
     *  2251 - assertIsFalse "\"Ende.am.Eingabeende\""                                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2252 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2253 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                       =   1 =  OK 
     *  2254 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2255 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2256 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2257 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                      =   1 =  OK 
     *  2258 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                              =   1 =  OK 
     *  2259 - assertIsFalse "\"Joe Smith\" email@domain.com"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2260 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2261 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments --------------------------------------------------------------------------------------------------------------------
     * 
     *  2262 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                           =   6 =  OK 
     *  2263 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                          =   6 =  OK 
     *  2264 - assertIsFalse "ABC.DEF(GHI)    @JKL.MNO"                                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2265 - assertIsFalse "(ABC) (DEF)  GHI@JKL.MNO"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2266 - assertIsFalse "(ABC) (DEF).GHI@JKL.MNO"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2267 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                           =   6 =  OK 
     *  2268 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                       =   6 =  OK 
     *  2269 - assertIsFalse "ABC.DEF@GHI.JKL(MNO)    "                                                   = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2270 - assertIsTrue  "ABC.DEF@(GHI)JKL.MNO"                                                       =   6 =  OK 
     *  2271 - assertIsTrue  "ABC.DEF@(GHI.@.)JKL.MNO"                                                    =   6 =  OK 
     *  2272 - assertIsFalse "ABC.DEF@(GHI\@)JKL.MNO"                                                     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2273 - assertIsFalse "ABC.DEF@(GHI)   JKL.MNO"                                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2274 - assertIsFalse "ABC.DEF@(GHI.)   JKL.MNO"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2275 - assertIsFalse "ABC.DEF@(GHI.) (ABC)JKL.MNO"                                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2276 - assertIsFalse "ABC.DEF@(GHI.)   JKL(MNO)"                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2277 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                                 =   6 =  OK 
     *  2278 - assertIsFalse "ABC.DEF@             (MNO)"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2279 - assertIsFalse "ABC.DEF@   .         (MNO)"                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2280 - assertIsFalse "ABC.DEF              (MNO)"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2281 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2282 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2283 - assertIsFalse "ABC.DEF@GHI.JKL          "                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2284 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2285 - assertIsFalse "("                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2286 - assertIsFalse ")"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2287 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                         =   6 =  OK 
     *  2288 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                          =   6 =  OK 
     *  2289 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                          =   6 =  OK 
     *  2290 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                          =   6 =  OK 
     *  2291 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                          =   6 =  OK 
     *  2292 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2293 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2294 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2295 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2296 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2297 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2298 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2299 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2300 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                          = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2301 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2302 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                      = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  2303 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2304 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2305 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2306 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                           =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  2307 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2308 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2309 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                          =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2310 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                         =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2311 - assertIsFalse "ABC(DEF@GHI.JKL"                                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2312 - assertIsFalse "ABC.DEF@GHI)JKL"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2313 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2314 - assertIsFalse "ABC.DEF)@GHI.JKL"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2315 - assertIsFalse "ABC(DEF@GHI).JKL"                                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2316 - assertIsFalse "ABC(DEF.GHI).JKL"                                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2317 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                          =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  2318 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2319 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2320 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2321 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2322 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2323 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                                 =   2 =  OK 
     *  2324 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                                = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  2325 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                                 =   2 =  OK 
     *  2326 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                             =   2 =  OK 
     *  2327 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2328 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                     =   4 =  OK 
     *  2329 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                     =   4 =  OK 
     *  2330 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                 =   4 =  OK 
     *  2331 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2332 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                                  =   6 =  OK 
     *  2333 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                                  =   6 =  OK 
     *  2334 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                                  = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2335 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2336 - assertIsTrue  "(comment)john.smith@example.com"                                            =   6 =  OK 
     *  2337 - assertIsTrue  "john.smith(comment)@example.com"                                            =   6 =  OK 
     *  2338 - assertIsTrue  "john.smith@(comment)example.com"                                            =   6 =  OK 
     *  2339 - assertIsTrue  "john.smith@example.com(comment)"                                            =   6 =  OK 
     *  2340 - assertIsFalse "john.smith@exampl(comment)e.com"                                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2341 - assertIsFalse "john.s(comment)mith@example.com"                                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2342 - assertIsFalse "john.smith(comment)@(comment)example.com"                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2343 - assertIsFalse "john.smith(com@ment)example.com"                                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2344 - assertIsFalse "email( (nested) )@plus.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2345 - assertIsFalse "email)mirror(@plus.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2346 - assertIsFalse "email@plus.com (not closed comment"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2347 - assertIsFalse "email(with @ in comment)plus.com"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2348 - assertIsTrue  "email@domain.com (joe Smith)"                                               =   6 =  OK 
     *  2349 - assertIsFalse "a@abc(bananas)def.com"                                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2350 - assertIsTrue  "\"address(comment\"@example.com"                                            =   1 =  OK 
     *  2351 - assertIsFalse "address(co\"mm\"ent)@example.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2352 - assertIsFalse "address(co\"mment)@example.com"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     * 
     * ---- Pointy Brackets -------------------------------------------------------------------------------------------------------------
     * 
     *  2353 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                                  =   0 =  OK 
     *  2354 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                                  =   0 =  OK 
     *  2355 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                                   =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2356 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  2357 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                              =   0 =  OK 
     *  2358 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                        =   1 =  OK 
     *  2359 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                                =   1 =  OK 
     *  2360 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2361 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2362 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2363 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2364 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2365 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2366 - assertIsFalse "ABC DEF <A@A>"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2367 - assertIsFalse "<A@A> ABC DEF"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2368 - assertIsFalse "ABC DEF <>"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2369 - assertIsFalse "<> ABC DEF"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2370 - assertIsFalse "<"                                                                          =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  2371 - assertIsFalse ">"                                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2372 - assertIsFalse "<         >"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2373 - assertIsFalse "< <     > >"                                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2374 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                          =   0 =  OK 
     *  2375 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                         = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2376 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2377 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                         =   0 =  OK 
     *  2378 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2379 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                         =   0 =  OK 
     *  2380 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2381 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                                 =   6 =  OK 
     *  2382 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2383 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                                = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2384 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                                =   6 =  OK 
     *  2385 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                                = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2386 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                                =   6 =  OK 
     *  2387 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                                = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2388 - assertIsTrue  "Joe Smith <email@domain.com>"                                               =   0 =  OK 
     *  2389 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2390 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2391 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"            =   9 =  OK 
     *  2392 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"            =   9 =  OK 
     *  2393 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"            =   9 =  OK 
     *  2394 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "           =   9 =  OK 
     *  2395 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2396 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2397 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2398 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2399 - assertIsFalse "Test |<gaaf <email@domain.com>"                                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2400 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2401 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2402 - assertIsFalse "<null>@mail.com"                                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2403 - assertIsFalse "email.adress@domain.com <display name>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2404 - assertIsFalse "eimail.adress@domain.com <eimail.adress@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2405 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2406 - assertIsFalse "<eimail>.<adress>@domain.com"                                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2407 - assertIsFalse "<eimail>.<adress> email.adress@domain.com"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------------------------
     * 
     *  2408 - assertIsTrue  "A@B.CD"                                                                     =   0 =  OK 
     *  2409 - assertIsFalse "A@B.C"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2410 - assertIsFalse "A@COM"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2411 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                            =   0 =  OK 
     *  2412 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  2413 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  2414 - assertIsTrue  "A@B.CD"                                                                     =   0 =  OK 
     *  2415 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  2416 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2417 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2418 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2419 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld> True64 " =   0 =  OK 
     *  2420 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2421 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2422 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2423 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"      =   0 =  OK 
     *  2424 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  2425 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2426 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2427 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2428 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2429 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2430 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2431 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2432 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2433 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2434 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2435 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2436 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2437 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
     *  2438 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  2439 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  2440 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2441 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2442 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  2443 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2444 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2445 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2446 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
     *  2447 - assertIsTrue  "email@domain.topleveldomain"                                                =   0 =  OK 
     *  2448 - assertIsTrue  "email@email.email.mydomain"                                                 =   0 =  OK 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ --------------------------------------------------------------------------------
     * 
     *  2449 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                           =   6 =  OK 
     *  2450 - assertIsTrue  "\"MaxMustermann\"@example.com"                                              =   1 =  OK 
     *  2451 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                                 =   1 =  OK 
     *  2452 - assertIsTrue  "\".John.Doe\"@example.com"                                                  =   1 =  OK 
     *  2453 - assertIsTrue  "\"John.Doe.\"@example.com"                                                  =   1 =  OK 
     *  2454 - assertIsTrue  "\"John..Doe\"@example.com"                                                  =   1 =  OK 
     *  2455 - assertIsTrue  "john.smith(comment)@example.com"                                            =   6 =  OK 
     *  2456 - assertIsTrue  "(comment)john.smith@example.com"                                            =   6 =  OK 
     *  2457 - assertIsTrue  "john.smith@(comment)example.com"                                            =   6 =  OK 
     *  2458 - assertIsTrue  "john.smith@example.com(comment)"                                            =   6 =  OK 
     *  2459 - assertIsTrue  "john.smith@example.com"                                                     =   0 =  OK 
     *  2460 - assertIsTrue  "joeuser+tag@example.com"                                                    =   0 =  OK 
     *  2461 - assertIsTrue  "jsmith@[192.168.2.1]"                                                       =   2 =  OK 
     *  2462 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                                  =   4 =  OK 
     *  2463 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                          =   0 =  OK 
     *  2464 - assertIsTrue  "Marc Dupont <md118@example.com>"                                            =   0 =  OK 
     *  2465 - assertIsTrue  "simple@example.com"                                                         =   0 =  OK 
     *  2466 - assertIsTrue  "very.common@example.com"                                                    =   0 =  OK 
     *  2467 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                             =   0 =  OK 
     *  2468 - assertIsTrue  "other.email-with-hyphen@example.com"                                        =   0 =  OK 
     *  2469 - assertIsTrue  "fully-qualified-domain@example.com"                                         =   0 =  OK 
     *  2470 - assertIsTrue  "user.name+tag+sorting@example.com"                                          =   0 =  OK 
     *  2471 - assertIsTrue  "user+mailbox/department=shipping@example.com"                               =   0 =  OK 
     *  2472 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                           =   0 =  OK 
     *  2473 - assertIsTrue  "x@example.com"                                                              =   0 =  OK 
     *  2474 - assertIsTrue  "info@firma.org"                                                             =   0 =  OK 
     *  2475 - assertIsTrue  "example-indeed@strange-example.com"                                         =   0 =  OK 
     *  2476 - assertIsTrue  "admin@mailserver1"                                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2477 - assertIsTrue  "example@s.example"                                                          =   0 =  OK 
     *  2478 - assertIsTrue  "\" \"@example.org"                                                          =   1 =  OK 
     *  2479 - assertIsTrue  "mailhost!username@example.org"                                              =   0 =  OK 
     *  2480 - assertIsTrue  "user%example.com@example.org"                                               =   0 =  OK 
     *  2481 - assertIsTrue  "joe25317@NOSPAMexample.com"                                                 =   0 =  OK 
     *  2482 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                                 =   0 =  OK 
     *  2483 - assertIsTrue  "nama@contoh.com"                                                            =   0 =  OK 
     *  2484 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                                 =   0 =  OK 
     *  2485 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2486 - assertIsFalse "Abc.example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2487 - assertIsFalse "Abc..123@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2488 - assertIsFalse "A@b@c@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2489 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2490 - assertIsFalse "just\"not\"right@example.com"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2491 - assertIsFalse "this is\"not\allowed@example.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2492 - assertIsFalse "this\ still\\"not\\allowed@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2493 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2494 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"         =   0 =  OK 
     *  2495 - assertIsTrue  "(buero)office@example.com"                                                  =   6 =  OK 
     *  2496 - assertIsTrue  "office(etage-3)@example.com"                                                =   6 =  OK 
     *  2497 - assertIsFalse "off(kommentar)ice@example.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2498 - assertIsTrue  "\"(buero)office\"@example.com"                                              =   1 =  OK 
     *  2499 - assertIsTrue  "\"office(etage-3)\"@example.com"                                            =   1 =  OK 
     *  2500 - assertIsTrue  "\"off(kommentar)ice\"@example.com"                                          =   1 =  OK 
     *  2501 - assertIsTrue  "\"address(comment)\"@example.com"                                           =   1 =  OK 
     *  2502 - assertIsTrue  "Buero <office@example.com>"                                                 =   0 =  OK 
     *  2503 - assertIsTrue  "\"vorname(Kommentar).nachname\"@provider.de"                                =   1 =  OK 
     *  2504 - assertIsTrue  "\"Herr \\"Kaiser\\" Franz Beckenbauer\" <local-part@domain-part.com>"       =   0 =  OK 
     *  2505 - assertIsTrue  "Erwin Empfaenger <erwin@example.com>"                                       =   0 =  OK 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ---------------------------------------------------------------------------------
     * 
     *  2506 - assertIsFalse "nolocalpart.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2507 - assertIsFalse "test@example.com test"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2508 - assertIsFalse "user  name@example.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2509 - assertIsFalse "user   name@example.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2510 - assertIsFalse "example.@example.co.uk"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2511 - assertIsFalse "example@example@example.co.uk"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2512 - assertIsFalse "(test_exampel@example.fr}"                                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2513 - assertIsFalse "example(example)example@example.co.uk"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2514 - assertIsFalse ".example@localhost"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2515 - assertIsFalse "ex\ample@localhost"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2516 - assertIsFalse "example@local\host"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2517 - assertIsFalse "example@localhost."                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2518 - assertIsFalse "user name@example.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2519 - assertIsFalse "username@ example . com"                                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2520 - assertIsFalse "example@(fake}.com"                                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2521 - assertIsFalse "example@(fake.com"                                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2522 - assertIsTrue  "username@example.com"                                                       =   0 =  OK 
     *  2523 - assertIsTrue  "usern.ame@example.com"                                                      =   0 =  OK 
     *  2524 - assertIsFalse "user[na]me@example.com"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2525 - assertIsFalse "\"\"\"@iana.org"                                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2526 - assertIsFalse "\"\\"@iana.org"                                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2527 - assertIsFalse "\"test\"test@iana.org"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2528 - assertIsFalse "\"test\"\"test\"@iana.org"                                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2529 - assertIsTrue  "\"test\".\"test\"@iana.org"                                                 =   1 =  OK 
     *  2530 - assertIsTrue  "\"test\".test@iana.org"                                                     =   1 =  OK 
     *  2531 - assertIsFalse "\"test\\"@iana.org"                                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2532 - assertIsFalse "\r\ntest@iana.org"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2533 - assertIsFalse "\r\n test@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2534 - assertIsFalse "\r\n \r\ntest@iana.org"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2535 - assertIsFalse "\r\n \r\n test@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2536 - assertIsFalse "test@iana.org \r\n"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2537 - assertIsFalse "test@iana.org \r\n "                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2538 - assertIsFalse "test@iana.org \r\n \r\n"                                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2539 - assertIsFalse "test@iana.org \r\n\r\n"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2540 - assertIsFalse "test@iana.org  \r\n\r\n "                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2541 - assertIsFalse "test@iana/icann.org"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2542 - assertIsFalse "test@foo;bar.com"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2543 - assertIsFalse "a@test.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2544 - assertIsFalse "comment)example@example.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2545 - assertIsFalse "comment(example))@example.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2546 - assertIsFalse "example@example)comment.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2547 - assertIsFalse "example@example(comment)).com"                                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2548 - assertIsFalse "example@[1.2.3.4"                                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2549 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2550 - assertIsFalse "exam(ple@exam).ple"                                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2551 - assertIsFalse "example@(example))comment.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2552 - assertIsTrue  "example@example.com"                                                        =   0 =  OK 
     *  2553 - assertIsTrue  "example@example.co.uk"                                                      =   0 =  OK 
     *  2554 - assertIsTrue  "example_underscore@example.fr"                                              =   0 =  OK 
     *  2555 - assertIsTrue  "exam'ple@example.com"                                                       =   0 =  OK 
     *  2556 - assertIsTrue  "exam\ ple@example.com"                                                      =   0 =  OK 
     *  2557 - assertIsFalse "example((example))@fakedfake.co.uk"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2558 - assertIsFalse "example@faked(fake).co.uk"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2559 - assertIsTrue  "example+@example.com"                                                       =   0 =  OK 
     *  2560 - assertIsTrue  "example@with-hyphen.example.com"                                            =   0 =  OK 
     *  2561 - assertIsTrue  "with-hyphen@example.com"                                                    =   0 =  OK 
     *  2562 - assertIsTrue  "example@1leadingnum.example.com"                                            =   0 =  OK 
     *  2563 - assertIsTrue  "1leadingnum@example.com"                                                    =   0 =  OK 
     *  2564 - assertIsTrue  "инфо@письмо.рф"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2565 - assertIsTrue  "\"username\"@example.com"                                                   =   1 =  OK 
     *  2566 - assertIsTrue  "\"user.name\"@example.com"                                                  =   1 =  OK 
     *  2567 - assertIsTrue  "\"user name\"@example.com"                                                  =   1 =  OK 
     *  2568 - assertIsTrue  "\"user@name\"@example.com"                                                  =   1 =  OK 
     *  2569 - assertIsFalse "\"\a\"@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2570 - assertIsTrue  "\"test\ test\"@iana.org"                                                    =   1 =  OK 
     *  2571 - assertIsFalse "\"\"@iana.org"                                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2572 - assertIsFalse "\"\"@[]"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2573 - assertIsTrue  "\"\\"\"@iana.org"                                                           =   1 =  OK 
     *  2574 - assertIsTrue  "example@localhost"                                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- unsorted from the WEB -------------------------------------------------------------------------------------------------------
     * 
     *  2575 - assertIsFalse "<')))><@fish.left.com"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2576 - assertIsFalse "><(((*>@fish.right.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2577 - assertIsFalse " check@this.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2578 - assertIsFalse " email@example.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2579 - assertIsFalse ".....@a...."                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2580 - assertIsFalse "..@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2581 - assertIsFalse "..@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2582 - assertIsTrue  "\"test....\"@gmail.com"                                                     =   1 =  OK 
     *  2583 - assertIsFalse "test....@gmail.com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2584 - assertIsTrue  "name@xn--4ca9at.at"                                                         =   0 =  OK 
     *  2585 - assertIsTrue  "simon-@hotmail.com"                                                         =   0 =  OK 
     *  2586 - assertIsTrue  "!@mydomain.net"                                                             =   0 =  OK 
     *  2587 - assertIsTrue  "sean.o'leary@cobbcounty.org"                                                =   0 =  OK 
     *  2588 - assertIsTrue  "xjhgjg876896@domain.com"                                                    =   0 =  OK 
     *  2589 - assertIsTrue  "Tony Snow <tony@example.com>"                                               =   0 =  OK 
     *  2590 - assertIsTrue  "(tony snow) tony@example.com"                                               =   6 =  OK 
     *  2591 - assertIsTrue  "tony%example.com@example.org"                                               =   0 =  OK 
     *  2592 - assertIsFalse "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2593 - assertIsFalse "a-b'c_d.e@f-g.h"                                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2594 - assertIsFalse "a-b'c_d.@f-g.h"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2595 - assertIsFalse "a-b'c_d.e@f-.h"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2596 - assertIsTrue  "\"root\"@example.com"                                                       =   1 =  OK 
     *  2597 - assertIsTrue  "root@example.com"                                                           =   0 =  OK 
     *  2598 - assertIsFalse ".@s.dd"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2599 - assertIsFalse ".@s.dd"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2600 - assertIsFalse ".a@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2601 - assertIsFalse ".a@test.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2602 - assertIsFalse ".abc@somedomain.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2603 - assertIsFalse ".dot@example.com"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2604 - assertIsFalse ".email@domain.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2605 - assertIsFalse ".journaldev@journaldev.com"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2606 - assertIsFalse ".username@yahoo.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2607 - assertIsFalse ".username@yahoo.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2608 - assertIsFalse ".xxxx@mysite.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2609 - assertIsFalse "asdf@asdf"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2610 - assertIsFalse "123@$.xyz"                                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2611 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2612 - assertIsFalse "@%^%#$@#$@#.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2613 - assertIsFalse "@b.com"                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2614 - assertIsFalse "@domain.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2615 - assertIsFalse "@example.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2616 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2617 - assertIsFalse "@yahoo.com"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2618 - assertIsFalse "@you.me.net"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2619 - assertIsFalse "A@b@c@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2620 - assertIsFalse "Abc.example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2621 - assertIsFalse "Abc@example.com."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2622 - assertIsFalse "Display Name <email@plus.com> (after name with display)"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2623 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2624 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2625 - assertIsFalse "Foobar Some@thing.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2626 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2627 - assertIsFalse "MailTo:casesensitve@domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2628 - assertIsFalse "No -foo@bar.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2629 - assertIsFalse "No asd@-bar.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2630 - assertIsFalse "DomainHyphen@-atstart"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2631 - assertIsFalse "DomainHyphen@atend-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2632 - assertIsFalse "DomainHyphen@bb.-cc"                                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2633 - assertIsFalse "DomainHyphen@bb.-cc-"                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2634 - assertIsFalse "DomainHyphen@bb.cc-"                                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2635 - assertIsFalse "DomainHyphen@bb.c-c"                                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2636 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2637 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                          =   0 =  OK 
     *  2638 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2639 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2640 - assertIsFalse "DomainNotAllowedCharacter@example'"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2641 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2642 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                       =   0 =  OK 
     *  2643 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                         =   0 =  OK 
     *  2644 - assertIsFalse "tld.starts.with.digit@domain.2com"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2645 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                            =   0 =  OK 
     *  2646 - assertIsFalse "email@=qowaiv.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2647 - assertIsFalse "email@plus+.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2648 - assertIsFalse "email@domain.com>"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2649 - assertIsFalse "email@mailto:domain.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2650 - assertIsFalse "mailto:mailto:email@domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2651 - assertIsFalse "email@-domain.com"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2652 - assertIsFalse "email@domain-.com"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2653 - assertIsFalse "email@domain.com-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2654 - assertIsFalse "email@{leftbracket.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2655 - assertIsFalse "email@rightbracket}.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2656 - assertIsFalse "email@pp|e.com"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2657 - assertIsTrue  "email@domain.domain.domain.com.com"                                         =   0 =  OK 
     *  2658 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                                  =   0 =  OK 
     *  2659 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                           =   0 =  OK 
     *  2660 - assertIsFalse "unescaped white space@fake$com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2661 - assertIsFalse "\"Joe Smith email@domain.com"                                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2662 - assertIsFalse "\"Joe Smith' email@domain.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2663 - assertIsFalse "\"Joe Smith\"email@domain.com"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2664 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2665 - assertIsTrue  "{john'doe}@my.server"                                                       =   0 =  OK 
     *  2666 - assertIsTrue  "email@domain-one.com"                                                       =   0 =  OK 
     *  2667 - assertIsTrue  "_______@domain.com"                                                         =   0 =  OK 
     *  2668 - assertIsTrue  "?????@domain.com"                                                           =   0 =  OK 
     *  2669 - assertIsFalse "local@?????.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2670 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                              =   1 =  OK 
     *  2671 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                                     =   1 =  OK 
     *  2672 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                  =   0 =  OK 
     *  2673 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                           =   0 =  OK 
     *  2674 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2675 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"                    =   0 =  OK 
     *  2676 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2677 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                        =   1 =  OK 
     *  2678 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2679 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2680 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2681 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2682 - assertIsFalse "\"Joe\Blow\"@example.com"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2683 - assertIsFalse "\"\"Joe Smith email@domain.com"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2684 - assertIsFalse "\"\"Joe Smith' email@domain.com"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2685 - assertIsFalse "\"\"Joe Smith\"\"email@domain.com"                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2686 - assertIsFalse "\"qu@example.com"                                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2687 - assertIsFalse "\$A12345@example.com"                                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2688 - assertIsFalse "_@bde.cc,"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2689 - assertIsFalse "a..b@bde.cc"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2690 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2691 - assertIsFalse "a.b@example,co.de"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2692 - assertIsFalse "a.b@example,com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2693 - assertIsFalse "a>b@somedomain.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2694 - assertIsFalse "a@.com"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2695 - assertIsFalse "a@b."                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2696 - assertIsFalse "a@b.-de.cc"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2697 - assertIsFalse "a@b._de.cc"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2698 - assertIsFalse "a@bde-.cc"                                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2699 - assertIsFalse "a@bde.c-c"                                                                  =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2700 - assertIsFalse "a@bde.cc."                                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2701 - assertIsFalse "a@bde_.cc"                                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2702 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2703 - assertIsFalse "ab@120.25.1111.120"                                                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2704 - assertIsFalse "ab@120.256.256.120"                                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2705 - assertIsFalse "ab@188.120.150.10]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2706 - assertIsFalse "ab@988.120.150.10"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2707 - assertIsFalse "ab@[188.120.150.10"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2708 - assertIsFalse "ab@[188.120.150.10].com"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2709 - assertIsFalse "ab@b+de.cc"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2710 - assertIsFalse "ab@sd@dd"                                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2711 - assertIsFalse "abc.@somedomain.com"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2712 - assertIsFalse "abc@def@example.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2713 - assertIsFalse "abc@gmail..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2714 - assertIsFalse "abc@gmail.com.."                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2715 - assertIsFalse "abc\"defghi\"xyz@example.com"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2716 - assertIsFalse "abc\@example.com"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2717 - assertIsFalse "abc\\"def\\"ghi@example.com"                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2718 - assertIsFalse "abc\\@def@example.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2719 - assertIsFalse "as3d@dac.coas-"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2720 - assertIsFalse "asd@dasd@asd.cm"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2721 - assertIsFalse "check@this..com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2722 - assertIsFalse "check@thiscom"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2723 - assertIsFalse "da23@das..com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2724 - assertIsFalse "dad@sds"                                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2725 - assertIsFalse "dasddas-@.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2726 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2727 - assertIsFalse "dot.@example.com"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2728 - assertIsFalse "doug@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2729 - assertIsFalse "email( (nested) )@plus.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2730 - assertIsFalse "email(with @ in comment)plus.com"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2731 - assertIsFalse "email)mirror(@plus.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2732 - assertIsFalse "email..email@domain.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2733 - assertIsFalse "email..email@domain.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2734 - assertIsFalse "email.@domain.com"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2735 - assertIsFalse "email.domain.com"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2736 - assertIsFalse "email@#hash.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2737 - assertIsFalse "email@.domain.com"                                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2738 - assertIsFalse "email@111.222.333"                                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2739 - assertIsFalse "email@111.222.333.256"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2740 - assertIsFalse "email@123.123.123.123]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2741 - assertIsFalse "email@123.123.[123.123]"                                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2742 - assertIsFalse "email@=qowaiv.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2743 - assertIsFalse "email@[123.123.123.123"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2744 - assertIsFalse "email@[123.123.123].123"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2745 - assertIsFalse "email@caret^xor.com"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2746 - assertIsFalse "email@colon:colon.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2747 - assertIsFalse "email@dollar$.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2748 - assertIsFalse "email@domain"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2749 - assertIsFalse "email@domain-.com"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2750 - assertIsFalse "email@domain..com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2751 - assertIsFalse "email@domain.com-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2752 - assertIsFalse "email@domain.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2753 - assertIsFalse "email@domain.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2754 - assertIsFalse "email@domain.com>"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2755 - assertIsFalse "email@domain@domain.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2756 - assertIsFalse "email@example"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2757 - assertIsFalse "email@example..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2758 - assertIsFalse "email@example.co.uk."                                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2759 - assertIsFalse "email@example.com "                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2760 - assertIsFalse "email@exclamation!mark.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2761 - assertIsFalse "email@grave`accent.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2762 - assertIsFalse "email@mailto:domain.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2763 - assertIsFalse "email@obelix*asterisk.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2764 - assertIsFalse "email@plus+.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2765 - assertIsFalse "email@plus.com (not closed comment"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2766 - assertIsFalse "email@p|pe.com"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2767 - assertIsFalse "email@question?mark.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2768 - assertIsFalse "email@r&amp;d.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2769 - assertIsFalse "email@rightbracket}.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2770 - assertIsFalse "email@wave~tilde.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2771 - assertIsFalse "email@{leftbracket.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2772 - assertIsFalse "f...bar@gmail.com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2773 - assertIsFalse "fa ke@false.com"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2774 - assertIsFalse "fake@-false.com"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2775 - assertIsFalse "fake@fal se.com"                                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2776 - assertIsFalse "fdsa"                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2777 - assertIsFalse "fdsa@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2778 - assertIsFalse "fdsa@fdsa"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2779 - assertIsFalse "fdsa@fdsa."                                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2780 - assertIsFalse "foo.bar#gmail.co.u"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2781 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2782 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2783 - assertIsFalse "foo~&(&)(@bar.com"                                                          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2784 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2785 - assertIsFalse "get_at_m.e@gmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2786 - assertIsFalse "hallo2ww22@example....caaaao"                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2787 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2788 - assertIsFalse "hello world@example.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2789 - assertIsFalse "invalid.email.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2790 - assertIsFalse "invalid@email@domain.com"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2791 - assertIsFalse "isis@100%.nl"                                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2792 - assertIsFalse "j..s@proseware.com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2793 - assertIsFalse "j.@server1.proseware.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2794 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2795 - assertIsFalse "journaldev"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2796 - assertIsFalse "journaldev()*@gmail.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2797 - assertIsFalse "journaldev..2002@gmail.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2798 - assertIsFalse "journaldev.@gmail.com"                                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2799 - assertIsFalse "journaldev123@.com"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2800 - assertIsFalse "journaldev123@.com.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2801 - assertIsFalse "journaldev123@gmail.a"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2802 - assertIsFalse "journaldev@%*.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2803 - assertIsFalse "journaldev@.com.my"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2804 - assertIsFalse "journaldev@gmail.com.1a"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2805 - assertIsFalse "journaldev@journaldev@gmail.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2806 - assertIsFalse "js@proseware..com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2807 - assertIsFalse "mailto:email@domain.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2808 - assertIsFalse "mailto:mailto:email@domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2809 - assertIsFalse "me..2002@gmail.com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2810 - assertIsFalse "me.@gmail.com"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2811 - assertIsFalse "me123@.com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2812 - assertIsFalse "me123@.com.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2813 - assertIsFalse "me@%*.com"                                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2814 - assertIsFalse "me@.com.my"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2815 - assertIsFalse "me@gmail.com.1a"                                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2816 - assertIsFalse "me@me@gmail.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2817 - assertIsFalse "myemail@@sample.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2818 - assertIsFalse "myemail@sa@mple.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2819 - assertIsFalse "myemailsample.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2820 - assertIsFalse "ote\"@example.com"                                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2821 - assertIsFalse "pio_pio@#factory.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2822 - assertIsFalse "pio_pio@factory.c#om"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2823 - assertIsFalse "pio_pio@factory.c*om"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2824 - assertIsFalse "plain.address"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2825 - assertIsFalse "plainaddress"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2826 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2827 - assertIsFalse "this is not valid@email$com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2828 - assertIsFalse "this\ still\\"not\allowed@example.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2829 - assertIsFalse "two..dot@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2830 - assertIsFalse "user#domain.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2831 - assertIsFalse "username@yahoo..com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2832 - assertIsFalse "username@yahoo.c"                                                           =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2833 - assertIsTrue  "username@domain.com"                                                        =   0 =  OK 
     *  2834 - assertIsTrue  "_username@domain.com"                                                       =   0 =  OK 
     *  2835 - assertIsTrue  "username_@domain.com"                                                       =   0 =  OK 
     *  2836 - assertIsFalse "xxx@u*.com"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2837 - assertIsFalse "xxxx..1234@yahoo.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2838 - assertIsFalse "xxxx.ourearth.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2839 - assertIsFalse "xxxx123@gmail.b"                                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2840 - assertIsFalse "xxxx@.com.my"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2841 - assertIsFalse "xxxx@.org.org"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2842 - assertIsFalse "xxxxx()*@gmail.com"                                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2843 - assertIsFalse "{something}@{something}.{something}"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2844 - assertIsTrue  "mymail\@hello@hotmail.com"                                                  =   0 =  OK 
     *  2845 - assertIsTrue  "!def!xyz%abc@example.com"                                                   =   0 =  OK 
     *  2846 - assertIsTrue  "!sd@gh.com"                                                                 =   0 =  OK 
     *  2847 - assertIsTrue  "$A12345@example.com"                                                        =   0 =  OK 
     *  2848 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                      =   0 =  OK 
     *  2849 - assertIsTrue  "%2@gmail.com"                                                               =   0 =  OK 
     *  2850 - assertIsTrue  "--@ooo.ooo"                                                                 =   0 =  OK 
     *  2851 - assertIsTrue  "-@bde.cc"                                                                   =   0 =  OK 
     *  2852 - assertIsTrue  "-asd@das.com"                                                               =   0 =  OK 
     *  2853 - assertIsTrue  "1234567890@domain.com"                                                      =   0 =  OK 
     *  2854 - assertIsTrue  "1@domain.com"                                                               =   0 =  OK 
     *  2855 - assertIsTrue  "1@gmail.com"                                                                =   0 =  OK 
     *  2856 - assertIsTrue  "1_example@something.gmail.com"                                              =   0 =  OK 
     *  2857 - assertIsTrue  "2@bde.cc"                                                                   =   0 =  OK 
     *  2858 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                                     =   0 =  OK 
     *  2859 - assertIsTrue  "<boss@nil.test>"                                                            =   0 =  OK 
     *  2860 - assertIsTrue  "<john@doe.com>"                                                             =   0 =  OK 
     *  2861 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                                  =   0 =  OK 
     *  2862 - assertIsTrue  "Abc.123@example.com"                                                        =   0 =  OK 
     *  2863 - assertIsTrue  "Abc@10.42.0.1"                                                              =   2 =  OK 
     *  2864 - assertIsTrue  "Abc@example.com"                                                            =   0 =  OK 
     *  2865 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                       =   0 =  OK 
     *  2866 - assertIsTrue  "Fred\ Bloggs@example.com"                                                   =   0 =  OK 
     *  2867 - assertIsTrue  "Joe.\\Blow@example.com"                                                     =   0 =  OK 
     *  2868 - assertIsTrue  "John <john@doe.com>"                                                        =   0 =  OK 
     *  2869 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                                =   0 =  OK 
     *  2870 - assertIsTrue  "This is <john@127.0.0.1>"                                                   =   2 =  OK 
     *  2871 - assertIsTrue  "This is <john@[127.0.0.1]>"                                                 =   2 =  OK 
     *  2872 - assertIsTrue  "Who? <one@y.test>"                                                          =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2873 - assertIsTrue  "\" \"@example.org"                                                          =   1 =  OK 
     *  2874 - assertIsTrue  "\"%2\"@gmail.com"                                                           =   1 =  OK 
     *  2875 - assertIsTrue  "\"Abc@def\"@example.com"                                                    =   1 =  OK 
     *  2876 - assertIsTrue  "\"Abc\@def\"@example.com"                                                   =   1 =  OK 
     *  2877 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                          =   1 =  OK 
     *  2878 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                                =   1 =  OK 
     *  2879 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                               =   1 =  OK 
     *  2880 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                         =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2881 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                                  =   1 =  OK 
     *  2882 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                                  =   1 =  OK 
     *  2883 - assertIsTrue  "\"a..b\"@gmail.com"                                                         =   1 =  OK 
     *  2884 - assertIsTrue  "\"a@b\"@example.com"                                                        =   1 =  OK 
     *  2885 - assertIsTrue  "\"a_b\"@gmail.com"                                                          =   1 =  OK 
     *  2886 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                               =   1 =  OK 
     *  2887 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                        =   1 =  OK 
     *  2888 - assertIsTrue  "\"foo\@bar\"@Something.com"                                                 =   1 =  OK 
     *  2889 - assertIsTrue  "\"j\\"s\"@proseware.com"                                                    =   1 =  OK 
     *  2890 - assertIsTrue  "\"myemail@sa\"@mple.com"                                                    =   1 =  OK 
     *  2891 - assertIsTrue  "_-_@bde.cc"                                                                 =   0 =  OK 
     *  2892 - assertIsTrue  "_@gmail.com"                                                                =   0 =  OK 
     *  2893 - assertIsTrue  "_dasd@sd.com"                                                               =   0 =  OK 
     *  2894 - assertIsTrue  "_dasd_das_@9.com"                                                           =   0 =  OK 
     *  2895 - assertIsTrue  "_somename@example.com"                                                      =   0 =  OK 
     *  2896 - assertIsTrue  "a&d@somedomain.com"                                                         =   0 =  OK 
     *  2897 - assertIsTrue  "a*d@somedomain.com"                                                         =   0 =  OK 
     *  2898 - assertIsTrue  "a+b@bde.cc"                                                                 =   0 =  OK 
     *  2899 - assertIsTrue  "a+b@c.com"                                                                  =   0 =  OK 
     *  2900 - assertIsTrue  "a-b@bde.cc"                                                                 =   0 =  OK 
     *  2901 - assertIsTrue  "a.a@test.com"                                                               =   0 =  OK 
     *  2902 - assertIsTrue  "a.b@com"                                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2903 - assertIsTrue  "a/d@somedomain.com"                                                         =   0 =  OK 
     *  2904 - assertIsTrue  "a2@bde.cc"                                                                  =   0 =  OK 
     *  2905 - assertIsTrue  "a@123.45.67.89"                                                             =   2 =  OK 
     *  2906 - assertIsTrue  "a@b.c.com"                                                                  =   0 =  OK 
     *  2907 - assertIsTrue  "a@b.com"                                                                    =   0 =  OK 
     *  2908 - assertIsTrue  "a@bc.com"                                                                   =   0 =  OK 
     *  2909 - assertIsTrue  "a@bcom"                                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2910 - assertIsTrue  "a@domain.com"                                                               =   0 =  OK 
     *  2911 - assertIsTrue  "a__z@provider.com"                                                          =   0 =  OK 
     *  2912 - assertIsTrue  "a_z%@gmail.com"                                                             =   0 =  OK 
     *  2913 - assertIsTrue  "aaron@theinfo.org"                                                          =   0 =  OK 
     *  2914 - assertIsTrue  "ab@288.120.150.10.com"                                                      =   0 =  OK 
     *  2915 - assertIsTrue  "ab@[120.254.254.120]"                                                       =   2 =  OK 
     *  2916 - assertIsTrue  "ab@b-de.cc"                                                                 =   0 =  OK 
     *  2917 - assertIsTrue  "ab@c.com"                                                                   =   0 =  OK 
     *  2918 - assertIsTrue  "ab_c@bde.cc"                                                                =   0 =  OK 
     *  2919 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                             =   1 =  OK 
     *  2920 - assertIsTrue  "abc.efg@gmail.com"                                                          =   0 =  OK 
     *  2921 - assertIsTrue  "abc.xyz@gmail.com.in"                                                       =   0 =  OK 
     *  2922 - assertIsTrue  "abc123xyz@asdf.co.in"                                                       =   0 =  OK 
     *  2923 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                       =   0 =  OK 
     *  2924 - assertIsTrue  "abc@abc.abc"                                                                =   0 =  OK 
     *  2925 - assertIsTrue  "abc@abc.abcd"                                                               =   0 =  OK 
     *  2926 - assertIsTrue  "abc@abc.abcde"                                                              =   0 =  OK 
     *  2927 - assertIsTrue  "abc@abc.co.in"                                                              =   0 =  OK 
     *  2928 - assertIsTrue  "abc@abc.com.com.com.com"                                                    =   0 =  OK 
     *  2929 - assertIsTrue  "abc@gmail.com.my"                                                           =   0 =  OK 
     *  2930 - assertIsTrue  "abc\@def@example.com"                                                       =   0 =  OK 
     *  2931 - assertIsTrue  "abc\\@example.com"                                                          =   0 =  OK 
     *  2932 - assertIsTrue  "abcxyz123@qwert.com"                                                        =   0 =  OK 
     *  2933 - assertIsTrue  "alex@example.com"                                                           =   0 =  OK 
     *  2934 - assertIsTrue  "alireza@test.co.uk"                                                         =   0 =  OK 
     *  2935 - assertIsTrue  "asd-@asd.com"                                                               =   0 =  OK 
     *  2936 - assertIsTrue  "begeddov@jfinity.com"                                                       =   0 =  OK 
     *  2937 - assertIsTrue  "check@this.com"                                                             =   0 =  OK 
     *  2938 - assertIsTrue  "cog@wheel.com"                                                              =   0 =  OK 
     *  2939 - assertIsTrue  "customer/department=shipping@example.com"                                   =   0 =  OK 
     *  2940 - assertIsTrue  "d._.___d@gmail.com"                                                         =   0 =  OK 
     *  2941 - assertIsTrue  "d.j@server1.proseware.com"                                                  =   0 =  OK 
     *  2942 - assertIsTrue  "d.oy.smith@gmail.com"                                                       =   0 =  OK 
     *  2943 - assertIsTrue  "d23d@da9.co9"                                                               =   0 =  OK 
     *  2944 - assertIsTrue  "d_oy_smith@gmail.com"                                                       =   0 =  OK 
     *  2945 - assertIsTrue  "dasd-dasd@das.com.das"                                                      =   0 =  OK 
     *  2946 - assertIsTrue  "dasd.dadas@dasd.com"                                                        =   0 =  OK 
     *  2947 - assertIsTrue  "dasd_-@jdas.com"                                                            =   0 =  OK 
     *  2948 - assertIsTrue  "david.jones@proseware.com"                                                  =   0 =  OK 
     *  2949 - assertIsTrue  "dclo@us.ibm.com"                                                            =   0 =  OK 
     *  2950 - assertIsTrue  "dda_das@das-dasd.com"                                                       =   0 =  OK 
     *  2951 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                               =   0 =  OK 
     *  2952 - assertIsTrue  "digit-only-domain@123.com"                                                  =   0 =  OK 
     *  2953 - assertIsTrue  "doysmith@gmail.com"                                                         =   0 =  OK 
     *  2954 - assertIsTrue  "drp@drp.cz"                                                                 =   0 =  OK 
     *  2955 - assertIsTrue  "dsq!a?@das.com"                                                             =   0 =  OK 
     *  2956 - assertIsTrue  "email@domain.co.de"                                                         =   0 =  OK 
     *  2957 - assertIsTrue  "email@domain.com"                                                           =   0 =  OK 
     *  2958 - assertIsTrue  "email@example.co.uk"                                                        =   0 =  OK 
     *  2959 - assertIsTrue  "email@example.com"                                                          =   0 =  OK 
     *  2960 - assertIsTrue  "email@mail.gmail.com"                                                       =   0 =  OK 
     *  2961 - assertIsTrue  "email@subdomain.domain.com"                                                 =   0 =  OK 
     *  2962 - assertIsTrue  "example@example.co"                                                         =   0 =  OK 
     *  2963 - assertIsTrue  "f.f.f@bde.cc"                                                               =   0 =  OK 
     *  2964 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                      =   0 =  OK 
     *  2965 - assertIsTrue  "first-name-last-name@d-a-n.com"                                             =   0 =  OK 
     *  2966 - assertIsTrue  "firstname+lastname@domain.com"                                              =   0 =  OK 
     *  2967 - assertIsTrue  "firstname-lastname@domain.com"                                              =   0 =  OK 
     *  2968 - assertIsTrue  "firstname.lastname@domain.com"                                              =   0 =  OK 
     *  2969 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                                  =   0 =  OK 
     *  2970 - assertIsTrue  "futureTLD@somewhere.fooo"                                                   =   0 =  OK 
     *  2971 - assertIsTrue  "hello.me_1@email.com"                                                       =   0 =  OK 
     *  2972 - assertIsTrue  "hello7___@ca.com.pt"                                                        =   0 =  OK 
     *  2973 - assertIsTrue  "info@ermaelan.com"                                                          =   0 =  OK 
     *  2974 - assertIsTrue  "j.s@server1.proseware.com"                                                  =   0 =  OK 
     *  2975 - assertIsTrue  "j@proseware.com9"                                                           =   0 =  OK 
     *  2976 - assertIsTrue  "j_9@[129.126.118.1]"                                                        =   2 =  OK 
     *  2977 - assertIsTrue  "jinujawad6s@gmail.com"                                                      =   0 =  OK 
     *  2978 - assertIsTrue  "john.doe@example.com"                                                       =   0 =  OK 
     *  2979 - assertIsTrue  "john.o'doe@example.com"                                                     =   0 =  OK 
     *  2980 - assertIsTrue  "john.smith@example.com"                                                     =   0 =  OK 
     *  2981 - assertIsTrue  "jones@ms1.proseware.com"                                                    =   0 =  OK 
     *  2982 - assertIsTrue  "journaldev+100@gmail.com"                                                   =   0 =  OK 
     *  2983 - assertIsTrue  "journaldev-100@journaldev.net"                                              =   0 =  OK 
     *  2984 - assertIsTrue  "journaldev-100@yahoo-test.com"                                              =   0 =  OK 
     *  2985 - assertIsTrue  "journaldev-100@yahoo.com"                                                   =   0 =  OK 
     *  2986 - assertIsTrue  "journaldev.100@journaldev.com.au"                                           =   0 =  OK 
     *  2987 - assertIsTrue  "journaldev.100@yahoo.com"                                                   =   0 =  OK 
     *  2988 - assertIsTrue  "journaldev111@journaldev.com"                                               =   0 =  OK 
     *  2989 - assertIsTrue  "journaldev@1.com"                                                           =   0 =  OK 
     *  2990 - assertIsTrue  "journaldev@gmail.com.com"                                                   =   0 =  OK 
     *  2991 - assertIsTrue  "journaldev@yahoo.com"                                                       =   0 =  OK 
     *  2992 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                          =   0 =  OK 
     *  2993 - assertIsTrue  "js#internal@proseware.com"                                                  =   0 =  OK 
     *  2994 - assertIsTrue  "js*@proseware.com"                                                          =   0 =  OK 
     *  2995 - assertIsTrue  "js@proseware.com9"                                                          =   0 =  OK 
     *  2996 - assertIsTrue  "me+100@me.com"                                                              =   0 =  OK 
     *  2997 - assertIsTrue  "me+alpha@example.com"                                                       =   0 =  OK 
     *  2998 - assertIsTrue  "me-100@me.com"                                                              =   0 =  OK 
     *  2999 - assertIsTrue  "me-100@me.com.au"                                                           =   0 =  OK 
     *  3000 - assertIsTrue  "me-100@yahoo-test.com"                                                      =   0 =  OK 
     *  3001 - assertIsTrue  "me.100@me.com"                                                              =   0 =  OK 
     *  3002 - assertIsTrue  "me@aaronsw.com"                                                             =   0 =  OK 
     *  3003 - assertIsTrue  "me@company.co.uk"                                                           =   0 =  OK 
     *  3004 - assertIsTrue  "me@gmail.com"                                                               =   0 =  OK 
     *  3005 - assertIsTrue  "me@gmail.com"                                                               =   0 =  OK 
     *  3006 - assertIsTrue  "me@mail.s2.example.com"                                                     =   0 =  OK 
     *  3007 - assertIsTrue  "me@me.cu.uk"                                                                =   0 =  OK 
     *  3008 - assertIsTrue  "my.ownsite@ourearth.org"                                                    =   0 =  OK 
     *  3009 - assertIsFalse "myemail@sample"                                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3010 - assertIsTrue  "myemail@sample.com"                                                         =   0 =  OK 
     *  3011 - assertIsTrue  "mysite@you.me.net"                                                          =   0 =  OK 
     *  3012 - assertIsTrue  "o'hare@example.com"                                                         =   0 =  OK 
     *  3013 - assertIsTrue  "peter.example@domain.comau"                                                 =   0 =  OK 
     *  3014 - assertIsTrue  "peter.piper@example.com"                                                    =   0 =  OK 
     *  3015 - assertIsTrue  "peter_123@news.com"                                                         =   0 =  OK 
     *  3016 - assertIsTrue  "pio^_pio@factory.com"                                                       =   0 =  OK 
     *  3017 - assertIsTrue  "pio_#pio@factory.com"                                                       =   0 =  OK 
     *  3018 - assertIsTrue  "pio_pio@factory.com"                                                        =   0 =  OK 
     *  3019 - assertIsTrue  "pio_~pio@factory.com"                                                       =   0 =  OK 
     *  3020 - assertIsTrue  "piskvor@example.lighting"                                                   =   0 =  OK 
     *  3021 - assertIsTrue  "rss-dev@yahoogroups.com"                                                    =   0 =  OK 
     *  3022 - assertIsTrue  "someone+tag@somewhere.net"                                                  =   0 =  OK 
     *  3023 - assertIsTrue  "someone@somewhere.co.uk"                                                    =   0 =  OK 
     *  3024 - assertIsTrue  "someone@somewhere.com"                                                      =   0 =  OK 
     *  3025 - assertIsTrue  "something_valid@somewhere.tld"                                              =   0 =  OK 
     *  3026 - assertIsTrue  "tvf@tvf.cz"                                                                 =   0 =  OK 
     *  3027 - assertIsTrue  "user#@domain.co.in"                                                         =   0 =  OK 
     *  3028 - assertIsTrue  "user'name@domain.co.in"                                                     =   0 =  OK 
     *  3029 - assertIsTrue  "user+mailbox@example.com"                                                   =   0 =  OK 
     *  3030 - assertIsTrue  "user-name@domain.co.in"                                                     =   0 =  OK 
     *  3031 - assertIsTrue  "user.name@domain.com"                                                       =   0 =  OK 
     *  3032 - assertIsFalse ".user.name@domain.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3033 - assertIsFalse "user-name@domain.com."                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3034 - assertIsFalse "username@.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3035 - assertIsTrue  "user1@domain.com"                                                           =   0 =  OK 
     *  3036 - assertIsTrue  "user?name@domain.co.in"                                                     =   0 =  OK 
     *  3037 - assertIsTrue  "user@domain.co.in"                                                          =   0 =  OK 
     *  3038 - assertIsTrue  "user@domain.com"                                                            =   0 =  OK 
     *  3039 - assertIsFalse "user@domaincom"                                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3040 - assertIsTrue  "user_name@domain.co.in"                                                     =   0 =  OK 
     *  3041 - assertIsTrue  "user_name@domain.com"                                                       =   0 =  OK 
     *  3042 - assertIsTrue  "username@yahoo.corporate"                                                   =   0 =  OK 
     *  3043 - assertIsTrue  "username@yahoo.corporate.in"                                                =   0 =  OK 
     *  3044 - assertIsTrue  "username+something@domain.com"                                              =   0 =  OK 
     *  3045 - assertIsTrue  "vdv@dyomedea.com"                                                           =   0 =  OK 
     *  3046 - assertIsTrue  "xxxx@gmail.com"                                                             =   0 =  OK 
     *  3047 - assertIsTrue  "xxxxxx@yahoo.com"                                                           =   0 =  OK 
     *  3048 - assertIsTrue  "user+mailbox/department=shipping@example.com"                               =   0 =  OK 
     *  3049 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                                    =   4 =  OK 
     *  3050 - assertIsFalse "user@localserver"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3051 - assertIsTrue  "w.b.f@test.com"                                                             =   0 =  OK 
     *  3052 - assertIsTrue  "w.b.f@test.museum"                                                          =   0 =  OK 
     *  3053 - assertIsTrue  "yoursite@ourearth.com"                                                      =   0 =  OK 
     *  3054 - assertIsTrue  "~pio_pio@factory.com"                                                       =   0 =  OK 
     *  3055 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                   =   0 =  OK 
     *  3056 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3057 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3058 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  3059 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  3060 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3061 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"             =   0 =  OK 
     *  3062 - assertIsTrue  "valid@[1.1.1.1]"                                                            =   2 =  OK 
     *  3063 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3064 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                                     =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3065 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                            =   4 =  OK 
     *  3066 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"             =   4 =  OK 
     *  3067 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                                  =   4 =  OK 
     *  3068 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"                     =   4 =  OK 
     *  3069 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                       =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3070 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                                =   4 =  OK 
     *  3071 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                                =   4 =  OK 
     *  3072 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                          =   2 =  OK 
     *  3073 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                              =   2 =  OK 
     *  3074 - assertIsFalse "invalid@[10]"                                                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3075 - assertIsFalse "invalid@[10.1]"                                                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3076 - assertIsFalse "invalid@[10.1.52]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3077 - assertIsFalse "invalid@[256.256.256.256]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3078 - assertIsFalse "invalid@[IPv6:123456]"                                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3079 - assertIsFalse "invalid@[127.0.0.1.]"                                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  3080 - assertIsFalse "invalid@[127.0.0.1]."                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3081 - assertIsFalse "invalid@[127.0.0.1]x"                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3082 - assertIsFalse "invalid@domain1.com@domain2.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3083 - assertIsFalse "\"locál-part\"@example.com"                                                 =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3084 - assertIsFalse "invalid@[IPv6:1::2:]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3085 - assertIsFalse "invalid@[IPv6::1::1]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3086 - assertIsFalse "invalid@[]"                                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3087 - assertIsFalse "invalid@[111.111.111.111"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3088 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3089 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3090 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3091 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3092 - assertIsFalse "invalid@[IPv6:1111:1111]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3093 - assertIsFalse "\"invalid-qstring@example.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs --------------------------------------------------
     * 
     *  3094 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                                 =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3095 - assertIsTrue  "\"back\slash\"@sld.com"                                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3096 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                                =   1 =  OK 
     *  3097 - assertIsTrue  "\"quoted\"@sld.com"                                                         =   1 =  OK 
     *  3098 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                         =   1 =  OK 
     *  3099 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                         =   0 =  OK 
     *  3100 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                        =   0 =  OK 
     *  3101 - assertIsTrue  "01234567890@numbers-in-local.net"                                           =   0 =  OK 
     *  3102 - assertIsTrue  "a@single-character-in-local.org"                                            =   0 =  OK 
     *  3103 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org"  =   0 =  OK 
     *  3104 - assertIsTrue  "backticksarelegit@test.com"                                                 =   0 =  OK 
     *  3105 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                                 =   2 =  OK 
     *  3106 - assertIsTrue  "country-code-tld@sld.rw"                                                    =   0 =  OK 
     *  3107 - assertIsTrue  "country-code-tld@sld.uk"                                                    =   0 =  OK 
     *  3108 - assertIsTrue  "letters-in-sld@123.com"                                                     =   0 =  OK 
     *  3109 - assertIsTrue  "local@dash-in-sld.com"                                                      =   0 =  OK 
     *  3110 - assertIsTrue  "local@sld.newTLD"                                                           =   0 =  OK 
     *  3111 - assertIsTrue  "local@sub.domains.com"                                                      =   0 =  OK 
     *  3112 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                           =   0 =  OK 
     *  3113 - assertIsTrue  "one-character-third-level@a.example.com"                                    =   0 =  OK 
     *  3114 - assertIsTrue  "one-letter-sld@x.org"                                                       =   0 =  OK 
     *  3115 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                                   =   0 =  OK 
     *  3116 - assertIsTrue  "single-character-in-sld@x.org"                                              =   0 =  OK 
     *  3117 - assertIsTrue  "uncommon-tld@sld.mobi"                                                      =   0 =  OK 
     *  3118 - assertIsTrue  "uncommon-tld@sld.museum"                                                    =   0 =  OK 
     *  3119 - assertIsTrue  "uncommon-tld@sld.travel"                                                    =   0 =  OK 
     *  3120 - assertIsFalse "invalid"                                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3121 - assertIsFalse "invalid@"                                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3122 - assertIsFalse "invalid @"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3123 - assertIsFalse "invalid@[555.666.777.888]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3124 - assertIsFalse "invalid@[IPv6:123456]"                                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3125 - assertIsFalse "invalid@[127.0.0.1.]"                                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  3126 - assertIsFalse "invalid@[127.0.0.1]."                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3127 - assertIsFalse "invalid@[127.0.0.1]x"                                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3128 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3129 - assertIsFalse "@missing-local.org"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3130 - assertIsFalse "IP-and-port@127.0.0.1:25"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3131 - assertIsFalse "another-invalid-ip@127.0.0.256"                                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3132 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3133 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3134 - assertIsFalse "invalid-ip@127.0.0.1.26"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3135 - assertIsFalse "local-ends-with-dot.@sld.com"                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3136 - assertIsFalse "missing-at-sign.net"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3137 - assertIsFalse "missing-sld@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3138 - assertIsFalse "missing-tld@sld."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3139 - assertIsFalse "sld-ends-with-dash@sld-.com"                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3140 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3141 - assertIsFalse "two..consecutive-dots@sld.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3142 - assertIsTrue  "unbracketed-IP@127.0.0.1"                                                   =   2 =  OK 
     *  3143 - assertIsFalse "underscore.error@example.com_"                                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php -------------------------------------------------
     * 
     *  3144 - assertIsTrue  "first.last@iana.org"                                                        =   0 =  OK 
     *  3145 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org"  =   0 =  OK 
     *  3146 - assertIsTrue  "\"first\\"last\"@iana.org"                                                  =   1 =  OK 
     *  3147 - assertIsTrue  "\"first@last\"@iana.org"                                                    =   1 =  OK 
     *  3148 - assertIsTrue  "\"first\\last\"@iana.org"                                                   =   1 =  OK 
     *  3149 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  3150 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  3151 - assertIsTrue  "first.last@[12.34.56.78]"                                                   =   2 =  OK 
     *  3152 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                          =   4 =  OK 
     *  3153 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                           =   4 =  OK 
     *  3154 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                          =   4 =  OK 
     *  3155 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                          =   4 =  OK 
     *  3156 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"                  =   4 =  OK 
     *  3157 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  3158 - assertIsTrue  "first.last@3com.com"                                                        =   0 =  OK 
     *  3159 - assertIsTrue  "first.last@123.iana.org"                                                    =   0 =  OK 
     *  3160 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3161 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                      =   4 =  OK 
     *  3162 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                      =   1 =  OK 
     *  3163 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                                  =   1 =  OK 
     *  3164 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                                    =   1 =  OK 
     *  3165 - assertIsTrue  "\"Abc@def\"@iana.org"                                                       =   1 =  OK 
     *  3166 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                                 =   1 =  OK 
     *  3167 - assertIsTrue  "user+mailbox@iana.org"                                                      =   0 =  OK 
     *  3168 - assertIsTrue  "$A12345@iana.org"                                                           =   0 =  OK 
     *  3169 - assertIsTrue  "!def!xyz%abc@iana.org"                                                      =   0 =  OK 
     *  3170 - assertIsTrue  "_somename@iana.org"                                                         =   0 =  OK 
     *  3171 - assertIsTrue  "dclo@us.ibm.com"                                                            =   0 =  OK 
     *  3172 - assertIsTrue  "peter.piper@iana.org"                                                       =   0 =  OK 
     *  3173 - assertIsTrue  "test@iana.org"                                                              =   0 =  OK 
     *  3174 - assertIsTrue  "TEST@iana.org"                                                              =   0 =  OK 
     *  3175 - assertIsTrue  "1234567890@iana.org"                                                        =   0 =  OK 
     *  3176 - assertIsTrue  "test+test@iana.org"                                                         =   0 =  OK 
     *  3177 - assertIsTrue  "test-test@iana.org"                                                         =   0 =  OK 
     *  3178 - assertIsTrue  "t*est@iana.org"                                                             =   0 =  OK 
     *  3179 - assertIsTrue  "+1~1+@iana.org"                                                             =   0 =  OK 
     *  3180 - assertIsTrue  "{_test_}@iana.org"                                                          =   0 =  OK 
     *  3181 - assertIsTrue  "test.test@iana.org"                                                         =   0 =  OK 
     *  3182 - assertIsTrue  "\"test.test\"@iana.org"                                                     =   1 =  OK 
     *  3183 - assertIsTrue  "test.\"test\"@iana.org"                                                     =   1 =  OK 
     *  3184 - assertIsTrue  "\"test@test\"@iana.org"                                                     =   1 =  OK 
     *  3185 - assertIsTrue  "test@123.123.123.x123"                                                      =   0 =  OK 
     *  3186 - assertIsTrue  "test@123.123.123.123"                                                       =   2 =  OK 
     *  3187 - assertIsTrue  "test@[123.123.123.123]"                                                     =   2 =  OK 
     *  3188 - assertIsTrue  "test@example.iana.org"                                                      =   0 =  OK 
     *  3189 - assertIsTrue  "test@example.example.iana.org"                                              =   0 =  OK 
     *  3190 - assertIsTrue  "customer/department@iana.org"                                               =   0 =  OK 
     *  3191 - assertIsTrue  "_Yosemite.Sam@iana.org"                                                     =   0 =  OK 
     *  3192 - assertIsTrue  "~@iana.org"                                                                 =   0 =  OK 
     *  3193 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                                 =   1 =  OK 
     *  3194 - assertIsTrue  "Ima.Fool@iana.org"                                                          =   0 =  OK 
     *  3195 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                      =   1 =  OK 
     *  3196 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                                    =   1 =  OK 
     *  3197 - assertIsTrue  "\"first\".\"last\"@iana.org"                                                =   1 =  OK 
     *  3198 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                         =   1 =  OK 
     *  3199 - assertIsTrue  "\"first\".last@iana.org"                                                    =   1 =  OK 
     *  3200 - assertIsTrue  "first.\"last\"@iana.org"                                                    =   1 =  OK 
     *  3201 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                                     =   1 =  OK 
     *  3202 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                         =   1 =  OK 
     *  3203 - assertIsTrue  "\"first.middle.last\"@iana.org"                                             =   1 =  OK 
     *  3204 - assertIsTrue  "\"first..last\"@iana.org"                                                   =   1 =  OK 
     *  3205 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                         =   1 =  OK 
     *  3206 - assertIsFalse "first.last @iana.orgin"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3207 - assertIsTrue  "\"test blah\"@iana.orgin"                                                   =   1 =  OK 
     *  3208 - assertIsTrue  "name.lastname@domain.com"                                                   =   0 =  OK 
     *  3209 - assertIsTrue  "a@bar.com"                                                                  =   0 =  OK 
     *  3210 - assertIsTrue  "aaa@[123.123.123.123]"                                                      =   2 =  OK 
     *  3211 - assertIsTrue  "a-b@bar.com"                                                                =   0 =  OK 
     *  3212 - assertIsFalse "+@b.c"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3213 - assertIsTrue  "+@b.com"                                                                    =   0 =  OK 
     *  3214 - assertIsTrue  "a@b.co-foo.uk"                                                              =   0 =  OK 
     *  3215 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                         =   1 =  OK 
     *  3216 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                         =   1 =  OK 
     *  3217 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                           =   0 =  OK 
     *  3218 - assertIsTrue  "foobar@192.168.0.1"                                                         =   2 =  OK 
     *  3219 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                          =   6 =  OK 
     *  3220 - assertIsTrue  "user%uucp!path@berkeley.edu"                                                =   0 =  OK 
     *  3221 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                                  =   0 =  OK 
     *  3222 - assertIsTrue  "test@test.com"                                                              =   0 =  OK 
     *  3223 - assertIsTrue  "test@xn--example.com"                                                       =   0 =  OK 
     *  3224 - assertIsTrue  "test@example.com"                                                           =   0 =  OK 
     *  3225 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                   =   0 =  OK 
     *  3226 - assertIsTrue  "first\@last@iana.org"                                                       =   0 =  OK 
     *  3227 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                                   =   0 =  OK 
     *  3228 - assertIsFalse "first.last@example.123"                                                     =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3229 - assertIsFalse "first.last@comin"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3230 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                                  =   1 =  OK 
     *  3231 - assertIsTrue  "Abc\@def@iana.org"                                                          =   0 =  OK 
     *  3232 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                      =   0 =  OK 
     *  3233 - assertIsFalse "Joe.\Blow@iana.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3234 - assertIsTrue  "first.last@sub.do.com"                                                      =   0 =  OK 
     *  3235 - assertIsFalse "first.last"                                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3236 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                            =   0 =  OK 
     *  3237 - assertIsTrue  "\"hello world\"@example.com"                                                =   1 =  OK 
     *  3238 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3239 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                               =   1 =  OK 
     *  3240 - assertIsTrue  "example+tag@gmail.com"                                                      =   0 =  OK 
     *  3241 - assertIsFalse ".ann..other.@example.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3242 - assertIsTrue  "ann.other@example.com"                                                      =   0 =  OK 
     *  3243 - assertIsTrue  "something@something.something"                                              =   0 =  OK 
     *  3244 - assertIsTrue  "c@(Chris's host.)public.examplein"                                          =   6 =  OK 
     *  3245 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3246 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3247 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3248 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3249 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3250 - assertIsFalse "first().last@iana.orgin"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3251 - assertIsFalse "pete(his account)@silly.test(his host)"                                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3252 - assertIsFalse "jdoe@machine(comment). examplein"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3253 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3254 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3255 - assertIsFalse "1234 @ local(blah) .machine .examplein"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3256 - assertIsFalse "a@bin"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3257 - assertIsFalse "a@barin"                                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3258 - assertIsFalse "@about.museum"                                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3259 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3260 - assertIsFalse ".first.last@iana.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3261 - assertIsFalse "first.last.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3262 - assertIsFalse "first..last@iana.org"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3263 - assertIsFalse "\"first\"last\"@iana.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3264 - assertIsFalse "first.last@"                                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3265 - assertIsFalse "first.last@-xample.com"                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3266 - assertIsFalse "first.last@exampl-.com"                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3267 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3268 - assertIsFalse "abc\@iana.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3269 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3270 - assertIsFalse "abc@def@iana.org"                                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3271 - assertIsFalse "@iana.org"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3272 - assertIsFalse "doug@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3273 - assertIsFalse "\"qu@iana.org"                                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  3274 - assertIsFalse "ote\"@iana.org"                                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3275 - assertIsFalse ".dot@iana.org"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3276 - assertIsFalse "dot.@iana.org"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3277 - assertIsFalse "two..dot@iana.org"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3278 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3279 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3280 - assertIsFalse "hello world@iana.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3281 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3282 - assertIsFalse "test.iana.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3283 - assertIsFalse "test.@iana.org"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3284 - assertIsFalse "test..test@iana.org"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3285 - assertIsFalse ".test@iana.org"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3286 - assertIsFalse "test@test@iana.org"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3287 - assertIsFalse "test@@iana.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3288 - assertIsFalse "-- test --@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3289 - assertIsFalse "[test]@iana.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3290 - assertIsFalse "\"test\"test\"@iana.org"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3291 - assertIsFalse "()[]\;:.><@iana.org"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3292 - assertIsFalse "test@."                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3293 - assertIsFalse "test@example."                                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3294 - assertIsFalse "test@.org"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3295 - assertIsFalse "test@[123.123.123.123"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3296 - assertIsFalse "test@123.123.123.123]"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3297 - assertIsFalse "NotAnEmail"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3298 - assertIsFalse "@NotAnEmail"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3299 - assertIsFalse "\"test\"blah\"@iana.org"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3300 - assertIsFalse ".wooly@iana.org"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3301 - assertIsFalse "wo..oly@iana.org"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3302 - assertIsFalse "pootietang.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3303 - assertIsFalse ".@iana.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3304 - assertIsFalse "Ima Fool@iana.org"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3305 - assertIsFalse "foo@[\1.2.3.4]"                                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3306 - assertIsFalse "first.\"\".last@iana.org"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3307 - assertIsFalse "first\last@iana.org"                                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3308 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3309 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3310 - assertIsFalse "cal(foo(bar)@iamcal.com"                                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3311 - assertIsFalse "cal(foo)bar)@iamcal.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3312 - assertIsFalse "cal(foo\)@iamcal.com"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3313 - assertIsFalse "first(middle)last@iana.org"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3314 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3315 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3316 - assertIsFalse ".@"                                                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3317 - assertIsFalse "@bar.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3318 - assertIsFalse "@@bar.com"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3319 - assertIsFalse "aaa.com"                                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3320 - assertIsFalse "aaa@.com"                                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3321 - assertIsFalse "aaa@.123"                                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3322 - assertIsFalse "aaa@[123.123.123.123]a"                                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3323 - assertIsFalse "aaa@[123.123.123.333]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3324 - assertIsFalse "a@bar.com."                                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3325 - assertIsFalse "a@-b.com"                                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3326 - assertIsFalse "a@b-.com"                                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3327 - assertIsFalse "-@..com"                                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3328 - assertIsFalse "-@a..com"                                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3329 - assertIsFalse "@about.museum-"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3330 - assertIsFalse "test@...........com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3331 - assertIsFalse "first.last@[IPv6::]"                                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3332 - assertIsFalse "first.last@[IPv6::::]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3333 - assertIsFalse "first.last@[IPv6::b4]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3334 - assertIsFalse "first.last@[IPv6::::b4]"                                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3335 - assertIsFalse "first.last@[IPv6::b3:b4]"                                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3336 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3337 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3338 - assertIsFalse "first.last@[IPv6:a1:]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  3339 - assertIsFalse "first.last@[IPv6:a1:::]"                                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3340 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3341 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3342 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3343 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3344 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3345 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3346 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3347 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3348 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3349 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3350 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3351 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3352 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                        =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  3353 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3354 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3355 - assertIsFalse "first.last@[IPv6::a2::b4]"                                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3356 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3357 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3358 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3359 - assertIsFalse "first.last@[.12.34.56.78]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  3360 - assertIsFalse "first.last@[12.34.56.789]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3361 - assertIsFalse "first.last@[::12.34.56.78]"                                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3362 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                            =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3363 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                            =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  3364 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3365 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3366 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"             =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3367 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3368 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3369 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3370 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3371 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3372 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3373 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3374 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3375 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3376 - assertIsTrue  "first.last@[IPv6:::]"                                                       =   4 =  OK 
     *  3377 - assertIsTrue  "first.last@[IPv6:::b4]"                                                     =   4 =  OK 
     *  3378 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                                  =   4 =  OK 
     *  3379 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                                   =   4 =  OK 
     *  3380 - assertIsTrue  "first.last@[IPv6:a1::]"                                                     =   4 =  OK 
     *  3381 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                                  =   4 =  OK 
     *  3382 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                                    =   4 =  OK 
     *  3383 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                                    =   4 =  OK 
     *  3384 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3385 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3386 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3387 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3388 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                          =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3389 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3390 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3391 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3392 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3393 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                       =   4 =  OK 
     * 
     * ---- https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/ -------------------------------
     * 
     *  3394 - assertIsTrue  "hello@example.com"                                                          =   0 =  OK 
     *  3395 - assertIsTrue  "hello@example.co.uk"                                                        =   0 =  OK 
     *  3396 - assertIsTrue  "hello-2020@example.com"                                                     =   0 =  OK 
     *  3397 - assertIsTrue  "hello.2020@example.com"                                                     =   0 =  OK 
     *  3398 - assertIsTrue  "hello_2020@example.com"                                                     =   0 =  OK 
     *  3399 - assertIsTrue  "h@example.com"                                                              =   0 =  OK 
     *  3400 - assertIsTrue  "h@example-example.com"                                                      =   0 =  OK 
     *  3401 - assertIsTrue  "h@example-example-example.com"                                              =   0 =  OK 
     *  3402 - assertIsTrue  "h@example.example-example.com"                                              =   0 =  OK 
     *  3403 - assertIsTrue  "hello.world-2020@example.com"                                               =   0 =  OK 
     *  3404 - assertIsTrue  "hello@example_example.com"                                                  =   0 =  OK 
     *  3405 - assertIsTrue  "hello!+2020@example.com"                                                    =   0 =  OK 
     *  3406 - assertIsFalse "hello"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3407 - assertIsFalse "hello@2020@example.com"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3408 - assertIsFalse ".hello@example.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3409 - assertIsFalse "hello.@example.com"                                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3410 - assertIsFalse "hello..world@example.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3411 - assertIsFalse "hello@example.a"                                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  3412 - assertIsFalse "hello@example..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3413 - assertIsFalse "hello@.com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3414 - assertIsFalse "hello@.com."                                                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3415 - assertIsFalse "hello@-example.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3416 - assertIsFalse "hello@example.com-"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3417 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234xx@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3418 - assertIsTrue  "email@goes_here.com"                                                        =   0 =  OK 
     *  3419 - assertIsTrue  "double--dash@example.com"                                                   =   0 =  OK 
     * 
     * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------
     * 
     *  3420 - assertIsFalse ""                                                                           =  11 =  OK    Laenge: Eingabe ist Leerstring
     *  3421 - assertIsFalse " "                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3422 - assertIsFalse " jkt@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3423 - assertIsFalse "jkt@gmail.com "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3424 - assertIsFalse "jkt@ gmail.com"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3425 - assertIsFalse "jkt@g mail.com"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3426 - assertIsFalse "jkt @gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3427 - assertIsFalse "j kt@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- https://www.abstractapi.com/guides/java-email-validation --------------------------------------------------------------------
     * 
     *  3428 - assertIsTrue  "\"test123\"@gmail.com"                                                      =   1 =  OK 
     *  3429 - assertIsTrue  "test123@gmail.comcomco"                                                     =   0 =  OK 
     *  3430 - assertIsTrue  "test123@gmail.c"                                                            =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  3431 - assertIsTrue  "test1&23@gmail.com"                                                         =   0 =  OK 
     *  3432 - assertIsTrue  "test123@gmail.com"                                                          =   0 =  OK 
     *  3433 - assertIsFalse "test123@gmail..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3434 - assertIsFalse ".test123@gmail.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3435 - assertIsFalse "test123@gmail.com."                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3436 - assertIsFalse "test1Ὠ23@gmail.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- https://www.javatpoint.com/java-email-validation ----------------------------------------------------------------------------
     * 
     *  3437 - assertIsTrue  "javaTpoint@domain.co.in"                                                    =   0 =  OK 
     *  3438 - assertIsTrue  "javaTpoint@domain.com"                                                      =   0 =  OK 
     *  3439 - assertIsTrue  "javaTpoint.name@domain.com"                                                 =   0 =  OK 
     *  3440 - assertIsTrue  "javaTpoint#@domain.co.in"                                                   =   0 =  OK 
     *  3441 - assertIsTrue  "javaTpoint@domain.com"                                                      =   0 =  OK 
     *  3442 - assertIsFalse "javaTpoint@domaincom"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3443 - assertIsTrue  "javaTpoint@domain.co.in"                                                    =   0 =  OK 
     *  3444 - assertIsTrue  "12453@domain.com"                                                           =   0 =  OK 
     *  3445 - assertIsTrue  "javaTpoint.name@domain.com"                                                 =   0 =  OK 
     *  3446 - assertIsTrue  "1avaTpoint#@domain.co.in"                                                   =   0 =  OK 
     *  3447 - assertIsTrue  "javaTpoint@domain.co.in"                                                    =   0 =  OK 
     *  3448 - assertIsTrue  "javaTpoint@domain.com"                                                      =   0 =  OK 
     *  3449 - assertIsTrue  "javaTpoint.name@domain.com"                                                 =   0 =  OK 
     *  3450 - assertIsTrue  "javaTpoint#@domain.co.in"                                                   =   0 =  OK 
     *  3451 - assertIsTrue  "java'Tpoint@domain.com"                                                     =   0 =  OK 
     *  3452 - assertIsFalse ".javaTpoint@yahoo.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3453 - assertIsFalse "javaTpoint@domain.com."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3454 - assertIsFalse "javaTpoint#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3455 - assertIsFalse "javaTpoint@domain..com"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3456 - assertIsFalse "@yahoo.com"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3457 - assertIsFalse "javaTpoint#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3458 - assertIsFalse "12javaTpoint#domain.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     * 
     * ---- https://java2blog.com/validate-email-address-in-java/ -----------------------------------------------------------------------
     * 
     *  3459 - assertIsTrue  "admin@java2blog.com"                                                        =   0 =  OK 
     *  3460 - assertIsFalse "@java2blog.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3461 - assertIsTrue  "arpit.mandliya@java2blog.com"                                               =   0 =  OK 
     * 
     * ---- https://www.tutorialspoint.com/javaexamples/regular_email.htm ---------------------------------------------------------------
     * 
     *  3462 - assertIsTrue  "sairamkrishna@tutorialspoint.com"                                           =   0 =  OK 
     *  3463 - assertIsTrue  "kittuprasad700@gmail.com"                                                   =   0 =  OK 
     *  3464 - assertIsFalse "sairamkrishna_mammahe%google-india.com"                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3465 - assertIsTrue  "sairam.krishna@gmail-indai.com"                                             =   0 =  OK 
     *  3466 - assertIsTrue  "sai#@youtube.co.in"                                                         =   0 =  OK 
     *  3467 - assertIsFalse "kittu@domaincom"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3468 - assertIsFalse "kittu#gmail.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3469 - assertIsFalse "@pindom.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * ---- https://www.rohannagar.com/jmail/ -------------------------------------------------------------------------------------------
     * 
     *  3470 - assertIsFalse "\"qu@test.org"                                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  3471 - assertIsFalse "ote\"@test.org"                                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3472 - assertIsFalse "\"().:;<>[\]@example.com"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3473 - assertIsFalse "\"\"\"@iana.org"                                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3474 - assertIsFalse "Abc.example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3475 - assertIsFalse "A@b@c@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3476 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3477 - assertIsFalse "this is\"not\allowed@example.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3478 - assertIsFalse "this\ still\"not\allowed@example.com"                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3479 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3480 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3481 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3482 - assertIsFalse "plainaddress"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3483 - assertIsFalse "@example.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3484 - assertIsFalse ".email@example.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3485 - assertIsFalse "email.@example.com"                                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3486 - assertIsFalse "email..email@example.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3487 - assertIsFalse "email@-example.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3488 - assertIsFalse "email@111.222.333.44444"                                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  3489 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3490 - assertIsFalse "email@[12.34.44.56"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3491 - assertIsFalse "email@14.44.56.34]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3492 - assertIsFalse "email@[1.1.23.5f]"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3493 - assertIsFalse "email@[3.256.255.23]"                                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3494 - assertIsTrue  "\"first\\"last\"@test.org"                                                  =   1 =  OK 
     *  3495 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3496 - assertIsTrue  "first\@last@iana.org"                                                       =   0 =  OK 
     *  3497 - assertIsFalse "test@example.com "                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3498 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3499 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3500 - assertIsFalse "invalid@about.museum-"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3501 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3502 - assertIsFalse "abc@def@test.org"                                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3503 - assertIsTrue  "abc\@def@test.org"                                                          =   0 =  OK 
     *  3504 - assertIsFalse "abc\@test.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3505 - assertIsFalse "@test.org"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3506 - assertIsFalse ".dot@test.org"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3507 - assertIsFalse "dot.@test.org"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3508 - assertIsFalse "two..dot@test.org"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3509 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3510 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3511 - assertIsFalse "hello world@test.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3512 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3513 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3514 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3515 - assertIsFalse "test.test.org"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3516 - assertIsFalse "test.@test.org"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3517 - assertIsFalse "test..test@test.org"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3518 - assertIsFalse ".test@test.org"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3519 - assertIsFalse "test@test@test.org"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3520 - assertIsFalse "test@@test.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3521 - assertIsFalse "-- test --@test.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3522 - assertIsFalse "[test]@test.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3523 - assertIsFalse "\"test\"test\"@test.org"                                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3524 - assertIsFalse "()[]\;:.><@test.org"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3525 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3526 - assertIsFalse ".@test.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3527 - assertIsFalse "Ima Fool@test.org"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3528 - assertIsTrue  "\"first\\"last\"@test.org"                                                  =   1 =  OK 
     *  3529 - assertIsFalse "foo@[.2.3.4]"                                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3530 - assertIsFalse "first\last@test.org"                                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3531 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3532 - assertIsFalse "first(middle)last@test.org"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3533 - assertIsFalse "\"test\"test@test.com"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3534 - assertIsFalse "()@test.com"                                                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  3535 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  3536 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3537 - assertIsFalse "invalid@[1]"                                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3538 - assertIsFalse "ä📧@-foo"                                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3539 - assertIsFalse "ä📧@foo-"                                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3540 - assertIsFalse "first(comment(inner@comment.com"                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3541 - assertIsFalse "Joe A Smith <email@example.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3542 - assertIsFalse "Joe A Smith email@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3543 - assertIsFalse "Joe A Smith <email@example.com->"                                           =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3544 - assertIsFalse "Joe A Smith <email@-example.com->"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3545 - assertIsFalse "Joe A Smith <email>"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3546 - assertIsTrue  "\"email\"@example.com"                                                      =   1 =  OK 
     *  3547 - assertIsTrue  "\"first@last\"@test.org"                                                    =   1 =  OK 
     *  3548 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                                 =   1 =  OK 
     *  3549 - assertIsFalse "\"first\"last\"@test.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3550 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                         =   1 =  OK 
     *  3551 - assertIsFalse "\"first\last\"@test.org"                                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3552 - assertIsTrue  "\"Abc\@def\"@test.org"                                                      =   1 =  OK 
     *  3553 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                                  =   1 =  OK 
     *  3554 - assertIsFalse "\"Joe.\Blow\"@test.org"                                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3555 - assertIsTrue  "\"Abc@def\"@test.org"                                                       =   1 =  OK 
     *  3556 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                                   =   1 =  OK 
     *  3557 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@test.org"                                             =   1 =  OK 
     *  3558 - assertIsTrue  "\"[[ test ]]\"@test.org"                                                    =   1 =  OK 
     *  3559 - assertIsTrue  "\"test.test\"@test.org"                                                     =   1 =  OK 
     *  3560 - assertIsTrue  "test.\"test\"@test.org"                                                     =   1 =  OK 
     *  3561 - assertIsTrue  "\"test@test\"@test.org"                                                     =   1 =  OK 
     *  3562 - assertIsFalse "\"test tabulator  est\"@test.org"                                           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3563 - assertIsTrue  "\"first\".\"last\"@test.org"                                                =   1 =  OK 
     *  3564 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                         =   1 =  OK 
     *  3565 - assertIsTrue  "\"first\".last@test.org"                                                    =   1 =  OK 
     *  3566 - assertIsTrue  "first.\"last\"@test.org"                                                    =   1 =  OK 
     *  3567 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                                     =   1 =  OK 
     *  3568 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                         =   1 =  OK 
     *  3569 - assertIsTrue  "\"first.middle.last\"@test.org"                                             =   1 =  OK 
     *  3570 - assertIsTrue  "\"first..last\"@test.org"                                                   =   1 =  OK 
     *  3571 - assertIsTrue  "\"Unicode NULL \"@char.com"                                                 =   1 =  OK 
     *  3572 - assertIsFalse "\"test\blah\"@test.org"                                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3573 - assertIsFalse "\"testlah\"@test.org"                                                      =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3574 - assertIsTrue  "\"test\\"blah\"@test.org"                                                   =   1 =  OK 
     *  3575 - assertIsTrue  "\"first\\"last\"@test.org"                                                  =   1 =  OK 
     *  3576 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                           =   1 =  OK 
     *  3577 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3578 - assertIsTrue  "\"test blah\"@test.org"                                                     =   1 =  OK 
     *  3579 - assertIsTrue  "first.last@test.org"                                                        =   0 =  OK 
     *  3580 - assertIsFalse "jdoe@machine(comment).example"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3581 - assertIsFalse "first.\"\".last@test.org"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3582 - assertIsFalse "\"\"@test.org"                                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3583 - assertIsTrue  "very.common@example.org"                                                    =   0 =  OK 
     *  3584 - assertIsTrue  "test/test@test.com"                                                         =   0 =  OK 
     *  3585 - assertIsTrue  "user-@example.org"                                                          =   0 =  OK 
     *  3586 - assertIsTrue  "firstname.lastname@example.com"                                             =   0 =  OK 
     *  3587 - assertIsTrue  "email@subdomain.example.com"                                                =   0 =  OK 
     *  3588 - assertIsTrue  "firstname+lastname@example.com"                                             =   0 =  OK 
     *  3589 - assertIsTrue  "1234567890@example.com"                                                     =   0 =  OK 
     *  3590 - assertIsTrue  "email@example-one.com"                                                      =   0 =  OK 
     *  3591 - assertIsTrue  "_______@example.com"                                                        =   0 =  OK 
     *  3592 - assertIsTrue  "email@example.name"                                                         =   0 =  OK 
     *  3593 - assertIsTrue  "email@example.museum"                                                       =   0 =  OK 
     *  3594 - assertIsTrue  "email@example.co.jp"                                                        =   0 =  OK 
     *  3595 - assertIsTrue  "firstname-lastname@example.com"                                             =   0 =  OK 
     *  3596 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  3597 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3598 - assertIsTrue  "first.last@123.test.org"                                                    =   0 =  OK 
     *  3599 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3600 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org"  =   0 =  OK 
     *  3601 - assertIsTrue  "user+mailbox@test.org"                                                      =   0 =  OK 
     *  3602 - assertIsTrue  "customer/department=shipping@test.org"                                      =   0 =  OK 
     *  3603 - assertIsTrue  "$A12345@test.org"                                                           =   0 =  OK 
     *  3604 - assertIsTrue  "!def!xyz%abc@test.org"                                                      =   0 =  OK 
     *  3605 - assertIsTrue  "_somename@test.org"                                                         =   0 =  OK 
     *  3606 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                            =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3607 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3608 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3609 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3610 - assertIsTrue  "+@b.c"                                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3611 - assertIsTrue  "TEST@test.org"                                                              =   0 =  OK 
     *  3612 - assertIsTrue  "1234567890@test.org"                                                        =   0 =  OK 
     *  3613 - assertIsTrue  "test-test@test.org"                                                         =   0 =  OK 
     *  3614 - assertIsTrue  "t*est@test.org"                                                             =   0 =  OK 
     *  3615 - assertIsTrue  "+1~1+@test.org"                                                             =   0 =  OK 
     *  3616 - assertIsTrue  "{_test_}@test.org"                                                          =   0 =  OK 
     *  3617 - assertIsTrue  "valid@about.museum"                                                         =   0 =  OK 
     *  3618 - assertIsTrue  "a@bar"                                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3619 - assertIsFalse "cal(foo\@bar)@iamcal.com"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3620 - assertIsTrue  "(comment)test@test.org"                                                     =   6 =  OK 
     *  3621 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3622 - assertIsFalse "cal(foo\)bar)@iamcal.com"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3623 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.com"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3624 - assertIsFalse "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3625 - assertIsFalse "pete(his account)@silly.test(his host)"                                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3626 - assertIsFalse "first(abc\(def)@test.org"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3627 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3628 - assertIsTrue  "c@(Chris's host.)public.example"                                            =   6 =  OK 
     *  3629 - assertIsTrue  "_Yosemite.Sam@test.org"                                                     =   0 =  OK 
     *  3630 - assertIsTrue  "~@test.org"                                                                 =   0 =  OK 
     *  3631 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                              =   6 =  OK 
     *  3632 - assertIsTrue  "test@Bücher.ch"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3633 - assertIsTrue  "あいうえお@example.com"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3634 - assertIsTrue  "Pelé@example.com"                                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3635 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3636 - assertIsTrue  "我買@屋企.香港"                                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3637 - assertIsTrue  "二ノ宮@黒川.日本"                                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3638 - assertIsTrue  "медведь@с-балалайкой.рф"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3639 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3640 - assertIsTrue  "email@example.com (Joe Smith)"                                              =   6 =  OK 
     *  3641 - assertIsFalse "cal@iamcal(woo).(yay)com"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3642 - assertIsFalse "first(abc.def).last@test.org"                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3643 - assertIsFalse "first(a\"bc.def).last@test.org"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3644 - assertIsFalse "first.(\")middle.last(\")@test.org"                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  3645 - assertIsFalse "first().last@test.org"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3646 - assertIsTrue  "mymail\@hello@hotmail.com"                                                  =   0 =  OK 
     *  3647 - assertIsTrue  "Abc\@def@test.org"                                                          =   0 =  OK 
     *  3648 - assertIsTrue  "Fred\ Bloggs@test.org"                                                      =   0 =  OK 
     *  3649 - assertIsTrue  "Joe.\\Blow@test.org"                                                        =   0 =  OK 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ---------------------------
     * 
     *  3650 - assertIsTrue  "me@example.com"                                                             =   0 =  OK 
     *  3651 - assertIsTrue  "a.nonymous@example.com"                                                     =   0 =  OK 
     *  3652 - assertIsTrue  "name+tag@example.com"                                                       =   0 =  OK 
     *  3653 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                             =   2 =  OK 
     *  3654 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"          =   4 =  OK 
     *  3655 - assertIsTrue  "me(this is a comment)@example.com"                                          =   6 =  OK 
     *  3656 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                                  =   1 =  OK 
     *  3657 - assertIsTrue  "me.example@com"                                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3658 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                            =   0 =  OK 
     *  3659 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"        =   0 =  OK 
     *  3660 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                           =   0 =  OK 
     *  3661 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                          =   0 =  OK 
     *  3662 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                         =   0 =  OK 
     *  3663 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                        =   0 =  OK 
     *  3664 - assertIsFalse "NotAnEmail"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3665 - assertIsFalse "me@"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3666 - assertIsFalse "@example.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3667 - assertIsFalse ".me@example.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3668 - assertIsFalse "me@example..com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3669 - assertIsFalse "me\@example.com"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3670 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3671 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3672 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3673 - assertIsFalse "semico...@gmail.com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- my old tests ----------------------------------------------------------------------------------------------------------------
     * 
     *  3674 - assertIsTrue  "A@B.CD"                                                                     =   0 =  OK 
     *  3675 - assertIsTrue  "A.\"B\"@C.DE"                                                               =   1 =  OK 
     *  3676 - assertIsTrue  "A.B@[1.2.3.4]"                                                              =   2 =  OK 
     *  3677 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                          =   3 =  OK 
     *  3678 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                 =   4 =  OK 
     *  3679 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                             =   5 =  OK 
     *  3680 - assertIsTrue  "(A)B@C.DE"                                                                  =   6 =  OK 
     *  3681 - assertIsTrue  "A(B)@C.DE"                                                                  =   6 =  OK 
     *  3682 - assertIsTrue  "(A)\"B\"@C.DE"                                                              =   7 =  OK 
     *  3683 - assertIsTrue  "\"A\"(B)@C.DE"                                                              =   7 =  OK 
     *  3684 - assertIsTrue  "(A)B@[1.2.3.4]"                                                             =   2 =  OK 
     *  3685 - assertIsTrue  "A(B)@[1.2.3.4]"                                                             =   2 =  OK 
     *  3686 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                         =   8 =  OK 
     *  3687 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                         =   8 =  OK 
     *  3688 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                =   4 =  OK 
     *  3689 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                =   4 =  OK 
     *  3690 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                            =   9 =  OK 
     *  3691 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                            =   9 =  OK 
     *  3692 - assertIsTrue  "a.b.c.d@domain.com"                                                         =   0 =  OK 
     *  3693 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3694 - assertIsFalse "ABC.DEF.GHI.JKL"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3695 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3696 - assertIsFalse "ABC.DEF @GHI.JKL"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3697 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3698 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3699 - assertIsFalse "ABC.DEF@"                                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3700 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3701 - assertIsFalse "ABC@DEF@GHI.JKL"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3702 - assertIsFalse "@%^%#$@#$@#.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3703 - assertIsFalse "email.domain.com"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3704 - assertIsFalse "email@domain@domain.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3705 - assertIsFalse "first@last@test.org"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3706 - assertIsFalse "@test@a.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3707 - assertIsFalse "@\"someStringThatMightBe@email.com"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3708 - assertIsFalse "test@@test.com"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3709 - assertIsFalse "ABCDEF@GHIJKL"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3710 - assertIsFalse "ABC.DEF@GHIJKL"                                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3711 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3712 - assertIsFalse "ABC.DEF@GHI.JKL."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3713 - assertIsFalse "ABC..DEF@GHI.JKL"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3714 - assertIsFalse "ABC.DEF@GHI..JKL"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3715 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3716 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3717 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3718 - assertIsFalse "ABC.DEF@."                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3719 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                       =   1 =  OK 
     *  3720 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                        =   0 =  OK 
     *  3721 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                           =   0 =  OK 
     *  3722 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                           =   0 =  OK 
     *  3723 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                            =   0 =  OK 
     *  3724 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                            =   0 =  OK 
     *  3725 - assertIsFalse "ABC.DEF@GHI.2KL"                                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3726 - assertIsFalse "ABC.DEF@GHI.JK-"                                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3727 - assertIsFalse "ABC.DEF@GHI.JK_"                                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3728 - assertIsFalse "ABC.DEF@-HI.JKL"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3729 - assertIsFalse "ABC.DEF@_HI.JKL"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3730 - assertIsFalse "ABC DEF@GHI.DE"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3731 - assertIsFalse "ABC.DEF@GHI DE"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3732 - assertIsFalse "A . B & C . D"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3733 - assertIsFalse " A . B & C . D"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3734 - assertIsFalse "(?).[!]@{&}.<:>"                                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3735 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3736 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
     * 
     * ---- unsupported -----------------------------------------------------------------------------------------------------------------
     * 
     *  3737 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3738 - assertIsTrue  "Ärger.Öde@Übel.de"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3739 - assertIsTrue  "Smørrebrød@danmark.dk"                                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3740 - assertIsTrue  "ip6.without.brackets@1:2:3:4:5:6:7:8"                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3741 - assertIsTrue  "email.address.without@topleveldomain"                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3742 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3743 - assertIsFalse "@1st.relay,@2nd.relay:user@final.domain"                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------------------------
     * 
     * Fillup ist nicht aktiv
     * 
     * 
     * ---- Statistik -------------------------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1247   KORREKT 1202 =   96.391 % | FALSCH ERKANNT   45 =    3.609 % = Error 0
     *   ASSERT_IS_FALSE 2496   KORREKT 2482 =   99.439 % | FALSCH ERKANNT   14 =    0.561 % = Error 0
     * 
     *   GESAMT          3743   KORREKT 3684 =   98.424 % | FALSCH ERKANNT   59 =    1.576 % = Error 0
     * 
     * 
     *   Millisekunden    131 = 0.03499866417312316
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
