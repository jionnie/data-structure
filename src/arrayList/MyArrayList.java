package arrayList;

import java.util.Arrays;

public class MyArrayList<E> implements MyList<E> {
    private static final int DEFAULT_CAPACITY = 10; // 배열의 기본 용량
    private static final Object[] EMPTY_ELEMENT_DATA = {};
    
    Object[] elementData; // 실제 ArrayList에 들어오는 데이터를 담을 배열
    private int size; // elementData 배열에 담긴 요소의 개수

    public MyArrayList() {
        elementData = new Object[DEFAULT_CAPACITY]; // 매개값이 없으면 기본 용량으로 배열 생성
        size = 0;
    }

    public MyArrayList(int initCapacity) {
        if(initCapacity > 0) {
            elementData = new Object[initCapacity]; // 매개값이 0보다 크면 매개값 크기의 배열 생성
        } else if(initCapacity == 0) {
            elementData = EMPTY_ELEMENT_DATA; // 매개값이 0이면 공유 빈 배열 저장
        } else {
            throw new IllegalArgumentException("용량은 음수가 될 수 없습니다.");
        }

        size = 0;
    }
    
    // 인덱스를 검증하는 메소드
    // 클래스 내부에서만 사용하므로 private 접근 제한
    private void checkIndex(int index, boolean isForAdd) {
        if(index < 0 || index >= (isForAdd ? size + 1 : size)) {
            throw new IndexOutOfBoundsException("유효하지 않은 인덱스입니다.");
        }
    }

    // 클래스 내부에서만 사용하므로 private 접근 제한
    private void resize() {
        int oldCapacity = elementData.length; // 현재 배열의 크기를 얻음

        if (oldCapacity == size) {
            int newCapacity = oldCapacity * 2; // 데이터가 배열 크기를 초과할 경우 넉넉하게 공간을 유지하기 위해 현재 용량의 2배
            elementData = Arrays.copyOf(elementData, newCapacity);
        } else if((oldCapacity / 2) > size) {
            int newCapacity = oldCapacity / 2; // 데이터에 비해 배열 용량이 크면 현재 용량의 절반
            elementData = Arrays.copyOf(elementData, Math.max(newCapacity, DEFAULT_CAPACITY)); // newCapacity가 기본 용량 보다 작으면 기본 용량으로 설정
        } else if(Arrays.equals(elementData, EMPTY_ELEMENT_DATA)) {
            elementData = new Object[DEFAULT_CAPACITY]; // 들어있는 데이터가 0개일 경우 기본 용량으로 설정
        }
    }

    // 배열의 마지막에 요소 추가
    @Override
    public boolean add(E item) {
        resize();
        elementData[size] = item; // size가 원소의 개수이므로 결국 size 값이 마지막 요소의 다음 위치(초기 size는 0)
        size++; // 요소가 추가되었으므로 배열의 크기인 size도 증가
        return true;
    }

    // 배열의 특정 위치에 요소 추가
    @Override
    public void add(int index, E item) {
        // 인덱스가 음수이거나, size를 초과해 중간에 빈 공간이 있는 채로 삽입을 시도하는 경우
        // index == size는 마지막 자리에 요소 추가이므로 허용
        checkIndex(index, true);

        // 인덱스가 마지막 위치일 경우 add(E item) 재활용
        if(index == size) {
            add(item);
        } else { // 인덱스가 중간일 경우
            resize();
            System.arraycopy(elementData, index, elementData, index + 1, size - index); // 한 칸씩 뒤로 밀기
            elementData[index] = item; // 해당 인덱스에 요소 추가
            size++; // 요소가 추가되었으므로 배열의 크기인 size도 증가
        }
    }
    
