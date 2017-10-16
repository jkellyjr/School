#John Kelly Jr
#Project 3

import random

#welcome message
print('\t\t\tWelcome to the game rainbow explosion.')
print('\nThe idea of the game is to guess the secret word,'
       'without guessing \nsix wrong letters. You may guess one letter at a'
      'ime or the \nword in its entirety.')
print('\n\t\t\t\tGoodluck!\n')


def main():
    #opens file and reads a random word
    lines = open('P3A2_KELLY_3938535.txt').read().splitlines()
    secretWord =random.choice(lines)
    print('Secret word:', secretWord)

    #hint
    numVowels=0
    for char in secretWord:
        if char in "aeiouAEIOU":
           numVowels +=1 

    print('\n*HINT* There are',numVowels,'vowels in the secret word.')
    
    #creates empty lists
    listWord=[]
    guessedLetters=[]

    #iterates through the string and adds it to an arrayList
    count=0
    for x in secretWord:
        count+=1
        listWord.append(x)

    print('\nThe word is',count,'letters long.')

    ticks=0
    correct=0
    dashList=['_' for x in range(0,count)]

    #plays game
    guess=""
    while ticks<6 and correct!=count and guess!=secretWord:
        guess= input('\nPlease guess a letter: ')
        guess.lower()
        guessList=[guess]
        

        if guess==secretWord:
            print('\nCongrats, you have guessed the secret word!\n')
            break

        if guess not in listWord:
            print(guess,'is not in the list.\n')
            ticks+=1
            print(dashList)

        if guess in listWord:
            print('\nYou guessed a letter correctly.\n')
            for x in range(0,len(secretWord)):
                if guess == secretWord[x]:
                    dashList[x]=guess
            print(dashList)
            correct+=1

        if guess in guessedLetters and guess in listWord:
            print('\nYou have already guessed:',guess)

        if guess in guessedLetters and guess not in listWord:
            print('\nYou have already guessed:',guess)
            ticks-=1

        #arrayList of guessed letters
        guessedLetters.append(guess)

        print('\nTick counter:',ticks)
            

        if correct==count:
            print('Congrats, you have won!')
            

        if ticks==6:
            print('\nI\'m sorry, but you have lost')

main()


play=input('Would you like to play again?')
play.lower()
if play=="yes":
    main()
