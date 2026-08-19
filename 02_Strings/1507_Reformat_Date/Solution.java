class Solution {
    public String reformatDate(String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Jan", "01");
        map.put("Feb", "02");
        map.put("Mar", "03");
        map.put("Apr", "04");
        map.put("May", "05");
        map.put("Jun", "06");
        map.put("Jul", "07");
        map.put("Aug", "08");
        map.put("Sep", "09");
        map.put("Oct", "10");
        map.put("Nov", "11");
        map.put("Dec", "12");

        String words[] = date.split(" ");
        String day = words[0].substring(0, words[0].length()-2);
        String month = map.get(words[1]);
        String year = words[2];
        if(day.length() == 1){
            day = "0"+day;
        }

        return year + "-" + month + "-" + day;
    }
}