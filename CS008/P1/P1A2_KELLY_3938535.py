# Budget Calculator
# By: John Kelly Jr

print('\t \t \t Budget Caluclator \n'+'\t \t \t By:John Kelly Jr')
print('\nThe objective of this program is to create budget calculator \n'+
      'to help manage expenses.')

#hard coded variables
h_r = 10.5
print('Your hourly rate is: $',h_r)
print(type(h_r))

o_r = 21
print('Your overtime rate is: $',o_r)
print(type(o_r))

r_h_w = 30
print('Your regular hours worked is:',r_h_w)
print(type(r_h_w))

o_h_w = 5
print('Your overtime hours worked:', o_h_w)
print(type(o_h_w))

rent = 300
print('Your rent is: $',rent)
print(type(rent))


#calculate to make percentages
electric = .03
per_electric = electric * 100
print('Percentage that goes towards the electric bill:',per_electric,'%')
print(type(electric))

water = .02
per_water = water * 100
print('Percentage that goes towards the water bill:',per_water,'%')
print(type(water))
      
sewer = .01
per_sewer = sewer *100
print('Percentage that goes towards the sewer bill:',per_sewer,'%')
print(type(sewer))
      
gas = .025
per_gas = gas * 100
print('Percentage that goes towards the gas bill:',per_gas,'%')
print(type(gas))
      

food = 300
print('The amount you spend on food is: $',food)
print(type(food))
      
entertainment = 100
print('The amount you spend on entertainment is: $',entertainment)
print(type(entertainment))
      
car = 150
print('The amount that you spend on your car is: $', car)
print(type(car))
