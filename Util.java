public class Util
{

   /**********************************************************
	* Method Name    : setLeft
	* Author         : Prof Scheemaker
	* Date           :
	* Course/Section :
	* Program Description:  This method will insert spaces
	*    in front of a string and return the new string. The
	*    number of spaces to insert and the string itself will
	*    be passed in as parameters.
	*
	* BEGIN setLeft (number of spaces, string)
	*   FOR (each space to be inserted)
	*      add a space to be beginning of the string
	*   END FOR
	*   return the new string with the leading spaces
	* END setLeft
	**********************************************************/

	public static String setLeft(int num, String word)
	{
		//local constants

		//local variables

		/*******************************************************/

		for (int i = 0;i < num;i++)

		   word = " " + word;

		return word;

	}
   /**********************************************************
	* Method Name    : setRight
	* Author         : Prof Scheemaker
	* Date           :
	* Course/Section :
	* Program Description:  This method will insert spaces
	*    in front of a string and return the new string. The
	*    number of spaces will be determined by the field
	*    width and the length of the string (Width - Length).
	*    If the string is wider than the field width, no
	*    spaces will be added to the front of the string
	*
	* BEGIN setRight (field width, string)
	*    Find the length of the string
	*    Calc the number of spaces to be added
	*    IF (there is room to add spaces)
	*       FOR (each space to be added)
	*          add a space to the front of the string
	*       END FOR
	*    END IF
	*    return the string
	* END setLeft
	**********************************************************/

	public static String setRight(int width, String word)
	{
		//local constants

        //local variables
		int len = word.length(); //get the string length
		int pad = width - len;   //how many spaces to add to front of string

		/*******************/

        //if there is room to add sapces
		if (len < width)

           //add the spaces to the front of the string
		   for (int i = 0; i < pad; i++)
		      word = " " + word;

        //return the string
		return word;
	}

	public static String titleCase(String word)
	{
		String firstLetter;

		word = word.toLowerCase();
		firstLetter = word.substring(0,1);
		firstLetter = firstLetter.toUpperCase();
		word = word.substring(1);
		word = firstLetter.concat(word);

		return word;
	}
}
