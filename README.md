# hack_virtual_machine WIP

Kind of a toy stack based virtual machine implementation for interest / curiosity 

Example of use; 

push constant 2 
push constant 7 
add 
push constant 1 
gt 

(nums, bools represented as 16 bit 'binary' numbers) 
stack -> 
stack -> 2 
stack -> 2 -> 7 
stack -> 9 
stack -> 1 -> 9 
stack -> 'false' 

