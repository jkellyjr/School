#John Kelly Jr
#Project 3

#welcome message
print('\t\t\tWelcome to the game rainbow explosion.')
print('\nThe idea of the game is to guess the secret word,'
       'without guessing \nsix wrong letters.')
print('\n\t\t\t\tGoodluck!\n')

def main():
    
    #secret word
    secretWord='jinx'

    #creates empty list
    listWord=[]

    #iterates through the string and adds it to an arrayList
    count=0
    for x in secretWord:
        count+=1
        listWord.append(x)

    print('\nThe word is',count,'letters long.\n')

    ticks=0
    correct=0
    dashList=['_' for x in range(0,count)]
    print(dashList)

    #plays game
    while ticks<6 and correct!=count:
        guess= input('\nPlease guess a letter: ')
        print('Guess:',guess,'\n')

        if guess not in listWord:
            print(guess,'is not in the list.\n')
            ticks+=1
            print(dashList)

        print('\nTick counter:',ticks)

        if guess in listWord:
            print('\nYou guessed a letter correctly.\n')
            location=listWord.index(guess)
            dashList[location]=guess
            print(dashList)
            correct+=1

        if correct==count:
            print('Congrats, you have won!')
            

        if ticks==6:
            print('\nI\'m sorry, but you have lost')

main()

play=input('Would you like to play again?')
play.lower()
if play=="yes":
    main()
