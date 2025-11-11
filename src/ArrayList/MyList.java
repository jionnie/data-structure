package ArrayList;

public interface MyList<E> {
    /**
     * add는 리스트에 요소가 들어가는, 타입이 중요한 동작이기 때문에 제네릭 타입으로 엄격하게 제어
     */
    boolean add(E item); // 요소를 추가
    void add(int index, E item); // 요소를 특정 위치에 추가

    /**
     * remove는 단순히 비교해서 같으면 제거하고 아니면 제거하지 않기 때문에 제네릭 사용 x
     */
    boolean remove(Object item); // 요소를 삭제
    E remove(int index); // 특정 위치에 있는 요소를 삭제

    /**
     * set은 리스트의 요소를 대체하는, 타입이 중요한 동작이기 때문에 제네릭 타입으로 엄격하게 제어
     */
    void set(int index, E item); // 특정 위치에 있는 요소를 새 요소로 대체

    E get(int index); // 요소 가져오기

    /**
     * contains, indexOf, lastIndexOf는 요소 추가가 아닌 비교이기 때문에 제네릭 타입이 아닌 Object 타입 사용
     */
    boolean contains(Object item); // 특정 요소가 리스트에 있는지 여부를 확인
    int indexOf(Object item); // 특정 요소가 몇 번째 위치에 있는지를 반환(순차 검색)
    int lastIndexOf(Object item); // 특정 요소가 몇 번째 위치에 있는지를 반환(역순 검색)
    
    int size(); // 요소의 개수를 반환
    boolean isEmpty(); // 리스트가 비어있는지(요소가 0개인지)를 반환
    
    void clear(); // 요소를 모두 삭제
}
