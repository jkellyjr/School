#P2
#John Kelly Jr
#The program will allow the user to test their luck in a game of Rockymon.
#Lecture: Mon/Wed 4:30-5:45
#Lab: Wed 2-2:50

from random import randint
import sys

print('\t\t\t     Rockymon')
print('\t\t\tBy: John Kelly Jr')
name=input('\nPlease enter in your name: ')
print('\nHello,',name,'!')
print('\nRules of the game:')
print('   You will first roll two die. If you roll a 5 or 10 on your first try you win!')
print('   If you roll a 2, 4, or 11 on your first try you lose.')
print('   Otherwise, the number you roll becomes the \"match number.\"')
print('   You must keep rolling until you roll the \"match numer,\" which means you win,')
print('   or a 5, which means you lose.\n')
print('\t\t\t     Good luck!')
#Welcome message

dice = randint(2,12)#random number generator (imported the function up top)

choice=int(input('\nEnter in a "1" if you would like to control the game manually, \n'
            'or enter a "2" for the computer to run the program:'))
      
if choice==1:#user controlled section
    print('\nYou rolled a:', dice)

    if dice == 5 or dice == 10:
        print('Congrats, you win!')
        sys.exit()
    elif dice==2 or dice==4 or dice==11:
        print('I\'m sorry, but you lose.')
        sys.exit()

    print('\nYour new match number is:', dice,'good luck!')


    playGame= input('\nWould you like to play the game again (yes or no)?\n')
    playGame.lower() #make string lower case

    while playGame=='yes':
        dice2 = randint(2,12)
        print('\nThe match number is: ',dice)
        print('You rolled a:', dice2)
        

        if dice2==dice:
            print('Congrats, you win!')
            sys.exit()
        elif dice2==5:
            print('I\'m sorry, but you lose.')
            sys.exit()
        else:
             print('I\'m sorry, but you did not get the match number.')

        playGame= input('\nWould you like to roll again (yes or no)?\n')
        playGame.lower()
            
    
elif choice==2:#computer ran section 
    dice = randint(2,12)    
    w=0
    l=0
    if dice == 5 or dice == 10:
            w+=1
    elif dice==2 or dice==4 or dice==11:
            l+=1

    tries=int(input('\nHow many times would you like to roll the dice?'))    
    for tries in range(0,tries-1):
        dice2 = randint(2,12)

        if dice2==dice:
            w+=1
            print('w')
        elif dice2==5:
            l+=1


    print('You won', w,'times, out of',tries+2,'tries.') 
          
