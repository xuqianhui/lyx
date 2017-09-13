public void init(ServletConfig servletConfig) throws ServletException {
// Save our ServletConfig instance
this.servletConfig = servletConfig;
// Acquire our FacesContextFactory instance
try {
facesContextFactory = (FacesContextFactory)
FactoryFinder.getFactory
(FactoryFinder.FACES_CONTEXT_FACTORY);
} catch (FacesException e) {
ResourceBundle rb = LOGGER.getResourceBundle();
String msg = rb.getString("severe.webapp.facesservlet.init_failed");
Throwable rootCause = (e.getCause() != null) ? e.getCause() : e;
LOGGER.log(Level.SEVERE, msg, rootCause);
throw new UnavailableException(msg);
}
// Acquire our Lifecycle instance
try {
LifecycleFactory lifecycleFactory = (LifecycleFactory)
FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
String lifecycleId ;
// First look in the servlet init-param set
if (null == (lifecycleId = servletConfig.getInitParameter(LIFECYCLE_ID_ATTR))) {
// If not found, look in the context-param set
lifecycleId = servletConfig.getServletContext().getInitParameter
(LIFECYCLE_ID_ATTR);
}
if (lifecycleId == null) {
lifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
}
lifecycle = lifecycleFactory.getLifecycle(lifecycleId);
} catch (FacesException e) {
Throwable rootCause = e.getCause();
if (rootCause == null) {
throw e;
} else {
throw new ServletException(e.getMessage(), rootCause);
}
}
}
