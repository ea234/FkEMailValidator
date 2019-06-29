package de.fk.email;

public class FkEMail
{
  /**
   * <pre>
   * Validierung einer eMail-Adresse.
   * </pre>
   * 
   * @param pEingabe die zu pruefende Eingabe
   * @return TRUE, wenn die Eingabe nach der Struktur her eine eMail-Adresse ergeben kann. 
   */
  public static boolean validateEMailAdresse( String pEingabe )
  {
    return checkEMailAdresse( pEingabe ) < 10;
  }

  /**
   * <pre>
   * Validierung einer eMail-Adresse.
   * 
   * Auf den Eingabeparameter wird kein TRIM gemacht. 
   * 
   * Rueckgabewerte kleiner 10 sind OK.
   * 
   * Ein Rueckgabe von 0, ist eine eMail-Adresse ohne weitere Besonderheiten
   * 
   * Rueckgabewerte von 1 bis 9, sind eMail-Adressen mit Sonderangaben String, Kommentar oder IP-Adresse.
   * 
   * Folgende Rueckgaben sind moeglich:
   * 
   *     0 = eMail-Adresse korrekt
   *     1 = eMail-Adresse korrekt (Local Part mit String)
   *     2 = eMail-Adresse korrekt (IP4-Adresse)
   *     3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *     4 = eMail-Adresse korrekt (IP6-Adresse)
   *     5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *     6 = eMail-Adresse korrekt (Kommentar)
   *     7 = eMail-Adresse korrekt (Kommentar, String)
   *     8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *     9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *         
   *    
   *    10 Laenge: Eingabe ist null
   *    11 Laenge: Eingabe ist Leerstring
   *    12 Laenge: Laengenbegrenzungen stimmen nicht
   *    13 Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *    14 Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *    15 Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   *    
   *    20 Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    21 Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *    22 Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    23 Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    24 Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    
   *    26 AT-Zeichen: kein AT-Zeichen am Anfang
   *    27 AT-Zeichen: kein AT-Zeichen am Ende
   *    28 AT-Zeichen: kein AT-Zeichen gefunden
   *    29 AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    
   *    30 Trennzeichen: kein Beginn mit einem Punkt
   *    31 Trennzeichen: keine zwei Punkte hintereinander
   *    32 Trennzeichen: ungueltige Zeichenkombination ".@"
   *    33 Trennzeichen: ungueltige Zeichenkombination "@."
   *    34 Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    35 Trennzeichen: der letzte Punkt muss nach dem AT-Zeichen liegen
   *    36 Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    
   * Domain-Name mit IP-Adresse:
   * 
   *    40 IP6-Adressteil: String "IPv6:" erwartet
   *    41 IP6-Adressteil: Trennzeichenanzahl ist 0
   *    42 IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *    43 IP6-Adressteil: Zu wenig Trennzeichen
   *    44 IP6-Adressteil: ungueltige Kombination ":]"
   *    45 IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *    46 IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *    47 IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *    48 IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *    49 IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *    50 IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *    51 IP-Adressteil: IP-Adresse vor AT-Zeichen
   *    52 IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *    53 IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *    54 IP4-Adressteil: Byte-Overflow
   *    55 IP4-Adressteil: keine Ziffern vorhanden
   *    56 IP4-Adressteil: zu viele Trennzeichen
   *    57 IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    58 IP4-Adressteil: ungueltige Kombination ".]"
   *    59 IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    60 IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *    61 IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *        
   * Local-Part mit Anfuehrungszeichen 
   *        
   *    80 String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *    81 String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    82 String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    83 String: Escape-Zeichen nicht am Ende der Eingabe
   *    84 String: Ungueltige Escape-Sequenz im String
   *    85 String: Leerstring in Anfuehrungszeichen
   *    86 String: kein abschliessendes Anfuehrungszeichen gefunden.
   *    87 String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    88 String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *    89 String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *    
   * Kommentare
   * 
   *    91 Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *    92 Kommentar: Ungueltiges Zeichen im Kommentar
   *    93 Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *    94 Kommentar: kein Kommentar nach dem AT-Zeichen
   *    95 Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *    96 Kommentar: Escape-Zeichen nicht am Ende der Eingabe
   *    97 Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *    98 Kommentar: Kein lokaler Part vorhanden
   *    99 Kommentar: kein zweiter Kommentar gueltig
   *    
   *    
   * FkEMail.checkEMailAdresse( "A.B@C.DE"                       ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "A."B"@C.DE"                     ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * 
   * FkEMail.checkEMailAdresse( "A.B@[1.2.3.4]"                  ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "A."B"@[1.2.3.4]"                ) =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "A.B@[IPv6:1:2:3:4:5:6:7:8]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "A."B"@[IPv6:1:2:3:4:5:6:7:8]"   ) =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)B@C.DE"                      ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "A(B)@C.DE"                      ) =  6 = eMail-Adresse korrekt (Kommentar)
   * 
   * FkEMail.checkEMailAdresse( "(A)"B"@C.DE"                    ) =  7 = eMail-Adresse korrekt (Kommentar, String)
   * FkEMail.checkEMailAdresse( ""A"(B)@C.DE"                    ) =  7 = eMail-Adresse korrekt (Kommentar, String)
   * 
   * FkEMail.checkEMailAdresse( "(A)B@[1.2.3.4]"                 ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "A(B)@[1.2.3.4]"                 ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)"B"@[1.2.3.4]"               ) =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   * FkEMail.checkEMailAdresse( ""A"(B)@[1.2.3.4]"               ) =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)B@[IPv6:1:2:3:4:5:6:7:8]"    ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "A(B)@[IPv6:1:2:3:4:5:6:7:8]"    ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)"B"@[IPv6:1:2:3:4:5:6:7:8]"  ) =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   * FkEMail.checkEMailAdresse( ""A"(B)@[IPv6:1:2:3:4:5:6:7:8]"  ) =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   * 
   * 
   * Grundlegendes ----------------------------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "A@B.CD"                         ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ABC1.DEF2@GHI3.JKL4"            ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( null                             ) = 10 = Laenge: Eingabe ist null
   * FkEMail.checkEMailAdresse( " "                              ) = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   * FkEMail.checkEMailAdresse( "                "               ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "ABCDEFGHIJKLMNOP"               ) = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.J"                  ) = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   * FkEMail.checkEMailAdresse( "ME@MYSELF.LOCALHOST"            ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ME@MYSELF.LOCALHORST"           ) = 15 = Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.2KL"                ) = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JK-"                ) = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JK_"                ) = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JK2"                ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ABC.DEF@2HI.JKL"                ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ABC.DEF@-HI.JKL"                ) = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   * FkEMail.checkEMailAdresse( "ABC.DEF@_HI.JKL"                ) = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   * 
   * FkEMail.checkEMailAdresse( "A . B & C . D"                  ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "(?).[!]@{&}.<:>"                ) = 30 = Trennzeichen: kein Beginn mit einem Punkt
   * FkEMail.checkEMailAdresse( ".ABC.DEF@GHI.JKL"               ) = 30 = Trennzeichen: kein Beginn mit einem Punkt
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI..JKL"               ) = 31 = Trennzeichen: keine zwei Punkte hintereinander
   * FkEMail.checkEMailAdresse( "ABC.DEF.@GHI.JKL"               ) = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   * FkEMail.checkEMailAdresse( "ABC.DEF@."                      ) = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   * FkEMail.checkEMailAdresse( "ABC.DEF@.GHI.JKL"               ) = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   * FkEMail.checkEMailAdresse( "ABCDEF@GHIJKL"                  ) = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JKL."               ) = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   * FkEMail.checkEMailAdresse( "ABC.DEF@"                       ) = 27 = AT-Zeichen: kein AT-Zeichen am Ende
   * FkEMail.checkEMailAdresse( "ABC.DEF\@"                      ) = 28 = AT-Zeichen: kein AT-Zeichen gefunden (... da hier das AT-Zeichen maskiert ist)
   * FkEMail.checkEMailAdresse( "ABC.DEF@@GHI.JKL"               ) = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   * FkEMail.checkEMailAdresse( "ABC\@DEF@GHI.JKL"               ) =  0 = eMail-Adresse korrekt (erstes AT-Zeichen im lokal Part wurde maskiert)
   * 
   * Local-Part mit Anfuehrungszeichen --------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( ""ABC.DEF"@GHI.DE"               ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( ""ABC DEF"@GHI.DE"               ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( "ABC DEF@GHI.DE"                 ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( ""ABC@DEF"@GHI.DE"               ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( ""ABC DEF@G"HI.DE"               ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( """@GHI.DE"                      ) = 85 = String: Leerstring in Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( ""ABC.DEF@G"HI.DE"               ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( "A@G"HI.DE"                      ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( ""@GHI.DE"                       ) = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   * FkEMail.checkEMailAdresse( "ABC.DEF.""                      ) = 85 = String: Leerstring in Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@"""                     ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@G"HI.DE"                ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.DE""                ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@"GHI.DE"                ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( ""Escape.Sequenz.Ende\"          ) = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   * FkEMail.checkEMailAdresse( "ABC.DEF"GHI.DE"                 ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( "ABC.DEF"@GHI.DE"                ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( "ABC.DE"F@GHI.DE"                ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( ""ABC.DEF@GHI.DE"                ) = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   * FkEMail.checkEMailAdresse( ""ABC.DEF@GHI.DE""               ) = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   * 
   * FkEMail.checkEMailAdresse( "".ABC.DEF"@GHI.DE"              ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( ""ABC.DEF."@GHI.DE"              ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * 
   * FkEMail.checkEMailAdresse( ""ABC".DEF."GHI"@JKL.de"         ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( "A"BC".DEF."GHI"@JKL.de"         ) = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   * FkEMail.checkEMailAdresse( ""ABC".DEF.G"HI"@JKL.de"         ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( ""AB"C.DEF."GHI"@JKL.de"         ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( ""ABC".DEF."GHI"J@KL.de"         ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( ""AB"C.D"EF"@GHI.DE"             ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( ""Ende.am.Eingabeende""          ) = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   * FkEMail.checkEMailAdresse( "0"00.000"@GHI.JKL"              ) = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   * 
   * Domain-Name mit IP-Adresse ---------------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4]"              ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[001.002.003.004]"      ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( ""ABC.DEF"@[127.0.0.1]"          ) =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF[1.2.3.4]"               ) = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   * FkEMail.checkEMailAdresse( "[1.2.3.4]@[5.6.7.8]"            ) = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF[@1.2.3.4]"              ) = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   * FkEMail.checkEMailAdresse( ""[1.2.3.4]"@[5.6.7.8]"          ) = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@MyDomain[1.2.3.4]"      ) = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.00002.3.4]"          ) = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.456]"            ) = 54 = IP4-Adressteil: Byte-Overflow
   * FkEMail.checkEMailAdresse( "ABC.DEF@[..]"                   ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * FkEMail.checkEMailAdresse( "ABC.DEF@[.2.3.4]"               ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[]"                     ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1]"                    ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2]"                  ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3]"                ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4]"              ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4.5]"            ) = 56 = IP4-Adressteil: zu viele Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4.5.6]"          ) = 56 = IP4-Adressteil: zu viele Trennzeichen
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[MyDomain.de]"          ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.]"               ) = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3. ]"              ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4].de"           ) = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DE@[1.2.3.4][5.6.7.8]"      ) = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4"               ) = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   * FkEMail.checkEMailAdresse( "ABC.DEF@1.2.3.4]"               ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:2001:db8::1]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "ABC@[IP"                        ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC@[IPv6]"                     ) = 40 = IP6-Adressteil: String "IPv6:" erwartet
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:]"                    ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1]"                   ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2]"                 ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3]"               ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4]"             ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:]"          ) = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6]"         ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6:7]"       ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6:7:8]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6:7:8:9]"   ) = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   * 
   * FkEMail.checkEMailAdresse( "ABC@[IPv4:1:2:3:4]"             ) = 40 = IP6-Adressteil: String "IPv6:" erwartet
   * FkEMail.checkEMailAdresse( "ABC@[I127.0.0.1]"               ) = 40 = IP6-Adressteil: String "IPv6:" erwartet
   * FkEMail.checkEMailAdresse( "ABC@[D127.0.0.1]"               ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC@[iPv6:2001:db8::1]"         ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   *                                                                      Eine IPv6 Adresse muss mit einem grossem "I" eingeleitet werden
   *                                                                      Es wurde hier keine IPv6-Adresse erkannt, daher wird versucht eine IPv4-Adresse zu lesen.
   *  
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3::5:6:7:8]"      ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3::5::7:8]"       ) = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   * FkEMail.checkEMailAdresse( "ABC@[IPv6::ffff:.127.0.0.1]"    ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * FkEMail.checkEMailAdresse( "ABC@[IPv6::FFFF:127.0.0.1]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6::ffff:127.0.0.1]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6::fff:127.0.0.1]"      ) = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6::1234:127.0.0.1]"     ) = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:127.0.0.1]"           ) = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:::127.0.0.1]"         ) = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4]"              ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.Z]"              ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@[12.34]"                ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.]"               ) = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4] "             ) = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4"               ) = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1234.5.6.7]"           ) = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2...3.4]"            ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * 
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6]"         ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:Z]"         ) = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:12:34]"               ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:]"          ) = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6] "        ) = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6"          ) = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:12345:6:7:8:9]"       ) = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:::6:7:8]"       ) = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   * 
   * 
   * Kommentare -------------------------------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "(ABC)DEF@GHI.JKL"               ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "ABC(DEF)@GHI.JKL"               ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "AB(CD)EF@GHI.JKL"               ) = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   * FkEMail.checkEMailAdresse( "AB.(CD).EF@GHI.JKL"             ) = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   * FkEMail.checkEMailAdresse( "AB."(CD)".EF@GHI.JKL"           ) = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( "(ABCDEF)@GHI.JKL"               ) = 98 = Kommentar: Kein lokaler Part vorhanden
   * FkEMail.checkEMailAdresse( "(ABCDEF).@GHI.JKL"              ) = 30 = Trennzeichen: kein Beginn mit einem Punkt
   * FkEMail.checkEMailAdresse( "(AB\"C)DEF@GHI.JKL"             ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "(AB\\C)DEF@GHI.JKL"             ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "(AB\@C)DEF@GHI.JKL"             ) = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   * FkEMail.checkEMailAdresse( "ABC(DEF@GHI.JKL"                ) = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI)JKL"                ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( ")ABC.DEF@GHI.JKL"               ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "ABC.DEF@(GHI).JKL"              ) = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC(DEF@GHI).JKL"               ) = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   * FkEMail.checkEMailAdresse( "(ABC.DEF@GHI.JKL)"              ) = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   * FkEMail.checkEMailAdresse( "(A(B(C)DEF@GHI.JKL"             ) = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   * FkEMail.checkEMailAdresse( "(A)B)C)DEF@GHI.JKL"             ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "(A)BCDE(F)@GHI.JKL"             ) = 99 = Kommentar: kein zweiter Kommentar gueltig
   * 
   * Korrekte eMail-Adressen, welche nicht erkannt werden -------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "ABC DEF <ABC.DEF@GHJ.com>" ) = 29 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   *                                                          Vorgeschaltete While-Schleife um die eMail-Adresse in spitzen Klammern zu suchen
   *                                                          Hauptroutine so umstellen, dass es einen Index-Von und einen Index-Bis gibt.
   *                                                          Andere Zeichen ausserhalb der spitzen Klammern seperat pruefen                                                           
   * 
   * FkEMail.checkEMailAdresse( "ABC@localhost"             ) = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * 
   * nicht geklaert ---------------------------------------------------------------------------------------------------
   *
   * Kommentar am Ende der eMail-Adresse korrekt?
   * Darf eine eMail-Domain mit einer Zahl enden? 
   * 
   * IPv6-Adressangaben werden nicht 100%ig korrekt erkannt
   * - Overflow in den Angaben werden nicht erkannt
   * 
   * "ABC@[IPv6123::ffff:127.0.0.1]" - Praefix "IPv6" oder "IPv6:" (Ohne oder mit Doppelpunkt als Trennzeichen?)
   * 
   * FkEMail.checkEMailAdresse( "email@192.0.2.123" ) = IP4-Adressangabe ohne eckige Klammern gueltig ?
   * 
   * FkEMail.checkEMailAdresse( ""-- --- .. -."@sh.de" ) = Zeichenkombination ". oder ." im String korrekt ?
   *                                                       (sonst gibt es einen "... .... .. -"@storm.de)
   * 
   * FkEMail.checkEMailAdresse( "(A(B(C)DEF@GHI.JKL"  ) In einem Kommentar auch oeffnende Klammer '(' zulassen ?
   * 
   * ------------------------------------------------------------------------------------------------------------------
   * 
   * " + ( FkEMail.checkEMailAdresse( str_email_adresse ) < 10 ? "eMail-Adresse OK" : " eMail-Adresse ungueltig " ) + "
   *   
   * 
   * http://www.ex-parrot.com/~pdw/Mail-RFC822-Address.html
   * - It is impossible to match these restrictions with a single technique. 
   * - Using regular expressions results in long patterns giving incomplete results.
   * 
   * https://davidcel.is/posts/stop-validating-email-addresses-with-regex/
   * - Some people, when confronted with a problem, think, "I know, I'll use regular expressions." Now they have two problems.
   * 
   * https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
   * - There don't seem to be any perfect libraries or ways to do this yourself ...
   * - Another option is use the Hibernate email validator, using the annotation Email or using the validator class programatically
   * - I agree that the Apache Commons validator works well, but I find it to be quite slow - over 3ms per call. 
   * - After actually trying to build my project, it seems apache commons doesn't work with Android very well, hundreds of warnings and some errors, it didn't even compile.
   * - Same problem with me as of Benjiko99. After adding the dependency, the project wont compile, says java.exe finished with non zero exit code 2.
   * - You may also want to check for the length - emails are a maximum of 254 chars long. I use the apache commons validator and it doesn't check for this.
   * - But really what you want is a lexer that properly parses a string and breaks it up into the component structure according to the RFC grammar. 
   *   EmailValidator4J seems promising in that regard, but is still young and limited.
   *
   * https://www.regular-expressions.info/email.html
   *
   * https://de.wikipedia.org/wiki/Top-Level-Domain
   * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address?rq=1
   * https://tools.ietf.org/id/draft-ietf-behave-address-format-10.html
   * </pre>
   * 
   * @param pEingabe die auf eine eMail-Adresse zu pruefende Eingabe
   * @return wenn die Eingabe nach der Struktur her eine eMail-Adresse ergeben kann einen Wert kleiner 10, ansonsten einen der oben genannten Rueckgabewerte 
   */
  public static int checkEMailAdresse( String pEingabe )
  {
    /*
     * Grober Ablaufplan:
     * 
     * Laengenpruefungen
     * 
     * While-Schleife 1
     * 
     *    Zeichen A-Z, a-z und Zalen
     *    
     *    Sonderzeichen Domain-Part - _
     *    
     *    Zeichen .
     *    
     *    Zeichen @
     *    
     *    Zeichen \
     *    
     *    Sonderzeichen Local-Part
     * 
     *    Zeichen "
     *       
     *       While-Schleife 2 - String
     *       
     *          Zeichen A-Z, a-z, Zahlen und Sonderzeichen 
     *          
     *          Zeichen "
     *          
     *          Zeichen \
     *       
     *    Zeichen [
     *    
     *       Zeichen "IPv6:"
     *    
     *       While-Schleife 3 - IP-Adresse
     *          
     *          IPv6
     *          
     *              Zeichen A-F, a-f und Zahlen
     *              
     *              Zeichen .
     *              
     *              Zeichen :
     *              
     *              Zeichen ]
     *              
     *          IPv4
     *          
     *              Zahlen
     *          
     *              Zeichen .
     *          
     *              Zeichen ]
     *       
     *    Zeichen (
     *    
     *       While-Schleife 4 - Kommentare
     *          
     *          Zeichen A-Z, a-z, Zahlen und Sonderzeichen 
     *          
     *          Zeichen \
     *          
     *          Zeichen )
     * 
     * Abschlusspruefungen
     */

    /*
     * Keine Eingabe --> keine gueltige Adresse
     */
    if ( pEingabe == null )
    {
      return 10; // Laenge: Eingabe ist null
    }

    /*
     * Pruefung: Eingabelaenge gleich 0 ?
     * 
     * Es wird kein Trim gemacht, da ein Trim Zeit verbrauchen wuerde. 
     * Diese Funktion soll den String so pruefen, wie dieser uebergeben wurde.
     * Es ist nicht die Aufgabe dieser Funktion, die Eingabe zu manipulieren.
     * Besteht die Eingabe nur aus Leerzeichen, wird beim ersten Leerzeichen  
     * ein ungueltiges Zeichen erkannt und der Fehler 29 zurueckgegeben.
     * 
     * Ist die Eingabe ohne Trim schon ein Leerstring, wird der 
     * Fehler 11 zurueckgegeben.
     */
    if ( pEingabe.length() == 0 )
    {
      return 11; // Laenge: Eingabe ist Leerstring
    }

    int laenge_eingabe_string = pEingabe.length();

    /*
     * http://de.wikipedia.org/wiki/E-Mail-Adresse
     * Innerhalb von RFC 5322 gibt es keine Laengenbegrenzung fuer eMail-Adressen. 
     * 
     * Im RFC 5321 wird die maximale Laenge des Local-Parts mit 64 Bytes und
     * die maximale Laenge des Domainnamens mit 255 Bytes angegeben. Zusammen 
     * mit dem "@"-Zeichen ergaebe sich daraus die maximale Laenge einer 
     * E-Mail-Adresse von 320 Bytes. 
     * 
     * Im RFC 5321 wird auch die maximale Laenge des "Path"-Elements definiert, 
     * welches die Elemente "FROM" und "RCPT TO" im Envelope bestimmt und die 
     * maximale Laenge von Bytes einschliesslich der Separatoren "<" und ">" hat. 
     * Daraus ergibt sich eine maximale Laenge der E-Mail-Adresse von 254 Bytes 
     * einschliesslich des "@". Eine E-Mail mit einer laengeren Adresse, kann 
     * ueber RFC-konforme SMTP-Server weder verschickt noch empfangen werden.
     * 
     * Minimal moegliche eMail-Adresse ist "A@B.CD", gleich 6 Stellen.
     */
    if ( ( laenge_eingabe_string < 6 ) || ( laenge_eingabe_string > 254 ) )
    {
      return 12; // Laenge: Laengenbegrenzungen stimmen nicht 
    }

    /*
     * Variable "fkt_ergebnis_email_ok"
     * 
     * Speicherung des Funktionsergebnisses fuer korrekte eMail-Angaben. 
     * 
     * Ist die eMail-Adresse rein Text ohne Sonderformen, bleibt der Wert auf 0.
     * 
     * Ist im Local-Part ein String vorhanden, wird der Wert auf 1 geaendert.
     * 
     * Ist eine IP4-Adressangabe vorhanden, wird der Wert um 2 erhoeht.
     * 
     * Ist eine IP6-Adressangabe vorhanden, wird der Wert um 4 erhoeht.
     *
     * Ist ein Kommentar vorhanden, wird der Rueckgabewert konvertiert.
     * 
     * Es werden nur die unten stehenden 10 Ergebniswerte geliefert, 
     * damit korrekte eMail-Adressen unter dem Wert 10 bleiben.
     * 
     * Bezueglich der Erhoehung des Wertes bei einer Stringangabe ist es 
     * so, dass die IP-Adresse erst nach dem AT-Zeichen korrekt erkannt
     * wird. Eine korrekt gelesene String-Angabe wird immer vor einer 
     * moeglichen IP-Adresse gelesen werden und somit wird der Ergebnis-
     * wert auf 1 gestellt, bevor die Erhoehung bei der IP-Adresse 
     * gemacht werden kann.   
     * 
     * Folgende Ergebnisse sind moeglich:
     * 
     *  0 = eMail-Adresse korrekt
     *  1 = eMail-Adresse korrekt (Local Part mit String)
     *  2 = eMail-Adresse korrekt (IP4-Adresse)
     *  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
     *  4 = eMail-Adresse korrekt (IP6-Adresse)
     *  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
     *  6 = eMail-Adresse korrekt (Kommentar)
     *  7 = eMail-Adresse korrekt (Kommentar, String)
     *  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
     *  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
     *  
     * Durch die verschiedenen Rueckgabewerte, kann der Aufrufer eMail-Adressen 
     * mit IP-Adresse oder String-Teilen im Nachgang noch abweisen.  
     */
    int fkt_ergebnis_email_ok = 0;

    /*
     * Position AT-Zeichen
     * Initialwert -1 steht fuer "kein AT-Zeichen gefunden"
     */
    int position_at_zeichen = -1;

    /*
     * Merker "letzte Position eines Punktes"
     * Initialwert -1 steht fuer "keinen Punkt gefunden"
     */
    int position_letzter_punkt = -1;

    /*
     * Speichert die Position des zuletzt gefundenen Anfuehrungszeichens. 
     * Start- oder Endzeichen. 
     */
    int position_anf_zeichen_akt = -1;

    /*
     * Speichert die Position der letzten geschlossenen Klammer ')' eines gueltigen Kommentares. 
     */
    int position_kommentar_ende = -1;

    /*
     * Zaehler fuer Zeichen zwischen zwei Trennzeichen.
     * Die Trennzeichen sind Punkt und AT-Zeichen
     */
    int zeichen_zaehler = 0;

    /*
     * Start Leseposition
     * Die Startposition fuer die While-Schleife ist hier 0. Das ist
     * das erste Zeichen der Eingabe. 
     * 
     * Sollten einmal eMail-Adressen in spitzen Klammern validiert werden, liegt
     * die Startposition ein Zeichen nach der oeffnenden spitzen Klammer.
     */
    int akt_index = 0;

    char aktuelles_zeichen = ' ';

    /*
     * While-Schleife 1
     * 
     * In der aeusseren While-Schleife wird eine grundlegende eMail-Adressstruktur geparst. 
     * 
     * Es gibt drei innere While-Schleifen zum parsen von String-Bestandteilen, Kommentaren und IP-Adressen.
     * 
     * In den inneren While-Schleifen werden alle Bedingungen abgeprueft, welche dort zu pruefen sind.
     * 
     * Bedingungen aus einer inneren While-Schleifen, sollen (moeglichst) nicht auf die aeussere While-Schleife durchgreifen.
     */
    while ( akt_index < laenge_eingabe_string )
    {
      /*
       * Aktuelles Pruefzeichen
       * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
       */
      aktuelles_zeichen = pEingabe.charAt( akt_index );

      /*
       * Bedingungen Zeichen A-Z, a-z und Zahlen
       */
      if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) || ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
      {
        /*
         * Buchstaben ("A" bis "Z" und "a" bis "z") und Zahlen duerfen an jeder Stelle der eMail-Adresse vorkommen.
         * 
         * Ein solches Zeichen kann selber keinen Fehler produzieren.
         *   
         * Es wird der Zeichenzaehler um eins erhoeht.
         */
        zeichen_zaehler++;
      }
      else if ( ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) )
      {
        /*
         * Im Domain-Part duerfen die Sonderzeichen '_' und '-' nicht am Start stehen.  
         * 
         * Steht der Zeichenzaehler auf 0, steht das aktuelle Zeichen an einer ungueltigen Position.
         * 
         * Nach RFC 952 darf im Domain-Part kein Teilstring mit einer Zahl oder einem Punkt starten.
         * Nach RFC 1123 duerfen Hostnamen mit Zahlen starten.
         */

        if ( position_at_zeichen > 0 ) // kein Beginn mit einer Zahl oder Sonderzeichen im Domain-Part
        {
          if ( zeichen_zaehler == 0 )
          {
            return 20; // Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
          }

          if ( ( akt_index + 1 ) == laenge_eingabe_string )
          {
            return 24; // Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
          }
        }
      }
      /*
       * Bedingungen fuer einen Punkt
       */
      else if ( aktuelles_zeichen == '.' )
      {
        /*
         * Pruefung: Wurde schon ein Punkt gefunden?
         * 
         * Nein, wenn in der Speichervariablen "position_letzter_punkt" noch der Initialwert von -1 steht.
         */
        if ( position_letzter_punkt == -1 )
        {
          /*
           * Pruefung: Leseposition gleich 0 ?
           * 
           * Ist der aktuelle Index gleich 0 und das aktuelle Zeichen ein Punkt,
           * wird der Fehler 30 zurueckgegeben. 
           * 
           * Es darf nicht mit dem Zeichenzaehler geprueft werden, da der erste 
           * Local-Part auch ein String sein kann. Dort wird der Zeichenzaehler 
           * nicht erhoeht.
           */
          if ( akt_index == ( position_kommentar_ende + 1 ) )
          {
            return 30; // Trennzeichen: kein Beginn mit einem Punkt
          }
        }
        else
        {
          /*
           * Pruefung: Zwei Punkte hintereinander ?
           * 
           * Ist die Differenz von der aktuellen Leseposition und der letzten 
           * Punkt-Position gleich 1, stehen 2 Punkte hintereinander. Es wird 
           * in diesem Fall der Fehler 31 zurueckgegeben.
           */
          if ( ( akt_index - position_letzter_punkt ) == 1 )
          {
            return 31; // Trennzeichen: keine zwei Punkte hintereinander
          }
        }

        /*
         * Index des letzten Punktes speichern
         */
        position_letzter_punkt = akt_index;

        /*
         * Zeichen- und Zahlenzaehler nach einem Punkt auf 0 stellen
         */
        zeichen_zaehler = 0;
      }
      /*
       * Bedingungen fuer das AT-Zeichen
       */
      else if ( aktuelles_zeichen == '@' )
      {
        /*
         * Pruefung: Position AT-Zeichen ungleich -1 ?
         * 
         * Wurde bereits ein AT-Zeichen gefunden, ist in der Positionsvariablen 
         * ein Wert groesser 0 vorhanden. Es darf im Leseprozess nur einmal 
         * ein (unmaskiertes) AT-Zeichen als Trennerzeichen gefunden werden. 
         * Ist schon eine AT-Zeichenposition vorhanden, wird der Fehler
         * 29 zurueckgegeben. 
         * 
         * Wurde noch kein AT-Zeichen gefunden, werden weitere Pruefungen gemacht. 
         */
        if ( position_at_zeichen != -1 )
        {
          return 29; // AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
        }

        if ( akt_index == 0 )
        {
          return 26; // AT-Zeichen: kein AT-Zeichen am Anfang
        }

        if ( akt_index > 64 )
        {
          return 13; // Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
        }

        if ( ( akt_index + 1 ) == laenge_eingabe_string )
        {
          return 27; // AT-Zeichen: kein AT-Zeichen am Ende
        }

        if ( akt_index - position_letzter_punkt == 1 )
        {
          return 32; // Trennzeichen: ungueltige Zeichenkombination ".@"
        }

        /*
         * Kombination "@."
         * An dieser Position ist sichergestellt, dass in der Eingabe noch 
         * mindestens 1 Zeichen folgt. Es gibt hier keine Index-Out-Of-Bounds Exception.
         * 
         * Ansonsten wuerde das AT-Zeichen am Ende stehen und der Aufrufer 
         * wuerde 8 als Funktionsergebnis bekommen.
         */
        if ( pEingabe.charAt( akt_index + 1 ) == '.' )
        {
          return 33; // Trennzeichen: ungueltige Zeichenkombination "@."
        }

        /*
         * Position des AT-Zeichens merken
         */
        position_at_zeichen = akt_index;

        /*
         * Zeichenzaehler nach dem AT-Zeichen auf 0 stellen
         */
        zeichen_zaehler = 0;
      }
      else if ( aktuelles_zeichen == '\\' )
      {
        /*
         * Sonderzeichen mit Qoutierung, welche nur im Local-Part vorkommen duerfen
         * 
         * \ @
         * 
         * Ist die Positon fuer das AT-Zeichen groesser als 0, befindet sich 
         * der Leseprozess im Domain-Part der eMail-Adresse. Dort sind diese 
         * Zeichen nicht erlaubt und es wird 21 zurueckgegeben.
         */
        if ( position_at_zeichen > 0 )
        {
          return 21; // Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
        }

        /*
         * Maskiertes Zeichen 
         * Der Leseprozess muss noch das naechste Zeichen pruefen. 
         * Der Leseprozessindex wird um ein Zeichen weiter gestellt.
         */
        akt_index++;

        /*
         * Pruefung: Stringende ?
         */
        if ( akt_index == laenge_eingabe_string )
        {
          return 83; // String: Escape-Zeichen nicht am Ende der Eingabe
        }

        /*
         * Zeichen nach dem Backslash lesen. 
         * Das Zeichen darf ein Backslash oder ein Anfuehrungszeichen sein. 
         * Alle anderen Zeichen fuehren zum Fehler 84.
         */
        aktuelles_zeichen = pEingabe.charAt( akt_index );

        if ( ( aktuelles_zeichen != '\\' ) && ( aktuelles_zeichen != '@' ) && ( aktuelles_zeichen != ' ' ) )
        {
          return 84; // String: Ungueltige Escape-Sequenz im String
        }
      }
      else if ( ( aktuelles_zeichen == '!' ) || ( aktuelles_zeichen == '#' ) || ( aktuelles_zeichen == '$' ) || ( aktuelles_zeichen == '%' ) || ( aktuelles_zeichen == '&' ) || ( aktuelles_zeichen == '\'' ) || ( aktuelles_zeichen == '*' ) || ( aktuelles_zeichen == '+' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '/' ) || ( aktuelles_zeichen == '=' ) || ( aktuelles_zeichen == '?' ) || ( aktuelles_zeichen == '^' ) || ( aktuelles_zeichen == '`' ) || ( aktuelles_zeichen == '{' ) || ( aktuelles_zeichen == '|' ) || ( aktuelles_zeichen == '}' ) || ( aktuelles_zeichen == '~' ) )
      {
        /*
         *   asc("!") = 033   asc("*") = 042 
         *   asc("#") = 035   asc("+") = 043 
         *   asc("$") = 036   asc("-") = 045 
         *   asc("%") = 037   asc("/") = 047
         *   asc("&") = 038 
         *   asc("'") = 039
         *   
         *   asc("=") = 061   asc("{") = 123 
         *   asc("?") = 063   asc("|") = 124 
         *   asc("^") = 094   asc("}") = 125 
         *   asc("_") = 095   asc("~") = 126 
         *   asc("`") = 096   asc(" ") = 032 
         */

        /*
         * Sonderzeichen, welche nur im Local-Part vorkommen duerfen
         * 
         * !#$%&'*+-/=?^_`{|}~
         * 
         * Ist die Positon fuer das AT-Zeichen groesser als 0, befindet sich 
         * der Leseprozess im Domain-Part der eMail-Adresse. Dort sind diese 
         * Zeichen nicht erlaubt und es wird 21 zurueckgegeben.
         */
        if ( position_at_zeichen > 0 )
        {
          return 21; // Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
        }
      }
      else if ( aktuelles_zeichen == '"' )
      {
        /*
         * While-Schleife 2 - String
         */

        /*
         * Pruefung: Leseprozess im Domain-Part ?
         * Im Domain-Part sind keine Anfuehrungszeichen erlaubt. 
         * Es wird der Fehler 82 zurueckgegeben.
         */
        if ( position_at_zeichen > 0 )
        {
          return 82; // String: kein Anfuehrungszeichen nach dem AT-Zeichen
        }

        /*
         * Pruefung: Zeichenkombination ."
         * 
         * Ein lokaler Part in Anfuehrungszeichen muss nach einem Trennzeichen starten.
         * 
         * Wurde schon ein Punkt gefunden und die Differenz des Leseprozesses 
         * ist nicht 1, wird der Fehler 81 zurueckgegeben.
         */
        if ( position_letzter_punkt > 0 )
        {
          if ( akt_index - position_letzter_punkt != 1 )
          {
            return 81; // String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
          }
        }

        /*
         * Pruefung: Start mit Anfuehrungszeichen 
         * Sind noch keine Zeichen gelesen worden, wird der Fehler 80 zurueckgegeben.
         */
        if ( zeichen_zaehler > 0 )
        {
          return 80; // String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
        }

        /*
         * Das aktuelle Zeichen ist hier noch ein Anfuehrungszeichen. 
         * Die nachfolgende While-Schleife wuerde nicht starten, wenn 
         * das Zeichen nicht geaendert wuerde. Darum wird der Variablen 
         * "aktuelles_zeichen" das Zeichen 'A' zugewiesen.
         */
        aktuelles_zeichen = 'A';

        /*
         * Position des aktuellen Anfuehrungszeichens merken
         */
        position_anf_zeichen_akt = akt_index;

        /*
         * Der Leseprozess wird um ein Zeichen weitergestellt. 
         * Damit wird das einleitende Anfuehrungszeichen vom Parser verarbeitet.
         * 
         * Steht das einleitende Anfuehrungszeichen am Stringende, wird die 
         * While-Schleife nicht ausgefuehrt, da die Variable "akt_index" jetzt 
         * gleich der Laenge des Eingabestrings ist. Die Differenz zur 
         * gespeicherten Position des einleitenden Anfuehrungszeichens ist 1. 
         * Das ist die erste Pruefung nach der While-Schleife. Es werden 
         * somit Eingaben wie:
         * 
         *         ABC.DEF."
         * 
         * erkannt. Der Aufrufer bekommt in diesem Fall den Fehler 85 zurueck.
         */
        akt_index++;

        /*
         * Innere While-Schleife zum Einlesen des Strings.
         * Laeuft bis zum Stringende oder bis zum naechsten - nicht maskierten - Anfuehrungszeichen.
         */
        while ( ( akt_index < laenge_eingabe_string ) && ( aktuelles_zeichen != '"' ) )
        {
          /*
           * Aktuelles Pruefzeichen
           * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
           */
          aktuelles_zeichen = pEingabe.charAt( akt_index );

          /*
           * Alle erlaubten Stringzeichen werden durchgelassen. 
           * Falsche Zeichen erzeugen einen Fehler.
           */

          if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) )
          {
            // OK - Buchstaben
          }
          else if ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) )
          {
            // OK - Zahlen
          }
          else if ( ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '@' ) || ( aktuelles_zeichen == '.' ) || ( aktuelles_zeichen == ' ' ) || ( aktuelles_zeichen == '!' ) || ( aktuelles_zeichen == '#' ) || ( aktuelles_zeichen == '$' ) || ( aktuelles_zeichen == '%' ) || ( aktuelles_zeichen == '&' ) || ( aktuelles_zeichen == '\'' ) || ( aktuelles_zeichen == '*' ) || ( aktuelles_zeichen == '+' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '/' ) || ( aktuelles_zeichen == '=' ) || ( aktuelles_zeichen == '?' ) || ( aktuelles_zeichen == '^' ) || ( aktuelles_zeichen == '`' ) || ( aktuelles_zeichen == '{' ) || ( aktuelles_zeichen == '|' ) || ( aktuelles_zeichen == '}' ) || ( aktuelles_zeichen == '~' ) )
          {
            // OK - Sonderzeichen
          }
          else if ( aktuelles_zeichen == '"' )
          {
            // OK - abschliessendes Anfuehrungszeichen 

            /*
             * Der Leseprozessindex muss nach der While-Schleife auf dem jetzt
             * gerade gueltigen Index stehen. 
             * 
             * Da am Ende dieser inneren While-Schleife der Leseprozess um 
             * eine Position weiter gestellt wird, muss der Index hier um 
             * eine Position verringert werden.
             * 
             * Die aeussere While-Schleife erhoeht den Leseprozessindex nochmals. 
             */
            akt_index--;
          }
          else if ( aktuelles_zeichen == '\\' )
          {
            /*
             * Maskiertes Zeichen 
             * Der Leseprozess muss nach naechste Zeichen pruefen. 
             * Der Leseprozessindex wird um ein Zeichen weiter gestellt.
             */
            akt_index++;

            /*
             * Pruefung: Stringende ?
             */
            if ( akt_index == laenge_eingabe_string )
            {
              return 83; // String: Escape-Zeichen nicht am Ende der Eingabe
            }

            /*
             * Zeichen nach dem Backslash lesen. 
             * Das Zeichen darf ein Backslash oder ein Anfuehrungszeichen sein. 
             * Alle anderen Zeichen fuehren zum Fehler 84.
             */
            aktuelles_zeichen = pEingabe.charAt( akt_index );

            if ( ( aktuelles_zeichen != '\\' ) && ( aktuelles_zeichen != '"' ) )
            {
              return 84; // String: Ungueltige Escape-Sequenz im String
            }

            /*
             * Vermeidung dass die While-Schleife fruehzeitig beendet wird. 
             * Im Falle dass ein Anfuehrungszeichen gefunden wurde, wuerde 
             * die While-Schleife zum String einlesen beendet werden. 
             */
            aktuelles_zeichen = 'A';
          }
          else
          {
            /*
             * Fehler 89
             * Dieser Fehler wird zurueckgegeben, wenn ein ungueltiges 
             * Zeichen im String vorhanden ist.
             */
            return 89; // String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
          }

          /*
           * Der Leseprozessindex wird um eine Position weitergestellt.
           */
          akt_index++;
        }

        /*
         * Leerstring 
         * Wird ein abschliessendes Leerzeichen gefunden, darf die Differenz zur
         * letzten Position eines Anfuehrungszeichens nicht 1 sein.
         */
        if ( ( akt_index - position_anf_zeichen_akt ) == 1 )
        {
          return 85; // String: Leerstring in Anfuehrungszeichen "", oder Start mit Leerzeichen am Ende der Eingabe
        }

        /*
         * Pruefung: abschliessendes Anfuehrungszeichen vorhanden ?
         * 
         * Nach dem Ende der While-Schleife, muss die Variable "aktuelles_zeichen" ein 
         * Anfuehrungszeichen beinhalten. 
         * 
         * Wurde kein abschliessendes Anfuehrungszeichen gefunden? 
         */
        if ( aktuelles_zeichen != '"' )
        {
          return 86; // String: kein abschliessendes Anfuehrungszeichen gefunden.
        }

        /*
         * Pruefung: Leseprozess am Stringende angelangt ? 
         */
        if ( akt_index + 1 >= laenge_eingabe_string )
        {
          return 88; // String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
        }

        /*
         * Gueltige Abschlusszeichenkombinationen pruefen
         */
        if ( pEingabe.charAt( akt_index + 1 ) == '.' )
        {
          // gueltige Zeichenkombination ".
        }
        else if ( pEingabe.charAt( akt_index + 1 ) == '@' )
        {
          // gueltige Zeichenkombination "@
        }
        else if ( pEingabe.charAt( akt_index + 1 ) == '(' )
        {
          // gueltige Zeichenkombination "(
        }
        else
        {
          return 87; // String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
        }

        /*
         * Position des letzten Anfuehrungszeichens merken
         */
        position_anf_zeichen_akt = akt_index;

        /*
         * Es wurde ein String im Local-Part der eMail-Adresse erkannt. 
         * Die Variable "fkt_ergebnis_email_ok" wird auf 1 gesetzt.
         */
        fkt_ergebnis_email_ok = 1;
      }
      else if ( aktuelles_zeichen == '[' )
      {
        /*
         * While-Schleife 3 - IP-Adresse
         */

        /*
         * Pruefung: AT-Zeichen noch nicht vorgekommen ?
         * 
         * Die IP-Adresse muss sich im Domain-Teil der eMail-Adresse befinden. 
         * Wurde noch kein AT-Zeichen gefunden, befindet sich der Leseprozess 
         * noch nicht im Domain-Teil. Es wird 51 als Fehler zurueckgegeben. 
         */
        if ( position_at_zeichen == -1 )
        {
          return 51; // IP-Adressteil: IP-Adresse vor AT-Zeichen
        }

        /*
         * Pruefung: Startzeichen direkt nach AT-Zeichen ?
         * 
         * Das Startzeichen "[" muss direkt nach dem AT-Zeichen kommen. 
         * Die aktuelle Leseposition muss genau 1 Zeichen nach der Position  
         * des AT-Zeichens liegen. Ist das nicht der Fall, wird 
         * 52 als Fehler zurueckgegeben.
         */
        if ( ( akt_index - position_at_zeichen ) != 1 )
        {
          return 52; // IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
        }

        /*
         * Kennzeichenfeld IPv6
         * 
         * Ist keine IPv6-Adresse vorhanden, ist der Wert 0.
         * 
         * Ist eine normale IPv6-Adresse vorhanden, ist der Wert 1.
         * 
         * Ist eine IPv6-Adresse mit einer IPv4-Adressangabe vorhanden, ist der Wert 2.
         */
        int knz_ipv6 = 0;

        int ip_adresse_zaehler_trennzeichen = 0;

        int ip_adresse_anzahl_trennzeichen_hintereinander = 0;

        /*
         * Pruefung: IP-V6 Adressangabe ?
         * 
         * Es wird auf vorhandensein des Strings "IPv6:" ab der aktuellen Leseposition geprueft.
         * 
         * Ist der String vorhanden, wird eine IP-V6 Adresse gelesen. 
         * Ist der String nicht vorhanden, wird eine IP-4 Adresse gelesen.
         * 
         * Die Pruefung wird nur gemacht, wenn ab der aktuellen Position noch mindestens 5 Stellen vorhanden sind.
         * 
         *      1234567890
         * ABC@[IPv6:1:2::3]
         * ABC@[123.456.789.012]
         */
        if ( akt_index + 5 < laenge_eingabe_string )
        {
          /*
           * Ist das naechste Zeichen ein "I", werden die darauffolgenden Zeichen auf 
           * die Zeichen "P", "v", "6" und ":" geprueft. 
           * 
           * Sind diese Zeichen vorhanden, wird eine IPv6-Adresse gelesen.
           * 
           * Sind diese Zeichen nicht vorhanden, ist es ein Fehler, da das einleitende Zeichen "I" falsch ist.
           * In diesem Fall wird der Fehler 40 zurueckgegeben.
           */
          if ( pEingabe.charAt( akt_index + 1 ) == 'I' )
          {
            if ( ( pEingabe.charAt( akt_index + 2 ) == 'P' ) && ( pEingabe.charAt( akt_index + 3 ) == 'v' ) && ( pEingabe.charAt( akt_index + 4 ) == '6' ) && ( pEingabe.charAt( akt_index + 5 ) == ':' ) )
            {
              /*
               * Der Index des Leseprozesses wird um 5 erhoeht. 
               * Am schleifenende wird der Prozess nochmals um eine Position weitergestellt.
               * 
               * 01234567890123456789
               * ABC@[IPv6::ffff:127.0.0.1]" );
               */
              akt_index += 5;

              /*
               * Der Leseprozess befindet sich in einer IPv6 Adresse.
               * Die Kennzeichenvariable wird auf 1 gestellt.
               */
              knz_ipv6 = 1;

              /*
               * Es wurde 1 Trennzeichen gelesen.
               * (Trennzeichen zaehlt?)
               */
              ip_adresse_zaehler_trennzeichen++;
            }
            else
            {
              return 40; // IP6-Adressteil: String "IPv6:" erwartet
            }
          }
        }

        /*
         * https://de.wikipedia.org/wiki/IPv6#Adressaufbau_von_IPv6
         * 
         * Die textuelle Notation von IPv6-Adressen ist in Abschnitt 2.2 von RFC 4291 beschrieben:
         * 
         * IPv6-Adressen werden fuer gewoehnlich hexadezimal (IPv4: dezimal) notiert, 
         * wobei die Zahl in acht Bloecke zu jeweils 16 Bit (4 Hexadezimalstellen) unterteilt wird.
         *  
         * Diese Bloecke werden durch Doppelpunkte (IPv4: Punkte) getrennt 
         * notiert: 2001:0db8:85a3:08d3:1319:8a2e:0370:7344.
         * 
         * Fuehrende Nullen innerhalb eines Blockes duerfen ausgelassen werden: 
         * 2001:0db8:0000:08d3:0000:8a2e:0070:7344 ist gleichbedeutend mit 2001:db8:0:8d3:0:8a2e:70:7344.
         * 
         * Ein oder mehrere aufeinander folgende Bloecke, deren Wert 0 (bzw. 0000) betraegt, duerfen ausgelassen werden. 
         * Dies wird durch zwei aufeinander folgende Doppelpunkte angezeigt: 2001:0db8:0:0:0:0:1428:57ab ist gleichbedeutend mit 2001:db8::1428:57ab.[15]
         * 
         * Die Reduktion durch Regel 3 darf nur einmal durchgefuehrt werden.
         * 
         * Es darf hoechstens eine zusammenhaengende Gruppe aus Null-Bloecken in der Adresse ersetzt werden.
         *  
         * Die Adresse 2001:0db8:0:0:8d3:0:0:0 darf demnach entweder zu 2001:db8:0:0:8d3:: oder 2001:db8::8d3:0:0:0 gekuerzt werden.
         * 2001:db8::8d3:: ist unzulaessig, da dies mehrdeutig ist und faelschlicherweise z.B. auch als 2001:db8:0:0:0:8d3:0:0 interpretiert werden koennte.
         *  
         * Die Reduktion darf auch dann nicht mehrfach durchgefuehrt werden, wenn das Ergebnis eindeutig waere. 
         * 
         * Ebenfalls darf fuer die letzten beiden Bloecke (vier Bytes, also 32 Bits) der Adresse die herkoemmliche dezimale Notation mit Punkten als Trennzeichen verwendet werden. 
         * So ist ::ffff:127.0.0.1 eine alternative Schreibweise fuer ::ffff:7f00:1. Diese Schreibweise wird vor allem bei Einbettung des IPv4-Adressraums in den IPv6-Adressraum verwendet.
         */

        /*
         * Innere While-Schleife
         * In einer inneren While-Schleife wird die IP-Adresse geparst und 
         * auf Gueltigkeit geprueft. 
         * 
         * Die innere While-Schleife laeuft bis zum Ende des Eingabestrings.
         */
        int ip_adresse_akt_zahl = 0;

        int ip_adresse_zahlen_zaehler = 0;

        akt_index++;

        while ( akt_index < laenge_eingabe_string )
        {
          /*
           * Aktuelles Pruefzeichen
           * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
           */
          aktuelles_zeichen = pEingabe.charAt( akt_index );

          if ( knz_ipv6 == 1 )
          {
            if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'f' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'F' ) ) || ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
            {
              /*
               * Zahlenzaehler
               * Der Zahlenzaehler wird erhoeht.
               * Anschliessend wird geprueft, ob schon mehr als 4 Zeichen gelsen wurden. 
               * Ist das der Fall, wird 46 zurueckgegeben. 
               */
              ip_adresse_zahlen_zaehler++;

              if ( ip_adresse_zahlen_zaehler > 4 )
              {
                return 46; // IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
              }
            }
            else if ( aktuelles_zeichen == '.' )
            {
              /*
               * IPv4 Adressangabe
               * Wird innerhalb der IPv6-Adresse eine IPv4-Adresse angegeben, wird 
               * dieses durch einen Punkt erkannt. 
               * 
               * Der Leseprozess wird auf das letzte Trennzeichen gesetzt.
               * Die IPv4-Adresse wird vom IP4-Adressparser geprueft.
               * Die erste Angabeder IPv4-Adresse wird demnach doppelt gelesen. 
               */

              /*
               * Ist eine IP4-Adresse eingebettet worden, muessen Trennzeichen 
               * der IPv6-Adresse gefunden worden sein.  
               * 
               * (Nach dem aktuellem Verstaendnis von IP6-Adressen, muessen es 3 sein)
               */
              if ( ip_adresse_zaehler_trennzeichen != 3 )
              {
                return 47; // IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch 
              }

              /*
               * Der Zahlenzaehler muss bei einem Ruecksprung in die IP4-Routine
               * kleiner als 4 sein, da die erste Zahl der IP4-Adresse hier 
               * schon gelesen worden ist. 
               */
              if ( ip_adresse_zahlen_zaehler > 3 )
              {
                return 48; // IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
              }

              /*
               * Der Leseprozess wird auf die letzte Trennzeichenposition gestellt.  
               * Am Schleifenende wird der Leseprozess nochmals um eine Position 
               * weitergestellt, welches dann die korrekte Position des ersten 
               * Zeichens der IP4-Adresse ergibt.
               */
              akt_index = position_letzter_punkt; // +1 am Schleifenende

              /*
               * Sicherstellung, dass die Einbettung der IP4-Adresse mit "ffff" startet.
               * Es ist hier sichergestellt, dass mindestens 5 Zeichen vor dem Trennzeichen vorhanden sind.
               * Es muessen 5 Zeichen mindestens vorhanden sein, damit eine IP6-Adresse geparst wird.
               */
              if ( ( pEingabe.charAt( akt_index - 1 ) == 'f' ) && ( pEingabe.charAt( akt_index - 2 ) == 'f' ) && ( pEingabe.charAt( akt_index - 3 ) == 'f' ) && ( pEingabe.charAt( akt_index - 4 ) == 'f' ) )
              {
                // OK - ffff gefunden
              }
              else
              {
                if ( ( pEingabe.charAt( akt_index - 1 ) == 'F' ) && ( pEingabe.charAt( akt_index - 2 ) == 'F' ) && ( pEingabe.charAt( akt_index - 3 ) == 'F' ) && ( pEingabe.charAt( akt_index - 4 ) == 'F' ) )
                {
                  // OK - FFFF gefunden
                }
                else
                {
                  return 62; // IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
                }
              }

              /*
               * Der Zahlenzaehler wird auf 0 gestellt, da nun eine IP4-Adresse gelesen wird
               * und der Zahlenzaehler global in dieser While-Schleife benutzt wird.
               */
              ip_adresse_zahlen_zaehler = 0;

              /*
               * Der Trennzeichenzaehler wird auf 0 gestellt.
               */
              ip_adresse_zaehler_trennzeichen = 0;

              /*
               * Die Kennzeichenvariable wird auf 2 gestellt, damit der Leseprozess 
               * in die IP4-Leseroutine verzweigt.
               */
              knz_ipv6 = 2;
            }
            else if ( aktuelles_zeichen == ':' )
            {
              /*
               * Doppel-Punkt (Trennzeichen IP-Adressangaben)
               */

              /*
               * Anzahl Trennzeichen
               * Es gibt maximal 8 Bloecke. 
               * Das ergibt eine maximale Anzahl von 7 Trennzeichen.
               * 
               * Es duerfen nicht mehr als 7 Trennzeichen gelesen werden. 
               * Beim 8ten Trennzeichen, wird der Fehler 42 zurueckgegeben.
               */
              ip_adresse_zaehler_trennzeichen++;

              if ( ip_adresse_zaehler_trennzeichen > 8 )
              {
                return 42; // IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
              }

              /*
               * Es duerfen einmal 2 Doppelpunkte hintereinander kommen.
               */

              /*
               * Pruefung: 2 Doppelpunkte hintereinander ?
               * Liegt die letzte Position eines Trennzeichenst vor der aktuellen 
               * Lesepostion, wird der Zaehler fuer aufeinanderfolgende Trennzeichen 
               * um 1 erhoeht. Dieser Zaehler wird anschliessend auf den Wert 2 
               * geprueft. Ist der Wert 2, wird der Fehler 50 zurueckgegeben. 
               */
              if ( ( akt_index - position_letzter_punkt ) == 1 )
              {
                ip_adresse_anzahl_trennzeichen_hintereinander++;

                if ( ip_adresse_anzahl_trennzeichen_hintereinander == 2 )
                {
                  return 50; // IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
                }
              }

              /*
               * Sind alle Pruefungen fuer einen Punkt durchgefuehrt worden, 
               * wird die Position des letzten Punktes aktualisiert. 
               * 
               * Es wird der Zahlenzaehler und der Wert der aktuellen Zahl auf 0 gestellt.
               */
              position_letzter_punkt = akt_index;

              ip_adresse_zahlen_zaehler = 0;

              ip_adresse_akt_zahl = 0;
            }
            else if ( aktuelles_zeichen == ']' )
            {
              /*
               * Abschlusszeichen "]"
               */

              if ( ip_adresse_zaehler_trennzeichen == 0 )
              {
                return 41; // IP6-Adressteil: Trennzeichenanzahl ist 0 
              }

              /*
               * Anzahl Trennzeichen
               * Fuer eine IP6-Adresse muessen mindestens 3 Trennzeichen gelesen worden sein. 
               * Ist das nicht der Fall, wird der Fehler 43 zurueckgegeben.
               */
              if ( ip_adresse_zaehler_trennzeichen < 3 )
              {
                return 43; // IP6-Adressteil: Zu wenig Trennzeichen  
              }

              /*
               * Der letzte Punkt darf nicht auf der vorhergehenden Position 
               * liegen. Ist das der Fall, wird der Fehler 44 zurueckgegeben. 
               */
              if ( ( akt_index - position_letzter_punkt ) == 1 )
              {
                return 44; // IP6-Adressteil: ungueltige Kombination ":]"
              }

              /*
               * Das Abschlusszeichen muss auf der letzten Stelle des
               * Eingabestrings liegen. Ist das nicht der Fall, wird 
               * 45 als Fehler zurueckgegeben.
               */
              if ( ( akt_index + 1 ) != laenge_eingabe_string )
              {
                return 45; // IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
              }

              /*
               * Eine Pruefung auf den Zahlenzaehler bringt nichts. 
               * Der Zahlenzaehler ist im eventuellen Fehlerfall hier 0. 
               * Wuerde eine Zahl gelesen, wuerde ein Fehler bei den  
               * Zahlen geprueft werden.
               * 
               * Wuerde versucht werden, den Punkt und das Abschlusszeichen 
               * mit anderen Zeichen zu trennen, wuerde ein ungueltiges 
               * Zeichen erkannt werden. 
               */
            }
            else
            {
              return 49; // IP6-Adressteil: Falsches Zeichen in der IP-Adresse
            }
          }
          else
          {
            /*
             * Die IP-Adresse besteht nur aus Zahlen und Punkten mit einem 
             * abschliessendem "]"-Zeichen. Alle anderen Zeichen fuehren 
             * zu dem Fehler 59 = ungueltiges Zeichen.
             * 
             * Es wird nur eine IP4 Adresse geprueft.
             */
            if ( ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
            {
              /*
               * Zahlen
               * - nicht mehr als 3 Ziffern
               * - nicht groesser als 255 (= 1 Byte)
               */

              /*
               * Zahlenzaehler
               * Der Zahlenzaehler wird erhoeht.
               * Anschliessend wird geprueft, ob schon mehr als 3 Zahlen gelsen wurden. 
               * Ist das der Fall, wird 53 zurueckgegeben. 
               */
              ip_adresse_zahlen_zaehler++;

              if ( ip_adresse_zahlen_zaehler > 3 )
              {
                return 53; // IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
              }

              /*
               * Berechnung Akt-Zahl
               * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
               * der Wert des aktullen Zeichens hinzugezaehlt. 
               * 
               * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48.
               * 
               * Anschliessend wird geprueft, ob die Zahl groesser als 255 ist. 
               * Ist die Zahl groesser, wird 54 zurueckgegeben. (Byteoverflow)
               */
              ip_adresse_akt_zahl = ( ip_adresse_akt_zahl * 10 ) + ( ( (int) aktuelles_zeichen ) - 48 );

              if ( ip_adresse_akt_zahl > 255 )
              {
                return 54; // IP4-Adressteil: Byte-Overflow
              }
            }
            else if ( aktuelles_zeichen == '.' )
            {
              /*
               * Punkt (Trennzeichen Zahlen)
               */

              /*
               * Pruefung: Zahlen vorhanden ?
               * 
               * Steht der Zahlenzaehler auf 0, wurden keine Zahlen gelesen. 
               * In diesem Fall wird 55 zurueckgegeben.
               */
              if ( ip_adresse_zahlen_zaehler == 0 )
              {
                return 55; // IP4-Adressteil: keine Ziffern vorhanden
              }

              /*
               * ANMERKUNG FEHLER 63 
               * Dieser Fehlercode kann nicht kommen, da bei 2 hintereinanderkommenden 
               * Punkten keine Zahl gelesen worden sein kann. Dieses ist die  
               * vorhergehende Pruefung.
               * 
               * Es kann auch kein anderes Zeichen gelesen worden sein, das 
               * wuerde einen anderen Fehler verursachen.
               */
              // 
              // /*
              //  * Pruefung: 2 Punkte hintereinander ?
              //  * Die letzte Position eines Punktes, darf nicht vor der aktuellen 
              //  * Lesepostion liegen. Ist das der Fall, wird 63 zurueckgegeben.
              //  */
              // if ( ( akt_index - position_letzter_punkt ) == 1 )
              // {
              //   return 63; // IP4-Adressteil: keine 2 Punkte hintereinander
              // }

              /*
               * Anzahl Trennzeichen
               * Es duerfen nicht mehr als 3 Punkte (=Trennzeichen) gelesen werden. 
               * Beim 4ten Punkt, wird 56 zurueckgegeben.
               */
              ip_adresse_zaehler_trennzeichen++;

              if ( ip_adresse_zaehler_trennzeichen > 3 )
              {
                return 56; // IP4-Adressteil: zu viele Trennzeichen
              }

              /*
               * Sind alle Pruefungen fuer einen Punkt durchgefuehrt worden, 
               * wird die Position des letzten Punktes aktualisiert. 
               * 
               * Es wird der Zahlenzaehler und der Wert der aktuellen Zahl auf 0 gestellt.
               */
              position_letzter_punkt = akt_index;

              ip_adresse_zahlen_zaehler = 0;

              ip_adresse_akt_zahl = 0;
            }
            else if ( aktuelles_zeichen == ']' )
            {
              /*
               * Abschlusszeichen "]"
               */

              /*
               * Anzahl Trennzeichen
               * Fuer eine IP4-Adresse muessen 3 Punkte gelesen worden sein. 
               * Ist das nicht der Fall, wird 57 zurueckgegeben.
               */
              if ( ip_adresse_zaehler_trennzeichen != 3 )
              {
                return 57; // IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein 
              }

              /*
               * Der letzte Punkt darf nicht auf der vorhergehenden Position 
               * liegen. Ist das der Fall, wird 58 zurueckgegeben. 
               */
              if ( ( akt_index - position_letzter_punkt ) == 1 )
              {
                return 58; // IP4-Adressteil: ungueltige Kombination ".]"
              }

              /*
               * Das Abschlusszeichen muss auf der letzten Stelle des
               * Eingabestrings liegen. Ist das nicht der Fall, wird 
               * 60 als Fehler zurueckgegeben.
               */
              if ( ( akt_index + 1 ) != laenge_eingabe_string )
              {
                return 60; // IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
              }

              /*
               * Eine Pruefung auf den Zahlenzaehler bringt nichts. 
               * Der Zahlenzaehler ist im eventuellen Fehlerfall hier 0. 
               * Wuerde eine Zahl gelesen, wuerde ein Fehler bei den  
               * Zahlen geprueft werden.
               * 
               * Wuerde versucht werden, den Punkt und das Abschlusszeichen 
               * mit anderen Zeichen zu trennen, wuerde ein ungueltiges 
               * Zeichen erkannt werden. 
               */
            }
            else
            {
              return 59; // IP4-Adressteil: Falsches Zeichen in der IP-Adresse
            }
          }

          akt_index++;
        }

        /*
         * Pruefung: Abschluss mit ']' ?
         * 
         * Ist die IP-Adressangabe korrekt, steht nach der While-Schleife das abschliessende 
         * Zeichen ']' in der Variablen "aktuelles_zeichen". Ist es ein anderes Zeichen, wird 
         * der Fehler 61 zurueckgebeben. 
         */
        if ( aktuelles_zeichen != ']' )
        {
          return 61; // IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
        }

        /*
         * Es wurde eine IP-Adresse erkannt, der Wert in der 
         * Variablen "fkt_ergebnis_email_ok" wird um 2 erhoeht.
         */
        fkt_ergebnis_email_ok += ( knz_ipv6 == 0 ? 2 : 4 );

        /*
         * Index Leseposition nach IP-Adresseinlesung
         * 
         * Bei einer korrekten IP-Adresse, wurde in der While-Schleife die Leseposition um 
         * eine Position zu weit erhoeht. Da die eMail-Adresse in diesem Fall auch korrekt
         * beendet wurde, wird auch in der auesseren While-Schleife die Position nochmals
         * erhoeht. Die aeussere While-Schleife ist aber dann auch beendet. 
         * 
         * Nach der aeusseren While-Schleife kommen keine weiteren Pruefungen auf den 
         * Leseindex, weshalb auf eine Korrektur der Leseposition verzichtet werden kann.
         * 
         * Ist die IP-Adressangabe falsch, wird die innere While-Schleife vorzeitig
         * mit einem Fehlercode verlassen. 
         */
      }
      else if ( aktuelles_zeichen == '(' )
      {
        /*
         * While-Schleife 4 - Kommentare
         */

        /*
         * Kommentare sind am Anfang oder am Ende des Local-Parts erlaubt.
         * Im Domain-Part sind keine Kommentare zugelassen
         */
        if ( position_at_zeichen > 0 )
        {
          return 94; // Kommentar: kein Kommentar nach dem AT-Zeichen
        }

        /*
         * Wurde schon ein Kommentar gelesen, darf kein zweiter Kommentar
         * zugelassen werden. Es wird der Feler 99 zurueckgegeben.
         */
        if ( position_kommentar_ende > 0 )
        {
          return 99; // Kommentar: kein zweiter Kommentar gueltig
        }

        /*
         * Innere While-Schleife
         * In einer inneren While-Schleife wird die IP-Adresse geparst und 
         * auf Gueltigkeit geprueft. Die innere While-Schleife laeuft bis
         * zum Stringende der Eingabe.
         */

        /*
         * Befindet sich der Leseprozess nicht am Anfang der eMail-Adresse, 
         * muss nach dem Abschlusszeichen das AT-Zeichen folgen. 
         * 
         * Kommentart innerhalb des lokalen Parts sind nicht erlaubt. 
         */
        boolean knz_abschluss_mit_at_zeichen = ( akt_index > 0 );

        akt_index++;

        aktuelles_zeichen = 'A';

        while ( ( akt_index < laenge_eingabe_string ) && ( aktuelles_zeichen != ')' ) )
        {
          /*
           * Aktuelles Pruefzeichen
           * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
           */
          aktuelles_zeichen = pEingabe.charAt( akt_index );

          /*
           * Alle erlaubten Stringzeichen werden durchgelassen. 
           * Falsche Zeichen erzeugen einen Fehler.
           */

          if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) )
          {
            // OK - Buchstaben
          }
          else if ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) )
          {
            // OK - Zahlen
          }
          else if ( ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '@' ) || ( aktuelles_zeichen == '.' ) || ( aktuelles_zeichen == ' ' ) || ( aktuelles_zeichen == '!' ) || ( aktuelles_zeichen == '#' ) || ( aktuelles_zeichen == '$' ) || ( aktuelles_zeichen == '%' ) || ( aktuelles_zeichen == '&' ) || ( aktuelles_zeichen == '\'' ) || ( aktuelles_zeichen == '*' ) || ( aktuelles_zeichen == '+' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '/' ) || ( aktuelles_zeichen == '=' ) || ( aktuelles_zeichen == '?' ) || ( aktuelles_zeichen == '^' ) || ( aktuelles_zeichen == '`' ) || ( aktuelles_zeichen == '{' ) || ( aktuelles_zeichen == '|' ) || ( aktuelles_zeichen == '}' ) || ( aktuelles_zeichen == '~' ) )
          {
            // OK - Sonderzeichen
          }
          else if ( aktuelles_zeichen == ')' )
          {
            // OK - abschliessendes Anfuehrungszeichen 

            /*
             * Der Leseprozessindex muss nach der While-Schleife auf dem jetzt
             * gerade gueltigen Index stehen. 
             * 
             * Am Ende dieser inneren While-Schleife wird der Leseprozess um 
             * eine Position weiter gestellt. Der Leseprozessindex wird zum 
             * Ausgleich hier um eine Position verringert.
             * 
             * Die aeussere While-Schleife erhoeht schlussendlich den Leseprozess, 
             * damit dieser dann auf das Zeichen nach dem hier gefundenen 
             * Anfuehrungszeichen verarbeiten kann. 
             */
            akt_index--;
          }
          else if ( aktuelles_zeichen == '\\' )
          {
            /*
             * Maskiertes Zeichen 
             * Der Leseprozess muss nach naechste Zeichen pruefen. 
             * Der Leseprozessindex wird um ein Zeichen weiter gestellt.
             */
            akt_index++;

            /*
             * Pruefung: Stringende ?
             */
            if ( akt_index == laenge_eingabe_string )
            {
              return 96; // Kommentar: Escape-Zeichen nicht am Ende der Eingabe
            }

            /*
             * Zeichen nach dem Backslash lesen. 
             * Das Zeichen darf ein Backslash oder ein Anfuehrungszeichen sein. 
             * Alle anderen Zeichen fuehren zum Fehler 91.
             */
            aktuelles_zeichen = pEingabe.charAt( akt_index );

            if ( ( aktuelles_zeichen != '\\' ) && ( aktuelles_zeichen != '"' ) )
            {
              return 91; // Kommentar: Ungueltige Escape-Sequenz im Kommentar
            }
          }
          else
          {
            /*
             * Fehler 92
             * Dieser Fehler wird zurueckgegeben, wenn ein ungueltiges 
             * Zeichen im Kommentar vorhanden ist.
             */
            return 92;// Kommentar: Ungueltiges Zeichen im Kommentar
          }

          /*
           * Der Leseprozessindex wird um eine Position weitergestellt.
           */
          akt_index++;
        }

        /*
         * Pruefung: Wurde eine abschliessende Klammer gefunden ? 
         * 
         * Nach der While-Schleife muss die Variablen "aktuelles_zeichen" eine 
         * abschliessende Klammer sein. Nur dann wurde die Einleseschleife 
         * korrekt beendet. 
         * 
         * Steht ein anderes Zeichen in der Variablen, ist der Leseprozess 
         * am Stringende der Eingabe angekommen. Das ist die zweite Moeglichkeit, 
         * wie die While-Schleife beendet werden kann. 
         */
        if ( aktuelles_zeichen != ')' )
        {
          return 93; // Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
        }

        /*
         * Pruefung: Leseprozess am Stringende angelangt ?
         * 
         * Wurde die While-Schleife korrekt beendet, kann es sein, dass die 
         * abschliessende Klammer am Stringende steht.  
         */
        if ( akt_index + 1 >= laenge_eingabe_string )
        {
          return 95; // Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
        }

        /*
         * Pruefung: naechstes Zeichen gleich AT-Zeichen ?
         */
        if ( pEingabe.charAt( akt_index + 1 ) == '@' )
        {
          if ( knz_abschluss_mit_at_zeichen == false )
          {
            return 98; // Kommentar: Kein lokaler Part vorhanden
          }
        }
        else
        {
          if ( knz_abschluss_mit_at_zeichen )
          {
            return 97; // Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
          }
        }

        /*
         * Die Position der abschliessenden Klammer wird gespeichert. 
         */
        position_kommentar_ende = akt_index;
      }
      else
      {
        return 22; // Zeichen: ungueltiges Zeichen in der Eingabe gefunden 
      }

      /*
       * Leseposition erhoehen
       * Sind alle Pruefungen fuer das aktuelle Zeichen gemacht worden, 
       * wird die Leseposition um eins erhoeht und das naechste Zeichen
       * der Eingabe wird geprueft. 
       */
      akt_index++;
    }

    /*
     * Pruefungen nach der While-Schleife
     */

    /*
     * Pruefung: Punkt gefunden ?
     * Bei einer IP-Adressangabe OK, da auch dort Punkte gefunden werden muessen.
     * Bei einer IP6-Adressangabe wird die Variable "position_letzter_punkt" fuer 
     * die Doppelpunkte ":" in der Adressangabe benutzt. 
     */
    if ( position_letzter_punkt == -1 )
    {
      return 34; // Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
    }

    /*
     * Pruefung: AT-Zeichen gefunden ?
     * Ist der Wert der Variablen "position_at_zeichen" gleich -1, wurde kein AT-Zeichen 
     * gefunden. Es wird der Fehler 28 zurueckgegeben.
     *  
     * Bei einer IP-Adressangabe OK, da auch dort ein AT-Zeichen vorhanden sein muss.
     */
    if ( position_at_zeichen == -1 )
    {
      return 28; // AT-Zeichen: kein AT-Zeichen gefunden
    }

    /*
     * Pruefung: EMail-Adresse ohne IP-Adressangabe ?
     * 
     * Handelt es sich um eine eMail-Adresse ohne IP-Adresse, ist der Wert in der 
     * Variablen "fkt_ergebnis_email_ok" kleiner als 2. 
     * 
     * Wenn dem so ist, muessen noch Abschlusspruefungen bezueglich des letzten 
     * Punktes gemacht werden.
     */
    if ( fkt_ergebnis_email_ok < 2 )
    {
      /*
       * Pruefung: Letzter Punkt nach AT-Zeichen ?
       * Die Position des letzten Punktes muss groesser sein als die Position 
       * des AT-Zeichens. Ist die Punkt-Position kleiner als die Position des 
       * AT-Zeichens wird der Fehler 35 zurueckgegeben.
       *  
       * Bei einer IP-Adressangabe OK, da dort die gleichen Bedingungen gelten.
       */
      if ( position_letzter_punkt < position_at_zeichen )
      {
        return 35; // Trennzeichen: der letzte Punkt muss nach dem AT-Zeichen liegen (... hier eben die negative Form, wenn der letzte Punkt vor dem AT-Zeichen stand ist es ein Fehler)
      }

      /*
       * Pruefung: Letzter Punkt gleich Stringende ?
       * Die Position des letzten Punktes darf nicht am Stringende liegen. 
       * Lieg der letzte Punkt am Stringende, wird der Fehler 36 zurueckgegeben.
       *  
       * Bei einer IP-Adressangabe OK, da dort die gleichen Bedingungen gelten.
       */
      if ( ( position_letzter_punkt + 1 ) == laenge_eingabe_string )
      {
        return 36; // Trennzeichen: der letzte Punkt darf nicht am Ende liegen
      }

      if ( ( position_letzter_punkt + 1 ) > ( laenge_eingabe_string - 2 ) )
      {
        return 14; // Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
      }

      if ( ( laenge_eingabe_string - position_letzter_punkt ) > 10 )
      {
        return 15; // Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
      }

      /*
       * https://stackoverflow.com/questions/9071279/number-in-the-top-level-domain
       */

      aktuelles_zeichen = pEingabe.charAt( position_letzter_punkt + 1 );

      if ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) )
      {
        return 23; // Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
      }

      //if ( zahlen_zaehler > 0 ) { return 19; } // Laenge: Top-Level-Domain darf keine Zahlen haben ... oder doch ?
    }

    /*
     * Ergebnisanpassung
     * 
     * Pruefung: Wurde ein Kommentar gefunden ?
     * 
     * Enthaelt die eMail-Adresse einen Kommentar, wird der Ergebniswert angepasst. 
     * Diese Anpassung erfolgt ueber eine If-Konstruktion.
     */
    if ( position_kommentar_ende > 0 )
    {
      if ( fkt_ergebnis_email_ok == 0 )
      {
        fkt_ergebnis_email_ok = 6; // "eMail-Adresse korrekt (Kommentar)" 
      }
      else if ( fkt_ergebnis_email_ok == 1 )
      {
        fkt_ergebnis_email_ok = 7; // "eMail-Adresse korrekt (Kommentar, String)"
      }
      else if ( fkt_ergebnis_email_ok == 3 )
      {
        fkt_ergebnis_email_ok = 8; // "eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)";
      }
      else if ( fkt_ergebnis_email_ok == 5 )
      {
        fkt_ergebnis_email_ok = 9; // "eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)"
      }

      /*
       * Die Rueckgabewerte 2 und 4 werden nicht konvertiert, da 
       * ... mir die Ergebniszahlen ausgehen (eMail-Adresse OK muss kleiner 10 bleiben)
       * ... es die Sonderfaelle einer eMail-Adresse mit einer IP-Angabe sind (das zaehlt mehr, als ein Kommentar)
       */
    }

    /*
     * Sind alle Pruefungen bis hier ohne Fehler durchgefuehrt worden, stimmt 
     * die Eingabe mit der Struktur einer eMail-Adresse ueberein.
     * 
     * Es ist hinreichend geprueft, dass die Eingabe eine eMail-Adresse ist (sein kann).
     * Der Aufrufer bekommt den Wert der Variablen "fkt_ergebnis_email_ok" zurueck.
     * 
     * ALTERNATIV: - alle Fehler als negative Zahlen zurueckgeben.
     *             - Position des At-Zeichens zurueckgeben, damit die eMail-Adresse 
     *               schneller in Lokal- und Domain-Part zu trennen ist
     */
    return fkt_ergebnis_email_ok; // eMail-Adresse korrekt
  }

  /**
   * @param pEingabe die zu pruefende Eingabe
   * @return eine Stringzeile mit dem Return-Code und dessen textueller Bezeichnung
   */
  public static String checkEMailAdresseX( String pEingabe )
  {
    int return_code = checkEMailAdresse( pEingabe );

    return ( return_code < 10 ? " " : "" ) + return_code + " = " + getFehlerText( return_code );
  }

  /**
   * @param pFehlerNr die von der CheckEmail-Funktion zurueckgegebene Fehlernummer
   * @return den Text zur Fehlernummer
   */
  public static String getFehlerText( int pFehlerNr )
  {
    if ( pFehlerNr == 0 ) return "eMail-Adresse korrekt";
    if ( pFehlerNr == 1 ) return "eMail-Adresse korrekt (Local Part mit String)";
    if ( pFehlerNr == 2 ) return "eMail-Adresse korrekt (IP4-Adresse)";
    if ( pFehlerNr == 3 ) return "eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)";
    if ( pFehlerNr == 4 ) return "eMail-Adresse korrekt (IP6-Adresse)";
    if ( pFehlerNr == 5 ) return "eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)";

    if ( pFehlerNr == 6 ) return "eMail-Adresse korrekt (Kommentar)";
    if ( pFehlerNr == 7 ) return "eMail-Adresse korrekt (Kommentar, String)";
    if ( pFehlerNr == 8 ) return "eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)";
    if ( pFehlerNr == 9 ) return "eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)";

    if ( pFehlerNr == 10 ) return "Laenge: Eingabe ist null";
    if ( pFehlerNr == 11 ) return "Laenge: Eingabe ist Leerstring";
    if ( pFehlerNr == 12 ) return "Laenge: Laengenbegrenzungen stimmen nicht";
    if ( pFehlerNr == 13 ) return "Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes";
    if ( pFehlerNr == 14 ) return "Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.";
    if ( pFehlerNr == 15 ) return "Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)";

    if ( pFehlerNr == 20 ) return "Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)";
    if ( pFehlerNr == 21 ) return "Zeichen: Sonderzeichen im Domain-Part nicht erlaubt";
    if ( pFehlerNr == 22 ) return "Zeichen: ungueltiges Zeichen in der Eingabe gefunden";
    if ( pFehlerNr == 23 ) return "Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen";
    if ( pFehlerNr == 24 ) return "Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse";

    if ( pFehlerNr == 26 ) return "AT-Zeichen: kein AT-Zeichen am Anfang";
    if ( pFehlerNr == 27 ) return "AT-Zeichen: kein AT-Zeichen am Ende";
    if ( pFehlerNr == 28 ) return "AT-Zeichen: kein AT-Zeichen gefunden";
    if ( pFehlerNr == 29 ) return "AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde";

    if ( pFehlerNr == 30 ) return "Trennzeichen: kein Beginn mit einem Punkt";
    if ( pFehlerNr == 31 ) return "Trennzeichen: keine zwei Punkte hintereinander";
    if ( pFehlerNr == 32 ) return "Trennzeichen: ungueltige Zeichenkombination \".@\"";
    if ( pFehlerNr == 33 ) return "Trennzeichen: ungueltige Zeichenkombination \"@.\"";
    if ( pFehlerNr == 34 ) return "Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)";
    if ( pFehlerNr == 35 ) return "Trennzeichen: der letzte Punkt muss nach dem AT-Zeichen liegen";
    if ( pFehlerNr == 36 ) return "Trennzeichen: der letzte Punkt darf nicht am Ende liegen";

    if ( pFehlerNr == 40 ) return "IP6-Adressteil: String \"IPv6:\" erwartet";
    if ( pFehlerNr == 41 ) return "IP6-Adressteil: Trennzeichenanzahl ist 0";
    if ( pFehlerNr == 42 ) return "IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen";
    if ( pFehlerNr == 43 ) return "IP6-Adressteil: Zu wenig Trennzeichen";
    if ( pFehlerNr == 44 ) return "IP6-Adressteil: ungueltige Kombination \":]\"";
    if ( pFehlerNr == 45 ) return "IP6-Adressteil: Abschlusszeichen \"]\" muss am Ende stehen";
    if ( pFehlerNr == 46 ) return "IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern";
    if ( pFehlerNr == 47 ) return "IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch";
    if ( pFehlerNr == 48 ) return "IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block";
    if ( pFehlerNr == 49 ) return "IP6-Adressteil: Falsches Zeichen in der IP-Adresse";
    if ( pFehlerNr == 50 ) return "IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.";
    if ( pFehlerNr == 51 ) return "IP-Adressteil: IP-Adresse vor AT-Zeichen";
    if ( pFehlerNr == 52 ) return "IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination \"@[\")";
    if ( pFehlerNr == 53 ) return "IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern";
    if ( pFehlerNr == 54 ) return "IP4-Adressteil: Byte-Overflow";
    if ( pFehlerNr == 55 ) return "IP4-Adressteil: keine Ziffern vorhanden";
    if ( pFehlerNr == 56 ) return "IP4-Adressteil: zu viele Trennzeichen";
    if ( pFehlerNr == 57 ) return "IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein";
    if ( pFehlerNr == 58 ) return "IP4-Adressteil: ungueltige Kombination \".]\"";
    if ( pFehlerNr == 59 ) return "IP4-Adressteil: Falsches Zeichen in der IP-Adresse";
    if ( pFehlerNr == 60 ) return "IP4-Adressteil: Abschlusszeichen \"]\" muss am Ende stehen";
    if ( pFehlerNr == 61 ) return "IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'";
    if ( pFehlerNr == 62 ) return "IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)";

    if ( pFehlerNr == 80 ) return "String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein";
    if ( pFehlerNr == 81 ) return "String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen";
    if ( pFehlerNr == 82 ) return "String: kein Anfuehrungszeichen nach dem AT-Zeichen";
    if ( pFehlerNr == 83 ) return "String: Escape-Zeichen nicht am Ende der Eingabe";
    if ( pFehlerNr == 84 ) return "String: Ungueltige Escape-Sequenz im String";
    if ( pFehlerNr == 85 ) return "String: Leerstring in Anfuehrungszeichen";
    if ( pFehlerNr == 86 ) return "String: kein abschliessendes Anfuehrungszeichen gefunden.";
    if ( pFehlerNr == 87 ) return "String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen";
    if ( pFehlerNr == 88 ) return "String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)";
    if ( pFehlerNr == 89 ) return "String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen";

    if ( pFehlerNr == 91 ) return "Kommentar: Ungueltige Escape-Sequenz im Kommentar";
    if ( pFehlerNr == 92 ) return "Kommentar: Ungueltiges Zeichen im Kommentar";
    if ( pFehlerNr == 93 ) return "Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet";
    if ( pFehlerNr == 94 ) return "Kommentar: kein Kommentar nach dem AT-Zeichen";
    if ( pFehlerNr == 95 ) return "Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)";
    if ( pFehlerNr == 96 ) return "Kommentar: Escape-Zeichen nicht am Ende der Eingabe";
    if ( pFehlerNr == 97 ) return "Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen";
    if ( pFehlerNr == 98 ) return "Kommentar: Kein lokaler Part vorhanden";
    if ( pFehlerNr == 99 ) return "Kommentar: kein zweiter Kommentar gueltig";

    return "Unbekannte Fehlernummer";
  }

  /**
   * @param pString der zu pruefende String 
   */
  private static void assertIsTrue( String pString )
  {
    int return_code = checkEMailAdresse( pString );

    boolean knz_soll_wert = true;

    boolean is_true = return_code < 10;

    System.out.println( "assertIsTrue  " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? " " : "" ) + return_code + " = " + ( knz_soll_wert ? "TRUE " : "FALSE" ) + "  " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER #### " ) );
  }

  /**
   * @param pString der zu pruefende String 
   */
  private static void assertIsFalse( String pString )
  {
    int return_code = checkEMailAdresse( pString );

    boolean knz_soll_wert = false;

    boolean is_true = return_code < 10;

    System.out.println( "assertIsFalse " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? " " : "" ) + return_code + " = " + ( knz_soll_wert ? "TRUE " : "FALSE" ) + "  " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER #### " ) );
  }

  public static void main( String[] args )
  {
    try
    {
      assertIsTrue( "ABC.DEF@GHI.JKL" );
      assertIsTrue( "A@B.CD" );
      assertIsTrue( "\"ABC.DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC@DEF\"@GHI.DE" );
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
      assertIsTrue( "ABC-DEF@GHI.JKL" );
      assertIsTrue( "ABC\\@DEF@GHI.JKL" );
      assertIsTrue( "$ABCDEF@GHI.DE" );
      assertIsTrue( "ABC1.DEF2@GHI3.JKL4" );
      assertIsFalse( null );
      assertIsFalse( " " );
      assertIsFalse( "                " );
      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "A" );
      assertIsFalse( "A.B.C.D" );
      assertIsFalse( "ABC.DEF@GHI.J" );
      assertIsTrue( "ME@MYSELF.LOCALHOST" );
      assertIsFalse( "ME@MYSELF.LOCALHORST" );
      assertIsFalse( "ABC.DEF@GHI.2KL" );
      assertIsFalse( "ABC.DEF@GHI.JK-" );
      assertIsFalse( "ABC.DEF@GHI.JK_" );
      assertIsTrue( "ABC.DEF@GHI.JK2" );
      assertIsTrue( "ABC.DEF@2HI.JKL" );
      assertIsFalse( "ABC.DEF@-HI.JKL" );
      assertIsFalse( "ABC.DEF@_HI.JKL" );
      assertIsFalse( "A . B & C . D" );
      assertIsFalse( "(?).[!]@{&}.<:>" );
      assertIsFalse( ".ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI..JKL" );
      assertIsFalse( "ABC.DEF.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@." );
      assertIsFalse( "ABC.DEF@.GHI.JKL" );
      assertIsFalse( "ABCDEF@GHIJKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL." );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );
      assertIsTrue( "\"ABC.DEF.\"@GHI.DE" );
      assertIsTrue( "\".ABC.DEF\"@GHI.DE" );
      assertIsFalse( "\"\"@GHI.DE" );
      assertIsFalse( "\"ABC.DEF\\" );
      assertIsFalse( "\"ABC.DEF@G\"HI.DE" );
      assertIsFalse( "\"ABC.DEF@GHI.DE\"" );
      assertIsFalse( "\"ABC.DEF@GHI.DE" );
      assertIsFalse( "\"ABC DEF@G\"HI.DE" );
      assertIsFalse( "\"@GHI.DE" );
      assertIsFalse( "ABC.DE\"F@GHI.DE" );
      assertIsFalse( "ABC.DEF\"GHI.DE" );
      assertIsFalse( "ABC.DEF\"@GHI.DE" );
      assertIsFalse( "ABC.DEF@\"\"" );
      assertIsFalse( "ABC.DEF@\"GHI.DE" );
      assertIsFalse( "ABC.DEF@G\"HI.DE" );
      assertIsFalse( "ABC.DEF@GHI.DE\"" );
      assertIsFalse( "ABC.DEF.\"" );
      assertIsFalse( "ABC DEF@GHI.DE" );
      assertIsFalse( "A@G\"HI.DE" );
      assertIsTrue( "\"ABC\".DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "A\"BC\".DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "\"ABC\".DEF.G\"HI\"@JKL.de" );
      assertIsFalse( "\"AB\"C.DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "\"ABC\".DEF.\"GHI\"J@KL.de" );
      assertIsFalse( "\"AB\"C.D\"EF\"@GHI.DE" );
      assertIsFalse( "0\"00.000\"@GHI.JKL" );
      assertIsTrue( "(ABC)DEF@GHI.JKL" );
      assertIsTrue( "ABC(DEF)@GHI.JKL" );
      assertIsFalse( "AB(CD)EF@GHI.JKL" );
      assertIsFalse( "AB.(CD).EF@GHI.JKL" );
      assertIsFalse( "AB.\"(CD)\".EF@GHI.JKL" );
      assertIsFalse( "(ABCDEF)@GHI.JKL" );
      assertIsFalse( "(ABCDEF).@GHI.JKL" );
      assertIsTrue( "(AB\\\"C)DEF@GHI.JKL" );
      assertIsTrue( "(AB\\\\C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\@C)DEF@GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI)JKL" );
      assertIsFalse( ")ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GHI).JKL" );
      assertIsFalse( "ABCDEF(@)GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI).JKL" );
      assertIsFalse( "(A(B(C)DEF@GHI.JKL" );
      assertIsFalse( "(A)B)C)DEF@GHI.JKL" );
      assertIsFalse( "(A)BCDE(F)@GHI.JKL" );
      assertIsFalse( "(ABC.DEF@GHI.JKL)" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[001.002.003.004]" );
      assertIsTrue( "\"ABC.DEF\"@[127.0.0.1]" );
      assertIsFalse( "ABC.DEF[1.2.3.4]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
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
      assertIsFalse( "ABC.DEF@[1.2.3.4] " );
      assertIsFalse( "ABC.DEF@[1234.5.6.7]" );
      assertIsFalse( "ABC.DEF@[1.2...3.4]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:db8::1]" );
      assertIsFalse( "ABC@[IP" );
      assertIsFalse( "ABC@[IPv6]" );
      assertIsFalse( "ABC@[IPv6:]" );
      assertIsFalse( "ABC@[IPv6:1]" );
      assertIsFalse( "ABC@[IPv6:1:2]" );
      assertIsTrue( "ABC@[IPv6:1:2:3]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4:5:6]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4:5:6:7]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:6:7:8:9]" );
      assertIsFalse( "ABC@[IPv4:1:2:3:4]" );
      assertIsFalse( "ABC@[I127.0.0.1]" );
      assertIsFalse( "ABC@[D127.0.0.1]" );
      assertIsTrue( "ABC@[IPv6:1:2:3::5:6:7:8]" );
      assertIsFalse( "ABC@[IPv6:1:2:3::5::7:8]" );
      assertIsFalse( "ABC@[IPv6::ffff:.127.0.0.1]" );
      assertIsTrue( "ABC@[IPv6::ffff:127.0.0.1]" );
      assertIsTrue( "ABC@[IPv6::FFFF:127.0.0.1]" );
      assertIsFalse( "ABC@[IPv6::fff:127.0.0.1]" );
      assertIsFalse( "ABC@[IPv6::1234:127.0.0.1]" );
      assertIsTrue( "ABC@[IPv6:0:ffff:127.0.0.1]" );
      assertIsTrue( "ABC@[IPv6:1:ffff:127.0.0.1]" );
      assertIsFalse( "ABC@[IPv6:127.0.0.1]" );
      assertIsFalse( "ABC@[IPv6:::127.0.0.1]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:Z]" );
      assertIsFalse( "ABC@[IPv6:12:34]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:6] " );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:::6:7:8]" );
      assertIsFalse( "ABC@[IPv6::ffff:127A.0.0.1]" );
      assertIsFalse( "ABC@[IPv6::ffff:fff.0.0.1]" );
      assertIsFalse( "ABC@[IPv6::ffff:999.0.0.1]" );
      assertIsFalse( "ABC@[IPv6:a:b:c:d:127.0.0.1]" );
      assertIsFalse( "ABC@[IPv6::fffff:127.0.0.1]" );
    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    System.exit( 0 );
  }

}
