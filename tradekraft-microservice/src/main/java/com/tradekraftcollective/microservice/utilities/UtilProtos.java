package com.tradekraftcollective.microservice.utilities;

/**
 * Created by brandonfeist on 11/16/17.
 */
public final class UtilProtos {
    private UtilProtos() {}
    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions(
                (com.google.protobuf.ExtensionRegistryLite) registry);
    }
    public interface UrlOrBuilder extends
            // @@protoc_insertion_point(interface_extends:spotify.Url)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>required .spotify.Url.Scheme scheme = 1;</code>
         */
        boolean hasScheme();
        /**
         * <code>required .spotify.Url.Scheme scheme = 1;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme getScheme();

        /**
         * <code>required string host = 2;</code>
         */
        boolean hasHost();
        /**
         * <code>required string host = 2;</code>
         */
        java.lang.String getHost();
        /**
         * <code>required string host = 2;</code>
         */
        com.google.protobuf.ByteString
        getHostBytes();

        /**
         * <code>required int32 port = 3;</code>
         */
        boolean hasPort();
        /**
         * <code>required int32 port = 3;</code>
         */
        int getPort();

        /**
         * <code>required string path = 4;</code>
         */
        boolean hasPath();
        /**
         * <code>required string path = 4;</code>
         */
        java.lang.String getPath();
        /**
         * <code>required string path = 4;</code>
         */
        com.google.protobuf.ByteString
        getPathBytes();

        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>
        getParametersList();
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getParameters(int index);
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        int getParametersCount();
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
        getParametersOrBuilderList();
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getParametersOrBuilder(
                int index);

        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part>
        getPartsList();
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part getParts(int index);
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        int getPartsCount();
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder>
        getPartsOrBuilderList();
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder getPartsOrBuilder(
                int index);

        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>
        getHeaderParametersList();
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getHeaderParameters(int index);
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        int getHeaderParametersCount();
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
        getHeaderParametersOrBuilderList();
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getHeaderParametersOrBuilder(
                int index);

        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>
        getBodyParametersList();
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getBodyParameters(int index);
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        int getBodyParametersCount();
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
        getBodyParametersOrBuilderList();
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getBodyParametersOrBuilder(
                int index);

        /**
         * <code>optional string jsonBody = 9;</code>
         */
        boolean hasJsonBody();
        /**
         * <code>optional string jsonBody = 9;</code>
         */
        java.lang.String getJsonBody();
        /**
         * <code>optional string jsonBody = 9;</code>
         */
        com.google.protobuf.ByteString
        getJsonBodyBytes();
    }
    /**
     * Protobuf type {@code spotify.Url}
     */
    public  static final class Url extends
            com.google.protobuf.GeneratedMessageV3 implements
            // @@protoc_insertion_point(message_implements:spotify.Url)
            UrlOrBuilder {
        private static final long serialVersionUID = 0L;
        // Use Url.newBuilder() to construct.
        private Url(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }
        private Url() {
            scheme_ = 0;
            host_ = "";
            port_ = 0;
            path_ = "";
            parameters_ = java.util.Collections.emptyList();
            parts_ = java.util.Collections.emptyList();
            headerParameters_ = java.util.Collections.emptyList();
            bodyParameters_ = java.util.Collections.emptyList();
            jsonBody_ = "";
        }

        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
            return this.unknownFields;
        }
        private Url(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(
                                    input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 8: {
                            int rawValue = input.readEnum();
                            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme value = com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                            } else {
                                bitField0_ |= 0x00000001;
                                scheme_ = rawValue;
                            }
                            break;
                        }
                        case 18: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000002;
                            host_ = bs;
                            break;
                        }
                        case 24: {
                            bitField0_ |= 0x00000004;
                            port_ = input.readInt32();
                            break;
                        }
                        case 34: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000008;
                            path_ = bs;
                            break;
                        }
                        case 42: {
                            if (!((mutable_bitField0_ & 0x00000010) == 0x00000010)) {
                                parameters_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>();
                                mutable_bitField0_ |= 0x00000010;
                            }
                            parameters_.add(
                                    input.readMessage(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.PARSER, extensionRegistry));
                            break;
                        }
                        case 50: {
                            if (!((mutable_bitField0_ & 0x00000020) == 0x00000020)) {
                                parts_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part>();
                                mutable_bitField0_ |= 0x00000020;
                            }
                            parts_.add(
                                    input.readMessage(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.PARSER, extensionRegistry));
                            break;
                        }
                        case 58: {
                            if (!((mutable_bitField0_ & 0x00000040) == 0x00000040)) {
                                headerParameters_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>();
                                mutable_bitField0_ |= 0x00000040;
                            }
                            headerParameters_.add(
                                    input.readMessage(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.PARSER, extensionRegistry));
                            break;
                        }
                        case 66: {
                            if (!((mutable_bitField0_ & 0x00000080) == 0x00000080)) {
                                bodyParameters_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>();
                                mutable_bitField0_ |= 0x00000080;
                            }
                            bodyParameters_.add(
                                    input.readMessage(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.PARSER, extensionRegistry));
                            break;
                        }
                        case 74: {
                            com.google.protobuf.ByteString bs = input.readBytes();
                            bitField0_ |= 0x00000010;
                            jsonBody_ = bs;
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e).setUnfinishedMessage(this);
            } finally {
                if (((mutable_bitField0_ & 0x00000010) == 0x00000010)) {
                    parameters_ = java.util.Collections.unmodifiableList(parameters_);
                }
                if (((mutable_bitField0_ & 0x00000020) == 0x00000020)) {
                    parts_ = java.util.Collections.unmodifiableList(parts_);
                }
                if (((mutable_bitField0_ & 0x00000040) == 0x00000040)) {
                    headerParameters_ = java.util.Collections.unmodifiableList(headerParameters_);
                }
                if (((mutable_bitField0_ & 0x00000080) == 0x00000080)) {
                    bodyParameters_ = java.util.Collections.unmodifiableList(bodyParameters_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }
        public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
            return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_descriptor;
        }

        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
            return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.class, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Builder.class);
        }

        /**
         * Protobuf enum {@code spotify.Url.Scheme}
         */
        public enum Scheme
                implements com.google.protobuf.ProtocolMessageEnum {
            /**
             * <code>HTTP = 0;</code>
             */
            HTTP(0),
            /**
             * <code>HTTPS = 1;</code>
             */
            HTTPS(1),
            ;

            /**
             * <code>HTTP = 0;</code>
             */
            public static final int HTTP_VALUE = 0;
            /**
             * <code>HTTPS = 1;</code>
             */
            public static final int HTTPS_VALUE = 1;


            public final int getNumber() {
                return value;
            }

            /**
             * @deprecated Use {@link #forNumber(int)} instead.
             */
            @java.lang.Deprecated
            public static Scheme valueOf(int value) {
                return forNumber(value);
            }

            public static Scheme forNumber(int value) {
                switch (value) {
                    case 0: return HTTP;
                    case 1: return HTTPS;
                    default: return null;
                }
            }

            public static com.google.protobuf.Internal.EnumLiteMap<Scheme>
            internalGetValueMap() {
                return internalValueMap;
            }
            private static final com.google.protobuf.Internal.EnumLiteMap<
                    Scheme> internalValueMap =
                    new com.google.protobuf.Internal.EnumLiteMap<Scheme>() {
                        public Scheme findValueByNumber(int number) {
                            return Scheme.forNumber(number);
                        }
                    };

            public final com.google.protobuf.Descriptors.EnumValueDescriptor
            getValueDescriptor() {
                return getDescriptor().getValues().get(ordinal());
            }
            public final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptorForType() {
                return getDescriptor();
            }
            public static final com.google.protobuf.Descriptors.EnumDescriptor
            getDescriptor() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.Url.getDescriptor().getEnumTypes().get(0);
            }

            private static final Scheme[] VALUES = values();

            public static Scheme valueOf(
                    com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != getDescriptor()) {
                    throw new java.lang.IllegalArgumentException(
                            "EnumValueDescriptor is not for this type.");
                }
                return VALUES[desc.getIndex()];
            }

            private final int value;

            private Scheme(int value) {
                this.value = value;
            }

            // @@protoc_insertion_point(enum_scope:spotify.Url.Scheme)
        }

        public interface ParameterOrBuilder extends
                // @@protoc_insertion_point(interface_extends:spotify.Url.Parameter)
                com.google.protobuf.MessageOrBuilder {

            /**
             * <code>optional string name = 1;</code>
             */
            boolean hasName();
            /**
             * <code>optional string name = 1;</code>
             */
            java.lang.String getName();
            /**
             * <code>optional string name = 1;</code>
             */
            com.google.protobuf.ByteString
            getNameBytes();

            /**
             * <code>optional string value = 2;</code>
             */
            boolean hasValue();
            /**
             * <code>optional string value = 2;</code>
             */
            java.lang.String getValue();
            /**
             * <code>optional string value = 2;</code>
             */
            com.google.protobuf.ByteString
            getValueBytes();
        }
        /**
         * Protobuf type {@code spotify.Url.Parameter}
         */
        public  static final class Parameter extends
                com.google.protobuf.GeneratedMessageV3 implements
                // @@protoc_insertion_point(message_implements:spotify.Url.Parameter)
                ParameterOrBuilder {
            private static final long serialVersionUID = 0L;
            // Use Parameter.newBuilder() to construct.
            private Parameter(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }
            private Parameter() {
                name_ = "";
                value_ = "";
            }

            @java.lang.Override
            public final com.google.protobuf.UnknownFieldSet
            getUnknownFields() {
                return this.unknownFields;
            }
            private Parameter(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                this();
                int mutable_bitField0_ = 0;
                com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                        com.google.protobuf.UnknownFieldSet.newBuilder();
                try {
                    boolean done = false;
                    while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            default: {
                                if (!parseUnknownField(
                                        input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                }
                                break;
                            }
                            case 10: {
                                com.google.protobuf.ByteString bs = input.readBytes();
                                bitField0_ |= 0x00000001;
                                name_ = bs;
                                break;
                            }
                            case 18: {
                                com.google.protobuf.ByteString bs = input.readBytes();
                                bitField0_ |= 0x00000002;
                                value_ = bs;
                                break;
                            }
                        }
                    }
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (java.io.IOException e) {
                    throw new com.google.protobuf.InvalidProtocolBufferException(
                            e).setUnfinishedMessage(this);
                } finally {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Parameter_descriptor;
            }

            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Parameter_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.class, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder.class);
            }

            private int bitField0_;
            public static final int NAME_FIELD_NUMBER = 1;
            private volatile java.lang.Object name_;
            /**
             * <code>optional string name = 1;</code>
             */
            public boolean hasName() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>optional string name = 1;</code>
             */
            public java.lang.String getName() {
                java.lang.Object ref = name_;
                if (ref instanceof java.lang.String) {
                    return (java.lang.String) ref;
                } else {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        name_ = s;
                    }
                    return s;
                }
            }
            /**
             * <code>optional string name = 1;</code>
             */
            public com.google.protobuf.ByteString
            getNameBytes() {
                java.lang.Object ref = name_;
                if (ref instanceof java.lang.String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    name_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            public static final int VALUE_FIELD_NUMBER = 2;
            private volatile java.lang.Object value_;
            /**
             * <code>optional string value = 2;</code>
             */
            public boolean hasValue() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>optional string value = 2;</code>
             */
            public java.lang.String getValue() {
                java.lang.Object ref = value_;
                if (ref instanceof java.lang.String) {
                    return (java.lang.String) ref;
                } else {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        value_ = s;
                    }
                    return s;
                }
            }
            /**
             * <code>optional string value = 2;</code>
             */
            public com.google.protobuf.ByteString
            getValueBytes() {
                java.lang.Object ref = value_;
                if (ref instanceof java.lang.String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    value_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            private byte memoizedIsInitialized = -1;
            public final boolean isInitialized() {
                byte isInitialized = memoizedIsInitialized;
                if (isInitialized == 1) return true;
                if (isInitialized == 0) return false;

                memoizedIsInitialized = 1;
                return true;
            }

            public void writeTo(com.google.protobuf.CodedOutputStream output)
                    throws java.io.IOException {
                if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
                }
                if (((bitField0_ & 0x00000002) == 0x00000002)) {
                    com.google.protobuf.GeneratedMessageV3.writeString(output, 2, value_);
                }
                unknownFields.writeTo(output);
            }

            public int getSerializedSize() {
                int size = memoizedSize;
                if (size != -1) return size;

                size = 0;
                if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
                }
                if (((bitField0_ & 0x00000002) == 0x00000002)) {
                    size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, value_);
                }
                size += unknownFields.getSerializedSize();
                memoizedSize = size;
                return size;
            }

            @java.lang.Override
            public boolean equals(final java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter)) {
                    return super.equals(obj);
                }
                com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter other = (com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter) obj;

                boolean result = true;
                result = result && (hasName() == other.hasName());
                if (hasName()) {
                    result = result && getName()
                            .equals(other.getName());
                }
                result = result && (hasValue() == other.hasValue());
                if (hasValue()) {
                    result = result && getValue()
                            .equals(other.getValue());
                }
                result = result && unknownFields.equals(other.unknownFields);
                return result;
            }

            @java.lang.Override
            public int hashCode() {
                if (memoizedHashCode != 0) {
                    return memoizedHashCode;
                }
                int hash = 41;
                hash = (19 * hash) + getDescriptor().hashCode();
                if (hasName()) {
                    hash = (37 * hash) + NAME_FIELD_NUMBER;
                    hash = (53 * hash) + getName().hashCode();
                }
                if (hasValue()) {
                    hash = (37 * hash) + VALUE_FIELD_NUMBER;
                    hash = (53 * hash) + getValue().hashCode();
                }
                hash = (29 * hash) + unknownFields.hashCode();
                memoizedHashCode = hash;
                return hash;
            }

            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    java.nio.ByteBuffer data)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    java.nio.ByteBuffer data,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    com.google.protobuf.ByteString data)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    com.google.protobuf.ByteString data,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(byte[] data)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    byte[] data,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(java.io.InputStream input)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    java.io.InputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseDelimitedFrom(java.io.InputStream input)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseDelimitedWithIOException(PARSER, input);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseDelimitedFrom(
                    java.io.InputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    com.google.protobuf.CodedInputStream input)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parseFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() { return newBuilder(); }
            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }
            public static Builder newBuilder(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter prototype) {
                return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            public Builder toBuilder() {
                return this == DEFAULT_INSTANCE
                        ? new Builder() : new Builder().mergeFrom(this);
            }

            @java.lang.Override
            protected Builder newBuilderForType(
                    com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                Builder builder = new Builder(parent);
                return builder;
            }
            /**
             * Protobuf type {@code spotify.Url.Parameter}
             */
            public static final class Builder extends
                    com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                    // @@protoc_insertion_point(builder_implements:spotify.Url.Parameter)
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder {
                public static final com.google.protobuf.Descriptors.Descriptor
                getDescriptor() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Parameter_descriptor;
                }

                protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
                internalGetFieldAccessorTable() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Parameter_fieldAccessorTable
                            .ensureFieldAccessorsInitialized(
                                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.class, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder.class);
                }

                // Construct using com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.newBuilder()
                private Builder() {
                    maybeForceBuilderInitialization();
                }

                private Builder(
                        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    maybeForceBuilderInitialization();
                }
                private void maybeForceBuilderInitialization() {
                    if (com.google.protobuf.GeneratedMessageV3
                            .alwaysUseFieldBuilders) {
                    }
                }
                public Builder clear() {
                    super.clear();
                    name_ = "";
                    bitField0_ = (bitField0_ & ~0x00000001);
                    value_ = "";
                    bitField0_ = (bitField0_ & ~0x00000002);
                    return this;
                }

                public com.google.protobuf.Descriptors.Descriptor
                getDescriptorForType() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Parameter_descriptor;
                }

                public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getDefaultInstanceForType() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance();
                }

                public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter build() {
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter result = buildPartial();
                    if (!result.isInitialized()) {
                        throw newUninitializedMessageException(result);
                    }
                    return result;
                }

                public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter buildPartial() {
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter result = new com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter(this);
                    int from_bitField0_ = bitField0_;
                    int to_bitField0_ = 0;
                    if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                        to_bitField0_ |= 0x00000001;
                    }
                    result.name_ = name_;
                    if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                        to_bitField0_ |= 0x00000002;
                    }
                    result.value_ = value_;
                    result.bitField0_ = to_bitField0_;
                    onBuilt();
                    return result;
                }

                public Builder clone() {
                    return (Builder) super.clone();
                }
                public Builder setField(
                        com.google.protobuf.Descriptors.FieldDescriptor field,
                        java.lang.Object value) {
                    return (Builder) super.setField(field, value);
                }
                public Builder clearField(
                        com.google.protobuf.Descriptors.FieldDescriptor field) {
                    return (Builder) super.clearField(field);
                }
                public Builder clearOneof(
                        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                    return (Builder) super.clearOneof(oneof);
                }
                public Builder setRepeatedField(
                        com.google.protobuf.Descriptors.FieldDescriptor field,
                        int index, java.lang.Object value) {
                    return (Builder) super.setRepeatedField(field, index, value);
                }
                public Builder addRepeatedField(
                        com.google.protobuf.Descriptors.FieldDescriptor field,
                        java.lang.Object value) {
                    return (Builder) super.addRepeatedField(field, value);
                }
                public Builder mergeFrom(com.google.protobuf.Message other) {
                    if (other instanceof com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter) {
                        return mergeFrom((com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter)other);
                    } else {
                        super.mergeFrom(other);
                        return this;
                    }
                }

                public Builder mergeFrom(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter other) {
                    if (other == com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance()) return this;
                    if (other.hasName()) {
                        bitField0_ |= 0x00000001;
                        name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasValue()) {
                        bitField0_ |= 0x00000002;
                        value_ = other.value_;
                        onChanged();
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    onChanged();
                    return this;
                }

                public final boolean isInitialized() {
                    return true;
                }

                public Builder mergeFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws java.io.IOException {
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter parsedMessage = null;
                    try {
                        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                        parsedMessage = (com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } finally {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }
                private int bitField0_;

                private java.lang.Object name_ = "";
                /**
                 * <code>optional string name = 1;</code>
                 */
                public boolean hasName() {
                    return ((bitField0_ & 0x00000001) == 0x00000001);
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public java.lang.String getName() {
                    java.lang.Object ref = name_;
                    if (!(ref instanceof java.lang.String)) {
                        com.google.protobuf.ByteString bs =
                                (com.google.protobuf.ByteString) ref;
                        java.lang.String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            name_ = s;
                        }
                        return s;
                    } else {
                        return (java.lang.String) ref;
                    }
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public com.google.protobuf.ByteString
                getNameBytes() {
                    java.lang.Object ref = name_;
                    if (ref instanceof String) {
                        com.google.protobuf.ByteString b =
                                com.google.protobuf.ByteString.copyFromUtf8(
                                        (java.lang.String) ref);
                        name_ = b;
                        return b;
                    } else {
                        return (com.google.protobuf.ByteString) ref;
                    }
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public Builder setName(
                        java.lang.String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000001;
                    name_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public Builder clearName() {
                    bitField0_ = (bitField0_ & ~0x00000001);
                    name_ = getDefaultInstance().getName();
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public Builder setNameBytes(
                        com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000001;
                    name_ = value;
                    onChanged();
                    return this;
                }

                private java.lang.Object value_ = "";
                /**
                 * <code>optional string value = 2;</code>
                 */
                public boolean hasValue() {
                    return ((bitField0_ & 0x00000002) == 0x00000002);
                }
                /**
                 * <code>optional string value = 2;</code>
                 */
                public java.lang.String getValue() {
                    java.lang.Object ref = value_;
                    if (!(ref instanceof java.lang.String)) {
                        com.google.protobuf.ByteString bs =
                                (com.google.protobuf.ByteString) ref;
                        java.lang.String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            value_ = s;
                        }
                        return s;
                    } else {
                        return (java.lang.String) ref;
                    }
                }
                /**
                 * <code>optional string value = 2;</code>
                 */
                public com.google.protobuf.ByteString
                getValueBytes() {
                    java.lang.Object ref = value_;
                    if (ref instanceof String) {
                        com.google.protobuf.ByteString b =
                                com.google.protobuf.ByteString.copyFromUtf8(
                                        (java.lang.String) ref);
                        value_ = b;
                        return b;
                    } else {
                        return (com.google.protobuf.ByteString) ref;
                    }
                }
                /**
                 * <code>optional string value = 2;</code>
                 */
                public Builder setValue(
                        java.lang.String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000002;
                    value_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string value = 2;</code>
                 */
                public Builder clearValue() {
                    bitField0_ = (bitField0_ & ~0x00000002);
                    value_ = getDefaultInstance().getValue();
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string value = 2;</code>
                 */
                public Builder setValueBytes(
                        com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000002;
                    value_ = value;
                    onChanged();
                    return this;
                }
                public final Builder setUnknownFields(
                        final com.google.protobuf.UnknownFieldSet unknownFields) {
                    return super.setUnknownFields(unknownFields);
                }

                public final Builder mergeUnknownFields(
                        final com.google.protobuf.UnknownFieldSet unknownFields) {
                    return super.mergeUnknownFields(unknownFields);
                }


                // @@protoc_insertion_point(builder_scope:spotify.Url.Parameter)
            }

            // @@protoc_insertion_point(class_scope:spotify.Url.Parameter)
            private static final com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter DEFAULT_INSTANCE;
            static {
                DEFAULT_INSTANCE = new com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter();
            }

            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            @java.lang.Deprecated public static final com.google.protobuf.Parser<Parameter>
                    PARSER = new com.google.protobuf.AbstractParser<Parameter>() {
                public Parameter parsePartialFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
                    return new Parameter(input, extensionRegistry);
                }
            };

            public static com.google.protobuf.Parser<Parameter> parser() {
                return PARSER;
            }

            @java.lang.Override
            public com.google.protobuf.Parser<Parameter> getParserForType() {
                return PARSER;
            }

            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

        }

        public interface PartOrBuilder extends
                // @@protoc_insertion_point(interface_extends:spotify.Url.Part)
                com.google.protobuf.MessageOrBuilder {

            /**
             * <code>optional string name = 1;</code>
             */
            boolean hasName();
            /**
             * <code>optional string name = 1;</code>
             */
            java.lang.String getName();
            /**
             * <code>optional string name = 1;</code>
             */
            com.google.protobuf.ByteString
            getNameBytes();

            /**
             * <code>optional string filename = 2;</code>
             */
            boolean hasFilename();
            /**
             * <code>optional string filename = 2;</code>
             */
            java.lang.String getFilename();
            /**
             * <code>optional string filename = 2;</code>
             */
            com.google.protobuf.ByteString
            getFilenameBytes();

            /**
             * <code>optional string content_type = 3;</code>
             */
            boolean hasContentType();
            /**
             * <code>optional string content_type = 3;</code>
             */
            java.lang.String getContentType();
            /**
             * <code>optional string content_type = 3;</code>
             */
            com.google.protobuf.ByteString
            getContentTypeBytes();

            /**
             * <code>optional string charset = 4;</code>
             */
            boolean hasCharset();
            /**
             * <code>optional string charset = 4;</code>
             */
            java.lang.String getCharset();
            /**
             * <code>optional string charset = 4;</code>
             */
            com.google.protobuf.ByteString
            getCharsetBytes();

            /**
             * <code>optional bytes value = 5;</code>
             */
            boolean hasValue();
            /**
             * <code>optional bytes value = 5;</code>
             */
            com.google.protobuf.ByteString getValue();
        }
        /**
         * Protobuf type {@code spotify.Url.Part}
         */
        public  static final class Part extends
                com.google.protobuf.GeneratedMessageV3 implements
                // @@protoc_insertion_point(message_implements:spotify.Url.Part)
                PartOrBuilder {
            private static final long serialVersionUID = 0L;
            // Use Part.newBuilder() to construct.
            private Part(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
                super(builder);
            }
            private Part() {
                name_ = "";
                filename_ = "";
                contentType_ = "";
                charset_ = "";
                value_ = com.google.protobuf.ByteString.EMPTY;
            }

            @java.lang.Override
            public final com.google.protobuf.UnknownFieldSet
            getUnknownFields() {
                return this.unknownFields;
            }
            private Part(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                this();
                int mutable_bitField0_ = 0;
                com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                        com.google.protobuf.UnknownFieldSet.newBuilder();
                try {
                    boolean done = false;
                    while (!done) {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            default: {
                                if (!parseUnknownField(
                                        input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                }
                                break;
                            }
                            case 10: {
                                com.google.protobuf.ByteString bs = input.readBytes();
                                bitField0_ |= 0x00000001;
                                name_ = bs;
                                break;
                            }
                            case 18: {
                                com.google.protobuf.ByteString bs = input.readBytes();
                                bitField0_ |= 0x00000002;
                                filename_ = bs;
                                break;
                            }
                            case 26: {
                                com.google.protobuf.ByteString bs = input.readBytes();
                                bitField0_ |= 0x00000004;
                                contentType_ = bs;
                                break;
                            }
                            case 34: {
                                com.google.protobuf.ByteString bs = input.readBytes();
                                bitField0_ |= 0x00000008;
                                charset_ = bs;
                                break;
                            }
                            case 42: {
                                bitField0_ |= 0x00000010;
                                value_ = input.readBytes();
                                break;
                            }
                        }
                    }
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (java.io.IOException e) {
                    throw new com.google.protobuf.InvalidProtocolBufferException(
                            e).setUnfinishedMessage(this);
                } finally {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Part_descriptor;
            }

            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Part_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.class, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder.class);
            }

            private int bitField0_;
            public static final int NAME_FIELD_NUMBER = 1;
            private volatile java.lang.Object name_;
            /**
             * <code>optional string name = 1;</code>
             */
            public boolean hasName() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>optional string name = 1;</code>
             */
            public java.lang.String getName() {
                java.lang.Object ref = name_;
                if (ref instanceof java.lang.String) {
                    return (java.lang.String) ref;
                } else {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        name_ = s;
                    }
                    return s;
                }
            }
            /**
             * <code>optional string name = 1;</code>
             */
            public com.google.protobuf.ByteString
            getNameBytes() {
                java.lang.Object ref = name_;
                if (ref instanceof java.lang.String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    name_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            public static final int FILENAME_FIELD_NUMBER = 2;
            private volatile java.lang.Object filename_;
            /**
             * <code>optional string filename = 2;</code>
             */
            public boolean hasFilename() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>optional string filename = 2;</code>
             */
            public java.lang.String getFilename() {
                java.lang.Object ref = filename_;
                if (ref instanceof java.lang.String) {
                    return (java.lang.String) ref;
                } else {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        filename_ = s;
                    }
                    return s;
                }
            }
            /**
             * <code>optional string filename = 2;</code>
             */
            public com.google.protobuf.ByteString
            getFilenameBytes() {
                java.lang.Object ref = filename_;
                if (ref instanceof java.lang.String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    filename_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            public static final int CONTENT_TYPE_FIELD_NUMBER = 3;
            private volatile java.lang.Object contentType_;
            /**
             * <code>optional string content_type = 3;</code>
             */
            public boolean hasContentType() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }
            /**
             * <code>optional string content_type = 3;</code>
             */
            public java.lang.String getContentType() {
                java.lang.Object ref = contentType_;
                if (ref instanceof java.lang.String) {
                    return (java.lang.String) ref;
                } else {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        contentType_ = s;
                    }
                    return s;
                }
            }
            /**
             * <code>optional string content_type = 3;</code>
             */
            public com.google.protobuf.ByteString
            getContentTypeBytes() {
                java.lang.Object ref = contentType_;
                if (ref instanceof java.lang.String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    contentType_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            public static final int CHARSET_FIELD_NUMBER = 4;
            private volatile java.lang.Object charset_;
            /**
             * <code>optional string charset = 4;</code>
             */
            public boolean hasCharset() {
                return ((bitField0_ & 0x00000008) == 0x00000008);
            }
            /**
             * <code>optional string charset = 4;</code>
             */
            public java.lang.String getCharset() {
                java.lang.Object ref = charset_;
                if (ref instanceof java.lang.String) {
                    return (java.lang.String) ref;
                } else {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        charset_ = s;
                    }
                    return s;
                }
            }
            /**
             * <code>optional string charset = 4;</code>
             */
            public com.google.protobuf.ByteString
            getCharsetBytes() {
                java.lang.Object ref = charset_;
                if (ref instanceof java.lang.String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    charset_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }

            public static final int VALUE_FIELD_NUMBER = 5;
            private com.google.protobuf.ByteString value_;
            /**
             * <code>optional bytes value = 5;</code>
             */
            public boolean hasValue() {
                return ((bitField0_ & 0x00000010) == 0x00000010);
            }
            /**
             * <code>optional bytes value = 5;</code>
             */
            public com.google.protobuf.ByteString getValue() {
                return value_;
            }

            private byte memoizedIsInitialized = -1;
            public final boolean isInitialized() {
                byte isInitialized = memoizedIsInitialized;
                if (isInitialized == 1) return true;
                if (isInitialized == 0) return false;

                memoizedIsInitialized = 1;
                return true;
            }

            public void writeTo(com.google.protobuf.CodedOutputStream output)
                    throws java.io.IOException {
                if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    com.google.protobuf.GeneratedMessageV3.writeString(output, 1, name_);
                }
                if (((bitField0_ & 0x00000002) == 0x00000002)) {
                    com.google.protobuf.GeneratedMessageV3.writeString(output, 2, filename_);
                }
                if (((bitField0_ & 0x00000004) == 0x00000004)) {
                    com.google.protobuf.GeneratedMessageV3.writeString(output, 3, contentType_);
                }
                if (((bitField0_ & 0x00000008) == 0x00000008)) {
                    com.google.protobuf.GeneratedMessageV3.writeString(output, 4, charset_);
                }
                if (((bitField0_ & 0x00000010) == 0x00000010)) {
                    output.writeBytes(5, value_);
                }
                unknownFields.writeTo(output);
            }

            public int getSerializedSize() {
                int size = memoizedSize;
                if (size != -1) return size;

                size = 0;
                if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, name_);
                }
                if (((bitField0_ & 0x00000002) == 0x00000002)) {
                    size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, filename_);
                }
                if (((bitField0_ & 0x00000004) == 0x00000004)) {
                    size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, contentType_);
                }
                if (((bitField0_ & 0x00000008) == 0x00000008)) {
                    size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, charset_);
                }
                if (((bitField0_ & 0x00000010) == 0x00000010)) {
                    size += com.google.protobuf.CodedOutputStream
                            .computeBytesSize(5, value_);
                }
                size += unknownFields.getSerializedSize();
                memoizedSize = size;
                return size;
            }

            @java.lang.Override
            public boolean equals(final java.lang.Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part)) {
                    return super.equals(obj);
                }
                com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part other = (com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part) obj;

                boolean result = true;
                result = result && (hasName() == other.hasName());
                if (hasName()) {
                    result = result && getName()
                            .equals(other.getName());
                }
                result = result && (hasFilename() == other.hasFilename());
                if (hasFilename()) {
                    result = result && getFilename()
                            .equals(other.getFilename());
                }
                result = result && (hasContentType() == other.hasContentType());
                if (hasContentType()) {
                    result = result && getContentType()
                            .equals(other.getContentType());
                }
                result = result && (hasCharset() == other.hasCharset());
                if (hasCharset()) {
                    result = result && getCharset()
                            .equals(other.getCharset());
                }
                result = result && (hasValue() == other.hasValue());
                if (hasValue()) {
                    result = result && getValue()
                            .equals(other.getValue());
                }
                result = result && unknownFields.equals(other.unknownFields);
                return result;
            }

            @java.lang.Override
            public int hashCode() {
                if (memoizedHashCode != 0) {
                    return memoizedHashCode;
                }
                int hash = 41;
                hash = (19 * hash) + getDescriptor().hashCode();
                if (hasName()) {
                    hash = (37 * hash) + NAME_FIELD_NUMBER;
                    hash = (53 * hash) + getName().hashCode();
                }
                if (hasFilename()) {
                    hash = (37 * hash) + FILENAME_FIELD_NUMBER;
                    hash = (53 * hash) + getFilename().hashCode();
                }
                if (hasContentType()) {
                    hash = (37 * hash) + CONTENT_TYPE_FIELD_NUMBER;
                    hash = (53 * hash) + getContentType().hashCode();
                }
                if (hasCharset()) {
                    hash = (37 * hash) + CHARSET_FIELD_NUMBER;
                    hash = (53 * hash) + getCharset().hashCode();
                }
                if (hasValue()) {
                    hash = (37 * hash) + VALUE_FIELD_NUMBER;
                    hash = (53 * hash) + getValue().hashCode();
                }
                hash = (29 * hash) + unknownFields.hashCode();
                memoizedHashCode = hash;
                return hash;
            }

            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    java.nio.ByteBuffer data)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    java.nio.ByteBuffer data,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    com.google.protobuf.ByteString data)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    com.google.protobuf.ByteString data,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(byte[] data)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    byte[] data,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return PARSER.parseFrom(data, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(java.io.InputStream input)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    java.io.InputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseDelimitedFrom(java.io.InputStream input)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseDelimitedWithIOException(PARSER, input);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseDelimitedFrom(
                    java.io.InputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    com.google.protobuf.CodedInputStream input)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input);
            }
            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parseFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                return com.google.protobuf.GeneratedMessageV3
                        .parseWithIOException(PARSER, input, extensionRegistry);
            }

            public Builder newBuilderForType() { return newBuilder(); }
            public static Builder newBuilder() {
                return DEFAULT_INSTANCE.toBuilder();
            }
            public static Builder newBuilder(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part prototype) {
                return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
            }
            public Builder toBuilder() {
                return this == DEFAULT_INSTANCE
                        ? new Builder() : new Builder().mergeFrom(this);
            }

            @java.lang.Override
            protected Builder newBuilderForType(
                    com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                Builder builder = new Builder(parent);
                return builder;
            }
            /**
             * Protobuf type {@code spotify.Url.Part}
             */
            public static final class Builder extends
                    com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                    // @@protoc_insertion_point(builder_implements:spotify.Url.Part)
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder {
                public static final com.google.protobuf.Descriptors.Descriptor
                getDescriptor() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Part_descriptor;
                }

                protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
                internalGetFieldAccessorTable() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Part_fieldAccessorTable
                            .ensureFieldAccessorsInitialized(
                                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.class, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder.class);
                }

                // Construct using com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.newBuilder()
                private Builder() {
                    maybeForceBuilderInitialization();
                }

                private Builder(
                        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                    super(parent);
                    maybeForceBuilderInitialization();
                }
                private void maybeForceBuilderInitialization() {
                    if (com.google.protobuf.GeneratedMessageV3
                            .alwaysUseFieldBuilders) {
                    }
                }
                public Builder clear() {
                    super.clear();
                    name_ = "";
                    bitField0_ = (bitField0_ & ~0x00000001);
                    filename_ = "";
                    bitField0_ = (bitField0_ & ~0x00000002);
                    contentType_ = "";
                    bitField0_ = (bitField0_ & ~0x00000004);
                    charset_ = "";
                    bitField0_ = (bitField0_ & ~0x00000008);
                    value_ = com.google.protobuf.ByteString.EMPTY;
                    bitField0_ = (bitField0_ & ~0x00000010);
                    return this;
                }

                public com.google.protobuf.Descriptors.Descriptor
                getDescriptorForType() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_Part_descriptor;
                }

                public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part getDefaultInstanceForType() {
                    return com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.getDefaultInstance();
                }

                public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part build() {
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part result = buildPartial();
                    if (!result.isInitialized()) {
                        throw newUninitializedMessageException(result);
                    }
                    return result;
                }

                public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part buildPartial() {
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part result = new com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part(this);
                    int from_bitField0_ = bitField0_;
                    int to_bitField0_ = 0;
                    if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                        to_bitField0_ |= 0x00000001;
                    }
                    result.name_ = name_;
                    if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                        to_bitField0_ |= 0x00000002;
                    }
                    result.filename_ = filename_;
                    if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                        to_bitField0_ |= 0x00000004;
                    }
                    result.contentType_ = contentType_;
                    if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
                        to_bitField0_ |= 0x00000008;
                    }
                    result.charset_ = charset_;
                    if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
                        to_bitField0_ |= 0x00000010;
                    }
                    result.value_ = value_;
                    result.bitField0_ = to_bitField0_;
                    onBuilt();
                    return result;
                }

                public Builder clone() {
                    return (Builder) super.clone();
                }
                public Builder setField(
                        com.google.protobuf.Descriptors.FieldDescriptor field,
                        java.lang.Object value) {
                    return (Builder) super.setField(field, value);
                }
                public Builder clearField(
                        com.google.protobuf.Descriptors.FieldDescriptor field) {
                    return (Builder) super.clearField(field);
                }
                public Builder clearOneof(
                        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                    return (Builder) super.clearOneof(oneof);
                }
                public Builder setRepeatedField(
                        com.google.protobuf.Descriptors.FieldDescriptor field,
                        int index, java.lang.Object value) {
                    return (Builder) super.setRepeatedField(field, index, value);
                }
                public Builder addRepeatedField(
                        com.google.protobuf.Descriptors.FieldDescriptor field,
                        java.lang.Object value) {
                    return (Builder) super.addRepeatedField(field, value);
                }
                public Builder mergeFrom(com.google.protobuf.Message other) {
                    if (other instanceof com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part) {
                        return mergeFrom((com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part)other);
                    } else {
                        super.mergeFrom(other);
                        return this;
                    }
                }

                public Builder mergeFrom(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part other) {
                    if (other == com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.getDefaultInstance()) return this;
                    if (other.hasName()) {
                        bitField0_ |= 0x00000001;
                        name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasFilename()) {
                        bitField0_ |= 0x00000002;
                        filename_ = other.filename_;
                        onChanged();
                    }
                    if (other.hasContentType()) {
                        bitField0_ |= 0x00000004;
                        contentType_ = other.contentType_;
                        onChanged();
                    }
                    if (other.hasCharset()) {
                        bitField0_ |= 0x00000008;
                        charset_ = other.charset_;
                        onChanged();
                    }
                    if (other.hasValue()) {
                        setValue(other.getValue());
                    }
                    this.mergeUnknownFields(other.unknownFields);
                    onChanged();
                    return this;
                }

                public final boolean isInitialized() {
                    return true;
                }

                public Builder mergeFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws java.io.IOException {
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part parsedMessage = null;
                    try {
                        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                        parsedMessage = (com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part) e.getUnfinishedMessage();
                        throw e.unwrapIOException();
                    } finally {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                    }
                    return this;
                }
                private int bitField0_;

                private java.lang.Object name_ = "";
                /**
                 * <code>optional string name = 1;</code>
                 */
                public boolean hasName() {
                    return ((bitField0_ & 0x00000001) == 0x00000001);
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public java.lang.String getName() {
                    java.lang.Object ref = name_;
                    if (!(ref instanceof java.lang.String)) {
                        com.google.protobuf.ByteString bs =
                                (com.google.protobuf.ByteString) ref;
                        java.lang.String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            name_ = s;
                        }
                        return s;
                    } else {
                        return (java.lang.String) ref;
                    }
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public com.google.protobuf.ByteString
                getNameBytes() {
                    java.lang.Object ref = name_;
                    if (ref instanceof String) {
                        com.google.protobuf.ByteString b =
                                com.google.protobuf.ByteString.copyFromUtf8(
                                        (java.lang.String) ref);
                        name_ = b;
                        return b;
                    } else {
                        return (com.google.protobuf.ByteString) ref;
                    }
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public Builder setName(
                        java.lang.String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000001;
                    name_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public Builder clearName() {
                    bitField0_ = (bitField0_ & ~0x00000001);
                    name_ = getDefaultInstance().getName();
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string name = 1;</code>
                 */
                public Builder setNameBytes(
                        com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000001;
                    name_ = value;
                    onChanged();
                    return this;
                }

                private java.lang.Object filename_ = "";
                /**
                 * <code>optional string filename = 2;</code>
                 */
                public boolean hasFilename() {
                    return ((bitField0_ & 0x00000002) == 0x00000002);
                }
                /**
                 * <code>optional string filename = 2;</code>
                 */
                public java.lang.String getFilename() {
                    java.lang.Object ref = filename_;
                    if (!(ref instanceof java.lang.String)) {
                        com.google.protobuf.ByteString bs =
                                (com.google.protobuf.ByteString) ref;
                        java.lang.String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            filename_ = s;
                        }
                        return s;
                    } else {
                        return (java.lang.String) ref;
                    }
                }
                /**
                 * <code>optional string filename = 2;</code>
                 */
                public com.google.protobuf.ByteString
                getFilenameBytes() {
                    java.lang.Object ref = filename_;
                    if (ref instanceof String) {
                        com.google.protobuf.ByteString b =
                                com.google.protobuf.ByteString.copyFromUtf8(
                                        (java.lang.String) ref);
                        filename_ = b;
                        return b;
                    } else {
                        return (com.google.protobuf.ByteString) ref;
                    }
                }
                /**
                 * <code>optional string filename = 2;</code>
                 */
                public Builder setFilename(
                        java.lang.String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000002;
                    filename_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string filename = 2;</code>
                 */
                public Builder clearFilename() {
                    bitField0_ = (bitField0_ & ~0x00000002);
                    filename_ = getDefaultInstance().getFilename();
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string filename = 2;</code>
                 */
                public Builder setFilenameBytes(
                        com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000002;
                    filename_ = value;
                    onChanged();
                    return this;
                }

                private java.lang.Object contentType_ = "";
                /**
                 * <code>optional string content_type = 3;</code>
                 */
                public boolean hasContentType() {
                    return ((bitField0_ & 0x00000004) == 0x00000004);
                }
                /**
                 * <code>optional string content_type = 3;</code>
                 */
                public java.lang.String getContentType() {
                    java.lang.Object ref = contentType_;
                    if (!(ref instanceof java.lang.String)) {
                        com.google.protobuf.ByteString bs =
                                (com.google.protobuf.ByteString) ref;
                        java.lang.String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            contentType_ = s;
                        }
                        return s;
                    } else {
                        return (java.lang.String) ref;
                    }
                }
                /**
                 * <code>optional string content_type = 3;</code>
                 */
                public com.google.protobuf.ByteString
                getContentTypeBytes() {
                    java.lang.Object ref = contentType_;
                    if (ref instanceof String) {
                        com.google.protobuf.ByteString b =
                                com.google.protobuf.ByteString.copyFromUtf8(
                                        (java.lang.String) ref);
                        contentType_ = b;
                        return b;
                    } else {
                        return (com.google.protobuf.ByteString) ref;
                    }
                }
                /**
                 * <code>optional string content_type = 3;</code>
                 */
                public Builder setContentType(
                        java.lang.String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000004;
                    contentType_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string content_type = 3;</code>
                 */
                public Builder clearContentType() {
                    bitField0_ = (bitField0_ & ~0x00000004);
                    contentType_ = getDefaultInstance().getContentType();
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string content_type = 3;</code>
                 */
                public Builder setContentTypeBytes(
                        com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000004;
                    contentType_ = value;
                    onChanged();
                    return this;
                }

                private java.lang.Object charset_ = "";
                /**
                 * <code>optional string charset = 4;</code>
                 */
                public boolean hasCharset() {
                    return ((bitField0_ & 0x00000008) == 0x00000008);
                }
                /**
                 * <code>optional string charset = 4;</code>
                 */
                public java.lang.String getCharset() {
                    java.lang.Object ref = charset_;
                    if (!(ref instanceof java.lang.String)) {
                        com.google.protobuf.ByteString bs =
                                (com.google.protobuf.ByteString) ref;
                        java.lang.String s = bs.toStringUtf8();
                        if (bs.isValidUtf8()) {
                            charset_ = s;
                        }
                        return s;
                    } else {
                        return (java.lang.String) ref;
                    }
                }
                /**
                 * <code>optional string charset = 4;</code>
                 */
                public com.google.protobuf.ByteString
                getCharsetBytes() {
                    java.lang.Object ref = charset_;
                    if (ref instanceof String) {
                        com.google.protobuf.ByteString b =
                                com.google.protobuf.ByteString.copyFromUtf8(
                                        (java.lang.String) ref);
                        charset_ = b;
                        return b;
                    } else {
                        return (com.google.protobuf.ByteString) ref;
                    }
                }
                /**
                 * <code>optional string charset = 4;</code>
                 */
                public Builder setCharset(
                        java.lang.String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000008;
                    charset_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string charset = 4;</code>
                 */
                public Builder clearCharset() {
                    bitField0_ = (bitField0_ & ~0x00000008);
                    charset_ = getDefaultInstance().getCharset();
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional string charset = 4;</code>
                 */
                public Builder setCharsetBytes(
                        com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000008;
                    charset_ = value;
                    onChanged();
                    return this;
                }

                private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
                /**
                 * <code>optional bytes value = 5;</code>
                 */
                public boolean hasValue() {
                    return ((bitField0_ & 0x00000010) == 0x00000010);
                }
                /**
                 * <code>optional bytes value = 5;</code>
                 */
                public com.google.protobuf.ByteString getValue() {
                    return value_;
                }
                /**
                 * <code>optional bytes value = 5;</code>
                 */
                public Builder setValue(com.google.protobuf.ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    bitField0_ |= 0x00000010;
                    value_ = value;
                    onChanged();
                    return this;
                }
                /**
                 * <code>optional bytes value = 5;</code>
                 */
                public Builder clearValue() {
                    bitField0_ = (bitField0_ & ~0x00000010);
                    value_ = getDefaultInstance().getValue();
                    onChanged();
                    return this;
                }
                public final Builder setUnknownFields(
                        final com.google.protobuf.UnknownFieldSet unknownFields) {
                    return super.setUnknownFields(unknownFields);
                }

                public final Builder mergeUnknownFields(
                        final com.google.protobuf.UnknownFieldSet unknownFields) {
                    return super.mergeUnknownFields(unknownFields);
                }


                // @@protoc_insertion_point(builder_scope:spotify.Url.Part)
            }

            // @@protoc_insertion_point(class_scope:spotify.Url.Part)
            private static final com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part DEFAULT_INSTANCE;
            static {
                DEFAULT_INSTANCE = new com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part();
            }

            public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            @java.lang.Deprecated public static final com.google.protobuf.Parser<Part>
                    PARSER = new com.google.protobuf.AbstractParser<Part>() {
                public Part parsePartialFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
                    return new Part(input, extensionRegistry);
                }
            };

            public static com.google.protobuf.Parser<Part> parser() {
                return PARSER;
            }

            @java.lang.Override
            public com.google.protobuf.Parser<Part> getParserForType() {
                return PARSER;
            }

            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part getDefaultInstanceForType() {
                return DEFAULT_INSTANCE;
            }

        }

        private int bitField0_;
        public static final int SCHEME_FIELD_NUMBER = 1;
        private int scheme_;
        /**
         * <code>required .spotify.Url.Scheme scheme = 1;</code>
         */
        public boolean hasScheme() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }
        /**
         * <code>required .spotify.Url.Scheme scheme = 1;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme getScheme() {
            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme result = com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme.valueOf(scheme_);
            return result == null ? com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme.HTTP : result;
        }

        public static final int HOST_FIELD_NUMBER = 2;
        private volatile java.lang.Object host_;
        /**
         * <code>required string host = 2;</code>
         */
        public boolean hasHost() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }
        /**
         * <code>required string host = 2;</code>
         */
        public java.lang.String getHost() {
            java.lang.Object ref = host_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    host_ = s;
                }
                return s;
            }
        }
        /**
         * <code>required string host = 2;</code>
         */
        public com.google.protobuf.ByteString
        getHostBytes() {
            java.lang.Object ref = host_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                host_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int PORT_FIELD_NUMBER = 3;
        private int port_;
        /**
         * <code>required int32 port = 3;</code>
         */
        public boolean hasPort() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }
        /**
         * <code>required int32 port = 3;</code>
         */
        public int getPort() {
            return port_;
        }

        public static final int PATH_FIELD_NUMBER = 4;
        private volatile java.lang.Object path_;
        /**
         * <code>required string path = 4;</code>
         */
        public boolean hasPath() {
            return ((bitField0_ & 0x00000008) == 0x00000008);
        }
        /**
         * <code>required string path = 4;</code>
         */
        public java.lang.String getPath() {
            java.lang.Object ref = path_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    path_ = s;
                }
                return s;
            }
        }
        /**
         * <code>required string path = 4;</code>
         */
        public com.google.protobuf.ByteString
        getPathBytes() {
            java.lang.Object ref = path_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                path_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        public static final int PARAMETERS_FIELD_NUMBER = 5;
        private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> parameters_;
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> getParametersList() {
            return parameters_;
        }
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
        getParametersOrBuilderList() {
            return parameters_;
        }
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        public int getParametersCount() {
            return parameters_.size();
        }
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getParameters(int index) {
            return parameters_.get(index);
        }
        /**
         * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getParametersOrBuilder(
                int index) {
            return parameters_.get(index);
        }

        public static final int PARTS_FIELD_NUMBER = 6;
        private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part> parts_;
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part> getPartsList() {
            return parts_;
        }
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder>
        getPartsOrBuilderList() {
            return parts_;
        }
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        public int getPartsCount() {
            return parts_.size();
        }
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part getParts(int index) {
            return parts_.get(index);
        }
        /**
         * <code>repeated .spotify.Url.Part parts = 6;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder getPartsOrBuilder(
                int index) {
            return parts_.get(index);
        }

        public static final int HEADERPARAMETERS_FIELD_NUMBER = 7;
        private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> headerParameters_;
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> getHeaderParametersList() {
            return headerParameters_;
        }
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
        getHeaderParametersOrBuilderList() {
            return headerParameters_;
        }
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        public int getHeaderParametersCount() {
            return headerParameters_.size();
        }
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getHeaderParameters(int index) {
            return headerParameters_.get(index);
        }
        /**
         * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getHeaderParametersOrBuilder(
                int index) {
            return headerParameters_.get(index);
        }

        public static final int BODYPARAMETERS_FIELD_NUMBER = 8;
        private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> bodyParameters_;
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> getBodyParametersList() {
            return bodyParameters_;
        }
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
        getBodyParametersOrBuilderList() {
            return bodyParameters_;
        }
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        public int getBodyParametersCount() {
            return bodyParameters_.size();
        }
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getBodyParameters(int index) {
            return bodyParameters_.get(index);
        }
        /**
         * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
         */
        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getBodyParametersOrBuilder(
                int index) {
            return bodyParameters_.get(index);
        }

        public static final int JSONBODY_FIELD_NUMBER = 9;
        private volatile java.lang.Object jsonBody_;
        /**
         * <code>optional string jsonBody = 9;</code>
         */
        public boolean hasJsonBody() {
            return ((bitField0_ & 0x00000010) == 0x00000010);
        }
        /**
         * <code>optional string jsonBody = 9;</code>
         */
        public java.lang.String getJsonBody() {
            java.lang.Object ref = jsonBody_;
            if (ref instanceof java.lang.String) {
                return (java.lang.String) ref;
            } else {
                com.google.protobuf.ByteString bs =
                        (com.google.protobuf.ByteString) ref;
                java.lang.String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    jsonBody_ = s;
                }
                return s;
            }
        }
        /**
         * <code>optional string jsonBody = 9;</code>
         */
        public com.google.protobuf.ByteString
        getJsonBodyBytes() {
            java.lang.Object ref = jsonBody_;
            if (ref instanceof java.lang.String) {
                com.google.protobuf.ByteString b =
                        com.google.protobuf.ByteString.copyFromUtf8(
                                (java.lang.String) ref);
                jsonBody_ = b;
                return b;
            } else {
                return (com.google.protobuf.ByteString) ref;
            }
        }

        private byte memoizedIsInitialized = -1;
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            if (!hasScheme()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasHost()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasPort()) {
                memoizedIsInitialized = 0;
                return false;
            }
            if (!hasPath()) {
                memoizedIsInitialized = 0;
                return false;
            }
            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeEnum(1, scheme_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 2, host_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeInt32(3, port_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 4, path_);
            }
            for (int i = 0; i < parameters_.size(); i++) {
                output.writeMessage(5, parameters_.get(i));
            }
            for (int i = 0; i < parts_.size(); i++) {
                output.writeMessage(6, parts_.get(i));
            }
            for (int i = 0; i < headerParameters_.size(); i++) {
                output.writeMessage(7, headerParameters_.get(i));
            }
            for (int i = 0; i < bodyParameters_.size(); i++) {
                output.writeMessage(8, bodyParameters_.get(i));
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                com.google.protobuf.GeneratedMessageV3.writeString(output, 9, jsonBody_);
            }
            unknownFields.writeTo(output);
        }

        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(1, scheme_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, host_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeInt32Size(3, port_);
            }
            if (((bitField0_ & 0x00000008) == 0x00000008)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, path_);
            }
            for (int i = 0; i < parameters_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(5, parameters_.get(i));
            }
            for (int i = 0; i < parts_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(6, parts_.get(i));
            }
            for (int i = 0; i < headerParameters_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(7, headerParameters_.get(i));
            }
            for (int i = 0; i < bodyParameters_.size(); i++) {
                size += com.google.protobuf.CodedOutputStream
                        .computeMessageSize(8, bodyParameters_.get(i));
            }
            if (((bitField0_ & 0x00000010) == 0x00000010)) {
                size += com.google.protobuf.GeneratedMessageV3.computeStringSize(9, jsonBody_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.tradekraftcollective.microservice.utilities.UtilProtos.Url)) {
                return super.equals(obj);
            }
            com.tradekraftcollective.microservice.utilities.UtilProtos.Url other = (com.tradekraftcollective.microservice.utilities.UtilProtos.Url) obj;

            boolean result = true;
            result = result && (hasScheme() == other.hasScheme());
            if (hasScheme()) {
                result = result && scheme_ == other.scheme_;
            }
            result = result && (hasHost() == other.hasHost());
            if (hasHost()) {
                result = result && getHost()
                        .equals(other.getHost());
            }
            result = result && (hasPort() == other.hasPort());
            if (hasPort()) {
                result = result && (getPort()
                        == other.getPort());
            }
            result = result && (hasPath() == other.hasPath());
            if (hasPath()) {
                result = result && getPath()
                        .equals(other.getPath());
            }
            result = result && getParametersList()
                    .equals(other.getParametersList());
            result = result && getPartsList()
                    .equals(other.getPartsList());
            result = result && getHeaderParametersList()
                    .equals(other.getHeaderParametersList());
            result = result && getBodyParametersList()
                    .equals(other.getBodyParametersList());
            result = result && (hasJsonBody() == other.hasJsonBody());
            if (hasJsonBody()) {
                result = result && getJsonBody()
                        .equals(other.getJsonBody());
            }
            result = result && unknownFields.equals(other.unknownFields);
            return result;
        }

        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            if (hasScheme()) {
                hash = (37 * hash) + SCHEME_FIELD_NUMBER;
                hash = (53 * hash) + scheme_;
            }
            if (hasHost()) {
                hash = (37 * hash) + HOST_FIELD_NUMBER;
                hash = (53 * hash) + getHost().hashCode();
            }
            if (hasPort()) {
                hash = (37 * hash) + PORT_FIELD_NUMBER;
                hash = (53 * hash) + getPort();
            }
            if (hasPath()) {
                hash = (37 * hash) + PATH_FIELD_NUMBER;
                hash = (53 * hash) + getPath().hashCode();
            }
            if (getParametersCount() > 0) {
                hash = (37 * hash) + PARAMETERS_FIELD_NUMBER;
                hash = (53 * hash) + getParametersList().hashCode();
            }
            if (getPartsCount() > 0) {
                hash = (37 * hash) + PARTS_FIELD_NUMBER;
                hash = (53 * hash) + getPartsList().hashCode();
            }
            if (getHeaderParametersCount() > 0) {
                hash = (37 * hash) + HEADERPARAMETERS_FIELD_NUMBER;
                hash = (53 * hash) + getHeaderParametersList().hashCode();
            }
            if (getBodyParametersCount() > 0) {
                hash = (37 * hash) + BODYPARAMETERS_FIELD_NUMBER;
                hash = (53 * hash) + getBodyParametersList().hashCode();
            }
            if (hasJsonBody()) {
                hash = (37 * hash) + JSONBODY_FIELD_NUMBER;
                hash = (53 * hash) + getJsonBody().hashCode();
            }
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input);
        }
        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3
                    .parseWithIOException(PARSER, input, extensionRegistry);
        }

        public Builder newBuilderForType() { return newBuilder(); }
        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }
        public static Builder newBuilder(com.tradekraftcollective.microservice.utilities.UtilProtos.Url prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE
                    ? new Builder() : new Builder().mergeFrom(this);
        }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         * Protobuf type {@code spotify.Url}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
                // @@protoc_insertion_point(builder_implements:spotify.Url)
                com.tradekraftcollective.microservice.utilities.UtilProtos.UrlOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor
            getDescriptor() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_descriptor;
            }

            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internalGetFieldAccessorTable() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                com.tradekraftcollective.microservice.utilities.UtilProtos.Url.class, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Builder.class);
            }

            // Construct using com.tradekraftcollective.microservice.utilities.UtilProtos.Url.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(
                    com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }
            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3
                        .alwaysUseFieldBuilders) {
                    getParametersFieldBuilder();
                    getPartsFieldBuilder();
                    getHeaderParametersFieldBuilder();
                    getBodyParametersFieldBuilder();
                }
            }
            public Builder clear() {
                super.clear();
                scheme_ = 0;
                bitField0_ = (bitField0_ & ~0x00000001);
                host_ = "";
                bitField0_ = (bitField0_ & ~0x00000002);
                port_ = 0;
                bitField0_ = (bitField0_ & ~0x00000004);
                path_ = "";
                bitField0_ = (bitField0_ & ~0x00000008);
                if (parametersBuilder_ == null) {
                    parameters_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000010);
                } else {
                    parametersBuilder_.clear();
                }
                if (partsBuilder_ == null) {
                    parts_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000020);
                } else {
                    partsBuilder_.clear();
                }
                if (headerParametersBuilder_ == null) {
                    headerParameters_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000040);
                } else {
                    headerParametersBuilder_.clear();
                }
                if (bodyParametersBuilder_ == null) {
                    bodyParameters_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000080);
                } else {
                    bodyParametersBuilder_.clear();
                }
                jsonBody_ = "";
                bitField0_ = (bitField0_ & ~0x00000100);
                return this;
            }

            public com.google.protobuf.Descriptors.Descriptor
            getDescriptorForType() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.internal_static_spotify_Url_descriptor;
            }

            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url getDefaultInstanceForType() {
                return com.tradekraftcollective.microservice.utilities.UtilProtos.Url.getDefaultInstance();
            }

            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url build() {
                com.tradekraftcollective.microservice.utilities.UtilProtos.Url result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url buildPartial() {
                com.tradekraftcollective.microservice.utilities.UtilProtos.Url result = new com.tradekraftcollective.microservice.utilities.UtilProtos.Url(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.scheme_ = scheme_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.host_ = host_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.port_ = port_;
                if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
                    to_bitField0_ |= 0x00000008;
                }
                result.path_ = path_;
                if (parametersBuilder_ == null) {
                    if (((bitField0_ & 0x00000010) == 0x00000010)) {
                        parameters_ = java.util.Collections.unmodifiableList(parameters_);
                        bitField0_ = (bitField0_ & ~0x00000010);
                    }
                    result.parameters_ = parameters_;
                } else {
                    result.parameters_ = parametersBuilder_.build();
                }
                if (partsBuilder_ == null) {
                    if (((bitField0_ & 0x00000020) == 0x00000020)) {
                        parts_ = java.util.Collections.unmodifiableList(parts_);
                        bitField0_ = (bitField0_ & ~0x00000020);
                    }
                    result.parts_ = parts_;
                } else {
                    result.parts_ = partsBuilder_.build();
                }
                if (headerParametersBuilder_ == null) {
                    if (((bitField0_ & 0x00000040) == 0x00000040)) {
                        headerParameters_ = java.util.Collections.unmodifiableList(headerParameters_);
                        bitField0_ = (bitField0_ & ~0x00000040);
                    }
                    result.headerParameters_ = headerParameters_;
                } else {
                    result.headerParameters_ = headerParametersBuilder_.build();
                }
                if (bodyParametersBuilder_ == null) {
                    if (((bitField0_ & 0x00000080) == 0x00000080)) {
                        bodyParameters_ = java.util.Collections.unmodifiableList(bodyParameters_);
                        bitField0_ = (bitField0_ & ~0x00000080);
                    }
                    result.bodyParameters_ = bodyParameters_;
                } else {
                    result.bodyParameters_ = bodyParametersBuilder_.build();
                }
                if (((from_bitField0_ & 0x00000100) == 0x00000100)) {
                    to_bitField0_ |= 0x00000010;
                }
                result.jsonBody_ = jsonBody_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder clone() {
                return (Builder) super.clone();
            }
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    java.lang.Object value) {
                return (Builder) super.setField(field, value);
            }
            public Builder clearField(
                    com.google.protobuf.Descriptors.FieldDescriptor field) {
                return (Builder) super.clearField(field);
            }
            public Builder clearOneof(
                    com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return (Builder) super.clearOneof(oneof);
            }
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index, java.lang.Object value) {
                return (Builder) super.setRepeatedField(field, index, value);
            }
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    java.lang.Object value) {
                return (Builder) super.addRepeatedField(field, value);
            }
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other instanceof com.tradekraftcollective.microservice.utilities.UtilProtos.Url) {
                    return mergeFrom((com.tradekraftcollective.microservice.utilities.UtilProtos.Url)other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(com.tradekraftcollective.microservice.utilities.UtilProtos.Url other) {
                if (other == com.tradekraftcollective.microservice.utilities.UtilProtos.Url.getDefaultInstance()) return this;
                if (other.hasScheme()) {
                    setScheme(other.getScheme());
                }
                if (other.hasHost()) {
                    bitField0_ |= 0x00000002;
                    host_ = other.host_;
                    onChanged();
                }
                if (other.hasPort()) {
                    setPort(other.getPort());
                }
                if (other.hasPath()) {
                    bitField0_ |= 0x00000008;
                    path_ = other.path_;
                    onChanged();
                }
                if (parametersBuilder_ == null) {
                    if (!other.parameters_.isEmpty()) {
                        if (parameters_.isEmpty()) {
                            parameters_ = other.parameters_;
                            bitField0_ = (bitField0_ & ~0x00000010);
                        } else {
                            ensureParametersIsMutable();
                            parameters_.addAll(other.parameters_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.parameters_.isEmpty()) {
                        if (parametersBuilder_.isEmpty()) {
                            parametersBuilder_.dispose();
                            parametersBuilder_ = null;
                            parameters_ = other.parameters_;
                            bitField0_ = (bitField0_ & ~0x00000010);
                            parametersBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getParametersFieldBuilder() : null;
                        } else {
                            parametersBuilder_.addAllMessages(other.parameters_);
                        }
                    }
                }
                if (partsBuilder_ == null) {
                    if (!other.parts_.isEmpty()) {
                        if (parts_.isEmpty()) {
                            parts_ = other.parts_;
                            bitField0_ = (bitField0_ & ~0x00000020);
                        } else {
                            ensurePartsIsMutable();
                            parts_.addAll(other.parts_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.parts_.isEmpty()) {
                        if (partsBuilder_.isEmpty()) {
                            partsBuilder_.dispose();
                            partsBuilder_ = null;
                            parts_ = other.parts_;
                            bitField0_ = (bitField0_ & ~0x00000020);
                            partsBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getPartsFieldBuilder() : null;
                        } else {
                            partsBuilder_.addAllMessages(other.parts_);
                        }
                    }
                }
                if (headerParametersBuilder_ == null) {
                    if (!other.headerParameters_.isEmpty()) {
                        if (headerParameters_.isEmpty()) {
                            headerParameters_ = other.headerParameters_;
                            bitField0_ = (bitField0_ & ~0x00000040);
                        } else {
                            ensureHeaderParametersIsMutable();
                            headerParameters_.addAll(other.headerParameters_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.headerParameters_.isEmpty()) {
                        if (headerParametersBuilder_.isEmpty()) {
                            headerParametersBuilder_.dispose();
                            headerParametersBuilder_ = null;
                            headerParameters_ = other.headerParameters_;
                            bitField0_ = (bitField0_ & ~0x00000040);
                            headerParametersBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getHeaderParametersFieldBuilder() : null;
                        } else {
                            headerParametersBuilder_.addAllMessages(other.headerParameters_);
                        }
                    }
                }
                if (bodyParametersBuilder_ == null) {
                    if (!other.bodyParameters_.isEmpty()) {
                        if (bodyParameters_.isEmpty()) {
                            bodyParameters_ = other.bodyParameters_;
                            bitField0_ = (bitField0_ & ~0x00000080);
                        } else {
                            ensureBodyParametersIsMutable();
                            bodyParameters_.addAll(other.bodyParameters_);
                        }
                        onChanged();
                    }
                } else {
                    if (!other.bodyParameters_.isEmpty()) {
                        if (bodyParametersBuilder_.isEmpty()) {
                            bodyParametersBuilder_.dispose();
                            bodyParametersBuilder_ = null;
                            bodyParameters_ = other.bodyParameters_;
                            bitField0_ = (bitField0_ & ~0x00000080);
                            bodyParametersBuilder_ =
                                    com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                                            getBodyParametersFieldBuilder() : null;
                        } else {
                            bodyParametersBuilder_.addAllMessages(other.bodyParameters_);
                        }
                    }
                }
                if (other.hasJsonBody()) {
                    bitField0_ |= 0x00000100;
                    jsonBody_ = other.jsonBody_;
                    onChanged();
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            public final boolean isInitialized() {
                if (!hasScheme()) {
                    return false;
                }
                if (!hasHost()) {
                    return false;
                }
                if (!hasPort()) {
                    return false;
                }
                if (!hasPath()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                com.tradekraftcollective.microservice.utilities.UtilProtos.Url parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (com.tradekraftcollective.microservice.utilities.UtilProtos.Url) e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }
            private int bitField0_;

            private int scheme_ = 0;
            /**
             * <code>required .spotify.Url.Scheme scheme = 1;</code>
             */
            public boolean hasScheme() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }
            /**
             * <code>required .spotify.Url.Scheme scheme = 1;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme getScheme() {
                com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme result = com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme.valueOf(scheme_);
                return result == null ? com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme.HTTP : result;
            }
            /**
             * <code>required .spotify.Url.Scheme scheme = 1;</code>
             */
            public Builder setScheme(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                scheme_ = value.getNumber();
                onChanged();
                return this;
            }
            /**
             * <code>required .spotify.Url.Scheme scheme = 1;</code>
             */
            public Builder clearScheme() {
                bitField0_ = (bitField0_ & ~0x00000001);
                scheme_ = 0;
                onChanged();
                return this;
            }

            private java.lang.Object host_ = "";
            /**
             * <code>required string host = 2;</code>
             */
            public boolean hasHost() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }
            /**
             * <code>required string host = 2;</code>
             */
            public java.lang.String getHost() {
                java.lang.Object ref = host_;
                if (!(ref instanceof java.lang.String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        host_ = s;
                    }
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>required string host = 2;</code>
             */
            public com.google.protobuf.ByteString
            getHostBytes() {
                java.lang.Object ref = host_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    host_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>required string host = 2;</code>
             */
            public Builder setHost(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                host_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required string host = 2;</code>
             */
            public Builder clearHost() {
                bitField0_ = (bitField0_ & ~0x00000002);
                host_ = getDefaultInstance().getHost();
                onChanged();
                return this;
            }
            /**
             * <code>required string host = 2;</code>
             */
            public Builder setHostBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000002;
                host_ = value;
                onChanged();
                return this;
            }

            private int port_ ;
            /**
             * <code>required int32 port = 3;</code>
             */
            public boolean hasPort() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }
            /**
             * <code>required int32 port = 3;</code>
             */
            public int getPort() {
                return port_;
            }
            /**
             * <code>required int32 port = 3;</code>
             */
            public Builder setPort(int value) {
                bitField0_ |= 0x00000004;
                port_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required int32 port = 3;</code>
             */
            public Builder clearPort() {
                bitField0_ = (bitField0_ & ~0x00000004);
                port_ = 0;
                onChanged();
                return this;
            }

            private java.lang.Object path_ = "";
            /**
             * <code>required string path = 4;</code>
             */
            public boolean hasPath() {
                return ((bitField0_ & 0x00000008) == 0x00000008);
            }
            /**
             * <code>required string path = 4;</code>
             */
            public java.lang.String getPath() {
                java.lang.Object ref = path_;
                if (!(ref instanceof java.lang.String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        path_ = s;
                    }
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>required string path = 4;</code>
             */
            public com.google.protobuf.ByteString
            getPathBytes() {
                java.lang.Object ref = path_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    path_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>required string path = 4;</code>
             */
            public Builder setPath(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000008;
                path_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>required string path = 4;</code>
             */
            public Builder clearPath() {
                bitField0_ = (bitField0_ & ~0x00000008);
                path_ = getDefaultInstance().getPath();
                onChanged();
                return this;
            }
            /**
             * <code>required string path = 4;</code>
             */
            public Builder setPathBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000008;
                path_ = value;
                onChanged();
                return this;
            }

            private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> parameters_ =
                    java.util.Collections.emptyList();
            private void ensureParametersIsMutable() {
                if (!((bitField0_ & 0x00000010) == 0x00000010)) {
                    parameters_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>(parameters_);
                    bitField0_ |= 0x00000010;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder> parametersBuilder_;

            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> getParametersList() {
                if (parametersBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(parameters_);
                } else {
                    return parametersBuilder_.getMessageList();
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public int getParametersCount() {
                if (parametersBuilder_ == null) {
                    return parameters_.size();
                } else {
                    return parametersBuilder_.getCount();
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getParameters(int index) {
                if (parametersBuilder_ == null) {
                    return parameters_.get(index);
                } else {
                    return parametersBuilder_.getMessage(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder setParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (parametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureParametersIsMutable();
                    parameters_.set(index, value);
                    onChanged();
                } else {
                    parametersBuilder_.setMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder setParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (parametersBuilder_ == null) {
                    ensureParametersIsMutable();
                    parameters_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    parametersBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder addParameters(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (parametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureParametersIsMutable();
                    parameters_.add(value);
                    onChanged();
                } else {
                    parametersBuilder_.addMessage(value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder addParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (parametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureParametersIsMutable();
                    parameters_.add(index, value);
                    onChanged();
                } else {
                    parametersBuilder_.addMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder addParameters(
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (parametersBuilder_ == null) {
                    ensureParametersIsMutable();
                    parameters_.add(builderForValue.build());
                    onChanged();
                } else {
                    parametersBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder addParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (parametersBuilder_ == null) {
                    ensureParametersIsMutable();
                    parameters_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    parametersBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder addAllParameters(
                    java.lang.Iterable<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> values) {
                if (parametersBuilder_ == null) {
                    ensureParametersIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, parameters_);
                    onChanged();
                } else {
                    parametersBuilder_.addAllMessages(values);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder clearParameters() {
                if (parametersBuilder_ == null) {
                    parameters_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000010);
                    onChanged();
                } else {
                    parametersBuilder_.clear();
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public Builder removeParameters(int index) {
                if (parametersBuilder_ == null) {
                    ensureParametersIsMutable();
                    parameters_.remove(index);
                    onChanged();
                } else {
                    parametersBuilder_.remove(index);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder getParametersBuilder(
                    int index) {
                return getParametersFieldBuilder().getBuilder(index);
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getParametersOrBuilder(
                    int index) {
                if (parametersBuilder_ == null) {
                    return parameters_.get(index);  } else {
                    return parametersBuilder_.getMessageOrBuilder(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
            getParametersOrBuilderList() {
                if (parametersBuilder_ != null) {
                    return parametersBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(parameters_);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder addParametersBuilder() {
                return getParametersFieldBuilder().addBuilder(
                        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder addParametersBuilder(
                    int index) {
                return getParametersFieldBuilder().addBuilder(
                        index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Parameter parameters = 5;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder>
            getParametersBuilderList() {
                return getParametersFieldBuilder().getBuilderList();
            }
            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
            getParametersFieldBuilder() {
                if (parametersBuilder_ == null) {
                    parametersBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>(
                            parameters_,
                            ((bitField0_ & 0x00000010) == 0x00000010),
                            getParentForChildren(),
                            isClean());
                    parameters_ = null;
                }
                return parametersBuilder_;
            }

            private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part> parts_ =
                    java.util.Collections.emptyList();
            private void ensurePartsIsMutable() {
                if (!((bitField0_ & 0x00000020) == 0x00000020)) {
                    parts_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part>(parts_);
                    bitField0_ |= 0x00000020;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder> partsBuilder_;

            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part> getPartsList() {
                if (partsBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(parts_);
                } else {
                    return partsBuilder_.getMessageList();
                }
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public int getPartsCount() {
                if (partsBuilder_ == null) {
                    return parts_.size();
                } else {
                    return partsBuilder_.getCount();
                }
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part getParts(int index) {
                if (partsBuilder_ == null) {
                    return parts_.get(index);
                } else {
                    return partsBuilder_.getMessage(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder setParts(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part value) {
                if (partsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensurePartsIsMutable();
                    parts_.set(index, value);
                    onChanged();
                } else {
                    partsBuilder_.setMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder setParts(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder builderForValue) {
                if (partsBuilder_ == null) {
                    ensurePartsIsMutable();
                    parts_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    partsBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder addParts(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part value) {
                if (partsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensurePartsIsMutable();
                    parts_.add(value);
                    onChanged();
                } else {
                    partsBuilder_.addMessage(value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder addParts(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part value) {
                if (partsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensurePartsIsMutable();
                    parts_.add(index, value);
                    onChanged();
                } else {
                    partsBuilder_.addMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder addParts(
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder builderForValue) {
                if (partsBuilder_ == null) {
                    ensurePartsIsMutable();
                    parts_.add(builderForValue.build());
                    onChanged();
                } else {
                    partsBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder addParts(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder builderForValue) {
                if (partsBuilder_ == null) {
                    ensurePartsIsMutable();
                    parts_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    partsBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder addAllParts(
                    java.lang.Iterable<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part> values) {
                if (partsBuilder_ == null) {
                    ensurePartsIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, parts_);
                    onChanged();
                } else {
                    partsBuilder_.addAllMessages(values);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder clearParts() {
                if (partsBuilder_ == null) {
                    parts_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000020);
                    onChanged();
                } else {
                    partsBuilder_.clear();
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public Builder removeParts(int index) {
                if (partsBuilder_ == null) {
                    ensurePartsIsMutable();
                    parts_.remove(index);
                    onChanged();
                } else {
                    partsBuilder_.remove(index);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder getPartsBuilder(
                    int index) {
                return getPartsFieldBuilder().getBuilder(index);
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder getPartsOrBuilder(
                    int index) {
                if (partsBuilder_ == null) {
                    return parts_.get(index);  } else {
                    return partsBuilder_.getMessageOrBuilder(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder>
            getPartsOrBuilderList() {
                if (partsBuilder_ != null) {
                    return partsBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(parts_);
                }
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder addPartsBuilder() {
                return getPartsFieldBuilder().addBuilder(
                        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder addPartsBuilder(
                    int index) {
                return getPartsFieldBuilder().addBuilder(
                        index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Part parts = 6;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder>
            getPartsBuilderList() {
                return getPartsFieldBuilder().getBuilderList();
            }
            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder>
            getPartsFieldBuilder() {
                if (partsBuilder_ == null) {
                    partsBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Part.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.PartOrBuilder>(
                            parts_,
                            ((bitField0_ & 0x00000020) == 0x00000020),
                            getParentForChildren(),
                            isClean());
                    parts_ = null;
                }
                return partsBuilder_;
            }

            private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> headerParameters_ =
                    java.util.Collections.emptyList();
            private void ensureHeaderParametersIsMutable() {
                if (!((bitField0_ & 0x00000040) == 0x00000040)) {
                    headerParameters_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>(headerParameters_);
                    bitField0_ |= 0x00000040;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder> headerParametersBuilder_;

            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> getHeaderParametersList() {
                if (headerParametersBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(headerParameters_);
                } else {
                    return headerParametersBuilder_.getMessageList();
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public int getHeaderParametersCount() {
                if (headerParametersBuilder_ == null) {
                    return headerParameters_.size();
                } else {
                    return headerParametersBuilder_.getCount();
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getHeaderParameters(int index) {
                if (headerParametersBuilder_ == null) {
                    return headerParameters_.get(index);
                } else {
                    return headerParametersBuilder_.getMessage(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder setHeaderParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (headerParametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureHeaderParametersIsMutable();
                    headerParameters_.set(index, value);
                    onChanged();
                } else {
                    headerParametersBuilder_.setMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder setHeaderParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (headerParametersBuilder_ == null) {
                    ensureHeaderParametersIsMutable();
                    headerParameters_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    headerParametersBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder addHeaderParameters(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (headerParametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureHeaderParametersIsMutable();
                    headerParameters_.add(value);
                    onChanged();
                } else {
                    headerParametersBuilder_.addMessage(value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder addHeaderParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (headerParametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureHeaderParametersIsMutable();
                    headerParameters_.add(index, value);
                    onChanged();
                } else {
                    headerParametersBuilder_.addMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder addHeaderParameters(
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (headerParametersBuilder_ == null) {
                    ensureHeaderParametersIsMutable();
                    headerParameters_.add(builderForValue.build());
                    onChanged();
                } else {
                    headerParametersBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder addHeaderParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (headerParametersBuilder_ == null) {
                    ensureHeaderParametersIsMutable();
                    headerParameters_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    headerParametersBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder addAllHeaderParameters(
                    java.lang.Iterable<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> values) {
                if (headerParametersBuilder_ == null) {
                    ensureHeaderParametersIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, headerParameters_);
                    onChanged();
                } else {
                    headerParametersBuilder_.addAllMessages(values);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder clearHeaderParameters() {
                if (headerParametersBuilder_ == null) {
                    headerParameters_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000040);
                    onChanged();
                } else {
                    headerParametersBuilder_.clear();
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public Builder removeHeaderParameters(int index) {
                if (headerParametersBuilder_ == null) {
                    ensureHeaderParametersIsMutable();
                    headerParameters_.remove(index);
                    onChanged();
                } else {
                    headerParametersBuilder_.remove(index);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder getHeaderParametersBuilder(
                    int index) {
                return getHeaderParametersFieldBuilder().getBuilder(index);
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getHeaderParametersOrBuilder(
                    int index) {
                if (headerParametersBuilder_ == null) {
                    return headerParameters_.get(index);  } else {
                    return headerParametersBuilder_.getMessageOrBuilder(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
            getHeaderParametersOrBuilderList() {
                if (headerParametersBuilder_ != null) {
                    return headerParametersBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(headerParameters_);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder addHeaderParametersBuilder() {
                return getHeaderParametersFieldBuilder().addBuilder(
                        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder addHeaderParametersBuilder(
                    int index) {
                return getHeaderParametersFieldBuilder().addBuilder(
                        index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Parameter headerParameters = 7;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder>
            getHeaderParametersBuilderList() {
                return getHeaderParametersFieldBuilder().getBuilderList();
            }
            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
            getHeaderParametersFieldBuilder() {
                if (headerParametersBuilder_ == null) {
                    headerParametersBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>(
                            headerParameters_,
                            ((bitField0_ & 0x00000040) == 0x00000040),
                            getParentForChildren(),
                            isClean());
                    headerParameters_ = null;
                }
                return headerParametersBuilder_;
            }

            private java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> bodyParameters_ =
                    java.util.Collections.emptyList();
            private void ensureBodyParametersIsMutable() {
                if (!((bitField0_ & 0x00000080) == 0x00000080)) {
                    bodyParameters_ = new java.util.ArrayList<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter>(bodyParameters_);
                    bitField0_ |= 0x00000080;
                }
            }

            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder> bodyParametersBuilder_;

            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> getBodyParametersList() {
                if (bodyParametersBuilder_ == null) {
                    return java.util.Collections.unmodifiableList(bodyParameters_);
                } else {
                    return bodyParametersBuilder_.getMessageList();
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public int getBodyParametersCount() {
                if (bodyParametersBuilder_ == null) {
                    return bodyParameters_.size();
                } else {
                    return bodyParametersBuilder_.getCount();
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter getBodyParameters(int index) {
                if (bodyParametersBuilder_ == null) {
                    return bodyParameters_.get(index);
                } else {
                    return bodyParametersBuilder_.getMessage(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder setBodyParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (bodyParametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureBodyParametersIsMutable();
                    bodyParameters_.set(index, value);
                    onChanged();
                } else {
                    bodyParametersBuilder_.setMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder setBodyParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (bodyParametersBuilder_ == null) {
                    ensureBodyParametersIsMutable();
                    bodyParameters_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    bodyParametersBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder addBodyParameters(com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (bodyParametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureBodyParametersIsMutable();
                    bodyParameters_.add(value);
                    onChanged();
                } else {
                    bodyParametersBuilder_.addMessage(value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder addBodyParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter value) {
                if (bodyParametersBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    ensureBodyParametersIsMutable();
                    bodyParameters_.add(index, value);
                    onChanged();
                } else {
                    bodyParametersBuilder_.addMessage(index, value);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder addBodyParameters(
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (bodyParametersBuilder_ == null) {
                    ensureBodyParametersIsMutable();
                    bodyParameters_.add(builderForValue.build());
                    onChanged();
                } else {
                    bodyParametersBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder addBodyParameters(
                    int index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder builderForValue) {
                if (bodyParametersBuilder_ == null) {
                    ensureBodyParametersIsMutable();
                    bodyParameters_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    bodyParametersBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder addAllBodyParameters(
                    java.lang.Iterable<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter> values) {
                if (bodyParametersBuilder_ == null) {
                    ensureBodyParametersIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(
                            values, bodyParameters_);
                    onChanged();
                } else {
                    bodyParametersBuilder_.addAllMessages(values);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder clearBodyParameters() {
                if (bodyParametersBuilder_ == null) {
                    bodyParameters_ = java.util.Collections.emptyList();
                    bitField0_ = (bitField0_ & ~0x00000080);
                    onChanged();
                } else {
                    bodyParametersBuilder_.clear();
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public Builder removeBodyParameters(int index) {
                if (bodyParametersBuilder_ == null) {
                    ensureBodyParametersIsMutable();
                    bodyParameters_.remove(index);
                    onChanged();
                } else {
                    bodyParametersBuilder_.remove(index);
                }
                return this;
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder getBodyParametersBuilder(
                    int index) {
                return getBodyParametersFieldBuilder().getBuilder(index);
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder getBodyParametersOrBuilder(
                    int index) {
                if (bodyParametersBuilder_ == null) {
                    return bodyParameters_.get(index);  } else {
                    return bodyParametersBuilder_.getMessageOrBuilder(index);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public java.util.List<? extends com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
            getBodyParametersOrBuilderList() {
                if (bodyParametersBuilder_ != null) {
                    return bodyParametersBuilder_.getMessageOrBuilderList();
                } else {
                    return java.util.Collections.unmodifiableList(bodyParameters_);
                }
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder addBodyParametersBuilder() {
                return getBodyParametersFieldBuilder().addBuilder(
                        com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder addBodyParametersBuilder(
                    int index) {
                return getBodyParametersFieldBuilder().addBuilder(
                        index, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.getDefaultInstance());
            }
            /**
             * <code>repeated .spotify.Url.Parameter bodyParameters = 8;</code>
             */
            public java.util.List<com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder>
            getBodyParametersBuilderList() {
                return getBodyParametersFieldBuilder().getBuilderList();
            }
            private com.google.protobuf.RepeatedFieldBuilderV3<
                    com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>
            getBodyParametersFieldBuilder() {
                if (bodyParametersBuilder_ == null) {
                    bodyParametersBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
                            com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Parameter.Builder, com.tradekraftcollective.microservice.utilities.UtilProtos.Url.ParameterOrBuilder>(
                            bodyParameters_,
                            ((bitField0_ & 0x00000080) == 0x00000080),
                            getParentForChildren(),
                            isClean());
                    bodyParameters_ = null;
                }
                return bodyParametersBuilder_;
            }

            private java.lang.Object jsonBody_ = "";
            /**
             * <code>optional string jsonBody = 9;</code>
             */
            public boolean hasJsonBody() {
                return ((bitField0_ & 0x00000100) == 0x00000100);
            }
            /**
             * <code>optional string jsonBody = 9;</code>
             */
            public java.lang.String getJsonBody() {
                java.lang.Object ref = jsonBody_;
                if (!(ref instanceof java.lang.String)) {
                    com.google.protobuf.ByteString bs =
                            (com.google.protobuf.ByteString) ref;
                    java.lang.String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        jsonBody_ = s;
                    }
                    return s;
                } else {
                    return (java.lang.String) ref;
                }
            }
            /**
             * <code>optional string jsonBody = 9;</code>
             */
            public com.google.protobuf.ByteString
            getJsonBodyBytes() {
                java.lang.Object ref = jsonBody_;
                if (ref instanceof String) {
                    com.google.protobuf.ByteString b =
                            com.google.protobuf.ByteString.copyFromUtf8(
                                    (java.lang.String) ref);
                    jsonBody_ = b;
                    return b;
                } else {
                    return (com.google.protobuf.ByteString) ref;
                }
            }
            /**
             * <code>optional string jsonBody = 9;</code>
             */
            public Builder setJsonBody(
                    java.lang.String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000100;
                jsonBody_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>optional string jsonBody = 9;</code>
             */
            public Builder clearJsonBody() {
                bitField0_ = (bitField0_ & ~0x00000100);
                jsonBody_ = getDefaultInstance().getJsonBody();
                onChanged();
                return this;
            }
            /**
             * <code>optional string jsonBody = 9;</code>
             */
            public Builder setJsonBodyBytes(
                    com.google.protobuf.ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000100;
                jsonBody_ = value;
                onChanged();
                return this;
            }
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }


            // @@protoc_insertion_point(builder_scope:spotify.Url)
        }

        // @@protoc_insertion_point(class_scope:spotify.Url)
        private static final com.tradekraftcollective.microservice.utilities.UtilProtos.Url DEFAULT_INSTANCE;
        static {
            DEFAULT_INSTANCE = new com.tradekraftcollective.microservice.utilities.UtilProtos.Url();
        }

        public static com.tradekraftcollective.microservice.utilities.UtilProtos.Url getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        @java.lang.Deprecated public static final com.google.protobuf.Parser<Url>
                PARSER = new com.google.protobuf.AbstractParser<Url>() {
            public Url parsePartialFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws com.google.protobuf.InvalidProtocolBufferException {
                return new Url(input, extensionRegistry);
            }
        };

        public static com.google.protobuf.Parser<Url> parser() {
            return PARSER;
        }

        @java.lang.Override
        public com.google.protobuf.Parser<Url> getParserForType() {
            return PARSER;
        }

        public com.tradekraftcollective.microservice.utilities.UtilProtos.Url getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_spotify_Url_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_spotify_Url_fieldAccessorTable;
    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_spotify_Url_Parameter_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_spotify_Url_Parameter_fieldAccessorTable;
    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_spotify_Url_Part_descriptor;
    private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_spotify_Url_Part_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor
    getDescriptor() {
        return descriptor;
    }
    private static  com.google.protobuf.Descriptors.FileDescriptor
            descriptor;
    static {
        java.lang.String[] descriptorData = {
                "\n\031src/main/proto/util.proto\022\007spotify\"\275\003\n" +
                        "\003Url\022#\n\006scheme\030\001 \002(\0162\023.spotify.Url.Schem" +
                        "e\022\014\n\004host\030\002 \002(\t\022\014\n\004port\030\003 \002(\005\022\014\n\004path\030\004 " +
                        "\002(\t\022*\n\nparameters\030\005 \003(\0132\026.spotify.Url.Pa" +
                        "rameter\022 \n\005parts\030\006 \003(\0132\021.spotify.Url.Par" +
                        "t\0220\n\020headerParameters\030\007 \003(\0132\026.spotify.Ur" +
                        "l.Parameter\022.\n\016bodyParameters\030\010 \003(\0132\026.sp" +
                        "otify.Url.Parameter\022\020\n\010jsonBody\030\t \001(\t\032(\n" +
                        "\tParameter\022\014\n\004name\030\001 \001(\t\022\r\n\005value\030\002 \001(\t\032" +
                        "\\\n\004Part\022\014\n\004name\030\001 \001(\t\022\020\n\010filename\030\002 \001(\t\022",
                "\024\n\014content_type\030\003 \001(\t\022\017\n\007charset\030\004 \001(\t\022\r" +
                        "\n\005value\030\005 \001(\014\"\035\n\006Scheme\022\010\n\004HTTP\020\000\022\t\n\005HTT" +
                        "PS\020\001B!\n\023com.wrapper.spotifyB\nUtilProtos"
        };
        com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
                new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
                    public com.google.protobuf.ExtensionRegistry assignDescriptors(
                            com.google.protobuf.Descriptors.FileDescriptor root) {
                        descriptor = root;
                        return null;
                    }
                };
        com.google.protobuf.Descriptors.FileDescriptor
                .internalBuildGeneratedFileFrom(descriptorData,
                        new com.google.protobuf.Descriptors.FileDescriptor[] {
                        }, assigner);
        internal_static_spotify_Url_descriptor =
                getDescriptor().getMessageTypes().get(0);
        internal_static_spotify_Url_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_spotify_Url_descriptor,
                new java.lang.String[] { "Scheme", "Host", "Port", "Path", "Parameters", "Parts", "HeaderParameters", "BodyParameters", "JsonBody", });
        internal_static_spotify_Url_Parameter_descriptor =
                internal_static_spotify_Url_descriptor.getNestedTypes().get(0);
        internal_static_spotify_Url_Parameter_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_spotify_Url_Parameter_descriptor,
                new java.lang.String[] { "Name", "Value", });
        internal_static_spotify_Url_Part_descriptor =
                internal_static_spotify_Url_descriptor.getNestedTypes().get(1);
        internal_static_spotify_Url_Part_fieldAccessorTable = new
                com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                internal_static_spotify_Url_Part_descriptor,
                new java.lang.String[] { "Name", "Filename", "ContentType", "Charset", "Value", });
    }

    // @@protoc_insertion_point(outer_class_scope)
}