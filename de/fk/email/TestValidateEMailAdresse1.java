package de.fk.email;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestValidateEMailAdresse1
{
  private static int LAUFENDE_ZAHL                 = 0;

  private static int COUNT_ASSERT_IS_TRUE          = 0;

  private static int COUNT_ASSERT_IS_FALSE         = 0;

  private static int T_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int T_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int F_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int F_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  public static void main( String[] args )
  {
    /*
     * NR      Fkt.          Input                                                Result
     * 
     * ---- General Correct ----------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  A.B@C.DE                                           =   0 =  OK 
     *     2 - assertIsTrue  A."B"@C.DE                                         =   1 =  OK 
     *     3 - assertIsTrue  A.B@[1.2.3.4]                                      =   2 =  OK 
     *     4 - assertIsTrue  A."B"@[1.2.3.4]                                    =   3 =  OK 
     *     5 - assertIsTrue  A.B@[IPv6:1:2:3:4:5:6:7:8]                         =   4 =  OK 
     *     6 - assertIsTrue  A."B"@[IPv6:1:2:3:4:5:6:7:8]                       =   5 =  OK 
     *     7 - assertIsTrue  (A)B@C.DE                                          =   6 =  OK 
     *     8 - assertIsTrue  A(B)@C.DE                                          =   6 =  OK 
     *     9 - assertIsTrue  (A)"B"@C.DE                                        =   7 =  OK 
     *    10 - assertIsTrue  "A"(B)@C.DE                                        =   7 =  OK 
     *    11 - assertIsTrue  (A)B@[1.2.3.4]                                     =   2 =  OK 
     *    12 - assertIsTrue  A(B)@[1.2.3.4]                                     =   2 =  OK 
     *    13 - assertIsTrue  (A)"B"@[1.2.3.4]                                   =   8 =  OK 
     *    14 - assertIsTrue  "A"(B)@[1.2.3.4]                                   =   8 =  OK 
     *    15 - assertIsTrue  (A)B@[IPv6:1:2:3:4:5:6:7:8]                        =   4 =  OK 
     *    16 - assertIsTrue  A(B)@[IPv6:1:2:3:4:5:6:7:8]                        =   4 =  OK 
     *    17 - assertIsTrue  (A)"B"@[IPv6:1:2:3:4:5:6:7:8]                      =   9 =  OK 
     *    18 - assertIsTrue  "A"(B)@[IPv6:1:2:3:4:5:6:7:8]                      =   9 =  OK 
     *    19 - assertIsTrue  firstname.lastname@domain.com                      =   0 =  OK 
     *    20 - assertIsTrue  firstname+lastname@domain.com                      =   0 =  OK 
     *    21 - assertIsTrue  firstname-lastname@domain.com                      =   0 =  OK 
     *    22 - assertIsTrue  first-name-last-name@d-a-n.com                     =   0 =  OK 
     *    23 - assertIsTrue  a.b.c.d@domain.com                                 =   0 =  OK 
     *    24 - assertIsTrue  1@domain.com                                       =   0 =  OK 
     *    25 - assertIsTrue  a@domain.com                                       =   0 =  OK 
     *    26 - assertIsTrue  email@domain.co.de                                 =   0 =  OK 
     *    27 - assertIsTrue  email@domain.com                                   =   0 =  OK 
     *    28 - assertIsTrue  email@subdomain.domain.com                         =   0 =  OK 
     *    29 - assertIsTrue  2@bde.cc                                           =   0 =  OK 
     *    30 - assertIsTrue  -@bde.cc                                           =   0 =  OK 
     *    31 - assertIsTrue  a2@bde.cc                                          =   0 =  OK 
     *    32 - assertIsTrue  a-b@bde.cc                                         =   0 =  OK 
     *    33 - assertIsTrue  ab@b-de.cc                                         =   0 =  OK 
     *    34 - assertIsTrue  a+b@bde.cc                                         =   0 =  OK 
     *    35 - assertIsTrue  f.f.f@bde.cc                                       =   0 =  OK 
     *    36 - assertIsTrue  ab_c@bde.cc                                        =   0 =  OK 
     *    37 - assertIsTrue  _-_@bde.cc                                         =   0 =  OK 
     *    38 - assertIsTrue  w.b.f@test.com                                     =   0 =  OK 
     *    39 - assertIsTrue  w.b.f@test.museum                                  =   0 =  OK 
     *    40 - assertIsTrue  a.a@test.com                                       =   0 =  OK 
     *    41 - assertIsTrue  ab@288.120.150.10.com                              =   0 =  OK 
     *    42 - assertIsTrue  ab@[120.254.254.120]                               =   2 =  OK 
     *    43 - assertIsTrue  1234567890@domain.com                              =   0 =  OK 
     *    44 - assertIsTrue  john.smith@example.com                             =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    45 - assertIsFalse null                                               =  10 =  OK    Laenge: Eingabe ist null
     *    46 - assertIsFalse                                                    =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    47 - assertIsFalse                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    48 - assertIsFalse ABCDEFGHIJKLMNOP                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    49 - assertIsFalse ABC.DEF.GHI.JKL                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    50 - assertIsFalse @GHI.JKL                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    51 - assertIsFalse ABC.DEF@                                           =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    52 - assertIsFalse ABC.DEF@@GHI.JKL                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    53 - assertIsFalse @%^%#$@#$@#.com                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    54 - assertIsFalse @domain.com                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    55 - assertIsFalse email.domain.com                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    56 - assertIsFalse email@domain@domain.com                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    57 - assertIsFalse ABCDEF@GHIJKL                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    58 - assertIsFalse ABC.DEF@GHIJKL                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    59 - assertIsFalse .ABC.DEF@GHI.JKL                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    60 - assertIsFalse ABC.DEF@GHI.JKL.                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    61 - assertIsFalse ABC..DEF@GHI.JKL                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    62 - assertIsFalse ABC.DEF@GHI..JKL                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    63 - assertIsFalse ABC.DEF@GHI.JKL..                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    64 - assertIsFalse ABC.DEF.@GHI.JKL                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    65 - assertIsFalse ABC.DEF@.GHI.JKL                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    66 - assertIsFalse ABC.DEF@.                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    67 - assertIsFalse john..doe@example.com                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    68 - assertIsFalse john.doe@example..com                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    69 - assertIsTrue  "john..doe"@example.com                            =   1 =  OK 
     *    70 - assertIsFalse ..........@domain.                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    71 - assertIsTrue  ABC1.DEF2@GHI3.JKL4                                =   0 =  OK 
     *    72 - assertIsTrue  ABC.DEF_@GHI.JKL                                   =   0 =  OK 
     *    73 - assertIsTrue  #ABC.DEF@GHI.JKL                                   =   0 =  OK 
     *    74 - assertIsTrue  ABC.DEF@GHI.JK2                                    =   0 =  OK 
     *    75 - assertIsTrue  ABC.DEF@2HI.JKL                                    =   0 =  OK 
     *    76 - assertIsFalse ABC.DEF@GHI.2KL                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    77 - assertIsFalse ABC.DEF@GHI.JK-                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    78 - assertIsFalse ABC.DEF@GHI.JK_                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    79 - assertIsFalse ABC.DEF@-HI.JKL                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    80 - assertIsFalse ABC.DEF@_HI.JKL                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    81 - assertIsFalse ABC DEF@GHI.DE                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    82 - assertIsFalse ABC.DEF@GHI DE                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    83 - assertIsFalse A . B & C . D                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    84 - assertIsFalse  A . B & C . D                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    85 - assertIsFalse (?).[!]@{&}.<:>                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    86 - assertIsTrue  &local&&name&with&$@amp.com                        =   0 =  OK 
     *    87 - assertIsTrue  *local**name*with*@asterisk.com                    =   0 =  OK 
     *    88 - assertIsTrue  $local$$name$with$@dollar.com                      =   0 =  OK 
     *    89 - assertIsTrue  =local==name=with=@equality.com                    =   0 =  OK 
     *    90 - assertIsTrue  !local!!name!with!@exclamation.com                 =   0 =  OK 
     *    91 - assertIsTrue  `local``name`with`@grave-accent.com                =   0 =  OK 
     *    92 - assertIsTrue  #local##name#with#@hash.com                        =   0 =  OK 
     *    93 - assertIsTrue  -local--name-with-@hypen.com                       =   0 =  OK 
     *    94 - assertIsTrue  {local{name{{with{@leftbracket.com                 =   0 =  OK 
     *    95 - assertIsTrue  %local%%name%with%@percentage.com                  =   0 =  OK 
     *    96 - assertIsTrue  |local||name|with|@pipe.com                        =   0 =  OK 
     *    97 - assertIsTrue  +local++name+with+@plus.com                        =   0 =  OK 
     *    98 - assertIsTrue  ?local??name?with?@question.com                    =   0 =  OK 
     *    99 - assertIsTrue  }local}name}}with}@rightbracket.com                =   0 =  OK 
     *   100 - assertIsTrue  ~local~~name~with~@tilde.com                       =   0 =  OK 
     *   101 - assertIsTrue  ^local^^name^with^@xor.com                         =   0 =  OK 
     *   102 - assertIsTrue  _local__name_with_@underscore.com                  =   0 =  OK 
     *   103 - assertIsFalse :local::name:with:@colon.com                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   104 - assertIsTrue  domain.part@with-hyphen.com                        =   0 =  OK 
     *   105 - assertIsTrue  domain.part@with_underscore.com                    =   0 =  OK 
     *   106 - assertIsFalse domain.part@-starts.with.hyphen.com                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   107 - assertIsFalse domain.part@ends.with.hyphen.com-                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   108 - assertIsFalse domain.part@with&amp.com                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   109 - assertIsFalse domain.part@with*asterisk.com                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   110 - assertIsFalse domain.part@with$dollar.com                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   111 - assertIsFalse domain.part@with=equality.com                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   112 - assertIsFalse domain.part@with!exclamation.com                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   113 - assertIsFalse domain.part@with?question.com                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   114 - assertIsFalse domain.part@with`grave-accent.com                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   115 - assertIsFalse domain.part@with#hash.com                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   116 - assertIsFalse domain.part@with%percentage.com                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   117 - assertIsFalse domain.part@with|pipe.com                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   118 - assertIsFalse domain.part@with+plus.com                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   119 - assertIsFalse domain.part@with{leftbracket.com                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   120 - assertIsFalse domain.part@with}rightbracket.com                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   121 - assertIsFalse domain.part@with(leftbracket.com                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   122 - assertIsFalse domain.part@with)rightbracket.com                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   123 - assertIsFalse domain.part@with[leftbracket.com                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   124 - assertIsFalse domain.part@with]rightbracket.com                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   125 - assertIsFalse domain.part@with~tilde.com                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   126 - assertIsFalse domain.part@with^xor.com                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   127 - assertIsFalse domain.part@with:colon.com                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   128 - assertIsFalse DomainHyphen@-atstart                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   129 - assertIsFalse DomainHyphen@atend-.com                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   130 - assertIsFalse DomainHyphen@bb.-cc                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   131 - assertIsFalse DomainHyphen@bb.-cc-                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   132 - assertIsFalse DomainHyphen@bb.cc-                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   133 - assertIsFalse DomainHyphen@bb.c-c                                =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   134 - assertIsFalse DomainNotAllowedCharacter@/atstart                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   135 - assertIsFalse DomainNotAllowedCharacter@a,start                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   136 - assertIsFalse DomainNotAllowedCharacter@atst\art.com             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   137 - assertIsFalse DomainNotAllowedCharacter@exa\mple                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   138 - assertIsFalse DomainNotAllowedCharacter@example'                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   139 - assertIsFalse DomainNotAllowedCharacter@100%.de'                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   140 - assertIsTrue  domain.starts.with.digit@2domain.com               =   0 =  OK 
     *   141 - assertIsTrue  domain.ends.with.digit@domain2.com                 =   0 =  OK 
     *   142 - assertIsFalse tld.starts.with.digit@domain.2com                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   143 - assertIsTrue  tld.ends.with.digit@domain.com2                    =   0 =  OK 
     *   144 - assertIsFalse email@=qowaiv.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   145 - assertIsFalse email@plus+.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   146 - assertIsFalse email@domain.com>                                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   147 - assertIsFalse email@mailto:domain.com                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   148 - assertIsFalse mailto:mailto:email@domain.com                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   149 - assertIsFalse email@-domain.com                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   150 - assertIsFalse email@domain-.com                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   151 - assertIsFalse email@domain.com-                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   152 - assertIsFalse email@{leftbracket.com                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   153 - assertIsFalse email@rightbracket}.com                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   154 - assertIsFalse email@pp|e.com                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   155 - assertIsTrue  email@domain.domain.domain.com.com                 =   0 =  OK 
     *   156 - assertIsTrue  email@domain.domain.domain.domain.com.com          =   0 =  OK 
     *   157 - assertIsTrue  email@domain.domain.domain.domain.domain.com.com   =   0 =  OK 
     *   158 - assertIsFalse unescaped white space@fake$com                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   159 - assertIsFalse "Joe Smith email@domain.com                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   160 - assertIsFalse "Joe Smith' email@domain.com                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   161 - assertIsFalse "Joe Smith"email@domain.com                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   162 - assertIsFalse Joe Smith &lt;email@domain.com&gt;                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   163 - assertIsTrue  {john'doe}@my.server                               =   0 =  OK 
     *   164 - assertIsTrue  email@domain-one.com                               =   0 =  OK 
     *   165 - assertIsTrue  _______@domain.com                                 =   0 =  OK 
     *   166 - assertIsTrue  ?????@domain.com                                   =   0 =  OK 
     *   167 - assertIsFalse local@?????.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   168 - assertIsTrue  "B3V3RLY H1LL$"@example.com                        =   1 =  OK 
     *   169 - assertIsTrue  "-- --- .. -."@sh.de                               =   1 =  OK 
     *   170 - assertIsTrue  {{-^-}{-=-}{-^-}}@GHI.JKL                          =   0 =  OK 
     *   171 - assertIsTrue  "\" + \"select * from user\" + \""@example.de      =   1 =  OK 
     *   172 - assertIsTrue  #!$%&'*+-/=?^_`{}|~@eksample.org                   =   0 =  OK 
     *   173 - assertIsFalse eksample@#!$%&'*+-/=?^_`{}|~.org                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   174 - assertIsFalse [A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   175 - assertIsFalse "()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a"@example.org =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   176 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =   2 =  OK 
     *   177 - assertIsTrue  ABC.DEF@[001.002.003.004]                          =   2 =  OK 
     *   178 - assertIsTrue  "ABC.DEF"@[127.0.0.1]                              =   3 =  OK 
     *   179 - assertIsFalse ABC.DEF[1.2.3.4]                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   180 - assertIsFalse [1.2.3.4]@[5.6.7.8]                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   181 - assertIsFalse ABC.DEF[@1.2.3.4]                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   182 - assertIsFalse "[1.2.3.4]"@[5.6.7.8]                              =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   183 - assertIsFalse ABC.DEF@MyDomain[1.2.3.4]                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   184 - assertIsFalse ABC.DEF@[1.00002.3.4]                              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   185 - assertIsFalse ABC.DEF@[1.2.3.456]                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   186 - assertIsFalse ABC.DEF@[..]                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   187 - assertIsFalse ABC.DEF@[.2.3.4]                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   188 - assertIsFalse ABC.DEF@[]                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   189 - assertIsFalse ABC.DEF@[1]                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   190 - assertIsFalse ABC.DEF@[1.2]                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   191 - assertIsFalse ABC.DEF@[1.2.3]                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   192 - assertIsFalse ABC.DEF@[1.2.3.4.5]                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   193 - assertIsFalse ABC.DEF@[1.2.3.4.5.6]                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   194 - assertIsFalse ABC.DEF@[MyDomain.de]                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   195 - assertIsFalse ABC.DEF@[1.2.3.]                                   =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   196 - assertIsFalse ABC.DEF@[1.2.3. ]                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   197 - assertIsFalse ABC.DEF@[1.2.3.4].de                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   198 - assertIsFalse ABC.DE@[1.2.3.4][5.6.7.8]                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   199 - assertIsFalse ABC.DEF@[1.2.3.4                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   200 - assertIsFalse ABC.DEF@1.2.3.4]                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   201 - assertIsFalse ABC.DEF@[1.2.3.Z]                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   202 - assertIsFalse ABC.DEF@[12.34]                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   203 - assertIsFalse ABC.DEF@[1.2.3.4]ABC                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   204 - assertIsFalse ABC.DEF@[1234.5.6.7]                               =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   205 - assertIsFalse ABC.DEF@[1.2...3.4]                                =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   206 - assertIsTrue  email@[123.123.123.123]                            =   2 =  OK 
     *   207 - assertIsFalse email@111.222.333                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   208 - assertIsFalse email@111.222.333.256                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   209 - assertIsFalse email@[123.123.123.123                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   210 - assertIsFalse email@[123.123.123].123                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   211 - assertIsFalse email@123.123.123.123]                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   212 - assertIsFalse email@123.123.[123.123]                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   213 - assertIsFalse ab@988.120.150.10                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   214 - assertIsFalse ab@120.256.256.120                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   215 - assertIsFalse ab@120.25.1111.120                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   216 - assertIsFalse ab@[188.120.150.10                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   217 - assertIsFalse ab@188.120.150.10]                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   218 - assertIsFalse ab@[188.120.150.10].com                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   219 - assertIsTrue  ab@188.120.150.10                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   220 - assertIsTrue  ab@1.0.0.10                                        =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   221 - assertIsTrue  ab@120.25.254.120                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   222 - assertIsTrue  ab@01.120.150.1                                    =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   223 - assertIsTrue  ab@88.120.150.021                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   224 - assertIsTrue  ab@88.120.150.01                                   =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   225 - assertIsTrue  email@123.123.123.123                              =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   226 - assertIsTrue  ABC.DEF@[IPv6:2001:db8::1]                         =   4 =  OK 
     *   227 - assertIsFalse ABC.DEF@[IP                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   228 - assertIsFalse ABC.DEF@[IPv6]                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   229 - assertIsFalse ABC.DEF@[IPv6:]                                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   230 - assertIsFalse ABC.DEF@[IPv6:1]                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   231 - assertIsFalse ABC.DEF@[IPv6:1:2]                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   232 - assertIsTrue  ABC.DEF@[IPv6:1:2:3]                               =   4 =  OK 
     *   233 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4]                             =   4 =  OK 
     *   234 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:]                          =  44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   235 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6]                         =   4 =  OK 
     *   236 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6:7]                       =   4 =  OK 
     *   237 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]                     =   4 =  OK 
     *   238 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]                   =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   239 - assertIsFalse ABC.DEF@[IPv4:1:2:3:4]                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   240 - assertIsFalse ABC.DEF@[I127.0.0.1]                               =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   241 - assertIsFalse ABC.DEF@[D127.0.0.1]                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   242 - assertIsFalse ABC.DEF@[iPv6:2001:db8::1]                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   243 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8]                      =   4 =  OK 
     *   244 - assertIsFalse ABC.DEF@[IPv6:1:2:3::5::7:8]                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   245 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:Z]                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   246 - assertIsFalse ABC.DEF@[IPv6:12:34]                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   247 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6]ABC                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   248 - assertIsTrue  ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]                     =   4 =  OK 
     *   249 - assertIsFalse ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   250 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   251 - assertIsFalse ABC.DEF@[IPv6:12345:6:7:8:9]                       =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   252 - assertIsFalse ABC.DEF@[IPv6:1:2:3:::6:7:8]                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   253 - assertIsFalse ABC.DEF@[IPv6:1:2:3]:4:5:6:7]                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   254 - assertIsFalse ABC.DEF@[IPv6:1:2](:3:4:5:6:7])                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   255 - assertIsFalse ABC.DEF@[IPv6:1:2:3](:4:5:6:7])                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   256 - assertIsTrue  ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]      =   4 =  OK 
     *   257 - assertIsTrue  ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334] =   4 =  OK 
     *   258 - assertIsFalse ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334] =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   259 - assertIsFalse ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334] =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   260 - assertIsTrue  ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334] =   4 =  OK 
     *   261 - assertIsTrue  ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   262 - assertIsTrue  ABC.DEF@[IPv6::FFFF:127.0.0.1]                     =   4 =  OK 
     *   263 - assertIsTrue  ABC.DEF@[IPv6::ffff:127.0.0.1]                     =   4 =  OK 
     *   264 - assertIsTrue  ABC.DEF@[::FFFF:127.0.0.1]                         =   4 =  OK 
     *   265 - assertIsTrue  ABC.DEF@[::ffff:127.0.0.1]                         =   4 =  OK 
     *   266 - assertIsFalse ABC.DEF@[IPv6::ffff:.127.0.1]                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   267 - assertIsFalse ABC.DEF@[IPv6::fff:127.0.0.1]                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   268 - assertIsFalse ABC.DEF@[IPv6::1234:127.0.0.1]                     =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   269 - assertIsFalse ABC.DEF@[IPv6:127.0.0.1]                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   270 - assertIsFalse ABC.DEF@[IPv6:::127.0.0.1]                         =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *   271 - assertIsTrue  "ABC.DEF"@GHI.DE                                   =   1 =  OK 
     *   272 - assertIsTrue  "ABC DEF"@GHI.DE                                   =   1 =  OK 
     *   273 - assertIsTrue  "ABC@DEF"@GHI.DE                                   =   1 =  OK 
     *   274 - assertIsTrue  "ABC\"DEF"@GHI.DE                                  =   1 =  OK 
     *   275 - assertIsTrue  "ABC\@DEF"@GHI.DE                                  =   1 =  OK 
     *   276 - assertIsTrue  "ABC\'DEF"@GHI.DE                                  =   1 =  OK 
     *   277 - assertIsTrue  "ABC\\DEF"@GHI.DE                                  =   1 =  OK 
     *   278 - assertIsFalse "ABC DEF@G"HI.DE                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   279 - assertIsFalse ""@GHI.DE                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   280 - assertIsFalse "ABC.DEF@G"HI.DE                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   281 - assertIsFalse A@G"HI.DE                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   282 - assertIsFalse "@GHI.DE                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   283 - assertIsFalse ABC.DEF."                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   284 - assertIsFalse ABC.DEF@""                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   285 - assertIsFalse ABC.DEF@G"HI.DE                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   286 - assertIsFalse ABC.DEF@GHI.DE"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   287 - assertIsFalse ABC.DEF@"GHI.DE                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   288 - assertIsFalse "Escape.Sequenz.Ende"                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   289 - assertIsFalse ABC.DEF"GHI.DE                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   290 - assertIsFalse ABC.DEF"@GHI.DE                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   291 - assertIsFalse ABC.DE"F@GHI.DE                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   292 - assertIsFalse "ABC.DEF@GHI.DE                                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   293 - assertIsFalse "ABC.DEF@GHI.DE"                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   294 - assertIsTrue  ".ABC.DEF"@GHI.DE                                  =   1 =  OK 
     *   295 - assertIsTrue  "ABC.DEF."@GHI.DE                                  =   1 =  OK 
     *   296 - assertIsTrue  "ABC".DEF."GHI"@JKL.de                             =   1 =  OK 
     *   297 - assertIsFalse A"BC".DEF."GHI"@JKL.de                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   298 - assertIsFalse "ABC".DEF.G"HI"@JKL.de                             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   299 - assertIsFalse "AB"C.DEF."GHI"@JKL.de                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   300 - assertIsFalse "ABC".DEF."GHI"J@KL.de                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   301 - assertIsFalse "AB"C.D"EF"@GHI.DE                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   302 - assertIsFalse "Ende.am.Eingabeende"                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   303 - assertIsFalse 0"00.000"@GHI.JKL                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   304 - assertIsTrue  "Joe Smith" email@domain.com                       =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   305 - assertIsTrue  "Joe\tSmith" email@domain.com                      =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   306 - assertIsTrue  "Joe"Smith" email@domain.com                       =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *   307 - assertIsTrue  (ABC)DEF@GHI.JKL                                   =   6 =  OK 
     *   308 - assertIsTrue  (ABC) DEF@GHI.JKL                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   309 - assertIsTrue  ABC(DEF)@GHI.JKL                                   =   6 =  OK 
     *   310 - assertIsTrue  ABC.DEF@GHI.JKL(MNO)                               =   6 =  OK 
     *   311 - assertIsTrue  ABC.DEF@GHI.JKL      (MNO)                         =   6 =  OK 
     *   312 - assertIsFalse ABC.DEF@             (MNO)                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   313 - assertIsFalse ABC.DEF@   .         (MNO)                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   314 - assertIsFalse ABC.DEF              (MNO)                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   315 - assertIsFalse ABC.DEF@GHI.         (MNO)                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   316 - assertIsFalse ABC.DEF@GHI.JKL       MNO                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   317 - assertIsFalse ABC.DEF@GHI.JKL                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   318 - assertIsFalse ABC.DEF@GHI.JKL       .                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   319 - assertIsTrue  ABC.DEF@GHI.JKL ()                                 =   6 =  OK 
     *   320 - assertIsTrue  ABC.DEF@GHI.JKL()                                  =   6 =  OK 
     *   321 - assertIsTrue  ABC.DEF@()GHI.JKL                                  =   6 =  OK 
     *   322 - assertIsTrue  ABC.DEF()@GHI.JKL                                  =   6 =  OK 
     *   323 - assertIsTrue  ()ABC.DEF@GHI.JKL                                  =   6 =  OK 
     *   324 - assertIsFalse (ABC).DEF@GHI.JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   325 - assertIsFalse ABC.(DEF)@GHI.JKL                                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   326 - assertIsFalse ABC.DEF@(GHI).JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   327 - assertIsFalse ABC.DEF@GHI.(JKL).MNO                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   328 - assertIsFalse ABC.DEF@GHI.JK(L.M)NO                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   329 - assertIsFalse AB(CD)EF@GHI.JKL                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   330 - assertIsFalse AB.(CD).EF@GHI.JKL                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   331 - assertIsFalse AB."(CD)".EF@GHI.JKL                               =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   332 - assertIsFalse (ABCDEF)@GHI.JKL                                   =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   333 - assertIsFalse (ABCDEF).@GHI.JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   334 - assertIsFalse (AB"C)DEF@GHI.JKL                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   335 - assertIsFalse (AB\C)DEF@GHI.JKL                                  =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   336 - assertIsFalse (AB\@C)DEF@GHI.JKL                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   337 - assertIsFalse ABC(DEF@GHI.JKL                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   338 - assertIsFalse ABC.DEF@GHI)JKL                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   339 - assertIsFalse )ABC.DEF@GHI.JKL                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   340 - assertIsFalse ABC(DEF@GHI).JKL                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   341 - assertIsFalse ABC(DEF.GHI).JKL                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   342 - assertIsFalse (ABC.DEF@GHI.JKL)                                  =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   343 - assertIsFalse (A(B(C)DEF@GHI.JKL                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   344 - assertIsFalse (A)B)C)DEF@GHI.JKL                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   345 - assertIsFalse (A)BCDE(F)@GHI.JKL                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   346 - assertIsFalse ABC.DEF@(GH)I.JK(LM)                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   347 - assertIsFalse ABC.DEF@(GH(I.JK)L)M                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   348 - assertIsTrue  ABC.DEF@(comment)[1.2.3.4]                         =   2 =  OK 
     *   349 - assertIsFalse ABC.DEF@(comment) [1.2.3.4]                        = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *   350 - assertIsTrue  ABC.DEF@[1.2.3.4](comment)                         =   2 =  OK 
     *   351 - assertIsTrue  ABC.DEF@[1.2.3.4]    (comment)                     =   2 =  OK 
     *   352 - assertIsFalse ABC.DEF@[1.2.3(comment).4]                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   353 - assertIsTrue  ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]             =   4 =  OK 
     *   354 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)             =   4 =  OK 
     *   355 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)         =   4 =  OK 
     *   356 - assertIsTrue  (comment)john.smith@example.com                    =   6 =  OK 
     *   357 - assertIsTrue  john.smith(comment)@example.com                    =   6 =  OK 
     *   358 - assertIsTrue  john.smith@(comment)example.com                    =   6 =  OK 
     *   359 - assertIsTrue  john.smith@example.com(comment)                    =   6 =  OK 
     *   360 - assertIsFalse john.smith@exampl(comment)e.com                    = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   361 - assertIsFalse john.s(comment)mith@example.com                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   362 - assertIsFalse john.smith(comment)@(comment)example.com           =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   363 - assertIsFalse john.smith(com@ment)example.com                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   364 - assertIsFalse email( (nested) )@plus.com                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   365 - assertIsFalse email)mirror(@plus.com                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   366 - assertIsFalse email@plus.com (not closed comment                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   367 - assertIsFalse email(with @ in comment)plus.com                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   368 - assertIsTrue  email@domain.com (joe Smith)                       =   6 =  OK 
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *   369 - assertIsTrue  ABC DEF <ABC.DEF@GHI.JKL>                          =   0 =  OK 
     *   370 - assertIsTrue  <ABC.DEF@GHI.JKL> ABC DEF                          =   0 =  OK 
     *   371 - assertIsFalse ABC DEF ABC.DEF@GHI.JKL>                           =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   372 - assertIsFalse <ABC.DEF@GHI.JKL ABC DEF                           =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   373 - assertIsTrue  "ABC DEF "<ABC.DEF@GHI.JKL>                        =   0 =  OK 
     *   374 - assertIsFalse "ABC<DEF>"@JKL.DE                                  =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   375 - assertIsFalse >                                                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   376 - assertIsFalse "ABC<DEF@GHI.COM>"@JKL.DE                          =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   377 - assertIsFalse ABC DEF <ABC.<DEF@GHI.JKL>                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   378 - assertIsFalse <ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   379 - assertIsFalse ABC DEF <ABC.DEF@GHI.JKL                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   380 - assertIsFalse ABC.DEF@GHI.JKL> ABC DEF                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   381 - assertIsFalse ABC DEF >ABC.DEF@GHI.JKL<                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsFalse >ABC.DEF@GHI.JKL< ABC DEF                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   383 - assertIsFalse ABC DEF <A@A>                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   384 - assertIsFalse <A@A> ABC DEF                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   385 - assertIsFalse ABC DEF <>                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   386 - assertIsFalse <> ABC DEF                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   387 - assertIsTrue  <ABC.DEF@GHI.JKL>                                  =   0 =  OK 
     *   388 - assertIsTrue  Joe Smith <email@domain.com>                       =   0 =  OK 
     *   389 - assertIsFalse Joe Smith <mailto:email@domain.com>                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   390 - assertIsFalse Joe Smith <mailto:email(with comment)@domain.com>  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   391 - assertIsTrue  Non EMail part <(comment)Local."Part"@[IPv6::ffff:127.0.0.1]> =   9 =  OK 
     *   392 - assertIsTrue  Non EMail part <Local."Part"(comment)@[IPv6::ffff:127.0.0.1]> =   9 =  OK 
     *   393 - assertIsTrue  <(comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part =   9 =  OK 
     *   394 - assertIsTrue  <Local."Part"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part  =   9 =  OK 
     *   395 - assertIsFalse Non EMail part < (comment)Local."Part"@[IPv6::ffff:127.0.0.1]> =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   396 - assertIsFalse Non EMail part <Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]> =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   397 - assertIsFalse < (comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   398 - assertIsFalse <Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   399 - assertIsFalse Test |<gaaf <email@domain.com>                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   400 - assertIsFalse Display Name <email@plus.com> (Comment after name with display) =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   401 - assertIsFalse "With extra < within quotes" Display Name<email@domain.com> =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *   402 - assertIsTrue  A@B.CD                                             =   0 =  OK 
     *   403 - assertIsFalse A@B.C                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   404 - assertIsFalse A@COM                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   405 - assertIsTrue  ABC.DEF@GHI.JKL                                    =   0 =  OK 
     *   406 - assertIsTrue  ABC.DEF@GHI.J                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   407 - assertIsTrue  ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123 =   0 =  OK 
     *   408 - assertIsFalse ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *   409 - assertIsTrue  A@B.CD                                             =   0 =  OK 
     *   410 - assertIsTrue  ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com =   0 =  OK 
     *   411 - assertIsTrue  abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de =   0 =  OK 
     *   412 - assertIsTrue  ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL =   0 =  OK 
     *   413 - assertIsFalse ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   414 - assertIsFalse 1234567890123456789012345678901234567890123456789012345678901234+x@example.com =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   415 - assertIsTrue  domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   416 - assertIsFalse domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   417 - assertIsTrue  two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   418 - assertIsFalse domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   419 - assertIsTrue  63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   420 - assertIsTrue  63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   421 - assertIsTrue  12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   422 - assertIsFalse 12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   423 - assertIsFalse "very.(z),:;<>[]\".VERY.\"very@\\ \"very\".unusual"@strange.example.com =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   424 - assertIsFalse too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   425 - assertIsFalse Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   426 - assertIsFalse 3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   427 - assertIsTrue  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   428 - assertIsFalse ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   429 - assertIsTrue  email@domain.topleveldomain                        =   0 =  OK 
     * 
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   430 - assertIsFalse ..@test.com                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   431 - assertIsFalse .a@test.com                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   432 - assertIsFalse ab@sd@dd                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   433 - assertIsFalse .@s.dd                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   434 - assertIsFalse a@b.-de.cc                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   435 - assertIsFalse a@bde-.cc                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   436 - assertIsFalse a@b._de.cc                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   437 - assertIsFalse a@bde_.cc                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   438 - assertIsFalse a@bde.cc.                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   439 - assertIsFalse ab@b+de.cc                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   440 - assertIsFalse a..b@bde.cc                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   441 - assertIsFalse _@bde.cc,                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   442 - assertIsFalse plainaddress                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   443 - assertIsFalse plain.address                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   444 - assertIsFalse .email@domain.com                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   445 - assertIsFalse email.@domain.com                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   446 - assertIsFalse email..email@domain.com                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   447 - assertIsFalse email@.domain.com                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   448 - assertIsFalse email@domain.com.                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   449 - assertIsFalse email@domain..com                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   450 - assertIsFalse MailTo:casesensitve@domain.com                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   451 - assertIsFalse mailto:email@domain.com                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   452 - assertIsFalse email@domain                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   453 - assertIsTrue  someone@somewhere.com                              =   0 =  OK 
     *   454 - assertIsTrue  someone@somewhere.co.uk                            =   0 =  OK 
     *   455 - assertIsTrue  someone+tag@somewhere.net                          =   0 =  OK 
     *   456 - assertIsTrue  futureTLD@somewhere.fooo                           =   0 =  OK 
     *   457 - assertIsFalse fdsa                                               =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   458 - assertIsFalse fdsa@                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   459 - assertIsFalse fdsa@fdsa                                          =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   460 - assertIsFalse fdsa@fdsa.                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   461 - assertIsFalse Foobar Some@thing.com                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   462 - assertIsTrue  david.jones@proseware.com                          =   0 =  OK 
     *   463 - assertIsTrue  d.j@server1.proseware.com                          =   0 =  OK 
     *   464 - assertIsTrue  jones@ms1.proseware.com                            =   0 =  OK 
     *   465 - assertIsFalse j.@server1.proseware.com                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   466 - assertIsTrue  j@proseware.com9                                   =   0 =  OK 
     *   467 - assertIsTrue  js#internal@proseware.com                          =   0 =  OK 
     *   468 - assertIsTrue  j_9@[129.126.118.1]                                =   2 =  OK 
     *   469 - assertIsFalse j..s@proseware.com                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   470 - assertIsTrue  js*@proseware.com                                  =   0 =  OK 
     *   471 - assertIsFalse js@proseware..com                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   472 - assertIsTrue  js@proseware.com9                                  =   0 =  OK 
     *   473 - assertIsTrue  j.s@server1.proseware.com                          =   0 =  OK 
     *   474 - assertIsTrue  "j\"s"@proseware.com                               =   1 =  OK 
     *   475 - assertIsTrue  cog@wheel.com                                      =   0 =  OK 
     *   476 - assertIsTrue  "cogwheel the orange"@example.com                  =   1 =  OK 
     *   477 - assertIsFalse 123@$.xyz                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   478 - assertIsFalse dasddas-@.com                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   479 - assertIsTrue  -asd@das.com                                       =   0 =  OK 
     *   480 - assertIsFalse as3d@dac.coas-                                     =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   481 - assertIsTrue  dsq!a?@das.com                                     =   0 =  OK 
     *   482 - assertIsTrue  _dasd@sd.com                                       =   0 =  OK 
     *   483 - assertIsFalse dad@sds                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   484 - assertIsTrue  asd-@asd.com                                       =   0 =  OK 
     *   485 - assertIsTrue  dasd_-@jdas.com                                    =   0 =  OK 
     *   486 - assertIsFalse asd@dasd@asd.cm                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   487 - assertIsFalse da23@das..com                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   488 - assertIsTrue  _dasd_das_@9.com                                   =   0 =  OK 
     *   489 - assertIsTrue  d23d@da9.co9                                       =   0 =  OK 
     *   490 - assertIsTrue  dasd.dadas@dasd.com                                =   0 =  OK 
     *   491 - assertIsTrue  dda_das@das-dasd.com                               =   0 =  OK 
     *   492 - assertIsTrue  dasd-dasd@das.com.das                              =   0 =  OK 
     *   493 - assertIsFalse @b.com                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   494 - assertIsFalse a@.com                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   495 - assertIsTrue  a@bcom                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   496 - assertIsTrue  a.b@com                                            =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   497 - assertIsFalse a@b.                                               =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   498 - assertIsTrue  ab@c.com                                           =   0 =  OK 
     *   499 - assertIsTrue  a@bc.com                                           =   0 =  OK 
     *   500 - assertIsTrue  a@b.com                                            =   0 =  OK 
     *   501 - assertIsTrue  a@b.c.com                                          =   0 =  OK 
     *   502 - assertIsTrue  a+b@c.com                                          =   0 =  OK 
     *   503 - assertIsTrue  a@123.45.67.89                                     =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   504 - assertIsTrue  %2@gmail.com                                       =   0 =  OK 
     *   505 - assertIsTrue  "%2"@gmail.com                                     =   1 =  OK 
     *   506 - assertIsTrue  "a..b"@gmail.com                                   =   1 =  OK 
     *   507 - assertIsTrue  "a_b"@gmail.com                                    =   1 =  OK 
     *   508 - assertIsTrue  _@gmail.com                                        =   0 =  OK 
     *   509 - assertIsTrue  1@gmail.com                                        =   0 =  OK 
     *   510 - assertIsTrue  1_example@something.gmail.com                      =   0 =  OK 
     *   511 - assertIsTrue  d._.___d@gmail.com                                 =   0 =  OK 
     *   512 - assertIsTrue  d.oy.smith@gmail.com                               =   0 =  OK 
     *   513 - assertIsTrue  d_oy_smith@gmail.com                               =   0 =  OK 
     *   514 - assertIsTrue  doysmith@gmail.com                                 =   0 =  OK 
     *   515 - assertIsTrue  D.Oy'Smith@gmail.com                               =   0 =  OK 
     *   516 - assertIsTrue  %20f3v34g34@gvvre.com                              =   0 =  OK 
     *   517 - assertIsTrue  piskvor@example.lighting                           =   0 =  OK 
     *   518 - assertIsTrue  --@ooo.ooo                                         =   0 =  OK 
     *   519 - assertIsFalse foo@bar@machine.subdomain.example.museum           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   520 - assertIsTrue  foo\@bar@machine.subdomain.example.museum          =   0 =  OK 
     *   521 - assertIsFalse foo.bar@machine.sub\@domain.example.museum         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   522 - assertIsFalse check@thiscom                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   523 - assertIsFalse check@this..com                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   524 - assertIsFalse  check@this.com                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   525 - assertIsTrue  check@this.com                                     =   0 =  OK 
     *   526 - assertIsTrue  Abc@example.com                                    =   0 =  OK 
     *   527 - assertIsTrue  Abc@example.com.                                   =  36 =  #### FEHLER ####    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   528 - assertIsTrue  Abc@10.42.0.1                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   529 - assertIsTrue  user@localserver                                   =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   530 - assertIsTrue  Abc.123@example.com                                =   0 =  OK 
     *   531 - assertIsTrue  user+mailbox/department=shipping@example.com       =   0 =  OK 
     *   532 - assertIsTrue  Loïc.Accentué@voilà.fr                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   533 - assertIsTrue  " "@example.org                                    =   1 =  OK 
     *   534 - assertIsTrue  user@[IPv6:2001:DB8::1]                            =   4 =  OK 
     *   535 - assertIsFalse Abc.example.com                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   536 - assertIsFalse A@b@c@example.com                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   537 - assertIsFalse a"b(c)d,e:f;g<h>i[j\k]l@example.com                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   538 - assertIsFalse just"not"right@example.com                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   539 - assertIsFalse this is"not\allowed@example.com                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   540 - assertIsFalse this\ still\"not\allowed@example.com               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   541 - assertIsFalse john..doe@example.com                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   542 - assertIsFalse john.doe@example..com                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   543 - assertIsTrue  email@example.com                                  =   0 =  OK 
     *   544 - assertIsTrue  email@example.co.uk                                =   0 =  OK 
     *   545 - assertIsTrue  email@mail.gmail.com                               =   0 =  OK 
     *   546 - assertIsTrue  unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com =   0 =  OK 
     *   547 - assertIsFalse email@example.co.uk.                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   548 - assertIsFalse email@example                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   549 - assertIsFalse  email@example.com                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   550 - assertIsFalse email@example.com                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   551 - assertIsFalse email@example,com                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   552 - assertIsFalse invalid.email.com                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   553 - assertIsFalse invalid@email@domain.com                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   554 - assertIsFalse email@example..com                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   555 - assertIsTrue  yoursite@ourearth.com                              =   0 =  OK 
     *   556 - assertIsTrue  my.ownsite@ourearth.org                            =   0 =  OK 
     *   557 - assertIsTrue  mysite@you.me.net                                  =   0 =  OK 
     *   558 - assertIsTrue  xxxx@gmail.com                                     =   0 =  OK 
     *   559 - assertIsTrue  xxxxxx@yahoo.com                                   =   0 =  OK 
     *   560 - assertIsFalse xxxx.ourearth.com                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   561 - assertIsFalse xxxx@.com.my                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   562 - assertIsFalse @you.me.net                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   563 - assertIsFalse xxxx123@gmail.b                                    =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   564 - assertIsFalse xxxx@.org.org                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   565 - assertIsFalse .xxxx@mysite.org                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   566 - assertIsFalse xxxxx()*@gmail.com                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   567 - assertIsFalse xxxx..1234@yahoo.com                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   568 - assertIsTrue  alex@example.com                                   =   0 =  OK 
     *   569 - assertIsTrue  alireza@test.co.uk                                 =   0 =  OK 
     *   570 - assertIsTrue  peter.example@yahoo.com.au                         =   0 =  OK 
     *   571 - assertIsTrue  alex@example.com                                   =   0 =  OK 
     *   572 - assertIsTrue  peter_123@news.com                                 =   0 =  OK 
     *   573 - assertIsTrue  hello7___@ca.com.pt                                =   0 =  OK 
     *   574 - assertIsTrue  example@example.co                                 =   0 =  OK 
     *   575 - assertIsFalse hallo@example.coassjj#sswzazaaaa                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse hallo2ww22@example....caaaao                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   577 - assertIsTrue  abcxyz123@qwert.com                                =   0 =  OK 
     *   578 - assertIsTrue  abc123xyz@asdf.co.in                               =   0 =  OK 
     *   579 - assertIsTrue  abc1_xyz1@gmail1.com                               =   0 =  OK 
     *   580 - assertIsTrue  abc.xyz@gmail.com.in                               =   0 =  OK 
     *   581 - assertIsTrue  pio_pio@factory.com                                =   0 =  OK 
     *   582 - assertIsTrue  ~pio_pio@factory.com                               =   0 =  OK 
     *   583 - assertIsTrue  pio_~pio@factory.com                               =   0 =  OK 
     *   584 - assertIsTrue  pio_#pio@factory.com                               =   0 =  OK 
     *   585 - assertIsFalse pio_pio@#factory.com                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   586 - assertIsFalse pio_pio@factory.c#om                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   587 - assertIsFalse pio_pio@factory.c*om                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   588 - assertIsTrue  pio^_pio@factory.com                               =   0 =  OK 
     *   589 - assertIsTrue  "Abc\@def"@example.com                             =   1 =  OK 
     *   590 - assertIsTrue  "Fred Bloggs"@example.com                          =   1 =  OK 
     *   591 - assertIsTrue  "Fred\ Bloggs"@example.com                         =   1 =  OK 
     *   592 - assertIsTrue  Fred\ Bloggs@example.com                           =   0 =  OK 
     *   593 - assertIsTrue  "Joe\Blow"@example.com                             =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   594 - assertIsTrue  "Joe\\Blow"@example.com                            =   1 =  OK 
     *   595 - assertIsTrue  "Abc@def"@example.com                              =   1 =  OK 
     *   596 - assertIsTrue  customer/department=shipping@example.com           =   0 =  OK 
     *   597 - assertIsTrue  \$A12345@example.com                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   598 - assertIsTrue  !def!xyz%abc@example.com                           =   0 =  OK 
     *   599 - assertIsTrue  _somename@example.com                              =   0 =  OK 
     *   600 - assertIsTrue  abc."defghi".xyz@example.com                       =   1 =  OK 
     *   601 - assertIsTrue  "abcdefghixyz"@example.com                         =   1 =  OK 
     *   602 - assertIsFalse abc"defghi"xyz@example.com                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   603 - assertIsFalse abc\"def\"ghi@example.com                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     * 
     * 
     *   ASSERT_IS_TRUE   267   KORREKT  244 =   91.386 % | FALSCH ERKANNT   23
     *   ASSERT_IS_FALSE  336   KORREKT  335 =   99.702 % | FALSCH ERKANNT    1
     * 
     *   INSGESAMT        603   KORREKT  579 =   96.020 % | FALSCH ERKANNT   24
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

      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[001.002.003.004]" );
      assertIsTrue( "\"ABC.DEF\"@[127.0.0.1]" );
      assertIsFalse( "ABC.DEF[1.2.3.4]" );
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
      assertIsFalse( "ABC.DEF@[IPv6:1]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7]" );
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
      wlHeadline( "unsorted from the WEB" );
      wl( "" );

      /*
       * Various examples. Scraped from the Internet-Forums.
       * 
       * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
       * https://stackoverflow.com/questions/25471114/how-to-validate-an-e-mail-address-in-swift?noredirect=1&lq=1
       * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
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

      assertIsFalse( "fdsa" );
      assertIsFalse( "fdsa@" );
      assertIsFalse( "fdsa@fdsa" );
      assertIsFalse( "fdsa@fdsa." );

      assertIsFalse( "Foobar Some@thing.com" );

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

      assertIsTrue( "cog@wheel.com" );
      assertIsTrue( "\"cogwheel the orange\"@example.com" );
      assertIsFalse( "123@$.xyz" );

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

      assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
      assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
      assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );

      assertIsFalse( "check@thiscom" );
      assertIsFalse( "check@this..com" );
      assertIsFalse( " check@this.com" );
      assertIsTrue( "check@this.com" );

      assertIsTrue( "Abc@example.com" );
      assertIsTrue( "Abc@example.com." );
      assertIsTrue( "Abc@10.42.0.1" );
      assertIsTrue( "user@localserver" );
      assertIsTrue( "Abc.123@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "Loïc.Accentué@voilà.fr" );
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
    wl( "" );
    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( proz_true_korrekt_erkannt ) + m_number_format.format( proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_FALSE ) + "" );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( proz_false_korrekt_erkannt ) + m_number_format.format( proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE ) + "" );
    wl( "" );
    wl( "  INSGESAMT       " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( proz_korrekt_erkannt_insgesamt ) + m_number_format.format( proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE + T_RESULT_COUNT_EMAIL_IS_FALSE ) + "" );
    wl( "" );

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
    int return_code = FkEMail.checkEMailAdresse( pString );

    //int return_code = TestValidateEMailAdresse.isEmailValid( 8, pString ) ? 1 : 11;

    boolean knz_soll_wert = true;

    boolean is_true = return_code < 10;

    COUNT_ASSERT_IS_TRUE++;

    if ( is_true )
    {
      T_RESULT_COUNT_EMAIL_IS_TRUE++;
    }
    else
    {
      T_RESULT_COUNT_EMAIL_IS_FALSE++;
    }

    wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
  }

  private static void assertIsFalse( String pString )
  {
    int return_code = FkEMail.checkEMailAdresse( pString );

    //int return_code = TestValidateEMailAdresse.isEmailValid( 2, pString ) ? 1 : 11;

    boolean knz_soll_wert = false;

    boolean is_true = return_code < 10;

    COUNT_ASSERT_IS_FALSE++;

    if ( is_true )
    {
      F_RESULT_COUNT_EMAIL_IS_TRUE++;
    }
    else
    {
      F_RESULT_COUNT_EMAIL_IS_FALSE++;
    }

    wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
  }

}
