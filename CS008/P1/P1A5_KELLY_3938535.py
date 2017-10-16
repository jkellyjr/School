# Budget Calculator P1
# By: John Kelly Jr

name = input('Hello, please enter your name:')
print('\t \t \t Budget Caluclator \n'+'\t \t \t By:John Kelly Jr')
print('Hello',name +'!')
print('\nThe objective of this program is to create budget calculator \n'+
      'to help manage expenses.\n')

#user input
h_r = float(input('Please enter in your hourly rate:'))
x = '$%.2f' % h_r
print('Your hourly rate is:', x)
print(type(h_r))

o_r = float(input('\nPlease enter in your overtime rate:'))
x = '$%.2f' % o_r
print('Your overtime rate is:',x)
print(type(o_r))

r_h_w = float(input('\nPlease enter in your regular hours worked:'))
print('Your regular hours worked is:',r_h_w)
print(type(r_h_w))

o_h_w = float(input('\nPlease enter in your overtime hours worked:'))
print('Your overtime hours worked:', o_h_w)
print(type(o_h_w))

# calculations for goss pay
total_reg = (h_r * r_h_w)
total_overtime = (o_r * o_h_w)
gross_pay = (total_reg + total_overtime)
x = '$%.2f' % gross_pay
print('\n \t \tYour goss pay is:',x)



rent = float(input('\nPlease enter in your rent:'))
x = '$%.2f' % rent
print('Your rent is:', x)
print(type(rent))


#input to make percentages
electric = float(input('\nPlease enter the percentage of gross pay that goes towards your electric bill:'))
per_electric = electric / 100
print('Value that goes towards the electric bill:',per_electric)
print(type(electric))

water = float(input('\nPlease enter the percentage of gross pay that goes towards your water bill:'))
per_water = water / 100
print('Value that goes towards the water bill:',per_water)
print(type(water))
      
sewer = float(input('\nPlease enter the percentage of gross pay that goes towards your sewer bill:'))
per_sewer = sewer / 100
print('Value that goes towards the sewer bill:',per_sewer)
print(type(sewer))
      
gas = float(input('\nPlease enter the percentage of gross pay that goes towards your gas bill:'))
per_gas = gas / 100
print('Value that goes towards the gas bill:',per_gas)
print(type(gas))

#calculations for bills
mon_electric = (per_electric * gross_pay)
mon_water = (per_water * gross_pay)
mon_sewer = (per_sewer * gross_pay)
mon_gas = (per_gas * gross_pay)
total_bill = (mon_electric + mon_water + mon_sewer + mon_gas)

      

# the extras 
food = float(input('\nPlease enter in your food budget:'))
x = '$%.2f' % food
print('The amount you spend on food is:',x)
print(type(food))
      
entertainment = float(input('\nPlease enter in your entertainemnt budget:'))
x = '$%.2f' % entertainment 
print('The amount you spend on entertainment is: $',x)
print(type(entertainment))
      
car = float(input('\nPlease enter in your car budget:'))
x = '$%.2f' % car 
print('The amount that you spend on your car is: $', x)
print(type(car))

#final calculations
deductions = (rent + total_bill + food + entertainment + car)
net_pay = (gross_pay - deductions)

# correct output
x = '$%.2f' % net_pay
print('\n \t \tYour net pay is:',x)