    // 특정 요소가 들어있는 위치의 인덱스 얻기(순차 검색)
    @Override
    public int indexOf(Object item) {
        // null의 비교는 동등연산자로 해야되기 때문에 로직 분리
        if(item == null) {
            for(int i = 0; i < size; i++) {
                if(elementData[i] == null) { // 찾고자 하는 값이 중복으로 들어있으면 가장 먼저 검색되는 요소의 위치를 반환
                    return i;
                }
            }
        } else { // 매개변수가 null이 아닐 경우
            for(int i = 0; i < size; i++) {
                if(elementData[i].equals(item)) { // 찾고자 하는 값이 중복으로 들어있으면 가장 먼저 검색되는 요소의 위치를 반환
                    return i;
                }
            }
        }

        return -1; // 찾는 값이 없는 경우 -1을 반환
    }

    // 특정 요소가 들어있는 위치의 인덱스 얻기(역순 검색)
    @Override
    public int lastIndexOf(Object item) {
        if(item == null) {
            for(int i = size - 1; i >= 0; i--) {
                if(elementData[i] == null) { // 찾고자 하는 값이 중복으로 들어있으면 가장 먼저 검색되는 요소의 위치를 반환
                    return i;
                }
            }
        } else {
            for(int i = size - 1; i >= 0; i--) {
                if(elementData[i].equals(item)) { // 찾고자 하는 값이 중복으로 들어있으면 가장 먼저 검색되는 요소의 위치를 반환
                    return i;
                }
            }
        }

        return -1; // 찾는 값이 없는 경우 -1을 반환
    }

    // 특정 인덱스의 요소를 삭제
    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index, false);

        E item = (E) elementData[index]; // 반환할 값을 백업
        
        elementData[index] = null; // 명시적으로 요소를 null 처리해서 GC가 수거하도록 함(요소 제거)

        System.arraycopy(elementData, index + 1, elementData, index, size - index - 1);

        size--; // 요소가 삭제되었으므로 요소의 개수인 size 감소
        resize();

        return item; // 요소 반환
    }

    // 특정 값을 가지는 요소를 삭제
    @Override
    public boolean remove(Object item) {
        // 해당 요소가 존재하는 인덱스 얻기
        int index = indexOf(item);

        if(index == -1) {
            return false; // 만약 index가 -1이면 찾고자 하는 값이 존재하지 않으므로 메소드 종료
        }
        
        remove(index); // 인덱스를 찾았으면 remove(int index) 재활용해서 삭제, 중복된 값이 있으면 첫 번째로 검색된 요소 삭제

        return true;
    }

    // 요소 가져오기
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        checkIndex(index, false);

        return (E) elementData[index]; // 요소 값 반환
    }

    // 특정 위치에 있는 요소를 새 요소로 대체
    @Override
    public void set(int index, E item) {
        checkIndex(index, false);
        elementData[index] = item;
    }

    // 특정 요소가 리스트에 있는지 여부를 확인
    @Override
    public boolean contains(Object item) {
        return indexOf(item) >= 0; // -1 이면 요소를 찾지 못한 거고, 양수면 요소가 있는 것
    }

    // 요소의 개수를 반환
    // 외부에서 size 값에 접근할 수 없도록 private 접근 제한을 가지므로 메소드로 값 얻기
    @Override
    public int size() {
        return size;
    }

    // 리스트가 비어있는지(요소가 0개인지)를 반환
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // 요소를 모두 삭제
    @Override
    public void clear() {
        elementData = new Object[DEFAULT_CAPACITY]; // 기본 용량으로 새 배열 생성
        size = 0; // 모든 요소가 삭제되었으므로 size에도 0 대입
    }

    // 출력을 위해 Object 클래스의 toString 메소드 오버라이딩
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < size; i++) {
            sb.append(elementData[i]);
            if(i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println("ArrayList: " + list);

        list.add(0, 0);
        System.out.println("ArrayList: " + list);

        list.remove(4);
        System.out.println("ArrayList: " + list);

        System.out.println("1번 인덱스 요소: " + list.get(1));
        System.out.println("리스트의 요소 개수: " + list.size());
    }
}
