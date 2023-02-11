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
     *     7 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                 =   2 =  OK 
     *     8 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                    =   4 =  OK 
     *     9 - assertIsTrue  "\"quote1\".name1@domain1.tld"                                         =   1 =  OK 
     *    10 - assertIsTrue  "name1.\"quote1\"@domain1.tld"                                         =   1 =  OK 
     *    11 - assertIsTrue  "\"quote1\".\"quote2\".name1@domain1.tld"                              =   1 =  OK 
     *    12 - assertIsTrue  "(comment1)name1@domain1.tld"                                          =   6 =  OK 
     *    13 - assertIsTrue  "name1(comment1)@domain1.tld"                                          =   6 =  OK 
     *    14 - assertIsTrue  "name1@(comment1)domain1.tld"                                          =   6 =  OK 
     *    15 - assertIsTrue  "name1@domain1.tld(comment1)"                                          =   6 =  OK 
     *    16 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                 =   2 =  OK 
     *    17 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                 =   2 =  OK 
     *    18 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                 =   2 =  OK 
     *    19 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                 =   2 =  OK 
     *    20 - assertIsTrue  "(comment1)\"quote1\".name1@domain1.tld"                               =   7 =  OK 
     *    21 - assertIsTrue  "(comment1)name1.\"quote1\"@domain1.tld"                               =   7 =  OK 
     *    22 - assertIsTrue  "name1.\"quote1\"(comment1)@domain1.tld"                               =   7 =  OK 
     *    23 - assertIsTrue  "\"quote1\".name1(comment1)@domain1.tld"                               =   7 =  OK 
     *    24 - assertIsTrue  "name1.\"quote1\"@(comment1)domain1.tld"                               =   7 =  OK 
     *    25 - assertIsTrue  "\"quote1\".name1@domain1.tld(comment1)"                               =   7 =  OK 
     *    26 - assertIsTrue  "<name1.name2@domain1.tld>"                                            =   0 =  OK 
     *    27 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                      =   0 =  OK 
     *    28 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                      =   0 =  OK 
     *    29 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                            =   0 =  OK 
     *    30 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                         =   2 =  OK 
     *    31 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                            =   4 =  OK 
     *    32 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                         =   2 =  OK 
     *    33 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                           =   4 =  OK 
     *    34 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"  =   6 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    35 - assertIsFalse null                                                                   =  10 =  OK    Laenge: Eingabe ist null
     *    36 - assertIsFalse ""                                                                     =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    37 - assertIsFalse "        "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    38 - assertIsFalse "1234567890"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    39 - assertIsFalse "OnlyTextNoDotNoAt"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    40 - assertIsFalse "email.with.no.at.character"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    41 - assertIsFalse "email.with.no.domain@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    42 - assertIsFalse "@.local.name.starts.with.at@domain.com"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    43 - assertIsFalse "@no.local.email.part.com"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    44 - assertIsFalse "local.name.with@at@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    45 - assertIsFalse "local.name.ends.with.at@@domain.com"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    46 - assertIsFalse "local.name.with.at.before@.point@domain.com"                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    47 - assertIsFalse "local.name.with.at.after.@point@domain.com"                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    48 - assertIsFalse "local.name.with.double.at@@test@domain.com"                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    49 - assertIsFalse "(comment @) local.name.with.comment.with.at@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    50 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"          =   6 =  OK 
     *    51 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"  =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *    52 - assertIsTrue  "\"quote@\".local.name.with.qoute.with.at@domain.com"                  =   1 =  OK 
     *    53 - assertIsTrue  "qouted.\@.character@domain.com"                                       =   0 =  OK 
     *    54 - assertIsTrue  "qouted\@character@domain.com"                                         =   0 =  OK 
     *    55 - assertIsTrue  "\@@domain.com"                                                        =   0 =  OK 
     *    56 - assertIsFalse "@@domain.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    57 - assertIsFalse "@domain.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    58 - assertIsFalse "@@@@@@@domain.com"                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    59 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                         =   0 =  OK 
     *    60 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    61 - assertIsFalse "@.@.@.@.@.@@domain.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    62 - assertIsFalse "@.@.@."                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    63 - assertIsFalse "\@.\@@\@.\@"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    64 - assertIsFalse "@"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    65 - assertIsFalse "name @ <pointy.brackets1.with.at@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    66 - assertIsFalse "<pointy.brackets2.with.at@domain.com> name @"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    67 - assertIsFalse "..local.name.starts.with.dot@domain.com"                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    68 - assertIsFalse "local.name.ends.with.dot.@domain.com"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    69 - assertIsFalse "local.name.with.dot.before..point@domain.com"                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    70 - assertIsFalse "local.name.with.dot.after..point@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    71 - assertIsFalse "local.name.with.double.dot..test@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    72 - assertIsFalse "(comment .) local.name.with.comment.with.dot@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    73 - assertIsTrue  "\"quote.\".local.name.with.qoute.with.dot@domain.com"                 =   1 =  OK 
     *    74 - assertIsFalse ".@domain.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    75 - assertIsFalse "......@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    76 - assertIsFalse "...........@domain.com"                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    77 - assertIsFalse "qouted\.dot@domain.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *    78 - assertIsFalse "name . <pointy.brackets1.with.dot@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    79 - assertIsFalse "<pointy.brackets2.with.dot@domain.com> name ."                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    80 - assertIsFalse "domain.part.without.dot@domaincom"                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    81 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    82 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    83 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    84 - assertIsFalse "domain.part@with.dot.before..point.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    85 - assertIsFalse "domain.part@with.dot.after..point.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    86 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    87 - assertIsFalse "EmailAdressWith@NoDots"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    88 - assertIsTrue  "&local&&name&with&$@amp.com"                                          =   0 =  OK 
     *    89 - assertIsTrue  "*local**name*with*@asterisk.com"                                      =   0 =  OK 
     *    90 - assertIsTrue  "$local$$name$with$@dollar.com"                                        =   0 =  OK 
     *    91 - assertIsTrue  "=local==name=with=@equality.com"                                      =   0 =  OK 
     *    92 - assertIsTrue  "!local!!name!with!@exclamation.com"                                   =   0 =  OK 
     *    93 - assertIsTrue  "`local``name`with`@grave-accent.com"                                  =   0 =  OK 
     *    94 - assertIsTrue  "#local##name#with#@hash.com"                                          =   0 =  OK 
     *    95 - assertIsTrue  "-local--name-with-@hypen.com"                                         =   0 =  OK 
     *    96 - assertIsTrue  "{local{name{{with{@leftbracket.com"                                   =   0 =  OK 
     *    97 - assertIsTrue  "%local%%name%with%@percentage.com"                                    =   0 =  OK 
     *    98 - assertIsTrue  "|local||name|with|@pipe.com"                                          =   0 =  OK 
     *    99 - assertIsTrue  "+local++name+with+@plus.com"                                          =   0 =  OK 
     *   100 - assertIsTrue  "?local??name?with?@question.com"                                      =   0 =  OK 
     *   101 - assertIsTrue  "}local}name}}with}@rightbracket.com"                                  =   0 =  OK 
     *   102 - assertIsTrue  "~local~~name~with~@tilde.com"                                         =   0 =  OK 
     *   103 - assertIsTrue  "^local^^name^with^@xor.com"                                           =   0 =  OK 
     *   104 - assertIsTrue  "_local__name_with_@underscore.com"                                    =   0 =  OK 
     *   105 - assertIsFalse ":local::name:with:@colon.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   106 - assertIsTrue  "&.local.name.starts.with.amp@domain.com"                              =   0 =  OK 
     *   107 - assertIsTrue  "local.name.ends.with.amp&@domain.com"                                 =   0 =  OK 
     *   108 - assertIsTrue  "local.name.with.amp.before&.point@domain.com"                         =   0 =  OK 
     *   109 - assertIsTrue  "local.name.with.amp.after.&point@domain.com"                          =   0 =  OK 
     *   110 - assertIsTrue  "local.name.with.double.amp&&test@domain.com"                          =   0 =  OK 
     *   111 - assertIsFalse "(comment &) local.name.with.comment.with.amp@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   112 - assertIsTrue  "\"quote&\".local.name.with.qoute.with.amp@domain.com"                 =   1 =  OK 
     *   113 - assertIsTrue  "&@amp.domain.com"                                                     =   0 =  OK 
     *   114 - assertIsTrue  "&&&&&&@amp.domain.com"                                                =   0 =  OK 
     *   115 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                           =   0 =  OK 
     *   116 - assertIsFalse "name & <pointy.brackets1.with.amp@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   117 - assertIsFalse "<pointy.brackets2.with.amp@domain.com> name &"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   118 - assertIsTrue  "*.local.name.starts.with.asterisk@domain.com"                         =   0 =  OK 
     *   119 - assertIsTrue  "local.name.ends.with.asterisk*@domain.com"                            =   0 =  OK 
     *   120 - assertIsTrue  "local.name.with.asterisk.before*.point@domain.com"                    =   0 =  OK 
     *   121 - assertIsTrue  "local.name.with.asterisk.after.*point@domain.com"                     =   0 =  OK 
     *   122 - assertIsTrue  "local.name.with.double.asterisk**test@domain.com"                     =   0 =  OK 
     *   123 - assertIsFalse "(comment *) local.name.with.comment.with.asterisk@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   124 - assertIsTrue  "\"quote*\".local.name.with.qoute.with.asterisk@domain.com"            =   1 =  OK 
     *   125 - assertIsTrue  "*@asterisk.domain.com"                                                =   0 =  OK 
     *   126 - assertIsTrue  "******@asterisk.domain.com"                                           =   0 =  OK 
     *   127 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                      =   0 =  OK 
     *   128 - assertIsFalse "name * <pointy.brackets1.with.asterisk@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   129 - assertIsFalse "<pointy.brackets2.with.asterisk@domain.com> name *"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   130 - assertIsTrue  "$.local.name.starts.with.dollar@domain.com"                           =   0 =  OK 
     *   131 - assertIsTrue  "local.name.ends.with.dollar$@domain.com"                              =   0 =  OK 
     *   132 - assertIsTrue  "local.name.with.dollar.before$.point@domain.com"                      =   0 =  OK 
     *   133 - assertIsTrue  "local.name.with.dollar.after.$point@domain.com"                       =   0 =  OK 
     *   134 - assertIsTrue  "local.name.with.double.dollar$$test@domain.com"                       =   0 =  OK 
     *   135 - assertIsFalse "(comment $) local.name.with.comment.with.dollar@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   136 - assertIsTrue  "\"quote$\".local.name.with.qoute.with.dollar@domain.com"              =   1 =  OK 
     *   137 - assertIsTrue  "$@dollar.domain.com"                                                  =   0 =  OK 
     *   138 - assertIsTrue  "$$$$$$@dollar.domain.com"                                             =   0 =  OK 
     *   139 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                        =   0 =  OK 
     *   140 - assertIsFalse "name $ <pointy.brackets1.with.dollar@domain.com>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   141 - assertIsFalse "<pointy.brackets2.with.dollar@domain.com> name $"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   142 - assertIsTrue  "=.local.name.starts.with.equality@domain.com"                         =   0 =  OK 
     *   143 - assertIsTrue  "local.name.ends.with.equality=@domain.com"                            =   0 =  OK 
     *   144 - assertIsTrue  "local.name.with.equality.before=.point@domain.com"                    =   0 =  OK 
     *   145 - assertIsTrue  "local.name.with.equality.after.=point@domain.com"                     =   0 =  OK 
     *   146 - assertIsTrue  "local.name.with.double.equality==test@domain.com"                     =   0 =  OK 
     *   147 - assertIsFalse "(comment =) local.name.with.comment.with.equality@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   148 - assertIsTrue  "\"quote=\".local.name.with.qoute.with.equality@domain.com"            =   1 =  OK 
     *   149 - assertIsTrue  "=@equality.domain.com"                                                =   0 =  OK 
     *   150 - assertIsTrue  "======@equality.domain.com"                                           =   0 =  OK 
     *   151 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                      =   0 =  OK 
     *   152 - assertIsFalse "name = <pointy.brackets1.with.equality@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   153 - assertIsFalse "<pointy.brackets2.with.equality@domain.com> name ="                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   154 - assertIsTrue  "!.local.name.starts.with.exclamation@domain.com"                      =   0 =  OK 
     *   155 - assertIsTrue  "local.name.ends.with.exclamation!@domain.com"                         =   0 =  OK 
     *   156 - assertIsTrue  "local.name.with.exclamation.before!.point@domain.com"                 =   0 =  OK 
     *   157 - assertIsTrue  "local.name.with.exclamation.after.!point@domain.com"                  =   0 =  OK 
     *   158 - assertIsTrue  "local.name.with.double.exclamation!!test@domain.com"                  =   0 =  OK 
     *   159 - assertIsFalse "(comment !) local.name.with.comment.with.exclamation@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   160 - assertIsTrue  "\"quote!\".local.name.with.qoute.with.exclamation@domain.com"         =   1 =  OK 
     *   161 - assertIsTrue  "!@exclamation.domain.com"                                             =   0 =  OK 
     *   162 - assertIsTrue  "!!!!!!@exclamation.domain.com"                                        =   0 =  OK 
     *   163 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                   =   0 =  OK 
     *   164 - assertIsFalse "name ! <pointy.brackets1.with.exclamation@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   165 - assertIsFalse "<pointy.brackets2.with.exclamation@domain.com> name !"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   166 - assertIsTrue  "`.local.name.starts.with.grave-accent@domain.com"                     =   0 =  OK 
     *   167 - assertIsTrue  "local.name.ends.with.grave-accent`@domain.com"                        =   0 =  OK 
     *   168 - assertIsTrue  "local.name.with.grave-accent.before`.point@domain.com"                =   0 =  OK 
     *   169 - assertIsTrue  "local.name.with.grave-accent.after.`point@domain.com"                 =   0 =  OK 
     *   170 - assertIsTrue  "local.name.with.double.grave-accent``test@domain.com"                 =   0 =  OK 
     *   171 - assertIsFalse "(comment `) local.name.with.comment.with.grave-accent@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   172 - assertIsTrue  "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com"        =   1 =  OK 
     *   173 - assertIsTrue  "`@grave-accent.domain.com"                                            =   0 =  OK 
     *   174 - assertIsTrue  "``````@grave-accent.domain.com"                                       =   0 =  OK 
     *   175 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                  =   0 =  OK 
     *   176 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   177 - assertIsFalse "<pointy.brackets2.with.grave-accent@domain.com> name `"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   178 - assertIsTrue  "#.local.name.starts.with.hash@domain.com"                             =   0 =  OK 
     *   179 - assertIsTrue  "local.name.ends.with.hash#@domain.com"                                =   0 =  OK 
     *   180 - assertIsTrue  "local.name.with.hash.before#.point@domain.com"                        =   0 =  OK 
     *   181 - assertIsTrue  "local.name.with.hash.after.#point@domain.com"                         =   0 =  OK 
     *   182 - assertIsTrue  "local.name.with.double.hash##test@domain.com"                         =   0 =  OK 
     *   183 - assertIsFalse "(comment #) local.name.with.comment.with.hash@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   184 - assertIsTrue  "\"quote#\".local.name.with.qoute.with.hash@domain.com"                =   1 =  OK 
     *   185 - assertIsTrue  "#@hash.domain.com"                                                    =   0 =  OK 
     *   186 - assertIsTrue  "######@hash.domain.com"                                               =   0 =  OK 
     *   187 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                          =   0 =  OK 
     *   188 - assertIsFalse "name # <pointy.brackets1.with.hash@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   189 - assertIsFalse "<pointy.brackets2.with.hash@domain.com> name #"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   190 - assertIsTrue  "-.local.name.starts.with.hypen@domain.com"                            =   0 =  OK 
     *   191 - assertIsTrue  "local.name.ends.with.hypen-@domain.com"                               =   0 =  OK 
     *   192 - assertIsTrue  "local.name.with.hypen.before-.point@domain.com"                       =   0 =  OK 
     *   193 - assertIsTrue  "local.name.with.hypen.after.-point@domain.com"                        =   0 =  OK 
     *   194 - assertIsTrue  "local.name.with.double.hypen--test@domain.com"                        =   0 =  OK 
     *   195 - assertIsFalse "(comment -) local.name.with.comment.with.hypen@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   196 - assertIsTrue  "\"quote-\".local.name.with.qoute.with.hypen@domain.com"               =   1 =  OK 
     *   197 - assertIsTrue  "-@hypen.domain.com"                                                   =   0 =  OK 
     *   198 - assertIsTrue  "------@hypen.domain.com"                                              =   0 =  OK 
     *   199 - assertIsTrue  "-.-.-.-.-.-@hypen.domain.com"                                         =   0 =  OK 
     *   200 - assertIsFalse "name - <pointy.brackets1.with.hypen@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   201 - assertIsFalse "<pointy.brackets2.with.hypen@domain.com> name -"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   202 - assertIsTrue  "{.local.name.starts.with.leftbracket@domain.com"                      =   0 =  OK 
     *   203 - assertIsTrue  "local.name.ends.with.leftbracket{@domain.com"                         =   0 =  OK 
     *   204 - assertIsTrue  "local.name.with.leftbracket.before{.point@domain.com"                 =   0 =  OK 
     *   205 - assertIsTrue  "local.name.with.leftbracket.after.{point@domain.com"                  =   0 =  OK 
     *   206 - assertIsTrue  "local.name.with.double.leftbracket{{test@domain.com"                  =   0 =  OK 
     *   207 - assertIsFalse "(comment {) local.name.with.comment.with.leftbracket@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   208 - assertIsTrue  "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   209 - assertIsTrue  "{@leftbracket.domain.com"                                             =   0 =  OK 
     *   210 - assertIsTrue  "{{{{{{@leftbracket.domain.com"                                        =   0 =  OK 
     *   211 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                   =   0 =  OK 
     *   212 - assertIsFalse "name { <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   213 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name {"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   214 - assertIsTrue  "%.local.name.starts.with.percentage@domain.com"                       =   0 =  OK 
     *   215 - assertIsTrue  "local.name.ends.with.percentage%@domain.com"                          =   0 =  OK 
     *   216 - assertIsTrue  "local.name.with.percentage.before%.point@domain.com"                  =   0 =  OK 
     *   217 - assertIsTrue  "local.name.with.percentage.after.%point@domain.com"                   =   0 =  OK 
     *   218 - assertIsTrue  "local.name.with.double.percentage%%test@domain.com"                   =   0 =  OK 
     *   219 - assertIsFalse "(comment %) local.name.with.comment.with.percentage@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   220 - assertIsTrue  "\"quote%\".local.name.with.qoute.with.percentage@domain.com"          =   1 =  OK 
     *   221 - assertIsTrue  "%@percentage.domain.com"                                              =   0 =  OK 
     *   222 - assertIsTrue  "%%%%%%@percentage.domain.com"                                         =   0 =  OK 
     *   223 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                    =   0 =  OK 
     *   224 - assertIsFalse "name % <pointy.brackets1.with.percentage@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   225 - assertIsFalse "<pointy.brackets2.with.percentage@domain.com> name %"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   226 - assertIsTrue  "|.local.name.starts.with.pipe@domain.com"                             =   0 =  OK 
     *   227 - assertIsTrue  "local.name.ends.with.pipe|@domain.com"                                =   0 =  OK 
     *   228 - assertIsTrue  "local.name.with.pipe.before|.point@domain.com"                        =   0 =  OK 
     *   229 - assertIsTrue  "local.name.with.pipe.after.|point@domain.com"                         =   0 =  OK 
     *   230 - assertIsTrue  "local.name.with.double.pipe||test@domain.com"                         =   0 =  OK 
     *   231 - assertIsFalse "(comment |) local.name.with.comment.with.pipe@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   232 - assertIsTrue  "\"quote|\".local.name.with.qoute.with.pipe@domain.com"                =   1 =  OK 
     *   233 - assertIsTrue  "|@pipe.domain.com"                                                    =   0 =  OK 
     *   234 - assertIsTrue  "||||||@pipe.domain.com"                                               =   0 =  OK 
     *   235 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                          =   0 =  OK 
     *   236 - assertIsFalse "name | <pointy.brackets1.with.pipe@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   237 - assertIsFalse "<pointy.brackets2.with.pipe@domain.com> name |"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   238 - assertIsTrue  "+.local.name.starts.with.plus@domain.com"                             =   0 =  OK 
     *   239 - assertIsTrue  "local.name.ends.with.plus+@domain.com"                                =   0 =  OK 
     *   240 - assertIsTrue  "local.name.with.plus.before+.point@domain.com"                        =   0 =  OK 
     *   241 - assertIsTrue  "local.name.with.plus.after.+point@domain.com"                         =   0 =  OK 
     *   242 - assertIsTrue  "local.name.with.double.plus++test@domain.com"                         =   0 =  OK 
     *   243 - assertIsFalse "(comment +) local.name.with.comment.with.plus@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   244 - assertIsTrue  "\"quote+\".local.name.with.qoute.with.plus@domain.com"                =   1 =  OK 
     *   245 - assertIsTrue  "+@plus.domain.com"                                                    =   0 =  OK 
     *   246 - assertIsTrue  "++++++@plus.domain.com"                                               =   0 =  OK 
     *   247 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                          =   0 =  OK 
     *   248 - assertIsFalse "name + <pointy.brackets1.with.plus@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   249 - assertIsFalse "<pointy.brackets2.with.plus@domain.com> name +"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   250 - assertIsTrue  "?.local.name.starts.with.question@domain.com"                         =   0 =  OK 
     *   251 - assertIsTrue  "local.name.ends.with.question?@domain.com"                            =   0 =  OK 
     *   252 - assertIsTrue  "local.name.with.question.before?.point@domain.com"                    =   0 =  OK 
     *   253 - assertIsTrue  "local.name.with.question.after.?point@domain.com"                     =   0 =  OK 
     *   254 - assertIsTrue  "local.name.with.double.question??test@domain.com"                     =   0 =  OK 
     *   255 - assertIsFalse "(comment ?) local.name.with.comment.with.question@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   256 - assertIsTrue  "\"quote?\".local.name.with.qoute.with.question@domain.com"            =   1 =  OK 
     *   257 - assertIsTrue  "?@question.domain.com"                                                =   0 =  OK 
     *   258 - assertIsTrue  "??????@question.domain.com"                                           =   0 =  OK 
     *   259 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                      =   0 =  OK 
     *   260 - assertIsFalse "name ? <pointy.brackets1.with.question@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   261 - assertIsFalse "<pointy.brackets2.with.question@domain.com> name ?"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   262 - assertIsTrue  "}.local.name.starts.with.rightbracket@domain.com"                     =   0 =  OK 
     *   263 - assertIsTrue  "local.name.ends.with.rightbracket}@domain.com"                        =   0 =  OK 
     *   264 - assertIsTrue  "local.name.with.rightbracket.before}.point@domain.com"                =   0 =  OK 
     *   265 - assertIsTrue  "local.name.with.rightbracket.after.}point@domain.com"                 =   0 =  OK 
     *   266 - assertIsTrue  "local.name.with.double.rightbracket}}test@domain.com"                 =   0 =  OK 
     *   267 - assertIsFalse "(comment }) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   268 - assertIsTrue  "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   269 - assertIsTrue  "}@rightbracket.domain.com"                                            =   0 =  OK 
     *   270 - assertIsTrue  "}}}}}}@rightbracket.domain.com"                                       =   0 =  OK 
     *   271 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                  =   0 =  OK 
     *   272 - assertIsFalse "name } <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   273 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name }"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   274 - assertIsTrue  "~.local.name.starts.with.tilde@domain.com"                            =   0 =  OK 
     *   275 - assertIsTrue  "local.name.ends.with.tilde~@domain.com"                               =   0 =  OK 
     *   276 - assertIsTrue  "local.name.with.tilde.before~.point@domain.com"                       =   0 =  OK 
     *   277 - assertIsTrue  "local.name.with.tilde.after.~point@domain.com"                        =   0 =  OK 
     *   278 - assertIsTrue  "local.name.with.double.tilde~~test@domain.com"                        =   0 =  OK 
     *   279 - assertIsFalse "(comment ~) local.name.with.comment.with.tilde@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   280 - assertIsTrue  "\"quote~\".local.name.with.qoute.with.tilde@domain.com"               =   1 =  OK 
     *   281 - assertIsTrue  "~@tilde.domain.com"                                                   =   0 =  OK 
     *   282 - assertIsTrue  "~~~~~~@tilde.domain.com"                                              =   0 =  OK 
     *   283 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                         =   0 =  OK 
     *   284 - assertIsFalse "name ~ <pointy.brackets1.with.tilde@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   285 - assertIsFalse "<pointy.brackets2.with.tilde@domain.com> name ~"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   286 - assertIsTrue  "^.local.name.starts.with.xor@domain.com"                              =   0 =  OK 
     *   287 - assertIsTrue  "local.name.ends.with.xor^@domain.com"                                 =   0 =  OK 
     *   288 - assertIsTrue  "local.name.with.xor.before^.point@domain.com"                         =   0 =  OK 
     *   289 - assertIsTrue  "local.name.with.xor.after.^point@domain.com"                          =   0 =  OK 
     *   290 - assertIsTrue  "local.name.with.double.xor^^test@domain.com"                          =   0 =  OK 
     *   291 - assertIsFalse "(comment ^) local.name.with.comment.with.xor@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   292 - assertIsTrue  "\"quote^\".local.name.with.qoute.with.xor@domain.com"                 =   1 =  OK 
     *   293 - assertIsTrue  "^@xor.domain.com"                                                     =   0 =  OK 
     *   294 - assertIsTrue  "^^^^^^@xor.domain.com"                                                =   0 =  OK 
     *   295 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                           =   0 =  OK 
     *   296 - assertIsFalse "name ^ <pointy.brackets1.with.xor@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   297 - assertIsFalse "<pointy.brackets2.with.xor@domain.com> name ^"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   298 - assertIsTrue  "_.local.name.starts.with.underscore@domain.com"                       =   0 =  OK 
     *   299 - assertIsTrue  "local.name.ends.with.underscore_@domain.com"                          =   0 =  OK 
     *   300 - assertIsTrue  "local.name.with.underscore.before_.point@domain.com"                  =   0 =  OK 
     *   301 - assertIsTrue  "local.name.with.underscore.after._point@domain.com"                   =   0 =  OK 
     *   302 - assertIsTrue  "local.name.with.double.underscore__test@domain.com"                   =   0 =  OK 
     *   303 - assertIsFalse "(comment _) local.name.with.comment.with.underscore@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   304 - assertIsTrue  "\"quote_\".local.name.with.qoute.with.underscore@domain.com"          =   1 =  OK 
     *   305 - assertIsTrue  "_@underscore.domain.com"                                              =   0 =  OK 
     *   306 - assertIsTrue  "______@underscore.domain.com"                                         =   0 =  OK 
     *   307 - assertIsTrue  "_._._._._._@underscore.domain.com"                                    =   0 =  OK 
     *   308 - assertIsFalse "name _ <pointy.brackets1.with.underscore@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   309 - assertIsFalse "<pointy.brackets2.with.underscore@domain.com> name _"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   310 - assertIsFalse ":.local.name.starts.with.colon@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   311 - assertIsFalse "local.name.ends.with.colon:@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   312 - assertIsFalse "local.name.with.colon.before:.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   313 - assertIsFalse "local.name.with.colon.after.:point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   314 - assertIsFalse "local.name.with.double.colon::test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   315 - assertIsFalse "(comment :) local.name.with.comment.with.colon@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   316 - assertIsTrue  "\"quote:\".local.name.with.qoute.with.colon@domain.com"               =   1 =  OK 
     *   317 - assertIsFalse ":@colon.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   318 - assertIsFalse "::::::@colon.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   319 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   320 - assertIsFalse "name : <pointy.brackets1.with.colon@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   321 - assertIsFalse "<pointy.brackets2.with.colon@domain.com> name :"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   322 - assertIsFalse "(.local.name.starts.with.leftbracket@domain.com"                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   323 - assertIsFalse "local.name.ends.with.leftbracket(@domain.com"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   324 - assertIsFalse "local.name.with.leftbracket.before(.point@domain.com"                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   325 - assertIsFalse "local.name.with.leftbracket.after.(point@domain.com"                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   326 - assertIsFalse "local.name.with.double.leftbracket((test@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   327 - assertIsFalse "(comment () local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   328 - assertIsTrue  "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   329 - assertIsFalse "(@leftbracket.domain.com"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   330 - assertIsFalse "((((((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   331 - assertIsFalse "(()(((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   332 - assertIsFalse "((<)>(((@leftbracket.domain.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   333 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   334 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket@domain.com>"                =   0 =  OK 
     *   335 - assertIsTrue  "<pointy.brackets2.with.leftbracket@domain.com> name ("                =   0 =  OK 
     *   336 - assertIsFalse "\.local.name.starts.with.slash@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   337 - assertIsFalse "local.name.ends.with.slash\@domain.com"                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   338 - assertIsFalse "local.name.with.slash.before\.point@domain.com"                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   339 - assertIsFalse "local.name.with.slash.after.\point@domain.com"                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   340 - assertIsTrue  "local.name.with.double.slash\\test@domain.com"                        =   0 =  OK 
     *   341 - assertIsFalse "(comment \) local.name.with.comment.with.slash@domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   342 - assertIsFalse "\"quote\\".local.name.with.qoute.with.slash@domain.com"               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   343 - assertIsFalse "\@slash.domain.com"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   344 - assertIsTrue  "\\\\\\@slash.domain.com"                                              =   0 =  OK 
     *   345 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   346 - assertIsFalse "name \ <pointy.brackets1.with.slash@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   347 - assertIsFalse "<pointy.brackets2.with.slash@domain.com> name \"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   348 - assertIsFalse ").local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   349 - assertIsFalse "local.name.ends.with.rightbracket)@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   350 - assertIsFalse "local.name.with.rightbracket.before).point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   351 - assertIsFalse "local.name.with.rightbracket.after.)point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   352 - assertIsFalse "local.name.with.double.rightbracket))test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   353 - assertIsFalse "(comment )) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   354 - assertIsTrue  "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   355 - assertIsFalse ")@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   356 - assertIsFalse "))))))@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   357 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   358 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket@domain.com>"               =   0 =  OK 
     *   359 - assertIsTrue  "<pointy.brackets2.with.rightbracket@domain.com> name )"               =   0 =  OK 
     *   360 - assertIsFalse "[.local.name.starts.with.leftbracket@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   361 - assertIsFalse "local.name.ends.with.leftbracket[@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   362 - assertIsFalse "local.name.with.leftbracket.before[.point@domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   363 - assertIsFalse "local.name.with.leftbracket.after.[point@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   364 - assertIsFalse "local.name.with.double.leftbracket[[test@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   365 - assertIsFalse "(comment [) local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   366 - assertIsTrue  "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   367 - assertIsFalse "[@leftbracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   368 - assertIsFalse "[[[[[[@leftbracket.domain.com"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   369 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   370 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   371 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name ["                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   372 - assertIsFalse "].local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   373 - assertIsFalse "local.name.ends.with.rightbracket]@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   374 - assertIsFalse "local.name.with.rightbracket.before].point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   375 - assertIsFalse "local.name.with.rightbracket.after.]point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   376 - assertIsFalse "local.name.with.double.rightbracket]]test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   377 - assertIsFalse "(comment ]) local.name.with.comment.with.rightbracket@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   378 - assertIsTrue  "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   379 - assertIsFalse "]@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   380 - assertIsFalse "]]]]]]@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   381 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   383 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name ]"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   384 - assertIsFalse " .local.name.starts.with.space@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   385 - assertIsFalse "local.name.ends.with.space @domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   386 - assertIsFalse "local.name.with.space.before .point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   387 - assertIsFalse "local.name.with.space.after. point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   388 - assertIsFalse "local.name.with.double.space  test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   389 - assertIsFalse "(comment  ) local.name.with.comment.with.space@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   390 - assertIsTrue  "\"quote \".local.name.with.qoute.with.space@domain.com"               =   1 =  OK 
     *   391 - assertIsFalse " @space.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   392 - assertIsFalse "      @space.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   393 - assertIsFalse " . . . . . @space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   394 - assertIsTrue  "name   <pointy.brackets1.with.space@domain.com>"                      =   0 =  OK 
     *   395 - assertIsTrue  "<pointy.brackets2.with.space@domain.com> name  "                      =   0 =  OK 
     *   396 - assertIsFalse "().local.name.starts.with.empty.bracket@domain.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   397 - assertIsTrue  "local.name.ends.with.empty.bracket()@domain.com"                      =   6 =  OK 
     *   398 - assertIsFalse "local.name.with.empty.bracket.before().point@domain.com"              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   399 - assertIsFalse "local.name.with.empty.bracket.after.()point@domain.com"               = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   400 - assertIsFalse "local.name.with.double.empty.bracket()()test@domain.com"              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   401 - assertIsFalse "(comment ()) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   402 - assertIsTrue  "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   403 - assertIsFalse "()@empty.bracket.domain.com"                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   404 - assertIsFalse "()()()()()()@empty.bracket.domain.com"                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   405 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   406 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket@domain.com>"             =   0 =  OK 
     *   407 - assertIsTrue  "<pointy.brackets2.with.empty.bracket@domain.com> name ()"             =   0 =  OK 
     *   408 - assertIsTrue  "{}.local.name.starts.with.empty.bracket@domain.com"                   =   0 =  OK 
     *   409 - assertIsTrue  "local.name.ends.with.empty.bracket{}@domain.com"                      =   0 =  OK 
     *   410 - assertIsTrue  "local.name.with.empty.bracket.before{}.point@domain.com"              =   0 =  OK 
     *   411 - assertIsTrue  "local.name.with.empty.bracket.after.{}point@domain.com"               =   0 =  OK 
     *   412 - assertIsTrue  "local.name.with.double.empty.bracket{}{}test@domain.com"              =   0 =  OK 
     *   413 - assertIsFalse "(comment {}) local.name.with.comment.with.empty.bracket@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   414 - assertIsTrue  "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   415 - assertIsTrue  "{}@empty.bracket.domain.com"                                          =   0 =  OK 
     *   416 - assertIsTrue  "{}{}{}{}{}{}@empty.bracket.domain.com"                                =   0 =  OK 
     *   417 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                           =   0 =  OK 
     *   418 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   419 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name {}"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   420 - assertIsFalse "[].local.name.starts.with.empty.bracket@domain.com"                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   421 - assertIsFalse "local.name.ends.with.empty.bracket[]@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   422 - assertIsFalse "local.name.with.empty.bracket.before[].point@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   423 - assertIsFalse "local.name.with.empty.bracket.after.[]point@domain.com"               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   424 - assertIsFalse "local.name.with.double.empty.bracket[][]test@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   425 - assertIsFalse "(comment []) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   426 - assertIsTrue  "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   427 - assertIsFalse "[]@empty.bracket.domain.com"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   428 - assertIsFalse "[][][][][][]@empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   429 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   430 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   431 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name []"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   432 - assertIsTrue  "999.local.name.starts.with.byte.overflow@domain.com"                  =   0 =  OK 
     *   433 - assertIsTrue  "local.name.ends.with.byte.overflow999@domain.com"                     =   0 =  OK 
     *   434 - assertIsTrue  "local.name.with.byte.overflow.before999.point@domain.com"             =   0 =  OK 
     *   435 - assertIsTrue  "local.name.with.byte.overflow.after.999point@domain.com"              =   0 =  OK 
     *   436 - assertIsTrue  "local.name.with.double.byte.overflow999999test@domain.com"            =   0 =  OK 
     *   437 - assertIsTrue  "(comment 999) local.name.with.comment.with.byte.overflow@domain.com"  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   438 - assertIsTrue  "\"quote999\".local.name.with.qoute.with.byte.overflow@domain.com"     =   1 =  OK 
     *   439 - assertIsTrue  "999@byte.overflow.domain.com"                                         =   0 =  OK 
     *   440 - assertIsTrue  "999999999999999999@byte.overflow.domain.com"                          =   0 =  OK 
     *   441 - assertIsTrue  "999.999.999.999.999.999@byte.overflow.domain.com"                     =   0 =  OK 
     *   442 - assertIsTrue  "name 999 <pointy.brackets1.with.byte.overflow@domain.com>"            =   0 =  OK 
     *   443 - assertIsTrue  "<pointy.brackets2.with.byte.overflow@domain.com> name 999"            =   0 =  OK 
     *   444 - assertIsTrue  "\"str\".local.name.starts.with.string@domain.com"                     =   1 =  OK 
     *   445 - assertIsFalse "local.name.ends.with.string\"str\"@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   446 - assertIsFalse "local.name.with.string.before\"str\".point@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   447 - assertIsFalse "local.name.with.string.after.\"str\"point@domain.com"                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   448 - assertIsFalse "local.name.with.double.string\"str\"\"str\"test@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   449 - assertIsFalse "(comment \"str\") local.name.with.comment.with.string@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   450 - assertIsFalse "\"quote\"str\"\".local.name.with.qoute.with.string@domain.com"        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   451 - assertIsTrue  "\"str\"@string.domain.com"                                            =   1 =  OK 
     *   452 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com"         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   453 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"    =   1 =  OK 
     *   454 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string@domain.com>"               =   0 =  OK 
     *   455 - assertIsTrue  "<pointy.brackets2.with.string@domain.com> name \"str\""               =   0 =  OK 
     *   456 - assertIsFalse "(comment).local.name.starts.with.comment@domain.com"                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   457 - assertIsTrue  "local.name.ends.with.comment(comment)@domain.com"                     =   6 =  OK 
     *   458 - assertIsFalse "local.name.with.comment.before(comment).point@domain.com"             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   459 - assertIsFalse "local.name.with.comment.after.(comment)point@domain.com"              = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   460 - assertIsFalse "local.name.with.double.comment(comment)(comment)test@domain.com"      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   461 - assertIsFalse "(comment (comment)) local.name.with.comment.with.comment@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   462 - assertIsTrue  "\"quote(comment)\".local.name.with.qoute.with.comment@domain.com"     =   1 =  OK 
     *   463 - assertIsFalse "(comment)@comment.domain.com"                                         =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   464 - assertIsFalse "(comment)(comment)(comment)(comment)@comment.domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   465 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   466 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment@domain.com>"            =   0 =  OK 
     *   467 - assertIsTrue  "<pointy.brackets2.with.comment@domain.com> name (comment)"            =   0 =  OK 
     *   468 - assertIsTrue  "domain.part@with0number0.com"                                         =   0 =  OK 
     *   469 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                        =   0 =  OK 
     *   470 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                         =   0 =  OK 
     *   471 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                         =   0 =  OK 
     *   472 - assertIsTrue  "domain.part@with.number0.before0.point.com"                           =   0 =  OK 
     *   473 - assertIsTrue  "domain.part@with.number0.after.0point.com"                            =   0 =  OK 
     *   474 - assertIsTrue  "domain.part@with9number9.com"                                         =   0 =  OK 
     *   475 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                        =   0 =  OK 
     *   476 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                         =   0 =  OK 
     *   477 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                         =   0 =  OK 
     *   478 - assertIsTrue  "domain.part@with.number9.before9.point.com"                           =   0 =  OK 
     *   479 - assertIsTrue  "domain.part@with.number9.after.9point.com"                            =   0 =  OK 
     *   480 - assertIsTrue  "domain.part.only.numbers@1234567890.com"                              =   0 =  OK 
     *   481 - assertIsTrue  "domain.part@with0123456789numbers.com"                                =   0 =  OK 
     *   482 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"               =   0 =  OK 
     *   483 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                =   0 =  OK 
     *   484 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                =   0 =  OK 
     *   485 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                  =   0 =  OK 
     *   486 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                   =   0 =  OK 
     *   487 - assertIsTrue  "domain.part@with-hyphen.com"                                          =   0 =  OK 
     *   488 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   489 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   490 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   491 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   492 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   493 - assertIsTrue  "domain.part@with_underscore.com"                                      =   0 =  OK 
     *   494 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   495 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   496 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   497 - assertIsFalse "domain.part@with.underscore.before_.point.com"                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   498 - assertIsFalse "domain.part@with.underscore.after._point.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   499 - assertIsFalse "domain.part@with&amp.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   500 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   501 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   502 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   503 - assertIsFalse "domain.part@with.amp.before&.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   504 - assertIsFalse "domain.part@with.amp.after.&point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   505 - assertIsFalse "domain.part@with*asterisk.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   506 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   507 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   508 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   509 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   510 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   511 - assertIsFalse "domain.part@with$dollar.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   512 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   513 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   514 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   515 - assertIsFalse "domain.part@with.dollar.before$.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   516 - assertIsFalse "domain.part@with.dollar.after.$point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   517 - assertIsFalse "domain.part@with=equality.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   518 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   519 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   520 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   521 - assertIsFalse "domain.part@with.equality.before=.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   522 - assertIsFalse "domain.part@with.equality.after.=point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   523 - assertIsFalse "domain.part@with!exclamation.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   524 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   525 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   526 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   527 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   528 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   529 - assertIsFalse "domain.part@with?question.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   530 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   531 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   532 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   533 - assertIsFalse "domain.part@with.question.before?.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   534 - assertIsFalse "domain.part@with.question.after.?point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   535 - assertIsFalse "domain.part@with`grave-accent.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   536 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   537 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   538 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   539 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   540 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   541 - assertIsFalse "domain.part@with#hash.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   542 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   543 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   544 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   545 - assertIsFalse "domain.part@with.hash.before#.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   546 - assertIsFalse "domain.part@with.hash.after.#point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   547 - assertIsFalse "domain.part@with%percentage.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   548 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   549 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   550 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   551 - assertIsFalse "domain.part@with.percentage.before%.point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   552 - assertIsFalse "domain.part@with.percentage.after.%point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   553 - assertIsFalse "domain.part@with|pipe.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   554 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   555 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   556 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   557 - assertIsFalse "domain.part@with.pipe.before|.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   558 - assertIsFalse "domain.part@with.pipe.after.|point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   559 - assertIsFalse "domain.part@with+plus.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   560 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   561 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   562 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   563 - assertIsFalse "domain.part@with.plus.before+.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   564 - assertIsFalse "domain.part@with.plus.after.+point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   565 - assertIsFalse "domain.part@with{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   566 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   567 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   568 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   569 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   570 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   571 - assertIsFalse "domain.part@with}rightbracket.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   572 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   573 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   574 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   575 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsFalse "domain.part@with(leftbracket.com"                                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   578 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   579 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   580 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   581 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   582 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   583 - assertIsFalse "domain.part@with)rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   584 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   585 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   586 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   587 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   588 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   589 - assertIsFalse "domain.part@with[leftbracket.com"                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   590 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   591 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   592 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   593 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                       =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   594 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   595 - assertIsFalse "domain.part@with]rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   596 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   597 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   598 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   599 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   600 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   601 - assertIsFalse "domain.part@with~tilde.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   602 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   603 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   604 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   605 - assertIsFalse "domain.part@with.tilde.before~.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   606 - assertIsFalse "domain.part@with.tilde.after.~point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   607 - assertIsFalse "domain.part@with^xor.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   608 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   609 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   610 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   611 - assertIsFalse "domain.part@with.xor.before^.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   612 - assertIsFalse "domain.part@with.xor.after.^point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   613 - assertIsFalse "domain.part@with:colon.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   614 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   615 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   616 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   617 - assertIsFalse "domain.part@with.colon.before:.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   618 - assertIsFalse "domain.part@with.colon.after.:point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   619 - assertIsFalse "domain.part@with space.com"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   620 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   621 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   622 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   623 - assertIsFalse "domain.part@with.space.before .point.com"                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   624 - assertIsFalse "domain.part@with.space.after. point.com"                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   625 - assertIsTrue  "domain.part@with999byte.overflow.com"                                 =   0 =  OK 
     *   626 - assertIsTrue  "domain.part@999with.byte.overflow.at.domain.start.com"                =   0 =  OK 
     *   627 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end1999.com"                 =   0 =  OK 
     *   628 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end2.com999"                 =   0 =  OK 
     *   629 - assertIsTrue  "domain.part@with.byte.overflow.before999.point.com"                   =   0 =  OK 
     *   630 - assertIsTrue  "domain.part@with.byte.overflow.after.999point.com"                    =   0 =  OK 
     *   631 - assertIsTrue  "domain.part@withxyzno.hex.number.com"                                 =   0 =  OK 
     *   632 - assertIsTrue  "domain.part@xyzwith.no.hex.number.at.domain.start.com"                =   0 =  OK 
     *   633 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end1xyz.com"                 =   0 =  OK 
     *   634 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end2.comxyz"                 =   0 =  OK 
     *   635 - assertIsTrue  "domain.part@with.no.hex.number.beforexyz.point.com"                   =   0 =  OK 
     *   636 - assertIsTrue  "domain.part@with.no.hex.number.after.xyzpoint.com"                    =   0 =  OK 
     *   637 - assertIsFalse "domain.part@with\"str\"string.com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   638 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   639 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   640 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   641 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   642 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   643 - assertIsFalse "domain.part@with(comment)comment.com"                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   644 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                =   6 =  OK 
     *   645 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   646 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                 =   6 =  OK 
     *   647 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   648 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                    = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   649 - assertIsFalse ",.local.name.starts.with.comma@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   650 - assertIsFalse "local.name.ends.with.comma,@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   651 - assertIsFalse "local.name.with.comma.before,.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   652 - assertIsFalse "local.name.with.comma.after.,point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   653 - assertIsFalse "local.name.with.double.comma,,test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   654 - assertIsFalse "(comment ,) local.name.with.comment.with.comma@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   655 - assertIsTrue  "\"quote,\".local.name.with.qoute.with.comma@domain.com"               =   1 =  OK 
     *   656 - assertIsFalse ",@comma.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   657 - assertIsFalse ",,,,,,@comma.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   658 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   659 - assertIsFalse "name , <pointy.brackets1.with.comma@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   660 - assertIsFalse "<pointy.brackets2.with.comma@domain.com> name ,"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   661 - assertIsFalse "domain.part@with,comma.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   662 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   663 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   664 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   665 - assertIsFalse "domain.part@with.comma.before,.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   666 - assertIsFalse "domain.part@with.comma.after.,point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   667 - assertIsFalse "§.local.name.starts.with.paragraph@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   668 - assertIsFalse "local.name.ends.with.paragraph§@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   669 - assertIsFalse "local.name.with.paragraph.before§.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   670 - assertIsFalse "local.name.with.paragraph.after.§point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   671 - assertIsFalse "local.name.with.double.paragraph§§test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   672 - assertIsFalse "(comment §) local.name.with.comment.with.paragraph@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   673 - assertIsFalse "\"quote§\".local.name.with.qoute.with.paragraph@domain.com"           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   674 - assertIsFalse "§@paragraph.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsFalse "§§§§§§@paragraph.domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   676 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   677 - assertIsFalse "name § <pointy.brackets1.with.paragraph@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   678 - assertIsFalse "<pointy.brackets2.with.paragraph@domain.com> name §"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   679 - assertIsFalse "domain.part@with§paragraph.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   680 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   681 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   682 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   683 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   684 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   685 - assertIsTrue  "'.local.name.starts.with.quote@domain.com"                            =   0 =  OK 
     *   686 - assertIsTrue  "local.name.ends.with.quote'@domain.com"                               =   0 =  OK 
     *   687 - assertIsTrue  "local.name.with.quote.before'.point@domain.com"                       =   0 =  OK 
     *   688 - assertIsTrue  "local.name.with.quote.after.'point@domain.com"                        =   0 =  OK 
     *   689 - assertIsTrue  "local.name.with.double.quote''test@domain.com"                        =   0 =  OK 
     *   690 - assertIsFalse "(comment ') local.name.with.comment.with.quote@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   691 - assertIsTrue  "\"quote'\".local.name.with.qoute.with.quote@domain.com"               =   1 =  OK 
     *   692 - assertIsTrue  "'@quote.domain.com"                                                   =   0 =  OK 
     *   693 - assertIsTrue  "''''''@quote.domain.com"                                              =   0 =  OK 
     *   694 - assertIsTrue  "'.'.'.'.'.'@quote.domain.com"                                         =   0 =  OK 
     *   695 - assertIsFalse "name ' <pointy.brackets1.with.quote@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   696 - assertIsFalse "<pointy.brackets2.with.quote@domain.com> name '"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   697 - assertIsFalse "domain.part@with'quote.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   698 - assertIsFalse "domain.part@'with.quote.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   699 - assertIsFalse "domain.part@with.quote.at.domain.end1'.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   700 - assertIsFalse "domain.part@with.quote.at.domain.end2.com'"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   701 - assertIsFalse "domain.part@with.quote.before'.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   702 - assertIsFalse "domain.part@with.quote.after.'point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   703 - assertIsFalse "\".local.name.starts.with.double.quote@domain.com"                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   704 - assertIsFalse "local.name.ends.with.double.quote\"@domain.com"                       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   705 - assertIsFalse "local.name.with.double.quote.before\".point@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   706 - assertIsFalse "local.name.with.double.quote.after.\"point@domain.com"                =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   707 - assertIsFalse "local.name.with.double.double.quote\"\"test@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   708 - assertIsFalse "(comment \") local.name.with.comment.with.double.quote@domain.com"    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   709 - assertIsFalse "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com"       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   710 - assertIsFalse "\"@double.quote.domain.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   711 - assertIsTrue  "\".\".\".\".\".\"@double.quote.domain.com"                            =   1 =  OK 
     *   712 - assertIsTrue  "name \" <pointy.brackets1.with.double.quote@domain.com>"              =   0 =  OK 
     *   713 - assertIsTrue  "<pointy.brackets2.with.double.quote@domain.com> name \""              =   0 =  OK 
     *   714 - assertIsFalse "domain.part@with\"double.quote.com"                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   715 - assertIsFalse "domain.part@\"with.double.quote.at.domain.start.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   716 - assertIsFalse "domain.part@with.double.quote.at.domain.end1\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   717 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com\""                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   718 - assertIsFalse "domain.part@with.double.quote.before\".point.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   719 - assertIsFalse "domain.part@with.double.quote.after.\"point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   720 - assertIsFalse ")(.local.name.starts.with.false.bracket1@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   721 - assertIsFalse "local.name.ends.with.false.bracket1)(@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   722 - assertIsFalse "local.name.with.false.bracket1.before)(.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   723 - assertIsFalse "local.name.with.false.bracket1.after.)(point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   724 - assertIsFalse "local.name.with.double.false.bracket1)()(test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   725 - assertIsFalse "(comment )() local.name.with.comment.with.false.bracket1@domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   726 - assertIsTrue  "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com"     =   1 =  OK 
     *   727 - assertIsFalse ")(@false.bracket1.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   728 - assertIsFalse ")()()()()()(@false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   729 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   730 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1@domain.com>"            =   0 =  OK 
     *   731 - assertIsTrue  "<pointy.brackets2.with.false.bracket1@domain.com> name )("            =   0 =  OK 
     *   732 - assertIsFalse "domain.part@with)(false.bracket1.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   733 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   734 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   735 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   736 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   737 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   738 - assertIsTrue  "}{.local.name.starts.with.false.bracket2@domain.com"                  =   0 =  OK 
     *   739 - assertIsTrue  "local.name.ends.with.false.bracket2}{@domain.com"                     =   0 =  OK 
     *   740 - assertIsTrue  "local.name.with.false.bracket2.before}{.point@domain.com"             =   0 =  OK 
     *   741 - assertIsTrue  "local.name.with.false.bracket2.after.}{point@domain.com"              =   0 =  OK 
     *   742 - assertIsTrue  "local.name.with.double.false.bracket2}{}{test@domain.com"             =   0 =  OK 
     *   743 - assertIsFalse "(comment }{) local.name.with.comment.with.false.bracket2@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   744 - assertIsTrue  "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com"     =   1 =  OK 
     *   745 - assertIsTrue  "}{@false.bracket2.domain.com"                                         =   0 =  OK 
     *   746 - assertIsTrue  "}{}{}{}{}{}{@false.bracket2.domain.com"                               =   0 =  OK 
     *   747 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                          =   0 =  OK 
     *   748 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   749 - assertIsFalse "<pointy.brackets2.with.false.bracket2@domain.com> name }{"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   750 - assertIsFalse "domain.part@with}{false.bracket2.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   751 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   752 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   753 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   754 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   755 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   756 - assertIsFalse "][.local.name.starts.with.false.bracket3@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   757 - assertIsFalse "local.name.ends.with.false.bracket3][@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   758 - assertIsFalse "local.name.with.false.bracket3.before][.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   759 - assertIsFalse "local.name.with.false.bracket3.after.][point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   760 - assertIsFalse "local.name.with.double.false.bracket3][][test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   761 - assertIsFalse "(comment ][) local.name.with.comment.with.false.bracket3@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   762 - assertIsTrue  "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com"     =   1 =  OK 
     *   763 - assertIsFalse "][@false.bracket3.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   764 - assertIsFalse "][][][][][][@false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   765 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   766 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   767 - assertIsFalse "<pointy.brackets2.with.false.bracket3@domain.com> name ]["            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   768 - assertIsFalse "domain.part@with][false.bracket3.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   769 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   770 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   771 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   773 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   774 - assertIsFalse "><.local.name.starts.with.false.bracket4@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   775 - assertIsFalse "local.name.ends.with.false.bracket4><@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   776 - assertIsFalse "local.name.with.false.bracket4.before><.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   777 - assertIsFalse "local.name.with.false.bracket4.after.><point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   778 - assertIsFalse "local.name.with.double.false.bracket4><><test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   779 - assertIsFalse "(comment ><) local.name.with.comment.with.false.bracket4@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   780 - assertIsTrue  "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com"     =   1 =  OK 
     *   781 - assertIsFalse "><@false.bracket4.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   782 - assertIsFalse "><><><><><><@false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   783 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   784 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   785 - assertIsFalse "<pointy.brackets2.with.false.bracket4@domain.com> name ><"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   786 - assertIsFalse "domain.part@with\slash.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   787 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   788 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   789 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   790 - assertIsFalse "domain.part@with.slash.before\.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   791 - assertIsFalse "domain.part@with.slash.after.\point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   792 - assertIsFalse "domain.part@with><false.bracket4.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   793 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   794 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   795 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   796 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   797 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   798 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                    =   0 =  OK 
     *   799 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   800 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   801 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   802 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   803 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   804 - assertIsFalse "domain.part@with.consecutive.question??test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   805 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   806 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   807 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   808 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   809 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   810 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   811 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   812 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   813 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   814 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   815 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   816 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   817 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   818 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   819 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   820 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   821 - assertIsFalse "domain.part@with.consecutive.space  test.com"                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   822 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   823 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   824 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   825 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   826 - assertIsFalse "domain.part@with.consecutive.double.quote\"\"test.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   827 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"               = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   828 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   829 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   830 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   831 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   832 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   833 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   834 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   835 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   836 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   837 - assertIsTrue  "domain.part.with.comment.with.underscore@(comment _)domain.com"       =   6 =  OK 
     *   838 - assertIsTrue  "domain.part.with.comment.with.amp@(comment &)domain.com"              =   6 =  OK 
     *   839 - assertIsTrue  "domain.part.with.comment.with.asterisk@(comment *)domain.com"         =   6 =  OK 
     *   840 - assertIsTrue  "domain.part.with.comment.with.dollar@(comment $)domain.com"           =   6 =  OK 
     *   841 - assertIsTrue  "domain.part.with.comment.with.equality@(comment =)domain.com"         =   6 =  OK 
     *   842 - assertIsTrue  "domain.part.with.comment.with.exclamation@(comment !)domain.com"      =   6 =  OK 
     *   843 - assertIsTrue  "domain.part.with.comment.with.question@(comment ?)domain.com"         =   6 =  OK 
     *   844 - assertIsTrue  "domain.part.with.comment.with.grave-accent@(comment `)domain.com"     =   6 =  OK 
     *   845 - assertIsTrue  "domain.part.with.comment.with.hash@(comment #)domain.com"             =   6 =  OK 
     *   846 - assertIsTrue  "domain.part.with.comment.with.percentage@(comment %)domain.com"       =   6 =  OK 
     *   847 - assertIsTrue  "domain.part.with.comment.with.pipe@(comment |)domain.com"             =   6 =  OK 
     *   848 - assertIsTrue  "domain.part.with.comment.with.plus@(comment +)domain.com"             =   6 =  OK 
     *   849 - assertIsTrue  "domain.part.with.comment.with.leftbracket@(comment {)domain.com"      =   6 =  OK 
     *   850 - assertIsTrue  "domain.part.with.comment.with.rightbracket@(comment })domain.com"     =   6 =  OK 
     *   851 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment ()domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   852 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ))domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   853 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment [)domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   854 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ])domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   855 - assertIsFalse "domain.part.with.comment.with.lower.than@(comment <)domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   856 - assertIsFalse "domain.part.with.comment.with.greater.than@(comment >)domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   857 - assertIsTrue  "domain.part.with.comment.with.tilde@(comment ~)domain.com"            =   6 =  OK 
     *   858 - assertIsTrue  "domain.part.with.comment.with.xor@(comment ^)domain.com"              =   6 =  OK 
     *   859 - assertIsFalse "domain.part.with.comment.with.colon@(comment :)domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   860 - assertIsTrue  "domain.part.with.comment.with.space@(comment  )domain.com"            =   6 =  OK 
     *   861 - assertIsFalse "domain.part.with.comment.with.comma@(comment ,)domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   862 - assertIsFalse "domain.part.with.comment.with.paragraph@(comment §)domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   863 - assertIsTrue  "domain.part.with.comment.with.double.quote@(comment ')domain.com"     =   6 =  OK 
     *   864 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment ())domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   865 - assertIsTrue  "domain.part.with.comment.with.empty.bracket@(comment {})domain.com"   =   6 =  OK 
     *   866 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment [])domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   867 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment <>)domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   868 - assertIsFalse "domain.part.with.comment.with.false.bracket1@(comment )()domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   869 - assertIsTrue  "domain.part.with.comment.with.false.bracket2@(comment }{)domain.com"  =   6 =  OK 
     *   870 - assertIsFalse "domain.part.with.comment.with.false.bracket3@(comment ][)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   871 - assertIsFalse "domain.part.with.comment.with.false.bracket4@(comment ><)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   872 - assertIsFalse "domain.part.with.comment.with.slash@(comment \)domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   873 - assertIsFalse "domain.part.with.comment.with.string@(comment \"str\")domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   874 - assertIsFalse "domain.part.only.underscore@_.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   875 - assertIsFalse "domain.part.only.amp@&.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   876 - assertIsFalse "domain.part.only.asterisk@*.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   877 - assertIsFalse "domain.part.only.dollar@$.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   878 - assertIsFalse "domain.part.only.equality@=.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   879 - assertIsFalse "domain.part.only.exclamation@!.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   880 - assertIsFalse "domain.part.only.question@?.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   881 - assertIsFalse "domain.part.only.grave-accent@`.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   882 - assertIsFalse "domain.part.only.hash@#.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   883 - assertIsFalse "domain.part.only.percentage@%.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   884 - assertIsFalse "domain.part.only.pipe@|.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   885 - assertIsFalse "domain.part.only.plus@+.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   886 - assertIsFalse "domain.part.only.leftbracket@{.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   887 - assertIsFalse "domain.part.only.rightbracket@}.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   888 - assertIsFalse "domain.part.only.leftbracket@(.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   889 - assertIsFalse "domain.part.only.rightbracket@).com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   890 - assertIsFalse "domain.part.only.leftbracket@[.com"                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   891 - assertIsFalse "domain.part.only.rightbracket@].com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   892 - assertIsFalse "domain.part.only.lower.than@<.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   893 - assertIsFalse "domain.part.only.greater.than@>.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   894 - assertIsFalse "domain.part.only.tilde@~.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   895 - assertIsFalse "domain.part.only.xor@^.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   896 - assertIsFalse "domain.part.only.colon@:.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   897 - assertIsFalse "domain.part.only.space@ .com"                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   898 - assertIsFalse "domain.part.only.dot@..com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   899 - assertIsFalse "domain.part.only.comma@,.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   900 - assertIsFalse "domain.part.only.at@@.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   901 - assertIsFalse "domain.part.only.paragraph@§.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   902 - assertIsFalse "domain.part.only.double.quote@'.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   903 - assertIsFalse "domain.part.only.double.quote@\".com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   904 - assertIsFalse "domain.part.only.double.quote@\\".com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   905 - assertIsFalse "domain.part.only.empty.bracket@().com"                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   906 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   907 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   908 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   909 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   910 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   911 - assertIsFalse "domain.part.only.false.bracket3@][.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   912 - assertIsFalse "domain.part.only.false.bracket4@><.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   913 - assertIsTrue  "domain.part.only.number0@0.com"                                       =   0 =  OK 
     *   914 - assertIsTrue  "domain.part.only.number9@9.com"                                       =   0 =  OK 
     *   915 - assertIsFalse "domain.part.only.slash@\.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   916 - assertIsTrue  "domain.part.only.byte.overflow@999.com"                               =   0 =  OK 
     *   917 - assertIsTrue  "domain.part.only.no.hex.number@xyz.com"                               =   0 =  OK 
     *   918 - assertIsFalse "domain.part.only.string@\"str\".com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   919 - assertIsFalse "domain.part.only.comment@(comment).com"                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   920 - assertIsFalse "DomainHyphen@-atstart"                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   921 - assertIsFalse "DomainHyphen@atend-.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   922 - assertIsFalse "DomainHyphen@bb.-cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   923 - assertIsFalse "DomainHyphen@bb.-cc-"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   924 - assertIsFalse "DomainHyphen@bb.cc-"                                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   925 - assertIsFalse "DomainHyphen@bb.c-c"                                                  =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   926 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   927 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                    =   0 =  OK 
     *   928 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   929 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   930 - assertIsFalse "DomainNotAllowedCharacter@example'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   931 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   932 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                 =   0 =  OK 
     *   933 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                   =   0 =  OK 
     *   934 - assertIsFalse "tld.starts.with.digit@domain.2com"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   935 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                      =   0 =  OK 
     *   936 - assertIsFalse "email@=qowaiv.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   937 - assertIsFalse "email@plus+.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   938 - assertIsFalse "email@domain.com>"                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   939 - assertIsFalse "email@mailto:domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   940 - assertIsFalse "mailto:mailto:email@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   941 - assertIsFalse "email@-domain.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   942 - assertIsFalse "email@domain-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   943 - assertIsFalse "email@domain.com-"                                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   944 - assertIsFalse "email@{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   945 - assertIsFalse "email@rightbracket}.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   946 - assertIsFalse "email@pp|e.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   947 - assertIsTrue  "email@domain.domain.domain.com.com"                                   =   0 =  OK 
     *   948 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                            =   0 =  OK 
     *   949 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                     =   0 =  OK 
     *   950 - assertIsFalse "unescaped white space@fake$com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   951 - assertIsFalse "\"Joe Smith email@domain.com"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   952 - assertIsFalse "\"Joe Smith' email@domain.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   953 - assertIsFalse "\"Joe Smith\"email@domain.com"                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   954 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   955 - assertIsTrue  "{john'doe}@my.server"                                                 =   0 =  OK 
     *   956 - assertIsTrue  "email@domain-one.com"                                                 =   0 =  OK 
     *   957 - assertIsTrue  "_______@domain.com"                                                   =   0 =  OK 
     *   958 - assertIsTrue  "?????@domain.com"                                                     =   0 =  OK 
     *   959 - assertIsFalse "local@?????.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   960 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                        =   1 =  OK 
     *   961 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                               =   1 =  OK 
     *   962 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                            =   0 =  OK 
     *   963 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                     =   0 =  OK 
     *   964 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   965 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"              =   0 =  OK 
     *   966 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   967 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                  =   1 =  OK 
     *   968 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   969 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   970 - assertIsFalse "\"\"@[1"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   971 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   972 - assertIsFalse "ABC.DEF@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   973 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                   =   3 =  OK 
     *   974 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                            =   2 =  OK 
     *   975 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                              =   3 =  OK 
     *   976 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                                    =   2 =  OK 
     *   977 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   978 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   979 - assertIsFalse "ABC.DEF@([001.002.003.004])"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   980 - assertIsFalse "ABC.DEF[1.2.3.4]"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   981 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   982 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   983 - assertIsFalse "ABC.DEF@[][][][]"                                                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   984 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   985 - assertIsFalse "ABC.DEF@[....]"                                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   986 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   987 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   988 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                              =   3 =  OK 
     *   989 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   990 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   991 - assertIsFalse "ABC.DEF@[..]"                                                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   992 - assertIsFalse "ABC.DEF@[.2.3.4]"                                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   993 - assertIsFalse "ABC.DEF@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   994 - assertIsFalse "ABC.DEF@[1.2]"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   995 - assertIsFalse "ABC.DEF@[1.2.3]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   996 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                                  =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   997 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   998 - assertIsFalse "ABC.DEF@[1.2.3.]"                                                     =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   999 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1000 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1001 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1002 - assertIsFalse "ABC.DEF@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1003 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1004 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1005 - assertIsFalse "ABC.DEF@[12.34]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1006 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1007 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1008 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1009 - assertIsFalse "ABC.DEF@[-1.2.3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1010 - assertIsFalse "ABC.DEF@[1.-2.3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1011 - assertIsFalse "ABC.DEF@[1.2.-3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1012 - assertIsFalse "ABC.DEF@[1.2.3.-4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1013 - assertIsFalse "ip.v4.with.hyphen@[123.14-5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1014 - assertIsFalse "ip.v4.with.hyphen@[123.145-.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1015 - assertIsFalse "ip.v4.with.hyphen@[123.145.-178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1016 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90-]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1017 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90]-"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1018 - assertIsFalse "ip.v4.with.hyphen@[-123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1019 - assertIsFalse "ip.v4.with.hyphen@-[123.145.178.90]"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1020 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1021 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1022 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1023 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1024 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1025 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90]_"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1026 - assertIsFalse "ip.v4.with.underscore@_[123.145.178.90]"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1027 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1028 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1029 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1030 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1031 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1032 - assertIsFalse "ip.v4.with.amp@[123.145.178.90]&"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1033 - assertIsFalse "ip.v4.with.amp@&[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1034 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1035 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1036 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1037 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1038 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1039 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90]*"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1040 - assertIsFalse "ip.v4.with.asterisk@*[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1041 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1042 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1043 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1044 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1045 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1046 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90]$"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1047 - assertIsFalse "ip.v4.with.dollar@$[123.145.178.90]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1048 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1049 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1050 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1051 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1052 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1053 - assertIsFalse "ip.v4.with.equality@[123.145.178.90]="                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1054 - assertIsFalse "ip.v4.with.equality@=[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1055 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1056 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1057 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1058 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1059 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1060 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90]!"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1061 - assertIsFalse "ip.v4.with.exclamation@![123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1062 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1063 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1064 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1065 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1066 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1067 - assertIsFalse "ip.v4.with.question@[123.145.178.90]?"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1068 - assertIsFalse "ip.v4.with.question@?[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1069 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1070 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1071 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1072 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1073 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1074 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90]`"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1075 - assertIsFalse "ip.v4.with.grave-accent@`[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1076 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1077 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1078 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1079 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1080 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1081 - assertIsFalse "ip.v4.with.hash@[123.145.178.90]#"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1082 - assertIsFalse "ip.v4.with.hash@#[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1083 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1084 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1085 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1086 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1087 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1088 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90]%"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1089 - assertIsFalse "ip.v4.with.percentage@%[123.145.178.90]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1090 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1091 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1092 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1093 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1094 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1095 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90]|"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1096 - assertIsFalse "ip.v4.with.pipe@|[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1097 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1098 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1099 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1100 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1101 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1102 - assertIsFalse "ip.v4.with.plus@[123.145.178.90]+"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1103 - assertIsFalse "ip.v4.with.plus@+[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1104 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1105 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1106 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1107 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1108 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1109 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]{"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1110 - assertIsFalse "ip.v4.with.leftbracket@{[123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1111 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1112 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1113 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1114 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1115 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1116 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]}"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1117 - assertIsFalse "ip.v4.with.rightbracket@}[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1118 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1119 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1120 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1121 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1122 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1123 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]("                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1124 - assertIsFalse "ip.v4.with.leftbracket@([123.145.178.90]"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1125 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1126 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1127 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1128 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1129 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1130 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90])"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1131 - assertIsFalse "ip.v4.with.rightbracket@)[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1132 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1133 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1134 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1135 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1136 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1137 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]["                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1138 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1139 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1140 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1141 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1142 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1143 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1144 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1145 - assertIsFalse "ip.v4.with.rightbracket@][123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1146 - assertIsFalse "ip.v4.with.lower.than@[123.14<5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1147 - assertIsFalse "ip.v4.with.lower.than@[123.145<.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1148 - assertIsFalse "ip.v4.with.lower.than@[123.145.<178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1149 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90<]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1150 - assertIsFalse "ip.v4.with.lower.than@[<123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1151 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90]<"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1152 - assertIsFalse "ip.v4.with.lower.than@<[123.145.178.90]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1153 - assertIsFalse "ip.v4.with.greater.than@[123.14>5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1154 - assertIsFalse "ip.v4.with.greater.than@[123.145>.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1155 - assertIsFalse "ip.v4.with.greater.than@[123.145.>178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1156 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90>]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1157 - assertIsFalse "ip.v4.with.greater.than@[>123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1158 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90]>"                            =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1159 - assertIsFalse "ip.v4.with.greater.than@>[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1160 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1161 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1162 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1163 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1164 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1165 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90]~"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1166 - assertIsFalse "ip.v4.with.tilde@~[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1167 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1168 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1169 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1170 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1171 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1172 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1173 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1174 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1175 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1176 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1177 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1178 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1179 - assertIsFalse "ip.v4.with.xor@[123.145.178.90]^"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1180 - assertIsFalse "ip.v4.with.xor@^[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1181 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1182 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1183 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1184 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1185 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1186 - assertIsFalse "ip.v4.with.colon@[123.145.178.90]:"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1187 - assertIsFalse "ip.v4.with.colon@:[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1188 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1189 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1190 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1191 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1192 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1193 - assertIsFalse "ip.v4.with.space@[123.145.178.90] "                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1194 - assertIsFalse "ip.v4.with.space@ [123.145.178.90]"                                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1195 - assertIsFalse "ip.v4.with.dot@[123.14.5.178.90]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1196 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1197 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1198 - assertIsFalse "ip.v4.with.dot@[123.145.178.90.]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1199 - assertIsFalse "ip.v4.with.dot@[.123.145.178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1200 - assertIsFalse "ip.v4.with.dot@[123.145.178.90]."                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1201 - assertIsFalse "ip.v4.with.dot@.[123.145.178.90]"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1202 - assertIsFalse "ip.v4.with.comma@[123.14,5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1203 - assertIsFalse "ip.v4.with.comma@[123.145,.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1204 - assertIsFalse "ip.v4.with.comma@[123.145.,178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1205 - assertIsFalse "ip.v4.with.comma@[123.145.178.90,]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1206 - assertIsFalse "ip.v4.with.comma@[,123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1207 - assertIsFalse "ip.v4.with.comma@[123.145.178.90],"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1208 - assertIsFalse "ip.v4.with.comma@,[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1209 - assertIsFalse "ip.v4.with.at@[123.14@5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1210 - assertIsFalse "ip.v4.with.at@[123.145@.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1211 - assertIsFalse "ip.v4.with.at@[123.145.@178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1212 - assertIsFalse "ip.v4.with.at@[123.145.178.90@]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1213 - assertIsFalse "ip.v4.with.at@[@123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1214 - assertIsFalse "ip.v4.with.at@[123.145.178.90]@"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1215 - assertIsFalse "ip.v4.with.at@@[123.145.178.90]"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1216 - assertIsFalse "ip.v4.with.paragraph@[123.14§5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1217 - assertIsFalse "ip.v4.with.paragraph@[123.145§.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1218 - assertIsFalse "ip.v4.with.paragraph@[123.145.§178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1219 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90§]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1220 - assertIsFalse "ip.v4.with.paragraph@[§123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1221 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90]§"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1222 - assertIsFalse "ip.v4.with.paragraph@§[123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1223 - assertIsFalse "ip.v4.with.double.quote@[123.14'5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1224 - assertIsFalse "ip.v4.with.double.quote@[123.145'.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1225 - assertIsFalse "ip.v4.with.double.quote@[123.145.'178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1226 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90']"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1227 - assertIsFalse "ip.v4.with.double.quote@['123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1228 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]'"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1229 - assertIsFalse "ip.v4.with.double.quote@'[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1230 - assertIsFalse "ip.v4.with.double.quote@[123.14\"5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1231 - assertIsFalse "ip.v4.with.double.quote@[123.145\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1232 - assertIsFalse "ip.v4.with.double.quote@[123.145.\"178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1233 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90\"]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1234 - assertIsFalse "ip.v4.with.double.quote@[\"123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1235 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]\""                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1236 - assertIsFalse "ip.v4.with.double.quote@\"[123.145.178.90]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1237 - assertIsFalse "ip.v4.with.empty.bracket@[123.14()5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1238 - assertIsFalse "ip.v4.with.empty.bracket@[123.145().178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1239 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.()178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1240 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90()]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1241 - assertIsFalse "ip.v4.with.empty.bracket@[()123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1242 - assertIsTrue  "ip.v4.with.empty.bracket@[123.145.178.90]()"                          =   2 =  OK 
     *  1243 - assertIsTrue  "ip.v4.with.empty.bracket@()[123.145.178.90]"                          =   2 =  OK 
     *  1244 - assertIsFalse "ip.v4.with.empty.bracket@[123.14{}5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1245 - assertIsFalse "ip.v4.with.empty.bracket@[123.145{}.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1246 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.{}178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1247 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90{}]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1248 - assertIsFalse "ip.v4.with.empty.bracket@[{}123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1249 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]{}"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1250 - assertIsFalse "ip.v4.with.empty.bracket@{}[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1251 - assertIsFalse "ip.v4.with.empty.bracket@[123.14[]5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1252 - assertIsFalse "ip.v4.with.empty.bracket@[123.145[].178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1253 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.[]178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1254 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90[]]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1255 - assertIsFalse "ip.v4.with.empty.bracket@[[]123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1256 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90][]"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1257 - assertIsFalse "ip.v4.with.empty.bracket@[][123.145.178.90]"                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1258 - assertIsFalse "ip.v4.with.empty.bracket@[123.14<>5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1259 - assertIsFalse "ip.v4.with.empty.bracket@[123.145<>.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1260 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.<>178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1261 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90<>]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1262 - assertIsFalse "ip.v4.with.empty.bracket@[<>123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1263 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]<>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1264 - assertIsFalse "ip.v4.with.empty.bracket@<>[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1265 - assertIsFalse "ip.v4.with.false.bracket1@[123.14)(5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1266 - assertIsFalse "ip.v4.with.false.bracket1@[123.145)(.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1267 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.)(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1268 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90)(]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1269 - assertIsFalse "ip.v4.with.false.bracket1@[)(123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1270 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90])("                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1271 - assertIsFalse "ip.v4.with.false.bracket1@)([123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1272 - assertIsFalse "ip.v4.with.false.bracket2@[123.14}{5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1273 - assertIsFalse "ip.v4.with.false.bracket2@[123.145}{.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1274 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.}{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1275 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90}{]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1276 - assertIsFalse "ip.v4.with.false.bracket2@[}{123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1277 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90]}{"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1278 - assertIsFalse "ip.v4.with.false.bracket2@}{[123.145.178.90]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1279 - assertIsFalse "ip.v4.with.false.bracket3@[123.14][5.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1280 - assertIsFalse "ip.v4.with.false.bracket3@[123.145][.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1281 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.][178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1282 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90][]"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1283 - assertIsFalse "ip.v4.with.false.bracket3@[][123.145.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1284 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90]]["                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1285 - assertIsFalse "ip.v4.with.false.bracket3@][[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1286 - assertIsFalse "ip.v4.with.false.bracket4@[123.14><5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1287 - assertIsFalse "ip.v4.with.false.bracket4@[123.145><.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1288 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.><178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1289 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90><]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1290 - assertIsFalse "ip.v4.with.false.bracket4@[><123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1291 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90]><"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1292 - assertIsFalse "ip.v4.with.false.bracket4@><[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1293 - assertIsFalse "ip.v4.with.number0@[123.1405.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1294 - assertIsFalse "ip.v4.with.number0@[123.1450.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1295 - assertIsFalse "ip.v4.with.number0@[123.145.0178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1296 - assertIsFalse "ip.v4.with.number0@[123.145.178.900]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1297 - assertIsFalse "ip.v4.with.number0@[0123.145.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1298 - assertIsFalse "ip.v4.with.number0@[123.145.178.90]0"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1299 - assertIsFalse "ip.v4.with.number0@0[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1300 - assertIsFalse "ip.v4.with.number9@[123.1495.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1301 - assertIsFalse "ip.v4.with.number9@[123.1459.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1302 - assertIsFalse "ip.v4.with.number9@[123.145.9178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1303 - assertIsFalse "ip.v4.with.number9@[123.145.178.909]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1304 - assertIsFalse "ip.v4.with.number9@[9123.145.178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1305 - assertIsFalse "ip.v4.with.number9@[123.145.178.90]9"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1306 - assertIsFalse "ip.v4.with.number9@9[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1307 - assertIsFalse "ip.v4.with.numbers@[123.1401234567895.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1308 - assertIsFalse "ip.v4.with.numbers@[123.1450123456789.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1309 - assertIsFalse "ip.v4.with.numbers@[123.145.0123456789178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1310 - assertIsFalse "ip.v4.with.numbers@[123.145.178.900123456789]"                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1311 - assertIsFalse "ip.v4.with.numbers@[0123456789123.145.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1312 - assertIsFalse "ip.v4.with.numbers@[123.145.178.90]0123456789"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1313 - assertIsFalse "ip.v4.with.numbers@0123456789[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1314 - assertIsFalse "ip.v4.with.slash@[123.14\5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1315 - assertIsFalse "ip.v4.with.slash@[123.145\.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1316 - assertIsFalse "ip.v4.with.slash@[123.145.\178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1317 - assertIsFalse "ip.v4.with.slash@[123.145.178.90\]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1318 - assertIsFalse "ip.v4.with.slash@[\123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1319 - assertIsFalse "ip.v4.with.slash@[123.145.178.90]\"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1320 - assertIsFalse "ip.v4.with.slash@\[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1321 - assertIsFalse "ip.v4.with.byte.overflow@[123.149995.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1322 - assertIsFalse "ip.v4.with.byte.overflow@[123.145999.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1323 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.999178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1324 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90999]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1325 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90]999"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1326 - assertIsFalse "ip.v4.with.byte.overflow@[999123.145.178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1327 - assertIsFalse "ip.v4.with.byte.overflow@999[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1328 - assertIsFalse "ip.v4.with.no.hex.number@[123.14xyz5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1329 - assertIsFalse "ip.v4.with.no.hex.number@[123.145xyz.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1330 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.xyz178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1331 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90xyz]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1332 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90]xyz"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1333 - assertIsFalse "ip.v4.with.no.hex.number@[xyz123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1334 - assertIsFalse "ip.v4.with.no.hex.number@xyz[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1335 - assertIsFalse "ip.v4.with.string@[123.14\"str\"5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1336 - assertIsFalse "ip.v4.with.string@[123.145\"str\".178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1337 - assertIsFalse "ip.v4.with.string@[123.145.\"str\"178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1338 - assertIsFalse "ip.v4.with.string@[123.145.178.90\"str\"]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1339 - assertIsFalse "ip.v4.with.string@[123.145.178.90]\"str\""                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1340 - assertIsFalse "ip.v4.with.string@[\"str\"123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1341 - assertIsFalse "ip.v4.with.string@\"str\"[123.145.178.90]"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1342 - assertIsFalse "ip.v4.with.comment@[123.14(comment)5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1343 - assertIsFalse "ip.v4.with.comment@[123.145(comment).178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1344 - assertIsFalse "ip.v4.with.comment@[123.145.(comment)178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1345 - assertIsFalse "ip.v4.with.comment@[123.145.178.90(comment)]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1346 - assertIsTrue  "ip.v4.with.comment@[123.145.178.90](comment)"                         =   2 =  OK 
     *  1347 - assertIsFalse "ip.v4.with.comment@[(comment)123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1348 - assertIsTrue  "ip.v4.with.comment@(comment)[123.145.178.90]"                         =   2 =  OK 
     *  1349 - assertIsTrue  "email@[123.123.123.123]"                                              =   2 =  OK 
     *  1350 - assertIsFalse "email@111.222.333"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1351 - assertIsFalse "email@111.222.333.256"                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1352 - assertIsFalse "email@[123.123.123.123"                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1353 - assertIsFalse "email@[123.123.123].123"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1354 - assertIsFalse "email@123.123.123.123]"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1355 - assertIsFalse "email@123.123.[123.123]"                                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1356 - assertIsFalse "ab@988.120.150.10"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1357 - assertIsFalse "ab@120.256.256.120"                                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1358 - assertIsFalse "ab@120.25.1111.120"                                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1359 - assertIsFalse "ab@[188.120.150.10"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1360 - assertIsFalse "ab@188.120.150.10]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1361 - assertIsFalse "ab@[188.120.150.10].com"                                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1362 - assertIsTrue  "ab@188.120.150.10"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1363 - assertIsTrue  "ab@1.0.0.10"                                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1364 - assertIsTrue  "ab@120.25.254.120"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1365 - assertIsTrue  "ab@01.120.150.1"                                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1366 - assertIsTrue  "ab@88.120.150.021"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1367 - assertIsTrue  "ab@88.120.150.01"                                                     =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1368 - assertIsTrue  "email@123.123.123.123"                                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1369 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                           =   4 =  OK 
     *  1370 - assertIsFalse "ABC.DEF@[IP"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1371 - assertIsFalse "ABC.DEF@[IPv6]"                                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1372 - assertIsFalse "ABC.DEF@[IPv6:]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1373 - assertIsFalse "ABC.DEF@[IPv6:"                                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1374 - assertIsFalse "ABC.DEF@[IPv6::]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1375 - assertIsFalse "ABC.DEF@[IPv6::"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1376 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1377 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1378 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1379 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1380 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1381 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                 =   4 =  OK 
     *  1382 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                               =   4 =  OK 
     *  1383 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                            =   4 =  OK 
     *  1384 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                           =   4 =  OK 
     *  1385 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                           =   4 =  OK 
     *  1386 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                         =   4 =  OK 
     *  1387 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1388 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                       =   4 =  OK 
     *  1389 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                     =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1390 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                               =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1391 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                             =   4 =  OK 
     *  1392 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1393 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1394 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1395 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1396 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1397 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                        =   4 =  OK 
     *  1398 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1399 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1400 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1401 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1402 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1403 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1404 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1405 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1406 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1407 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1408 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1409 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1410 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1411 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1412 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1413 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1414 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1415 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1416 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1417 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1418 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1419 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1420 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1421 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1422 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1423 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1424 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1425 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1426 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1427 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1428 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1429 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1430 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1431 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1432 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1433 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1434 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1435 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1436 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1437 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1438 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1439 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1440 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1441 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1442 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1443 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1444 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1445 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1446 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1447 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1448 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1449 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1450 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1451 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1452 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1453 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1454 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1455 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1456 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1457 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1458 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1459 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1460 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1461 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1462 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1463 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1464 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1465 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1466 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1467 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1468 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1469 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1470 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1472 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1473 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1474 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1475 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1476 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1477 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1478 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1479 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1480 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1481 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1482 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1483 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1484 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1485 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1486 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1487 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1488 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1489 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1490 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1491 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1492 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1493 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1494 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1495 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1496 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1497 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1498 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1499 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1500 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1501 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1502 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1503 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1504 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1505 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1507 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1508 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1509 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1510 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1511 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1512 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1513 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1514 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1515 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1516 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1517 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1518 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1519 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1520 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1521 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1522 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1523 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1524 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1525 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1526 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1527 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1528 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1529 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1530 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1531 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1532 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1533 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1534 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1535 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1536 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1537 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1538 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1539 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1540 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1541 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                              =   4 =  OK 
     *  1542 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1543 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1544 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                              =   4 =  OK 
     *  1545 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1546 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1547 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1548 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1549 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1550 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1551 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1552 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1553 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1554 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1555 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1556 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1557 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1558 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1559 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1560 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1561 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1562 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1563 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1564 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1565 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1567 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1568 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1570 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1571 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1573 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1574 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1575 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1576 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1577 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1578 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1579 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1580 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1581 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1582 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1583 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1584 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1585 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1586 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1587 - assertIsFalse "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1588 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\""                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1589 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1590 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1591 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1592 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                     =   4 =  OK 
     *  1594 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                     =   4 =  OK 
     *  1595 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1596 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1597 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1598 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1599 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1600 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1601 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1602 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1603 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1604 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1605 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1606 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1607 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1608 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1609 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1610 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1611 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1612 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1613 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1614 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1615 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1616 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1617 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1618 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1619 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1620 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1621 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1622 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1623 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1624 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1625 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1626 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1627 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1628 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1629 - assertIsFalse "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1630 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]]["                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1631 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1632 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1633 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1634 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1635 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1636 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1637 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1638 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1639 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1640 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1641 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1642 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1643 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1644 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1645 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1646 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1647 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1648 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1649 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1650 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1651 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:29993:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1652 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1653 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1654 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1655 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1656 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1657 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1658 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1659 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1660 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1661 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1662 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1663 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1664 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1665 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1666 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1667 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1668 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1669 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1670 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1671 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1672 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1673 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1674 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                    =   4 =  OK 
     *  1675 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                    =   4 =  OK 
     *  1676 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1677 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                        =   4 =  OK 
     *  1678 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1679 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1680 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1681 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1682 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1683 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"               =   4 =  OK 
     *  1684 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"               =   4 =  OK 
     *  1685 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"               =   4 =  OK 
     *  1686 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                              =   4 =  OK 
     *  1687 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                               =   4 =  OK 
     *  1688 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1689 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1690 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1691 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1692 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                       =   4 =  OK 
     *  1693 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                       =   4 =  OK 
     *  1694 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                           =   4 =  OK 
     *  1695 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                           =   4 =  OK 
     *  1696 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1697 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                        =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1698 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1699 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1700 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1701 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1702 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1703 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1704 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *  1705 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1706 - assertIsTrue  "\"ABC\".\"DEF\"@GHI.DE"                                               =   1 =  OK 
     *  1707 - assertIsFalse "-\"ABC\".\"DEF\"@GHI.DE"                                              = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
     *  1708 - assertIsFalse "\"ABC\"-.\"DEF\"@GHI.DE"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1709 - assertIsFalse "\"ABC\".-\"DEF\"@GHI.DE"                                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1710 - assertIsFalse ".\"ABC\".\"DEF\"@GHI.DE"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1711 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1712 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1713 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                 =   1 =  OK 
     *  1714 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1715 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1716 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1717 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1718 - assertIsFalse "\"\"@GHI.DE"                                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1719 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1720 - assertIsFalse "A@G\"HI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1721 - assertIsFalse "\"@GHI.DE"                                                            =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1722 - assertIsFalse "ABC.DEF.\""                                                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1723 - assertIsFalse "ABC.DEF@\"\""                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1724 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1725 - assertIsFalse "ABC.DEF@GHI.DE\""                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1726 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1727 - assertIsFalse "\"Escape.Sequenz.Ende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1728 - assertIsFalse "ABC.DEF\"GHI.DE"                                                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1729 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1730 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1731 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1732 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1733 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1734 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                  =   1 =  OK 
     *  1735 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                           =   1 =  OK 
     *  1736 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1737 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1738 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1739 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1740 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1741 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1742 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1743 - assertIsTrue  "\"\".\"\".ABC.DEF@GHI.DE"                                             =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  1744 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1745 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1746 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1747 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1748 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1749 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1750 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1751 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1752 - assertIsFalse "\"\".ABC.DEF@GHI.DE"                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1753 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1754 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1755 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                               =   1 =  OK 
     *  1756 - assertIsFalse "\"Ende.am.Eingabeende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1757 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1758 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                 =   1 =  OK 
     *  1759 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1760 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1761 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1762 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                =   1 =  OK 
     *  1763 - assertIsFalse "\"Joe Smith\" email@domain.com"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1764 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1765 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *  1766 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                     =   6 =  OK 
     *  1767 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1768 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                     =   6 =  OK 
     *  1769 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                 =   6 =  OK 
     *  1770 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                           =   6 =  OK 
     *  1771 - assertIsFalse "ABC.DEF@             (MNO)"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1772 - assertIsFalse "ABC.DEF@   .         (MNO)"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1773 - assertIsFalse "ABC.DEF              (MNO)"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1774 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                           =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1775 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1776 - assertIsFalse "ABC.DEF@GHI.JKL          "                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1777 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1778 - assertIsFalse "("                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1779 - assertIsFalse ")"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1780 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                   =   6 =  OK 
     *  1781 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                    =   6 =  OK 
     *  1782 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                    =   6 =  OK 
     *  1783 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                    =   6 =  OK 
     *  1784 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                    =   6 =  OK 
     *  1785 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1786 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1787 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1788 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1789 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1790 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1791 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1792 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1793 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1794 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1795 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1796 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1797 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1798 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                   = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1799 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                     =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1800 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1801 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1802 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1803 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1804 - assertIsFalse "ABC(DEF@GHI.JKL"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1805 - assertIsFalse "ABC.DEF@GHI)JKL"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1806 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1807 - assertIsFalse "ABC.DEF)@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1808 - assertIsFalse "ABC(DEF@GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1809 - assertIsFalse "ABC(DEF.GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1810 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1811 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1812 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1813 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1814 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1815 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1816 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                           =   2 =  OK 
     *  1817 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                          = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  1818 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                           =   2 =  OK 
     *  1819 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                       =   2 =  OK 
     *  1820 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1821 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                               =   4 =  OK 
     *  1822 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                               =   4 =  OK 
     *  1823 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                           =   4 =  OK 
     *  1824 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1825 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                            =   6 =  OK 
     *  1826 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                            = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1827 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1828 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1829 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1830 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1831 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1832 - assertIsFalse "john.smith@exampl(comment)e.com"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1833 - assertIsFalse "john.s(comment)mith@example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1834 - assertIsFalse "john.smith(comment)@(comment)example.com"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1835 - assertIsFalse "john.smith(com@ment)example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1836 - assertIsFalse "email( (nested) )@plus.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1837 - assertIsFalse "email)mirror(@plus.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1838 - assertIsFalse "email@plus.com (not closed comment"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1839 - assertIsFalse "email(with @ in comment)plus.com"                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1840 - assertIsTrue  "email@domain.com (joe Smith)"                                         =   6 =  OK 
     *  1841 - assertIsFalse "a@abc(bananas)def.com"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1842 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                            =   0 =  OK 
     *  1843 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                            =   0 =  OK 
     *  1844 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                             =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1845 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                             =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1846 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                        =   0 =  OK 
     *  1847 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                  =   1 =  OK 
     *  1848 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                          =   1 =  OK 
     *  1849 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1850 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1851 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1852 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1853 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1854 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1855 - assertIsFalse "ABC DEF <A@A>"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1856 - assertIsFalse "<A@A> ABC DEF"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1857 - assertIsFalse "ABC DEF <>"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1858 - assertIsFalse "<> ABC DEF"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1859 - assertIsFalse "<"                                                                    =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1860 - assertIsFalse ">"                                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1861 - assertIsFalse "<         >"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1862 - assertIsFalse "< <     > >"                                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1863 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                    =   0 =  OK 
     *  1864 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                   = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1865 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1866 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                   =   0 =  OK 
     *  1867 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1868 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                   =   0 =  OK 
     *  1869 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1870 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                           =   6 =  OK 
     *  1871 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1872 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                          = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1873 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                          =   6 =  OK 
     *  1874 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                          = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1875 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                          =   6 =  OK 
     *  1876 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                          = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1877 - assertIsTrue  "Joe Smith <email@domain.com>"                                         =   0 =  OK 
     *  1878 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1879 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1880 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1881 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1882 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"      =   9 =  OK 
     *  1883 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "     =   9 =  OK 
     *  1884 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1885 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1886 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1887 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1888 - assertIsFalse "Test |<gaaf <email@domain.com>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1889 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1890 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1891 - assertIsFalse "<null>@mail.com"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *  1892 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1893 - assertIsFalse "A@B.C"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1894 - assertIsFalse "A@COM"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1895 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                      =   0 =  OK 
     *  1896 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  1897 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1898 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1899 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  1900 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1901 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  1902 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1903 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld> True64 " =   0 =  OK 
     *  1904 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1905 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  1906 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1907 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *  1908 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  1909 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1910 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1911 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1912 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1913 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1914 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1915 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1916 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1917 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1918 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1919 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1920 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1921 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
     *  1922 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  1923 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  1924 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1925 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1926 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  1927 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1928 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1929 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1930 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
     *  1931 - assertIsTrue  "email@domain.topleveldomain"                                          =   0 =  OK 
     *  1932 - assertIsTrue  "email@email.email.mydomain"                                           =   0 =  OK 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     *  1933 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                     =   6 =  OK 
     *  1934 - assertIsTrue  "\"MaxMustermann\"@example.com"                                        =   1 =  OK 
     *  1935 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                           =   1 =  OK 
     *  1936 - assertIsTrue  "\".John.Doe\"@example.com"                                            =   1 =  OK 
     *  1937 - assertIsTrue  "\"John.Doe.\"@example.com"                                            =   1 =  OK 
     *  1938 - assertIsTrue  "\"John..Doe\"@example.com"                                            =   1 =  OK 
     *  1939 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1940 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1941 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1942 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1943 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  1944 - assertIsTrue  "jsmith@[192.168.2.1]"                                                 =   2 =  OK 
     *  1945 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                            =   4 =  OK 
     *  1946 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                    =   0 =  OK 
     *  1947 - assertIsTrue  "Marc Dupont <md118@example.com>"                                      =   0 =  OK 
     *  1948 - assertIsTrue  "simple@example.com"                                                   =   0 =  OK 
     *  1949 - assertIsTrue  "very.common@example.com"                                              =   0 =  OK 
     *  1950 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                       =   0 =  OK 
     *  1951 - assertIsTrue  "other.email-with-hyphen@example.com"                                  =   0 =  OK 
     *  1952 - assertIsTrue  "fully-qualified-domain@example.com"                                   =   0 =  OK 
     *  1953 - assertIsTrue  "user.name+tag+sorting@example.com"                                    =   0 =  OK 
     *  1954 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  1955 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                     =   0 =  OK 
     *  1956 - assertIsTrue  "x@example.com"                                                        =   0 =  OK 
     *  1957 - assertIsTrue  "info@firma.org"                                                       =   0 =  OK 
     *  1958 - assertIsTrue  "example-indeed@strange-example.com"                                   =   0 =  OK 
     *  1959 - assertIsTrue  "admin@mailserver1"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1960 - assertIsTrue  "example@s.example"                                                    =   0 =  OK 
     *  1961 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  1962 - assertIsTrue  "\"john..doe\"@example.org"                                            =   1 =  OK 
     *  1963 - assertIsTrue  "mailhost!username@example.org"                                        =   0 =  OK 
     *  1964 - assertIsTrue  "user%example.com@example.org"                                         =   0 =  OK 
     *  1965 - assertIsTrue  "joe25317@NOSPAMexample.com"                                           =   0 =  OK 
     *  1966 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  1967 - assertIsTrue  "nama@contoh.com"                                                      =   0 =  OK 
     *  1968 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  1969 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1970 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1971 - assertIsFalse "Abc..123@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1972 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1973 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1974 - assertIsFalse "just\"not\"right@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1975 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1976 - assertIsFalse "this\ still\\"not\\allowed@example.com"                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1977 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1978 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"   =   0 =  OK 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     *  1979 - assertIsFalse "nolocalpart.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1980 - assertIsFalse "test@example.com test"                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1981 - assertIsFalse "user  name@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1982 - assertIsFalse "user   name@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1983 - assertIsFalse "example.@example.co.uk"                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1984 - assertIsFalse "example@example@example.co.uk"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1985 - assertIsFalse "(test_exampel@example.fr}"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1986 - assertIsFalse "example(example)example@example.co.uk"                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1987 - assertIsFalse ".example@localhost"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1988 - assertIsFalse "ex\ample@localhost"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1989 - assertIsFalse "example@local\host"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1990 - assertIsFalse "example@localhost."                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1991 - assertIsFalse "user name@example.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1992 - assertIsFalse "username@ example . com"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1993 - assertIsFalse "example@(fake}.com"                                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1994 - assertIsFalse "example@(fake.com"                                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1995 - assertIsTrue  "username@example.com"                                                 =   0 =  OK 
     *  1996 - assertIsTrue  "usern.ame@example.com"                                                =   0 =  OK 
     *  1997 - assertIsFalse "user[na]me@example.com"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1998 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1999 - assertIsFalse "\"\\"@iana.org"                                                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2000 - assertIsFalse "\"test\"test@iana.org"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2001 - assertIsFalse "\"test\"\"test\"@iana.org"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2002 - assertIsTrue  "\"test\".\"test\"@iana.org"                                           =   1 =  OK 
     *  2003 - assertIsTrue  "\"test\".test@iana.org"                                               =   1 =  OK 
     *  2004 - assertIsFalse "\"test\\"@iana.org"                                                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2005 - assertIsFalse "\r\ntest@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2006 - assertIsFalse "\r\n test@iana.org"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2007 - assertIsFalse "\r\n \r\ntest@iana.org"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2008 - assertIsFalse "\r\n \r\n test@iana.org"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2009 - assertIsFalse "test@iana.org \r\n"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2010 - assertIsFalse "test@iana.org \r\n "                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2011 - assertIsFalse "test@iana.org \r\n \r\n"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2012 - assertIsFalse "test@iana.org \r\n\r\n"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2013 - assertIsFalse "test@iana.org  \r\n\r\n "                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2014 - assertIsFalse "test@iana/icann.org"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2015 - assertIsFalse "test@foo;bar.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2016 - assertIsFalse "a@test.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2017 - assertIsFalse "comment)example@example.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2018 - assertIsFalse "comment(example))@example.com"                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2019 - assertIsFalse "example@example)comment.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2020 - assertIsFalse "example@example(comment)).com"                                        = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2021 - assertIsFalse "example@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2022 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2023 - assertIsFalse "exam(ple@exam).ple"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2024 - assertIsFalse "example@(example))comment.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2025 - assertIsTrue  "example@example.com"                                                  =   0 =  OK 
     *  2026 - assertIsTrue  "example@example.co.uk"                                                =   0 =  OK 
     *  2027 - assertIsTrue  "example_underscore@example.fr"                                        =   0 =  OK 
     *  2028 - assertIsTrue  "exam'ple@example.com"                                                 =   0 =  OK 
     *  2029 - assertIsTrue  "exam\ ple@example.com"                                                =   0 =  OK 
     *  2030 - assertIsFalse "example((example))@fakedfake.co.uk"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2031 - assertIsFalse "example@faked(fake).co.uk"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2032 - assertIsTrue  "example+@example.com"                                                 =   0 =  OK 
     *  2033 - assertIsTrue  "example@with-hyphen.example.com"                                      =   0 =  OK 
     *  2034 - assertIsTrue  "with-hyphen@example.com"                                              =   0 =  OK 
     *  2035 - assertIsTrue  "example@1leadingnum.example.com"                                      =   0 =  OK 
     *  2036 - assertIsTrue  "1leadingnum@example.com"                                              =   0 =  OK 
     *  2037 - assertIsTrue  "инфо@письмо.рф"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2038 - assertIsTrue  "\"username\"@example.com"                                             =   1 =  OK 
     *  2039 - assertIsTrue  "\"user.name\"@example.com"                                            =   1 =  OK 
     *  2040 - assertIsTrue  "\"user name\"@example.com"                                            =   1 =  OK 
     *  2041 - assertIsTrue  "\"user@name\"@example.com"                                            =   1 =  OK 
     *  2042 - assertIsFalse "\"\a\"@iana.org"                                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2043 - assertIsTrue  "\"test\ test\"@iana.org"                                              =   1 =  OK 
     *  2044 - assertIsFalse "\"\"@iana.org"                                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2045 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2046 - assertIsTrue  "\"\\"\"@iana.org"                                                     =   1 =  OK 
     *  2047 - assertIsTrue  "example@localhost"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     *  2048 - assertIsFalse " check@this.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2049 - assertIsFalse " email@example.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2050 - assertIsTrue  "!def!xyz%abc@example.com"                                             =   0 =  OK 
     *  2051 - assertIsTrue  "!sd@gh.com"                                                           =   0 =  OK 
     *  2052 - assertIsTrue  "$A12345@example.com"                                                  =   0 =  OK 
     *  2053 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                =   0 =  OK 
     *  2054 - assertIsTrue  "%2@gmail.com"                                                         =   0 =  OK 
     *  2055 - assertIsTrue  "--@ooo.ooo"                                                           =   0 =  OK 
     *  2056 - assertIsTrue  "-@bde.cc"                                                             =   0 =  OK 
     *  2057 - assertIsTrue  "-asd@das.com"                                                         =   0 =  OK 
     *  2058 - assertIsFalse ".....@a...."                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2059 - assertIsFalse "..@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2060 - assertIsFalse ".@s.dd"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2061 - assertIsFalse ".a@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2062 - assertIsFalse ".dot@example.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2063 - assertIsFalse ".email@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2064 - assertIsFalse ".journaldev@journaldev.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2065 - assertIsFalse ".xxxx@mysite.org"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2066 - assertIsTrue  "1234567890@domain.com"                                                =   0 =  OK 
     *  2067 - assertIsFalse "123@$.xyz"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2068 - assertIsTrue  "1@domain.com"                                                         =   0 =  OK 
     *  2069 - assertIsTrue  "1@gmail.com"                                                          =   0 =  OK 
     *  2070 - assertIsTrue  "1_example@something.gmail.com"                                        =   0 =  OK 
     *  2071 - assertIsTrue  "2@bde.cc"                                                             =   0 =  OK 
     *  2072 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2073 - assertIsTrue  "<boss@nil.test>"                                                      =   0 =  OK 
     *  2074 - assertIsFalse "@b.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2075 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2076 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2077 - assertIsFalse "@you.me.net"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2078 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2079 - assertIsTrue  "Abc.123@example.com"                                                  =   0 =  OK 
     *  2080 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2081 - assertIsTrue  "Abc@10.42.0.1"                                                        =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2082 - assertIsTrue  "Abc@example.com"                                                      =   0 =  OK 
     *  2083 - assertIsFalse "Abc@example.com."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2084 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                 =   0 =  OK 
     *  2085 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2086 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2087 - assertIsFalse "Foobar Some@thing.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2088 - assertIsTrue  "Fred\ Bloggs@example.com"                                             =   0 =  OK 
     *  2089 - assertIsTrue  "Joe.\\Blow@example.com"                                               =   0 =  OK 
     *  2090 - assertIsFalse "MailTo:casesensitve@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2091 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                          =   0 =  OK 
     *  2092 - assertIsTrue  "Who? <one@y.test>"                                                    =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2093 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  2094 - assertIsTrue  "\"%2\"@gmail.com"                                                     =   1 =  OK 
     *  2095 - assertIsTrue  "\"Abc@def\"@example.com"                                              =   1 =  OK 
     *  2096 - assertIsTrue  "\"Abc\@def\"@example.com"                                             =   1 =  OK 
     *  2097 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2098 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                    =   1 =  OK 
     *  2099 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  2100 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  2101 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                         =   1 =  OK 
     *  2102 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                   =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2103 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2104 - assertIsFalse "\"Joe\Blow\"@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2105 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                            =   1 =  OK 
     *  2106 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                            =   1 =  OK 
     *  2107 - assertIsTrue  "\"a..b\"@gmail.com"                                                   =   1 =  OK 
     *  2108 - assertIsTrue  "\"a_b\"@gmail.com"                                                    =   1 =  OK 
     *  2109 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                         =   1 =  OK 
     *  2110 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                  =   1 =  OK 
     *  2111 - assertIsTrue  "\"foo\@bar\"@Something.com"                                           =   1 =  OK 
     *  2112 - assertIsTrue  "\"j\\"s\"@proseware.com"                                              =   1 =  OK 
     *  2113 - assertIsTrue  "\"myemail@sa\"@mple.com"                                              =   1 =  OK 
     *  2114 - assertIsFalse "\"qu@example.com"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2115 - assertIsFalse "\$A12345@example.com"                                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2116 - assertIsTrue  "_-_@bde.cc"                                                           =   0 =  OK 
     *  2117 - assertIsFalse "_@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2118 - assertIsTrue  "_@gmail.com"                                                          =   0 =  OK 
     *  2119 - assertIsTrue  "_dasd@sd.com"                                                         =   0 =  OK 
     *  2120 - assertIsTrue  "_dasd_das_@9.com"                                                     =   0 =  OK 
     *  2121 - assertIsTrue  "_somename@example.com"                                                =   0 =  OK 
     *  2122 - assertIsTrue  "a+b@bde.cc"                                                           =   0 =  OK 
     *  2123 - assertIsTrue  "a+b@c.com"                                                            =   0 =  OK 
     *  2124 - assertIsTrue  "a-b@bde.cc"                                                           =   0 =  OK 
     *  2125 - assertIsFalse "a..b@bde.cc"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2126 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2127 - assertIsTrue  "a.a@test.com"                                                         =   0 =  OK 
     *  2128 - assertIsTrue  "a.b@com"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2129 - assertIsFalse "a.b@example,co.de"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2130 - assertIsFalse "a.b@example,com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2131 - assertIsTrue  "a2@bde.cc"                                                            =   0 =  OK 
     *  2132 - assertIsFalse "a@.com"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2133 - assertIsTrue  "a@123.45.67.89"                                                       =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2134 - assertIsFalse "a@b."                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2135 - assertIsFalse "a@b.-de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2136 - assertIsFalse "a@b._de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2137 - assertIsTrue  "a@b.c.com"                                                            =   0 =  OK 
     *  2138 - assertIsTrue  "a@b.com"                                                              =   0 =  OK 
     *  2139 - assertIsTrue  "a@bc.com"                                                             =   0 =  OK 
     *  2140 - assertIsTrue  "a@bcom"                                                               =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2141 - assertIsFalse "a@bde-.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2142 - assertIsFalse "a@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2143 - assertIsFalse "a@bde_.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2144 - assertIsTrue  "a@domain.com"                                                         =   0 =  OK 
     *  2145 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2146 - assertIsTrue  "aaron@theinfo.org"                                                    =   0 =  OK 
     *  2147 - assertIsTrue  "ab@288.120.150.10.com"                                                =   0 =  OK 
     *  2148 - assertIsTrue  "ab@[120.254.254.120]"                                                 =   2 =  OK 
     *  2149 - assertIsFalse "ab@b+de.cc"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2150 - assertIsTrue  "ab@b-de.cc"                                                           =   0 =  OK 
     *  2151 - assertIsTrue  "ab@c.com"                                                             =   0 =  OK 
     *  2152 - assertIsFalse "ab@sd@dd"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2153 - assertIsTrue  "ab_c@bde.cc"                                                          =   0 =  OK 
     *  2154 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                       =   1 =  OK 
     *  2155 - assertIsTrue  "abc.xyz@gmail.com.in"                                                 =   0 =  OK 
     *  2156 - assertIsTrue  "abc123xyz@asdf.co.in"                                                 =   0 =  OK 
     *  2157 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                 =   0 =  OK 
     *  2158 - assertIsFalse "abc@def@example.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2159 - assertIsFalse "abc\"defghi\"xyz@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2160 - assertIsTrue  "abc\@def@example.com"                                                 =   0 =  OK 
     *  2161 - assertIsFalse "abc\@example.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2162 - assertIsFalse "abc\\"def\\"ghi@example.com"                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2163 - assertIsFalse "abc\\@def@example.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2164 - assertIsTrue  "abc\\@example.com"                                                    =   0 =  OK 
     *  2165 - assertIsTrue  "abcxyz123@qwert.com"                                                  =   0 =  OK 
     *  2166 - assertIsTrue  "alex@example.com"                                                     =   0 =  OK 
     *  2167 - assertIsTrue  "alireza@test.co.uk"                                                   =   0 =  OK 
     *  2168 - assertIsFalse "as3d@dac.coas-"                                                       =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2169 - assertIsTrue  "asd-@asd.com"                                                         =   0 =  OK 
     *  2170 - assertIsFalse "asd@dasd@asd.cm"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2171 - assertIsTrue  "begeddov@jfinity.com"                                                 =   0 =  OK 
     *  2172 - assertIsFalse "check@this..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2173 - assertIsTrue  "check@this.com"                                                       =   0 =  OK 
     *  2174 - assertIsFalse "check@thiscom"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2175 - assertIsTrue  "cog@wheel.com"                                                        =   0 =  OK 
     *  2176 - assertIsTrue  "customer/department=shipping@example.com"                             =   0 =  OK 
     *  2177 - assertIsTrue  "d._.___d@gmail.com"                                                   =   0 =  OK 
     *  2178 - assertIsTrue  "d.j@server1.proseware.com"                                            =   0 =  OK 
     *  2179 - assertIsTrue  "d.oy.smith@gmail.com"                                                 =   0 =  OK 
     *  2180 - assertIsTrue  "d23d@da9.co9"                                                         =   0 =  OK 
     *  2181 - assertIsTrue  "d_oy_smith@gmail.com"                                                 =   0 =  OK 
     *  2182 - assertIsFalse "da23@das..com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2183 - assertIsFalse "dad@sds"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2184 - assertIsTrue  "dasd-dasd@das.com.das"                                                =   0 =  OK 
     *  2185 - assertIsTrue  "dasd.dadas@dasd.com"                                                  =   0 =  OK 
     *  2186 - assertIsTrue  "dasd_-@jdas.com"                                                      =   0 =  OK 
     *  2187 - assertIsFalse "dasddas-@.com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2188 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2189 - assertIsTrue  "david.jones@proseware.com"                                            =   0 =  OK 
     *  2190 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2191 - assertIsTrue  "dda_das@das-dasd.com"                                                 =   0 =  OK 
     *  2192 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                         =   0 =  OK 
     *  2193 - assertIsTrue  "digit-only-domain@123.com"                                            =   0 =  OK 
     *  2194 - assertIsFalse "dot.@example.com"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2195 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2196 - assertIsTrue  "doysmith@gmail.com"                                                   =   0 =  OK 
     *  2197 - assertIsTrue  "drp@drp.cz"                                                           =   0 =  OK 
     *  2198 - assertIsTrue  "dsq!a?@das.com"                                                       =   0 =  OK 
     *  2199 - assertIsFalse "email..email@domain.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2200 - assertIsFalse "email.@domain.com"                                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2201 - assertIsFalse "email@.domain.com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2202 - assertIsFalse "email@domain"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2203 - assertIsFalse "email@domain..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2204 - assertIsTrue  "email@domain.co.de"                                                   =   0 =  OK 
     *  2205 - assertIsTrue  "email@domain.com"                                                     =   0 =  OK 
     *  2206 - assertIsFalse "email@domain.com."                                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2207 - assertIsFalse "email@example"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2208 - assertIsFalse "email@example..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2209 - assertIsTrue  "email@example.co.uk"                                                  =   0 =  OK 
     *  2210 - assertIsFalse "email@example.co.uk."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2211 - assertIsFalse "email@example.com "                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2212 - assertIsTrue  "email@example.com"                                                    =   0 =  OK 
     *  2213 - assertIsTrue  "email@mail.gmail.com"                                                 =   0 =  OK 
     *  2214 - assertIsTrue  "email@subdomain.domain.com"                                           =   0 =  OK 
     *  2215 - assertIsTrue  "example@example.co"                                                   =   0 =  OK 
     *  2216 - assertIsFalse "f...bar@gmail.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2217 - assertIsTrue  "f.f.f@bde.cc"                                                         =   0 =  OK 
     *  2218 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                =   0 =  OK 
     *  2219 - assertIsFalse "fdsa"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2220 - assertIsFalse "fdsa@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2221 - assertIsFalse "fdsa@fdsa"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2222 - assertIsFalse "fdsa@fdsa."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2223 - assertIsTrue  "first-name-last-name@d-a-n.com"                                       =   0 =  OK 
     *  2224 - assertIsTrue  "firstname+lastname@domain.com"                                        =   0 =  OK 
     *  2225 - assertIsTrue  "firstname-lastname@domain.com"                                        =   0 =  OK 
     *  2226 - assertIsTrue  "firstname.lastname@domain.com"                                        =   0 =  OK 
     *  2227 - assertIsFalse "foo.bar#gmail.co.u"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2228 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2229 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2230 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                            =   0 =  OK 
     *  2231 - assertIsFalse "foo~&(&)(@bar.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2232 - assertIsTrue  "futureTLD@somewhere.fooo"                                             =   0 =  OK 
     *  2233 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2234 - assertIsFalse "get_at_m.e@gmail"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2235 - assertIsFalse "hallo2ww22@example....caaaao"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2236 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2237 - assertIsFalse "hello world@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2238 - assertIsTrue  "hello.me_1@email.com"                                                 =   0 =  OK 
     *  2239 - assertIsTrue  "hello7___@ca.com.pt"                                                  =   0 =  OK 
     *  2240 - assertIsTrue  "info@ermaelan.com"                                                    =   0 =  OK 
     *  2241 - assertIsFalse "invalid.email.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2242 - assertIsFalse "invalid@email@domain.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2243 - assertIsFalse "j..s@proseware.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2244 - assertIsFalse "j.@server1.proseware.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2245 - assertIsTrue  "j.s@server1.proseware.com"                                            =   0 =  OK 
     *  2246 - assertIsTrue  "j@proseware.com9"                                                     =   0 =  OK 
     *  2247 - assertIsTrue  "j_9@[129.126.118.1]"                                                  =   2 =  OK 
     *  2248 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2249 - assertIsTrue  "jinujawad6s@gmail.com"                                                =   0 =  OK 
     *  2250 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  2251 - assertIsTrue  "jones@ms1.proseware.com"                                              =   0 =  OK 
     *  2252 - assertIsFalse "journaldev"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2253 - assertIsFalse "journaldev()*@gmail.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2254 - assertIsTrue  "journaldev+100@gmail.com"                                             =   0 =  OK 
     *  2255 - assertIsTrue  "journaldev-100@journaldev.net"                                        =   0 =  OK 
     *  2256 - assertIsTrue  "journaldev-100@yahoo-test.com"                                        =   0 =  OK 
     *  2257 - assertIsTrue  "journaldev-100@yahoo.com"                                             =   0 =  OK 
     *  2258 - assertIsFalse "journaldev..2002@gmail.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2259 - assertIsTrue  "journaldev.100@journaldev.com.au"                                     =   0 =  OK 
     *  2260 - assertIsTrue  "journaldev.100@yahoo.com"                                             =   0 =  OK 
     *  2261 - assertIsFalse "journaldev.@gmail.com"                                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2262 - assertIsTrue  "journaldev111@journaldev.com"                                         =   0 =  OK 
     *  2263 - assertIsFalse "journaldev123@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2264 - assertIsFalse "journaldev123@.com.com"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2265 - assertIsFalse "journaldev123@gmail.a"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2266 - assertIsFalse "journaldev@%*.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2267 - assertIsFalse "journaldev@.com.my"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2268 - assertIsTrue  "journaldev@1.com"                                                     =   0 =  OK 
     *  2269 - assertIsFalse "journaldev@gmail.com.1a"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2270 - assertIsTrue  "journaldev@gmail.com.com"                                             =   0 =  OK 
     *  2271 - assertIsFalse "journaldev@journaldev@gmail.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2272 - assertIsTrue  "journaldev@yahoo.com"                                                 =   0 =  OK 
     *  2273 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                    =   0 =  OK 
     *  2274 - assertIsTrue  "js#internal@proseware.com"                                            =   0 =  OK 
     *  2275 - assertIsTrue  "js*@proseware.com"                                                    =   0 =  OK 
     *  2276 - assertIsFalse "js@proseware..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2277 - assertIsTrue  "js@proseware.com9"                                                    =   0 =  OK 
     *  2278 - assertIsFalse "mailto:email@domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2279 - assertIsFalse "me@.com.my"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2280 - assertIsFalse "me123@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2281 - assertIsFalse "me123@.com.com"                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2282 - assertIsFalse "me@%*.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2283 - assertIsFalse "me..2002@gmail.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2284 - assertIsFalse "me.@gmail.com"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2285 - assertIsFalse "me@me@gmail.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2286 - assertIsTrue  "me@gmail.com"                                                         =   0 =  OK 
     *  2287 - assertIsTrue  "me@me.cu.uk"                                                          =   0 =  OK 
     *  2288 - assertIsTrue  "me-100@me.com"                                                        =   0 =  OK 
     *  2289 - assertIsTrue  "me.100@me.com"                                                        =   0 =  OK 
     *  2290 - assertIsTrue  "me-100@me.com.au"                                                     =   0 =  OK 
     *  2291 - assertIsTrue  "me+100@me.com"                                                        =   0 =  OK 
     *  2292 - assertIsTrue  "me-100@yahoo-test.com"                                                =   0 =  OK 
     *  2293 - assertIsTrue  "me@gmail.com"                                                         =   0 =  OK 
     *  2294 - assertIsFalse "me@gmail.com.1a"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2295 - assertIsTrue  "me@company.co.uk"                                                     =   0 =  OK 
     *  2296 - assertIsTrue  "user_name@domain.com"                                                 =   0 =  OK 
     *  2297 - assertIsTrue  "user_name@domain.co.in"                                               =   0 =  OK 
     *  2298 - assertIsTrue  "user@domaincom"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2299 - assertIsTrue  "user@domain.com"                                                      =   0 =  OK 
     *  2300 - assertIsTrue  "user@domain.co.in"                                                    =   0 =  OK 
     *  2301 - assertIsTrue  "user?name@domain.co.in"                                               =   0 =  OK 
     *  2302 - assertIsTrue  "user1@domain.com"                                                     =   0 =  OK 
     *  2303 - assertIsTrue  "user.name@domain.com"                                                 =   0 =  OK 
     *  2304 - assertIsTrue  "user-name@domain.co.in"                                               =   0 =  OK 
     *  2305 - assertIsTrue  "user'name@domain.co.in"                                               =   0 =  OK 
     *  2306 - assertIsTrue  "user#@domain.co.in"                                                   =   0 =  OK 
     *  2307 - assertIsTrue  "username@yahoo.corporate.in"                                          =   0 =  OK 
     *  2308 - assertIsTrue  "username@yahoo.corporate"                                             =   0 =  OK 
     *  2309 - assertIsFalse "username@yahoo.com."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2310 - assertIsFalse "username@yahoo.c"                                                     =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2311 - assertIsFalse "username@yahoo..com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2312 - assertIsFalse "user#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2313 - assertIsFalse "@yahoo.com"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2314 - assertIsFalse ".username@yahoo.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2315 - assertIsTrue  "abc@abc.com.com.com.com"                                              =   0 =  OK 
     *  2316 - assertIsTrue  "abc@abc.co.in"                                                        =   0 =  OK 
     *  2317 - assertIsTrue  "abc@abc.abc"                                                          =   0 =  OK 
     *  2318 - assertIsTrue  "abc@abc.abcde"                                                        =   0 =  OK 
     *  2319 - assertIsTrue  "abc@abc.abcd"                                                         =   0 =  OK 
     *  2320 - assertIsTrue  "abc.efg@gmail.com"                                                    =   0 =  OK 
     *  2321 - assertIsTrue  "abc@gmail.com.my"                                                     =   0 =  OK 
     *  2322 - assertIsFalse "abc@gmail..com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2323 - assertIsFalse "abc@gmail.com.."                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2324 - assertIsFalse "No -foo@bar.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2325 - assertIsFalse "No asd@-bar.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2326 - assertIsFalse ".username@yahoo.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2327 - assertIsTrue  "a&d@somedomain.com"                                                   =   0 =  OK 
     *  2328 - assertIsTrue  "a*d@somedomain.com"                                                   =   0 =  OK 
     *  2329 - assertIsTrue  "a/d@somedomain.com"                                                   =   0 =  OK 
     *  2330 - assertIsTrue  "\"a@b\"@example.com"                                                  =   1 =  OK 
     *  2331 - assertIsFalse ".abc@somedomain.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2332 - assertIsFalse "abc.@somedomain.com"                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2333 - assertIsFalse "a>b@somedomain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2334 - assertIsTrue  "me+alpha@example.com"                                                 =   0 =  OK 
     *  2335 - assertIsTrue  "o'hare@example.com"                                                   =   0 =  OK 
     *  2336 - assertIsTrue  "me@mail.s2.example.com"                                               =   0 =  OK 
     *  2337 - assertIsFalse "{something}@{something}.{something}"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2338 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                               =   0 =  OK 
     *  2339 - assertIsTrue  "This is <john@127.0.0.1>"                                             =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2340 - assertIsTrue  "This is <john@[127.0.0.1]>"                                           =   2 =  OK 
     *  2341 - assertIsTrue  "john.doe@example.com"                                                 =   0 =  OK 
     *  2342 - assertIsTrue  "john.o'doe@example.com"                                               =   0 =  OK 
     *  2343 - assertIsTrue  "John <john@doe.com>"                                                  =   0 =  OK 
     *  2344 - assertIsTrue  "<john@doe.com>"                                                       =   0 =  OK 
     *  2345 - assertIsTrue  "a_z%@gmail.com"                                                       =   0 =  OK 
     *  2346 - assertIsTrue  "a__z@provider.com"                                                    =   0 =  OK 
     *  2347 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                            =   0 =  OK 
     *  2348 - assertIsTrue  "me@aaronsw.com"                                                       =   0 =  OK 
     *  2349 - assertIsTrue  "my.ownsite@ourearth.org"                                              =   0 =  OK 
     *  2350 - assertIsFalse "myemail@@sample.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2351 - assertIsFalse "myemail@sa@mple.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2352 - assertIsTrue  "myemail@sample"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2353 - assertIsTrue  "myemail@sample.com"                                                   =   0 =  OK 
     *  2354 - assertIsFalse "myemailsample.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2355 - assertIsTrue  "mysite@you.me.net"                                                    =   0 =  OK 
     *  2356 - assertIsFalse "ote\"@example.com"                                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2357 - assertIsTrue  "peter.example@yahoo.com.au"                                           =   0 =  OK 
     *  2358 - assertIsTrue  "peter.piper@example.com"                                              =   0 =  OK 
     *  2359 - assertIsTrue  "peter_123@news.com"                                                   =   0 =  OK 
     *  2360 - assertIsTrue  "pio^_pio@factory.com"                                                 =   0 =  OK 
     *  2361 - assertIsTrue  "pio_#pio@factory.com"                                                 =   0 =  OK 
     *  2362 - assertIsFalse "pio_pio@#factory.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2363 - assertIsFalse "pio_pio@factory.c#om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2364 - assertIsFalse "pio_pio@factory.c*om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2365 - assertIsTrue  "pio_pio@factory.com"                                                  =   0 =  OK 
     *  2366 - assertIsTrue  "pio_~pio@factory.com"                                                 =   0 =  OK 
     *  2367 - assertIsTrue  "piskvor@example.lighting"                                             =   0 =  OK 
     *  2368 - assertIsFalse "plain.address"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2369 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2370 - assertIsTrue  "rss-dev@yahoogroups.com"                                              =   0 =  OK 
     *  2371 - assertIsTrue  "someone+tag@somewhere.net"                                            =   0 =  OK 
     *  2372 - assertIsTrue  "someone@somewhere.co.uk"                                              =   0 =  OK 
     *  2373 - assertIsTrue  "someone@somewhere.com"                                                =   0 =  OK 
     *  2374 - assertIsTrue  "something_valid@somewhere.tld"                                        =   0 =  OK 
     *  2375 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2376 - assertIsFalse "this\ still\\"not\allowed@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2377 - assertIsTrue  "tvf@tvf.cz"                                                           =   0 =  OK 
     *  2378 - assertIsFalse "two..dot@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2379 - assertIsTrue  "user+mailbox@example.com"                                             =   0 =  OK 
     *  2380 - assertIsTrue  "vdv@dyomedea.com"                                                     =   0 =  OK 
     *  2381 - assertIsFalse "xxxx..1234@yahoo.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2382 - assertIsFalse "xxxx.ourearth.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2383 - assertIsFalse "xxxx123@gmail.b"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2384 - assertIsFalse "xxxx@.com.my"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2385 - assertIsFalse "xxxx@.org.org"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2386 - assertIsTrue  "xxxx@gmail.com"                                                       =   0 =  OK 
     *  2387 - assertIsFalse "xxxxx()*@gmail.com"                                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2388 - assertIsTrue  "xxxxxx@yahoo.com"                                                     =   0 =  OK 
     *  2389 - assertIsFalse "<')))><@fish.left.com"                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2390 - assertIsFalse "><(((*>@fish.right.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2391 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2392 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2393 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2394 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  2395 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  2396 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2397 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"       =   0 =  OK 
     *  2398 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  2399 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                              =   4 =  OK 
     *  2400 - assertIsTrue  "user@localserver"                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2401 - assertIsTrue  "w.b.f@test.com"                                                       =   0 =  OK 
     *  2402 - assertIsTrue  "w.b.f@test.museum"                                                    =   0 =  OK 
     *  2403 - assertIsTrue  "yoursite@ourearth.com"                                                =   0 =  OK 
     *  2404 - assertIsTrue  "~pio_pio@factory.com"                                                 =   0 =  OK 
     *  2405 - assertIsTrue  "valid@[1.1.1.1]"                                                      =   2 =  OK 
     *  2406 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2407 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                               =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2408 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                      =   4 =  OK 
     *  2409 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"       =   4 =  OK 
     *  2410 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                            =   4 =  OK 
     *  2411 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"               =   4 =  OK 
     *  2412 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                 =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2413 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                          =   4 =  OK 
     *  2414 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                          =   4 =  OK 
     *  2415 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                    =   2 =  OK 
     *  2416 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                        =   2 =  OK 
     *  2417 - assertIsFalse "invalid@[10]"                                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2418 - assertIsFalse "invalid@[10.1]"                                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2419 - assertIsFalse "invalid@[10.1.52]"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2420 - assertIsFalse "invalid@[256.256.256.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2421 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2422 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2423 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2424 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2425 - assertIsFalse "invalid@domain1.com@domain2.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2426 - assertIsFalse "\"locál-part\"@example.com"                                           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2427 - assertIsFalse "invalid@[IPv6:1::2:]"                                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2428 - assertIsFalse "invalid@[IPv6::1::1]"                                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2429 - assertIsFalse "invalid@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2430 - assertIsFalse "invalid@[111.111.111.111"                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2431 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2432 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2433 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2434 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2435 - assertIsFalse "invalid@[IPv6:1111:1111]"                                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2436 - assertIsFalse "\"invalid-qstring@example.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     *  2437 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                           =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2438 - assertIsTrue  "\"back\slash\"@sld.com"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2439 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                          =   1 =  OK 
     *  2440 - assertIsTrue  "\"quoted\"@sld.com"                                                   =   1 =  OK 
     *  2441 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                   =   1 =  OK 
     *  2442 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                   =   0 =  OK 
     *  2443 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                  =   0 =  OK 
     *  2444 - assertIsTrue  "01234567890@numbers-in-local.net"                                     =   0 =  OK 
     *  2445 - assertIsTrue  "a@single-character-in-local.org"                                      =   0 =  OK 
     *  2446 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  2447 - assertIsTrue  "backticksarelegit@test.com"                                           =   0 =  OK 
     *  2448 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                           =   2 =  OK 
     *  2449 - assertIsTrue  "country-code-tld@sld.rw"                                              =   0 =  OK 
     *  2450 - assertIsTrue  "country-code-tld@sld.uk"                                              =   0 =  OK 
     *  2451 - assertIsTrue  "letters-in-sld@123.com"                                               =   0 =  OK 
     *  2452 - assertIsTrue  "local@dash-in-sld.com"                                                =   0 =  OK 
     *  2453 - assertIsTrue  "local@sld.newTLD"                                                     =   0 =  OK 
     *  2454 - assertIsTrue  "local@sub.domains.com"                                                =   0 =  OK 
     *  2455 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                     =   0 =  OK 
     *  2456 - assertIsTrue  "one-character-third-level@a.example.com"                              =   0 =  OK 
     *  2457 - assertIsTrue  "one-letter-sld@x.org"                                                 =   0 =  OK 
     *  2458 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                             =   0 =  OK 
     *  2459 - assertIsTrue  "single-character-in-sld@x.org"                                        =   0 =  OK 
     *  2460 - assertIsTrue  "uncommon-tld@sld.mobi"                                                =   0 =  OK 
     *  2461 - assertIsTrue  "uncommon-tld@sld.museum"                                              =   0 =  OK 
     *  2462 - assertIsTrue  "uncommon-tld@sld.travel"                                              =   0 =  OK 
     *  2463 - assertIsFalse "invalid"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2464 - assertIsFalse "invalid@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2465 - assertIsFalse "invalid @"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2466 - assertIsFalse "invalid@[555.666.777.888]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2467 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2468 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2469 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2470 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2471 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2472 - assertIsFalse "@missing-local.org"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2473 - assertIsFalse "IP-and-port@127.0.0.1:25"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2474 - assertIsFalse "another-invalid-ip@127.0.0.256"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2475 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2476 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2477 - assertIsFalse "invalid-ip@127.0.0.1.26"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2478 - assertIsFalse "local-ends-with-dot.@sld.com"                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2479 - assertIsFalse "missing-at-sign.net"                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2480 - assertIsFalse "missing-sld@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2481 - assertIsFalse "missing-tld@sld."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2482 - assertIsFalse "sld-ends-with-dash@sld-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2483 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2484 - assertIsFalse "two..consecutive-dots@sld.com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2485 - assertIsFalse "unbracketed-IP@127.0.0.1"                                             =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2486 - assertIsFalse "underscore.error@example.com_"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     *  2487 - assertIsTrue  "first.last@iana.org"                                                  =   0 =  OK 
     *  2488 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  2489 - assertIsTrue  "\"first\\"last\"@iana.org"                                            =   1 =  OK 
     *  2490 - assertIsTrue  "\"first@last\"@iana.org"                                              =   1 =  OK 
     *  2491 - assertIsTrue  "\"first\\last\"@iana.org"                                             =   1 =  OK 
     *  2492 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  2493 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  2494 - assertIsTrue  "first.last@[12.34.56.78]"                                             =   2 =  OK 
     *  2495 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                    =   4 =  OK 
     *  2496 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                     =   4 =  OK 
     *  2497 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                    =   4 =  OK 
     *  2498 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                    =   4 =  OK 
     *  2499 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"            =   4 =  OK 
     *  2500 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  2501 - assertIsTrue  "first.last@3com.com"                                                  =   0 =  OK 
     *  2502 - assertIsTrue  "first.last@123.iana.org"                                              =   0 =  OK 
     *  2503 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2504 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                =   4 =  OK 
     *  2505 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                =   1 =  OK 
     *  2506 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                            =   1 =  OK 
     *  2507 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                              =   1 =  OK 
     *  2508 - assertIsTrue  "\"Abc@def\"@iana.org"                                                 =   1 =  OK 
     *  2509 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                           =   1 =  OK 
     *  2510 - assertIsTrue  "user+mailbox@iana.org"                                                =   0 =  OK 
     *  2511 - assertIsTrue  "$A12345@iana.org"                                                     =   0 =  OK 
     *  2512 - assertIsTrue  "!def!xyz%abc@iana.org"                                                =   0 =  OK 
     *  2513 - assertIsTrue  "_somename@iana.org"                                                   =   0 =  OK 
     *  2514 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2515 - assertIsTrue  "peter.piper@iana.org"                                                 =   0 =  OK 
     *  2516 - assertIsTrue  "test@iana.org"                                                        =   0 =  OK 
     *  2517 - assertIsTrue  "TEST@iana.org"                                                        =   0 =  OK 
     *  2518 - assertIsTrue  "1234567890@iana.org"                                                  =   0 =  OK 
     *  2519 - assertIsTrue  "test+test@iana.org"                                                   =   0 =  OK 
     *  2520 - assertIsTrue  "test-test@iana.org"                                                   =   0 =  OK 
     *  2521 - assertIsTrue  "t*est@iana.org"                                                       =   0 =  OK 
     *  2522 - assertIsTrue  "+1~1+@iana.org"                                                       =   0 =  OK 
     *  2523 - assertIsTrue  "{_test_}@iana.org"                                                    =   0 =  OK 
     *  2524 - assertIsTrue  "test.test@iana.org"                                                   =   0 =  OK 
     *  2525 - assertIsTrue  "\"test.test\"@iana.org"                                               =   1 =  OK 
     *  2526 - assertIsTrue  "test.\"test\"@iana.org"                                               =   1 =  OK 
     *  2527 - assertIsTrue  "\"test@test\"@iana.org"                                               =   1 =  OK 
     *  2528 - assertIsTrue  "test@123.123.123.x123"                                                =   0 =  OK 
     *  2529 - assertIsFalse "test@123.123.123.123"                                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2530 - assertIsTrue  "test@[123.123.123.123]"                                               =   2 =  OK 
     *  2531 - assertIsTrue  "test@example.iana.org"                                                =   0 =  OK 
     *  2532 - assertIsTrue  "test@example.example.iana.org"                                        =   0 =  OK 
     *  2533 - assertIsTrue  "customer/department@iana.org"                                         =   0 =  OK 
     *  2534 - assertIsTrue  "_Yosemite.Sam@iana.org"                                               =   0 =  OK 
     *  2535 - assertIsTrue  "~@iana.org"                                                           =   0 =  OK 
     *  2536 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                           =   1 =  OK 
     *  2537 - assertIsTrue  "Ima.Fool@iana.org"                                                    =   0 =  OK 
     *  2538 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                =   1 =  OK 
     *  2539 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                              =   1 =  OK 
     *  2540 - assertIsTrue  "\"first\".\"last\"@iana.org"                                          =   1 =  OK 
     *  2541 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                   =   1 =  OK 
     *  2542 - assertIsTrue  "\"first\".last@iana.org"                                              =   1 =  OK 
     *  2543 - assertIsTrue  "first.\"last\"@iana.org"                                              =   1 =  OK 
     *  2544 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                               =   1 =  OK 
     *  2545 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2546 - assertIsTrue  "\"first.middle.last\"@iana.org"                                       =   1 =  OK 
     *  2547 - assertIsTrue  "\"first..last\"@iana.org"                                             =   1 =  OK 
     *  2548 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2549 - assertIsFalse "first.last @iana.orgin"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2550 - assertIsTrue  "\"test blah\"@iana.orgin"                                             =   1 =  OK 
     *  2551 - assertIsTrue  "name.lastname@domain.com"                                             =   0 =  OK 
     *  2552 - assertIsTrue  "a@bar.com"                                                            =   0 =  OK 
     *  2553 - assertIsTrue  "aaa@[123.123.123.123]"                                                =   2 =  OK 
     *  2554 - assertIsTrue  "a-b@bar.com"                                                          =   0 =  OK 
     *  2555 - assertIsFalse "+@b.c"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2556 - assertIsTrue  "+@b.com"                                                              =   0 =  OK 
     *  2557 - assertIsTrue  "a@b.co-foo.uk"                                                        =   0 =  OK 
     *  2558 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                   =   1 =  OK 
     *  2559 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                   =   1 =  OK 
     *  2560 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                     =   0 =  OK 
     *  2561 - assertIsFalse "foobar@192.168.0.1"                                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2562 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                    =   6 =  OK 
     *  2563 - assertIsTrue  "user%uucp!path@berkeley.edu"                                          =   0 =  OK 
     *  2564 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                            =   0 =  OK 
     *  2565 - assertIsTrue  "test@test.com"                                                        =   0 =  OK 
     *  2566 - assertIsTrue  "test@xn--example.com"                                                 =   0 =  OK 
     *  2567 - assertIsTrue  "test@example.com"                                                     =   0 =  OK 
     *  2568 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2569 - assertIsTrue  "first\@last@iana.org"                                                 =   0 =  OK 
     *  2570 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                             =   0 =  OK 
     *  2571 - assertIsFalse "first.last@example.123"                                               =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2572 - assertIsFalse "first.last@comin"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2573 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                            =   1 =  OK 
     *  2574 - assertIsTrue  "Abc\@def@iana.org"                                                    =   0 =  OK 
     *  2575 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                =   0 =  OK 
     *  2576 - assertIsFalse "Joe.\Blow@iana.org"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2577 - assertIsTrue  "first.last@sub.do.com"                                                =   0 =  OK 
     *  2578 - assertIsFalse "first.last"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2579 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                      =   0 =  OK 
     *  2580 - assertIsTrue  "\"hello world\"@example.com"                                          =   1 =  OK 
     *  2581 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2582 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                         =   1 =  OK 
     *  2583 - assertIsTrue  "example+tag@gmail.com"                                                =   0 =  OK 
     *  2584 - assertIsFalse ".ann..other.@example.com"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2585 - assertIsTrue  "ann.other@example.com"                                                =   0 =  OK 
     *  2586 - assertIsTrue  "something@something.something"                                        =   0 =  OK 
     *  2587 - assertIsTrue  "c@(Chris's host.)public.examplein"                                    =   6 =  OK 
     *  2588 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2589 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2590 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2591 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2592 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2593 - assertIsFalse "first().last@iana.orgin"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2594 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2595 - assertIsFalse "jdoe@machine(comment). examplein"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2596 - assertIsFalse "first(abc.def).last@iana.orgin"                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2597 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2598 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2599 - assertIsFalse "first(abc\(def)@iana.orgin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2600 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2601 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2602 - assertIsFalse "1234 @ local(blah) .machine .examplein"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2603 - assertIsFalse "a@bin"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2604 - assertIsFalse "a@barin"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2605 - assertIsFalse "@about.museum"                                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2606 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2607 - assertIsFalse ".first.last@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2608 - assertIsFalse "first.last.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2609 - assertIsFalse "first..last@iana.org"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2610 - assertIsFalse "\"first\"last\"@iana.org"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2611 - assertIsFalse "first.last@"                                                          =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2612 - assertIsFalse "first.last@-xample.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2613 - assertIsFalse "first.last@exampl-.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2614 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2615 - assertIsFalse "abc\@iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2616 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2617 - assertIsFalse "abc@def@iana.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2618 - assertIsFalse "@iana.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2619 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2620 - assertIsFalse "\"qu@iana.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2621 - assertIsFalse "ote\"@iana.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2622 - assertIsFalse ".dot@iana.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2623 - assertIsFalse "dot.@iana.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2624 - assertIsFalse "two..dot@iana.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2625 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2626 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2627 - assertIsFalse "hello world@iana.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2628 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2629 - assertIsFalse "test.iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2630 - assertIsFalse "test.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2631 - assertIsFalse "test..test@iana.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2632 - assertIsFalse ".test@iana.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2633 - assertIsFalse "test@test@iana.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2634 - assertIsFalse "test@@iana.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2635 - assertIsFalse "-- test --@iana.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2636 - assertIsFalse "[test]@iana.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2637 - assertIsFalse "\"test\"test\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2638 - assertIsFalse "()[]\;:.><@iana.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2639 - assertIsFalse "test@."                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2640 - assertIsFalse "test@example."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2641 - assertIsFalse "test@.org"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2642 - assertIsFalse "test@[123.123.123.123"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2643 - assertIsFalse "test@123.123.123.123]"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2644 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2645 - assertIsFalse "@NotAnEmail"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2646 - assertIsFalse "\"test\"blah\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2647 - assertIsFalse ".wooly@iana.org"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2648 - assertIsFalse "wo..oly@iana.org"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2649 - assertIsFalse "pootietang.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2650 - assertIsFalse ".@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2651 - assertIsFalse "Ima Fool@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2652 - assertIsFalse "foo@[\1.2.3.4]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2653 - assertIsFalse "first.\"\".last@iana.org"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2654 - assertIsFalse "first\last@iana.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2655 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2656 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2657 - assertIsFalse "cal(foo(bar)@iamcal.com"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2658 - assertIsFalse "cal(foo)bar)@iamcal.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2659 - assertIsFalse "cal(foo\)@iamcal.com"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2660 - assertIsFalse "first(middle)last@iana.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2661 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2662 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2663 - assertIsFalse ".@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2664 - assertIsFalse "@bar.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2665 - assertIsFalse "@@bar.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2666 - assertIsFalse "aaa.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2667 - assertIsFalse "aaa@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2668 - assertIsFalse "aaa@.123"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2669 - assertIsFalse "aaa@[123.123.123.123]a"                                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2670 - assertIsFalse "aaa@[123.123.123.333]"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2671 - assertIsFalse "a@bar.com."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2672 - assertIsFalse "a@-b.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2673 - assertIsFalse "a@b-.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2674 - assertIsFalse "-@..com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2675 - assertIsFalse "-@a..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2676 - assertIsFalse "@about.museum-"                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2677 - assertIsFalse "test@...........com"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2678 - assertIsFalse "first.last@[IPv6::]"                                                  =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2679 - assertIsFalse "first.last@[IPv6::::]"                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2680 - assertIsFalse "first.last@[IPv6::b4]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2681 - assertIsFalse "first.last@[IPv6::::b4]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2682 - assertIsFalse "first.last@[IPv6::b3:b4]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2683 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2684 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2685 - assertIsFalse "first.last@[IPv6:a1:]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2686 - assertIsFalse "first.last@[IPv6:a1:::]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2687 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2688 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2689 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2690 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2691 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2692 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2693 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2694 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2695 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                  =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2696 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2697 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2698 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2699 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                  =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  2700 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2701 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2702 - assertIsFalse "first.last@[IPv6::a2::b4]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2703 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2704 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2705 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2706 - assertIsFalse "first.last@[.12.34.56.78]"                                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2707 - assertIsFalse "first.last@[12.34.56.789]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2708 - assertIsFalse "first.last@[::12.34.56.78]"                                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2709 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2710 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                      =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  2711 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2712 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2713 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2714 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2715 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2716 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2717 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2718 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2719 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2720 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2721 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2722 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2723 - assertIsTrue  "first.last@[IPv6:::]"                                                 =   4 =  OK 
     *  2724 - assertIsTrue  "first.last@[IPv6:::b4]"                                               =   4 =  OK 
     *  2725 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                            =   4 =  OK 
     *  2726 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                             =   4 =  OK 
     *  2727 - assertIsTrue  "first.last@[IPv6:a1::]"                                               =   4 =  OK 
     *  2728 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                            =   4 =  OK 
     *  2729 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                              =   4 =  OK 
     *  2730 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                              =   4 =  OK 
     *  2731 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2732 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2733 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2734 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2735 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                    =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2736 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2737 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2738 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2739 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2740 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  OK 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     *  2741 - assertIsFalse "\"qu@test.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2742 - assertIsFalse "ote\"@test.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2743 - assertIsFalse "\"().:;<>[\]@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2744 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2745 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2746 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2747 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2748 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2749 - assertIsFalse "this\ still\"not\allowed@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2750 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2751 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2752 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2753 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2754 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2755 - assertIsFalse ".email@example.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2756 - assertIsFalse "email.@example.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2757 - assertIsFalse "email..email@example.com"                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2758 - assertIsFalse "email@-example.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2759 - assertIsFalse "email@111.222.333.44444"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2760 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2761 - assertIsFalse "email@[12.34.44.56"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2762 - assertIsFalse "email@14.44.56.34]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2763 - assertIsFalse "email@[1.1.23.5f]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2764 - assertIsFalse "email@[3.256.255.23]"                                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2765 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2766 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2767 - assertIsFalse "first\@last@iana.org"                                                 =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2768 - assertIsFalse "test@example.com "                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2769 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2770 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2771 - assertIsFalse "invalid@about.museum-"                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2772 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2773 - assertIsFalse "abc@def@test.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2774 - assertIsTrue  "abc\@def@test.org"                                                    =   0 =  OK 
     *  2775 - assertIsFalse "abc\@test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2776 - assertIsFalse "@test.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2777 - assertIsFalse ".dot@test.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2778 - assertIsFalse "dot.@test.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2779 - assertIsFalse "two..dot@test.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2780 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2781 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2782 - assertIsFalse "hello world@test.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2783 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2784 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2785 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2786 - assertIsFalse "test.test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2787 - assertIsFalse "test.@test.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2788 - assertIsFalse "test..test@test.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2789 - assertIsFalse ".test@test.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2790 - assertIsFalse "test@test@test.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2791 - assertIsFalse "test@@test.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2792 - assertIsFalse "-- test --@test.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2793 - assertIsFalse "[test]@test.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2794 - assertIsFalse "\"test\"test\"@test.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2795 - assertIsFalse "()[]\;:.><@test.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2796 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2797 - assertIsFalse ".@test.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2798 - assertIsFalse "Ima Fool@test.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2799 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2800 - assertIsFalse "foo@[.2.3.4]"                                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2801 - assertIsFalse "first\last@test.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2802 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2803 - assertIsFalse "first(middle)last@test.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2804 - assertIsFalse "\"test\"test@test.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2805 - assertIsFalse "()@test.com"                                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  2806 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  2807 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2808 - assertIsFalse "invalid@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2809 - assertIsFalse "ä📧@-foo"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2810 - assertIsFalse "ä📧@foo-"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2811 - assertIsFalse "first(comment(inner@comment.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2812 - assertIsFalse "Joe A Smith <email@example.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2813 - assertIsFalse "Joe A Smith email@example.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2814 - assertIsFalse "Joe A Smith <email@example.com->"                                     =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2815 - assertIsFalse "Joe A Smith <email@-example.com->"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2816 - assertIsFalse "Joe A Smith <email>"                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2817 - assertIsTrue  "\"email\"@example.com"                                                =   1 =  OK 
     *  2818 - assertIsTrue  "\"first@last\"@test.org"                                              =   1 =  OK 
     *  2819 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                           =   1 =  OK 
     *  2820 - assertIsTrue  "\"first\"last\"@test.org"                                             =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2821 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                   =   1 =  OK 
     *  2822 - assertIsTrue  "\"first\last\"@test.org"                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2823 - assertIsTrue  "\"Abc\@def\"@test.org"                                                =   1 =  OK 
     *  2824 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                            =   1 =  OK 
     *  2825 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2826 - assertIsTrue  "\"Abc@def\"@test.org"                                                 =   1 =  OK 
     *  2827 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                             =   1 =  OK 
     *  2828 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2829 - assertIsTrue  "\"[[ test ]]\"@test.org"                                              =   1 =  OK 
     *  2830 - assertIsTrue  "\"test.test\"@test.org"                                               =   1 =  OK 
     *  2831 - assertIsTrue  "test.\"test\"@test.org"                                               =   1 =  OK 
     *  2832 - assertIsTrue  "\"test@test\"@test.org"                                               =   1 =  OK 
     *  2833 - assertIsTrue  "\"test  est\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2834 - assertIsTrue  "\"first\".\"last\"@test.org"                                          =   1 =  OK 
     *  2835 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                   =   1 =  OK 
     *  2836 - assertIsTrue  "\"first\".last@test.org"                                              =   1 =  OK 
     *  2837 - assertIsTrue  "first.\"last\"@test.org"                                              =   1 =  OK 
     *  2838 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                               =   1 =  OK 
     *  2839 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                   =   1 =  OK 
     *  2840 - assertIsTrue  "\"first.middle.last\"@test.org"                                       =   1 =  OK 
     *  2841 - assertIsTrue  "\"first..last\"@test.org"                                             =   1 =  OK 
     *  2842 - assertIsTrue  "\"Unicode NULL \"@char.com"                                           =   1 =  OK 
     *  2843 - assertIsTrue  "\"test\blah\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2844 - assertIsTrue  "\"testlah\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2845 - assertIsTrue  "\"test\"blah\"@test.org"                                              =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2846 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2847 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                     =   1 =  OK 
     *  2848 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2849 - assertIsTrue  "\"test blah\"@test.org"                                               =   1 =  OK 
     *  2850 - assertIsTrue  "first.last@test.org"                                                  =   0 =  OK 
     *  2851 - assertIsFalse "jdoe@machine(comment).example"                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2852 - assertIsTrue  "first.\"\".last@test.org"                                             =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2853 - assertIsTrue  "\"\"@test.org"                                                        =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2854 - assertIsTrue  "very.common@example.org"                                              =   0 =  OK 
     *  2855 - assertIsTrue  "test/test@test.com"                                                   =   0 =  OK 
     *  2856 - assertIsTrue  "user-@example.org"                                                    =   0 =  OK 
     *  2857 - assertIsTrue  "firstname.lastname@example.com"                                       =   0 =  OK 
     *  2858 - assertIsTrue  "email@subdomain.example.com"                                          =   0 =  OK 
     *  2859 - assertIsTrue  "firstname+lastname@example.com"                                       =   0 =  OK 
     *  2860 - assertIsTrue  "1234567890@example.com"                                               =   0 =  OK 
     *  2861 - assertIsTrue  "email@example-one.com"                                                =   0 =  OK 
     *  2862 - assertIsTrue  "_______@example.com"                                                  =   0 =  OK 
     *  2863 - assertIsTrue  "email@example.name"                                                   =   0 =  OK 
     *  2864 - assertIsTrue  "email@example.museum"                                                 =   0 =  OK 
     *  2865 - assertIsTrue  "email@example.co.jp"                                                  =   0 =  OK 
     *  2866 - assertIsTrue  "firstname-lastname@example.com"                                       =   0 =  OK 
     *  2867 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  2868 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2869 - assertIsTrue  "first.last@123.test.org"                                              =   0 =  OK 
     *  2870 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2871 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  2872 - assertIsTrue  "user+mailbox@test.org"                                                =   0 =  OK 
     *  2873 - assertIsTrue  "customer/department=shipping@test.org"                                =   0 =  OK 
     *  2874 - assertIsTrue  "$A12345@test.org"                                                     =   0 =  OK 
     *  2875 - assertIsTrue  "!def!xyz%abc@test.org"                                                =   0 =  OK 
     *  2876 - assertIsTrue  "_somename@test.org"                                                   =   0 =  OK 
     *  2877 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2878 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2879 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2880 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2881 - assertIsTrue  "+@b.c"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2882 - assertIsTrue  "TEST@test.org"                                                        =   0 =  OK 
     *  2883 - assertIsTrue  "1234567890@test.org"                                                  =   0 =  OK 
     *  2884 - assertIsTrue  "test-test@test.org"                                                   =   0 =  OK 
     *  2885 - assertIsTrue  "t*est@test.org"                                                       =   0 =  OK 
     *  2886 - assertIsTrue  "+1~1+@test.org"                                                       =   0 =  OK 
     *  2887 - assertIsTrue  "{_test_}@test.org"                                                    =   0 =  OK 
     *  2888 - assertIsTrue  "valid@about.museum"                                                   =   0 =  OK 
     *  2889 - assertIsTrue  "a@bar"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2890 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2891 - assertIsTrue  "(comment)test@test.org"                                               =   6 =  OK 
     *  2892 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2893 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2894 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2895 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"   =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2896 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2897 - assertIsTrue  "first(abc\(def)@test.org"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2898 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                     =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2899 - assertIsTrue  "c@(Chris's host.)public.example"                                      =   6 =  OK 
     *  2900 - assertIsTrue  "_Yosemite.Sam@test.org"                                               =   0 =  OK 
     *  2901 - assertIsTrue  "~@test.org"                                                           =   0 =  OK 
     *  2902 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                        =   6 =  OK 
     *  2903 - assertIsTrue  "test@Bücher.ch"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2904 - assertIsTrue  "あいうえお@example.com"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2905 - assertIsTrue  "Pelé@example.com"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2906 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2907 - assertIsTrue  "我買@屋企.香港"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2908 - assertIsTrue  "二ノ宮@黒川.日本"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2909 - assertIsTrue  "медведь@с-балалайкой.рф"                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2910 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2911 - assertIsTrue  "email@example.com (Joe Smith)"                                        =   6 =  OK 
     *  2912 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                             = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2913 - assertIsTrue  "first(abc.def).last@test.org"                                         = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2914 - assertIsTrue  "first(a\"bc.def).last@test.org"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2915 - assertIsTrue  "first.(\")middle.last(\")@test.org"                                   = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2916 - assertIsTrue  "first().last@test.org"                                                = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2917 - assertIsTrue  "mymail\@hello@hotmail.com"                                            =   0 =  OK 
     *  2918 - assertIsTrue  "Abc\@def@test.org"                                                    =   0 =  OK 
     *  2919 - assertIsTrue  "Fred\ Bloggs@test.org"                                                =   0 =  OK 
     *  2920 - assertIsTrue  "Joe.\\Blow@test.org"                                                  =   0 =  OK 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ----------------------------------------------------------------------------------------------------
     * 
     *  2921 - assertIsTrue  "me@example.com"                                                       =   0 =  OK 
     *  2922 - assertIsTrue  "a.nonymous@example.com"                                               =   0 =  OK 
     *  2923 - assertIsTrue  "name+tag@example.com"                                                 =   0 =  OK 
     *  2924 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                       =   2 =  OK 
     *  2925 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"    =   4 =  OK 
     *  2926 - assertIsTrue  "me(this is a comment)@example.com"                                    =   6 =  OK 
     *  2927 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                            =   1 =  OK 
     *  2928 - assertIsTrue  "me.example@com"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2929 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                      =   0 =  OK 
     *  2930 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"  =   0 =  OK 
     *  2931 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                     =   0 =  OK 
     *  2932 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                    =   0 =  OK 
     *  2933 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                   =   0 =  OK 
     *  2934 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                  =   0 =  OK 
     *  2935 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2936 - assertIsFalse "me@"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2937 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2938 - assertIsFalse ".me@example.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2939 - assertIsFalse "me@example..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2940 - assertIsFalse "me\@example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2941 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2942 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2943 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2944 - assertIsFalse "semico...@gmail.com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- my old tests ----------------------------------------------------------------------------------------------------
     * 
     *  2945 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  2946 - assertIsTrue  "A.\"B\"@C.DE"                                                         =   1 =  OK 
     *  2947 - assertIsTrue  "A.B@[1.2.3.4]"                                                        =   2 =  OK 
     *  2948 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                    =   3 =  OK 
     *  2949 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                           =   4 =  OK 
     *  2950 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                       =   5 =  OK 
     *  2951 - assertIsTrue  "(A)B@C.DE"                                                            =   6 =  OK 
     *  2952 - assertIsTrue  "A(B)@C.DE"                                                            =   6 =  OK 
     *  2953 - assertIsTrue  "(A)\"B\"@C.DE"                                                        =   7 =  OK 
     *  2954 - assertIsTrue  "\"A\"(B)@C.DE"                                                        =   7 =  OK 
     *  2955 - assertIsTrue  "(A)B@[1.2.3.4]"                                                       =   2 =  OK 
     *  2956 - assertIsTrue  "A(B)@[1.2.3.4]"                                                       =   2 =  OK 
     *  2957 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                   =   8 =  OK 
     *  2958 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                   =   8 =  OK 
     *  2959 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  2960 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  2961 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *  2962 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *  2963 - assertIsTrue  "a.b.c.d@domain.com"                                                   =   0 =  OK 
     *  2964 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2965 - assertIsFalse "ABC.DEF.GHI.JKL"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2966 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2967 - assertIsFalse "ABC.DEF @GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2968 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2969 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2970 - assertIsFalse "ABC.DEF@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2971 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2972 - assertIsFalse "ABC@DEF@GHI.JKL"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2973 - assertIsFalse "@%^%#$@#$@#.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2974 - assertIsFalse "email.domain.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2975 - assertIsFalse "email@domain@domain.com"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2976 - assertIsFalse "first@last@test.org"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2977 - assertIsFalse "@test@a.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2978 - assertIsFalse "@\"someStringThatMightBe@email.com"                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2979 - assertIsFalse "test@@test.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2980 - assertIsFalse "ABCDEF@GHIJKL"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2981 - assertIsFalse "ABC.DEF@GHIJKL"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2982 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2983 - assertIsFalse "ABC.DEF@GHI.JKL."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2984 - assertIsFalse "ABC..DEF@GHI.JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2985 - assertIsFalse "ABC.DEF@GHI..JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2986 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2987 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2988 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2989 - assertIsFalse "ABC.DEF@."                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2990 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                 =   1 =  OK 
     *  2991 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                  =   0 =  OK 
     *  2992 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                     =   0 =  OK 
     *  2993 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                     =   0 =  OK 
     *  2994 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                      =   0 =  OK 
     *  2995 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                      =   0 =  OK 
     *  2996 - assertIsFalse "ABC.DEF@GHI.2KL"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2997 - assertIsFalse "ABC.DEF@GHI.JK-"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2998 - assertIsFalse "ABC.DEF@GHI.JK_"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2999 - assertIsFalse "ABC.DEF@-HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3000 - assertIsFalse "ABC.DEF@_HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3001 - assertIsFalse "ABC DEF@GHI.DE"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3002 - assertIsFalse "ABC.DEF@GHI DE"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3003 - assertIsFalse "A . B & C . D"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3004 - assertIsFalse " A . B & C . D"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3005 - assertIsFalse "(?).[!]@{&}.<:>"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- some more Testcases ----------------------------------------------------------------------------------------------------
     * 
     *  3006 - assertIsFalse "\"\".local.name.starts.with.empty.string1@domain.com"                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3007 - assertIsFalse "local.name.ends.with.empty.string1\"\"@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3008 - assertIsFalse "local.name.with.empty.string1\"\"character@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3009 - assertIsFalse "local.name.with.empty.string1.before\"\".point@domain.com"            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3010 - assertIsFalse "local.name.with.empty.string1.after.\"\"point@domain.com"             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3011 - assertIsFalse "local.name.with.double.empty.string1\"\"\"\"test@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3012 - assertIsFalse "(comment \"\") local.name.with.comment.with.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3013 - assertIsFalse "\"quote\"\"\".local.name.with.qoute.with.empty.string1@domain.com"    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3014 - assertIsFalse "\"\"@empty.string1.domain.com"                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3015 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@empty.string1.domain.com"                    =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3016 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3017 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1@domain.com>"           =   0 =  OK 
     *  3018 - assertIsTrue  "<pointy.brackets2.with.empty.string1@domain.com> name \"\""           =   0 =  OK 
     *  3019 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3020 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3021 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3022 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3023 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3024 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3025 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3026 - assertIsFalse "domain.part.with.comment.with.empty.string1@(comment \"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3027 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3028 - assertIsFalse "ip.v4.with.empty.string1@[123.14\"\"5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3029 - assertIsFalse "ip.v4.with.empty.string1@[123.145\"\".178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3030 - assertIsFalse "ip.v4.with.empty.string1@[123.145.\"\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3031 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90\"\"]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3032 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90]\"\""                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3033 - assertIsFalse "ip.v4.with.empty.string1@[\"\"123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3034 - assertIsFalse "ip.v4.with.empty.string1@\"\"[123.145.178.90]"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3035 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3036 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3037 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3038 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3039 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3040 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3041 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3042 - assertIsFalse "a\"\"b.local.name.starts.with.empty.string2@domain.com"               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3043 - assertIsFalse "local.name.ends.with.empty.string2a\"\"b@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3044 - assertIsFalse "local.name.with.empty.string2a\"\"bcharacter@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3045 - assertIsFalse "local.name.with.empty.string2.beforea\"\"b.point@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3046 - assertIsFalse "local.name.with.empty.string2.after.a\"\"bpoint@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3047 - assertIsFalse "local.name.with.double.empty.string2a\"\"ba\"\"btest@domain.com"      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3048 - assertIsFalse "(comment a\"\"b) local.name.with.comment.with.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3049 - assertIsFalse "\"quotea\"\"b\".local.name.with.qoute.with.empty.string2@domain.com"  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3050 - assertIsFalse "a\"\"b@empty.string2.domain.com"                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3051 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@empty.string2.domain.com"        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3052 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3053 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2@domain.com>"         =   0 =  OK 
     *  3054 - assertIsTrue  "<pointy.brackets2.with.empty.string2@domain.com> name a\"\"b"         =   0 =  OK 
     *  3055 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3056 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3057 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3058 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3059 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3060 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3061 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3062 - assertIsFalse "domain.part.with.comment.with.empty.string2@(comment a\"\"b)domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3063 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3064 - assertIsFalse "ip.v4.with.empty.string2@[123.14a\"\"b5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3065 - assertIsFalse "ip.v4.with.empty.string2@[123.145a\"\"b.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3066 - assertIsFalse "ip.v4.with.empty.string2@[123.145.a\"\"b178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3067 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90a\"\"b]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3068 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90]a\"\"b"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3069 - assertIsFalse "ip.v4.with.empty.string2@[a\"\"b123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3070 - assertIsFalse "ip.v4.with.empty.string2@a\"\"b[123.145.178.90]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3071 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3072 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3073 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3074 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3075 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3076 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3077 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3078 - assertIsFalse "\"\"\"\".local.name.starts.with.double.empty.string1@domain.com"      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3079 - assertIsFalse "local.name.ends.with.double.empty.string1\"\"\"\"@domain.com"         =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3080 - assertIsFalse "local.name.with.double.empty.string1\"\"\"\"character@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3081 - assertIsFalse "local.name.with.double.empty.string1.before\"\"\"\".point@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3082 - assertIsFalse "local.name.with.double.empty.string1.after.\"\"\"\"point@domain.com"  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3083 - assertIsFalse "local.name.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3084 - assertIsFalse "(comment \"\"\"\") local.name.with.comment.with.double.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3085 - assertIsFalse "\"quote\"\"\"\"\".local.name.with.qoute.with.double.empty.string1@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3086 - assertIsFalse "\"\"\"\"@double.empty.string1.domain.com"                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3087 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3088 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3089 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1@domain.com>" =   0 =  OK 
     *  3090 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1@domain.com> name \"\"\"\"" =   0 =  OK 
     *  3091 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3092 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3093 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3094 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3095 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3096 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3097 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3098 - assertIsFalse "domain.part.with.comment.with.double.empty.string1@(comment \"\"\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3099 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3100 - assertIsFalse "ip.v4.with.double.empty.string1@[123.14\"\"\"\"5.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3101 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145\"\"\"\".178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3102 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.\"\"\"\"178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3103 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90\"\"\"\"]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3104 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90]\"\"\"\""             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3105 - assertIsFalse "ip.v4.with.double.empty.string1@[\"\"\"\"123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3106 - assertIsFalse "ip.v4.with.double.empty.string1@\"\"\"\"[123.145.178.90]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3107 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3108 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3109 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3110 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3111 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3112 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3113 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3114 - assertIsFalse "\"\".\"\".local.name.starts.with.double.empty.string2@domain.com"     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3115 - assertIsFalse "local.name.ends.with.double.empty.string2\"\".\"\"@domain.com"        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3116 - assertIsFalse "local.name.with.double.empty.string2\"\".\"\"character@domain.com"    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3117 - assertIsFalse "local.name.with.double.empty.string2.before\"\".\"\".point@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3118 - assertIsFalse "local.name.with.double.empty.string2.after.\"\".\"\"point@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3119 - assertIsFalse "local.name.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3120 - assertIsFalse "(comment \"\".\"\") local.name.with.comment.with.double.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3121 - assertIsFalse "\"quote\"\".\"\"\".local.name.with.qoute.with.double.empty.string2@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3122 - assertIsFalse "\"\".\"\"@double.empty.string2.domain.com"                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3123 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3124 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3125 - assertIsTrue  "name \"\".\"\" <pointy.brackets1.with.double.empty.string2@domain.com>" =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3126 - assertIsTrue  "<pointy.brackets2.with.double.empty.string2@domain.com> name \"\".\"\"" =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3127 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3128 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3129 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3130 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3131 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3132 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3133 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3134 - assertIsFalse "domain.part.with.comment.with.double.empty.string2@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3135 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3136 - assertIsFalse "ip.v4.with.double.empty.string2@[123.14\"\".\"\"5.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3137 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145\"\".\"\".178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3138 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.\"\".\"\"178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3139 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90\"\".\"\"]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3140 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90]\"\".\"\""            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3141 - assertIsFalse "ip.v4.with.double.empty.string2@[\"\".\"\"123.145.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3142 - assertIsFalse "ip.v4.with.double.empty.string2@\"\".\"\"[123.145.178.90]"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3143 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3144 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3145 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3146 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3147 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3148 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3149 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3150 - assertIsTrue  "/.local.name.starts.with.forward.slash@domain.com"                    =   0 =  OK 
     *  3151 - assertIsTrue  "local.name.ends.with.forward.slash/@domain.com"                       =   0 =  OK 
     *  3152 - assertIsTrue  "local.name.with.forward.slash/character@domain.com"                   =   0 =  OK 
     *  3153 - assertIsTrue  "local.name.with.forward.slash.before/.point@domain.com"               =   0 =  OK 
     *  3154 - assertIsTrue  "local.name.with.forward.slash.after./point@domain.com"                =   0 =  OK 
     *  3155 - assertIsTrue  "local.name.with.double.forward.slash//test@domain.com"                =   0 =  OK 
     *  3156 - assertIsTrue  "(comment /) local.name.with.comment.with.forward.slash@domain.com"    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3157 - assertIsTrue  "\"quote/\".local.name.with.qoute.with.forward.slash@domain.com"       =   1 =  OK 
     *  3158 - assertIsTrue  "/@forward.slash.domain.com"                                           =   0 =  OK 
     *  3159 - assertIsTrue  "//////@forward.slash.domain.com"                                      =   0 =  OK 
     *  3160 - assertIsTrue  "/./././././@forward.slash.domain.com"                                 =   0 =  OK 
     *  3161 - assertIsFalse "name / <pointy.brackets1.with.forward.slash@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3162 - assertIsFalse "<pointy.brackets2.with.forward.slash@domain.com> name /"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3163 - assertIsFalse "domain.part@with/forward.slash.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3164 - assertIsFalse "domain.part@with//double.forward.slash.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3165 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3166 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3167 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3168 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3169 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3170 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3171 - assertIsTrue  "domain.part.with.comment.with.forward.slash@(comment /)domain.com"    =   6 =  OK 
     *  3172 - assertIsFalse "domain.part.only.forward.slash@/.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3173 - assertIsFalse "ip.v4.with.forward.slash@[123.14/5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3174 - assertIsFalse "ip.v4.with.forward.slash@[123.145/.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3175 - assertIsFalse "ip.v4.with.forward.slash@[123.145./178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3176 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90/]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3177 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90]/"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3178 - assertIsFalse "ip.v4.with.forward.slash@[/123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3179 - assertIsFalse "ip.v4.with.forward.slash@/[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3180 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3181 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3182 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3183 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3184 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3185 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3186 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3187 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3188 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     *  3189 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3190 - assertIsTrue  "Ärger.Öde@Übel.de"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3191 - assertIsTrue  "Smørrebrød@danmark.dk"                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3192 - assertIsTrue  "ip.without.brackets@1.2.3.4"                                          =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  3193 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3194 - assertIsTrue  "(space after comment) john.smith@example.com"                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3195 - assertIsTrue  "email.address.without@topleveldomain"                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3196 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                   =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     *  3197 - assertIsTrue  "valid.email.from.nr1079@fillup.tofalse.com"                           =   0 =  OK 
     *           ...
     *  3198 - assertIsTrue  "valid.email.to.nr2118@fillup.tofalse.com"                             =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  2118   KORREKT 2031 =   95.892 % | FALSCH ERKANNT   87 =    4.108 % = Error 0
     *   ASSERT_IS_FALSE 2118   KORREKT 2107 =   99.481 % | FALSCH ERKANNT   11 =    0.519 % = Error 0
     * 
     *   GESAMT          4236   KORREKT 4138 =   97.686 % | FALSCH ERKANNT   98 =    2.314 % = Error 0
     * 
     * 
     *   Millisekunden    132 = 0.031161473087818695
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

      assertIsTrue( "ip4.adress@[1.2.3.4]" );
      assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );

      assertIsTrue( "\"quote1\".name1@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@domain1.tld" );
      assertIsTrue( "\"quote1\".\"quote2\".name1@domain1.tld" );

      assertIsTrue( "(comment1)name1@domain1.tld" );
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
      assertIsFalse( "\"locál-part\"@example.com" ); // international local-part when allowInternational=false should fail#8
      assertIsFalse( "invalid@[IPv6:1::2:]" ); // incomplete IPv6#8
      assertIsFalse( "invalid@[IPv6::1::1]" );

      assertIsFalse( "invalid@[]" ); // empty IP literal#8
      assertIsFalse( "invalid@[111.111.111.111" ); // unenclosed IPv4 literal#8
      assertIsFalse( "invalid@[IPv6:2607:f0d0:1002:51::4" ); // unenclosed IPv6 literal#8
      assertIsFalse( "invalid@[IPv6:1111::1111::1111]" ); // invalid IPv6-comp#8
      assertIsFalse( "invalid@[IPv6:1111:::1111::1111]" ); // more than 2 consecutive :'s in IPv6#8
      assertIsFalse( "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]" ); // invalid IPv4 address in IPv6v4#8
      assertIsFalse( "invalid@[IPv6:1111:1111]" ); // incomplete IPv6#8
      assertIsFalse( "\"invalid-qstring@example.com" ); // unterminated q-string in local-part of the addr-spec#8

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
