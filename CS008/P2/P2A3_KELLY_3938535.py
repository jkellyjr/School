#P2
#John Kelly Jr
#The program will allow the user to test their luck in a game of Rockymon.
#Lecture: Mon/Wed 4:30-5:45
#Lab: Wed 2-2:50

from random import randint

print('\t\t\t     Rockymon')
print('\t\t\tBy: John Kelly Jr')
name=input('\nPlease enter in your name: ')
print('\nHello,',name,'!')
print('\nRules of the game:')
print('   You will first roll two die. If you roll a 5 or 10 on your first try you win!')
print('   If you roll a 2, 4, or 11 on your first try you lose.')
print('   Otherwise, the number you roll becomes the \"match number\".')
print('   You must keep rolling until you roll the \"match numer\", which means you win,')
print('   or a 5, which means you lose.\n')
print('\t\t\t     Good luck!')
#Welcome message


dice = randint(2,12)#random number generator 
print('You rolled a:', dice)

#Game (1 roll)
if dice == 5 or dice == 10:
    print('Congrats, you win!')
elif dice==2 or dice==4 or dice==11:
    print('I\'m sorry, but you lose.')
else:
    print('Your new match number is:', dice)
