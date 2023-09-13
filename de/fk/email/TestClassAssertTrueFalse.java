package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestClassAssertTrueFalse
{
  private static int          TEST_B_TEST_NR                    = 5;

  private static int          LAUFENDE_ZAHL                     = 0;

  private static int          COUNT_ASSERT_IS_TRUE              = 0;

  private static int          COUNT_ASSERT_IS_FALSE             = 0;

  private static int          TRUE_RESULT_COUNT_EMAIL_IS_TRUE   = 0;

  private static int          TRUE_RESULT_COUNT_EMAIL_IS_FALSE  = 0;

  private static int          FALSE_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          FALSE_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          FALSE_RESULT_COUNT_ERROR          = 0;

  private static int          TRUE_RESULT_COUNT_ERROR           = 0;

  private static int          BREITE_SPALTE_EMAIL_AUSGABE       = 70;

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
     *     1 - assertIsTrue  "n@d.td"                                                               =   0 =  OK 
     *     2 - assertIsTrue  "1@2.td"                                                               =   0 =  OK 
     *     3 - assertIsTrue  "12.345@678.90.tld"                                                    =   0 =  OK 
     *     4 - assertIsTrue  "name1.name2@domain1.tld"                                              =   0 =  OK 
     *     5 - assertIsTrue  "name1+name2@domain1.tld"                                              =   0 =  OK 
     *     6 - assertIsTrue  "name1-name2@domain1.tld"                                              =   0 =  OK 
     *     7 - assertIsTrue  "name1.name2@subdomain1.domain1.tld"                                   =   0 =  OK 
     *     8 - assertIsTrue  "name1.name2@subdomain1.tu-domain1.tld"                                =   0 =  OK 
     *     9 - assertIsTrue  "name1.name2@subdomain1.tu_domain1.tld"                                =   0 =  OK 
     *    10 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                 =   2 =  OK 
     *    11 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                    =   4 =  OK 
     *    12 - assertIsTrue  "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]"                           =   4 =  OK 
     *    13 - assertIsTrue  "ip4.without.brackets@1.2.3.4"                                         =   2 =  OK 
     *    14 - assertIsTrue  "\"quote1\".name1@domain1.tld"                                         =   1 =  OK 
     *    15 - assertIsTrue  "name1.\"quote1\"@domain1.tld"                                         =   1 =  OK 
     *    16 - assertIsTrue  "name1.\"quote1\".name2@domain1.tld"                                   =   1 =  OK 
     *    17 - assertIsTrue  "name1.\"quote1\".name2@subdomain1.domain1.tld"                        =   1 =  OK 
     *    18 - assertIsTrue  "\"quote1\".\"quote2\".name1@domain1.tld"                              =   1 =  OK 
     *    19 - assertIsTrue  "\"quote1\"@domain1.tld"                                               =   1 =  OK 
     *    20 - assertIsTrue  "\"quote1\\"qoute2\\"\"@domain1.tld"                                   =   1 =  OK 
     *    21 - assertIsTrue  "(comment1)name1@domain1.tld"                                          =   6 =  OK 
     *    22 - assertIsTrue  "(comment1)-name1@domain1.tld"                                         =   6 =  OK 
     *    23 - assertIsTrue  "name1(comment1)@domain1.tld"                                          =   6 =  OK 
     *    24 - assertIsTrue  "name1@(comment1)domain1.tld"                                          =   6 =  OK 
     *    25 - assertIsTrue  "name1@domain1.tld(comment1)"                                          =   6 =  OK 
     *    26 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                 =   2 =  OK 
     *    27 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                 =   2 =  OK 
     *    28 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                 =   2 =  OK 
     *    29 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                 =   2 =  OK 
     *    30 - assertIsTrue  "(comment1)\"quote1\".name1@domain1.tld"                               =   7 =  OK 
     *    31 - assertIsTrue  "(comment1)name1.\"quote1\"@domain1.tld"                               =   7 =  OK 
     *    32 - assertIsTrue  "name1.\"quote1\"(comment1)@domain1.tld"                               =   7 =  OK 
     *    33 - assertIsTrue  "\"quote1\".name1(comment1)@domain1.tld"                               =   7 =  OK 
     *    34 - assertIsTrue  "name1.\"quote1\"@(comment1)domain1.tld"                               =   7 =  OK 
     *    35 - assertIsTrue  "\"quote1\".name1@domain1.tld(comment1)"                               =   7 =  OK 
     *    36 - assertIsTrue  "<name1.name2@domain1.tld>"                                            =   0 =  OK 
     *    37 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                      =   0 =  OK 
     *    38 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                      =   0 =  OK 
     *    39 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                            =   0 =  OK 
     *    40 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                         =   2 =  OK 
     *    41 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                            =   4 =  OK 
     *    42 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                         =   2 =  OK 
     *    43 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                           =   4 =  OK 
     *    44 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"  =   6 =  OK 
     *    45 - assertIsTrue  "\"display name\" <local.part@(comment)domain-name.top_level_domain>"  =   6 =  OK 
     *    46 - assertIsTrue  "\"display name\" <(comment)local.part.\"quote1\"@domain-name.top_level_domain>" =   7 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    47 - assertIsFalse null                                                                   =  10 =  OK    Laenge: Eingabe ist null
     *    48 - assertIsFalse ""                                                                     =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    49 - assertIsFalse "        "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    50 - assertIsFalse "1234567890"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    51 - assertIsFalse "OnlyTextNoDotNoAt"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    52 - assertIsFalse "email.with.no.at.character"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    53 - assertIsFalse "email.with.no.domain@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    54 - assertIsFalse "@.local.name.starts.with.at@domain.com"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    55 - assertIsFalse "@no.local.email.part.com"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    56 - assertIsFalse "local.name.with@at@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    57 - assertIsFalse "local.name.ends.with.at@@domain.com"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    58 - assertIsFalse "local.name.with.at.before@.point@domain.com"                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    59 - assertIsFalse "local.name.with.at.after.@point@domain.com"                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    60 - assertIsFalse "local.name.with.double.at@@test@domain.com"                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    61 - assertIsFalse "(comment @) local.name.with.comment.with.at@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    62 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"          =   6 =  OK 
     *    63 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"  =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *    64 - assertIsTrue  "\"quote@\".local.name.with.qoute.with.at@domain.com"                  =   1 =  OK 
     *    65 - assertIsTrue  "qouted.\@.character@domain.com"                                       =   0 =  OK 
     *    66 - assertIsTrue  "qouted\@character@domain.com"                                         =   0 =  OK 
     *    67 - assertIsTrue  "\@@domain.com"                                                        =   0 =  OK 
     *    68 - assertIsFalse "@@domain.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    69 - assertIsFalse "@domain.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    70 - assertIsFalse "@@@@@@@domain.com"                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    71 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                         =   0 =  OK 
     *    72 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    73 - assertIsFalse "@.@.@.@.@.@@domain.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    74 - assertIsFalse "@.@.@."                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    75 - assertIsFalse "\@.\@@\@.\@"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    76 - assertIsFalse "@"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    77 - assertIsFalse "name @ <pointy.brackets1.with.at@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    78 - assertIsFalse "<pointy.brackets2.with.at@domain.com> name @"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    79 - assertIsFalse "..local.name.starts.with.dot@domain.com"                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    80 - assertIsFalse "local.name.ends.with.dot.@domain.com"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    81 - assertIsFalse "local.name.with.dot.before..point@domain.com"                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    82 - assertIsFalse "local.name.with.dot.after..point@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    83 - assertIsFalse "local.name.with.double.dot..test@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    84 - assertIsFalse "(comment .) local.name.with.comment.with.dot@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    85 - assertIsTrue  "\"quote.\".local.name.with.qoute.with.dot@domain.com"                 =   1 =  OK 
     *    86 - assertIsFalse ".@domain.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    87 - assertIsFalse "......@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    88 - assertIsFalse "...........@domain.com"                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    89 - assertIsFalse "qouted\.dot@domain.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *    90 - assertIsFalse "name . <pointy.brackets1.with.dot@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    91 - assertIsFalse "<pointy.brackets2.with.dot@domain.com> name ."                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    92 - assertIsFalse "domain.part.without.dot@domaincom"                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    93 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    94 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    95 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    96 - assertIsFalse "domain.part@with.dot.before..point.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    97 - assertIsFalse "domain.part@with.dot.after..point.com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    98 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    99 - assertIsFalse "EmailAdressWith@NoDots"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *   100 - assertIsTrue  "&local&&name&with&$@amp.com"                                          =   0 =  OK 
     *   101 - assertIsTrue  "*local**name*with*@asterisk.com"                                      =   0 =  OK 
     *   102 - assertIsTrue  "$local$$name$with$@dollar.com"                                        =   0 =  OK 
     *   103 - assertIsTrue  "=local==name=with=@equality.com"                                      =   0 =  OK 
     *   104 - assertIsTrue  "!local!!name!with!@exclamation.com"                                   =   0 =  OK 
     *   105 - assertIsTrue  "`local``name`with`@grave-accent.com"                                  =   0 =  OK 
     *   106 - assertIsTrue  "#local##name#with#@hash.com"                                          =   0 =  OK 
     *   107 - assertIsTrue  "-local--name-with-@hypen.com"                                         =   0 =  OK 
     *   108 - assertIsTrue  "{local{name{{with{@leftbracket.com"                                   =   0 =  OK 
     *   109 - assertIsTrue  "%local%%name%with%@percentage.com"                                    =   0 =  OK 
     *   110 - assertIsTrue  "|local||name|with|@pipe.com"                                          =   0 =  OK 
     *   111 - assertIsTrue  "+local++name+with+@plus.com"                                          =   0 =  OK 
     *   112 - assertIsTrue  "?local??name?with?@question.com"                                      =   0 =  OK 
     *   113 - assertIsTrue  "}local}name}}with}@rightbracket.com"                                  =   0 =  OK 
     *   114 - assertIsTrue  "~local~~name~with~@tilde.com"                                         =   0 =  OK 
     *   115 - assertIsTrue  "^local^^name^with^@xor.com"                                           =   0 =  OK 
     *   116 - assertIsTrue  "_local__name_with_@underscore.com"                                    =   0 =  OK 
     *   117 - assertIsFalse ":local::name:with:@colon.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   118 - assertIsTrue  "&.local.name.starts.with.amp@domain.com"                              =   0 =  OK 
     *   119 - assertIsTrue  "local.name.ends.with.amp&@domain.com"                                 =   0 =  OK 
     *   120 - assertIsTrue  "local.name.with.amp.before&.point@domain.com"                         =   0 =  OK 
     *   121 - assertIsTrue  "local.name.with.amp.after.&point@domain.com"                          =   0 =  OK 
     *   122 - assertIsTrue  "local.name.with.double.amp&&test@domain.com"                          =   0 =  OK 
     *   123 - assertIsFalse "(comment &) local.name.with.comment.with.amp@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   124 - assertIsTrue  "\"quote&\".local.name.with.qoute.with.amp@domain.com"                 =   1 =  OK 
     *   125 - assertIsTrue  "&@amp.domain.com"                                                     =   0 =  OK 
     *   126 - assertIsTrue  "&&&&&&@amp.domain.com"                                                =   0 =  OK 
     *   127 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                           =   0 =  OK 
     *   128 - assertIsFalse "name & <pointy.brackets1.with.amp@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   129 - assertIsFalse "<pointy.brackets2.with.amp@domain.com> name &"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   130 - assertIsTrue  "*.local.name.starts.with.asterisk@domain.com"                         =   0 =  OK 
     *   131 - assertIsTrue  "local.name.ends.with.asterisk*@domain.com"                            =   0 =  OK 
     *   132 - assertIsTrue  "local.name.with.asterisk.before*.point@domain.com"                    =   0 =  OK 
     *   133 - assertIsTrue  "local.name.with.asterisk.after.*point@domain.com"                     =   0 =  OK 
     *   134 - assertIsTrue  "local.name.with.double.asterisk**test@domain.com"                     =   0 =  OK 
     *   135 - assertIsFalse "(comment *) local.name.with.comment.with.asterisk@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   136 - assertIsTrue  "\"quote*\".local.name.with.qoute.with.asterisk@domain.com"            =   1 =  OK 
     *   137 - assertIsTrue  "*@asterisk.domain.com"                                                =   0 =  OK 
     *   138 - assertIsTrue  "******@asterisk.domain.com"                                           =   0 =  OK 
     *   139 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                      =   0 =  OK 
     *   140 - assertIsFalse "name * <pointy.brackets1.with.asterisk@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   141 - assertIsFalse "<pointy.brackets2.with.asterisk@domain.com> name *"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   142 - assertIsTrue  "$.local.name.starts.with.dollar@domain.com"                           =   0 =  OK 
     *   143 - assertIsTrue  "local.name.ends.with.dollar$@domain.com"                              =   0 =  OK 
     *   144 - assertIsTrue  "local.name.with.dollar.before$.point@domain.com"                      =   0 =  OK 
     *   145 - assertIsTrue  "local.name.with.dollar.after.$point@domain.com"                       =   0 =  OK 
     *   146 - assertIsTrue  "local.name.with.double.dollar$$test@domain.com"                       =   0 =  OK 
     *   147 - assertIsFalse "(comment $) local.name.with.comment.with.dollar@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   148 - assertIsTrue  "\"quote$\".local.name.with.qoute.with.dollar@domain.com"              =   1 =  OK 
     *   149 - assertIsTrue  "$@dollar.domain.com"                                                  =   0 =  OK 
     *   150 - assertIsTrue  "$$$$$$@dollar.domain.com"                                             =   0 =  OK 
     *   151 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                        =   0 =  OK 
     *   152 - assertIsFalse "name $ <pointy.brackets1.with.dollar@domain.com>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   153 - assertIsFalse "<pointy.brackets2.with.dollar@domain.com> name $"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   154 - assertIsTrue  "=.local.name.starts.with.equality@domain.com"                         =   0 =  OK 
     *   155 - assertIsTrue  "local.name.ends.with.equality=@domain.com"                            =   0 =  OK 
     *   156 - assertIsTrue  "local.name.with.equality.before=.point@domain.com"                    =   0 =  OK 
     *   157 - assertIsTrue  "local.name.with.equality.after.=point@domain.com"                     =   0 =  OK 
     *   158 - assertIsTrue  "local.name.with.double.equality==test@domain.com"                     =   0 =  OK 
     *   159 - assertIsFalse "(comment =) local.name.with.comment.with.equality@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   160 - assertIsTrue  "\"quote=\".local.name.with.qoute.with.equality@domain.com"            =   1 =  OK 
     *   161 - assertIsTrue  "=@equality.domain.com"                                                =   0 =  OK 
     *   162 - assertIsTrue  "======@equality.domain.com"                                           =   0 =  OK 
     *   163 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                      =   0 =  OK 
     *   164 - assertIsFalse "name = <pointy.brackets1.with.equality@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   165 - assertIsFalse "<pointy.brackets2.with.equality@domain.com> name ="                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   166 - assertIsTrue  "!.local.name.starts.with.exclamation@domain.com"                      =   0 =  OK 
     *   167 - assertIsTrue  "local.name.ends.with.exclamation!@domain.com"                         =   0 =  OK 
     *   168 - assertIsTrue  "local.name.with.exclamation.before!.point@domain.com"                 =   0 =  OK 
     *   169 - assertIsTrue  "local.name.with.exclamation.after.!point@domain.com"                  =   0 =  OK 
     *   170 - assertIsTrue  "local.name.with.double.exclamation!!test@domain.com"                  =   0 =  OK 
     *   171 - assertIsFalse "(comment !) local.name.with.comment.with.exclamation@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   172 - assertIsTrue  "\"quote!\".local.name.with.qoute.with.exclamation@domain.com"         =   1 =  OK 
     *   173 - assertIsTrue  "!@exclamation.domain.com"                                             =   0 =  OK 
     *   174 - assertIsTrue  "!!!!!!@exclamation.domain.com"                                        =   0 =  OK 
     *   175 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                   =   0 =  OK 
     *   176 - assertIsFalse "name ! <pointy.brackets1.with.exclamation@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   177 - assertIsFalse "<pointy.brackets2.with.exclamation@domain.com> name !"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   178 - assertIsTrue  "`.local.name.starts.with.grave-accent@domain.com"                     =   0 =  OK 
     *   179 - assertIsTrue  "local.name.ends.with.grave-accent`@domain.com"                        =   0 =  OK 
     *   180 - assertIsTrue  "local.name.with.grave-accent.before`.point@domain.com"                =   0 =  OK 
     *   181 - assertIsTrue  "local.name.with.grave-accent.after.`point@domain.com"                 =   0 =  OK 
     *   182 - assertIsTrue  "local.name.with.double.grave-accent``test@domain.com"                 =   0 =  OK 
     *   183 - assertIsFalse "(comment `) local.name.with.comment.with.grave-accent@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   184 - assertIsTrue  "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com"        =   1 =  OK 
     *   185 - assertIsTrue  "`@grave-accent.domain.com"                                            =   0 =  OK 
     *   186 - assertIsTrue  "``````@grave-accent.domain.com"                                       =   0 =  OK 
     *   187 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                  =   0 =  OK 
     *   188 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   189 - assertIsFalse "<pointy.brackets2.with.grave-accent@domain.com> name `"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   190 - assertIsTrue  "#.local.name.starts.with.hash@domain.com"                             =   0 =  OK 
     *   191 - assertIsTrue  "local.name.ends.with.hash#@domain.com"                                =   0 =  OK 
     *   192 - assertIsTrue  "local.name.with.hash.before#.point@domain.com"                        =   0 =  OK 
     *   193 - assertIsTrue  "local.name.with.hash.after.#point@domain.com"                         =   0 =  OK 
     *   194 - assertIsTrue  "local.name.with.double.hash##test@domain.com"                         =   0 =  OK 
     *   195 - assertIsFalse "(comment #) local.name.with.comment.with.hash@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   196 - assertIsTrue  "\"quote#\".local.name.with.qoute.with.hash@domain.com"                =   1 =  OK 
     *   197 - assertIsTrue  "#@hash.domain.com"                                                    =   0 =  OK 
     *   198 - assertIsTrue  "######@hash.domain.com"                                               =   0 =  OK 
     *   199 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                          =   0 =  OK 
     *   200 - assertIsFalse "name # <pointy.brackets1.with.hash@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   201 - assertIsFalse "<pointy.brackets2.with.hash@domain.com> name #"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   202 - assertIsTrue  "-.local.name.starts.with.hypen@domain.com"                            =   0 =  OK 
     *   203 - assertIsTrue  "local.name.ends.with.hypen-@domain.com"                               =   0 =  OK 
     *   204 - assertIsTrue  "local.name.with.hypen.before-.point@domain.com"                       =   0 =  OK 
     *   205 - assertIsTrue  "local.name.with.hypen.after.-point@domain.com"                        =   0 =  OK 
     *   206 - assertIsTrue  "local.name.with.double.hypen--test@domain.com"                        =   0 =  OK 
     *   207 - assertIsFalse "(comment -) local.name.with.comment.with.hypen@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   208 - assertIsTrue  "\"quote-\".local.name.with.qoute.with.hypen@domain.com"               =   1 =  OK 
     *   209 - assertIsTrue  "-@hypen.domain.com"                                                   =   0 =  OK 
     *   210 - assertIsTrue  "------@hypen.domain.com"                                              =   0 =  OK 
     *   211 - assertIsTrue  "-.-.-.-.-.-@hypen.domain.com"                                         =   0 =  OK 
     *   212 - assertIsFalse "name - <pointy.brackets1.with.hypen@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   213 - assertIsFalse "<pointy.brackets2.with.hypen@domain.com> name -"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   214 - assertIsTrue  "{.local.name.starts.with.leftbracket@domain.com"                      =   0 =  OK 
     *   215 - assertIsTrue  "local.name.ends.with.leftbracket{@domain.com"                         =   0 =  OK 
     *   216 - assertIsTrue  "local.name.with.leftbracket.before{.point@domain.com"                 =   0 =  OK 
     *   217 - assertIsTrue  "local.name.with.leftbracket.after.{point@domain.com"                  =   0 =  OK 
     *   218 - assertIsTrue  "local.name.with.double.leftbracket{{test@domain.com"                  =   0 =  OK 
     *   219 - assertIsFalse "(comment {) local.name.with.comment.with.leftbracket@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   220 - assertIsTrue  "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   221 - assertIsTrue  "{@leftbracket.domain.com"                                             =   0 =  OK 
     *   222 - assertIsTrue  "{{{{{{@leftbracket.domain.com"                                        =   0 =  OK 
     *   223 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                   =   0 =  OK 
     *   224 - assertIsFalse "name { <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   225 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name {"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   226 - assertIsTrue  "%.local.name.starts.with.percentage@domain.com"                       =   0 =  OK 
     *   227 - assertIsTrue  "local.name.ends.with.percentage%@domain.com"                          =   0 =  OK 
     *   228 - assertIsTrue  "local.name.with.percentage.before%.point@domain.com"                  =   0 =  OK 
     *   229 - assertIsTrue  "local.name.with.percentage.after.%point@domain.com"                   =   0 =  OK 
     *   230 - assertIsTrue  "local.name.with.double.percentage%%test@domain.com"                   =   0 =  OK 
     *   231 - assertIsFalse "(comment %) local.name.with.comment.with.percentage@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   232 - assertIsTrue  "\"quote%\".local.name.with.qoute.with.percentage@domain.com"          =   1 =  OK 
     *   233 - assertIsTrue  "%@percentage.domain.com"                                              =   0 =  OK 
     *   234 - assertIsTrue  "%%%%%%@percentage.domain.com"                                         =   0 =  OK 
     *   235 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                    =   0 =  OK 
     *   236 - assertIsFalse "name % <pointy.brackets1.with.percentage@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   237 - assertIsFalse "<pointy.brackets2.with.percentage@domain.com> name %"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   238 - assertIsTrue  "|.local.name.starts.with.pipe@domain.com"                             =   0 =  OK 
     *   239 - assertIsTrue  "local.name.ends.with.pipe|@domain.com"                                =   0 =  OK 
     *   240 - assertIsTrue  "local.name.with.pipe.before|.point@domain.com"                        =   0 =  OK 
     *   241 - assertIsTrue  "local.name.with.pipe.after.|point@domain.com"                         =   0 =  OK 
     *   242 - assertIsTrue  "local.name.with.double.pipe||test@domain.com"                         =   0 =  OK 
     *   243 - assertIsFalse "(comment |) local.name.with.comment.with.pipe@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   244 - assertIsTrue  "\"quote|\".local.name.with.qoute.with.pipe@domain.com"                =   1 =  OK 
     *   245 - assertIsTrue  "|@pipe.domain.com"                                                    =   0 =  OK 
     *   246 - assertIsTrue  "||||||@pipe.domain.com"                                               =   0 =  OK 
     *   247 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                          =   0 =  OK 
     *   248 - assertIsFalse "name | <pointy.brackets1.with.pipe@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   249 - assertIsFalse "<pointy.brackets2.with.pipe@domain.com> name |"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   250 - assertIsTrue  "+.local.name.starts.with.plus@domain.com"                             =   0 =  OK 
     *   251 - assertIsTrue  "local.name.ends.with.plus+@domain.com"                                =   0 =  OK 
     *   252 - assertIsTrue  "local.name.with.plus.before+.point@domain.com"                        =   0 =  OK 
     *   253 - assertIsTrue  "local.name.with.plus.after.+point@domain.com"                         =   0 =  OK 
     *   254 - assertIsTrue  "local.name.with.double.plus++test@domain.com"                         =   0 =  OK 
     *   255 - assertIsFalse "(comment +) local.name.with.comment.with.plus@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   256 - assertIsTrue  "\"quote+\".local.name.with.qoute.with.plus@domain.com"                =   1 =  OK 
     *   257 - assertIsTrue  "+@plus.domain.com"                                                    =   0 =  OK 
     *   258 - assertIsTrue  "++++++@plus.domain.com"                                               =   0 =  OK 
     *   259 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                          =   0 =  OK 
     *   260 - assertIsFalse "name + <pointy.brackets1.with.plus@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   261 - assertIsFalse "<pointy.brackets2.with.plus@domain.com> name +"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   262 - assertIsTrue  "?.local.name.starts.with.question@domain.com"                         =   0 =  OK 
     *   263 - assertIsTrue  "local.name.ends.with.question?@domain.com"                            =   0 =  OK 
     *   264 - assertIsTrue  "local.name.with.question.before?.point@domain.com"                    =   0 =  OK 
     *   265 - assertIsTrue  "local.name.with.question.after.?point@domain.com"                     =   0 =  OK 
     *   266 - assertIsTrue  "local.name.with.double.question??test@domain.com"                     =   0 =  OK 
     *   267 - assertIsFalse "(comment ?) local.name.with.comment.with.question@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   268 - assertIsTrue  "\"quote?\".local.name.with.qoute.with.question@domain.com"            =   1 =  OK 
     *   269 - assertIsTrue  "?@question.domain.com"                                                =   0 =  OK 
     *   270 - assertIsTrue  "??????@question.domain.com"                                           =   0 =  OK 
     *   271 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                      =   0 =  OK 
     *   272 - assertIsFalse "name ? <pointy.brackets1.with.question@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   273 - assertIsFalse "<pointy.brackets2.with.question@domain.com> name ?"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   274 - assertIsTrue  "}.local.name.starts.with.rightbracket@domain.com"                     =   0 =  OK 
     *   275 - assertIsTrue  "local.name.ends.with.rightbracket}@domain.com"                        =   0 =  OK 
     *   276 - assertIsTrue  "local.name.with.rightbracket.before}.point@domain.com"                =   0 =  OK 
     *   277 - assertIsTrue  "local.name.with.rightbracket.after.}point@domain.com"                 =   0 =  OK 
     *   278 - assertIsTrue  "local.name.with.double.rightbracket}}test@domain.com"                 =   0 =  OK 
     *   279 - assertIsFalse "(comment }) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   280 - assertIsTrue  "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   281 - assertIsTrue  "}@rightbracket.domain.com"                                            =   0 =  OK 
     *   282 - assertIsTrue  "}}}}}}@rightbracket.domain.com"                                       =   0 =  OK 
     *   283 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                  =   0 =  OK 
     *   284 - assertIsFalse "name } <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   285 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name }"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   286 - assertIsTrue  "~.local.name.starts.with.tilde@domain.com"                            =   0 =  OK 
     *   287 - assertIsTrue  "local.name.ends.with.tilde~@domain.com"                               =   0 =  OK 
     *   288 - assertIsTrue  "local.name.with.tilde.before~.point@domain.com"                       =   0 =  OK 
     *   289 - assertIsTrue  "local.name.with.tilde.after.~point@domain.com"                        =   0 =  OK 
     *   290 - assertIsTrue  "local.name.with.double.tilde~~test@domain.com"                        =   0 =  OK 
     *   291 - assertIsFalse "(comment ~) local.name.with.comment.with.tilde@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   292 - assertIsTrue  "\"quote~\".local.name.with.qoute.with.tilde@domain.com"               =   1 =  OK 
     *   293 - assertIsTrue  "~@tilde.domain.com"                                                   =   0 =  OK 
     *   294 - assertIsTrue  "~~~~~~@tilde.domain.com"                                              =   0 =  OK 
     *   295 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                         =   0 =  OK 
     *   296 - assertIsFalse "name ~ <pointy.brackets1.with.tilde@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   297 - assertIsFalse "<pointy.brackets2.with.tilde@domain.com> name ~"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   298 - assertIsTrue  "^.local.name.starts.with.xor@domain.com"                              =   0 =  OK 
     *   299 - assertIsTrue  "local.name.ends.with.xor^@domain.com"                                 =   0 =  OK 
     *   300 - assertIsTrue  "local.name.with.xor.before^.point@domain.com"                         =   0 =  OK 
     *   301 - assertIsTrue  "local.name.with.xor.after.^point@domain.com"                          =   0 =  OK 
     *   302 - assertIsTrue  "local.name.with.double.xor^^test@domain.com"                          =   0 =  OK 
     *   303 - assertIsFalse "(comment ^) local.name.with.comment.with.xor@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   304 - assertIsTrue  "\"quote^\".local.name.with.qoute.with.xor@domain.com"                 =   1 =  OK 
     *   305 - assertIsTrue  "^@xor.domain.com"                                                     =   0 =  OK 
     *   306 - assertIsTrue  "^^^^^^@xor.domain.com"                                                =   0 =  OK 
     *   307 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                           =   0 =  OK 
     *   308 - assertIsFalse "name ^ <pointy.brackets1.with.xor@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   309 - assertIsFalse "<pointy.brackets2.with.xor@domain.com> name ^"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   310 - assertIsTrue  "_.local.name.starts.with.underscore@domain.com"                       =   0 =  OK 
     *   311 - assertIsTrue  "local.name.ends.with.underscore_@domain.com"                          =   0 =  OK 
     *   312 - assertIsTrue  "local.name.with.underscore.before_.point@domain.com"                  =   0 =  OK 
     *   313 - assertIsTrue  "local.name.with.underscore.after._point@domain.com"                   =   0 =  OK 
     *   314 - assertIsTrue  "local.name.with.double.underscore__test@domain.com"                   =   0 =  OK 
     *   315 - assertIsFalse "(comment _) local.name.with.comment.with.underscore@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   316 - assertIsTrue  "\"quote_\".local.name.with.qoute.with.underscore@domain.com"          =   1 =  OK 
     *   317 - assertIsTrue  "_@underscore.domain.com"                                              =   0 =  OK 
     *   318 - assertIsTrue  "______@underscore.domain.com"                                         =   0 =  OK 
     *   319 - assertIsTrue  "_._._._._._@underscore.domain.com"                                    =   0 =  OK 
     *   320 - assertIsFalse "name _ <pointy.brackets1.with.underscore@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   321 - assertIsFalse "<pointy.brackets2.with.underscore@domain.com> name _"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   322 - assertIsFalse ":.local.name.starts.with.colon@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   323 - assertIsFalse "local.name.ends.with.colon:@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   324 - assertIsFalse "local.name.with.colon.before:.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   325 - assertIsFalse "local.name.with.colon.after.:point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   326 - assertIsFalse "local.name.with.double.colon::test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   327 - assertIsFalse "(comment :) local.name.with.comment.with.colon@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   328 - assertIsTrue  "\"quote:\".local.name.with.qoute.with.colon@domain.com"               =   1 =  OK 
     *   329 - assertIsFalse ":@colon.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   330 - assertIsFalse "::::::@colon.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   331 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   332 - assertIsFalse "name : <pointy.brackets1.with.colon@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   333 - assertIsFalse "<pointy.brackets2.with.colon@domain.com> name :"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   334 - assertIsFalse "(.local.name.starts.with.leftbracket@domain.com"                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   335 - assertIsFalse "local.name.ends.with.leftbracket(@domain.com"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   336 - assertIsFalse "local.name.with.leftbracket.before(.point@domain.com"                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   337 - assertIsFalse "local.name.with.leftbracket.after.(point@domain.com"                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   338 - assertIsFalse "local.name.with.double.leftbracket((test@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   339 - assertIsFalse "(comment () local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   340 - assertIsTrue  "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   341 - assertIsFalse "(@leftbracket.domain.com"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   342 - assertIsFalse "((((((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   343 - assertIsFalse "(()(((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   344 - assertIsFalse "((<)>(((@leftbracket.domain.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   345 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   346 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket@domain.com>"                =   0 =  OK 
     *   347 - assertIsTrue  "<pointy.brackets2.with.leftbracket@domain.com> name ("                =   0 =  OK 
     *   348 - assertIsFalse "\.local.name.starts.with.slash@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   349 - assertIsFalse "local.name.ends.with.slash\@domain.com"                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   350 - assertIsFalse "local.name.with.slash.before\.point@domain.com"                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   351 - assertIsFalse "local.name.with.slash.after.\point@domain.com"                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   352 - assertIsTrue  "local.name.with.double.slash\\test@domain.com"                        =   0 =  OK 
     *   353 - assertIsFalse "(comment \) local.name.with.comment.with.slash@domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   354 - assertIsFalse "\"quote\\".local.name.with.qoute.with.slash@domain.com"               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   355 - assertIsFalse "\@slash.domain.com"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   356 - assertIsTrue  "\\\\\\@slash.domain.com"                                              =   0 =  OK 
     *   357 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   358 - assertIsFalse "name \ <pointy.brackets1.with.slash@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   359 - assertIsFalse "<pointy.brackets2.with.slash@domain.com> name \"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   360 - assertIsFalse ").local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   361 - assertIsFalse "local.name.ends.with.rightbracket)@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   362 - assertIsFalse "local.name.with.rightbracket.before).point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   363 - assertIsFalse "local.name.with.rightbracket.after.)point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   364 - assertIsFalse "local.name.with.double.rightbracket))test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   365 - assertIsFalse "(comment )) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   366 - assertIsTrue  "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   367 - assertIsFalse ")@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   368 - assertIsFalse "))))))@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   369 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   370 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket@domain.com>"               =   0 =  OK 
     *   371 - assertIsTrue  "<pointy.brackets2.with.rightbracket@domain.com> name )"               =   0 =  OK 
     *   372 - assertIsFalse "[.local.name.starts.with.leftbracket@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   373 - assertIsFalse "local.name.ends.with.leftbracket[@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   374 - assertIsFalse "local.name.with.leftbracket.before[.point@domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   375 - assertIsFalse "local.name.with.leftbracket.after.[point@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   376 - assertIsFalse "local.name.with.double.leftbracket[[test@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   377 - assertIsFalse "(comment [) local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   378 - assertIsTrue  "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   379 - assertIsFalse "[@leftbracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   380 - assertIsFalse "[[[[[[@leftbracket.domain.com"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   381 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   382 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   383 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name ["                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   384 - assertIsFalse "].local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   385 - assertIsFalse "local.name.ends.with.rightbracket]@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   386 - assertIsFalse "local.name.with.rightbracket.before].point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   387 - assertIsFalse "local.name.with.rightbracket.after.]point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   388 - assertIsFalse "local.name.with.double.rightbracket]]test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   389 - assertIsFalse "(comment ]) local.name.with.comment.with.rightbracket@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   390 - assertIsTrue  "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   391 - assertIsFalse "]@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   392 - assertIsFalse "]]]]]]@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   393 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   394 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   395 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name ]"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   396 - assertIsFalse " .local.name.starts.with.space@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   397 - assertIsFalse "local.name.ends.with.space @domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   398 - assertIsFalse "local.name.with.space.before .point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   399 - assertIsFalse "local.name.with.space.after. point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   400 - assertIsFalse "local.name.with.double.space  test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   401 - assertIsFalse "(comment  ) local.name.with.comment.with.space@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   402 - assertIsTrue  "\"quote \".local.name.with.qoute.with.space@domain.com"               =   1 =  OK 
     *   403 - assertIsFalse " @space.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   404 - assertIsFalse "      @space.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   405 - assertIsFalse " . . . . . @space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   406 - assertIsTrue  "name   <pointy.brackets1.with.space@domain.com>"                      =   0 =  OK 
     *   407 - assertIsTrue  "<pointy.brackets2.with.space@domain.com> name  "                      =   0 =  OK 
     *   408 - assertIsFalse "().local.name.starts.with.empty.bracket@domain.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   409 - assertIsTrue  "local.name.ends.with.empty.bracket()@domain.com"                      =   6 =  OK 
     *   410 - assertIsFalse "local.name.with.empty.bracket.before().point@domain.com"              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   411 - assertIsFalse "local.name.with.empty.bracket.after.()point@domain.com"               = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   412 - assertIsFalse "local.name.with.double.empty.bracket()()test@domain.com"              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   413 - assertIsFalse "(comment ()) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   414 - assertIsTrue  "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   415 - assertIsFalse "()@empty.bracket.domain.com"                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   416 - assertIsFalse "()()()()()()@empty.bracket.domain.com"                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   417 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   418 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket@domain.com>"             =   0 =  OK 
     *   419 - assertIsTrue  "<pointy.brackets2.with.empty.bracket@domain.com> name ()"             =   0 =  OK 
     *   420 - assertIsTrue  "{}.local.name.starts.with.empty.bracket@domain.com"                   =   0 =  OK 
     *   421 - assertIsTrue  "local.name.ends.with.empty.bracket{}@domain.com"                      =   0 =  OK 
     *   422 - assertIsTrue  "local.name.with.empty.bracket.before{}.point@domain.com"              =   0 =  OK 
     *   423 - assertIsTrue  "local.name.with.empty.bracket.after.{}point@domain.com"               =   0 =  OK 
     *   424 - assertIsTrue  "local.name.with.double.empty.bracket{}{}test@domain.com"              =   0 =  OK 
     *   425 - assertIsFalse "(comment {}) local.name.with.comment.with.empty.bracket@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   426 - assertIsTrue  "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   427 - assertIsTrue  "{}@empty.bracket.domain.com"                                          =   0 =  OK 
     *   428 - assertIsTrue  "{}{}{}{}{}{}@empty.bracket.domain.com"                                =   0 =  OK 
     *   429 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                           =   0 =  OK 
     *   430 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   431 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name {}"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   432 - assertIsFalse "[].local.name.starts.with.empty.bracket@domain.com"                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   433 - assertIsFalse "local.name.ends.with.empty.bracket[]@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   434 - assertIsFalse "local.name.with.empty.bracket.before[].point@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   435 - assertIsFalse "local.name.with.empty.bracket.after.[]point@domain.com"               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   436 - assertIsFalse "local.name.with.double.empty.bracket[][]test@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   437 - assertIsFalse "(comment []) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   438 - assertIsTrue  "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   439 - assertIsFalse "[]@empty.bracket.domain.com"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   440 - assertIsFalse "[][][][][][]@empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   441 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   442 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   443 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name []"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   444 - assertIsTrue  "999.local.name.starts.with.byte.overflow@domain.com"                  =   0 =  OK 
     *   445 - assertIsTrue  "local.name.ends.with.byte.overflow999@domain.com"                     =   0 =  OK 
     *   446 - assertIsTrue  "local.name.with.byte.overflow.before999.point@domain.com"             =   0 =  OK 
     *   447 - assertIsTrue  "local.name.with.byte.overflow.after.999point@domain.com"              =   0 =  OK 
     *   448 - assertIsTrue  "local.name.with.double.byte.overflow999999test@domain.com"            =   0 =  OK 
     *   449 - assertIsTrue  "(comment 999) local.name.with.comment.with.byte.overflow@domain.com"  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   450 - assertIsTrue  "\"quote999\".local.name.with.qoute.with.byte.overflow@domain.com"     =   1 =  OK 
     *   451 - assertIsTrue  "999@byte.overflow.domain.com"                                         =   0 =  OK 
     *   452 - assertIsTrue  "999999999999999999@byte.overflow.domain.com"                          =   0 =  OK 
     *   453 - assertIsTrue  "999.999.999.999.999.999@byte.overflow.domain.com"                     =   0 =  OK 
     *   454 - assertIsTrue  "name 999 <pointy.brackets1.with.byte.overflow@domain.com>"            =   0 =  OK 
     *   455 - assertIsTrue  "<pointy.brackets2.with.byte.overflow@domain.com> name 999"            =   0 =  OK 
     *   456 - assertIsTrue  "\"str\".local.name.starts.with.string@domain.com"                     =   1 =  OK 
     *   457 - assertIsFalse "local.name.ends.with.string\"str\"@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   458 - assertIsFalse "local.name.with.string.before\"str\".point@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   459 - assertIsFalse "local.name.with.string.after.\"str\"point@domain.com"                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   460 - assertIsFalse "local.name.with.double.string\"str\"\"str\"test@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   461 - assertIsFalse "(comment \"str\") local.name.with.comment.with.string@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   462 - assertIsFalse "\"quote\"str\"\".local.name.with.qoute.with.string@domain.com"        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   463 - assertIsTrue  "\"str\"@string.domain.com"                                            =   1 =  OK 
     *   464 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com"         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   465 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"    =   1 =  OK 
     *   466 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string@domain.com>"               =   0 =  OK 
     *   467 - assertIsTrue  "<pointy.brackets2.with.string@domain.com> name \"str\""               =   0 =  OK 
     *   468 - assertIsFalse "(comment).local.name.starts.with.comment@domain.com"                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   469 - assertIsTrue  "local.name.ends.with.comment(comment)@domain.com"                     =   6 =  OK 
     *   470 - assertIsFalse "local.name.with.comment.before(comment).point@domain.com"             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   471 - assertIsFalse "local.name.with.comment.after.(comment)point@domain.com"              = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   472 - assertIsFalse "local.name.with.double.comment(comment)(comment)test@domain.com"      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   473 - assertIsFalse "(comment (comment)) local.name.with.comment.with.comment@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   474 - assertIsTrue  "\"quote(comment)\".local.name.with.qoute.with.comment@domain.com"     =   1 =  OK 
     *   475 - assertIsFalse "(comment)@comment.domain.com"                                         =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   476 - assertIsFalse "(comment)(comment)(comment)(comment)@comment.domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   477 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   478 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment@domain.com>"            =   0 =  OK 
     *   479 - assertIsTrue  "<pointy.brackets2.with.comment@domain.com> name (comment)"            =   0 =  OK 
     *   480 - assertIsTrue  "domain.part@with0number0.com"                                         =   0 =  OK 
     *   481 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                        =   0 =  OK 
     *   482 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                         =   0 =  OK 
     *   483 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                         =   0 =  OK 
     *   484 - assertIsTrue  "domain.part@with.number0.before0.point.com"                           =   0 =  OK 
     *   485 - assertIsTrue  "domain.part@with.number0.after.0point.com"                            =   0 =  OK 
     *   486 - assertIsTrue  "domain.part@with9number9.com"                                         =   0 =  OK 
     *   487 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                        =   0 =  OK 
     *   488 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                         =   0 =  OK 
     *   489 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                         =   0 =  OK 
     *   490 - assertIsTrue  "domain.part@with.number9.before9.point.com"                           =   0 =  OK 
     *   491 - assertIsTrue  "domain.part@with.number9.after.9point.com"                            =   0 =  OK 
     *   492 - assertIsTrue  "domain.part.only.numbers@1234567890.com"                              =   0 =  OK 
     *   493 - assertIsTrue  "domain.part@with0123456789numbers.com"                                =   0 =  OK 
     *   494 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"               =   0 =  OK 
     *   495 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                =   0 =  OK 
     *   496 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                =   0 =  OK 
     *   497 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                  =   0 =  OK 
     *   498 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                   =   0 =  OK 
     *   499 - assertIsTrue  "domain.part@with-hyphen.com"                                          =   0 =  OK 
     *   500 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   501 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   502 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   503 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   504 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   505 - assertIsTrue  "domain.part@with_underscore.com"                                      =   0 =  OK 
     *   506 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   507 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   508 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   509 - assertIsFalse "domain.part@with.underscore.before_.point.com"                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   510 - assertIsFalse "domain.part@with.underscore.after._point.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   511 - assertIsFalse "domain.part@with&amp.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   512 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   513 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   514 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   515 - assertIsFalse "domain.part@with.amp.before&.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   516 - assertIsFalse "domain.part@with.amp.after.&point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   517 - assertIsFalse "domain.part@with*asterisk.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   518 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   519 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   520 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   521 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   522 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   523 - assertIsFalse "domain.part@with$dollar.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   524 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   525 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   526 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   527 - assertIsFalse "domain.part@with.dollar.before$.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   528 - assertIsFalse "domain.part@with.dollar.after.$point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   529 - assertIsFalse "domain.part@with=equality.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   530 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   531 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   532 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   533 - assertIsFalse "domain.part@with.equality.before=.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   534 - assertIsFalse "domain.part@with.equality.after.=point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   535 - assertIsFalse "domain.part@with!exclamation.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   536 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   537 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   538 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   539 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   540 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   541 - assertIsFalse "domain.part@with?question.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   542 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   543 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   544 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   545 - assertIsFalse "domain.part@with.question.before?.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   546 - assertIsFalse "domain.part@with.question.after.?point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   547 - assertIsFalse "domain.part@with`grave-accent.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   548 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   549 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   550 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   551 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   552 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   553 - assertIsFalse "domain.part@with#hash.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   554 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   555 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   556 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   557 - assertIsFalse "domain.part@with.hash.before#.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   558 - assertIsFalse "domain.part@with.hash.after.#point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   559 - assertIsFalse "domain.part@with%percentage.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   560 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   561 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   562 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   563 - assertIsFalse "domain.part@with.percentage.before%.point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   564 - assertIsFalse "domain.part@with.percentage.after.%point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   565 - assertIsFalse "domain.part@with|pipe.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   566 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   567 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   568 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   569 - assertIsFalse "domain.part@with.pipe.before|.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   570 - assertIsFalse "domain.part@with.pipe.after.|point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   571 - assertIsFalse "domain.part@with+plus.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   572 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   573 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   574 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   575 - assertIsFalse "domain.part@with.plus.before+.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse "domain.part@with.plus.after.+point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsFalse "domain.part@with{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   578 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   579 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   580 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   581 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   582 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   583 - assertIsFalse "domain.part@with}rightbracket.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   584 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   585 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   586 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   587 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   588 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   589 - assertIsFalse "domain.part@with(leftbracket.com"                                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   590 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   591 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   592 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   593 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   594 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   595 - assertIsFalse "domain.part@with)rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   596 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   597 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   598 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   599 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   600 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   601 - assertIsFalse "domain.part@with[leftbracket.com"                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   602 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   603 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   604 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   605 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                       =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   606 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   607 - assertIsFalse "domain.part@with]rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   608 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   609 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   610 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   611 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   612 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   613 - assertIsFalse "domain.part@with~tilde.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   614 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   615 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   616 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   617 - assertIsFalse "domain.part@with.tilde.before~.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   618 - assertIsFalse "domain.part@with.tilde.after.~point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   619 - assertIsFalse "domain.part@with^xor.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   620 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   621 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   622 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   623 - assertIsFalse "domain.part@with.xor.before^.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   624 - assertIsFalse "domain.part@with.xor.after.^point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   625 - assertIsFalse "domain.part@with:colon.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   626 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   627 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   628 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   629 - assertIsFalse "domain.part@with.colon.before:.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   630 - assertIsFalse "domain.part@with.colon.after.:point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   631 - assertIsFalse "domain.part@with space.com"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   632 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   633 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   634 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   635 - assertIsFalse "domain.part@with.space.before .point.com"                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   636 - assertIsFalse "domain.part@with.space.after. point.com"                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   637 - assertIsTrue  "domain.part@with999byte.overflow.com"                                 =   0 =  OK 
     *   638 - assertIsTrue  "domain.part@999with.byte.overflow.at.domain.start.com"                =   0 =  OK 
     *   639 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end1999.com"                 =   0 =  OK 
     *   640 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end2.com999"                 =   0 =  OK 
     *   641 - assertIsTrue  "domain.part@with.byte.overflow.before999.point.com"                   =   0 =  OK 
     *   642 - assertIsTrue  "domain.part@with.byte.overflow.after.999point.com"                    =   0 =  OK 
     *   643 - assertIsTrue  "domain.part@withxyzno.hex.number.com"                                 =   0 =  OK 
     *   644 - assertIsTrue  "domain.part@xyzwith.no.hex.number.at.domain.start.com"                =   0 =  OK 
     *   645 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end1xyz.com"                 =   0 =  OK 
     *   646 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end2.comxyz"                 =   0 =  OK 
     *   647 - assertIsTrue  "domain.part@with.no.hex.number.beforexyz.point.com"                   =   0 =  OK 
     *   648 - assertIsTrue  "domain.part@with.no.hex.number.after.xyzpoint.com"                    =   0 =  OK 
     *   649 - assertIsFalse "domain.part@with\"str\"string.com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   650 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   651 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   652 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   653 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   654 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   655 - assertIsFalse "domain.part@with(comment)comment.com"                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   656 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                =   6 =  OK 
     *   657 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   658 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                 =   6 =  OK 
     *   659 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   660 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                    = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   661 - assertIsFalse ",.local.name.starts.with.comma@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   662 - assertIsFalse "local.name.ends.with.comma,@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   663 - assertIsFalse "local.name.with.comma.before,.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   664 - assertIsFalse "local.name.with.comma.after.,point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   665 - assertIsFalse "local.name.with.double.comma,,test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   666 - assertIsFalse "(comment ,) local.name.with.comment.with.comma@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   667 - assertIsTrue  "\"quote,\".local.name.with.qoute.with.comma@domain.com"               =   1 =  OK 
     *   668 - assertIsFalse ",@comma.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   669 - assertIsFalse ",,,,,,@comma.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   670 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   671 - assertIsFalse "name , <pointy.brackets1.with.comma@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   672 - assertIsFalse "<pointy.brackets2.with.comma@domain.com> name ,"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   673 - assertIsFalse "domain.part@with,comma.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   674 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   676 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   677 - assertIsFalse "domain.part@with.comma.before,.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   678 - assertIsFalse "domain.part@with.comma.after.,point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   679 - assertIsFalse "§.local.name.starts.with.paragraph@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   680 - assertIsFalse "local.name.ends.with.paragraph§@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   681 - assertIsFalse "local.name.with.paragraph.before§.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   682 - assertIsFalse "local.name.with.paragraph.after.§point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   683 - assertIsFalse "local.name.with.double.paragraph§§test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   684 - assertIsFalse "(comment §) local.name.with.comment.with.paragraph@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   685 - assertIsFalse "\"quote§\".local.name.with.qoute.with.paragraph@domain.com"           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   686 - assertIsFalse "§@paragraph.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   687 - assertIsFalse "§§§§§§@paragraph.domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   688 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   689 - assertIsFalse "name § <pointy.brackets1.with.paragraph@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   690 - assertIsFalse "<pointy.brackets2.with.paragraph@domain.com> name §"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   691 - assertIsFalse "domain.part@with§paragraph.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   692 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   693 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   694 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   695 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   696 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   697 - assertIsTrue  "'.local.name.starts.with.quote@domain.com"                            =   0 =  OK 
     *   698 - assertIsTrue  "local.name.ends.with.quote'@domain.com"                               =   0 =  OK 
     *   699 - assertIsTrue  "local.name.with.quote.before'.point@domain.com"                       =   0 =  OK 
     *   700 - assertIsTrue  "local.name.with.quote.after.'point@domain.com"                        =   0 =  OK 
     *   701 - assertIsTrue  "local.name.with.double.quote''test@domain.com"                        =   0 =  OK 
     *   702 - assertIsFalse "(comment ') local.name.with.comment.with.quote@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   703 - assertIsTrue  "\"quote'\".local.name.with.qoute.with.quote@domain.com"               =   1 =  OK 
     *   704 - assertIsTrue  "'@quote.domain.com"                                                   =   0 =  OK 
     *   705 - assertIsTrue  "''''''@quote.domain.com"                                              =   0 =  OK 
     *   706 - assertIsTrue  "'.'.'.'.'.'@quote.domain.com"                                         =   0 =  OK 
     *   707 - assertIsFalse "name ' <pointy.brackets1.with.quote@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   708 - assertIsFalse "<pointy.brackets2.with.quote@domain.com> name '"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   709 - assertIsFalse "domain.part@with'quote.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   710 - assertIsFalse "domain.part@'with.quote.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   711 - assertIsFalse "domain.part@with.quote.at.domain.end1'.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   712 - assertIsFalse "domain.part@with.quote.at.domain.end2.com'"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   713 - assertIsFalse "domain.part@with.quote.before'.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   714 - assertIsFalse "domain.part@with.quote.after.'point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   715 - assertIsFalse "\".local.name.starts.with.double.quote@domain.com"                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   716 - assertIsFalse "local.name.ends.with.double.quote\"@domain.com"                       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   717 - assertIsFalse "local.name.with.double.quote.before\".point@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   718 - assertIsFalse "local.name.with.double.quote.after.\"point@domain.com"                =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   719 - assertIsFalse "local.name.with.double.double.quote\"\"test@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   720 - assertIsFalse "(comment \") local.name.with.comment.with.double.quote@domain.com"    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   721 - assertIsFalse "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com"       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   722 - assertIsFalse "\"@double.quote.domain.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   723 - assertIsTrue  "\".\".\".\".\".\"@double.quote.domain.com"                            =   1 =  OK 
     *   724 - assertIsTrue  "name \" <pointy.brackets1.with.double.quote@domain.com>"              =   0 =  OK 
     *   725 - assertIsTrue  "<pointy.brackets2.with.double.quote@domain.com> name \""              =   0 =  OK 
     *   726 - assertIsFalse "domain.part@with\"double.quote.com"                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   727 - assertIsFalse "domain.part@\"with.double.quote.at.domain.start.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   728 - assertIsFalse "domain.part@with.double.quote.at.domain.end1\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   729 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com\""                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   730 - assertIsFalse "domain.part@with.double.quote.before\".point.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   731 - assertIsFalse "domain.part@with.double.quote.after.\"point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   732 - assertIsFalse ")(.local.name.starts.with.false.bracket1@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   733 - assertIsFalse "local.name.ends.with.false.bracket1)(@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   734 - assertIsFalse "local.name.with.false.bracket1.before)(.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   735 - assertIsFalse "local.name.with.false.bracket1.after.)(point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   736 - assertIsFalse "local.name.with.double.false.bracket1)()(test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   737 - assertIsFalse "(comment )() local.name.with.comment.with.false.bracket1@domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   738 - assertIsTrue  "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com"     =   1 =  OK 
     *   739 - assertIsFalse ")(@false.bracket1.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   740 - assertIsFalse ")()()()()()(@false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   741 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   742 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1@domain.com>"            =   0 =  OK 
     *   743 - assertIsTrue  "<pointy.brackets2.with.false.bracket1@domain.com> name )("            =   0 =  OK 
     *   744 - assertIsFalse "domain.part@with)(false.bracket1.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   745 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   746 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   747 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   748 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   749 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   750 - assertIsTrue  "}{.local.name.starts.with.false.bracket2@domain.com"                  =   0 =  OK 
     *   751 - assertIsTrue  "local.name.ends.with.false.bracket2}{@domain.com"                     =   0 =  OK 
     *   752 - assertIsTrue  "local.name.with.false.bracket2.before}{.point@domain.com"             =   0 =  OK 
     *   753 - assertIsTrue  "local.name.with.false.bracket2.after.}{point@domain.com"              =   0 =  OK 
     *   754 - assertIsTrue  "local.name.with.double.false.bracket2}{}{test@domain.com"             =   0 =  OK 
     *   755 - assertIsFalse "(comment }{) local.name.with.comment.with.false.bracket2@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   756 - assertIsTrue  "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com"     =   1 =  OK 
     *   757 - assertIsTrue  "}{@false.bracket2.domain.com"                                         =   0 =  OK 
     *   758 - assertIsTrue  "}{}{}{}{}{}{@false.bracket2.domain.com"                               =   0 =  OK 
     *   759 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                          =   0 =  OK 
     *   760 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   761 - assertIsFalse "<pointy.brackets2.with.false.bracket2@domain.com> name }{"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   762 - assertIsFalse "domain.part@with}{false.bracket2.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   763 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   764 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   765 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   766 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   767 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   768 - assertIsFalse "][.local.name.starts.with.false.bracket3@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   769 - assertIsFalse "local.name.ends.with.false.bracket3][@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   770 - assertIsFalse "local.name.with.false.bracket3.before][.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   771 - assertIsFalse "local.name.with.false.bracket3.after.][point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse "local.name.with.double.false.bracket3][][test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   773 - assertIsFalse "(comment ][) local.name.with.comment.with.false.bracket3@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   774 - assertIsTrue  "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com"     =   1 =  OK 
     *   775 - assertIsFalse "][@false.bracket3.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   776 - assertIsFalse "][][][][][][@false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   777 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   778 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   779 - assertIsFalse "<pointy.brackets2.with.false.bracket3@domain.com> name ]["            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   780 - assertIsFalse "domain.part@with][false.bracket3.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   781 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   782 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   783 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   784 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   785 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   786 - assertIsFalse "><.local.name.starts.with.false.bracket4@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   787 - assertIsFalse "local.name.ends.with.false.bracket4><@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   788 - assertIsFalse "local.name.with.false.bracket4.before><.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   789 - assertIsFalse "local.name.with.false.bracket4.after.><point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   790 - assertIsFalse "local.name.with.double.false.bracket4><><test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   791 - assertIsFalse "(comment ><) local.name.with.comment.with.false.bracket4@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   792 - assertIsTrue  "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com"     =   1 =  OK 
     *   793 - assertIsFalse "><@false.bracket4.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   794 - assertIsFalse "><><><><><><@false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   795 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   796 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   797 - assertIsFalse "<pointy.brackets2.with.false.bracket4@domain.com> name ><"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   798 - assertIsFalse "domain.part@with\slash.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   799 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   800 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   801 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   802 - assertIsFalse "domain.part@with.slash.before\.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   803 - assertIsFalse "domain.part@with.slash.after.\point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   804 - assertIsFalse "domain.part@with><false.bracket4.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   805 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   806 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   807 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   808 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   809 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   810 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                    =   0 =  OK 
     *   811 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   812 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   813 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   814 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   815 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   816 - assertIsFalse "domain.part@with.consecutive.question??test.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   817 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   818 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   819 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   820 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   821 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   822 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   823 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   824 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   825 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   826 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   827 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   828 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   829 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   830 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   831 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   832 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   833 - assertIsFalse "domain.part@with.consecutive.space  test.com"                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   834 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   835 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   836 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   837 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   838 - assertIsFalse "domain.part@with.consecutive.double.quote\"\"test.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   839 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"               = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   840 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   841 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   842 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   843 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   844 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   845 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   846 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   847 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   848 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   849 - assertIsTrue  "domain.part.with.comment.with.underscore@(comment _)domain.com"       =   6 =  OK 
     *   850 - assertIsTrue  "domain.part.with.comment.with.amp@(comment &)domain.com"              =   6 =  OK 
     *   851 - assertIsTrue  "domain.part.with.comment.with.asterisk@(comment *)domain.com"         =   6 =  OK 
     *   852 - assertIsTrue  "domain.part.with.comment.with.dollar@(comment $)domain.com"           =   6 =  OK 
     *   853 - assertIsTrue  "domain.part.with.comment.with.equality@(comment =)domain.com"         =   6 =  OK 
     *   854 - assertIsTrue  "domain.part.with.comment.with.exclamation@(comment !)domain.com"      =   6 =  OK 
     *   855 - assertIsTrue  "domain.part.with.comment.with.question@(comment ?)domain.com"         =   6 =  OK 
     *   856 - assertIsTrue  "domain.part.with.comment.with.grave-accent@(comment `)domain.com"     =   6 =  OK 
     *   857 - assertIsTrue  "domain.part.with.comment.with.hash@(comment #)domain.com"             =   6 =  OK 
     *   858 - assertIsTrue  "domain.part.with.comment.with.percentage@(comment %)domain.com"       =   6 =  OK 
     *   859 - assertIsTrue  "domain.part.with.comment.with.pipe@(comment |)domain.com"             =   6 =  OK 
     *   860 - assertIsTrue  "domain.part.with.comment.with.plus@(comment +)domain.com"             =   6 =  OK 
     *   861 - assertIsTrue  "domain.part.with.comment.with.leftbracket@(comment {)domain.com"      =   6 =  OK 
     *   862 - assertIsTrue  "domain.part.with.comment.with.rightbracket@(comment })domain.com"     =   6 =  OK 
     *   863 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment ()domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   864 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ))domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   865 - assertIsFalse "domain.part.with.comment.with.leftbracket@(comment [)domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   866 - assertIsFalse "domain.part.with.comment.with.rightbracket@(comment ])domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   867 - assertIsFalse "domain.part.with.comment.with.lower.than@(comment <)domain.com"       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   868 - assertIsFalse "domain.part.with.comment.with.greater.than@(comment >)domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   869 - assertIsTrue  "domain.part.with.comment.with.tilde@(comment ~)domain.com"            =   6 =  OK 
     *   870 - assertIsTrue  "domain.part.with.comment.with.xor@(comment ^)domain.com"              =   6 =  OK 
     *   871 - assertIsFalse "domain.part.with.comment.with.colon@(comment :)domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   872 - assertIsTrue  "domain.part.with.comment.with.space@(comment  )domain.com"            =   6 =  OK 
     *   873 - assertIsFalse "domain.part.with.comment.with.comma@(comment ,)domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   874 - assertIsFalse "domain.part.with.comment.with.paragraph@(comment §)domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   875 - assertIsTrue  "domain.part.with.comment.with.double.quote@(comment ')domain.com"     =   6 =  OK 
     *   876 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment ())domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   877 - assertIsTrue  "domain.part.with.comment.with.empty.bracket@(comment {})domain.com"   =   6 =  OK 
     *   878 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment [])domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   879 - assertIsFalse "domain.part.with.comment.with.empty.bracket@(comment <>)domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   880 - assertIsFalse "domain.part.with.comment.with.false.bracket1@(comment )()domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   881 - assertIsTrue  "domain.part.with.comment.with.false.bracket2@(comment }{)domain.com"  =   6 =  OK 
     *   882 - assertIsFalse "domain.part.with.comment.with.false.bracket3@(comment ][)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   883 - assertIsFalse "domain.part.with.comment.with.false.bracket4@(comment ><)domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   884 - assertIsFalse "domain.part.with.comment.with.slash@(comment \)domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   885 - assertIsFalse "domain.part.with.comment.with.string@(comment \"str\")domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   886 - assertIsFalse "domain.part.only.underscore@_.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   887 - assertIsFalse "domain.part.only.amp@&.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   888 - assertIsFalse "domain.part.only.asterisk@*.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   889 - assertIsFalse "domain.part.only.dollar@$.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   890 - assertIsFalse "domain.part.only.equality@=.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   891 - assertIsFalse "domain.part.only.exclamation@!.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   892 - assertIsFalse "domain.part.only.question@?.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   893 - assertIsFalse "domain.part.only.grave-accent@`.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   894 - assertIsFalse "domain.part.only.hash@#.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   895 - assertIsFalse "domain.part.only.percentage@%.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   896 - assertIsFalse "domain.part.only.pipe@|.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   897 - assertIsFalse "domain.part.only.plus@+.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   898 - assertIsFalse "domain.part.only.leftbracket@{.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   899 - assertIsFalse "domain.part.only.rightbracket@}.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   900 - assertIsFalse "domain.part.only.leftbracket@(.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   901 - assertIsFalse "domain.part.only.rightbracket@).com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   902 - assertIsFalse "domain.part.only.leftbracket@[.com"                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   903 - assertIsFalse "domain.part.only.rightbracket@].com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   904 - assertIsFalse "domain.part.only.lower.than@<.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   905 - assertIsFalse "domain.part.only.greater.than@>.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   906 - assertIsFalse "domain.part.only.tilde@~.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   907 - assertIsFalse "domain.part.only.xor@^.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   908 - assertIsFalse "domain.part.only.colon@:.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   909 - assertIsFalse "domain.part.only.space@ .com"                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   910 - assertIsFalse "domain.part.only.dot@..com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   911 - assertIsFalse "domain.part.only.comma@,.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   912 - assertIsFalse "domain.part.only.at@@.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   913 - assertIsFalse "domain.part.only.paragraph@§.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   914 - assertIsFalse "domain.part.only.double.quote@'.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   915 - assertIsFalse "domain.part.only.double.quote@\".com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   916 - assertIsFalse "domain.part.only.double.quote@\\".com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   917 - assertIsFalse "domain.part.only.empty.bracket@().com"                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   918 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   919 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   920 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   921 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   922 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   923 - assertIsFalse "domain.part.only.false.bracket3@][.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   924 - assertIsFalse "domain.part.only.false.bracket4@><.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   925 - assertIsTrue  "domain.part.only.number0@0.com"                                       =   0 =  OK 
     *   926 - assertIsTrue  "domain.part.only.number9@9.com"                                       =   0 =  OK 
     *   927 - assertIsFalse "domain.part.only.slash@\.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   928 - assertIsTrue  "domain.part.only.byte.overflow@999.com"                               =   0 =  OK 
     *   929 - assertIsTrue  "domain.part.only.no.hex.number@xyz.com"                               =   0 =  OK 
     *   930 - assertIsFalse "domain.part.only.string@\"str\".com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   931 - assertIsFalse "domain.part.only.comment@(comment).com"                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   932 - assertIsFalse "DomainHyphen@-atstart"                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   933 - assertIsFalse "DomainHyphen@atend-.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   934 - assertIsFalse "DomainHyphen@bb.-cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   935 - assertIsFalse "DomainHyphen@bb.-cc-"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   936 - assertIsFalse "DomainHyphen@bb.cc-"                                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   937 - assertIsFalse "DomainHyphen@bb.c-c"                                                  =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   938 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   939 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                    =   0 =  OK 
     *   940 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   941 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   942 - assertIsFalse "DomainNotAllowedCharacter@example'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   943 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   944 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                 =   0 =  OK 
     *   945 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                   =   0 =  OK 
     *   946 - assertIsFalse "tld.starts.with.digit@domain.2com"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   947 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                      =   0 =  OK 
     *   948 - assertIsFalse "email@=qowaiv.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   949 - assertIsFalse "email@plus+.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   950 - assertIsFalse "email@domain.com>"                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   951 - assertIsFalse "email@mailto:domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   952 - assertIsFalse "mailto:mailto:email@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   953 - assertIsFalse "email@-domain.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   954 - assertIsFalse "email@domain-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   955 - assertIsFalse "email@domain.com-"                                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   956 - assertIsFalse "email@{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   957 - assertIsFalse "email@rightbracket}.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   958 - assertIsFalse "email@pp|e.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   959 - assertIsTrue  "email@domain.domain.domain.com.com"                                   =   0 =  OK 
     *   960 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                            =   0 =  OK 
     *   961 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                     =   0 =  OK 
     *   962 - assertIsFalse "unescaped white space@fake$com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   963 - assertIsFalse "\"Joe Smith email@domain.com"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   964 - assertIsFalse "\"Joe Smith' email@domain.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   965 - assertIsFalse "\"Joe Smith\"email@domain.com"                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   966 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   967 - assertIsTrue  "{john'doe}@my.server"                                                 =   0 =  OK 
     *   968 - assertIsTrue  "email@domain-one.com"                                                 =   0 =  OK 
     *   969 - assertIsTrue  "_______@domain.com"                                                   =   0 =  OK 
     *   970 - assertIsTrue  "?????@domain.com"                                                     =   0 =  OK 
     *   971 - assertIsFalse "local@?????.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   972 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                        =   1 =  OK 
     *   973 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                               =   1 =  OK 
     *   974 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                            =   0 =  OK 
     *   975 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                     =   0 =  OK 
     *   976 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   977 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"              =   0 =  OK 
     *   978 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   979 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                  =   1 =  OK 
     *   980 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   981 - assertIsFalse "top.level.domain.only@underscore._"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   982 - assertIsFalse "top.level.domain.only@amp.&"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   983 - assertIsFalse "top.level.domain.only@asterisk.*"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   984 - assertIsFalse "top.level.domain.only@dollar.$"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   985 - assertIsFalse "top.level.domain.only@equality.="                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   986 - assertIsFalse "top.level.domain.only@exclamation.!"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   987 - assertIsFalse "top.level.domain.only@question.?"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   988 - assertIsFalse "top.level.domain.only@grave-accent.`"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   989 - assertIsFalse "top.level.domain.only@hash.#"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   990 - assertIsFalse "top.level.domain.only@percentage.%"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   991 - assertIsFalse "top.level.domain.only@pipe.|"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   992 - assertIsFalse "top.level.domain.only@plus.+"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   993 - assertIsFalse "top.level.domain.only@leftbracket.{"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   994 - assertIsFalse "top.level.domain.only@rightbracket.}"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   995 - assertIsFalse "top.level.domain.only@leftbracket.("                                  = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   996 - assertIsFalse "top.level.domain.only@rightbracket.)"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   997 - assertIsFalse "top.level.domain.only@leftbracket.["                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   998 - assertIsFalse "top.level.domain.only@rightbracket.]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   999 - assertIsFalse "top.level.domain.only@lower.than.<"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1000 - assertIsFalse "top.level.domain.only@greater.than.>"                                 =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1001 - assertIsFalse "top.level.domain.only@tilde.~"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1002 - assertIsFalse "top.level.domain.only@xor.^"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1003 - assertIsFalse "top.level.domain.only@colon.:"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1004 - assertIsFalse "top.level.domain.only@space. "                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1005 - assertIsFalse "top.level.domain.only@dot.."                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1006 - assertIsFalse "top.level.domain.only@comma.,"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1007 - assertIsFalse "top.level.domain.only@at.@"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1008 - assertIsFalse "top.level.domain.only@paragraph.§"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1009 - assertIsFalse "top.level.domain.only@double.quote.'"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1010 - assertIsFalse "top.level.domain.only@double.quote.\"\""                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1011 - assertIsFalse "top.level.domain.only@forward.slash./"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1012 - assertIsFalse "top.level.domain.only@hyphen.-"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1013 - assertIsFalse "top.level.domain.only@empty.bracket.()"                               = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1014 - assertIsFalse "top.level.domain.only@empty.bracket.{}"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1015 - assertIsFalse "top.level.domain.only@empty.bracket.[]"                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1016 - assertIsFalse "top.level.domain.only@empty.bracket.<>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1017 - assertIsFalse "top.level.domain.only@empty.string1.\"\""                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1018 - assertIsFalse "top.level.domain.only@empty.string2.a\"\"b"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1019 - assertIsFalse "top.level.domain.only@double.empty.string1.\"\"\"\""                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1020 - assertIsFalse "top.level.domain.only@double.empty.string2.\"\".\"\""                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1021 - assertIsFalse "top.level.domain.only@false.bracket1.)("                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1022 - assertIsFalse "top.level.domain.only@false.bracket2.}{"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1023 - assertIsFalse "top.level.domain.only@false.bracket3.]["                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1024 - assertIsFalse "top.level.domain.only@false.bracket4.><"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1025 - assertIsFalse "top.level.domain.only@number0.0"                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1026 - assertIsFalse "top.level.domain.only@number9.9"                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1027 - assertIsFalse "top.level.domain.only@numbers.123"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1028 - assertIsFalse "top.level.domain.only@slash.\"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1029 - assertIsFalse "top.level.domain.only@string.\"str\""                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1030 - assertIsFalse "top.level.domain.only@comment.(comment)"                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *  1031 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1032 - assertIsFalse "\"\"@[1"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1033 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1034 - assertIsFalse "ABC.DEF@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1035 - assertIsFalse "ABC.DEF@]"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1036 - assertIsFalse "ABC.DEF@["                                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1037 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1038 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                   =   3 =  OK 
     *  1039 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                            =   2 =  OK 
     *  1040 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                              =   3 =  OK 
     *  1041 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                                    =   2 =  OK 
     *  1042 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1043 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1044 - assertIsFalse "ABC.DEF@([001.002.003.004])"                                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1045 - assertIsFalse "ABC.DEF[1.2.3.4]"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1046 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1047 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1048 - assertIsFalse "ABC.DEF@[][][][]"                                                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1049 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1050 - assertIsFalse "ABC.DEF@[....]"                                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1051 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1052 - assertIsFalse "1.2.3.4]@[5.6.7.8]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1053 - assertIsFalse "[1.2.3.4@[5.6.7.8]"                                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1054 - assertIsFalse "[1.2.3.4][5.6.7.8]@[9.10.11.12]"                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1055 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12]"                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1056 - assertIsFalse "[1.2.3.4]@[5.6.7.8]9.10.11.12]"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1057 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12["                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1058 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1059 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                              =   3 =  OK 
     *  1060 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1061 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1062 - assertIsFalse "ABC.DEF@[..]"                                                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1063 - assertIsFalse "ABC.DEF@[.2.3.4]"                                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1064 - assertIsFalse "ABC.DEF@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1065 - assertIsFalse "ABC.DEF@[1.2]"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1066 - assertIsFalse "ABC.DEF@[1.2.3]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1067 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                                  =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1068 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1069 - assertIsFalse "ABC.DEF@[1.2.3.]"                                                     =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *  1070 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1071 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1072 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1073 - assertIsFalse "ABC.DEF@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1074 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1075 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1076 - assertIsFalse "ABC.DEF@[12.34]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1077 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1078 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1079 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1080 - assertIsFalse "ABC.DEF@[-1.2.3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1081 - assertIsFalse "ABC.DEF@[1.-2.3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1082 - assertIsFalse "ABC.DEF@[1.2.-3.4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1083 - assertIsFalse "ABC.DEF@[1.2.3.-4]"                                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1084 - assertIsFalse "ip.v4.with.hyphen@[123.14-5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1085 - assertIsFalse "ip.v4.with.hyphen@[123.145-.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1086 - assertIsFalse "ip.v4.with.hyphen@[123.145.-178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1087 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90-]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1088 - assertIsFalse "ip.v4.with.hyphen@[123.145.178.90]-"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1089 - assertIsFalse "ip.v4.with.hyphen@[-123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1090 - assertIsFalse "ip.v4.with.hyphen@-[123.145.178.90]"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1091 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1092 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1093 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1094 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1095 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1096 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90]_"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1097 - assertIsFalse "ip.v4.with.underscore@_[123.145.178.90]"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1098 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1099 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1100 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1101 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1102 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1103 - assertIsFalse "ip.v4.with.amp@[123.145.178.90]&"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1104 - assertIsFalse "ip.v4.with.amp@&[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1105 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1106 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1107 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1108 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1109 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1110 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90]*"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1111 - assertIsFalse "ip.v4.with.asterisk@*[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1112 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1113 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1114 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1115 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1116 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1117 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90]$"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1118 - assertIsFalse "ip.v4.with.dollar@$[123.145.178.90]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1119 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1120 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1121 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1122 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1123 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1124 - assertIsFalse "ip.v4.with.equality@[123.145.178.90]="                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1125 - assertIsFalse "ip.v4.with.equality@=[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1126 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1127 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1128 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1129 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1130 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1131 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90]!"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1132 - assertIsFalse "ip.v4.with.exclamation@![123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1133 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1134 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1135 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1136 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1137 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1138 - assertIsFalse "ip.v4.with.question@[123.145.178.90]?"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1139 - assertIsFalse "ip.v4.with.question@?[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1140 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1141 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1142 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1143 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1144 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1145 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90]`"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1146 - assertIsFalse "ip.v4.with.grave-accent@`[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1147 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1148 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1149 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1150 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1151 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1152 - assertIsFalse "ip.v4.with.hash@[123.145.178.90]#"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1153 - assertIsFalse "ip.v4.with.hash@#[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1154 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1155 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1156 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1157 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1158 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1159 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90]%"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1160 - assertIsFalse "ip.v4.with.percentage@%[123.145.178.90]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1161 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1162 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1163 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1164 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1165 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1166 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90]|"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1167 - assertIsFalse "ip.v4.with.pipe@|[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1168 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1169 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1170 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1171 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1172 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1173 - assertIsFalse "ip.v4.with.plus@[123.145.178.90]+"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1174 - assertIsFalse "ip.v4.with.plus@+[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1175 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1176 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1177 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1178 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1179 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1180 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]{"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1181 - assertIsFalse "ip.v4.with.leftbracket@{[123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1182 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1183 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1184 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1185 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1186 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1187 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]}"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1188 - assertIsFalse "ip.v4.with.rightbracket@}[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1189 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1190 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1191 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1192 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1193 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1194 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]("                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1195 - assertIsFalse "ip.v4.with.leftbracket@([123.145.178.90]"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1196 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1197 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1198 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1199 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1200 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1201 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90])"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1202 - assertIsFalse "ip.v4.with.rightbracket@)[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1203 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1204 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1205 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1206 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1207 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1208 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]["                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1209 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1210 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1211 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1212 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1213 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1214 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1215 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1216 - assertIsFalse "ip.v4.with.rightbracket@][123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1217 - assertIsFalse "ip.v4.with.lower.than@[123.14<5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1218 - assertIsFalse "ip.v4.with.lower.than@[123.145<.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1219 - assertIsFalse "ip.v4.with.lower.than@[123.145.<178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1220 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90<]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1221 - assertIsFalse "ip.v4.with.lower.than@[<123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1222 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90]<"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1223 - assertIsFalse "ip.v4.with.lower.than@<[123.145.178.90]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1224 - assertIsFalse "ip.v4.with.greater.than@[123.14>5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1225 - assertIsFalse "ip.v4.with.greater.than@[123.145>.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1226 - assertIsFalse "ip.v4.with.greater.than@[123.145.>178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1227 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90>]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1228 - assertIsFalse "ip.v4.with.greater.than@[>123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1229 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90]>"                            =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1230 - assertIsFalse "ip.v4.with.greater.than@>[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1231 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1232 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1233 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1234 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1235 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1236 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90]~"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1237 - assertIsFalse "ip.v4.with.tilde@~[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1238 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1239 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1240 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1241 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1242 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1243 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1244 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1245 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1246 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1247 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1248 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1249 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1250 - assertIsFalse "ip.v4.with.xor@[123.145.178.90]^"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1251 - assertIsFalse "ip.v4.with.xor@^[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1252 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1253 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1254 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1255 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1256 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1257 - assertIsFalse "ip.v4.with.colon@[123.145.178.90]:"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1258 - assertIsFalse "ip.v4.with.colon@:[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1259 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1260 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1261 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1262 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1263 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1264 - assertIsFalse "ip.v4.with.space@[123.145.178.90] "                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1265 - assertIsFalse "ip.v4.with.space@ [123.145.178.90]"                                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1266 - assertIsFalse "ip.v4.with.dot@[123.14.5.178.90]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1267 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1268 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1269 - assertIsFalse "ip.v4.with.dot@[123.145.178.90.]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1270 - assertIsFalse "ip.v4.with.dot@[.123.145.178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1271 - assertIsFalse "ip.v4.with.dot@[123.145.178.90]."                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1272 - assertIsFalse "ip.v4.with.dot@.[123.145.178.90]"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1273 - assertIsFalse "ip.v4.with.comma@[123.14,5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1274 - assertIsFalse "ip.v4.with.comma@[123.145,.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1275 - assertIsFalse "ip.v4.with.comma@[123.145.,178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1276 - assertIsFalse "ip.v4.with.comma@[123.145.178.90,]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1277 - assertIsFalse "ip.v4.with.comma@[,123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1278 - assertIsFalse "ip.v4.with.comma@[123.145.178.90],"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1279 - assertIsFalse "ip.v4.with.comma@,[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1280 - assertIsFalse "ip.v4.with.at@[123.14@5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1281 - assertIsFalse "ip.v4.with.at@[123.145@.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1282 - assertIsFalse "ip.v4.with.at@[123.145.@178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1283 - assertIsFalse "ip.v4.with.at@[123.145.178.90@]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1284 - assertIsFalse "ip.v4.with.at@[@123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1285 - assertIsFalse "ip.v4.with.at@[123.145.178.90]@"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1286 - assertIsFalse "ip.v4.with.at@@[123.145.178.90]"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1287 - assertIsFalse "ip.v4.with.paragraph@[123.14§5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1288 - assertIsFalse "ip.v4.with.paragraph@[123.145§.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1289 - assertIsFalse "ip.v4.with.paragraph@[123.145.§178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1290 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90§]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1291 - assertIsFalse "ip.v4.with.paragraph@[§123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1292 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90]§"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1293 - assertIsFalse "ip.v4.with.paragraph@§[123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1294 - assertIsFalse "ip.v4.with.double.quote@[123.14'5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1295 - assertIsFalse "ip.v4.with.double.quote@[123.145'.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1296 - assertIsFalse "ip.v4.with.double.quote@[123.145.'178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1297 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90']"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1298 - assertIsFalse "ip.v4.with.double.quote@['123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1299 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]'"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1300 - assertIsFalse "ip.v4.with.double.quote@'[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1301 - assertIsFalse "ip.v4.with.double.quote@[123.14\"5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1302 - assertIsFalse "ip.v4.with.double.quote@[123.145\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1303 - assertIsFalse "ip.v4.with.double.quote@[123.145.\"178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1304 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90\"]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1305 - assertIsFalse "ip.v4.with.double.quote@[\"123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1306 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]\""                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1307 - assertIsFalse "ip.v4.with.double.quote@\"[123.145.178.90]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1308 - assertIsFalse "ip.v4.with.empty.bracket@[123.14()5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1309 - assertIsFalse "ip.v4.with.empty.bracket@[123.145().178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1310 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.()178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1311 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90()]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1312 - assertIsFalse "ip.v4.with.empty.bracket@[()123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1313 - assertIsTrue  "ip.v4.with.empty.bracket@[123.145.178.90]()"                          =   2 =  OK 
     *  1314 - assertIsTrue  "ip.v4.with.empty.bracket@()[123.145.178.90]"                          =   2 =  OK 
     *  1315 - assertIsFalse "ip.v4.with.empty.bracket@[123.14{}5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1316 - assertIsFalse "ip.v4.with.empty.bracket@[123.145{}.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1317 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.{}178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1318 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90{}]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1319 - assertIsFalse "ip.v4.with.empty.bracket@[{}123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1320 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]{}"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1321 - assertIsFalse "ip.v4.with.empty.bracket@{}[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1322 - assertIsFalse "ip.v4.with.empty.bracket@[123.14[]5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1323 - assertIsFalse "ip.v4.with.empty.bracket@[123.145[].178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1324 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.[]178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1325 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90[]]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1326 - assertIsFalse "ip.v4.with.empty.bracket@[[]123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1327 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90][]"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1328 - assertIsFalse "ip.v4.with.empty.bracket@[][123.145.178.90]"                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1329 - assertIsFalse "ip.v4.with.empty.bracket@[123.14<>5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1330 - assertIsFalse "ip.v4.with.empty.bracket@[123.145<>.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1331 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.<>178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1332 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90<>]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1333 - assertIsFalse "ip.v4.with.empty.bracket@[<>123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1334 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]<>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1335 - assertIsFalse "ip.v4.with.empty.bracket@<>[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1336 - assertIsFalse "ip.v4.with.false.bracket1@[123.14)(5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1337 - assertIsFalse "ip.v4.with.false.bracket1@[123.145)(.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1338 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.)(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1339 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90)(]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1340 - assertIsFalse "ip.v4.with.false.bracket1@[)(123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1341 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90])("                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1342 - assertIsFalse "ip.v4.with.false.bracket1@)([123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1343 - assertIsFalse "ip.v4.with.false.bracket2@[123.14}{5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1344 - assertIsFalse "ip.v4.with.false.bracket2@[123.145}{.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1345 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.}{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1346 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90}{]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1347 - assertIsFalse "ip.v4.with.false.bracket2@[}{123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1348 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90]}{"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1349 - assertIsFalse "ip.v4.with.false.bracket2@}{[123.145.178.90]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1350 - assertIsFalse "ip.v4.with.false.bracket3@[123.14][5.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1351 - assertIsFalse "ip.v4.with.false.bracket3@[123.145][.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1352 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.][178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1353 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90][]"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1354 - assertIsFalse "ip.v4.with.false.bracket3@[][123.145.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1355 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90]]["                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1356 - assertIsFalse "ip.v4.with.false.bracket3@][[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1357 - assertIsFalse "ip.v4.with.false.bracket4@[123.14><5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1358 - assertIsFalse "ip.v4.with.false.bracket4@[123.145><.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1359 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.><178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1360 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90><]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1361 - assertIsFalse "ip.v4.with.false.bracket4@[><123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1362 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90]><"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1363 - assertIsFalse "ip.v4.with.false.bracket4@><[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1364 - assertIsFalse "ip.v4.with.number0@[123.1405.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1365 - assertIsFalse "ip.v4.with.number0@[123.1450.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1366 - assertIsFalse "ip.v4.with.number0@[123.145.0178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1367 - assertIsFalse "ip.v4.with.number0@[123.145.178.900]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1368 - assertIsFalse "ip.v4.with.number0@[0123.145.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1369 - assertIsFalse "ip.v4.with.number0@[123.145.178.90]0"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1370 - assertIsFalse "ip.v4.with.number0@0[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1371 - assertIsFalse "ip.v4.with.number9@[123.1495.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1372 - assertIsFalse "ip.v4.with.number9@[123.1459.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1373 - assertIsFalse "ip.v4.with.number9@[123.145.9178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1374 - assertIsFalse "ip.v4.with.number9@[123.145.178.909]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1375 - assertIsFalse "ip.v4.with.number9@[9123.145.178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1376 - assertIsFalse "ip.v4.with.number9@[123.145.178.90]9"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1377 - assertIsFalse "ip.v4.with.number9@9[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1378 - assertIsFalse "ip.v4.with.numbers@[123.1401234567895.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1379 - assertIsFalse "ip.v4.with.numbers@[123.1450123456789.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1380 - assertIsFalse "ip.v4.with.numbers@[123.145.0123456789178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1381 - assertIsFalse "ip.v4.with.numbers@[123.145.178.900123456789]"                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1382 - assertIsFalse "ip.v4.with.numbers@[0123456789123.145.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1383 - assertIsFalse "ip.v4.with.numbers@[123.145.178.90]0123456789"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1384 - assertIsFalse "ip.v4.with.numbers@0123456789[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1385 - assertIsFalse "ip.v4.with.slash@[123.14\5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1386 - assertIsFalse "ip.v4.with.slash@[123.145\.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1387 - assertIsFalse "ip.v4.with.slash@[123.145.\178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1388 - assertIsFalse "ip.v4.with.slash@[123.145.178.90\]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1389 - assertIsFalse "ip.v4.with.slash@[\123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1390 - assertIsFalse "ip.v4.with.slash@[123.145.178.90]\"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1391 - assertIsFalse "ip.v4.with.slash@\[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1392 - assertIsFalse "ip.v4.with.byte.overflow@[123.149995.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1393 - assertIsFalse "ip.v4.with.byte.overflow@[123.145999.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1394 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.999178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1395 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90999]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1396 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90]999"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1397 - assertIsFalse "ip.v4.with.byte.overflow@[999123.145.178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1398 - assertIsFalse "ip.v4.with.byte.overflow@999[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1399 - assertIsFalse "ip.v4.with.no.hex.number@[123.14xyz5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1400 - assertIsFalse "ip.v4.with.no.hex.number@[123.145xyz.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1401 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.xyz178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1402 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90xyz]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1403 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90]xyz"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1404 - assertIsFalse "ip.v4.with.no.hex.number@[xyz123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1405 - assertIsFalse "ip.v4.with.no.hex.number@xyz[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1406 - assertIsFalse "ip.v4.with.string@[123.14\"str\"5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1407 - assertIsFalse "ip.v4.with.string@[123.145\"str\".178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1408 - assertIsFalse "ip.v4.with.string@[123.145.\"str\"178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1409 - assertIsFalse "ip.v4.with.string@[123.145.178.90\"str\"]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1410 - assertIsFalse "ip.v4.with.string@[123.145.178.90]\"str\""                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1411 - assertIsFalse "ip.v4.with.string@[\"str\"123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1412 - assertIsFalse "ip.v4.with.string@\"str\"[123.145.178.90]"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1413 - assertIsFalse "ip.v4.with.comment@[123.14(comment)5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1414 - assertIsFalse "ip.v4.with.comment@[123.145(comment).178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1415 - assertIsFalse "ip.v4.with.comment@[123.145.(comment)178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1416 - assertIsFalse "ip.v4.with.comment@[123.145.178.90(comment)]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1417 - assertIsTrue  "ip.v4.with.comment@[123.145.178.90](comment)"                         =   2 =  OK 
     *  1418 - assertIsFalse "ip.v4.with.comment@[(comment)123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1419 - assertIsTrue  "ip.v4.with.comment@(comment)[123.145.178.90]"                         =   2 =  OK 
     *  1420 - assertIsTrue  "email@[123.123.123.123]"                                              =   2 =  OK 
     *  1421 - assertIsFalse "email@111.222.333"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1422 - assertIsFalse "email@111.222.333.256"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1423 - assertIsFalse "email@[123.123.123.123"                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1424 - assertIsFalse "email@[123.123.123].123"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1425 - assertIsFalse "email@123.123.123.123]"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1426 - assertIsFalse "email@123.123.[123.123]"                                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1427 - assertIsFalse "ab@988.120.150.10"                                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1428 - assertIsFalse "ab@120.256.256.120"                                                   =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1429 - assertIsFalse "ab@120.25.1111.120"                                                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1430 - assertIsFalse "ab@[188.120.150.10"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1431 - assertIsFalse "ab@188.120.150.10]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1432 - assertIsFalse "ab@[188.120.150.10].com"                                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1433 - assertIsTrue  "ab@188.120.150.10"                                                    =   2 =  OK 
     *  1434 - assertIsTrue  "ab@1.0.0.10"                                                          =   2 =  OK 
     *  1435 - assertIsTrue  "ab@120.25.254.120"                                                    =   2 =  OK 
     *  1436 - assertIsTrue  "ab@01.120.150.1"                                                      =   2 =  OK 
     *  1437 - assertIsTrue  "ab@88.120.150.021"                                                    =   2 =  OK 
     *  1438 - assertIsTrue  "ab@88.120.150.01"                                                     =   2 =  OK 
     *  1439 - assertIsTrue  "email@123.123.123.123"                                                =   2 =  OK 
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1440 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                           =   4 =  OK 
     *  1441 - assertIsFalse "ABC.DEF@[IP"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1442 - assertIsFalse "ABC.DEF@[IPv6]"                                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1443 - assertIsFalse "ABC.DEF@[IPv6:]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1444 - assertIsFalse "ABC.DEF@[IPv6:"                                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1445 - assertIsFalse "ABC.DEF@[IPv6::]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1446 - assertIsFalse "ABC.DEF@[IPv6::"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1447 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1448 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1449 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1450 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1451 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1452 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                 =   4 =  OK 
     *  1453 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                               =   4 =  OK 
     *  1454 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                            =   4 =  OK 
     *  1455 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                           =   4 =  OK 
     *  1456 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                           =   4 =  OK 
     *  1457 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                         =   4 =  OK 
     *  1458 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1459 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                       =   4 =  OK 
     *  1460 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                     =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1461 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                               =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1462 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                             =   4 =  OK 
     *  1463 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1464 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1465 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1466 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1467 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1468 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                        =   4 =  OK 
     *  1469 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1470 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1472 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1473 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1474 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1475 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1476 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1477 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1478 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1479 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1480 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1481 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1482 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1483 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1484 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1485 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1486 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1487 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1488 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1489 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1490 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1491 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1492 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1493 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1494 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1495 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1496 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1497 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1498 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1499 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1500 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1501 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1502 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1503 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1504 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1505 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1507 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1508 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1509 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1510 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1511 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1512 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1513 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1514 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1515 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1516 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1517 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1518 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1519 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1520 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1521 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1522 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1523 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1524 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1525 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1526 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1527 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1528 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1529 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1530 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1531 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1532 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1533 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1534 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1535 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1536 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1537 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1538 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1539 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1540 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1541 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1542 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1543 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1544 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1545 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1546 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1547 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1548 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1549 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1550 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1551 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1552 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1553 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1554 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1555 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1556 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1557 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1558 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1559 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1560 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1561 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1562 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1563 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1564 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1565 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1567 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1568 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1570 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1571 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1573 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1574 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1575 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1576 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1577 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1578 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1579 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1580 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1581 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1582 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1583 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1584 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1585 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1586 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1587 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1588 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1589 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1590 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1591 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1592 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1594 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1595 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1596 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1597 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1598 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1599 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1600 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1601 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1602 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1603 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1604 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1605 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1606 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1607 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1608 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1609 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1610 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1611 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1612 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1613 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1614 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                              =   4 =  OK 
     *  1615 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1616 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1617 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                              =   4 =  OK 
     *  1618 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1619 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1620 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1621 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1622 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1623 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1624 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1625 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1626 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1627 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1628 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1629 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1630 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1631 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1632 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1633 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1634 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1635 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1636 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1637 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1638 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1639 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1640 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1641 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1642 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1643 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1644 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1645 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1646 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1647 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1648 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1649 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1650 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1651 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1652 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1653 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1654 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1655 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1656 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1657 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1658 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1659 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1660 - assertIsFalse "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1661 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\""                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1662 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1663 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1664 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1665 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1666 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                     =   4 =  OK 
     *  1667 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                     =   4 =  OK 
     *  1668 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1669 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1670 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1671 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1672 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1673 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1674 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1675 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1676 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1677 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1678 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1679 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1680 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1681 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1682 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1683 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1684 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1685 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1686 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1687 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1688 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1689 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1690 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1691 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1692 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1693 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1694 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1695 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1696 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1697 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1698 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1699 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1700 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1701 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1702 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1703 - assertIsFalse "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1704 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]]["                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1705 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1706 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1707 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1708 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1709 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1710 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1711 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1712 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1713 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1714 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1715 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1716 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1717 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1718 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1719 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1720 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1721 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1722 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1723 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1724 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1725 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:29993:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1726 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1727 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1728 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1729 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1730 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1731 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1732 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1733 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1734 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1735 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1736 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1737 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1738 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1739 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1740 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1741 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1742 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1743 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1744 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1745 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1746 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1747 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1748 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                    =   4 =  OK 
     *  1749 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                    =   4 =  OK 
     *  1750 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1751 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                        =   4 =  OK 
     *  1752 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1753 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1754 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1755 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1756 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1757 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"               =   4 =  OK 
     *  1758 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"               =   4 =  OK 
     *  1759 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"               =   4 =  OK 
     *  1760 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                              =   4 =  OK 
     *  1761 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                               =   4 =  OK 
     *  1762 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1763 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1764 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1765 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1766 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                       =   4 =  OK 
     *  1767 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                       =   4 =  OK 
     *  1768 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                           =   4 =  OK 
     *  1769 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                           =   4 =  OK 
     *  1770 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1771 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                        =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1772 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1773 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1774 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1775 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1776 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1777 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1778 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.0001]"                                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1779 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- ip4 without brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1780 - assertIsTrue  "ip4.without.brackets.ok1@127.0.0.1"                                   =   2 =  OK 
     *  1781 - assertIsTrue  "ip4.without.brackets.ok2@0.0.0.0"                                     =   2 =  OK 
     *  1782 - assertIsFalse "ip4.without.brackets.but.with.space.at.end@127.0.0.1 "                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1783 - assertIsFalse "ip4.without.brackets.byte.overflow@127.0.999.1"                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1784 - assertIsFalse "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1"           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1785 - assertIsFalse "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1"           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1786 - assertIsFalse "ip4.without.brackets.negative.number@127.0.-1.1"                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1787 - assertIsFalse "ip4.without.brackets.point.error1@127.0..0.1"                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1788 - assertIsFalse "ip4.without.brackets.point.error1@127...1"                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1789 - assertIsFalse "ip4.without.brackets.point.error2@127001"                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1790 - assertIsFalse "ip4.without.brackets.point.error3@127.0.0."                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1791 - assertIsFalse "ip4.without.brackets.character.error@127.0.A.1"                       =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1792 - assertIsFalse "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1"            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1793 - assertIsTrue  "ip4.without.brackets.normal.tld1@127.0.0.1.com"                       =   0 =  OK 
     *  1794 - assertIsTrue  "ip4.without.brackets.normal.tld2@127.0.99.1.com"                      =   0 =  OK 
     *  1795 - assertIsTrue  "ip4.without.brackets.normal.tld3@127.0.A.1.com"                       =   0 =  OK 
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *  1796 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1797 - assertIsTrue  "\"ABC\".\"DEF\"@GHI.DE"                                               =   1 =  OK 
     *  1798 - assertIsFalse "-\"ABC\".\"DEF\"@GHI.DE"                                              = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
     *  1799 - assertIsFalse "\"ABC\"-.\"DEF\"@GHI.DE"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1800 - assertIsFalse "\"ABC\".-\"DEF\"@GHI.DE"                                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1801 - assertIsFalse ".\"ABC\".\"DEF\"@GHI.DE"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1802 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1803 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1804 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                 =   1 =  OK 
     *  1805 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1806 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1807 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1808 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1809 - assertIsFalse "\"\"@GHI.DE"                                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1810 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1811 - assertIsFalse "A@G\"HI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1812 - assertIsFalse "\"@GHI.DE"                                                            =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1813 - assertIsFalse "ABC.DEF.\""                                                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1814 - assertIsFalse "ABC.DEF@\"\""                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1815 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1816 - assertIsFalse "ABC.DEF@GHI.DE\""                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1817 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1818 - assertIsFalse "\"Escape.Sequenz.Ende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1819 - assertIsFalse "ABC.DEF\"GHI.DE"                                                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1820 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1821 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1822 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1823 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1824 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1825 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                  =   1 =  OK 
     *  1826 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                           =   1 =  OK 
     *  1827 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1828 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1829 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1830 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1831 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1832 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1833 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1834 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1835 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1836 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1837 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1838 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1839 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1840 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1841 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1842 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1843 - assertIsFalse "\"\".ABC.DEF@GHI.DE"                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1844 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1845 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1846 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                               =   1 =  OK 
     *  1847 - assertIsFalse "\"Ende.am.Eingabeende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1848 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1849 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                 =   1 =  OK 
     *  1850 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1851 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1852 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1853 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                =   1 =  OK 
     *  1854 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                        =   1 =  OK 
     *  1855 - assertIsFalse "\"Joe Smith\" email@domain.com"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1856 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1857 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *  1858 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                     =   6 =  OK 
     *  1859 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1860 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                     =   6 =  OK 
     *  1861 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                 =   6 =  OK 
     *  1862 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                           =   6 =  OK 
     *  1863 - assertIsFalse "ABC.DEF@             (MNO)"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1864 - assertIsFalse "ABC.DEF@   .         (MNO)"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1865 - assertIsFalse "ABC.DEF              (MNO)"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1866 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                           =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1867 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1868 - assertIsFalse "ABC.DEF@GHI.JKL          "                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1869 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1870 - assertIsFalse "("                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1871 - assertIsFalse ")"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1872 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                   =   6 =  OK 
     *  1873 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                    =   6 =  OK 
     *  1874 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                    =   6 =  OK 
     *  1875 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                    =   6 =  OK 
     *  1876 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                    =   6 =  OK 
     *  1877 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1878 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1879 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1880 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1881 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1882 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1883 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1884 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1885 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1886 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1887 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1888 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1889 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1890 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                   = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1891 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                     =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1892 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1893 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1894 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1895 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1896 - assertIsFalse "ABC(DEF@GHI.JKL"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1897 - assertIsFalse "ABC.DEF@GHI)JKL"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1898 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1899 - assertIsFalse "ABC.DEF)@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1900 - assertIsFalse "ABC(DEF@GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1901 - assertIsFalse "ABC(DEF.GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1902 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1903 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1904 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1905 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1906 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1907 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1908 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                           =   2 =  OK 
     *  1909 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                          = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  1910 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                           =   2 =  OK 
     *  1911 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                       =   2 =  OK 
     *  1912 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1913 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                               =   4 =  OK 
     *  1914 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                               =   4 =  OK 
     *  1915 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                           =   4 =  OK 
     *  1916 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1917 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                            =   6 =  OK 
     *  1918 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                            =   6 =  OK 
     *  1919 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                            = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1920 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1921 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1922 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1923 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1924 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1925 - assertIsFalse "john.smith@exampl(comment)e.com"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1926 - assertIsFalse "john.s(comment)mith@example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1927 - assertIsFalse "john.smith(comment)@(comment)example.com"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1928 - assertIsFalse "john.smith(com@ment)example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1929 - assertIsFalse "email( (nested) )@plus.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1930 - assertIsFalse "email)mirror(@plus.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1931 - assertIsFalse "email@plus.com (not closed comment"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1932 - assertIsFalse "email(with @ in comment)plus.com"                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1933 - assertIsTrue  "email@domain.com (joe Smith)"                                         =   6 =  OK 
     *  1934 - assertIsFalse "a@abc(bananas)def.com"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1935 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                            =   0 =  OK 
     *  1936 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                            =   0 =  OK 
     *  1937 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                             =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1938 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                             =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1939 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                        =   0 =  OK 
     *  1940 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                  =   1 =  OK 
     *  1941 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                          =   1 =  OK 
     *  1942 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1943 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1944 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1945 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1946 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1947 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1948 - assertIsFalse "ABC DEF <A@A>"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1949 - assertIsFalse "<A@A> ABC DEF"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1950 - assertIsFalse "ABC DEF <>"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1951 - assertIsFalse "<> ABC DEF"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1952 - assertIsFalse "<"                                                                    =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1953 - assertIsFalse ">"                                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1954 - assertIsFalse "<         >"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1955 - assertIsFalse "< <     > >"                                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1956 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                    =   0 =  OK 
     *  1957 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                   = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1958 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1959 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                   =   0 =  OK 
     *  1960 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1961 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                   =   0 =  OK 
     *  1962 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1963 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                           =   6 =  OK 
     *  1964 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1965 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                          = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1966 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                          =   6 =  OK 
     *  1967 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                          = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1968 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                          =   6 =  OK 
     *  1969 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                          = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
     *  1970 - assertIsTrue  "Joe Smith <email@domain.com>"                                         =   0 =  OK 
     *  1971 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1972 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1973 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1974 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1975 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"      =   9 =  OK 
     *  1976 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "     =   9 =  OK 
     *  1977 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1978 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1979 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1980 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1981 - assertIsFalse "Test |<gaaf <email@domain.com>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1982 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1983 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1984 - assertIsFalse "<null>@mail.com"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1985 - assertIsFalse "email.adress@domain.com <display name>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1986 - assertIsFalse "eimail.adress@domain.com <eimail.adress@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1987 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1988 - assertIsFalse "<eimail>.<adress>@domain.com"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1989 - assertIsFalse "<eimail>.<adress> email.adress@domain.com"                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *  1990 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1991 - assertIsFalse "A@B.C"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1992 - assertIsFalse "A@COM"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1993 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                      =   0 =  OK 
     *  1994 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  1995 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1996 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1997 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  1998 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1999 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2000 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2001 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld> True64 " =   0 =  OK 
     *  2002 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2003 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain1.tld>" =   0 =  OK 
     *  2004 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain1.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2005 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *  2006 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  2007 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2008 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2009 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2010 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2011 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2012 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2013 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2014 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  2015 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2016 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2017 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  2018 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2019 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
     *  2020 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  2021 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  2022 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2023 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2024 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  2025 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2026 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2027 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2028 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
     *  2029 - assertIsTrue  "email@domain.topleveldomain"                                          =   0 =  OK 
     *  2030 - assertIsTrue  "email@email.email.mydomain"                                           =   0 =  OK 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     *  2031 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                     =   6 =  OK 
     *  2032 - assertIsTrue  "\"MaxMustermann\"@example.com"                                        =   1 =  OK 
     *  2033 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                           =   1 =  OK 
     *  2034 - assertIsTrue  "\".John.Doe\"@example.com"                                            =   1 =  OK 
     *  2035 - assertIsTrue  "\"John.Doe.\"@example.com"                                            =   1 =  OK 
     *  2036 - assertIsTrue  "\"John..Doe\"@example.com"                                            =   1 =  OK 
     *  2037 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  2038 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  2039 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  2040 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  2041 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  2042 - assertIsTrue  "joeuser+tag@example.com"                                              =   0 =  OK 
     *  2043 - assertIsTrue  "jsmith@[192.168.2.1]"                                                 =   2 =  OK 
     *  2044 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                            =   4 =  OK 
     *  2045 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                    =   0 =  OK 
     *  2046 - assertIsTrue  "Marc Dupont <md118@example.com>"                                      =   0 =  OK 
     *  2047 - assertIsTrue  "simple@example.com"                                                   =   0 =  OK 
     *  2048 - assertIsTrue  "very.common@example.com"                                              =   0 =  OK 
     *  2049 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                       =   0 =  OK 
     *  2050 - assertIsTrue  "other.email-with-hyphen@example.com"                                  =   0 =  OK 
     *  2051 - assertIsTrue  "fully-qualified-domain@example.com"                                   =   0 =  OK 
     *  2052 - assertIsTrue  "user.name+tag+sorting@example.com"                                    =   0 =  OK 
     *  2053 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  2054 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                     =   0 =  OK 
     *  2055 - assertIsTrue  "x@example.com"                                                        =   0 =  OK 
     *  2056 - assertIsTrue  "info@firma.org"                                                       =   0 =  OK 
     *  2057 - assertIsTrue  "example-indeed@strange-example.com"                                   =   0 =  OK 
     *  2058 - assertIsTrue  "admin@mailserver1"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2059 - assertIsTrue  "example@s.example"                                                    =   0 =  OK 
     *  2060 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  2061 - assertIsTrue  "mailhost!username@example.org"                                        =   0 =  OK 
     *  2062 - assertIsTrue  "user%example.com@example.org"                                         =   0 =  OK 
     *  2063 - assertIsTrue  "joe25317@NOSPAMexample.com"                                           =   0 =  OK 
     *  2064 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  2065 - assertIsTrue  "nama@contoh.com"                                                      =   0 =  OK 
     *  2066 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  2067 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2068 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2069 - assertIsFalse "Abc..123@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2070 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2071 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2072 - assertIsFalse "just\"not\"right@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2073 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2074 - assertIsFalse "this\ still\\"not\\allowed@example.com"                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2075 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2076 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"   =   0 =  OK 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     *  2077 - assertIsFalse "nolocalpart.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2078 - assertIsFalse "test@example.com test"                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2079 - assertIsFalse "user  name@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2080 - assertIsFalse "user   name@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2081 - assertIsFalse "example.@example.co.uk"                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2082 - assertIsFalse "example@example@example.co.uk"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2083 - assertIsFalse "(test_exampel@example.fr}"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2084 - assertIsFalse "example(example)example@example.co.uk"                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2085 - assertIsFalse ".example@localhost"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2086 - assertIsFalse "ex\ample@localhost"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2087 - assertIsFalse "example@local\host"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2088 - assertIsFalse "example@localhost."                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2089 - assertIsFalse "user name@example.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2090 - assertIsFalse "username@ example . com"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2091 - assertIsFalse "example@(fake}.com"                                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2092 - assertIsFalse "example@(fake.com"                                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2093 - assertIsTrue  "username@example.com"                                                 =   0 =  OK 
     *  2094 - assertIsTrue  "usern.ame@example.com"                                                =   0 =  OK 
     *  2095 - assertIsFalse "user[na]me@example.com"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2096 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2097 - assertIsFalse "\"\\"@iana.org"                                                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2098 - assertIsFalse "\"test\"test@iana.org"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2099 - assertIsFalse "\"test\"\"test\"@iana.org"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2100 - assertIsTrue  "\"test\".\"test\"@iana.org"                                           =   1 =  OK 
     *  2101 - assertIsTrue  "\"test\".test@iana.org"                                               =   1 =  OK 
     *  2102 - assertIsFalse "\"test\\"@iana.org"                                                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2103 - assertIsFalse "\r\ntest@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2104 - assertIsFalse "\r\n test@iana.org"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2105 - assertIsFalse "\r\n \r\ntest@iana.org"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2106 - assertIsFalse "\r\n \r\n test@iana.org"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2107 - assertIsFalse "test@iana.org \r\n"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2108 - assertIsFalse "test@iana.org \r\n "                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2109 - assertIsFalse "test@iana.org \r\n \r\n"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2110 - assertIsFalse "test@iana.org \r\n\r\n"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2111 - assertIsFalse "test@iana.org  \r\n\r\n "                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2112 - assertIsFalse "test@iana/icann.org"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2113 - assertIsFalse "test@foo;bar.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2114 - assertIsFalse "a@test.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2115 - assertIsFalse "comment)example@example.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2116 - assertIsFalse "comment(example))@example.com"                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2117 - assertIsFalse "example@example)comment.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2118 - assertIsFalse "example@example(comment)).com"                                        = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  2119 - assertIsFalse "example@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2120 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2121 - assertIsFalse "exam(ple@exam).ple"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2122 - assertIsFalse "example@(example))comment.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2123 - assertIsTrue  "example@example.com"                                                  =   0 =  OK 
     *  2124 - assertIsTrue  "example@example.co.uk"                                                =   0 =  OK 
     *  2125 - assertIsTrue  "example_underscore@example.fr"                                        =   0 =  OK 
     *  2126 - assertIsTrue  "exam'ple@example.com"                                                 =   0 =  OK 
     *  2127 - assertIsTrue  "exam\ ple@example.com"                                                =   0 =  OK 
     *  2128 - assertIsFalse "example((example))@fakedfake.co.uk"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2129 - assertIsFalse "example@faked(fake).co.uk"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2130 - assertIsTrue  "example+@example.com"                                                 =   0 =  OK 
     *  2131 - assertIsTrue  "example@with-hyphen.example.com"                                      =   0 =  OK 
     *  2132 - assertIsTrue  "with-hyphen@example.com"                                              =   0 =  OK 
     *  2133 - assertIsTrue  "example@1leadingnum.example.com"                                      =   0 =  OK 
     *  2134 - assertIsTrue  "1leadingnum@example.com"                                              =   0 =  OK 
     *  2135 - assertIsTrue  "инфо@письмо.рф"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2136 - assertIsTrue  "\"username\"@example.com"                                             =   1 =  OK 
     *  2137 - assertIsTrue  "\"user.name\"@example.com"                                            =   1 =  OK 
     *  2138 - assertIsTrue  "\"user name\"@example.com"                                            =   1 =  OK 
     *  2139 - assertIsTrue  "\"user@name\"@example.com"                                            =   1 =  OK 
     *  2140 - assertIsFalse "\"\a\"@iana.org"                                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2141 - assertIsTrue  "\"test\ test\"@iana.org"                                              =   1 =  OK 
     *  2142 - assertIsFalse "\"\"@iana.org"                                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2143 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2144 - assertIsTrue  "\"\\"\"@iana.org"                                                     =   1 =  OK 
     *  2145 - assertIsTrue  "example@localhost"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     *  2146 - assertIsFalse "<')))><@fish.left.com"                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2147 - assertIsFalse "><(((*>@fish.right.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2148 - assertIsFalse " check@this.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2149 - assertIsFalse " email@example.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2150 - assertIsFalse ".....@a...."                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2151 - assertIsFalse "..@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2152 - assertIsFalse "..@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2153 - assertIsTrue  "\"test....\"@gmail.com"                                               =   1 =  OK 
     *  2154 - assertIsFalse "test....@gmail.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2155 - assertIsTrue  "name@xn--4ca9at.at"                                                   =   0 =  OK 
     *  2156 - assertIsTrue  "simon-@hotmail.com"                                                   =   0 =  OK 
     *  2157 - assertIsTrue  "!@mydomain.net"                                                       =   0 =  OK 
     *  2158 - assertIsFalse "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]"                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2159 - assertIsFalse "a-b'c_d.e@f-g.h"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2160 - assertIsFalse "a-b'c_d.@f-g.h"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2161 - assertIsFalse "a-b'c_d.e@f-.h"                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2162 - assertIsTrue  "\"root\"@example.com"                                                 =   1 =  OK 
     *  2163 - assertIsTrue  "root@example.com"                                                     =   0 =  OK 
     *  2164 - assertIsFalse ".@s.dd"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2165 - assertIsFalse ".@s.dd"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2166 - assertIsFalse ".a@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2167 - assertIsFalse ".a@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2168 - assertIsFalse ".abc@somedomain.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2169 - assertIsFalse ".dot@example.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2170 - assertIsFalse ".email@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2171 - assertIsFalse ".journaldev@journaldev.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2172 - assertIsFalse ".username@yahoo.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2173 - assertIsFalse ".username@yahoo.com"                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2174 - assertIsFalse ".xxxx@mysite.org"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2175 - assertIsFalse "asdf@asdf"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2176 - assertIsFalse "123@$.xyz"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2177 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2178 - assertIsFalse "@%^%#$@#$@#.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2179 - assertIsFalse "@b.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2180 - assertIsFalse "@domain.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2181 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2182 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2183 - assertIsFalse "@yahoo.com"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2184 - assertIsFalse "@you.me.net"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2185 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2186 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2187 - assertIsFalse "Abc@example.com."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2188 - assertIsFalse "Display Name <email@plus.com> (after name with display)"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2189 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2190 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2191 - assertIsFalse "Foobar Some@thing.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2192 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2193 - assertIsFalse "MailTo:casesensitve@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2194 - assertIsFalse "No -foo@bar.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2195 - assertIsFalse "No asd@-bar.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2196 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2197 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2198 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2199 - assertIsFalse "\"Joe\Blow\"@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2200 - assertIsFalse "\"\"Joe Smith email@domain.com"                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2201 - assertIsFalse "\"\"Joe Smith' email@domain.com"                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2202 - assertIsFalse "\"\"Joe Smith\"\"email@domain.com"                                    =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2203 - assertIsFalse "\"qu@example.com"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2204 - assertIsFalse "\$A12345@example.com"                                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2205 - assertIsFalse "_@bde.cc,"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2206 - assertIsFalse "a..b@bde.cc"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2207 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2208 - assertIsFalse "a.b@example,co.de"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2209 - assertIsFalse "a.b@example,com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2210 - assertIsFalse "a>b@somedomain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2211 - assertIsFalse "a@.com"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2212 - assertIsFalse "a@b."                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2213 - assertIsFalse "a@b.-de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2214 - assertIsFalse "a@b._de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2215 - assertIsFalse "a@bde-.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2216 - assertIsFalse "a@bde.c-c"                                                            =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2217 - assertIsFalse "a@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2218 - assertIsFalse "a@bde_.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2219 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2220 - assertIsFalse "ab@120.25.1111.120"                                                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2221 - assertIsFalse "ab@120.256.256.120"                                                   =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2222 - assertIsFalse "ab@188.120.150.10]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2223 - assertIsFalse "ab@988.120.150.10"                                                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2224 - assertIsFalse "ab@[188.120.150.10"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2225 - assertIsFalse "ab@[188.120.150.10].com"                                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2226 - assertIsFalse "ab@b+de.cc"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2227 - assertIsFalse "ab@sd@dd"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2228 - assertIsFalse "abc.@somedomain.com"                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2229 - assertIsFalse "abc@def@example.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2230 - assertIsFalse "abc@gmail..com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2231 - assertIsFalse "abc@gmail.com.."                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2232 - assertIsFalse "abc\"defghi\"xyz@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2233 - assertIsFalse "abc\@example.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2234 - assertIsFalse "abc\\"def\\"ghi@example.com"                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2235 - assertIsFalse "abc\\@def@example.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2236 - assertIsFalse "as3d@dac.coas-"                                                       =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2237 - assertIsFalse "asd@dasd@asd.cm"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2238 - assertIsFalse "check@this..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2239 - assertIsFalse "check@thiscom"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2240 - assertIsFalse "da23@das..com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2241 - assertIsFalse "dad@sds"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2242 - assertIsFalse "dasddas-@.com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2243 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2244 - assertIsFalse "dot.@example.com"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2245 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2246 - assertIsFalse "email( (nested) )@plus.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2247 - assertIsFalse "email(with @ in comment)plus.com"                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2248 - assertIsFalse "email)mirror(@plus.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2249 - assertIsFalse "email..email@domain.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2250 - assertIsFalse "email..email@domain.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2251 - assertIsFalse "email.@domain.com"                                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2252 - assertIsFalse "email.domain.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2253 - assertIsFalse "email@#hash.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2254 - assertIsFalse "email@.domain.com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2255 - assertIsFalse "email@111.222.333"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2256 - assertIsFalse "email@111.222.333.256"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2257 - assertIsFalse "email@123.123.123.123]"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2258 - assertIsFalse "email@123.123.[123.123]"                                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  2259 - assertIsFalse "email@=qowaiv.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2260 - assertIsFalse "email@[123.123.123.123"                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2261 - assertIsFalse "email@[123.123.123].123"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2262 - assertIsFalse "email@caret^xor.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2263 - assertIsFalse "email@colon:colon.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2264 - assertIsFalse "email@dollar$.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2265 - assertIsFalse "email@domain"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2266 - assertIsFalse "email@domain-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2267 - assertIsFalse "email@domain..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2268 - assertIsFalse "email@domain.com-"                                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2269 - assertIsFalse "email@domain.com."                                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2270 - assertIsFalse "email@domain.com."                                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2271 - assertIsFalse "email@domain.com>"                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  2272 - assertIsFalse "email@domain@domain.com"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2273 - assertIsFalse "email@example"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2274 - assertIsFalse "email@example..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2275 - assertIsFalse "email@example.co.uk."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2276 - assertIsFalse "email@example.com "                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2277 - assertIsFalse "email@exclamation!mark.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2278 - assertIsFalse "email@grave`accent.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2279 - assertIsFalse "email@mailto:domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2280 - assertIsFalse "email@obelix*asterisk.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2281 - assertIsFalse "email@plus+.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2282 - assertIsFalse "email@plus.com (not closed comment"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  2283 - assertIsFalse "email@p|pe.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2284 - assertIsFalse "email@question?mark.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2285 - assertIsFalse "email@r&amp;d.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2286 - assertIsFalse "email@rightbracket}.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2287 - assertIsFalse "email@wave~tilde.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2288 - assertIsFalse "email@{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2289 - assertIsFalse "f...bar@gmail.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2290 - assertIsFalse "fa ke@false.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2291 - assertIsFalse "fake@-false.com"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2292 - assertIsFalse "fake@fal se.com"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2293 - assertIsFalse "fdsa"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2294 - assertIsFalse "fdsa@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2295 - assertIsFalse "fdsa@fdsa"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2296 - assertIsFalse "fdsa@fdsa."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2297 - assertIsFalse "foo.bar#gmail.co.u"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2298 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2299 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2300 - assertIsFalse "foo~&(&)(@bar.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2301 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2302 - assertIsFalse "get_at_m.e@gmail"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2303 - assertIsFalse "hallo2ww22@example....caaaao"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2304 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2305 - assertIsFalse "hello world@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2306 - assertIsFalse "invalid.email.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2307 - assertIsFalse "invalid@email@domain.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2308 - assertIsFalse "isis@100%.nl"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2309 - assertIsFalse "j..s@proseware.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2310 - assertIsFalse "j.@server1.proseware.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2311 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2312 - assertIsFalse "journaldev"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2313 - assertIsFalse "journaldev()*@gmail.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2314 - assertIsFalse "journaldev..2002@gmail.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2315 - assertIsFalse "journaldev.@gmail.com"                                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2316 - assertIsFalse "journaldev123@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2317 - assertIsFalse "journaldev123@.com.com"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2318 - assertIsFalse "journaldev123@gmail.a"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2319 - assertIsFalse "journaldev@%*.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2320 - assertIsFalse "journaldev@.com.my"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2321 - assertIsFalse "journaldev@gmail.com.1a"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2322 - assertIsFalse "journaldev@journaldev@gmail.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2323 - assertIsFalse "js@proseware..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2324 - assertIsFalse "mailto:email@domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2325 - assertIsFalse "mailto:mailto:email@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2326 - assertIsFalse "me..2002@gmail.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2327 - assertIsFalse "me.@gmail.com"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2328 - assertIsFalse "me123@.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2329 - assertIsFalse "me123@.com.com"                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2330 - assertIsFalse "me@%*.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2331 - assertIsFalse "me@.com.my"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2332 - assertIsFalse "me@gmail.com.1a"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2333 - assertIsFalse "me@me@gmail.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2334 - assertIsFalse "myemail@@sample.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2335 - assertIsFalse "myemail@sa@mple.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2336 - assertIsFalse "myemailsample.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2337 - assertIsFalse "ote\"@example.com"                                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2338 - assertIsFalse "pio_pio@#factory.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2339 - assertIsFalse "pio_pio@factory.c#om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2340 - assertIsFalse "pio_pio@factory.c*om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2341 - assertIsFalse "plain.address"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2342 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2343 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2344 - assertIsFalse "this is not valid@email$com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2345 - assertIsFalse "this\ still\\"not\allowed@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2346 - assertIsFalse "two..dot@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2347 - assertIsFalse "user#domain.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2348 - assertIsFalse "username@yahoo..com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2349 - assertIsFalse "username@yahoo.c"                                                     =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2350 - assertIsTrue  "username@domain.com"                                                  =   0 =  OK 
     *  2351 - assertIsTrue  "_username@domain.com"                                                 =   0 =  OK 
     *  2352 - assertIsTrue  "username_@domain.com"                                                 =   0 =  OK 
     *  2353 - assertIsFalse ""                                                                     =  11 =  OK    Laenge: Eingabe ist Leerstring
     *  2354 - assertIsFalse " "                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2355 - assertIsFalse " jkt@gmail.com"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2356 - assertIsFalse "jkt@gmail.com "                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2357 - assertIsFalse "jkt@ gmail.com"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2358 - assertIsFalse "jkt@g mail.com"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2359 - assertIsFalse "jkt @gmail.com"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2360 - assertIsFalse "j kt@gmail.com"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2361 - assertIsFalse "xxx@u*.com"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2362 - assertIsFalse "xxxx..1234@yahoo.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2363 - assertIsFalse "xxxx.ourearth.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2364 - assertIsFalse "xxxx123@gmail.b"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2365 - assertIsFalse "xxxx@.com.my"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2366 - assertIsFalse "xxxx@.org.org"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2367 - assertIsFalse "xxxxx()*@gmail.com"                                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2368 - assertIsFalse "{something}@{something}.{something}"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2369 - assertIsTrue  "mymail\@hello@hotmail.com"                                            =   0 =  OK 
     *  2370 - assertIsTrue  "!def!xyz%abc@example.com"                                             =   0 =  OK 
     *  2371 - assertIsTrue  "!sd@gh.com"                                                           =   0 =  OK 
     *  2372 - assertIsTrue  "$A12345@example.com"                                                  =   0 =  OK 
     *  2373 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                =   0 =  OK 
     *  2374 - assertIsTrue  "%2@gmail.com"                                                         =   0 =  OK 
     *  2375 - assertIsTrue  "--@ooo.ooo"                                                           =   0 =  OK 
     *  2376 - assertIsTrue  "-@bde.cc"                                                             =   0 =  OK 
     *  2377 - assertIsTrue  "-asd@das.com"                                                         =   0 =  OK 
     *  2378 - assertIsTrue  "1234567890@domain.com"                                                =   0 =  OK 
     *  2379 - assertIsTrue  "1@domain.com"                                                         =   0 =  OK 
     *  2380 - assertIsTrue  "1@gmail.com"                                                          =   0 =  OK 
     *  2381 - assertIsTrue  "1_example@something.gmail.com"                                        =   0 =  OK 
     *  2382 - assertIsTrue  "2@bde.cc"                                                             =   0 =  OK 
     *  2383 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                               =   0 =  OK 
     *  2384 - assertIsTrue  "<boss@nil.test>"                                                      =   0 =  OK 
     *  2385 - assertIsTrue  "<john@doe.com>"                                                       =   0 =  OK 
     *  2386 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                            =   0 =  OK 
     *  2387 - assertIsTrue  "Abc.123@example.com"                                                  =   0 =  OK 
     *  2388 - assertIsTrue  "Abc@10.42.0.1"                                                        =   2 =  OK 
     *  2389 - assertIsTrue  "Abc@example.com"                                                      =   0 =  OK 
     *  2390 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                 =   0 =  OK 
     *  2391 - assertIsTrue  "Fred\ Bloggs@example.com"                                             =   0 =  OK 
     *  2392 - assertIsTrue  "Joe.\\Blow@example.com"                                               =   0 =  OK 
     *  2393 - assertIsTrue  "John <john@doe.com>"                                                  =   0 =  OK 
     *  2394 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                          =   0 =  OK 
     *  2395 - assertIsTrue  "This is <john@127.0.0.1>"                                             =   2 =  OK 
     *  2396 - assertIsTrue  "This is <john@[127.0.0.1]>"                                           =   2 =  OK 
     *  2397 - assertIsTrue  "Who? <one@y.test>"                                                    =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2398 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  2399 - assertIsTrue  "\"%2\"@gmail.com"                                                     =   1 =  OK 
     *  2400 - assertIsTrue  "\"Abc@def\"@example.com"                                              =   1 =  OK 
     *  2401 - assertIsTrue  "\"Abc\@def\"@example.com"                                             =   1 =  OK 
     *  2402 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                    =   1 =  OK 
     *  2403 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  2404 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                         =   1 =  OK 
     *  2405 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                   =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2406 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                            =   1 =  OK 
     *  2407 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                            =   1 =  OK 
     *  2408 - assertIsTrue  "\"a..b\"@gmail.com"                                                   =   1 =  OK 
     *  2409 - assertIsTrue  "\"a@b\"@example.com"                                                  =   1 =  OK 
     *  2410 - assertIsTrue  "\"a_b\"@gmail.com"                                                    =   1 =  OK 
     *  2411 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                         =   1 =  OK 
     *  2412 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                  =   1 =  OK 
     *  2413 - assertIsTrue  "\"foo\@bar\"@Something.com"                                           =   1 =  OK 
     *  2414 - assertIsTrue  "\"j\\"s\"@proseware.com"                                              =   1 =  OK 
     *  2415 - assertIsTrue  "\"myemail@sa\"@mple.com"                                              =   1 =  OK 
     *  2416 - assertIsTrue  "_-_@bde.cc"                                                           =   0 =  OK 
     *  2417 - assertIsTrue  "_@gmail.com"                                                          =   0 =  OK 
     *  2418 - assertIsTrue  "_dasd@sd.com"                                                         =   0 =  OK 
     *  2419 - assertIsTrue  "_dasd_das_@9.com"                                                     =   0 =  OK 
     *  2420 - assertIsTrue  "_somename@example.com"                                                =   0 =  OK 
     *  2421 - assertIsTrue  "a&d@somedomain.com"                                                   =   0 =  OK 
     *  2422 - assertIsTrue  "a*d@somedomain.com"                                                   =   0 =  OK 
     *  2423 - assertIsTrue  "a+b@bde.cc"                                                           =   0 =  OK 
     *  2424 - assertIsTrue  "a+b@c.com"                                                            =   0 =  OK 
     *  2425 - assertIsTrue  "a-b@bde.cc"                                                           =   0 =  OK 
     *  2426 - assertIsTrue  "a.a@test.com"                                                         =   0 =  OK 
     *  2427 - assertIsTrue  "a.b@com"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2428 - assertIsTrue  "a/d@somedomain.com"                                                   =   0 =  OK 
     *  2429 - assertIsTrue  "a2@bde.cc"                                                            =   0 =  OK 
     *  2430 - assertIsTrue  "a@123.45.67.89"                                                       =   2 =  OK 
     *  2431 - assertIsTrue  "a@b.c.com"                                                            =   0 =  OK 
     *  2432 - assertIsTrue  "a@b.com"                                                              =   0 =  OK 
     *  2433 - assertIsTrue  "a@bc.com"                                                             =   0 =  OK 
     *  2434 - assertIsTrue  "a@bcom"                                                               =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2435 - assertIsTrue  "a@domain.com"                                                         =   0 =  OK 
     *  2436 - assertIsTrue  "a__z@provider.com"                                                    =   0 =  OK 
     *  2437 - assertIsTrue  "a_z%@gmail.com"                                                       =   0 =  OK 
     *  2438 - assertIsTrue  "aaron@theinfo.org"                                                    =   0 =  OK 
     *  2439 - assertIsTrue  "ab@288.120.150.10.com"                                                =   0 =  OK 
     *  2440 - assertIsTrue  "ab@[120.254.254.120]"                                                 =   2 =  OK 
     *  2441 - assertIsTrue  "ab@b-de.cc"                                                           =   0 =  OK 
     *  2442 - assertIsTrue  "ab@c.com"                                                             =   0 =  OK 
     *  2443 - assertIsTrue  "ab_c@bde.cc"                                                          =   0 =  OK 
     *  2444 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                       =   1 =  OK 
     *  2445 - assertIsTrue  "abc.efg@gmail.com"                                                    =   0 =  OK 
     *  2446 - assertIsTrue  "abc.xyz@gmail.com.in"                                                 =   0 =  OK 
     *  2447 - assertIsTrue  "abc123xyz@asdf.co.in"                                                 =   0 =  OK 
     *  2448 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                 =   0 =  OK 
     *  2449 - assertIsTrue  "abc@abc.abc"                                                          =   0 =  OK 
     *  2450 - assertIsTrue  "abc@abc.abcd"                                                         =   0 =  OK 
     *  2451 - assertIsTrue  "abc@abc.abcde"                                                        =   0 =  OK 
     *  2452 - assertIsTrue  "abc@abc.co.in"                                                        =   0 =  OK 
     *  2453 - assertIsTrue  "abc@abc.com.com.com.com"                                              =   0 =  OK 
     *  2454 - assertIsTrue  "abc@gmail.com.my"                                                     =   0 =  OK 
     *  2455 - assertIsTrue  "abc\@def@example.com"                                                 =   0 =  OK 
     *  2456 - assertIsTrue  "abc\\@example.com"                                                    =   0 =  OK 
     *  2457 - assertIsTrue  "abcxyz123@qwert.com"                                                  =   0 =  OK 
     *  2458 - assertIsTrue  "alex@example.com"                                                     =   0 =  OK 
     *  2459 - assertIsTrue  "alireza@test.co.uk"                                                   =   0 =  OK 
     *  2460 - assertIsTrue  "asd-@asd.com"                                                         =   0 =  OK 
     *  2461 - assertIsTrue  "begeddov@jfinity.com"                                                 =   0 =  OK 
     *  2462 - assertIsTrue  "check@this.com"                                                       =   0 =  OK 
     *  2463 - assertIsTrue  "cog@wheel.com"                                                        =   0 =  OK 
     *  2464 - assertIsTrue  "customer/department=shipping@example.com"                             =   0 =  OK 
     *  2465 - assertIsTrue  "d._.___d@gmail.com"                                                   =   0 =  OK 
     *  2466 - assertIsTrue  "d.j@server1.proseware.com"                                            =   0 =  OK 
     *  2467 - assertIsTrue  "d.oy.smith@gmail.com"                                                 =   0 =  OK 
     *  2468 - assertIsTrue  "d23d@da9.co9"                                                         =   0 =  OK 
     *  2469 - assertIsTrue  "d_oy_smith@gmail.com"                                                 =   0 =  OK 
     *  2470 - assertIsTrue  "dasd-dasd@das.com.das"                                                =   0 =  OK 
     *  2471 - assertIsTrue  "dasd.dadas@dasd.com"                                                  =   0 =  OK 
     *  2472 - assertIsTrue  "dasd_-@jdas.com"                                                      =   0 =  OK 
     *  2473 - assertIsTrue  "david.jones@proseware.com"                                            =   0 =  OK 
     *  2474 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2475 - assertIsTrue  "dda_das@das-dasd.com"                                                 =   0 =  OK 
     *  2476 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                         =   0 =  OK 
     *  2477 - assertIsTrue  "digit-only-domain@123.com"                                            =   0 =  OK 
     *  2478 - assertIsTrue  "doysmith@gmail.com"                                                   =   0 =  OK 
     *  2479 - assertIsTrue  "drp@drp.cz"                                                           =   0 =  OK 
     *  2480 - assertIsTrue  "dsq!a?@das.com"                                                       =   0 =  OK 
     *  2481 - assertIsTrue  "email@domain.co.de"                                                   =   0 =  OK 
     *  2482 - assertIsTrue  "email@domain.com"                                                     =   0 =  OK 
     *  2483 - assertIsTrue  "email@example.co.uk"                                                  =   0 =  OK 
     *  2484 - assertIsTrue  "email@example.com"                                                    =   0 =  OK 
     *  2485 - assertIsTrue  "email@mail.gmail.com"                                                 =   0 =  OK 
     *  2486 - assertIsTrue  "email@subdomain.domain.com"                                           =   0 =  OK 
     *  2487 - assertIsTrue  "example@example.co"                                                   =   0 =  OK 
     *  2488 - assertIsTrue  "f.f.f@bde.cc"                                                         =   0 =  OK 
     *  2489 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                =   0 =  OK 
     *  2490 - assertIsTrue  "first-name-last-name@d-a-n.com"                                       =   0 =  OK 
     *  2491 - assertIsTrue  "firstname+lastname@domain.com"                                        =   0 =  OK 
     *  2492 - assertIsTrue  "firstname-lastname@domain.com"                                        =   0 =  OK 
     *  2493 - assertIsTrue  "firstname.lastname@domain.com"                                        =   0 =  OK 
     *  2494 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                            =   0 =  OK 
     *  2495 - assertIsTrue  "futureTLD@somewhere.fooo"                                             =   0 =  OK 
     *  2496 - assertIsTrue  "hello.me_1@email.com"                                                 =   0 =  OK 
     *  2497 - assertIsTrue  "hello7___@ca.com.pt"                                                  =   0 =  OK 
     *  2498 - assertIsTrue  "info@ermaelan.com"                                                    =   0 =  OK 
     *  2499 - assertIsTrue  "j.s@server1.proseware.com"                                            =   0 =  OK 
     *  2500 - assertIsTrue  "j@proseware.com9"                                                     =   0 =  OK 
     *  2501 - assertIsTrue  "j_9@[129.126.118.1]"                                                  =   2 =  OK 
     *  2502 - assertIsTrue  "jinujawad6s@gmail.com"                                                =   0 =  OK 
     *  2503 - assertIsTrue  "john.doe@example.com"                                                 =   0 =  OK 
     *  2504 - assertIsTrue  "john.o'doe@example.com"                                               =   0 =  OK 
     *  2505 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  2506 - assertIsTrue  "jones@ms1.proseware.com"                                              =   0 =  OK 
     *  2507 - assertIsTrue  "journaldev+100@gmail.com"                                             =   0 =  OK 
     *  2508 - assertIsTrue  "journaldev-100@journaldev.net"                                        =   0 =  OK 
     *  2509 - assertIsTrue  "journaldev-100@yahoo-test.com"                                        =   0 =  OK 
     *  2510 - assertIsTrue  "journaldev-100@yahoo.com"                                             =   0 =  OK 
     *  2511 - assertIsTrue  "journaldev.100@journaldev.com.au"                                     =   0 =  OK 
     *  2512 - assertIsTrue  "journaldev.100@yahoo.com"                                             =   0 =  OK 
     *  2513 - assertIsTrue  "journaldev111@journaldev.com"                                         =   0 =  OK 
     *  2514 - assertIsTrue  "journaldev@1.com"                                                     =   0 =  OK 
     *  2515 - assertIsTrue  "journaldev@gmail.com.com"                                             =   0 =  OK 
     *  2516 - assertIsTrue  "journaldev@yahoo.com"                                                 =   0 =  OK 
     *  2517 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                    =   0 =  OK 
     *  2518 - assertIsTrue  "js#internal@proseware.com"                                            =   0 =  OK 
     *  2519 - assertIsTrue  "js*@proseware.com"                                                    =   0 =  OK 
     *  2520 - assertIsTrue  "js@proseware.com9"                                                    =   0 =  OK 
     *  2521 - assertIsTrue  "me+100@me.com"                                                        =   0 =  OK 
     *  2522 - assertIsTrue  "me+alpha@example.com"                                                 =   0 =  OK 
     *  2523 - assertIsTrue  "me-100@me.com"                                                        =   0 =  OK 
     *  2524 - assertIsTrue  "me-100@me.com.au"                                                     =   0 =  OK 
     *  2525 - assertIsTrue  "me-100@yahoo-test.com"                                                =   0 =  OK 
     *  2526 - assertIsTrue  "me.100@me.com"                                                        =   0 =  OK 
     *  2527 - assertIsTrue  "me@aaronsw.com"                                                       =   0 =  OK 
     *  2528 - assertIsTrue  "me@company.co.uk"                                                     =   0 =  OK 
     *  2529 - assertIsTrue  "me@gmail.com"                                                         =   0 =  OK 
     *  2530 - assertIsTrue  "me@gmail.com"                                                         =   0 =  OK 
     *  2531 - assertIsTrue  "me@mail.s2.example.com"                                               =   0 =  OK 
     *  2532 - assertIsTrue  "me@me.cu.uk"                                                          =   0 =  OK 
     *  2533 - assertIsTrue  "my.ownsite@ourearth.org"                                              =   0 =  OK 
     *  2534 - assertIsFalse "myemail@sample"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2535 - assertIsTrue  "myemail@sample.com"                                                   =   0 =  OK 
     *  2536 - assertIsTrue  "mysite@you.me.net"                                                    =   0 =  OK 
     *  2537 - assertIsTrue  "o'hare@example.com"                                                   =   0 =  OK 
     *  2538 - assertIsTrue  "peter.example@domain.comau"                                           =   0 =  OK 
     *  2539 - assertIsTrue  "peter.piper@example.com"                                              =   0 =  OK 
     *  2540 - assertIsTrue  "peter_123@news.com"                                                   =   0 =  OK 
     *  2541 - assertIsTrue  "pio^_pio@factory.com"                                                 =   0 =  OK 
     *  2542 - assertIsTrue  "pio_#pio@factory.com"                                                 =   0 =  OK 
     *  2543 - assertIsTrue  "pio_pio@factory.com"                                                  =   0 =  OK 
     *  2544 - assertIsTrue  "pio_~pio@factory.com"                                                 =   0 =  OK 
     *  2545 - assertIsTrue  "piskvor@example.lighting"                                             =   0 =  OK 
     *  2546 - assertIsTrue  "rss-dev@yahoogroups.com"                                              =   0 =  OK 
     *  2547 - assertIsTrue  "someone+tag@somewhere.net"                                            =   0 =  OK 
     *  2548 - assertIsTrue  "someone@somewhere.co.uk"                                              =   0 =  OK 
     *  2549 - assertIsTrue  "someone@somewhere.com"                                                =   0 =  OK 
     *  2550 - assertIsTrue  "something_valid@somewhere.tld"                                        =   0 =  OK 
     *  2551 - assertIsTrue  "tvf@tvf.cz"                                                           =   0 =  OK 
     *  2552 - assertIsTrue  "user#@domain.co.in"                                                   =   0 =  OK 
     *  2553 - assertIsTrue  "user'name@domain.co.in"                                               =   0 =  OK 
     *  2554 - assertIsTrue  "user+mailbox@example.com"                                             =   0 =  OK 
     *  2555 - assertIsTrue  "user-name@domain.co.in"                                               =   0 =  OK 
     *  2556 - assertIsTrue  "user.name@domain.com"                                                 =   0 =  OK 
     *  2557 - assertIsFalse ".user.name@domain.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2558 - assertIsFalse "user-name@domain.com."                                                =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2559 - assertIsFalse "username@.com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2560 - assertIsTrue  "user1@domain.com"                                                     =   0 =  OK 
     *  2561 - assertIsTrue  "user?name@domain.co.in"                                               =   0 =  OK 
     *  2562 - assertIsTrue  "user@domain.co.in"                                                    =   0 =  OK 
     *  2563 - assertIsTrue  "user@domain.com"                                                      =   0 =  OK 
     *  2564 - assertIsFalse "user@domaincom"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2565 - assertIsTrue  "user_name@domain.co.in"                                               =   0 =  OK 
     *  2566 - assertIsTrue  "user_name@domain.com"                                                 =   0 =  OK 
     *  2567 - assertIsTrue  "username@yahoo.corporate"                                             =   0 =  OK 
     *  2568 - assertIsTrue  "username@yahoo.corporate.in"                                          =   0 =  OK 
     *  2569 - assertIsTrue  "username+something@domain.com"                                        =   0 =  OK 
     *  2570 - assertIsTrue  "vdv@dyomedea.com"                                                     =   0 =  OK 
     *  2571 - assertIsTrue  "xxxx@gmail.com"                                                       =   0 =  OK 
     *  2572 - assertIsTrue  "xxxxxx@yahoo.com"                                                     =   0 =  OK 
     *  2573 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  2574 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                              =   4 =  OK 
     *  2575 - assertIsFalse "user@localserver"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2576 - assertIsTrue  "w.b.f@test.com"                                                       =   0 =  OK 
     *  2577 - assertIsTrue  "w.b.f@test.museum"                                                    =   0 =  OK 
     *  2578 - assertIsTrue  "yoursite@ourearth.com"                                                =   0 =  OK 
     *  2579 - assertIsTrue  "~pio_pio@factory.com"                                                 =   0 =  OK 
     *  2580 - assertIsTrue  "\"test123\"@gmail.com"                                                =   1 =  OK 
     *  2581 - assertIsTrue  "test123@gmail.comcomco"                                               =   0 =  OK 
     *  2582 - assertIsTrue  "test123@gmail.c"                                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2583 - assertIsTrue  "test1&23@gmail.com"                                                   =   0 =  OK 
     *  2584 - assertIsTrue  "test123@gmail.com"                                                    =   0 =  OK 
     *  2585 - assertIsFalse "test123@gmail..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2586 - assertIsFalse ".test123@gmail.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2587 - assertIsFalse "test123@gmail.com."                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2588 - assertIsFalse "test1Ὠ23@gmail.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2589 - assertIsTrue  "javaTpoint@domain.co.in"                                              =   0 =  OK 
     *  2590 - assertIsTrue  "javaTpoint@domain.com"                                                =   0 =  OK 
     *  2591 - assertIsTrue  "javaTpoint.name@domain.com"                                           =   0 =  OK 
     *  2592 - assertIsTrue  "javaTpoint#@domain.co.in"                                             =   0 =  OK 
     *  2593 - assertIsTrue  "javaTpoint@domain.com"                                                =   0 =  OK 
     *  2594 - assertIsFalse "javaTpoint@domaincom"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2595 - assertIsTrue  "javaTpoint@domain.co.in"                                              =   0 =  OK 
     *  2596 - assertIsTrue  "12453@domain.com"                                                     =   0 =  OK 
     *  2597 - assertIsTrue  "javaTpoint.name@domain.com"                                           =   0 =  OK 
     *  2598 - assertIsTrue  "1avaTpoint#@domain.co.in"                                             =   0 =  OK 
     *  2599 - assertIsTrue  "javaTpoint@domain.co.in"                                              =   0 =  OK 
     *  2600 - assertIsTrue  "javaTpoint@domain.com"                                                =   0 =  OK 
     *  2601 - assertIsTrue  "javaTpoint.name@domain.com"                                           =   0 =  OK 
     *  2602 - assertIsTrue  "javaTpoint#@domain.co.in"                                             =   0 =  OK 
     *  2603 - assertIsTrue  "java'Tpoint@domain.com"                                               =   0 =  OK 
     *  2604 - assertIsFalse ".javaTpoint@yahoo.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2605 - assertIsFalse "javaTpoint@domain.com."                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2606 - assertIsFalse "javaTpoint#domain.com"                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2607 - assertIsFalse "javaTpoint@domain..com"                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2608 - assertIsFalse "@yahoo.com"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2609 - assertIsFalse "javaTpoint#domain.com"                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2610 - assertIsFalse "12javaTpoint#domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2611 - assertIsTrue  "admin@java2blog.com"                                                  =   0 =  OK 
     *  2612 - assertIsFalse "@java2blog.com"                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2613 - assertIsTrue  "arpit.mandliya@java2blog.com"                                         =   0 =  OK 
     *  2614 - assertIsTrue  "sairamkrishna@tutorialspoint.com"                                     =   0 =  OK 
     *  2615 - assertIsTrue  "kittuprasad700@gmail.com"                                             =   0 =  OK 
     *  2616 - assertIsFalse "sairamkrishna_mammahe%google-india.com"                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2617 - assertIsTrue  "sairam.krishna@gmail-indai.com"                                       =   0 =  OK 
     *  2618 - assertIsTrue  "sai#@youtube.co.in"                                                   =   0 =  OK 
     *  2619 - assertIsFalse "kittu@domaincom"                                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2620 - assertIsFalse "kittu#gmail.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2621 - assertIsFalse "@pindom.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2622 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2623 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2624 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2625 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  2626 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  2627 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2628 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"       =   0 =  OK 
     *  2629 - assertIsTrue  "valid@[1.1.1.1]"                                                      =   2 =  OK 
     *  2630 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2631 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                               =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2632 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                      =   4 =  OK 
     *  2633 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"       =   4 =  OK 
     *  2634 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                            =   4 =  OK 
     *  2635 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"               =   4 =  OK 
     *  2636 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                 =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2637 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                          =   4 =  OK 
     *  2638 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                          =   4 =  OK 
     *  2639 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                    =   2 =  OK 
     *  2640 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                        =   2 =  OK 
     *  2641 - assertIsFalse "invalid@[10]"                                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2642 - assertIsFalse "invalid@[10.1]"                                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2643 - assertIsFalse "invalid@[10.1.52]"                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2644 - assertIsFalse "invalid@[256.256.256.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2645 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2646 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2647 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2648 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2649 - assertIsFalse "invalid@domain1.com@domain2.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2650 - assertIsFalse "\"locál-part\"@example.com"                                           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2651 - assertIsFalse "invalid@[IPv6:1::2:]"                                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2652 - assertIsFalse "invalid@[IPv6::1::1]"                                                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2653 - assertIsFalse "invalid@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2654 - assertIsFalse "invalid@[111.111.111.111"                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2655 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2656 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2657 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2658 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2659 - assertIsFalse "invalid@[IPv6:1111:1111]"                                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2660 - assertIsFalse "\"invalid-qstring@example.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     *  2661 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                           =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2662 - assertIsTrue  "\"back\slash\"@sld.com"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2663 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                          =   1 =  OK 
     *  2664 - assertIsTrue  "\"quoted\"@sld.com"                                                   =   1 =  OK 
     *  2665 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                   =   1 =  OK 
     *  2666 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                   =   0 =  OK 
     *  2667 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                  =   0 =  OK 
     *  2668 - assertIsTrue  "01234567890@numbers-in-local.net"                                     =   0 =  OK 
     *  2669 - assertIsTrue  "a@single-character-in-local.org"                                      =   0 =  OK 
     *  2670 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  2671 - assertIsTrue  "backticksarelegit@test.com"                                           =   0 =  OK 
     *  2672 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                           =   2 =  OK 
     *  2673 - assertIsTrue  "country-code-tld@sld.rw"                                              =   0 =  OK 
     *  2674 - assertIsTrue  "country-code-tld@sld.uk"                                              =   0 =  OK 
     *  2675 - assertIsTrue  "letters-in-sld@123.com"                                               =   0 =  OK 
     *  2676 - assertIsTrue  "local@dash-in-sld.com"                                                =   0 =  OK 
     *  2677 - assertIsTrue  "local@sld.newTLD"                                                     =   0 =  OK 
     *  2678 - assertIsTrue  "local@sub.domains.com"                                                =   0 =  OK 
     *  2679 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                     =   0 =  OK 
     *  2680 - assertIsTrue  "one-character-third-level@a.example.com"                              =   0 =  OK 
     *  2681 - assertIsTrue  "one-letter-sld@x.org"                                                 =   0 =  OK 
     *  2682 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                             =   0 =  OK 
     *  2683 - assertIsTrue  "single-character-in-sld@x.org"                                        =   0 =  OK 
     *  2684 - assertIsTrue  "uncommon-tld@sld.mobi"                                                =   0 =  OK 
     *  2685 - assertIsTrue  "uncommon-tld@sld.museum"                                              =   0 =  OK 
     *  2686 - assertIsTrue  "uncommon-tld@sld.travel"                                              =   0 =  OK 
     *  2687 - assertIsFalse "invalid"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2688 - assertIsFalse "invalid@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2689 - assertIsFalse "invalid @"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2690 - assertIsFalse "invalid@[555.666.777.888]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2691 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2692 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2693 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2694 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2695 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2696 - assertIsFalse "@missing-local.org"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2697 - assertIsFalse "IP-and-port@127.0.0.1:25"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2698 - assertIsFalse "another-invalid-ip@127.0.0.256"                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2699 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2700 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2701 - assertIsFalse "invalid-ip@127.0.0.1.26"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2702 - assertIsFalse "local-ends-with-dot.@sld.com"                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2703 - assertIsFalse "missing-at-sign.net"                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2704 - assertIsFalse "missing-sld@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2705 - assertIsFalse "missing-tld@sld."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2706 - assertIsFalse "sld-ends-with-dash@sld-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2707 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2708 - assertIsFalse "two..consecutive-dots@sld.com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2709 - assertIsTrue  "unbracketed-IP@127.0.0.1"                                             =   2 =  OK 
     *  2710 - assertIsFalse "underscore.error@example.com_"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     *  2711 - assertIsTrue  "first.last@iana.org"                                                  =   0 =  OK 
     *  2712 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  2713 - assertIsTrue  "\"first\\"last\"@iana.org"                                            =   1 =  OK 
     *  2714 - assertIsTrue  "\"first@last\"@iana.org"                                              =   1 =  OK 
     *  2715 - assertIsTrue  "\"first\\last\"@iana.org"                                             =   1 =  OK 
     *  2716 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  2717 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  2718 - assertIsTrue  "first.last@[12.34.56.78]"                                             =   2 =  OK 
     *  2719 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                    =   4 =  OK 
     *  2720 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                     =   4 =  OK 
     *  2721 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                    =   4 =  OK 
     *  2722 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                    =   4 =  OK 
     *  2723 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"            =   4 =  OK 
     *  2724 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  2725 - assertIsTrue  "first.last@3com.com"                                                  =   0 =  OK 
     *  2726 - assertIsTrue  "first.last@123.iana.org"                                              =   0 =  OK 
     *  2727 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2728 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                =   4 =  OK 
     *  2729 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                =   1 =  OK 
     *  2730 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                            =   1 =  OK 
     *  2731 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                              =   1 =  OK 
     *  2732 - assertIsTrue  "\"Abc@def\"@iana.org"                                                 =   1 =  OK 
     *  2733 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                           =   1 =  OK 
     *  2734 - assertIsTrue  "user+mailbox@iana.org"                                                =   0 =  OK 
     *  2735 - assertIsTrue  "$A12345@iana.org"                                                     =   0 =  OK 
     *  2736 - assertIsTrue  "!def!xyz%abc@iana.org"                                                =   0 =  OK 
     *  2737 - assertIsTrue  "_somename@iana.org"                                                   =   0 =  OK 
     *  2738 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2739 - assertIsTrue  "peter.piper@iana.org"                                                 =   0 =  OK 
     *  2740 - assertIsTrue  "test@iana.org"                                                        =   0 =  OK 
     *  2741 - assertIsTrue  "TEST@iana.org"                                                        =   0 =  OK 
     *  2742 - assertIsTrue  "1234567890@iana.org"                                                  =   0 =  OK 
     *  2743 - assertIsTrue  "test+test@iana.org"                                                   =   0 =  OK 
     *  2744 - assertIsTrue  "test-test@iana.org"                                                   =   0 =  OK 
     *  2745 - assertIsTrue  "t*est@iana.org"                                                       =   0 =  OK 
     *  2746 - assertIsTrue  "+1~1+@iana.org"                                                       =   0 =  OK 
     *  2747 - assertIsTrue  "{_test_}@iana.org"                                                    =   0 =  OK 
     *  2748 - assertIsTrue  "test.test@iana.org"                                                   =   0 =  OK 
     *  2749 - assertIsTrue  "\"test.test\"@iana.org"                                               =   1 =  OK 
     *  2750 - assertIsTrue  "test.\"test\"@iana.org"                                               =   1 =  OK 
     *  2751 - assertIsTrue  "\"test@test\"@iana.org"                                               =   1 =  OK 
     *  2752 - assertIsTrue  "test@123.123.123.x123"                                                =   0 =  OK 
     *  2753 - assertIsTrue  "test@123.123.123.123"                                                 =   2 =  OK 
     *  2754 - assertIsTrue  "test@[123.123.123.123]"                                               =   2 =  OK 
     *  2755 - assertIsTrue  "test@example.iana.org"                                                =   0 =  OK 
     *  2756 - assertIsTrue  "test@example.example.iana.org"                                        =   0 =  OK 
     *  2757 - assertIsTrue  "customer/department@iana.org"                                         =   0 =  OK 
     *  2758 - assertIsTrue  "_Yosemite.Sam@iana.org"                                               =   0 =  OK 
     *  2759 - assertIsTrue  "~@iana.org"                                                           =   0 =  OK 
     *  2760 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                           =   1 =  OK 
     *  2761 - assertIsTrue  "Ima.Fool@iana.org"                                                    =   0 =  OK 
     *  2762 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                =   1 =  OK 
     *  2763 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                              =   1 =  OK 
     *  2764 - assertIsTrue  "\"first\".\"last\"@iana.org"                                          =   1 =  OK 
     *  2765 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                   =   1 =  OK 
     *  2766 - assertIsTrue  "\"first\".last@iana.org"                                              =   1 =  OK 
     *  2767 - assertIsTrue  "first.\"last\"@iana.org"                                              =   1 =  OK 
     *  2768 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                               =   1 =  OK 
     *  2769 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2770 - assertIsTrue  "\"first.middle.last\"@iana.org"                                       =   1 =  OK 
     *  2771 - assertIsTrue  "\"first..last\"@iana.org"                                             =   1 =  OK 
     *  2772 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2773 - assertIsFalse "first.last @iana.orgin"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2774 - assertIsTrue  "\"test blah\"@iana.orgin"                                             =   1 =  OK 
     *  2775 - assertIsTrue  "name.lastname@domain.com"                                             =   0 =  OK 
     *  2776 - assertIsTrue  "a@bar.com"                                                            =   0 =  OK 
     *  2777 - assertIsTrue  "aaa@[123.123.123.123]"                                                =   2 =  OK 
     *  2778 - assertIsTrue  "a-b@bar.com"                                                          =   0 =  OK 
     *  2779 - assertIsFalse "+@b.c"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2780 - assertIsTrue  "+@b.com"                                                              =   0 =  OK 
     *  2781 - assertIsTrue  "a@b.co-foo.uk"                                                        =   0 =  OK 
     *  2782 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                   =   1 =  OK 
     *  2783 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                   =   1 =  OK 
     *  2784 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                     =   0 =  OK 
     *  2785 - assertIsTrue  "foobar@192.168.0.1"                                                   =   2 =  OK 
     *  2786 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                    =   6 =  OK 
     *  2787 - assertIsTrue  "user%uucp!path@berkeley.edu"                                          =   0 =  OK 
     *  2788 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                            =   0 =  OK 
     *  2789 - assertIsTrue  "test@test.com"                                                        =   0 =  OK 
     *  2790 - assertIsTrue  "test@xn--example.com"                                                 =   0 =  OK 
     *  2791 - assertIsTrue  "test@example.com"                                                     =   0 =  OK 
     *  2792 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2793 - assertIsTrue  "first\@last@iana.org"                                                 =   0 =  OK 
     *  2794 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                             =   0 =  OK 
     *  2795 - assertIsFalse "first.last@example.123"                                               =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2796 - assertIsFalse "first.last@comin"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2797 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                            =   1 =  OK 
     *  2798 - assertIsTrue  "Abc\@def@iana.org"                                                    =   0 =  OK 
     *  2799 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                =   0 =  OK 
     *  2800 - assertIsFalse "Joe.\Blow@iana.org"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2801 - assertIsTrue  "first.last@sub.do.com"                                                =   0 =  OK 
     *  2802 - assertIsFalse "first.last"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2803 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                      =   0 =  OK 
     *  2804 - assertIsTrue  "\"hello world\"@example.com"                                          =   1 =  OK 
     *  2805 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2806 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                         =   1 =  OK 
     *  2807 - assertIsTrue  "example+tag@gmail.com"                                                =   0 =  OK 
     *  2808 - assertIsFalse ".ann..other.@example.com"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2809 - assertIsTrue  "ann.other@example.com"                                                =   0 =  OK 
     *  2810 - assertIsTrue  "something@something.something"                                        =   0 =  OK 
     *  2811 - assertIsTrue  "c@(Chris's host.)public.examplein"                                    =   6 =  OK 
     *  2812 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2813 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2814 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2815 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2816 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2817 - assertIsFalse "first().last@iana.orgin"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2818 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2819 - assertIsFalse "jdoe@machine(comment). examplein"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2820 - assertIsFalse "first(abc.def).last@iana.orgin"                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2821 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2822 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2823 - assertIsFalse "first(abc\(def)@iana.orgin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2824 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2825 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2826 - assertIsFalse "1234 @ local(blah) .machine .examplein"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2827 - assertIsFalse "a@bin"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2828 - assertIsFalse "a@barin"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2829 - assertIsFalse "@about.museum"                                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2830 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2831 - assertIsFalse ".first.last@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2832 - assertIsFalse "first.last.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2833 - assertIsFalse "first..last@iana.org"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2834 - assertIsFalse "\"first\"last\"@iana.org"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2835 - assertIsFalse "first.last@"                                                          =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2836 - assertIsFalse "first.last@-xample.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2837 - assertIsFalse "first.last@exampl-.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2838 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2839 - assertIsFalse "abc\@iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2840 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2841 - assertIsFalse "abc@def@iana.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2842 - assertIsFalse "@iana.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2843 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2844 - assertIsFalse "\"qu@iana.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2845 - assertIsFalse "ote\"@iana.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2846 - assertIsFalse ".dot@iana.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2847 - assertIsFalse "dot.@iana.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2848 - assertIsFalse "two..dot@iana.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2849 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2850 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2851 - assertIsFalse "hello world@iana.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2852 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2853 - assertIsFalse "test.iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2854 - assertIsFalse "test.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2855 - assertIsFalse "test..test@iana.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2856 - assertIsFalse ".test@iana.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2857 - assertIsFalse "test@test@iana.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2858 - assertIsFalse "test@@iana.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2859 - assertIsFalse "-- test --@iana.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2860 - assertIsFalse "[test]@iana.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2861 - assertIsFalse "\"test\"test\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2862 - assertIsFalse "()[]\;:.><@iana.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2863 - assertIsFalse "test@."                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2864 - assertIsFalse "test@example."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2865 - assertIsFalse "test@.org"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2866 - assertIsFalse "test@[123.123.123.123"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2867 - assertIsFalse "test@123.123.123.123]"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2868 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2869 - assertIsFalse "@NotAnEmail"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2870 - assertIsFalse "\"test\"blah\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2871 - assertIsFalse ".wooly@iana.org"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2872 - assertIsFalse "wo..oly@iana.org"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2873 - assertIsFalse "pootietang.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2874 - assertIsFalse ".@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2875 - assertIsFalse "Ima Fool@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2876 - assertIsFalse "foo@[\1.2.3.4]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2877 - assertIsFalse "first.\"\".last@iana.org"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2878 - assertIsFalse "first\last@iana.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2879 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2880 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2881 - assertIsFalse "cal(foo(bar)@iamcal.com"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2882 - assertIsFalse "cal(foo)bar)@iamcal.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2883 - assertIsFalse "cal(foo\)@iamcal.com"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2884 - assertIsFalse "first(middle)last@iana.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2885 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2886 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2887 - assertIsFalse ".@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2888 - assertIsFalse "@bar.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2889 - assertIsFalse "@@bar.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2890 - assertIsFalse "aaa.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2891 - assertIsFalse "aaa@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2892 - assertIsFalse "aaa@.123"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2893 - assertIsFalse "aaa@[123.123.123.123]a"                                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2894 - assertIsFalse "aaa@[123.123.123.333]"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2895 - assertIsFalse "a@bar.com."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2896 - assertIsFalse "a@-b.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2897 - assertIsFalse "a@b-.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2898 - assertIsFalse "-@..com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2899 - assertIsFalse "-@a..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2900 - assertIsFalse "@about.museum-"                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2901 - assertIsFalse "test@...........com"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2902 - assertIsFalse "first.last@[IPv6::]"                                                  =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2903 - assertIsFalse "first.last@[IPv6::::]"                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2904 - assertIsFalse "first.last@[IPv6::b4]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2905 - assertIsFalse "first.last@[IPv6::::b4]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2906 - assertIsFalse "first.last@[IPv6::b3:b4]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2907 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2908 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2909 - assertIsFalse "first.last@[IPv6:a1:]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2910 - assertIsFalse "first.last@[IPv6:a1:::]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2911 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2912 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2913 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2914 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2915 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2916 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2917 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2918 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2919 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                  =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2920 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2921 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2922 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2923 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                  =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  2924 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2925 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2926 - assertIsFalse "first.last@[IPv6::a2::b4]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2927 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2928 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2929 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2930 - assertIsFalse "first.last@[.12.34.56.78]"                                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2931 - assertIsFalse "first.last@[12.34.56.789]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2932 - assertIsFalse "first.last@[::12.34.56.78]"                                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2933 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2934 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                      =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  2935 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2936 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2937 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2938 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2939 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2940 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2941 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2942 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2943 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2944 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2945 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2946 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2947 - assertIsTrue  "first.last@[IPv6:::]"                                                 =   4 =  OK 
     *  2948 - assertIsTrue  "first.last@[IPv6:::b4]"                                               =   4 =  OK 
     *  2949 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                            =   4 =  OK 
     *  2950 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                             =   4 =  OK 
     *  2951 - assertIsTrue  "first.last@[IPv6:a1::]"                                               =   4 =  OK 
     *  2952 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                            =   4 =  OK 
     *  2953 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                              =   4 =  OK 
     *  2954 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                              =   4 =  OK 
     *  2955 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2956 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2957 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2958 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2959 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                    =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2960 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2961 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2962 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2963 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2964 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  OK 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     *  2965 - assertIsFalse "\"qu@test.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2966 - assertIsFalse "ote\"@test.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2967 - assertIsFalse "\"().:;<>[\]@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2968 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2969 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2970 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2971 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2972 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2973 - assertIsFalse "this\ still\"not\allowed@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2974 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2975 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2976 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2977 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2978 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2979 - assertIsFalse ".email@example.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2980 - assertIsFalse "email.@example.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2981 - assertIsFalse "email..email@example.com"                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2982 - assertIsFalse "email@-example.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2983 - assertIsFalse "email@111.222.333.44444"                                              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  2984 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2985 - assertIsFalse "email@[12.34.44.56"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2986 - assertIsFalse "email@14.44.56.34]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2987 - assertIsFalse "email@[1.1.23.5f]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2988 - assertIsFalse "email@[3.256.255.23]"                                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2989 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2990 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2991 - assertIsFalse "first\@last@iana.org"                                                 =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2992 - assertIsFalse "test@example.com "                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2993 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2994 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2995 - assertIsFalse "invalid@about.museum-"                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2996 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2997 - assertIsFalse "abc@def@test.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2998 - assertIsTrue  "abc\@def@test.org"                                                    =   0 =  OK 
     *  2999 - assertIsFalse "abc\@test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3000 - assertIsFalse "@test.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3001 - assertIsFalse ".dot@test.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3002 - assertIsFalse "dot.@test.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3003 - assertIsFalse "two..dot@test.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3004 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3005 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3006 - assertIsFalse "hello world@test.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3007 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3008 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3009 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3010 - assertIsFalse "test.test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3011 - assertIsFalse "test.@test.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3012 - assertIsFalse "test..test@test.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3013 - assertIsFalse ".test@test.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3014 - assertIsFalse "test@test@test.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3015 - assertIsFalse "test@@test.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3016 - assertIsFalse "-- test --@test.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3017 - assertIsFalse "[test]@test.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3018 - assertIsFalse "\"test\"test\"@test.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3019 - assertIsFalse "()[]\;:.><@test.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  3020 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3021 - assertIsFalse ".@test.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3022 - assertIsFalse "Ima Fool@test.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3023 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  3024 - assertIsFalse "foo@[.2.3.4]"                                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3025 - assertIsFalse "first\last@test.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  3026 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3027 - assertIsFalse "first(middle)last@test.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  3028 - assertIsFalse "\"test\"test@test.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3029 - assertIsFalse "()@test.com"                                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  3030 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  3031 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  3032 - assertIsFalse "invalid@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  3033 - assertIsFalse "ä📧@-foo"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3034 - assertIsFalse "ä📧@foo-"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3035 - assertIsFalse "first(comment(inner@comment.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3036 - assertIsFalse "Joe A Smith <email@example.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3037 - assertIsFalse "Joe A Smith email@example.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3038 - assertIsFalse "Joe A Smith <email@example.com->"                                     =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3039 - assertIsFalse "Joe A Smith <email@-example.com->"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3040 - assertIsFalse "Joe A Smith <email>"                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3041 - assertIsTrue  "\"email\"@example.com"                                                =   1 =  OK 
     *  3042 - assertIsTrue  "\"first@last\"@test.org"                                              =   1 =  OK 
     *  3043 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                           =   1 =  OK 
     *  3044 - assertIsTrue  "\"first\"last\"@test.org"                                             =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3045 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                   =   1 =  OK 
     *  3046 - assertIsTrue  "\"first\last\"@test.org"                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3047 - assertIsTrue  "\"Abc\@def\"@test.org"                                                =   1 =  OK 
     *  3048 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                            =   1 =  OK 
     *  3049 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3050 - assertIsTrue  "\"Abc@def\"@test.org"                                                 =   1 =  OK 
     *  3051 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                             =   1 =  OK 
     *  3052 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3053 - assertIsTrue  "\"[[ test ]]\"@test.org"                                              =   1 =  OK 
     *  3054 - assertIsTrue  "\"test.test\"@test.org"                                               =   1 =  OK 
     *  3055 - assertIsTrue  "test.\"test\"@test.org"                                               =   1 =  OK 
     *  3056 - assertIsTrue  "\"test@test\"@test.org"                                               =   1 =  OK 
     *  3057 - assertIsTrue  "\"test  est\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3058 - assertIsTrue  "\"first\".\"last\"@test.org"                                          =   1 =  OK 
     *  3059 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                   =   1 =  OK 
     *  3060 - assertIsTrue  "\"first\".last@test.org"                                              =   1 =  OK 
     *  3061 - assertIsTrue  "first.\"last\"@test.org"                                              =   1 =  OK 
     *  3062 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                               =   1 =  OK 
     *  3063 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                   =   1 =  OK 
     *  3064 - assertIsTrue  "\"first.middle.last\"@test.org"                                       =   1 =  OK 
     *  3065 - assertIsTrue  "\"first..last\"@test.org"                                             =   1 =  OK 
     *  3066 - assertIsTrue  "\"Unicode NULL \"@char.com"                                           =   1 =  OK 
     *  3067 - assertIsTrue  "\"test\blah\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  3068 - assertIsTrue  "\"testlah\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  3069 - assertIsTrue  "\"test\"blah\"@test.org"                                              =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3070 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  3071 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                     =   1 =  OK 
     *  3072 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3073 - assertIsTrue  "\"test blah\"@test.org"                                               =   1 =  OK 
     *  3074 - assertIsTrue  "first.last@test.org"                                                  =   0 =  OK 
     *  3075 - assertIsFalse "jdoe@machine(comment).example"                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  3076 - assertIsTrue  "first.\"\".last@test.org"                                             =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  3077 - assertIsTrue  "\"\"@test.org"                                                        =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  3078 - assertIsTrue  "very.common@example.org"                                              =   0 =  OK 
     *  3079 - assertIsTrue  "test/test@test.com"                                                   =   0 =  OK 
     *  3080 - assertIsTrue  "user-@example.org"                                                    =   0 =  OK 
     *  3081 - assertIsTrue  "firstname.lastname@example.com"                                       =   0 =  OK 
     *  3082 - assertIsTrue  "email@subdomain.example.com"                                          =   0 =  OK 
     *  3083 - assertIsTrue  "firstname+lastname@example.com"                                       =   0 =  OK 
     *  3084 - assertIsTrue  "1234567890@example.com"                                               =   0 =  OK 
     *  3085 - assertIsTrue  "email@example-one.com"                                                =   0 =  OK 
     *  3086 - assertIsTrue  "_______@example.com"                                                  =   0 =  OK 
     *  3087 - assertIsTrue  "email@example.name"                                                   =   0 =  OK 
     *  3088 - assertIsTrue  "email@example.museum"                                                 =   0 =  OK 
     *  3089 - assertIsTrue  "email@example.co.jp"                                                  =   0 =  OK 
     *  3090 - assertIsTrue  "firstname-lastname@example.com"                                       =   0 =  OK 
     *  3091 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  3092 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3093 - assertIsTrue  "first.last@123.test.org"                                              =   0 =  OK 
     *  3094 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  3095 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  3096 - assertIsTrue  "user+mailbox@test.org"                                                =   0 =  OK 
     *  3097 - assertIsTrue  "customer/department=shipping@test.org"                                =   0 =  OK 
     *  3098 - assertIsTrue  "$A12345@test.org"                                                     =   0 =  OK 
     *  3099 - assertIsTrue  "!def!xyz%abc@test.org"                                                =   0 =  OK 
     *  3100 - assertIsTrue  "_somename@test.org"                                                   =   0 =  OK 
     *  3101 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  3102 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3103 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3104 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  3105 - assertIsTrue  "+@b.c"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3106 - assertIsTrue  "TEST@test.org"                                                        =   0 =  OK 
     *  3107 - assertIsTrue  "1234567890@test.org"                                                  =   0 =  OK 
     *  3108 - assertIsTrue  "test-test@test.org"                                                   =   0 =  OK 
     *  3109 - assertIsTrue  "t*est@test.org"                                                       =   0 =  OK 
     *  3110 - assertIsTrue  "+1~1+@test.org"                                                       =   0 =  OK 
     *  3111 - assertIsTrue  "{_test_}@test.org"                                                    =   0 =  OK 
     *  3112 - assertIsTrue  "valid@about.museum"                                                   =   0 =  OK 
     *  3113 - assertIsTrue  "a@bar"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  3114 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3115 - assertIsTrue  "(comment)test@test.org"                                               =   6 =  OK 
     *  3116 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3117 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3118 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3119 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"   =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3120 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  3121 - assertIsTrue  "first(abc\(def)@test.org"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  3122 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                     =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3123 - assertIsTrue  "c@(Chris's host.)public.example"                                      =   6 =  OK 
     *  3124 - assertIsTrue  "_Yosemite.Sam@test.org"                                               =   0 =  OK 
     *  3125 - assertIsTrue  "~@test.org"                                                           =   0 =  OK 
     *  3126 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                        =   6 =  OK 
     *  3127 - assertIsTrue  "test@Bücher.ch"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3128 - assertIsTrue  "あいうえお@example.com"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3129 - assertIsTrue  "Pelé@example.com"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3130 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3131 - assertIsTrue  "我買@屋企.香港"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3132 - assertIsTrue  "二ノ宮@黒川.日本"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3133 - assertIsTrue  "медведь@с-балалайкой.рф"                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3134 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3135 - assertIsTrue  "email@example.com (Joe Smith)"                                        =   6 =  OK 
     *  3136 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                             = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  3137 - assertIsTrue  "first(abc.def).last@test.org"                                         = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  3138 - assertIsTrue  "first(a\"bc.def).last@test.org"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3139 - assertIsTrue  "first.(\")middle.last(\")@test.org"                                   = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  3140 - assertIsTrue  "first().last@test.org"                                                = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  3141 - assertIsTrue  "mymail\@hello@hotmail.com"                                            =   0 =  OK 
     *  3142 - assertIsTrue  "Abc\@def@test.org"                                                    =   0 =  OK 
     *  3143 - assertIsTrue  "Fred\ Bloggs@test.org"                                                =   0 =  OK 
     *  3144 - assertIsTrue  "Joe.\\Blow@test.org"                                                  =   0 =  OK 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ----------------------------------------------------------------------------------------------------
     * 
     *  3145 - assertIsTrue  "me@example.com"                                                       =   0 =  OK 
     *  3146 - assertIsTrue  "a.nonymous@example.com"                                               =   0 =  OK 
     *  3147 - assertIsTrue  "name+tag@example.com"                                                 =   0 =  OK 
     *  3148 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                       =   2 =  OK 
     *  3149 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"    =   4 =  OK 
     *  3150 - assertIsTrue  "me(this is a comment)@example.com"                                    =   6 =  OK 
     *  3151 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                            =   1 =  OK 
     *  3152 - assertIsTrue  "me.example@com"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3153 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                      =   0 =  OK 
     *  3154 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"  =   0 =  OK 
     *  3155 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                     =   0 =  OK 
     *  3156 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                    =   0 =  OK 
     *  3157 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                   =   0 =  OK 
     *  3158 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                  =   0 =  OK 
     *  3159 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3160 - assertIsFalse "me@"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  3161 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3162 - assertIsFalse ".me@example.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3163 - assertIsFalse "me@example..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3164 - assertIsFalse "me\@example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3165 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3166 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3167 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3168 - assertIsFalse "semico...@gmail.com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- my old tests ----------------------------------------------------------------------------------------------------
     * 
     *  3169 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  3170 - assertIsTrue  "A.\"B\"@C.DE"                                                         =   1 =  OK 
     *  3171 - assertIsTrue  "A.B@[1.2.3.4]"                                                        =   2 =  OK 
     *  3172 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                    =   3 =  OK 
     *  3173 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                           =   4 =  OK 
     *  3174 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                       =   5 =  OK 
     *  3175 - assertIsTrue  "(A)B@C.DE"                                                            =   6 =  OK 
     *  3176 - assertIsTrue  "A(B)@C.DE"                                                            =   6 =  OK 
     *  3177 - assertIsTrue  "(A)\"B\"@C.DE"                                                        =   7 =  OK 
     *  3178 - assertIsTrue  "\"A\"(B)@C.DE"                                                        =   7 =  OK 
     *  3179 - assertIsTrue  "(A)B@[1.2.3.4]"                                                       =   2 =  OK 
     *  3180 - assertIsTrue  "A(B)@[1.2.3.4]"                                                       =   2 =  OK 
     *  3181 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                   =   8 =  OK 
     *  3182 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                   =   8 =  OK 
     *  3183 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  3184 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *  3185 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *  3186 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *  3187 - assertIsTrue  "a.b.c.d@domain.com"                                                   =   0 =  OK 
     *  3188 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3189 - assertIsFalse "ABC.DEF.GHI.JKL"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3190 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3191 - assertIsFalse "ABC.DEF @GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3192 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3193 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3194 - assertIsFalse "ABC.DEF@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  3195 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3196 - assertIsFalse "ABC@DEF@GHI.JKL"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3197 - assertIsFalse "@%^%#$@#$@#.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3198 - assertIsFalse "email.domain.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  3199 - assertIsFalse "email@domain@domain.com"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3200 - assertIsFalse "first@last@test.org"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3201 - assertIsFalse "@test@a.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3202 - assertIsFalse "@\"someStringThatMightBe@email.com"                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  3203 - assertIsFalse "test@@test.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  3204 - assertIsFalse "ABCDEF@GHIJKL"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3205 - assertIsFalse "ABC.DEF@GHIJKL"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3206 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  3207 - assertIsFalse "ABC.DEF@GHI.JKL."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  3208 - assertIsFalse "ABC..DEF@GHI.JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3209 - assertIsFalse "ABC.DEF@GHI..JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3210 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  3211 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  3212 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3213 - assertIsFalse "ABC.DEF@."                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  3214 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                 =   1 =  OK 
     *  3215 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                  =   0 =  OK 
     *  3216 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                     =   0 =  OK 
     *  3217 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                     =   0 =  OK 
     *  3218 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                      =   0 =  OK 
     *  3219 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                      =   0 =  OK 
     *  3220 - assertIsFalse "ABC.DEF@GHI.2KL"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  3221 - assertIsFalse "ABC.DEF@GHI.JK-"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3222 - assertIsFalse "ABC.DEF@GHI.JK_"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  3223 - assertIsFalse "ABC.DEF@-HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3224 - assertIsFalse "ABC.DEF@_HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  3225 - assertIsFalse "ABC DEF@GHI.DE"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3226 - assertIsFalse "ABC.DEF@GHI DE"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  3227 - assertIsFalse "A . B & C . D"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3228 - assertIsFalse " A . B & C . D"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3229 - assertIsFalse "(?).[!]@{&}.<:>"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- some more Testcases ----------------------------------------------------------------------------------------------------
     * 
     *  3230 - assertIsFalse "\"\".local.name.starts.with.empty.string1@domain.com"                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3231 - assertIsFalse "local.name.ends.with.empty.string1\"\"@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3232 - assertIsFalse "local.name.with.empty.string1\"\"character@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3233 - assertIsFalse "local.name.with.empty.string1.before\"\".point@domain.com"            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3234 - assertIsFalse "local.name.with.empty.string1.after.\"\"point@domain.com"             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3235 - assertIsFalse "local.name.with.double.empty.string1\"\"\"\"test@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3236 - assertIsFalse "(comment \"\") local.name.with.comment.with.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3237 - assertIsFalse "\"quote\"\"\".local.name.with.qoute.with.empty.string1@domain.com"    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3238 - assertIsFalse "\"\"@empty.string1.domain.com"                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3239 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@empty.string1.domain.com"                    =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3240 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3241 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1@domain.com>"           =   0 =  OK 
     *  3242 - assertIsTrue  "<pointy.brackets2.with.empty.string1@domain.com> name \"\""           =   0 =  OK 
     *  3243 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3244 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3245 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3246 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3247 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3248 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3249 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3250 - assertIsFalse "domain.part.with.comment.with.empty.string1@(comment \"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3251 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3252 - assertIsFalse "ip.v4.with.empty.string1@[123.14\"\"5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3253 - assertIsFalse "ip.v4.with.empty.string1@[123.145\"\".178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3254 - assertIsFalse "ip.v4.with.empty.string1@[123.145.\"\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3255 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90\"\"]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3256 - assertIsFalse "ip.v4.with.empty.string1@[123.145.178.90]\"\""                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3257 - assertIsFalse "ip.v4.with.empty.string1@[\"\"123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3258 - assertIsFalse "ip.v4.with.empty.string1@\"\"[123.145.178.90]"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3259 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3260 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3261 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3262 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3263 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3264 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3265 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3266 - assertIsFalse "a\"\"b.local.name.starts.with.empty.string2@domain.com"               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3267 - assertIsFalse "local.name.ends.with.empty.string2a\"\"b@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3268 - assertIsFalse "local.name.with.empty.string2a\"\"bcharacter@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3269 - assertIsFalse "local.name.with.empty.string2.beforea\"\"b.point@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3270 - assertIsFalse "local.name.with.empty.string2.after.a\"\"bpoint@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3271 - assertIsFalse "local.name.with.double.empty.string2a\"\"ba\"\"btest@domain.com"      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3272 - assertIsFalse "(comment a\"\"b) local.name.with.comment.with.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3273 - assertIsFalse "\"quotea\"\"b\".local.name.with.qoute.with.empty.string2@domain.com"  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3274 - assertIsFalse "a\"\"b@empty.string2.domain.com"                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3275 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@empty.string2.domain.com"        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3276 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  3277 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2@domain.com>"         =   0 =  OK 
     *  3278 - assertIsTrue  "<pointy.brackets2.with.empty.string2@domain.com> name a\"\"b"         =   0 =  OK 
     *  3279 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3280 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3281 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3282 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3283 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3284 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3285 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3286 - assertIsFalse "domain.part.with.comment.with.empty.string2@(comment a\"\"b)domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3287 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3288 - assertIsFalse "ip.v4.with.empty.string2@[123.14a\"\"b5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3289 - assertIsFalse "ip.v4.with.empty.string2@[123.145a\"\"b.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3290 - assertIsFalse "ip.v4.with.empty.string2@[123.145.a\"\"b178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3291 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90a\"\"b]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3292 - assertIsFalse "ip.v4.with.empty.string2@[123.145.178.90]a\"\"b"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3293 - assertIsFalse "ip.v4.with.empty.string2@[a\"\"b123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3294 - assertIsFalse "ip.v4.with.empty.string2@a\"\"b[123.145.178.90]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3295 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3296 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3297 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3298 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3299 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3300 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3301 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3302 - assertIsFalse "\"\"\"\".local.name.starts.with.double.empty.string1@domain.com"      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3303 - assertIsFalse "local.name.ends.with.double.empty.string1\"\"\"\"@domain.com"         =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3304 - assertIsFalse "local.name.with.double.empty.string1\"\"\"\"character@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3305 - assertIsFalse "local.name.with.double.empty.string1.before\"\"\"\".point@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3306 - assertIsFalse "local.name.with.double.empty.string1.after.\"\"\"\"point@domain.com"  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3307 - assertIsFalse "local.name.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3308 - assertIsFalse "(comment \"\"\"\") local.name.with.comment.with.double.empty.string1@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3309 - assertIsFalse "\"quote\"\"\"\"\".local.name.with.qoute.with.double.empty.string1@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3310 - assertIsFalse "\"\"\"\"@double.empty.string1.domain.com"                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3311 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3312 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3313 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1@domain.com>" =   0 =  OK 
     *  3314 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1@domain.com> name \"\"\"\"" =   0 =  OK 
     *  3315 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3316 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3317 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3318 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3319 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3320 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3321 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3322 - assertIsFalse "domain.part.with.comment.with.double.empty.string1@(comment \"\"\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3323 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3324 - assertIsFalse "ip.v4.with.double.empty.string1@[123.14\"\"\"\"5.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3325 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145\"\"\"\".178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3326 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.\"\"\"\"178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3327 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90\"\"\"\"]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3328 - assertIsFalse "ip.v4.with.double.empty.string1@[123.145.178.90]\"\"\"\""             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3329 - assertIsFalse "ip.v4.with.double.empty.string1@[\"\"\"\"123.145.178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3330 - assertIsFalse "ip.v4.with.double.empty.string1@\"\"\"\"[123.145.178.90]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3331 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3332 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3333 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3334 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3335 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3336 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3337 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3338 - assertIsFalse "\"\".\"\".local.name.starts.with.double.empty.string2@domain.com"     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3339 - assertIsFalse "local.name.ends.with.double.empty.string2\"\".\"\"@domain.com"        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3340 - assertIsFalse "local.name.with.double.empty.string2\"\".\"\"character@domain.com"    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3341 - assertIsFalse "local.name.with.double.empty.string2.before\"\".\"\".point@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3342 - assertIsFalse "local.name.with.double.empty.string2.after.\"\".\"\"point@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3343 - assertIsFalse "local.name.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  3344 - assertIsFalse "(comment \"\".\"\") local.name.with.comment.with.double.empty.string2@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3345 - assertIsFalse "\"quote\"\".\"\"\".local.name.with.qoute.with.double.empty.string2@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  3346 - assertIsFalse "\"\".\"\"@double.empty.string2.domain.com"                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3347 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3348 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  3349 - assertIsTrue  "name \"\".\"\" <pointy.brackets1.with.double.empty.string2@domain.com>" =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3350 - assertIsTrue  "<pointy.brackets2.with.double.empty.string2@domain.com> name \"\".\"\"" =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  3351 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3352 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3353 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3354 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3355 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3356 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3357 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3358 - assertIsFalse "domain.part.with.comment.with.double.empty.string2@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  3359 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3360 - assertIsFalse "ip.v4.with.double.empty.string2@[123.14\"\".\"\"5.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3361 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145\"\".\"\".178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3362 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.\"\".\"\"178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3363 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90\"\".\"\"]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3364 - assertIsFalse "ip.v4.with.double.empty.string2@[123.145.178.90]\"\".\"\""            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3365 - assertIsFalse "ip.v4.with.double.empty.string2@[\"\".\"\"123.145.178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3366 - assertIsFalse "ip.v4.with.double.empty.string2@\"\".\"\"[123.145.178.90]"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3367 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3368 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3369 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3370 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3371 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3372 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  3373 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3374 - assertIsTrue  "/.local.name.starts.with.forward.slash@domain.com"                    =   0 =  OK 
     *  3375 - assertIsTrue  "local.name.ends.with.forward.slash/@domain.com"                       =   0 =  OK 
     *  3376 - assertIsTrue  "local.name.with.forward.slash/character@domain.com"                   =   0 =  OK 
     *  3377 - assertIsTrue  "local.name.with.forward.slash.before/.point@domain.com"               =   0 =  OK 
     *  3378 - assertIsTrue  "local.name.with.forward.slash.after./point@domain.com"                =   0 =  OK 
     *  3379 - assertIsTrue  "local.name.with.double.forward.slash//test@domain.com"                =   0 =  OK 
     *  3380 - assertIsTrue  "(comment /) local.name.with.comment.with.forward.slash@domain.com"    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3381 - assertIsTrue  "\"quote/\".local.name.with.qoute.with.forward.slash@domain.com"       =   1 =  OK 
     *  3382 - assertIsTrue  "/@forward.slash.domain.com"                                           =   0 =  OK 
     *  3383 - assertIsTrue  "//////@forward.slash.domain.com"                                      =   0 =  OK 
     *  3384 - assertIsTrue  "/./././././@forward.slash.domain.com"                                 =   0 =  OK 
     *  3385 - assertIsFalse "name / <pointy.brackets1.with.forward.slash@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3386 - assertIsFalse "<pointy.brackets2.with.forward.slash@domain.com> name /"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  3387 - assertIsFalse "domain.part@with/forward.slash.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3388 - assertIsFalse "domain.part@with//double.forward.slash.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3389 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3390 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3391 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3392 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3393 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3394 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3395 - assertIsTrue  "domain.part.with.comment.with.forward.slash@(comment /)domain.com"    =   6 =  OK 
     *  3396 - assertIsFalse "domain.part.only.forward.slash@/.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3397 - assertIsFalse "ip.v4.with.forward.slash@[123.14/5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3398 - assertIsFalse "ip.v4.with.forward.slash@[123.145/.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3399 - assertIsFalse "ip.v4.with.forward.slash@[123.145./178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3400 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90/]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3401 - assertIsFalse "ip.v4.with.forward.slash@[123.145.178.90]/"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3402 - assertIsFalse "ip.v4.with.forward.slash@[/123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3403 - assertIsFalse "ip.v4.with.forward.slash@/[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3404 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3405 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3406 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3407 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3408 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  3409 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  3410 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  3411 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3412 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     *  3413 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3414 - assertIsTrue  "Ärger.Öde@Übel.de"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3415 - assertIsTrue  "Smørrebrød@danmark.dk"                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3416 - assertIsTrue  "ip6.without.brackets@1:2:3:4:5:6:7:8"                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3417 - assertIsTrue  "(space after comment) john.smith@example.com"                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  3418 - assertIsTrue  "email.address.without@topleveldomain"                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3419 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                   =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  3420 - assertIsFalse "@1st.relay,@2nd.relay:user@final.domain"                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * Fillup ist nicht aktiv
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1130   KORREKT 1057 =   93.540 % | FALSCH ERKANNT   73 =    6.460 % = Error 0
     *   ASSERT_IS_FALSE 2290   KORREKT 2278 =   99.476 % | FALSCH ERKANNT   12 =    0.524 % = Error 0
     * 
     *   GESAMT          3420   KORREKT 3335 =   97.515 % | FALSCH ERKANNT   85 =    2.485 % = Error 0
     * 
     * 
     *   Millisekunden    123 = 0.035964912280701755
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
      assertIsTrue( "12.345@678.90.tld" );

      assertIsTrue( "name1.name2@domain1.tld" );
      assertIsTrue( "name1+name2@domain1.tld" );
      assertIsTrue( "name1-name2@domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.tu-domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.tu_domain1.tld" );

      assertIsTrue( "ip4.adress@[1.2.3.4]" );
      assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]" );
      assertIsTrue( "ip4.without.brackets@1.2.3.4" );

      assertIsTrue( "\"quote1\".name1@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@domain1.tld" );
      assertIsTrue( "name1.\"quote1\".name2@domain1.tld" );
      assertIsTrue( "name1.\"quote1\".name2@subdomain1.domain1.tld" );
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
      assertIsTrue( "\"display name\" <local.part@(comment)domain-name.top_level_domain>" );
      assertIsTrue( "\"display name\" <(comment)local.part.\"quote1\"@domain-name.top_level_domain>" );

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
//    wl( "" );
//    wl( "      assertIsTrue( \"" + pCharacter + ".local.name.starts.with." + pName + "@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.name.ends.with." + pName + pCharacter + "@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.name.with." + pName + pCharacter + "character@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.name.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.name.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
//    wl( "      assertIsTrue( \"local.name.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
//    wl( "      assertIsTrue( \"(comment " + pCharacter + ") local.name.with.comment.with." + pName + "@domain.com\" );" );
//    wl( "      assertIsTrue( \"\\\"quote" + pCharacter + "\\\".local.name.with.qoute.with." + pName + "@domain.com\" );" );
//    wl( "      assertIsTrue( \"" + pCharacter + "@" + pName + ".domain.com\" );" );
//    wl( "      assertIsTrue( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@" + pName + ".domain.com\" );" );
//    wl( "      assertIsTrue( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
//    wl( "      assertIsTrue( \"name " + pCharacter + " <pointy.brackets1.with." + pName + "@domain.com>\" );" );
//    wl( "      assertIsTrue( \"<pointy.brackets2.with." + pName + "@domain.com> name " + pCharacter + "\" );" );
//    wl( "" );
//    wl( "      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
//    wl( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
//    wl( "      assertIsFalse( \"domain.part@with.consecutive." + pName + "" + pCharacter + "" + pCharacter + "test.com\" );" );
//    wl( "      assertIsFalse( \"domain.part.with.comment.with." + pName + "@(comment " + pCharacter + ")domain.com\" );" );
//
//    wl( "      assertIsFalse( \"domain.part.only." + pName + "@" + pCharacter + ".com\" );" );
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

    wl( "      assertIsFalse( \"top.level.domain.only@" + pName + "." + pCharacter + "\" );" );

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
