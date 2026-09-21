package lox;

class Return extends RuntimeException {
    final Object value;

    Return(Object value) {
        super(null, null, false, false);    // 禁用一些不需要的jvm机制
        this.value = value;
    }
}
