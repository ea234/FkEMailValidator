package de.fk.email;

/**
 * Roughly translated with google translate.
 * And then roughhly hand optimized translated and reformated.
 * You may find better words.
 * 
 * Sometimes it reads a little bit like Yoda-Mode.
 * ... but grown up Person that you are, cope you will with it.
 * 
 * I am well capable of writing better documentation in english. 
 * But as it is, this is the English Version. 
 */
public class FkEMail_ENGLISH_GOOGLE_TRANSLATE
{
  /**
  * <pre>
  * Validation of an email address.
  * </pre>
  *
  * @param pInput the input to be checked
  * @return TRUE, if the input according to the structure could be an eMail address.
  */
  public static boolean validateEMailAddress( String pInput )
  {
    return checkEMailAddress( pInput ) < 10;
  }

  /**
   * <pre>
   * Validation of an email address.
   *
   * No TRIM is made on the input parameter.
   *
   * Return values less than 10 are OK.
   *
   * A return of 0, is an eMail address without any other specials
   * (... 0 can now be an email in pointed brackets)
   *
   * Return values from 1 to 9, are email addresses with special string, comment or IP address.
   *
   * The following returns are possible:
   *
   * 0 = eMail address correct
   * 1 = eMail address correct (local part with string)
   * 2 = eMail address correct (IP4 address)
   * 3 = eMail address correct (Local part with string and IP4 address)
   * 4 = eMail address correct (IP6 address)
   * 5 = eMail address correct (Local part with string and IP6 address)
   * 6 = eMail adress correct (comment)
   * 7 = email address correct (comment, string)
   * 8 = eMail address correct (comment, string, IP4 address)
   * 9 = eMail address correct (comment, string, IP6 address)
   *
   *
   * 10 length: input is zero
   * 11 length: input is empty string
   * 12 length: length limitations are not correct
   * 13 Length: RFC 5321 = SMTP protocol = maximum length of the local part is 64 bytes
   * 14 Lenght: Top-level domain must be at least 2 digits long.
   * 15 Length: top-level domain can not be more than X-places long. (X is 10 here)
   *
   * 16 Structure: no opening square bracket found.
   * 17 Structure: no closing square bracket found.
   * 18 Structure: Error in Address String X
   *
   * 20 characters: number or special characters only after one letter (substring may not begin with numbers or special characters)
   * 21 characters: Special characters not allowed in the domain part
   * 22 characters: invalid character found in the input
   * 23 characters: top-level domain can not begin with numbers
   * 24 characters: No special characters at the end of the eMail address
   *
   * 26 AT-Character: no AT-Character at the beginning
   * 27 AT character: no AT character at the end
   * 28 AT characters: no AT character found
   * 29 AT characters: do not allow another AT character if AT character has already been found
   *
   * 30 separators: no beginning with a dot
   * 31 separators: no two dots in a row
   * 32 separators: invalid character combination ". @"
   * 33 separator: invalid character combination "@."
   * 34 separators: no point found (there must be at least one point for the domain separator)
   * 35 separator: the last dot must be after the AT character
   * 36 separators: the last point must not be at the end
   *
   * Domain name with IP address:
   *
   * 40 IP6 address part: string "IPv6:" expected
   * 41 IP6 address part: Separator number is 0
   * 42 IP6 address part: too many separators, maximum of 8 separators
   * 43 IP6 address part: Too few separators
   * 44 IP6 address part: invalid combination ":]"
   * 45 IP6 address part: Terminator "]" must be at the end
   * 46 IP6 address part: too many digits, max. 4 digits
   * 47 IP6 address section: IPv4 in IPv6 - delimiter number incorrect
   * 48 IP6 address part: IPv4 in IPv6 - too many characters in the first IP4 block
   * 49 IP6 address part: Wrong character in the IP address
   * 50 IP6 address part: Only one double colon may exist.
   * 51 IP address part: IP address before AT sign
   * 52 IP address part: IP address must come directly after the AT sign (correct combination "@ [")
   * 53 IP4 address part: too many digits, a maximum of 3 digits
   * 54 IP4 Address Section: Byte Overflow
   * 55 IP4 address section: no digits available
   * 56 IP4 address part: too many separators
   * 57 IP4 address part: IP address Separator number must be 3
   * 58 IP4 address part: invalid combination ".]"
   * 59 IP4 address part: Wrong character in the IP address
   * 60 IP4 address part: Terminator "]" must be at the end
   * 61 IP address part: No completion of the IP address on ']'
   *
   * Local part with quotation marks
   *
   * 80 String: A starting quotation mark must be at the beginning, the character error must not be greater than 0
   * 81 String: A starting leader must come immediately after a period
   * 82 String: no quotation mark after the AT character
   * 83 String: escape character not at the end of the input
   * 85 String: Empty string in quotation marks
   * 86 String: no final quotation mark found.
   * 87 String: After a closing quotation mark, an AT sign or a period must follow
   * 88 String: The string ends at the end of the string (premature end of input)
   * 89 String: Invalid character within quotes
   *
   * Comments
   *
   * 91 Comment: Invalid escape sequence in the comment
   * 92 Comment: Invalid character in the comment
   * 93 Comment: no conclusive sign for the commentary found. ')' expected
   * 94 Comment: no comment after the AT sign
   * 95 Comment: The comment ends at the end of the string (premature end of the input)
   * 96 Comment: Escape characters do not end at the input
   * 97 Comment: After the comment an AT-Character must come
   * 98 Comment: No local part available
   * 99 Comment: no second comment valid
   *
   * Correct email addresses that are not recognized ---------------------------------------- ---------------------
   *
   * FkEMail.checkemailadresse ("ABC@localhost")     = 34 = Separator: no point found (at least one point must exist for the domain separator)
   * FkEMail.checkemailaddress ("ABC.DEF@localhost") = 35 = separator: the last dot must be after the AT character
   *
   *                                                  ALTERNATE: Error 34 and error 35 specify exactly this CASE.
   *                                                             These error numbers can be accepted as correct if
   *                                                             such eMail address information should be allowed
   *
   * not clarified ----------------------------------------------- -------------------------------------------------- -
   *
   * Comment at the end of the email address correct?
   * Can an email domain end with a number?
   *
   * IPv6 address information is not 100% correctly recognized
   * - Overflow in the information is not recognized
   *
   * ABC@[IPv6123::ffff:127.0.0.1] = prefix "IPv6" or "IPv6:" (with or without colon as separator?)
   *
   * ABC.DEF@192.0.2.123 = IP4 address without brackets valid?
   *
   * "- --- .. -."@sh.de = character combination "or." in the string correct?
   *                       (otherwise there will be a "... .... .. -"@storm.de)
   *
   * "(A (B (C) DEF@GHI.JKL" = Allow opening in a comment '('?
   *
   * "<ABC.DEF@GHI.JKL>" = correct eMail address information?
   * "<ABC.DEF@GHI.JKL> ABC DEF" = correct eMail address information? (... the parentheses come at the start of the string)
   *
   *
   * ------------------------------------------------- -------------------------------------------------- ---------------
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
   * https://docs.microsoft.com/en-us/dotnet/standard/base-types/how-to-verify-that-strings-are-in-valid-email-format
   *
   * https://www.regular-expressions.info/email.html
   * https://de.wikipedia.org/wiki/Top-Level-Domain
   * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address?rq=1
   * https://tools.ietf.org/id/draft-ietf-behave-address-format-10.html
   * </pre>
   * 
   * @param pInput die auf eine eMail-Adresse zu pruefende Eingabe
   * @return wenn die Eingabe nach der Struktur her eine eMail-Adresse ergeben kann einen Wert kleiner 10, ansonsten einen der oben genannten Rueckgabewerte 
   */
  public static int checkEMailAddress( String pInput )
  {

    /*
     * Rough schedule:
     *
     * Length tests 1 - input total
     *
     * End on '>'
     *      YES = search '<'
     *
     * Start on '<'
     *      YES = search '>'
     *
     * Square Brackets found?
     *       YES = check the non-email string
     *
     * Length tests 2 - eMail address
     *
     * While Loop 1
     *
     * A-Z, a-z and Number characters
     *
     * Special characters domain part - _
     *
     *    Character .
     *
     *    Character @
     *
     *    Character \
     *
     * Special characters Local-Part
     *
     *    Character "
     *
     *  While Loop 2 - String
     *
     *          Characters A-Z, a-z, numbers and special characters
     *
     *          Character "
     *
     *          Character \
     *
     *          Character [
     *
     *             Characters "IPv6:"
     *
     *             While loop 3 - IP address
     *
     *             IPv6
     *
     *              Characters A-F, a-f and numbers
     *
     *              Character .
     *
     *              Character :
     *
     *              Character ]
     *
     *             IPv4
     *
     *              Numbers
     *
     *              Character .
     *
     *              Character ]
     *
     * Character (
     *
     *         While Loop 4 - Comments
     *
     *             Characters A-Z, a-z, numbers and special characters
     *
     *             Character \
     *
     *             Character )
     *
     * Final checks
     */

    /*
     * Check: Input equals "null"?
     *
     * If the input is "null", it is not a valid eMail address.
     * The error 10 is returned.
     */
    if ( pInput == null )
    {
      return 10;// Length: input is zero
    }

    /*
     * Check: Input length equals 0?
     *
     * No trim is done as a trim would consume time.
     * This function should check the string as it was passed.
     * It is not the job of this function to manipulate the input.
     * If the input consists of spaces only, the first space will be used
     * an invalid character is detected and error 29 is returned.
     *
     * If the input without trim already an empty string, the
     * Error 11 returned.
     */
    if ( pInput.length() == 0 )
    {
      return 11;// Length: input is empty string
    }

    /*
     * Length input string
     * The variable "length_input_string" referred to in the first version
     * the actual length of the input string. By installing the check routine
     * for square brackets, but the content can also be the meaning of a
     * Have position information.
     */
    int length_input_string = pInput.length();

    /*
     * In general, the input must not be longer than 255 characters.
     * The check on the minimum length of the email address follows below.
     */
    if ( length_input_string > 255 )
    {
      return 12;
    }

    /*
     * Position AT sign
     * Initial value -1 stands for "no AT character found"
     */
    int position_at_character = -1;

    /*
     * Marker "last position of a point"
     * Initial value -1 stands for "no point found"
     */
    int position_last_point = -1;

    /*
     * Saves the position of the last found quotation mark.
     * Start or end character.
     */
    int position_anf_character_act = -1;

    /*
     * Saves the position of the last closed parenthesis ')' of a valid comment.
     */
    int position_comment_end = -1;

    int position_kommentar_start = -1;

    /*
     * Counter for characters between two separators.
     * The separators are dot and AT character
     */
    int character_counter = 0;

    /*
     * Start reading position
     * The start position for the while loop is 0. This is
     * the first character of the input.
     *
     * For email addresses in angle brackets is the starting position
     * always the position after the opening square bracket.
     */
    int current_index = 0;

    char current_character = ' ';

    /*
     * Check: square brackets
     *
     *
     * ABC DEF <ABC.DEF@GHI.JKL>
     *
     * <ABC.DEF@GHI.JKL> ABC DEF
     *
     * Starts the email address with an opening square bracket, will
     * looking for a closing square bracket. From the front to the back.
     *
     * End the email address with a closing square bracket, will
     * looking for an opening square bracket. From back to front.
     *
     * If no corrosponding bracket is found, the function becomes
     * ended with an error code (16 or 17).
     *
     * If the corrosponding bracket is found, the start and end
     * End position for the actual check routine on the in the
     * Square brackets containing eMail address limited.
     *
     * The string outside the square brackets will have its own
     * Checked while loop. For this, existing variables
     * deprecated to not have to declare more variables.
     * If the characters in the non-email string are OK, they will
     * Variables reset to their initial value of -1.
     *
     * If there is an invalid character in the "non-email address string",
     * the error 18 is returned.
     */

    current_character = pInput.charAt( length_input_string - 1 );

    /*
     * Check: End with a closing square bracket?
     */
    if ( current_character == '>' )
    {
      /*
       * The last character in this case is a closing square bracket.
       *
       * This character may not be from the while loop below
       * be checked, otherwise an invalid character detected
       * would become.
       *
       * The BIS-Poisition for the eMail address check loop will be changed
       * diminished one character.
       */
      length_input_string--;

      /*
       * In a while loop, the opening square bracket is searched.
       * It is searched from the back to the front.
       */
      current_index = length_input_string;

      while ( ( current_index > 0 ) && ( current_character != '<' ) )
      {
        current_index--;

        current_character = pInput.charAt( current_index );
      }

      /*
       * If the last character is a closing square bracket, it must be one
       * Give square starting bracket.
       *
       * After the while loop must be in the variable "current_character"
       * The open square bracket should be included.
       *
       * If it is another sign, the structure is not right.
       */
      if ( current_character != '<' )
      {
        return 16; // structure: no opening square bracket found.
      }

      /*
       * Determining the positions of the "non-email address string" to be checked separately
       */
      position_last_point = 0;
      position_comment_end = current_index;

      /*
       * The current index is now in the position of the opening square bracket.
       * The sign has been checked and is OK, so the reading process starts
       * passed on by one character. (In addition error avoidance No. 22)
       */
      current_index++;
    }
    else
    {
      /*
       * Input did not end on a closing square bracket.
       * It is checked if the entry opens with a square
       * Clip starts.
       *
       * The current_characteracter is read at position 0.
       */
      current_character = pInput.charAt( current_index );

      if ( current_character == '<' )
      {
        /*
         * Starts the input with a square open parenthesis, will
         * searched the closing square bracket in a while loop.
         * It is searched from the front to the back.
         */
        while ( ( current_index < ( length_input_string - 1 ) ) && ( current_character != '>' ) )
        {
          current_index++;

          current_character = pInput.charAt( current_index );
        }

        /*
         * If the first character is an opening square bracket, it must be a
         * Give square closing bracket.
         *
         * After the while loop must be in the variable "current_character"
         * The closing square bracket should be included.
         *
         * If it is another sign, the structure is not right.
         */
        if ( current_character != '>' )
        {
          return 17; // structure: no closing square bracket found.
        }

        /*
         * Determining the positions of the "non-email address string" to be checked separately
         *
         * The string to be checked starts after the character behind the current position.
         * The string ends at the index of the last character.
         */
        position_last_point = current_index + 1;
        position_comment_end = length_input_string;

        /*
         * The reading process must end one character before the found closing square bracket.
         * The length of the input string will be adjusted accordingly.
         */
        length_input_string = current_index;

        /*
         * The character at position 0 is the opening square bracket.
         * The reading process must start at index 1.
         */
        current_index = 1;
      }
    }

    int email_local_part_gesamt_start = current_index;

    /*
     * Check: is there a separate "non-email address string" to check?
     */
    if ( position_last_point != -1 )
    {
      /*
       * Input = "<ABC@DEF.GHI>"
       *
       * The search routine detects an angle bracket. It will also
       * one - in this case - opening parenthesis found.
       *
       * The position of the last point is set to 0.
       * However, there is no "non-email string" left.
       *
       * Is this a mistake or not?
       * At the moment, such an input will be passed through as the correct eMail address.
       */
      // if (position_last_point == position_comment_end)
      // {
      // return 19; // structure: there is no "non-email string"
      //}

      /*
       * The characters in the "non-email address string" are checked via a while loop.
       * If an invalid character is detected, error 18 is returned.
       */
      while ( position_last_point < position_comment_end )
      {
        current_character = pInput.charAt( position_last_point );

        if ( ( ( current_character >= 'a' ) && ( current_character <= 'z' ) ) || ( ( current_character >= 'A' ) && ( current_character <= 'Z' ) ) || ( ( current_character >= '0' ) && ( current_character <= '9' ) ) )
        {
          // OK
        }
        else if ( ( current_character == ' ' ) || ( current_character == '(' ) || ( current_character == ')' ) || ( current_character == '\"' ) )
        {
          // OK ... maybe more characters here, which would be added
        }
        else
        {
          //System.out.println (current_character + "error");

          return 18; // structure: error in address-string-X
        }

        position_last_point++;
      }

      /*
       * Restoration of the default values
       * The temporary variables used for other purposes will return to their
       * Default values ​​of -1 are set so that the actual check routine works correctly.
       */
      position_last_point = -1;
      position_comment_end = -1;
    }

    current_character = ' ';

    /*
     * Calculation of the length of the pure email address information.
     *
     * The variable "current_index" stands here on the first character of the eMail address.
     * The variable "length_input_string" is after the last to be checked
     * Sign of the email address. This order with the previous variable name
     * to be compliant, which was the length of the input string.
     */
    character_counter = length_input_string - current_index;

    /*
     * http://en.wikipedia.org/wiki/Email_address
     * Within RFC 5322, there is no limit to the length of eMail addresses.
     *
     * In RFC 5321, the maximum length of the local part is 64 bytes and
     * the maximum length of the domain name specified with 255 bytes. Together
     * with the "@" - sign this results in the maximum length of a
     * eMail address of 320 bytes.
     *
     * RFC 5321 also defines the maximum length of the "Path" element
     * which determines the elements "FROM" and "RCPT TO" in the envelope and the
     * maximum length of bytes including separators "<" and ">".
     * This results in a maximum length of the eMail address of 254 bytes
     * including the "@". An e-mail with a longer address, can
     * are neither sent nor received via RFC-compliant SMTP servers.
     *
     * Minimum possible email address is "A@B.CD", equal to 6 digits.
     */
    if ( ( character_counter < 6 ) || ( character_counter > 254 ) )
    {
      return 12;// Length: length limitations are not correct
    }

    character_counter = 0;

    /*
     * Variable "fkt_result_email_ok"
     *
     * Storage of the function result for correct eMail indications.
     *
     * If the eMail address is purely text without special forms, the value remains at 0.
     *
     * If there is a string in the local part, the value is changed to 1.
     *
     * If there is an IP4 address, the value is increased by 2.
     *
     * If there is an IP6 address specification, the value is increased by 4.
     *
     * If there is a comment, the return value is converted.
     *
     * Only the 10 result values ​​below are supplied,
     * so that correct email addresses stay below the value 10.
     *
     * Regarding the increase of the value in a string specification, it is
     * so that the IP address is recognized correctly only after the AT sign
     * becomes. A correctly read string specification always precedes one
     * possible IP address are read and thus the result-
     * value is set to 1 before the increase in the IP address
     * can be made.
     *
     * The following results are possible:
     *
     * 0 = eMail address correct
     * 1 = eMail address correct (local part with string)
     * 2 = eMail address correct (IP4 address)
     * 3 = eMail address correct (Local part with string and IP4 address)
     * 4 = eMail address correct (IP6 address)
     * 5 = eMail address correct (Local part with string and IP6 address)
     * 6 = eMail adress correct (comment)
     * 7 = email address correct (comment, string)
     * 8 = eMail address correct (comment, string, IP4 address)
     * 9 = eMail address correct (comment, string, IP6 address)
     *
     * Due to the different return values, the caller can get email addresses
     * with IP address or string parts after rejection.
     */
    int fkt_result_email_ok = 0;

    boolean knz_kommentar_abschluss_am_stringende = false;

    /*
     * While Loop 1
     *
     * In the outer while loop, a basic email address structure is parsed.
     *
     * There are three inner while loops for parsing string components, comments, and IP addresses.
     *
     * In the inner while loops all conditions are checked, which are to be tested there.
     *
     * Conditions from an inner while loops should not (if possible) pass through the outer while loop.
     */
    while ( current_index < length_input_string )
    {
      /*
       * Current character
       * The current_characteracter is read from the input at the current index.
       */
      current_character = pInput.charAt( current_index );

      /*
       * Conditions Characters A-Z, a-z and numbers
       */
      if ( ( ( current_character >= 'a' ) && ( current_character <= 'z' ) ) || ( ( current_character >= 'A' ) && ( current_character <= 'Z' ) ) || ( ( current_character >= '0' ) && ( current_character <= '9' ) ) )
      {
        /*
         * Letters ("A" to "Z" and "a" to "z") and numbers may appear anywhere in the email address.
         *
         * Such a sign can not produce an error itself.
         *
         * The character error is increased by one.
         */
        character_counter++;
      }
      else if ( ( current_character == '_' ) || ( current_character == '-' ) )
      {
        /*
         * In the domain part the special characters '_' and '-' are not allowed to start.
         *
         * If the character error is 0, the current_characteracter is in an invalid position.
         *
         * According to RFC 952, no part string may start with a number or a dot in the domain part.
         * According to RFC 1123, hostnames may start with numbers.
         */

        if ( position_at_character > 0 ) // no beginning with a number or special characters in the domain part
        {
          if ( character_counter == 0 )
          {
            return 20; // character: number or special character only after one letter (substring may not start with numbers or special characters)
          }

          if ( ( current_index + 1 ) == length_input_string )
          {
            return 24; // character: No special character at the end of the eMail address
          }
          else
          {
            /*
             * https://en.wikipedia.org/wiki/Email_address
             * 
             * Domain-Part:
             * hyphen -, provided that it is not the first or last character.
             */
            if ( pInput.charAt( current_index + 1 ) == '.' )
            {
              return 20; // Trennzeichen: ungueltige Zeichenkombination "-."
            }
            //else if ( pInput.charAt( current_index - 1 ) == '.' )
            //{
            //  return 20; // Trennzeichen: ungueltige Zeichenkombination ".-"
            //}
          }
        }
      }
      /*
       * Conditions for a point
       */
      else if ( current_character == '.' )
      {
        /*
         * Check: Has a point been found yet?
         *
         * No, if the initial value of -1 is still in the storage variable "position_last_point".
         */
        if ( position_last_point == -1 )
        {
          /*
           * Check: Reading position equals 0?
           *
           * If the current index is 0 and the current_characteracter is a point,
           * error 30 is returned.
           *
           * It must not be checked with the sign error, since the first
           * Local part can also be a string. There is the sign error
           * not increased.
           */
          if ( current_index == ( position_comment_end + 1 ) )
          {
            return 30; // Separator: no beginning with a dot
          }
        }
        else
        {
          /*
           * Check: Two points in a row?
           *
           * Is the difference from the current reading position and the last
           * Point position equal to 1, stand 2 points in a row. It will
           * in this case the error 31 returned.
           */
          if ( ( current_index - position_last_point ) == 1 )
          {
            return 31; // Separator: no two dots in a row
          }
        }

        if ( position_at_character > 0 )
        {
          /*
           * Domain part label length
           * https://de.wikipedia.org/wiki/Hostname
           * https://en.wikipedia.org/wiki/Hostname
           *
           * A domain label must have 1 characters and can be a maximum of 63 characters.
           * 64 characters are checked in the calculation, since the subtraction is not
           * must be complicated (the position of the last point is counted).
           *
           * If the current domain label is too long, error 63 is returned.
           */
          if ( ( current_index - position_last_point ) > 64 )
          {
            // System.out.println ("" + (current_index - position_letzt_punkt));

            return 63; // Domain part: Domain label too long (maximum 63 characters)
          }
        }
        /*
         * Save index of the last point
         */
        position_last_point = current_index;

        /*
         * Set the character and number counter after one point to 0
         */
        character_counter = 0;
      }
      /*
       * Conditions for the AT-Character
       */
      else if ( current_character == '@' )
      {
        /*
         * Check: Position AT-Character not equal to -1?
         *
         * If an AT character has already been found, it is in the position variable
         * a value greater than 0 exists. It may only be used once in the reading process
         * an (unmasked) AT character can be found as a separator character.
         * If an AT character position already exists, the error becomes
         * 29 returned.
         *
         * If no AT character has yet been found, further checks are made.
         */
        if ( position_at_character != -1 )
        {
          return 29; // AT character: do not allow another AT character if AT character has already been found
        }

        if ( current_index == 0 )
        {
          return 26; // AT character: no AT character at the beginning
        }

        if ( current_index > 64 )
        {
          return 13;// Length: RFC 5321 = SMTP protocol = maximum length of the local part is 64 bytes
        }

        if ( ( current_index + 1 ) == length_input_string )
        {
          return 27; // AT character: no AT character at the end
        }

        if ( current_index - position_last_point == 1 )
        {
          return 32; // Separator: invalid character combination ".@"
        }

        /*
         * Combination "@."
         * At this position it is ensured that in the input still
         * at least 1 character follows. There is no index-out-of-bounds exception here.
         *
         Otherwise the AT sign would be at the end and the caller
         * would get 8 as a result of the function.
         */
        if ( pInput.charAt( current_index + 1 ) == '.' )
        {
          return 33; // Separator: invalid character combination "@."
        }

        /*
         * Note the position of the AT sign
         */
        position_at_character = current_index;

        /*
         * Set the character error after the AT-Character to 0
         */
        character_counter = 0;

        /*
        * Position of last point
        *
        * The AT symbol separates the local and domain part.
        * The position of the last point must be zeroed out
        * Side effects in the length calculation of the individual
        * Avoid domain parts.
        *
        * The domain part starts at the AT symbol and on its index
        * the position of the last point is also set.
        */
        position_last_point = current_index;

      }
      else if ( current_character == '\\' )
      {
        /*
         * Special characters with Qoutierung, which may occur only in the Local Part
         *
         * \ @
         *
         * If the position for the AT sign is greater than 0, is
         * the reading process in the domain part of the eMail address. There are these
         * Characters are not allowed and 21 will be returned.
         */
        if ( position_at_character > 0 )
        {
          return 21; // Characters: Special characters not allowed in the domain part
        }

        /*
         * Masked character
         * The reading process must still check the next character.
         * The reading process index is advanced by one character.
         */
        current_index++;

        /*
         * Check: String end?
         */
        if ( current_index == length_input_string )
        {
          return 83; // String: escape character not at the end of the input
        }

        /*
         * Read characters after the backslash.
         * The character may be a backslash or a quotation mark.
         * All other characters lead to error 84.
         */
        current_character = pInput.charAt( current_index );

        if ( ( current_character != '\\' ) && ( current_character != '@' ) && ( current_character != ' ' ) )
        {
          return 84; // String: Invalid escape sequence in the string
        }
      }
      else if ( ( current_character == '!' ) || ( current_character == '#' ) || ( current_character == '$' ) || ( current_character == '%' ) || ( current_character == '&' ) || ( current_character == '\'' ) || ( current_character == '*' ) || ( current_character == '+' ) || ( current_character == '-' ) || ( current_character == '/' ) || ( current_character == '=' ) || ( current_character == '?' ) || ( current_character == '^' ) || ( current_character == '`' ) || ( current_character == '{' ) || ( current_character == '|' ) || ( current_character == '}' ) || ( current_character == '~' ) )
      {
        /*
         * asc ("!") = 033 asc ("*") = 042
         * asc ("#") = 035 asc ("+") = 043
         * asc ("$") = 036 asc ("-") = 045
         * asc ("%") = 037 asc ("/") = 047
         * asc ("&") = 038
         * asc ("'") = 039
         *
         * asc ("=") = 061 asc ("{") = 123
         * asc ("?") = 063 asc ("|") = 124
         * asc ("^") = 094 asc ("}") = 125
         * asc ("_") = 095 asc ("~") = 126
         * asc ("` ") = 096 asc (" ") = 032
         */
        /*
         * Special characters, which may occur only in the local part
         *
         *! # $% & '* + - / =? ^ _ `{|} ~
         *
         * If the position for the AT sign is greater than 0, is
         * the reading process in the domain part of the eMail address. There are these
         * Characters are not allowed and 21 will be returned.
         */
        if ( position_at_character > 0 )
        {
          return 21; // Characters: Special characters not allowed in the domain part
        }
      }
      else if ( current_character == '"' )
      {
        /*
         * While Loop 2 - String
         */

        /*
         * Check: Reading process in the domain part?
         * No quotation marks are allowed in the domain part.
         * The error 82 is returned.
         */
        if ( position_at_character > 0 )
        {
          return 82; // String: no quotation mark after the AT character
        }

        /*
         * Check: Character combination. "
         *
         * A local part in quotes must start after a delimiter.
         *
         * Has already found a point and the difference of the reading process
         * is not 1, the error 81 is returned.
         */
        if ( position_last_point > 0 )
        {
          if ( current_index - position_last_point != 1 )
          {
            return 81; // String: A starting leader must come immediately after a period
          }
        }

        /*
         * Check: Start with quotation marks
         * If no characters have been read, the error 80 is returned.
         */
        if ( character_counter > 0 )
        {
          return 80; // String: A starting quotation mark must be at the beginning, the character error must not be greater than 0
        }

        /*
         * The current_characteracter is here still a quotation mark.
         * The following while loop would not start if
         * the character would not be changed. That is why the variable becomes
         * "current_character" is assigned the character 'A'.
         */
        current_character = 'A';

        /*
         * Note the position of the current quotation mark
         */
        position_anf_character_act = current_index;

        /*
         * The reading process is continued by one character.
         * This will process the introductory quotes from the parser.
         *
         * If the introductory quotation mark is at the end of the string, the
         * While loop was not executed because the variable "current_index" is now
         * equals the length of the input string. The difference to
         * stored position of the introductory quotes is 1.
         * This is the first check after the while loop. It will
         * thus inputs like:
         *
         * ABC.DEF. "
         *
         * recognized. The caller gets the error 85 back in this case.
         */
        current_index++;

        /*
         * Inner while loop for reading in the string.
         * Runs to the end of the string or until the next - unscreened - quotes.
         */
        while ( ( current_index < length_input_string ) && ( current_character != '"' ) )
        {
          /*
           * Current character
           * The current_characteracter is read from the input at the current index.
           */
          current_character = pInput.charAt( current_index );

          /*
           * All allowed string characters are allowed through.
           * Incorrect characters generate an error.
           */

          if ( ( ( current_character >= 'a' ) && ( current_character <= 'z' ) ) || ( ( current_character >= 'A' ) && ( current_character <= 'Z' ) ) )
          {
            // OK - letters
          }
          else if ( ( current_character >= '0' ) && ( current_character <= '9' ) )
          {
            // OK - numbers
          }
          else if ( ( current_character == '_' ) || ( current_character == '-' ) || ( current_character == '@' ) || ( current_character == '.' ) || ( current_character == ' ' ) || ( current_character == '!' ) || ( current_character == '#' ) || ( current_character == '$' ) || ( current_character == '%' ) || ( current_character == '&' ) || ( current_character == '\'' ) || ( current_character == '*' ) || ( current_character == '+' ) || ( current_character == '-' ) || ( current_character == '/' ) || ( current_character == '=' ) || ( current_character == '?' ) || ( current_character == '^' ) || ( current_character == '`' ) || ( current_character == '{' ) || ( current_character == '|' ) || ( current_character == '}' ) || ( current_character == '~' ) )
          {
            // OK - special characters
          }
          else if ( current_character == '"' )
          {
            // OK - final quotes

            /*
             * The reading process index must be after the while loop on the now
             * currently valid index stand.
             *
             * At the end of this inner while loop, the reading process is over
             * one position is passed on, the index must be here around
             * a position can be reduced.
             *
             * The outer while loop increments the read process index again.
             */
            current_index--;
          }
          else if ( current_character == '\\' )
          {
            /*
             * Masked character
             * The reading process must check for the next characters.
             * The reading process index is advanced by one character.
             */
            current_index++;

            /*
             * Check: String end?
             */
            if ( current_index == length_input_string )
            {
              return 83; // String: escape character not at the end of the input
            }

            /*
             * Read characters after the backslash.
             * The character may be a backslash or a quotation mark.
             * All other characters lead to error 84.
             */
            current_character = pInput.charAt( current_index );

            if ( ( current_character != '\\' ) && ( current_character != '"' ) )
            {
              return 84; // String: Invalid escape sequence in the string
            }

            /*
             * Preventing the While loop from ending early.
             * In case a quotation mark was found, would
             * the while loop to read the string are terminated.
             */
            current_character = 'A';
          }
          else
          {
            /*
             * Error 89
             * This error is returned if an invalid
             * Character exists in the string.
             */
            return 89; // String: Invalid character within quotes
          }

          /*
           * The reading process index is advanced by one position.
           */
          current_index++;
        }

        /*
         * Empty string
         * If a final space is found, the difference to the
         * last position of a quotation mark not 1.
         */
        if ( ( current_index - position_anf_character_act ) == 1 )
        {
          return 85; // String: Empty string in quotes "", or start with spaces at the end of the input
        }
        /*
         * Check: final quotation mark available?
         *
         * After the end of the while loop, the variable "current_character" must be
         * Include quotation marks.
         *
         * Was not a final quotation mark found?
         */
        if ( current_character != '"' )
        {
          return 86; // String: no final quotation mark found.
        }

        /*
         * Examination: reading process reached the end of the string?
         */
        if ( current_index + 1 >= length_input_string )
        {
          return 88; // String: The string ends at the end of the string (premature end of input)
        }

        /*
         * Check valid closing character combinations
         */
        if ( pInput.charAt( current_index + 1 ) == '.' )
        {
          // valid character combination ".
        }
        else if ( pInput.charAt( current_index + 1 ) == '@' )
        {
          // valid character combination "@
        }
        else if ( pInput.charAt( current_index + 1 ) == '(' )
        {
          // valid character combination "(
        }
        else
        {
          return 87; // String: After a closing quotation mark, an AT sign or a period must follow
        }

        /*
         * Note the position of the last quotation mark
         */
        position_anf_character_act = current_index;

        /*
         * A string was detected in the local part of the eMail address.
         * The variable "fkt_ergebnis_email_ok" is set to 1.
         */
        fkt_result_email_ok = 1;
      }
      else if ( current_character == '[' )
      {
        /*
         * While loop 3 - IP address
         */

        /*
         * Check: AT-Character did not happen yet?
         *
         * The IP address must be in the domain part of the email address.
         * If no AT character has yet been found, the reading process is in progress
         * not yet in the domain part. It is returned 51 as a mistake.
         */
        if ( position_at_character == -1 )
        {
          return 51; // IP address part: IP address before AT sign
        }

        if ( ( position_comment_end > position_at_character ) )
        {
          /*
           * 
           * Check: Domain part with commentary after AT characters
           *
           * ABC.DEF @ (comment) [1.2.3.4]
           *
           * If there is a comment immediately after the AT symbol, the introductory
           * square brackets must come directly after the closing comment bracket.
           *
           * The distance between the current reading position and the position of the comment bracket must be 1.
           * If the difference is larger, error 106 is returned.           
           */
          if ( ( current_index - position_comment_end ) != 1 )
          {
            return 106; // Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
          }
        }
        else
        {
          /*
           * Check: Start character directly after AT-Character?
           *
           * The start character "[" must come directly after the AT-symbol.
           * The current reading position must be exactly 1 character after the position
           * of the AT sign. If that is not the case, will
           * 52 returned as error.
           */
          if ( ( current_index - position_at_character ) != 1 )
          {
            return 52; // IP address part: IP address must come directly after the AT sign (correct combination "@ [")
          }
        }
        /*
         * License plate field IPv6
         *
         * If there is no IPv6 address, the value is 0.
         *
         * If there is a normal IPv6 address, the value is 1.
         *
         * If there is an IPv6 address with an IPv4 address specification, the value is 2.
         */
        int knz_ipv6 = 0;

        int ip_address_number_number_symbol = 0;

        int ip_adresse_anzahl_trennzeichen_hintereinander = 0;

        /*
         * Check: IP-V6 address information?
         *
         * It is checked for existence of the string "IPv6:" from the current reading position.
         *
         * If the string is present, an IP-V6 address is read.
         * If the string is not present, an IP-4 address is read.
         *
         * The examination is only made if there are at least 5 more places left from the current position.
         *
         * 1234567890
         * ABC @ [IPv6: 1: 2 :: 3]
         * ABC @ [123.456.789.012]
         */
        if ( current_index + 5 < length_input_string )
        {
          /*
           * If the next character is an "I", the following characters will appear
           * checked the characters "P", "v", "6" and ":".
           *
           * If these characters are present, an IPv6 address is read.
           *
           * If these characters are not present, it is an error because the introductory character "I" is incorrect.
           * In this case the error 40 is returned.
           */
          if ( pInput.charAt( current_index + 1 ) == 'I' )
          {
            if ( ( pInput.charAt( current_index + 2 ) == 'P' ) && ( pInput.charAt( current_index + 3 ) == 'v' ) && ( pInput.charAt( current_index + 4 ) == '6' ) && ( pInput.charAt( current_index + 5 ) == ':' ) )
            {
              /*
               * The index of the reading process is increased by 5.
               * At the end of grinding, the process is further advanced by one position.
               *
               * 01234567890123456789
               * ABC @ [IPv6 :: ffff: 127.0.0.1] ");
               */
              current_index += 5;

              /*
               * The reading process is in an IPv6 address.
               * The tag variable is set to 1.
               */
              knz_ipv6 = 1;

              /*
               * 1 separator was read.
               * (Delimiter counts?)
               */
              ip_address_number_number_symbol++;
            }
            else
            {
              return 40; // IP6 address part: string "IPv6:" expected
            }
          }
          else if ( ( pInput.charAt( current_index + 1 ) == ':' ) || ( pInput.charAt( current_index + 2 ) == ':' ) || ( pInput.charAt( current_index + 3 ) == ':' ) || ( pInput.charAt( current_index + 4 ) == ':' ) || ( pInput.charAt( current_index + 5 ) == ':' ) )
          {
            /*
             * Embedding IP-V6 address without prefix "IPv6"
             *
             * If the prefix "IPv6" is not found, it is checked 
             * for a colon within the next 5 characters.
             * If a colon is found, this is the indicator that
             * an IP-V6 address is available.
             *
             * The first character is also checked, since the IP-V6 address could start with "::".
             */

            /*
             * The reading process is in an IPv6 address.
             * The tag variable is set to 1.
             */
            knz_ipv6 = 1;
          }
        }

        /*
         * https://en.wikipedia.org/wiki/IPv6#address_building_of_IPv6
         *
         * The textual notation of IPv6 addresses is described in Section 2.2 of RFC 4291:
         *
         * IPv6 addresses are usually noted in hexadecimal (IPv4: decimal),
         * where the number is divided into eight blocks of 16 bits each (4 hexadecimal digits).
         *
         * These blocks are separated by colons (IPv4: points)
         * noted: 2001: 0db8: 85a3: 08d3: 1319: 8a2e: 0370: 7344.
         *
         * Leading zeros within a block should be left out:
         * 2001: 0db8: 0000: 08d3: 0000: 8a2e: 0070: 7344 is synonymous with 2001: db8: 0: 8d3: 0: 8a2e: 70: 7344.
         *
         * One or more consecutive blocks whose value is 0 (or 0000) may be left out.
         * This is indicated by two consecutive colons: 2001: 0db8: 0: 0: 0: 0: 1428: 57ab is equivalent to 2001: db8 :: 1428: 57ab. [15]
         *
         * The reduction by rule 3 may only be carried out once.
         *
         * At most one related group of null-blocks may be replaced in the address.
         *
         * The address 2001: 0db8: 0: 0: 8d3: 0: 0: 0 may therefore be shortened to either 2001: db8: 0: 0: 8d3 :: or 2001: db8 :: 8d3: 0: 0: 0.
         * 2001: db8 :: 8d3 :: is not allowed, as this is ambiguous and incorrectly e.g. also as 2001: db8: 0: 0: 0: 8d3: 0: 0 could be interpreted.
         *
         * The reduction may not be repeated even if the result is clear.
         *
         * Also for the last two blocks (four bytes, so 32 bits) of the address the conventional decimal notation with dots may be used as separator.
         * So :: ffff: 127.0.0.1 is an alternative notation for :: ffff: 7f00: 1. This notation is mainly used when embedding the IPv4 address space in the IPv6 address space.
         */

        /*
         * Inner While Loop
         * In an inner while loop, the IP address is parsed and
         * checked for validity.
         *
         * The inner while loop runs to the end of the input string.
         */
        int ip_address_akt_zahl = 0;

        int ip_address_numbers = 0;

        current_index++;

        while ( current_index < length_input_string )
        {
          /*
           * Current character
           * The current_characteracter is read from the input at the current index.
           */
          current_character = pInput.charAt( current_index );

          if ( knz_ipv6 == 1 )
          {
            if ( ( ( current_character >= 'a' ) && ( current_character <= 'f' ) ) || ( ( current_character >= 'A' ) && ( current_character <= 'F' ) ) || ( ( current_character >= '0' ) && ( current_character <= '9' ) ) )
            {
              /*
               * Counting error
               * The ip adress counter is increased.
               * Then it will be checked if more than 4 characters have been answered.
               * If so, 46 ​​will be returned.
               */
              ip_address_numbers++;

              if ( ip_address_numbers > 4 )
              {
                return 46; // IP6 address part: too many digits, max. 4 digits
              }
            }
            else if ( current_character == '.' )
            {
              /*
               * IPv4 address information
               * If an IPv4 address is specified within the IPv6 address, then
               * this recognized by a point.
               *
               * The reading process is redirected to the detection routine for the IPv4 address.
               *
               * The first number of the IPv4 address must be read again so that the
               * Check IPv4 detection routine correctly. This is the reading process index
               * reset to the position of the last delimiter.
               *
               * The first specification of the IPv4 address is read twice.
               */

              /*
               * If an IP4 address has been embedded, separators must be used
               * the IPv6 address has been found.
               *
               * (According to the current understanding of IP6 addresses, it must be 3)
               */
              if ( ip_address_number_number_symbol != 3 )
              {
                return 47; // IP6 address part: IPv4 in IPv6 - delimiter number incorrect
              }

              /*
               * The number counter must be returned to the IP4 routine
               * be less than 4, since the first number of the IP4 address here
               * has already been read.
               */
              if ( ip_address_numbers > 3 )
              {
                return 48; // IP6 address part: IPv4 in IPv6 - too many characters in the first IP4 block
              }

              /*
               * The reading process is set to the last delimiter position.
               * At the end of the loop, the reading process will be one more position
               * Next, which then the correct position of the first
               * Sign of the IP4 address results.
               */
              current_index = position_last_point; // +1 at the end of the loop

              /*
               * Make sure that embedding the IP4 address starts with "ffff".
               * It is ensured here that there are at least 5 characters before the separator.
               * There must be at least 5 characters for an IP6 address to be parsed.
               */
              if ( ( pInput.charAt( current_index - 1 ) == 'f' ) && ( pInput.charAt( current_index - 2 ) == 'f' ) && ( pInput.charAt( current_index - 3 ) == 'f' ) && ( pInput.charAt( current_index - 4 ) == 'f' ) )
              {
                // OK - found ffff
              }
              else
              {
                if ( ( pInput.charAt( current_index - 1 ) == 'F' ) && ( pInput.charAt( current_index - 2 ) == 'F' ) && ( pInput.charAt( current_index - 3 ) == 'F' ) && ( pInput.charAt( current_index - 4 ) == 'F' ) )
                {
                  // OK - FFFF found - Where the question remains open, if the capitalization here is in order.
                }
                else
                {
                  return 62; // IP6 address part: IPv4 in IPv6 - wrong specification of IP4 embedding (string 'ffff' expected)
                }
              }

              /*
               * The ip adress counter is set to 0 because an IP4 address is now read
               * and the number counter is used globally in this while loop.
               */
              ip_address_numbers = 0;

              /*
               * The delimiter error is set to 0.
               */
              ip_address_number_number_symbol = 0;

              /*
               * The tag variable is set to 2, thus the reading process
               * branches to the IP4 read routine.
               */
              knz_ipv6 = 2;
            }
            else if ( current_character == ':' )
            {
              /*
               * Double-dot (separator IP address information)
               */

              /*
               * Number of separators
               * There are a maximum of 8 blocks.
               * This gives a maximum of 7 separators.
               *
               * It can not be read more than 7 separators.
               * At the 8th separator, the error 42 is returned.
               */
              ip_address_number_number_symbol++;

              if ( ip_address_number_number_symbol > 8 )
              {

                return 42; // IP6 address part: too many separators, maximum of 8 separators
              }

              /*
               * Only once 2 colons in a row.
               */

              /*
               * Check: 2 colons in a row?
               * The last position of a delimiter is before the current one
               * reading Position, becomes the count for successive delimiters
               * increased by 1. This counter is then set to the value 2
               * checked. If the value is 2, the error 50 is returned.
               */
              if ( ( current_index - position_last_point ) == 1 )
              {
                ip_adresse_anzahl_trennzeichen_hintereinander++;

                if ( ip_adresse_anzahl_trennzeichen_hintereinander == 2 )
                {
                  return 50; // IP6 address part: There must only be a double colon for one time.
                }
              }

              /*
               * If all examinations have been done for one point,
               * the position of the last point is updated.
               *
               * The number count and the value of the current number are set to 0.
               */
              position_last_point = current_index;

              ip_address_numbers = 0;

              ip_address_akt_zahl = 0;
            }
            else if ( current_character == ']' )
            {
              /*
               * IP6 address part - terminator "]"
               */

              if ( ip_address_numbers == 0 )
              {
                return 41; // IP6 address part: separator number is 0
              }

              /*
               * Number of separators
               * For an IP6 address at least 3 separators must have been read.
               If this is not the case, the error 43 is returned.
               */
              if ( ip_address_number_number_symbol < 3 )
              {
                return 43; // IP6 address part: too few separators
              }

              /*
               * The last point may not be on the previous position
               * lie. If so, the error 44 is returned.
               */
              if ( ( current_index - position_last_point ) == 1 )
              {
                return 44; // IP6 address part: invalid combination ":]"
              }

              /*
               * The terminator must be at the last digit of the
               * Input strings are located. If that is not the case, will
               * 45 returned as an error.
               */
              if ( ( current_index + 1 ) != length_input_string )
              {
                /*
                 * A comment can follow the IP address.
                 * Currently the comment must come immediately after the final character.
                 * 
                 * ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)
                 */
                if ( pInput.charAt( current_index + 1 ) == '(' )
                { 
                  /*
                    * If the next character after "]" is the opening bracket,
                    * is the sign OK. It becomes the reading index by one position
                    * decreased.
                    *
                    * The reading process is then immediately in the reading process for the
                    * Comments go.
                    *
                    * ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)
                    */
                  current_index--;
                }
                else if ( pInput.charAt( current_index + 1 ) == ' ' )
                {
                  /*
                   * If the next character after "]" is a space, then that is
                   * Comment separated from the end of the IP address by spaces.
                   * The space is OK, the reading is reduced.
                   *
                   * The reading process will be the space in the next pass
                   * recognize and be led into the last else branch. There
                   * it is recognized that the reading process is and will be in the domain part
                   * skip the spaces.
                   *
                   * ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)                   
                   */
                  current_index--;
                }
                else
                {
                  return 45; // IP6 address part: Terminator "]" must be at the end
                }
              }

              /*
               * A check on the Zahlahreehler brings nothing.
               * The number counter is 0 in case of error.
               * If a number were read, would be a mistake in the
               * Numbers are checked.
               *
               * Would be trying the dot and the terminator
               * separating with other characters would be an invalid
               * Characters are detected.
               */
            }
            else
            {
              return 49; // IP6 address part: Wrong character in the IP address
            }
          }
          else
          {
            /*
             * The IP address consists only of numbers and periods with one
             * final "]" sign. All other characters lead
             * to the error 59 = invalid character.
             *
             * Only one IP4 address is checked.
             */
            if ( ( ( current_character >= '0' ) && ( current_character <= '9' ) ) )
            {
              /*
               * Numbers
               * - not more than 3 digits
               * - not greater than 255 (= 1 byte)
               */

              /*
               * Counting error
               * The ip adress counter is increased.
               * Then it will be checked if more than 3 numbers have been answered.
               * If so, 53 will be returned.
               */
              ip_address_numbers++;

              if ( ip_address_numbers > 3 )
              {
                return 53; // IP4 address part: too many digits, a maximum of 3 digits
              }

              /*
               * Calculation act number
               * The value in the variable "akt_zahl" is multiplied by 10 and
               * added the value of the current_characteracter.
               *
               * The value of the current_characteracter is the value of the ASCII code 48.
               *
               * It is then checked if the number is greater than 255.
               * If the number is greater, 54 is returned. (Byteoverflow)
               */
              ip_address_akt_zahl = ( ip_address_akt_zahl * 10 ) + ( ( (int) current_character ) - 48 );

              if ( ip_address_akt_zahl > 255 )
              {
                return 54; // IP4 address part: byte overflow
              }
            }
            else if ( current_character == '.' )
            {
              /*
               * Dot (separator numbers)
               */

              /*
               * Check: numbers available?
               *
               * If the ip adress counter is 0, no numbers were read.
               * 55 will be returned in this case.
               */
              if ( ip_address_numbers == 0 )
              {
                return 55; // IP4 address part: no digits available
              }

              /*
               * NOTE ERROR 63
               * This error code can not come because 2 consecutive
               * Points no number can be read. This is the one
               * previous exam.
               *
               * There can not have been read another sign that
               * would cause another error.
               */
              //
              // /*
              //* Check: 2 points in a row?
              //* The last position of a point may not be before the current one
              //* Reading post lie. If that is the case, 63 is returned.
              //*/
              // if ((current_index - position_last_point) == 1)
              // {
              // return 63; // IP4 address part: no 2 points in a row
              //}

              /*
               * Number of separators
               * It can not be read more than 3 points (= delimiter).
               * At the 4th point, 56 is returned.
               */
              ip_address_number_number_symbol++;

              if ( ip_address_number_number_symbol > 3 )
              {
                return 56; // IP4 address part: too many separators
              }

              /*
               * If all examinations have been done for one point,
               * the position of the last point is updated.
               *
               * The number count and the value of the current number are set to 0.
               */
              position_last_point = current_index;

              ip_address_numbers = 0;

              ip_address_akt_zahl = 0;
            }
            else if ( current_character == ']' )
            {
              /*
               * IP4 address part - terminator "]"
               */

              /*
               * Number of separators
               * For an IP4 address 3 points must have been read.
               * If this is not the case, 57 is returned.
               */
              if ( ip_address_number_number_symbol != 3 )
              {
                return 57; // IP4 address part: IP address separator number must be 3
              }

              /*
               * The last point may not be on the previous position
               * lie. If that is the case, 58 is returned.
               */
              if ( ( current_index - position_last_point ) == 1 )
              {
                return 58; // IP4 address part: invalid combination ".]"
              }

              /*
               * The terminator must be at the last digit of the
               * Input strings are located. If that is not the case, will
               * 60 returned as error.
               */
              if ( ( current_index + 1 ) != length_input_string )
              {
                /*
                 * A comment can follow the IP address.
                 * Currently the comment must come immediately after the final character.
                 * 
                 * ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)
                 */
                if ( pInput.charAt( current_index + 1 ) == '(' )
                {
                  /*
                   * Korrektur des Leseindexes
                   */
                  current_index--;
                }
                else if ( pInput.charAt( current_index + 1 ) == ' ' )
                {
                  /*
                   * Korrektur des Leseindexes
                   */
                  current_index--;
                }
                else
                {
                  return 60; // IP4 address part: Terminator "]" must be at the end
                } 
              }

              /*
               * A check on the Zahlahreehler brings nothing.
               * The number counter is 0 in case of error.
               * If a number were read, would be a mistake in the
               * Numbers are checked.
               *
               * Would be trying the dot and the terminator
               * separating with other characters would be an invalid
               * Characters are detected.
               */
            }
            else
            {
              return 59; // IP4 address part: Wrong character in the IP address
            }
          }

          current_index++;
        }

        /*
         * Check: End with ']'?
         *
         * If the IP address specification is correct, the final is after the while loop
         * Character ']' in the variable "current_character". Is another in the variable
         * If there is a character, the error 61 will be returned.
         */
        if ( current_character != ']' )
        {
          return 61; // IP address part: No completion of the IP address on ']'
        }

        /*
         * An IP address was detected, the value in the
         * Variable "fkt_ergebnis_email_ok" is increased by 2.
         */
        fkt_result_email_ok += ( knz_ipv6 == 0 ? 2 : 4 );

        /*
         * Index reading position after IP address reading
         *
         * If the IP address was correct, the reading position in the while loop changed
         * one position too far. Because the email address in this case also correct
         * has ended, the position also becomes the same in the outer while loop
         * elevated. The outer while loop is then terminated.
         *
         * After the outer while loop, no further checks are made on the
         * Reading index, which is why a correction of the reading position can be dispensed with.
         *
         * If the IP address is incorrect, the inner while loop becomes premature
         * leave with an error code.
         */
      }
      else if ( current_character == '(' )
      {
        /*
         * While Loop 4 - Comments
         */

        if ( position_at_character > 0 )
        {
          /*
           * Kombination ".(" pruefen Domain-Part.
           */
          if ( ( position_last_point > position_at_character ) && ( ( current_index - position_last_point ) == 1 ) )
          {
            return 102; // Kommentar: Falsche Zeichenkombination ".(" im Domain Part
          }
        }
        else
        {
          /*
           * Character-Kombination ". (" Check Local Part
           *
           * If an introductory bracket is found, the last point must not
           * are in front of the bracket.
           *
           * The position of the last point must be greater than 0. At omit
           * this test, there is a side effect with the initial value
           * from -1 of the variables for the last point.
           *
           * If there is an incorrect combination of characters, error 101 is returned.           
           */
          if ( ( position_last_point > 0 ) && ( ( current_index - position_last_point ) == 1 ) )
          {
            return 101; // Kommentar: Falsche Zeichenkombination ".(" im Local Part
          }
        }

        /*
         * If a comment has already been read, no second comment is allowed
         * be allowed. It is the Feler 99 returned.
         */
        if ( position_comment_end > 0 )
        {
          return 99; // Comment: no second comment valid
        }

        /*
         * Inner While Loop
         * In an inner while loop, the IP address is parsed and
         * checked for validity. The inner while loop runs up
         * to the end of the string.
         */

        /*
         * If the reading process is not at the beginning of the email address,
         * must follow the AT sign after the terminator.
         *
         * Commenting within the local part is not allowed.
         */
        boolean knz_must_end_with_at_character = ( current_index > 0 );

        knz_must_end_with_at_character = ( current_index == email_local_part_gesamt_start ) == false;

        if ( position_at_character > 0 )
        {
          knz_must_end_with_at_character = false;
        }

        if ( position_at_character > 0 )
        {
          /*
           * If an AT character has already been read, the comment does not have to end with an AT character. 
           */
          knz_must_end_with_at_character = false;

          /*
           * If characters have already been read after the AT symbol, the
           * Comment ends at the end of the string.
           *
           * If the comment ends before the end of the string, the comment is in the middle
           * in the email string and is therefore incorrect.           
           */
          if ( ( current_index - position_at_character ) > 1 )
          {
            knz_kommentar_abschluss_am_stringende = true;
          }
        }

        current_index++;

        current_character = 'A';

        while ( ( current_index < length_input_string ) && ( current_character != ')' ) )
        {
          /*
           * Current character
           * The current_characteracter is read from the input at the current index.
           */
          current_character = pInput.charAt( current_index );

          /*
           * All allowed string characters are allowed through.
           * Incorrect characters generate an error.
           */

          if ( ( ( current_character >= 'a' ) && ( current_character <= 'z' ) ) || ( ( current_character >= 'A' ) && ( current_character <= 'Z' ) ) )
          {
            // OK - letters
          }
          else if ( ( current_character >= '0' ) && ( current_character <= '9' ) )
          {
            // OK - numbers
          }
          else if ( ( current_character == '_' ) || ( current_character == '-' ) || ( current_character == '@' ) || ( current_character == '.' ) || ( current_character == ' ' ) || ( current_character == '!' ) || ( current_character == '#' ) || ( current_character == '$' ) || ( current_character == '%' ) || ( current_character == '&' ) || ( current_character == '\'' ) || ( current_character == '*' ) || ( current_character == '+' ) || ( current_character == '-' ) || ( current_character == '/' ) || ( current_character == '=' ) || ( current_character == '?' ) || ( current_character == '^' ) || ( current_character == '`' ) || ( current_character == '{' ) || ( current_character == '|' ) || ( current_character == '}' ) || ( current_character == '~' ) )
          {
            // OK - special characters
          }
          else if ( current_character == ')' )
          {
            // OK - final quotes

            /*
             * The reading process index must be after the while loop on the now
             * currently valid index stand.
             *
             * At the end of this inner while loop, the reading process is reversed
             * moved one position further. The reading process index becomes the
             * Compensation reduced by one position here.
             *
             * The outer while loop eventually increases the reading process,
             * then this on the sign after the here found
             * Can handle quotes.
             */
            current_index--;
          }
          else if ( current_character == '\\' )
          {
            /*
             * Masked character
             * The reading process must check for the next characters.
             * The reading process index is advanced by one character.
             */
            current_index++;

            /*
             * Check: String end?
             */
            if ( current_index == length_input_string )
            {
              return 96; // comment: escape character not at the end of the input
            }

            /*
             * Read characters after the backslash.
             * The character may be a backslash or a quotation mark.
             * All other characters lead to error 91.
             */
            current_character = pInput.charAt( current_index );

            if ( ( current_character != '\\' ) && ( current_character != '"' ) )
            {
              return 91; // Comment: Invalid escape sequence in the comment
            }
          }
          else
          {
            /*
             * Error 92
             * This error is returned if an invalid
             * Character is present in the comment.
             */
            return 92; // Comment: Invalid character in the comment
          }

          /*
           * The reading process index is advanced by one position.
           */
          current_index++;
        }

        /*
         * Check: Was a final bracket found?
         *
         * After the while loop, the variable "current_character" must have a
         * be final clip. Only then was the read-in loop
         * ended correctly.
         *
         * If there is another character in the variable, is the reading process
         * arrived at the string end of the input. That is the second possibility
         * how the while loop can be ended.
         */
        if ( current_character != ')' )
        {
          return 93; // Comment: no conclusive sign for the commentary found. ')' expected
        }

        /*
         * Examination: reading process reached the end of the string?
         *
         * If the While loop ended correctly, it may be that the
         * final clip is at the end of the string.
         */
        if ( current_index + 1 >= length_input_string )
        {
          if ( position_at_character > 0 )
          {
            /*
             * Im Domain-Part darf der Kommentar am Stringende aufhoeren
             */
          }
          else
          {
            return 95; // Comment: The comment ends at the end of the string (premature end of input)
          }
        }
        else
        {
          /*
           * Check: next character equal to AT-Character?
           */
          if ( pInput.charAt( current_index + 1 ) == '@' )
          {
            if ( knz_must_end_with_at_character == false )
            {
              return 98; // Comment: No local part exists
            }
          }
          else if ( pInput.charAt( current_index + 1 ) == '.' )
          {
            return 103; // Kommentar: Wron Charactercombination ")."
          }
          else
          {
            if ( knz_must_end_with_at_character )
            {
              return 97; // Comment: After the comment an AT-Character must come
            }
          }

          if ( knz_kommentar_abschluss_am_stringende )
          {
            return 100; // Kommentar: Kommentar muss am Strinende enden
          }
        }
        /*
         * The position of the final bracket is saved.
         */
        position_comment_end = current_index;
      }
      else
      {
        /*
        * Special condition: space separation until comment in the domain part
        *
        * "email@domain.com (joe Smith)"
        *
        * The current character is a space and the reading process is located
        * yourself in the domain part (position_at_zeichen> 0), then must be checked,
        * whether there is an opening bracket after the spaces.
        */
        if ( ( current_character == ' ' ) && ( position_at_character > 0 ) )
        {
          /*
          * Consume the current character or continue reading 1
          */
          current_index++;

          /*
          * Read over all spaces in a while loop.
          */
          while ( ( current_index < length_input_string ) && ( pInput.charAt( current_index ) == ' ' ) )
          {
            current_index++;
          }

          /*
          * Was read in the while loop until the end of input,
          * error 22 is returned because the space
          * wrong is.
          */
          if ( current_index == length_input_string )
          {
            return 22;
          }

          /*
          * After the while loop, the character must be at the current one
          * Reading position should be an opening bracket. All other
          * Characters lead to an error because the introductory
          * Space was a wrong character. It will be in this
          * In case of error 105 returned.
          */
          if ( pInput.charAt( current_index ) == '[' )
          {
            current_index--;
          }
          else if ( pInput.charAt( current_index ) != '(' )
          {
            return 105; // Comment: space separation in the domain part. Opening bracket expected
          }
          else
          {
            /*
            * The reading position was increased too much in the while loop.
            * The reading position is reduced by one position.
            * Since the reading process is in the domain part, there is a preceding character.
            */
            current_index--;
          }
        }
        else
        {
          /*
           * Here you can put the characters for international eMail addresses,
           * which should be passed through.
           */
          // if ((current_character == 'ä') || (current_character == 'ö'))
          // {
          //  OK
          //}
          // else
          // {

          return 22; // character: invalid character found in the input

          //}
        }
      }

      /*
       * Increase reading position
       * Are all tests for the current sign made,
       * the reading position is increased by one and the next character
       * the input is checked.
       */
      current_index++;
    }

    /*
     * Tests after the while loop
     */

    /*
     * Check: found a point?
     * For an IP address specification OK, since points must also be found there.
     * For an IP6 address specification, the variable "position_last_point" becomes
     * uses the colon ":" in the address information.
     */
    if ( ( position_last_point == -1 ) || ( position_last_point == position_at_character ) )
    {
      return 34; // Separator: no point found (there must be at least one point for the domain separator)
    }

    /*
     * Check: found AT-Character?
     * If the value of the variable "position_at_character" equals -1, then no AT character was created
     * found. The error 28 is returned.
     *
     * For an IP address specification OK, as there must also be an AT symbol there.
     */
    if ( position_at_character == -1 )
    {
      return 28; // AT character: no AT character found
    }

    /*
     * Check: EMail address without IP address information?
     *
     * If it is an email address without an IP address, the value in the
     * Variables "fkt_result_email_ok" less than 2.
     *
     * If so, there are still final checks for the last one
     * Point to be made.
     */
    if ( fkt_result_email_ok < 2 )
    {
      /*
       * Check: Last point after AT sign?
       * The position of the last point must be greater than the position
       * of the AT sign. If the point position is smaller than the position of the
       * AT character returns error 35.
       *
       * For an IP address specification OK, since the same conditions apply there.
       */
      if ( position_last_point < position_at_character )
      {
        return 35; // Separator: the last dot must be after the AT character (... here is the negative form, if the last dot was before the AT character it is a mistake)
      }

      /*
       * Check: Last point equal string end?
       * The position of the last point must not be at the end of the string.
       * If the last point is at the end of the string, the error 36 is returned.
       *
       * For an IP address specification OK, since the same conditions apply there.
       */
      if ( ( position_last_point + 1 ) == length_input_string )
      {
        return 36; // Separator: the last point must not be at the end
      }

      int laenge_tld = 0;

      if ( ( position_last_point > position_at_character ) && ( position_kommentar_start > position_last_point ) )
      {
        laenge_tld = position_kommentar_start - position_last_point;
      }
      else
      {
        laenge_tld = length_input_string - position_last_point;
      }

      if ( laenge_tld < 2 )
      {
        return 14;// Length: top-level domain must be at least 2 digits long.
      }

      if ( laenge_tld > 10 )
      {
        return 15;// Length: top-level domain can not be more than X-places long. (X is 10 here)
      }

      /*
       * https://stackoverflow.com/questions/9071279/number-in-the-top-level-domain
       */

      current_character = pInput.charAt( position_last_point + 1 );

      if ( ( current_character >= '0' ) && ( current_character <= '9' ) )
      {
        return 23; // character: top-level domain can not begin with number
      }

      // if (number_number> 0) {return 19; }// Length: top-level domain may not have numbers ... or does it?
    }

    /*
     * Result adjustment
     *
     * Check: Was a comment found?
     *
     * If the eMail address contains a comment, the result value will be adjusted.
     * This adaptation is done via an if construction.
     */
    if ( position_comment_end > 0 )
    {
      if ( fkt_result_email_ok == 0 )
      {
        fkt_result_email_ok = 6; // "eMail address correct (comment)"
      }
      else if ( fkt_result_email_ok == 1 )
      {
        fkt_result_email_ok = 7; // "eMail address correct (comment, string)"
      }
      else if ( fkt_result_email_ok == 3 )
      {
        fkt_result_email_ok = 8; // "eMail address correct (comment, string, IP4 address)";
      }
      else if ( fkt_result_email_ok == 5 )
      {
        fkt_result_email_ok = 9; // "eMail address correct (comment, string, IP6 address)"
      }

      /*
       * The return values ​​2 and 4 are not converted since
       * ... I run out of results (email address OK must be less than 10)
       * ... are the special cases of an email address with an IP specification (that counts more than a comment)
       */
    }

    /*
     * If all examinations have been carried out without errors, that's right
     * match the input with the structure of an email address.
     *
     * It is sufficiently verified that the input is (can be) an email address.
     * The caller gets the value of the variable "fkt_ergebnis_email_ok" back.
     *
     * ALTERNATE: - return all errors as negative numbers.
     *            - Return the position of the at sign, hence the email address
     *              faster to separate in local and domain part
     */
    return fkt_result_email_ok; // eMail address correct
  }

