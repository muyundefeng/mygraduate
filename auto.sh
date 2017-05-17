# /bin/bash

##process file directory
##before running global project please run this script
file_path=('./afterUrls/' './Html/' './urls/' './url/')
# file_path=('./afterUrls/' './url/')
name=./url/
for var in ${file_path[@]};
do
    if [[ "$var" =  "$name" ]]; then
    	#statements
    	rm -r $var
    else
    	find $var  -name "*" -type f  -delete
    fi
done
