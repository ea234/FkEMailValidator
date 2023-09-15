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

  private static int          BREITE_SPALTE_EMAIL_AUSGABE       = 73;

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
     * ---- Correct ----------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  "n@d.td"                                                                  =   0 =  OK 
     *     2 - assertIsTrue  "1@2.td"                                                                  =   0 =  OK 
     *     3 - assertIsTrue  "12.345@678.90.tld"                                                       =   0 =  OK 
     *     4 - assertIsTrue  "name1.name2@domain1.tld"                                                 =   0 =  OK 
     *     5 - assertIsTrue  "name1+name2@domain1.tld"                                                 =   0 =  OK 
     *     6 - assertIsTrue  "name1-name2@domain1.tld"                                                 =   0 =  OK 
     *     7 - assertIsTrue  "name1.name2@subdomain1.domain1.tld"                                      =   0 =  OK 
     *     8 - assertIsTrue  "name1.name2@subdomain1.tu-domain1.tld"                                   =   0 =  OK 
     *     9 - assertIsTrue  "name1.name2@subdomain1.tu_domain1.tld"                                   =   0 =  OK 
     *    10 - assertIsTrue  "escaped.at\@.sign@domain.tld"                                            =   0 =  OK 
     *    11 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                    =   2 =  OK 
     *    12 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                       =   4 =  OK 
     *    13 - assertIsTrue  "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]"                              =   4 =  OK 
     *    14 - assertIsTrue  "ip4.without.brackets@1.2.3.4"                                            =   2 =  OK 
     *    15 - assertIsTrue  "\"string1\".name1@domain1.tld"                                           =   1 =  OK 
     *    16 - assertIsTrue  "name1.\"string1\"@domain1.tld"                                           =   1 =  OK 
     *    17 - assertIsTrue  "name1.\"string1\".name2@domain1.tld"                                     =   1 =  OK 
     *    18 - assertIsTrue  "name1.\"string1\".name2@subdomain1.domain1.tld"                          =   1 =  OK 
     *    19 - assertIsTrue  "\"string1\".\"quote2\".name1@domain1.tld"                                =   1 =  OK 
     *    20 - assertIsTrue  "\"string1\"@domain1.tld"                                                 =   1 =  OK 
     *    21 - assertIsTrue  "\"string1\\"qoute2\\"\"@domain1.tld"                                     =   1 =  OK 
     *    22 - assertIsTrue  "(comment1)name1@domain1.tld"                                             =   6 =  OK 
     *    23 - assertIsTrue  "(comment1)-name1@domain1.tld"                                            =   6 =  OK 
     *    24 - assertIsTrue  "name1(comment1)@domain1.tld"                                             =   6 =  OK 
     *    25 - assertIsTrue  "name1@(comment1)domain1.tld"                                             =   6 =  OK 
     *    26 - assertIsTrue  "name1@domain1.tld(comment1)"                                             =   6 =  OK 
     *    27 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                    =   2 =  OK 
     *    28 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                    =   2 =  OK 
     *    29 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                    =   2 =  OK 
     *    30 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                    =   2 =  OK 
     *    31 - assertIsTrue  "(comment1)\"string1\".name1@domain1.tld"                                 =   7 =  OK 
     *    32 - assertIsTrue  "(comment1)name1.\"string1\"@domain1.tld"                                 =   7 =  OK 
     *    33 - assertIsTrue  "name1.\"string1\"(comment1)@domain1.tld"                                 =   7 =  OK 
     *    34 - assertIsTrue  "\"string1\".name1(comment1)@domain1.tld"                                 =   7 =  OK 
     *    35 - assertIsTrue  "name1.\"string1\"@(comment1)domain1.tld"                                 =   7 =  OK 
     *    36 - assertIsTrue  "\"string1\".name1@domain1.tld(comment1)"                                 =   7 =  OK 
     *    37 - assertIsTrue  "<name1.name2@domain1.tld>"                                               =   0 =  OK 
     *    38 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                         =   0 =  OK 
     *    39 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                         =   0 =  OK 
     *    40 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                               =   0 =  OK 
     *    41 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                            =   2 =  OK 
     *    42 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                               =   4 =  OK 
     *    43 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                            =   2 =  OK 
     *    44 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                              =   4 =  OK 
     *    45 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"     =   6 =  OK 
     *    46 - assertIsTrue  "\"display name\" <local.part@(comment)domain-name.top_level_domain>"     =   6 =  OK 
     *    47 - assertIsTrue  "\"display name\" <(comment)local.part.\"string1\"@domain-name.top_level_domain>" =   7 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    48 - assertIsFalse null                                                                      =  10 =  OK    Laenge: Eingabe ist null
     *    49 - assertIsFalse ""                                                                        =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    50 - assertIsFalse "        "                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Sign ----------------------------------------------------------------------------------------------------
     * 
     *    51 - assertIsFalse "1234567890"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    52 - assertIsFalse "OnlyTextNoDotNoAt"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    53 - assertIsFalse "email.with.no.at.sign"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    54 - assertIsFalse "email.with.no.domain@"                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    55 - assertIsFalse "@@domain.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    56 - assertIsFalse "email.with.no.domain\@domain.com"                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    57 - assertIsFalse "email.with.no.domain\@.domain.com"                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    58 - assertIsFalse "email.with.no.domain\@123domain.com"                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    59 - assertIsFalse "email.with.no.domain\@_domain.com"                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    60 - assertIsFalse "email.with.no.domain\@-domain.com"                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    61 - assertIsFalse "email.with.double\@no.domain\@domain.com"                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    62 - assertIsTrue  "email.with.escaped.at\@.sign.version1@domain.com"                        =   0 =  OK 
     *    63 - assertIsTrue  "email.with.escaped.\@.sign.version2@domain.com"                          =   0 =  OK 
     *    64 - assertIsTrue  "email.with.escaped.at\@123.sign.version3@domain.com"                     =   0 =  OK 
     *    65 - assertIsTrue  "email.with.escaped.\@123.sign.version4@domain.com"                       =   0 =  OK 
     *    66 - assertIsTrue  "email.with.escaped.at\@-.sign.version5@domain.com"                       =   0 =  OK 
     *    67 - assertIsTrue  "email.with.escaped.\@-.sign.version6@domain.com"                         =   0 =  OK 
     *    68 - assertIsTrue  "email.with.escaped.at.sign.\@@domain.com"                                =   0 =  OK 
     *    69 - assertIsFalse "@@email.with.unescaped.at.sign.as.local.part"                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    70 - assertIsTrue  "\@@email.with.escaped.at.sign.as.local.part"                             =   0 =  OK 
     *    71 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    72 - assertIsFalse "@no.local.part.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    73 - assertIsFalse "@@@@@@only.multiple.at.signs.in.local.part.com"                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    74 - assertIsFalse "local.part.with.two.@at.signs@domain.com"                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    75 - assertIsFalse "local.part.ends.with.at.sign@@domain.com"                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    76 - assertIsFalse "local.part.with.at.sign.before@.point@domain.com"                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    77 - assertIsFalse "local.part.with.at.sign.after.@point@domain.com"                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    78 - assertIsFalse "local.part.with.double.at@@test@domain.com"                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    79 - assertIsFalse "(comment @) local.part.with.at.sign.in.comment@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    80 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"             =   6 =  OK 
     *    81 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *    82 - assertIsTrue  "\"String@\".local.part.with.at.sign.in.string@domain.com"                =   1 =  OK 
     *    83 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                            =   0 =  OK 
     *    84 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    85 - assertIsFalse "@.@.@.@.@.@@domain.com"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    86 - assertIsFalse "@.@.@."                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    87 - assertIsFalse "\@.\@@\@.\@"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    88 - assertIsFalse "@"                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    89 - assertIsFalse "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    90 - assertIsFalse "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    91 - assertIsFalse "EmailAdressWith@NoDots"                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    92 - assertIsFalse "..local.part.starts.with.dot@domain.com"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    93 - assertIsFalse "local.part.ends.with.dot.@domain.com"                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    94 - assertIsTrue  "local.part.with.dot.character@domain.com"                                =   0 =  OK 
     *    95 - assertIsFalse "local.part.with.dot.before..point@domain.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    96 - assertIsFalse "local.part.with.dot.after..point@domain.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    97 - assertIsFalse "local.part.with.double.dot..test@domain.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    98 - assertIsFalse "(comment .) local.part.with.dot.in.comment@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    99 - assertIsTrue  "\"string.\".local.part.with.dot.in.String@domain.com"                    =   1 =  OK 
     *   100 - assertIsFalse "\"string\.\".local.part.with.escaped.dot.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   101 - assertIsFalse ".@local.part.only.dot.domain.com"                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   102 - assertIsFalse "......@local.part.only.consecutive.dot.domain.com"                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   103 - assertIsFalse "...........@dot.domain.com"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   104 - assertIsFalse "name . <pointy.brackets1.with.dot.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   105 - assertIsFalse "<pointy.brackets2.with.dot.in.display.name@domain.com> name ."           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   106 - assertIsTrue  "domain.part@with.dot.com"                                                =   0 =  OK 
     *   107 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   108 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   109 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                                =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   110 - assertIsFalse "domain.part@with.dot.before..point.com"                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   111 - assertIsFalse "domain.part@with.dot.after..point.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   112 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   113 - assertIsTrue  "domain.part.with.dot.in.comment@(comment .)domain.com"                   =   6 =  OK 
     *   114 - assertIsFalse "domain.part.only.dot@..com"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   115 - assertIsFalse "top.level.domain.only@dot.."                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   116 - assertIsFalse "...local.part.starts.with.double.dot@domain.com"                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   117 - assertIsFalse "local.part.ends.with.double.dot..@domain.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   118 - assertIsFalse "local.part.with.double.dot..character@domain.com"                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   119 - assertIsFalse "local.part.with.double.dot.before...point@domain.com"                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   120 - assertIsFalse "local.part.with.double.dot.after...point@domain.com"                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   121 - assertIsFalse "local.part.with.double.double.dot....test@domain.com"                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   122 - assertIsFalse "(comment ..) local.part.with.double.dot.in.comment@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   123 - assertIsTrue  "\"string..\".local.part.with.double.dot.in.String@domain.com"            =   1 =  OK 
     *   124 - assertIsFalse "\"string\..\".local.part.with.escaped.double.dot.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   125 - assertIsFalse "..@local.part.only.double.dot.domain.com"                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   126 - assertIsFalse "............@local.part.only.consecutive.double.dot.domain.com"          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   127 - assertIsFalse ".................@double.dot.domain.com"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   128 - assertIsFalse "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   129 - assertIsFalse "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .."   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   130 - assertIsFalse "domain.part@with..double.dot.com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   131 - assertIsFalse "domain.part@..with.double.dot.at.domain.start.com"                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   132 - assertIsFalse "domain.part@with.double.dot.at.domain.end1...com"                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   133 - assertIsFalse "domain.part@with.double.dot.at.domain.end2.com.."                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   134 - assertIsFalse "domain.part@with.double.dot.before...point.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   135 - assertIsFalse "domain.part@with.double.dot.after...point.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   136 - assertIsFalse "domain.part@with.consecutive.double.dot....test.com"                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   137 - assertIsTrue  "domain.part.with.comment.with.double.dot@(comment ..)domain.com"         =   6 =  OK 
     *   138 - assertIsFalse "domain.part.only.double.dot@...com"                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   139 - assertIsFalse "top.level.domain.only@double.dot..."                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *   140 - assertIsTrue  "&local&&part&with&$@amp.com"                                             =   0 =  OK 
     *   141 - assertIsTrue  "*local**part*with*@asterisk.com"                                         =   0 =  OK 
     *   142 - assertIsTrue  "$local$$part$with$@dollar.com"                                           =   0 =  OK 
     *   143 - assertIsTrue  "=local==part=with=@equality.com"                                         =   0 =  OK 
     *   144 - assertIsTrue  "!local!!part!with!@exclamation.com"                                      =   0 =  OK 
     *   145 - assertIsTrue  "`local``part`with`@grave-accent.com"                                     =   0 =  OK 
     *   146 - assertIsTrue  "#local##part#with#@hash.com"                                             =   0 =  OK 
     *   147 - assertIsTrue  "-local--part-with-@hypen.com"                                            =   0 =  OK 
     *   148 - assertIsTrue  "{local{part{{with{@leftbracket.com"                                      =   0 =  OK 
     *   149 - assertIsTrue  "%local%%part%with%@percentage.com"                                       =   0 =  OK 
     *   150 - assertIsTrue  "|local||part|with|@pipe.com"                                             =   0 =  OK 
     *   151 - assertIsTrue  "+local++part+with+@plus.com"                                             =   0 =  OK 
     *   152 - assertIsTrue  "?local??part?with?@question.com"                                         =   0 =  OK 
     *   153 - assertIsTrue  "}local}part}}with}@rightbracket.com"                                     =   0 =  OK 
     *   154 - assertIsTrue  "~local~~part~with~@tilde.com"                                            =   0 =  OK 
     *   155 - assertIsTrue  "^local^^part^with^@xor.com"                                              =   0 =  OK 
     *   156 - assertIsTrue  "_local__part_with_@underscore.com"                                       =   0 =  OK 
     *   157 - assertIsFalse ":local::part:with:@colon.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   158 - assertIsTrue  "&.local.part.starts.with.amp@domain.com"                                 =   0 =  OK 
     *   159 - assertIsTrue  "local.part.ends.with.amp&@domain.com"                                    =   0 =  OK 
     *   160 - assertIsTrue  "local.part.with.amp&character@domain.com"                                =   0 =  OK 
     *   161 - assertIsTrue  "local.part.with.amp.before&.point@domain.com"                            =   0 =  OK 
     *   162 - assertIsTrue  "local.part.with.amp.after.&point@domain.com"                             =   0 =  OK 
     *   163 - assertIsTrue  "local.part.with.double.amp&&test@domain.com"                             =   0 =  OK 
     *   164 - assertIsFalse "(comment &) local.part.with.amp.in.comment@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   165 - assertIsTrue  "\"string&\".local.part.with.amp.in.String@domain.com"                    =   1 =  OK 
     *   166 - assertIsFalse "\"string\&\".local.part.with.escaped.amp.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   167 - assertIsTrue  "&@local.part.only.amp.domain.com"                                        =   0 =  OK 
     *   168 - assertIsTrue  "&&&&&&@local.part.only.consecutive.amp.domain.com"                       =   0 =  OK 
     *   169 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                              =   0 =  OK 
     *   170 - assertIsFalse "name & <pointy.brackets1.with.amp.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   171 - assertIsFalse "<pointy.brackets2.with.amp.in.display.name@domain.com> name &"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   172 - assertIsFalse "domain.part@with&amp.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   173 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   174 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   175 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   176 - assertIsFalse "domain.part@with.amp.before&.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   177 - assertIsFalse "domain.part@with.amp.after.&point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   178 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   179 - assertIsTrue  "domain.part.with.amp.in.comment@(comment &)domain.com"                   =   6 =  OK 
     *   180 - assertIsFalse "domain.part.only.amp@&.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   181 - assertIsFalse "top.level.domain.only@amp.&"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   182 - assertIsTrue  "*.local.part.starts.with.asterisk@domain.com"                            =   0 =  OK 
     *   183 - assertIsTrue  "local.part.ends.with.asterisk*@domain.com"                               =   0 =  OK 
     *   184 - assertIsTrue  "local.part.with.asterisk*character@domain.com"                           =   0 =  OK 
     *   185 - assertIsTrue  "local.part.with.asterisk.before*.point@domain.com"                       =   0 =  OK 
     *   186 - assertIsTrue  "local.part.with.asterisk.after.*point@domain.com"                        =   0 =  OK 
     *   187 - assertIsTrue  "local.part.with.double.asterisk**test@domain.com"                        =   0 =  OK 
     *   188 - assertIsFalse "(comment *) local.part.with.asterisk.in.comment@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   189 - assertIsTrue  "\"string*\".local.part.with.asterisk.in.String@domain.com"               =   1 =  OK 
     *   190 - assertIsFalse "\"string\*\".local.part.with.escaped.asterisk.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   191 - assertIsTrue  "*@local.part.only.asterisk.domain.com"                                   =   0 =  OK 
     *   192 - assertIsTrue  "******@local.part.only.consecutive.asterisk.domain.com"                  =   0 =  OK 
     *   193 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                         =   0 =  OK 
     *   194 - assertIsFalse "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   195 - assertIsFalse "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   196 - assertIsFalse "domain.part@with*asterisk.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   197 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   198 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   199 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   200 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   201 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   202 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   203 - assertIsTrue  "domain.part.with.asterisk.in.comment@(comment *)domain.com"              =   6 =  OK 
     *   204 - assertIsFalse "domain.part.only.asterisk@*.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   205 - assertIsFalse "top.level.domain.only@asterisk.*"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   206 - assertIsTrue  "$.local.part.starts.with.dollar@domain.com"                              =   0 =  OK 
     *   207 - assertIsTrue  "local.part.ends.with.dollar$@domain.com"                                 =   0 =  OK 
     *   208 - assertIsTrue  "local.part.with.dollar.before$.point@domain.com"                         =   0 =  OK 
     *   209 - assertIsTrue  "local.part.with.dollar.after.$point@domain.com"                          =   0 =  OK 
     *   210 - assertIsTrue  "local.part.with.double.dollar$$test@domain.com"                          =   0 =  OK 
     *   211 - assertIsFalse "(comment $) local.part.with.comment.with.dollar@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   212 - assertIsTrue  "\"quote$\".local.part.with.qoute.with.dollar@domain.com"                 =   1 =  OK 
     *   213 - assertIsTrue  "$@dollar.domain.com"                                                     =   0 =  OK 
     *   214 - assertIsTrue  "$$$$$$@dollar.domain.com"                                                =   0 =  OK 
     *   215 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                           =   0 =  OK 
     *   216 - assertIsFalse "name $ <pointy.brackets1.with.dollar@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   217 - assertIsFalse "<pointy.brackets2.with.dollar@domain.com> name $"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   218 - assertIsTrue  "=.local.part.starts.with.equality@domain.com"                            =   0 =  OK 
     *   219 - assertIsTrue  "local.part.ends.with.equality=@domain.com"                               =   0 =  OK 
     *   220 - assertIsTrue  "local.part.with.equality.before=.point@domain.com"                       =   0 =  OK 
     *   221 - assertIsTrue  "local.part.with.equality.after.=point@domain.com"                        =   0 =  OK 
     *   222 - assertIsTrue  "local.part.with.double.equality==test@domain.com"                        =   0 =  OK 
     *   223 - assertIsFalse "(comment =) local.part.with.comment.with.equality@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   224 - assertIsTrue  "\"quote=\".local.part.with.qoute.with.equality@domain.com"               =   1 =  OK 
     *   225 - assertIsTrue  "=@equality.domain.com"                                                   =   0 =  OK 
     *   226 - assertIsTrue  "======@equality.domain.com"                                              =   0 =  OK 
     *   227 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                         =   0 =  OK 
     *   228 - assertIsFalse "name = <pointy.brackets1.with.equality@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   229 - assertIsFalse "<pointy.brackets2.with.equality@domain.com> name ="                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   230 - assertIsTrue  "!.local.part.starts.with.exclamation@domain.com"                         =   0 =  OK 
     *   231 - assertIsTrue  "local.part.ends.with.exclamation!@domain.com"                            =   0 =  OK 
     *   232 - assertIsTrue  "local.part.with.exclamation.before!.point@domain.com"                    =   0 =  OK 
     *   233 - assertIsTrue  "local.part.with.exclamation.after.!point@domain.com"                     =   0 =  OK 
     *   234 - assertIsTrue  "local.part.with.double.exclamation!!test@domain.com"                     =   0 =  OK 
     *   235 - assertIsFalse "(comment !) local.part.with.comment.with.exclamation@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   236 - assertIsTrue  "\"quote!\".local.part.with.qoute.with.exclamation@domain.com"            =   1 =  OK 
     *   237 - assertIsTrue  "!@exclamation.domain.com"                                                =   0 =  OK 
     *   238 - assertIsTrue  "!!!!!!@exclamation.domain.com"                                           =   0 =  OK 
     *   239 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                      =   0 =  OK 
     *   240 - assertIsFalse "name ! <pointy.brackets1.with.exclamation@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   241 - assertIsFalse "<pointy.brackets2.with.exclamation@domain.com> name !"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   242 - assertIsTrue  "`.local.part.starts.with.grave-accent@domain.com"                        =   0 =  OK 
     *   243 - assertIsTrue  "local.part.ends.with.grave-accent`@domain.com"                           =   0 =  OK 
     *   244 - assertIsTrue  "local.part.with.grave-accent.before`.point@domain.com"                   =   0 =  OK 
     *   245 - assertIsTrue  "local.part.with.grave-accent.after.`point@domain.com"                    =   0 =  OK 
     *   246 - assertIsTrue  "local.part.with.double.grave-accent``test@domain.com"                    =   0 =  OK 
     *   247 - assertIsFalse "(comment `) local.part.with.comment.with.grave-accent@domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   248 - assertIsTrue  "\"quote`\".local.part.with.qoute.with.grave-accent@domain.com"           =   1 =  OK 
     *   249 - assertIsTrue  "`@grave-accent.domain.com"                                               =   0 =  OK 
     *   250 - assertIsTrue  "``````@grave-accent.domain.com"                                          =   0 =  OK 
     *   251 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                     =   0 =  OK 
     *   252 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   253 - assertIsFalse "<pointy.brackets2.with.grave-accent@domain.com> name `"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   254 - assertIsTrue  "#.local.part.starts.with.hash@domain.com"                                =   0 =  OK 
     *   255 - assertIsTrue  "local.part.ends.with.hash#@domain.com"                                   =   0 =  OK 
     *   256 - assertIsTrue  "local.part.with.hash.before#.point@domain.com"                           =   0 =  OK 
     *   257 - assertIsTrue  "local.part.with.hash.after.#point@domain.com"                            =   0 =  OK 
     *   258 - assertIsTrue  "local.part.with.double.hash##test@domain.com"                            =   0 =  OK 
     *   259 - assertIsFalse "(comment #) local.part.with.comment.with.hash@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   260 - assertIsTrue  "\"quote#\".local.part.with.qoute.with.hash@domain.com"                   =   1 =  OK 
     *   261 - assertIsTrue  "#@hash.domain.com"                                                       =   0 =  OK 
     *   262 - assertIsTrue  "######@hash.domain.com"                                                  =   0 =  OK 
     *   263 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                             =   0 =  OK 
     *   264 - assertIsFalse "name # <pointy.brackets1.with.hash@domain.com>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   265 - assertIsFalse "<pointy.brackets2.with.hash@domain.com> name #"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   266 - assertIsTrue  "-.local.part.starts.with.hypen@domain.com"                               =   0 =  OK 
     *   267 - assertIsTrue  "local.part.ends.with.hypen-@domain.com"                                  =   0 =  OK 
     *   268 - assertIsTrue  "local.part.with.hypen.before-.point@domain.com"                          =   0 =  OK 
     *   269 - assertIsTrue  "local.part.with.hypen.after.-point@domain.com"                           =   0 =  OK 
     *   270 - assertIsTrue  "local.part.with.double.hypen--test@domain.com"                           =   0 =  OK 
     *   271 - assertIsFalse "(comment -) local.part.with.comment.with.hypen@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   272 - assertIsTrue  "\"quote-\".local.part.with.qoute.with.hypen@domain.com"                  =   1 =  OK 
     *   273 - assertIsTrue  "-@hypen.domain.com"                                                      =   0 =  OK 
     *   274 - assertIsTrue  "------@hypen.domain.com"                                                 =   0 =  OK 
     *   275 - assertIsTrue  "-.-.-.-.-.-@hypen.domain.com"                                            =   0 =  OK 
     *   276 - assertIsFalse "name - <pointy.brackets1.with.hypen@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   277 - assertIsFalse "<pointy.brackets2.with.hypen@domain.com> name -"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   278 - assertIsTrue  "{.local.part.starts.with.leftbracket@domain.com"                         =   0 =  OK 
     *   279 - assertIsTrue  "local.part.ends.with.leftbracket{@domain.com"                            =   0 =  OK 
     *   280 - assertIsTrue  "local.part.with.leftbracket.before{.point@domain.com"                    =   0 =  OK 
     *   281 - assertIsTrue  "local.part.with.leftbracket.after.{point@domain.com"                     =   0 =  OK 
     *   282 - assertIsTrue  "local.part.with.double.leftbracket{{test@domain.com"                     =   0 =  OK 
     *   283 - assertIsFalse "(comment {) local.part.with.comment.with.leftbracket@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   284 - assertIsTrue  "\"quote{\".local.part.with.qoute.with.leftbracket@domain.com"            =   1 =  OK 
     *   285 - assertIsTrue  "{@leftbracket.domain.com"                                                =   0 =  OK 
     *   286 - assertIsTrue  "{{{{{{@leftbracket.domain.com"                                           =   0 =  OK 
     *   287 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                      =   0 =  OK 
     *   288 - assertIsFalse "name { <pointy.brackets1.with.leftbracket@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   289 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name {"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   290 - assertIsTrue  "%.local.part.starts.with.percentage@domain.com"                          =   0 =  OK 
     *   291 - assertIsTrue  "local.part.ends.with.percentage%@domain.com"                             =   0 =  OK 
     *   292 - assertIsTrue  "local.part.with.percentage.before%.point@domain.com"                     =   0 =  OK 
     *   293 - assertIsTrue  "local.part.with.percentage.after.%point@domain.com"                      =   0 =  OK 
     *   294 - assertIsTrue  "local.part.with.double.percentage%%test@domain.com"                      =   0 =  OK 
     *   295 - assertIsFalse "(comment %) local.part.with.comment.with.percentage@domain.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   296 - assertIsTrue  "\"quote%\".local.part.with.qoute.with.percentage@domain.com"             =   1 =  OK 
     *   297 - assertIsTrue  "%@percentage.domain.com"                                                 =   0 =  OK 
     *   298 - assertIsTrue  "%%%%%%@percentage.domain.com"                                            =   0 =  OK 
     *   299 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                       =   0 =  OK 
     *   300 - assertIsFalse "name % <pointy.brackets1.with.percentage@domain.com>"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   301 - assertIsFalse "<pointy.brackets2.with.percentage@domain.com> name %"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   302 - assertIsTrue  "|.local.part.starts.with.pipe@domain.com"                                =   0 =  OK 
     *   303 - assertIsTrue  "local.part.ends.with.pipe|@domain.com"                                   =   0 =  OK 
     *   304 - assertIsTrue  "local.part.with.pipe.before|.point@domain.com"                           =   0 =  OK 
     *   305 - assertIsTrue  "local.part.with.pipe.after.|point@domain.com"                            =   0 =  OK 
     *   306 - assertIsTrue  "local.part.with.double.pipe||test@domain.com"                            =   0 =  OK 
     *   307 - assertIsFalse "(comment |) local.part.with.comment.with.pipe@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   308 - assertIsTrue  "\"quote|\".local.part.with.qoute.with.pipe@domain.com"                   =   1 =  OK 
     *   309 - assertIsTrue  "|@pipe.domain.com"                                                       =   0 =  OK 
     *   310 - assertIsTrue  "||||||@pipe.domain.com"                                                  =   0 =  OK 
     *   311 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                             =   0 =  OK 
     *   312 - assertIsFalse "name | <pointy.brackets1.with.pipe@domain.com>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   313 - assertIsFalse "<pointy.brackets2.with.pipe@domain.com> name |"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   314 - assertIsTrue  "+.local.part.starts.with.plus@domain.com"                                =   0 =  OK 
     *   315 - assertIsTrue  "local.part.ends.with.plus+@domain.com"                                   =   0 =  OK 
     *   316 - assertIsTrue  "local.part.with.plus.before+.point@domain.com"                           =   0 =  OK 
     *   317 - assertIsTrue  "local.part.with.plus.after.+point@domain.com"                            =   0 =  OK 
     *   318 - assertIsTrue  "local.part.with.double.plus++test@domain.com"                            =   0 =  OK 
     *   319 - assertIsFalse "(comment +) local.part.with.comment.with.plus@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   320 - assertIsTrue  "\"quote+\".local.part.with.qoute.with.plus@domain.com"                   =   1 =  OK 
     *   321 - assertIsTrue  "+@plus.domain.com"                                                       =   0 =  OK 
     *   322 - assertIsTrue  "++++++@plus.domain.com"                                                  =   0 =  OK 
     *   323 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                             =   0 =  OK 
     *   324 - assertIsFalse "name + <pointy.brackets1.with.plus@domain.com>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   325 - assertIsFalse "<pointy.brackets2.with.plus@domain.com> name +"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   326 - assertIsTrue  "?.local.part.starts.with.question@domain.com"                            =   0 =  OK 
     *   327 - assertIsTrue  "local.part.ends.with.question?@domain.com"                               =   0 =  OK 
     *   328 - assertIsTrue  "local.part.with.question.before?.point@domain.com"                       =   0 =  OK 
     *   329 - assertIsTrue  "local.part.with.question.after.?point@domain.com"                        =   0 =  OK 
     *   330 - assertIsTrue  "local.part.with.double.question??test@domain.com"                        =   0 =  OK 
     *   331 - assertIsFalse "(comment ?) local.part.with.comment.with.question@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   332 - assertIsTrue  "\"quote?\".local.part.with.qoute.with.question@domain.com"               =   1 =  OK 
     *   333 - assertIsTrue  "?@question.domain.com"                                                   =   0 =  OK 
     *   334 - assertIsTrue  "??????@question.domain.com"                                              =   0 =  OK 
     *   335 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                         =   0 =  OK 
     *   336 - assertIsFalse "name ? <pointy.brackets1.with.question@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   337 - assertIsFalse "<pointy.brackets2.with.question@domain.com> name ?"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   338 - assertIsTrue  "}.local.part.starts.with.rightbracket@domain.com"                        =   0 =  OK 
     *   339 - assertIsTrue  "local.part.ends.with.rightbracket}@domain.com"                           =   0 =  OK 
     *   340 - assertIsTrue  "local.part.with.rightbracket.before}.point@domain.com"                   =   0 =  OK 
     *   341 - assertIsTrue  "local.part.with.rightbracket.after.}point@domain.com"                    =   0 =  OK 
     *   342 - assertIsTrue  "local.part.with.double.rightbracket}}test@domain.com"                    =   0 =  OK 
     *   343 - assertIsFalse "(comment }) local.part.with.comment.with.rightbracket@domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   344 - assertIsTrue  "\"quote}\".local.part.with.qoute.with.rightbracket@domain.com"           =   1 =  OK 
     *   345 - assertIsTrue  "}@rightbracket.domain.com"                                               =   0 =  OK 
     *   346 - assertIsTrue  "}}}}}}@rightbracket.domain.com"                                          =   0 =  OK 
     *   347 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                     =   0 =  OK 
     *   348 - assertIsFalse "name } <pointy.brackets1.with.rightbracket@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   349 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name }"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   350 - assertIsTrue  "~.local.part.starts.with.tilde@domain.com"                               =   0 =  OK 
     *   351 - assertIsTrue  "local.part.ends.with.tilde~@domain.com"                                  =   0 =  OK 
     *   352 - assertIsTrue  "local.part.with.tilde.before~.point@domain.com"                          =   0 =  OK 
     *   353 - assertIsTrue  "local.part.with.tilde.after.~point@domain.com"                           =   0 =  OK 
     *   354 - assertIsTrue  "local.part.with.double.tilde~~test@domain.com"                           =   0 =  OK 
     *   355 - assertIsFalse "(comment ~) local.part.with.comment.with.tilde@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   356 - assertIsTrue  "\"quote~\".local.part.with.qoute.with.tilde@domain.com"                  =   1 =  OK 
     *   357 - assertIsTrue  "~@tilde.domain.com"                                                      =   0 =  OK 
     *   358 - assertIsTrue  "~~~~~~@tilde.domain.com"                                                 =   0 =  OK 
     *   359 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                            =   0 =  OK 
     *   360 - assertIsFalse "name ~ <pointy.brackets1.with.tilde@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   361 - assertIsFalse "<pointy.brackets2.with.tilde@domain.com> name ~"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   362 - assertIsTrue  "^.local.part.starts.with.xor@domain.com"                                 =   0 =  OK 
     *   363 - assertIsTrue  "local.part.ends.with.xor^@domain.com"                                    =   0 =  OK 
     *   364 - assertIsTrue  "local.part.with.xor.before^.point@domain.com"                            =   0 =  OK 
     *   365 - assertIsTrue  "local.part.with.xor.after.^point@domain.com"                             =   0 =  OK 
     *   366 - assertIsTrue  "local.part.with.double.xor^^test@domain.com"                             =   0 =  OK 
     *   367 - assertIsFalse "(comment ^) local.part.with.comment.with.xor@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   368 - assertIsTrue  "\"quote^\".local.part.with.qoute.with.xor@domain.com"                    =   1 =  OK 
     *   369 - assertIsTrue  "^@xor.domain.com"                                                        =   0 =  OK 
     *   370 - assertIsTrue  "^^^^^^@xor.domain.com"                                                   =   0 =  OK 
     *   371 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                              =   0 =  OK 
     *   372 - assertIsFalse "name ^ <pointy.brackets1.with.xor@domain.com>"                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   373 - assertIsFalse "<pointy.brackets2.with.xor@domain.com> name ^"                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   374 - assertIsTrue  "_.local.part.starts.with.underscore@domain.com"                          =   0 =  OK 
     *   375 - assertIsTrue  "local.part.ends.with.underscore_@domain.com"                             =   0 =  OK 
     *   376 - assertIsTrue  "local.part.with.underscore.before_.point@domain.com"                     =   0 =  OK 
     *   377 - assertIsTrue  "local.part.with.underscore.after._point@domain.com"                      =   0 =  OK 
     *   378 - assertIsTrue  "local.part.with.double.underscore__test@domain.com"                      =   0 =  OK 
     *   379 - assertIsFalse "(comment _) local.part.with.comment.with.underscore@domain.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   380 - assertIsTrue  "\"quote_\".local.part.with.qoute.with.underscore@domain.com"             =   1 =  OK 
     *   381 - assertIsTrue  "_@underscore.domain.com"                                                 =   0 =  OK 
     *   382 - assertIsTrue  "______@underscore.domain.com"                                            =   0 =  OK 
     *   383 - assertIsTrue  "_._._._._._@underscore.domain.com"                                       =   0 =  OK 
     *   384 - assertIsFalse "name _ <pointy.brackets1.with.underscore@domain.com>"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   385 - assertIsFalse "<pointy.brackets2.with.underscore@domain.com> name _"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   386 - assertIsFalse ":.local.part.starts.with.colon@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   387 - assertIsFalse "local.part.ends.with.colon:@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   388 - assertIsFalse "local.part.with.colon.before:.point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   389 - assertIsFalse "local.part.with.colon.after.:point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   390 - assertIsFalse "local.part.with.double.colon::test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   391 - assertIsFalse "(comment :) local.part.with.comment.with.colon@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   392 - assertIsTrue  "\"quote:\".local.part.with.qoute.with.colon@domain.com"                  =   1 =  OK 
     *   393 - assertIsFalse ":@colon.domain.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   394 - assertIsFalse "::::::@colon.domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   395 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   396 - assertIsFalse "name : <pointy.brackets1.with.colon@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   397 - assertIsFalse "<pointy.brackets2.with.colon@domain.com> name :"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   398 - assertIsFalse "(.local.part.starts.with.leftbracket@domain.com"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   399 - assertIsFalse "local.part.ends.with.leftbracket(@domain.com"                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   400 - assertIsFalse "local.part.with.leftbracket.before(.point@domain.com"                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   401 - assertIsFalse "local.part.with.leftbracket.after.(point@domain.com"                     = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   402 - assertIsFalse "local.part.with.double.leftbracket((test@domain.com"                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   403 - assertIsFalse "(comment () local.part.with.comment.with.leftbracket@domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   404 - assertIsTrue  "\"quote(\".local.part.with.qoute.with.leftbracket@domain.com"            =   1 =  OK 
     *   405 - assertIsFalse "(@leftbracket.domain.com"                                                =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   406 - assertIsFalse "((((((@leftbracket.domain.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   407 - assertIsFalse "(()(((@leftbracket.domain.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   408 - assertIsFalse "((<)>(((@leftbracket.domain.com"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   409 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   410 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket@domain.com>"                   =   0 =  OK 
     *   411 - assertIsTrue  "<pointy.brackets2.with.leftbracket@domain.com> name ("                   =   0 =  OK 
     *   412 - assertIsFalse "\.local.part.starts.with.slash@domain.com"                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   413 - assertIsFalse "local.part.ends.with.slash\@domain.com"                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   414 - assertIsFalse "local.part.with.slash.before\.point@domain.com"                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   415 - assertIsFalse "local.part.with.slash.after.\point@domain.com"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   416 - assertIsTrue  "local.part.with.double.slash\\test@domain.com"                           =   0 =  OK 
     *   417 - assertIsFalse "(comment \) local.part.with.comment.with.slash@domain.com"               =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   418 - assertIsFalse "\"quote\\".local.part.with.qoute.with.slash@domain.com"                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   419 - assertIsFalse "\@slash.domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   420 - assertIsTrue  "\\\\\\@slash.domain.com"                                                 =   0 =  OK 
     *   421 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   422 - assertIsFalse "name \ <pointy.brackets1.with.slash@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   423 - assertIsFalse "<pointy.brackets2.with.slash@domain.com> name \"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   424 - assertIsFalse ").local.part.starts.with.rightbracket@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   425 - assertIsFalse "local.part.ends.with.rightbracket)@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   426 - assertIsFalse "local.part.with.rightbracket.before).point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   427 - assertIsFalse "local.part.with.rightbracket.after.)point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   428 - assertIsFalse "local.part.with.double.rightbracket))test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   429 - assertIsFalse "(comment )) local.part.with.comment.with.rightbracket@domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   430 - assertIsTrue  "\"quote)\".local.part.with.qoute.with.rightbracket@domain.com"           =   1 =  OK 
     *   431 - assertIsFalse ")@rightbracket.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   432 - assertIsFalse "))))))@rightbracket.domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   433 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   434 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket@domain.com>"                  =   0 =  OK 
     *   435 - assertIsTrue  "<pointy.brackets2.with.rightbracket@domain.com> name )"                  =   0 =  OK 
     *   436 - assertIsFalse "[.local.part.starts.with.leftbracket@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   437 - assertIsFalse "local.part.ends.with.leftbracket[@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   438 - assertIsFalse "local.part.with.leftbracket.before[.point@domain.com"                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   439 - assertIsFalse "local.part.with.leftbracket.after.[point@domain.com"                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   440 - assertIsFalse "local.part.with.double.leftbracket[[test@domain.com"                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   441 - assertIsFalse "(comment [) local.part.with.comment.with.leftbracket@domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   442 - assertIsTrue  "\"quote[\".local.part.with.qoute.with.leftbracket@domain.com"            =   1 =  OK 
     *   443 - assertIsFalse "[@leftbracket.domain.com"                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   444 - assertIsFalse "[[[[[[@leftbracket.domain.com"                                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   445 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   446 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   447 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name ["                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   448 - assertIsFalse "].local.part.starts.with.rightbracket@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   449 - assertIsFalse "local.part.ends.with.rightbracket]@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   450 - assertIsFalse "local.part.with.rightbracket.before].point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   451 - assertIsFalse "local.part.with.rightbracket.after.]point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   452 - assertIsFalse "local.part.with.double.rightbracket]]test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   453 - assertIsFalse "(comment ]) local.part.with.comment.with.rightbracket@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   454 - assertIsTrue  "\"quote]\".local.part.with.qoute.with.rightbracket@domain.com"           =   1 =  OK 
     *   455 - assertIsFalse "]@rightbracket.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   456 - assertIsFalse "]]]]]]@rightbracket.domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   457 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   458 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   459 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name ]"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   460 - assertIsFalse " .local.part.starts.with.space@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   461 - assertIsFalse "local.part.ends.with.space @domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   462 - assertIsFalse "local.part.with.space.before .point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   463 - assertIsFalse "local.part.with.space.after. point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   464 - assertIsFalse "local.part.with.double.space  test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   465 - assertIsFalse "(comment  ) local.part.with.comment.with.space@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   466 - assertIsTrue  "\"quote \".local.part.with.qoute.with.space@domain.com"                  =   1 =  OK 
     *   467 - assertIsFalse " @space.domain.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   468 - assertIsFalse "      @space.domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   469 - assertIsFalse " . . . . . @space.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   470 - assertIsTrue  "name   <pointy.brackets1.with.space@domain.com>"                         =   0 =  OK 
     *   471 - assertIsTrue  "<pointy.brackets2.with.space@domain.com> name  "                         =   0 =  OK 
     *   472 - assertIsFalse "().local.part.starts.with.empty.bracket@domain.com"                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   473 - assertIsTrue  "local.part.ends.with.empty.bracket()@domain.com"                         =   6 =  OK 
     *   474 - assertIsFalse "local.part.with.empty.bracket.before().point@domain.com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   475 - assertIsFalse "local.part.with.empty.bracket.after.()point@domain.com"                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   476 - assertIsFalse "local.part.with.double.empty.bracket()()test@domain.com"                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   477 - assertIsFalse "(comment ()) local.part.with.comment.with.empty.bracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   478 - assertIsTrue  "\"quote()\".local.part.with.qoute.with.empty.bracket@domain.com"         =   1 =  OK 
     *   479 - assertIsFalse "()@empty.bracket.domain.com"                                             =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   480 - assertIsFalse "()()()()()()@empty.bracket.domain.com"                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   481 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   482 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket@domain.com>"                =   0 =  OK 
     *   483 - assertIsTrue  "<pointy.brackets2.with.empty.bracket@domain.com> name ()"                =   0 =  OK 
     *   484 - assertIsTrue  "{}.local.part.starts.with.empty.bracket@domain.com"                      =   0 =  OK 
     *   485 - assertIsTrue  "local.part.ends.with.empty.bracket{}@domain.com"                         =   0 =  OK 
     *   486 - assertIsTrue  "local.part.with.empty.bracket.before{}.point@domain.com"                 =   0 =  OK 
     *   487 - assertIsTrue  "local.part.with.empty.bracket.after.{}point@domain.com"                  =   0 =  OK 
     *   488 - assertIsTrue  "local.part.with.double.empty.bracket{}{}test@domain.com"                 =   0 =  OK 
     *   489 - assertIsFalse "(comment {}) local.part.with.comment.with.empty.bracket@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   490 - assertIsTrue  "\"quote{}\".local.part.with.qoute.with.empty.bracket@domain.com"         =   1 =  OK 
     *   491 - assertIsTrue  "{}@empty.bracket.domain.com"                                             =   0 =  OK 
     *   492 - assertIsTrue  "{}{}{}{}{}{}@empty.bracket.domain.com"                                   =   0 =  OK 
     *   493 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                              =   0 =  OK 
     *   494 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   495 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name {}"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   496 - assertIsFalse "[].local.part.starts.with.empty.bracket@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   497 - assertIsFalse "local.part.ends.with.empty.bracket[]@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   498 - assertIsFalse "local.part.with.empty.bracket.before[].point@domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   499 - assertIsFalse "local.part.with.empty.bracket.after.[]point@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   500 - assertIsFalse "local.part.with.double.empty.bracket[][]test@domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   501 - assertIsFalse "(comment []) local.part.with.comment.with.empty.bracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   502 - assertIsTrue  "\"quote[]\".local.part.with.qoute.with.empty.bracket@domain.com"         =   1 =  OK 
     *   503 - assertIsFalse "[]@empty.bracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   504 - assertIsFalse "[][][][][][]@empty.bracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   505 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   506 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   507 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name []"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   508 - assertIsTrue  "999.local.part.starts.with.byte.overflow@domain.com"                     =   0 =  OK 
     *   509 - assertIsTrue  "local.part.ends.with.byte.overflow999@domain.com"                        =   0 =  OK 
     *   510 - assertIsTrue  "local.part.with.byte.overflow.before999.point@domain.com"                =   0 =  OK 
     *   511 - assertIsTrue  "local.part.with.byte.overflow.after.999point@domain.com"                 =   0 =  OK 
     *   512 - assertIsTrue  "local.part.with.double.byte.overflow999999test@domain.com"               =   0 =  OK 
     *   513 - assertIsTrue  "(comment 999) local.part.with.comment.with.byte.overflow@domain.com"     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   514 - assertIsTrue  "\"quote999\".local.part.with.qoute.with.byte.overflow@domain.com"        =   1 =  OK 
     *   515 - assertIsTrue  "999@byte.overflow.domain.com"                                            =   0 =  OK 
     *   516 - assertIsTrue  "999999999999999999@byte.overflow.domain.com"                             =   0 =  OK 
     *   517 - assertIsTrue  "999.999.999.999.999.999@byte.overflow.domain.com"                        =   0 =  OK 
     *   518 - assertIsTrue  "name 999 <pointy.brackets1.with.byte.overflow@domain.com>"               =   0 =  OK 
     *   519 - assertIsTrue  "<pointy.brackets2.with.byte.overflow@domain.com> name 999"               =   0 =  OK 
     *   520 - assertIsTrue  "\"str\".local.part.starts.with.string@domain.com"                        =   1 =  OK 
     *   521 - assertIsFalse "local.part.ends.with.string\"str\"@domain.com"                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   522 - assertIsFalse "local.part.with.string.before\"str\".point@domain.com"                   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   523 - assertIsFalse "local.part.with.string.after.\"str\"point@domain.com"                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   524 - assertIsFalse "local.part.with.double.string\"str\"\"str\"test@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   525 - assertIsFalse "(comment \"str\") local.part.with.comment.with.string@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   526 - assertIsFalse "\"quote\"str\"\".local.part.with.qoute.with.string@domain.com"           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   527 - assertIsTrue  "\"str\"@string.domain.com"                                               =   1 =  OK 
     *   528 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com"            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   529 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"       =   1 =  OK 
     *   530 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string@domain.com>"                  =   0 =  OK 
     *   531 - assertIsTrue  "<pointy.brackets2.with.string@domain.com> name \"str\""                  =   0 =  OK 
     *   532 - assertIsFalse "(comment).local.part.starts.with.comment@domain.com"                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   533 - assertIsTrue  "local.part.ends.with.comment(comment)@domain.com"                        =   6 =  OK 
     *   534 - assertIsFalse "local.part.with.comment.before(comment).point@domain.com"                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   535 - assertIsFalse "local.part.with.comment.after.(comment)point@domain.com"                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   536 - assertIsFalse "local.part.with.double.comment(comment)(comment)test@domain.com"         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   537 - assertIsFalse "(comment (comment)) local.part.with.comment.with.comment@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   538 - assertIsTrue  "\"quote(comment)\".local.part.with.qoute.with.comment@domain.com"        =   1 =  OK 
     *   539 - assertIsFalse "(comment)@comment.domain.com"                                            =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   540 - assertIsFalse "(comment)(comment)(comment)(comment)@comment.domain.com"                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   541 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   542 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment@domain.com>"               =   0 =  OK 
     *   543 - assertIsTrue  "<pointy.brackets2.with.comment@domain.com> name (comment)"               =   0 =  OK 
     *   544 - assertIsTrue  "domain.part@with0number0.com"                                            =   0 =  OK 
     *   545 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                           =   0 =  OK 
     *   546 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                            =   0 =  OK 
     *   547 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                            =   0 =  OK 
     *   548 - assertIsTrue  "domain.part@with.number0.before0.point.com"                              =   0 =  OK 
     *   549 - assertIsTrue  "domain.part@with.number0.after.0point.com"                               =   0 =  OK 
     *   550 - assertIsTrue  "domain.part@with9number9.com"                                            =   0 =  OK 
     *   551 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                           =   0 =  OK 
     *   552 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                            =   0 =  OK 
     *   553 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                            =   0 =  OK 
     *   554 - assertIsTrue  "domain.part@with.number9.before9.point.com"                              =   0 =  OK 
     *   555 - assertIsTrue  "domain.part@with.number9.after.9point.com"                               =   0 =  OK 
     *   556 - assertIsTrue  "domain.part.only.numbers@1234567890.com"                                 =   0 =  OK 
     *   557 - assertIsTrue  "domain.part@with0123456789numbers.com"                                   =   0 =  OK 
     *   558 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"                  =   0 =  OK 
     *   559 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                   =   0 =  OK 
     *   560 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                   =   0 =  OK 
     *   561 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                     =   0 =  OK 
     *   562 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                      =   0 =  OK 
     *   563 - assertIsTrue  "domain.part@with-hyphen.com"                                             =   0 =  OK 
     *   564 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   565 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   566 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   567 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   568 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   569 - assertIsTrue  "domain.part@with_underscore.com"                                         =   0 =  OK 
     *   570 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   571 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   572 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   573 - assertIsFalse "domain.part@with.underscore.before_.point.com"                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   574 - assertIsFalse "domain.part@with.underscore.after._point.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   575 - assertIsFalse "domain.part@with&amp.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   578 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   579 - assertIsFalse "domain.part@with.amp.before&.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   580 - assertIsFalse "domain.part@with.amp.after.&point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   581 - assertIsFalse "domain.part@with*asterisk.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   582 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   583 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   584 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   585 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   586 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   587 - assertIsFalse "domain.part@with$dollar.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   588 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   589 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   590 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   591 - assertIsFalse "domain.part@with.dollar.before$.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   592 - assertIsFalse "domain.part@with.dollar.after.$point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   593 - assertIsFalse "domain.part@with=equality.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   594 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   595 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   596 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   597 - assertIsFalse "domain.part@with.equality.before=.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   598 - assertIsFalse "domain.part@with.equality.after.=point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   599 - assertIsFalse "domain.part@with!exclamation.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   600 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   601 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   602 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   603 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   604 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   605 - assertIsFalse "domain.part@with?question.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   606 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   607 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   608 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   609 - assertIsFalse "domain.part@with.question.before?.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   610 - assertIsFalse "domain.part@with.question.after.?point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   611 - assertIsFalse "domain.part@with`grave-accent.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   612 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   613 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   614 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   615 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   616 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   617 - assertIsFalse "domain.part@with#hash.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   618 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   619 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   620 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   621 - assertIsFalse "domain.part@with.hash.before#.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   622 - assertIsFalse "domain.part@with.hash.after.#point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   623 - assertIsFalse "domain.part@with%percentage.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   624 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   625 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   626 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   627 - assertIsFalse "domain.part@with.percentage.before%.point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   628 - assertIsFalse "domain.part@with.percentage.after.%point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   629 - assertIsFalse "domain.part@with|pipe.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   630 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   631 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   632 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   633 - assertIsFalse "domain.part@with.pipe.before|.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   634 - assertIsFalse "domain.part@with.pipe.after.|point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   635 - assertIsFalse "domain.part@with+plus.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   636 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   637 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   638 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   639 - assertIsFalse "domain.part@with.plus.before+.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   640 - assertIsFalse "domain.part@with.plus.after.+point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   641 - assertIsFalse "domain.part@with{leftbracket.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   642 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   643 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   644 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   645 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   646 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   647 - assertIsFalse "domain.part@with}rightbracket.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   648 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   649 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   650 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   651 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   652 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   653 - assertIsFalse "domain.part@with(leftbracket.com"                                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   654 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   655 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   656 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   657 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   658 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                           = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   659 - assertIsFalse "domain.part@with)rightbracket.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   660 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   661 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   662 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   663 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   664 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   665 - assertIsFalse "domain.part@with[leftbracket.com"                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   666 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   667 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   668 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   669 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   670 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   671 - assertIsFalse "domain.part@with]rightbracket.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   672 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   673 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   674 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   676 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   677 - assertIsFalse "domain.part@with~tilde.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   678 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   679 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   680 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   681 - assertIsFalse "domain.part@with.tilde.before~.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   682 - assertIsFalse "domain.part@with.tilde.after.~point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   683 - assertIsFalse "domain.part@with^xor.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   684 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   685 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   686 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   687 - assertIsFalse "domain.part@with.xor.before^.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   688 - assertIsFalse "domain.part@with.xor.after.^point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   689 - assertIsFalse "domain.part@with:colon.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   690 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   691 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   692 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   693 - assertIsFalse "domain.part@with.colon.before:.point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   694 - assertIsFalse "domain.part@with.colon.after.:point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   695 - assertIsFalse "domain.part@with space.com"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   696 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   697 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   698 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   699 - assertIsFalse "domain.part@with.space.before .point.com"                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   700 - assertIsFalse "domain.part@with.space.after. point.com"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   701 - assertIsTrue  "domain.part@with999byte.overflow.com"                                    =   0 =  OK 
     *   702 - assertIsTrue  "domain.part@999with.byte.overflow.at.domain.start.com"                   =   0 =  OK 
     *   703 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end1999.com"                    =   0 =  OK 
     *   704 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end2.com999"                    =   0 =  OK 
     *   705 - assertIsTrue  "domain.part@with.byte.overflow.before999.point.com"                      =   0 =  OK 
     *   706 - assertIsTrue  "domain.part@with.byte.overflow.after.999point.com"                       =   0 =  OK 
     *   707 - assertIsTrue  "domain.part@withxyzno.hex.number.com"                                    =   0 =  OK 
     *   708 - assertIsTrue  "domain.part@xyzwith.no.hex.number.at.domain.start.com"                   =   0 =  OK 
     *   709 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end1xyz.com"                    =   0 =  OK 
     *   710 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end2.comxyz"                    =   0 =  OK 
     *   711 - assertIsTrue  "domain.part@with.no.hex.number.beforexyz.point.com"                      =   0 =  OK 
     *   712 - assertIsTrue  "domain.part@with.no.hex.number.after.xyzpoint.com"                       =   0 =  OK 
     *   713 - assertIsFalse "domain.part@with\"str\"string.com"                                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   714 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   715 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   716 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   717 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   718 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   719 - assertIsFalse "domain.part@with(comment)comment.com"                                    = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   720 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                   =   6 =  OK 
     *   721 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   722 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                    =   6 =  OK 
     *   723 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   724 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                       = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   725 - assertIsFalse ",.local.part.starts.with.comma@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   726 - assertIsFalse "local.part.ends.with.comma,@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   727 - assertIsFalse "local.part.with.comma.before,.point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   728 - assertIsFalse "local.part.with.comma.after.,point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   729 - assertIsFalse "local.part.with.double.comma,,test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   730 - assertIsFalse "(comment ,) local.part.with.comment.with.comma@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   731 - assertIsTrue  "\"quote,\".local.part.with.qoute.with.comma@domain.com"                  =   1 =  OK 
     *   732 - assertIsFalse ",@comma.domain.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   733 - assertIsFalse ",,,,,,@comma.domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   734 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   735 - assertIsFalse "name , <pointy.brackets1.with.comma@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   736 - assertIsFalse "<pointy.brackets2.with.comma@domain.com> name ,"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   737 - assertIsFalse "domain.part@with,comma.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   738 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   739 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   740 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   741 - assertIsFalse "domain.part@with.comma.before,.point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   742 - assertIsFalse "domain.part@with.comma.after.,point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   743 - assertIsFalse "§.local.part.starts.with.paragraph@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   744 - assertIsFalse "local.part.ends.with.paragraph§@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   745 - assertIsFalse "local.part.with.paragraph.before§.point@domain.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   746 - assertIsFalse "local.part.with.paragraph.after.§point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   747 - assertIsFalse "local.part.with.double.paragraph§§test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   748 - assertIsFalse "(comment §) local.part.with.comment.with.paragraph@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   749 - assertIsFalse "\"quote§\".local.part.with.qoute.with.paragraph@domain.com"              =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   750 - assertIsFalse "§@paragraph.domain.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   751 - assertIsFalse "§§§§§§@paragraph.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   752 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   753 - assertIsFalse "name § <pointy.brackets1.with.paragraph@domain.com>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   754 - assertIsFalse "<pointy.brackets2.with.paragraph@domain.com> name §"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   755 - assertIsFalse "domain.part@with§paragraph.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   756 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   757 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   758 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   759 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   760 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   761 - assertIsTrue  "'.local.part.starts.with.quote@domain.com"                               =   0 =  OK 
     *   762 - assertIsTrue  "local.part.ends.with.quote'@domain.com"                                  =   0 =  OK 
     *   763 - assertIsTrue  "local.part.with.quote.before'.point@domain.com"                          =   0 =  OK 
     *   764 - assertIsTrue  "local.part.with.quote.after.'point@domain.com"                           =   0 =  OK 
     *   765 - assertIsTrue  "local.part.with.double.quote''test@domain.com"                           =   0 =  OK 
     *   766 - assertIsFalse "(comment ') local.part.with.comment.with.quote@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   767 - assertIsTrue  "\"quote'\".local.part.with.qoute.with.quote@domain.com"                  =   1 =  OK 
     *   768 - assertIsTrue  "'@quote.domain.com"                                                      =   0 =  OK 
     *   769 - assertIsTrue  "''''''@quote.domain.com"                                                 =   0 =  OK 
     *   770 - assertIsTrue  "'.'.'.'.'.'@quote.domain.com"                                            =   0 =  OK 
     *   771 - assertIsFalse "name ' <pointy.brackets1.with.quote@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   772 - assertIsFalse "<pointy.brackets2.with.quote@domain.com> name '"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   773 - assertIsFalse "domain.part@with'quote.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   774 - assertIsFalse "domain.part@'with.quote.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   775 - assertIsFalse "domain.part@with.quote.at.domain.end1'.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   776 - assertIsFalse "domain.part@with.quote.at.domain.end2.com'"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   777 - assertIsFalse "domain.part@with.quote.before'.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   778 - assertIsFalse "domain.part@with.quote.after.'point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   779 - assertIsFalse "\".local.part.starts.with.double.quote@domain.com"                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   780 - assertIsFalse "local.part.ends.with.double.quote\"@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   781 - assertIsFalse "local.part.with.double.quote.before\".point@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   782 - assertIsFalse "local.part.with.double.quote.after.\"point@domain.com"                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   783 - assertIsFalse "local.part.with.double.double.quote\"\"test@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   784 - assertIsFalse "(comment \") local.part.with.comment.with.double.quote@domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   785 - assertIsFalse "\"quote\"\".local.part.with.qoute.with.double.quote@domain.com"          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   786 - assertIsFalse "\"@double.quote.domain.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   787 - assertIsTrue  "\".\".\".\".\".\"@double.quote.domain.com"                               =   1 =  OK 
     *   788 - assertIsTrue  "name \" <pointy.brackets1.with.double.quote@domain.com>"                 =   0 =  OK 
     *   789 - assertIsTrue  "<pointy.brackets2.with.double.quote@domain.com> name \""                 =   0 =  OK 
     *   790 - assertIsFalse "domain.part@with\"double.quote.com"                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   791 - assertIsFalse "domain.part@\"with.double.quote.at.domain.start.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   792 - assertIsFalse "domain.part@with.double.quote.at.domain.end1\".com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   793 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com\""                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   794 - assertIsFalse "domain.part@with.double.quote.before\".point.com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   795 - assertIsFalse "domain.part@with.double.quote.after.\"point.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   796 - assertIsFalse ")(.local.part.starts.with.false.bracket1@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   797 - assertIsFalse "local.part.ends.with.false.bracket1)(@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   798 - assertIsFalse "local.part.with.false.bracket1.before)(.point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   799 - assertIsFalse "local.part.with.false.bracket1.after.)(point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   800 - assertIsFalse "local.part.with.double.false.bracket1)()(test@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   801 - assertIsFalse "(comment )() local.part.with.comment.with.false.bracket1@domain.com"     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   802 - assertIsTrue  "\"quote)(\".local.part.with.qoute.with.false.bracket1@domain.com"        =   1 =  OK 
     *   803 - assertIsFalse ")(@false.bracket1.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   804 - assertIsFalse ")()()()()()(@false.bracket1.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   805 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   806 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1@domain.com>"               =   0 =  OK 
     *   807 - assertIsTrue  "<pointy.brackets2.with.false.bracket1@domain.com> name )("               =   0 =  OK 
     *   808 - assertIsFalse "domain.part@with)(false.bracket1.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   809 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   810 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   811 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   812 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   813 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   814 - assertIsTrue  "}{.local.part.starts.with.false.bracket2@domain.com"                     =   0 =  OK 
     *   815 - assertIsTrue  "local.part.ends.with.false.bracket2}{@domain.com"                        =   0 =  OK 
     *   816 - assertIsTrue  "local.part.with.false.bracket2.before}{.point@domain.com"                =   0 =  OK 
     *   817 - assertIsTrue  "local.part.with.false.bracket2.after.}{point@domain.com"                 =   0 =  OK 
     *   818 - assertIsTrue  "local.part.with.double.false.bracket2}{}{test@domain.com"                =   0 =  OK 
     *   819 - assertIsFalse "(comment }{) local.part.with.comment.with.false.bracket2@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   820 - assertIsTrue  "\"quote}{\".local.part.with.qoute.with.false.bracket2@domain.com"        =   1 =  OK 
     *   821 - assertIsTrue  "}{@false.bracket2.domain.com"                                            =   0 =  OK 
     *   822 - assertIsTrue  "}{}{}{}{}{}{@false.bracket2.domain.com"                                  =   0 =  OK 
     *   823 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                             =   0 =  OK 
     *   824 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   825 - assertIsFalse "<pointy.brackets2.with.false.bracket2@domain.com> name }{"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   826 - assertIsFalse "domain.part@with}{false.bracket2.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   827 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   828 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   829 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   830 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   831 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   832 - assertIsFalse "][.local.part.starts.with.false.bracket3@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   833 - assertIsFalse "local.part.ends.with.false.bracket3][@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   834 - assertIsFalse "local.part.with.false.bracket3.before][.point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   835 - assertIsFalse "local.part.with.false.bracket3.after.][point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   836 - assertIsFalse "local.part.with.double.false.bracket3][][test@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   837 - assertIsFalse "(comment ][) local.part.with.comment.with.false.bracket3@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   838 - assertIsTrue  "\"quote][\".local.part.with.qoute.with.false.bracket3@domain.com"        =   1 =  OK 
     *   839 - assertIsFalse "][@false.bracket3.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   840 - assertIsFalse "][][][][][][@false.bracket3.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   841 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   842 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   843 - assertIsFalse "<pointy.brackets2.with.false.bracket3@domain.com> name ]["               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   844 - assertIsFalse "domain.part@with][false.bracket3.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   845 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   846 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   847 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   848 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   849 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   850 - assertIsFalse "><.local.part.starts.with.false.bracket4@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   851 - assertIsFalse "local.part.ends.with.false.bracket4><@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   852 - assertIsFalse "local.part.with.false.bracket4.before><.point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   853 - assertIsFalse "local.part.with.false.bracket4.after.><point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   854 - assertIsFalse "local.part.with.double.false.bracket4><><test@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   855 - assertIsFalse "(comment ><) local.part.with.comment.with.false.bracket4@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   856 - assertIsTrue  "\"quote><\".local.part.with.qoute.with.false.bracket4@domain.com"        =   1 =  OK 
     *   857 - assertIsFalse "><@false.bracket4.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   858 - assertIsFalse "><><><><><><@false.bracket4.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   859 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   860 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   861 - assertIsFalse "<pointy.brackets2.with.false.bracket4@domain.com> name ><"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   862 - assertIsFalse "domain.part@with\slash.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   863 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   864 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   865 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   866 - assertIsFalse "domain.part@with.slash.before\.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   867 - assertIsFalse "domain.part@with.slash.after.\point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   868 - assertIsFalse "domain.part@with><false.bracket4.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   869 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   870 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   871 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   872 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   873 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   874 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                       =   0 =  OK 
     *   875 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   876 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   877 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   878 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   879 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   880 - assertIsFalse "domain.part@with.consecutive.question??test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   881 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   882 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   883 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   884 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   885 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   886 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   887 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   888 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   889 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   890 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   891 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   892 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   893 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   894 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   895 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   896 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   897 - assertIsFalse "domain.part@with.consecutive.space  test.com"                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   898 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   899 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   900 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   901 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   902 - assertIsFalse "domain.part@with.consecutive.double.quote\"\"test.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   903 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"                  = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   904 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   905 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   906 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   907 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   908 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   909 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   910 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   911 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   912 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   913 - assertIsTrue  "domain.part.with.comment.with.underscore@(comment _)domain.com"          =   6 =  OK 
     *   914 - assertIsTrue  "domain.part.with.comment.with.amp@(comment &)domain.com"                 =   6 =  OK 
     *   915 - assertIsTrue  "domain.part.with.comment.with.asterisk@(comment *)domain.com"            =   6 =  OK 
     *   916 - assertIsTrue  "domain.part.with.comment.with.dollar@(comment $)domain.com"              =   6 =  OK 
     *   917 - assertIsTrue  "domain.part.with.comment.with.equality@(comment =)domain.com"            =   6 =  OK 
     *   918 - assertIsTrue  "domain.part.with.comment.with.exclamation@(comment !)domain.com"         =   6 =  OK 
     *   919 - assertIsTrue  "domain.part.with.comment.with.question@(comment ?)domain.com"            =   6 =  OK 
     *   920 - assertIsTrue  "domain.part.with.comment.with.grave-accent@(comment `)domain.com"        =   6 =  OK 
     *   921 - assertIsTrue  "domain.part.with.comment.with.hash@(comment #)domain.com"                =   6 =  OK 
     *   922 - assertIsTrue  "domain.part.with.comment.with.percentage@(comment %)domain.com"          =   6 =  OK 
     *   923 - assertIsTrue  "domain.part.with.comment.with.pipe@(comment |)domain.com"                =   6 =  OK 
     *   924 - assertIsTrue  "domain.part.with.comment.with.plus@(comment +)domain.com"                =   6 =  OK 
     *   925 - assertIsTrue  "domain.part.with.comment.with.leftbracket@(comment {)domain.com"         =   6 =  OK 
     *   926 - assertIsTrue  "domain.part.with.comment.with.rightbracket@(comment })domain.com"        =   6 =  OK 
     *   927 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment ()domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   928 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ))domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   929 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment [)domain.com"         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   930 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ])domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   931 - assertIsFalse "domain.part.with.comment.with.lower.than@(comment <)domain.com"          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   932 - assertIsFalse "domain.part.with.comment.with.greater.than@(comment >)domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   933 - assertIsTrue  "domain.part.with.comment.with.tilde@(comment ~)domain.com"               =   6 =  OK 
     *   934 - assertIsTrue  "domain.part.with.comment.with.xor@(comment ^)domain.com"                 =   6 =  OK 
     *   935 - assertIsFalse "domain.part.with.comment.with.colon@(comment :)domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   936 - assertIsTrue  "domain.part.with.comment.with.space@(comment  )domain.com"               =   6 =  OK 
     *   937 - assertIsFalse "domain.part.with.comment.with.comma@(comment ,)domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   938 - assertIsFalse "domain.part.with.comment.with.paragraph@(comment §)domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   939 - assertIsTrue  "domain.part.with.comment.with.double.quote@(comment ')domain.com"        =   6 =  OK 
     *   940 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment ())domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   941 - assertIsTrue  "domain.part.with.comment.with.empty.bracket@(comment {})domain.com"      =   6 =  OK 
     *   942 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment [])domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   943 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment <>)domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   944 - assertIsFalse "domain.part.with.comment.with.false.bracket1@(comment )()domain.com"     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   945 - assertIsTrue  "domain.part.with.comment.with.false.bracket2@(comment }{)domain.com"     =   6 =  OK 
     *   946 - assertIsFalse "domain.part.with.comment.with.false.bracket3@(comment ][)domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   947 - assertIsFalse "domain.part.with.comment.with.false.bracket4@(comment ><)domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   948 - assertIsFalse "domain.part.with.comment.with.slash@(comment \)domain.com"               =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   949 - assertIsFalse "domain.part.with.comment.with.string@(comment \"str\")domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   950 - assertIsFalse "domain.part.only.underscore@_.com"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   951 - assertIsFalse "domain.part.only.amp@&.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   952 - assertIsFalse "domain.part.only.asterisk@*.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   953 - assertIsFalse "domain.part.only.dollar@$.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   954 - assertIsFalse "domain.part.only.equality@=.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   955 - assertIsFalse "domain.part.only.exclamation@!.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   956 - assertIsFalse "domain.part.only.question@?.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   957 - assertIsFalse "domain.part.only.grave-accent@`.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   958 - assertIsFalse "domain.part.only.hash@#.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   959 - assertIsFalse "domain.part.only.percentage@%.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   960 - assertIsFalse "domain.part.only.pipe@|.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   961 - assertIsFalse "domain.part.only.plus@+.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   962 - assertIsFalse "domain.part.only.leftbracket@{.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   963 - assertIsFalse "domain.part.only.rightbracket@}.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   964 - assertIsFalse "domain.part.only.leftbracket@(.com"                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   965 - assertIsFalse "domain.part.only.rightbracket@).com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   966 - assertIsFalse "domain.part.only.leftbracket@[.com"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   967 - assertIsFalse "domain.part.only.rightbracket@].com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   968 - assertIsFalse "domain.part.only.lower.than@<.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   969 - assertIsFalse "domain.part.only.greater.than@>.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   970 - assertIsFalse "domain.part.only.tilde@~.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   971 - assertIsFalse "domain.part.only.xor@^.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   972 - assertIsFalse "domain.part.only.colon@:.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   973 - assertIsFalse "domain.part.only.space@ .com"                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   974 - assertIsFalse "domain.part.only.dot@..com"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   975 - assertIsFalse "domain.part.only.comma@,.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   976 - assertIsFalse "domain.part.only.at@@.com"                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   977 - assertIsFalse "domain.part.only.paragraph@§.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   978 - assertIsFalse "domain.part.only.double.quote@'.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   979 - assertIsFalse "domain.part.only.double.quote@\".com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   980 - assertIsFalse "domain.part.only.double.quote@\\".com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   981 - assertIsFalse "domain.part.only.empty.bracket@().com"                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   982 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   983 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                   =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   984 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   985 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   986 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   987 - assertIsFalse "domain.part.only.false.bracket3@][.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   988 - assertIsFalse "domain.part.only.false.bracket4@><.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   989 - assertIsTrue  "domain.part.only.number0@0.com"                                          =   0 =  OK 
     *   990 - assertIsTrue  "domain.part.only.number9@9.com"                                          =   0 =  OK 
     *   991 - assertIsFalse "domain.part.only.slash@\.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   992 - assertIsTrue  "domain.part.only.byte.overflow@999.com"                                  =   0 =  OK 
     *   993 - assertIsTrue  "domain.part.only.no.hex.number@xyz.com"                                  =   0 =  OK 
     *   994 - assertIsFalse "domain.part.only.string@\"str\".com"                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   995 - assertIsFalse "domain.part.only.comment@(comment).com"                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   996 - assertIsFalse "DomainHyphen@-atstart"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   997 - assertIsFalse "DomainHyphen@atend-.com"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   998 - assertIsFalse "DomainHyphen@bb.-cc"                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   999 - assertIsFalse "DomainHyphen@bb.-cc-"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1000 - assertIsFalse "DomainHyphen@bb.cc-"                                                     =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1001 - assertIsFalse "DomainHyphen@bb.c-c"                                                     =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1002 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1003 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                       =   0 =  OK 
     *  1004 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1005 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1006 - assertIsFalse "DomainNotAllowedCharacter@example'"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1007 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1008 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                    =   0 =  OK 
     *  1009 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                      =   0 =  OK 
     *  1010 - assertIsFalse "tld.starts.with.digit@domain.2com"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1011 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                         =   0 =  OK 
     *  1012 - assertIsFalse "email@=qowaiv.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1013 - assertIsFalse "email@plus+.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1014 - assertIsFalse "email@domain.com>"                                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1015 - assertIsFalse "email@mailto:domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1016 - assertIsFalse "mailto:mailto:email@domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1017 - assertIsFalse "email@-domain.com"                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1018 - assertIsFalse "email@domain-.com"                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1019 - assertIsFalse "email@domain.com-"                                                       =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1020 - assertIsFalse "email@{leftbracket.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1021 - assertIsFalse "email@rightbracket}.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1022 - assertIsFalse "email@pp|e.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1023 - assertIsTrue  "email@domain.domain.domain.com.com"                                      =   0 =  OK 
     *  1024 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                               =   0 =  OK 
     *  1025 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                        =   0 =  OK 
     *  1026 - assertIsFalse "unescaped white space@fake$com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1027 - assertIsFalse "\"Joe Smith email@domain.com"                                            =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1028 - assertIsFalse "\"Joe Smith' email@domain.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1029 - assertIsFalse "\"Joe Smith\"email@domain.com"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1030 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1031 - assertIsTrue  "{john'doe}@my.server"                                                    =   0 =  OK 
     *  1032 - assertIsTrue  "email@domain-one.com"                                                    =   0 =  OK 
     *  1033 - assertIsTrue  "_______@domain.com"                                                      =   0 =  OK 
     *  1034 - assertIsTrue  "?????@domain.com"                                                        =   0 =  OK 
     *  1035 - assertIsFalse "local@?????.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1036 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                           =   1 =  OK 
     *  1037 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                                  =   1 =  OK 
     *  1038 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                               =   0 =  OK 
     *  1039 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                        =   0 =  OK 
     *  1040 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1041 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"                 =   0 =  OK 
     *  1042 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1043 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                     =   1 =  OK 
     *  1044 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1045 - assertIsFalse "top.level.domain.only@underscore._"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1046 - assertIsFalse "top.level.domain.only@amp.&"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1047 - assertIsFalse "top.level.domain.only@asterisk.*"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1048 - assertIsFalse "top.level.domain.only@dollar.$"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1049 - assertIsFalse "top.level.domain.only@equality.="                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1050 - assertIsFalse "top.level.domain.only@exclamation.!"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1051 - assertIsFalse "top.level.domain.only@question.?"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1052 - assertIsFalse "top.level.domain.only@grave-accent.`"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1053 - assertIsFalse "top.level.domain.only@hash.#"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1054 - assertIsFalse "top.level.domain.only@percentage.%"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1055 - assertIsFalse "top.level.domain.only@pipe.|"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1056 - assertIsFalse "top.level.domain.only@plus.+"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1057 - assertIsFalse "top.level.domain.only@leftbracket.{"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1058 - assertIsFalse "top.level.domain.only@rightbracket.}"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1059 - assertIsFalse "top.level.domain.only@leftbracket.("                                     = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1060 - assertIsFalse "top.level.domain.only@rightbracket.)"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1061 - assertIsFalse "top.level.domain.only@leftbracket.["                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1062 - assertIsFalse "top.level.domain.only@rightbracket.]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1063 - assertIsFalse "top.level.domain.only@lower.than.<"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1064 - assertIsFalse "top.level.domain.only@greater.than.>"                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1065 - assertIsFalse "top.level.domain.only@tilde.~"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1066 - assertIsFalse "top.level.domain.only@xor.^"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1067 - assertIsFalse "top.level.domain.only@colon.:"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1068 - assertIsFalse "top.level.domain.only@space. "                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1069 - assertIsFalse "top.level.domain.only@dot.."                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1070 - assertIsFalse "top.level.domain.only@comma.,"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1071 - assertIsFalse "top.level.domain.only@at.@"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1072 - assertIsFalse "top.level.domain.only@paragraph.§"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1073 - assertIsFalse "top.level.domain.only@double.quote.'"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1074 - assertIsFalse "top.level.domain.only@double.quote.\"\""                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1075 - assertIsFalse "top.level.domain.only@forward.slash./"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1076 - assertIsFalse "top.level.domain.only@hyphen.-"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1077 - assertIsFalse "top.level.domain.only@empty.bracket.()"                                  = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1078 - assertIsFalse "top.level.domain.only@empty.bracket.{}"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1079 - assertIsFalse "top.level.domain.only@empty.bracket.[]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1080 - assertIsFalse "top.level.domain.only@empty.bracket.<>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1081 - assertIsFalse "top.level.domain.only@empty.string1.\"\""                                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1082 - assertIsFalse "top.level.domain.only@empty.string2.a\"\"b"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1083 - assertIsFalse "top.level.domain.only@double.empty.string1.\"\"\"\""                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1084 - assertIsFalse "top.level.domain.only@double.empty.string2.\"\".\"\""                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1085 - assertIsFalse "top.level.domain.only@false.bracket1.)("                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1086 - assertIsFalse "top.level.domain.only@false.bracket2.}{"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1087 - assertIsFalse "top.level.domain.only@false.bracket3.]["                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1088 - assertIsFalse "top.level.domain.only@false.bracket4.><"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1089 - assertIsFalse "top.level.domain.only@number0.0"                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1090 - assertIsFalse "top.level.domain.only@number9.9"                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1091 - assertIsFalse "top.level.domain.only@numbers.123"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1092 - assertIsFalse "top.level.domain.only@slash.\"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1093 - assertIsFalse "top.level.domain.only@string.\"str\""                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1094 - assertIsFalse "top.level.domain.only@comment.(comment)"                                 = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *  1095 - assertIsFalse "\"\"@[]"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1096 - assertIsFalse "\"\"@[1"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1097 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1098 - assertIsFalse "ABC.DEF@[]"                                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1099 - assertIsFalse "ABC.DEF@]"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1100 - assertIsFalse "ABC.DEF@["                                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1101 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1102 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                      =   3 =  OK 
     *  1103 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                               =   2 =  OK 
     *  1104 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                                 =   3 =  OK 
     *  1105 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                                       =   2 =  OK 
     *  1106 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1107 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1108 - assertIsFalse "ABC.DEF@([001.002.003.004])"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1109 - assertIsFalse "ABC.DEF[1.2.3.4]"                                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1110 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1111 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1112 - assertIsFalse "ABC.DEF@[][][][]"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1113 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1114 - assertIsFalse "ABC.DEF@[....]"                                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1115 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1116 - assertIsFalse "1.2.3.4]@[5.6.7.8]"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1117 - assertIsFalse "[1.2.3.4@[5.6.7.8]"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1118 - assertIsFalse "[1.2.3.4][5.6.7.8]@[9.10.11.12]"                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1119 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12]"                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1120 - assertIsFalse "[1.2.3.4]@[5.6.7.8]9.10.11.12]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1121 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12["                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1122 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1123 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                                 =   3 =  OK 
     *  1124 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1125 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1126 - assertIsFalse "ABC.DEF@[..]"                                                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1127 - assertIsFalse "ABC.DEF@[.2.3.4]"                                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1128 - assertIsFalse "ABC.DEF@[1]"                                                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1129 - assertIsFalse "ABC.DEF@[1.2]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1130 - assertIsFalse "ABC.DEF@[1.2.3]"                                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1131 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1132 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                                   =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1133 - assertIsFalse "ABC.DEF@[1.2.3.]"                                                        =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1134 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1135 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1136 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1137 - assertIsFalse "ABC.DEF@[1.2.3.4"                                                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1138 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1139 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1140 - assertIsFalse "ABC.DEF@[12.34]"                                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1141 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1142 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1143 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1144 - assertIsFalse "ABC.DEF@[-1.2.3.4]"                                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1145 - assertIsFalse "ABC.DEF@[1.-2.3.4]"                                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1146 - assertIsFalse "ABC.DEF@[1.2.-3.4]"                                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1147 - assertIsFalse "ABC.DEF@[1.2.3.-4]"                                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1148 - assertIsFalse "ip.v4.with.hyphen@[123.14-5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1149 - assertIsFalse "ip.v4.with.hyphen@[123.145-.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1150 - assertIsFalse "ip.v4.with.hyphen@[123.145.-178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1151 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90-]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1152 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90]-"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1153 - assertIsFalse "ip.v4.with.hyphen@[-123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1154 - assertIsFalse "ip.v4.with.hyphen@-[123.145.178.90]"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1155 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1156 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1157 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1158 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1159 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1160 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90]_"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1161 - assertIsFalse "ip.v4.with.underscore@_[123.145.178.90]"                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1162 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1163 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1164 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1165 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1166 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1167 - assertIsFalse "ip.v4.with.amp@[123.145.178.90]&"                                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1168 - assertIsFalse "ip.v4.with.amp@&[123.145.178.90]"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1169 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1170 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1171 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1172 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1173 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1174 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90]*"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1175 - assertIsFalse "ip.v4.with.asterisk@*[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1176 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1177 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1178 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1179 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1180 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1181 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90]$"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1182 - assertIsFalse "ip.v4.with.dollar@$[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1183 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1184 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1185 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1186 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1187 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1188 - assertIsFalse "ip.v4.with.equality@[123.145.178.90]="                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1189 - assertIsFalse "ip.v4.with.equality@=[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1190 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1191 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1192 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1193 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1194 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1195 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90]!"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1196 - assertIsFalse "ip.v4.with.exclamation@![123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1197 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1198 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1199 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1200 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1201 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1202 - assertIsFalse "ip.v4.with.question@[123.145.178.90]?"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1203 - assertIsFalse "ip.v4.with.question@?[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1204 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1205 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1206 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1207 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1208 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1209 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90]`"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1210 - assertIsFalse "ip.v4.with.grave-accent@`[123.145.178.90]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1211 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1212 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1213 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1214 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1215 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1216 - assertIsFalse "ip.v4.with.hash@[123.145.178.90]#"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1217 - assertIsFalse "ip.v4.with.hash@#[123.145.178.90]"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1218 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1219 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1220 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1221 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1222 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1223 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90]%"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1224 - assertIsFalse "ip.v4.with.percentage@%[123.145.178.90]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1225 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1226 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1227 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1228 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1229 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1230 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90]|"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1231 - assertIsFalse "ip.v4.with.pipe@|[123.145.178.90]"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1232 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1233 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1234 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1235 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1236 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1237 - assertIsFalse "ip.v4.with.plus@[123.145.178.90]+"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1238 - assertIsFalse "ip.v4.with.plus@+[123.145.178.90]"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1239 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1240 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1241 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1242 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1243 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1244 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]{"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1245 - assertIsFalse "ip.v4.with.leftbracket@{[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1246 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1247 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1248 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1249 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1250 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1251 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]}"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1252 - assertIsFalse "ip.v4.with.rightbracket@}[123.145.178.90]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1253 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1254 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1255 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1256 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1257 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1258 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]("                                =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1259 - assertIsFalse "ip.v4.with.leftbracket@([123.145.178.90]"                                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1260 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1261 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1262 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1263 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1264 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1265 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90])"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1266 - assertIsFalse "ip.v4.with.rightbracket@)[123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1267 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1268 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1269 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1270 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1271 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1272 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]["                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1273 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1274 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1275 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1276 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1277 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1278 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1279 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1280 - assertIsFalse "ip.v4.with.rightbracket@][123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1281 - assertIsFalse "ip.v4.with.lower.than@[123.14<5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1282 - assertIsFalse "ip.v4.with.lower.than@[123.145<.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1283 - assertIsFalse "ip.v4.with.lower.than@[123.145.<178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1284 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90<]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1285 - assertIsFalse "ip.v4.with.lower.than@[<123.145.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1286 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90]<"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1287 - assertIsFalse "ip.v4.with.lower.than@<[123.145.178.90]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1288 - assertIsFalse "ip.v4.with.greater.than@[123.14>5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1289 - assertIsFalse "ip.v4.with.greater.than@[123.145>.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1290 - assertIsFalse "ip.v4.with.greater.than@[123.145.>178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1291 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90>]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1292 - assertIsFalse "ip.v4.with.greater.than@[>123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1293 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90]>"                               =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1294 - assertIsFalse "ip.v4.with.greater.than@>[123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1295 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1296 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1297 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1298 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1299 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1300 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90]~"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1301 - assertIsFalse "ip.v4.with.tilde@~[123.145.178.90]"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1302 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1303 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1304 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1305 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1306 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1307 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1308 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1309 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1310 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1311 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1312 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1313 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1314 - assertIsFalse "ip.v4.with.xor@[123.145.178.90]^"                                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1315 - assertIsFalse "ip.v4.with.xor@^[123.145.178.90]"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1316 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1317 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1318 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1319 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1320 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1321 - assertIsFalse "ip.v4.with.colon@[123.145.178.90]:"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1322 - assertIsFalse "ip.v4.with.colon@:[123.145.178.90]"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1323 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1324 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1325 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1326 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1327 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1328 - assertIsFalse "ip.v4.with.space@[123.145.178.90] "                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1329 - assertIsFalse "ip.v4.with.space@ [123.145.178.90]"                                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1330 - assertIsFalse "ip.v4.with.dot@[123.14.5.178.90]"                                        =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1331 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1332 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1333 - assertIsFalse "ip.v4.with.dot@[123.145.178.90.]"                                        =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1334 - assertIsFalse "ip.v4.with.dot@[.123.145.178.90]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1335 - assertIsFalse "ip.v4.with.dot@[123.145.178.90]."                                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1336 - assertIsFalse "ip.v4.with.dot@.[123.145.178.90]"                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1337 - assertIsFalse "ip.v4.with.comma@[123.14,5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1338 - assertIsFalse "ip.v4.with.comma@[123.145,.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1339 - assertIsFalse "ip.v4.with.comma@[123.145.,178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1340 - assertIsFalse "ip.v4.with.comma@[123.145.178.90,]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1341 - assertIsFalse "ip.v4.with.comma@[,123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1342 - assertIsFalse "ip.v4.with.comma@[123.145.178.90],"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1343 - assertIsFalse "ip.v4.with.comma@,[123.145.178.90]"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1344 - assertIsFalse "ip.v4.with.at@[123.14@5.178.90]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1345 - assertIsFalse "ip.v4.with.at@[123.145@.178.90]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1346 - assertIsFalse "ip.v4.with.at@[123.145.@178.90]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1347 - assertIsFalse "ip.v4.with.at@[123.145.178.90@]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1348 - assertIsFalse "ip.v4.with.at@[@123.145.178.90]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1349 - assertIsFalse "ip.v4.with.at@[123.145.178.90]@"                                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1350 - assertIsFalse "ip.v4.with.at@@[123.145.178.90]"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1351 - assertIsFalse "ip.v4.with.paragraph@[123.14§5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1352 - assertIsFalse "ip.v4.with.paragraph@[123.145§.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1353 - assertIsFalse "ip.v4.with.paragraph@[123.145.§178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1354 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90§]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1355 - assertIsFalse "ip.v4.with.paragraph@[§123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1356 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90]§"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1357 - assertIsFalse "ip.v4.with.paragraph@§[123.145.178.90]"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1358 - assertIsFalse "ip.v4.with.double.quote@[123.14'5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1359 - assertIsFalse "ip.v4.with.double.quote@[123.145'.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1360 - assertIsFalse "ip.v4.with.double.quote@[123.145.'178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1361 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90']"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1362 - assertIsFalse "ip.v4.with.double.quote@['123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1363 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]'"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1364 - assertIsFalse "ip.v4.with.double.quote@'[123.145.178.90]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1365 - assertIsFalse "ip.v4.with.double.quote@[123.14\"5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1366 - assertIsFalse "ip.v4.with.double.quote@[123.145\".178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1367 - assertIsFalse "ip.v4.with.double.quote@[123.145.\"178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1368 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90\"]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1369 - assertIsFalse "ip.v4.with.double.quote@[\"123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1370 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]\""                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1371 - assertIsFalse "ip.v4.with.double.quote@\"[123.145.178.90]"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1372 - assertIsFalse "ip.v4.with.empty.bracket@[123.14()5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1373 - assertIsFalse "ip.v4.with.empty.bracket@[123.145().178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1374 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.()178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1375 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90()]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1376 - assertIsFalse "ip.v4.with.empty.bracket@[()123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1377 - assertIsTrue  "ip.v4.with.empty.bracket@[123.145.178.90]()"                             =   2 =  OK 
     *  1378 - assertIsTrue  "ip.v4.with.empty.bracket@()[123.145.178.90]"                             =   2 =  OK 
     *  1379 - assertIsFalse "ip.v4.with.empty.bracket@[123.14{}5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1380 - assertIsFalse "ip.v4.with.empty.bracket@[123.145{}.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1381 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.{}178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1382 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90{}]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1383 - assertIsFalse "ip.v4.with.empty.bracket@[{}123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1384 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]{}"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1385 - assertIsFalse "ip.v4.with.empty.bracket@{}[123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1386 - assertIsFalse "ip.v4.with.empty.bracket@[123.14[]5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1387 - assertIsFalse "ip.v4.with.empty.bracket@[123.145[].178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1388 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.[]178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1389 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90[]]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1390 - assertIsFalse "ip.v4.with.empty.bracket@[[]123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1391 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90][]"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1392 - assertIsFalse "ip.v4.with.empty.bracket@[][123.145.178.90]"                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1393 - assertIsFalse "ip.v4.with.empty.bracket@[123.14<>5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1394 - assertIsFalse "ip.v4.with.empty.bracket@[123.145<>.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1395 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.<>178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1396 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90<>]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1397 - assertIsFalse "ip.v4.with.empty.bracket@[<>123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1398 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]<>"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1399 - assertIsFalse "ip.v4.with.empty.bracket@<>[123.145.178.90]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1400 - assertIsFalse "ip.v4.with.false.bracket1@[123.14)(5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1401 - assertIsFalse "ip.v4.with.false.bracket1@[123.145)(.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1402 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.)(178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1403 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90)(]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1404 - assertIsFalse "ip.v4.with.false.bracket1@[)(123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1405 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90])("                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1406 - assertIsFalse "ip.v4.with.false.bracket1@)([123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1407 - assertIsFalse "ip.v4.with.false.bracket2@[123.14}{5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1408 - assertIsFalse "ip.v4.with.false.bracket2@[123.145}{.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1409 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.}{178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1410 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90}{]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1411 - assertIsFalse "ip.v4.with.false.bracket2@[}{123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1412 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90]}{"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1413 - assertIsFalse "ip.v4.with.false.bracket2@}{[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1414 - assertIsFalse "ip.v4.with.false.bracket3@[123.14][5.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1415 - assertIsFalse "ip.v4.with.false.bracket3@[123.145][.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1416 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.][178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1417 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90][]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1418 - assertIsFalse "ip.v4.with.false.bracket3@[][123.145.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1419 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90]]["                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1420 - assertIsFalse "ip.v4.with.false.bracket3@][[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1421 - assertIsFalse "ip.v4.with.false.bracket4@[123.14><5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1422 - assertIsFalse "ip.v4.with.false.bracket4@[123.145><.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1423 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.><178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1424 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90><]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1425 - assertIsFalse "ip.v4.with.false.bracket4@[><123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1426 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90]><"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1427 - assertIsFalse "ip.v4.with.false.bracket4@><[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1428 - assertIsFalse "ip.v4.with.number0@[123.1405.178.90]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1429 - assertIsFalse "ip.v4.with.number0@[123.1450.178.90]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1430 - assertIsFalse "ip.v4.with.number0@[123.145.0178.90]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1431 - assertIsFalse "ip.v4.with.number0@[123.145.178.900]"                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1432 - assertIsFalse "ip.v4.with.number0@[0123.145.178.90]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1433 - assertIsFalse "ip.v4.with.number0@[123.145.178.90]0"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1434 - assertIsFalse "ip.v4.with.number0@0[123.145.178.90]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1435 - assertIsFalse "ip.v4.with.number9@[123.1495.178.90]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1436 - assertIsFalse "ip.v4.with.number9@[123.1459.178.90]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1437 - assertIsFalse "ip.v4.with.number9@[123.145.9178.90]"                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1438 - assertIsFalse "ip.v4.with.number9@[123.145.178.909]"                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1439 - assertIsFalse "ip.v4.with.number9@[9123.145.178.90]"                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1440 - assertIsFalse "ip.v4.with.number9@[123.145.178.90]9"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1441 - assertIsFalse "ip.v4.with.number9@9[123.145.178.90]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1442 - assertIsFalse "ip.v4.with.numbers@[123.1401234567895.178.90]"                           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1443 - assertIsFalse "ip.v4.with.numbers@[123.1450123456789.178.90]"                           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1444 - assertIsFalse "ip.v4.with.numbers@[123.145.0123456789178.90]"                           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1445 - assertIsFalse "ip.v4.with.numbers@[123.145.178.900123456789]"                           =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1446 - assertIsFalse "ip.v4.with.numbers@[0123456789123.145.178.90]"                           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1447 - assertIsFalse "ip.v4.with.numbers@[123.145.178.90]0123456789"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1448 - assertIsFalse "ip.v4.with.numbers@0123456789[123.145.178.90]"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1449 - assertIsFalse "ip.v4.with.slash@[123.14\5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1450 - assertIsFalse "ip.v4.with.slash@[123.145\.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1451 - assertIsFalse "ip.v4.with.slash@[123.145.\178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1452 - assertIsFalse "ip.v4.with.slash@[123.145.178.90\]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1453 - assertIsFalse "ip.v4.with.slash@[\123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1454 - assertIsFalse "ip.v4.with.slash@[123.145.178.90]\"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1455 - assertIsFalse "ip.v4.with.slash@\[123.145.178.90]"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1456 - assertIsFalse "ip.v4.with.byte.overflow@[123.149995.178.90]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1457 - assertIsFalse "ip.v4.with.byte.overflow@[123.145999.178.90]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1458 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.999178.90]"                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1459 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90999]"                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1460 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90]999"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1461 - assertIsFalse "ip.v4.with.byte.overflow@[999123.145.178.90]"                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1462 - assertIsFalse "ip.v4.with.byte.overflow@999[123.145.178.90]"                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1463 - assertIsFalse "ip.v4.with.no.hex.number@[123.14xyz5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1464 - assertIsFalse "ip.v4.with.no.hex.number@[123.145xyz.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1465 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.xyz178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1466 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90xyz]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1467 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90]xyz"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1468 - assertIsFalse "ip.v4.with.no.hex.number@[xyz123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1469 - assertIsFalse "ip.v4.with.no.hex.number@xyz[123.145.178.90]"                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1470 - assertIsFalse "ip.v4.with.string@[123.14\"str\"5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ip.v4.with.string@[123.145\"str\".178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1472 - assertIsFalse "ip.v4.with.string@[123.145.\"str\"178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1473 - assertIsFalse "ip.v4.with.string@[123.145.178.90\"str\"]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1474 - assertIsFalse "ip.v4.with.string@[123.145.178.90]\"str\""                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1475 - assertIsFalse "ip.v4.with.string@[\"str\"123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1476 - assertIsFalse "ip.v4.with.string@\"str\"[123.145.178.90]"                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1477 - assertIsFalse "ip.v4.with.comment@[123.14(comment)5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1478 - assertIsFalse "ip.v4.with.comment@[123.145(comment).178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1479 - assertIsFalse "ip.v4.with.comment@[123.145.(comment)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1480 - assertIsFalse "ip.v4.with.comment@[123.145.178.90(comment)]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1481 - assertIsTrue  "ip.v4.with.comment@[123.145.178.90](comment)"                            =   2 =  OK 
     *  1482 - assertIsFalse "ip.v4.with.comment@[(comment)123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1483 - assertIsTrue  "ip.v4.with.comment@(comment)[123.145.178.90]"                            =   2 =  OK 
     *  1484 - assertIsTrue  "email@[123.123.123.123]"                                                 =   2 =  OK 
     *  1485 - assertIsFalse "email@111.222.333"                                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1486 - assertIsFalse "email@111.222.333.256"                                                   =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1487 - assertIsFalse "email@[123.123.123.123"                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1488 - assertIsFalse "email@[123.123.123].123"                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1489 - assertIsFalse "email@123.123.123.123]"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1490 - assertIsFalse "email@123.123.[123.123]"                                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1491 - assertIsFalse "ab@988.120.150.10"                                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1492 - assertIsFalse "ab@120.256.256.120"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1493 - assertIsFalse "ab@120.25.1111.120"                                                      =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1494 - assertIsFalse "ab@[188.120.150.10"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1495 - assertIsFalse "ab@188.120.150.10]"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1496 - assertIsFalse "ab@[188.120.150.10].com"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1497 - assertIsTrue  "ab@188.120.150.10"                                                       =   2 =  OK 
     *  1498 - assertIsTrue  "ab@1.0.0.10"                                                             =   2 =  OK 
     *  1499 - assertIsTrue  "ab@120.25.254.120"                                                       =   2 =  OK 
     *  1500 - assertIsTrue  "ab@01.120.150.1"                                                         =   2 =  OK 
     *  1501 - assertIsTrue  "ab@88.120.150.021"                                                       =   2 =  OK 
     *  1502 - assertIsTrue  "ab@88.120.150.01"                                                        =   2 =  OK 
     *  1503 - assertIsTrue  "email@123.123.123.123"                                                   =   2 =  OK 
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1504 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                              =   4 =  OK 
     *  1505 - assertIsFalse "ABC.DEF@[IP"                                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ABC.DEF@[IPv6]"                                                          =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1507 - assertIsFalse "ABC.DEF@[IPv6:]"                                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1508 - assertIsFalse "ABC.DEF@[IPv6:"                                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1509 - assertIsFalse "ABC.DEF@[IPv6::]"                                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1510 - assertIsFalse "ABC.DEF@[IPv6::"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1511 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1512 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1513 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1514 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1515 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1516 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                    =   4 =  OK 
     *  1517 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                                  =   4 =  OK 
     *  1518 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                               =   4 =  OK 
     *  1519 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                              =   4 =  OK 
     *  1520 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                              =   4 =  OK 
     *  1521 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                            =   4 =  OK 
     *  1522 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1523 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  1524 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                        =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1525 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                                  =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1526 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                                =   4 =  OK 
     *  1527 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1528 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1529 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                    =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1530 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1531 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1532 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                           =   4 =  OK 
     *  1533 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1534 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1535 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1536 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1537 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                            =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1538 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1539 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1540 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1541 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1542 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1543 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1544 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1545 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1546 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1547 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1548 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1549 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                            =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1550 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1551 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1552 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1553 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1554 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1555 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1556 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1557 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1558 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1559 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1560 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1561 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1562 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1563 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1564 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1565 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1567 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1568 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1570 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1571 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1573 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1574 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1575 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1576 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1577 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1578 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1579 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1580 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1581 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1582 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1583 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1584 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1585 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1586 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1587 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1588 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1589 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1590 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1591 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1592 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1594 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1595 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1596 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1597 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1598 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1599 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1600 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1601 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1602 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1603 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                            =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1604 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1605 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1606 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1607 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1608 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1609 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1610 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1611 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1612 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1613 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1614 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1615 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1616 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1617 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1618 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1619 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1620 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1621 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1622 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1623 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1624 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1625 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1626 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1627 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1628 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1629 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1630 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1631 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1632 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1633 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1634 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1635 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1636 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1637 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1638 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1639 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1640 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1641 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1642 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1643 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1644 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1645 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1646 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1647 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                          =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1648 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                          =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1649 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1650 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1651 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1652 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1653 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1654 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1655 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1656 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1657 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1658 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1659 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                            =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1660 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1661 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1662 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1663 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1664 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1665 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1666 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1667 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1668 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1669 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1670 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1671 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1672 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1673 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1674 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1675 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1676 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1677 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1678 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                                 =   4 =  OK 
     *  1679 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                 =   4 =  OK 
     *  1680 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                 =   4 =  OK 
     *  1681 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                                 =   4 =  OK 
     *  1682 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1683 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1684 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1685 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1686 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1687 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1688 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1689 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1690 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1691 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1692 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                   =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1693 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1694 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1695 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1696 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1697 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1698 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1699 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1700 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1701 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1702 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1703 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1704 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1705 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1706 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1707 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1708 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1709 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1710 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1711 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1712 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1713 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1714 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1715 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1716 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1717 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1718 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1719 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1720 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1721 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1722 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1723 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1724 - assertIsFalse "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1725 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\""                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1726 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1727 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1728 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1729 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1730 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                        =   4 =  OK 
     *  1731 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                        =   4 =  OK 
     *  1732 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1733 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1734 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1735 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1736 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1737 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1738 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1739 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1740 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1741 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1742 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1743 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1744 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1745 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1746 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1747 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1748 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1749 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1750 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1751 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1752 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1753 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1754 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1755 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1756 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1757 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1758 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1759 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1760 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1761 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1762 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1763 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1764 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1765 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1766 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1767 - assertIsFalse "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1768 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]]["                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1769 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1770 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1771 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1772 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1773 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1774 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1775 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1776 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1777 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1778 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1779 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1780 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1781 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1782 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1783 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1784 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1785 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1786 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1787 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                       =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1788 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                       =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1789 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:29993:4:5:6:7]"                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1790 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]"                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1791 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999"                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1792 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                       =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1793 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1794 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1795 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1796 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1797 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1798 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1799 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                       =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1800 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1801 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1802 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1803 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1804 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1805 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1806 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1807 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1808 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1809 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1810 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1811 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1812 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                       =   4 =  OK 
     *  1813 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                       =   4 =  OK 
     *  1814 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1815 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                           =   4 =  OK 
     *  1816 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                      =   4 =  OK 
     *  1817 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                      =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1818 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1819 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                      =   4 =  OK 
     *  1820 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1821 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"                  =   4 =  OK 
     *  1822 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"                  =   4 =  OK 
     *  1823 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"                  =   4 =  OK 
     *  1824 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                                 =   4 =  OK 
     *  1825 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                                  =   4 =  OK 
     *  1826 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1827 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"                 =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1828 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1829 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1830 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                          =   4 =  OK 
     *  1831 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                          =   4 =  OK 
     *  1832 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                              =   4 =  OK 
     *  1833 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                              =   4 =  OK 
     *  1834 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                           =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1835 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1836 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                          =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1837 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1838 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1839 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1840 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1841 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1842 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.0001]"                                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1843 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- ip4 without brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1844 - assertIsTrue  "ip4.without.brackets.ok1@127.0.0.1"                                      =   2 =  OK 
     *  1845 - assertIsTrue  "ip4.without.brackets.ok2@0.0.0.0"                                        =   2 =  OK 
     *  1846 - assertIsFalse "ip4.without.brackets.but.with.space.at.end@127.0.0.1 "                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1847 - assertIsFalse "ip4.without.brackets.byte.overflow@127.0.999.1"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1848 - assertIsFalse "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1"              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1849 - assertIsFalse "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1"              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1850 - assertIsFalse "ip4.without.brackets.negative.number@127.0.-1.1"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1851 - assertIsFalse "ip4.without.brackets.point.error1@127.0..0.1"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1852 - assertIsFalse "ip4.without.brackets.point.error1@127...1"                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1853 - assertIsFalse "ip4.without.brackets.point.error2@127001"                                =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1854 - assertIsFalse "ip4.without.brackets.point.error3@127.0.0."                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1855 - assertIsFalse "ip4.without.brackets.character.error@127.0.A.1"                          =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1856 - assertIsFalse "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1"               =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1857 - assertIsTrue  "ip4.without.brackets.normal.tld1@127.0.0.1.com"                          =   0 =  OK 
     *  1858 - assertIsTrue  "ip4.without.brackets.normal.tld2@127.0.99.1.com"                         =   0 =  OK 
     *  1859 - assertIsTrue  "ip4.without.brackets.normal.tld3@127.0.A.1.com"                          =   0 =  OK 
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *  1860 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                      =   1 =  OK 
     *  1861 - assertIsTrue  "\"ABC\".\"DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1862 - assertIsFalse "-\"ABC\".\"DEF\"@GHI.DE"                                                 = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
     *  1863 - assertIsFalse "\"ABC\"-.\"DEF\"@GHI.DE"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1864 - assertIsFalse "\"ABC\".-\"DEF\"@GHI.DE"                                                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1865 - assertIsFalse ".\"ABC\".\"DEF\"@GHI.DE"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1866 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                      =   1 =  OK 
     *  1867 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                      =   1 =  OK 
     *  1868 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                    =   1 =  OK 
     *  1869 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                     =   1 =  OK 
     *  1870 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                     =   1 =  OK 
     *  1871 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                     =   1 =  OK 
     *  1872 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1873 - assertIsFalse "\"\"@GHI.DE"                                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1874 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1875 - assertIsFalse "A@G\"HI.DE"                                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1876 - assertIsFalse "\"@GHI.DE"                                                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1877 - assertIsFalse "ABC.DEF.\""                                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1878 - assertIsFalse "ABC.DEF@\"\""                                                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1879 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1880 - assertIsFalse "ABC.DEF@GHI.DE\""                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1881 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1882 - assertIsFalse "\"Escape.Sequenz.Ende\""                                                 =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1883 - assertIsFalse "ABC.DEF\"GHI.DE"                                                         =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1884 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1885 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1886 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1887 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                      =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1888 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                     =   1 =  OK 
     *  1889 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                     =   1 =  OK 
     *  1890 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                              =   1 =  OK 
     *  1891 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                              =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1892 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1893 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1894 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1895 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1896 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1897 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1898 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1899 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1900 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1901 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1902 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1903 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1904 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1905 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1906 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1907 - assertIsFalse "\"\".ABC.DEF@GHI.DE"                                                     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1908 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1909 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1910 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                    =   1 =  OK 
     *  1911 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                                  =   1 =  OK 
     *  1912 - assertIsTrue  "\"ABC.\\".\\".DEF\"@GHI.DE"                                              =   1 =  OK 
     *  1913 - assertIsTrue  "\"ABC.\\"\\".DEF\"@GHI.DE"                                               =   1 =  OK 
     *  1914 - assertIsTrue  "\"ABC.\\" \@ \\".DEF\"@GHI.DE"                                           =   1 =  OK 
     *  1915 - assertIsFalse "\"Ende.am.Eingabeende\""                                                 =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1916 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1917 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                    =   1 =  OK 
     *  1918 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1919 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1920 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1921 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                   =   1 =  OK 
     *  1922 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                           =   1 =  OK 
     *  1923 - assertIsFalse "\"Joe Smith\" email@domain.com"                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1924 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1925 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *  1926 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                        =   6 =  OK 
     *  1927 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1928 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                        =   6 =  OK 
     *  1929 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                    =   6 =  OK 
     *  1930 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                              =   6 =  OK 
     *  1931 - assertIsFalse "ABC.DEF@             (MNO)"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1932 - assertIsFalse "ABC.DEF@   .         (MNO)"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1933 - assertIsFalse "ABC.DEF              (MNO)"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1934 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                              =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1935 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1936 - assertIsFalse "ABC.DEF@GHI.JKL          "                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1937 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1938 - assertIsFalse "("                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1939 - assertIsFalse ")"                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1940 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                      =   6 =  OK 
     *  1941 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                       =   6 =  OK 
     *  1942 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                       =   6 =  OK 
     *  1943 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                       =   6 =  OK 
     *  1944 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                       =   6 =  OK 
     *  1945 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1946 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1947 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                    = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1948 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1949 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1950 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1951 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1952 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1953 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                       = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1954 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1955 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                   = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1956 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                   = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1957 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1958 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                      = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1959 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                        =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1960 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1961 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1962 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1963 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                      =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1964 - assertIsFalse "ABC(DEF@GHI.JKL"                                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1965 - assertIsFalse "ABC.DEF@GHI)JKL"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1966 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1967 - assertIsFalse "ABC.DEF)@GHI.JKL"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1968 - assertIsFalse "ABC(DEF@GHI).JKL"                                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1969 - assertIsFalse "ABC(DEF.GHI).JKL"                                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1970 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                       =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1971 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1972 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1973 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1974 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                    =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1975 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1976 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                              =   2 =  OK 
     *  1977 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                             = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  1978 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                              =   2 =  OK 
     *  1979 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                          =   2 =  OK 
     *  1980 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1981 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                  =   4 =  OK 
     *  1982 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                  =   4 =  OK 
     *  1983 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                              =   4 =  OK 
     *  1984 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1985 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                               =   6 =  OK 
     *  1986 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                               =   6 =  OK 
     *  1987 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                               = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1988 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1989 - assertIsTrue  "(comment)john.smith@example.com"                                         =   6 =  OK 
     *  1990 - assertIsTrue  "john.smith(comment)@example.com"                                         =   6 =  OK 
     *  1991 - assertIsTrue  "john.smith@(comment)example.com"                                         =   6 =  OK 
     *  1992 - assertIsTrue  "john.smith@example.com(comment)"                                         =   6 =  OK 
     *  1993 - assertIsFalse "john.smith@exampl(comment)e.com"                                         = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1994 - assertIsFalse "john.s(comment)mith@example.com"                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1995 - assertIsFalse "john.smith(comment)@(comment)example.com"                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1996 - assertIsFalse "john.smith(com@ment)example.com"                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1997 - assertIsFalse "email( (nested) )@plus.com"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1998 - assertIsFalse "email)mirror(@plus.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1999 - assertIsFalse "email@plus.com (not closed comment"                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2000 - assertIsFalse "email(with @ in comment)plus.com"                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2001 - assertIsTrue  "email@domain.com (joe Smith)"                                            =   6 =  OK 
     *  2002 - assertIsFalse "a@abc(bananas)def.com"                                                   = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2003 - assertIsTrue  "\"address(comment\"@example.com"                                         =   1 =  OK 
     *  2004 - assertIsFalse "address(co\"mm\"ent)@example.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2005 - assertIsFalse "address(co\"mment)@example.com"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *  2006 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                               =   0 =  OK 
     *  2007 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                               =   0 =  OK 
     *  2008 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                                =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2009 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                                =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  2010 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                           =   0 =  OK 
     *  2011 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                     =   1 =  OK 
     *  2012 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                             =   1 =  OK 
     *  2013 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2014 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2015 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2016 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2017 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2018 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2019 - assertIsFalse "ABC DEF <A@A>"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2020 - assertIsFalse "<A@A> ABC DEF"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2021 - assertIsFalse "ABC DEF <>"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2022 - assertIsFalse "<> ABC DEF"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2023 - assertIsFalse "<"                                                                       =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  2024 - assertIsFalse ">"                                                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2025 - assertIsFalse "<         >"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2026 - assertIsFalse "< <     > >"                                                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2027 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                       =   0 =  OK 
     *  2028 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                      = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2029 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2030 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                      =   0 =  OK 
     *  2031 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2032 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                      =   0 =  OK 
     *  2033 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2034 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                              =   6 =  OK 
     *  2035 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2036 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                             = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2037 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                             =   6 =  OK 
     *  2038 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                             = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2039 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                             =   6 =  OK 
     *  2040 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                             = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  2041 - assertIsTrue  "Joe Smith <email@domain.com>"                                            =   0 =  OK 
     *  2042 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2043 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2044 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"         =   9 =  OK 
     *  2045 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"         =   9 =  OK 
     *  2046 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"         =   9 =  OK 
     *  2047 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "        =   9 =  OK 
     *  2048 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2049 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2050 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2051 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2052 - assertIsFalse "Test |<gaaf <email@domain.com>"                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2053 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2054 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2055 - assertIsFalse "<null>@mail.com"                                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2056 - assertIsFalse "email.adress@domain.com <display name>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2057 - assertIsFalse "eimail.adress@domain.com <eimail.adress@domain.com>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2058 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2059 - assertIsFalse "<eimail>.<adress>@domain.com"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2060 - assertIsFalse "<eimail>.<adress> email.adress@domain.com"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *  2061 - assertIsTrue  "A@B.CD"                                                                  =   0 =  OK 
     *  2062 - assertIsFalse "A@B.C"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2063 - assertIsFalse "A@COM"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2064 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                         =   0 =  OK 
     *  2065 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  2066 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  2067 - assertIsTrue  "A@B.CD"                                                                  =   0 =  OK 
     *  2068 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  2069 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2070 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2071 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2072 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld> True64 " =   0 =  OK 
     *  2073 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2074 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2075 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2076 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"   =   0 =  OK 
     *  2077 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  2078 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2079 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2080 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2081 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2082 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2083 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2084 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2085 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2086 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2087 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2088 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2089 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2090 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
     *  2091 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  2092 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  2093 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2094 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2095 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  2096 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2097 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2098 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2099 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
     *  2100 - assertIsTrue  "email@domain.topleveldomain"                                             =   0 =  OK 
     *  2101 - assertIsTrue  "email@email.email.mydomain"                                              =   0 =  OK 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     *  2102 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                        =   6 =  OK 
     *  2103 - assertIsTrue  "\"MaxMustermann\"@example.com"                                           =   1 =  OK 
     *  2104 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                              =   1 =  OK 
     *  2105 - assertIsTrue  "\".John.Doe\"@example.com"                                               =   1 =  OK 
     *  2106 - assertIsTrue  "\"John.Doe.\"@example.com"                                               =   1 =  OK 
     *  2107 - assertIsTrue  "\"John..Doe\"@example.com"                                               =   1 =  OK 
     *  2108 - assertIsTrue  "john.smith(comment)@example.com"                                         =   6 =  OK 
     *  2109 - assertIsTrue  "(comment)john.smith@example.com"                                         =   6 =  OK 
     *  2110 - assertIsTrue  "john.smith@(comment)example.com"                                         =   6 =  OK 
     *  2111 - assertIsTrue  "john.smith@example.com(comment)"                                         =   6 =  OK 
     *  2112 - assertIsTrue  "john.smith@example.com"                                                  =   0 =  OK 
     *  2113 - assertIsTrue  "joeuser+tag@example.com"                                                 =   0 =  OK 
     *  2114 - assertIsTrue  "jsmith@[192.168.2.1]"                                                    =   2 =  OK 
     *  2115 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                               =   4 =  OK 
     *  2116 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                       =   0 =  OK 
     *  2117 - assertIsTrue  "Marc Dupont <md118@example.com>"                                         =   0 =  OK 
     *  2118 - assertIsTrue  "simple@example.com"                                                      =   0 =  OK 
     *  2119 - assertIsTrue  "very.common@example.com"                                                 =   0 =  OK 
     *  2120 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                          =   0 =  OK 
     *  2121 - assertIsTrue  "other.email-with-hyphen@example.com"                                     =   0 =  OK 
     *  2122 - assertIsTrue  "fully-qualified-domain@example.com"                                      =   0 =  OK 
     *  2123 - assertIsTrue  "user.name+tag+sorting@example.com"                                       =   0 =  OK 
     *  2124 - assertIsTrue  "user+mailbox/department=shipping@example.com"                            =   0 =  OK 
     *  2125 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                        =   0 =  OK 
     *  2126 - assertIsTrue  "x@example.com"                                                           =   0 =  OK 
     *  2127 - assertIsTrue  "info@firma.org"                                                          =   0 =  OK 
     *  2128 - assertIsTrue  "example-indeed@strange-example.com"                                      =   0 =  OK 
     *  2129 - assertIsTrue  "admin@mailserver1"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2130 - assertIsTrue  "example@s.example"                                                       =   0 =  OK 
     *  2131 - assertIsTrue  "\" \"@example.org"                                                       =   1 =  OK 
     *  2132 - assertIsTrue  "mailhost!username@example.org"                                           =   0 =  OK 
     *  2133 - assertIsTrue  "user%example.com@example.org"                                            =   0 =  OK 
     *  2134 - assertIsTrue  "joe25317@NOSPAMexample.com"                                              =   0 =  OK 
     *  2135 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                              =   0 =  OK 
     *  2136 - assertIsTrue  "nama@contoh.com"                                                         =   0 =  OK 
     *  2137 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                              =   0 =  OK 
     *  2138 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2139 - assertIsFalse "Abc.example.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2140 - assertIsFalse "Abc..123@example.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2141 - assertIsFalse "A@b@c@example.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2142 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2143 - assertIsFalse "just\"not\"right@example.com"                                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2144 - assertIsFalse "this is\"not\allowed@example.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2145 - assertIsFalse "this\ still\\"not\\allowed@example.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2146 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2147 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"      =   0 =  OK 
     *  2148 - assertIsTrue  "(buero)office@example.com"                                               =   6 =  OK 
     *  2149 - assertIsTrue  "office(etage-3)@example.com"                                             =   6 =  OK 
     *  2150 - assertIsFalse "off(kommentar)ice@example.com"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2151 - assertIsTrue  "\"(buero)office\"@example.com"                                           =   1 =  OK 
     *  2152 - assertIsTrue  "\"office(etage-3)\"@example.com"                                         =   1 =  OK 
     *  2153 - assertIsTrue  "\"off(kommentar)ice\"@example.com"                                       =   1 =  OK 
     *  2154 - assertIsTrue  "\"address(comment)\"@example.com"                                        =   1 =  OK 
     *  2155 - assertIsTrue  "Buero <office@example.com>"                                              =   0 =  OK 
     *  2156 - assertIsTrue  "\"vorname(Kommentar).nachname\"@provider.de"                             =   1 =  OK 
     *  2157 - assertIsTrue  "\"Herr \\"Kaiser\\" Franz Beckenbauer\" <local-part@domain-part>"        =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2158 - assertIsTrue  "Erwin Empfaenger <erwin@example.com>"                                    =   0 =  OK 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     *  2159 - assertIsFalse "nolocalpart.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2160 - assertIsFalse "test@example.com test"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2161 - assertIsFalse "user  name@example.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2162 - assertIsFalse "user   name@example.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2163 - assertIsFalse "example.@example.co.uk"                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2164 - assertIsFalse "example@example@example.co.uk"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2165 - assertIsFalse "(test_exampel@example.fr}"                                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2166 - assertIsFalse "example(example)example@example.co.uk"                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2167 - assertIsFalse ".example@localhost"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2168 - assertIsFalse "ex\ample@localhost"                                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2169 - assertIsFalse "example@local\host"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2170 - assertIsFalse "example@localhost."                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2171 - assertIsFalse "user name@example.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2172 - assertIsFalse "username@ example . com"                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2173 - assertIsFalse "example@(fake}.com"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2174 - assertIsFalse "example@(fake.com"                                                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2175 - assertIsTrue  "username@example.com"                                                    =   0 =  OK 
     *  2176 - assertIsTrue  "usern.ame@example.com"                                                   =   0 =  OK 
     *  2177 - assertIsFalse "user[na]me@example.com"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2178 - assertIsFalse "\"\"\"@iana.org"                                                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2179 - assertIsFalse "\"\\"@iana.org"                                                          =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2180 - assertIsFalse "\"test\"test@iana.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2181 - assertIsFalse "\"test\"\"test\"@iana.org"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2182 - assertIsTrue  "\"test\".\"test\"@iana.org"                                              =   1 =  OK 
     *  2183 - assertIsTrue  "\"test\".test@iana.org"                                                  =   1 =  OK 
     *  2184 - assertIsFalse "\"test\\"@iana.org"                                                      =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2185 - assertIsFalse "\r\ntest@iana.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2186 - assertIsFalse "\r\n test@iana.org"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2187 - assertIsFalse "\r\n \r\ntest@iana.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2188 - assertIsFalse "\r\n \r\n test@iana.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2189 - assertIsFalse "test@iana.org \r\n"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2190 - assertIsFalse "test@iana.org \r\n "                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2191 - assertIsFalse "test@iana.org \r\n \r\n"                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2192 - assertIsFalse "test@iana.org \r\n\r\n"                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2193 - assertIsFalse "test@iana.org  \r\n\r\n "                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2194 - assertIsFalse "test@iana/icann.org"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2195 - assertIsFalse "test@foo;bar.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2196 - assertIsFalse "a@test.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2197 - assertIsFalse "comment)example@example.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2198 - assertIsFalse "comment(example))@example.com"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2199 - assertIsFalse "example@example)comment.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2200 - assertIsFalse "example@example(comment)).com"                                           = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2201 - assertIsFalse "example@[1.2.3.4"                                                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2202 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2203 - assertIsFalse "exam(ple@exam).ple"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2204 - assertIsFalse "example@(example))comment.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2205 - assertIsTrue  "example@example.com"                                                     =   0 =  OK 
     *  2206 - assertIsTrue  "example@example.co.uk"                                                   =   0 =  OK 
     *  2207 - assertIsTrue  "example_underscore@example.fr"                                           =   0 =  OK 
     *  2208 - assertIsTrue  "exam'ple@example.com"                                                    =   0 =  OK 
     *  2209 - assertIsTrue  "exam\ ple@example.com"                                                   =   0 =  OK 
     *  2210 - assertIsFalse "example((example))@fakedfake.co.uk"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2211 - assertIsFalse "example@faked(fake).co.uk"                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2212 - assertIsTrue  "example+@example.com"                                                    =   0 =  OK 
     *  2213 - assertIsTrue  "example@with-hyphen.example.com"                                         =   0 =  OK 
     *  2214 - assertIsTrue  "with-hyphen@example.com"                                                 =   0 =  OK 
     *  2215 - assertIsTrue  "example@1leadingnum.example.com"                                         =   0 =  OK 
     *  2216 - assertIsTrue  "1leadingnum@example.com"                                                 =   0 =  OK 
     *  2217 - assertIsTrue  "инфо@письмо.рф"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2218 - assertIsTrue  "\"username\"@example.com"                                                =   1 =  OK 
     *  2219 - assertIsTrue  "\"user.name\"@example.com"                                               =   1 =  OK 
     *  2220 - assertIsTrue  "\"user name\"@example.com"                                               =   1 =  OK 
     *  2221 - assertIsTrue  "\"user@name\"@example.com"                                               =   1 =  OK 
     *  2222 - assertIsFalse "\"\a\"@iana.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2223 - assertIsTrue  "\"test\ test\"@iana.org"                                                 =   1 =  OK 
     *  2224 - assertIsFalse "\"\"@iana.org"                                                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2225 - assertIsFalse "\"\"@[]"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2226 - assertIsTrue  "\"\\"\"@iana.org"                                                        =   1 =  OK 
     *  2227 - assertIsTrue  "example@localhost"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     *  2228 - assertIsFalse "<')))><@fish.left.com"                                                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2229 - assertIsFalse "><(((*>@fish.right.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2230 - assertIsFalse " check@this.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2231 - assertIsFalse " email@example.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2232 - assertIsFalse ".....@a...."                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2233 - assertIsFalse "..@test.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2234 - assertIsFalse "..@test.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2235 - assertIsTrue  "\"test....\"@gmail.com"                                                  =   1 =  OK 
     *  2236 - assertIsFalse "test....@gmail.com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2237 - assertIsTrue  "name@xn--4ca9at.at"                                                      =   0 =  OK 
     *  2238 - assertIsTrue  "simon-@hotmail.com"                                                      =   0 =  OK 
     *  2239 - assertIsTrue  "!@mydomain.net"                                                          =   0 =  OK 
     *  2240 - assertIsTrue  "sean.o'leary@cobbcounty.org"                                             =   0 =  OK 
     *  2241 - assertIsFalse "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2242 - assertIsFalse "a-b'c_d.e@f-g.h"                                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2243 - assertIsFalse "a-b'c_d.@f-g.h"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2244 - assertIsFalse "a-b'c_d.e@f-.h"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2245 - assertIsTrue  "\"root\"@example.com"                                                    =   1 =  OK 
     *  2246 - assertIsTrue  "root@example.com"                                                        =   0 =  OK 
     *  2247 - assertIsFalse ".@s.dd"                                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2248 - assertIsFalse ".@s.dd"                                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2249 - assertIsFalse ".a@test.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2250 - assertIsFalse ".a@test.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2251 - assertIsFalse ".abc@somedomain.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2252 - assertIsFalse ".dot@example.com"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2253 - assertIsFalse ".email@domain.com"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2254 - assertIsFalse ".journaldev@journaldev.com"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2255 - assertIsFalse ".username@yahoo.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2256 - assertIsFalse ".username@yahoo.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2257 - assertIsFalse ".xxxx@mysite.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2258 - assertIsFalse "asdf@asdf"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2259 - assertIsFalse "123@$.xyz"                                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2260 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2261 - assertIsFalse "@%^%#$@#$@#.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2262 - assertIsFalse "@b.com"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2263 - assertIsFalse "@domain.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2264 - assertIsFalse "@example.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2265 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2266 - assertIsFalse "@yahoo.com"                                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2267 - assertIsFalse "@you.me.net"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2268 - assertIsFalse "A@b@c@example.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2269 - assertIsFalse "Abc.example.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2270 - assertIsFalse "Abc@example.com."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2271 - assertIsFalse "Display Name <email@plus.com> (after name with display)"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2272 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2273 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2274 - assertIsFalse "Foobar Some@thing.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2275 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2276 - assertIsFalse "MailTo:casesensitve@domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2277 - assertIsFalse "No -foo@bar.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2278 - assertIsFalse "No asd@-bar.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2279 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2280 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2281 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2282 - assertIsFalse "\"Joe\Blow\"@example.com"                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2283 - assertIsFalse "\"\"Joe Smith email@domain.com"                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2284 - assertIsFalse "\"\"Joe Smith' email@domain.com"                                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2285 - assertIsFalse "\"\"Joe Smith\"\"email@domain.com"                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2286 - assertIsFalse "\"qu@example.com"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2287 - assertIsFalse "\$A12345@example.com"                                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2288 - assertIsFalse "_@bde.cc,"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2289 - assertIsFalse "a..b@bde.cc"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2290 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2291 - assertIsFalse "a.b@example,co.de"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2292 - assertIsFalse "a.b@example,com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2293 - assertIsFalse "a>b@somedomain.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2294 - assertIsFalse "a@.com"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2295 - assertIsFalse "a@b."                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2296 - assertIsFalse "a@b.-de.cc"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2297 - assertIsFalse "a@b._de.cc"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2298 - assertIsFalse "a@bde-.cc"                                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2299 - assertIsFalse "a@bde.c-c"                                                               =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2300 - assertIsFalse "a@bde.cc."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2301 - assertIsFalse "a@bde_.cc"                                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2302 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2303 - assertIsFalse "ab@120.25.1111.120"                                                      =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2304 - assertIsFalse "ab@120.256.256.120"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2305 - assertIsFalse "ab@188.120.150.10]"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2306 - assertIsFalse "ab@988.120.150.10"                                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2307 - assertIsFalse "ab@[188.120.150.10"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2308 - assertIsFalse "ab@[188.120.150.10].com"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2309 - assertIsFalse "ab@b+de.cc"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2310 - assertIsFalse "ab@sd@dd"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2311 - assertIsFalse "abc.@somedomain.com"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2312 - assertIsFalse "abc@def@example.com"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2313 - assertIsFalse "abc@gmail..com"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2314 - assertIsFalse "abc@gmail.com.."                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2315 - assertIsFalse "abc\"defghi\"xyz@example.com"                                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2316 - assertIsFalse "abc\@example.com"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2317 - assertIsFalse "abc\\"def\\"ghi@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2318 - assertIsFalse "abc\\@def@example.com"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2319 - assertIsFalse "as3d@dac.coas-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2320 - assertIsFalse "asd@dasd@asd.cm"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2321 - assertIsFalse "check@this..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2322 - assertIsFalse "check@thiscom"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2323 - assertIsFalse "da23@das..com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2324 - assertIsFalse "dad@sds"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2325 - assertIsFalse "dasddas-@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2326 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2327 - assertIsFalse "dot.@example.com"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2328 - assertIsFalse "doug@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2329 - assertIsFalse "email( (nested) )@plus.com"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2330 - assertIsFalse "email(with @ in comment)plus.com"                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2331 - assertIsFalse "email)mirror(@plus.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2332 - assertIsFalse "email..email@domain.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2333 - assertIsFalse "email..email@domain.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2334 - assertIsFalse "email.@domain.com"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2335 - assertIsFalse "email.domain.com"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2336 - assertIsFalse "email@#hash.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2337 - assertIsFalse "email@.domain.com"                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2338 - assertIsFalse "email@111.222.333"                                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2339 - assertIsFalse "email@111.222.333.256"                                                   =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2340 - assertIsFalse "email@123.123.123.123]"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2341 - assertIsFalse "email@123.123.[123.123]"                                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2342 - assertIsFalse "email@=qowaiv.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2343 - assertIsFalse "email@[123.123.123.123"                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2344 - assertIsFalse "email@[123.123.123].123"                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2345 - assertIsFalse "email@caret^xor.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2346 - assertIsFalse "email@colon:colon.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2347 - assertIsFalse "email@dollar$.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2348 - assertIsFalse "email@domain"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2349 - assertIsFalse "email@domain-.com"                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2350 - assertIsFalse "email@domain..com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2351 - assertIsFalse "email@domain.com-"                                                       =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2352 - assertIsFalse "email@domain.com."                                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2353 - assertIsFalse "email@domain.com."                                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2354 - assertIsFalse "email@domain.com>"                                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2355 - assertIsFalse "email@domain@domain.com"                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2356 - assertIsFalse "email@example"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2357 - assertIsFalse "email@example..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2358 - assertIsFalse "email@example.co.uk."                                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2359 - assertIsFalse "email@example.com "                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2360 - assertIsFalse "email@exclamation!mark.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2361 - assertIsFalse "email@grave`accent.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2362 - assertIsFalse "email@mailto:domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2363 - assertIsFalse "email@obelix*asterisk.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2364 - assertIsFalse "email@plus+.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2365 - assertIsFalse "email@plus.com (not closed comment"                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2366 - assertIsFalse "email@p|pe.com"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2367 - assertIsFalse "email@question?mark.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2368 - assertIsFalse "email@r&amp;d.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2369 - assertIsFalse "email@rightbracket}.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2370 - assertIsFalse "email@wave~tilde.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2371 - assertIsFalse "email@{leftbracket.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2372 - assertIsFalse "f...bar@gmail.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2373 - assertIsFalse "fa ke@false.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2374 - assertIsFalse "fake@-false.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2375 - assertIsFalse "fake@fal se.com"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2376 - assertIsFalse "fdsa"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2377 - assertIsFalse "fdsa@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2378 - assertIsFalse "fdsa@fdsa"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2379 - assertIsFalse "fdsa@fdsa."                                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2380 - assertIsFalse "foo.bar#gmail.co.u"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2381 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2382 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2383 - assertIsFalse "foo~&(&)(@bar.com"                                                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2384 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2385 - assertIsFalse "get_at_m.e@gmail"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2386 - assertIsFalse "hallo2ww22@example....caaaao"                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2387 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2388 - assertIsFalse "hello world@example.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2389 - assertIsFalse "invalid.email.com"                                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2390 - assertIsFalse "invalid@email@domain.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2391 - assertIsFalse "isis@100%.nl"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2392 - assertIsFalse "j..s@proseware.com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2393 - assertIsFalse "j.@server1.proseware.com"                                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2394 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2395 - assertIsFalse "journaldev"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2396 - assertIsFalse "journaldev()*@gmail.com"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2397 - assertIsFalse "journaldev..2002@gmail.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2398 - assertIsFalse "journaldev.@gmail.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2399 - assertIsFalse "journaldev123@.com"                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2400 - assertIsFalse "journaldev123@.com.com"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2401 - assertIsFalse "journaldev123@gmail.a"                                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2402 - assertIsFalse "journaldev@%*.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2403 - assertIsFalse "journaldev@.com.my"                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2404 - assertIsFalse "journaldev@gmail.com.1a"                                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2405 - assertIsFalse "journaldev@journaldev@gmail.com"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2406 - assertIsFalse "js@proseware..com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2407 - assertIsFalse "mailto:email@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2408 - assertIsFalse "mailto:mailto:email@domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2409 - assertIsFalse "me..2002@gmail.com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2410 - assertIsFalse "me.@gmail.com"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2411 - assertIsFalse "me123@.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2412 - assertIsFalse "me123@.com.com"                                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2413 - assertIsFalse "me@%*.com"                                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2414 - assertIsFalse "me@.com.my"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2415 - assertIsFalse "me@gmail.com.1a"                                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2416 - assertIsFalse "me@me@gmail.com"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2417 - assertIsFalse "myemail@@sample.com"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2418 - assertIsFalse "myemail@sa@mple.com"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2419 - assertIsFalse "myemailsample.com"                                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2420 - assertIsFalse "ote\"@example.com"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2421 - assertIsFalse "pio_pio@#factory.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2422 - assertIsFalse "pio_pio@factory.c#om"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2423 - assertIsFalse "pio_pio@factory.c*om"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2424 - assertIsFalse "plain.address"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2425 - assertIsFalse "plainaddress"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2426 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2427 - assertIsFalse "this is not valid@email$com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2428 - assertIsFalse "this\ still\\"not\allowed@example.com"                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2429 - assertIsFalse "two..dot@example.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2430 - assertIsFalse "user#domain.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2431 - assertIsFalse "username@yahoo..com"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2432 - assertIsFalse "username@yahoo.c"                                                        =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2433 - assertIsTrue  "username@domain.com"                                                     =   0 =  OK 
     *  2434 - assertIsTrue  "_username@domain.com"                                                    =   0 =  OK 
     *  2435 - assertIsTrue  "username_@domain.com"                                                    =   0 =  OK 
     *  2436 - assertIsFalse ""                                                                        =  11 =  OK    Laenge: Eingabe ist Leerstring
     *  2437 - assertIsFalse " "                                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2438 - assertIsFalse " jkt@gmail.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2439 - assertIsFalse "jkt@gmail.com "                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2440 - assertIsFalse "jkt@ gmail.com"                                                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2441 - assertIsFalse "jkt@g mail.com"                                                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2442 - assertIsFalse "jkt @gmail.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2443 - assertIsFalse "j kt@gmail.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2444 - assertIsFalse "xxx@u*.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2445 - assertIsFalse "xxxx..1234@yahoo.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2446 - assertIsFalse "xxxx.ourearth.com"                                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2447 - assertIsFalse "xxxx123@gmail.b"                                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2448 - assertIsFalse "xxxx@.com.my"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2449 - assertIsFalse "xxxx@.org.org"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2450 - assertIsFalse "xxxxx()*@gmail.com"                                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2451 - assertIsFalse "{something}@{something}.{something}"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2452 - assertIsTrue  "mymail\@hello@hotmail.com"                                               =   0 =  OK 
     *  2453 - assertIsTrue  "!def!xyz%abc@example.com"                                                =   0 =  OK 
     *  2454 - assertIsTrue  "!sd@gh.com"                                                              =   0 =  OK 
     *  2455 - assertIsTrue  "$A12345@example.com"                                                     =   0 =  OK 
     *  2456 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                   =   0 =  OK 
     *  2457 - assertIsTrue  "%2@gmail.com"                                                            =   0 =  OK 
     *  2458 - assertIsTrue  "--@ooo.ooo"                                                              =   0 =  OK 
     *  2459 - assertIsTrue  "-@bde.cc"                                                                =   0 =  OK 
     *  2460 - assertIsTrue  "-asd@das.com"                                                            =   0 =  OK 
     *  2461 - assertIsTrue  "1234567890@domain.com"                                                   =   0 =  OK 
     *  2462 - assertIsTrue  "1@domain.com"                                                            =   0 =  OK 
     *  2463 - assertIsTrue  "1@gmail.com"                                                             =   0 =  OK 
     *  2464 - assertIsTrue  "1_example@something.gmail.com"                                           =   0 =  OK 
     *  2465 - assertIsTrue  "2@bde.cc"                                                                =   0 =  OK 
     *  2466 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                                  =   0 =  OK 
     *  2467 - assertIsTrue  "<boss@nil.test>"                                                         =   0 =  OK 
     *  2468 - assertIsTrue  "<john@doe.com>"                                                          =   0 =  OK 
     *  2469 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                               =   0 =  OK 
     *  2470 - assertIsTrue  "Abc.123@example.com"                                                     =   0 =  OK 
     *  2471 - assertIsTrue  "Abc@10.42.0.1"                                                           =   2 =  OK 
     *  2472 - assertIsTrue  "Abc@example.com"                                                         =   0 =  OK 
     *  2473 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                    =   0 =  OK 
     *  2474 - assertIsTrue  "Fred\ Bloggs@example.com"                                                =   0 =  OK 
     *  2475 - assertIsTrue  "Joe.\\Blow@example.com"                                                  =   0 =  OK 
     *  2476 - assertIsTrue  "John <john@doe.com>"                                                     =   0 =  OK 
     *  2477 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                             =   0 =  OK 
     *  2478 - assertIsTrue  "This is <john@127.0.0.1>"                                                =   2 =  OK 
     *  2479 - assertIsTrue  "This is <john@[127.0.0.1]>"                                              =   2 =  OK 
     *  2480 - assertIsTrue  "Who? <one@y.test>"                                                       =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2481 - assertIsTrue  "\" \"@example.org"                                                       =   1 =  OK 
     *  2482 - assertIsTrue  "\"%2\"@gmail.com"                                                        =   1 =  OK 
     *  2483 - assertIsTrue  "\"Abc@def\"@example.com"                                                 =   1 =  OK 
     *  2484 - assertIsTrue  "\"Abc\@def\"@example.com"                                                =   1 =  OK 
     *  2485 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                       =   1 =  OK 
     *  2486 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                             =   1 =  OK 
     *  2487 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                            =   1 =  OK 
     *  2488 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                      =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2489 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                               =   1 =  OK 
     *  2490 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                               =   1 =  OK 
     *  2491 - assertIsTrue  "\"a..b\"@gmail.com"                                                      =   1 =  OK 
     *  2492 - assertIsTrue  "\"a@b\"@example.com"                                                     =   1 =  OK 
     *  2493 - assertIsTrue  "\"a_b\"@gmail.com"                                                       =   1 =  OK 
     *  2494 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                            =   1 =  OK 
     *  2495 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                     =   1 =  OK 
     *  2496 - assertIsTrue  "\"foo\@bar\"@Something.com"                                              =   1 =  OK 
     *  2497 - assertIsTrue  "\"j\\"s\"@proseware.com"                                                 =   1 =  OK 
     *  2498 - assertIsTrue  "\"myemail@sa\"@mple.com"                                                 =   1 =  OK 
     *  2499 - assertIsTrue  "_-_@bde.cc"                                                              =   0 =  OK 
     *  2500 - assertIsTrue  "_@gmail.com"                                                             =   0 =  OK 
     *  2501 - assertIsTrue  "_dasd@sd.com"                                                            =   0 =  OK 
     *  2502 - assertIsTrue  "_dasd_das_@9.com"                                                        =   0 =  OK 
     *  2503 - assertIsTrue  "_somename@example.com"                                                   =   0 =  OK 
     *  2504 - assertIsTrue  "a&d@somedomain.com"                                                      =   0 =  OK 
     *  2505 - assertIsTrue  "a*d@somedomain.com"                                                      =   0 =  OK 
     *  2506 - assertIsTrue  "a+b@bde.cc"                                                              =   0 =  OK 
     *  2507 - assertIsTrue  "a+b@c.com"                                                               =   0 =  OK 
     *  2508 - assertIsTrue  "a-b@bde.cc"                                                              =   0 =  OK 
     *  2509 - assertIsTrue  "a.a@test.com"                                                            =   0 =  OK 
     *  2510 - assertIsTrue  "a.b@com"                                                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2511 - assertIsTrue  "a/d@somedomain.com"                                                      =   0 =  OK 
     *  2512 - assertIsTrue  "a2@bde.cc"                                                               =   0 =  OK 
     *  2513 - assertIsTrue  "a@123.45.67.89"                                                          =   2 =  OK 
     *  2514 - assertIsTrue  "a@b.c.com"                                                               =   0 =  OK 
     *  2515 - assertIsTrue  "a@b.com"                                                                 =   0 =  OK 
     *  2516 - assertIsTrue  "a@bc.com"                                                                =   0 =  OK 
     *  2517 - assertIsTrue  "a@bcom"                                                                  =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2518 - assertIsTrue  "a@domain.com"                                                            =   0 =  OK 
     *  2519 - assertIsTrue  "a__z@provider.com"                                                       =   0 =  OK 
     *  2520 - assertIsTrue  "a_z%@gmail.com"                                                          =   0 =  OK 
     *  2521 - assertIsTrue  "aaron@theinfo.org"                                                       =   0 =  OK 
     *  2522 - assertIsTrue  "ab@288.120.150.10.com"                                                   =   0 =  OK 
     *  2523 - assertIsTrue  "ab@[120.254.254.120]"                                                    =   2 =  OK 
     *  2524 - assertIsTrue  "ab@b-de.cc"                                                              =   0 =  OK 
     *  2525 - assertIsTrue  "ab@c.com"                                                                =   0 =  OK 
     *  2526 - assertIsTrue  "ab_c@bde.cc"                                                             =   0 =  OK 
     *  2527 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                          =   1 =  OK 
     *  2528 - assertIsTrue  "abc.efg@gmail.com"                                                       =   0 =  OK 
     *  2529 - assertIsTrue  "abc.xyz@gmail.com.in"                                                    =   0 =  OK 
     *  2530 - assertIsTrue  "abc123xyz@asdf.co.in"                                                    =   0 =  OK 
     *  2531 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                    =   0 =  OK 
     *  2532 - assertIsTrue  "abc@abc.abc"                                                             =   0 =  OK 
     *  2533 - assertIsTrue  "abc@abc.abcd"                                                            =   0 =  OK 
     *  2534 - assertIsTrue  "abc@abc.abcde"                                                           =   0 =  OK 
     *  2535 - assertIsTrue  "abc@abc.co.in"                                                           =   0 =  OK 
     *  2536 - assertIsTrue  "abc@abc.com.com.com.com"                                                 =   0 =  OK 
     *  2537 - assertIsTrue  "abc@gmail.com.my"                                                        =   0 =  OK 
     *  2538 - assertIsTrue  "abc\@def@example.com"                                                    =   0 =  OK 
     *  2539 - assertIsTrue  "abc\\@example.com"                                                       =   0 =  OK 
     *  2540 - assertIsTrue  "abcxyz123@qwert.com"                                                     =   0 =  OK 
     *  2541 - assertIsTrue  "alex@example.com"                                                        =   0 =  OK 
     *  2542 - assertIsTrue  "alireza@test.co.uk"                                                      =   0 =  OK 
     *  2543 - assertIsTrue  "asd-@asd.com"                                                            =   0 =  OK 
     *  2544 - assertIsTrue  "begeddov@jfinity.com"                                                    =   0 =  OK 
     *  2545 - assertIsTrue  "check@this.com"                                                          =   0 =  OK 
     *  2546 - assertIsTrue  "cog@wheel.com"                                                           =   0 =  OK 
     *  2547 - assertIsTrue  "customer/department=shipping@example.com"                                =   0 =  OK 
     *  2548 - assertIsTrue  "d._.___d@gmail.com"                                                      =   0 =  OK 
     *  2549 - assertIsTrue  "d.j@server1.proseware.com"                                               =   0 =  OK 
     *  2550 - assertIsTrue  "d.oy.smith@gmail.com"                                                    =   0 =  OK 
     *  2551 - assertIsTrue  "d23d@da9.co9"                                                            =   0 =  OK 
     *  2552 - assertIsTrue  "d_oy_smith@gmail.com"                                                    =   0 =  OK 
     *  2553 - assertIsTrue  "dasd-dasd@das.com.das"                                                   =   0 =  OK 
     *  2554 - assertIsTrue  "dasd.dadas@dasd.com"                                                     =   0 =  OK 
     *  2555 - assertIsTrue  "dasd_-@jdas.com"                                                         =   0 =  OK 
     *  2556 - assertIsTrue  "david.jones@proseware.com"                                               =   0 =  OK 
     *  2557 - assertIsTrue  "dclo@us.ibm.com"                                                         =   0 =  OK 
     *  2558 - assertIsTrue  "dda_das@das-dasd.com"                                                    =   0 =  OK 
     *  2559 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                            =   0 =  OK 
     *  2560 - assertIsTrue  "digit-only-domain@123.com"                                               =   0 =  OK 
     *  2561 - assertIsTrue  "doysmith@gmail.com"                                                      =   0 =  OK 
     *  2562 - assertIsTrue  "drp@drp.cz"                                                              =   0 =  OK 
     *  2563 - assertIsTrue  "dsq!a?@das.com"                                                          =   0 =  OK 
     *  2564 - assertIsTrue  "email@domain.co.de"                                                      =   0 =  OK 
     *  2565 - assertIsTrue  "email@domain.com"                                                        =   0 =  OK 
     *  2566 - assertIsTrue  "email@example.co.uk"                                                     =   0 =  OK 
     *  2567 - assertIsTrue  "email@example.com"                                                       =   0 =  OK 
     *  2568 - assertIsTrue  "email@mail.gmail.com"                                                    =   0 =  OK 
     *  2569 - assertIsTrue  "email@subdomain.domain.com"                                              =   0 =  OK 
     *  2570 - assertIsTrue  "example@example.co"                                                      =   0 =  OK 
     *  2571 - assertIsTrue  "f.f.f@bde.cc"                                                            =   0 =  OK 
     *  2572 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                   =   0 =  OK 
     *  2573 - assertIsTrue  "first-name-last-name@d-a-n.com"                                          =   0 =  OK 
     *  2574 - assertIsTrue  "firstname+lastname@domain.com"                                           =   0 =  OK 
     *  2575 - assertIsTrue  "firstname-lastname@domain.com"                                           =   0 =  OK 
     *  2576 - assertIsTrue  "firstname.lastname@domain.com"                                           =   0 =  OK 
     *  2577 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                               =   0 =  OK 
     *  2578 - assertIsTrue  "futureTLD@somewhere.fooo"                                                =   0 =  OK 
     *  2579 - assertIsTrue  "hello.me_1@email.com"                                                    =   0 =  OK 
     *  2580 - assertIsTrue  "hello7___@ca.com.pt"                                                     =   0 =  OK 
     *  2581 - assertIsTrue  "info@ermaelan.com"                                                       =   0 =  OK 
     *  2582 - assertIsTrue  "j.s@server1.proseware.com"                                               =   0 =  OK 
     *  2583 - assertIsTrue  "j@proseware.com9"                                                        =   0 =  OK 
     *  2584 - assertIsTrue  "j_9@[129.126.118.1]"                                                     =   2 =  OK 
     *  2585 - assertIsTrue  "jinujawad6s@gmail.com"                                                   =   0 =  OK 
     *  2586 - assertIsTrue  "john.doe@example.com"                                                    =   0 =  OK 
     *  2587 - assertIsTrue  "john.o'doe@example.com"                                                  =   0 =  OK 
     *  2588 - assertIsTrue  "john.smith@example.com"                                                  =   0 =  OK 
     *  2589 - assertIsTrue  "jones@ms1.proseware.com"                                                 =   0 =  OK 
     *  2590 - assertIsTrue  "journaldev+100@gmail.com"                                                =   0 =  OK 
     *  2591 - assertIsTrue  "journaldev-100@journaldev.net"                                           =   0 =  OK 
     *  2592 - assertIsTrue  "journaldev-100@yahoo-test.com"                                           =   0 =  OK 
     *  2593 - assertIsTrue  "journaldev-100@yahoo.com"                                                =   0 =  OK 
     *  2594 - assertIsTrue  "journaldev.100@journaldev.com.au"                                        =   0 =  OK 
     *  2595 - assertIsTrue  "journaldev.100@yahoo.com"                                                =   0 =  OK 
     *  2596 - assertIsTrue  "journaldev111@journaldev.com"                                            =   0 =  OK 
     *  2597 - assertIsTrue  "journaldev@1.com"                                                        =   0 =  OK 
     *  2598 - assertIsTrue  "journaldev@gmail.com.com"                                                =   0 =  OK 
     *  2599 - assertIsTrue  "journaldev@yahoo.com"                                                    =   0 =  OK 
     *  2600 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                       =   0 =  OK 
     *  2601 - assertIsTrue  "js#internal@proseware.com"                                               =   0 =  OK 
     *  2602 - assertIsTrue  "js*@proseware.com"                                                       =   0 =  OK 
     *  2603 - assertIsTrue  "js@proseware.com9"                                                       =   0 =  OK 
     *  2604 - assertIsTrue  "me+100@me.com"                                                           =   0 =  OK 
     *  2605 - assertIsTrue  "me+alpha@example.com"                                                    =   0 =  OK 
     *  2606 - assertIsTrue  "me-100@me.com"                                                           =   0 =  OK 
     *  2607 - assertIsTrue  "me-100@me.com.au"                                                        =   0 =  OK 
     *  2608 - assertIsTrue  "me-100@yahoo-test.com"                                                   =   0 =  OK 
     *  2609 - assertIsTrue  "me.100@me.com"                                                           =   0 =  OK 
     *  2610 - assertIsTrue  "me@aaronsw.com"                                                          =   0 =  OK 
     *  2611 - assertIsTrue  "me@company.co.uk"                                                        =   0 =  OK 
     *  2612 - assertIsTrue  "me@gmail.com"                                                            =   0 =  OK 
     *  2613 - assertIsTrue  "me@gmail.com"                                                            =   0 =  OK 
     *  2614 - assertIsTrue  "me@mail.s2.example.com"                                                  =   0 =  OK 
     *  2615 - assertIsTrue  "me@me.cu.uk"                                                             =   0 =  OK 
     *  2616 - assertIsTrue  "my.ownsite@ourearth.org"                                                 =   0 =  OK 
     *  2617 - assertIsFalse "myemail@sample"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2618 - assertIsTrue  "myemail@sample.com"                                                      =   0 =  OK 
     *  2619 - assertIsTrue  "mysite@you.me.net"                                                       =   0 =  OK 
     *  2620 - assertIsTrue  "o'hare@example.com"                                                      =   0 =  OK 
     *  2621 - assertIsTrue  "peter.example@domain.comau"                                              =   0 =  OK 
     *  2622 - assertIsTrue  "peter.piper@example.com"                                                 =   0 =  OK 
     *  2623 - assertIsTrue  "peter_123@news.com"                                                      =   0 =  OK 
     *  2624 - assertIsTrue  "pio^_pio@factory.com"                                                    =   0 =  OK 
     *  2625 - assertIsTrue  "pio_#pio@factory.com"                                                    =   0 =  OK 
     *  2626 - assertIsTrue  "pio_pio@factory.com"                                                     =   0 =  OK 
     *  2627 - assertIsTrue  "pio_~pio@factory.com"                                                    =   0 =  OK 
     *  2628 - assertIsTrue  "piskvor@example.lighting"                                                =   0 =  OK 
     *  2629 - assertIsTrue  "rss-dev@yahoogroups.com"                                                 =   0 =  OK 
     *  2630 - assertIsTrue  "someone+tag@somewhere.net"                                               =   0 =  OK 
     *  2631 - assertIsTrue  "someone@somewhere.co.uk"                                                 =   0 =  OK 
     *  2632 - assertIsTrue  "someone@somewhere.com"                                                   =   0 =  OK 
     *  2633 - assertIsTrue  "something_valid@somewhere.tld"                                           =   0 =  OK 
     *  2634 - assertIsTrue  "tvf@tvf.cz"                                                              =   0 =  OK 
     *  2635 - assertIsTrue  "user#@domain.co.in"                                                      =   0 =  OK 
     *  2636 - assertIsTrue  "user'name@domain.co.in"                                                  =   0 =  OK 
     *  2637 - assertIsTrue  "user+mailbox@example.com"                                                =   0 =  OK 
     *  2638 - assertIsTrue  "user-name@domain.co.in"                                                  =   0 =  OK 
     *  2639 - assertIsTrue  "user.name@domain.com"                                                    =   0 =  OK 
     *  2640 - assertIsFalse ".user.name@domain.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2641 - assertIsFalse "user-name@domain.com."                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2642 - assertIsFalse "username@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2643 - assertIsTrue  "user1@domain.com"                                                        =   0 =  OK 
     *  2644 - assertIsTrue  "user?name@domain.co.in"                                                  =   0 =  OK 
     *  2645 - assertIsTrue  "user@domain.co.in"                                                       =   0 =  OK 
     *  2646 - assertIsTrue  "user@domain.com"                                                         =   0 =  OK 
     *  2647 - assertIsFalse "user@domaincom"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2648 - assertIsTrue  "user_name@domain.co.in"                                                  =   0 =  OK 
     *  2649 - assertIsTrue  "user_name@domain.com"                                                    =   0 =  OK 
     *  2650 - assertIsTrue  "username@yahoo.corporate"                                                =   0 =  OK 
     *  2651 - assertIsTrue  "username@yahoo.corporate.in"                                             =   0 =  OK 
     *  2652 - assertIsTrue  "username+something@domain.com"                                           =   0 =  OK 
     *  2653 - assertIsTrue  "vdv@dyomedea.com"                                                        =   0 =  OK 
     *  2654 - assertIsTrue  "xxxx@gmail.com"                                                          =   0 =  OK 
     *  2655 - assertIsTrue  "xxxxxx@yahoo.com"                                                        =   0 =  OK 
     *  2656 - assertIsTrue  "user+mailbox/department=shipping@example.com"                            =   0 =  OK 
     *  2657 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                                 =   4 =  OK 
     *  2658 - assertIsFalse "user@localserver"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2659 - assertIsTrue  "w.b.f@test.com"                                                          =   0 =  OK 
     *  2660 - assertIsTrue  "w.b.f@test.museum"                                                       =   0 =  OK 
     *  2661 - assertIsTrue  "yoursite@ourearth.com"                                                   =   0 =  OK 
     *  2662 - assertIsTrue  "~pio_pio@factory.com"                                                    =   0 =  OK 
     *  2663 - assertIsTrue  "\"test123\"@gmail.com"                                                   =   1 =  OK 
     *  2664 - assertIsTrue  "test123@gmail.comcomco"                                                  =   0 =  OK 
     *  2665 - assertIsTrue  "test123@gmail.c"                                                         =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2666 - assertIsTrue  "test1&23@gmail.com"                                                      =   0 =  OK 
     *  2667 - assertIsTrue  "test123@gmail.com"                                                       =   0 =  OK 
     *  2668 - assertIsFalse "test123@gmail..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2669 - assertIsFalse ".test123@gmail.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2670 - assertIsFalse "test123@gmail.com."                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2671 - assertIsFalse "test1Ὠ23@gmail.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2672 - assertIsTrue  "javaTpoint@domain.co.in"                                                 =   0 =  OK 
     *  2673 - assertIsTrue  "javaTpoint@domain.com"                                                   =   0 =  OK 
     *  2674 - assertIsTrue  "javaTpoint.name@domain.com"                                              =   0 =  OK 
     *  2675 - assertIsTrue  "javaTpoint#@domain.co.in"                                                =   0 =  OK 
     *  2676 - assertIsTrue  "javaTpoint@domain.com"                                                   =   0 =  OK 
     *  2677 - assertIsFalse "javaTpoint@domaincom"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2678 - assertIsTrue  "javaTpoint@domain.co.in"                                                 =   0 =  OK 
     *  2679 - assertIsTrue  "12453@domain.com"                                                        =   0 =  OK 
     *  2680 - assertIsTrue  "javaTpoint.name@domain.com"                                              =   0 =  OK 
     *  2681 - assertIsTrue  "1avaTpoint#@domain.co.in"                                                =   0 =  OK 
     *  2682 - assertIsTrue  "javaTpoint@domain.co.in"                                                 =   0 =  OK 
     *  2683 - assertIsTrue  "javaTpoint@domain.com"                                                   =   0 =  OK 
     *  2684 - assertIsTrue  "javaTpoint.name@domain.com"                                              =   0 =  OK 
     *  2685 - assertIsTrue  "javaTpoint#@domain.co.in"                                                =   0 =  OK 
     *  2686 - assertIsTrue  "java'Tpoint@domain.com"                                                  =   0 =  OK 
     *  2687 - assertIsFalse ".javaTpoint@yahoo.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2688 - assertIsFalse "javaTpoint@domain.com."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2689 - assertIsFalse "javaTpoint#domain.com"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2690 - assertIsFalse "javaTpoint@domain..com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2691 - assertIsFalse "@yahoo.com"                                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2692 - assertIsFalse "javaTpoint#domain.com"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2693 - assertIsFalse "12javaTpoint#domain.com"                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2694 - assertIsTrue  "admin@java2blog.com"                                                     =   0 =  OK 
     *  2695 - assertIsFalse "@java2blog.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2696 - assertIsTrue  "arpit.mandliya@java2blog.com"                                            =   0 =  OK 
     *  2697 - assertIsTrue  "sairamkrishna@tutorialspoint.com"                                        =   0 =  OK 
     *  2698 - assertIsTrue  "kittuprasad700@gmail.com"                                                =   0 =  OK 
     *  2699 - assertIsFalse "sairamkrishna_mammahe%google-india.com"                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2700 - assertIsTrue  "sairam.krishna@gmail-indai.com"                                          =   0 =  OK 
     *  2701 - assertIsTrue  "sai#@youtube.co.in"                                                      =   0 =  OK 
     *  2702 - assertIsFalse "kittu@domaincom"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2703 - assertIsFalse "kittu#gmail.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2704 - assertIsFalse "@pindom.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2705 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                =   0 =  OK 
     *  2706 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2707 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2708 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  2709 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  2710 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2711 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"          =   0 =  OK 
     *  2712 - assertIsTrue  "valid@[1.1.1.1]"                                                         =   2 =  OK 
     *  2713 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2714 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                                  =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2715 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                         =   4 =  OK 
     *  2716 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"          =   4 =  OK 
     *  2717 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                               =   4 =  OK 
     *  2718 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"                  =   4 =  OK 
     *  2719 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                    =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2720 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                             =   4 =  OK 
     *  2721 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                             =   4 =  OK 
     *  2722 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                       =   2 =  OK 
     *  2723 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                           =   2 =  OK 
     *  2724 - assertIsFalse "invalid@[10]"                                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2725 - assertIsFalse "invalid@[10.1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2726 - assertIsFalse "invalid@[10.1.52]"                                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2727 - assertIsFalse "invalid@[256.256.256.256]"                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2728 - assertIsFalse "invalid@[IPv6:123456]"                                                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2729 - assertIsFalse "invalid@[127.0.0.1.]"                                                    =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2730 - assertIsFalse "invalid@[127.0.0.1]."                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2731 - assertIsFalse "invalid@[127.0.0.1]x"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2732 - assertIsFalse "invalid@domain1.com@domain2.com"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2733 - assertIsFalse "\"locál-part\"@example.com"                                              =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2734 - assertIsFalse "invalid@[IPv6:1::2:]"                                                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2735 - assertIsFalse "invalid@[IPv6::1::1]"                                                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2736 - assertIsFalse "invalid@[]"                                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2737 - assertIsFalse "invalid@[111.111.111.111"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2738 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2739 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2740 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2741 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"            =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2742 - assertIsFalse "invalid@[IPv6:1111:1111]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2743 - assertIsFalse "\"invalid-qstring@example.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     *  2744 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2745 - assertIsTrue  "\"back\slash\"@sld.com"                                                  =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2746 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                             =   1 =  OK 
     *  2747 - assertIsTrue  "\"quoted\"@sld.com"                                                      =   1 =  OK 
     *  2748 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                      =   1 =  OK 
     *  2749 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                      =   0 =  OK 
     *  2750 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                     =   0 =  OK 
     *  2751 - assertIsTrue  "01234567890@numbers-in-local.net"                                        =   0 =  OK 
     *  2752 - assertIsTrue  "a@single-character-in-local.org"                                         =   0 =  OK 
     *  2753 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  2754 - assertIsTrue  "backticksarelegit@test.com"                                              =   0 =  OK 
     *  2755 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                              =   2 =  OK 
     *  2756 - assertIsTrue  "country-code-tld@sld.rw"                                                 =   0 =  OK 
     *  2757 - assertIsTrue  "country-code-tld@sld.uk"                                                 =   0 =  OK 
     *  2758 - assertIsTrue  "letters-in-sld@123.com"                                                  =   0 =  OK 
     *  2759 - assertIsTrue  "local@dash-in-sld.com"                                                   =   0 =  OK 
     *  2760 - assertIsTrue  "local@sld.newTLD"                                                        =   0 =  OK 
     *  2761 - assertIsTrue  "local@sub.domains.com"                                                   =   0 =  OK 
     *  2762 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                        =   0 =  OK 
     *  2763 - assertIsTrue  "one-character-third-level@a.example.com"                                 =   0 =  OK 
     *  2764 - assertIsTrue  "one-letter-sld@x.org"                                                    =   0 =  OK 
     *  2765 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                                =   0 =  OK 
     *  2766 - assertIsTrue  "single-character-in-sld@x.org"                                           =   0 =  OK 
     *  2767 - assertIsTrue  "uncommon-tld@sld.mobi"                                                   =   0 =  OK 
     *  2768 - assertIsTrue  "uncommon-tld@sld.museum"                                                 =   0 =  OK 
     *  2769 - assertIsTrue  "uncommon-tld@sld.travel"                                                 =   0 =  OK 
     *  2770 - assertIsFalse "invalid"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2771 - assertIsFalse "invalid@"                                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2772 - assertIsFalse "invalid @"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2773 - assertIsFalse "invalid@[555.666.777.888]"                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2774 - assertIsFalse "invalid@[IPv6:123456]"                                                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2775 - assertIsFalse "invalid@[127.0.0.1.]"                                                    =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2776 - assertIsFalse "invalid@[127.0.0.1]."                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2777 - assertIsFalse "invalid@[127.0.0.1]x"                                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2778 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2779 - assertIsFalse "@missing-local.org"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2780 - assertIsFalse "IP-and-port@127.0.0.1:25"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2781 - assertIsFalse "another-invalid-ip@127.0.0.256"                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2782 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2783 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2784 - assertIsFalse "invalid-ip@127.0.0.1.26"                                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2785 - assertIsFalse "local-ends-with-dot.@sld.com"                                            =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2786 - assertIsFalse "missing-at-sign.net"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2787 - assertIsFalse "missing-sld@.com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2788 - assertIsFalse "missing-tld@sld."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2789 - assertIsFalse "sld-ends-with-dash@sld-.com"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2790 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2791 - assertIsFalse "two..consecutive-dots@sld.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2792 - assertIsTrue  "unbracketed-IP@127.0.0.1"                                                =   2 =  OK 
     *  2793 - assertIsFalse "underscore.error@example.com_"                                           =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     *  2794 - assertIsTrue  "first.last@iana.org"                                                     =   0 =  OK 
     *  2795 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  2796 - assertIsTrue  "\"first\\"last\"@iana.org"                                               =   1 =  OK 
     *  2797 - assertIsTrue  "\"first@last\"@iana.org"                                                 =   1 =  OK 
     *  2798 - assertIsTrue  "\"first\\last\"@iana.org"                                                =   1 =  OK 
     *  2799 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  2800 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  2801 - assertIsTrue  "first.last@[12.34.56.78]"                                                =   2 =  OK 
     *  2802 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                       =   4 =  OK 
     *  2803 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                        =   4 =  OK 
     *  2804 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                       =   4 =  OK 
     *  2805 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                       =   4 =  OK 
     *  2806 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"               =   4 =  OK 
     *  2807 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  2808 - assertIsTrue  "first.last@3com.com"                                                     =   0 =  OK 
     *  2809 - assertIsTrue  "first.last@123.iana.org"                                                 =   0 =  OK 
     *  2810 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2811 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                   =   4 =  OK 
     *  2812 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                   =   1 =  OK 
     *  2813 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                               =   1 =  OK 
     *  2814 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                                 =   1 =  OK 
     *  2815 - assertIsTrue  "\"Abc@def\"@iana.org"                                                    =   1 =  OK 
     *  2816 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                              =   1 =  OK 
     *  2817 - assertIsTrue  "user+mailbox@iana.org"                                                   =   0 =  OK 
     *  2818 - assertIsTrue  "$A12345@iana.org"                                                        =   0 =  OK 
     *  2819 - assertIsTrue  "!def!xyz%abc@iana.org"                                                   =   0 =  OK 
     *  2820 - assertIsTrue  "_somename@iana.org"                                                      =   0 =  OK 
     *  2821 - assertIsTrue  "dclo@us.ibm.com"                                                         =   0 =  OK 
     *  2822 - assertIsTrue  "peter.piper@iana.org"                                                    =   0 =  OK 
     *  2823 - assertIsTrue  "test@iana.org"                                                           =   0 =  OK 
     *  2824 - assertIsTrue  "TEST@iana.org"                                                           =   0 =  OK 
     *  2825 - assertIsTrue  "1234567890@iana.org"                                                     =   0 =  OK 
     *  2826 - assertIsTrue  "test+test@iana.org"                                                      =   0 =  OK 
     *  2827 - assertIsTrue  "test-test@iana.org"                                                      =   0 =  OK 
     *  2828 - assertIsTrue  "t*est@iana.org"                                                          =   0 =  OK 
     *  2829 - assertIsTrue  "+1~1+@iana.org"                                                          =   0 =  OK 
     *  2830 - assertIsTrue  "{_test_}@iana.org"                                                       =   0 =  OK 
     *  2831 - assertIsTrue  "test.test@iana.org"                                                      =   0 =  OK 
     *  2832 - assertIsTrue  "\"test.test\"@iana.org"                                                  =   1 =  OK 
     *  2833 - assertIsTrue  "test.\"test\"@iana.org"                                                  =   1 =  OK 
     *  2834 - assertIsTrue  "\"test@test\"@iana.org"                                                  =   1 =  OK 
     *  2835 - assertIsTrue  "test@123.123.123.x123"                                                   =   0 =  OK 
     *  2836 - assertIsTrue  "test@123.123.123.123"                                                    =   2 =  OK 
     *  2837 - assertIsTrue  "test@[123.123.123.123]"                                                  =   2 =  OK 
     *  2838 - assertIsTrue  "test@example.iana.org"                                                   =   0 =  OK 
     *  2839 - assertIsTrue  "test@example.example.iana.org"                                           =   0 =  OK 
     *  2840 - assertIsTrue  "customer/department@iana.org"                                            =   0 =  OK 
     *  2841 - assertIsTrue  "_Yosemite.Sam@iana.org"                                                  =   0 =  OK 
     *  2842 - assertIsTrue  "~@iana.org"                                                              =   0 =  OK 
     *  2843 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                              =   1 =  OK 
     *  2844 - assertIsTrue  "Ima.Fool@iana.org"                                                       =   0 =  OK 
     *  2845 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                   =   1 =  OK 
     *  2846 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                                 =   1 =  OK 
     *  2847 - assertIsTrue  "\"first\".\"last\"@iana.org"                                             =   1 =  OK 
     *  2848 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                      =   1 =  OK 
     *  2849 - assertIsTrue  "\"first\".last@iana.org"                                                 =   1 =  OK 
     *  2850 - assertIsTrue  "first.\"last\"@iana.org"                                                 =   1 =  OK 
     *  2851 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                                  =   1 =  OK 
     *  2852 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                      =   1 =  OK 
     *  2853 - assertIsTrue  "\"first.middle.last\"@iana.org"                                          =   1 =  OK 
     *  2854 - assertIsTrue  "\"first..last\"@iana.org"                                                =   1 =  OK 
     *  2855 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                      =   1 =  OK 
     *  2856 - assertIsFalse "first.last @iana.orgin"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2857 - assertIsTrue  "\"test blah\"@iana.orgin"                                                =   1 =  OK 
     *  2858 - assertIsTrue  "name.lastname@domain.com"                                                =   0 =  OK 
     *  2859 - assertIsTrue  "a@bar.com"                                                               =   0 =  OK 
     *  2860 - assertIsTrue  "aaa@[123.123.123.123]"                                                   =   2 =  OK 
     *  2861 - assertIsTrue  "a-b@bar.com"                                                             =   0 =  OK 
     *  2862 - assertIsFalse "+@b.c"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2863 - assertIsTrue  "+@b.com"                                                                 =   0 =  OK 
     *  2864 - assertIsTrue  "a@b.co-foo.uk"                                                           =   0 =  OK 
     *  2865 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                      =   1 =  OK 
     *  2866 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                      =   1 =  OK 
     *  2867 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                        =   0 =  OK 
     *  2868 - assertIsTrue  "foobar@192.168.0.1"                                                      =   2 =  OK 
     *  2869 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                       =   6 =  OK 
     *  2870 - assertIsTrue  "user%uucp!path@berkeley.edu"                                             =   0 =  OK 
     *  2871 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                               =   0 =  OK 
     *  2872 - assertIsTrue  "test@test.com"                                                           =   0 =  OK 
     *  2873 - assertIsTrue  "test@xn--example.com"                                                    =   0 =  OK 
     *  2874 - assertIsTrue  "test@example.com"                                                        =   0 =  OK 
     *  2875 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                =   0 =  OK 
     *  2876 - assertIsTrue  "first\@last@iana.org"                                                    =   0 =  OK 
     *  2877 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                                =   0 =  OK 
     *  2878 - assertIsFalse "first.last@example.123"                                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2879 - assertIsFalse "first.last@comin"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2880 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                               =   1 =  OK 
     *  2881 - assertIsTrue  "Abc\@def@iana.org"                                                       =   0 =  OK 
     *  2882 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                   =   0 =  OK 
     *  2883 - assertIsFalse "Joe.\Blow@iana.org"                                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2884 - assertIsTrue  "first.last@sub.do.com"                                                   =   0 =  OK 
     *  2885 - assertIsFalse "first.last"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2886 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                         =   0 =  OK 
     *  2887 - assertIsTrue  "\"hello world\"@example.com"                                             =   1 =  OK 
     *  2888 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2889 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                            =   1 =  OK 
     *  2890 - assertIsTrue  "example+tag@gmail.com"                                                   =   0 =  OK 
     *  2891 - assertIsFalse ".ann..other.@example.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2892 - assertIsTrue  "ann.other@example.com"                                                   =   0 =  OK 
     *  2893 - assertIsTrue  "something@something.something"                                           =   0 =  OK 
     *  2894 - assertIsTrue  "c@(Chris's host.)public.examplein"                                       =   6 =  OK 
     *  2895 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2896 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2897 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2898 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                              =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2899 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                              =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2900 - assertIsFalse "first().last@iana.orgin"                                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2901 - assertIsFalse "pete(his account)@silly.test(his host)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2902 - assertIsFalse "jdoe@machine(comment). examplein"                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2903 - assertIsFalse "first(abc.def).last@iana.orgin"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2904 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2905 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2906 - assertIsFalse "first(abc\(def)@iana.orgin"                                              =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2907 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2908 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2909 - assertIsFalse "1234 @ local(blah) .machine .examplein"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2910 - assertIsFalse "a@bin"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2911 - assertIsFalse "a@barin"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2912 - assertIsFalse "@about.museum"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2913 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2914 - assertIsFalse ".first.last@iana.org"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2915 - assertIsFalse "first.last.@iana.org"                                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2916 - assertIsFalse "first..last@iana.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2917 - assertIsFalse "\"first\"last\"@iana.org"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2918 - assertIsFalse "first.last@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2919 - assertIsFalse "first.last@-xample.com"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2920 - assertIsFalse "first.last@exampl-.com"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2921 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2922 - assertIsFalse "abc\@iana.org"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2923 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2924 - assertIsFalse "abc@def@iana.org"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2925 - assertIsFalse "@iana.org"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2926 - assertIsFalse "doug@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2927 - assertIsFalse "\"qu@iana.org"                                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2928 - assertIsFalse "ote\"@iana.org"                                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2929 - assertIsFalse ".dot@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2930 - assertIsFalse "dot.@iana.org"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2931 - assertIsFalse "two..dot@iana.org"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2932 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2933 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2934 - assertIsFalse "hello world@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2935 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2936 - assertIsFalse "test.iana.org"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2937 - assertIsFalse "test.@iana.org"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2938 - assertIsFalse "test..test@iana.org"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2939 - assertIsFalse ".test@iana.org"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2940 - assertIsFalse "test@test@iana.org"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2941 - assertIsFalse "test@@iana.org"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2942 - assertIsFalse "-- test --@iana.org"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2943 - assertIsFalse "[test]@iana.org"                                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2944 - assertIsFalse "\"test\"test\"@iana.org"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2945 - assertIsFalse "()[]\;:.><@iana.org"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2946 - assertIsFalse "test@."                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2947 - assertIsFalse "test@example."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2948 - assertIsFalse "test@.org"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2949 - assertIsFalse "test@[123.123.123.123"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2950 - assertIsFalse "test@123.123.123.123]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2951 - assertIsFalse "NotAnEmail"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2952 - assertIsFalse "@NotAnEmail"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2953 - assertIsFalse "\"test\"blah\"@iana.org"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2954 - assertIsFalse ".wooly@iana.org"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2955 - assertIsFalse "wo..oly@iana.org"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2956 - assertIsFalse "pootietang.@iana.org"                                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2957 - assertIsFalse ".@iana.org"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2958 - assertIsFalse "Ima Fool@iana.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2959 - assertIsFalse "foo@[\1.2.3.4]"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2960 - assertIsFalse "first.\"\".last@iana.org"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2961 - assertIsFalse "first\last@iana.org"                                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2962 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"            =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2963 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2964 - assertIsFalse "cal(foo(bar)@iamcal.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2965 - assertIsFalse "cal(foo)bar)@iamcal.com"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2966 - assertIsFalse "cal(foo\)@iamcal.com"                                                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2967 - assertIsFalse "first(middle)last@iana.org"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2968 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2969 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2970 - assertIsFalse ".@"                                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2971 - assertIsFalse "@bar.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2972 - assertIsFalse "@@bar.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2973 - assertIsFalse "aaa.com"                                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2974 - assertIsFalse "aaa@.com"                                                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2975 - assertIsFalse "aaa@.123"                                                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2976 - assertIsFalse "aaa@[123.123.123.123]a"                                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2977 - assertIsFalse "aaa@[123.123.123.333]"                                                   =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2978 - assertIsFalse "a@bar.com."                                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2979 - assertIsFalse "a@-b.com"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2980 - assertIsFalse "a@b-.com"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2981 - assertIsFalse "-@..com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2982 - assertIsFalse "-@a..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2983 - assertIsFalse "@about.museum-"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2984 - assertIsFalse "test@...........com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2985 - assertIsFalse "first.last@[IPv6::]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2986 - assertIsFalse "first.last@[IPv6::::]"                                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2987 - assertIsFalse "first.last@[IPv6::b4]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2988 - assertIsFalse "first.last@[IPv6::::b4]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2989 - assertIsFalse "first.last@[IPv6::b3:b4]"                                                =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2990 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2991 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2992 - assertIsFalse "first.last@[IPv6:a1:]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2993 - assertIsFalse "first.last@[IPv6:a1:::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2994 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                                =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2995 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2996 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2997 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2998 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                        =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2999 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3000 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3001 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3002 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                     =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3003 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                             =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3004 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                          =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3005 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                    =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3006 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                     =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  3007 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3008 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                               =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3009 - assertIsFalse "first.last@[IPv6::a2::b4]"                                               =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3010 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3011 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3012 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                              =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3013 - assertIsFalse "first.last@[.12.34.56.78]"                                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  3014 - assertIsFalse "first.last@[12.34.56.789]"                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3015 - assertIsFalse "first.last@[::12.34.56.78]"                                              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3016 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                         =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3017 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  3018 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3019 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"        =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3020 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"          =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3021 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3022 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3023 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                            =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  3024 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3025 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3026 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                      =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3027 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3028 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                                =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3029 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                                =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  3030 - assertIsTrue  "first.last@[IPv6:::]"                                                    =   4 =  OK 
     *  3031 - assertIsTrue  "first.last@[IPv6:::b4]"                                                  =   4 =  OK 
     *  3032 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                               =   4 =  OK 
     *  3033 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                                =   4 =  OK 
     *  3034 - assertIsTrue  "first.last@[IPv6:a1::]"                                                  =   4 =  OK 
     *  3035 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                               =   4 =  OK 
     *  3036 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                                 =   4 =  OK 
     *  3037 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                                 =   4 =  OK 
     *  3038 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3039 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3040 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3041 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3042 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                       =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3043 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3044 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                      =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3045 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                      =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3046 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3047 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                    =   4 =  OK 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     *  3048 - assertIsFalse "\"qu@test.org"                                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  3049 - assertIsFalse "ote\"@test.org"                                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3050 - assertIsFalse "\"().:;<>[\]@example.com"                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3051 - assertIsFalse "\"\"\"@iana.org"                                                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3052 - assertIsFalse "Abc.example.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3053 - assertIsFalse "A@b@c@example.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3054 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3055 - assertIsFalse "this is\"not\allowed@example.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3056 - assertIsFalse "this\ still\"not\allowed@example.com"                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3057 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  3058 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3059 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3060 - assertIsFalse "plainaddress"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3061 - assertIsFalse "@example.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3062 - assertIsFalse ".email@example.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3063 - assertIsFalse "email.@example.com"                                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3064 - assertIsFalse "email..email@example.com"                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3065 - assertIsFalse "email@-example.com"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3066 - assertIsFalse "email@111.222.333.44444"                                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  3067 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3068 - assertIsFalse "email@[12.34.44.56"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  3069 - assertIsFalse "email@14.44.56.34]"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3070 - assertIsFalse "email@[1.1.23.5f]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3071 - assertIsFalse "email@[3.256.255.23]"                                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  3072 - assertIsTrue  "\"first\\"last\"@test.org"                                               =   1 =  OK 
     *  3073 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3074 - assertIsFalse "first\@last@iana.org"                                                    =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  3075 - assertIsFalse "test@example.com "                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3076 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  3077 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  3078 - assertIsFalse "invalid@about.museum-"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3079 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3080 - assertIsFalse "abc@def@test.org"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3081 - assertIsTrue  "abc\@def@test.org"                                                       =   0 =  OK 
     *  3082 - assertIsFalse "abc\@test.org"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3083 - assertIsFalse "@test.org"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3084 - assertIsFalse ".dot@test.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3085 - assertIsFalse "dot.@test.org"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3086 - assertIsFalse "two..dot@test.org"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3087 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3088 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3089 - assertIsFalse "hello world@test.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3090 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3091 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3092 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3093 - assertIsFalse "test.test.org"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3094 - assertIsFalse "test.@test.org"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3095 - assertIsFalse "test..test@test.org"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3096 - assertIsFalse ".test@test.org"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3097 - assertIsFalse "test@test@test.org"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3098 - assertIsFalse "test@@test.org"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3099 - assertIsFalse "-- test --@test.org"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3100 - assertIsFalse "[test]@test.org"                                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3101 - assertIsFalse "\"test\"test\"@test.org"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3102 - assertIsFalse "()[]\;:.><@test.org"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3103 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3104 - assertIsFalse ".@test.org"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3105 - assertIsFalse "Ima Fool@test.org"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3106 - assertIsTrue  "\"first\\"last\"@test.org"                                               =   1 =  OK 
     *  3107 - assertIsFalse "foo@[.2.3.4]"                                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3108 - assertIsFalse "first\last@test.org"                                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3109 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3110 - assertIsFalse "first(middle)last@test.org"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3111 - assertIsFalse "\"test\"test@test.com"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3112 - assertIsFalse "()@test.com"                                                             =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  3113 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  3114 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3115 - assertIsFalse "invalid@[1]"                                                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3116 - assertIsFalse "ä📧@-foo"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3117 - assertIsFalse "ä📧@foo-"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3118 - assertIsFalse "first(comment(inner@comment.com"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3119 - assertIsFalse "Joe A Smith <email@example.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3120 - assertIsFalse "Joe A Smith email@example.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3121 - assertIsFalse "Joe A Smith <email@example.com->"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3122 - assertIsFalse "Joe A Smith <email@-example.com->"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3123 - assertIsFalse "Joe A Smith <email>"                                                     =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3124 - assertIsTrue  "\"email\"@example.com"                                                   =   1 =  OK 
     *  3125 - assertIsTrue  "\"first@last\"@test.org"                                                 =   1 =  OK 
     *  3126 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                              =   1 =  OK 
     *  3127 - assertIsTrue  "\"first\"last\"@test.org"                                                =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3128 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                      =   1 =  OK 
     *  3129 - assertIsTrue  "\"first\last\"@test.org"                                                 =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3130 - assertIsTrue  "\"Abc\@def\"@test.org"                                                   =   1 =  OK 
     *  3131 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                               =   1 =  OK 
     *  3132 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                                  =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3133 - assertIsTrue  "\"Abc@def\"@test.org"                                                    =   1 =  OK 
     *  3134 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                                =   1 =  OK 
     *  3135 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                                            =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3136 - assertIsTrue  "\"[[ test ]]\"@test.org"                                                 =   1 =  OK 
     *  3137 - assertIsTrue  "\"test.test\"@test.org"                                                  =   1 =  OK 
     *  3138 - assertIsTrue  "test.\"test\"@test.org"                                                  =   1 =  OK 
     *  3139 - assertIsTrue  "\"test@test\"@test.org"                                                  =   1 =  OK 
     *  3140 - assertIsTrue  "\"test  est\"@test.org"                                                   =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3141 - assertIsTrue  "\"first\".\"last\"@test.org"                                             =   1 =  OK 
     *  3142 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                      =   1 =  OK 
     *  3143 - assertIsTrue  "\"first\".last@test.org"                                                 =   1 =  OK 
     *  3144 - assertIsTrue  "first.\"last\"@test.org"                                                 =   1 =  OK 
     *  3145 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                                  =   1 =  OK 
     *  3146 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                      =   1 =  OK 
     *  3147 - assertIsTrue  "\"first.middle.last\"@test.org"                                          =   1 =  OK 
     *  3148 - assertIsTrue  "\"first..last\"@test.org"                                                =   1 =  OK 
     *  3149 - assertIsTrue  "\"Unicode NULL \"@char.com"                                              =   1 =  OK 
     *  3150 - assertIsTrue  "\"test\blah\"@test.org"                                                  =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3151 - assertIsTrue  "\"testlah\"@test.org"                                                   =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3152 - assertIsTrue  "\"test\"blah\"@test.org"                                                 =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3153 - assertIsTrue  "\"first\\"last\"@test.org"                                               =   1 =  OK 
     *  3154 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                        =   1 =  OK 
     *  3155 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3156 - assertIsTrue  "\"test blah\"@test.org"                                                  =   1 =  OK 
     *  3157 - assertIsTrue  "first.last@test.org"                                                     =   0 =  OK 
     *  3158 - assertIsFalse "jdoe@machine(comment).example"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3159 - assertIsTrue  "first.\"\".last@test.org"                                                =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  3160 - assertIsTrue  "\"\"@test.org"                                                           =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  3161 - assertIsTrue  "very.common@example.org"                                                 =   0 =  OK 
     *  3162 - assertIsTrue  "test/test@test.com"                                                      =   0 =  OK 
     *  3163 - assertIsTrue  "user-@example.org"                                                       =   0 =  OK 
     *  3164 - assertIsTrue  "firstname.lastname@example.com"                                          =   0 =  OK 
     *  3165 - assertIsTrue  "email@subdomain.example.com"                                             =   0 =  OK 
     *  3166 - assertIsTrue  "firstname+lastname@example.com"                                          =   0 =  OK 
     *  3167 - assertIsTrue  "1234567890@example.com"                                                  =   0 =  OK 
     *  3168 - assertIsTrue  "email@example-one.com"                                                   =   0 =  OK 
     *  3169 - assertIsTrue  "_______@example.com"                                                     =   0 =  OK 
     *  3170 - assertIsTrue  "email@example.name"                                                      =   0 =  OK 
     *  3171 - assertIsTrue  "email@example.museum"                                                    =   0 =  OK 
     *  3172 - assertIsTrue  "email@example.co.jp"                                                     =   0 =  OK 
     *  3173 - assertIsTrue  "firstname-lastname@example.com"                                          =   0 =  OK 
     *  3174 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  3175 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3176 - assertIsTrue  "first.last@123.test.org"                                                 =   0 =  OK 
     *  3177 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3178 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  3179 - assertIsTrue  "user+mailbox@test.org"                                                   =   0 =  OK 
     *  3180 - assertIsTrue  "customer/department=shipping@test.org"                                   =   0 =  OK 
     *  3181 - assertIsTrue  "$A12345@test.org"                                                        =   0 =  OK 
     *  3182 - assertIsTrue  "!def!xyz%abc@test.org"                                                   =   0 =  OK 
     *  3183 - assertIsTrue  "_somename@test.org"                                                      =   0 =  OK 
     *  3184 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                         =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3185 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                      =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3186 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3187 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3188 - assertIsTrue  "+@b.c"                                                                   =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3189 - assertIsTrue  "TEST@test.org"                                                           =   0 =  OK 
     *  3190 - assertIsTrue  "1234567890@test.org"                                                     =   0 =  OK 
     *  3191 - assertIsTrue  "test-test@test.org"                                                      =   0 =  OK 
     *  3192 - assertIsTrue  "t*est@test.org"                                                          =   0 =  OK 
     *  3193 - assertIsTrue  "+1~1+@test.org"                                                          =   0 =  OK 
     *  3194 - assertIsTrue  "{_test_}@test.org"                                                       =   0 =  OK 
     *  3195 - assertIsTrue  "valid@about.museum"                                                      =   0 =  OK 
     *  3196 - assertIsTrue  "a@bar"                                                                   =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3197 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                                =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3198 - assertIsTrue  "(comment)test@test.org"                                                  =   6 =  OK 
     *  3199 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3200 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                                =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3201 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                                          =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3202 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"      =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3203 - assertIsFalse "pete(his account)@silly.test(his host)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3204 - assertIsTrue  "first(abc\(def)@test.org"                                                =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3205 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                        =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3206 - assertIsTrue  "c@(Chris's host.)public.example"                                         =   6 =  OK 
     *  3207 - assertIsTrue  "_Yosemite.Sam@test.org"                                                  =   0 =  OK 
     *  3208 - assertIsTrue  "~@test.org"                                                              =   0 =  OK 
     *  3209 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                           =   6 =  OK 
     *  3210 - assertIsTrue  "test@Bücher.ch"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3211 - assertIsTrue  "あいうえお@example.com"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3212 - assertIsTrue  "Pelé@example.com"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3213 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3214 - assertIsTrue  "我買@屋企.香港"                                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3215 - assertIsTrue  "二ノ宮@黒川.日本"                                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3216 - assertIsTrue  "медведь@с-балалайкой.рф"                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3217 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3218 - assertIsTrue  "email@example.com (Joe Smith)"                                           =   6 =  OK 
     *  3219 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                                = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  3220 - assertIsTrue  "first(abc.def).last@test.org"                                            = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  3221 - assertIsTrue  "first(a\"bc.def).last@test.org"                                          =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3222 - assertIsTrue  "first.(\")middle.last(\")@test.org"                                      = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  3223 - assertIsTrue  "first().last@test.org"                                                   = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  3224 - assertIsTrue  "mymail\@hello@hotmail.com"                                               =   0 =  OK 
     *  3225 - assertIsTrue  "Abc\@def@test.org"                                                       =   0 =  OK 
     *  3226 - assertIsTrue  "Fred\ Bloggs@test.org"                                                   =   0 =  OK 
     *  3227 - assertIsTrue  "Joe.\\Blow@test.org"                                                     =   0 =  OK 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ----------------------------------------------------------------------------------------------------
     * 
     *  3228 - assertIsTrue  "me@example.com"                                                          =   0 =  OK 
     *  3229 - assertIsTrue  "a.nonymous@example.com"                                                  =   0 =  OK 
     *  3230 - assertIsTrue  "name+tag@example.com"                                                    =   0 =  OK 
     *  3231 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                          =   2 =  OK 
     *  3232 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"       =   4 =  OK 
     *  3233 - assertIsTrue  "me(this is a comment)@example.com"                                       =   6 =  OK 
     *  3234 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                               =   1 =  OK 
     *  3235 - assertIsTrue  "me.example@com"                                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3236 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                         =   0 =  OK 
     *  3237 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"     =   0 =  OK 
     *  3238 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                        =   0 =  OK 
     *  3239 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                       =   0 =  OK 
     *  3240 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                      =   0 =  OK 
     *  3241 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                     =   0 =  OK 
     *  3242 - assertIsFalse "NotAnEmail"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3243 - assertIsFalse "me@"                                                                     =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3244 - assertIsFalse "@example.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3245 - assertIsFalse ".me@example.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3246 - assertIsFalse "me@example..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3247 - assertIsFalse "me\@example.com"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3248 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3249 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3250 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3251 - assertIsFalse "semico...@gmail.com"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- my old tests ----------------------------------------------------------------------------------------------------
     * 
     *  3252 - assertIsTrue  "A@B.CD"                                                                  =   0 =  OK 
     *  3253 - assertIsTrue  "A.\"B\"@C.DE"                                                            =   1 =  OK 
     *  3254 - assertIsTrue  "A.B@[1.2.3.4]"                                                           =   2 =  OK 
     *  3255 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                       =   3 =  OK 
     *  3256 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                              =   4 =  OK 
     *  3257 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                          =   5 =  OK 
     *  3258 - assertIsTrue  "(A)B@C.DE"                                                               =   6 =  OK 
     *  3259 - assertIsTrue  "A(B)@C.DE"                                                               =   6 =  OK 
     *  3260 - assertIsTrue  "(A)\"B\"@C.DE"                                                           =   7 =  OK 
     *  3261 - assertIsTrue  "\"A\"(B)@C.DE"                                                           =   7 =  OK 
     *  3262 - assertIsTrue  "(A)B@[1.2.3.4]"                                                          =   2 =  OK 
     *  3263 - assertIsTrue  "A(B)@[1.2.3.4]"                                                          =   2 =  OK 
     *  3264 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                      =   8 =  OK 
     *  3265 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                      =   8 =  OK 
     *  3266 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                             =   4 =  OK 
     *  3267 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                             =   4 =  OK 
     *  3268 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                         =   9 =  OK 
     *  3269 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                         =   9 =  OK 
     *  3270 - assertIsTrue  "a.b.c.d@domain.com"                                                      =   0 =  OK 
     *  3271 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3272 - assertIsFalse "ABC.DEF.GHI.JKL"                                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3273 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3274 - assertIsFalse "ABC.DEF @GHI.JKL"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3275 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3276 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3277 - assertIsFalse "ABC.DEF@"                                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3278 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3279 - assertIsFalse "ABC@DEF@GHI.JKL"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3280 - assertIsFalse "@%^%#$@#$@#.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3281 - assertIsFalse "email.domain.com"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3282 - assertIsFalse "email@domain@domain.com"                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3283 - assertIsFalse "first@last@test.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3284 - assertIsFalse "@test@a.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3285 - assertIsFalse "@\"someStringThatMightBe@email.com"                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3286 - assertIsFalse "test@@test.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3287 - assertIsFalse "ABCDEF@GHIJKL"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3288 - assertIsFalse "ABC.DEF@GHIJKL"                                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3289 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3290 - assertIsFalse "ABC.DEF@GHI.JKL."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3291 - assertIsFalse "ABC..DEF@GHI.JKL"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3292 - assertIsFalse "ABC.DEF@GHI..JKL"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3293 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3294 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3295 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3296 - assertIsFalse "ABC.DEF@."                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3297 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                    =   1 =  OK 
     *  3298 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                     =   0 =  OK 
     *  3299 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                        =   0 =  OK 
     *  3300 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                        =   0 =  OK 
     *  3301 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                         =   0 =  OK 
     *  3302 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                         =   0 =  OK 
     *  3303 - assertIsFalse "ABC.DEF@GHI.2KL"                                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3304 - assertIsFalse "ABC.DEF@GHI.JK-"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3305 - assertIsFalse "ABC.DEF@GHI.JK_"                                                         =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3306 - assertIsFalse "ABC.DEF@-HI.JKL"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3307 - assertIsFalse "ABC.DEF@_HI.JKL"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3308 - assertIsFalse "ABC DEF@GHI.DE"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3309 - assertIsFalse "ABC.DEF@GHI DE"                                                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3310 - assertIsFalse "A . B & C . D"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3311 - assertIsFalse " A . B & C . D"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3312 - assertIsFalse "(?).[!]@{&}.<:>"                                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- some more Testcases ----------------------------------------------------------------------------------------------------
     * 
     *  3313 - assertIsFalse "\"\".local.part.starts.with.empty.string1@domain.com"                    =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3314 - assertIsFalse "local.part.ends.with.empty.string1\"\"@domain.com"                       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3315 - assertIsFalse "local.part.with.empty.string1\"\"character@domain.com"                   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3316 - assertIsFalse "local.part.with.empty.string1.before\"\".point@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3317 - assertIsFalse "local.part.with.empty.string1.after.\"\"point@domain.com"                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3318 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"test@domain.com"             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3319 - assertIsFalse "(comment \"\") local.part.with.comment.with.empty.string1@domain.com"    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3320 - assertIsFalse "\"quote\"\"\".local.part.with.qoute.with.empty.string1@domain.com"       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3321 - assertIsFalse "\"\"@empty.string1.domain.com"                                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3322 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@empty.string1.domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3323 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3324 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1@domain.com>"              =   0 =  OK 
     *  3325 - assertIsTrue  "<pointy.brackets2.with.empty.string1@domain.com> name \"\""              =   0 =  OK 
     *  3326 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3327 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3328 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3329 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3330 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3331 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3332 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3333 - assertIsFalse "domain.part.with.comment.with.empty.string1@(comment \"\")domain.com"    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3334 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3335 - assertIsFalse "ip.v4.with.empty.string1@[123.14\"\"5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3336 - assertIsFalse "ip.v4.with.empty.string1@[123.145\"\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3337 - assertIsFalse "ip.v4.with.empty.string1@[123.145.\"\"178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3338 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90\"\"]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3339 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90]\"\""                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3340 - assertIsFalse "ip.v4.with.empty.string1@[\"\"123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3341 - assertIsFalse "ip.v4.with.empty.string1@\"\"[123.145.178.90]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3342 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3343 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3344 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3345 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3346 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3347 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3348 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3349 - assertIsFalse "a\"\"b.local.part.starts.with.empty.string2@domain.com"                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3350 - assertIsFalse "local.part.ends.with.empty.string2a\"\"b@domain.com"                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3351 - assertIsFalse "local.part.with.empty.string2a\"\"bcharacter@domain.com"                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3352 - assertIsFalse "local.part.with.empty.string2.beforea\"\"b.point@domain.com"             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3353 - assertIsFalse "local.part.with.empty.string2.after.a\"\"bpoint@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3354 - assertIsFalse "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com"         =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3355 - assertIsFalse "(comment a\"\"b) local.part.with.comment.with.empty.string2@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3356 - assertIsFalse "\"quotea\"\"b\".local.part.with.qoute.with.empty.string2@domain.com"     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3357 - assertIsFalse "a\"\"b@empty.string2.domain.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3358 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@empty.string2.domain.com"           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3359 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3360 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2@domain.com>"            =   0 =  OK 
     *  3361 - assertIsTrue  "<pointy.brackets2.with.empty.string2@domain.com> name a\"\"b"            =   0 =  OK 
     *  3362 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3363 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3364 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3365 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3366 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3367 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3368 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3369 - assertIsFalse "domain.part.with.comment.with.empty.string2@(comment a\"\"b)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3370 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3371 - assertIsFalse "ip.v4.with.empty.string2@[123.14a\"\"b5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3372 - assertIsFalse "ip.v4.with.empty.string2@[123.145a\"\"b.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3373 - assertIsFalse "ip.v4.with.empty.string2@[123.145.a\"\"b178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3374 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90a\"\"b]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3375 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90]a\"\"b"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3376 - assertIsFalse "ip.v4.with.empty.string2@[a\"\"b123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3377 - assertIsFalse "ip.v4.with.empty.string2@a\"\"b[123.145.178.90]"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3378 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3379 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3380 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3381 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3382 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3383 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3384 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3385 - assertIsFalse "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com"         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3386 - assertIsFalse "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com"            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3387 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"character@domain.com"        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3388 - assertIsFalse "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com"    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3389 - assertIsFalse "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com"     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3390 - assertIsFalse "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3391 - assertIsFalse "(comment \"\"\"\") local.part.with.comment.with.double.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3392 - assertIsFalse "\"quote\"\"\"\"\".local.part.with.qoute.with.double.empty.string1@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3393 - assertIsFalse "\"\"\"\"@double.empty.string1.domain.com"                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3394 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3395 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3396 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1@domain.com>"   =   0 =  OK 
     *  3397 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1@domain.com> name \"\"\"\""   =   0 =  OK 
     *  3398 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3399 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3400 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3401 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3402 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3403 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3404 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3405 - assertIsFalse "domain.part.with.comment.with.double.empty.string1@(comment \"\"\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3406 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3407 - assertIsFalse "ip.v4.with.double.empty.string1@[123.14\"\"\"\"5.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3408 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145\"\"\"\".178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3409 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.\"\"\"\"178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3410 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90\"\"\"\"]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3411 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90]\"\"\"\""                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3412 - assertIsFalse "ip.v4.with.double.empty.string1@[\"\"\"\"123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3413 - assertIsFalse "ip.v4.with.double.empty.string1@\"\"\"\"[123.145.178.90]"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3414 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3415 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3416 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3417 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3418 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3419 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3420 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3421 - assertIsFalse "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com"        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3422 - assertIsFalse "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3423 - assertIsFalse "local.part.with.double.empty.string2\"\".\"\"character@domain.com"       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3424 - assertIsFalse "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com"   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3425 - assertIsFalse "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com"    =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3426 - assertIsFalse "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3427 - assertIsFalse "(comment \"\".\"\") local.part.with.comment.with.double.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3428 - assertIsFalse "\"quote\"\".\"\"\".local.part.with.qoute.with.double.empty.string2@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3429 - assertIsFalse "\"\".\"\"@double.empty.string2.domain.com"                               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3430 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3431 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3432 - assertIsTrue  "name \"\".\"\" <pointy.brackets1.with.double.empty.string2@domain.com>"  =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3433 - assertIsTrue  "<pointy.brackets2.with.double.empty.string2@domain.com> name \"\".\"\""  =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3434 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3435 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3436 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3437 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3438 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3439 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3440 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3441 - assertIsFalse "domain.part.with.comment.with.double.empty.string2@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3442 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3443 - assertIsFalse "ip.v4.with.double.empty.string2@[123.14\"\".\"\"5.178.90]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3444 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145\"\".\"\".178.90]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3445 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.\"\".\"\"178.90]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3446 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90\"\".\"\"]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3447 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90]\"\".\"\""               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3448 - assertIsFalse "ip.v4.with.double.empty.string2@[\"\".\"\"123.145.178.90]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3449 - assertIsFalse "ip.v4.with.double.empty.string2@\"\".\"\"[123.145.178.90]"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3450 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3451 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3452 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3453 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3454 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3455 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3456 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3457 - assertIsTrue  "/.local.part.starts.with.forward.slash@domain.com"                       =   0 =  OK 
     *  3458 - assertIsTrue  "local.part.ends.with.forward.slash/@domain.com"                          =   0 =  OK 
     *  3459 - assertIsTrue  "local.part.with.forward.slash/character@domain.com"                      =   0 =  OK 
     *  3460 - assertIsTrue  "local.part.with.forward.slash.before/.point@domain.com"                  =   0 =  OK 
     *  3461 - assertIsTrue  "local.part.with.forward.slash.after./point@domain.com"                   =   0 =  OK 
     *  3462 - assertIsTrue  "local.part.with.double.forward.slash//test@domain.com"                   =   0 =  OK 
     *  3463 - assertIsTrue  "(comment /) local.part.with.comment.with.forward.slash@domain.com"       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3464 - assertIsTrue  "\"quote/\".local.part.with.qoute.with.forward.slash@domain.com"          =   1 =  OK 
     *  3465 - assertIsTrue  "/@forward.slash.domain.com"                                              =   0 =  OK 
     *  3466 - assertIsTrue  "//////@forward.slash.domain.com"                                         =   0 =  OK 
     *  3467 - assertIsTrue  "/./././././@forward.slash.domain.com"                                    =   0 =  OK 
     *  3468 - assertIsFalse "name / <pointy.brackets1.with.forward.slash@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3469 - assertIsFalse "<pointy.brackets2.with.forward.slash@domain.com> name /"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3470 - assertIsFalse "domain.part@with/forward.slash.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3471 - assertIsFalse "domain.part@with//double.forward.slash.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3472 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3473 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3474 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3475 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3476 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3477 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3478 - assertIsTrue  "domain.part.with.comment.with.forward.slash@(comment /)domain.com"       =   6 =  OK 
     *  3479 - assertIsFalse "domain.part.only.forward.slash@/.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3480 - assertIsFalse "ip.v4.with.forward.slash@[123.14/5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3481 - assertIsFalse "ip.v4.with.forward.slash@[123.145/.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3482 - assertIsFalse "ip.v4.with.forward.slash@[123.145./178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3483 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90/]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3484 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90]/"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3485 - assertIsFalse "ip.v4.with.forward.slash@[/123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3486 - assertIsFalse "ip.v4.with.forward.slash@/[123.145.178.90]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3487 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3488 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3489 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3490 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3491 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3492 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3493 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3494 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3495 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     *  3496 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3497 - assertIsTrue  "Ärger.Öde@Übel.de"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3498 - assertIsTrue  "Smørrebrød@danmark.dk"                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3499 - assertIsTrue  "ip6.without.brackets@1:2:3:4:5:6:7:8"                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3500 - assertIsTrue  "(space after comment) john.smith@example.com"                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3501 - assertIsTrue  "email.address.without@topleveldomain"                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3502 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                      =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3503 - assertIsFalse "@1st.relay,@2nd.relay:user@final.domain"                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * Fillup ist nicht aktiv
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1161   KORREKT 1087 =   93.626 % | FALSCH ERKANNT   74 =    6.374 % = Error 0
     *   ASSERT_IS_FALSE 2342   KORREKT 2330 =   99.488 % | FALSCH ERKANNT   12 =    0.512 % = Error 0
     * 
     *   GESAMT          3503   KORREKT 3417 =   97.545 % | FALSCH ERKANNT   86 =    2.455 % = Error 0
     * 
     * 
     *   Millisekunden     85 = 0.024264915786468742
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
      assertIsTrue( "\"string1\\\"qoute2\\\"\"@domain1.tld" );

      assertIsTrue( "(comment1)name1@domain1.tld" );
      assertIsTrue( "(comment1)-name1@domain1.tld" );
      assertIsTrue( "name1(comment1)@domain1.tld" );
      assertIsTrue( "name1@(comment1)domain1.tld" );
      assertIsTrue( "name1@domain1.tld(comment1)" );

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
      assertIsTrue( "\"display name\" <(comment)local.part.\"string1\"@domain-name.top_level_domain>" );

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
      assertIsFalse( "(comment @) local.part.with.at.sign.in.comment@domain.com" );
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
      assertIsFalse( "(comment .) local.part.with.dot.in.comment@domain.com" );
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
      assertIsFalse( "(comment ..) local.part.with.double.dot.in.comment@domain.com" );
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
      assertIsFalse( "(comment &) local.part.with.amp.in.comment@domain.com" );
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
      assertIsFalse( "(comment *) local.part.with.asterisk.in.comment@domain.com" );
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

      assertIsTrue( "$.local.part.starts.with.dollar@domain.com" );
      assertIsTrue( "local.part.ends.with.dollar$@domain.com" );
      assertIsTrue( "local.part.with.dollar.before$.point@domain.com" );
      assertIsTrue( "local.part.with.dollar.after.$point@domain.com" );
      assertIsTrue( "local.part.with.double.dollar$$test@domain.com" );
      assertIsFalse( "(comment $) local.part.with.comment.with.dollar@domain.com" ); // ?
      assertIsTrue( "\"quote$\".local.part.with.qoute.with.dollar@domain.com" );
      assertIsTrue( "$@dollar.domain.com" );
      assertIsTrue( "$$$$$$@dollar.domain.com" );
      assertIsTrue( "$.$.$.$.$.$@dollar.domain.com" );
      assertIsFalse( "name $ <pointy.brackets1.with.dollar@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.dollar@domain.com> name $" ); // ?

      assertIsTrue( "=.local.part.starts.with.equality@domain.com" );
      assertIsTrue( "local.part.ends.with.equality=@domain.com" );
      assertIsTrue( "local.part.with.equality.before=.point@domain.com" );
      assertIsTrue( "local.part.with.equality.after.=point@domain.com" );
      assertIsTrue( "local.part.with.double.equality==test@domain.com" );
      assertIsFalse( "(comment =) local.part.with.comment.with.equality@domain.com" ); // ?
      assertIsTrue( "\"quote=\".local.part.with.qoute.with.equality@domain.com" );
      assertIsTrue( "=@equality.domain.com" );
      assertIsTrue( "======@equality.domain.com" );
      assertIsTrue( "=.=.=.=.=.=@equality.domain.com" );
      assertIsFalse( "name = <pointy.brackets1.with.equality@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.equality@domain.com> name =" ); // ?

      assertIsTrue( "!.local.part.starts.with.exclamation@domain.com" );

      assertIsTrue( "local.part.ends.with.exclamation!@domain.com" );
      assertIsTrue( "local.part.with.exclamation.before!.point@domain.com" );
      assertIsTrue( "local.part.with.exclamation.after.!point@domain.com" );
      assertIsTrue( "local.part.with.double.exclamation!!test@domain.com" );
      assertIsFalse( "(comment !) local.part.with.comment.with.exclamation@domain.com" ); // ?
      assertIsTrue( "\"quote!\".local.part.with.qoute.with.exclamation@domain.com" );
      assertIsTrue( "!@exclamation.domain.com" );
      assertIsTrue( "!!!!!!@exclamation.domain.com" );
      assertIsTrue( "!.!.!.!.!.!@exclamation.domain.com" );
      assertIsFalse( "name ! <pointy.brackets1.with.exclamation@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.exclamation@domain.com> name !" ); // ?

      assertIsTrue( "`.local.part.starts.with.grave-accent@domain.com" );
      assertIsTrue( "local.part.ends.with.grave-accent`@domain.com" );
      assertIsTrue( "local.part.with.grave-accent.before`.point@domain.com" );
      assertIsTrue( "local.part.with.grave-accent.after.`point@domain.com" );
      assertIsTrue( "local.part.with.double.grave-accent``test@domain.com" );
      assertIsFalse( "(comment `) local.part.with.comment.with.grave-accent@domain.com" ); // ?
      assertIsTrue( "\"quote`\".local.part.with.qoute.with.grave-accent@domain.com" );
      assertIsTrue( "`@grave-accent.domain.com" );
      assertIsTrue( "``````@grave-accent.domain.com" );
      assertIsTrue( "`.`.`.`.`.`@grave-accent.domain.com" );
      assertIsFalse( "name ` <pointy.brackets1.with.grave-accent@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.grave-accent@domain.com> name `" ); // ?

      assertIsTrue( "#.local.part.starts.with.hash@domain.com" );
      assertIsTrue( "local.part.ends.with.hash#@domain.com" );
      assertIsTrue( "local.part.with.hash.before#.point@domain.com" );
      assertIsTrue( "local.part.with.hash.after.#point@domain.com" );
      assertIsTrue( "local.part.with.double.hash##test@domain.com" );
      assertIsFalse( "(comment #) local.part.with.comment.with.hash@domain.com" ); // ?
      assertIsTrue( "\"quote#\".local.part.with.qoute.with.hash@domain.com" );
      assertIsTrue( "#@hash.domain.com" );
      assertIsTrue( "######@hash.domain.com" );
      assertIsTrue( "#.#.#.#.#.#@hash.domain.com" );
      assertIsFalse( "name # <pointy.brackets1.with.hash@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.hash@domain.com> name #" ); // ?

      assertIsTrue( "-.local.part.starts.with.hypen@domain.com" );
      assertIsTrue( "local.part.ends.with.hypen-@domain.com" );
      assertIsTrue( "local.part.with.hypen.before-.point@domain.com" );
      assertIsTrue( "local.part.with.hypen.after.-point@domain.com" );
      assertIsTrue( "local.part.with.double.hypen--test@domain.com" );
      assertIsFalse( "(comment -) local.part.with.comment.with.hypen@domain.com" ); // ?
      assertIsTrue( "\"quote-\".local.part.with.qoute.with.hypen@domain.com" );
      assertIsTrue( "-@hypen.domain.com" );
      assertIsTrue( "------@hypen.domain.com" );
      assertIsTrue( "-.-.-.-.-.-@hypen.domain.com" );
      assertIsFalse( "name - <pointy.brackets1.with.hypen@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.hypen@domain.com> name -" ); // ?

      assertIsTrue( "{.local.part.starts.with.leftbracket@domain.com" );
      assertIsTrue( "local.part.ends.with.leftbracket{@domain.com" );
      assertIsTrue( "local.part.with.leftbracket.before{.point@domain.com" );
      assertIsTrue( "local.part.with.leftbracket.after.{point@domain.com" );
      assertIsTrue( "local.part.with.double.leftbracket{{test@domain.com" );
      assertIsFalse( "(comment {) local.part.with.comment.with.leftbracket@domain.com" ); // ?
      assertIsTrue( "\"quote{\".local.part.with.qoute.with.leftbracket@domain.com" );
      assertIsTrue( "{@leftbracket.domain.com" );
      assertIsTrue( "{{{{{{@leftbracket.domain.com" );
      assertIsTrue( "{.{.{.{.{.{@leftbracket.domain.com" );
      assertIsFalse( "name { <pointy.brackets1.with.leftbracket@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.leftbracket@domain.com> name {" ); // ?

      assertIsTrue( "%.local.part.starts.with.percentage@domain.com" );
      assertIsTrue( "local.part.ends.with.percentage%@domain.com" );
      assertIsTrue( "local.part.with.percentage.before%.point@domain.com" );
      assertIsTrue( "local.part.with.percentage.after.%point@domain.com" );
      assertIsTrue( "local.part.with.double.percentage%%test@domain.com" );
      assertIsFalse( "(comment %) local.part.with.comment.with.percentage@domain.com" ); // ?
      assertIsTrue( "\"quote%\".local.part.with.qoute.with.percentage@domain.com" );
      assertIsTrue( "%@percentage.domain.com" );
      assertIsTrue( "%%%%%%@percentage.domain.com" );
      assertIsTrue( "%.%.%.%.%.%@percentage.domain.com" );
      assertIsFalse( "name % <pointy.brackets1.with.percentage@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.percentage@domain.com> name %" ); // ?

      assertIsTrue( "|.local.part.starts.with.pipe@domain.com" );
      assertIsTrue( "local.part.ends.with.pipe|@domain.com" );
      assertIsTrue( "local.part.with.pipe.before|.point@domain.com" );
      assertIsTrue( "local.part.with.pipe.after.|point@domain.com" );
      assertIsTrue( "local.part.with.double.pipe||test@domain.com" );
      assertIsFalse( "(comment |) local.part.with.comment.with.pipe@domain.com" ); // ?
      assertIsTrue( "\"quote|\".local.part.with.qoute.with.pipe@domain.com" );
      assertIsTrue( "|@pipe.domain.com" );
      assertIsTrue( "||||||@pipe.domain.com" );
      assertIsTrue( "|.|.|.|.|.|@pipe.domain.com" );
      assertIsFalse( "name | <pointy.brackets1.with.pipe@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.pipe@domain.com> name |" ); // ?

      assertIsTrue( "+.local.part.starts.with.plus@domain.com" );
      assertIsTrue( "local.part.ends.with.plus+@domain.com" );
      assertIsTrue( "local.part.with.plus.before+.point@domain.com" );
      assertIsTrue( "local.part.with.plus.after.+point@domain.com" );
      assertIsTrue( "local.part.with.double.plus++test@domain.com" );
      assertIsFalse( "(comment +) local.part.with.comment.with.plus@domain.com" ); // ?
      assertIsTrue( "\"quote+\".local.part.with.qoute.with.plus@domain.com" );
      assertIsTrue( "+@plus.domain.com" );
      assertIsTrue( "++++++@plus.domain.com" );
      assertIsTrue( "+.+.+.+.+.+@plus.domain.com" );
      assertIsFalse( "name + <pointy.brackets1.with.plus@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.plus@domain.com> name +" ); // ?

      assertIsTrue( "?.local.part.starts.with.question@domain.com" );
      assertIsTrue( "local.part.ends.with.question?@domain.com" );
      assertIsTrue( "local.part.with.question.before?.point@domain.com" );
      assertIsTrue( "local.part.with.question.after.?point@domain.com" );
      assertIsTrue( "local.part.with.double.question??test@domain.com" );
      assertIsFalse( "(comment ?) local.part.with.comment.with.question@domain.com" ); // ?
      assertIsTrue( "\"quote?\".local.part.with.qoute.with.question@domain.com" );
      assertIsTrue( "?@question.domain.com" );
      assertIsTrue( "??????@question.domain.com" );
      assertIsTrue( "?.?.?.?.?.?@question.domain.com" );
      assertIsFalse( "name ? <pointy.brackets1.with.question@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.question@domain.com> name ?" );

      assertIsTrue( "}.local.part.starts.with.rightbracket@domain.com" );
      assertIsTrue( "local.part.ends.with.rightbracket}@domain.com" );
      assertIsTrue( "local.part.with.rightbracket.before}.point@domain.com" );
      assertIsTrue( "local.part.with.rightbracket.after.}point@domain.com" );
      assertIsTrue( "local.part.with.double.rightbracket}}test@domain.com" );
      assertIsFalse( "(comment }) local.part.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote}\".local.part.with.qoute.with.rightbracket@domain.com" );
      assertIsTrue( "}@rightbracket.domain.com" );
      assertIsTrue( "}}}}}}@rightbracket.domain.com" );
      assertIsTrue( "}.}.}.}.}.}@rightbracket.domain.com" );
      assertIsFalse( "name } <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket@domain.com> name }" );

      assertIsTrue( "~.local.part.starts.with.tilde@domain.com" );
      assertIsTrue( "local.part.ends.with.tilde~@domain.com" );
      assertIsTrue( "local.part.with.tilde.before~.point@domain.com" );
      assertIsTrue( "local.part.with.tilde.after.~point@domain.com" );
      assertIsTrue( "local.part.with.double.tilde~~test@domain.com" );
      assertIsFalse( "(comment ~) local.part.with.comment.with.tilde@domain.com" );
      assertIsTrue( "\"quote~\".local.part.with.qoute.with.tilde@domain.com" );
      assertIsTrue( "~@tilde.domain.com" );
      assertIsTrue( "~~~~~~@tilde.domain.com" );
      assertIsTrue( "~.~.~.~.~.~@tilde.domain.com" );
      assertIsFalse( "name ~ <pointy.brackets1.with.tilde@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.tilde@domain.com> name ~" );

      assertIsTrue( "^.local.part.starts.with.xor@domain.com" );
      assertIsTrue( "local.part.ends.with.xor^@domain.com" );
      assertIsTrue( "local.part.with.xor.before^.point@domain.com" );
      assertIsTrue( "local.part.with.xor.after.^point@domain.com" );
      assertIsTrue( "local.part.with.double.xor^^test@domain.com" );
      assertIsFalse( "(comment ^) local.part.with.comment.with.xor@domain.com" );
      assertIsTrue( "\"quote^\".local.part.with.qoute.with.xor@domain.com" );
      assertIsTrue( "^@xor.domain.com" );
      assertIsTrue( "^^^^^^@xor.domain.com" );
      assertIsTrue( "^.^.^.^.^.^@xor.domain.com" );
      assertIsFalse( "name ^ <pointy.brackets1.with.xor@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.xor@domain.com> name ^" );

      assertIsTrue( "_.local.part.starts.with.underscore@domain.com" );
      assertIsTrue( "local.part.ends.with.underscore_@domain.com" );
      assertIsTrue( "local.part.with.underscore.before_.point@domain.com" );
      assertIsTrue( "local.part.with.underscore.after._point@domain.com" );
      assertIsTrue( "local.part.with.double.underscore__test@domain.com" );
      assertIsFalse( "(comment _) local.part.with.comment.with.underscore@domain.com" );
      assertIsTrue( "\"quote_\".local.part.with.qoute.with.underscore@domain.com" );
      assertIsTrue( "_@underscore.domain.com" );
      assertIsTrue( "______@underscore.domain.com" );
      assertIsTrue( "_._._._._._@underscore.domain.com" );
      assertIsFalse( "name _ <pointy.brackets1.with.underscore@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.underscore@domain.com> name _" );

      assertIsFalse( ":.local.part.starts.with.colon@domain.com" );
      assertIsFalse( "local.part.ends.with.colon:@domain.com" );
      assertIsFalse( "local.part.with.colon.before:.point@domain.com" );
      assertIsFalse( "local.part.with.colon.after.:point@domain.com" );
      assertIsFalse( "local.part.with.double.colon::test@domain.com" );
      assertIsFalse( "(comment :) local.part.with.comment.with.colon@domain.com" );
      assertIsTrue( "\"quote:\".local.part.with.qoute.with.colon@domain.com" );
      assertIsFalse( ":@colon.domain.com" );
      assertIsFalse( "::::::@colon.domain.com" );
      assertIsFalse( ":.:.:.:.:.:@colon.domain.com" );
      assertIsFalse( "name : <pointy.brackets1.with.colon@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.colon@domain.com> name :" );

      assertIsFalse( "(.local.part.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.leftbracket(@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.before(.point@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.after.(point@domain.com" );
      assertIsFalse( "local.part.with.double.leftbracket((test@domain.com" );
      assertIsFalse( "(comment () local.part.with.comment.with.leftbracket@domain.com" );
      assertIsTrue( "\"quote(\".local.part.with.qoute.with.leftbracket@domain.com" );
      assertIsFalse( "(@leftbracket.domain.com" );
      assertIsFalse( "((((((@leftbracket.domain.com" );
      assertIsFalse( "(()(((@leftbracket.domain.com" );
      assertIsFalse( "((<)>(((@leftbracket.domain.com" );
      assertIsFalse( "(.(.(.(.(.(@leftbracket.domain.com" );
      assertIsTrue( "name ( <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.leftbracket@domain.com> name (" );

      assertIsFalse( "\\.local.part.starts.with.slash@domain.com" );
      assertIsFalse( "local.part.ends.with.slash\\@domain.com" );
      assertIsFalse( "local.part.with.slash.before\\.point@domain.com" );
      assertIsFalse( "local.part.with.slash.after.\\point@domain.com" );
      assertIsTrue( "local.part.with.double.slash\\\\test@domain.com" );
      assertIsFalse( "(comment \\) local.part.with.comment.with.slash@domain.com" );
      assertIsFalse( "\"quote\\\".local.part.with.qoute.with.slash@domain.com" );
      assertIsFalse( "\\@slash.domain.com" );
      assertIsTrue( "\\\\\\\\\\\\@slash.domain.com" );
      assertIsFalse( "\\.\\.\\.\\.\\.\\@slash.domain.com" );
      assertIsFalse( "name \\ <pointy.brackets1.with.slash@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.slash@domain.com> name \\" );

      assertIsFalse( ").local.part.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.rightbracket)@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.before).point@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.after.)point@domain.com" );
      assertIsFalse( "local.part.with.double.rightbracket))test@domain.com" );
      assertIsFalse( "(comment )) local.part.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote)\".local.part.with.qoute.with.rightbracket@domain.com" );
      assertIsFalse( ")@rightbracket.domain.com" );
      assertIsFalse( "))))))@rightbracket.domain.com" );
      assertIsFalse( ").).).).).)@rightbracket.domain.com" );
      assertIsTrue( "name ) <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.rightbracket@domain.com> name )" );

      assertIsFalse( "[.local.part.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.leftbracket[@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.before[.point@domain.com" );
      assertIsFalse( "local.part.with.leftbracket.after.[point@domain.com" );
      assertIsFalse( "local.part.with.double.leftbracket[[test@domain.com" );
      assertIsFalse( "(comment [) local.part.with.comment.with.leftbracket@domain.com" );
      assertIsTrue( "\"quote[\".local.part.with.qoute.with.leftbracket@domain.com" );
      assertIsFalse( "[@leftbracket.domain.com" );
      assertIsFalse( "[[[[[[@leftbracket.domain.com" );
      assertIsFalse( "[.[.[.[.[.[@leftbracket.domain.com" );
      assertIsFalse( "name [ <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.leftbracket@domain.com> name [" );

      assertIsFalse( "].local.part.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.part.ends.with.rightbracket]@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.before].point@domain.com" );
      assertIsFalse( "local.part.with.rightbracket.after.]point@domain.com" );
      assertIsFalse( "local.part.with.double.rightbracket]]test@domain.com" );
      assertIsFalse( "(comment ]) local.part.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote]\".local.part.with.qoute.with.rightbracket@domain.com" );
      assertIsFalse( "]@rightbracket.domain.com" );
      assertIsFalse( "]]]]]]@rightbracket.domain.com" );
      assertIsFalse( "].].].].].]@rightbracket.domain.com" );
      assertIsFalse( "name ] <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket@domain.com> name ]" );

      assertIsFalse( " .local.part.starts.with.space@domain.com" );
      assertIsFalse( "local.part.ends.with.space @domain.com" );
      assertIsFalse( "local.part.with.space.before .point@domain.com" );
      assertIsFalse( "local.part.with.space.after. point@domain.com" );
      assertIsFalse( "local.part.with.double.space  test@domain.com" );
      assertIsFalse( "(comment  ) local.part.with.comment.with.space@domain.com" );
      assertIsTrue( "\"quote \".local.part.with.qoute.with.space@domain.com" );
      assertIsFalse( " @space.domain.com" );
      assertIsFalse( "      @space.domain.com" );
      assertIsFalse( " . . . . . @space.domain.com" );
      assertIsTrue( "name   <pointy.brackets1.with.space@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.space@domain.com> name  " );

      assertIsFalse( "().local.part.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.part.ends.with.empty.bracket()@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.before().point@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.after.()point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.bracket()()test@domain.com" );
      assertIsFalse( "(comment ()) local.part.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote()\".local.part.with.qoute.with.empty.bracket@domain.com" );
      assertIsFalse( "()@empty.bracket.domain.com" );
      assertIsFalse( "()()()()()()@empty.bracket.domain.com" );
      assertIsFalse( "().().().().().()@empty.bracket.domain.com" );
      assertIsTrue( "name () <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.bracket@domain.com> name ()" );

      assertIsTrue( "{}.local.part.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.part.ends.with.empty.bracket{}@domain.com" );
      assertIsTrue( "local.part.with.empty.bracket.before{}.point@domain.com" );
      assertIsTrue( "local.part.with.empty.bracket.after.{}point@domain.com" );
      assertIsTrue( "local.part.with.double.empty.bracket{}{}test@domain.com" );
      assertIsFalse( "(comment {}) local.part.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote{}\".local.part.with.qoute.with.empty.bracket@domain.com" );
      assertIsTrue( "{}@empty.bracket.domain.com" );
      assertIsTrue( "{}{}{}{}{}{}@empty.bracket.domain.com" );
      assertIsTrue( "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com" );
      assertIsFalse( "name {} <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket@domain.com> name {}" );

      assertIsFalse( "[].local.part.starts.with.empty.bracket@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.bracket[]@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.before[].point@domain.com" );
      assertIsFalse( "local.part.with.empty.bracket.after.[]point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.bracket[][]test@domain.com" );
      assertIsFalse( "(comment []) local.part.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote[]\".local.part.with.qoute.with.empty.bracket@domain.com" );
      assertIsFalse( "[]@empty.bracket.domain.com" );
      assertIsFalse( "[][][][][][]@empty.bracket.domain.com" );
      assertIsFalse( "[].[].[].[].[].[]@empty.bracket.domain.com" );
      assertIsFalse( "name [] <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket@domain.com> name []" );

      assertIsTrue( "999.local.part.starts.with.byte.overflow@domain.com" );
      assertIsTrue( "local.part.ends.with.byte.overflow999@domain.com" );
      assertIsTrue( "local.part.with.byte.overflow.before999.point@domain.com" );
      assertIsTrue( "local.part.with.byte.overflow.after.999point@domain.com" );
      assertIsTrue( "local.part.with.double.byte.overflow999999test@domain.com" );
      assertIsTrue( "(comment 999) local.part.with.comment.with.byte.overflow@domain.com" );
      assertIsTrue( "\"quote999\".local.part.with.qoute.with.byte.overflow@domain.com" );
      assertIsTrue( "999@byte.overflow.domain.com" );
      assertIsTrue( "999999999999999999@byte.overflow.domain.com" );
      assertIsTrue( "999.999.999.999.999.999@byte.overflow.domain.com" );
      assertIsTrue( "name 999 <pointy.brackets1.with.byte.overflow@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.byte.overflow@domain.com> name 999" );

      assertIsTrue( "\"str\".local.part.starts.with.string@domain.com" );
      assertIsFalse( "local.part.ends.with.string\"str\"@domain.com" );
      assertIsFalse( "local.part.with.string.before\"str\".point@domain.com" );
      assertIsFalse( "local.part.with.string.after.\"str\"point@domain.com" );
      assertIsFalse( "local.part.with.double.string\"str\"\"str\"test@domain.com" );
      assertIsFalse( "(comment \"str\") local.part.with.comment.with.string@domain.com" );
      assertIsFalse( "\"quote\"str\"\".local.part.with.qoute.with.string@domain.com" );
      assertIsTrue( "\"str\"@string.domain.com" );
      assertIsFalse( "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com" );
      assertIsTrue( "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com" );
      assertIsTrue( "name \"str\" <pointy.brackets1.with.string@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.string@domain.com> name \"str\"" );

      assertIsFalse( "(comment).local.part.starts.with.comment@domain.com" );
      assertIsTrue( "local.part.ends.with.comment(comment)@domain.com" );
      assertIsFalse( "local.part.with.comment.before(comment).point@domain.com" );
      assertIsFalse( "local.part.with.comment.after.(comment)point@domain.com" );
      assertIsFalse( "local.part.with.double.comment(comment)(comment)test@domain.com" );
      assertIsFalse( "(comment (comment)) local.part.with.comment.with.comment@domain.com" );
      assertIsTrue( "\"quote(comment)\".local.part.with.qoute.with.comment@domain.com" );
      assertIsFalse( "(comment)@comment.domain.com" );
      assertIsFalse( "(comment)(comment)(comment)(comment)@comment.domain.com" );
      assertIsFalse( "(comment).(comment).(comment).(comment)@comment.domain.com" );
      assertIsTrue( "name (comment) <pointy.brackets1.with.comment@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.comment@domain.com> name (comment)" );

      assertIsTrue( "domain.part@with0number0.com" );
      assertIsTrue( "domain.part@0with.number0.at.domain.start.com" );
      assertIsTrue( "domain.part@with.number0.at.domain.end10.com" );
      assertIsTrue( "domain.part@with.number0.at.domain.end2.com0" );
      assertIsTrue( "domain.part@with.number0.before0.point.com" );
      assertIsTrue( "domain.part@with.number0.after.0point.com" );

      assertIsTrue( "domain.part@with9number9.com" );
      assertIsTrue( "domain.part@9with.number9.at.domain.start.com" );
      assertIsTrue( "domain.part@with.number9.at.domain.end19.com" );
      assertIsTrue( "domain.part@with.number9.at.domain.end2.com9" );
      assertIsTrue( "domain.part@with.number9.before9.point.com" );
      assertIsTrue( "domain.part@with.number9.after.9point.com" );

      assertIsTrue( "domain.part.only.numbers@1234567890.com" );
      assertIsTrue( "domain.part@with0123456789numbers.com" );
      assertIsTrue( "domain.part@0123456789with.numbers.at.domain.start.com" );
      assertIsTrue( "domain.part@with.numbers.at.domain.end10123456789.com" );
      assertIsTrue( "domain.part@with.numbers.at.domain.end2.com0123456789" );
      assertIsTrue( "domain.part@with.numbers.before0123456789.point.com" );
      assertIsTrue( "domain.part@with.numbers.after.0123456789point.com" );

      assertIsTrue( "domain.part@with-hyphen.com" );
      assertIsFalse( "domain.part@-with.hyphen.at.domain.start.com" );
      assertIsFalse( "domain.part@with.hyphen.at.domain.end1-.com" );
      assertIsFalse( "domain.part@with.hyphen.at.domain.end2.com-" );
      assertIsFalse( "domain.part@with.hyphen.before-.point.com" );
      assertIsFalse( "domain.part@with.-hyphen.after.point.com" );

      assertIsTrue( "domain.part@with_underscore.com" );
      assertIsFalse( "domain.part@_with.underscore.at.domain.start.com" );
      assertIsFalse( "domain.part@with.underscore.at.domain.end1_.com" );
      assertIsFalse( "domain.part@with.underscore.at.domain.end2.com_" );
      assertIsFalse( "domain.part@with.underscore.before_.point.com" );
      assertIsFalse( "domain.part@with.underscore.after._point.com" );

      assertIsFalse( "domain.part@with&amp.com" );
      assertIsFalse( "domain.part@&with.amp.at.domain.start.com" );
      assertIsFalse( "domain.part@with.amp.at.domain.end1&.com" );
      assertIsFalse( "domain.part@with.amp.at.domain.end2.com&" );
      assertIsFalse( "domain.part@with.amp.before&.point.com" );
      assertIsFalse( "domain.part@with.amp.after.&point.com" );

      assertIsFalse( "domain.part@with*asterisk.com" );
      assertIsFalse( "domain.part@*with.asterisk.at.domain.start.com" );
      assertIsFalse( "domain.part@with.asterisk.at.domain.end1*.com" );
      assertIsFalse( "domain.part@with.asterisk.at.domain.end2.com*" );
      assertIsFalse( "domain.part@with.asterisk.before*.point.com" );
      assertIsFalse( "domain.part@with.asterisk.after.*point.com" );

      assertIsFalse( "domain.part@with$dollar.com" );
      assertIsFalse( "domain.part@$with.dollar.at.domain.start.com" );
      assertIsFalse( "domain.part@with.dollar.at.domain.end1$.com" );
      assertIsFalse( "domain.part@with.dollar.at.domain.end2.com$" );
      assertIsFalse( "domain.part@with.dollar.before$.point.com" );
      assertIsFalse( "domain.part@with.dollar.after.$point.com" );

      assertIsFalse( "domain.part@with=equality.com" );
      assertIsFalse( "domain.part@=with.equality.at.domain.start.com" );
      assertIsFalse( "domain.part@with.equality.at.domain.end1=.com" );
      assertIsFalse( "domain.part@with.equality.at.domain.end2.com=" );
      assertIsFalse( "domain.part@with.equality.before=.point.com" );
      assertIsFalse( "domain.part@with.equality.after.=point.com" );

      assertIsFalse( "domain.part@with!exclamation.com" );
      assertIsFalse( "domain.part@!with.exclamation.at.domain.start.com" );
      assertIsFalse( "domain.part@with.exclamation.at.domain.end1!.com" );
      assertIsFalse( "domain.part@with.exclamation.at.domain.end2.com!" );
      assertIsFalse( "domain.part@with.exclamation.before!.point.com" );
      assertIsFalse( "domain.part@with.exclamation.after.!point.com" );

      assertIsFalse( "domain.part@with?question.com" );
      assertIsFalse( "domain.part@?with.question.at.domain.start.com" );
      assertIsFalse( "domain.part@with.question.at.domain.end1?.com" );
      assertIsFalse( "domain.part@with.question.at.domain.end2.com?" );
      assertIsFalse( "domain.part@with.question.before?.point.com" );
      assertIsFalse( "domain.part@with.question.after.?point.com" );

      assertIsFalse( "domain.part@with`grave-accent.com" );
      assertIsFalse( "domain.part@`with.grave-accent.at.domain.start.com" );
      assertIsFalse( "domain.part@with.grave-accent.at.domain.end1`.com" );
      assertIsFalse( "domain.part@with.grave-accent.at.domain.end2.com`" );
      assertIsFalse( "domain.part@with.grave-accent.before`.point.com" );
      assertIsFalse( "domain.part@with.grave-accent.after.`point.com" );

      assertIsFalse( "domain.part@with#hash.com" );
      assertIsFalse( "domain.part@#with.hash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.hash.at.domain.end1#.com" );
      assertIsFalse( "domain.part@with.hash.at.domain.end2.com#" );
      assertIsFalse( "domain.part@with.hash.before#.point.com" );
      assertIsFalse( "domain.part@with.hash.after.#point.com" );

      assertIsFalse( "domain.part@with%percentage.com" );
      assertIsFalse( "domain.part@%with.percentage.at.domain.start.com" );
      assertIsFalse( "domain.part@with.percentage.at.domain.end1%.com" );
      assertIsFalse( "domain.part@with.percentage.at.domain.end2.com%" );
      assertIsFalse( "domain.part@with.percentage.before%.point.com" );
      assertIsFalse( "domain.part@with.percentage.after.%point.com" );

      assertIsFalse( "domain.part@with|pipe.com" );
      assertIsFalse( "domain.part@|with.pipe.at.domain.start.com" );
      assertIsFalse( "domain.part@with.pipe.at.domain.end1|.com" );
      assertIsFalse( "domain.part@with.pipe.at.domain.end2.com|" );
      assertIsFalse( "domain.part@with.pipe.before|.point.com" );
      assertIsFalse( "domain.part@with.pipe.after.|point.com" );

      assertIsFalse( "domain.part@with+plus.com" );
      assertIsFalse( "domain.part@+with.plus.at.domain.start.com" );
      assertIsFalse( "domain.part@with.plus.at.domain.end1+.com" );
      assertIsFalse( "domain.part@with.plus.at.domain.end2.com+" );
      assertIsFalse( "domain.part@with.plus.before+.point.com" );
      assertIsFalse( "domain.part@with.plus.after.+point.com" );

      assertIsFalse( "domain.part@with{leftbracket.com" );
      assertIsFalse( "domain.part@{with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1{.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com{" );
      assertIsFalse( "domain.part@with.leftbracket.before{.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.{point.com" );

      assertIsFalse( "domain.part@with}rightbracket.com" );
      assertIsFalse( "domain.part@}with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1}.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com}" );
      assertIsFalse( "domain.part@with.rightbracket.before}.point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.}point.com" );

      assertIsFalse( "domain.part@with(leftbracket.com" );
      assertIsFalse( "domain.part@(with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1(.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com(" );
      assertIsFalse( "domain.part@with.leftbracket.before(.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.(point.com" );

      assertIsFalse( "domain.part@with)rightbracket.com" );
      assertIsFalse( "domain.part@)with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1).com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com)" );
      assertIsFalse( "domain.part@with.rightbracket.before).point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.)point.com" );

      assertIsFalse( "domain.part@with[leftbracket.com" );
      assertIsFalse( "domain.part@[with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1[.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com[" );
      assertIsFalse( "domain.part@with.leftbracket.before[.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.[point.com" );

      assertIsFalse( "domain.part@with]rightbracket.com" );
      assertIsFalse( "domain.part@]with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1].com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com]" );
      assertIsFalse( "domain.part@with.rightbracket.before].point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.]point.com" );

      assertIsFalse( "domain.part@with~tilde.com" );
      assertIsFalse( "domain.part@~with.tilde.at.domain.start.com" );
      assertIsFalse( "domain.part@with.tilde.at.domain.end1~.com" );
      assertIsFalse( "domain.part@with.tilde.at.domain.end2.com~" );
      assertIsFalse( "domain.part@with.tilde.before~.point.com" );
      assertIsFalse( "domain.part@with.tilde.after.~point.com" );

      assertIsFalse( "domain.part@with^xor.com" );
      assertIsFalse( "domain.part@^with.xor.at.domain.start.com" );
      assertIsFalse( "domain.part@with.xor.at.domain.end1^.com" );
      assertIsFalse( "domain.part@with.xor.at.domain.end2.com^" );
      assertIsFalse( "domain.part@with.xor.before^.point.com" );
      assertIsFalse( "domain.part@with.xor.after.^point.com" );

      assertIsFalse( "domain.part@with:colon.com" );
      assertIsFalse( "domain.part@:with.colon.at.domain.start.com" );
      assertIsFalse( "domain.part@with.colon.at.domain.end1:.com" );
      assertIsFalse( "domain.part@with.colon.at.domain.end2.com:" );
      assertIsFalse( "domain.part@with.colon.before:.point.com" );
      assertIsFalse( "domain.part@with.colon.after.:point.com" );

      assertIsFalse( "domain.part@with space.com" );
      assertIsFalse( "domain.part@ with.space.at.domain.start.com" );
      assertIsFalse( "domain.part@with.space.at.domain.end1 .com" );
      assertIsFalse( "domain.part@with.space.at.domain.end2.com " );
      assertIsFalse( "domain.part@with.space.before .point.com" );
      assertIsFalse( "domain.part@with.space.after. point.com" );

      assertIsTrue( "domain.part@with999byte.overflow.com" );
      assertIsTrue( "domain.part@999with.byte.overflow.at.domain.start.com" );
      assertIsTrue( "domain.part@with.byte.overflow.at.domain.end1999.com" );
      assertIsTrue( "domain.part@with.byte.overflow.at.domain.end2.com999" );
      assertIsTrue( "domain.part@with.byte.overflow.before999.point.com" );
      assertIsTrue( "domain.part@with.byte.overflow.after.999point.com" );

      assertIsTrue( "domain.part@withxyzno.hex.number.com" );
      assertIsTrue( "domain.part@xyzwith.no.hex.number.at.domain.start.com" );
      assertIsTrue( "domain.part@with.no.hex.number.at.domain.end1xyz.com" );
      assertIsTrue( "domain.part@with.no.hex.number.at.domain.end2.comxyz" );
      assertIsTrue( "domain.part@with.no.hex.number.beforexyz.point.com" );
      assertIsTrue( "domain.part@with.no.hex.number.after.xyzpoint.com" );

      assertIsFalse( "domain.part@with\"str\"string.com" );
      assertIsFalse( "domain.part@\"str\"with.string.at.domain.start.com" );
      assertIsFalse( "domain.part@with.string.at.domain.end1\"str\".com" );
      assertIsFalse( "domain.part@with.string.at.domain.end2.com\"str\"" );
      assertIsFalse( "domain.part@with.string.before\"str\".point.com" );
      assertIsFalse( "domain.part@with.string.after.\"str\"point.com" );

      assertIsFalse( "domain.part@with(comment)comment.com" );
      assertIsTrue( "domain.part@(comment)with.comment.at.domain.start.com" );
      assertIsFalse( "domain.part@with.comment.at.domain.end1(comment).com" );
      assertIsTrue( "domain.part@with.comment.at.domain.end2.com(comment)" );
      assertIsFalse( "domain.part@with.comment.before(comment).point.com" );
      assertIsFalse( "domain.part@with.comment.after.(comment)point.com" );

      assertIsFalse( ",.local.part.starts.with.comma@domain.com" );
      assertIsFalse( "local.part.ends.with.comma,@domain.com" );
      assertIsFalse( "local.part.with.comma.before,.point@domain.com" );
      assertIsFalse( "local.part.with.comma.after.,point@domain.com" );
      assertIsFalse( "local.part.with.double.comma,,test@domain.com" );
      assertIsFalse( "(comment ,) local.part.with.comment.with.comma@domain.com" );
      assertIsTrue( "\"quote,\".local.part.with.qoute.with.comma@domain.com" );
      assertIsFalse( ",@comma.domain.com" );
      assertIsFalse( ",,,,,,@comma.domain.com" );
      assertIsFalse( ",.,.,.,.,.,@comma.domain.com" );
      assertIsFalse( "name , <pointy.brackets1.with.comma@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.comma@domain.com> name ," );

      assertIsFalse( "domain.part@with,comma.com" );
      assertIsFalse( "domain.part@,with.comma.at.domain.start.com" );
      assertIsFalse( "domain.part@with.comma.at.domain.end1,.com" );
      assertIsFalse( "domain.part@with.comma.at.domain.end2.com," );
      assertIsFalse( "domain.part@with.comma.before,.point.com" );
      assertIsFalse( "domain.part@with.comma.after.,point.com" );

      assertIsFalse( "§.local.part.starts.with.paragraph@domain.com" );
      assertIsFalse( "local.part.ends.with.paragraph§@domain.com" );
      assertIsFalse( "local.part.with.paragraph.before§.point@domain.com" );
      assertIsFalse( "local.part.with.paragraph.after.§point@domain.com" );
      assertIsFalse( "local.part.with.double.paragraph§§test@domain.com" );
      assertIsFalse( "(comment §) local.part.with.comment.with.paragraph@domain.com" );
      assertIsFalse( "\"quote§\".local.part.with.qoute.with.paragraph@domain.com" );
      assertIsFalse( "§@paragraph.domain.com" );
      assertIsFalse( "§§§§§§@paragraph.domain.com" );
      assertIsFalse( "§.§.§.§.§.§@paragraph.domain.com" );
      assertIsFalse( "name § <pointy.brackets1.with.paragraph@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.paragraph@domain.com> name §" );

      assertIsFalse( "domain.part@with§paragraph.com" );
      assertIsFalse( "domain.part@§with.paragraph.at.domain.start.com" );
      assertIsFalse( "domain.part@with.paragraph.at.domain.end1§.com" );
      assertIsFalse( "domain.part@with.paragraph.at.domain.end2.com§" );
      assertIsFalse( "domain.part@with.paragraph.before§.point.com" );
      assertIsFalse( "domain.part@with.paragraph.after.§point.com" );

      assertIsTrue( "'.local.part.starts.with.quote@domain.com" );
      assertIsTrue( "local.part.ends.with.quote'@domain.com" );
      assertIsTrue( "local.part.with.quote.before'.point@domain.com" );
      assertIsTrue( "local.part.with.quote.after.'point@domain.com" );
      assertIsTrue( "local.part.with.double.quote''test@domain.com" );
      assertIsFalse( "(comment ') local.part.with.comment.with.quote@domain.com" );
      assertIsTrue( "\"quote'\".local.part.with.qoute.with.quote@domain.com" );
      assertIsTrue( "'@quote.domain.com" );
      assertIsTrue( "''''''@quote.domain.com" );
      assertIsTrue( "'.'.'.'.'.'@quote.domain.com" );
      assertIsFalse( "name ' <pointy.brackets1.with.quote@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.quote@domain.com> name '" );

      assertIsFalse( "domain.part@with'quote.com" );
      assertIsFalse( "domain.part@'with.quote.at.domain.start.com" );
      assertIsFalse( "domain.part@with.quote.at.domain.end1'.com" );
      assertIsFalse( "domain.part@with.quote.at.domain.end2.com'" );
      assertIsFalse( "domain.part@with.quote.before'.point.com" );
      assertIsFalse( "domain.part@with.quote.after.'point.com" );

      assertIsFalse( "\".local.part.starts.with.double.quote@domain.com" );
      assertIsFalse( "local.part.ends.with.double.quote\"@domain.com" );
      assertIsFalse( "local.part.with.double.quote.before\".point@domain.com" );
      assertIsFalse( "local.part.with.double.quote.after.\"point@domain.com" );
      assertIsFalse( "local.part.with.double.double.quote\"\"test@domain.com" );
      assertIsFalse( "(comment \") local.part.with.comment.with.double.quote@domain.com" );
      assertIsFalse( "\"quote\"\".local.part.with.qoute.with.double.quote@domain.com" );
      assertIsFalse( "\"@double.quote.domain.com" );
      assertIsTrue( "\".\".\".\".\".\"@double.quote.domain.com" );
      assertIsTrue( "name \" <pointy.brackets1.with.double.quote@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.double.quote@domain.com> name \"" );

      assertIsFalse( "domain.part@with\"double.quote.com" );
      assertIsFalse( "domain.part@\"with.double.quote.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.quote.at.domain.end1\".com" );
      assertIsFalse( "domain.part@with.double.quote.at.domain.end2.com\"" );
      assertIsFalse( "domain.part@with.double.quote.before\".point.com" );
      assertIsFalse( "domain.part@with.double.quote.after.\"point.com" );

      assertIsFalse( ")(.local.part.starts.with.false.bracket1@domain.com" );
      assertIsFalse( "local.part.ends.with.false.bracket1)(@domain.com" );
      assertIsFalse( "local.part.with.false.bracket1.before)(.point@domain.com" );
      assertIsFalse( "local.part.with.false.bracket1.after.)(point@domain.com" );
      assertIsFalse( "local.part.with.double.false.bracket1)()(test@domain.com" );
      assertIsFalse( "(comment )() local.part.with.comment.with.false.bracket1@domain.com" );
      assertIsTrue( "\"quote)(\".local.part.with.qoute.with.false.bracket1@domain.com" );
      assertIsFalse( ")(@false.bracket1.domain.com" );
      assertIsFalse( ")()()()()()(@false.bracket1.domain.com" );
      assertIsFalse( ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com" );
      assertIsTrue( "name )( <pointy.brackets1.with.false.bracket1@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.false.bracket1@domain.com> name )(" );

      assertIsFalse( "domain.part@with)(false.bracket1.com" );
      assertIsFalse( "domain.part@)(with.false.bracket1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket1.at.domain.end1)(.com" );
      assertIsFalse( "domain.part@with.false.bracket1.at.domain.end2.com)(" );
      assertIsFalse( "domain.part@with.false.bracket1.before)(.point.com" );
      assertIsFalse( "domain.part@with.false.bracket1.after.)(point.com" );

      assertIsTrue( "}{.local.part.starts.with.false.bracket2@domain.com" );
      assertIsTrue( "local.part.ends.with.false.bracket2}{@domain.com" );
      assertIsTrue( "local.part.with.false.bracket2.before}{.point@domain.com" );
      assertIsTrue( "local.part.with.false.bracket2.after.}{point@domain.com" );
      assertIsTrue( "local.part.with.double.false.bracket2}{}{test@domain.com" );
      assertIsFalse( "(comment }{) local.part.with.comment.with.false.bracket2@domain.com" );
      assertIsTrue( "\"quote}{\".local.part.with.qoute.with.false.bracket2@domain.com" );
      assertIsTrue( "}{@false.bracket2.domain.com" );
      assertIsTrue( "}{}{}{}{}{}{@false.bracket2.domain.com" );
      assertIsTrue( "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com" );
      assertIsFalse( "name }{ <pointy.brackets1.with.false.bracket2@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket2@domain.com> name }{" );

      assertIsFalse( "domain.part@with}{false.bracket2.com" );
      assertIsFalse( "domain.part@}{with.false.bracket2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket2.at.domain.end1}{.com" );
      assertIsFalse( "domain.part@with.false.bracket2.at.domain.end2.com}{" );
      assertIsFalse( "domain.part@with.false.bracket2.before}{.point.com" );
      assertIsFalse( "domain.part@with.false.bracket2.after.}{point.com" );

      assertIsFalse( "][.local.part.starts.with.false.bracket3@domain.com" );
      assertIsFalse( "local.part.ends.with.false.bracket3][@domain.com" );
      assertIsFalse( "local.part.with.false.bracket3.before][.point@domain.com" );
      assertIsFalse( "local.part.with.false.bracket3.after.][point@domain.com" );
      assertIsFalse( "local.part.with.double.false.bracket3][][test@domain.com" );
      assertIsFalse( "(comment ][) local.part.with.comment.with.false.bracket3@domain.com" );
      assertIsTrue( "\"quote][\".local.part.with.qoute.with.false.bracket3@domain.com" );
      assertIsFalse( "][@false.bracket3.domain.com" );
      assertIsFalse( "][][][][][][@false.bracket3.domain.com" );
      assertIsFalse( "][.][.][.][.][.][@false.bracket3.domain.com" );
      assertIsFalse( "name ][ <pointy.brackets1.with.false.bracket3@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket3@domain.com> name ][" );

      assertIsFalse( "domain.part@with][false.bracket3.com" );
      assertIsFalse( "domain.part@][with.false.bracket3.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket3.at.domain.end1][.com" );
      assertIsFalse( "domain.part@with.false.bracket3.at.domain.end2.com][" );
      assertIsFalse( "domain.part@with.false.bracket3.before][.point.com" );
      assertIsFalse( "domain.part@with.false.bracket3.after.][point.com" );

      assertIsFalse( "><.local.part.starts.with.false.bracket4@domain.com" );
      assertIsFalse( "local.part.ends.with.false.bracket4><@domain.com" );
      assertIsFalse( "local.part.with.false.bracket4.before><.point@domain.com" );
      assertIsFalse( "local.part.with.false.bracket4.after.><point@domain.com" );
      assertIsFalse( "local.part.with.double.false.bracket4><><test@domain.com" );
      assertIsFalse( "(comment ><) local.part.with.comment.with.false.bracket4@domain.com" );
      assertIsTrue( "\"quote><\".local.part.with.qoute.with.false.bracket4@domain.com" );
      assertIsFalse( "><@false.bracket4.domain.com" );
      assertIsFalse( "><><><><><><@false.bracket4.domain.com" );
      assertIsFalse( "><.><.><.><.><.><@false.bracket4.domain.com" );
      assertIsFalse( "name >< <pointy.brackets1.with.false.bracket4@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket4@domain.com> name ><" );

      assertIsFalse( "domain.part@with\\slash.com" );
      assertIsFalse( "domain.part@\\with.slash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.slash.at.domain.end1\\.com" );
      assertIsFalse( "domain.part@with.slash.at.domain.end2.com\\" );
      assertIsFalse( "domain.part@with.slash.before\\.point.com" );
      assertIsFalse( "domain.part@with.slash.after.\\point.com" );

      assertIsFalse( "domain.part@with><false.bracket4.com" );
      assertIsFalse( "domain.part@><with.false.bracket4.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket4.at.domain.end1><.com" );
      assertIsFalse( "domain.part@with.false.bracket4.at.domain.end2.com><" );
      assertIsFalse( "domain.part@with.false.bracket4.before><.point.com" );
      assertIsFalse( "domain.part@with.false.bracket4.after.><point.com" );

      assertIsTrue( "domain.part@with.consecutive.underscore__test.com" );
      assertIsFalse( "domain.part@with.consecutive.amp&&test.com" );
      assertIsFalse( "domain.part@with.consecutive.asterisk**test.com" );
      assertIsFalse( "domain.part@with.consecutive.dollar$$test.com" );
      assertIsFalse( "domain.part@with.consecutive.equality==test.com" );
      assertIsFalse( "domain.part@with.consecutive.exclamation!!test.com" );
      assertIsFalse( "domain.part@with.consecutive.question??test.com" );
      assertIsFalse( "domain.part@with.consecutive.grave-accent``test.com" );
      assertIsFalse( "domain.part@with.consecutive.hash##test.com" );
      assertIsFalse( "domain.part@with.consecutive.percentage%%test.com" );
      assertIsFalse( "domain.part@with.consecutive.pipe||test.com" );
      assertIsFalse( "domain.part@with.consecutive.plus++test.com" );
      assertIsFalse( "domain.part@with.consecutive.leftbracket{{test.com" );
      assertIsFalse( "domain.part@with.consecutive.rightbracket}}test.com" );
      assertIsFalse( "domain.part@with.consecutive.leftbracket((test.com" );
      assertIsFalse( "domain.part@with.consecutive.rightbracket))test.com" );
      assertIsFalse( "domain.part@with.consecutive.leftbracket[[test.com" );
      assertIsFalse( "domain.part@with.consecutive.rightbracket]]test.com" );
      assertIsFalse( "domain.part@with.consecutive.lower.than<<test.com" );
      assertIsFalse( "domain.part@with.consecutive.greater.than>>test.com" );
      assertIsFalse( "domain.part@with.consecutive.tilde~~test.com" );
      assertIsFalse( "domain.part@with.consecutive.xor^^test.com" );
      assertIsFalse( "domain.part@with.consecutive.colon::test.com" );
      assertIsFalse( "domain.part@with.consecutive.space  test.com" );
      assertIsFalse( "domain.part@with.consecutive.comma,,test.com" );
      assertIsFalse( "domain.part@with.consecutive.at@@test.com" );
      assertIsFalse( "domain.part@with.consecutive.paragraph§§test.com" );
      assertIsFalse( "domain.part@with.consecutive.double.quote''test.com" );
      assertIsFalse( "domain.part@with.consecutive.double.quote\"\"test.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket()()test.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket{}{}test.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket[][]test.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.bracket<><>test.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket1)()(test.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket2}{}{test.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket3][][test.com" );
      assertIsFalse( "domain.part@with.consecutive.false.bracket4><><test.com" );
      assertIsFalse( "domain.part@with.consecutive.slash\\\\test.com" );
      assertIsFalse( "domain.part@with.consecutive.string\"str\"\"str\"test.com" );

      assertIsTrue( "domain.part.with.comment.with.underscore@(comment _)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.amp@(comment &)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.asterisk@(comment *)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.dollar@(comment $)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.equality@(comment =)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.exclamation@(comment !)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.question@(comment ?)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.grave-accent@(comment `)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.hash@(comment #)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.percentage@(comment %)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.pipe@(comment |)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.plus@(comment +)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.leftbracket@(comment {)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.rightbracket@(comment })domain.com" );
      assertIsFalse( "domain.part.with.comment.with.leftbracket@(comment ()domain.com" );
      assertIsFalse( "domain.part.with.comment.with.rightbracket@(comment ))domain.com" );
      assertIsFalse( "domain.part.with.comment.with.leftbracket@(comment [)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.rightbracket@(comment ])domain.com" );
      assertIsFalse( "domain.part.with.comment.with.lower.than@(comment <)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.greater.than@(comment >)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.tilde@(comment ~)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.xor@(comment ^)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.colon@(comment :)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.space@(comment  )domain.com" );
      assertIsFalse( "domain.part.with.comment.with.comma@(comment ,)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.paragraph@(comment §)domain.com" );
      assertIsTrue( "domain.part.with.comment.with.double.quote@(comment ')domain.com" );
      assertIsFalse( "domain.part.with.comment.with.empty.bracket@(comment ())domain.com" );
      assertIsTrue( "domain.part.with.comment.with.empty.bracket@(comment {})domain.com" );
      assertIsFalse( "domain.part.with.comment.with.empty.bracket@(comment [])domain.com" );
      assertIsFalse( "domain.part.with.comment.with.empty.bracket@(comment <>)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.false.bracket1@(comment )()domain.com" );
      assertIsTrue( "domain.part.with.comment.with.false.bracket2@(comment }{)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.false.bracket3@(comment ][)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.false.bracket4@(comment ><)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.slash@(comment \\)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.string@(comment \"str\")domain.com" );

      assertIsFalse( "domain.part.only.underscore@_.com" );
      assertIsFalse( "domain.part.only.amp@&.com" );
      assertIsFalse( "domain.part.only.asterisk@*.com" );
      assertIsFalse( "domain.part.only.dollar@$.com" );
      assertIsFalse( "domain.part.only.equality@=.com" );
      assertIsFalse( "domain.part.only.exclamation@!.com" );
      assertIsFalse( "domain.part.only.question@?.com" );
      assertIsFalse( "domain.part.only.grave-accent@`.com" );
      assertIsFalse( "domain.part.only.hash@#.com" );
      assertIsFalse( "domain.part.only.percentage@%.com" );
      assertIsFalse( "domain.part.only.pipe@|.com" );
      assertIsFalse( "domain.part.only.plus@+.com" );
      assertIsFalse( "domain.part.only.leftbracket@{.com" );
      assertIsFalse( "domain.part.only.rightbracket@}.com" );
      assertIsFalse( "domain.part.only.leftbracket@(.com" );
      assertIsFalse( "domain.part.only.rightbracket@).com" );
      assertIsFalse( "domain.part.only.leftbracket@[.com" );
      assertIsFalse( "domain.part.only.rightbracket@].com" );
      assertIsFalse( "domain.part.only.lower.than@<.com" );
      assertIsFalse( "domain.part.only.greater.than@>.com" );
      assertIsFalse( "domain.part.only.tilde@~.com" );
      assertIsFalse( "domain.part.only.xor@^.com" );
      assertIsFalse( "domain.part.only.colon@:.com" );
      assertIsFalse( "domain.part.only.space@ .com" );
      assertIsFalse( "domain.part.only.dot@..com" );
      assertIsFalse( "domain.part.only.comma@,.com" );
      assertIsFalse( "domain.part.only.at@@.com" );
      assertIsFalse( "domain.part.only.paragraph@§.com" );
      assertIsFalse( "domain.part.only.double.quote@'.com" );
      assertIsFalse( "domain.part.only.double.quote@\".com" );
      assertIsFalse( "domain.part.only.double.quote@\\\".com" );
      assertIsFalse( "domain.part.only.empty.bracket@().com" );
      assertIsFalse( "domain.part.only.empty.bracket@{}.com" );
      assertIsFalse( "domain.part.only.empty.bracket@[].com" );
      assertIsFalse( "domain.part.only.empty.bracket@<>.com" );
      assertIsFalse( "domain.part.only.false.bracket1@)(.com" );
      assertIsFalse( "domain.part.only.false.bracket2@}{.com" );
      assertIsFalse( "domain.part.only.false.bracket3@][.com" );
      assertIsFalse( "domain.part.only.false.bracket4@><.com" );
      assertIsTrue( "domain.part.only.number0@0.com" );
      assertIsTrue( "domain.part.only.number9@9.com" );
      assertIsFalse( "domain.part.only.slash@\\.com" );
      assertIsTrue( "domain.part.only.byte.overflow@999.com" );
      assertIsTrue( "domain.part.only.no.hex.number@xyz.com" );
      assertIsFalse( "domain.part.only.string@\"str\".com" );
      assertIsFalse( "domain.part.only.comment@(comment).com" );

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
      assertIsFalse( "top.level.domain.only@underscore._" );
      assertIsFalse( "top.level.domain.only@amp.&" );
      assertIsFalse( "top.level.domain.only@asterisk.*" );
      assertIsFalse( "top.level.domain.only@dollar.$" );
      assertIsFalse( "top.level.domain.only@equality.=" );
      assertIsFalse( "top.level.domain.only@exclamation.!" );
      assertIsFalse( "top.level.domain.only@question.?" );
      assertIsFalse( "top.level.domain.only@grave-accent.`" );
      assertIsFalse( "top.level.domain.only@hash.#" );
      assertIsFalse( "top.level.domain.only@percentage.%" );
      assertIsFalse( "top.level.domain.only@pipe.|" );
      assertIsFalse( "top.level.domain.only@plus.+" );
      assertIsFalse( "top.level.domain.only@leftbracket.{" );
      assertIsFalse( "top.level.domain.only@rightbracket.}" );
      assertIsFalse( "top.level.domain.only@leftbracket.(" );
      assertIsFalse( "top.level.domain.only@rightbracket.)" );
      assertIsFalse( "top.level.domain.only@leftbracket.[" );
      assertIsFalse( "top.level.domain.only@rightbracket.]" );
      assertIsFalse( "top.level.domain.only@lower.than.<" );
      assertIsFalse( "top.level.domain.only@greater.than.>" );
      assertIsFalse( "top.level.domain.only@tilde.~" );
      assertIsFalse( "top.level.domain.only@xor.^" );
      assertIsFalse( "top.level.domain.only@colon.:" );
      assertIsFalse( "top.level.domain.only@space. " );
      assertIsFalse( "top.level.domain.only@dot.." );
      assertIsFalse( "top.level.domain.only@comma.," );
      assertIsFalse( "top.level.domain.only@at.@" );
      assertIsFalse( "top.level.domain.only@paragraph.§" );
      assertIsFalse( "top.level.domain.only@double.quote.'" );
      assertIsFalse( "top.level.domain.only@double.quote.\"\"" );
      assertIsFalse( "top.level.domain.only@forward.slash./" );
      assertIsFalse( "top.level.domain.only@hyphen.-" );
      assertIsFalse( "top.level.domain.only@empty.bracket.()" );
      assertIsFalse( "top.level.domain.only@empty.bracket.{}" );
      assertIsFalse( "top.level.domain.only@empty.bracket.[]" );
      assertIsFalse( "top.level.domain.only@empty.bracket.<>" );
      assertIsFalse( "top.level.domain.only@empty.string1.\"\"" );
      assertIsFalse( "top.level.domain.only@empty.string2.a\"\"b" );
      assertIsFalse( "top.level.domain.only@double.empty.string1.\"\"\"\"" );
      assertIsFalse( "top.level.domain.only@double.empty.string2.\"\".\"\"" );
      assertIsFalse( "top.level.domain.only@false.bracket1.)(" );
      assertIsFalse( "top.level.domain.only@false.bracket2.}{" );
      assertIsFalse( "top.level.domain.only@false.bracket3.][" );
      assertIsFalse( "top.level.domain.only@false.bracket4.><" );
      assertIsFalse( "top.level.domain.only@number0.0" );
      assertIsFalse( "top.level.domain.only@number9.9" );
      assertIsFalse( "top.level.domain.only@numbers.123" );
      assertIsFalse( "top.level.domain.only@slash.\\" );
      assertIsFalse( "top.level.domain.only@string.\"str\"" );
      assertIsFalse( "top.level.domain.only@comment.(comment)" );

      wlHeadline( "IP V4" );

      assertIsFalse( "\"\"@[]" );
      assertIsFalse( "\"\"@[1" );
      assertIsFalse( "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" );
      assertIsFalse( "ABC.DEF@[]" );
      assertIsFalse( "ABC.DEF@]" );
      assertIsFalse( "ABC.DEF@[" );
      assertIsFalse( "ABC.DEF@1.2.3.4]" );
      assertIsTrue( "\"    \"@[1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[001.002.003.004]" );
      assertIsTrue( "\"ABC.DEF\"@[127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsFalse( "ABC.DE[F@1.2.3.4]" );
      assertIsFalse( "ABC.DEF@{1.2.3.4}" );
      assertIsFalse( "ABC.DEF@([001.002.003.004])" );
      assertIsFalse( "ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[][][][]" );
      assertIsFalse( "ABC.DEF@[{][})][}][}\\\"]" );
      assertIsFalse( "ABC.DEF@[....]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "[1.2.3.4@[5.6.7.8]" );
      assertIsFalse( "[1.2.3.4][5.6.7.8]@[9.10.11.12]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8][9.10.11.12]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]9.10.11.12]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8][9.10.11.12[" );
      assertIsFalse( "ABC.DEF[@1.2.3.4]" );
      assertIsTrue( "\"[1.2.3.4]\"@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[1.00002.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.456]" );
      assertIsFalse( "ABC.DEF@[..]" );
      assertIsFalse( "ABC.DEF@[.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1]" );
      assertIsFalse( "ABC.DEF@[1.2]" );
      assertIsFalse( "ABC.DEF@[1.2.3]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5.6]" );
      assertIsFalse( "ABC.DEF@[1.2.3.]" );
      assertIsFalse( "ABC.DEF@[1.2.3. ]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4].de" );
      assertIsFalse( "ABC.DE@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4" );
      assertIsFalse( "ABC.DEF@1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.Z]" );
      assertIsFalse( "ABC.DEF@[12.34]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4]ABC" );
      assertIsFalse( "ABC.DEF@[1234.5.6.7]" );
      assertIsFalse( "ABC.DEF@[1.2...3.4]" );

      assertIsFalse( "ABC.DEF@[-1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.-2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.-3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.-4]" );

      assertIsFalse( "ip.v4.with.hyphen@[123.14-5.178.90]" );
      assertIsFalse( "ip.v4.with.hyphen@[123.145-.178.90]" );
      assertIsFalse( "ip.v4.with.hyphen@[123.145.-178.90]" );
      assertIsFalse( "ip.v4.with.hyphen@[123.145.178.90-]" );
      assertIsFalse( "ip.v4.with.hyphen@[123.145.178.90]-" );
      assertIsFalse( "ip.v4.with.hyphen@[-123.145.178.90]" );
      assertIsFalse( "ip.v4.with.hyphen@-[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.underscore@[123.14_5.178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145_.178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145._178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145.178.90_]" );
      assertIsFalse( "ip.v4.with.underscore@[_123.145.178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145.178.90]_" );
      assertIsFalse( "ip.v4.with.underscore@_[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.amp@[123.14&5.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145&.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.&178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.178.90&]" );
      assertIsFalse( "ip.v4.with.amp@[&123.145.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.178.90]&" );
      assertIsFalse( "ip.v4.with.amp@&[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.asterisk@[123.14*5.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145*.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.*178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.178.90*]" );
      assertIsFalse( "ip.v4.with.asterisk@[*123.145.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.178.90]*" );
      assertIsFalse( "ip.v4.with.asterisk@*[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.dollar@[123.14$5.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145$.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.$178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.178.90$]" );
      assertIsFalse( "ip.v4.with.dollar@[$123.145.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.178.90]$" );
      assertIsFalse( "ip.v4.with.dollar@$[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.equality@[123.14=5.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145=.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.=178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.178.90=]" );
      assertIsFalse( "ip.v4.with.equality@[=123.145.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.178.90]=" );
      assertIsFalse( "ip.v4.with.equality@=[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.exclamation@[123.14!5.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145!.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.!178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.178.90!]" );
      assertIsFalse( "ip.v4.with.exclamation@[!123.145.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.178.90]!" );
      assertIsFalse( "ip.v4.with.exclamation@![123.145.178.90]" );

      assertIsFalse( "ip.v4.with.question@[123.14?5.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145?.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.?178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.178.90?]" );
      assertIsFalse( "ip.v4.with.question@[?123.145.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.178.90]?" );
      assertIsFalse( "ip.v4.with.question@?[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.grave-accent@[123.14`5.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145`.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.`178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.178.90`]" );
      assertIsFalse( "ip.v4.with.grave-accent@[`123.145.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.178.90]`" );
      assertIsFalse( "ip.v4.with.grave-accent@`[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.hash@[123.14#5.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145#.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.#178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.178.90#]" );
      assertIsFalse( "ip.v4.with.hash@[#123.145.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.178.90]#" );
      assertIsFalse( "ip.v4.with.hash@#[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.percentage@[123.14%5.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145%.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.%178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.178.90%]" );
      assertIsFalse( "ip.v4.with.percentage@[%123.145.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.178.90]%" );
      assertIsFalse( "ip.v4.with.percentage@%[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.pipe@[123.14|5.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145|.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.|178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.178.90|]" );
      assertIsFalse( "ip.v4.with.pipe@[|123.145.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.178.90]|" );
      assertIsFalse( "ip.v4.with.pipe@|[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.plus@[123.14+5.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145+.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.+178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.178.90+]" );
      assertIsFalse( "ip.v4.with.plus@[+123.145.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.178.90]+" );
      assertIsFalse( "ip.v4.with.plus@+[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14{5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145{.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.{178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90{]" );
      assertIsFalse( "ip.v4.with.leftbracket@[{123.145.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90]{" );
      assertIsFalse( "ip.v4.with.leftbracket@{[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14}5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145}.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.}178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90}]" );
      assertIsFalse( "ip.v4.with.rightbracket@[}123.145.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]}" );
      assertIsFalse( "ip.v4.with.rightbracket@}[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14(5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145(.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.(178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90(]" );
      assertIsFalse( "ip.v4.with.leftbracket@[(123.145.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90](" );
      assertIsFalse( "ip.v4.with.leftbracket@([123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14)5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145).178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.)178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90)]" );
      assertIsFalse( "ip.v4.with.rightbracket@[)123.145.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90])" );
      assertIsFalse( "ip.v4.with.rightbracket@)[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14[5.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.145[.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.[178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90[]" );
      assertIsFalse( "ip.v4.with.leftbracket@[[123.145.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90][" );
      assertIsFalse( "ip.v4.with.leftbracket@[[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14]5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145].178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.]178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]]" );
      assertIsFalse( "ip.v4.with.rightbracket@[]123.145.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]]" );
      assertIsFalse( "ip.v4.with.rightbracket@][123.145.178.90]" );

      assertIsFalse( "ip.v4.with.lower.than@[123.14<5.178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145<.178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145.<178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145.178.90<]" );
      assertIsFalse( "ip.v4.with.lower.than@[<123.145.178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145.178.90]<" );
      assertIsFalse( "ip.v4.with.lower.than@<[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.greater.than@[123.14>5.178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145>.178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145.>178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145.178.90>]" );
      assertIsFalse( "ip.v4.with.greater.than@[>123.145.178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145.178.90]>" );
      assertIsFalse( "ip.v4.with.greater.than@>[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.tilde@[123.14~5.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145~.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.~178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.178.90~]" );

      assertIsFalse( "ip.v4.with.tilde@[~123.145.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.178.90]~" );
      assertIsFalse( "ip.v4.with.tilde@~[123.145.178.90]" );

      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]" );
      assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-" );
      assertIsFalse( "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v4.with.xor@[123.14^5.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145^.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.^178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.178.90^]" );
      assertIsFalse( "ip.v4.with.xor@[^123.145.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.178.90]^" );
      assertIsFalse( "ip.v4.with.xor@^[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.colon@[123.14:5.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145:.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.:178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.178.90:]" );
      assertIsFalse( "ip.v4.with.colon@[:123.145.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.178.90]:" );
      assertIsFalse( "ip.v4.with.colon@:[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.space@[123.14 5.178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145 .178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145. 178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145.178.90 ]" );
      assertIsFalse( "ip.v4.with.space@[ 123.145.178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145.178.90] " );
      assertIsFalse( "ip.v4.with.space@ [123.145.178.90]" );

      assertIsFalse( "ip.v4.with.dot@[123.14.5.178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145..178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145..178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145.178.90.]" );
      assertIsFalse( "ip.v4.with.dot@[.123.145.178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145.178.90]." );
      assertIsFalse( "ip.v4.with.dot@.[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.comma@[123.14,5.178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145,.178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145.,178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145.178.90,]" );
      assertIsFalse( "ip.v4.with.comma@[,123.145.178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145.178.90]," );
      assertIsFalse( "ip.v4.with.comma@,[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.at@[123.14@5.178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145@.178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145.@178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145.178.90@]" );
      assertIsFalse( "ip.v4.with.at@[@123.145.178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145.178.90]@" );
      assertIsFalse( "ip.v4.with.at@@[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.paragraph@[123.14§5.178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145§.178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145.§178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145.178.90§]" );
      assertIsFalse( "ip.v4.with.paragraph@[§123.145.178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145.178.90]§" );
      assertIsFalse( "ip.v4.with.paragraph@§[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.double.quote@[123.14'5.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145'.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.'178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90']" );
      assertIsFalse( "ip.v4.with.double.quote@['123.145.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90]'" );
      assertIsFalse( "ip.v4.with.double.quote@'[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.double.quote@[123.14\"5.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145\".178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.\"178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90\"]" );
      assertIsFalse( "ip.v4.with.double.quote@[\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90]\"" );
      assertIsFalse( "ip.v4.with.double.quote@\"[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14()5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145().178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.()178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90()]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[()123.145.178.90]" );
      assertIsTrue( "ip.v4.with.empty.bracket@[123.145.178.90]()" );
      assertIsTrue( "ip.v4.with.empty.bracket@()[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14{}5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145{}.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.{}178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90{}]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[{}123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90]{}" );
      assertIsFalse( "ip.v4.with.empty.bracket@{}[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14[]5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145[].178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.[]178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90[]]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[[]123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90][]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[][123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14<>5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145<>.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.<>178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90<>]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[<>123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90]<>" );
      assertIsFalse( "ip.v4.with.empty.bracket@<>[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket1@[123.14)(5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145)(.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145.)(178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145.178.90)(]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[)(123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145.178.90])(" );
      assertIsFalse( "ip.v4.with.false.bracket1@)([123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket2@[123.14}{5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145}{.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145.}{178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145.178.90}{]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[}{123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145.178.90]}{" );
      assertIsFalse( "ip.v4.with.false.bracket2@}{[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket3@[123.14][5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145][.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145.][178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145.178.90][]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[][123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145.178.90]][" );
      assertIsFalse( "ip.v4.with.false.bracket3@][[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket4@[123.14><5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145><.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145.><178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145.178.90><]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[><123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145.178.90]><" );
      assertIsFalse( "ip.v4.with.false.bracket4@><[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.number0@[123.1405.178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.1450.178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.145.0178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.145.178.900]" );
      assertIsFalse( "ip.v4.with.number0@[0123.145.178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.145.178.90]0" );
      assertIsFalse( "ip.v4.with.number0@0[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.number9@[123.1495.178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.1459.178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.145.9178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.145.178.909]" );
      assertIsFalse( "ip.v4.with.number9@[9123.145.178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.145.178.90]9" );
      assertIsFalse( "ip.v4.with.number9@9[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.numbers@[123.1401234567895.178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.1450123456789.178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.145.0123456789178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.145.178.900123456789]" );
      assertIsFalse( "ip.v4.with.numbers@[0123456789123.145.178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.145.178.90]0123456789" );
      assertIsFalse( "ip.v4.with.numbers@0123456789[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.slash@[123.14\\5.178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145\\.178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145.\\178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145.178.90\\]" );
      assertIsFalse( "ip.v4.with.slash@[\\123.145.178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145.178.90]\\" );
      assertIsFalse( "ip.v4.with.slash@\\[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.byte.overflow@[123.149995.178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145999.178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145.999178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145.178.90999]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145.178.90]999" );
      assertIsFalse( "ip.v4.with.byte.overflow@[999123.145.178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@999[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.no.hex.number@[123.14xyz5.178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145xyz.178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145.xyz178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145.178.90xyz]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145.178.90]xyz" );
      assertIsFalse( "ip.v4.with.no.hex.number@[xyz123.145.178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@xyz[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.string@[123.14\"str\"5.178.90]" );
      assertIsFalse( "ip.v4.with.string@[123.145\"str\".178.90]" );
      assertIsFalse( "ip.v4.with.string@[123.145.\"str\"178.90]" );
      assertIsFalse( "ip.v4.with.string@[123.145.178.90\"str\"]" );
      assertIsFalse( "ip.v4.with.string@[123.145.178.90]\"str\"" );
      assertIsFalse( "ip.v4.with.string@[\"str\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.string@\"str\"[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.comment@[123.14(comment)5.178.90]" );
      assertIsFalse( "ip.v4.with.comment@[123.145(comment).178.90]" );
      assertIsFalse( "ip.v4.with.comment@[123.145.(comment)178.90]" );
      assertIsFalse( "ip.v4.with.comment@[123.145.178.90(comment)]" );
      assertIsTrue( "ip.v4.with.comment@[123.145.178.90](comment)" );
      assertIsFalse( "ip.v4.with.comment@[(comment)123.145.178.90]" );
      assertIsTrue( "ip.v4.with.comment@(comment)[123.145.178.90]" );

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

      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]" );
      assertIsFalse( "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_" );

      assertIsFalse( "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]" );
      assertIsFalse( "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&" );

      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]" );
      assertIsFalse( "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*" );

      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]" );
      assertIsFalse( "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$" );

      assertIsFalse( "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]" );
      assertIsFalse( "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]=" );

      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]" );
      assertIsFalse( "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!" );

      assertIsFalse( "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]" );
      assertIsFalse( "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?" );

      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]" );
      assertIsFalse( "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`" );

      assertIsFalse( "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]" );
      assertIsFalse( "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#" );

      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]" );
      assertIsFalse( "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%" );

      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]" );
      assertIsFalse( "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|" );

      assertIsFalse( "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]" );
      assertIsFalse( "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]" );
      assertIsFalse( "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.999]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]" );
      assertIsFalse( "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]" );
      assertIsFalse( "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7](" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]" );
      assertIsFalse( "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]" );
      assertIsFalse( "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7][" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );
      assertIsFalse( "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.999]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );

      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]" );
      assertIsFalse( "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<" );

      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]" );
      assertIsFalse( "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>" );

      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]" );
      assertIsFalse( "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~" );

      assertIsFalse( "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]" );
      assertIsFalse( "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^" );

      assertIsTrue( "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]" );
      assertIsFalse( "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:" );

      assertIsFalse( "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]" );
      assertIsFalse( "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] " );

      assertIsFalse( "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]" );
      assertIsFalse( "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]." );

      assertIsFalse( "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]" );
      assertIsFalse( "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7]," );

      assertIsFalse( "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]" );
      assertIsFalse( "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@" );

      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]" );
      assertIsFalse( "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§" );

      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']" );
      assertIsFalse( "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'" );

      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]" );
      assertIsFalse( "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\"" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]" );
      assertIsTrue( "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.999]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]" );
      assertIsFalse( "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]" );
      assertIsFalse( "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>" );

      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]" );
      assertIsFalse( "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])(" );

      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]" );
      assertIsFalse( "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{" );

      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]" );
      assertIsFalse( "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]][" );

      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]" );
      assertIsFalse( "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><" );

      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]" );
      assertIsFalse( "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789" );

      assertIsFalse( "ip.v6.with.slash@[IPv6:1:2\\2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22\\:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:\\3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\\]" );
      assertIsFalse( "ip.v6.with.slash@\\[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\\" );

      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:29993:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999" );
      assertIsFalse( "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz" );
      assertIsFalse( "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]" );

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

      assertIsTrue( "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );

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
      assertIsTrue( "ABC(DEF)@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.JKL(MNO)" );
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
      assertIsTrue( "\"Herr \\\"Kaiser\\\" Franz Beckenbauer\" <local-part@domain-part>" );
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

      /*
       * https://github.com/dotnet/docs/issues/6620
       */
      assertIsFalse( "" ); //(empty string) results in ArgumentException
      assertIsFalse( " " ); //(string that contains only white spaces) results in FormatException
      assertIsFalse( " jkt@gmail.com" ); //(white spaces at the start or at the end) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "jkt@gmail.com " ); //(white spaces at the start or at the end) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "jkt@ gmail.com" ); //(white space is directly after @) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "jkt@g mail.com" ); //(any not trailing white space after @ except the above case) resuls in FormatException
      assertIsFalse( "jkt @gmail.com" ); //(white space is in front of @) results in "jkt@gmail.com" (no white spaces)
      assertIsFalse( "j kt@gmail.com" ); //(white space is before @, inside the username) results in "kt@gmail.com" (the part after a white space is taken as the mail address)

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

      /*
       * https://www.abstractapi.com/guides/java-email-validation
       */
      assertIsTrue( "\"test123\"@gmail.com" );
      assertIsTrue( "test123@gmail.comcomco" );
      assertIsTrue( "test123@gmail.c" );
      assertIsTrue( "test1&23@gmail.com" );
      assertIsTrue( "test123@gmail.com" );
      assertIsFalse( "test123@gmail..com" );
      assertIsFalse( ".test123@gmail.com" );
      assertIsFalse( "test123@gmail.com." );
      assertIsFalse( "test1Ὠ23@gmail.com" );

      /*
       * https://www.javatpoint.com/java-email-validation
       */
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

      /*
       * https://java2blog.com/validate-email-address-in-java/
       */
      assertIsTrue( "admin@java2blog.com" );
      assertIsFalse( "@java2blog.com" );
      assertIsTrue( "arpit.mandliya@java2blog.com" );

      /*
       * https://www.tutorialspoint.com/javaexamples/regular_email.htm
       */
      assertIsTrue( "sairamkrishna@tutorialspoint.com" );
      assertIsTrue( "kittuprasad700@gmail.com" );
      assertIsFalse( "sairamkrishna_mammahe%google-india.com" );
      assertIsTrue( "sairam.krishna@gmail-indai.com" );
      assertIsTrue( "sai#@youtube.co.in" );
      assertIsFalse( "kittu@domaincom" );
      assertIsFalse( "kittu#gmail.com" );
      assertIsFalse( "@pindom.com" );

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
      assertIsFalse( "first(abc.def).last@iana.orgin" );
      assertIsFalse( "first(a\"bc.def).last@iana.orgin" );
      assertIsFalse( "first.(\")middle.last(\")@iana.orgin" );
      assertIsFalse( "first(abc\\(def)@iana.orgin" );
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
      assertIsFalse( "first\\@last@iana.org" );
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
      assertIsTrue( "\"first\"last\"@test.org" );
      assertIsTrue( "much.\"more\\ unusual\"@example.com" );
      assertIsTrue( "\"first\\last\"@test.org" );
      assertIsTrue( "\"Abc\\@def\"@test.org" );
      assertIsTrue( "\"Fred\\ Bloggs\"@test.org" );
      assertIsTrue( "\"Joe.\\Blow\"@test.org" );
      assertIsTrue( "\"Abc@def\"@test.org" );
      assertIsTrue( "\"Fred Bloggs\"@test.org" );
      assertIsTrue( "\"Doug \"Ace\" L.\"@test.org" );
      assertIsTrue( "\"[[ test ]]\"@test.org" );
      assertIsTrue( "\"test.test\"@test.org" );
      assertIsTrue( "test.\"test\"@test.org" );
      assertIsTrue( "\"test@test\"@test.org" );
      assertIsTrue( "\"test\test\"@test.org" );
      assertIsTrue( "\"first\".\"last\"@test.org" );
      assertIsTrue( "\"first\".middle.\"last\"@test.org" );
      assertIsTrue( "\"first\".last@test.org" );
      assertIsTrue( "first.\"last\"@test.org" );
      assertIsTrue( "\"first\".\"middle\".\"last\"@test.org" );
      assertIsTrue( "\"first.middle\".\"last\"@test.org" );
      assertIsTrue( "\"first.middle.last\"@test.org" );
      assertIsTrue( "\"first..last\"@test.org" );
      assertIsTrue( "\"Unicode NULL \"@char.com" );
      assertIsTrue( "\"test\\blah\"@test.org" );
      assertIsTrue( "\"test\blah\"@test.org" );
      assertIsTrue( "\"test\"blah\"@test.org" );
      assertIsTrue( "\"first\\\"last\"@test.org" );
      assertIsTrue( "\"Test \\\"Fail\\\" Ing\"@test.org" );
      assertIsFalse( "\"Test \"Fail\" Ing\"@test.org" );
      assertIsTrue( "\"test blah\"@test.org" );
      assertIsTrue( "first.last@test.org" );
      assertIsFalse( "jdoe@machine(comment).example" );
      assertIsTrue( "first.\"\".last@test.org" );
      assertIsTrue( "\"\"@test.org" );
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
      assertIsTrue( "cal(foo\\@bar)@iamcal.com" );
      assertIsTrue( "(comment)test@test.org" );
      assertIsFalse( "(foo)cal(bar)@(baz)iamcal.com(quux)" );
      assertIsTrue( "cal(foo\\)bar)@iamcal.com" );
      assertIsTrue( "cal(woo(yay)hoopla)@iamcal.com" );
      assertIsTrue( "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" );
      assertIsFalse( "pete(his account)@silly.test(his host)" );
      assertIsTrue( "first(abc\\(def)@test.org" );
      assertIsTrue( "a(a(b(c)d(e(f))g)h(i)j)@test.org" );
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
      assertIsTrue( "cal@iamcal(woo).(yay)com" );
      assertIsTrue( "first(abc.def).last@test.org" );
      assertIsTrue( "first(a\"bc.def).last@test.org" );
      assertIsTrue( "first.(\")middle.last(\")@test.org" );
      assertIsTrue( "first().last@test.org" );
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

      wlHeadline( "some more Testcases" );

      assertIsFalse( "\"\".local.part.starts.with.empty.string1@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.string1\"\"@domain.com" );
      assertIsFalse( "local.part.with.empty.string1\"\"character@domain.com" );
      assertIsFalse( "local.part.with.empty.string1.before\"\".point@domain.com" );
      assertIsFalse( "local.part.with.empty.string1.after.\"\"point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1\"\"\"\"test@domain.com" );
      assertIsFalse( "(comment \"\") local.part.with.comment.with.empty.string1@domain.com" );
      assertIsFalse( "\"quote\"\"\".local.part.with.qoute.with.empty.string1@domain.com" );
      assertIsFalse( "\"\"@empty.string1.domain.com" );
      assertIsFalse( "\"\"\"\"\"\"\"\"\"\"\"\"@empty.string1.domain.com" );
      assertIsFalse( "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com" );
      assertIsTrue( "name \"\" <pointy.brackets1.with.empty.string1@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.string1@domain.com> name \"\"" );

      assertIsFalse( "domain.part@with\"\"empty.string1.com" );
      assertIsFalse( "domain.part@\"\"with.empty.string1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.string1.at.domain.end1\"\".com" );
      assertIsFalse( "domain.part@with.empty.string1.at.domain.end2.com\"\"" );
      assertIsFalse( "domain.part@with.empty.string1.before\"\".point.com" );
      assertIsFalse( "domain.part@with.empty.string1.after.\"\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.string1\"\"\"\"test.com" );
      assertIsFalse( "domain.part.with.comment.with.empty.string1@(comment \"\")domain.com" );
      assertIsFalse( "domain.part.only.empty.string1@\"\".com" );

      assertIsFalse( "ip.v4.with.empty.string1@[123.14\"\"5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.string1@[123.145\"\".178.90]" );
      assertIsFalse( "ip.v4.with.empty.string1@[123.145.\"\"178.90]" );
      assertIsFalse( "ip.v4.with.empty.string1@[123.145.178.90\"\"]" );
      assertIsFalse( "ip.v4.with.empty.string1@[123.145.178.90]\"\"" );
      assertIsFalse( "ip.v4.with.empty.string1@[\"\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.string1@\"\"[123.145.178.90]" );

      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]" );
      assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"" );
      assertIsFalse( "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "a\"\"b.local.part.starts.with.empty.string2@domain.com" );
      assertIsFalse( "local.part.ends.with.empty.string2a\"\"b@domain.com" );
      assertIsFalse( "local.part.with.empty.string2a\"\"bcharacter@domain.com" );
      assertIsFalse( "local.part.with.empty.string2.beforea\"\"b.point@domain.com" );
      assertIsFalse( "local.part.with.empty.string2.after.a\"\"bpoint@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com" );
      assertIsFalse( "(comment a\"\"b) local.part.with.comment.with.empty.string2@domain.com" );
      assertIsFalse( "\"quotea\"\"b\".local.part.with.qoute.with.empty.string2@domain.com" );
      assertIsFalse( "a\"\"b@empty.string2.domain.com" );
      assertIsFalse( "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@empty.string2.domain.com" );
      assertIsFalse( "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com" );
      assertIsTrue( "name a\"\"b <pointy.brackets1.with.empty.string2@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.string2@domain.com> name a\"\"b" );

      assertIsFalse( "domain.part@witha\"\"bempty.string2.com" );
      assertIsFalse( "domain.part@a\"\"bwith.empty.string2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.empty.string2.at.domain.end1a\"\"b.com" );
      assertIsFalse( "domain.part@with.empty.string2.at.domain.end2.coma\"\"b" );
      assertIsFalse( "domain.part@with.empty.string2.beforea\"\"b.point.com" );
      assertIsFalse( "domain.part@with.empty.string2.after.a\"\"bpoint.com" );
      assertIsFalse( "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com" );
      assertIsFalse( "domain.part.with.comment.with.empty.string2@(comment a\"\"b)domain.com" );
      assertIsFalse( "domain.part.only.empty.string2@a\"\"b.com" );

      assertIsFalse( "ip.v4.with.empty.string2@[123.14a\"\"b5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.string2@[123.145a\"\"b.178.90]" );
      assertIsFalse( "ip.v4.with.empty.string2@[123.145.a\"\"b178.90]" );
      assertIsFalse( "ip.v4.with.empty.string2@[123.145.178.90a\"\"b]" );
      assertIsFalse( "ip.v4.with.empty.string2@[123.145.178.90]a\"\"b" );
      assertIsFalse( "ip.v4.with.empty.string2@[a\"\"b123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.string2@a\"\"b[123.145.178.90]" );

      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]" );
      assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b" );
      assertIsFalse( "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com" );
      assertIsFalse( "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1\"\"\"\"character@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com" );
      assertIsFalse( "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" );
      assertIsFalse( "(comment \"\"\"\") local.part.with.comment.with.double.empty.string1@domain.com" );
      assertIsFalse( "\"quote\"\"\"\"\".local.part.with.qoute.with.double.empty.string1@domain.com" );
      assertIsFalse( "\"\"\"\"@double.empty.string1.domain.com" );
      assertIsFalse( "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@double.empty.string1.domain.com" );
      assertIsFalse( "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" );
      assertIsTrue( "name \"\"\"\" <pointy.brackets1.with.double.empty.string1@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.double.empty.string1@domain.com> name \"\"\"\"" );

      assertIsFalse( "domain.part@with\"\"\"\"double.empty.string1.com" );
      assertIsFalse( "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com" );
      assertIsFalse( "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\"" );
      assertIsFalse( "domain.part@with.double.empty.string1.before\"\"\"\".point.com" );
      assertIsFalse( "domain.part@with.double.empty.string1.after.\"\"\"\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" );
      assertIsFalse( "domain.part.with.comment.with.double.empty.string1@(comment \"\"\"\")domain.com" );
      assertIsFalse( "domain.part.only.double.empty.string1@\"\"\"\".com" );

      assertIsFalse( "ip.v4.with.double.empty.string1@[123.14\"\"\"\"5.178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string1@[123.145\"\"\"\".178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string1@[123.145.\"\"\"\"178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string1@[123.145.178.90\"\"\"\"]" );
      assertIsFalse( "ip.v4.with.double.empty.string1@[123.145.178.90]\"\"\"\"" );
      assertIsFalse( "ip.v4.with.double.empty.string1@[\"\"\"\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string1@\"\"\"\"[123.145.178.90]" );

      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\"" );
      assertIsFalse( "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com" );
      assertIsFalse( "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2\"\".\"\"character@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com" );
      assertIsFalse( "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com" );
      assertIsFalse( "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" );
      assertIsFalse( "(comment \"\".\"\") local.part.with.comment.with.double.empty.string2@domain.com" );
      assertIsFalse( "\"quote\"\".\"\"\".local.part.with.qoute.with.double.empty.string2@domain.com" );
      assertIsFalse( "\"\".\"\"@double.empty.string2.domain.com" );
      assertIsFalse( "\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"@double.empty.string2.domain.com" );
      assertIsFalse( "\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" );
      assertIsTrue( "name \"\".\"\" <pointy.brackets1.with.double.empty.string2@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.double.empty.string2@domain.com> name \"\".\"\"" );

      assertIsFalse( "domain.part@with\"\".\"\"double.empty.string2.com" );
      assertIsFalse( "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com" );
      assertIsFalse( "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\"" );
      assertIsFalse( "domain.part@with.double.empty.string2.before\"\".\"\".point.com" );
      assertIsFalse( "domain.part@with.double.empty.string2.after.\"\".\"\"point.com" );
      assertIsFalse( "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" );
      assertIsFalse( "domain.part.with.comment.with.double.empty.string2@(comment \"\".\"\")domain.com" );
      assertIsFalse( "domain.part.only.double.empty.string2@\"\".\"\".com" );

      assertIsFalse( "ip.v4.with.double.empty.string2@[123.14\"\".\"\"5.178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string2@[123.145\"\".\"\".178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string2@[123.145.\"\".\"\"178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string2@[123.145.178.90\"\".\"\"]" );
      assertIsFalse( "ip.v4.with.double.empty.string2@[123.145.178.90]\"\".\"\"" );
      assertIsFalse( "ip.v4.with.double.empty.string2@[\"\".\"\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.double.empty.string2@\"\".\"\"[123.145.178.90]" );

      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\"" );
      assertIsFalse( "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]" );

      assertIsTrue( "/.local.part.starts.with.forward.slash@domain.com" );
      assertIsTrue( "local.part.ends.with.forward.slash/@domain.com" );
      assertIsTrue( "local.part.with.forward.slash/character@domain.com" );
      assertIsTrue( "local.part.with.forward.slash.before/.point@domain.com" );
      assertIsTrue( "local.part.with.forward.slash.after./point@domain.com" );
      assertIsTrue( "local.part.with.double.forward.slash//test@domain.com" );
      assertIsTrue( "(comment /) local.part.with.comment.with.forward.slash@domain.com" );
      assertIsTrue( "\"quote/\".local.part.with.qoute.with.forward.slash@domain.com" );
      assertIsTrue( "/@forward.slash.domain.com" );
      assertIsTrue( "//////@forward.slash.domain.com" );
      assertIsTrue( "/./././././@forward.slash.domain.com" );
      assertIsFalse( "name / <pointy.brackets1.with.forward.slash@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.forward.slash@domain.com> name /" );

      assertIsFalse( "domain.part@with/forward.slash.com" );
      assertIsFalse( "domain.part@with//double.forward.slash.com" );
      assertIsFalse( "domain.part@/with.forward.slash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.forward.slash.at.domain.end1/.com" );
      assertIsFalse( "domain.part@with.forward.slash.at.domain.end2.com/" );
      assertIsFalse( "domain.part@with.forward.slash.before/.point.com" );
      assertIsFalse( "domain.part@with.forward.slash.after./point.com" );
      assertIsFalse( "domain.part@with.consecutive.forward.slash//test.com" );
      assertIsTrue( "domain.part.with.comment.with.forward.slash@(comment /)domain.com" );
      assertIsFalse( "domain.part.only.forward.slash@/.com" );

      assertIsFalse( "ip.v4.with.forward.slash@[123.14/5.178.90]" );
      assertIsFalse( "ip.v4.with.forward.slash@[123.145/.178.90]" );
      assertIsFalse( "ip.v4.with.forward.slash@[123.145./178.90]" );
      assertIsFalse( "ip.v4.with.forward.slash@[123.145.178.90/]" );
      assertIsFalse( "ip.v4.with.forward.slash@[123.145.178.90]/" );
      assertIsFalse( "ip.v4.with.forward.slash@[/123.145.178.90]" );
      assertIsFalse( "ip.v4.with.forward.slash@/[123.145.178.90]" );

      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]" );
      assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/" );
      assertIsFalse( "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " );
      assertIsTrue( "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" );

      wlHeadline( "unsupported" );

      assertIsTrue( "Loïc.Accentué@voilà.fr" );
      assertIsTrue( "Ärger.Öde@Übel.de" );
      assertIsTrue( "Smørrebrød@danmark.dk" );

      assertIsTrue( "ip6.without.brackets@1:2:3:4:5:6:7:8" );

      assertIsTrue( "(space after comment) john.smith@example.com" );

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
    wl( "---- " + pString + " ----------------------------------------------------------------------------------------------------" );
    wl( "" );
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
//  generateTest( ".", "dot" );
//  generateTest( "..", "double.dot" );

      generateTest( "&", "amp" );
  generateTest( "*", "asterisk" );

    //    generateTest( "_", "underscore" );
//    generateTest( "$", "dollar" );
//    generateTest( "=", "equality" );
//    generateTest( "!", "exclamation" );
//    generateTest( "?", "question" );
//    generateTest( "`", "grave-accent" );
//    generateTest( "#", "hash" );
//    generateTest( "%", "percentage" );
//    generateTest( "|", "pipe" );
//    generateTest( "+", "plus" );
//    generateTest( "{", "leftbracket" );
//    generateTest( "}", "rightbracket" );
//    generateTest( "(", "leftbracket" );
//    generateTest( ")", "rightbracket" );
//    generateTest( "[", "leftbracket" );
//    generateTest( "]", "rightbracket" );
//    generateTest( "<", "lower.than" );
//    generateTest( ">", "greater.than" );
//    generateTest( "~", "tilde" );
//    generateTest( "^", "xor" );
//    generateTest( ":", "colon" );
//    generateTest( " ", "space" );
//    generateTest( ",", "comma" );
//    generateTest( "@", "at" );
//    generateTest( "§", "paragraph" );
//    generateTest( "'", "double.quote" );
//    generateTest( "\"", "double.quote" );
//    generateTest( "/", "forward.slash" );
//    generateTest( "-", "hyphen" );
//
//    generateTest( "()", "empty.bracket" );
//    generateTest( "{}", "empty.bracket" );
//    generateTest( "[]", "empty.bracket" );
//    generateTest( "<>", "empty.bracket" );
//    generateTest( "\\\"\\\"", "empty.string1" );
//    generateTest( "a\\\"\\\"b", "empty.string2" );
//    generateTest( "\\\"\\\"\\\"\\\"", "double.empty.string1" );
//    generateTest( "\\\"\\\".\\\"\\\"", "double.empty.string2" );
//
//    generateTest( ")(", "false.bracket1" );
//    generateTest( "}{", "false.bracket2" );
//    generateTest( "][", "false.bracket3" );
//    generateTest( "><", "false.bracket4" );
//
//    generateTest( "0", "number0" );
//    generateTest( "9", "number9" );
//
//    generateTest( "0123456789", "numbers" );
//
//    generateTest( "\\\\", "slash" );
//
//    generateTest( "999", "byte.overflow" );
//    generateTest( "xyz", "no.hex.number" );
//
//    generateTest( "\\\"str\\\"", "string" );
//    generateTest( "(comment)", "comment" );

  }

  private static void generateTest( String pCharacter, String pName )
  {
    wl( "" );
    wl( "      assertIsFalse( \"" + pCharacter + ".local.part.starts.with." + pName + "@domain.com\" );" );
    wl( "      assertIsFalse( \"local.part.ends.with." + pName + pCharacter + "@domain.com\" );" );
    wl( "      assertIsFalse( \"local.part.with." + pName + pCharacter + "character@domain.com\" );" );
    wl( "      assertIsFalse( \"local.part.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
    wl( "      assertIsFalse( \"local.part.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
    wl( "      assertIsFalse( \"local.part.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
    wl( "      assertIsFalse( \"(comment " + pCharacter + ") local.part.with." + pName + ".in.comment@domain.com\" );" );
    wl( "      assertIsTrue( \"\\\"string" + pCharacter + "\\\".local.part.with." + pName + ".in.String@domain.com\" );" );
    wl( "      assertIsFalse( \"\\\"string\\\\" + pCharacter + "\\\".local.part.with.escaped." + pName + ".in.String@domain.com\" );" );
    wl( "      assertIsFalse( \"" + pCharacter + "@local.part.only." + pName + ".domain.com\" );" );
    wl( "      assertIsFalse( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@local.part.only.consecutive." + pName + ".domain.com\" );" );
    wl( "      assertIsFalse( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
    wl( "      assertIsFalse( \"name " + pCharacter + " <pointy.brackets1.with." + pName + ".in.display.name@domain.com>\" );" );
    wl( "      assertIsFalse( \"<pointy.brackets2.with." + pName + ".in.display.name@domain.com> name " + pCharacter + "\" );" );
    wl( "" );
    wl( "      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
    wl( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with.consecutive." + pName + "" + pCharacter + "" + pCharacter + "test.com\" );" );
    wl( "      assertIsTrue( \"domain.part.with." + pName + ".in.comment@(comment " + pCharacter + ")domain.com\" );" );

    wl( "      assertIsFalse( \"domain.part.only." + pName + "@" + pCharacter + ".com\" );" );

    wl( "      assertIsFalse( \"top.level.domain.only@" + pName + "." + pCharacter + "\" );" );
//
//    wl( "" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.14" + pCharacter + "5.178.90]\" );" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145" + pCharacter + ".178.90]\" );" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145." + pCharacter + "178.90]\" );" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90" + pCharacter + "]\" );" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90]" + pCharacter + "\" );" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[" + pCharacter + "123.145.178.90]\" );" );
//    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@" + pCharacter + "[123.145.178.90]\" );" );
//    wl( "" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:2" + pCharacter + "2:3:4:5:6:7]\" );" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22" + pCharacter + ":3:4:5:6:7]\" );" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:" + pCharacter + "3:4:5:6:7]\" );" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7" + pCharacter + "]\" );" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7]" + pCharacter + "\" );" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@" + pCharacter + "[IPv6:1:22:3:4:5:6:7]\" );" );
//    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[" + pCharacter + "IPv6:1:22:3:4:5:6:7]\" );" );

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
