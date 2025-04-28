# pyhton for merge sort





def merge_sort(arr,low,high):
    if(low<high):
        mid=(low+high)//2
        merge_sort(arr,low,mid)
        merge_sort(arr,mid+1,high)
        merge(arr,low,mid,high)
    

def merge(arr,low,mid,high):
    merged=[]
    i=low
    j=mid+1
    while(i<=mid and j<=high):
        if(arr[i]<arr[j]):
            merged.append(arr[i])
            i+=1
        else:
            merged.append(arr[j])
            j+=1
    while(i<=mid):
        merged.append(arr[i])
        i+=1
    while(j<=high):
        merged.append(arr[j])
        j+=1
    k=0
    while(k<len(merged)):
         arr[low+k]=merged[k]
         k+=1
    
    


arr=[6,5,4,3,2,1]


low=0
high=len(arr)-1

merge_sort(arr,low,high)

print(arr)

