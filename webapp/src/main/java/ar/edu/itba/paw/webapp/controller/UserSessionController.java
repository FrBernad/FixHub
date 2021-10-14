//    @Produces
//    @DELETE
//    @Path("/refreshToken")
//    public Response deleteRefreshToken(@HeaderParam(JwtUtil.REFRESH_TOKEN_HEADER) String sessionRefreshToken,
//                                       @QueryParam("allSessions") @DefaultValue("false") boolean allSessions) {
//
//        LOGGER.info("Accessed /user/refreshToken DELETE controller");
//
//        final Response.ResponseBuilder responseBuilder = Response.noContent();
//
//        if (sessionRefreshToken != null) {
//
//            if (allSessions) {
//                userService
//                    .getUserByRefreshToken(sessionRefreshToken)
//                    .ifPresent(user -> userService.deleteSessionRefreshToken(user));
//            }
//        }
//
//        return responseBuilder.build();
//    }
//
//}
