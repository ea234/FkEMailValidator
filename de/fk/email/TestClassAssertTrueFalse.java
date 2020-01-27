package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestClassAssertTrueFalse
{
  private static int          LAUFENDE_ZAHL                 = 0;

  private static int          COUNT_ASSERT_IS_TRUE          = 0;

  private static int          COUNT_ASSERT_IS_FALSE         = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static boolean      KNZ_LOG_AUSGABE               = true;

  private static int          TEST_B_TEST_NR                = 8;

  private static boolean      TEST_B_KNZ_AKTIV              = false;

  private static StringBuffer m_str_buffer                  = null;

  public static void main( String[] args )
  {
    /*
     * NR      Fkt.          Input                                                Result
     * 
     * ---- General Correct ----------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  "A.B@C.DE"                                         =   0 =  OK 
     *     2 - assertIsTrue  "A."B"@C.DE"                                       =   1 =  OK 
     *     3 - assertIsTrue  "A.B@[1.2.3.4]"                                    =   2 =  OK 
     *     4 - assertIsTrue  "A."B"@[1.2.3.4]"                                  =   3 =  OK 
     *     5 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                       =   4 =  OK 
     *     6 - assertIsTrue  "A."B"@[IPv6:1:2:3:4:5:6:7:8]"                     =   5 =  OK 
     *     7 - assertIsTrue  "(A)B@C.DE"                                        =   6 =  OK 
     *     8 - assertIsTrue  "A(B)@C.DE"                                        =   6 =  OK 
     *     9 - assertIsTrue  "(A)"B"@C.DE"                                      =   7 =  OK 
     *    10 - assertIsTrue  ""A"(B)@C.DE"                                      =   7 =  OK 
     *    11 - assertIsTrue  "(A)B@[1.2.3.4]"                                   =   2 =  OK 
     *    12 - assertIsTrue  "A(B)@[1.2.3.4]"                                   =   2 =  OK 
     *    13 - assertIsTrue  "(A)"B"@[1.2.3.4]"                                 =   8 =  OK 
     *    14 - assertIsTrue  ""A"(B)@[1.2.3.4]"                                 =   8 =  OK 
     *    15 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                      =   4 =  OK 
     *    16 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                      =   4 =  OK 
     *    17 - assertIsTrue  "(A)"B"@[IPv6:1:2:3:4:5:6:7:8]"                    =   9 =  OK 
     *    18 - assertIsTrue  ""A"(B)@[IPv6:1:2:3:4:5:6:7:8]"                    =   9 =  OK 
     *    19 - assertIsTrue  "firstname.lastname@domain.com"                    =   0 =  OK 
     *    20 - assertIsTrue  "firstname+lastname@domain.com"                    =   0 =  OK 
     *    21 - assertIsTrue  "firstname-lastname@domain.com"                    =   0 =  OK 
     *    22 - assertIsTrue  "first-name-last-name@d-a-n.com"                   =   0 =  OK 
     *    23 - assertIsTrue  "a.b.c.d@domain.com"                               =   0 =  OK 
     *    24 - assertIsTrue  "1@domain.com"                                     =   0 =  OK 
     *    25 - assertIsTrue  "a@domain.com"                                     =   0 =  OK 
     *    26 - assertIsTrue  "email@domain.co.de"                               =   0 =  OK 
     *    27 - assertIsTrue  "email@domain.com"                                 =   0 =  OK 
     *    28 - assertIsTrue  "email@subdomain.domain.com"                       =   0 =  OK 
     *    29 - assertIsTrue  "2@bde.cc"                                         =   0 =  OK 
     *    30 - assertIsTrue  "-@bde.cc"                                         =   0 =  OK 
     *    31 - assertIsTrue  "a2@bde.cc"                                        =   0 =  OK 
     *    32 - assertIsTrue  "a-b@bde.cc"                                       =   0 =  OK 
     *    33 - assertIsTrue  "ab@b-de.cc"                                       =   0 =  OK 
     *    34 - assertIsTrue  "a+b@bde.cc"                                       =   0 =  OK 
     *    35 - assertIsTrue  "f.f.f@bde.cc"                                     =   0 =  OK 
     *    36 - assertIsTrue  "ab_c@bde.cc"                                      =   0 =  OK 
     *    37 - assertIsTrue  "_-_@bde.cc"                                       =   0 =  OK 
     *    38 - assertIsTrue  "w.b.f@test.com"                                   =   0 =  OK 
     *    39 - assertIsTrue  "w.b.f@test.museum"                                =   0 =  OK 
     *    40 - assertIsTrue  "a.a@test.com"                                     =   0 =  OK 
     *    41 - assertIsTrue  "ab@288.120.150.10.com"                            =   0 =  OK 
     *    42 - assertIsTrue  "ab@[120.254.254.120]"                             =   2 =  OK 
     *    43 - assertIsTrue  "1234567890@domain.com"                            =   0 =  OK 
     *    44 - assertIsTrue  "john.smith@example.com"                           =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    45 - assertIsFalse null                                               =  10 =  OK    Laenge: Eingabe ist null
     *    46 - assertIsFalse ""                                                 =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    47 - assertIsFalse "        "                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    48 - assertIsFalse "ABCDEFGHIJKLMNOP"                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    49 - assertIsFalse "ABC.DEF.GHI.JKL"                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    50 - assertIsFalse "ABC.DEF@ GHI.JKL"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    51 - assertIsFalse "ABC.DEF @GHI.JKL"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    52 - assertIsFalse "ABC.DEF @ GHI.JKL"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    53 - assertIsFalse "@"                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    54 - assertIsFalse "@.@.@."                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    55 - assertIsFalse "@.@.@GHI.JKL"                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    56 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    57 - assertIsFalse "@@@GHI.JKL"                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    58 - assertIsFalse "@GHI.JKL"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    59 - assertIsFalse "ABC.DEF@"                                         =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    60 - assertIsFalse "ABC.DEF@@GHI.JKL"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    61 - assertIsFalse "@%^%#$@#$@#.com"                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    62 - assertIsFalse "@domain.com"                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    63 - assertIsFalse "email.domain.com"                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    64 - assertIsFalse "email@domain@domain.com"                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    65 - assertIsFalse "ABCDEF@GHIJKL"                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    66 - assertIsFalse "ABC.DEF@GHIJKL"                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    67 - assertIsFalse ".ABC.DEF@GHI.JKL"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    68 - assertIsFalse "ABC.DEF@GHI.JKL."                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    69 - assertIsFalse "ABC..DEF@GHI.JKL"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    70 - assertIsFalse "ABC.DEF@GHI..JKL"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    71 - assertIsFalse "ABC.DEF@GHI.JKL.."                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    72 - assertIsFalse "ABC.DEF.@GHI.JKL"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    73 - assertIsFalse "ABC.DEF@.GHI.JKL"                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    74 - assertIsFalse "ABC.DEF@."                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    75 - assertIsFalse "john..doe@example.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    76 - assertIsFalse "john.doe@example..com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    77 - assertIsTrue  ""john..doe"@example.com"                          =   1 =  OK 
     *    78 - assertIsFalse "..........@domain."                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    79 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                              =   0 =  OK 
     *    80 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                 =   0 =  OK 
     *    81 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                 =   0 =  OK 
     *    82 - assertIsTrue  "ABC.DEF@GHI.JK2"                                  =   0 =  OK 
     *    83 - assertIsTrue  "ABC.DEF@2HI.JKL"                                  =   0 =  OK 
     *    84 - assertIsFalse "ABC.DEF@GHI.2KL"                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    85 - assertIsFalse "ABC.DEF@GHI.JK-"                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    86 - assertIsFalse "ABC.DEF@GHI.JK_"                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    87 - assertIsFalse "ABC.DEF@-HI.JKL"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    88 - assertIsFalse "ABC.DEF@_HI.JKL"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    89 - assertIsFalse "ABC DEF@GHI.DE"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    90 - assertIsFalse "ABC.DEF@GHI DE"                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    91 - assertIsFalse "A . B & C . D"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    92 - assertIsFalse " A . B & C . D"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    93 - assertIsFalse "(?).[!]@{&}.<:>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    94 - assertIsTrue  "&local&&name&with&$@amp.com"                      =   0 =  OK 
     *    95 - assertIsTrue  "*local**name*with*@asterisk.com"                  =   0 =  OK 
     *    96 - assertIsTrue  "$local$$name$with$@dollar.com"                    =   0 =  OK 
     *    97 - assertIsTrue  "=local==name=with=@equality.com"                  =   0 =  OK 
     *    98 - assertIsTrue  "!local!!name!with!@exclamation.com"               =   0 =  OK 
     *    99 - assertIsTrue  "`local``name`with`@grave-accent.com"              =   0 =  OK 
     *   100 - assertIsTrue  "#local##name#with#@hash.com"                      =   0 =  OK 
     *   101 - assertIsTrue  "-local--name-with-@hypen.com"                     =   0 =  OK 
     *   102 - assertIsTrue  "{local{name{{with{@leftbracket.com"               =   0 =  OK 
     *   103 - assertIsTrue  "%local%%name%with%@percentage.com"                =   0 =  OK 
     *   104 - assertIsTrue  "|local||name|with|@pipe.com"                      =   0 =  OK 
     *   105 - assertIsTrue  "+local++name+with+@plus.com"                      =   0 =  OK 
     *   106 - assertIsTrue  "?local??name?with?@question.com"                  =   0 =  OK 
     *   107 - assertIsTrue  "}local}name}}with}@rightbracket.com"              =   0 =  OK 
     *   108 - assertIsTrue  "~local~~name~with~@tilde.com"                     =   0 =  OK 
     *   109 - assertIsTrue  "^local^^name^with^@xor.com"                       =   0 =  OK 
     *   110 - assertIsTrue  "_local__name_with_@underscore.com"                =   0 =  OK 
     *   111 - assertIsFalse ":local::name:with:@colon.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   112 - assertIsTrue  "domain.part@with-hyphen.com"                      =   0 =  OK 
     *   113 - assertIsTrue  "domain.part@with_underscore.com"                  =   0 =  OK 
     *   114 - assertIsFalse "domain.part@-starts.with.hyphen.com"              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   115 - assertIsFalse "domain.part@ends.with.hyphen.com-"                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   116 - assertIsFalse "domain.part@with&amp.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   117 - assertIsFalse "domain.part@with*asterisk.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   118 - assertIsFalse "domain.part@with$dollar.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   119 - assertIsFalse "domain.part@with=equality.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   120 - assertIsFalse "domain.part@with!exclamation.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   121 - assertIsFalse "domain.part@with?question.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   122 - assertIsFalse "domain.part@with`grave-accent.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   123 - assertIsFalse "domain.part@with#hash.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   124 - assertIsFalse "domain.part@with%percentage.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   125 - assertIsFalse "domain.part@with|pipe.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   126 - assertIsFalse "domain.part@with+plus.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   127 - assertIsFalse "domain.part@with{leftbracket.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   128 - assertIsFalse "domain.part@with}rightbracket.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   129 - assertIsFalse "domain.part@with(leftbracket.com"                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   130 - assertIsFalse "domain.part@with)rightbracket.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   131 - assertIsFalse "domain.part@with[leftbracket.com"                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   132 - assertIsFalse "domain.part@with]rightbracket.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   133 - assertIsFalse "domain.part@with~tilde.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   134 - assertIsFalse "domain.part@with^xor.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   135 - assertIsFalse "domain.part@with:colon.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   136 - assertIsFalse "DomainHyphen@-atstart"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   137 - assertIsFalse "DomainHyphen@atend-.com"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   138 - assertIsFalse "DomainHyphen@bb.-cc"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   139 - assertIsFalse "DomainHyphen@bb.-cc-"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   140 - assertIsFalse "DomainHyphen@bb.cc-"                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   141 - assertIsFalse "DomainHyphen@bb.c-c"                              =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   142 - assertIsFalse "DomainNotAllowedCharacter@/atstart"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   143 - assertIsFalse "DomainNotAllowedCharacter@a,start"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   144 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   145 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   146 - assertIsFalse "DomainNotAllowedCharacter@example'"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   147 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   148 - assertIsTrue  "domain.starts.with.digit@2domain.com"             =   0 =  OK 
     *   149 - assertIsTrue  "domain.ends.with.digit@domain2.com"               =   0 =  OK 
     *   150 - assertIsFalse "tld.starts.with.digit@domain.2com"                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   151 - assertIsTrue  "tld.ends.with.digit@domain.com2"                  =   0 =  OK 
     *   152 - assertIsFalse "email@=qowaiv.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   153 - assertIsFalse "email@plus+.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   154 - assertIsFalse "email@domain.com>"                                =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   155 - assertIsFalse "email@mailto:domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   156 - assertIsFalse "mailto:mailto:email@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   157 - assertIsFalse "email@-domain.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   158 - assertIsFalse "email@domain-.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   159 - assertIsFalse "email@domain.com-"                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   160 - assertIsFalse "email@{leftbracket.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   161 - assertIsFalse "email@rightbracket}.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   162 - assertIsFalse "email@pp|e.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   163 - assertIsTrue  "email@domain.domain.domain.com.com"               =   0 =  OK 
     *   164 - assertIsTrue  "email@domain.domain.domain.domain.com.com"        =   0 =  OK 
     *   165 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com" =   0 =  OK 
     *   166 - assertIsFalse "unescaped white space@fake$com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   167 - assertIsFalse ""Joe Smith email@domain.com"                      =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   168 - assertIsFalse ""Joe Smith' email@domain.com"                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   169 - assertIsFalse ""Joe Smith"email@domain.com"                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   170 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   171 - assertIsTrue  "{john'doe}@my.server"                             =   0 =  OK 
     *   172 - assertIsTrue  "email@domain-one.com"                             =   0 =  OK 
     *   173 - assertIsTrue  "_______@domain.com"                               =   0 =  OK 
     *   174 - assertIsTrue  "?????@domain.com"                                 =   0 =  OK 
     *   175 - assertIsFalse "local@?????.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   176 - assertIsTrue  ""B3V3RLY H1LL$"@example.com"                      =   1 =  OK 
     *   177 - assertIsTrue  ""-- --- .. -."@sh.de"                             =   1 =  OK 
     *   178 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                        =   0 =  OK 
     *   179 - assertIsTrue  ""\" + \"select * from user\" + \""@example.de"    =   1 =  OK 
     *   180 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                 =   0 =  OK 
     *   181 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   182 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   183 - assertIsFalse ""()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a"@example.org" =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   184 - assertIsFalse """@[]"                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   185 - assertIsFalse """@[1"                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   186 - assertIsFalse "ABC.DEF@[]"                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   187 - assertIsTrue  ""    "@[1.2.3.4]"                                 =   3 =  OK 
     *   188 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                        =   2 =  OK 
     *   189 - assertIsTrue  ""ABC.DEF"@[127.0.0.1]"                            =   3 =  OK 
     *   190 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                =   2 =  OK 
     *   191 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   192 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   193 - assertIsFalse "ABC.DEF[1.2.3.4]"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   194 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   195 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   196 - assertIsFalse "ABC.DEF@[][][][]"                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   197 - assertIsFalse "ABC.DEF@[....]"                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   198 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   199 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   200 - assertIsFalse ""[1.2.3.4]"@[5.6.7.8]"                            =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   201 - assertIsFalse "ABC.DEF@MyDomain[1.2.3.4]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   202 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                            =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   203 - assertIsFalse "ABC.DEF@[1.2.3.456]"                              =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   204 - assertIsFalse "ABC.DEF@[..]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   205 - assertIsFalse "ABC.DEF@[.2.3.4]"                                 =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   206 - assertIsFalse "ABC.DEF@[]"                                       =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   207 - assertIsFalse "ABC.DEF@[1]"                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   208 - assertIsFalse "ABC.DEF@[1.2]"                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   209 - assertIsFalse "ABC.DEF@[1.2.3]"                                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   210 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   211 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                            =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   212 - assertIsFalse "ABC.DEF@[MyDomain.de]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   213 - assertIsFalse "ABC.DEF@[1.2.3.]"                                 =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   214 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   215 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   216 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   217 - assertIsFalse "ABC.DEF@[1.2.3.4"                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   218 - assertIsFalse "ABC.DEF@1.2.3.4]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   219 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   220 - assertIsFalse "ABC.DEF@[12.34]"                                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   221 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   222 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   223 - assertIsFalse "ABC.DEF@[1.2...3.4]"                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   224 - assertIsTrue  "email@[123.123.123.123]"                          =   2 =  OK 
     *   225 - assertIsFalse "email@111.222.333"                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   226 - assertIsFalse "email@111.222.333.256"                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   227 - assertIsFalse "email@[123.123.123.123"                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   228 - assertIsFalse "email@[123.123.123].123"                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   229 - assertIsFalse "email@123.123.123.123]"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   230 - assertIsFalse "email@123.123.[123.123]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   231 - assertIsFalse "ab@988.120.150.10"                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   232 - assertIsFalse "ab@120.256.256.120"                               =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   233 - assertIsFalse "ab@120.25.1111.120"                               =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   234 - assertIsFalse "ab@[188.120.150.10"                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   235 - assertIsFalse "ab@188.120.150.10]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   236 - assertIsFalse "ab@[188.120.150.10].com"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   237 - assertIsTrue  "ab@188.120.150.10"                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   238 - assertIsTrue  "ab@1.0.0.10"                                      =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   239 - assertIsTrue  "ab@120.25.254.120"                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   240 - assertIsTrue  "ab@01.120.150.1"                                  =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   241 - assertIsTrue  "ab@88.120.150.021"                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   242 - assertIsTrue  "ab@88.120.150.01"                                 =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   243 - assertIsTrue  "email@123.123.123.123"                            =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   244 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                       =   4 =  OK 
     *   245 - assertIsFalse "ABC.DEF@[IP"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   246 - assertIsFalse "ABC.DEF@[IPv6]"                                   =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   247 - assertIsFalse "ABC.DEF@[IPv6:]"                                  =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   248 - assertIsFalse "ABC.DEF@[IPv6::::::]"                             =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   249 - assertIsFalse "ABC.DEF@[IPv6:1]"                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   250 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   251 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                             =   4 =  OK 
     *   252 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                           =   4 =  OK 
     *   253 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:]"                        =  44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   254 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                       =   4 =  OK 
     *   255 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                     =   4 =  OK 
     *   256 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   257 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                   =   4 =  OK 
     *   258 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   259 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                           =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   260 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   261 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   262 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   263 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                    =   4 =  OK 
     *   264 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   265 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   266 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   267 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   268 - assertIsTrue  "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]"                   =   4 =  OK 
     *   269 - assertIsFalse "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   270 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   271 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                     =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   272 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   273 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   274 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                  =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   275 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   276 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"    =   4 =  OK 
     *   277 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]" =   4 =  OK 
     *   278 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]" =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   279 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]" =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   280 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]" =   4 =  OK 
     *   281 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   282 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                   =   4 =  OK 
     *   283 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                   =   4 =  OK 
     *   284 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                       =   4 =  OK 
     *   285 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                       =   4 =  OK 
     *   286 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                    =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   287 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                    =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   288 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                   =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   289 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   290 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *   291 - assertIsTrue  ""ABC.DEF"@GHI.DE"                                 =   1 =  OK 
     *   292 - assertIsTrue  ""ABC DEF"@GHI.DE"                                 =   1 =  OK 
     *   293 - assertIsTrue  ""ABC@DEF"@GHI.DE"                                 =   1 =  OK 
     *   294 - assertIsTrue  ""ABC\"DEF"@GHI.DE"                                =   1 =  OK 
     *   295 - assertIsTrue  ""ABC\@DEF"@GHI.DE"                                =   1 =  OK 
     *   296 - assertIsTrue  ""ABC\'DEF"@GHI.DE"                                =   1 =  OK 
     *   297 - assertIsTrue  ""ABC\\DEF"@GHI.DE"                                =   1 =  OK 
     *   298 - assertIsFalse ""ABC DEF@G"HI.DE"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   299 - assertIsFalse """@GHI.DE"                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   300 - assertIsFalse ""ABC.DEF@G"HI.DE"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   301 - assertIsFalse "A@G"HI.DE"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   302 - assertIsFalse ""@GHI.DE"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   303 - assertIsFalse "ABC.DEF.""                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   304 - assertIsFalse "ABC.DEF@"""                                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   305 - assertIsFalse "ABC.DEF@G"HI.DE"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   306 - assertIsFalse "ABC.DEF@GHI.DE""                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   307 - assertIsFalse "ABC.DEF@"GHI.DE"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   308 - assertIsFalse ""Escape.Sequenz.Ende""                            =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   309 - assertIsFalse "ABC.DEF"GHI.DE"                                   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   310 - assertIsFalse "ABC.DEF"@GHI.DE"                                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   311 - assertIsFalse "ABC.DE"F@GHI.DE"                                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   312 - assertIsFalse ""ABC.DEF@GHI.DE"                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   313 - assertIsFalse ""ABC.DEF@GHI.DE""                                 =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   314 - assertIsTrue  "".ABC.DEF"@GHI.DE"                                =   1 =  OK 
     *   315 - assertIsTrue  ""ABC.DEF."@GHI.DE"                                =   1 =  OK 
     *   316 - assertIsTrue  ""ABC".DEF."GHI"@JKL.de"                           =   1 =  OK 
     *   317 - assertIsFalse "A"BC".DEF."GHI"@JKL.de"                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   318 - assertIsFalse ""ABC".DEF.G"HI"@JKL.de"                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   319 - assertIsFalse ""AB"C.DEF."GHI"@JKL.de"                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   320 - assertIsFalse ""ABC".DEF."GHI"J@KL.de"                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   321 - assertIsFalse ""AB"C.D"EF"@GHI.DE"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   322 - assertIsFalse ""Ende.am.Eingabeende""                            =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   323 - assertIsFalse "0"00.000"@GHI.JKL"                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   324 - assertIsTrue  ""Joe Smith" email@domain.com"                     =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   325 - assertIsTrue  ""Joe\tSmith" email@domain.com"                    =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   326 - assertIsTrue  ""Joe"Smith" email@domain.com"                     =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *   327 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                 =   6 =  OK 
     *   328 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   329 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                 =   6 =  OK 
     *   330 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                             =   6 =  OK 
     *   331 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                       =   6 =  OK 
     *   332 - assertIsFalse "ABC.DEF@             (MNO)"                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   333 - assertIsFalse "ABC.DEF@   .         (MNO)"                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   334 - assertIsFalse "ABC.DEF              (MNO)"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   335 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                       =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   336 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   337 - assertIsFalse "ABC.DEF@GHI.JKL          "                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   338 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   339 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                               =   6 =  OK 
     *   340 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                =   6 =  OK 
     *   341 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                =   6 =  OK 
     *   342 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                =   6 =  OK 
     *   343 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                =   6 =  OK 
     *   344 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                            =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   345 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                             = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   346 - assertIsFalse "(ABC).DEF@GHI.JKL"                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   347 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   348 - assertIsFalse "ABC.DEF@(GHI).JKL"                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   349 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                            = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   350 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   351 - assertIsFalse "AB(CD)EF@GHI.JKL"                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   352 - assertIsFalse "AB.(CD).EF@GHI.JKL"                               = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   353 - assertIsFalse "AB."(CD)".EF@GHI.JKL"                             =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   354 - assertIsFalse "(ABCDEF)@GHI.JKL"                                 =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   355 - assertIsFalse "(ABCDEF).@GHI.JKL"                                = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   356 - assertIsFalse "(AB"C)DEF@GHI.JKL"                                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   357 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   358 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                               =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   359 - assertIsFalse "ABC(DEF@GHI.JKL"                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   360 - assertIsFalse "ABC.DEF@GHI)JKL"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   361 - assertIsFalse ")ABC.DEF@GHI.JKL"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   362 - assertIsFalse "ABC(DEF@GHI).JKL"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   363 - assertIsFalse "ABC(DEF.GHI).JKL"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   364 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   365 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   366 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   367 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   368 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   369 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   370 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                       =   2 =  OK 
     *   371 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                      = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *   372 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                       =   2 =  OK 
     *   373 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                   =   2 =  OK 
     *   374 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   375 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"           =   4 =  OK 
     *   376 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"           =   4 =  OK 
     *   377 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"       =   4 =  OK 
     *   378 - assertIsTrue  "(comment)john.smith@example.com"                  =   6 =  OK 
     *   379 - assertIsTrue  "john.smith(comment)@example.com"                  =   6 =  OK 
     *   380 - assertIsTrue  "john.smith@(comment)example.com"                  =   6 =  OK 
     *   381 - assertIsTrue  "john.smith@example.com(comment)"                  =   6 =  OK 
     *   382 - assertIsFalse "john.smith@exampl(comment)e.com"                  = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   383 - assertIsFalse "john.s(comment)mith@example.com"                  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   384 - assertIsFalse "john.smith(comment)@(comment)example.com"         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   385 - assertIsFalse "john.smith(com@ment)example.com"                  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   386 - assertIsFalse "email( (nested) )@plus.com"                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   387 - assertIsFalse "email)mirror(@plus.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   388 - assertIsFalse "email@plus.com (not closed comment"               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   389 - assertIsFalse "email(with @ in comment)plus.com"                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   390 - assertIsTrue  "email@domain.com (joe Smith)"                     =   6 =  OK 
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *   391 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                        =   0 =  OK 
     *   392 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                        =   0 =  OK 
     *   393 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                         =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   394 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                         =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   395 - assertIsTrue  ""ABC DEF "<ABC.DEF@GHI.JKL>"                      =   0 =  OK 
     *   396 - assertIsFalse ""ABC<DEF>"@JKL.DE"                                =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   397 - assertIsFalse ">"                                                =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   398 - assertIsFalse ""ABC<DEF@GHI.COM>"@JKL.DE"                        =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   399 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   400 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   401 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   402 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   403 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   404 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   405 - assertIsFalse "ABC DEF <A@A>"                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   406 - assertIsFalse "<A@A> ABC DEF"                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   407 - assertIsFalse "ABC DEF <>"                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   408 - assertIsFalse "<> ABC DEF"                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   409 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                =   0 =  OK 
     *   410 - assertIsTrue  "Joe Smith <email@domain.com>"                     =   0 =  OK 
     *   411 - assertIsFalse "Joe Smith <mailto:email@domain.com>"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   412 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   413 - assertIsTrue  "Non EMail part <(comment)Local."Part"@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *   414 - assertIsTrue  "Non EMail part <Local."Part"(comment)@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *   415 - assertIsTrue  "<(comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part" =   9 =  OK 
     *   416 - assertIsTrue  "<Local."Part"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " =   9 =  OK 
     *   417 - assertIsFalse "Non EMail part < (comment)Local."Part"@[IPv6::ffff:127.0.0.1]>" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   418 - assertIsFalse "Non EMail part <Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]>" =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   419 - assertIsFalse "< (comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   420 - assertIsFalse "<Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   421 - assertIsFalse "Test |<gaaf <email@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   422 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   423 - assertIsFalse ""With extra < within quotes" Display Name<email@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *   424 - assertIsTrue  "A@B.CD"                                           =   0 =  OK 
     *   425 - assertIsFalse "A@B.C"                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   426 - assertIsFalse "A@COM"                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   427 - assertIsTrue  "ABC.DEF@GHI.JKL"                                  =   0 =  OK 
     *   428 - assertIsTrue  "ABC.DEF@GHI.J"                                    =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   429 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *   430 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *   431 - assertIsTrue  "A@B.CD"                                           =   0 =  OK 
     *   432 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *   433 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *   434 - assertIsTrue  "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" =   0 =  OK 
     *   435 - assertIsFalse "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   436 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   437 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   438 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   439 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   440 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   441 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   442 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   443 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   444 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   445 - assertIsFalse ""very.(z),:;<>[]\".VERY.\"very@\\ \"very\".unusual"@strange.example.com" =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   446 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   447 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   448 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   449 - assertIsTrue  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   450 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   451 - assertIsTrue  "email@domain.topleveldomain"                      =   0 =  OK 
     * 
     * 
     * ---- Testclass EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   452 - assertIsFalse "nolocalpart.com"                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   453 - assertIsFalse "test@example.com test"                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   454 - assertIsFalse "user  name@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   455 - assertIsFalse "user   name@example.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   456 - assertIsFalse "example.@example.co.uk"                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   457 - assertIsFalse "example@example@example.co.uk"                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   458 - assertIsFalse "(test_exampel@example.fr}"                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   459 - assertIsFalse "example(example)example@example.co.uk"            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   460 - assertIsFalse ".example@localhost"                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   461 - assertIsFalse "ex\ample@localhost"                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   462 - assertIsFalse "example@local\host"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   463 - assertIsFalse "example@localhost."                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   464 - assertIsFalse "user name@example.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   465 - assertIsFalse "username@ example . com"                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   466 - assertIsFalse "example@(fake}.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   467 - assertIsFalse "example@(fake.com"                                =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   468 - assertIsFalse "username@example,com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   469 - assertIsFalse "usern,ame@example.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   470 - assertIsFalse "user[na]me@example.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   471 - assertIsFalse """"@iana.org"                                     =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   472 - assertIsFalse ""\"@iana.org"                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   473 - assertIsFalse ""test"test@iana.org"                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   474 - assertIsFalse ""test""test"@iana.org"                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   475 - assertIsTrue  ""test"."test"@iana.org"                           =   1 =  OK 
     *   476 - assertIsTrue  ""test".test@iana.org"                             =   1 =  OK 
     *   477 - assertIsFalse ""test\"@iana.org"                                 =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   478 - assertIsFalse "\r\ntest@iana.org"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   479 - assertIsFalse "\r\n test@iana.org"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   480 - assertIsFalse "\r\n \r\ntest@iana.org"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   481 - assertIsFalse "\r\n \r\ntest@iana.org"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   482 - assertIsFalse "\r\n \r\n test@iana.org"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   483 - assertIsFalse "test@iana.org \r\n"                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   484 - assertIsFalse "test@iana.org \r\n "                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   485 - assertIsFalse "test@iana.org \r\n \r\n"                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   486 - assertIsFalse "test@iana.org \r\n\r\n"                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   487 - assertIsFalse "test@iana.org  \r\n\r\n "                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   488 - assertIsFalse "test@iana/icann.org"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   489 - assertIsFalse "test@foo;bar.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   490 - assertIsFalse "a@test.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   491 - assertIsFalse "comment)example@example.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   492 - assertIsFalse "comment(example))@example.com"                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   493 - assertIsFalse "example@example)comment.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   494 - assertIsFalse "example@example(comment)).com"                    = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   495 - assertIsFalse "example@[1.2.3.4"                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   496 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                    =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   497 - assertIsFalse "exam(ple@exam).ple"                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   498 - assertIsFalse "example@(example))comment.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   499 - assertIsTrue  "example@example.com"                              =   0 =  OK 
     *   500 - assertIsTrue  "example@example.co.uk"                            =   0 =  OK 
     *   501 - assertIsTrue  "example_underscore@example.fr"                    =   0 =  OK 
     *   502 - assertIsTrue  "example@localhost"                                =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   503 - assertIsTrue  "exam'ple@example.com"                             =   0 =  OK 
     *   504 - assertIsTrue  "exam\ ple@example.com"                            =   0 =  OK 
     *   505 - assertIsFalse "example((example))@fakedfake.co.uk"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   506 - assertIsFalse "example@faked(fake).co.uk"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   507 - assertIsTrue  "example+@example.com"                             =   0 =  OK 
     *   508 - assertIsTrue  "example@with-hyphen.example.com"                  =   0 =  OK 
     *   509 - assertIsTrue  "with-hyphen@example.com"                          =   0 =  OK 
     *   510 - assertIsTrue  "example@1leadingnum.example.com"                  =   0 =  OK 
     *   511 - assertIsTrue  "1leadingnum@example.com"                          =   0 =  OK 
     *   512 - assertIsTrue  "инфо@письмо.рф"                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   513 - assertIsTrue  ""username"@example.com"                           =   1 =  OK 
     *   514 - assertIsTrue  ""user,name"@example.com"                          =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   515 - assertIsTrue  ""user name"@example.com"                          =   1 =  OK 
     *   516 - assertIsTrue  ""user@name"@example.com"                          =   1 =  OK 
     *   517 - assertIsFalse ""\a"@iana.org"                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   518 - assertIsTrue  ""test\ test"@iana.org"                            =   1 =  OK 
     *   519 - assertIsFalse """@iana.org"                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   520 - assertIsFalse """@[]"                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   521 - assertIsTrue  ""\""@iana.org"                                    =   1 =  OK 
     * 
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   522 - assertIsFalse "..@test.com"                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   523 - assertIsFalse ".a@test.com"                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   524 - assertIsFalse "ab@sd@dd"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   525 - assertIsFalse ".@s.dd"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   526 - assertIsFalse "a@b.-de.cc"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   527 - assertIsFalse "a@bde-.cc"                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   528 - assertIsFalse "a@b._de.cc"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   529 - assertIsFalse "a@bde_.cc"                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   530 - assertIsFalse "a@bde.cc."                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   531 - assertIsFalse "ab@b+de.cc"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   532 - assertIsFalse "a..b@bde.cc"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   533 - assertIsFalse "_@bde.cc,"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   534 - assertIsFalse "plainaddress"                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   535 - assertIsFalse "plain.address"                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   536 - assertIsFalse ".email@domain.com"                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   537 - assertIsFalse "email.@domain.com"                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   538 - assertIsFalse "email..email@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   539 - assertIsFalse "email@.domain.com"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   540 - assertIsFalse "email@domain.com."                                =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   541 - assertIsFalse "email@domain..com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   542 - assertIsFalse "MailTo:casesensitve@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   543 - assertIsFalse "mailto:email@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   544 - assertIsFalse "email@domain"                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   545 - assertIsTrue  "someone@somewhere.com"                            =   0 =  OK 
     *   546 - assertIsTrue  "someone@somewhere.co.uk"                          =   0 =  OK 
     *   547 - assertIsTrue  "someone+tag@somewhere.net"                        =   0 =  OK 
     *   548 - assertIsTrue  "futureTLD@somewhere.fooo"                         =   0 =  OK 
     *   549 - assertIsFalse "myemailsample.com"                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   550 - assertIsTrue  "myemail@sample"                                   =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   551 - assertIsTrue  "myemail@sample.com"                               =   0 =  OK 
     *   552 - assertIsFalse "myemail@@sample.com"                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   553 - assertIsFalse "myemail@sa@mple.com"                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   554 - assertIsTrue  ""myemail@sa"@mple.com"                            =   1 =  OK 
     *   555 - assertIsFalse "a."b@c".x."@".d.e@f.g@"                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   556 - assertIsTrue  ""foo\@bar"@Something.com"                         =   1 =  OK 
     *   557 - assertIsFalse "Foobar Some@thing.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   558 - assertIsFalse "foo@bar@machine.subdomain.example.museum"         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   559 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"        =   0 =  OK 
     *   560 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   561 - assertIsTrue  "cog@wheel.com"                                    =   0 =  OK 
     *   562 - assertIsTrue  ""cogwheel the orange"@example.com"                =   1 =  OK 
     *   563 - assertIsFalse "123@$.xyz"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   564 - assertIsTrue  "david.jones@proseware.com"                        =   0 =  OK 
     *   565 - assertIsTrue  "d.j@server1.proseware.com"                        =   0 =  OK 
     *   566 - assertIsTrue  "jones@ms1.proseware.com"                          =   0 =  OK 
     *   567 - assertIsFalse "j.@server1.proseware.com"                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   568 - assertIsTrue  "j@proseware.com9"                                 =   0 =  OK 
     *   569 - assertIsTrue  "js#internal@proseware.com"                        =   0 =  OK 
     *   570 - assertIsTrue  "j_9@[129.126.118.1]"                              =   2 =  OK 
     *   571 - assertIsFalse "j..s@proseware.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   572 - assertIsTrue  "js*@proseware.com"                                =   0 =  OK 
     *   573 - assertIsFalse "js@proseware..com"                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   574 - assertIsTrue  "js@proseware.com9"                                =   0 =  OK 
     *   575 - assertIsTrue  "j.s@server1.proseware.com"                        =   0 =  OK 
     *   576 - assertIsTrue  ""j\"s"@proseware.com"                             =   1 =  OK 
     *   577 - assertIsFalse "dasddas-@.com"                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   578 - assertIsTrue  "-asd@das.com"                                     =   0 =  OK 
     *   579 - assertIsFalse "as3d@dac.coas-"                                   =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   580 - assertIsTrue  "dsq!a?@das.com"                                   =   0 =  OK 
     *   581 - assertIsTrue  "_dasd@sd.com"                                     =   0 =  OK 
     *   582 - assertIsFalse "dad@sds"                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   583 - assertIsTrue  "asd-@asd.com"                                     =   0 =  OK 
     *   584 - assertIsTrue  "dasd_-@jdas.com"                                  =   0 =  OK 
     *   585 - assertIsFalse "asd@dasd@asd.cm"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   586 - assertIsFalse "da23@das..com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   587 - assertIsTrue  "_dasd_das_@9.com"                                 =   0 =  OK 
     *   588 - assertIsTrue  "d23d@da9.co9"                                     =   0 =  OK 
     *   589 - assertIsTrue  "dasd.dadas@dasd.com"                              =   0 =  OK 
     *   590 - assertIsTrue  "dda_das@das-dasd.com"                             =   0 =  OK 
     *   591 - assertIsTrue  "dasd-dasd@das.com.das"                            =   0 =  OK 
     *   592 - assertIsFalse "fdsa"                                             =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   593 - assertIsFalse "fdsa@"                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   594 - assertIsFalse "fdsa@fdsa"                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   595 - assertIsFalse "fdsa@fdsa."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   596 - assertIsFalse "@b.com"                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   597 - assertIsFalse "a@.com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   598 - assertIsTrue  "a@bcom"                                           =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   599 - assertIsTrue  "a.b@com"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   600 - assertIsFalse "a@b."                                             =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   601 - assertIsTrue  "ab@c.com"                                         =   0 =  OK 
     *   602 - assertIsTrue  "a@bc.com"                                         =   0 =  OK 
     *   603 - assertIsTrue  "a@b.com"                                          =   0 =  OK 
     *   604 - assertIsTrue  "a@b.c.com"                                        =   0 =  OK 
     *   605 - assertIsTrue  "a+b@c.com"                                        =   0 =  OK 
     *   606 - assertIsTrue  "a@123.45.67.89"                                   =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   607 - assertIsTrue  "%2@gmail.com"                                     =   0 =  OK 
     *   608 - assertIsTrue  ""%2"@gmail.com"                                   =   1 =  OK 
     *   609 - assertIsTrue  ""a..b"@gmail.com"                                 =   1 =  OK 
     *   610 - assertIsTrue  ""a_b"@gmail.com"                                  =   1 =  OK 
     *   611 - assertIsTrue  "_@gmail.com"                                      =   0 =  OK 
     *   612 - assertIsTrue  "1@gmail.com"                                      =   0 =  OK 
     *   613 - assertIsTrue  "1_example@something.gmail.com"                    =   0 =  OK 
     *   614 - assertIsTrue  "d._.___d@gmail.com"                               =   0 =  OK 
     *   615 - assertIsTrue  "d.oy.smith@gmail.com"                             =   0 =  OK 
     *   616 - assertIsTrue  "d_oy_smith@gmail.com"                             =   0 =  OK 
     *   617 - assertIsTrue  "doysmith@gmail.com"                               =   0 =  OK 
     *   618 - assertIsTrue  "D.Oy'Smith@gmail.com"                             =   0 =  OK 
     *   619 - assertIsTrue  "%20f3v34g34@gvvre.com"                            =   0 =  OK 
     *   620 - assertIsTrue  "piskvor@example.lighting"                         =   0 =  OK 
     *   621 - assertIsTrue  "--@ooo.ooo"                                       =   0 =  OK 
     *   622 - assertIsFalse "check@thiscom"                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   623 - assertIsFalse "check@this..com"                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   624 - assertIsFalse " check@this.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   625 - assertIsTrue  "check@this.com"                                   =   0 =  OK 
     *   626 - assertIsTrue  "Abc@example.com"                                  =   0 =  OK 
     *   627 - assertIsFalse "Abc@example.com."                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   628 - assertIsTrue  "Abc@10.42.0.1"                                    =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   629 - assertIsTrue  "user@localserver"                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   630 - assertIsTrue  "Abc.123@example.com"                              =   0 =  OK 
     *   631 - assertIsTrue  "user+mailbox/department=shipping@example.com"     =   0 =  OK 
     *   632 - assertIsTrue  "" "@example.org"                                  =   1 =  OK 
     *   633 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                          =   4 =  OK 
     *   634 - assertIsFalse "Abc.example.com"                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   635 - assertIsFalse "A@b@c@example.com"                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   636 - assertIsFalse "a"b(c)d,e:f;g<h>i[j\k]l@example.com"              =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   637 - assertIsFalse "just"not"right@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   638 - assertIsFalse "this is"not\allowed@example.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   639 - assertIsFalse "this\ still\"not\allowed@example.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   640 - assertIsFalse "john..doe@example.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   641 - assertIsFalse "john.doe@example..com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   642 - assertIsTrue  "email@example.com"                                =   0 =  OK 
     *   643 - assertIsTrue  "email@example.co.uk"                              =   0 =  OK 
     *   644 - assertIsTrue  "email@mail.gmail.com"                             =   0 =  OK 
     *   645 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com" =   0 =  OK 
     *   646 - assertIsFalse "email@example.co.uk."                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   647 - assertIsFalse "email@example"                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   648 - assertIsFalse " email@example.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   649 - assertIsFalse "email@example.com "                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   650 - assertIsFalse "email@example,com "                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   651 - assertIsFalse "invalid.email.com"                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   652 - assertIsFalse "invalid@email@domain.com"                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   653 - assertIsFalse "email@example..com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   654 - assertIsTrue  "yoursite@ourearth.com"                            =   0 =  OK 
     *   655 - assertIsTrue  "my.ownsite@ourearth.org"                          =   0 =  OK 
     *   656 - assertIsTrue  "mysite@you.me.net"                                =   0 =  OK 
     *   657 - assertIsTrue  "xxxx@gmail.com"                                   =   0 =  OK 
     *   658 - assertIsTrue  "xxxxxx@yahoo.com"                                 =   0 =  OK 
     *   659 - assertIsFalse "xxxx.ourearth.com"                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   660 - assertIsFalse "xxxx@.com.my"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   661 - assertIsFalse "@you.me.net"                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   662 - assertIsFalse "xxxx123@gmail.b"                                  =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   663 - assertIsFalse "xxxx@.org.org"                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   664 - assertIsFalse ".xxxx@mysite.org"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   665 - assertIsFalse "xxxxx()*@gmail.com"                               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   666 - assertIsFalse "xxxx..1234@yahoo.com"                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   667 - assertIsTrue  "alex@example.com"                                 =   0 =  OK 
     *   668 - assertIsTrue  "alireza@test.co.uk"                               =   0 =  OK 
     *   669 - assertIsTrue  "peter.example@yahoo.com.au"                       =   0 =  OK 
     *   670 - assertIsTrue  "alex@example.com"                                 =   0 =  OK 
     *   671 - assertIsTrue  "peter_123@news.com"                               =   0 =  OK 
     *   672 - assertIsTrue  "hello7___@ca.com.pt"                              =   0 =  OK 
     *   673 - assertIsTrue  "example@example.co"                               =   0 =  OK 
     *   674 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   675 - assertIsFalse "hallo2ww22@example....caaaao"                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   676 - assertIsTrue  "abcxyz123@qwert.com"                              =   0 =  OK 
     *   677 - assertIsTrue  "abc123xyz@asdf.co.in"                             =   0 =  OK 
     *   678 - assertIsTrue  "abc1_xyz1@gmail1.com"                             =   0 =  OK 
     *   679 - assertIsTrue  "abc.xyz@gmail.com.in"                             =   0 =  OK 
     *   680 - assertIsTrue  "pio_pio@factory.com"                              =   0 =  OK 
     *   681 - assertIsTrue  "~pio_pio@factory.com"                             =   0 =  OK 
     *   682 - assertIsTrue  "pio_~pio@factory.com"                             =   0 =  OK 
     *   683 - assertIsTrue  "pio_#pio@factory.com"                             =   0 =  OK 
     *   684 - assertIsFalse "pio_pio@#factory.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   685 - assertIsFalse "pio_pio@factory.c#om"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   686 - assertIsFalse "pio_pio@factory.c*om"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   687 - assertIsTrue  "pio^_pio@factory.com"                             =   0 =  OK 
     *   688 - assertIsTrue  ""Abc\@def"@example.com"                           =   1 =  OK 
     *   689 - assertIsTrue  ""Fred Bloggs"@example.com"                        =   1 =  OK 
     *   690 - assertIsTrue  ""Fred\ Bloggs"@example.com"                       =   1 =  OK 
     *   691 - assertIsTrue  "Fred\ Bloggs@example.com"                         =   0 =  OK 
     *   692 - assertIsTrue  ""Joe\Blow"@example.com"                           =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   693 - assertIsTrue  ""Joe\\Blow"@example.com"                          =   1 =  OK 
     *   694 - assertIsTrue  ""Abc@def"@example.com"                            =   1 =  OK 
     *   695 - assertIsTrue  "customer/department=shipping@example.com"         =   0 =  OK 
     *   696 - assertIsTrue  "\$A12345@example.com"                             =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   697 - assertIsTrue  "!def!xyz%abc@example.com"                         =   0 =  OK 
     *   698 - assertIsTrue  "_somename@example.com"                            =   0 =  OK 
     *   699 - assertIsTrue  "abc."defghi".xyz@example.com"                     =   1 =  OK 
     *   700 - assertIsTrue  ""abcdefghixyz"@example.com"                       =   1 =  OK 
     *   701 - assertIsFalse "abc"defghi"xyz@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   702 - assertIsFalse "abc\"def\"ghi@example.com"                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     * 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   703 - assertIsTrue  "Loïc.Accentué@voilà.fr"                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   704 - assertIsTrue  "Ärger.Öde@Übel.de"                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   705 - assertIsTrue  "Smørrebrød@danmark.dk"                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   706 - assertIsTrue  "ip.without.brackets@1.2.3.4"                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   707 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   708 - assertIsTrue  "(space after comment) john.smith@example.com"     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   709 - assertIsTrue  "email.address.without@topleveldomain"             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   710 - assertIsTrue  "EmailAddressWithout@PointSeperator"               =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   711 - assertIsTrue  ""use of komma, in double qoutes"@RFC2822.com"     =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     * 
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   712 - assertIsTrue  "valid.email.from.nr300@fillup.tofalse.com"        =   0 =  OK 
     *           ...
     *   713 - assertIsTrue  "valid.email.to.nr412@fillup.tofalse.com"          =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE   412   KORREKT  378 =   91.748 % | FALSCH ERKANNT   34
     *   ASSERT_IS_FALSE  412   KORREKT  411 =   99.757 % | FALSCH ERKANNT    1
     * 
     *   INSGESAMT        824   KORREKT  789 =   95.752 % | FALSCH ERKANNT   35
     * 
     */

    try
    {
      wlHeadline( "General Correct" );

      assertIsTrue( "A.B@C.DE" );
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
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
      assertIsTrue( "a.b.c.d@domain.com" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "a@domain.com" );
      assertIsTrue( "email@domain.co.de" );
      assertIsTrue( "email@domain.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "2@bde.cc" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "a2@bde.cc" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsTrue( "john.smith@example.com" );

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      wlHeadline( "AT-Character" );

      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "ABC.DEF.GHI.JKL" );
      assertIsFalse( "ABC.DEF@ GHI.JKL" );
      assertIsFalse( "ABC.DEF @GHI.JKL" );
      assertIsFalse( "ABC.DEF @ GHI.JKL" );
      assertIsFalse( "@" );
      assertIsFalse( "@.@.@." );
      assertIsFalse( "@.@.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@.@.@GHI.JKL" );
      assertIsFalse( "@@@GHI.JKL" );
      assertIsFalse( "@GHI.JKL" );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );

      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "@domain.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );

      wlHeadline( "Seperator" );

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

      assertIsFalse( "john..doe@example.com" );
      assertIsFalse( "john.doe@example..com" );
      assertIsTrue( "\"john..doe\"@example.com" );

      assertIsFalse( "..........@domain." );

      wlHeadline( "Characters" );

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

      assertIsTrue( "domain.part@with-hyphen.com" );
      assertIsTrue( "domain.part@with_underscore.com" );
      assertIsFalse( "domain.part@-starts.with.hyphen.com" );
      assertIsFalse( "domain.part@ends.with.hyphen.com-" );
      assertIsFalse( "domain.part@with&amp.com" );
      assertIsFalse( "domain.part@with*asterisk.com" );
      assertIsFalse( "domain.part@with$dollar.com" );
      assertIsFalse( "domain.part@with=equality.com" );
      assertIsFalse( "domain.part@with!exclamation.com" );
      assertIsFalse( "domain.part@with?question.com" );
      assertIsFalse( "domain.part@with`grave-accent.com" );
      assertIsFalse( "domain.part@with#hash.com" );
      assertIsFalse( "domain.part@with%percentage.com" );
      assertIsFalse( "domain.part@with|pipe.com" );
      assertIsFalse( "domain.part@with+plus.com" );
      assertIsFalse( "domain.part@with{leftbracket.com" );
      assertIsFalse( "domain.part@with}rightbracket.com" );
      assertIsFalse( "domain.part@with(leftbracket.com" );
      assertIsFalse( "domain.part@with)rightbracket.com" );
      assertIsFalse( "domain.part@with[leftbracket.com" );
      assertIsFalse( "domain.part@with]rightbracket.com" );
      assertIsFalse( "domain.part@with~tilde.com" );
      assertIsFalse( "domain.part@with^xor.com" );
      assertIsFalse( "domain.part@with:colon.com" );

      assertIsFalse( "DomainHyphen@-atstart" );
      assertIsFalse( "DomainHyphen@atend-.com" );
      assertIsFalse( "DomainHyphen@bb.-cc" );
      assertIsFalse( "DomainHyphen@bb.-cc-" );
      assertIsFalse( "DomainHyphen@bb.cc-" );
      assertIsFalse( "DomainHyphen@bb.c-c" ); // https://tools.ietf.org/id/draft-liman-tld-names-01.html
      assertIsFalse( "DomainNotAllowedCharacter@/atstart" );
      assertIsFalse( "DomainNotAllowedCharacter@a,start" );
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
      assertIsTrue( "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de" );
      assertIsTrue( "#!$%&'*+-/=?^_`{}|~@eksample.org" );
      assertIsFalse( "eksample@#!$%&'*+-/=?^_`{}|~.org" );
      assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" );
      assertIsFalse( "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org" );

      wlHeadline( "IP V4" );

      assertIsFalse( "\"\"@[]" );
      assertIsFalse( "\"\"@[1" );
      assertIsFalse( "ABC.DEF@[]" );
      assertIsTrue( "\"    \"@[1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[001.002.003.004]" );
      assertIsTrue( "\"ABC.DEF\"@[127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsFalse( "ABC.DE[F@1.2.3.4]" );
      assertIsFalse( "ABC.DEF@{1.2.3.4}" );
      assertIsFalse( "ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[][][][]" );
      assertIsFalse( "ABC.DEF@[....]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF[@1.2.3.4]" );
      assertIsFalse( "\"[1.2.3.4]\"@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF@MyDomain[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.00002.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.456]" );
      assertIsFalse( "ABC.DEF@[..]" );
      assertIsFalse( "ABC.DEF@[.2.3.4]" );
      assertIsFalse( "ABC.DEF@[]" );
      assertIsFalse( "ABC.DEF@[1]" );
      assertIsFalse( "ABC.DEF@[1.2]" );
      assertIsFalse( "ABC.DEF@[1.2.3]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5.6]" );
      assertIsFalse( "ABC.DEF@[MyDomain.de]" );
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
      assertIsFalse( "ABC.DEF@[IPv6::::::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6:7" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv4:1:2:3:4]" );
      assertIsFalse( "ABC.DEF@[I127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[D127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[iPv6:2001:db8::1]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3::5::7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:Z]" );
      assertIsFalse( "ABC.DEF@[IPv6:12:34]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC" );
      assertIsTrue( "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC.DEF@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])" );
      assertIsTrue( "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );

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

      wlHeadline( "Strings" );

      assertIsTrue( "\"ABC.DEF\"@GHI.DE" );
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
      assertIsFalse( "\"Ende.am.Eingabeende\"" );
      assertIsFalse( "0\"00.000\"@GHI.JKL" );

      assertIsTrue( "\"Joe Smith\" email@domain.com" );
      assertIsTrue( "\"Joe\\tSmith\" email@domain.com" );
      assertIsTrue( "\"Joe\"Smith\" email@domain.com" );

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
      assertIsTrue( "ABC.DEF@GHI.JKL ()" );
      assertIsTrue( "ABC.DEF@GHI.JKL()" );
      assertIsTrue( "ABC.DEF@()GHI.JKL" );
      assertIsTrue( "ABC.DEF()@GHI.JKL" );
      assertIsTrue( "()ABC.DEF@GHI.JKL" );
      assertIsFalse( "()()()ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL ()()" );
      assertIsFalse( "(ABC).DEF@GHI.JKL" );
      assertIsFalse( "ABC.(DEF)@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GHI).JKL" );
      assertIsFalse( "ABC.DEF@GHI.(JKL).MNO" );
      assertIsFalse( "ABC.DEF@GHI.JK(L.M)NO" );
      assertIsFalse( "AB(CD)EF@GHI.JKL" );
      assertIsFalse( "AB.(CD).EF@GHI.JKL" );
      assertIsFalse( "AB.\"(CD)\".EF@GHI.JKL" );
      assertIsFalse( "(ABCDEF)@GHI.JKL" );
      assertIsFalse( "(ABCDEF).@GHI.JKL" );
      assertIsFalse( "(AB\"C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\@C)DEF@GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI)JKL" );
      assertIsFalse( ")ABC.DEF@GHI.JKL" );
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

      wlHeadline( "Pointy Brackets" );

      assertIsTrue( "ABC DEF <ABC.DEF@GHI.JKL>" );
      assertIsTrue( "<ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL ABC DEF" );
      assertIsTrue( "\"ABC DEF \"<ABC.DEF@GHI.JKL>" );
      assertIsFalse( "\"ABC<DEF>\"@JKL.DE" );
      assertIsFalse( ">" );
      assertIsFalse( "\"ABC<DEF@GHI.COM>\"@JKL.DE" );
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

      assertIsTrue( "<ABC.DEF@GHI.JKL>" ); // correct ?

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

      wlHeadline( "Length" );

      String str_50 = "12345678901234567890123456789012345678901234567890";
      String str_63 = "A23456789012345678901234567890123456789012345678901234567890123";

      assertIsTrue( "A@B.CD" );
      assertIsFalse( "A@B.C" );
      assertIsFalse( "A@COM" );

      assertIsTrue( "ABC.DEF@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.J" );
      assertIsTrue( "ABC.DEF@GHI." + str_63 );
      assertIsFalse( "ABC.DEF@GHI." + str_63 + "A" );
      assertIsTrue( "A@B.CD" );

      assertIsTrue( "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" );
      assertIsTrue( "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" );
      assertIsFalse( "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );

      assertIsTrue( "domain.label.with.63.characters@" + str_63 + ".com" );
      assertIsFalse( "domain.label.with.64.characters@" + str_63 + "A.com" );
      assertIsTrue( "two.domain.labels.with.63.characters@" + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "domain.label.with.63.and.64.characters@" + str_63 + "." + str_63 + "A.com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + ".com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsTrue( "" + str_50 + ".1234567@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "" + str_50 + ".12345678@" + str_63 + "." + str_63 + "." + str_63 + ".com" );

      assertIsFalse( "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com" );
      assertIsFalse( "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" );
      assertIsFalse( "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" );
      assertIsFalse( "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" );

      assertIsTrue( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );

      assertIsTrue( "email@domain.topleveldomain" );

      wl( "" );
      wlHeadline( "Testclass EmailValidator4J" );
      wl( "" );

      /*
       * https://github.com/egulias/EmailValidator4J 
       */

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
      assertIsFalse( "username@example,com" );
      assertIsFalse( "usern,ame@example.com" );
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
      assertIsTrue( "example@localhost" );
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
      assertIsTrue( "\"user,name\"@example.com" );
      assertIsTrue( "\"user name\"@example.com" );
      assertIsTrue( "\"user@name\"@example.com" );
      assertIsFalse( "\"\\a\"@iana.org" );
      assertIsTrue( "\"test\\ test\"@iana.org" );
      assertIsFalse( "\"\"@iana.org" );
      assertIsFalse( "\"\"@[]" );/* kind of an edge case, valid for RFC 5322 but address literal is not for 5321 */
      assertIsTrue( String.format( "\"\\%s\"@iana.org", "\"" ) );

      wl( "" );
      wlHeadline( "unsorted from the WEB" );
      wl( "" );

      /*
       * Various examples. Scraped from the Internet-Forums.
       * 
       * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
       * https://stackoverflow.com/questions/25471114/how-to-validate-an-e-mail-address-in-swift?noredirect=1&lq=1
       * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
       * https://stackoverflow.com/questions/6850894/regex-split-email-address?noredirect=1&lq=1
       * https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript?page=3&tab=votes#tab-top
       * https://docs.microsoft.com/en-us/dotnet/api/system.net.mail.mailaddress.address?redirectedfrom=MSDN&view=netframework-4.8#System_Net_Mail_MailAddress_Address
       */

      assertIsFalse( "..@test.com" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( "ab@sd@dd" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( "a@b.-de.cc" );
      assertIsFalse( "a@bde-.cc" );
      assertIsFalse( "a@b._de.cc" );
      assertIsFalse( "a@bde_.cc" );
      assertIsFalse( "a@bde.cc." );
      assertIsFalse( "ab@b+de.cc" );
      assertIsFalse( "a..b@bde.cc" );
      assertIsFalse( "_@bde.cc," );
      assertIsFalse( "plainaddress" );
      assertIsFalse( "plain.address" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@domain..com" );
      assertIsFalse( "MailTo:casesensitve@domain.com" );
      assertIsFalse( "mailto:email@domain.com" );
      assertIsFalse( "email@domain" );

      assertIsTrue( "someone@somewhere.com" );
      assertIsTrue( "someone@somewhere.co.uk" );
      assertIsTrue( "someone+tag@somewhere.net" );
      assertIsTrue( "futureTLD@somewhere.fooo" );

      assertIsFalse( "myemailsample.com" );
      assertIsTrue( "myemail@sample" );
      assertIsTrue( "myemail@sample.com" );
      assertIsFalse( "myemail@@sample.com" );
      assertIsFalse( "myemail@sa@mple.com" );
      assertIsTrue( "\"myemail@sa\"@mple.com" );
      assertIsFalse( "a.\"b@c\".x.\"@\".d.e@f.g@" );
      assertIsTrue( "\"foo\\@bar\"@Something.com" );
      assertIsFalse( "Foobar Some@thing.com" );
      assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
      assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
      assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );
      assertIsTrue( "cog@wheel.com" );
      assertIsTrue( "\"cogwheel the orange\"@example.com" );
      assertIsFalse( "123@$.xyz" );

      assertIsTrue( "david.jones@proseware.com" );
      assertIsTrue( "d.j@server1.proseware.com" );
      assertIsTrue( "jones@ms1.proseware.com" );
      assertIsFalse( "j.@server1.proseware.com" );
      assertIsTrue( "j@proseware.com9" );
      assertIsTrue( "js#internal@proseware.com" );
      assertIsTrue( "j_9@[129.126.118.1]" );
      assertIsFalse( "j..s@proseware.com" );
      assertIsTrue( "js*@proseware.com" );
      assertIsFalse( "js@proseware..com" );
      assertIsTrue( "js@proseware.com9" );
      assertIsTrue( "j.s@server1.proseware.com" );
      assertIsTrue( "\"j\\\"s\"@proseware.com" );

      assertIsFalse( "dasddas-@.com" );
      assertIsTrue( "-asd@das.com" );
      assertIsFalse( "as3d@dac.coas-" );
      assertIsTrue( "dsq!a?@das.com" );
      assertIsTrue( "_dasd@sd.com" );
      assertIsFalse( "dad@sds" );
      assertIsTrue( "asd-@asd.com" );
      assertIsTrue( "dasd_-@jdas.com" );
      assertIsFalse( "asd@dasd@asd.cm" );
      assertIsFalse( "da23@das..com" );
      assertIsTrue( "_dasd_das_@9.com" );
      assertIsTrue( "d23d@da9.co9" );
      assertIsTrue( "dasd.dadas@dasd.com" );
      assertIsTrue( "dda_das@das-dasd.com" );
      assertIsTrue( "dasd-dasd@das.com.das" );

      assertIsFalse( "fdsa" );
      assertIsFalse( "fdsa@" );
      assertIsFalse( "fdsa@fdsa" );
      assertIsFalse( "fdsa@fdsa." );
      assertIsFalse( "@b.com" );
      assertIsFalse( "a@.com" );
      assertIsTrue( "a@bcom" );
      assertIsTrue( "a.b@com" );
      assertIsFalse( "a@b." );
      assertIsTrue( "ab@c.com" );
      assertIsTrue( "a@bc.com" );
      assertIsTrue( "a@b.com" );
      assertIsTrue( "a@b.c.com" );
      assertIsTrue( "a+b@c.com" );
      assertIsTrue( "a@123.45.67.89" );

      assertIsTrue( "%2@gmail.com" );
      assertIsTrue( "\"%2\"@gmail.com" );
      assertIsTrue( "\"a..b\"@gmail.com" );
      assertIsTrue( "\"a_b\"@gmail.com" );
      assertIsTrue( "_@gmail.com" );
      assertIsTrue( "1@gmail.com" );
      assertIsTrue( "1_example@something.gmail.com" );
      assertIsTrue( "d._.___d@gmail.com" );
      assertIsTrue( "d.oy.smith@gmail.com" );
      assertIsTrue( "d_oy_smith@gmail.com" );
      assertIsTrue( "doysmith@gmail.com" );
      assertIsTrue( "D.Oy'Smith@gmail.com" );

      assertIsTrue( "%20f3v34g34@gvvre.com" );
      assertIsTrue( "piskvor@example.lighting" );
      assertIsTrue( "--@ooo.ooo" );

      assertIsFalse( "check@thiscom" );
      assertIsFalse( "check@this..com" );
      assertIsFalse( " check@this.com" );
      assertIsTrue( "check@this.com" );

      assertIsTrue( "Abc@example.com" );
      assertIsFalse( "Abc@example.com." );
      assertIsTrue( "Abc@10.42.0.1" );
      assertIsTrue( "user@localserver" );
      assertIsTrue( "Abc.123@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "user@[IPv6:2001:DB8::1]" );

      assertIsFalse( "Abc.example.com" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsFalse( "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "just\"not\"right@example.com" );
      assertIsFalse( "this is\"not\\allowed@example.com" );
      assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
      assertIsFalse( "john..doe@example.com" );
      assertIsFalse( "john.doe@example..com" );

      assertIsTrue( "email@example.com" );
      assertIsTrue( "email@example.co.uk" );
      assertIsTrue( "email@mail.gmail.com" );
      assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );
      assertIsFalse( "email@example.co.uk." );
      assertIsFalse( "email@example" );
      assertIsFalse( " email@example.com" );
      assertIsFalse( "email@example.com " );
      assertIsFalse( "email@example,com " );
      assertIsFalse( "invalid.email.com" );
      assertIsFalse( "invalid@email@domain.com" );
      assertIsFalse( "email@example..com" );

      assertIsTrue( "yoursite@ourearth.com" );
      assertIsTrue( "my.ownsite@ourearth.org" );
      assertIsTrue( "mysite@you.me.net" );
      assertIsTrue( "xxxx@gmail.com" );
      assertIsTrue( "xxxxxx@yahoo.com" );

      assertIsFalse( "xxxx.ourearth.com" );
      assertIsFalse( "xxxx@.com.my" );
      assertIsFalse( "@you.me.net" );
      assertIsFalse( "xxxx123@gmail.b" );
      assertIsFalse( "xxxx@.org.org" );
      assertIsFalse( ".xxxx@mysite.org" );
      assertIsFalse( "xxxxx()*@gmail.com" );
      assertIsFalse( "xxxx..1234@yahoo.com" );

      assertIsTrue( "alex@example.com" );
      assertIsTrue( "alireza@test.co.uk" );
      assertIsTrue( "peter.example@yahoo.com.au" );
      assertIsTrue( "alex@example.com" );
      assertIsTrue( "peter_123@news.com" );
      assertIsTrue( "hello7___@ca.com.pt" );
      assertIsTrue( "example@example.co" );
      assertIsFalse( "hallo@example.coassjj#sswzazaaaa" );
      assertIsFalse( "hallo2ww22@example....caaaao" );

      assertIsTrue( "abcxyz123@qwert.com" );
      assertIsTrue( "abc123xyz@asdf.co.in" );
      assertIsTrue( "abc1_xyz1@gmail1.com" );
      assertIsTrue( "abc.xyz@gmail.com.in" );

      assertIsTrue( "pio_pio@factory.com" );
      assertIsTrue( "~pio_pio@factory.com" );
      assertIsTrue( "pio_~pio@factory.com" );
      assertIsTrue( "pio_#pio@factory.com" );
      assertIsFalse( "pio_pio@#factory.com" );
      assertIsFalse( "pio_pio@factory.c#om" );
      assertIsFalse( "pio_pio@factory.c*om" );
      assertIsTrue( "pio^_pio@factory.com" );
      assertIsTrue( "\"Abc\\@def\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
      assertIsTrue( "Fred\\ Bloggs@example.com" );
      assertIsTrue( "\"Joe\\Blow\"@example.com" );
      assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
      assertIsTrue( "\"Abc@def\"@example.com" );
      assertIsTrue( "customer/department=shipping@example.com" );
      assertIsTrue( "\\$A12345@example.com" );
      assertIsTrue( "!def!xyz%abc@example.com" );
      assertIsTrue( "_somename@example.com" );

      assertIsTrue( "abc.\"defghi\".xyz@example.com" );
      assertIsTrue( "\"abcdefghixyz\"@example.com" );

      assertIsFalse( "abc\"defghi\"xyz@example.com" );
      assertIsFalse( "abc\\\"def\\\"ghi@example.com" );

      wl( "" );
      wlHeadline( "unsupported" );
      wl( "" );

      assertIsTrue( "Loïc.Accentué@voilà.fr" );
      assertIsTrue( "Ärger.Öde@Übel.de" );
      assertIsTrue( "Smørrebrød@danmark.dk" );

      assertIsTrue( "ip.without.brackets@1.2.3.4" );
      assertIsTrue( "ip.without.brackets@1:2:3:4:5:6:7:8" );

      assertIsTrue( "(space after comment) john.smith@example.com" );

      assertIsTrue( "email.address.without@topleveldomain" );

      assertIsTrue( "EmailAddressWithout@PointSeperator" );

      /*
       * https://www.jochentopf.com/email/chars.html
       * RFC2822 allowes their use if they are inside double quotes
       */
      assertIsTrue( "\"use of komma, in double qoutes\"@RFC2822.com" );

      wl( "" );
      wlHeadline( "Fillup" );
      wl( "" );

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
        assertIsTrue( "false.email.from.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.totrue.[()]" );

        KNZ_LOG_AUSGABE = false;

        if ( COUNT_ASSERT_IS_FALSE + 1 == COUNT_ASSERT_IS_TRUE )
        {
          wl( "          ..." );

          KNZ_LOG_AUSGABE = true;

          assertIsTrue( "false.email.to.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.totrue.[()]" );
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

    double proz_true_korrekt_erkannt = ( 100.0 * T_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_TRUE;

    double proz_false_korrekt_erkannt = ( 100.0 * F_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_FALSE;

    double proz_korrekt_erkannt_insgesamt = ( 100.0 * ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    wl( "" );
    wlHeadline( "Statistik" );
    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( proz_true_korrekt_erkannt ) + m_number_format.format( proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_FALSE ) + "" );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( proz_false_korrekt_erkannt ) + m_number_format.format( proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE ) + "" );
    wl( "" );
    wl( "  INSGESAMT       " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( proz_korrekt_erkannt_insgesamt ) + m_number_format.format( proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE + T_RESULT_COUNT_EMAIL_IS_FALSE ) + "" );
    wl( "" );

    //assertIsFalse( "\"test\"" + String.valueOf( Character.toChars( 0 ) ) + "@iana.org" );

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

    if ( TEST_B_KNZ_AKTIV )
    {
      knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER #### " ) );
      }
    }
    else
    {
      return_code = FkEMail.checkEMailAdresse( pString );

      knz_is_valid_email_adress = return_code < 10;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
      }
    }

    COUNT_ASSERT_IS_TRUE++;

    if ( knz_is_valid_email_adress )
    {
      T_RESULT_COUNT_EMAIL_IS_TRUE++;
    }
    else
    {
      T_RESULT_COUNT_EMAIL_IS_FALSE++;
    }
  }

  private static void assertIsFalse( String pString )
  {
    boolean knz_is_valid_email_adress = false;

    boolean knz_soll_wert = false;

    int return_code = 0;

    if ( TEST_B_KNZ_AKTIV )
    {
      knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) );
      }
    }
    else
    {
      return_code = FkEMail.checkEMailAdresse( pString );

      knz_is_valid_email_adress = return_code < 10;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
      }
    }

    COUNT_ASSERT_IS_FALSE++;

    if ( knz_is_valid_email_adress )
    {
      F_RESULT_COUNT_EMAIL_IS_TRUE++;
    }
    else
    {
      F_RESULT_COUNT_EMAIL_IS_FALSE++;
    }
  }

  /**
   * <pre>
   * Erstellt die Datei und schreibt dort den "pInhalt" rein.
   * 
   * Ist kein "pInhalt" null wird eine leere Datei erstellt.
   * </pre>
   * 
   * @param pDateiName der Dateiname 
   * @param pInhalt der zu schreibende Inhalt
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