  /**
   * @param p Enter the input to be checked
   * @return a string line with the return code and its textual name
   */
  public static String checkEMailAddressX( String pInput )
  {
    int return_code = checkEMailAddress( pInput );

    return ( return_code < 10 ? "" : "" ) + return_code + "=" + getFehlerText( return_code );
  }

  /**
   * @param pErrorNumber the error number returned by the CheckEmail function
   * @return the text to the error number
   */
  public static String getFehlerText( int pErrorNumber )
  {
    if ( pErrorNumber == 0 ) return "eMail address correct";
    if ( pErrorNumber == 1 ) return "eMail address correct (Local Part with String)";
    if ( pErrorNumber == 2 ) return "eMail address correct (IP4 address)";
    if ( pErrorNumber == 3 ) return "eMail address correct (Local part with string and IP4 address)";
    if ( pErrorNumber == 4 ) return "eMail address correct (IP6 address)";
    if ( pErrorNumber == 5 ) return "eMail address correct (Local part with string and IP6 address)";

    if ( pErrorNumber == 6 ) return "eMail address correct (comment)";
    if ( pErrorNumber == 7 ) return "eMail address correct (comment, string)";
    if ( pErrorNumber == 8 ) return "eMail address correct (comment, string, IP4 address)";
    if ( pErrorNumber == 9 ) return "eMail address correct (comment, string, IP6 address)";

    if ( pErrorNumber == 10 ) return "Length: input is null";
    if ( pErrorNumber == 11 ) return "Length: input is empty string";
    if ( pErrorNumber == 12 ) return "Length: length limitations do not match";
    if ( pErrorNumber == 13 ) return "Length: RFC 5321 = SMTP protocol = maximum length of the local part is 64 bytes";
    if ( pErrorNumber == 14 ) return "Length: top-level domain must be at least 2 digits long.";
    if ( pErrorNumber == 15 ) return "Length: top-level domain can not be more than X-digits long (X is 10 here)";

    if ( pErrorNumber == 16 ) return "Structure: no opening square bracket found";
    if ( pErrorNumber == 17 ) return "Structure: no closing square bracket found.";
    if ( pErrorNumber == 18 ) return "Structure: error in address-string-X";
    if ( pErrorNumber == 20 ) return "Character: number or special character only after one letter (substring must not start with numbers or special characters)";
    if ( pErrorNumber == 21 ) return "Character: special characters not allowed in the domain part";
    if ( pErrorNumber == 22 ) return "Character: invalid character found in the input";
    if ( pErrorNumber == 23 ) return "Character: top-level domain can not begin with numbers";
    if ( pErrorNumber == 24 ) return "Character: no special character at the end of the eMail address";

    if ( pErrorNumber == 26 ) return "AT-Character: no AT-Character at the beginning";
    if ( pErrorNumber == 27 ) return "AT character: no AT character at the end";
    if ( pErrorNumber == 28 ) return "AT character: no AT character found";
    if ( pErrorNumber == 29 ) return "AT character: do not allow another AT character if AT character has already been found";

    if ( pErrorNumber == 30 ) return "Separator: no beginning with a period";
    if ( pErrorNumber == 31 ) return "Separator: no two consecutive points";
    if ( pErrorNumber == 32 ) return "Separator: invalid character combination \".@\"";
    if ( pErrorNumber == 33 ) return "Separator: invalid combination of characters \"@.\"";
    if ( pErrorNumber == 34 ) return "Separator: no point found (at least one point must exist for the domain separator)";
    if ( pErrorNumber == 35 ) return "Separator: the last dot must be after the AT character";
    if ( pErrorNumber == 36 ) return "Separator: the last point must not be at the end";

    if ( pErrorNumber == 40 ) return "IP6 address part: String \" IPv6: \" expected";
    if ( pErrorNumber == 41 ) return "IP6 address part: delimiter number is 0";
    if ( pErrorNumber == 42 ) return "IP6 address part: too many separators, maximum 8 separators";
    if ( pErrorNumber == 43 ) return "IP6 address part: too few separators";
    if ( pErrorNumber == 44 ) return "IP6 address part: invalid combination\":] \"";
    if ( pErrorNumber == 45 ) return "IP6 address part: terminator \"] \" must end";
    if ( pErrorNumber == 46 ) return "IP6 address part: too many digits, maximum 4 digits";
    if ( pErrorNumber == 47 ) return "IP6 address part: IPv4 in IPv6 - delimiter number wrong";
    if ( pErrorNumber == 48 ) return "IP6 address part: IPv4 in IPv6 - too many characters in the first IP4 block";
    if ( pErrorNumber == 49 ) return "IP6 address part: Wrong character in the IP address";
    if ( pErrorNumber == 50 ) return "IP6 address part: There must only be a two-colon\". ";
    if ( pErrorNumber == 51 ) return "IP address part: IP address before AT sign";
    if ( pErrorNumber == 52 ) return "IP address part: IP address must come directly after the AT sign (correct combination \" @ [\")";
    if ( pErrorNumber == 53 ) return "IP4 address part: too many digits, max. 3 digits";
    if ( pErrorNumber == 54 ) return "IP4 address part: byte overflow";
    if ( pErrorNumber == 55 ) return "IP4 address part: no digits available";
    if ( pErrorNumber == 56 ) return "IP4 address part: too many separators";
    if ( pErrorNumber == 57 ) return "IP4 address part: IP address separator number must be 3";
    if ( pErrorNumber == 58 ) return "IP4 address part: invalid combination\".] \"";
    if ( pErrorNumber == 59 ) return "IP4 address part: Wrong character in the IP address";
    if ( pErrorNumber == 60 ) return "IP4 address part: terminator \"] \" must end";
    if ( pErrorNumber == 61 ) return "IP address part: No IP address completed on ']'";
    if ( pErrorNumber == 62 ) return "IP6 address part: IPv4 in IPv6 - wrong specification of the IP4 embedding (string 'ffff' expected)";

    if ( pErrorNumber == 63 ) return "Domain part: Domain label too long (maximum 63 characters)";

    if ( pErrorNumber == 80 ) return "String: A starting quotation mark must come at the beginning, the character error must not be greater than 0";
    if ( pErrorNumber == 81 ) return "String: A starting leading character must come immediately after a period";
    if ( pErrorNumber == 82 ) return "String: no quotation mark after the AT character";
    if ( pErrorNumber == 83 ) return "String: escape character not at the end of the input";
    if ( pErrorNumber == 84 ) return "String: Invalid escape sequence in string";
    if ( pErrorNumber == 85 ) return "String: empty string in quotation marks";
    if ( pErrorNumber == 86 ) return "String: no conclusive quotation found.";
    if ( pErrorNumber == 87 ) return "String: After a closing quotation mark, an AT sign or a period must follow";
    if ( pErrorNumber == 88 ) return "String: The string ends at the end of the string (early end of input)";
    if ( pErrorNumber == 89 ) return "String: Invalid character within quotation marks";

    if ( pErrorNumber == 91 ) return "Comment: Invalid escape sequence in comment";
    if ( pErrorNumber == 92 ) return "Comment: Invalid character in the comment";

    if ( pErrorNumber == 93 ) return "Comment: no concluding sign found for the commentary. \")\" expected";
    if ( pErrorNumber == 94 ) return "comment: no comment after the AT-Character";
    if ( pErrorNumber == 95 ) return "Comment: The comment ends at the end of the string (premature end of input)";
    if ( pErrorNumber == 96 ) return "comment: escape character not at the end of the input";
    if ( pErrorNumber == 97 ) return "Comment: After the comment the AT-Character is expected";
    if ( pErrorNumber == 98 ) return "Comment: No local part exists";
    if ( pErrorNumber == 99 ) return "comment: no second comment valid";

    if ( pErrorNumber == 100 ) return "Comment: Comment must end at String-end";

    if ( pErrorNumber == 101 ) return "Comment: Wrong combination of characters \".(\" in Local Part";;
    if ( pErrorNumber == 102 ) return "Comment: Wrong combination of characters \".(\" in Domain Part";

    if ( pErrorNumber == 103 ) return "Comment: Kommentar: Wrong Charactercombination \").\"";

    if ( pErrorNumber == 105 ) return "Comment: space separation in the domain part. Opening bracket expected";

    return "Unknown error number" + pErrorNumber;
  }

