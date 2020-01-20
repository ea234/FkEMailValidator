package de.fk.email;

class TestValidateEMailAdresse1
{
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
     *    19 - assertIsTrue  2@bde.cc                                           =   0 =  OK 
     *    20 - assertIsTrue  -@bde.cc                                           =   0 =  OK 
     *    21 - assertIsTrue  a2@bde.cc                                          =   0 =  OK 
     *    22 - assertIsTrue  a-b@bde.cc                                         =   0 =  OK 
     *    23 - assertIsTrue  ab@b-de.cc                                         =   0 =  OK 
     *    24 - assertIsTrue  a+b@bde.cc                                         =   0 =  OK 
     *    25 - assertIsTrue  f.f.f@bde.cc                                       =   0 =  OK 
     *    26 - assertIsTrue  ab_c@bde.cc                                        =   0 =  OK 
     *    27 - assertIsTrue  _-_@bde.cc                                         =   0 =  OK 
     *    28 - assertIsTrue  k.haak@12move.nl                                   =   0 =  OK 
     *    29 - assertIsTrue  K.HAAK@12MOVE.NL                                   =   0 =  OK 
     *    30 - assertIsTrue  email@domain.com                                   =   0 =  OK 
     *    31 - assertIsTrue  w.b.f@test.com                                     =   0 =  OK 
     *    32 - assertIsTrue  w.b.f@test.museum                                  =   0 =  OK 
     *    33 - assertIsTrue  a.a@test.com                                       =   0 =  OK 
     *    34 - assertIsTrue  ab@288.120.150.10.com                              =   0 =  OK 
     *    35 - assertIsTrue  ab@[120.254.254.120]                               =   2 =  OK 
     *    36 - assertIsTrue  firstname.lastname@domain.com                      =   0 =  OK 
     *    37 - assertIsTrue  email@subdomain.domain.com                         =   0 =  OK 
     *    38 - assertIsTrue  firstname+lastname@domain.com                      =   0 =  OK 
     *    39 - assertIsTrue  1234567890@domain.com                              =   0 =  OK 
     *    40 - assertIsTrue  a@domain.com                                       =   0 =  OK 
     *    41 - assertIsTrue  a.b.c.d@domain.com                                 =   0 =  OK 
     *    42 - assertIsTrue  aap.123.noot.mies@domain.com                       =   0 =  OK 
     *    43 - assertIsTrue  1@domain.com                                       =   0 =  OK 
     *    44 - assertIsTrue  email@domain.co.jp                                 =   0 =  OK 
     *    45 - assertIsTrue  firstname-lastname@domain.com                      =   0 =  OK 
     *    46 - assertIsTrue  firstname-lastname@d.com                           =   0 =  OK 
     *    47 - assertIsTrue  FIRSTNAME-LASTNAME@d--n.com                        =   0 =  OK 
     *    48 - assertIsTrue  first-name-last-name@d-a-n.com                     =   0 =  OK 
     *    49 - assertIsTrue  john.smith@example.com                             =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    50 - assertIsFalse null                                               =  10 =  OK    Laenge: Eingabe ist null
     *    51 - assertIsFalse                                                    =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    52 - assertIsFalse                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    53 - assertIsFalse ABCDEFGHIJKLMNOP                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    54 - assertIsFalse ABC.DEF.GHI.JKL                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    55 - assertIsFalse @GHI.JKL                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    56 - assertIsFalse ABC.DEF@                                           =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    57 - assertIsFalse ABC.DEF@@GHI.JKL                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    58 - assertIsFalse @%^%#$@#$@#.com                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    59 - assertIsFalse @domain.com                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    60 - assertIsFalse email.domain.com                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    61 - assertIsFalse email@domain@domain.com                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    62 - assertIsFalse ABCDEF@GHIJKL                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    63 - assertIsFalse ABC.DEF@GHIJKL                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    64 - assertIsFalse .ABC.DEF@GHI.JKL                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    65 - assertIsFalse ABC.DEF@GHI.JKL.                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    66 - assertIsFalse ABC..DEF@GHI.JKL                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    67 - assertIsFalse ABC.DEF@GHI..JKL                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    68 - assertIsFalse ABC.DEF@GHI.JKL..                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    69 - assertIsFalse ABC.DEF.@GHI.JKL                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    70 - assertIsFalse ABC.DEF@.GHI.JKL                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    71 - assertIsFalse ABC.DEF@.                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    72 - assertIsTrue  ABC1.DEF2@GHI3.JKL4                                =   0 =  OK 
     *    73 - assertIsTrue  ABC.DEF_@GHI.JKL                                   =   0 =  OK 
     *    74 - assertIsTrue  #ABC.DEF@GHI.JKL                                   =   0 =  OK 
     *    75 - assertIsTrue  ABC.DEF@GHI.JK2                                    =   0 =  OK 
     *    76 - assertIsTrue  ABC.DEF@2HI.JKL                                    =   0 =  OK 
     *    77 - assertIsFalse ABC.DEF@GHI.2KL                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    78 - assertIsFalse ABC.DEF@GHI.JK-                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    79 - assertIsFalse ABC.DEF@GHI.JK_                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    80 - assertIsFalse ABC.DEF@-HI.JKL                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    81 - assertIsFalse ABC.DEF@_HI.JKL                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    82 - assertIsFalse ABC DEF@GHI.DE                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    83 - assertIsFalse A . B & C . D                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    84 - assertIsFalse (?).[!]@{&}.<:>                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    85 - assertIsTrue  {local{name{{with{@leftbracket.com                 =   0 =  OK 
     *    86 - assertIsTrue  }local}name}}with{@rightbracket.com                =   0 =  OK 
     *    87 - assertIsTrue  |local||name|with|@pipe.com                        =   0 =  OK 
     *    88 - assertIsTrue  %local%%name%with%@percentage.com                  =   0 =  OK 
     *    89 - assertIsTrue  $local$$name$with$@dollar.com                      =   0 =  OK 
     *    90 - assertIsTrue  &local&&name&with&$@amp.com                        =   0 =  OK 
     *    91 - assertIsTrue  ABC.DEF##name#with#@hash.com                       =   0 =  OK 
     *    92 - assertIsTrue  ~local~~name~with~@tilde.com                       =   0 =  OK 
     *    93 - assertIsTrue  !local!!name!with!@exclamation.com                 =   0 =  OK 
     *    94 - assertIsTrue  ?local??name?with?@question.com                    =   0 =  OK 
     *    95 - assertIsTrue  *local**name*with*@asterisk.com                    =   0 =  OK 
     *    96 - assertIsTrue  `local``name`with`@grave-accent.com                =   0 =  OK 
     *    97 - assertIsTrue  ^local^^name^with^@xor.com                         =   0 =  OK 
     *    98 - assertIsTrue  =local==name=with=@equality.com                    =   0 =  OK 
     *    99 - assertIsTrue  +local++name+with+@equality.com                    =   0 =  OK 
     *   100 - assertIsTrue  "B3V3RLY H1LL$"@example.com                        =   1 =  OK 
     *   101 - assertIsTrue  "-- --- .. -."@sh.de                               =   1 =  OK 
     *   102 - assertIsTrue  {{-^-}{-=-}{-^-}}@GHI.JKL                          =   0 =  OK 
     *   103 - assertIsTrue  #!$%&'*+-/=?^_`{}|~@eksample.org                   =   0 =  OK 
     *   104 - assertIsTrue  "\" + \"select * from user\" + \""@example.de      =   1 =  OK 
     *   105 - assertIsFalse [A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   106 - assertIsFalse "()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a"@example.org =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   107 - assertIsFalse email@{leftbracket.com                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   108 - assertIsFalse email@rightbracket}.com                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   109 - assertIsFalse email@p|pe.com                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   110 - assertIsFalse isis@100%.nl                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   111 - assertIsFalse email@dollar$.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   112 - assertIsFalse email@r&amp;d.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   113 - assertIsFalse email@#hash.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   114 - assertIsFalse email@wave~tilde.com                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   115 - assertIsFalse email@exclamation!mark.com                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   116 - assertIsFalse email@question?mark.com                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   117 - assertIsFalse email@obelix*asterisk.com                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   118 - assertIsFalse email@grave`accent.com                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   119 - assertIsFalse email@colon:colon.com                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   120 - assertIsFalse email@caret^xor.com                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   121 - assertIsFalse email@=qowaiv.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   122 - assertIsFalse email@plus+.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   123 - assertIsFalse email@domain.com>                                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   124 - assertIsFalse email@mailto:domain.com                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   125 - assertIsFalse mailto:mailto:email@domain.com                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   126 - assertIsFalse email@-domain.com                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   127 - assertIsFalse email@domain-.com                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   128 - assertIsFalse email@domain.com-                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   129 - assertIsFalse Joe Smith &lt;email@domain.com&gt;                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   130 - assertIsTrue  ?????@domain.com                                   =   0 =  OK 
     *   131 - assertIsFalse local@?????.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   132 - assertIsTrue  email@domain-one.com                               =   0 =  OK 
     *   133 - assertIsTrue  _______@domain.com                                 =   0 =  OK 
     *   134 - assertIsFalse DomainHyphen@-atstart                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   135 - assertIsFalse DomainHyphen@atstart-.com                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   136 - assertIsFalse DomainHyphen@bb.-cc                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   137 - assertIsFalse DomainHyphen@bb.-cc-                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   138 - assertIsFalse DomainHyphen@bb.cc-                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   139 - assertIsFalse DomainNotAllowedCharacter@/atstart                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   140 - assertIsFalse DomainNotAllowedCharacter@a,start                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   141 - assertIsFalse DomainNotAllowedCharacter@atst\art.com             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   142 - assertIsFalse DomainNotAllowedCharacter@exa\mple                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   143 - assertIsFalse DomainNotAllowedCharacter@example'                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   144 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =   2 =  OK 
     *   145 - assertIsTrue  ABC.DEF@[001.002.003.004]                          =   2 =  OK 
     *   146 - assertIsTrue  "ABC.DEF"@[127.0.0.1]                              =   3 =  OK 
     *   147 - assertIsFalse ABC.DEF[1.2.3.4]                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   148 - assertIsFalse [1.2.3.4]@[5.6.7.8]                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   149 - assertIsFalse ABC.DEF[@1.2.3.4]                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   150 - assertIsFalse "[1.2.3.4]"@[5.6.7.8]                              =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   151 - assertIsFalse ABC.DEF@MyDomain[1.2.3.4]                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   152 - assertIsFalse ABC.DEF@[1.00002.3.4]                              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   153 - assertIsFalse ABC.DEF@[1.2.3.456]                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   154 - assertIsFalse ABC.DEF@[..]                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   155 - assertIsFalse ABC.DEF@[.2.3.4]                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   156 - assertIsFalse ABC.DEF@[]                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   157 - assertIsFalse ABC.DEF@[1]                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   158 - assertIsFalse ABC.DEF@[1.2]                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   159 - assertIsFalse ABC.DEF@[1.2.3]                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   160 - assertIsFalse ABC.DEF@[1.2.3.4.5]                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   161 - assertIsFalse ABC.DEF@[1.2.3.4.5.6]                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   162 - assertIsFalse ABC.DEF@[MyDomain.de]                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   163 - assertIsFalse ABC.DEF@[1.2.3.]                                   =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   164 - assertIsFalse ABC.DEF@[1.2.3. ]                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   165 - assertIsFalse ABC.DEF@[1.2.3.4].de                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   166 - assertIsFalse ABC.DE@[1.2.3.4][5.6.7.8]                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   167 - assertIsFalse ABC.DEF@[1.2.3.4                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   168 - assertIsFalse ABC.DEF@1.2.3.4]                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   169 - assertIsFalse ABC.DEF@[1.2.3.Z]                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   170 - assertIsFalse ABC.DEF@[12.34]                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   171 - assertIsFalse ABC.DEF@[1.2.3.4]ABC                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   172 - assertIsFalse ABC.DEF@[1234.5.6.7]                               =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   173 - assertIsFalse ABC.DEF@[1.2...3.4]                                =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   174 - assertIsTrue  email@[123.123.123.123]                            =   2 =  OK 
     *   175 - assertIsFalse email@111.222.333                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   176 - assertIsFalse email@111.222.333.256                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   177 - assertIsFalse email@[123.123.123.123                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   178 - assertIsFalse email@[123.123.123].123                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   179 - assertIsFalse email@123.123.123.123]                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   180 - assertIsFalse email@123.123.[123.123]                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   181 - assertIsFalse ab@988.120.150.10                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   182 - assertIsFalse ab@120.256.256.120                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   183 - assertIsFalse ab@120.25.1111.120                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   184 - assertIsFalse ab@[188.120.150.10                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   185 - assertIsFalse ab@188.120.150.10]                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   186 - assertIsFalse ab@[188.120.150.10].com                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   187 - assertIsTrue  ABC.DEF@[IPv6:2001:db8::1]                         =   4 =  OK 
     *   188 - assertIsFalse ABC.DEF@[IP                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   189 - assertIsFalse ABC.DEF@[IPv6]                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   190 - assertIsFalse ABC.DEF@[IPv6:]                                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   191 - assertIsFalse ABC.DEF@[IPv6:1]                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   192 - assertIsFalse ABC.DEF@[IPv6:1:2]                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   193 - assertIsTrue  ABC.DEF@[IPv6:1:2:3]                               =   4 =  OK 
     *   194 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4]                             =   4 =  OK 
     *   195 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:]                          =  44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   196 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6]                         =   4 =  OK 
     *   197 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6:7]                       =   4 =  OK 
     *   198 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]                     =   4 =  OK 
     *   199 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]                   =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   200 - assertIsFalse ABC.DEF@[IPv4:1:2:3:4]                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   201 - assertIsFalse ABC.DEF@[I127.0.0.1]                               =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   202 - assertIsFalse ABC.DEF@[D127.0.0.1]                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   203 - assertIsFalse ABC.DEF@[iPv6:2001:db8::1]                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   204 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8]                      =   4 =  OK 
     *   205 - assertIsFalse ABC.DEF@[IPv6:1:2:3::5::7:8]                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   206 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:Z]                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   207 - assertIsFalse ABC.DEF@[IPv6:12:34]                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   208 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6]ABC                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   209 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   210 - assertIsFalse ABC.DEF@[IPv6:12345:6:7:8:9]                       =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   211 - assertIsFalse ABC.DEF@[IPv6:1:2:3:::6:7:8]                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   212 - assertIsTrue  ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]      =   4 =  OK 
     *   213 - assertIsTrue  ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334] =   4 =  OK 
     *   214 - assertIsFalse ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334] =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   215 - assertIsFalse ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334] =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   216 - assertIsTrue  ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334] =   4 =  OK 
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   217 - assertIsTrue  ABC.DEF@[IPv6::FFFF:127.0.0.1]                     =   4 =  OK 
     *   218 - assertIsTrue  ABC.DEF@[IPv6::ffff:127.0.0.1]                     =   4 =  OK 
     *   219 - assertIsTrue  ABC.DEF@[::FFFF:127.0.0.1]                         =   4 =  OK 
     *   220 - assertIsTrue  ABC.DEF@[::ffff:127.0.0.1]                         =   4 =  OK 
     *   221 - assertIsFalse ABC.DEF@[IPv6::ffff:.127.0.1]                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   222 - assertIsFalse ABC.DEF@[IPv6::fff:127.0.0.1]                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   223 - assertIsFalse ABC.DEF@[IPv6::1234:127.0.0.1]                     =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   224 - assertIsFalse ABC.DEF@[IPv6:127.0.0.1]                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   225 - assertIsFalse ABC.DEF@[IPv6:::127.0.0.1]                         =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *   226 - assertIsTrue  "ABC.DEF"@GHI.DE                                   =   1 =  OK 
     *   227 - assertIsTrue  "ABC DEF"@GHI.DE                                   =   1 =  OK 
     *   228 - assertIsTrue  "ABC@DEF"@GHI.DE                                   =   1 =  OK 
     *   229 - assertIsFalse "ABC DEF@G"HI.DE                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   230 - assertIsFalse ""@GHI.DE                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   231 - assertIsFalse "ABC.DEF@G"HI.DE                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   232 - assertIsFalse A@G"HI.DE                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   233 - assertIsFalse "@GHI.DE                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   234 - assertIsFalse ABC.DEF."                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   235 - assertIsFalse ABC.DEF@""                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   236 - assertIsFalse ABC.DEF@G"HI.DE                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   237 - assertIsFalse ABC.DEF@GHI.DE"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   238 - assertIsFalse ABC.DEF@"GHI.DE                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   239 - assertIsFalse "Escape.Sequenz.Ende"                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   240 - assertIsFalse ABC.DEF"GHI.DE                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   241 - assertIsFalse ABC.DEF"@GHI.DE                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   242 - assertIsFalse ABC.DE"F@GHI.DE                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   243 - assertIsFalse "ABC.DEF@GHI.DE                                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   244 - assertIsFalse "ABC.DEF@GHI.DE"                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   245 - assertIsTrue  ".ABC.DEF"@GHI.DE                                  =   1 =  OK 
     *   246 - assertIsTrue  "ABC.DEF."@GHI.DE                                  =   1 =  OK 
     *   247 - assertIsTrue  "ABC".DEF."GHI"@JKL.de                             =   1 =  OK 
     *   248 - assertIsFalse A"BC".DEF."GHI"@JKL.de                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   249 - assertIsFalse "ABC".DEF.G"HI"@JKL.de                             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   250 - assertIsFalse "AB"C.DEF."GHI"@JKL.de                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   251 - assertIsFalse "ABC".DEF."GHI"J@KL.de                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   252 - assertIsFalse "AB"C.D"EF"@GHI.DE                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   253 - assertIsFalse "Ende.am.Eingabeende"                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   254 - assertIsFalse 0"00.000"@GHI.JKL                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *   255 - assertIsTrue  (ABC)DEF@GHI.JKL                                   =   6 =  OK 
     *   256 - assertIsTrue  (ABC) DEF@GHI.JKL                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   257 - assertIsTrue  ABC(DEF)@GHI.JKL                                   =   6 =  OK 
     *   258 - assertIsTrue  ABC.DEF@GHI.JKL(MNO)                               =   6 =  OK 
     *   259 - assertIsTrue  ABC.DEF@GHI.JKL      (MNO)                         =   6 =  OK 
     *   260 - assertIsFalse ABC.DEF@             (MNO)                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   261 - assertIsFalse ABC.DEF@   .         (MNO)                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   262 - assertIsFalse ABC.DEF              (MNO)                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   263 - assertIsFalse ABC.DEF@GHI.         (MNO)                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   264 - assertIsFalse ABC.DEF@GHI.JKL       MNO                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   265 - assertIsFalse ABC.DEF@GHI.JKL                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   266 - assertIsFalse ABC.DEF@GHI.JKL       .                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   267 - assertIsTrue  ABC.DEF@GHI.JKL ()                                 =   6 =  OK 
     *   268 - assertIsTrue  ABC.DEF@GHI.JKL()                                  =   6 =  OK 
     *   269 - assertIsTrue  ABC.DEF@()GHI.JKL                                  =   6 =  OK 
     *   270 - assertIsTrue  ABC.DEF()@GHI.JKL                                  =   6 =  OK 
     *   271 - assertIsTrue  ()ABC.DEF@GHI.JKL                                  =   6 =  OK 
     *   272 - assertIsFalse (ABC).DEF@GHI.JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   273 - assertIsFalse ABC.(DEF)@GHI.JKL                                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   274 - assertIsFalse ABC.DEF@(GHI).JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   275 - assertIsFalse ABC.DEF@GHI.(JKL).MNO                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   276 - assertIsFalse ABC.DEF@GHI.JK(L.M)NO                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   277 - assertIsFalse AB(CD)EF@GHI.JKL                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   278 - assertIsFalse AB.(CD).EF@GHI.JKL                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   279 - assertIsFalse AB."(CD)".EF@GHI.JKL                               =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   280 - assertIsFalse (ABCDEF)@GHI.JKL                                   =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   281 - assertIsFalse (ABCDEF).@GHI.JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   282 - assertIsFalse (AB"C)DEF@GHI.JKL                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   283 - assertIsFalse (AB\C)DEF@GHI.JKL                                  =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   284 - assertIsFalse (AB\@C)DEF@GHI.JKL                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   285 - assertIsFalse ABC(DEF@GHI.JKL                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   286 - assertIsFalse ABC.DEF@GHI)JKL                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   287 - assertIsFalse )ABC.DEF@GHI.JKL                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   288 - assertIsFalse ABC(DEF@GHI).JKL                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   289 - assertIsFalse ABC(DEF.GHI).JKL                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   290 - assertIsFalse (ABC.DEF@GHI.JKL)                                  =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   291 - assertIsFalse (A(B(C)DEF@GHI.JKL                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   292 - assertIsFalse (A)B)C)DEF@GHI.JKL                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   293 - assertIsFalse (A)BCDE(F)@GHI.JKL                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   294 - assertIsFalse ABC.DEF@(GH)I.JK(LM)                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   295 - assertIsFalse ABC.DEF@(GH(I.JK)L)M                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   296 - assertIsTrue  ABC.DEF@(comment)[1.2.3.4]                         =   2 =  OK 
     *   297 - assertIsFalse ABC.DEF@(comment) [1.2.3.4]                        = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *   298 - assertIsTrue  ABC.DEF@[1.2.3.4](comment)                         =   2 =  OK 
     *   299 - assertIsTrue  ABC.DEF@[1.2.3.4]    (comment)                     =   2 =  OK 
     *   300 - assertIsFalse ABC.DEF@[1.2.3(comment).4]                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   301 - assertIsTrue  ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]             =   4 =  OK 
     *   302 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)             =   4 =  OK 
     *   303 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)         =   4 =  OK 
     *   304 - assertIsTrue  (comment)john.smith@example.com                    =   6 =  OK 
     *   305 - assertIsTrue  john.smith(comment)@example.com                    =   6 =  OK 
     *   306 - assertIsTrue  john.smith@(comment)example.com                    =   6 =  OK 
     *   307 - assertIsTrue  john.smith@example.com(comment)                    =   6 =  OK 
     *   308 - assertIsFalse john.smith@exampl(comment)e.com                    = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   309 - assertIsFalse john.s(comment)mith@example.com                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   310 - assertIsFalse john.smith(comment)@(comment)example.com           =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   311 - assertIsFalse john.smith(com@ment)example.com                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   312 - assertIsFalse email( (nested) )@plus.com                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   313 - assertIsFalse email)mirror(@plus.com                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   314 - assertIsFalse email@plus.com (not closed comment                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   315 - assertIsFalse email(with @ in comment)plus.com                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   316 - assertIsTrue  email@domain.com (joe Smith)                       =   6 =  OK 
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *   317 - assertIsTrue  ABC DEF <ABC.DEF@GHI.JKL>                          =   0 =  OK 
     *   318 - assertIsTrue  <ABC.DEF@GHI.JKL> ABC DEF                          =   0 =  OK 
     *   319 - assertIsFalse ABC DEF ABC.DEF@GHI.JKL>                           =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   320 - assertIsFalse <ABC.DEF@GHI.JKL ABC DEF                           =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   321 - assertIsTrue  "ABC DEF "<ABC.DEF@GHI.JKL>                        =   0 =  OK 
     *   322 - assertIsFalse "ABC<DEF>"@JKL.DE                                  =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   323 - assertIsFalse >                                                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   324 - assertIsFalse "ABC<DEF@GHI.COM>"@JKL.DE                          =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   325 - assertIsFalse ABC DEF <ABC.<DEF@GHI.JKL>                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   326 - assertIsFalse <ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   327 - assertIsFalse ABC DEF <ABC.DEF@GHI.JKL                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   328 - assertIsFalse ABC.DEF@GHI.JKL> ABC DEF                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   329 - assertIsFalse ABC DEF >ABC.DEF@GHI.JKL<                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   330 - assertIsFalse >ABC.DEF@GHI.JKL< ABC DEF                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   331 - assertIsFalse ABC DEF <A@A>                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   332 - assertIsFalse <A@A> ABC DEF                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   333 - assertIsFalse ABC DEF <>                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   334 - assertIsFalse <> ABC DEF                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   335 - assertIsTrue  Non EMail part <(comment)Local."Part"@[IPv6::ffff:127.0.0.1]> =   9 =  OK 
     *   336 - assertIsTrue  Non EMail part <Local."Part"(comment)@[IPv6::ffff:127.0.0.1]> =   9 =  OK 
     *   337 - assertIsTrue  <(comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part =   9 =  OK 
     *   338 - assertIsTrue  <Local."Part"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part  =   9 =  OK 
     *   339 - assertIsFalse Non EMail part < (comment)Local."Part"@[IPv6::ffff:127.0.0.1]> =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   340 - assertIsFalse Non EMail part <Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]> =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   341 - assertIsFalse < (comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   342 - assertIsFalse <Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   343 - assertIsTrue  Joe Smith <email@domain.com>                       =   0 =  OK 
     *   344 - assertIsFalse Joe Smith <mailto:email@domain.com>                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   345 - assertIsFalse Test |<gaaf <email@domain.com>                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   346 - assertIsFalse "With extra < within quotes" Display Name<email@domain.com> =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *   347 - assertIsTrue  A@B.CD                                             =   0 =  OK 
     *   348 - assertIsFalse A@B.C                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   349 - assertIsFalse A@COM                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   350 - assertIsTrue  ABC.DEF@GHI.JKL                                    =   0 =  OK 
     *   351 - assertIsTrue  ABC.DEF@GHI.J                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   352 - assertIsTrue  ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123 =   0 =  OK 
     *   353 - assertIsFalse ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *   354 - assertIsTrue  A@B.CD                                             =   0 =  OK 
     *   355 - assertIsTrue  ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com =   0 =  OK 
     *   356 - assertIsTrue  abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de =   0 =  OK 
     *   357 - assertIsTrue  ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL =   0 =  OK 
     *   358 - assertIsFalse ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   359 - assertIsFalse 1234567890123456789012345678901234567890123456789012345678901234+x@example.com =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   360 - assertIsTrue  domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   361 - assertIsFalse domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   362 - assertIsTrue  two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   363 - assertIsFalse domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   364 - assertIsTrue  63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   365 - assertIsTrue  63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   366 - assertIsTrue  12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   367 - assertIsFalse 12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   368 - assertIsFalse "very.(z),:;<>[]\".VERY.\"very@\\ \"very\".unusual"@strange.example.com =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   369 - assertIsFalse too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   370 - assertIsFalse Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   371 - assertIsFalse 3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   372 - assertIsTrue  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   373 - assertIsFalse ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   374 - assertIsTrue  email@domain.topleveldomain                        =   0 =  OK 
     * 
     * 
     * ---- unknown errors ----------------------------------------------------------------------------------------------------
     * 
     * 
     * eMail-Adresses which should be true according to Corniel Nobel.
     * 
     * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
     * 
     *   375 - assertIsTrue  local@2001:0db8:85a3:0000:0000:8a2e:0370:7334      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   376 - assertIsFalse a@bde.c-c                                          =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   377 - assertIsTrue  "Joe Smith" email@domain.com                       =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   378 - assertIsTrue  "Joe\tSmith" email@domain.com                      =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   379 - assertIsTrue  "Joe"Smith" email@domain.com                       =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   380 - assertIsTrue  MailTo:casesensitve@domain.com                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   381 - assertIsTrue  mailto:email@domain.com                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsTrue  ab@188.120.150.10                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   383 - assertIsTrue  ab@1.0.0.10                                        =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   384 - assertIsTrue  ab@120.25.254.120                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   385 - assertIsTrue  ab@01.120.150.1                                    =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   386 - assertIsTrue  ab@88.120.150.021                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   387 - assertIsTrue  ab@88.120.150.01                                   =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   388 - assertIsTrue  email@123.123.123.123                              =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   389 - assertIsTrue  email@domain                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   390 - assertIsTrue  Test |<gaaf <email@domain.com>                     =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *   391 - assertIsTrue  Joe Smith <mailto:email@domain.com>                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   392 - assertIsTrue  Joe Smith <mailto:email(with comment)@domain.com>  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   393 - assertIsTrue  "With extra < within quotes" Display Name<email@domain.com> =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     * 
     * 
     * ---- unsorted ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   394 - assertIsFalse ..@test.com                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   395 - assertIsFalse .a@test.com                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   396 - assertIsFalse ab@sd@dd                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   397 - assertIsFalse .@s.dd                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   398 - assertIsFalse a@b.-de.cc                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   399 - assertIsFalse a@bde-.cc                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   400 - assertIsFalse a@b._de.cc                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   401 - assertIsFalse a@bde_.cc                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   402 - assertIsFalse a@bde.cc.                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   403 - assertIsFalse ab@b+de.cc                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   404 - assertIsFalse a..b@bde.cc                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   405 - assertIsFalse _@bde.cc,                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   406 - assertIsFalse plainaddress                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   407 - assertIsFalse plain.address                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   408 - assertIsFalse .email@domain.com                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   409 - assertIsFalse email.@domain.com                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   410 - assertIsFalse email..email@domain.com                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   411 - assertIsFalse email@.domain.com                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   412 - assertIsFalse email@domain.com.                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   413 - assertIsFalse email@domain..com                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   414 - assertIsFalse "Joe Smith email@domain.com                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   415 - assertIsFalse "Joe Smith' email@domain.com                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   416 - assertIsFalse "Joe Smith"email@domain.com                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   417 - assertIsFalse Display Name <email@plus.com> (after name with display) =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   418 - assertIsTrue  domain.starts.with.digit@2domain.com               =   0 =  OK 
     *   419 - assertIsTrue  domain.ends.with.digit@domain2.com                 =   0 =  OK 
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

      assertIsTrue( "2@bde.cc" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "a2@bde.cc" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsTrue( "k.haak@12move.nl" );
      assertIsTrue( "K.HAAK@12MOVE.NL" );
      assertIsTrue( "email@domain.com" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsTrue( "a@domain.com" );
      assertIsTrue( "a.b.c.d@domain.com" );
      assertIsTrue( "aap.123.noot.mies@domain.com" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "email@domain.co.jp" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "firstname-lastname@d.com" );
      assertIsTrue( "FIRSTNAME-LASTNAME@d--n.com" );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
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
      assertIsFalse( "A . B & C . D" );
      assertIsFalse( "(?).[!]@{&}.<:>" );

      assertIsTrue( "{local{name{{with{@leftbracket.com" );
      assertIsTrue( "}local}name}}with{@rightbracket.com" );
      assertIsTrue( "|local||name|with|@pipe.com" );
      assertIsTrue( "%local%%name%with%@percentage.com" );
      assertIsTrue( "$local$$name$with$@dollar.com" );
      assertIsTrue( "&local&&name&with&$@amp.com" );
      assertIsTrue( "ABC.DEF##name#with#@hash.com" );
      assertIsTrue( "~local~~name~with~@tilde.com" );
      assertIsTrue( "!local!!name!with!@exclamation.com" );
      assertIsTrue( "?local??name?with?@question.com" );
      assertIsTrue( "*local**name*with*@asterisk.com" );
      assertIsTrue( "`local``name`with`@grave-accent.com" );
      assertIsTrue( "^local^^name^with^@xor.com" );
      assertIsTrue( "=local==name=with=@equality.com" );
      assertIsTrue( "+local++name+with+@equality.com" );

      assertIsTrue( "\"B3V3RLY H1LL$\"@example.com" );
      assertIsTrue( "\"-- --- .. -.\"@sh.de" );
      assertIsTrue( "{{-^-}{-=-}{-^-}}@GHI.JKL" );
      assertIsTrue( "#!$%&'*+-/=?^_`{}|~@eksample.org" );
      assertIsTrue( "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de" );
      assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" );
      assertIsFalse( "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org" );

      assertIsFalse( "email@{leftbracket.com" );
      assertIsFalse( "email@rightbracket}.com" );
      assertIsFalse( "email@p|pe.com" );
      assertIsFalse( "isis@100%.nl" );
      assertIsFalse( "email@dollar$.com" );
      assertIsFalse( "email@r&amp;d.com" );
      assertIsFalse( "email@#hash.com" );
      assertIsFalse( "email@wave~tilde.com" );
      assertIsFalse( "email@exclamation!mark.com" );
      assertIsFalse( "email@question?mark.com" );
      assertIsFalse( "email@obelix*asterisk.com" );
      assertIsFalse( "email@grave`accent.com" );
      assertIsFalse( "email@colon:colon.com" );
      assertIsFalse( "email@caret^xor.com" );
      assertIsFalse( "email@=qowaiv.com" );
      assertIsFalse( "email@plus+.com" );
      assertIsFalse( "email@domain.com>" );

      assertIsFalse( "email@mailto:domain.com" );
      assertIsFalse( "mailto:mailto:email@domain.com" );

      assertIsFalse( "email@-domain.com" );
      assertIsFalse( "email@domain-.com" );
      assertIsFalse( "email@domain.com-" );

      assertIsFalse( "Joe Smith &lt;email@domain.com&gt;" );

      assertIsTrue( "?????@domain.com" );
      assertIsFalse( "local@?????.com" );

      assertIsTrue( "email@domain-one.com" );
      assertIsTrue( "_______@domain.com" );

      assertIsFalse( "DomainHyphen@-atstart" );
      assertIsFalse( "DomainHyphen@atstart-.com" );
      assertIsFalse( "DomainHyphen@bb.-cc" );
      assertIsFalse( "DomainHyphen@bb.-cc-" );
      assertIsFalse( "DomainHyphen@bb.cc-" );
      assertIsFalse( "DomainNotAllowedCharacter@/atstart" );
      assertIsFalse( "DomainNotAllowedCharacter@a,start" );
      assertIsFalse( "DomainNotAllowedCharacter@atst\\art.com" );
      assertIsFalse( "DomainNotAllowedCharacter@exa\\mple" );
      assertIsFalse( "DomainNotAllowedCharacter@example'" );

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
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC.DEF@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]" );
      assertIsTrue( "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]" );

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

      assertIsTrue( "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsTrue( "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " );
      assertIsFalse( "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsFalse( "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " );
      assertIsTrue( "Joe Smith <email@domain.com>" );
      assertIsFalse( "Joe Smith <mailto:email@domain.com>" );
      assertIsFalse( "Test |<gaaf <email@domain.com>" );
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
      wlHeadline( "unknown errors" );
      wl( "" );
      wl( "eMail-Adresses which should be true according to Corniel Nobel." );
      wl( "" );
      wl( "https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top" );
      wl( "" );

      assertIsTrue( "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );
      assertIsFalse( "a@bde.c-c" );
      assertIsTrue( "\"Joe Smith\" email@domain.com" );
      assertIsTrue( "\"Joe\\tSmith\" email@domain.com" );
      assertIsTrue( "\"Joe\"Smith\" email@domain.com" );
      assertIsTrue( "MailTo:casesensitve@domain.com" );
      assertIsTrue( "mailto:email@domain.com" );
      assertIsTrue( "ab@188.120.150.10" );
      assertIsTrue( "ab@1.0.0.10" );
      assertIsTrue( "ab@120.25.254.120" );
      assertIsTrue( "ab@01.120.150.1" );
      assertIsTrue( "ab@88.120.150.021" );
      assertIsTrue( "ab@88.120.150.01" );
      assertIsTrue( "email@123.123.123.123" );
      assertIsTrue( "email@domain" );
      assertIsTrue( "Test |<gaaf <email@domain.com>" );
      assertIsTrue( "Joe Smith <mailto:email@domain.com>" );
      assertIsTrue( "Joe Smith <mailto:email(with comment)@domain.com>" );
      assertIsTrue( "\"With extra < within quotes\" Display Name<email@domain.com>" );

      wl( "" );
      wlHeadline( "unsorted" );
      wl( "" );

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
      assertIsFalse( "\"Joe Smith email@domain.com" );
      assertIsFalse( "\"Joe Smith' email@domain.com" );
      assertIsFalse( "\"Joe Smith\"email@domain.com" );
      assertIsFalse( "Display Name <email@plus.com> (after name with display)" );
      assertIsTrue( "domain.starts.with.digit@2domain.com" );
      assertIsTrue( "domain.ends.with.digit@domain2.com" );

    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    System.exit( 0 );
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

  private static int LAUFENDE_ZAHL = 0;

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

    boolean knz_soll_wert = true;

    boolean is_true = return_code < 10;

    System.out.println( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
  }

  private static void assertIsFalse( String pString )
  {
    int return_code = FkEMail.checkEMailAdresse( pString );

    boolean knz_soll_wert = false;

    boolean is_true = return_code < 10;

    System.out.println( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
  }

}
