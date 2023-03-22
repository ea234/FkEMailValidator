package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestClassAssertTrueFalse
{
  private static final int    TEST_B_TEST_NR                = 5;

  private static int          LAUFENDE_ZAHL                 = 0;

  private static int          COUNT_ASSERT_IS_TRUE          = 0;

  private static int          COUNT_ASSERT_IS_FALSE         = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_ERROR          = 0;

  private static int          T_RESULT_COUNT_ERROR          = 0;

  private static int          BREITE_SPALTE_EMAIL_AUSGABE   = 70;

  private static boolean      KNZ_LOG_AUSGABE               = true;

  private static boolean      TEST_B_KNZ_AKTIV              = false;

  private static StringBuffer m_str_buffer                  = null;

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
     *     1 - assertIsTrue  "n@d.td"                                                               =   0 =  OK 
     *     2 - assertIsTrue  "1@2.td"                                                               =   0 =  OK 
     *     3 - assertIsTrue  "name1.name2@domain1.tld"                                              =   0 =  OK 
     *     4 - assertIsTrue  "name1+name2@domain1.tld"                                              =   0 =  OK 
     *     5 - assertIsTrue  "name1-name2@domain1.tld"                                              =   0 =  OK 
     *     6 - assertIsTrue  "name1.name2@subdomain1.domain1.tld"                                   =   0 =  OK 
     *     7 - assertIsTrue  "name1.name2@subdomain1.tu-domain1.tld"                                =   0 =  OK 
     *     8 - assertIsTrue  "name1.name2@subdomain1.tu_domain1.tld"                                =   0 =  OK 
     *     9 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                 =   2 =  OK 
     *    10 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                    =   4 =  OK 
     *    11 - assertIsTrue  "\"quote1\".name1@domain1.tld"                                         =   1 =  OK 
     *    12 - assertIsTrue  "name1.\"quote1\"@domain1.tld"                                         =   1 =  OK 
     *    13 - assertIsTrue  "name1.\"quote1\".name2@domain1.tld"                                   =   1 =  OK 
     *    14 - assertIsTrue  "\"quote1\".\"quote2\".name1@domain1.tld"                              =   1 =  OK 
     *    15 - assertIsTrue  "\"quote1\"@domain1.tld"                                               =   1 =  OK 
     *    16 - assertIsTrue  "\"quote1\\"qoute2\\"\"@domain1.tld"                                   =   1 =  OK 
     *    17 - assertIsTrue  "(comment1)name1@domain1.tld"                                          =   6 =  OK 
     *    18 - assertIsTrue  "(comment1)-name1@domain1.tld"                                         =   6 =  OK 
     *    19 - assertIsTrue  "name1(comment1)@domain1.tld"                                          =   6 =  OK 
     *    20 - assertIsTrue  "name1@(comment1)domain1.tld"                                          =   6 =  OK 
     *    21 - assertIsTrue  "name1@domain1.tld(comment1)"                                          =   6 =  OK 
     *    22 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                 =   2 =  OK 
     *    23 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                 =   2 =  OK 
     *    24 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                 =   2 =  OK 
     *    25 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                 =   2 =  OK 
     *    26 - assertIsTrue  "(comment1)\"quote1\".name1@domain1.tld"                               =   7 =  OK 
     *    27 - assertIsTrue  "(comment1)name1.\"quote1\"@domain1.tld"                               =   7 =  OK 
     *    28 - assertIsTrue  "name1.\"quote1\"(comment1)@domain1.tld"                               =   7 =  OK 
     *    29 - assertIsTrue  "\"quote1\".name1(comment1)@domain1.tld"                               =   7 =  OK 
     *    30 - assertIsTrue  "name1.\"quote1\"@(comment1)domain1.tld"                               =   7 =  OK 
     *    31 - assertIsTrue  "\"quote1\".name1@domain1.tld(comment1)"                               =   7 =  OK 
     *    32 - assertIsTrue  "<name1.name2@domain1.tld>"                                            =   0 =  OK 
     *    33 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                      =   0 =  OK 
     *    34 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                      =   0 =  OK 
     *    35 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                            =   0 =  OK 
     *    36 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                         =   2 =  OK 
     *    37 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                            =   4 =  OK 
     *    38 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                         =   2 =  OK 
     *    39 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                           =   4 =  OK 
     *    40 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"  =   6 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    41 - assertIsFalse null                                                                   =  10 =  OK    Laenge: Eingabe ist null
     *    42 - assertIsFalse ""                                                                     =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    43 - assertIsFalse "        "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    44 - assertIsFalse "1234567890"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    45 - assertIsFalse "OnlyTextNoDotNoAt"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    46 - assertIsFalse "email.with.no.at.character"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    47 - assertIsFalse "email.with.no.domain@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    48 - assertIsFalse "@.local.name.starts.with.at@domain.com"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    49 - assertIsFalse "@no.local.email.part.com"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    50 - assertIsFalse "local.name.with@at@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    51 - assertIsFalse "local.name.ends.with.at@@domain.com"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    52 - assertIsFalse "local.name.with.at.before@.point@domain.com"                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    53 - assertIsFalse "local.name.with.at.after.@point@domain.com"                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    54 - assertIsFalse "local.name.with.double.at@@test@domain.com"                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    55 - assertIsFalse "(comment @) local.name.with.comment.with.at@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    56 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"          =   6 =  OK 
     *    57 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"  =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *    58 - assertIsTrue  "\"quote@\".local.name.with.qoute.with.at@domain.com"                  =   1 =  OK 
     *    59 - assertIsTrue  "qouted.\@.character@domain.com"                                       =   0 =  OK 
     *    60 - assertIsTrue  "qouted\@character@domain.com"                                         =   0 =  OK 
     *    61 - assertIsTrue  "\@@domain.com"                                                        =   0 =  OK 
     *    62 - assertIsFalse "@@domain.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    63 - assertIsFalse "@domain.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    64 - assertIsFalse "@@@@@@@domain.com"                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    65 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                         =   0 =  OK 
     *    66 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    67 - assertIsFalse "@.@.@.@.@.@@domain.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    68 - assertIsFalse "@.@.@."                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    69 - assertIsFalse "\@.\@@\@.\@"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    70 - assertIsFalse "@"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    71 - assertIsFalse "name @ <pointy.brackets1.with.at@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    72 - assertIsFalse "<pointy.brackets2.with.at@domain.com> name @"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    73 - assertIsFalse "..local.name.starts.with.dot@domain.com"                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    74 - assertIsFalse "local.name.ends.with.dot.@domain.com"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    75 - assertIsFalse "local.name.with.dot.before..point@domain.com"                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    76 - assertIsFalse "local.name.with.dot.after..point@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    77 - assertIsFalse "local.name.with.double.dot..test@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    78 - assertIsFalse "(comment .) local.name.with.comment.with.dot@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    79 - assertIsTrue  "\"quote.\".local.name.with.qoute.with.dot@domain.com"                 =   1 =  OK 
     *    80 - assertIsFalse ".@domain.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    81 - assertIsFalse "......@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    82 - assertIsFalse "...........@domain.com"                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    83 - assertIsFalse "qouted\.dot@domain.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *    84 - assertIsFalse "name . <pointy.brackets1.with.dot@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    85 - assertIsFalse "<pointy.brackets2.with.dot@domain.com> name ."                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    86 - assertIsFalse "domain.part.without.dot@domaincom"                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    87 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    88 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    89 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    90 - assertIsFalse "domain.part@with.dot.before..point.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    91 - assertIsFalse "domain.part@with.dot.after..point.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    92 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    93 - assertIsFalse "EmailAdressWith@NoDots"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    94 - assertIsTrue  "&local&&name&with&$@amp.com"                                          =   0 =  OK 
     *    95 - assertIsTrue  "*local**name*with*@asterisk.com"                                      =   0 =  OK 
     *    96 - assertIsTrue  "$local$$name$with$@dollar.com"                                        =   0 =  OK 
     *    97 - assertIsTrue  "=local==name=with=@equality.com"                                      =   0 =  OK 
     *    98 - assertIsTrue  "!local!!name!with!@exclamation.com"                                   =   0 =  OK 
     *    99 - assertIsTrue  "`local``name`with`@grave-accent.com"                                  =   0 =  OK 
     *   100 - assertIsTrue  "#local##name#with#@hash.com"                                          =   0 =  OK 
     *   101 - assertIsTrue  "-local--name-with-@hypen.com"                                         =   0 =  OK 
     *   102 - assertIsTrue  "{local{name{{with{@leftbracket.com"                                   =   0 =  OK 
     *   103 - assertIsTrue  "%local%%name%with%@percentage.com"                                    =   0 =  OK 
     *   104 - assertIsTrue  "|local||name|with|@pipe.com"                                          =   0 =  OK 
     *   105 - assertIsTrue  "+local++name+with+@plus.com"                                          =   0 =  OK 
     *   106 - assertIsTrue  "?local??name?with?@question.com"                                      =   0 =  OK 
     *   107 - assertIsTrue  "}local}name}}with}@rightbracket.com"                                  =   0 =  OK 
     *   108 - assertIsTrue  "~local~~name~with~@tilde.com"                                         =   0 =  OK 
     *   109 - assertIsTrue  "^local^^name^with^@xor.com"                                           =   0 =  OK 
     *   110 - assertIsTrue  "_local__name_with_@underscore.com"                                    =   0 =  OK 
     *   111 - assertIsFalse ":local::name:with:@colon.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   112 - assertIsTrue  "&.local.name.starts.with.amp@domain.com"                              =   0 =  OK 
     *   113 - assertIsTrue  "local.name.ends.with.amp&@domain.com"                                 =   0 =  OK 
     *   114 - assertIsTrue  "local.name.with.amp.before&.point@domain.com"                         =   0 =  OK 
     *   115 - assertIsTrue  "local.name.with.amp.after.&point@domain.com"                          =   0 =  OK 
     *   116 - assertIsTrue  "local.name.with.double.amp&&test@domain.com"                          =   0 =  OK 
     *   117 - assertIsFalse "(comment &) local.name.with.comment.with.amp@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   118 - assertIsTrue  "\"quote&\".local.name.with.qoute.with.amp@domain.com"                 =   1 =  OK 
     *   119 - assertIsTrue  "&@amp.domain.com"                                                     =   0 =  OK 
     *   120 - assertIsTrue  "&&&&&&@amp.domain.com"                                                =   0 =  OK 
     *   121 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                           =   0 =  OK 
     *   122 - assertIsFalse "name & <pointy.brackets1.with.amp@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   123 - assertIsFalse "<pointy.brackets2.with.amp@domain.com> name &"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   124 - assertIsTrue  "*.local.name.starts.with.asterisk@domain.com"                         =   0 =  OK 
     *   125 - assertIsTrue  "local.name.ends.with.asterisk*@domain.com"                            =   0 =  OK 
     *   126 - assertIsTrue  "local.name.with.asterisk.before*.point@domain.com"                    =   0 =  OK 
     *   127 - assertIsTrue  "local.name.with.asterisk.after.*point@domain.com"                     =   0 =  OK 
     *   128 - assertIsTrue  "local.name.with.double.asterisk**test@domain.com"                     =   0 =  OK 
     *   129 - assertIsFalse "(comment *) local.name.with.comment.with.asterisk@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   130 - assertIsTrue  "\"quote*\".local.name.with.qoute.with.asterisk@domain.com"            =   1 =  OK 
     *   131 - assertIsTrue  "*@asterisk.domain.com"                                                =   0 =  OK 
     *   132 - assertIsTrue  "******@asterisk.domain.com"                                           =   0 =  OK 
     *   133 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                      =   0 =  OK 
     *   134 - assertIsFalse "name * <pointy.brackets1.with.asterisk@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   135 - assertIsFalse "<pointy.brackets2.with.asterisk@domain.com> name *"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   136 - assertIsTrue  "$.local.name.starts.with.dollar@domain.com"                           =   0 =  OK 
     *   137 - assertIsTrue  "local.name.ends.with.dollar$@domain.com"                              =   0 =  OK 
     *   138 - assertIsTrue  "local.name.with.dollar.before$.point@domain.com"                      =   0 =  OK 
     *   139 - assertIsTrue  "local.name.with.dollar.after.$point@domain.com"                       =   0 =  OK 
     *   140 - assertIsTrue  "local.name.with.double.dollar$$test@domain.com"                       =   0 =  OK 
     *   141 - assertIsFalse "(comment $) local.name.with.comment.with.dollar@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   142 - assertIsTrue  "\"quote$\".local.name.with.qoute.with.dollar@domain.com"              =   1 =  OK 
     *   143 - assertIsTrue  "$@dollar.domain.com"                                                  =   0 =  OK 
     *   144 - assertIsTrue  "$$$$$$@dollar.domain.com"                                             =   0 =  OK 
     *   145 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                        =   0 =  OK 
     *   146 - assertIsFalse "name $ <pointy.brackets1.with.dollar@domain.com>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   147 - assertIsFalse "<pointy.brackets2.with.dollar@domain.com> name $"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   148 - assertIsTrue  "=.local.name.starts.with.equality@domain.com"                         =   0 =  OK 
     *   149 - assertIsTrue  "local.name.ends.with.equality=@domain.com"                            =   0 =  OK 
     *   150 - assertIsTrue  "local.name.with.equality.before=.point@domain.com"                    =   0 =  OK 
     *   151 - assertIsTrue  "local.name.with.equality.after.=point@domain.com"                     =   0 =  OK 
     *   152 - assertIsTrue  "local.name.with.double.equality==test@domain.com"                     =   0 =  OK 
     *   153 - assertIsFalse "(comment =) local.name.with.comment.with.equality@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   154 - assertIsTrue  "\"quote=\".local.name.with.qoute.with.equality@domain.com"            =   1 =  OK 
     *   155 - assertIsTrue  "=@equality.domain.com"                                                =   0 =  OK 
     *   156 - assertIsTrue  "======@equality.domain.com"                                           =   0 =  OK 
     *   157 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                      =   0 =  OK 
     *   158 - assertIsFalse "name = <pointy.brackets1.with.equality@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   159 - assertIsFalse "<pointy.brackets2.with.equality@domain.com> name ="                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   160 - assertIsTrue  "!.local.name.starts.with.exclamation@domain.com"                      =   0 =  OK 
     *   161 - assertIsTrue  "local.name.ends.with.exclamation!@domain.com"                         =   0 =  OK 
     *   162 - assertIsTrue  "local.name.with.exclamation.before!.point@domain.com"                 =   0 =  OK 
     *   163 - assertIsTrue  "local.name.with.exclamation.after.!point@domain.com"                  =   0 =  OK 
     *   164 - assertIsTrue  "local.name.with.double.exclamation!!test@domain.com"                  =   0 =  OK 
     *   165 - assertIsFalse "(comment !) local.name.with.comment.with.exclamation@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   166 - assertIsTrue  "\"quote!\".local.name.with.qoute.with.exclamation@domain.com"         =   1 =  OK 
     *   167 - assertIsTrue  "!@exclamation.domain.com"                                             =   0 =  OK 
     *   168 - assertIsTrue  "!!!!!!@exclamation.domain.com"                                        =   0 =  OK 
     *   169 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                   =   0 =  OK 
     *   170 - assertIsFalse "name ! <pointy.brackets1.with.exclamation@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   171 - assertIsFalse "<pointy.brackets2.with.exclamation@domain.com> name !"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   172 - assertIsTrue  "`.local.name.starts.with.grave-accent@domain.com"                     =   0 =  OK 
     *   173 - assertIsTrue  "local.name.ends.with.grave-accent`@domain.com"                        =   0 =  OK 
     *   174 - assertIsTrue  "local.name.with.grave-accent.before`.point@domain.com"                =   0 =  OK 
     *   175 - assertIsTrue  "local.name.with.grave-accent.after.`point@domain.com"                 =   0 =  OK 
     *   176 - assertIsTrue  "local.name.with.double.grave-accent``test@domain.com"                 =   0 =  OK 
     *   177 - assertIsFalse "(comment `) local.name.with.comment.with.grave-accent@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   178 - assertIsTrue  "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com"        =   1 =  OK 
     *   179 - assertIsTrue  "`@grave-accent.domain.com"                                            =   0 =  OK 
     *   180 - assertIsTrue  "``````@grave-accent.domain.com"                                       =   0 =  OK 
     *   181 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                  =   0 =  OK 
     *   182 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   183 - assertIsFalse "<pointy.brackets2.with.grave-accent@domain.com> name `"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   184 - assertIsTrue  "#.local.name.starts.with.hash@domain.com"                             =   0 =  OK 
     *   185 - assertIsTrue  "local.name.ends.with.hash#@domain.com"                                =   0 =  OK 
     *   186 - assertIsTrue  "local.name.with.hash.before#.point@domain.com"                        =   0 =  OK 
     *   187 - assertIsTrue  "local.name.with.hash.after.#point@domain.com"                         =   0 =  OK 
     *   188 - assertIsTrue  "local.name.with.double.hash##test@domain.com"                         =   0 =  OK 
     *   189 - assertIsFalse "(comment #) local.name.with.comment.with.hash@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   190 - assertIsTrue  "\"quote#\".local.name.with.qoute.with.hash@domain.com"                =   1 =  OK 
     *   191 - assertIsTrue  "#@hash.domain.com"                                                    =   0 =  OK 
     *   192 - assertIsTrue  "######@hash.domain.com"                                               =   0 =  OK 
     *   193 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                          =   0 =  OK 
     *   194 - assertIsFalse "name # <pointy.brackets1.with.hash@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   195 - assertIsFalse "<pointy.brackets2.with.hash@domain.com> name #"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   196 - assertIsTrue  "-.local.name.starts.with.hypen@domain.com"                            =   0 =  OK 
     *   197 - assertIsTrue  "local.name.ends.with.hypen-@domain.com"                               =   0 =  OK 
     *   198 - assertIsTrue  "local.name.with.hypen.before-.point@domain.com"                       =   0 =  OK 
     *   199 - assertIsTrue  "local.name.with.hypen.after.-point@domain.com"                        =   0 =  OK 
     *   200 - assertIsTrue  "local.name.with.double.hypen--test@domain.com"                        =   0 =  OK 
     *   201 - assertIsFalse "(comment -) local.name.with.comment.with.hypen@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   202 - assertIsTrue  "\"quote-\".local.name.with.qoute.with.hypen@domain.com"               =   1 =  OK 
     *   203 - assertIsTrue  "-@hypen.domain.com"                                                   =   0 =  OK 
     *   204 - assertIsTrue  "------@hypen.domain.com"                                              =   0 =  OK 
     *   205 - assertIsTrue  "-.-.-.-.-.-@hypen.domain.com"                                         =   0 =  OK 
     *   206 - assertIsFalse "name - <pointy.brackets1.with.hypen@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   207 - assertIsFalse "<pointy.brackets2.with.hypen@domain.com> name -"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   208 - assertIsTrue  "{.local.name.starts.with.leftbracket@domain.com"                      =   0 =  OK 
     *   209 - assertIsTrue  "local.name.ends.with.leftbracket{@domain.com"                         =   0 =  OK 
     *   210 - assertIsTrue  "local.name.with.leftbracket.before{.point@domain.com"                 =   0 =  OK 
     *   211 - assertIsTrue  "local.name.with.leftbracket.after.{point@domain.com"                  =   0 =  OK 
     *   212 - assertIsTrue  "local.name.with.double.leftbracket{{test@domain.com"                  =   0 =  OK 
     *   213 - assertIsFalse "(comment {) local.name.with.comment.with.leftbracket@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   214 - assertIsTrue  "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   215 - assertIsTrue  "{@leftbracket.domain.com"                                             =   0 =  OK 
     *   216 - assertIsTrue  "{{{{{{@leftbracket.domain.com"                                        =   0 =  OK 
     *   217 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                   =   0 =  OK 
     *   218 - assertIsFalse "name { <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   219 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name {"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   220 - assertIsTrue  "%.local.name.starts.with.percentage@domain.com"                       =   0 =  OK 
     *   221 - assertIsTrue  "local.name.ends.with.percentage%@domain.com"                          =   0 =  OK 
     *   222 - assertIsTrue  "local.name.with.percentage.before%.point@domain.com"                  =   0 =  OK 
     *   223 - assertIsTrue  "local.name.with.percentage.after.%point@domain.com"                   =   0 =  OK 
     *   224 - assertIsTrue  "local.name.with.double.percentage%%test@domain.com"                   =   0 =  OK 
     *   225 - assertIsFalse "(comment %) local.name.with.comment.with.percentage@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   226 - assertIsTrue  "\"quote%\".local.name.with.qoute.with.percentage@domain.com"          =   1 =  OK 
     *   227 - assertIsTrue  "%@percentage.domain.com"                                              =   0 =  OK 
     *   228 - assertIsTrue  "%%%%%%@percentage.domain.com"                                         =   0 =  OK 
     *   229 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                    =   0 =  OK 
     *   230 - assertIsFalse "name % <pointy.brackets1.with.percentage@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   231 - assertIsFalse "<pointy.brackets2.with.percentage@domain.com> name %"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   232 - assertIsTrue  "|.local.name.starts.with.pipe@domain.com"                             =   0 =  OK 
     *   233 - assertIsTrue  "local.name.ends.with.pipe|@domain.com"                                =   0 =  OK 
     *   234 - assertIsTrue  "local.name.with.pipe.before|.point@domain.com"                        =   0 =  OK 
     *   235 - assertIsTrue  "local.name.with.pipe.after.|point@domain.com"                         =   0 =  OK 
     *   236 - assertIsTrue  "local.name.with.double.pipe||test@domain.com"                         =   0 =  OK 
     *   237 - assertIsFalse "(comment |) local.name.with.comment.with.pipe@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   238 - assertIsTrue  "\"quote|\".local.name.with.qoute.with.pipe@domain.com"                =   1 =  OK 
     *   239 - assertIsTrue  "|@pipe.domain.com"                                                    =   0 =  OK 
     *   240 - assertIsTrue  "||||||@pipe.domain.com"                                               =   0 =  OK 
     *   241 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                          =   0 =  OK 
     *   242 - assertIsFalse "name | <pointy.brackets1.with.pipe@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   243 - assertIsFalse "<pointy.brackets2.with.pipe@domain.com> name |"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   244 - assertIsTrue  "+.local.name.starts.with.plus@domain.com"                             =   0 =  OK 
     *   245 - assertIsTrue  "local.name.ends.with.plus+@domain.com"                                =   0 =  OK 
     *   246 - assertIsTrue  "local.name.with.plus.before+.point@domain.com"                        =   0 =  OK 
     *   247 - assertIsTrue  "local.name.with.plus.after.+point@domain.com"                         =   0 =  OK 
     *   248 - assertIsTrue  "local.name.with.double.plus++test@domain.com"                         =   0 =  OK 
     *   249 - assertIsFalse "(comment +) local.name.with.comment.with.plus@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   250 - assertIsTrue  "\"quote+\".local.name.with.qoute.with.plus@domain.com"                =   1 =  OK 
     *   251 - assertIsTrue  "+@plus.domain.com"                                                    =   0 =  OK 
     *   252 - assertIsTrue  "++++++@plus.domain.com"                                               =   0 =  OK 
     *   253 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                          =   0 =  OK 
     *   254 - assertIsFalse "name + <pointy.brackets1.with.plus@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   255 - assertIsFalse "<pointy.brackets2.with.plus@domain.com> name +"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   256 - assertIsTrue  "?.local.name.starts.with.question@domain.com"                         =   0 =  OK 
     *   257 - assertIsTrue  "local.name.ends.with.question?@domain.com"                            =   0 =  OK 
     *   258 - assertIsTrue  "local.name.with.question.before?.point@domain.com"                    =   0 =  OK 
     *   259 - assertIsTrue  "local.name.with.question.after.?point@domain.com"                     =   0 =  OK 
     *   260 - assertIsTrue  "local.name.with.double.question??test@domain.com"                     =   0 =  OK 
     *   261 - assertIsFalse "(comment ?) local.name.with.comment.with.question@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   262 - assertIsTrue  "\"quote?\".local.name.with.qoute.with.question@domain.com"            =   1 =  OK 
     *   263 - assertIsTrue  "?@question.domain.com"                                                =   0 =  OK 
     *   264 - assertIsTrue  "??????@question.domain.com"                                           =   0 =  OK 
     *   265 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                      =   0 =  OK 
     *   266 - assertIsFalse "name ? <pointy.brackets1.with.question@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   267 - assertIsFalse "<pointy.brackets2.with.question@domain.com> name ?"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   268 - assertIsTrue  "}.local.name.starts.with.rightbracket@domain.com"                     =   0 =  OK 
     *   269 - assertIsTrue  "local.name.ends.with.rightbracket}@domain.com"                        =   0 =  OK 
     *   270 - assertIsTrue  "local.name.with.rightbracket.before}.point@domain.com"                =   0 =  OK 
     *   271 - assertIsTrue  "local.name.with.rightbracket.after.}point@domain.com"                 =   0 =  OK 
     *   272 - assertIsTrue  "local.name.with.double.rightbracket}}test@domain.com"                 =   0 =  OK 
     *   273 - assertIsFalse "(comment }) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   274 - assertIsTrue  "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   275 - assertIsTrue  "}@rightbracket.domain.com"                                            =   0 =  OK 
     *   276 - assertIsTrue  "}}}}}}@rightbracket.domain.com"                                       =   0 =  OK 
     *   277 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                  =   0 =  OK 
     *   278 - assertIsFalse "name } <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   279 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name }"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   280 - assertIsTrue  "~.local.name.starts.with.tilde@domain.com"                            =   0 =  OK 
     *   281 - assertIsTrue  "local.name.ends.with.tilde~@domain.com"                               =   0 =  OK 
     *   282 - assertIsTrue  "local.name.with.tilde.before~.point@domain.com"                       =   0 =  OK 
     *   283 - assertIsTrue  "local.name.with.tilde.after.~point@domain.com"                        =   0 =  OK 
     *   284 - assertIsTrue  "local.name.with.double.tilde~~test@domain.com"                        =   0 =  OK 
     *   285 - assertIsFalse "(comment ~) local.name.with.comment.with.tilde@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   286 - assertIsTrue  "\"quote~\".local.name.with.qoute.with.tilde@domain.com"               =   1 =  OK 
     *   287 - assertIsTrue  "~@tilde.domain.com"                                                   =   0 =  OK 
     *   288 - assertIsTrue  "~~~~~~@tilde.domain.com"                                              =   0 =  OK 
     *   289 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                         =   0 =  OK 
     *   290 - assertIsFalse "name ~ <pointy.brackets1.with.tilde@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   291 - assertIsFalse "<pointy.brackets2.with.tilde@domain.com> name ~"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   292 - assertIsTrue  "^.local.name.starts.with.xor@domain.com"                              =   0 =  OK 
     *   293 - assertIsTrue  "local.name.ends.with.xor^@domain.com"                                 =   0 =  OK 
     *   294 - assertIsTrue  "local.name.with.xor.before^.point@domain.com"                         =   0 =  OK 
     *   295 - assertIsTrue  "local.name.with.xor.after.^point@domain.com"                          =   0 =  OK 
     *   296 - assertIsTrue  "local.name.with.double.xor^^test@domain.com"                          =   0 =  OK 
     *   297 - assertIsFalse "(comment ^) local.name.with.comment.with.xor@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   298 - assertIsTrue  "\"quote^\".local.name.with.qoute.with.xor@domain.com"                 =   1 =  OK 
     *   299 - assertIsTrue  "^@xor.domain.com"                                                     =   0 =  OK 
     *   300 - assertIsTrue  "^^^^^^@xor.domain.com"                                                =   0 =  OK 
     *   301 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                           =   0 =  OK 
     *   302 - assertIsFalse "name ^ <pointy.brackets1.with.xor@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   303 - assertIsFalse "<pointy.brackets2.with.xor@domain.com> name ^"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   304 - assertIsTrue  "_.local.name.starts.with.underscore@domain.com"                       =   0 =  OK 
     *   305 - assertIsTrue  "local.name.ends.with.underscore_@domain.com"                          =   0 =  OK 
     *   306 - assertIsTrue  "local.name.with.underscore.before_.point@domain.com"                  =   0 =  OK 
     *   307 - assertIsTrue  "local.name.with.underscore.after._point@domain.com"                   =   0 =  OK 
     *   308 - assertIsTrue  "local.name.with.double.underscore__test@domain.com"                   =   0 =  OK 
     *   309 - assertIsFalse "(comment _) local.name.with.comment.with.underscore@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   310 - assertIsTrue  "\"quote_\".local.name.with.qoute.with.underscore@domain.com"          =   1 =  OK 
     *   311 - assertIsTrue  "_@underscore.domain.com"                                              =   0 =  OK 
     *   312 - assertIsTrue  "______@underscore.domain.com"                                         =   0 =  OK 
     *   313 - assertIsTrue  "_._._._._._@underscore.domain.com"                                    =   0 =  OK 
     *   314 - assertIsFalse "name _ <pointy.brackets1.with.underscore@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   315 - assertIsFalse "<pointy.brackets2.with.underscore@domain.com> name _"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   316 - assertIsFalse ":.local.name.starts.with.colon@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   317 - assertIsFalse "local.name.ends.with.colon:@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   318 - assertIsFalse "local.name.with.colon.before:.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   319 - assertIsFalse "local.name.with.colon.after.:point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   320 - assertIsFalse "local.name.with.double.colon::test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   321 - assertIsFalse "(comment :) local.name.with.comment.with.colon@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   322 - assertIsTrue  "\"quote:\".local.name.with.qoute.with.colon@domain.com"               =   1 =  OK 
     *   323 - assertIsFalse ":@colon.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   324 - assertIsFalse "::::::@colon.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   325 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   326 - assertIsFalse "name : <pointy.brackets1.with.colon@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   327 - assertIsFalse "<pointy.brackets2.with.colon@domain.com> name :"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   328 - assertIsFalse "(.local.name.starts.with.leftbracket@domain.com"                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   329 - assertIsFalse "local.name.ends.with.leftbracket(@domain.com"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   330 - assertIsFalse "local.name.with.leftbracket.before(.point@domain.com"                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   331 - assertIsFalse "local.name.with.leftbracket.after.(point@domain.com"                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   332 - assertIsFalse "local.name.with.double.leftbracket((test@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   333 - assertIsFalse "(comment () local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   334 - assertIsTrue  "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   335 - assertIsFalse "(@leftbracket.domain.com"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   336 - assertIsFalse "((((((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   337 - assertIsFalse "(()(((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   338 - assertIsFalse "((<)>(((@leftbracket.domain.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   339 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   340 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket@domain.com>"                =   0 =  OK 
     *   341 - assertIsTrue  "<pointy.brackets2.with.leftbracket@domain.com> name ("                =   0 =  OK 
     *   342 - assertIsFalse "\.local.name.starts.with.slash@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   343 - assertIsFalse "local.name.ends.with.slash\@domain.com"                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   344 - assertIsFalse "local.name.with.slash.before\.point@domain.com"                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   345 - assertIsFalse "local.name.with.slash.after.\point@domain.com"                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   346 - assertIsTrue  "local.name.with.double.slash\\test@domain.com"                        =   0 =  OK 
     *   347 - assertIsFalse "(comment \) local.name.with.comment.with.slash@domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   348 - assertIsFalse "\"quote\\".local.name.with.qoute.with.slash@domain.com"               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   349 - assertIsFalse "\@slash.domain.com"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   350 - assertIsTrue  "\\\\\\@slash.domain.com"                                              =   0 =  OK 
     *   351 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   352 - assertIsFalse "name \ <pointy.brackets1.with.slash@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   353 - assertIsFalse "<pointy.brackets2.with.slash@domain.com> name \"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   354 - assertIsFalse ").local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   355 - assertIsFalse "local.name.ends.with.rightbracket)@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   356 - assertIsFalse "local.name.with.rightbracket.before).point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   357 - assertIsFalse "local.name.with.rightbracket.after.)point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   358 - assertIsFalse "local.name.with.double.rightbracket))test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   359 - assertIsFalse "(comment )) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   360 - assertIsTrue  "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   361 - assertIsFalse ")@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   362 - assertIsFalse "))))))@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   363 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   364 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket@domain.com>"               =   0 =  OK 
     *   365 - assertIsTrue  "<pointy.brackets2.with.rightbracket@domain.com> name )"               =   0 =  OK 
     *   366 - assertIsFalse "[.local.name.starts.with.leftbracket@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   367 - assertIsFalse "local.name.ends.with.leftbracket[@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   368 - assertIsFalse "local.name.with.leftbracket.before[.point@domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   369 - assertIsFalse "local.name.with.leftbracket.after.[point@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   370 - assertIsFalse "local.name.with.double.leftbracket[[test@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   371 - assertIsFalse "(comment [) local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   372 - assertIsTrue  "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   373 - assertIsFalse "[@leftbracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   374 - assertIsFalse "[[[[[[@leftbracket.domain.com"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   375 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   376 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   377 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name ["                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   378 - assertIsFalse "].local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   379 - assertIsFalse "local.name.ends.with.rightbracket]@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   380 - assertIsFalse "local.name.with.rightbracket.before].point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   381 - assertIsFalse "local.name.with.rightbracket.after.]point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsFalse "local.name.with.double.rightbracket]]test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   383 - assertIsFalse "(comment ]) local.name.with.comment.with.rightbracket@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   384 - assertIsTrue  "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   385 - assertIsFalse "]@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   386 - assertIsFalse "]]]]]]@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   387 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   388 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   389 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name ]"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   390 - assertIsFalse " .local.name.starts.with.space@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   391 - assertIsFalse "local.name.ends.with.space @domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   392 - assertIsFalse "local.name.with.space.before .point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   393 - assertIsFalse "local.name.with.space.after. point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   394 - assertIsFalse "local.name.with.double.space  test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   395 - assertIsFalse "(comment  ) local.name.with.comment.with.space@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   396 - assertIsTrue  "\"quote \".local.name.with.qoute.with.space@domain.com"               =   1 =  OK 
     *   397 - assertIsFalse " @space.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   398 - assertIsFalse "      @space.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   399 - assertIsFalse " . . . . . @space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   400 - assertIsTrue  "name   <pointy.brackets1.with.space@domain.com>"                      =   0 =  OK 
     *   401 - assertIsTrue  "<pointy.brackets2.with.space@domain.com> name  "                      =   0 =  OK 
     *   402 - assertIsFalse "().local.name.starts.with.empty.bracket@domain.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   403 - assertIsTrue  "local.name.ends.with.empty.bracket()@domain.com"                      =   6 =  OK 
     *   404 - assertIsFalse "local.name.with.empty.bracket.before().point@domain.com"              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   405 - assertIsFalse "local.name.with.empty.bracket.after.()point@domain.com"               = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   406 - assertIsFalse "local.name.with.double.empty.bracket()()test@domain.com"              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   407 - assertIsFalse "(comment ()) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   408 - assertIsTrue  "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   409 - assertIsFalse "()@empty.bracket.domain.com"                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   410 - assertIsFalse "()()()()()()@empty.bracket.domain.com"                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   411 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   412 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket@domain.com>"             =   0 =  OK 
     *   413 - assertIsTrue  "<pointy.brackets2.with.empty.bracket@domain.com> name ()"             =   0 =  OK 
     *   414 - assertIsTrue  "{}.local.name.starts.with.empty.bracket@domain.com"                   =   0 =  OK 
     *   415 - assertIsTrue  "local.name.ends.with.empty.bracket{}@domain.com"                      =   0 =  OK 
     *   416 - assertIsTrue  "local.name.with.empty.bracket.before{}.point@domain.com"              =   0 =  OK 
     *   417 - assertIsTrue  "local.name.with.empty.bracket.after.{}point@domain.com"               =   0 =  OK 
     *   418 - assertIsTrue  "local.name.with.double.empty.bracket{}{}test@domain.com"              =   0 =  OK 
     *   419 - assertIsFalse "(comment {}) local.name.with.comment.with.empty.bracket@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   420 - assertIsTrue  "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   421 - assertIsTrue  "{}@empty.bracket.domain.com"                                          =   0 =  OK 
     *   422 - assertIsTrue  "{}{}{}{}{}{}@empty.bracket.domain.com"                                =   0 =  OK 
     *   423 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                           =   0 =  OK 
     *   424 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   425 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name {}"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   426 - assertIsFalse "[].local.name.starts.with.empty.bracket@domain.com"                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   427 - assertIsFalse "local.name.ends.with.empty.bracket[]@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   428 - assertIsFalse "local.name.with.empty.bracket.before[].point@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   429 - assertIsFalse "local.name.with.empty.bracket.after.[]point@domain.com"               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   430 - assertIsFalse "local.name.with.double.empty.bracket[][]test@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   431 - assertIsFalse "(comment []) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   432 - assertIsTrue  "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   433 - assertIsFalse "[]@empty.bracket.domain.com"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   434 - assertIsFalse "[][][][][][]@empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   435 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   436 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   437 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name []"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   438 - assertIsTrue  "999.local.name.starts.with.byte.overflow@domain.com"                  =   0 =  OK 
     *   439 - assertIsTrue  "local.name.ends.with.byte.overflow999@domain.com"                     =   0 =  OK 
     *   440 - assertIsTrue  "local.name.with.byte.overflow.before999.point@domain.com"             =   0 =  OK 
     *   441 - assertIsTrue  "local.name.with.byte.overflow.after.999point@domain.com"              =   0 =  OK 
     *   442 - assertIsTrue  "local.name.with.double.byte.overflow999999test@domain.com"            =   0 =  OK 
     *   443 - assertIsTrue  "(comment 999) local.name.with.comment.with.byte.overflow@domain.com"  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   444 - assertIsTrue  "\"quote999\".local.name.with.qoute.with.byte.overflow@domain.com"     =   1 =  OK 
     *   445 - assertIsTrue  "999@byte.overflow.domain.com"                                         =   0 =  OK 
     *   446 - assertIsTrue  "999999999999999999@byte.overflow.domain.com"                          =   0 =  OK 
     *   447 - assertIsTrue  "999.999.999.999.999.999@byte.overflow.domain.com"                     =   0 =  OK 
     *   448 - assertIsTrue  "name 999 <pointy.brackets1.with.byte.overflow@domain.com>"            =   0 =  OK 
     *   449 - assertIsTrue  "<pointy.brackets2.with.byte.overflow@domain.com> name 999"            =   0 =  OK 
     *   450 - assertIsTrue  "\"str\".local.name.starts.with.string@domain.com"                     =   1 =  OK 
     *   451 - assertIsFalse "local.name.ends.with.string\"str\"@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   452 - assertIsFalse "local.name.with.string.before\"str\".point@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   453 - assertIsFalse "local.name.with.string.after.\"str\"point@domain.com"                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   454 - assertIsFalse "local.name.with.double.string\"str\"\"str\"test@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   455 - assertIsFalse "(comment \"str\") local.name.with.comment.with.string@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   456 - assertIsFalse "\"quote\"str\"\".local.name.with.qoute.with.string@domain.com"        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   457 - assertIsTrue  "\"str\"@string.domain.com"                                            =   1 =  OK 
     *   458 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com"         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   459 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"    =   1 =  OK 
     *   460 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string@domain.com>"               =   0 =  OK 
     *   461 - assertIsTrue  "<pointy.brackets2.with.string@domain.com> name \"str\""               =   0 =  OK 
     *   462 - assertIsFalse "(comment).local.name.starts.with.comment@domain.com"                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   463 - assertIsTrue  "local.name.ends.with.comment(comment)@domain.com"                     =   6 =  OK 
     *   464 - assertIsFalse "local.name.with.comment.before(comment).point@domain.com"             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   465 - assertIsFalse "local.name.with.comment.after.(comment)point@domain.com"              = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   466 - assertIsFalse "local.name.with.double.comment(comment)(comment)test@domain.com"      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   467 - assertIsFalse "(comment (comment)) local.name.with.comment.with.comment@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   468 - assertIsTrue  "\"quote(comment)\".local.name.with.qoute.with.comment@domain.com"     =   1 =  OK 
     *   469 - assertIsFalse "(comment)@comment.domain.com"                                         =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   470 - assertIsFalse "(comment)(comment)(comment)(comment)@comment.domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   471 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   472 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment@domain.com>"            =   0 =  OK 
     *   473 - assertIsTrue  "<pointy.brackets2.with.comment@domain.com> name (comment)"            =   0 =  OK 
     *   474 - assertIsTrue  "domain.part@with0number0.com"                                         =   0 =  OK 
     *   475 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                        =   0 =  OK 
     *   476 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                         =   0 =  OK 
     *   477 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                         =   0 =  OK 
     *   478 - assertIsTrue  "domain.part@with.number0.before0.point.com"                           =   0 =  OK 
     *   479 - assertIsTrue  "domain.part@with.number0.after.0point.com"                            =   0 =  OK 
     *   480 - assertIsTrue  "domain.part@with9number9.com"                                         =   0 =  OK 
     *   481 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                        =   0 =  OK 
     *   482 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                         =   0 =  OK 
     *   483 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                         =   0 =  OK 
     *   484 - assertIsTrue  "domain.part@with.number9.before9.point.com"                           =   0 =  OK 
     *   485 - assertIsTrue  "domain.part@with.number9.after.9point.com"                            =   0 =  OK 
     *   486 - assertIsTrue  "domain.part.only.numbers@1234567890.com"                              =   0 =  OK 
     *   487 - assertIsTrue  "domain.part@with0123456789numbers.com"                                =   0 =  OK 
     *   488 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"               =   0 =  OK 
     *   489 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                =   0 =  OK 
     *   490 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                =   0 =  OK 
     *   491 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                  =   0 =  OK 
     *   492 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                   =   0 =  OK 
     *   493 - assertIsTrue  "domain.part@with-hyphen.com"                                          =   0 =  OK 
     *   494 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   495 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   496 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   497 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   498 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   499 - assertIsTrue  "domain.part@with_underscore.com"                                      =   0 =  OK 
     *   500 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   501 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   502 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   503 - assertIsFalse "domain.part@with.underscore.before_.point.com"                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   504 - assertIsFalse "domain.part@with.underscore.after._point.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   505 - assertIsFalse "domain.part@with&amp.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   506 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   507 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   508 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   509 - assertIsFalse "domain.part@with.amp.before&.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   510 - assertIsFalse "domain.part@with.amp.after.&point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   511 - assertIsFalse "domain.part@with*asterisk.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   512 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   513 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   514 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   515 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   516 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   517 - assertIsFalse "domain.part@with$dollar.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   518 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   519 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   520 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   521 - assertIsFalse "domain.part@with.dollar.before$.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   522 - assertIsFalse "domain.part@with.dollar.after.$point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   523 - assertIsFalse "domain.part@with=equality.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   524 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   525 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   526 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   527 - assertIsFalse "domain.part@with.equality.before=.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   528 - assertIsFalse "domain.part@with.equality.after.=point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   529 - assertIsFalse "domain.part@with!exclamation.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   530 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   531 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   532 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   533 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   534 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   535 - assertIsFalse "domain.part@with?question.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   536 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   537 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   538 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   539 - assertIsFalse "domain.part@with.question.before?.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   540 - assertIsFalse "domain.part@with.question.after.?point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   541 - assertIsFalse "domain.part@with`grave-accent.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   542 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   543 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   544 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   545 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   546 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   547 - assertIsFalse "domain.part@with#hash.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   548 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   549 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   550 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   551 - assertIsFalse "domain.part@with.hash.before#.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   552 - assertIsFalse "domain.part@with.hash.after.#point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   553 - assertIsFalse "domain.part@with%percentage.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   554 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   555 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   556 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   557 - assertIsFalse "domain.part@with.percentage.before%.point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   558 - assertIsFalse "domain.part@with.percentage.after.%point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   559 - assertIsFalse "domain.part@with|pipe.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   560 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   561 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   562 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   563 - assertIsFalse "domain.part@with.pipe.before|.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   564 - assertIsFalse "domain.part@with.pipe.after.|point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   565 - assertIsFalse "domain.part@with+plus.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   566 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   567 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   568 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   569 - assertIsFalse "domain.part@with.plus.before+.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   570 - assertIsFalse "domain.part@with.plus.after.+point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   571 - assertIsFalse "domain.part@with{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   572 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   573 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   574 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   575 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsFalse "domain.part@with}rightbracket.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   578 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   579 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   580 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   581 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   582 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   583 - assertIsFalse "domain.part@with(leftbracket.com"                                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   584 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   585 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   586 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   587 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   588 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   589 - assertIsFalse "domain.part@with)rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   590 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   591 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   592 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   593 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   594 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   595 - assertIsFalse "domain.part@with[leftbracket.com"                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   596 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   597 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   598 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   599 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                       =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   600 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   601 - assertIsFalse "domain.part@with]rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   602 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   603 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   604 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   605 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   606 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   607 - assertIsFalse "domain.part@with~tilde.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   608 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   609 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   610 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   611 - assertIsFalse "domain.part@with.tilde.before~.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   612 - assertIsFalse "domain.part@with.tilde.after.~point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   613 - assertIsFalse "domain.part@with^xor.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   614 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   615 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   616 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   617 - assertIsFalse "domain.part@with.xor.before^.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   618 - assertIsFalse "domain.part@with.xor.after.^point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   619 - assertIsFalse "domain.part@with:colon.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   620 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   621 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   622 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   623 - assertIsFalse "domain.part@with.colon.before:.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   624 - assertIsFalse "domain.part@with.colon.after.:point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   625 - assertIsFalse "domain.part@with space.com"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   626 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   627 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   628 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   629 - assertIsFalse "domain.part@with.space.before .point.com"                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   630 - assertIsFalse "domain.part@with.space.after. point.com"                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   631 - assertIsTrue  "domain.part@with999byte.overflow.com"                                 =   0 =  OK 
     *   632 - assertIsTrue  "domain.part@999with.byte.overflow.at.domain.start.com"                =   0 =  OK 
     *   633 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end1999.com"                 =   0 =  OK 
     *   634 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end2.com999"                 =   0 =  OK 
     *   635 - assertIsTrue  "domain.part@with.byte.overflow.before999.point.com"                   =   0 =  OK 
     *   636 - assertIsTrue  "domain.part@with.byte.overflow.after.999point.com"                    =   0 =  OK 
     *   637 - assertIsTrue  "domain.part@withxyzno.hex.number.com"                                 =   0 =  OK 
     *   638 - assertIsTrue  "domain.part@xyzwith.no.hex.number.at.domain.start.com"                =   0 =  OK 
     *   639 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end1xyz.com"                 =   0 =  OK 
     *   640 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end2.comxyz"                 =   0 =  OK 
     *   641 - assertIsTrue  "domain.part@with.no.hex.number.beforexyz.point.com"                   =   0 =  OK 
     *   642 - assertIsTrue  "domain.part@with.no.hex.number.after.xyzpoint.com"                    =   0 =  OK 
     *   643 - assertIsFalse "domain.part@with\"str\"string.com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   644 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   645 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   646 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   647 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   648 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   649 - assertIsFalse "domain.part@with(comment)comment.com"                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   650 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                =   6 =  OK 
     *   651 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   652 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                 =   6 =  OK 
     *   653 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   654 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                    = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   655 - assertIsFalse ",.local.name.starts.with.comma@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   656 - assertIsFalse "local.name.ends.with.comma,@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   657 - assertIsFalse "local.name.with.comma.before,.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   658 - assertIsFalse "local.name.with.comma.after.,point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   659 - assertIsFalse "local.name.with.double.comma,,test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   660 - assertIsFalse "(comment ,) local.name.with.comment.with.comma@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   661 - assertIsTrue  "\"quote,\".local.name.with.qoute.with.comma@domain.com"               =   1 =  OK 
     *   662 - assertIsFalse ",@comma.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   663 - assertIsFalse ",,,,,,@comma.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   664 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   665 - assertIsFalse "name , <pointy.brackets1.with.comma@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   666 - assertIsFalse "<pointy.brackets2.with.comma@domain.com> name ,"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   667 - assertIsFalse "domain.part@with,comma.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   668 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   669 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   670 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   671 - assertIsFalse "domain.part@with.comma.before,.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   672 - assertIsFalse "domain.part@with.comma.after.,point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   673 - assertIsFalse "§.local.name.starts.with.paragraph@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   674 - assertIsFalse "local.name.ends.with.paragraph§@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsFalse "local.name.with.paragraph.before§.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   676 - assertIsFalse "local.name.with.paragraph.after.§point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   677 - assertIsFalse "local.name.with.double.paragraph§§test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   678 - assertIsFalse "(comment §) local.name.with.comment.with.paragraph@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   679 - assertIsFalse "\"quote§\".local.name.with.qoute.with.paragraph@domain.com"           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   680 - assertIsFalse "§@paragraph.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   681 - assertIsFalse "§§§§§§@paragraph.domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   682 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   683 - assertIsFalse "name § <pointy.brackets1.with.paragraph@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   684 - assertIsFalse "<pointy.brackets2.with.paragraph@domain.com> name §"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   685 - assertIsFalse "domain.part@with§paragraph.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   686 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   687 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   688 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   689 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   690 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   691 - assertIsTrue  "'.local.name.starts.with.quote@domain.com"                            =   0 =  OK 
     *   692 - assertIsTrue  "local.name.ends.with.quote'@domain.com"                               =   0 =  OK 
     *   693 - assertIsTrue  "local.name.with.quote.before'.point@domain.com"                       =   0 =  OK 
     *   694 - assertIsTrue  "local.name.with.quote.after.'point@domain.com"                        =   0 =  OK 
     *   695 - assertIsTrue  "local.name.with.double.quote''test@domain.com"                        =   0 =  OK 
     *   696 - assertIsFalse "(comment ') local.name.with.comment.with.quote@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   697 - assertIsTrue  "\"quote'\".local.name.with.qoute.with.quote@domain.com"               =   1 =  OK 
     *   698 - assertIsTrue  "'@quote.domain.com"                                                   =   0 =  OK 
     *   699 - assertIsTrue  "''''''@quote.domain.com"                                              =   0 =  OK 
     *   700 - assertIsTrue  "'.'.'.'.'.'@quote.domain.com"                                         =   0 =  OK 
     *   701 - assertIsFalse "name ' <pointy.brackets1.with.quote@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   702 - assertIsFalse "<pointy.brackets2.with.quote@domain.com> name '"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   703 - assertIsFalse "domain.part@with'quote.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   704 - assertIsFalse "domain.part@'with.quote.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   705 - assertIsFalse "domain.part@with.quote.at.domain.end1'.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   706 - assertIsFalse "domain.part@with.quote.at.domain.end2.com'"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   707 - assertIsFalse "domain.part@with.quote.before'.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   708 - assertIsFalse "domain.part@with.quote.after.'point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   709 - assertIsFalse "\".local.name.starts.with.double.quote@domain.com"                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   710 - assertIsFalse "local.name.ends.with.double.quote\"@domain.com"                       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   711 - assertIsFalse "local.name.with.double.quote.before\".point@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   712 - assertIsFalse "local.name.with.double.quote.after.\"point@domain.com"                =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   713 - assertIsFalse "local.name.with.double.double.quote\"\"test@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   714 - assertIsFalse "(comment \") local.name.with.comment.with.double.quote@domain.com"    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   715 - assertIsFalse "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com"       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   716 - assertIsFalse "\"@double.quote.domain.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   717 - assertIsTrue  "\".\".\".\".\".\"@double.quote.domain.com"                            =   1 =  OK 
     *   718 - assertIsTrue  "name \" <pointy.brackets1.with.double.quote@domain.com>"              =   0 =  OK 
     *   719 - assertIsTrue  "<pointy.brackets2.with.double.quote@domain.com> name \""              =   0 =  OK 
     *   720 - assertIsFalse "domain.part@with\"double.quote.com"                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   721 - assertIsFalse "domain.part@\"with.double.quote.at.domain.start.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   722 - assertIsFalse "domain.part@with.double.quote.at.domain.end1\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   723 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com\""                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   724 - assertIsFalse "domain.part@with.double.quote.before\".point.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   725 - assertIsFalse "domain.part@with.double.quote.after.\"point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   726 - assertIsFalse ")(.local.name.starts.with.false.bracket1@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   727 - assertIsFalse "local.name.ends.with.false.bracket1)(@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   728 - assertIsFalse "local.name.with.false.bracket1.before)(.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   729 - assertIsFalse "local.name.with.false.bracket1.after.)(point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   730 - assertIsFalse "local.name.with.double.false.bracket1)()(test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   731 - assertIsFalse "(comment )() local.name.with.comment.with.false.bracket1@domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   732 - assertIsTrue  "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com"     =   1 =  OK 
     *   733 - assertIsFalse ")(@false.bracket1.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   734 - assertIsFalse ")()()()()()(@false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   735 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   736 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1@domain.com>"            =   0 =  OK 
     *   737 - assertIsTrue  "<pointy.brackets2.with.false.bracket1@domain.com> name )("            =   0 =  OK 
     *   738 - assertIsFalse "domain.part@with)(false.bracket1.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   739 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   740 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   741 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   742 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   743 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   744 - assertIsTrue  "}{.local.name.starts.with.false.bracket2@domain.com"                  =   0 =  OK 
     *   745 - assertIsTrue  "local.name.ends.with.false.bracket2}{@domain.com"                     =   0 =  OK 
     *   746 - assertIsTrue  "local.name.with.false.bracket2.before}{.point@domain.com"             =   0 =  OK 
     *   747 - assertIsTrue  "local.name.with.false.bracket2.after.}{point@domain.com"              =   0 =  OK 
     *   748 - assertIsTrue  "local.name.with.double.false.bracket2}{}{test@domain.com"             =   0 =  OK 
     *   749 - assertIsFalse "(comment }{) local.name.with.comment.with.false.bracket2@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   750 - assertIsTrue  "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com"     =   1 =  OK 
     *   751 - assertIsTrue  "}{@false.bracket2.domain.com"                                         =   0 =  OK 
     *   752 - assertIsTrue  "}{}{}{}{}{}{@false.bracket2.domain.com"                               =   0 =  OK 
     *   753 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                          =   0 =  OK 
     *   754 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   755 - assertIsFalse "<pointy.brackets2.with.false.bracket2@domain.com> name }{"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   756 - assertIsFalse "domain.part@with}{false.bracket2.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   757 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   758 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   759 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   760 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   761 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   762 - assertIsFalse "][.local.name.starts.with.false.bracket3@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   763 - assertIsFalse "local.name.ends.with.false.bracket3][@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   764 - assertIsFalse "local.name.with.false.bracket3.before][.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   765 - assertIsFalse "local.name.with.false.bracket3.after.][point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   766 - assertIsFalse "local.name.with.double.false.bracket3][][test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   767 - assertIsFalse "(comment ][) local.name.with.comment.with.false.bracket3@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   768 - assertIsTrue  "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com"     =   1 =  OK 
     *   769 - assertIsFalse "][@false.bracket3.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   770 - assertIsFalse "][][][][][][@false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   771 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   773 - assertIsFalse "<pointy.brackets2.with.false.bracket3@domain.com> name ]["            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   774 - assertIsFalse "domain.part@with][false.bracket3.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   775 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   776 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   777 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   778 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   779 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   780 - assertIsFalse "><.local.name.starts.with.false.bracket4@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   781 - assertIsFalse "local.name.ends.with.false.bracket4><@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   782 - assertIsFalse "local.name.with.false.bracket4.before><.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   783 - assertIsFalse "local.name.with.false.bracket4.after.><point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   784 - assertIsFalse "local.name.with.double.false.bracket4><><test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   785 - assertIsFalse "(comment ><) local.name.with.comment.with.false.bracket4@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   786 - assertIsTrue  "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com"     =   1 =  OK 
     *   787 - assertIsFalse "><@false.bracket4.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   788 - assertIsFalse "><><><><><><@false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   789 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   790 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   791 - assertIsFalse "<pointy.brackets2.with.false.bracket4@domain.com> name ><"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   792 - assertIsFalse "domain.part@with\slash.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   793 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   794 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   795 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   796 - assertIsFalse "domain.part@with.slash.before\.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   797 - assertIsFalse "domain.part@with.slash.after.\point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   798 - assertIsFalse "domain.part@with><false.bracket4.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   799 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   800 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   801 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   802 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   803 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   804 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                    =   0 =  OK 
     *   805 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   806 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   807 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   808 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   809 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   810 - assertIsFalse "domain.part@with.consecutive.question??test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   811 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   812 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   813 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   814 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   815 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   816 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   817 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   818 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   819 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   820 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   821 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   822 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   823 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   824 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   825 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   826 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   827 - assertIsFalse "domain.part@with.consecutive.space  test.com"                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   828 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   829 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   830 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   831 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   832 - assertIsFalse "domain.part@with.consecutive.double.quote\"\"test.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   833 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"               = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   834 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   835 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   836 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   837 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   838 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   839 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   840 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   841 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   842 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   843 - assertIsTrue  "domain.part.with.comment.with.underscore@(comment _)domain.com"       =   6 =  OK 
     *   844 - assertIsTrue  "domain.part.with.comment.with.amp@(comment &)domain.com"              =   6 =  OK 
     *   845 - assertIsTrue  "domain.part.with.comment.with.asterisk@(comment *)domain.com"         =   6 =  OK 
     *   846 - assertIsTrue  "domain.part.with.comment.with.dollar@(comment $)domain.com"           =   6 =  OK 
     *   847 - assertIsTrue  "domain.part.with.comment.with.equality@(comment =)domain.com"         =   6 =  OK 
     *   848 - assertIsTrue  "domain.part.with.comment.with.exclamation@(comment !)domain.com"      =   6 =  OK 
     *   849 - assertIsTrue  "domain.part.with.comment.with.question@(comment ?)domain.com"         =   6 =  OK 
     *   850 - assertIsTrue  "domain.part.with.comment.with.grave-accent@(comment `)domain.com"     =   6 =  OK 
     *   851 - assertIsTrue  "domain.part.with.comment.with.hash@(comment #)domain.com"             =   6 =  OK 
     *   852 - assertIsTrue  "domain.part.with.comment.with.percentage@(comment %)domain.com"       =   6 =  OK 
     *   853 - assertIsTrue  "domain.part.with.comment.with.pipe@(comment |)domain.com"             =   6 =  OK 
     *   854 - assertIsTrue  "domain.part.with.comment.with.plus@(comment +)domain.com"             =   6 =  OK 
     *   855 - assertIsTrue  "domain.part.with.comment.with.leftbracket@(comment {)domain.com"      =   6 =  OK 
     *   856 - assertIsTrue  "domain.part.with.comment.with.rightbracket@(comment })domain.com"     =   6 =  OK 
     *   857 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment ()domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   858 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ))domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   859 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment [)domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   860 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ])domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   861 - assertIsFalse "domain.part.with.comment.with.lower.than@(comment <)domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   862 - assertIsFalse "domain.part.with.comment.with.greater.than@(comment >)domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   863 - assertIsTrue  "domain.part.with.comment.with.tilde@(comment ~)domain.com"            =   6 =  OK 
     *   864 - assertIsTrue  "domain.part.with.comment.with.xor@(comment ^)domain.com"              =   6 =  OK 
     *   865 - assertIsFalse "domain.part.with.comment.with.colon@(comment :)domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   866 - assertIsTrue  "domain.part.with.comment.with.space@(comment  )domain.com"            =   6 =  OK 
     *   867 - assertIsFalse "domain.part.with.comment.with.comma@(comment ,)domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   868 - assertIsFalse "domain.part.with.comment.with.paragraph@(comment §)domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   869 - assertIsTrue  "domain.part.with.comment.with.double.quote@(comment ')domain.com"     =   6 =  OK 
     *   870 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment ())domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   871 - assertIsTrue  "domain.part.with.comment.with.empty.bracket@(comment {})domain.com"   =   6 =  OK 
     *   872 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment [])domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   873 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment <>)domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   874 - assertIsFalse "domain.part.with.comment.with.false.bracket1@(comment )()domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   875 - assertIsTrue  "domain.part.with.comment.with.false.bracket2@(comment }{)domain.com"  =   6 =  OK 
     *   876 - assertIsFalse "domain.part.with.comment.with.false.bracket3@(comment ][)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   877 - assertIsFalse "domain.part.with.comment.with.false.bracket4@(comment ><)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   878 - assertIsFalse "domain.part.with.comment.with.slash@(comment \)domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   879 - assertIsFalse "domain.part.with.comment.with.string@(comment \"str\")domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   880 - assertIsFalse "domain.part.only.underscore@_.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   881 - assertIsFalse "domain.part.only.amp@&.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   882 - assertIsFalse "domain.part.only.asterisk@*.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   883 - assertIsFalse "domain.part.only.dollar@$.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   884 - assertIsFalse "domain.part.only.equality@=.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   885 - assertIsFalse "domain.part.only.exclamation@!.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   886 - assertIsFalse "domain.part.only.question@?.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   887 - assertIsFalse "domain.part.only.grave-accent@`.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   888 - assertIsFalse "domain.part.only.hash@#.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   889 - assertIsFalse "domain.part.only.percentage@%.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   890 - assertIsFalse "domain.part.only.pipe@|.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   891 - assertIsFalse "domain.part.only.plus@+.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   892 - assertIsFalse "domain.part.only.leftbracket@{.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   893 - assertIsFalse "domain.part.only.rightbracket@}.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   894 - assertIsFalse "domain.part.only.leftbracket@(.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   895 - assertIsFalse "domain.part.only.rightbracket@).com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   896 - assertIsFalse "domain.part.only.leftbracket@[.com"                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   897 - assertIsFalse "domain.part.only.rightbracket@].com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   898 - assertIsFalse "domain.part.only.lower.than@<.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   899 - assertIsFalse "domain.part.only.greater.than@>.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   900 - assertIsFalse "domain.part.only.tilde@~.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   901 - assertIsFalse "domain.part.only.xor@^.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   902 - assertIsFalse "domain.part.only.colon@:.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   903 - assertIsFalse "domain.part.only.space@ .com"                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   904 - assertIsFalse "domain.part.only.dot@..com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   905 - assertIsFalse "domain.part.only.comma@,.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   906 - assertIsFalse "domain.part.only.at@@.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   907 - assertIsFalse "domain.part.only.paragraph@§.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   908 - assertIsFalse "domain.part.only.double.quote@'.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   909 - assertIsFalse "domain.part.only.double.quote@\".com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   910 - assertIsFalse "domain.part.only.double.quote@\\".com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   911 - assertIsFalse "domain.part.only.empty.bracket@().com"                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   912 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   913 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   914 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   915 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   916 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   917 - assertIsFalse "domain.part.only.false.bracket3@][.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   918 - assertIsFalse "domain.part.only.false.bracket4@><.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   919 - assertIsTrue  "domain.part.only.number0@0.com"                                       =   0 =  OK 
     *   920 - assertIsTrue  "domain.part.only.number9@9.com"                                       =   0 =  OK 
     *   921 - assertIsFalse "domain.part.only.slash@\.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   922 - assertIsTrue  "domain.part.only.byte.overflow@999.com"                               =   0 =  OK 
     *   923 - assertIsTrue  "domain.part.only.no.hex.number@xyz.com"                               =   0 =  OK 
     *   924 - assertIsFalse "domain.part.only.string@\"str\".com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   925 - assertIsFalse "domain.part.only.comment@(comment).com"                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   926 - assertIsFalse "DomainHyphen@-atstart"                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   927 - assertIsFalse "DomainHyphen@atend-.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   928 - assertIsFalse "DomainHyphen@bb.-cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   929 - assertIsFalse "DomainHyphen@bb.-cc-"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   930 - assertIsFalse "DomainHyphen@bb.cc-"                                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   931 - assertIsFalse "DomainHyphen@bb.c-c"                                                  =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   932 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   933 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                    =   0 =  OK 
     *   934 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   935 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   936 - assertIsFalse "DomainNotAllowedCharacter@example'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   937 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   938 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                 =   0 =  OK 
     *   939 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                   =   0 =  OK 
     *   940 - assertIsFalse "tld.starts.with.digit@domain.2com"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   941 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                      =   0 =  OK 
     *   942 - assertIsFalse "email@=qowaiv.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   943 - assertIsFalse "email@plus+.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   944 - assertIsFalse "email@domain.com>"                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   945 - assertIsFalse "email@mailto:domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   946 - assertIsFalse "mailto:mailto:email@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   947 - assertIsFalse "email@-domain.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   948 - assertIsFalse "email@domain-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   949 - assertIsFalse "email@domain.com-"                                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   950 - assertIsFalse "email@{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   951 - assertIsFalse "email@rightbracket}.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   952 - assertIsFalse "email@pp|e.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   953 - assertIsTrue  "email@domain.domain.domain.com.com"                                   =   0 =  OK 
     *   954 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                            =   0 =  OK 
     *   955 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                     =   0 =  OK 
     *   956 - assertIsFalse "unescaped white space@fake$com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   957 - assertIsFalse "\"Joe Smith email@domain.com"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   958 - assertIsFalse "\"Joe Smith' email@domain.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   959 - assertIsFalse "\"Joe Smith\"email@domain.com"                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   960 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   961 - assertIsTrue  "{john'doe}@my.server"                                                 =   0 =  OK 
     *   962 - assertIsTrue  "email@domain-one.com"                                                 =   0 =  OK 
     *   963 - assertIsTrue  "_______@domain.com"                                                   =   0 =  OK 
     *   964 - assertIsTrue  "?????@domain.com"                                                     =   0 =  OK 
     *   965 - assertIsFalse "local@?????.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   966 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                        =   1 =  OK 
     *   967 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                               =   1 =  OK 
     *   968 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                            =   0 =  OK 
     *   969 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                     =   0 =  OK 
     *   970 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   971 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"              =   0 =  OK 
     *   972 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   973 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                  =   1 =  OK 
     *   974 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   975 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   976 - assertIsFalse "\"\"@[1"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   977 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   978 - assertIsFalse "ABC.DEF@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   979 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                   =   3 =  OK 
     *   980 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                            =   2 =  OK 
     *   981 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                              =   3 =  OK 
     *   982 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                                    =   2 =  OK 
     *   983 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   984 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   985 - assertIsFalse "ABC.DEF@([001.002.003.004])"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   986 - assertIsFalse "ABC.DEF[1.2.3.4]"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   987 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   988 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   989 - assertIsFalse "ABC.DEF@[][][][]"                                                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   990 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   991 - assertIsFalse "ABC.DEF@[....]"                                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   992 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   993 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   994 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                              =   3 =  OK 
     *   995 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   996 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   997 - assertIsFalse "ABC.DEF@[..]"                                                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   998 - assertIsFalse "ABC.DEF@[.2.3.4]"                                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   999 - assertIsFalse "ABC.DEF@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1000 - assertIsFalse "ABC.DEF@[1.2]"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1001 - assertIsFalse "ABC.DEF@[1.2.3]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1002 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                                  =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1003 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1004 - assertIsFalse "ABC.DEF@[1.2.3.]"                                                     =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1005 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1006 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1007 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1008 - assertIsFalse "ABC.DEF@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1009 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1010 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1011 - assertIsFalse "ABC.DEF@[12.34]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1012 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1013 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1014 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1015 - assertIsFalse "ABC.DEF@[-1.2.3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1016 - assertIsFalse "ABC.DEF@[1.-2.3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1017 - assertIsFalse "ABC.DEF@[1.2.-3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1018 - assertIsFalse "ABC.DEF@[1.2.3.-4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1019 - assertIsFalse "ip.v4.with.hyphen@[123.14-5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1020 - assertIsFalse "ip.v4.with.hyphen@[123.145-.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1021 - assertIsFalse "ip.v4.with.hyphen@[123.145.-178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1022 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90-]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1023 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90]-"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1024 - assertIsFalse "ip.v4.with.hyphen@[-123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1025 - assertIsFalse "ip.v4.with.hyphen@-[123.145.178.90]"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1026 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1027 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1028 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1029 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1030 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1031 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90]_"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1032 - assertIsFalse "ip.v4.with.underscore@_[123.145.178.90]"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1033 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1034 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1035 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1036 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1037 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1038 - assertIsFalse "ip.v4.with.amp@[123.145.178.90]&"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1039 - assertIsFalse "ip.v4.with.amp@&[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1040 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1041 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1042 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1043 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1044 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1045 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90]*"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1046 - assertIsFalse "ip.v4.with.asterisk@*[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1047 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1048 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1049 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1050 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1051 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1052 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90]$"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1053 - assertIsFalse "ip.v4.with.dollar@$[123.145.178.90]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1054 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1055 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1056 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1057 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1058 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1059 - assertIsFalse "ip.v4.with.equality@[123.145.178.90]="                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1060 - assertIsFalse "ip.v4.with.equality@=[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1061 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1062 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1063 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1064 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1065 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1066 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90]!"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1067 - assertIsFalse "ip.v4.with.exclamation@![123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1068 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1069 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1070 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1071 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1072 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1073 - assertIsFalse "ip.v4.with.question@[123.145.178.90]?"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1074 - assertIsFalse "ip.v4.with.question@?[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1075 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1076 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1077 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1078 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1079 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1080 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90]`"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1081 - assertIsFalse "ip.v4.with.grave-accent@`[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1082 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1083 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1084 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1085 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1086 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1087 - assertIsFalse "ip.v4.with.hash@[123.145.178.90]#"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1088 - assertIsFalse "ip.v4.with.hash@#[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1089 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1090 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1091 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1092 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1093 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1094 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90]%"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1095 - assertIsFalse "ip.v4.with.percentage@%[123.145.178.90]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1096 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1097 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1098 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1099 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1100 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1101 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90]|"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1102 - assertIsFalse "ip.v4.with.pipe@|[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1103 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1104 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1105 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1106 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1107 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1108 - assertIsFalse "ip.v4.with.plus@[123.145.178.90]+"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1109 - assertIsFalse "ip.v4.with.plus@+[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1110 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1111 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1112 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1113 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1114 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1115 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]{"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1116 - assertIsFalse "ip.v4.with.leftbracket@{[123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1117 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1118 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1119 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1120 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1121 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1122 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]}"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1123 - assertIsFalse "ip.v4.with.rightbracket@}[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1124 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1125 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1126 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1127 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1128 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1129 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]("                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1130 - assertIsFalse "ip.v4.with.leftbracket@([123.145.178.90]"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1131 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1132 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1133 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1134 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1135 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1136 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90])"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1137 - assertIsFalse "ip.v4.with.rightbracket@)[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1138 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1139 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1140 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1141 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1142 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1143 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]["                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1144 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1145 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1146 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1147 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1148 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1149 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1150 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1151 - assertIsFalse "ip.v4.with.rightbracket@][123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1152 - assertIsFalse "ip.v4.with.lower.than@[123.14<5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1153 - assertIsFalse "ip.v4.with.lower.than@[123.145<.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1154 - assertIsFalse "ip.v4.with.lower.than@[123.145.<178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1155 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90<]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1156 - assertIsFalse "ip.v4.with.lower.than@[<123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1157 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90]<"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1158 - assertIsFalse "ip.v4.with.lower.than@<[123.145.178.90]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1159 - assertIsFalse "ip.v4.with.greater.than@[123.14>5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1160 - assertIsFalse "ip.v4.with.greater.than@[123.145>.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1161 - assertIsFalse "ip.v4.with.greater.than@[123.145.>178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1162 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90>]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1163 - assertIsFalse "ip.v4.with.greater.than@[>123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1164 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90]>"                            =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1165 - assertIsFalse "ip.v4.with.greater.than@>[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1166 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1167 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1168 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1169 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1170 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1171 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90]~"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1172 - assertIsFalse "ip.v4.with.tilde@~[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1173 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1174 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1175 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1176 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1177 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1178 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1179 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1180 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1181 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1182 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1183 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1184 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1185 - assertIsFalse "ip.v4.with.xor@[123.145.178.90]^"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1186 - assertIsFalse "ip.v4.with.xor@^[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1187 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1188 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1189 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1190 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1191 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1192 - assertIsFalse "ip.v4.with.colon@[123.145.178.90]:"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1193 - assertIsFalse "ip.v4.with.colon@:[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1194 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1195 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1196 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1197 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1198 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1199 - assertIsFalse "ip.v4.with.space@[123.145.178.90] "                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1200 - assertIsFalse "ip.v4.with.space@ [123.145.178.90]"                                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1201 - assertIsFalse "ip.v4.with.dot@[123.14.5.178.90]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1202 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1203 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1204 - assertIsFalse "ip.v4.with.dot@[123.145.178.90.]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1205 - assertIsFalse "ip.v4.with.dot@[.123.145.178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1206 - assertIsFalse "ip.v4.with.dot@[123.145.178.90]."                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1207 - assertIsFalse "ip.v4.with.dot@.[123.145.178.90]"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1208 - assertIsFalse "ip.v4.with.comma@[123.14,5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1209 - assertIsFalse "ip.v4.with.comma@[123.145,.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1210 - assertIsFalse "ip.v4.with.comma@[123.145.,178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1211 - assertIsFalse "ip.v4.with.comma@[123.145.178.90,]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1212 - assertIsFalse "ip.v4.with.comma@[,123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1213 - assertIsFalse "ip.v4.with.comma@[123.145.178.90],"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1214 - assertIsFalse "ip.v4.with.comma@,[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1215 - assertIsFalse "ip.v4.with.at@[123.14@5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1216 - assertIsFalse "ip.v4.with.at@[123.145@.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1217 - assertIsFalse "ip.v4.with.at@[123.145.@178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1218 - assertIsFalse "ip.v4.with.at@[123.145.178.90@]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1219 - assertIsFalse "ip.v4.with.at@[@123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1220 - assertIsFalse "ip.v4.with.at@[123.145.178.90]@"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1221 - assertIsFalse "ip.v4.with.at@@[123.145.178.90]"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1222 - assertIsFalse "ip.v4.with.paragraph@[123.14§5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1223 - assertIsFalse "ip.v4.with.paragraph@[123.145§.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1224 - assertIsFalse "ip.v4.with.paragraph@[123.145.§178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1225 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90§]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1226 - assertIsFalse "ip.v4.with.paragraph@[§123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1227 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90]§"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1228 - assertIsFalse "ip.v4.with.paragraph@§[123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1229 - assertIsFalse "ip.v4.with.double.quote@[123.14'5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1230 - assertIsFalse "ip.v4.with.double.quote@[123.145'.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1231 - assertIsFalse "ip.v4.with.double.quote@[123.145.'178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1232 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90']"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1233 - assertIsFalse "ip.v4.with.double.quote@['123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1234 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]'"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1235 - assertIsFalse "ip.v4.with.double.quote@'[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1236 - assertIsFalse "ip.v4.with.double.quote@[123.14\"5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1237 - assertIsFalse "ip.v4.with.double.quote@[123.145\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1238 - assertIsFalse "ip.v4.with.double.quote@[123.145.\"178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1239 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90\"]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1240 - assertIsFalse "ip.v4.with.double.quote@[\"123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1241 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]\""                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1242 - assertIsFalse "ip.v4.with.double.quote@\"[123.145.178.90]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1243 - assertIsFalse "ip.v4.with.empty.bracket@[123.14()5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1244 - assertIsFalse "ip.v4.with.empty.bracket@[123.145().178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1245 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.()178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1246 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90()]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1247 - assertIsFalse "ip.v4.with.empty.bracket@[()123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1248 - assertIsTrue  "ip.v4.with.empty.bracket@[123.145.178.90]()"                          =   2 =  OK 
     *  1249 - assertIsTrue  "ip.v4.with.empty.bracket@()[123.145.178.90]"                          =   2 =  OK 
     *  1250 - assertIsFalse "ip.v4.with.empty.bracket@[123.14{}5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1251 - assertIsFalse "ip.v4.with.empty.bracket@[123.145{}.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1252 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.{}178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1253 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90{}]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1254 - assertIsFalse "ip.v4.with.empty.bracket@[{}123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1255 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]{}"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1256 - assertIsFalse "ip.v4.with.empty.bracket@{}[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1257 - assertIsFalse "ip.v4.with.empty.bracket@[123.14[]5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1258 - assertIsFalse "ip.v4.with.empty.bracket@[123.145[].178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1259 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.[]178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1260 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90[]]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1261 - assertIsFalse "ip.v4.with.empty.bracket@[[]123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1262 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90][]"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1263 - assertIsFalse "ip.v4.with.empty.bracket@[][123.145.178.90]"                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1264 - assertIsFalse "ip.v4.with.empty.bracket@[123.14<>5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1265 - assertIsFalse "ip.v4.with.empty.bracket@[123.145<>.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1266 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.<>178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1267 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90<>]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1268 - assertIsFalse "ip.v4.with.empty.bracket@[<>123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1269 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]<>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1270 - assertIsFalse "ip.v4.with.empty.bracket@<>[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1271 - assertIsFalse "ip.v4.with.false.bracket1@[123.14)(5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1272 - assertIsFalse "ip.v4.with.false.bracket1@[123.145)(.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1273 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.)(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1274 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90)(]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1275 - assertIsFalse "ip.v4.with.false.bracket1@[)(123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1276 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90])("                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1277 - assertIsFalse "ip.v4.with.false.bracket1@)([123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1278 - assertIsFalse "ip.v4.with.false.bracket2@[123.14}{5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1279 - assertIsFalse "ip.v4.with.false.bracket2@[123.145}{.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1280 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.}{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1281 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90}{]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1282 - assertIsFalse "ip.v4.with.false.bracket2@[}{123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1283 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90]}{"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1284 - assertIsFalse "ip.v4.with.false.bracket2@}{[123.145.178.90]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1285 - assertIsFalse "ip.v4.with.false.bracket3@[123.14][5.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1286 - assertIsFalse "ip.v4.with.false.bracket3@[123.145][.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1287 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.][178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1288 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90][]"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1289 - assertIsFalse "ip.v4.with.false.bracket3@[][123.145.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1290 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90]]["                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1291 - assertIsFalse "ip.v4.with.false.bracket3@][[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1292 - assertIsFalse "ip.v4.with.false.bracket4@[123.14><5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1293 - assertIsFalse "ip.v4.with.false.bracket4@[123.145><.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1294 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.><178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1295 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90><]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1296 - assertIsFalse "ip.v4.with.false.bracket4@[><123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1297 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90]><"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1298 - assertIsFalse "ip.v4.with.false.bracket4@><[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1299 - assertIsFalse "ip.v4.with.number0@[123.1405.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1300 - assertIsFalse "ip.v4.with.number0@[123.1450.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1301 - assertIsFalse "ip.v4.with.number0@[123.145.0178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1302 - assertIsFalse "ip.v4.with.number0@[123.145.178.900]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1303 - assertIsFalse "ip.v4.with.number0@[0123.145.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1304 - assertIsFalse "ip.v4.with.number0@[123.145.178.90]0"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1305 - assertIsFalse "ip.v4.with.number0@0[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1306 - assertIsFalse "ip.v4.with.number9@[123.1495.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1307 - assertIsFalse "ip.v4.with.number9@[123.1459.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1308 - assertIsFalse "ip.v4.with.number9@[123.145.9178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1309 - assertIsFalse "ip.v4.with.number9@[123.145.178.909]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1310 - assertIsFalse "ip.v4.with.number9@[9123.145.178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1311 - assertIsFalse "ip.v4.with.number9@[123.145.178.90]9"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1312 - assertIsFalse "ip.v4.with.number9@9[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1313 - assertIsFalse "ip.v4.with.numbers@[123.1401234567895.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1314 - assertIsFalse "ip.v4.with.numbers@[123.1450123456789.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1315 - assertIsFalse "ip.v4.with.numbers@[123.145.0123456789178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1316 - assertIsFalse "ip.v4.with.numbers@[123.145.178.900123456789]"                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1317 - assertIsFalse "ip.v4.with.numbers@[0123456789123.145.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1318 - assertIsFalse "ip.v4.with.numbers@[123.145.178.90]0123456789"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1319 - assertIsFalse "ip.v4.with.numbers@0123456789[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1320 - assertIsFalse "ip.v4.with.slash@[123.14\5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1321 - assertIsFalse "ip.v4.with.slash@[123.145\.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1322 - assertIsFalse "ip.v4.with.slash@[123.145.\178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1323 - assertIsFalse "ip.v4.with.slash@[123.145.178.90\]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1324 - assertIsFalse "ip.v4.with.slash@[\123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1325 - assertIsFalse "ip.v4.with.slash@[123.145.178.90]\"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1326 - assertIsFalse "ip.v4.with.slash@\[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1327 - assertIsFalse "ip.v4.with.byte.overflow@[123.149995.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1328 - assertIsFalse "ip.v4.with.byte.overflow@[123.145999.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1329 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.999178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1330 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90999]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1331 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90]999"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1332 - assertIsFalse "ip.v4.with.byte.overflow@[999123.145.178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1333 - assertIsFalse "ip.v4.with.byte.overflow@999[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1334 - assertIsFalse "ip.v4.with.no.hex.number@[123.14xyz5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1335 - assertIsFalse "ip.v4.with.no.hex.number@[123.145xyz.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1336 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.xyz178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1337 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90xyz]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1338 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90]xyz"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1339 - assertIsFalse "ip.v4.with.no.hex.number@[xyz123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1340 - assertIsFalse "ip.v4.with.no.hex.number@xyz[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1341 - assertIsFalse "ip.v4.with.string@[123.14\"str\"5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1342 - assertIsFalse "ip.v4.with.string@[123.145\"str\".178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1343 - assertIsFalse "ip.v4.with.string@[123.145.\"str\"178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1344 - assertIsFalse "ip.v4.with.string@[123.145.178.90\"str\"]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1345 - assertIsFalse "ip.v4.with.string@[123.145.178.90]\"str\""                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1346 - assertIsFalse "ip.v4.with.string@[\"str\"123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1347 - assertIsFalse "ip.v4.with.string@\"str\"[123.145.178.90]"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1348 - assertIsFalse "ip.v4.with.comment@[123.14(comment)5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1349 - assertIsFalse "ip.v4.with.comment@[123.145(comment).178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1350 - assertIsFalse "ip.v4.with.comment@[123.145.(comment)178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1351 - assertIsFalse "ip.v4.with.comment@[123.145.178.90(comment)]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1352 - assertIsTrue  "ip.v4.with.comment@[123.145.178.90](comment)"                         =   2 =  OK 
     *  1353 - assertIsFalse "ip.v4.with.comment@[(comment)123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1354 - assertIsTrue  "ip.v4.with.comment@(comment)[123.145.178.90]"                         =   2 =  OK 
     *  1355 - assertIsTrue  "email@[123.123.123.123]"                                              =   2 =  OK 
     *  1356 - assertIsFalse "email@111.222.333"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1357 - assertIsFalse "email@111.222.333.256"                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1358 - assertIsFalse "email@[123.123.123.123"                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1359 - assertIsFalse "email@[123.123.123].123"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1360 - assertIsFalse "email@123.123.123.123]"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1361 - assertIsFalse "email@123.123.[123.123]"                                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1362 - assertIsFalse "ab@988.120.150.10"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1363 - assertIsFalse "ab@120.256.256.120"                                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1364 - assertIsFalse "ab@120.25.1111.120"                                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1365 - assertIsFalse "ab@[188.120.150.10"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1366 - assertIsFalse "ab@188.120.150.10]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1367 - assertIsFalse "ab@[188.120.150.10].com"                                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1368 - assertIsTrue  "ab@188.120.150.10"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1369 - assertIsTrue  "ab@1.0.0.10"                                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1370 - assertIsTrue  "ab@120.25.254.120"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1371 - assertIsTrue  "ab@01.120.150.1"                                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1372 - assertIsTrue  "ab@88.120.150.021"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1373 - assertIsTrue  "ab@88.120.150.01"                                                     =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1374 - assertIsTrue  "email@123.123.123.123"                                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1375 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                           =   4 =  OK 
     *  1376 - assertIsFalse "ABC.DEF@[IP"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1377 - assertIsFalse "ABC.DEF@[IPv6]"                                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1378 - assertIsFalse "ABC.DEF@[IPv6:]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1379 - assertIsFalse "ABC.DEF@[IPv6:"                                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1380 - assertIsFalse "ABC.DEF@[IPv6::]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1381 - assertIsFalse "ABC.DEF@[IPv6::"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1382 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1383 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1384 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1385 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1386 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1387 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                 =   4 =  OK 
     *  1388 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                               =   4 =  OK 
     *  1389 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                            =   4 =  OK 
     *  1390 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                           =   4 =  OK 
     *  1391 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                           =   4 =  OK 
     *  1392 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                         =   4 =  OK 
     *  1393 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1394 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                       =   4 =  OK 
     *  1395 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                     =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1396 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                               =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1397 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                             =   4 =  OK 
     *  1398 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1399 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1400 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1401 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1402 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1403 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                        =   4 =  OK 
     *  1404 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1405 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1406 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1407 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1408 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1409 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1410 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1411 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1412 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1413 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1414 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1415 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1416 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1417 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1418 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1419 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1420 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1421 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1422 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1423 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1424 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1425 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1426 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1427 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1428 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1429 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1430 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1431 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1432 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1433 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1434 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1435 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1436 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1437 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1438 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1439 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1440 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1441 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1442 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1443 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1444 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1445 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1446 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1447 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1448 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1449 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1450 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1451 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1452 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1453 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1454 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1455 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1456 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1457 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1458 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1459 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1460 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1461 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1462 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1463 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1464 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1465 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1466 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1467 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1468 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1469 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1470 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1472 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1473 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1474 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1475 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1476 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1477 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1478 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1479 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1480 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1481 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1482 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1483 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1484 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1485 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1486 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1487 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1488 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1489 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1490 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1491 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1492 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1493 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1494 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1495 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1496 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1497 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1498 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1499 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1500 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1501 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1502 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1503 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1504 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1505 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1507 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1508 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1509 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1510 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1511 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1512 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1513 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1514 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1515 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1516 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1517 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1518 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1519 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1520 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1521 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1522 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1523 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1524 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1525 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1526 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1527 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1528 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1529 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1530 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1531 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1532 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1533 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1534 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1535 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1536 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1537 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1538 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1539 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1540 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1541 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1542 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1543 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1544 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1545 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1546 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1547 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                              =   4 =  OK 
     *  1548 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1549 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1550 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                              =   4 =  OK 
     *  1551 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1552 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1553 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1554 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1555 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1556 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1557 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1558 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1559 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1560 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1561 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1562 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1563 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1564 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1565 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1567 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1568 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1570 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1571 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1573 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1574 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1575 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1576 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1577 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1578 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1579 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1580 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1581 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1582 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1583 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1584 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1585 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1586 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1587 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1588 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1589 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1590 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1591 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1592 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsFalse "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1594 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\""                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1595 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1596 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1597 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1598 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1599 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                     =   4 =  OK 
     *  1600 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                     =   4 =  OK 
     *  1601 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1602 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1603 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1604 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1605 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1606 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1607 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1608 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1609 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1610 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1611 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1612 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1613 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1614 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1615 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1616 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1617 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1618 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1619 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1620 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1621 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1622 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1623 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1624 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1625 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1626 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1627 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1628 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1629 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1630 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1631 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1632 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1633 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1634 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1635 - assertIsFalse "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1636 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]]["                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1637 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1638 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1639 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1640 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1641 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1642 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1643 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1644 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1645 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1646 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1647 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1648 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1649 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1650 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1651 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1652 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1653 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1654 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1655 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1656 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1657 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:29993:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1658 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1659 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1660 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1661 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1662 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1663 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1664 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1665 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1666 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1667 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1668 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1669 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1670 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1671 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1672 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1673 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1674 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1675 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1676 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1677 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1678 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1679 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1680 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                    =   4 =  OK 
     *  1681 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                    =   4 =  OK 
     *  1682 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1683 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                        =   4 =  OK 
     *  1684 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1685 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1686 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1687 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1688 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1689 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"               =   4 =  OK 
     *  1690 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"               =   4 =  OK 
     *  1691 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"               =   4 =  OK 
     *  1692 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                              =   4 =  OK 
     *  1693 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                               =   4 =  OK 
     *  1694 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1695 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1696 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1697 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1698 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                       =   4 =  OK 
     *  1699 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                       =   4 =  OK 
     *  1700 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                           =   4 =  OK 
     *  1701 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                           =   4 =  OK 
     *  1702 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1703 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                        =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1704 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1705 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1706 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1707 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1708 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1709 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1710 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *  1711 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1712 - assertIsTrue  "\"ABC\".\"DEF\"@GHI.DE"                                               =   1 =  OK 
     *  1713 - assertIsFalse "-\"ABC\".\"DEF\"@GHI.DE"                                              = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
     *  1714 - assertIsFalse "\"ABC\"-.\"DEF\"@GHI.DE"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1715 - assertIsFalse "\"ABC\".-\"DEF\"@GHI.DE"                                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1716 - assertIsFalse ".\"ABC\".\"DEF\"@GHI.DE"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1717 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1718 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1719 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                 =   1 =  OK 
     *  1720 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1721 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1722 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1723 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1724 - assertIsFalse "\"\"@GHI.DE"                                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1725 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1726 - assertIsFalse "A@G\"HI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1727 - assertIsFalse "\"@GHI.DE"                                                            =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1728 - assertIsFalse "ABC.DEF.\""                                                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1729 - assertIsFalse "ABC.DEF@\"\""                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1730 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1731 - assertIsFalse "ABC.DEF@GHI.DE\""                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1732 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1733 - assertIsFalse "\"Escape.Sequenz.Ende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1734 - assertIsFalse "ABC.DEF\"GHI.DE"                                                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1735 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1736 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1737 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1738 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1739 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1740 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                  =   1 =  OK 
     *  1741 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                           =   1 =  OK 
     *  1742 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1743 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1744 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1745 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1746 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1747 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1748 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1749 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1750 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1751 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1752 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1753 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1754 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1755 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1756 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1757 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1758 - assertIsFalse "\"\".ABC.DEF@GHI.DE"                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1759 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1760 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1761 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                               =   1 =  OK 
     *  1762 - assertIsFalse "\"Ende.am.Eingabeende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1763 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1764 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                 =   1 =  OK 
     *  1765 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1766 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1767 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1768 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                =   1 =  OK 
     *  1769 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                        =   1 =  OK 
     *  1770 - assertIsFalse "\"Joe Smith\" email@domain.com"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1771 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1772 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *  1773 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                     =   6 =  OK 
     *  1774 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1775 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                     =   6 =  OK 
     *  1776 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                 =   6 =  OK 
     *  1777 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                           =   6 =  OK 
     *  1778 - assertIsFalse "ABC.DEF@             (MNO)"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1779 - assertIsFalse "ABC.DEF@   .         (MNO)"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1780 - assertIsFalse "ABC.DEF              (MNO)"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1781 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                           =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1782 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1783 - assertIsFalse "ABC.DEF@GHI.JKL          "                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1784 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1785 - assertIsFalse "("                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1786 - assertIsFalse ")"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1787 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                   =   6 =  OK 
     *  1788 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                    =   6 =  OK 
     *  1789 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                    =   6 =  OK 
     *  1790 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                    =   6 =  OK 
     *  1791 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                    =   6 =  OK 
     *  1792 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1793 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1794 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1795 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1796 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1797 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1798 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1799 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1800 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1801 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1802 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1803 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1804 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1805 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                   = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1806 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                     =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1807 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1808 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1809 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1810 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1811 - assertIsFalse "ABC(DEF@GHI.JKL"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1812 - assertIsFalse "ABC.DEF@GHI)JKL"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1813 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1814 - assertIsFalse "ABC.DEF)@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1815 - assertIsFalse "ABC(DEF@GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1816 - assertIsFalse "ABC(DEF.GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1817 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1818 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1819 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1820 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1821 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1822 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1823 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                           =   2 =  OK 
     *  1824 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                          = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  1825 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                           =   2 =  OK 
     *  1826 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                       =   2 =  OK 
     *  1827 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1828 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                               =   4 =  OK 
     *  1829 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                               =   4 =  OK 
     *  1830 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                           =   4 =  OK 
     *  1831 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1832 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                            =   6 =  OK 
     *  1833 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                            =   6 =  OK 
     *  1834 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                            = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1835 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1836 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1837 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1838 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1839 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1840 - assertIsFalse "john.smith@exampl(comment)e.com"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1841 - assertIsFalse "john.s(comment)mith@example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1842 - assertIsFalse "john.smith(comment)@(comment)example.com"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1843 - assertIsFalse "john.smith(com@ment)example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1844 - assertIsFalse "email( (nested) )@plus.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1845 - assertIsFalse "email)mirror(@plus.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1846 - assertIsFalse "email@plus.com (not closed comment"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1847 - assertIsFalse "email(with @ in comment)plus.com"                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1848 - assertIsTrue  "email@domain.com (joe Smith)"                                         =   6 =  OK 
     *  1849 - assertIsFalse "a@abc(bananas)def.com"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1850 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                            =   0 =  OK 
     *  1851 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                            =   0 =  OK 
     *  1852 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                             =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1853 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                             =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1854 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                        =   0 =  OK 
     *  1855 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                  =   1 =  OK 
     *  1856 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                          =   1 =  OK 
     *  1857 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1858 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1859 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1860 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1861 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1862 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1863 - assertIsFalse "ABC DEF <A@A>"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1864 - assertIsFalse "<A@A> ABC DEF"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1865 - assertIsFalse "ABC DEF <>"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1866 - assertIsFalse "<> ABC DEF"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1867 - assertIsFalse "<"                                                                    =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1868 - assertIsFalse ">"                                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1869 - assertIsFalse "<         >"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1870 - assertIsFalse "< <     > >"                                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1871 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                    =   0 =  OK 
     *  1872 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                   = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1873 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1874 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                   =   0 =  OK 
     *  1875 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1876 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                   =   0 =  OK 
     *  1877 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1878 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                           =   6 =  OK 
     *  1879 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1880 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                          = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1881 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                          =   6 =  OK 
     *  1882 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                          = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1883 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                          =   6 =  OK 
     *  1884 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                          = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1885 - assertIsTrue  "Joe Smith <email@domain.com>"                                         =   0 =  OK 
     *  1886 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1887 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1888 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1889 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1890 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"      =   9 =  OK 
     *  1891 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "     =   9 =  OK 
     *  1892 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1893 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1894 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1895 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1896 - assertIsFalse "Test |<gaaf <email@domain.com>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1897 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1898 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1899 - assertIsFalse "<null>@mail.com"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1900 - assertIsFalse "email.adress@domain.com <display name>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1901 - assertIsFalse "eimail.adress@domain.com <eimail.adress@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1902 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1903 - assertIsFalse "<eimail>.<adress>@domain.com"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1904 - assertIsFalse "<eimail>.<adress> email.adress@domain.com"                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *  1905 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1906 - assertIsFalse "A@B.C"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1907 - assertIsFalse "A@COM"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1908 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                      =   0 =  OK 
     *  1909 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  1910 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1911 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1912 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  1913 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1914 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  1915 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1916 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld> True64 " =   0 =  OK 
     *  1917 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1918 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  1919 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1920 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *  1921 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  1922 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1923 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1924 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1925 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1926 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1927 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1928 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1929 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1930 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1931 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1932 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1933 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1934 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
     *  1935 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  1936 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  1937 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1938 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1939 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  1940 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1941 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1942 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1943 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
     *  1944 - assertIsTrue  "email@domain.topleveldomain"                                          =   0 =  OK 
     *  1945 - assertIsTrue  "email@email.email.mydomain"                                           =   0 =  OK 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     *  1946 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                     =   6 =  OK 
     *  1947 - assertIsTrue  "\"MaxMustermann\"@example.com"                                        =   1 =  OK 
     *  1948 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                           =   1 =  OK 
     *  1949 - assertIsTrue  "\".John.Doe\"@example.com"                                            =   1 =  OK 
     *  1950 - assertIsTrue  "\"John.Doe.\"@example.com"                                            =   1 =  OK 
     *  1951 - assertIsTrue  "\"John..Doe\"@example.com"                                            =   1 =  OK 
     *  1952 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1953 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1954 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1955 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1956 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  1957 - assertIsTrue  "jsmith@[192.168.2.1]"                                                 =   2 =  OK 
     *  1958 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                            =   4 =  OK 
     *  1959 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                    =   0 =  OK 
     *  1960 - assertIsTrue  "Marc Dupont <md118@example.com>"                                      =   0 =  OK 
     *  1961 - assertIsTrue  "simple@example.com"                                                   =   0 =  OK 
     *  1962 - assertIsTrue  "very.common@example.com"                                              =   0 =  OK 
     *  1963 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                       =   0 =  OK 
     *  1964 - assertIsTrue  "other.email-with-hyphen@example.com"                                  =   0 =  OK 
     *  1965 - assertIsTrue  "fully-qualified-domain@example.com"                                   =   0 =  OK 
     *  1966 - assertIsTrue  "user.name+tag+sorting@example.com"                                    =   0 =  OK 
     *  1967 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  1968 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                     =   0 =  OK 
     *  1969 - assertIsTrue  "x@example.com"                                                        =   0 =  OK 
     *  1970 - assertIsTrue  "info@firma.org"                                                       =   0 =  OK 
     *  1971 - assertIsTrue  "example-indeed@strange-example.com"                                   =   0 =  OK 
     *  1972 - assertIsTrue  "admin@mailserver1"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1973 - assertIsTrue  "example@s.example"                                                    =   0 =  OK 
     *  1974 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  1975 - assertIsTrue  "\"john..doe\"@example.org"                                            =   1 =  OK 
     *  1976 - assertIsTrue  "mailhost!username@example.org"                                        =   0 =  OK 
     *  1977 - assertIsTrue  "user%example.com@example.org"                                         =   0 =  OK 
     *  1978 - assertIsTrue  "joe25317@NOSPAMexample.com"                                           =   0 =  OK 
     *  1979 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  1980 - assertIsTrue  "nama@contoh.com"                                                      =   0 =  OK 
     *  1981 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  1982 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1983 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1984 - assertIsFalse "Abc..123@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1985 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1986 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1987 - assertIsFalse "just\"not\"right@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1988 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1989 - assertIsFalse "this\ still\\"not\\allowed@example.com"                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1990 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1991 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"   =   0 =  OK 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     *  1992 - assertIsFalse "nolocalpart.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1993 - assertIsFalse "test@example.com test"                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1994 - assertIsFalse "user  name@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1995 - assertIsFalse "user   name@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1996 - assertIsFalse "example.@example.co.uk"                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1997 - assertIsFalse "example@example@example.co.uk"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1998 - assertIsFalse "(test_exampel@example.fr}"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1999 - assertIsFalse "example(example)example@example.co.uk"                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2000 - assertIsFalse ".example@localhost"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2001 - assertIsFalse "ex\ample@localhost"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2002 - assertIsFalse "example@local\host"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2003 - assertIsFalse "example@localhost."                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2004 - assertIsFalse "user name@example.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2005 - assertIsFalse "username@ example . com"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2006 - assertIsFalse "example@(fake}.com"                                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2007 - assertIsFalse "example@(fake.com"                                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2008 - assertIsTrue  "username@example.com"                                                 =   0 =  OK 
     *  2009 - assertIsTrue  "usern.ame@example.com"                                                =   0 =  OK 
     *  2010 - assertIsFalse "user[na]me@example.com"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2011 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2012 - assertIsFalse "\"\\"@iana.org"                                                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2013 - assertIsFalse "\"test\"test@iana.org"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2014 - assertIsFalse "\"test\"\"test\"@iana.org"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2015 - assertIsTrue  "\"test\".\"test\"@iana.org"                                           =   1 =  OK 
     *  2016 - assertIsTrue  "\"test\".test@iana.org"                                               =   1 =  OK 
     *  2017 - assertIsFalse "\"test\\"@iana.org"                                                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2018 - assertIsFalse "\r\ntest@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2019 - assertIsFalse "\r\n test@iana.org"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2020 - assertIsFalse "\r\n \r\ntest@iana.org"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2021 - assertIsFalse "\r\n \r\n test@iana.org"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2022 - assertIsFalse "test@iana.org \r\n"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2023 - assertIsFalse "test@iana.org \r\n "                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2024 - assertIsFalse "test@iana.org \r\n \r\n"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2025 - assertIsFalse "test@iana.org \r\n\r\n"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2026 - assertIsFalse "test@iana.org  \r\n\r\n "                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2027 - assertIsFalse "test@iana/icann.org"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2028 - assertIsFalse "test@foo;bar.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2029 - assertIsFalse "a@test.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2030 - assertIsFalse "comment)example@example.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2031 - assertIsFalse "comment(example))@example.com"                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2032 - assertIsFalse "example@example)comment.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2033 - assertIsFalse "example@example(comment)).com"                                        = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2034 - assertIsFalse "example@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2035 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2036 - assertIsFalse "exam(ple@exam).ple"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2037 - assertIsFalse "example@(example))comment.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2038 - assertIsTrue  "example@example.com"                                                  =   0 =  OK 
     *  2039 - assertIsTrue  "example@example.co.uk"                                                =   0 =  OK 
     *  2040 - assertIsTrue  "example_underscore@example.fr"                                        =   0 =  OK 
     *  2041 - assertIsTrue  "exam'ple@example.com"                                                 =   0 =  OK 
     *  2042 - assertIsTrue  "exam\ ple@example.com"                                                =   0 =  OK 
     *  2043 - assertIsFalse "example((example))@fakedfake.co.uk"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2044 - assertIsFalse "example@faked(fake).co.uk"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2045 - assertIsTrue  "example+@example.com"                                                 =   0 =  OK 
     *  2046 - assertIsTrue  "example@with-hyphen.example.com"                                      =   0 =  OK 
     *  2047 - assertIsTrue  "with-hyphen@example.com"                                              =   0 =  OK 
     *  2048 - assertIsTrue  "example@1leadingnum.example.com"                                      =   0 =  OK 
     *  2049 - assertIsTrue  "1leadingnum@example.com"                                              =   0 =  OK 
     *  2050 - assertIsTrue  "инфо@письмо.рф"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2051 - assertIsTrue  "\"username\"@example.com"                                             =   1 =  OK 
     *  2052 - assertIsTrue  "\"user.name\"@example.com"                                            =   1 =  OK 
     *  2053 - assertIsTrue  "\"user name\"@example.com"                                            =   1 =  OK 
     *  2054 - assertIsTrue  "\"user@name\"@example.com"                                            =   1 =  OK 
     *  2055 - assertIsFalse "\"\a\"@iana.org"                                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2056 - assertIsTrue  "\"test\ test\"@iana.org"                                              =   1 =  OK 
     *  2057 - assertIsFalse "\"\"@iana.org"                                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2058 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2059 - assertIsTrue  "\"\\"\"@iana.org"                                                     =   1 =  OK 
     *  2060 - assertIsTrue  "example@localhost"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     *  2061 - assertIsFalse " check@this.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2062 - assertIsFalse " email@example.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2063 - assertIsTrue  "!def!xyz%abc@example.com"                                             =   0 =  OK 
     *  2064 - assertIsTrue  "!sd@gh.com"                                                           =   0 =  OK 
     *  2065 - assertIsTrue  "$A12345@example.com"                                                  =   0 =  OK 
     *  2066 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                =   0 =  OK 
     *  2067 - assertIsTrue  "%2@gmail.com"                                                         =   0 =  OK 
     *  2068 - assertIsTrue  "--@ooo.ooo"                                                           =   0 =  OK 
     *  2069 - assertIsTrue  "-@bde.cc"                                                             =   0 =  OK 
     *  2070 - assertIsTrue  "-asd@das.com"                                                         =   0 =  OK 
     *  2071 - assertIsFalse ".....@a...."                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2072 - assertIsFalse "..@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2073 - assertIsFalse ".@s.dd"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2074 - assertIsFalse ".a@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2075 - assertIsFalse ".dot@example.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2076 - assertIsFalse ".email@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2077 - assertIsFalse ".journaldev@journaldev.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2078 - assertIsFalse ".xxxx@mysite.org"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2079 - assertIsTrue  "1234567890@domain.com"                                                =   0 =  OK 
     *  2080 - assertIsFalse "123@$.xyz"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2081 - assertIsTrue  "1@domain.com"                                                         =   0 =  OK 
     *  2082 - assertIsTrue  "1@gmail.com"                                                          =   0 =  OK 
     *  2083 - assertIsTrue  "1_example@something.gmail.com"                                        =   0 =  OK 
     *  2084 - assertIsTrue  "2@bde.cc"                                                             =   0 =  OK 
     *  2085 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2086 - assertIsTrue  "<boss@nil.test>"                                                      =   0 =  OK 
     *  2087 - assertIsFalse "@b.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2088 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2089 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2090 - assertIsFalse "@you.me.net"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2091 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2092 - assertIsTrue  "Abc.123@example.com"                                                  =   0 =  OK 
     *  2093 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2094 - assertIsTrue  "Abc@10.42.0.1"                                                        =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2095 - assertIsTrue  "Abc@example.com"                                                      =   0 =  OK 
     *  2096 - assertIsFalse "Abc@example.com."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2097 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                 =   0 =  OK 
     *  2098 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2099 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2100 - assertIsFalse "Foobar Some@thing.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2101 - assertIsTrue  "Fred\ Bloggs@example.com"                                             =   0 =  OK 
     *  2102 - assertIsTrue  "Joe.\\Blow@example.com"                                               =   0 =  OK 
     *  2103 - assertIsFalse "MailTo:casesensitve@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2104 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                          =   0 =  OK 
     *  2105 - assertIsTrue  "Who? <one@y.test>"                                                    =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2106 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  2107 - assertIsTrue  "\"%2\"@gmail.com"                                                     =   1 =  OK 
     *  2108 - assertIsTrue  "\"Abc@def\"@example.com"                                              =   1 =  OK 
     *  2109 - assertIsTrue  "\"Abc\@def\"@example.com"                                             =   1 =  OK 
     *  2110 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2111 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                    =   1 =  OK 
     *  2112 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  2113 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  2114 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                         =   1 =  OK 
     *  2115 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                   =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2116 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2117 - assertIsFalse "\"Joe\Blow\"@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2118 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                            =   1 =  OK 
     *  2119 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                            =   1 =  OK 
     *  2120 - assertIsTrue  "\"a..b\"@gmail.com"                                                   =   1 =  OK 
     *  2121 - assertIsTrue  "\"a_b\"@gmail.com"                                                    =   1 =  OK 
     *  2122 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                         =   1 =  OK 
     *  2123 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                  =   1 =  OK 
     *  2124 - assertIsTrue  "\"foo\@bar\"@Something.com"                                           =   1 =  OK 
     *  2125 - assertIsTrue  "\"j\\"s\"@proseware.com"                                              =   1 =  OK 
     *  2126 - assertIsTrue  "\"myemail@sa\"@mple.com"                                              =   1 =  OK 
     *  2127 - assertIsFalse "\"qu@example.com"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2128 - assertIsFalse "\$A12345@example.com"                                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2129 - assertIsTrue  "_-_@bde.cc"                                                           =   0 =  OK 
     *  2130 - assertIsFalse "_@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2131 - assertIsTrue  "_@gmail.com"                                                          =   0 =  OK 
     *  2132 - assertIsTrue  "_dasd@sd.com"                                                         =   0 =  OK 
     *  2133 - assertIsTrue  "_dasd_das_@9.com"                                                     =   0 =  OK 
     *  2134 - assertIsTrue  "_somename@example.com"                                                =   0 =  OK 
     *  2135 - assertIsTrue  "a+b@bde.cc"                                                           =   0 =  OK 
     *  2136 - assertIsTrue  "a+b@c.com"                                                            =   0 =  OK 
     *  2137 - assertIsTrue  "a-b@bde.cc"                                                           =   0 =  OK 
     *  2138 - assertIsFalse "a..b@bde.cc"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2139 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2140 - assertIsTrue  "a.a@test.com"                                                         =   0 =  OK 
     *  2141 - assertIsTrue  "a.b@com"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2142 - assertIsFalse "a.b@example,co.de"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2143 - assertIsFalse "a.b@example,com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2144 - assertIsTrue  "a2@bde.cc"                                                            =   0 =  OK 
     *  2145 - assertIsFalse "a@.com"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2146 - assertIsTrue  "a@123.45.67.89"                                                       =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2147 - assertIsFalse "a@b."                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2148 - assertIsFalse "a@b.-de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2149 - assertIsFalse "a@b._de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2150 - assertIsTrue  "a@b.c.com"                                                            =   0 =  OK 
     *  2151 - assertIsTrue  "a@b.com"                                                              =   0 =  OK 
     *  2152 - assertIsTrue  "a@bc.com"                                                             =   0 =  OK 
     *  2153 - assertIsTrue  "a@bcom"                                                               =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2154 - assertIsFalse "a@bde-.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2155 - assertIsFalse "a@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2156 - assertIsFalse "a@bde_.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2157 - assertIsTrue  "a@domain.com"                                                         =   0 =  OK 
     *  2158 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2159 - assertIsTrue  "aaron@theinfo.org"                                                    =   0 =  OK 
     *  2160 - assertIsTrue  "ab@288.120.150.10.com"                                                =   0 =  OK 
     *  2161 - assertIsTrue  "ab@[120.254.254.120]"                                                 =   2 =  OK 
     *  2162 - assertIsFalse "ab@b+de.cc"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2163 - assertIsTrue  "ab@b-de.cc"                                                           =   0 =  OK 
     *  2164 - assertIsTrue  "ab@c.com"                                                             =   0 =  OK 
     *  2165 - assertIsFalse "ab@sd@dd"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2166 - assertIsTrue  "ab_c@bde.cc"                                                          =   0 =  OK 
     *  2167 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                       =   1 =  OK 
     *  2168 - assertIsTrue  "abc.xyz@gmail.com.in"                                                 =   0 =  OK 
     *  2169 - assertIsTrue  "abc123xyz@asdf.co.in"                                                 =   0 =  OK 
     *  2170 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                 =   0 =  OK 
     *  2171 - assertIsFalse "abc@def@example.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2172 - assertIsFalse "abc\"defghi\"xyz@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2173 - assertIsTrue  "abc\@def@example.com"                                                 =   0 =  OK 
     *  2174 - assertIsFalse "abc\@example.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2175 - assertIsFalse "abc\\"def\\"ghi@example.com"                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2176 - assertIsFalse "abc\\@def@example.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2177 - assertIsTrue  "abc\\@example.com"                                                    =   0 =  OK 
     *  2178 - assertIsTrue  "abcxyz123@qwert.com"                                                  =   0 =  OK 
     *  2179 - assertIsTrue  "alex@example.com"                                                     =   0 =  OK 
     *  2180 - assertIsTrue  "alireza@test.co.uk"                                                   =   0 =  OK 
     *  2181 - assertIsFalse "as3d@dac.coas-"                                                       =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2182 - assertIsTrue  "asd-@asd.com"                                                         =   0 =  OK 
     *  2183 - assertIsFalse "asd@dasd@asd.cm"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2184 - assertIsTrue  "begeddov@jfinity.com"                                                 =   0 =  OK 
     *  2185 - assertIsFalse "check@this..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2186 - assertIsTrue  "check@this.com"                                                       =   0 =  OK 
     *  2187 - assertIsFalse "check@thiscom"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2188 - assertIsTrue  "cog@wheel.com"                                                        =   0 =  OK 
     *  2189 - assertIsTrue  "customer/department=shipping@example.com"                             =   0 =  OK 
     *  2190 - assertIsTrue  "d._.___d@gmail.com"                                                   =   0 =  OK 
     *  2191 - assertIsTrue  "d.j@server1.proseware.com"                                            =   0 =  OK 
     *  2192 - assertIsTrue  "d.oy.smith@gmail.com"                                                 =   0 =  OK 
     *  2193 - assertIsTrue  "d23d@da9.co9"                                                         =   0 =  OK 
     *  2194 - assertIsTrue  "d_oy_smith@gmail.com"                                                 =   0 =  OK 
     *  2195 - assertIsFalse "da23@das..com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2196 - assertIsFalse "dad@sds"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2197 - assertIsTrue  "dasd-dasd@das.com.das"                                                =   0 =  OK 
     *  2198 - assertIsTrue  "dasd.dadas@dasd.com"                                                  =   0 =  OK 
     *  2199 - assertIsTrue  "dasd_-@jdas.com"                                                      =   0 =  OK 
     *  2200 - assertIsFalse "dasddas-@.com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2201 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2202 - assertIsTrue  "david.jones@proseware.com"                                            =   0 =  OK 
     *  2203 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2204 - assertIsTrue  "dda_das@das-dasd.com"                                                 =   0 =  OK 
     *  2205 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                         =   0 =  OK 
     *  2206 - assertIsTrue  "digit-only-domain@123.com"                                            =   0 =  OK 
     *  2207 - assertIsFalse "dot.@example.com"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2208 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2209 - assertIsTrue  "doysmith@gmail.com"                                                   =   0 =  OK 
     *  2210 - assertIsTrue  "drp@drp.cz"                                                           =   0 =  OK 
     *  2211 - assertIsTrue  "dsq!a?@das.com"                                                       =   0 =  OK 
     *  2212 - assertIsFalse "email..email@domain.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2213 - assertIsFalse "email.@domain.com"                                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2214 - assertIsFalse "email@.domain.com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2215 - assertIsFalse "email@domain"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2216 - assertIsFalse "email@domain..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2217 - assertIsTrue  "email@domain.co.de"                                                   =   0 =  OK 
     *  2218 - assertIsTrue  "email@domain.com"                                                     =   0 =  OK 
     *  2219 - assertIsFalse "email@domain.com."                                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2220 - assertIsFalse "email@example"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2221 - assertIsFalse "email@example..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2222 - assertIsTrue  "email@example.co.uk"                                                  =   0 =  OK 
     *  2223 - assertIsFalse "email@example.co.uk."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2224 - assertIsFalse "email@example.com "                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2225 - assertIsTrue  "email@example.com"                                                    =   0 =  OK 
     *  2226 - assertIsTrue  "email@mail.gmail.com"                                                 =   0 =  OK 
     *  2227 - assertIsTrue  "email@subdomain.domain.com"                                           =   0 =  OK 
     *  2228 - assertIsTrue  "example@example.co"                                                   =   0 =  OK 
     *  2229 - assertIsFalse "f...bar@gmail.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2230 - assertIsTrue  "f.f.f@bde.cc"                                                         =   0 =  OK 
     *  2231 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                =   0 =  OK 
     *  2232 - assertIsFalse "fdsa"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2233 - assertIsFalse "fdsa@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2234 - assertIsFalse "fdsa@fdsa"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2235 - assertIsFalse "fdsa@fdsa."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2236 - assertIsTrue  "first-name-last-name@d-a-n.com"                                       =   0 =  OK 
     *  2237 - assertIsTrue  "firstname+lastname@domain.com"                                        =   0 =  OK 
     *  2238 - assertIsTrue  "firstname-lastname@domain.com"                                        =   0 =  OK 
     *  2239 - assertIsTrue  "firstname.lastname@domain.com"                                        =   0 =  OK 
     *  2240 - assertIsFalse "foo.bar#gmail.co.u"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2241 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2242 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2243 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                            =   0 =  OK 
     *  2244 - assertIsFalse "foo~&(&)(@bar.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2245 - assertIsTrue  "futureTLD@somewhere.fooo"                                             =   0 =  OK 
     *  2246 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2247 - assertIsFalse "get_at_m.e@gmail"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2248 - assertIsFalse "hallo2ww22@example....caaaao"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2249 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2250 - assertIsFalse "hello world@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2251 - assertIsTrue  "hello.me_1@email.com"                                                 =   0 =  OK 
     *  2252 - assertIsTrue  "hello7___@ca.com.pt"                                                  =   0 =  OK 
     *  2253 - assertIsTrue  "info@ermaelan.com"                                                    =   0 =  OK 
     *  2254 - assertIsFalse "invalid.email.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2255 - assertIsFalse "invalid@email@domain.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2256 - assertIsFalse "j..s@proseware.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2257 - assertIsFalse "j.@server1.proseware.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2258 - assertIsTrue  "j.s@server1.proseware.com"                                            =   0 =  OK 
     *  2259 - assertIsTrue  "j@proseware.com9"                                                     =   0 =  OK 
     *  2260 - assertIsTrue  "j_9@[129.126.118.1]"                                                  =   2 =  OK 
     *  2261 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2262 - assertIsTrue  "jinujawad6s@gmail.com"                                                =   0 =  OK 
     *  2263 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  2264 - assertIsTrue  "jones@ms1.proseware.com"                                              =   0 =  OK 
     *  2265 - assertIsFalse "journaldev"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2266 - assertIsFalse "journaldev()*@gmail.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2267 - assertIsTrue  "journaldev+100@gmail.com"                                             =   0 =  OK 
     *  2268 - assertIsTrue  "journaldev-100@journaldev.net"                                        =   0 =  OK 
     *  2269 - assertIsTrue  "journaldev-100@yahoo-test.com"                                        =   0 =  OK 
     *  2270 - assertIsTrue  "journaldev-100@yahoo.com"                                             =   0 =  OK 
     *  2271 - assertIsFalse "journaldev..2002@gmail.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2272 - assertIsTrue  "journaldev.100@journaldev.com.au"                                     =   0 =  OK 
     *  2273 - assertIsTrue  "journaldev.100@yahoo.com"                                             =   0 =  OK 
     *  2274 - assertIsFalse "journaldev.@gmail.com"                                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2275 - assertIsTrue  "journaldev111@journaldev.com"                                         =   0 =  OK 
     *  2276 - assertIsFalse "journaldev123@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2277 - assertIsFalse "journaldev123@.com.com"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2278 - assertIsFalse "journaldev123@gmail.a"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2279 - assertIsFalse "journaldev@%*.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2280 - assertIsFalse "journaldev@.com.my"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2281 - assertIsTrue  "journaldev@1.com"                                                     =   0 =  OK 
     *  2282 - assertIsFalse "journaldev@gmail.com.1a"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2283 - assertIsTrue  "journaldev@gmail.com.com"                                             =   0 =  OK 
     *  2284 - assertIsFalse "journaldev@journaldev@gmail.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2285 - assertIsTrue  "journaldev@yahoo.com"                                                 =   0 =  OK 
     *  2286 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                    =   0 =  OK 
     *  2287 - assertIsTrue  "js#internal@proseware.com"                                            =   0 =  OK 
     *  2288 - assertIsTrue  "js*@proseware.com"                                                    =   0 =  OK 
     *  2289 - assertIsFalse "js@proseware..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2290 - assertIsTrue  "js@proseware.com9"                                                    =   0 =  OK 
     *  2291 - assertIsFalse "mailto:email@domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2292 - assertIsFalse "me@.com.my"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2293 - assertIsFalse "me123@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2294 - assertIsFalse "me123@.com.com"                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2295 - assertIsFalse "me@%*.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2296 - assertIsFalse "me..2002@gmail.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2297 - assertIsFalse "me.@gmail.com"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2298 - assertIsFalse "me@me@gmail.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2299 - assertIsTrue  "me@gmail.com"                                                         =   0 =  OK 
     *  2300 - assertIsTrue  "me@me.cu.uk"                                                          =   0 =  OK 
     *  2301 - assertIsTrue  "me-100@me.com"                                                        =   0 =  OK 
     *  2302 - assertIsTrue  "me.100@me.com"                                                        =   0 =  OK 
     *  2303 - assertIsTrue  "me-100@me.com.au"                                                     =   0 =  OK 
     *  2304 - assertIsTrue  "me+100@me.com"                                                        =   0 =  OK 
     *  2305 - assertIsTrue  "me-100@yahoo-test.com"                                                =   0 =  OK 
     *  2306 - assertIsTrue  "me@gmail.com"                                                         =   0 =  OK 
     *  2307 - assertIsFalse "me@gmail.com.1a"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2308 - assertIsTrue  "me@company.co.uk"                                                     =   0 =  OK 
     *  2309 - assertIsTrue  "user_name@domain.com"                                                 =   0 =  OK 
     *  2310 - assertIsTrue  "user_name@domain.co.in"                                               =   0 =  OK 
     *  2311 - assertIsTrue  "user@domaincom"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2312 - assertIsTrue  "user@domain.com"                                                      =   0 =  OK 
     *  2313 - assertIsTrue  "user@domain.co.in"                                                    =   0 =  OK 
     *  2314 - assertIsTrue  "user?name@domain.co.in"                                               =   0 =  OK 
     *  2315 - assertIsTrue  "user1@domain.com"                                                     =   0 =  OK 
     *  2316 - assertIsTrue  "user.name@domain.com"                                                 =   0 =  OK 
     *  2317 - assertIsTrue  "user-name@domain.co.in"                                               =   0 =  OK 
     *  2318 - assertIsTrue  "user'name@domain.co.in"                                               =   0 =  OK 
     *  2319 - assertIsTrue  "user#@domain.co.in"                                                   =   0 =  OK 
     *  2320 - assertIsTrue  "username@yahoo.corporate.in"                                          =   0 =  OK 
     *  2321 - assertIsTrue  "username@yahoo.corporate"                                             =   0 =  OK 
     *  2322 - assertIsFalse "username@yahoo.com."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2323 - assertIsFalse "username@yahoo.c"                                                     =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2324 - assertIsFalse "username@yahoo..com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2325 - assertIsFalse "user#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2326 - assertIsFalse "@yahoo.com"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2327 - assertIsFalse ".username@yahoo.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2328 - assertIsTrue  "abc@abc.com.com.com.com"                                              =   0 =  OK 
     *  2329 - assertIsTrue  "abc@abc.co.in"                                                        =   0 =  OK 
     *  2330 - assertIsTrue  "abc@abc.abc"                                                          =   0 =  OK 
     *  2331 - assertIsTrue  "abc@abc.abcde"                                                        =   0 =  OK 
     *  2332 - assertIsTrue  "abc@abc.abcd"                                                         =   0 =  OK 
     *  2333 - assertIsTrue  "abc.efg@gmail.com"                                                    =   0 =  OK 
     *  2334 - assertIsTrue  "abc@gmail.com.my"                                                     =   0 =  OK 
     *  2335 - assertIsFalse "abc@gmail..com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2336 - assertIsFalse "abc@gmail.com.."                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2337 - assertIsFalse "No -foo@bar.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2338 - assertIsFalse "No asd@-bar.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2339 - assertIsFalse ".username@yahoo.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2340 - assertIsTrue  "a&d@somedomain.com"                                                   =   0 =  OK 
     *  2341 - assertIsTrue  "a*d@somedomain.com"                                                   =   0 =  OK 
     *  2342 - assertIsTrue  "a/d@somedomain.com"                                                   =   0 =  OK 
     *  2343 - assertIsTrue  "\"a@b\"@example.com"                                                  =   1 =  OK 
     *  2344 - assertIsFalse ".abc@somedomain.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2345 - assertIsFalse "abc.@somedomain.com"                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2346 - assertIsFalse "a>b@somedomain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2347 - assertIsTrue  "me+alpha@example.com"                                                 =   0 =  OK 
     *  2348 - assertIsTrue  "o'hare@example.com"                                                   =   0 =  OK 
     *  2349 - assertIsTrue  "me@mail.s2.example.com"                                               =   0 =  OK 
     *  2350 - assertIsFalse "{something}@{something}.{something}"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2351 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                               =   0 =  OK 
     *  2352 - assertIsTrue  "This is <john@127.0.0.1>"                                             =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2353 - assertIsTrue  "This is <john@[127.0.0.1]>"                                           =   2 =  OK 
     *  2354 - assertIsTrue  "john.doe@example.com"                                                 =   0 =  OK 
     *  2355 - assertIsTrue  "john.o'doe@example.com"                                               =   0 =  OK 
     *  2356 - assertIsTrue  "John <john@doe.com>"                                                  =   0 =  OK 
     *  2357 - assertIsTrue  "<john@doe.com>"                                                       =   0 =  OK 
     *  2358 - assertIsTrue  "a_z%@gmail.com"                                                       =   0 =  OK 
     *  2359 - assertIsTrue  "a__z@provider.com"                                                    =   0 =  OK 
     *  2360 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                            =   0 =  OK 
     *  2361 - assertIsTrue  "me@aaronsw.com"                                                       =   0 =  OK 
     *  2362 - assertIsTrue  "my.ownsite@ourearth.org"                                              =   0 =  OK 
     *  2363 - assertIsFalse "myemail@@sample.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2364 - assertIsFalse "myemail@sa@mple.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2365 - assertIsTrue  "myemail@sample"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2366 - assertIsTrue  "myemail@sample.com"                                                   =   0 =  OK 
     *  2367 - assertIsFalse "myemailsample.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2368 - assertIsTrue  "mysite@you.me.net"                                                    =   0 =  OK 
     *  2369 - assertIsFalse "ote\"@example.com"                                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2370 - assertIsTrue  "peter.example@yahoo.com.au"                                           =   0 =  OK 
     *  2371 - assertIsTrue  "peter.piper@example.com"                                              =   0 =  OK 
     *  2372 - assertIsTrue  "peter_123@news.com"                                                   =   0 =  OK 
     *  2373 - assertIsTrue  "pio^_pio@factory.com"                                                 =   0 =  OK 
     *  2374 - assertIsTrue  "pio_#pio@factory.com"                                                 =   0 =  OK 
     *  2375 - assertIsFalse "pio_pio@#factory.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2376 - assertIsFalse "pio_pio@factory.c#om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2377 - assertIsFalse "pio_pio@factory.c*om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2378 - assertIsTrue  "pio_pio@factory.com"                                                  =   0 =  OK 
     *  2379 - assertIsTrue  "pio_~pio@factory.com"                                                 =   0 =  OK 
     *  2380 - assertIsTrue  "piskvor@example.lighting"                                             =   0 =  OK 
     *  2381 - assertIsFalse "plain.address"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2382 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2383 - assertIsTrue  "rss-dev@yahoogroups.com"                                              =   0 =  OK 
     *  2384 - assertIsTrue  "someone+tag@somewhere.net"                                            =   0 =  OK 
     *  2385 - assertIsTrue  "someone@somewhere.co.uk"                                              =   0 =  OK 
     *  2386 - assertIsTrue  "someone@somewhere.com"                                                =   0 =  OK 
     *  2387 - assertIsTrue  "something_valid@somewhere.tld"                                        =   0 =  OK 
     *  2388 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2389 - assertIsFalse "this\ still\\"not\allowed@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2390 - assertIsTrue  "tvf@tvf.cz"                                                           =   0 =  OK 
     *  2391 - assertIsFalse "two..dot@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2392 - assertIsTrue  "user+mailbox@example.com"                                             =   0 =  OK 
     *  2393 - assertIsTrue  "vdv@dyomedea.com"                                                     =   0 =  OK 
     *  2394 - assertIsFalse "xxxx..1234@yahoo.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2395 - assertIsFalse "xxxx.ourearth.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2396 - assertIsFalse "xxxx123@gmail.b"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2397 - assertIsFalse "xxxx@.com.my"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2398 - assertIsFalse "xxxx@.org.org"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2399 - assertIsTrue  "xxxx@gmail.com"                                                       =   0 =  OK 
     *  2400 - assertIsFalse "xxxxx()*@gmail.com"                                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2401 - assertIsTrue  "xxxxxx@yahoo.com"                                                     =   0 =  OK 
     *  2402 - assertIsFalse "<')))><@fish.left.com"                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2403 - assertIsFalse "><(((*>@fish.right.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2404 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2405 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2406 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2407 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  2408 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  2409 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2410 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"       =   0 =  OK 
     *  2411 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  2412 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                              =   4 =  OK 
     *  2413 - assertIsTrue  "user@localserver"                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2414 - assertIsTrue  "w.b.f@test.com"                                                       =   0 =  OK 
     *  2415 - assertIsTrue  "w.b.f@test.museum"                                                    =   0 =  OK 
     *  2416 - assertIsTrue  "yoursite@ourearth.com"                                                =   0 =  OK 
     *  2417 - assertIsTrue  "~pio_pio@factory.com"                                                 =   0 =  OK 
     *  2418 - assertIsTrue  "valid@[1.1.1.1]"                                                      =   2 =  OK 
     *  2419 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2420 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                               =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2421 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                      =   4 =  OK 
     *  2422 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"       =   4 =  OK 
     *  2423 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                            =   4 =  OK 
     *  2424 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"               =   4 =  OK 
     *  2425 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                 =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2426 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                          =   4 =  OK 
     *  2427 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                          =   4 =  OK 
     *  2428 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                    =   2 =  OK 
     *  2429 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                        =   2 =  OK 
     *  2430 - assertIsFalse "invalid@[10]"                                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2431 - assertIsFalse "invalid@[10.1]"                                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2432 - assertIsFalse "invalid@[10.1.52]"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2433 - assertIsFalse "invalid@[256.256.256.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2434 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2435 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2436 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2437 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2438 - assertIsFalse "invalid@domain1.com@domain2.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2439 - assertIsFalse "\"locál-part\"@example.com"                                           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2440 - assertIsFalse "invalid@[IPv6:1::2:]"                                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2441 - assertIsFalse "invalid@[IPv6::1::1]"                                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2442 - assertIsFalse "invalid@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2443 - assertIsFalse "invalid@[111.111.111.111"                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2444 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2445 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2446 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2447 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2448 - assertIsFalse "invalid@[IPv6:1111:1111]"                                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2449 - assertIsFalse "\"invalid-qstring@example.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     *  2450 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                           =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2451 - assertIsTrue  "\"back\slash\"@sld.com"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2452 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                          =   1 =  OK 
     *  2453 - assertIsTrue  "\"quoted\"@sld.com"                                                   =   1 =  OK 
     *  2454 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                   =   1 =  OK 
     *  2455 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                   =   0 =  OK 
     *  2456 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                  =   0 =  OK 
     *  2457 - assertIsTrue  "01234567890@numbers-in-local.net"                                     =   0 =  OK 
     *  2458 - assertIsTrue  "a@single-character-in-local.org"                                      =   0 =  OK 
     *  2459 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  2460 - assertIsTrue  "backticksarelegit@test.com"                                           =   0 =  OK 
     *  2461 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                           =   2 =  OK 
     *  2462 - assertIsTrue  "country-code-tld@sld.rw"                                              =   0 =  OK 
     *  2463 - assertIsTrue  "country-code-tld@sld.uk"                                              =   0 =  OK 
     *  2464 - assertIsTrue  "letters-in-sld@123.com"                                               =   0 =  OK 
     *  2465 - assertIsTrue  "local@dash-in-sld.com"                                                =   0 =  OK 
     *  2466 - assertIsTrue  "local@sld.newTLD"                                                     =   0 =  OK 
     *  2467 - assertIsTrue  "local@sub.domains.com"                                                =   0 =  OK 
     *  2468 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                     =   0 =  OK 
     *  2469 - assertIsTrue  "one-character-third-level@a.example.com"                              =   0 =  OK 
     *  2470 - assertIsTrue  "one-letter-sld@x.org"                                                 =   0 =  OK 
     *  2471 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                             =   0 =  OK 
     *  2472 - assertIsTrue  "single-character-in-sld@x.org"                                        =   0 =  OK 
     *  2473 - assertIsTrue  "uncommon-tld@sld.mobi"                                                =   0 =  OK 
     *  2474 - assertIsTrue  "uncommon-tld@sld.museum"                                              =   0 =  OK 
     *  2475 - assertIsTrue  "uncommon-tld@sld.travel"                                              =   0 =  OK 
     *  2476 - assertIsFalse "invalid"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2477 - assertIsFalse "invalid@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2478 - assertIsFalse "invalid @"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2479 - assertIsFalse "invalid@[555.666.777.888]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2480 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2481 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2482 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2483 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2484 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2485 - assertIsFalse "@missing-local.org"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2486 - assertIsFalse "IP-and-port@127.0.0.1:25"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2487 - assertIsFalse "another-invalid-ip@127.0.0.256"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2488 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2489 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2490 - assertIsFalse "invalid-ip@127.0.0.1.26"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2491 - assertIsFalse "local-ends-with-dot.@sld.com"                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2492 - assertIsFalse "missing-at-sign.net"                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2493 - assertIsFalse "missing-sld@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2494 - assertIsFalse "missing-tld@sld."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2495 - assertIsFalse "sld-ends-with-dash@sld-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2496 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2497 - assertIsFalse "two..consecutive-dots@sld.com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2498 - assertIsFalse "unbracketed-IP@127.0.0.1"                                             =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2499 - assertIsFalse "underscore.error@example.com_"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     *  2500 - assertIsTrue  "first.last@iana.org"                                                  =   0 =  OK 
     *  2501 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  2502 - assertIsTrue  "\"first\\"last\"@iana.org"                                            =   1 =  OK 
     *  2503 - assertIsTrue  "\"first@last\"@iana.org"                                              =   1 =  OK 
     *  2504 - assertIsTrue  "\"first\\last\"@iana.org"                                             =   1 =  OK 
     *  2505 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  2506 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  2507 - assertIsTrue  "first.last@[12.34.56.78]"                                             =   2 =  OK 
     *  2508 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                    =   4 =  OK 
     *  2509 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                     =   4 =  OK 
     *  2510 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                    =   4 =  OK 
     *  2511 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                    =   4 =  OK 
     *  2512 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"            =   4 =  OK 
     *  2513 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  2514 - assertIsTrue  "first.last@3com.com"                                                  =   0 =  OK 
     *  2515 - assertIsTrue  "first.last@123.iana.org"                                              =   0 =  OK 
     *  2516 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2517 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                =   4 =  OK 
     *  2518 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                =   1 =  OK 
     *  2519 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                            =   1 =  OK 
     *  2520 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                              =   1 =  OK 
     *  2521 - assertIsTrue  "\"Abc@def\"@iana.org"                                                 =   1 =  OK 
     *  2522 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                           =   1 =  OK 
     *  2523 - assertIsTrue  "user+mailbox@iana.org"                                                =   0 =  OK 
     *  2524 - assertIsTrue  "$A12345@iana.org"                                                     =   0 =  OK 
     *  2525 - assertIsTrue  "!def!xyz%abc@iana.org"                                                =   0 =  OK 
     *  2526 - assertIsTrue  "_somename@iana.org"                                                   =   0 =  OK 
     *  2527 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2528 - assertIsTrue  "peter.piper@iana.org"                                                 =   0 =  OK 
     *  2529 - assertIsTrue  "test@iana.org"                                                        =   0 =  OK 
     *  2530 - assertIsTrue  "TEST@iana.org"                                                        =   0 =  OK 
     *  2531 - assertIsTrue  "1234567890@iana.org"                                                  =   0 =  OK 
     *  2532 - assertIsTrue  "test+test@iana.org"                                                   =   0 =  OK 
     *  2533 - assertIsTrue  "test-test@iana.org"                                                   =   0 =  OK 
     *  2534 - assertIsTrue  "t*est@iana.org"                                                       =   0 =  OK 
     *  2535 - assertIsTrue  "+1~1+@iana.org"                                                       =   0 =  OK 
     *  2536 - assertIsTrue  "{_test_}@iana.org"                                                    =   0 =  OK 
     *  2537 - assertIsTrue  "test.test@iana.org"                                                   =   0 =  OK 
     *  2538 - assertIsTrue  "\"test.test\"@iana.org"                                               =   1 =  OK 
     *  2539 - assertIsTrue  "test.\"test\"@iana.org"                                               =   1 =  OK 
     *  2540 - assertIsTrue  "\"test@test\"@iana.org"                                               =   1 =  OK 
     *  2541 - assertIsTrue  "test@123.123.123.x123"                                                =   0 =  OK 
     *  2542 - assertIsFalse "test@123.123.123.123"                                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2543 - assertIsTrue  "test@[123.123.123.123]"                                               =   2 =  OK 
     *  2544 - assertIsTrue  "test@example.iana.org"                                                =   0 =  OK 
     *  2545 - assertIsTrue  "test@example.example.iana.org"                                        =   0 =  OK 
     *  2546 - assertIsTrue  "customer/department@iana.org"                                         =   0 =  OK 
     *  2547 - assertIsTrue  "_Yosemite.Sam@iana.org"                                               =   0 =  OK 
     *  2548 - assertIsTrue  "~@iana.org"                                                           =   0 =  OK 
     *  2549 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                           =   1 =  OK 
     *  2550 - assertIsTrue  "Ima.Fool@iana.org"                                                    =   0 =  OK 
     *  2551 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                =   1 =  OK 
     *  2552 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                              =   1 =  OK 
     *  2553 - assertIsTrue  "\"first\".\"last\"@iana.org"                                          =   1 =  OK 
     *  2554 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                   =   1 =  OK 
     *  2555 - assertIsTrue  "\"first\".last@iana.org"                                              =   1 =  OK 
     *  2556 - assertIsTrue  "first.\"last\"@iana.org"                                              =   1 =  OK 
     *  2557 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                               =   1 =  OK 
     *  2558 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2559 - assertIsTrue  "\"first.middle.last\"@iana.org"                                       =   1 =  OK 
     *  2560 - assertIsTrue  "\"first..last\"@iana.org"                                             =   1 =  OK 
     *  2561 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2562 - assertIsFalse "first.last @iana.orgin"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2563 - assertIsTrue  "\"test blah\"@iana.orgin"                                             =   1 =  OK 
     *  2564 - assertIsTrue  "name.lastname@domain.com"                                             =   0 =  OK 
     *  2565 - assertIsTrue  "a@bar.com"                                                            =   0 =  OK 
     *  2566 - assertIsTrue  "aaa@[123.123.123.123]"                                                =   2 =  OK 
     *  2567 - assertIsTrue  "a-b@bar.com"                                                          =   0 =  OK 
     *  2568 - assertIsFalse "+@b.c"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2569 - assertIsTrue  "+@b.com"                                                              =   0 =  OK 
     *  2570 - assertIsTrue  "a@b.co-foo.uk"                                                        =   0 =  OK 
     *  2571 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                   =   1 =  OK 
     *  2572 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                   =   1 =  OK 
     *  2573 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                     =   0 =  OK 
     *  2574 - assertIsFalse "foobar@192.168.0.1"                                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2575 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                    =   6 =  OK 
     *  2576 - assertIsTrue  "user%uucp!path@berkeley.edu"                                          =   0 =  OK 
     *  2577 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                            =   0 =  OK 
     *  2578 - assertIsTrue  "test@test.com"                                                        =   0 =  OK 
     *  2579 - assertIsTrue  "test@xn--example.com"                                                 =   0 =  OK 
     *  2580 - assertIsTrue  "test@example.com"                                                     =   0 =  OK 
     *  2581 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2582 - assertIsTrue  "first\@last@iana.org"                                                 =   0 =  OK 
     *  2583 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                             =   0 =  OK 
     *  2584 - assertIsFalse "first.last@example.123"                                               =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2585 - assertIsFalse "first.last@comin"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2586 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                            =   1 =  OK 
     *  2587 - assertIsTrue  "Abc\@def@iana.org"                                                    =   0 =  OK 
     *  2588 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                =   0 =  OK 
     *  2589 - assertIsFalse "Joe.\Blow@iana.org"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2590 - assertIsTrue  "first.last@sub.do.com"                                                =   0 =  OK 
     *  2591 - assertIsFalse "first.last"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2592 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                      =   0 =  OK 
     *  2593 - assertIsTrue  "\"hello world\"@example.com"                                          =   1 =  OK 
     *  2594 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2595 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                         =   1 =  OK 
     *  2596 - assertIsTrue  "example+tag@gmail.com"                                                =   0 =  OK 
     *  2597 - assertIsFalse ".ann..other.@example.com"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2598 - assertIsTrue  "ann.other@example.com"                                                =   0 =  OK 
     *  2599 - assertIsTrue  "something@something.something"                                        =   0 =  OK 
     *  2600 - assertIsTrue  "c@(Chris's host.)public.examplein"                                    =   6 =  OK 
     *  2601 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2602 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2603 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2604 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2605 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2606 - assertIsFalse "first().last@iana.orgin"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2607 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2608 - assertIsFalse "jdoe@machine(comment). examplein"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2609 - assertIsFalse "first(abc.def).last@iana.orgin"                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2610 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2611 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2612 - assertIsFalse "first(abc\(def)@iana.orgin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2613 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2614 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2615 - assertIsFalse "1234 @ local(blah) .machine .examplein"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2616 - assertIsFalse "a@bin"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2617 - assertIsFalse "a@barin"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2618 - assertIsFalse "@about.museum"                                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2619 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2620 - assertIsFalse ".first.last@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2621 - assertIsFalse "first.last.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2622 - assertIsFalse "first..last@iana.org"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2623 - assertIsFalse "\"first\"last\"@iana.org"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2624 - assertIsFalse "first.last@"                                                          =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2625 - assertIsFalse "first.last@-xample.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2626 - assertIsFalse "first.last@exampl-.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2627 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2628 - assertIsFalse "abc\@iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2629 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2630 - assertIsFalse "abc@def@iana.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2631 - assertIsFalse "@iana.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2632 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2633 - assertIsFalse "\"qu@iana.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2634 - assertIsFalse "ote\"@iana.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2635 - assertIsFalse ".dot@iana.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2636 - assertIsFalse "dot.@iana.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2637 - assertIsFalse "two..dot@iana.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2638 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2639 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2640 - assertIsFalse "hello world@iana.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2641 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2642 - assertIsFalse "test.iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2643 - assertIsFalse "test.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2644 - assertIsFalse "test..test@iana.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2645 - assertIsFalse ".test@iana.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2646 - assertIsFalse "test@test@iana.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2647 - assertIsFalse "test@@iana.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2648 - assertIsFalse "-- test --@iana.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2649 - assertIsFalse "[test]@iana.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2650 - assertIsFalse "\"test\"test\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2651 - assertIsFalse "()[]\;:.><@iana.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2652 - assertIsFalse "test@."                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2653 - assertIsFalse "test@example."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2654 - assertIsFalse "test@.org"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2655 - assertIsFalse "test@[123.123.123.123"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2656 - assertIsFalse "test@123.123.123.123]"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2657 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2658 - assertIsFalse "@NotAnEmail"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2659 - assertIsFalse "\"test\"blah\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2660 - assertIsFalse ".wooly@iana.org"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2661 - assertIsFalse "wo..oly@iana.org"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2662 - assertIsFalse "pootietang.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2663 - assertIsFalse ".@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2664 - assertIsFalse "Ima Fool@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2665 - assertIsFalse "foo@[\1.2.3.4]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2666 - assertIsFalse "first.\"\".last@iana.org"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2667 - assertIsFalse "first\last@iana.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2668 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2669 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2670 - assertIsFalse "cal(foo(bar)@iamcal.com"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2671 - assertIsFalse "cal(foo)bar)@iamcal.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2672 - assertIsFalse "cal(foo\)@iamcal.com"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2673 - assertIsFalse "first(middle)last@iana.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2674 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2675 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2676 - assertIsFalse ".@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2677 - assertIsFalse "@bar.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2678 - assertIsFalse "@@bar.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2679 - assertIsFalse "aaa.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2680 - assertIsFalse "aaa@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2681 - assertIsFalse "aaa@.123"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2682 - assertIsFalse "aaa@[123.123.123.123]a"                                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2683 - assertIsFalse "aaa@[123.123.123.333]"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2684 - assertIsFalse "a@bar.com."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2685 - assertIsFalse "a@-b.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2686 - assertIsFalse "a@b-.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2687 - assertIsFalse "-@..com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2688 - assertIsFalse "-@a..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2689 - assertIsFalse "@about.museum-"                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2690 - assertIsFalse "test@...........com"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2691 - assertIsFalse "first.last@[IPv6::]"                                                  =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2692 - assertIsFalse "first.last@[IPv6::::]"                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2693 - assertIsFalse "first.last@[IPv6::b4]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2694 - assertIsFalse "first.last@[IPv6::::b4]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2695 - assertIsFalse "first.last@[IPv6::b3:b4]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2696 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2697 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2698 - assertIsFalse "first.last@[IPv6:a1:]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2699 - assertIsFalse "first.last@[IPv6:a1:::]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2700 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2701 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2702 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2703 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2704 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2705 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2706 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2707 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2708 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                  =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2709 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2710 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2711 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2712 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                  =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  2713 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2714 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2715 - assertIsFalse "first.last@[IPv6::a2::b4]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2716 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2717 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2718 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2719 - assertIsFalse "first.last@[.12.34.56.78]"                                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2720 - assertIsFalse "first.last@[12.34.56.789]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2721 - assertIsFalse "first.last@[::12.34.56.78]"                                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2722 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2723 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                      =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  2724 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2725 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2726 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2727 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2728 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2729 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2730 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2731 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2732 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2733 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2734 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2735 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2736 - assertIsTrue  "first.last@[IPv6:::]"                                                 =   4 =  OK 
     *  2737 - assertIsTrue  "first.last@[IPv6:::b4]"                                               =   4 =  OK 
     *  2738 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                            =   4 =  OK 
     *  2739 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                             =   4 =  OK 
     *  2740 - assertIsTrue  "first.last@[IPv6:a1::]"                                               =   4 =  OK 
     *  2741 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                            =   4 =  OK 
     *  2742 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                              =   4 =  OK 
     *  2743 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                              =   4 =  OK 
     *  2744 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2745 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2746 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2747 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2748 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                    =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2749 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2750 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2751 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2752 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2753 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  OK 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     *  2754 - assertIsFalse "\"qu@test.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2755 - assertIsFalse "ote\"@test.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2756 - assertIsFalse "\"().:;<>[\]@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2757 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2758 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2759 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2760 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2761 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2762 - assertIsFalse "this\ still\"not\allowed@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2763 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2764 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2765 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2766 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2767 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2768 - assertIsFalse ".email@example.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2769 - assertIsFalse "email.@example.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2770 - assertIsFalse "email..email@example.com"                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2771 - assertIsFalse "email@-example.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2772 - assertIsFalse "email@111.222.333.44444"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2773 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2774 - assertIsFalse "email@[12.34.44.56"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2775 - assertIsFalse "email@14.44.56.34]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2776 - assertIsFalse "email@[1.1.23.5f]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2777 - assertIsFalse "email@[3.256.255.23]"                                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2778 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2779 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2780 - assertIsFalse "first\@last@iana.org"                                                 =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2781 - assertIsFalse "test@example.com "                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2782 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2783 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2784 - assertIsFalse "invalid@about.museum-"                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2785 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2786 - assertIsFalse "abc@def@test.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2787 - assertIsTrue  "abc\@def@test.org"                                                    =   0 =  OK 
     *  2788 - assertIsFalse "abc\@test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2789 - assertIsFalse "@test.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2790 - assertIsFalse ".dot@test.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2791 - assertIsFalse "dot.@test.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2792 - assertIsFalse "two..dot@test.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2793 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2794 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2795 - assertIsFalse "hello world@test.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2796 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2797 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2798 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2799 - assertIsFalse "test.test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2800 - assertIsFalse "test.@test.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2801 - assertIsFalse "test..test@test.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2802 - assertIsFalse ".test@test.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2803 - assertIsFalse "test@test@test.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2804 - assertIsFalse "test@@test.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2805 - assertIsFalse "-- test --@test.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2806 - assertIsFalse "[test]@test.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2807 - assertIsFalse "\"test\"test\"@test.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2808 - assertIsFalse "()[]\;:.><@test.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2809 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2810 - assertIsFalse ".@test.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2811 - assertIsFalse "Ima Fool@test.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2812 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2813 - assertIsFalse "foo@[.2.3.4]"                                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2814 - assertIsFalse "first\last@test.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2815 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2816 - assertIsFalse "first(middle)last@test.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2817 - assertIsFalse "\"test\"test@test.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2818 - assertIsFalse "()@test.com"                                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  2819 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  2820 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2821 - assertIsFalse "invalid@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2822 - assertIsFalse "ä📧@-foo"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2823 - assertIsFalse "ä📧@foo-"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2824 - assertIsFalse "first(comment(inner@comment.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2825 - assertIsFalse "Joe A Smith <email@example.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2826 - assertIsFalse "Joe A Smith email@example.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2827 - assertIsFalse "Joe A Smith <email@example.com->"                                     =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2828 - assertIsFalse "Joe A Smith <email@-example.com->"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2829 - assertIsFalse "Joe A Smith <email>"                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2830 - assertIsTrue  "\"email\"@example.com"                                                =   1 =  OK 
     *  2831 - assertIsTrue  "\"first@last\"@test.org"                                              =   1 =  OK 
     *  2832 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                           =   1 =  OK 
     *  2833 - assertIsTrue  "\"first\"last\"@test.org"                                             =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2834 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                   =   1 =  OK 
     *  2835 - assertIsTrue  "\"first\last\"@test.org"                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2836 - assertIsTrue  "\"Abc\@def\"@test.org"                                                =   1 =  OK 
     *  2837 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                            =   1 =  OK 
     *  2838 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2839 - assertIsTrue  "\"Abc@def\"@test.org"                                                 =   1 =  OK 
     *  2840 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                             =   1 =  OK 
     *  2841 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2842 - assertIsTrue  "\"[[ test ]]\"@test.org"                                              =   1 =  OK 
     *  2843 - assertIsTrue  "\"test.test\"@test.org"                                               =   1 =  OK 
     *  2844 - assertIsTrue  "test.\"test\"@test.org"                                               =   1 =  OK 
     *  2845 - assertIsTrue  "\"test@test\"@test.org"                                               =   1 =  OK 
     *  2846 - assertIsTrue  "\"test  est\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2847 - assertIsTrue  "\"first\".\"last\"@test.org"                                          =   1 =  OK 
     *  2848 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                   =   1 =  OK 
     *  2849 - assertIsTrue  "\"first\".last@test.org"                                              =   1 =  OK 
     *  2850 - assertIsTrue  "first.\"last\"@test.org"                                              =   1 =  OK 
     *  2851 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                               =   1 =  OK 
     *  2852 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                   =   1 =  OK 
     *  2853 - assertIsTrue  "\"first.middle.last\"@test.org"                                       =   1 =  OK 
     *  2854 - assertIsTrue  "\"first..last\"@test.org"                                             =   1 =  OK 
     *  2855 - assertIsTrue  "\"Unicode NULL \"@char.com"                                           =   1 =  OK 
     *  2856 - assertIsTrue  "\"test\blah\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2857 - assertIsTrue  "\"testlah\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2858 - assertIsTrue  "\"test\"blah\"@test.org"                                              =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2859 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2860 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                     =   1 =  OK 
     *  2861 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2862 - assertIsTrue  "\"test blah\"@test.org"                                               =   1 =  OK 
     *  2863 - assertIsTrue  "first.last@test.org"                                                  =   0 =  OK 
     *  2864 - assertIsFalse "jdoe@machine(comment).example"                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2865 - assertIsTrue  "first.\"\".last@test.org"                                             =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2866 - assertIsTrue  "\"\"@test.org"                                                        =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2867 - assertIsTrue  "very.common@example.org"                                              =   0 =  OK 
     *  2868 - assertIsTrue  "test/test@test.com"                                                   =   0 =  OK 
     *  2869 - assertIsTrue  "user-@example.org"                                                    =   0 =  OK 
     *  2870 - assertIsTrue  "firstname.lastname@example.com"                                       =   0 =  OK 
     *  2871 - assertIsTrue  "email@subdomain.example.com"                                          =   0 =  OK 
     *  2872 - assertIsTrue  "firstname+lastname@example.com"                                       =   0 =  OK 
     *  2873 - assertIsTrue  "1234567890@example.com"                                               =   0 =  OK 
     *  2874 - assertIsTrue  "email@example-one.com"                                                =   0 =  OK 
     *  2875 - assertIsTrue  "_______@example.com"                                                  =   0 =  OK 
     *  2876 - assertIsTrue  "email@example.name"                                                   =   0 =  OK 
     *  2877 - assertIsTrue  "email@example.museum"                                                 =   0 =  OK 
     *  2878 - assertIsTrue  "email@example.co.jp"                                                  =   0 =  OK 
     *  2879 - assertIsTrue  "firstname-lastname@example.com"                                       =   0 =  OK 
     *  2880 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  2881 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2882 - assertIsTrue  "first.last@123.test.org"                                              =   0 =  OK 
     *  2883 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2884 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  2885 - assertIsTrue  "user+mailbox@test.org"                                                =   0 =  OK 
     *  2886 - assertIsTrue  "customer/department=shipping@test.org"                                =   0 =  OK 
     *  2887 - assertIsTrue  "$A12345@test.org"                                                     =   0 =  OK 
     *  2888 - assertIsTrue  "!def!xyz%abc@test.org"                                                =   0 =  OK 
     *  2889 - assertIsTrue  "_somename@test.org"                                                   =   0 =  OK 
     *  2890 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2891 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2892 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2893 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2894 - assertIsTrue  "+@b.c"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2895 - assertIsTrue  "TEST@test.org"                                                        =   0 =  OK 
     *  2896 - assertIsTrue  "1234567890@test.org"                                                  =   0 =  OK 
     *  2897 - assertIsTrue  "test-test@test.org"                                                   =   0 =  OK 
     *  2898 - assertIsTrue  "t*est@test.org"                                                       =   0 =  OK 
     *  2899 - assertIsTrue  "+1~1+@test.org"                                                       =   0 =  OK 
     *  2900 - assertIsTrue  "{_test_}@test.org"                                                    =   0 =  OK 
     *  2901 - assertIsTrue  "valid@about.museum"                                                   =   0 =  OK 
     *  2902 - assertIsTrue  "a@bar"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2903 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2904 - assertIsTrue  "(comment)test@test.org"                                               =   6 =  OK 
     *  2905 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2906 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2907 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2908 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"   =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2909 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2910 - assertIsTrue  "first(abc\(def)@test.org"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2911 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                     =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2912 - assertIsTrue  "c@(Chris's host.)public.example"                                      =   6 =  OK 
     *  2913 - assertIsTrue  "_Yosemite.Sam@test.org"                                               =   0 =  OK 
     *  2914 - assertIsTrue  "~@test.org"                                                           =   0 =  OK 
     *  2915 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                        =   6 =  OK 
     *  2916 - assertIsTrue  "test@Bücher.ch"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2917 - assertIsTrue  "あいうえお@example.com"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2918 - assertIsTrue  "Pelé@example.com"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2919 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2920 - assertIsTrue  "我買@屋企.香港"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2921 - assertIsTrue  "二ノ宮@黒川.日本"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2922 - assertIsTrue  "медведь@с-балалайкой.рф"                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2923 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2924 - assertIsTrue  "email@example.com (Joe Smith)"                                        =   6 =  OK 
     *  2925 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                             = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2926 - assertIsTrue  "first(abc.def).last@test.org"                                         = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2927 - assertIsTrue  "first(a\"bc.def).last@test.org"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2928 - assertIsTrue  "first.(\")middle.last(\")@test.org"                                   = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2929 - assertIsTrue  "first().last@test.org"                                                = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2930 - assertIsTrue  "mymail\@hello@hotmail.com"                                            =   0 =  OK 
     *  2931 - assertIsTrue  "Abc\@def@test.org"                                                    =   0 =  OK 
     *  2932 - assertIsTrue  "Fred\ Bloggs@test.org"                                                =   0 =  OK 
     *  2933 - assertIsTrue  "Joe.\\Blow@test.org"                                                  =   0 =  OK 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ----------------------------------------------------------------------------------------------------
     * 
     *  2934 - assertIsTrue  "me@example.com"                                                       =   0 =  OK 
     *  2935 - assertIsTrue  "a.nonymous@example.com"                                               =   0 =  OK 
     *  2936 - assertIsTrue  "name+tag@example.com"                                                 =   0 =  OK 
     *  2937 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                       =   2 =  OK 
     *  2938 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"    =   4 =  OK 
     *  2939 - assertIsTrue  "me(this is a comment)@example.com"                                    =   6 =  OK 
     *  2940 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                            =   1 =  OK 
     *  2941 - assertIsTrue  "me.example@com"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2942 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                      =   0 =  OK 
     *  2943 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"  =   0 =  OK 
     *  2944 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                     =   0 =  OK 
     *  2945 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                    =   0 =  OK 
     *  2946 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                   =   0 =  OK 
     *  2947 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                  =   0 =  OK 
     *  2948 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2949 - assertIsFalse "me@"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2950 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2951 - assertIsFalse ".me@example.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2952 - assertIsFalse "me@example..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2953 - assertIsFalse "me\@example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2954 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2955 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2956 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2957 - assertIsFalse "semico...@gmail.com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- my old tests ----------------------------------------------------------------------------------------------------
     * 
     *  2958 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  2959 - assertIsTrue  "A.\"B\"@C.DE"                                                         =   1 =  OK 
     *  2960 - assertIsTrue  "A.B@[1.2.3.4]"                                                        =   2 =  OK 
     *  2961 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                    =   3 =  OK 
     *  2962 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                           =   4 =  OK 
     *  2963 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                       =   5 =  OK 
     *  2964 - assertIsTrue  "(A)B@C.DE"                                                            =   6 =  OK 
     *  2965 - assertIsTrue  "A(B)@C.DE"                                                            =   6 =  OK 
     *  2966 - assertIsTrue  "(A)\"B\"@C.DE"                                                        =   7 =  OK 
     *  2967 - assertIsTrue  "\"A\"(B)@C.DE"                                                        =   7 =  OK 
     *  2968 - assertIsTrue  "(A)B@[1.2.3.4]"                                                       =   2 =  OK 
     *  2969 - assertIsTrue  "A(B)@[1.2.3.4]"                                                       =   2 =  OK 
     *  2970 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                   =   8 =  OK 
     *  2971 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                   =   8 =  OK 
     *  2972 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  2973 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  2974 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *  2975 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *  2976 - assertIsTrue  "a.b.c.d@domain.com"                                                   =   0 =  OK 
     *  2977 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2978 - assertIsFalse "ABC.DEF.GHI.JKL"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2979 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2980 - assertIsFalse "ABC.DEF @GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2981 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2982 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2983 - assertIsFalse "ABC.DEF@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2984 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2985 - assertIsFalse "ABC@DEF@GHI.JKL"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2986 - assertIsFalse "@%^%#$@#$@#.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2987 - assertIsFalse "email.domain.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2988 - assertIsFalse "email@domain@domain.com"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2989 - assertIsFalse "first@last@test.org"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2990 - assertIsFalse "@test@a.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2991 - assertIsFalse "@\"someStringThatMightBe@email.com"                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2992 - assertIsFalse "test@@test.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2993 - assertIsFalse "ABCDEF@GHIJKL"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2994 - assertIsFalse "ABC.DEF@GHIJKL"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2995 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2996 - assertIsFalse "ABC.DEF@GHI.JKL."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2997 - assertIsFalse "ABC..DEF@GHI.JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2998 - assertIsFalse "ABC.DEF@GHI..JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2999 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3000 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3001 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3002 - assertIsFalse "ABC.DEF@."                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3003 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                 =   1 =  OK 
     *  3004 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                  =   0 =  OK 
     *  3005 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                     =   0 =  OK 
     *  3006 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                     =   0 =  OK 
     *  3007 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                      =   0 =  OK 
     *  3008 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                      =   0 =  OK 
     *  3009 - assertIsFalse "ABC.DEF@GHI.2KL"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3010 - assertIsFalse "ABC.DEF@GHI.JK-"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3011 - assertIsFalse "ABC.DEF@GHI.JK_"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3012 - assertIsFalse "ABC.DEF@-HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3013 - assertIsFalse "ABC.DEF@_HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3014 - assertIsFalse "ABC DEF@GHI.DE"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3015 - assertIsFalse "ABC.DEF@GHI DE"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3016 - assertIsFalse "A . B & C . D"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3017 - assertIsFalse " A . B & C . D"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3018 - assertIsFalse "(?).[!]@{&}.<:>"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- some more Testcases ----------------------------------------------------------------------------------------------------
     * 
     *  3019 - assertIsFalse "\"\".local.name.starts.with.empty.string1@domain.com"                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3020 - assertIsFalse "local.name.ends.with.empty.string1\"\"@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3021 - assertIsFalse "local.name.with.empty.string1\"\"character@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3022 - assertIsFalse "local.name.with.empty.string1.before\"\".point@domain.com"            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3023 - assertIsFalse "local.name.with.empty.string1.after.\"\"point@domain.com"             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3024 - assertIsFalse "local.name.with.double.empty.string1\"\"\"\"test@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3025 - assertIsFalse "(comment \"\") local.name.with.comment.with.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3026 - assertIsFalse "\"quote\"\"\".local.name.with.qoute.with.empty.string1@domain.com"    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3027 - assertIsFalse "\"\"@empty.string1.domain.com"                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3028 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@empty.string1.domain.com"                    =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3029 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3030 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1@domain.com>"           =   0 =  OK 
     *  3031 - assertIsTrue  "<pointy.brackets2.with.empty.string1@domain.com> name \"\""           =   0 =  OK 
     *  3032 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3033 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3034 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3035 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3036 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3037 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3038 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3039 - assertIsFalse "domain.part.with.comment.with.empty.string1@(comment \"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3040 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3041 - assertIsFalse "ip.v4.with.empty.string1@[123.14\"\"5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3042 - assertIsFalse "ip.v4.with.empty.string1@[123.145\"\".178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3043 - assertIsFalse "ip.v4.with.empty.string1@[123.145.\"\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3044 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90\"\"]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3045 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90]\"\""                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3046 - assertIsFalse "ip.v4.with.empty.string1@[\"\"123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3047 - assertIsFalse "ip.v4.with.empty.string1@\"\"[123.145.178.90]"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3048 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3049 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3050 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3051 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3052 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3053 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3054 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3055 - assertIsFalse "a\"\"b.local.name.starts.with.empty.string2@domain.com"               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3056 - assertIsFalse "local.name.ends.with.empty.string2a\"\"b@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3057 - assertIsFalse "local.name.with.empty.string2a\"\"bcharacter@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3058 - assertIsFalse "local.name.with.empty.string2.beforea\"\"b.point@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3059 - assertIsFalse "local.name.with.empty.string2.after.a\"\"bpoint@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3060 - assertIsFalse "local.name.with.double.empty.string2a\"\"ba\"\"btest@domain.com"      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3061 - assertIsFalse "(comment a\"\"b) local.name.with.comment.with.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3062 - assertIsFalse "\"quotea\"\"b\".local.name.with.qoute.with.empty.string2@domain.com"  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3063 - assertIsFalse "a\"\"b@empty.string2.domain.com"                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3064 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@empty.string2.domain.com"        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3065 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3066 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2@domain.com>"         =   0 =  OK 
     *  3067 - assertIsTrue  "<pointy.brackets2.with.empty.string2@domain.com> name a\"\"b"         =   0 =  OK 
     *  3068 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3069 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3070 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3071 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3072 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3073 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3074 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3075 - assertIsFalse "domain.part.with.comment.with.empty.string2@(comment a\"\"b)domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3076 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3077 - assertIsFalse "ip.v4.with.empty.string2@[123.14a\"\"b5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3078 - assertIsFalse "ip.v4.with.empty.string2@[123.145a\"\"b.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3079 - assertIsFalse "ip.v4.with.empty.string2@[123.145.a\"\"b178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3080 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90a\"\"b]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3081 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90]a\"\"b"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3082 - assertIsFalse "ip.v4.with.empty.string2@[a\"\"b123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3083 - assertIsFalse "ip.v4.with.empty.string2@a\"\"b[123.145.178.90]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3084 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3085 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3086 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3087 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3088 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3089 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3090 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3091 - assertIsFalse "\"\"\"\".local.name.starts.with.double.empty.string1@domain.com"      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3092 - assertIsFalse "local.name.ends.with.double.empty.string1\"\"\"\"@domain.com"         =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3093 - assertIsFalse "local.name.with.double.empty.string1\"\"\"\"character@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3094 - assertIsFalse "local.name.with.double.empty.string1.before\"\"\"\".point@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3095 - assertIsFalse "local.name.with.double.empty.string1.after.\"\"\"\"point@domain.com"  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3096 - assertIsFalse "local.name.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3097 - assertIsFalse "(comment \"\"\"\") local.name.with.comment.with.double.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3098 - assertIsFalse "\"quote\"\"\"\"\".local.name.with.qoute.with.double.empty.string1@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3099 - assertIsFalse "\"\"\"\"@double.empty.string1.domain.com"                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3100 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3101 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3102 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1@domain.com>" =   0 =  OK 
     *  3103 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1@domain.com> name \"\"\"\"" =   0 =  OK 
     *  3104 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3105 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3106 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3107 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3108 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3109 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3110 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3111 - assertIsFalse "domain.part.with.comment.with.double.empty.string1@(comment \"\"\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3112 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3113 - assertIsFalse "ip.v4.with.double.empty.string1@[123.14\"\"\"\"5.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3114 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145\"\"\"\".178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3115 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.\"\"\"\"178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3116 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90\"\"\"\"]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3117 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90]\"\"\"\""             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3118 - assertIsFalse "ip.v4.with.double.empty.string1@[\"\"\"\"123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3119 - assertIsFalse "ip.v4.with.double.empty.string1@\"\"\"\"[123.145.178.90]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3120 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3121 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3122 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3123 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3124 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3125 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3126 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3127 - assertIsFalse "\"\".\"\".local.name.starts.with.double.empty.string2@domain.com"     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3128 - assertIsFalse "local.name.ends.with.double.empty.string2\"\".\"\"@domain.com"        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3129 - assertIsFalse "local.name.with.double.empty.string2\"\".\"\"character@domain.com"    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3130 - assertIsFalse "local.name.with.double.empty.string2.before\"\".\"\".point@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3131 - assertIsFalse "local.name.with.double.empty.string2.after.\"\".\"\"point@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3132 - assertIsFalse "local.name.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3133 - assertIsFalse "(comment \"\".\"\") local.name.with.comment.with.double.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3134 - assertIsFalse "\"quote\"\".\"\"\".local.name.with.qoute.with.double.empty.string2@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3135 - assertIsFalse "\"\".\"\"@double.empty.string2.domain.com"                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3136 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3137 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3138 - assertIsTrue  "name \"\".\"\" <pointy.brackets1.with.double.empty.string2@domain.com>" =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3139 - assertIsTrue  "<pointy.brackets2.with.double.empty.string2@domain.com> name \"\".\"\"" =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3140 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3141 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3142 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3143 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3144 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3145 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3146 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3147 - assertIsFalse "domain.part.with.comment.with.double.empty.string2@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3148 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3149 - assertIsFalse "ip.v4.with.double.empty.string2@[123.14\"\".\"\"5.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3150 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145\"\".\"\".178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3151 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.\"\".\"\"178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3152 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90\"\".\"\"]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3153 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90]\"\".\"\""            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3154 - assertIsFalse "ip.v4.with.double.empty.string2@[\"\".\"\"123.145.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3155 - assertIsFalse "ip.v4.with.double.empty.string2@\"\".\"\"[123.145.178.90]"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3156 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3157 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3158 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3159 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3160 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3161 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3162 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3163 - assertIsTrue  "/.local.name.starts.with.forward.slash@domain.com"                    =   0 =  OK 
     *  3164 - assertIsTrue  "local.name.ends.with.forward.slash/@domain.com"                       =   0 =  OK 
     *  3165 - assertIsTrue  "local.name.with.forward.slash/character@domain.com"                   =   0 =  OK 
     *  3166 - assertIsTrue  "local.name.with.forward.slash.before/.point@domain.com"               =   0 =  OK 
     *  3167 - assertIsTrue  "local.name.with.forward.slash.after./point@domain.com"                =   0 =  OK 
     *  3168 - assertIsTrue  "local.name.with.double.forward.slash//test@domain.com"                =   0 =  OK 
     *  3169 - assertIsTrue  "(comment /) local.name.with.comment.with.forward.slash@domain.com"    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3170 - assertIsTrue  "\"quote/\".local.name.with.qoute.with.forward.slash@domain.com"       =   1 =  OK 
     *  3171 - assertIsTrue  "/@forward.slash.domain.com"                                           =   0 =  OK 
     *  3172 - assertIsTrue  "//////@forward.slash.domain.com"                                      =   0 =  OK 
     *  3173 - assertIsTrue  "/./././././@forward.slash.domain.com"                                 =   0 =  OK 
     *  3174 - assertIsFalse "name / <pointy.brackets1.with.forward.slash@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3175 - assertIsFalse "<pointy.brackets2.with.forward.slash@domain.com> name /"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3176 - assertIsFalse "domain.part@with/forward.slash.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3177 - assertIsFalse "domain.part@with//double.forward.slash.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3178 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3179 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3180 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3181 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3182 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3183 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3184 - assertIsTrue  "domain.part.with.comment.with.forward.slash@(comment /)domain.com"    =   6 =  OK 
     *  3185 - assertIsFalse "domain.part.only.forward.slash@/.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3186 - assertIsFalse "ip.v4.with.forward.slash@[123.14/5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3187 - assertIsFalse "ip.v4.with.forward.slash@[123.145/.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3188 - assertIsFalse "ip.v4.with.forward.slash@[123.145./178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3189 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90/]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3190 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90]/"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3191 - assertIsFalse "ip.v4.with.forward.slash@[/123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3192 - assertIsFalse "ip.v4.with.forward.slash@/[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3193 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3194 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3195 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3196 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3197 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3198 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3199 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3200 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3201 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     *  3202 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3203 - assertIsTrue  "Ärger.Öde@Übel.de"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3204 - assertIsTrue  "Smørrebrød@danmark.dk"                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3205 - assertIsTrue  "ip.without.brackets@1.2.3.4"                                          =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  3206 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3207 - assertIsTrue  "(space after comment) john.smith@example.com"                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3208 - assertIsTrue  "email.address.without@topleveldomain"                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3209 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                   =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     *  3210 - assertIsTrue  "valid.email.from.nr1086@fillup.tofalse.com"                           =   0 =  OK 
     *           ...
     *  3211 - assertIsTrue  "valid.email.to.nr2124@fillup.tofalse.com"                             =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  2124   KORREKT 2038 =   95.951 % | FALSCH ERKANNT   86 =    4.049 % = Error 0
     *   ASSERT_IS_FALSE 2124   KORREKT 2113 =   99.482 % | FALSCH ERKANNT   11 =    0.518 % = Error 0
     * 
     *   GESAMT          4248   KORREKT 4151 =   97.717 % | FALSCH ERKANNT   97 =    2.283 % = Error 0
     * 
     * 
     *   Millisekunden    132 = 0.031073446327683617
     *      
     * </pre> 
     */

    /*
     * Variable fuer die Startzeit der Funktion deklarieren und die aktuellen
     * System-Millisekunden speichern
     */
    long time_stamp_start = System.currentTimeMillis();

    //generateTestCases();

    try
    {
      wlHeadline( "Correct" );

      assertIsTrue( "n@d.td" );
      assertIsTrue( "1@2.td" );

      assertIsTrue( "name1.name2@domain1.tld" );
      assertIsTrue( "name1+name2@domain1.tld" );
      assertIsTrue( "name1-name2@domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.tu-domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.tu_domain1.tld" );

      assertIsTrue( "ip4.adress@[1.2.3.4]" );
      assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );

      assertIsTrue( "\"quote1\".name1@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@domain1.tld" );
      assertIsTrue( "name1.\"quote1\".name2@domain1.tld" );
      assertIsTrue( "\"quote1\".\"quote2\".name1@domain1.tld" );
      assertIsTrue( "\"quote1\"@domain1.tld" );
      assertIsTrue( "\"quote1\\\"qoute2\\\"\"@domain1.tld" );
      
      assertIsTrue( "(comment1)name1@domain1.tld" );
      assertIsTrue( "(comment1)-name1@domain1.tld" );
      assertIsTrue( "name1(comment1)@domain1.tld" );
      assertIsTrue( "name1@(comment1)domain1.tld" );
      assertIsTrue( "name1@domain1.tld(comment1)" );

      assertIsTrue( "(comment1)name1.ip4.adress@[1.2.3.4]" );
      assertIsTrue( "name1.ip4.adress(comment1)@[1.2.3.4]" );
      assertIsTrue( "name1.ip4.adress@(comment1)[1.2.3.4]" );
      assertIsTrue( "name1.ip4.adress@[1.2.3.4](comment1)" );

      assertIsTrue( "(comment1)\"quote1\".name1@domain1.tld" );
      assertIsTrue( "(comment1)name1.\"quote1\"@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"(comment1)@domain1.tld" );
      assertIsTrue( "\"quote1\".name1(comment1)@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@(comment1)domain1.tld" );
      assertIsTrue( "\"quote1\".name1@domain1.tld(comment1)" );

      assertIsTrue( "<name1.name2@domain1.tld>" );
      assertIsTrue( "name3 <name1.name2@domain1.tld>" );
      assertIsTrue( "<name1.name2@domain1.tld> name3" );
      assertIsTrue( "\"name3 name4\" <name1.name2@domain1.tld>" );

      assertIsTrue( "name1 <ip4.adress@[1.2.3.4]>" );
      assertIsTrue( "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>" );
      assertIsTrue( "<ip4.adress@[1.2.3.4]> name1" );
      assertIsTrue( "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1" );

      assertIsTrue( "\"display name\" <(comment)local.part@domain-name.top_level_domain>" );

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      wlHeadline( "AT-Character" );

      assertIsFalse( "1234567890" );
      assertIsFalse( "OnlyTextNoDotNoAt" );
      assertIsFalse( "email.with.no.at.character" );
      assertIsFalse( "email.with.no.domain@" );
      assertIsFalse( "@.local.name.starts.with.at@domain.com" );
      assertIsFalse( "@no.local.email.part.com" );
      assertIsFalse( "local.name.with@at@domain.com" );
      assertIsFalse( "local.name.ends.with.at@@domain.com" );
      assertIsFalse( "local.name.with.at.before@.point@domain.com" );
      assertIsFalse( "local.name.with.at.after.@point@domain.com" );
      assertIsFalse( "local.name.with.double.at@@test@domain.com" );
      assertIsFalse( "(comment @) local.name.with.comment.with.at@domain.com" );
      assertIsTrue( "domain.part.with.comment.with.at@(comment with @)domain.com" );
      assertIsFalse( "domain.part.with.comment.with.qouted.at@(comment with \\@)domain.com" );
      assertIsTrue( "\"quote@\".local.name.with.qoute.with.at@domain.com" );
      assertIsTrue( "qouted.\\@.character@domain.com" );
      assertIsTrue( "qouted\\@character@domain.com" );
      assertIsTrue( "\\@@domain.com" );
      assertIsFalse( "@@domain.com" );
      assertIsFalse( "@domain.com" );
      assertIsFalse( "@@@@@@@domain.com" );
      assertIsTrue( "\\@.\\@.\\@.\\@.\\@.\\@@domain.com" );
      assertIsFalse( "\\@.\\@.\\@.\\@.\\@.\\@@at.sub\\@domain.com" );
      assertIsFalse( "@.@.@.@.@.@@domain.com" );
      assertIsFalse( "@.@.@." );
      assertIsFalse( "\\@.\\@@\\@.\\@" );
      assertIsFalse( "@" );
      assertIsFalse( "name @ <pointy.brackets1.with.at@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.at@domain.com> name @" );

      wlHeadline( "Seperator" );

      assertIsFalse( "..local.name.starts.with.dot@domain.com" );
      assertIsFalse( "local.name.ends.with.dot.@domain.com" );
      assertIsFalse( "local.name.with.dot.before..point@domain.com" );
      assertIsFalse( "local.name.with.dot.after..point@domain.com" );
      assertIsFalse( "local.name.with.double.dot..test@domain.com" );
      assertIsFalse( "(comment .) local.name.with.comment.with.dot@domain.com" );
      assertIsTrue( "\"quote.\".local.name.with.qoute.with.dot@domain.com" );
      assertIsFalse( ".@domain.com" );
      assertIsFalse( "......@domain.com" );
      assertIsFalse( "...........@domain.com" );
      assertIsFalse( "qouted\\.dot@domain.com" );
      assertIsFalse( "name . <pointy.brackets1.with.dot@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.dot@domain.com> name ." );
      assertIsFalse( "domain.part.without.dot@domaincom" );
      assertIsFalse( "domain.part@.with.dot.at.domain.start.com" );
      assertIsFalse( "domain.part@with.dot.at.domain.end1..com" );
      assertIsFalse( "domain.part@with.dot.at.domain.end2.com." );
      assertIsFalse( "domain.part@with.dot.before..point.com" );
      assertIsFalse( "domain.part@with.dot.after..point.com" );
      assertIsFalse( "domain.part@with.consecutive.dot..test.com" );

      assertIsFalse( "EmailAdressWith@NoDots" );

      wlHeadline( "Characters" );

      assertIsTrue( "&local&&name&with&$@amp.com" );
      assertIsTrue( "*local**name*with*@asterisk.com" );
      assertIsTrue( "$local$$name$with$@dollar.com" );
      assertIsTrue( "=local==name=with=@equality.com" );
      assertIsTrue( "!local!!name!with!@exclamation.com" );
      assertIsTrue( "`local``name`with`@grave-accent.com" );
      assertIsTrue( "#local##name#with#@hash.com" );
      assertIsTrue( "-local--name-with-@hypen.com" );
      assertIsTrue( "{local{name{{with{@leftbracket.com" );
      assertIsTrue( "%local%%name%with%@percentage.com" );
      assertIsTrue( "|local||name|with|@pipe.com" );
      assertIsTrue( "+local++name+with+@plus.com" );
      assertIsTrue( "?local??name?with?@question.com" );
      assertIsTrue( "}local}name}}with}@rightbracket.com" );
      assertIsTrue( "~local~~name~with~@tilde.com" );
      assertIsTrue( "^local^^name^with^@xor.com" );
      assertIsTrue( "_local__name_with_@underscore.com" );
      assertIsFalse( ":local::name:with:@colon.com" );

      assertIsTrue( "&.local.name.starts.with.amp@domain.com" );
      assertIsTrue( "local.name.ends.with.amp&@domain.com" );
      assertIsTrue( "local.name.with.amp.before&.point@domain.com" );
      assertIsTrue( "local.name.with.amp.after.&point@domain.com" );
      assertIsTrue( "local.name.with.double.amp&&test@domain.com" );
      assertIsFalse( "(comment &) local.name.with.comment.with.amp@domain.com" ); // or ist it true?
      assertIsTrue( "\"quote&\".local.name.with.qoute.with.amp@domain.com" );
      assertIsTrue( "&@amp.domain.com" );
      assertIsTrue( "&&&&&&@amp.domain.com" );
      assertIsTrue( "&.&.&.&.&.&@amp.domain.com" );
      assertIsFalse( "name & <pointy.brackets1.with.amp@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.amp@domain.com> name &" ); // ?

      assertIsTrue( "*.local.name.starts.with.asterisk@domain.com" );
      assertIsTrue( "local.name.ends.with.asterisk*@domain.com" );
      assertIsTrue( "local.name.with.asterisk.before*.point@domain.com" );
      assertIsTrue( "local.name.with.asterisk.after.*point@domain.com" );
      assertIsTrue( "local.name.with.double.asterisk**test@domain.com" );
      assertIsFalse( "(comment *) local.name.with.comment.with.asterisk@domain.com" ); // ?
      assertIsTrue( "\"quote*\".local.name.with.qoute.with.asterisk@domain.com" );
      assertIsTrue( "*@asterisk.domain.com" );
      assertIsTrue( "******@asterisk.domain.com" );
      assertIsTrue( "*.*.*.*.*.*@asterisk.domain.com" );
      assertIsFalse( "name * <pointy.brackets1.with.asterisk@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.asterisk@domain.com> name *" ); // ?

      assertIsTrue( "$.local.name.starts.with.dollar@domain.com" );
      assertIsTrue( "local.name.ends.with.dollar$@domain.com" );
      assertIsTrue( "local.name.with.dollar.before$.point@domain.com" );
      assertIsTrue( "local.name.with.dollar.after.$point@domain.com" );
      assertIsTrue( "local.name.with.double.dollar$$test@domain.com" );
      assertIsFalse( "(comment $) local.name.with.comment.with.dollar@domain.com" ); // ?
      assertIsTrue( "\"quote$\".local.name.with.qoute.with.dollar@domain.com" );
      assertIsTrue( "$@dollar.domain.com" );
      assertIsTrue( "$$$$$$@dollar.domain.com" );
      assertIsTrue( "$.$.$.$.$.$@dollar.domain.com" );
      assertIsFalse( "name $ <pointy.brackets1.with.dollar@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.dollar@domain.com> name $" ); // ?

      assertIsTrue( "=.local.name.starts.with.equality@domain.com" );
      assertIsTrue( "local.name.ends.with.equality=@domain.com" );
      assertIsTrue( "local.name.with.equality.before=.point@domain.com" );
      assertIsTrue( "local.name.with.equality.after.=point@domain.com" );
      assertIsTrue( "local.name.with.double.equality==test@domain.com" );
      assertIsFalse( "(comment =) local.name.with.comment.with.equality@domain.com" ); // ?
      assertIsTrue( "\"quote=\".local.name.with.qoute.with.equality@domain.com" );
      assertIsTrue( "=@equality.domain.com" );
      assertIsTrue( "======@equality.domain.com" );
      assertIsTrue( "=.=.=.=.=.=@equality.domain.com" );
      assertIsFalse( "name = <pointy.brackets1.with.equality@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.equality@domain.com> name =" ); // ?

      assertIsTrue( "!.local.name.starts.with.exclamation@domain.com" );
      assertIsTrue( "local.name.ends.with.exclamation!@domain.com" );
      assertIsTrue( "local.name.with.exclamation.before!.point@domain.com" );
      assertIsTrue( "local.name.with.exclamation.after.!point@domain.com" );
      assertIsTrue( "local.name.with.double.exclamation!!test@domain.com" );
      assertIsFalse( "(comment !) local.name.with.comment.with.exclamation@domain.com" ); // ?
      assertIsTrue( "\"quote!\".local.name.with.qoute.with.exclamation@domain.com" );
      assertIsTrue( "!@exclamation.domain.com" );
      assertIsTrue( "!!!!!!@exclamation.domain.com" );
      assertIsTrue( "!.!.!.!.!.!@exclamation.domain.com" );
      assertIsFalse( "name ! <pointy.brackets1.with.exclamation@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.exclamation@domain.com> name !" ); // ?

      assertIsTrue( "`.local.name.starts.with.grave-accent@domain.com" );
      assertIsTrue( "local.name.ends.with.grave-accent`@domain.com" );
      assertIsTrue( "local.name.with.grave-accent.before`.point@domain.com" );
      assertIsTrue( "local.name.with.grave-accent.after.`point@domain.com" );
      assertIsTrue( "local.name.with.double.grave-accent``test@domain.com" );
      assertIsFalse( "(comment `) local.name.with.comment.with.grave-accent@domain.com" ); // ?
      assertIsTrue( "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com" );
      assertIsTrue( "`@grave-accent.domain.com" );
      assertIsTrue( "``````@grave-accent.domain.com" );
      assertIsTrue( "`.`.`.`.`.`@grave-accent.domain.com" );
      assertIsFalse( "name ` <pointy.brackets1.with.grave-accent@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.grave-accent@domain.com> name `" ); // ?

      assertIsTrue( "#.local.name.starts.with.hash@domain.com" );
      assertIsTrue( "local.name.ends.with.hash#@domain.com" );
      assertIsTrue( "local.name.with.hash.before#.point@domain.com" );
      assertIsTrue( "local.name.with.hash.after.#point@domain.com" );
      assertIsTrue( "local.name.with.double.hash##test@domain.com" );
      assertIsFalse( "(comment #) local.name.with.comment.with.hash@domain.com" ); // ?
      assertIsTrue( "\"quote#\".local.name.with.qoute.with.hash@domain.com" );
      assertIsTrue( "#@hash.domain.com" );
      assertIsTrue( "######@hash.domain.com" );
      assertIsTrue( "#.#.#.#.#.#@hash.domain.com" );
      assertIsFalse( "name # <pointy.brackets1.with.hash@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.hash@domain.com> name #" ); // ?

      assertIsTrue( "-.local.name.starts.with.hypen@domain.com" );
      assertIsTrue( "local.name.ends.with.hypen-@domain.com" );
      assertIsTrue( "local.name.with.hypen.before-.point@domain.com" );
      assertIsTrue( "local.name.with.hypen.after.-point@domain.com" );
      assertIsTrue( "local.name.with.double.hypen--test@domain.com" );
      assertIsFalse( "(comment -) local.name.with.comment.with.hypen@domain.com" ); // ?
      assertIsTrue( "\"quote-\".local.name.with.qoute.with.hypen@domain.com" );
      assertIsTrue( "-@hypen.domain.com" );
      assertIsTrue( "------@hypen.domain.com" );
      assertIsTrue( "-.-.-.-.-.-@hypen.domain.com" );
      assertIsFalse( "name - <pointy.brackets1.with.hypen@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.hypen@domain.com> name -" ); // ?

      assertIsTrue( "{.local.name.starts.with.leftbracket@domain.com" );
      assertIsTrue( "local.name.ends.with.leftbracket{@domain.com" );
      assertIsTrue( "local.name.with.leftbracket.before{.point@domain.com" );
      assertIsTrue( "local.name.with.leftbracket.after.{point@domain.com" );
      assertIsTrue( "local.name.with.double.leftbracket{{test@domain.com" );
      assertIsFalse( "(comment {) local.name.with.comment.with.leftbracket@domain.com" ); // ?
      assertIsTrue( "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com" );
      assertIsTrue( "{@leftbracket.domain.com" );
      assertIsTrue( "{{{{{{@leftbracket.domain.com" );
      assertIsTrue( "{.{.{.{.{.{@leftbracket.domain.com" );
      assertIsFalse( "name { <pointy.brackets1.with.leftbracket@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.leftbracket@domain.com> name {" ); // ?

      assertIsTrue( "%.local.name.starts.with.percentage@domain.com" );
      assertIsTrue( "local.name.ends.with.percentage%@domain.com" );
      assertIsTrue( "local.name.with.percentage.before%.point@domain.com" );
      assertIsTrue( "local.name.with.percentage.after.%point@domain.com" );
      assertIsTrue( "local.name.with.double.percentage%%test@domain.com" );
      assertIsFalse( "(comment %) local.name.with.comment.with.percentage@domain.com" ); // ?
      assertIsTrue( "\"quote%\".local.name.with.qoute.with.percentage@domain.com" );
      assertIsTrue( "%@percentage.domain.com" );
      assertIsTrue( "%%%%%%@percentage.domain.com" );
      assertIsTrue( "%.%.%.%.%.%@percentage.domain.com" );
      assertIsFalse( "name % <pointy.brackets1.with.percentage@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.percentage@domain.com> name %" ); // ?

      assertIsTrue( "|.local.name.starts.with.pipe@domain.com" );
      assertIsTrue( "local.name.ends.with.pipe|@domain.com" );
      assertIsTrue( "local.name.with.pipe.before|.point@domain.com" );
      assertIsTrue( "local.name.with.pipe.after.|point@domain.com" );
      assertIsTrue( "local.name.with.double.pipe||test@domain.com" );
      assertIsFalse( "(comment |) local.name.with.comment.with.pipe@domain.com" ); // ?
      assertIsTrue( "\"quote|\".local.name.with.qoute.with.pipe@domain.com" );
      assertIsTrue( "|@pipe.domain.com" );
      assertIsTrue( "||||||@pipe.domain.com" );
      assertIsTrue( "|.|.|.|.|.|@pipe.domain.com" );
      assertIsFalse( "name | <pointy.brackets1.with.pipe@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.pipe@domain.com> name |" ); // ?

      assertIsTrue( "+.local.name.starts.with.plus@domain.com" );
      assertIsTrue( "local.name.ends.with.plus+@domain.com" );
      assertIsTrue( "local.name.with.plus.before+.point@domain.com" );
      assertIsTrue( "local.name.with.plus.after.+point@domain.com" );
      assertIsTrue( "local.name.with.double.plus++test@domain.com" );
      assertIsFalse( "(comment +) local.name.with.comment.with.plus@domain.com" ); // ?
      assertIsTrue( "\"quote+\".local.name.with.qoute.with.plus@domain.com" );
      assertIsTrue( "+@plus.domain.com" );
      assertIsTrue( "++++++@plus.domain.com" );
      assertIsTrue( "+.+.+.+.+.+@plus.domain.com" );
      assertIsFalse( "name + <pointy.brackets1.with.plus@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.plus@domain.com> name +" ); // ?

      assertIsTrue( "?.local.name.starts.with.question@domain.com" );
      assertIsTrue( "local.name.ends.with.question?@domain.com" );
      assertIsTrue( "local.name.with.question.before?.point@domain.com" );
      assertIsTrue( "local.name.with.question.after.?point@domain.com" );
      assertIsTrue( "local.name.with.double.question??test@domain.com" );
      assertIsFalse( "(comment ?) local.name.with.comment.with.question@domain.com" ); // ?
      assertIsTrue( "\"quote?\".local.name.with.qoute.with.question@domain.com" );
      assertIsTrue( "?@question.domain.com" );
      assertIsTrue( "??????@question.domain.com" );
      assertIsTrue( "?.?.?.?.?.?@question.domain.com" );
      assertIsFalse( "name ? <pointy.brackets1.with.question@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.question@domain.com> name ?" );

      assertIsTrue( "}.local.name.starts.with.rightbracket@domain.com" );
      assertIsTrue( "local.name.ends.with.rightbracket}@domain.com" );
      assertIsTrue( "local.name.with.rightbracket.before}.point@domain.com" );
      assertIsTrue( "local.name.with.rightbracket.after.}point@domain.com" );
      assertIsTrue( "local.name.with.double.rightbracket}}test@domain.com" );
      assertIsFalse( "(comment }) local.name.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com" );
      assertIsTrue( "}@rightbracket.domain.com" );
      assertIsTrue( "}}}}}}@rightbracket.domain.com" );
      assertIsTrue( "}.}.}.}.}.}@rightbracket.domain.com" );
      assertIsFalse( "name } <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket@domain.com> name }" );

      assertIsTrue( "~.local.name.starts.with.tilde@domain.com" );
      assertIsTrue( "local.name.ends.with.tilde~@domain.com" );
      assertIsTrue( "local.name.with.tilde.before~.point@domain.com" );
      assertIsTrue( "local.name.with.tilde.after.~point@domain.com" );
      assertIsTrue( "local.name.with.double.tilde~~test@domain.com" );
      assertIsFalse( "(comment ~) local.name.with.comment.with.tilde@domain.com" );
      assertIsTrue( "\"quote~\".local.name.with.qoute.with.tilde@domain.com" );
      assertIsTrue( "~@tilde.domain.com" );
      assertIsTrue( "~~~~~~@tilde.domain.com" );
      assertIsTrue( "~.~.~.~.~.~@tilde.domain.com" );
      assertIsFalse( "name ~ <pointy.brackets1.with.tilde@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.tilde@domain.com> name ~" );

      assertIsTrue( "^.local.name.starts.with.xor@domain.com" );
      assertIsTrue( "local.name.ends.with.xor^@domain.com" );
      assertIsTrue( "local.name.with.xor.before^.point@domain.com" );
      assertIsTrue( "local.name.with.xor.after.^point@domain.com" );
      assertIsTrue( "local.name.with.double.xor^^test@domain.com" );
      assertIsFalse( "(comment ^) local.name.with.comment.with.xor@domain.com" );
      assertIsTrue( "\"quote^\".local.name.with.qoute.with.xor@domain.com" );
      assertIsTrue( "^@xor.domain.com" );
      assertIsTrue( "^^^^^^@xor.domain.com" );
      assertIsTrue( "^.^.^.^.^.^@xor.domain.com" );
      assertIsFalse( "name ^ <pointy.brackets1.with.xor@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.xor@domain.com> name ^" );

      assertIsTrue( "_.local.name.starts.with.underscore@domain.com" );
      assertIsTrue( "local.name.ends.with.underscore_@domain.com" );
      assertIsTrue( "local.name.with.underscore.before_.point@domain.com" );
      assertIsTrue( "local.name.with.underscore.after._point@domain.com" );
      assertIsTrue( "local.name.with.double.underscore__test@domain.com" );
      assertIsFalse( "(comment _) local.name.with.comment.with.underscore@domain.com" );
      assertIsTrue( "\"quote_\".local.name.with.qoute.with.underscore@domain.com" );
      assertIsTrue( "_@underscore.domain.com" );
      assertIsTrue( "______@underscore.domain.com" );
      assertIsTrue( "_._._._._._@underscore.domain.com" );
      assertIsFalse( "name _ <pointy.brackets1.with.underscore@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.underscore@domain.com> name _" );

      assertIsFalse( ":.local.name.starts.with.colon@domain.com" );
      assertIsFalse( "local.name.ends.with.colon:@domain.com" );
      assertIsFalse( "local.name.with.colon.before:.point@domain.com" );
      assertIsFalse( "local.name.with.colon.after.:point@domain.com" );
      assertIsFalse( "local.name.with.double.colon::test@domain.com" );
      assertIsFalse( "(comment :) local.name.with.comment.with.colon@domain.com" );
      assertIsTrue( "\"quote:\".local.name.with.qoute.with.colon@domain.com" );
      assertIsFalse( ":@colon.domain.com" );
      assertIsFalse( "::::::@colon.domain.com" );
      assertIsFalse( ":.:.:.:.:.:@colon.domain.com" );
      assertIsFalse( "name : <pointy.brackets1.with.colon@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.colon@domain.com> name :" );

      assertIsFalse( "(.local.name.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.leftbracket(@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.before(.point@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.after.(point@domain.com" );
      assertIsFalse( "local.name.with.double.leftbracket((test@domain.com" );
      assertIsFalse( "(comment () local.name.with.comment.with.leftbracket@domain.com" );
      assertIsTrue( "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com" );
      assertIsFalse( "(@leftbracket.domain.com" );
      assertIsFalse( "((((((@leftbracket.domain.com" );
      assertIsFalse( "(()(((@leftbracket.domain.com" );
      assertIsFalse( "((<)>(((@leftbracket.domain.com" );
      assertIsFalse( "(.(.(.(.(.(@leftbracket.domain.com" );
      assertIsTrue( "name ( <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.leftbracket@domain.com> name (" );

      assertIsFalse( "\\.local.name.starts.with.slash@domain.com" );
      assertIsFalse( "local.name.ends.with.slash\\@domain.com" );
      assertIsFalse( "local.name.with.slash.before\\.point@domain.com" );
      assertIsFalse( "local.name.with.slash.after.\\point@domain.com" );
      assertIsTrue( "local.name.with.double.slash\\\\test@domain.com" );
      assertIsFalse( "(comment \\) local.name.with.comment.with.slash@domain.com" );
      assertIsFalse( "\"quote\\\".local.name.with.qoute.with.slash@domain.com" );
      assertIsFalse( "\\@slash.domain.com" );
      assertIsTrue( "\\\\\\\\\\\\@slash.domain.com" );
      assertIsFalse( "\\.\\.\\.\\.\\.\\@slash.domain.com" );
      assertIsFalse( "name \\ <pointy.brackets1.with.slash@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.slash@domain.com> name \\" );

      assertIsFalse( ").local.name.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.rightbracket)@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.before).point@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.after.)point@domain.com" );
      assertIsFalse( "local.name.with.double.rightbracket))test@domain.com" );
      assertIsFalse( "(comment )) local.name.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com" );
      assertIsFalse( ")@rightbracket.domain.com" );
      assertIsFalse( "))))))@rightbracket.domain.com" );
      assertIsFalse( ").).).).).)@rightbracket.domain.com" );
      assertIsTrue( "name ) <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.rightbracket@domain.com> name )" );

      assertIsFalse( "[.local.name.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.leftbracket[@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.before[.point@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.after.[point@domain.com" );
      assertIsFalse( "local.name.with.double.leftbracket[[test@domain.com" );
      assertIsFalse( "(comment [) local.name.with.comment.with.leftbracket@domain.com" );
      assertIsTrue( "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com" );
      assertIsFalse( "[@leftbracket.domain.com" );
      assertIsFalse( "[[[[[[@leftbracket.domain.com" );
      assertIsFalse( "[.[.[.[.[.[@leftbracket.domain.com" );
      assertIsFalse( "name [ <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.leftbracket@domain.com> name [" );

      assertIsFalse( "].local.name.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.rightbracket]@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.before].point@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.after.]point@domain.com" );
      assertIsFalse( "local.name.with.double.rightbracket]]test@domain.com" );
      assertIsFalse( "(comment ]) local.name.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com" );
      assertIsFalse( "]@rightbracket.domain.com" );
      assertIsFalse( "]]]]]]@rightbracket.domain.com" );
      assertIsFalse( "].].].].].]@rightbracket.domain.com" );
      assertIsFalse( "name ] <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket@domain.com> name ]" );

      assertIsFalse( " .local.name.starts.with.space@domain.com" );
      assertIsFalse( "local.name.ends.with.space @domain.com" );
      assertIsFalse( "local.name.with.space.before .point@domain.com" );
      assertIsFalse( "local.name.with.space.after. point@domain.com" );
      assertIsFalse( "local.name.with.double.space  test@domain.com" );
      assertIsFalse( "(comment  ) local.name.with.comment.with.space@domain.com" );
      assertIsTrue( "\"quote \".local.name.with.qoute.with.space@domain.com" );
      assertIsFalse( " @space.domain.com" );
      assertIsFalse( "      @space.domain.com" );
      assertIsFalse( " . . . . . @space.domain.com" );
      assertIsTrue( "name   <pointy.brackets1.with.space@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.space@domain.com> name  " );

      assertIsFalse( "().local.name.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.name.ends.with.empty.bracket()@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.before().point@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.after.()point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.bracket()()test@domain.com" );
      assertIsFalse( "(comment ()) local.name.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com" );
      assertIsFalse( "()@empty.bracket.domain.com" );
      assertIsFalse( "()()()()()()@empty.bracket.domain.com" );
      assertIsFalse( "().().().().().()@empty.bracket.domain.com" );
      assertIsTrue( "name () <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.bracket@domain.com> name ()" );

      assertIsTrue( "{}.local.name.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.name.ends.with.empty.bracket{}@domain.com" );
      assertIsTrue( "local.name.with.empty.bracket.before{}.point@domain.com" );
      assertIsTrue( "local.name.with.empty.bracket.after.{}point@domain.com" );
      assertIsTrue( "local.name.with.double.empty.bracket{}{}test@domain.com" );
      assertIsFalse( "(comment {}) local.name.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com" );
      assertIsTrue( "{}@empty.bracket.domain.com" );
      assertIsTrue( "{}{}{}{}{}{}@empty.bracket.domain.com" );
      assertIsTrue( "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com" );
      assertIsFalse( "name {} <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket@domain.com> name {}" );

      assertIsFalse( "[].local.name.starts.with.empty.bracket@domain.com" );
      assertIsFalse( "local.name.ends.with.empty.bracket[]@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.before[].point@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.after.[]point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.bracket[][]test@domain.com" );
      assertIsFalse( "(comment []) local.name.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com" );
      assertIsFalse( "[]@empty.bracket.domain.com" );
      assertIsFalse( "[][][][][][]@empty.bracket.domain.com" );
      assertIsFalse( "[].[].[].[].[].[]@empty.bracket.domain.com" );
      assertIsFalse( "name [] <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket@domain.com> name []" );

      assertIsTrue( "999.local.name.starts.with.byte.overflow@domain.com" );
      assertIsTrue( "local.name.ends.with.byte.overflow999@domain.com" );
      assertIsTrue( "local.name.with.byte.overflow.before999.point@domain.com" );
      assertIsTrue( "local.name.with.byte.overflow.after.999point@domain.com" );
      assertIsTrue( "local.name.with.double.byte.overflow999999test@domain.com" );
      assertIsTrue( "(comment 999) local.name.with.comment.with.byte.overflow@domain.com" );
      assertIsTrue( "\"quote999\".local.name.with.qoute.with.byte.overflow@domain.com" );
      assertIsTrue( "999@byte.overflow.domain.com" );
      assertIsTrue( "999999999999999999@byte.overflow.domain.com" );
      assertIsTrue( "999.999.999.999.999.999@byte.overflow.domain.com" );
      assertIsTrue( "name 999 <pointy.brackets1.with.byte.overflow@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.byte.overflow@domain.com> name 999" );

      assertIsTrue( "\"str\".local.name.starts.with.string@domain.com" );
      assertIsFalse( "local.name.ends.with.string\"str\"@domain.com" );
      assertIsFalse( "local.name.with.string.before\"str\".point@domain.com" );
      assertIsFalse( "local.name.with.string.after.\"str\"point@domain.com" );
      assertIsFalse( "local.name.with.double.string\"str\"\"str\"test@domain.com" );
      assertIsFalse( "(comment \"str\") local.name.with.comment.with.string@domain.com" );
      assertIsFalse( "\"quote\"str\"\".local.name.with.qoute.with.string@domain.com" );
      assertIsTrue( "\"str\"@string.domain.com" );
      assertIsFalse( "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com" );
      assertIsTrue( "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com" );
      assertIsTrue( "name \"str\" <pointy.brackets1.with.string@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.string@domain.com> name \"str\"" );

      assertIsFalse( "(comment).local.name.starts.with.comment@domain.com" );
      assertIsTrue( "local.name.ends.with.comment(comment)@domain.com" );
      assertIsFalse( "local.name.with.comment.before(comment).point@domain.com" );
      assertIsFalse( "local.name.with.comment.after.(comment)point@domain.com" );
      assertIsFalse( "local.name.with.double.comment(comment)(comment)test@domain.com" );
      assertIsFalse( "(comment (comment)) local.name.with.comment.with.comment@domain.com" );
      assertIsTrue( "\"quote(comment)\".local.name.with.qoute.with.comment@domain.com" );
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

      assertIsFalse( ",.local.name.starts.with.comma@domain.com" );
      assertIsFalse( "local.name.ends.with.comma,@domain.com" );
      assertIsFalse( "local.name.with.comma.before,.point@domain.com" );
      assertIsFalse( "local.name.with.comma.after.,point@domain.com" );
      assertIsFalse( "local.name.with.double.comma,,test@domain.com" );
      assertIsFalse( "(comment ,) local.name.with.comment.with.comma@domain.com" );
      assertIsTrue( "\"quote,\".local.name.with.qoute.with.comma@domain.com" );
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

      assertIsFalse( "§.local.name.starts.with.paragraph@domain.com" );
      assertIsFalse( "local.name.ends.with.paragraph§@domain.com" );
      assertIsFalse( "local.name.with.paragraph.before§.point@domain.com" );
      assertIsFalse( "local.name.with.paragraph.after.§point@domain.com" );
      assertIsFalse( "local.name.with.double.paragraph§§test@domain.com" );
      assertIsFalse( "(comment §) local.name.with.comment.with.paragraph@domain.com" );
      assertIsFalse( "\"quote§\".local.name.with.qoute.with.paragraph@domain.com" );
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

      assertIsTrue( "'.local.name.starts.with.quote@domain.com" );
      assertIsTrue( "local.name.ends.with.quote'@domain.com" );
      assertIsTrue( "local.name.with.quote.before'.point@domain.com" );
      assertIsTrue( "local.name.with.quote.after.'point@domain.com" );
      assertIsTrue( "local.name.with.double.quote''test@domain.com" );
      assertIsFalse( "(comment ') local.name.with.comment.with.quote@domain.com" );
      assertIsTrue( "\"quote'\".local.name.with.qoute.with.quote@domain.com" );
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

      assertIsFalse( "\".local.name.starts.with.double.quote@domain.com" );
      assertIsFalse( "local.name.ends.with.double.quote\"@domain.com" );
      assertIsFalse( "local.name.with.double.quote.before\".point@domain.com" );
      assertIsFalse( "local.name.with.double.quote.after.\"point@domain.com" );
      assertIsFalse( "local.name.with.double.double.quote\"\"test@domain.com" );
      assertIsFalse( "(comment \") local.name.with.comment.with.double.quote@domain.com" );
      assertIsFalse( "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com" );
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

      assertIsFalse( ")(.local.name.starts.with.false.bracket1@domain.com" );
      assertIsFalse( "local.name.ends.with.false.bracket1)(@domain.com" );
      assertIsFalse( "local.name.with.false.bracket1.before)(.point@domain.com" );
      assertIsFalse( "local.name.with.false.bracket1.after.)(point@domain.com" );
      assertIsFalse( "local.name.with.double.false.bracket1)()(test@domain.com" );
      assertIsFalse( "(comment )() local.name.with.comment.with.false.bracket1@domain.com" );
      assertIsTrue( "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com" );
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

      assertIsTrue( "}{.local.name.starts.with.false.bracket2@domain.com" );
      assertIsTrue( "local.name.ends.with.false.bracket2}{@domain.com" );
      assertIsTrue( "local.name.with.false.bracket2.before}{.point@domain.com" );
      assertIsTrue( "local.name.with.false.bracket2.after.}{point@domain.com" );
      assertIsTrue( "local.name.with.double.false.bracket2}{}{test@domain.com" );
      assertIsFalse( "(comment }{) local.name.with.comment.with.false.bracket2@domain.com" );
      assertIsTrue( "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com" );
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

      assertIsFalse( "][.local.name.starts.with.false.bracket3@domain.com" );
      assertIsFalse( "local.name.ends.with.false.bracket3][@domain.com" );
      assertIsFalse( "local.name.with.false.bracket3.before][.point@domain.com" );
      assertIsFalse( "local.name.with.false.bracket3.after.][point@domain.com" );
      assertIsFalse( "local.name.with.double.false.bracket3][][test@domain.com" );
      assertIsFalse( "(comment ][) local.name.with.comment.with.false.bracket3@domain.com" );
      assertIsTrue( "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com" );
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

      assertIsFalse( "><.local.name.starts.with.false.bracket4@domain.com" );
      assertIsFalse( "local.name.ends.with.false.bracket4><@domain.com" );
      assertIsFalse( "local.name.with.false.bracket4.before><.point@domain.com" );
      assertIsFalse( "local.name.with.false.bracket4.after.><point@domain.com" );
      assertIsFalse( "local.name.with.double.false.bracket4><><test@domain.com" );
      assertIsFalse( "(comment ><) local.name.with.comment.with.false.bracket4@domain.com" );
      assertIsTrue( "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com" );
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

      wlHeadline( "IP V4" );

      assertIsFalse( "\"\"@[]" );
      assertIsFalse( "\"\"@[1" );
      assertIsFalse( "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" );
      assertIsFalse( "ABC.DEF@[]" );
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
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]" );

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

      assertIsTrue( "AB.\"(CD)\".EF@GHI.JKL" );
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

      assertIsFalse("email.adress@domain.com <display name>" );
      assertIsFalse("eimail.adress@domain.com <eimail.adress@domain.com>");
      assertIsFalse("display.name@false.com <email.adress@correct.com>");
      assertIsFalse("<eimail>.<adress>@domain.com");
      assertIsFalse("<eimail>.<adress> email.adress@domain.com" );
      
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
      assertIsTrue( "\"john..doe\"@example.org" );
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

      assertIsFalse( " check@this.com" );
      assertIsFalse( " email@example.com" );
      assertIsTrue( "!def!xyz%abc@example.com" );
      assertIsTrue( "!sd@gh.com" );
      assertIsTrue( "$A12345@example.com" );
      assertIsTrue( "%20f3v34g34@gvvre.com" );
      assertIsTrue( "%2@gmail.com" );
      assertIsTrue( "--@ooo.ooo" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "-asd@das.com" );
      assertIsFalse( ".....@a...." );
      assertIsFalse( "..@test.com" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( ".dot@example.com" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( ".journaldev@journaldev.com" );
      assertIsFalse( ".xxxx@mysite.org" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsFalse( "123@$.xyz" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "1@gmail.com" );
      assertIsTrue( "1_example@something.gmail.com" );
      assertIsTrue( "2@bde.cc" );
      assertIsFalse( "<1234   @   local(blah)  .machine .example>" );
      assertIsTrue( "<boss@nil.test>" );
      assertIsFalse( "@b.com" );
      assertIsFalse( "@example.com" );
      assertIsFalse( "@mail.example.com:joe@sixpack.com" );
      assertIsFalse( "@you.me.net" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsTrue( "Abc.123@example.com" );
      assertIsFalse( "Abc.example.com" );
      assertIsTrue( "Abc@10.42.0.1" );
      assertIsTrue( "Abc@example.com" );
      assertIsFalse( "Abc@example.com." );
      assertIsTrue( "D.Oy'Smith@gmail.com" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ L\\.@example.com" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@example.com" );
      assertIsFalse( "Foobar Some@thing.com" );
      assertIsTrue( "Fred\\ Bloggs@example.com" );
      assertIsTrue( "Joe.\\\\Blow@example.com" );
      assertIsFalse( "MailTo:casesensitve@domain.com" );
      assertIsTrue( "PN=Joe/OU=X400/@gateway.com" );
      assertIsTrue( "Who? <one@y.test>" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "\"%2\"@gmail.com" );
      assertIsTrue( "\"Abc@def\"@example.com" );
      assertIsTrue( "\"Abc\\@def\"@example.com" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@example.com" );
      assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
      assertIsTrue( "\"Giant; \\\"Big\\\" Box\" <sysservices@example.net>" );
      assertIsFalse( "\"Joe Q. Public\" <john.q.public@example.com>" );
      assertIsFalse( "\"Joe\\Blow\"@example.com" );
      assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
      assertIsTrue( "\"Look at all these spaces!\"@example.com" );
      assertIsTrue( "\"a..b\"@gmail.com" );
      assertIsTrue( "\"a_b\"@gmail.com" );
      assertIsTrue( "\"abcdefghixyz\"@example.com" );
      assertIsTrue( "\"cogwheel the orange\"@example.com" );
      assertIsTrue( "\"foo\\@bar\"@Something.com" );
      assertIsTrue( "\"j\\\"s\"@proseware.com" );
      assertIsTrue( "\"myemail@sa\"@mple.com" );
      assertIsFalse( "\"qu@example.com" );
      assertIsFalse( "\\$A12345@example.com" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsFalse( "_@bde.cc." );
      assertIsTrue( "_@gmail.com" );
      assertIsTrue( "_dasd@sd.com" );
      assertIsTrue( "_dasd_das_@9.com" );
      assertIsTrue( "_somename@example.com" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "a+b@c.com" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsFalse( "a..b@bde.cc" );
      assertIsFalse( "a.\"b@c\".x.\"@\".d.e@f.g@" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "a.b@com" );
      assertIsFalse( "a.b@example,co.de" );
      assertIsFalse( "a.b@example,com" );
      assertIsTrue( "a2@bde.cc" );
      assertIsFalse( "a@.com" );
      assertIsTrue( "a@123.45.67.89" );
      assertIsFalse( "a@b." );
      assertIsFalse( "a@b.-de.cc" );
      assertIsFalse( "a@b._de.cc" );
      assertIsTrue( "a@b.c.com" );
      assertIsTrue( "a@b.com" );
      assertIsTrue( "a@bc.com" );
      assertIsTrue( "a@bcom" );
      assertIsFalse( "a@bde-.cc" );
      assertIsFalse( "a@bde.cc." );
      assertIsFalse( "a@bde_.cc" );
      assertIsTrue( "a@domain.com" );
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" );
      assertIsTrue( "aaron@theinfo.org" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsFalse( "ab@b+de.cc" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "ab@c.com" );
      assertIsFalse( "ab@sd@dd" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "abc.\"defghi\".xyz@example.com" );
      assertIsTrue( "abc.xyz@gmail.com.in" );
      assertIsTrue( "abc123xyz@asdf.co.in" );
      assertIsTrue( "abc1_xyz1@gmail1.com" );
      assertIsFalse( "abc@def@example.com" );
      assertIsFalse( "abc\"defghi\"xyz@example.com" );
      assertIsTrue( "abc\\@def@example.com" );
      assertIsFalse( "abc\\@example.com" );
      assertIsFalse( "abc\\\"def\\\"ghi@example.com" );
      assertIsFalse( "abc\\\\@def@example.com" );
      assertIsTrue( "abc\\\\@example.com" );
      assertIsTrue( "abcxyz123@qwert.com" );
      assertIsTrue( "alex@example.com" );
      assertIsTrue( "alireza@test.co.uk" );
      assertIsFalse( "as3d@dac.coas-" );
      assertIsTrue( "asd-@asd.com" );
      assertIsFalse( "asd@dasd@asd.cm" );
      assertIsTrue( "begeddov@jfinity.com" );
      assertIsFalse( "check@this..com" );
      assertIsTrue( "check@this.com" );
      assertIsFalse( "check@thiscom" );
      assertIsTrue( "cog@wheel.com" );
      assertIsTrue( "customer/department=shipping@example.com" );
      assertIsTrue( "d._.___d@gmail.com" );
      assertIsTrue( "d.j@server1.proseware.com" );
      assertIsTrue( "d.oy.smith@gmail.com" );
      assertIsTrue( "d23d@da9.co9" );
      assertIsTrue( "d_oy_smith@gmail.com" );
      assertIsFalse( "da23@das..com" );
      assertIsFalse( "dad@sds" );
      assertIsTrue( "dasd-dasd@das.com.das" );
      assertIsTrue( "dasd.dadas@dasd.com" );
      assertIsTrue( "dasd_-@jdas.com" );
      assertIsFalse( "dasddas-@.com" );
      assertIsFalse( "david.gilbertson@SOME+THING-ODD!!.com" );
      assertIsTrue( "david.jones@proseware.com" );
      assertIsTrue( "dclo@us.ibm.com" );
      assertIsTrue( "dda_das@das-dasd.com" );
      assertIsTrue( "digit-only-domain-with-subdomain@sub.123.com" );
      assertIsTrue( "digit-only-domain@123.com" );
      assertIsFalse( "dot.@example.com" );
      assertIsFalse( "doug@" );
      assertIsTrue( "doysmith@gmail.com" );
      assertIsTrue( "drp@drp.cz" );
      assertIsTrue( "dsq!a?@das.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@domain" );
      assertIsFalse( "email@domain..com" );
      assertIsTrue( "email@domain.co.de" );
      assertIsTrue( "email@domain.com" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@example" );
      assertIsFalse( "email@example..com" );
      assertIsTrue( "email@example.co.uk" );
      assertIsFalse( "email@example.co.uk." );
      assertIsFalse( "email@example.com " );
      assertIsTrue( "email@example.com" );
      assertIsTrue( "email@mail.gmail.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "example@example.co" );
      assertIsFalse( "f...bar@gmail.com" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsTrue( "f.o.o.b.a.r@gmail.com" );
      assertIsFalse( "fdsa" );
      assertIsFalse( "fdsa@" );
      assertIsFalse( "fdsa@fdsa" );
      assertIsFalse( "fdsa@fdsa." );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsFalse( "foo.bar#gmail.co.u" );
      assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );
      assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
      assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
      assertIsFalse( "foo~&(&)(@bar.com" );
      assertIsTrue( "futureTLD@somewhere.fooo" );
      assertIsFalse( "gatsby@f.sc.ot.t.f.i.tzg.era.l.d." );
      assertIsFalse( "get_at_m.e@gmail" );
      assertIsFalse( "hallo2ww22@example....caaaao" );
      assertIsFalse( "hallo@example.coassjj#sswzazaaaa" );
      assertIsFalse( "hello world@example.com" );
      assertIsTrue( "hello.me_1@email.com" );
      assertIsTrue( "hello7___@ca.com.pt" );
      assertIsTrue( "info@ermaelan.com" );
      assertIsFalse( "invalid.email.com" );
      assertIsFalse( "invalid@email@domain.com" );
      assertIsFalse( "j..s@proseware.com" );
      assertIsFalse( "j.@server1.proseware.com" );
      assertIsTrue( "j.s@server1.proseware.com" );
      assertIsTrue( "j@proseware.com9" );
      assertIsTrue( "j_9@[129.126.118.1]" );
      assertIsFalse( "jane@jungle.com: | /usr/bin/vacation" );
      assertIsTrue( "jinujawad6s@gmail.com" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "jones@ms1.proseware.com" );
      assertIsFalse( "journaldev" );
      assertIsFalse( "journaldev()*@gmail.com" );
      assertIsTrue( "journaldev+100@gmail.com" );
      assertIsTrue( "journaldev-100@journaldev.net" );
      assertIsTrue( "journaldev-100@yahoo-test.com" );
      assertIsTrue( "journaldev-100@yahoo.com" );
      assertIsFalse( "journaldev..2002@gmail.com" );
      assertIsTrue( "journaldev.100@journaldev.com.au" );
      assertIsTrue( "journaldev.100@yahoo.com" );
      assertIsFalse( "journaldev.@gmail.com" );
      assertIsTrue( "journaldev111@journaldev.com" );
      assertIsFalse( "journaldev123@.com" );
      assertIsFalse( "journaldev123@.com.com" );
      assertIsFalse( "journaldev123@gmail.a" );
      assertIsFalse( "journaldev@%*.com" );
      assertIsFalse( "journaldev@.com.my" );
      assertIsTrue( "journaldev@1.com" );
      assertIsFalse( "journaldev@gmail.com.1a" );
      assertIsTrue( "journaldev@gmail.com.com" );
      assertIsFalse( "journaldev@journaldev@gmail.com" );
      assertIsTrue( "journaldev@yahoo.com" );
      assertIsTrue( "journaldev_100@yahoo-test.ABC.CoM" );
      assertIsTrue( "js#internal@proseware.com" );
      assertIsTrue( "js*@proseware.com" );
      assertIsFalse( "js@proseware..com" );
      assertIsTrue( "js@proseware.com9" );
      assertIsFalse( "mailto:email@domain.com" );

      assertIsFalse( "me@.com.my" );
      assertIsFalse( "me123@.com" );
      assertIsFalse( "me123@.com.com" );
      assertIsFalse( "me@%*.com" );
      assertIsFalse( "me..2002@gmail.com" );
      assertIsFalse( "me.@gmail.com" );
      assertIsFalse( "me@me@gmail.com" );
      assertIsTrue( "me@gmail.com" );
      assertIsTrue( "me@me.cu.uk" );
      assertIsTrue( "me-100@me.com" );
      assertIsTrue( "me.100@me.com" );
      assertIsTrue( "me-100@me.com.au" );
      assertIsTrue( "me+100@me.com" );
      assertIsTrue( "me-100@yahoo-test.com" );
      assertIsTrue( "me@gmail.com" );
      assertIsFalse( "me@gmail.com.1a" );
      assertIsTrue( "me@company.co.uk" );

      assertIsTrue( "user_name@domain.com" );
      assertIsTrue( "user_name@domain.co.in" );
      assertIsTrue( "user@domaincom" );
      assertIsTrue( "user@domain.com" );
      assertIsTrue( "user@domain.co.in" );
      assertIsTrue( "user?name@domain.co.in" );
      assertIsTrue( "user1@domain.com" );
      assertIsTrue( "user.name@domain.com" );
      assertIsTrue( "user-name@domain.co.in" );
      assertIsTrue( "user'name@domain.co.in" );
      assertIsTrue( "user#@domain.co.in" );
      assertIsTrue( "username@yahoo.corporate.in" );
      assertIsTrue( "username@yahoo.corporate" );
      assertIsFalse( "username@yahoo.com." );
      assertIsFalse( "username@yahoo.c" );
      assertIsFalse( "username@yahoo..com" );
      assertIsFalse( "user#domain.com" );
      assertIsFalse( "@yahoo.com" );
      assertIsFalse( ".username@yahoo.com" );

      assertIsTrue( "abc@abc.com.com.com.com" );
      assertIsTrue( "abc@abc.co.in" );
      assertIsTrue( "abc@abc.abc" );
      assertIsTrue( "abc@abc.abcde" );
      assertIsTrue( "abc@abc.abcd" );
      assertIsTrue( "abc.efg@gmail.com" );
      assertIsTrue( "abc@gmail.com.my" );
      assertIsFalse( "abc@gmail..com" );
      assertIsFalse( "abc@gmail.com.." );
      assertIsFalse( "No -foo@bar.com" );
      assertIsFalse( "No asd@-bar.com" );
      assertIsFalse( ".username@yahoo.com" );

      assertIsTrue( "a&d@somedomain.com" );
      assertIsTrue( "a*d@somedomain.com" );
      assertIsTrue( "a/d@somedomain.com" );

      assertIsTrue( "\"a@b\"@example.com" );
      assertIsFalse( ".abc@somedomain.com" );
      assertIsFalse( "abc.@somedomain.com" );
      assertIsFalse( "a>b@somedomain.com" );

      assertIsTrue( "me+alpha@example.com" );
      assertIsTrue( "o'hare@example.com" );
      assertIsTrue( "me@mail.s2.example.com" );
      assertIsFalse( "{something}@{something}.{something}" );
      assertIsTrue( "3c296rD3HNEE@d139c.a51" );
      assertIsTrue( "This is <john@127.0.0.1>" );
      assertIsTrue( "This is <john@[127.0.0.1]>" );

      assertIsTrue( "john.doe@example.com" );
      assertIsTrue( "john.o'doe@example.com" );
      assertIsTrue( "John <john@doe.com>" );
      assertIsTrue( "<john@doe.com>" );

      assertIsTrue( "a_z%@gmail.com" );
      assertIsTrue( "a__z@provider.com" );
      assertIsTrue( "A__z/J0hn.sm{it!}h_comment@example.com.co" );

      assertIsTrue( "me@aaronsw.com" );
      assertIsTrue( "my.ownsite@ourearth.org" );
      assertIsFalse( "myemail@@sample.com" );
      assertIsFalse( "myemail@sa@mple.com" );
      assertIsTrue( "myemail@sample" );
      assertIsTrue( "myemail@sample.com" );
      assertIsFalse( "myemailsample.com" );
      assertIsTrue( "mysite@you.me.net" );
      assertIsFalse( "ote\"@example.com" );
      assertIsTrue( "peter.example@yahoo.com.au" );
      assertIsTrue( "peter.piper@example.com" );
      assertIsTrue( "peter_123@news.com" );
      assertIsTrue( "pio^_pio@factory.com" );
      assertIsTrue( "pio_#pio@factory.com" );
      assertIsFalse( "pio_pio@#factory.com" );
      assertIsFalse( "pio_pio@factory.c#om" );
      assertIsFalse( "pio_pio@factory.c*om" );
      assertIsTrue( "pio_pio@factory.com" );
      assertIsTrue( "pio_~pio@factory.com" );
      assertIsTrue( "piskvor@example.lighting" );
      assertIsFalse( "plain.address" );
      assertIsFalse( "plainaddress" );
      assertIsTrue( "rss-dev@yahoogroups.com" );
      assertIsTrue( "someone+tag@somewhere.net" );
      assertIsTrue( "someone@somewhere.co.uk" );
      assertIsTrue( "someone@somewhere.com" );
      assertIsTrue( "something_valid@somewhere.tld" );
      assertIsFalse( "tarzan@jungle.org,jane@jungle.org" );
      assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
      assertIsTrue( "tvf@tvf.cz" );
      assertIsFalse( "two..dot@example.com" );
      assertIsTrue( "user+mailbox@example.com" );
      assertIsTrue( "vdv@dyomedea.com" );
      assertIsFalse( "xxxx..1234@yahoo.com" );
      assertIsFalse( "xxxx.ourearth.com" );
      assertIsFalse( "xxxx123@gmail.b" );
      assertIsFalse( "xxxx@.com.my" );
      assertIsFalse( "xxxx@.org.org" );
      assertIsTrue( "xxxx@gmail.com" );
      assertIsFalse( "xxxxx()*@gmail.com" );
      assertIsTrue( "xxxxxx@yahoo.com" );

      assertIsFalse( "<')))><@fish.left.com" );
      assertIsFalse( "><(((*>@fish.right.com" );

      assertIsTrue( "{^c\\@**Dog^}@cartoon.com" );
      assertIsFalse( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" );
      assertIsFalse( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" );
      assertIsTrue( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" );
      assertIsTrue( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" );

      assertIsFalse( "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" );

      assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "user@[IPv6:2001:DB8::1]" );
      assertIsTrue( "user@localserver" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsTrue( "yoursite@ourearth.com" );
      assertIsTrue( "~pio_pio@factory.com" );

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
      assertIsFalse( "unbracketed-IP@127.0.0.1" );
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
      assertIsFalse( "test@123.123.123.123" );
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
      assertIsFalse( "foobar@192.168.0.1" );
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

      assertIsFalse( "\"\".local.name.starts.with.empty.string1@domain.com" );
      assertIsFalse( "local.name.ends.with.empty.string1\"\"@domain.com" );
      assertIsFalse( "local.name.with.empty.string1\"\"character@domain.com" );
      assertIsFalse( "local.name.with.empty.string1.before\"\".point@domain.com" );
      assertIsFalse( "local.name.with.empty.string1.after.\"\"point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string1\"\"\"\"test@domain.com" );
      assertIsFalse( "(comment \"\") local.name.with.comment.with.empty.string1@domain.com" );
      assertIsFalse( "\"quote\"\"\".local.name.with.qoute.with.empty.string1@domain.com" );
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

      assertIsFalse( "a\"\"b.local.name.starts.with.empty.string2@domain.com" );
      assertIsFalse( "local.name.ends.with.empty.string2a\"\"b@domain.com" );
      assertIsFalse( "local.name.with.empty.string2a\"\"bcharacter@domain.com" );
      assertIsFalse( "local.name.with.empty.string2.beforea\"\"b.point@domain.com" );
      assertIsFalse( "local.name.with.empty.string2.after.a\"\"bpoint@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string2a\"\"ba\"\"btest@domain.com" );
      assertIsFalse( "(comment a\"\"b) local.name.with.comment.with.empty.string2@domain.com" );
      assertIsFalse( "\"quotea\"\"b\".local.name.with.qoute.with.empty.string2@domain.com" );
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

      assertIsFalse( "\"\"\"\".local.name.starts.with.double.empty.string1@domain.com" );
      assertIsFalse( "local.name.ends.with.double.empty.string1\"\"\"\"@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string1\"\"\"\"character@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string1.before\"\"\"\".point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string1.after.\"\"\"\"point@domain.com" );
      assertIsFalse( "local.name.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" );
      assertIsFalse( "(comment \"\"\"\") local.name.with.comment.with.double.empty.string1@domain.com" );
      assertIsFalse( "\"quote\"\"\"\"\".local.name.with.qoute.with.double.empty.string1@domain.com" );
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

      assertIsFalse( "\"\".\"\".local.name.starts.with.double.empty.string2@domain.com" );
      assertIsFalse( "local.name.ends.with.double.empty.string2\"\".\"\"@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string2\"\".\"\"character@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string2.before\"\".\"\".point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.string2.after.\"\".\"\"point@domain.com" );
      assertIsFalse( "local.name.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" );
      assertIsFalse( "(comment \"\".\"\") local.name.with.comment.with.double.empty.string2@domain.com" );
      assertIsFalse( "\"quote\"\".\"\"\".local.name.with.qoute.with.double.empty.string2@domain.com" );
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

      assertIsTrue( "/.local.name.starts.with.forward.slash@domain.com" );
      assertIsTrue( "local.name.ends.with.forward.slash/@domain.com" );
      assertIsTrue( "local.name.with.forward.slash/character@domain.com" );
      assertIsTrue( "local.name.with.forward.slash.before/.point@domain.com" );
      assertIsTrue( "local.name.with.forward.slash.after./point@domain.com" );
      assertIsTrue( "local.name.with.double.forward.slash//test@domain.com" );
      assertIsTrue( "(comment /) local.name.with.comment.with.forward.slash@domain.com" );
      assertIsTrue( "\"quote/\".local.name.with.qoute.with.forward.slash@domain.com" );
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

      assertIsTrue( "ip.without.brackets@1.2.3.4" );
      assertIsTrue( "ip.without.brackets@1:2:3:4:5:6:7:8" );

      assertIsTrue( "(space after comment) john.smith@example.com" );

      assertIsTrue( "email.address.without@topleveldomain" );

      assertIsTrue( "EmailAddressWithout@PointSeperator" );

      wlHeadline( "Fillup" );

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

    double email_ok_proz_true_korrekt_erkannt = ( 100.0 * T_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_TRUE;

    double email_ok_proz_false_korrekt_erkannt = ( 100.0 * F_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_FALSE;

    double email_ok_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    double email_false_proz_true_korrekt_erkannt = ( 100.0 * T_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_TRUE;

    double email_false_proz_false_korrekt_erkannt = ( 100.0 * F_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_FALSE;

    double email_false_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( T_RESULT_COUNT_EMAIL_IS_FALSE + F_RESULT_COUNT_EMAIL_IS_TRUE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    wl( "" );

    wlHeadline( "Statistik" );

    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_ok_proz_true_korrekt_erkannt ) + m_number_format.format( email_ok_proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_true_korrekt_erkannt ) + m_number_format.format( email_false_proz_true_korrekt_erkannt ) + " % = Error " + T_RESULT_COUNT_ERROR );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_ok_proz_false_korrekt_erkannt ) + m_number_format.format( email_ok_proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_false_proz_false_korrekt_erkannt ) + m_number_format.format( email_false_proz_false_korrekt_erkannt ) + " % = Error " + F_RESULT_COUNT_ERROR );
    wl( "" );
    wl( "  GESAMT          " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( email_ok_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_ok_proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE + T_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_false_proz_korrekt_erkannt_insgesamt ) + " % = Error " + ( T_RESULT_COUNT_ERROR + F_RESULT_COUNT_ERROR ) );
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
        T_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        T_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      T_RESULT_COUNT_ERROR++;

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
        F_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        F_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      F_RESULT_COUNT_ERROR++;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = Fehler = " + err_inst.getMessage() );
      }
    }

    COUNT_ASSERT_IS_FALSE++;
  }

  private static void generateTestCases()
  {
    generateTest( "_", "underscore" );
    generateTest( "&", "amp" );
    generateTest( "*", "asterisk" );
    generateTest( "$", "dollar" );
    generateTest( "=", "equality" );
    generateTest( "!", "exclamation" );
    generateTest( "?", "question" );
    generateTest( "`", "grave-accent" );
    generateTest( "#", "hash" );
    generateTest( "%", "percentage" );
    generateTest( "|", "pipe" );
    generateTest( "+", "plus" );
    generateTest( "{", "leftbracket" );
    generateTest( "}", "rightbracket" );
    generateTest( "(", "leftbracket" );
    generateTest( ")", "rightbracket" );
    generateTest( "[", "leftbracket" );
    generateTest( "]", "rightbracket" );
    generateTest( "<", "lower.than" );
    generateTest( ">", "greater.than" );
    generateTest( "~", "tilde" );
    generateTest( "^", "xor" );
    generateTest( ":", "colon" );
    generateTest( " ", "space" );
    generateTest( ".", "dot" );
    generateTest( ",", "comma" );
    generateTest( "@", "at" );
    generateTest( "§", "paragraph" );
    generateTest( "'", "double.quote" );
    generateTest( "\"", "double.quote" );
    generateTest( "/", "forward.slash" );
    generateTest( "-", "hyphen" );

    generateTest( "()", "empty.bracket" );
    generateTest( "{}", "empty.bracket" );
    generateTest( "[]", "empty.bracket" );
    generateTest( "<>", "empty.bracket" );
    generateTest( "\\\"\\\"", "empty.string1" );
    generateTest( "a\\\"\\\"b", "empty.string2" );
    generateTest( "\\\"\\\"\\\"\\\"", "double.empty.string1" );
    generateTest( "\\\"\\\".\\\"\\\"", "double.empty.string2" );

    generateTest( ")(", "false.bracket1" );
    generateTest( "}{", "false.bracket2" );
    generateTest( "][", "false.bracket3" );
    generateTest( "><", "false.bracket4" );

    generateTest( "0", "number0" );
    generateTest( "9", "number9" );

    generateTest( "0123456789", "numbers" );

    generateTest( "\\\\", "slash" );

    generateTest( "999", "byte.overflow" );
    generateTest( "xyz", "no.hex.number" );

    generateTest( "\\\"str\\\"", "string" );
    generateTest( "(comment)", "comment" );

  }

  private static void generateTest( String pCharacter, String pName )
  {
    wl( "" );
    wl( "      assertIsTrue( \"" + pCharacter + ".local.name.starts.with." + pName + "@domain.com\" );" );
    wl( "      assertIsTrue( \"local.name.ends.with." + pName + pCharacter + "@domain.com\" );" );
    wl( "      assertIsTrue( \"local.name.with." + pName + pCharacter + "character@domain.com\" );" );
    wl( "      assertIsTrue( \"local.name.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
    wl( "      assertIsTrue( \"local.name.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
    wl( "      assertIsTrue( \"local.name.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
    wl( "      assertIsTrue( \"(comment " + pCharacter + ") local.name.with.comment.with." + pName + "@domain.com\" );" );
    wl( "      assertIsTrue( \"\\\"quote" + pCharacter + "\\\".local.name.with.qoute.with." + pName + "@domain.com\" );" );
    wl( "      assertIsTrue( \"" + pCharacter + "@" + pName + ".domain.com\" );" );
    wl( "      assertIsTrue( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@" + pName + ".domain.com\" );" );
    wl( "      assertIsTrue( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
    wl( "      assertIsTrue( \"name " + pCharacter + " <pointy.brackets1.with." + pName + "@domain.com>\" );" );
    wl( "      assertIsTrue( \"<pointy.brackets2.with." + pName + "@domain.com> name " + pCharacter + "\" );" );
    wl( "" );
    wl( "      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
    wl( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with.consecutive." + pName + "" + pCharacter + "" + pCharacter + "test.com\" );" );
    wl( "      assertIsFalse( \"domain.part.with.comment.with." + pName + "@(comment " + pCharacter + ")domain.com\" );" );

    wl( "      assertIsFalse( \"domain.part.only." + pName + "@" + pCharacter + ".com\" );" );

    wl( "" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.14" + pCharacter + "5.178.90]\" );" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145" + pCharacter + ".178.90]\" );" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145." + pCharacter + "178.90]\" );" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90" + pCharacter + "]\" );" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90]" + pCharacter + "\" );" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[" + pCharacter + "123.145.178.90]\" );" );
    wl( "      assertIsFalse( \"ip.v4.with." + pName + "@" + pCharacter + "[123.145.178.90]\" );" );
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