  public static void assertIsTrue( String pString )
  {
    int return_code = checkEMailAddress( pString );

    boolean knz_soll_wert = true;

    boolean is_true = return_code < 10;

    System.out.println( "assertIsTrue  " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? " " : "" ) + return_code + " = " + ( knz_soll_wert ? "TRUE " : "FALSE" ) + "  " + ( is_true == knz_soll_wert ? " OK " : " #### ERROR ####    " + getFehlerText( return_code ) ) );
  }

  public static void assertIsFalse( String pString )
  {
    int return_code = checkEMailAddress( pString );

    boolean knz_soll_wert = false;

    boolean is_true = return_code < 10;

    System.out.println( "assertIsFalse " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? " " : "" ) + return_code + " = " + ( knz_soll_wert ? "TRUE " : "FALSE" ) + "  " + ( is_true == knz_soll_wert ? " OK " : " #### ERROR #### " ) + "   " + getFehlerText( return_code ) );
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
      assertIsTrue( "ABC DEF <ABC.DEF@GHI.JKL>" );
      assertIsTrue( "<ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF <A@A>" );
      assertIsFalse( "<A@A> ABC DEF" );
      assertIsTrue( "<ABC.DEF@GHI.JKL>" );
      assertIsFalse( "ABC DEF <ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL ABC DEF" );
      assertIsTrue( "\"ABC DEF \"<ABC.DEF@GHI.JKL>" );
      assertIsFalse( "\"ABC<DEF>\"@JKL.DE" );
      assertIsFalse( "\"ABC<DEF@GHI.COM>\"@JKL.DE" );
      assertIsFalse( "ABC DEF <ABC.<DEF@GHI.JKL>" );

      assertIsTrue( "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsTrue( "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " );

      assertIsFalse( "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsFalse( "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " );

      assertIsTrue( "()DEF@GHI.JKL" );
      assertIsTrue( "DEF()@GHI.JKL" );

      assertIsFalse( "ABC DEF <>" );
      assertIsFalse( "ABC DEF <       >" );
      assertIsFalse( "ABC DEF <   @   >" );
      assertIsFalse( "ABC DEF <(   )@   >" );
      assertIsFalse( "ABC DEF <(COMMENT)A@   >" );
      assertIsFalse( "ABC DEF <ABC<DEF@GHI.JKL>" );
      assertIsTrue( "BR0w$ed.for.pr0n@PQRN.biz" );
      assertIsTrue( "{-^-}{-=-}{-^-}@GHI.JKL" );

    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    System.exit( 0 );
  }

}
