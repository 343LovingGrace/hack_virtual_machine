# hack_virtual_machine WIP

Kind of a toy stack based virtual machine implementation for interest / curiosity.

Example of use (see tests for more complex examples); 

(nums, bools represented as 16 bit 'binary' numbers) 

push constant 2 | stack -> 2 

push constant 7 | stack -> 2 -> 7  

add  | stack -> 9  

push constant 1  | stack -> 1 -> 9 

gt  | stack -> 'false' 
