    package nextstep.sessions.Image;

    public class CoverImage {
        private ImageSize size;
        private ImageType type;
        private ImageWidth width;
        private ImageHeight height;

        public CoverImage(int size, String type, int width, int height) {
            this(new ImageSize(size), ImageType.valueOf(type.toUpperCase()), new ImageWidth(width), new ImageHeight(height));
        }

        public CoverImage(int size, ImageType type, int width, int height) {
            this(new ImageSize(size), type, new ImageWidth(width), new ImageHeight(height));
        }

        public CoverImage(ImageSize size, String type, int width, int height) {
            this(size, ImageType.valueOf(type.toUpperCase()), new ImageWidth(width), new ImageHeight(height));
        }

        public CoverImage(ImageSize size, ImageType type, ImageWidth width, ImageHeight height) {
            ImageType.validateType(type);

            this.size = size;
            this.type = type;
            this.width = width;
            this.height = height;
        }
    }
