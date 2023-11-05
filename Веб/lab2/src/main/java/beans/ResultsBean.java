package beans;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultsBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Response> results;

    public ResultsBean() {
        this.results = new ArrayList<>();
    }

    public void addResult(Response result) {
        this.results.add(result);
    }


    public static class Response implements Serializable {
        @Serial
        private static final long serialVersionUID = 2L;
        private String x;
        private String y;
        private String r;
        private boolean isHit;

        public Response(int x, double y, int r, boolean isHit) {
            this.x = String.valueOf(x);
            this.y = String.valueOf(y);
            this.r = String.valueOf(r);
            this.isHit = isHit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Response result = (Response) o;
            return isHit == result.isHit &&
                    x.equals(result.x) &&
                    y.equals(result.y) &&
                    r.equals(result.r);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, r, isHit);
        }

        @Override
        public String toString() {
            return  "x='" + x + '\'' +
                    ", y='" + y + '\'' +
                    ", r='" + r + '\'' +
                    ", isHit=" + isHit;
        }
    }

}

