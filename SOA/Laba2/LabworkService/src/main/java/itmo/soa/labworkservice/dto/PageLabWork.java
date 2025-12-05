package itmo.soa.labworkservice.dto;

import itmo.soa.labworkservice.model.LabWork;

import java.util.Collections;
import java.util.List;

public class PageLabWork {
    private List<LabWork> content = Collections.emptyList();
    private long totalElements;
    private int totalPages;
    private int size;
    private int number;
    private int numberOfElements;
    private boolean first;
    private boolean last;
    private boolean empty;

    public static PageLabWork of(List<LabWork> content, long totalElements, int page, int size) {
        PageLabWork pageLabWork = new PageLabWork();
        pageLabWork.setContent(List.copyOf(content));
        pageLabWork.setTotalElements(totalElements);
        int totalPages = size <= 0 ? 0 : (int) Math.ceil(totalElements / (double) size);
        pageLabWork.setTotalPages(totalPages);
        pageLabWork.setSize(size);
        pageLabWork.setNumber(page);
        pageLabWork.setNumberOfElements(content.size());
        pageLabWork.setFirst(page == 0);
        pageLabWork.setLast(totalPages == 0 || page >= totalPages - 1);
        pageLabWork.setEmpty(content.isEmpty());
        return pageLabWork;
    }

    public List<LabWork> getContent() {
        return content;
    }

    public void setContent(List<LabWork> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
