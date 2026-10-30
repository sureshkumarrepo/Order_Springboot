import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.Map;
import java.util.function.Function;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

class Sample{

    private String name;
    private int age;

    public Sample(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Sample() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isPrime(int num){
        if(num<1){
            return false;
        }
        for(int i=2;i<=Math.sqrt(num);i++){
            if(num%i==0){
                return false;
            }
        }
        return true;
    }

    public static void main(String arr[]){

        List<Sample> s3 = Arrays.asList(new Sample("suresh",25),new Sample("saranya",24),new Sample("Kalai",27));

        boolean  s5 = s3.stream().mapToInt(Sample::getAge).allMatch(n->n%2==0);

        boolean s6 = s3.stream().mapToDouble(Sample::getAge).noneMatch(n->n%2==0);

        boolean s7 = s3.stream().mapToInt(Sample::getAge).anyMatch(n->n%2==0);

        for(Sample s4 : s3){
            System.out.println(s4.getName() + " "+ s4.getAge() + " " + s5 + " " +s6+" "+s7);
        }
        List<String> s1 = Arrays.asList("Banan","cherry","grapefruit","Apple","banan");

        String s8 = "Suresh kumar";


        Map<String,Long> map = s1.stream().map(String::toUpperCase).collect(Collectors.groupingBy(e->e,Collectors.counting()));

        Map<Character,Long> s9 = s8.chars().mapToObj(c->(char)c).map(Character::toLowerCase).filter(b-> Character.isLetter(b) || Character.isLetterOrDigit(b)).collect(Collectors.groupingBy(b->b,Collectors.counting()));

        Map<Character,Long> s10 = s1.stream().flatMapToInt(String::chars).mapToObj(c->(char)c).map(Character::toLowerCase).filter(Character::isLetter).collect(Collectors.groupingBy(b->b,Collectors.counting()));

        s10.forEach((key,value)->System.out.println(key+" "+value));
        s9.forEach((key,value)->System.out.println(key+" "+value));

        for(Map.Entry<String,Long> k1 : map.entrySet()){
            System.out.println(k1.getValue());
        }

        map.forEach((key,value)->System.out.println(key + " "+value));

        Optional<String> s2 = s1.stream().min(Comparator.comparingInt(s->s.length()));

        System.out.println(s2);

        List<Integer> l1 = Arrays.asList(12,22,1,45,9,27);

        Sample s = new Sample();

        boolean l2 = l1.stream().anyMatch(s::isPrime);
        System.out.println(l2);

        List<String> c1 = Arrays.asList("suresh@2024","kumar ","kalai");

        List<String>c2 = c1.stream().map(d -> d.chars().filter(c->!Character.isLetter(c) && !Character.isDigit(c)).mapToObj(e->String.valueOf((char) e))
                .collect(Collectors.joining())).collect(Collectors.toList());

        for (String c3 : c2){
            System.out.println(c3);
        }

        int [] i1 = {23,45,56,2,57};

        Arrays.stream(i1).boxed().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        c1.stream().filter(Objects::nonNull).collect(Collectors.toList()).forEach(System.out::println);

        String ch = "banana";

        Set<Character> dup = new HashSet<>();

        ch.chars().mapToObj(c->(char) c).map(m-> !dup.add(m)?m:'@').collect(Collectors.toList()).forEach(System.out::println);

    }
}